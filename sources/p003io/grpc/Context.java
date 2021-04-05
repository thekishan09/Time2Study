package p003io.grpc;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

/* renamed from: io.grpc.Context */
public class Context {
    static final int CONTEXT_DEPTH_WARN_THRESH = 1000;
    private static final PersistentHashArrayMappedTrie<Key<?>, Object> EMPTY_ENTRIES = new PersistentHashArrayMappedTrie<>();
    public static final Context ROOT = new Context((Context) null, EMPTY_ENTRIES);
    static final Logger log = Logger.getLogger(Context.class.getName());
    final CancellableContext cancellableAncestor;
    final int generation;
    final PersistentHashArrayMappedTrie<Key<?>, Object> keyValueEntries;
    private ArrayList<ExecutableListener> listeners;
    private CancellationListener parentListener;

    /* renamed from: io.grpc.Context$CanIgnoreReturnValue */
    @interface CanIgnoreReturnValue {
    }

    /* renamed from: io.grpc.Context$CancellationListener */
    public interface CancellationListener {
        void cancelled(Context context);
    }

    /* renamed from: io.grpc.Context$CheckReturnValue */
    @interface CheckReturnValue {
    }

    static Storage storage() {
        return LazyStorage.storage;
    }

    /* renamed from: io.grpc.Context$LazyStorage */
    private static final class LazyStorage {
        static final Storage storage;

        private LazyStorage() {
        }

        static {
            AtomicReference<Throwable> deferredStorageFailure = new AtomicReference<>();
            storage = createStorage(deferredStorageFailure);
            Throwable failure = deferredStorageFailure.get();
            if (failure != null) {
                Context.log.log(Level.FINE, "Storage override doesn't exist. Using default", failure);
            }
        }

        private static Storage createStorage(AtomicReference<? super ClassNotFoundException> deferredStorageFailure) {
            try {
                return (Storage) Class.forName("io.grpc.override.ContextStorageOverride").asSubclass(Storage.class).getConstructor(new Class[0]).newInstance(new Object[0]);
            } catch (ClassNotFoundException e) {
                deferredStorageFailure.set(e);
                return new ThreadLocalContextStorage();
            } catch (Exception e2) {
                throw new RuntimeException("Storage override failed to initialize", e2);
            }
        }
    }

    public static <T> Key<T> key(String name) {
        return new Key<>(name);
    }

    public static <T> Key<T> keyWithDefault(String name, T defaultValue) {
        return new Key<>(name, defaultValue);
    }

    public static Context current() {
        Context current = storage().current();
        if (current == null) {
            return ROOT;
        }
        return current;
    }

    private Context(PersistentHashArrayMappedTrie<Key<?>, Object> keyValueEntries2, int generation2) {
        this.parentListener = new ParentListener();
        this.cancellableAncestor = null;
        this.keyValueEntries = keyValueEntries2;
        this.generation = generation2;
        validateGeneration(generation2);
    }

    private Context(Context parent, PersistentHashArrayMappedTrie<Key<?>, Object> keyValueEntries2) {
        this.parentListener = new ParentListener();
        this.cancellableAncestor = cancellableAncestor(parent);
        this.keyValueEntries = keyValueEntries2;
        int i = parent == null ? 0 : parent.generation + 1;
        this.generation = i;
        validateGeneration(i);
    }

    public CancellableContext withCancellation() {
        return new CancellableContext();
    }

    public CancellableContext withDeadlineAfter(long duration, TimeUnit unit, ScheduledExecutorService scheduler) {
        return withDeadline(Deadline.after(duration, unit), scheduler);
    }

    public CancellableContext withDeadline(Deadline newDeadline, ScheduledExecutorService scheduler) {
        checkNotNull(newDeadline, "deadline");
        checkNotNull(scheduler, "scheduler");
        Deadline existingDeadline = getDeadline();
        boolean scheduleDeadlineCancellation = true;
        if (existingDeadline != null && existingDeadline.compareTo(newDeadline) <= 0) {
            newDeadline = existingDeadline;
            scheduleDeadlineCancellation = false;
        }
        CancellableContext newCtx = new CancellableContext(newDeadline);
        if (scheduleDeadlineCancellation) {
            newCtx.setUpDeadlineCancellation(newDeadline, scheduler);
        }
        return newCtx;
    }

    public <V> Context withValue(Key<V> k1, V v1) {
        return new Context(this, this.keyValueEntries.put(k1, v1));
    }

    public <V1, V2> Context withValues(Key<V1> k1, V1 v1, Key<V2> k2, V2 v2) {
        return new Context(this, this.keyValueEntries.put(k1, v1).put(k2, v2));
    }

    public <V1, V2, V3> Context withValues(Key<V1> k1, V1 v1, Key<V2> k2, V2 v2, Key<V3> k3, V3 v3) {
        return new Context(this, this.keyValueEntries.put(k1, v1).put(k2, v2).put(k3, v3));
    }

    public <V1, V2, V3, V4> Context withValues(Key<V1> k1, V1 v1, Key<V2> k2, V2 v2, Key<V3> k3, V3 v3, Key<V4> k4, V4 v4) {
        return new Context(this, this.keyValueEntries.put(k1, v1).put(k2, v2).put(k3, v3).put(k4, v4));
    }

    public Context fork() {
        return new Context(this.keyValueEntries, this.generation + 1);
    }

    /* access modifiers changed from: package-private */
    public boolean canBeCancelled() {
        return this.cancellableAncestor != null;
    }

    public Context attach() {
        Context prev = storage().doAttach(this);
        if (prev == null) {
            return ROOT;
        }
        return prev;
    }

    public void detach(Context toAttach) {
        checkNotNull(toAttach, "toAttach");
        storage().detach(this, toAttach);
    }

    /* access modifiers changed from: package-private */
    public boolean isCurrent() {
        return current() == this;
    }

    public boolean isCancelled() {
        CancellableContext cancellableContext = this.cancellableAncestor;
        if (cancellableContext == null) {
            return false;
        }
        return cancellableContext.isCancelled();
    }

    public Throwable cancellationCause() {
        CancellableContext cancellableContext = this.cancellableAncestor;
        if (cancellableContext == null) {
            return null;
        }
        return cancellableContext.cancellationCause();
    }

    public Deadline getDeadline() {
        CancellableContext cancellableContext = this.cancellableAncestor;
        if (cancellableContext == null) {
            return null;
        }
        return cancellableContext.getDeadline();
    }

    public void addListener(CancellationListener cancellationListener, Executor executor) {
        checkNotNull(cancellationListener, "cancellationListener");
        checkNotNull(executor, "executor");
        if (canBeCancelled()) {
            ExecutableListener executableListener = new ExecutableListener(executor, cancellationListener);
            synchronized (this) {
                if (isCancelled()) {
                    executableListener.deliver();
                } else if (this.listeners == null) {
                    ArrayList<ExecutableListener> arrayList = new ArrayList<>();
                    this.listeners = arrayList;
                    arrayList.add(executableListener);
                    if (this.cancellableAncestor != null) {
                        this.cancellableAncestor.addListener(this.parentListener, DirectExecutor.INSTANCE);
                    }
                } else {
                    this.listeners.add(executableListener);
                }
            }
        }
    }

    public void removeListener(CancellationListener cancellationListener) {
        if (canBeCancelled()) {
            synchronized (this) {
                if (this.listeners != null) {
                    int i = this.listeners.size() - 1;
                    while (true) {
                        if (i < 0) {
                            break;
                        } else if (this.listeners.get(i).listener == cancellationListener) {
                            this.listeners.remove(i);
                            break;
                        } else {
                            i--;
                        }
                    }
                    if (this.listeners.isEmpty()) {
                        if (this.cancellableAncestor != null) {
                            this.cancellableAncestor.removeListener(this.parentListener);
                        }
                        this.listeners = null;
                    }
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0014, code lost:
        r1 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0019, code lost:
        if (r1 >= r0.size()) goto L_0x0033;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0025, code lost:
        if ((r0.get(r1).listener instanceof p003io.grpc.Context.ParentListener) != false) goto L_0x0030;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0027, code lost:
        r0.get(r1).deliver();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0030, code lost:
        r1 = r1 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0033, code lost:
        r1 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0038, code lost:
        if (r1 >= r0.size()) goto L_0x0052;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0044, code lost:
        if ((r0.get(r1).listener instanceof p003io.grpc.Context.ParentListener) == false) goto L_0x004f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0046, code lost:
        r0.get(r1).deliver();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x004f, code lost:
        r1 = r1 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0052, code lost:
        r1 = r3.cancellableAncestor;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0054, code lost:
        if (r1 == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0056, code lost:
        r1.removeListener(r3.parentListener);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void notifyAndClearListeners() {
        /*
            r3 = this;
            boolean r0 = r3.canBeCancelled()
            if (r0 != 0) goto L_0x0007
            return
        L_0x0007:
            monitor-enter(r3)
            java.util.ArrayList<io.grpc.Context$ExecutableListener> r0 = r3.listeners     // Catch:{ all -> 0x005c }
            if (r0 != 0) goto L_0x000e
            monitor-exit(r3)     // Catch:{ all -> 0x005c }
            return
        L_0x000e:
            java.util.ArrayList<io.grpc.Context$ExecutableListener> r0 = r3.listeners     // Catch:{ all -> 0x005c }
            r1 = 0
            r3.listeners = r1     // Catch:{ all -> 0x005c }
            monitor-exit(r3)     // Catch:{ all -> 0x005c }
            r1 = 0
        L_0x0015:
            int r2 = r0.size()
            if (r1 >= r2) goto L_0x0033
            java.lang.Object r2 = r0.get(r1)
            io.grpc.Context$ExecutableListener r2 = (p003io.grpc.Context.ExecutableListener) r2
            io.grpc.Context$CancellationListener r2 = r2.listener
            boolean r2 = r2 instanceof p003io.grpc.Context.ParentListener
            if (r2 != 0) goto L_0x0030
            java.lang.Object r2 = r0.get(r1)
            io.grpc.Context$ExecutableListener r2 = (p003io.grpc.Context.ExecutableListener) r2
            r2.deliver()
        L_0x0030:
            int r1 = r1 + 1
            goto L_0x0015
        L_0x0033:
            r1 = 0
        L_0x0034:
            int r2 = r0.size()
            if (r1 >= r2) goto L_0x0052
            java.lang.Object r2 = r0.get(r1)
            io.grpc.Context$ExecutableListener r2 = (p003io.grpc.Context.ExecutableListener) r2
            io.grpc.Context$CancellationListener r2 = r2.listener
            boolean r2 = r2 instanceof p003io.grpc.Context.ParentListener
            if (r2 == 0) goto L_0x004f
            java.lang.Object r2 = r0.get(r1)
            io.grpc.Context$ExecutableListener r2 = (p003io.grpc.Context.ExecutableListener) r2
            r2.deliver()
        L_0x004f:
            int r1 = r1 + 1
            goto L_0x0034
        L_0x0052:
            io.grpc.Context$CancellableContext r1 = r3.cancellableAncestor
            if (r1 == 0) goto L_0x005b
            io.grpc.Context$CancellationListener r2 = r3.parentListener
            r1.removeListener(r2)
        L_0x005b:
            return
        L_0x005c:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x005c }
            goto L_0x0060
        L_0x005f:
            throw r0
        L_0x0060:
            goto L_0x005f
        */
        throw new UnsupportedOperationException("Method not decompiled: p003io.grpc.Context.notifyAndClearListeners():void");
    }

    /* access modifiers changed from: package-private */
    public int listenerCount() {
        int size;
        synchronized (this) {
            size = this.listeners == null ? 0 : this.listeners.size();
        }
        return size;
    }

    public void run(Runnable r) {
        Context previous = attach();
        try {
            r.run();
        } finally {
            detach(previous);
        }
    }

    public <V> V call(Callable<V> c) throws Exception {
        Context previous = attach();
        try {
            return c.call();
        } finally {
            detach(previous);
        }
    }

    public Runnable wrap(final Runnable r) {
        return new Runnable() {
            public void run() {
                Context previous = Context.this.attach();
                try {
                    r.run();
                } finally {
                    Context.this.detach(previous);
                }
            }
        };
    }

    public <C> Callable<C> wrap(final Callable<C> c) {
        return new Callable<C>() {
            public C call() throws Exception {
                Context previous = Context.this.attach();
                try {
                    return c.call();
                } finally {
                    Context.this.detach(previous);
                }
            }
        };
    }

    public Executor fixedContextExecutor(final Executor e) {
        return new Executor() {
            public void execute(Runnable r) {
                e.execute(Context.this.wrap(r));
            }
        };
    }

    public static Executor currentContextExecutor(final Executor e) {
        return new Executor() {
            public void execute(Runnable r) {
                e.execute(Context.current().wrap(r));
            }
        };
    }

    /* access modifiers changed from: package-private */
    public Object lookup(Key<?> key) {
        return this.keyValueEntries.get(key);
    }

    /* renamed from: io.grpc.Context$CancellableContext */
    public static final class CancellableContext extends Context implements Closeable {
        private Throwable cancellationCause;
        private boolean cancelled;
        private final Deadline deadline;
        private ScheduledFuture<?> pendingDeadline;
        private final Context uncancellableSurrogate;

        private CancellableContext(Context parent) {
            super(parent.keyValueEntries);
            this.deadline = parent.getDeadline();
            this.uncancellableSurrogate = new Context(this.keyValueEntries);
        }

        private CancellableContext(Context parent, Deadline deadline2) {
            super(parent.keyValueEntries);
            this.deadline = deadline2;
            this.uncancellableSurrogate = new Context(this.keyValueEntries);
        }

        /* access modifiers changed from: private */
        public void setUpDeadlineCancellation(Deadline deadline2, ScheduledExecutorService scheduler) {
            if (!deadline2.isExpired()) {
                synchronized (this) {
                    this.pendingDeadline = deadline2.runOnExpiration(new Runnable() {
                        public void run() {
                            try {
                                CancellableContext.this.cancel(new TimeoutException("context timed out"));
                            } catch (Throwable t) {
                                Context.log.log(Level.SEVERE, "Cancel threw an exception, which should not happen", t);
                            }
                        }
                    }, scheduler);
                }
                return;
            }
            cancel(new TimeoutException("context timed out"));
        }

        public Context attach() {
            return this.uncancellableSurrogate.attach();
        }

        public void detach(Context toAttach) {
            this.uncancellableSurrogate.detach(toAttach);
        }

        @Deprecated
        public boolean isCurrent() {
            return this.uncancellableSurrogate.isCurrent();
        }

        public boolean cancel(Throwable cause) {
            boolean triggeredCancel = false;
            synchronized (this) {
                if (!this.cancelled) {
                    this.cancelled = true;
                    if (this.pendingDeadline != null) {
                        this.pendingDeadline.cancel(false);
                        this.pendingDeadline = null;
                    }
                    this.cancellationCause = cause;
                    triggeredCancel = true;
                }
            }
            if (triggeredCancel) {
                notifyAndClearListeners();
            }
            return triggeredCancel;
        }

        public void detachAndCancel(Context toAttach, Throwable cause) {
            try {
                detach(toAttach);
            } finally {
                cancel(cause);
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0016, code lost:
            return true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0017, code lost:
            return false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x000d, code lost:
            if (p003io.grpc.Context.super.isCancelled() == false) goto L_0x0017;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x000f, code lost:
            cancel(p003io.grpc.Context.super.cancellationCause());
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean isCancelled() {
            /*
                r2 = this;
                monitor-enter(r2)
                boolean r0 = r2.cancelled     // Catch:{ all -> 0x0019 }
                r1 = 1
                if (r0 == 0) goto L_0x0008
                monitor-exit(r2)     // Catch:{ all -> 0x0019 }
                return r1
            L_0x0008:
                monitor-exit(r2)     // Catch:{ all -> 0x0019 }
                boolean r0 = p003io.grpc.Context.super.isCancelled()
                if (r0 == 0) goto L_0x0017
                java.lang.Throwable r0 = p003io.grpc.Context.super.cancellationCause()
                r2.cancel(r0)
                return r1
            L_0x0017:
                r0 = 0
                return r0
            L_0x0019:
                r0 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x0019 }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: p003io.grpc.Context.CancellableContext.isCancelled():boolean");
        }

        public Throwable cancellationCause() {
            if (isCancelled()) {
                return this.cancellationCause;
            }
            return null;
        }

        public Deadline getDeadline() {
            return this.deadline;
        }

        /* access modifiers changed from: package-private */
        public boolean canBeCancelled() {
            return true;
        }

        public void close() {
            cancel((Throwable) null);
        }
    }

    /* renamed from: io.grpc.Context$Key */
    public static final class Key<T> {
        private final T defaultValue;
        private final String name;

        Key(String name2) {
            this(name2, (Object) null);
        }

        Key(String name2, T defaultValue2) {
            this.name = (String) Context.checkNotNull(name2, "name");
            this.defaultValue = defaultValue2;
        }

        public T get() {
            return get(Context.current());
        }

        public T get(Context context) {
            T value = context.lookup(this);
            return value == null ? this.defaultValue : value;
        }

        public String toString() {
            return this.name;
        }
    }

    /* renamed from: io.grpc.Context$Storage */
    public static abstract class Storage {
        public abstract Context current();

        public abstract void detach(Context context, Context context2);

        @Deprecated
        public void attach(Context toAttach) {
            throw new UnsupportedOperationException("Deprecated. Do not call.");
        }

        public Context doAttach(Context toAttach) {
            Context current = current();
            attach(toAttach);
            return current;
        }
    }

    /* renamed from: io.grpc.Context$ExecutableListener */
    private final class ExecutableListener implements Runnable {
        private final Executor executor;
        final CancellationListener listener;

        ExecutableListener(Executor executor2, CancellationListener listener2) {
            this.executor = executor2;
            this.listener = listener2;
        }

        /* access modifiers changed from: package-private */
        public void deliver() {
            try {
                this.executor.execute(this);
            } catch (Throwable t) {
                Context.log.log(Level.INFO, "Exception notifying context listener", t);
            }
        }

        public void run() {
            this.listener.cancelled(Context.this);
        }
    }

    /* renamed from: io.grpc.Context$ParentListener */
    private final class ParentListener implements CancellationListener {
        private ParentListener() {
        }

        public void cancelled(Context context) {
            Context context2 = Context.this;
            if (context2 instanceof CancellableContext) {
                ((CancellableContext) context2).cancel(context.cancellationCause());
            } else {
                context2.notifyAndClearListeners();
            }
        }
    }

    static <T> T checkNotNull(T reference, Object errorMessage) {
        if (reference != null) {
            return reference;
        }
        throw new NullPointerException(String.valueOf(errorMessage));
    }

    /* renamed from: io.grpc.Context$DirectExecutor */
    private enum DirectExecutor implements Executor {
        INSTANCE;

        public void execute(Runnable command) {
            command.run();
        }

        public String toString() {
            return "Context.DirectExecutor";
        }
    }

    static CancellableContext cancellableAncestor(Context parent) {
        if (parent == null) {
            return null;
        }
        if (parent instanceof CancellableContext) {
            return (CancellableContext) parent;
        }
        return parent.cancellableAncestor;
    }

    private static void validateGeneration(int generation2) {
        if (generation2 == 1000) {
            log.log(Level.SEVERE, "Context ancestry chain length is abnormally long. This suggests an error in application code. Length exceeded: 1000", new Exception());
        }
    }
}
