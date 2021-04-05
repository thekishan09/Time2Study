package com.google.firebase.storage;

import android.app.Activity;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.storage.StorageTask.ProvideError;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.Executor;

/* compiled from: com.google.firebase:firebase-storage@@19.1.1 */
public abstract class StorageTask<ResultT extends ProvideError> extends ControllableTask<ResultT> {
    static final int INTERNAL_STATE_CANCELED = 256;
    static final int INTERNAL_STATE_CANCELING = 32;
    static final int INTERNAL_STATE_FAILURE = 64;
    static final int INTERNAL_STATE_IN_PROGRESS = 4;
    static final int INTERNAL_STATE_NOT_STARTED = 1;
    static final int INTERNAL_STATE_PAUSED = 16;
    static final int INTERNAL_STATE_PAUSING = 8;
    static final int INTERNAL_STATE_QUEUED = 2;
    static final int INTERNAL_STATE_SUCCESS = 128;
    static final int STATES_CANCELED = 256;
    static final int STATES_COMPLETE = 448;
    static final int STATES_FAILURE = 64;
    static final int STATES_INPROGRESS = -465;
    static final int STATES_PAUSED = 16;
    static final int STATES_SUCCESS = 128;
    private static final String TAG = "StorageTask";
    private static final HashMap<Integer, HashSet<Integer>> ValidTaskInitiatedStateChanges = new HashMap<>();
    private static final HashMap<Integer, HashSet<Integer>> ValidUserInitiatedStateChanges = new HashMap<>();
    final TaskListenerImpl<OnCanceledListener, ResultT> cancelManager = new TaskListenerImpl<>(this, 256, StorageTask$$Lambda$6.lambdaFactory$(this));
    final TaskListenerImpl<OnCompleteListener<ResultT>, ResultT> completeListener = new TaskListenerImpl<>(this, STATES_COMPLETE, StorageTask$$Lambda$5.lambdaFactory$(this));
    private volatile int currentState = 1;
    final TaskListenerImpl<OnFailureListener, ResultT> failureManager = new TaskListenerImpl<>(this, 64, StorageTask$$Lambda$4.lambdaFactory$(this));
    private ResultT finalResult;
    final TaskListenerImpl<OnPausedListener<? super ResultT>, ResultT> pausedManager = new TaskListenerImpl<>(this, 16, StorageTask$$Lambda$8.lambdaFactory$());
    final TaskListenerImpl<OnProgressListener<? super ResultT>, ResultT> progressManager = new TaskListenerImpl<>(this, STATES_INPROGRESS, StorageTask$$Lambda$7.lambdaFactory$());
    final TaskListenerImpl<OnSuccessListener<? super ResultT>, ResultT> successManager = new TaskListenerImpl<>(this, 128, StorageTask$$Lambda$1.lambdaFactory$(this));
    protected final Object syncObject = new Object();

    /* compiled from: com.google.firebase:firebase-storage@@19.1.1 */
    protected interface ProvideError {
        Exception getError();
    }

    /* access modifiers changed from: package-private */
    public abstract StorageReference getStorage();

    /* access modifiers changed from: package-private */
    public abstract void run();

    /* access modifiers changed from: package-private */
    public abstract void schedule();

    /* access modifiers changed from: package-private */
    public abstract ResultT snapStateImpl();

    static {
        ValidUserInitiatedStateChanges.put(1, new HashSet(Arrays.asList(new Integer[]{16, 256})));
        ValidUserInitiatedStateChanges.put(2, new HashSet(Arrays.asList(new Integer[]{8, 32})));
        ValidUserInitiatedStateChanges.put(4, new HashSet(Arrays.asList(new Integer[]{8, 32})));
        ValidUserInitiatedStateChanges.put(16, new HashSet(Arrays.asList(new Integer[]{2, 256})));
        ValidUserInitiatedStateChanges.put(64, new HashSet(Arrays.asList(new Integer[]{2, 256})));
        ValidTaskInitiatedStateChanges.put(1, new HashSet(Arrays.asList(new Integer[]{2, 64})));
        ValidTaskInitiatedStateChanges.put(2, new HashSet(Arrays.asList(new Integer[]{4, 64, 128})));
        ValidTaskInitiatedStateChanges.put(4, new HashSet(Arrays.asList(new Integer[]{4, 64, 128})));
        ValidTaskInitiatedStateChanges.put(8, new HashSet(Arrays.asList(new Integer[]{16, 64, 128})));
        ValidTaskInitiatedStateChanges.put(32, new HashSet(Arrays.asList(new Integer[]{256, 64, 128})));
    }

    static /* synthetic */ void lambda$new$0(StorageTask storageTask, OnSuccessListener listener, ProvideError snappedState) {
        StorageTaskManager.getInstance().unRegister(storageTask);
        listener.onSuccess(snappedState);
    }

    static /* synthetic */ void lambda$new$1(StorageTask storageTask, OnFailureListener listener, ProvideError snappedState) {
        StorageTaskManager.getInstance().unRegister(storageTask);
        listener.onFailure(snappedState.getError());
    }

    static /* synthetic */ void lambda$new$2(StorageTask storageTask, OnCompleteListener listener, ProvideError snappedState) {
        StorageTaskManager.getInstance().unRegister(storageTask);
        listener.onComplete(storageTask);
    }

    static /* synthetic */ void lambda$new$3(StorageTask storageTask, OnCanceledListener listener, ProvideError snappedState) {
        StorageTaskManager.getInstance().unRegister(storageTask);
        listener.onCanceled();
    }

    protected StorageTask() {
    }

    /* access modifiers changed from: package-private */
    public boolean queue() {
        if (!tryChangeState(2, false)) {
            return false;
        }
        schedule();
        return true;
    }

    /* access modifiers changed from: package-private */
    public void resetState() {
    }

    public boolean resume() {
        if (!tryChangeState(2, true)) {
            return false;
        }
        resetState();
        schedule();
        return true;
    }

    public boolean pause() {
        return tryChangeState(new int[]{16, 8}, true);
    }

    public boolean cancel() {
        return tryChangeState(new int[]{256, 32}, true);
    }

    public boolean isComplete() {
        return (getInternalState() & STATES_COMPLETE) != 0;
    }

    public boolean isSuccessful() {
        return (getInternalState() & 128) != 0;
    }

    public boolean isCanceled() {
        return getInternalState() == 256;
    }

    public boolean isInProgress() {
        return (getInternalState() & STATES_INPROGRESS) != 0;
    }

    public boolean isPaused() {
        return (getInternalState() & 16) != 0;
    }

    public ResultT getResult() {
        if (getFinalResult() != null) {
            Throwable t = getFinalResult().getError();
            if (t == null) {
                return getFinalResult();
            }
            throw new RuntimeExecutionException(t);
        }
        throw new IllegalStateException();
    }

    public <X extends Throwable> ResultT getResult(Class<X> exceptionType) throws Throwable {
        if (getFinalResult() == null) {
            throw new IllegalStateException();
        } else if (!exceptionType.isInstance(getFinalResult().getError())) {
            Throwable t = getFinalResult().getError();
            if (t == null) {
                return getFinalResult();
            }
            throw new RuntimeExecutionException(t);
        } else {
            throw ((Throwable) exceptionType.cast(getFinalResult().getError()));
        }
    }

    public Exception getException() {
        if (getFinalResult() == null) {
            return null;
        }
        return getFinalResult().getError();
    }

    public ResultT getSnapshot() {
        return snapState();
    }

    /* access modifiers changed from: package-private */
    public int getInternalState() {
        return this.currentState;
    }

    /* access modifiers changed from: package-private */
    public Object getSyncObject() {
        return this.syncObject;
    }

    /* access modifiers changed from: package-private */
    public ResultT snapState() {
        ResultT snapStateImpl;
        synchronized (this.syncObject) {
            snapStateImpl = snapStateImpl();
        }
        return snapStateImpl;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00bd, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean tryChangeState(int[] r9, boolean r10) {
        /*
            r8 = this;
            if (r10 == 0) goto L_0x0005
            java.util.HashMap<java.lang.Integer, java.util.HashSet<java.lang.Integer>> r0 = ValidUserInitiatedStateChanges
            goto L_0x0007
        L_0x0005:
            java.util.HashMap<java.lang.Integer, java.util.HashSet<java.lang.Integer>> r0 = ValidTaskInitiatedStateChanges
        L_0x0007:
            java.lang.Object r1 = r8.syncObject
            monitor-enter(r1)
            int r2 = r9.length     // Catch:{ all -> 0x00f5 }
            r3 = 0
            r4 = 0
        L_0x000d:
            if (r4 >= r2) goto L_0x00c3
            r5 = r9[r4]     // Catch:{ all -> 0x00f5 }
            int r6 = r8.getInternalState()     // Catch:{ all -> 0x00f5 }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ all -> 0x00f5 }
            java.lang.Object r6 = r0.get(r6)     // Catch:{ all -> 0x00f5 }
            java.util.HashSet r6 = (java.util.HashSet) r6     // Catch:{ all -> 0x00f5 }
            if (r6 == 0) goto L_0x00bf
            java.lang.Integer r7 = java.lang.Integer.valueOf(r5)     // Catch:{ all -> 0x00f5 }
            boolean r7 = r6.contains(r7)     // Catch:{ all -> 0x00f5 }
            if (r7 == 0) goto L_0x00bf
            r8.currentState = r5     // Catch:{ all -> 0x00f5 }
            int r2 = r8.currentState     // Catch:{ all -> 0x00f5 }
            r3 = 2
            if (r2 == r3) goto L_0x005a
            r3 = 4
            if (r2 == r3) goto L_0x0056
            r3 = 16
            if (r2 == r3) goto L_0x0052
            r3 = 64
            if (r2 == r3) goto L_0x004e
            r3 = 128(0x80, float:1.794E-43)
            if (r2 == r3) goto L_0x004a
            r3 = 256(0x100, float:3.59E-43)
            if (r2 == r3) goto L_0x0046
            goto L_0x0065
        L_0x0046:
            r8.onCanceled()     // Catch:{ all -> 0x00f5 }
            goto L_0x0065
        L_0x004a:
            r8.onSuccess()     // Catch:{ all -> 0x00f5 }
            goto L_0x0065
        L_0x004e:
            r8.onFailure()     // Catch:{ all -> 0x00f5 }
            goto L_0x0065
        L_0x0052:
            r8.onPaused()     // Catch:{ all -> 0x00f5 }
            goto L_0x0065
        L_0x0056:
            r8.onProgress()     // Catch:{ all -> 0x00f5 }
            goto L_0x0065
        L_0x005a:
            com.google.firebase.storage.StorageTaskManager r2 = com.google.firebase.storage.StorageTaskManager.getInstance()     // Catch:{ all -> 0x00f5 }
            r2.ensureRegistered(r8)     // Catch:{ all -> 0x00f5 }
            r8.onQueued()     // Catch:{ all -> 0x00f5 }
        L_0x0065:
            com.google.firebase.storage.TaskListenerImpl<com.google.android.gms.tasks.OnSuccessListener<? super ResultT>, ResultT> r2 = r8.successManager     // Catch:{ all -> 0x00f5 }
            r2.onInternalStateChanged()     // Catch:{ all -> 0x00f5 }
            com.google.firebase.storage.TaskListenerImpl<com.google.android.gms.tasks.OnFailureListener, ResultT> r2 = r8.failureManager     // Catch:{ all -> 0x00f5 }
            r2.onInternalStateChanged()     // Catch:{ all -> 0x00f5 }
            com.google.firebase.storage.TaskListenerImpl<com.google.android.gms.tasks.OnCanceledListener, ResultT> r2 = r8.cancelManager     // Catch:{ all -> 0x00f5 }
            r2.onInternalStateChanged()     // Catch:{ all -> 0x00f5 }
            com.google.firebase.storage.TaskListenerImpl<com.google.android.gms.tasks.OnCompleteListener<ResultT>, ResultT> r2 = r8.completeListener     // Catch:{ all -> 0x00f5 }
            r2.onInternalStateChanged()     // Catch:{ all -> 0x00f5 }
            com.google.firebase.storage.TaskListenerImpl<com.google.firebase.storage.OnPausedListener<? super ResultT>, ResultT> r2 = r8.pausedManager     // Catch:{ all -> 0x00f5 }
            r2.onInternalStateChanged()     // Catch:{ all -> 0x00f5 }
            com.google.firebase.storage.TaskListenerImpl<com.google.firebase.storage.OnProgressListener<? super ResultT>, ResultT> r2 = r8.progressManager     // Catch:{ all -> 0x00f5 }
            r2.onInternalStateChanged()     // Catch:{ all -> 0x00f5 }
            java.lang.String r2 = "StorageTask"
            r3 = 3
            boolean r2 = android.util.Log.isLoggable(r2, r3)     // Catch:{ all -> 0x00f5 }
            if (r2 == 0) goto L_0x00bc
            java.lang.String r2 = "StorageTask"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00f5 }
            r3.<init>()     // Catch:{ all -> 0x00f5 }
            java.lang.String r4 = "changed internal state to: "
            r3.append(r4)     // Catch:{ all -> 0x00f5 }
            java.lang.String r4 = r8.getStateString((int) r5)     // Catch:{ all -> 0x00f5 }
            r3.append(r4)     // Catch:{ all -> 0x00f5 }
            java.lang.String r4 = " isUser: "
            r3.append(r4)     // Catch:{ all -> 0x00f5 }
            r3.append(r10)     // Catch:{ all -> 0x00f5 }
            java.lang.String r4 = " from state:"
            r3.append(r4)     // Catch:{ all -> 0x00f5 }
            int r4 = r8.currentState     // Catch:{ all -> 0x00f5 }
            java.lang.String r4 = r8.getStateString((int) r4)     // Catch:{ all -> 0x00f5 }
            r3.append(r4)     // Catch:{ all -> 0x00f5 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x00f5 }
            android.util.Log.d(r2, r3)     // Catch:{ all -> 0x00f5 }
        L_0x00bc:
            monitor-exit(r1)     // Catch:{ all -> 0x00f5 }
            r1 = 1
            return r1
        L_0x00bf:
            int r4 = r4 + 1
            goto L_0x000d
        L_0x00c3:
            java.lang.String r2 = "StorageTask"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x00f5 }
            r4.<init>()     // Catch:{ all -> 0x00f5 }
            java.lang.String r5 = "unable to change internal state to: "
            r4.append(r5)     // Catch:{ all -> 0x00f5 }
            java.lang.String r5 = r8.getStateString((int[]) r9)     // Catch:{ all -> 0x00f5 }
            r4.append(r5)     // Catch:{ all -> 0x00f5 }
            java.lang.String r5 = " isUser: "
            r4.append(r5)     // Catch:{ all -> 0x00f5 }
            r4.append(r10)     // Catch:{ all -> 0x00f5 }
            java.lang.String r5 = " from state:"
            r4.append(r5)     // Catch:{ all -> 0x00f5 }
            int r5 = r8.currentState     // Catch:{ all -> 0x00f5 }
            java.lang.String r5 = r8.getStateString((int) r5)     // Catch:{ all -> 0x00f5 }
            r4.append(r5)     // Catch:{ all -> 0x00f5 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x00f5 }
            android.util.Log.w(r2, r4)     // Catch:{ all -> 0x00f5 }
            monitor-exit(r1)     // Catch:{ all -> 0x00f5 }
            return r3
        L_0x00f5:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x00f5 }
            goto L_0x00f9
        L_0x00f8:
            throw r2
        L_0x00f9:
            goto L_0x00f8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.storage.StorageTask.tryChangeState(int[], boolean):boolean");
    }

    /* access modifiers changed from: package-private */
    public boolean tryChangeState(int newState, boolean userInitiated) {
        return tryChangeState(new int[]{newState}, userInitiated);
    }

    /* access modifiers changed from: protected */
    public void onQueued() {
    }

    /* access modifiers changed from: protected */
    public void onProgress() {
    }

    /* access modifiers changed from: protected */
    public void onPaused() {
    }

    /* access modifiers changed from: protected */
    public void onFailure() {
    }

    /* access modifiers changed from: protected */
    public void onSuccess() {
    }

    /* access modifiers changed from: protected */
    public void onCanceled() {
    }

    private ResultT getFinalResult() {
        ResultT resultt = this.finalResult;
        if (resultt != null) {
            return resultt;
        }
        if (!isComplete()) {
            return null;
        }
        if (this.finalResult == null) {
            this.finalResult = snapState();
        }
        return this.finalResult;
    }

    public StorageTask<ResultT> addOnPausedListener(OnPausedListener<? super ResultT> listener) {
        Preconditions.checkNotNull(listener);
        this.pausedManager.addListener((Activity) null, (Executor) null, listener);
        return this;
    }

    public StorageTask<ResultT> addOnPausedListener(Executor executor, OnPausedListener<? super ResultT> listener) {
        Preconditions.checkNotNull(listener);
        Preconditions.checkNotNull(executor);
        this.pausedManager.addListener((Activity) null, executor, listener);
        return this;
    }

    public StorageTask<ResultT> addOnPausedListener(Activity activity, OnPausedListener<? super ResultT> listener) {
        Preconditions.checkNotNull(listener);
        Preconditions.checkNotNull(activity);
        this.pausedManager.addListener(activity, (Executor) null, listener);
        return this;
    }

    public StorageTask<ResultT> removeOnPausedListener(OnPausedListener<? super ResultT> listener) {
        Preconditions.checkNotNull(listener);
        this.pausedManager.removeListener(listener);
        return this;
    }

    public StorageTask<ResultT> addOnProgressListener(OnProgressListener<? super ResultT> listener) {
        Preconditions.checkNotNull(listener);
        this.progressManager.addListener((Activity) null, (Executor) null, listener);
        return this;
    }

    public StorageTask<ResultT> addOnProgressListener(Executor executor, OnProgressListener<? super ResultT> listener) {
        Preconditions.checkNotNull(listener);
        Preconditions.checkNotNull(executor);
        this.progressManager.addListener((Activity) null, executor, listener);
        return this;
    }

    public StorageTask<ResultT> addOnProgressListener(Activity activity, OnProgressListener<? super ResultT> listener) {
        Preconditions.checkNotNull(listener);
        Preconditions.checkNotNull(activity);
        this.progressManager.addListener(activity, (Executor) null, listener);
        return this;
    }

    public StorageTask<ResultT> removeOnProgressListener(OnProgressListener<? super ResultT> listener) {
        Preconditions.checkNotNull(listener);
        this.progressManager.removeListener(listener);
        return this;
    }

    public StorageTask<ResultT> addOnSuccessListener(OnSuccessListener<? super ResultT> listener) {
        Preconditions.checkNotNull(listener);
        this.successManager.addListener((Activity) null, (Executor) null, listener);
        return this;
    }

    public StorageTask<ResultT> addOnSuccessListener(Executor executor, OnSuccessListener<? super ResultT> listener) {
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(listener);
        this.successManager.addListener((Activity) null, executor, listener);
        return this;
    }

    public StorageTask<ResultT> addOnSuccessListener(Activity activity, OnSuccessListener<? super ResultT> listener) {
        Preconditions.checkNotNull(activity);
        Preconditions.checkNotNull(listener);
        this.successManager.addListener(activity, (Executor) null, listener);
        return this;
    }

    public StorageTask<ResultT> removeOnSuccessListener(OnSuccessListener<? super ResultT> listener) {
        Preconditions.checkNotNull(listener);
        this.successManager.removeListener(listener);
        return this;
    }

    public StorageTask<ResultT> addOnFailureListener(OnFailureListener listener) {
        Preconditions.checkNotNull(listener);
        this.failureManager.addListener((Activity) null, (Executor) null, listener);
        return this;
    }

    public StorageTask<ResultT> addOnFailureListener(Executor executor, OnFailureListener listener) {
        Preconditions.checkNotNull(listener);
        Preconditions.checkNotNull(executor);
        this.failureManager.addListener((Activity) null, executor, listener);
        return this;
    }

    public StorageTask<ResultT> addOnFailureListener(Activity activity, OnFailureListener listener) {
        Preconditions.checkNotNull(listener);
        Preconditions.checkNotNull(activity);
        this.failureManager.addListener(activity, (Executor) null, listener);
        return this;
    }

    public StorageTask<ResultT> removeOnFailureListener(OnFailureListener listener) {
        Preconditions.checkNotNull(listener);
        this.failureManager.removeListener(listener);
        return this;
    }

    public StorageTask<ResultT> addOnCompleteListener(OnCompleteListener<ResultT> listener) {
        Preconditions.checkNotNull(listener);
        this.completeListener.addListener((Activity) null, (Executor) null, listener);
        return this;
    }

    public StorageTask<ResultT> addOnCompleteListener(Executor executor, OnCompleteListener<ResultT> listener) {
        Preconditions.checkNotNull(listener);
        Preconditions.checkNotNull(executor);
        this.completeListener.addListener((Activity) null, executor, listener);
        return this;
    }

    public StorageTask<ResultT> addOnCompleteListener(Activity activity, OnCompleteListener<ResultT> listener) {
        Preconditions.checkNotNull(listener);
        Preconditions.checkNotNull(activity);
        this.completeListener.addListener(activity, (Executor) null, listener);
        return this;
    }

    public StorageTask<ResultT> removeOnCompleteListener(OnCompleteListener<ResultT> listener) {
        Preconditions.checkNotNull(listener);
        this.completeListener.removeListener(listener);
        return this;
    }

    public StorageTask<ResultT> addOnCanceledListener(OnCanceledListener listener) {
        Preconditions.checkNotNull(listener);
        this.cancelManager.addListener((Activity) null, (Executor) null, listener);
        return this;
    }

    public StorageTask<ResultT> addOnCanceledListener(Executor executor, OnCanceledListener listener) {
        Preconditions.checkNotNull(listener);
        Preconditions.checkNotNull(executor);
        this.cancelManager.addListener((Activity) null, executor, listener);
        return this;
    }

    public StorageTask<ResultT> addOnCanceledListener(Activity activity, OnCanceledListener listener) {
        Preconditions.checkNotNull(listener);
        Preconditions.checkNotNull(activity);
        this.cancelManager.addListener(activity, (Executor) null, listener);
        return this;
    }

    public StorageTask<ResultT> removeOnCanceledListener(OnCanceledListener listener) {
        Preconditions.checkNotNull(listener);
        this.cancelManager.removeListener(listener);
        return this;
    }

    public <ContinuationResultT> Task<ContinuationResultT> continueWith(Continuation<ResultT, ContinuationResultT> continuation) {
        return continueWithImpl((Executor) null, continuation);
    }

    public <ContinuationResultT> Task<ContinuationResultT> continueWith(Executor executor, Continuation<ResultT, ContinuationResultT> continuation) {
        return continueWithImpl(executor, continuation);
    }

    private <ContinuationResultT> Task<ContinuationResultT> continueWithImpl(Executor executor, Continuation<ResultT, ContinuationResultT> continuation) {
        TaskCompletionSource<ContinuationResultT> source = new TaskCompletionSource<>();
        this.completeListener.addListener((Activity) null, executor, StorageTask$$Lambda$9.lambdaFactory$(this, continuation, source));
        return source.getTask();
    }

    static /* synthetic */ void lambda$continueWithImpl$4(StorageTask storageTask, Continuation continuation, TaskCompletionSource source, Task task) {
        try {
            ContinuationResultT result = continuation.then(storageTask);
            if (!source.getTask().isComplete()) {
                source.setResult(result);
            }
        } catch (RuntimeExecutionException e) {
            if (e.getCause() instanceof Exception) {
                source.setException((Exception) e.getCause());
            } else {
                source.setException(e);
            }
        } catch (Exception e2) {
            source.setException(e2);
        }
    }

    public <ContinuationResultT> Task<ContinuationResultT> continueWithTask(Continuation<ResultT, Task<ContinuationResultT>> continuation) {
        return continueWithTaskImpl((Executor) null, continuation);
    }

    public <ContinuationResultT> Task<ContinuationResultT> continueWithTask(Executor executor, Continuation<ResultT, Task<ContinuationResultT>> continuation) {
        return continueWithTaskImpl(executor, continuation);
    }

    public <ContinuationResultT> Task<ContinuationResultT> onSuccessTask(SuccessContinuation<ResultT, ContinuationResultT> continuation) {
        return successTaskImpl((Executor) null, continuation);
    }

    public <ContinuationResultT> Task<ContinuationResultT> onSuccessTask(Executor executor, SuccessContinuation<ResultT, ContinuationResultT> continuation) {
        return successTaskImpl(executor, continuation);
    }

    private <ContinuationResultT> Task<ContinuationResultT> continueWithTaskImpl(Executor executor, Continuation<ResultT, Task<ContinuationResultT>> continuation) {
        CancellationTokenSource cancellationTokenSource = new CancellationTokenSource();
        TaskCompletionSource<ContinuationResultT> source = new TaskCompletionSource<>(cancellationTokenSource.getToken());
        this.completeListener.addListener((Activity) null, executor, StorageTask$$Lambda$10.lambdaFactory$(this, continuation, source, cancellationTokenSource));
        return source.getTask();
    }

    static /* synthetic */ void lambda$continueWithTaskImpl$5(StorageTask storageTask, Continuation continuation, TaskCompletionSource source, CancellationTokenSource cancellationTokenSource, Task task) {
        try {
            Task<ContinuationResultT> resultTask = (Task) continuation.then(storageTask);
            if (source.getTask().isComplete()) {
                return;
            }
            if (resultTask == null) {
                source.setException(new NullPointerException("Continuation returned null"));
                return;
            }
            source.getClass();
            resultTask.addOnSuccessListener(StorageTask$$Lambda$16.lambdaFactory$(source));
            source.getClass();
            resultTask.addOnFailureListener(StorageTask$$Lambda$17.lambdaFactory$(source));
            cancellationTokenSource.getClass();
            resultTask.addOnCanceledListener(StorageTask$$Lambda$18.lambdaFactory$(cancellationTokenSource));
        } catch (RuntimeExecutionException e) {
            if (e.getCause() instanceof Exception) {
                source.setException((Exception) e.getCause());
            } else {
                source.setException(e);
            }
        } catch (Exception e2) {
            source.setException(e2);
        }
    }

    private <ContinuationResultT> Task<ContinuationResultT> successTaskImpl(Executor executor, SuccessContinuation<ResultT, ContinuationResultT> continuation) {
        CancellationTokenSource cancellationTokenSource = new CancellationTokenSource();
        TaskCompletionSource<ContinuationResultT> source = new TaskCompletionSource<>(cancellationTokenSource.getToken());
        this.successManager.addListener((Activity) null, executor, StorageTask$$Lambda$11.lambdaFactory$(continuation, source, cancellationTokenSource));
        return source.getTask();
    }

    static /* synthetic */ void lambda$successTaskImpl$6(SuccessContinuation continuation, TaskCompletionSource source, CancellationTokenSource cancellationTokenSource, ProvideError result) {
        try {
            Task<ContinuationResultT> resultTask = continuation.then(result);
            source.getClass();
            resultTask.addOnSuccessListener(StorageTask$$Lambda$13.lambdaFactory$(source));
            source.getClass();
            resultTask.addOnFailureListener(StorageTask$$Lambda$14.lambdaFactory$(source));
            cancellationTokenSource.getClass();
            resultTask.addOnCanceledListener(StorageTask$$Lambda$15.lambdaFactory$(cancellationTokenSource));
        } catch (RuntimeExecutionException e) {
            if (e.getCause() instanceof Exception) {
                source.setException((Exception) e.getCause());
            } else {
                source.setException(e);
            }
        } catch (Exception e2) {
            source.setException(e2);
        }
    }

    /* access modifiers changed from: package-private */
    public Runnable getRunnable() {
        return StorageTask$$Lambda$12.lambdaFactory$(this);
    }

    static /* synthetic */ void lambda$getRunnable$7(StorageTask storageTask) {
        try {
            storageTask.run();
        } finally {
            storageTask.ensureFinalState();
        }
    }

    private void ensureFinalState() {
        if (!isComplete() && !isPaused() && getInternalState() != 2 && !tryChangeState(256, false)) {
            tryChangeState(64, false);
        }
    }

    private String getStateString(int[] states) {
        if (states.length == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (int state : states) {
            builder.append(getStateString(state));
            builder.append(", ");
        }
        return builder.substring(0, builder.length() - 2);
    }

    private String getStateString(int state) {
        if (state == 1) {
            return "INTERNAL_STATE_NOT_STARTED";
        }
        if (state == 2) {
            return "INTERNAL_STATE_QUEUED";
        }
        if (state == 4) {
            return "INTERNAL_STATE_IN_PROGRESS";
        }
        if (state == 8) {
            return "INTERNAL_STATE_PAUSING";
        }
        if (state == 16) {
            return "INTERNAL_STATE_PAUSED";
        }
        if (state == 32) {
            return "INTERNAL_STATE_CANCELING";
        }
        if (state == 64) {
            return "INTERNAL_STATE_FAILURE";
        }
        if (state == 128) {
            return "INTERNAL_STATE_SUCCESS";
        }
        if (state != 256) {
            return "Unknown Internal State!";
        }
        return "INTERNAL_STATE_CANCELED";
    }

    /* compiled from: com.google.firebase:firebase-storage@@19.1.1 */
    public class SnapshotBase implements ProvideError {
        private final Exception error;

        public SnapshotBase(Exception error2) {
            if (error2 != null) {
                this.error = error2;
            } else if (StorageTask.this.isCanceled()) {
                this.error = StorageException.fromErrorStatus(Status.RESULT_CANCELED);
            } else if (StorageTask.this.getInternalState() == 64) {
                this.error = StorageException.fromErrorStatus(Status.RESULT_INTERNAL_ERROR);
            } else {
                this.error = null;
            }
        }

        public StorageTask<ResultT> getTask() {
            return StorageTask.this;
        }

        public StorageReference getStorage() {
            return getTask().getStorage();
        }

        public Exception getError() {
            return this.error;
        }
    }
}
