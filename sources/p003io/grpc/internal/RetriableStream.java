package p003io.grpc.internal;

import androidx.core.app.NotificationManagerCompat;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.CheckForNull;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import p003io.grpc.Attributes;
import p003io.grpc.ClientStreamTracer;
import p003io.grpc.Compressor;
import p003io.grpc.Deadline;
import p003io.grpc.DecompressorRegistry;
import p003io.grpc.Metadata;
import p003io.grpc.MethodDescriptor;
import p003io.grpc.Status;
import p003io.grpc.internal.ClientStreamListener;
import p003io.grpc.internal.HedgingPolicy;
import p003io.grpc.internal.RetryPolicy;
import p003io.grpc.internal.StreamListener;

/* renamed from: io.grpc.internal.RetriableStream */
abstract class RetriableStream<ReqT> implements ClientStream {
    /* access modifiers changed from: private */
    public static final Status CANCELLED_BECAUSE_COMMITTED = Status.CANCELLED.withDescription("Stream thrown away because RetriableStream committed");
    static final Metadata.Key<String> GRPC_PREVIOUS_RPC_ATTEMPTS = Metadata.Key.m590of("grpc-previous-rpc-attempts", Metadata.ASCII_STRING_MARSHALLER);
    static final Metadata.Key<String> GRPC_RETRY_PUSHBACK_MS = Metadata.Key.m590of("grpc-retry-pushback-ms", Metadata.ASCII_STRING_MARSHALLER);
    /* access modifiers changed from: private */
    public static Random random = new Random();
    /* access modifiers changed from: private */
    public final Executor callExecutor;
    /* access modifiers changed from: private */
    public final long channelBufferLimit;
    /* access modifiers changed from: private */
    public final ChannelBufferMeter channelBufferUsed;
    /* access modifiers changed from: private */
    public final InsightBuilder closedSubstreamsInsight = new InsightBuilder();
    private final Metadata headers;
    /* access modifiers changed from: private */
    public HedgingPolicy hedgingPolicy;
    private final HedgingPolicy.Provider hedgingPolicyProvider;
    /* access modifiers changed from: private */
    public boolean isHedging;
    /* access modifiers changed from: private */
    public final Object lock = new Object();
    /* access modifiers changed from: private */
    public ClientStreamListener masterListener;
    /* access modifiers changed from: private */
    public final MethodDescriptor<ReqT, ?> method;
    /* access modifiers changed from: private */
    public long nextBackoffIntervalNanos;
    /* access modifiers changed from: private */
    public final AtomicBoolean noMoreTransparentRetry = new AtomicBoolean();
    /* access modifiers changed from: private */
    public final long perRpcBufferLimit;
    /* access modifiers changed from: private */
    public long perRpcBufferUsed;
    /* access modifiers changed from: private */
    public RetryPolicy retryPolicy;
    /* access modifiers changed from: private */
    public final RetryPolicy.Provider retryPolicyProvider;
    /* access modifiers changed from: private */
    public final ScheduledExecutorService scheduledExecutorService;
    /* access modifiers changed from: private */
    public FutureCanceller scheduledHedging;
    /* access modifiers changed from: private */
    public FutureCanceller scheduledRetry;
    /* access modifiers changed from: private */
    public volatile State state = new State(new ArrayList(8), Collections.emptyList(), (Collection<Substream>) null, (Substream) null, false, false, false, 0);
    /* access modifiers changed from: private */
    @Nullable
    public final Throttle throttle;

    /* renamed from: io.grpc.internal.RetriableStream$BufferEntry */
    private interface BufferEntry {
        void runWith(Substream substream);
    }

    /* access modifiers changed from: package-private */
    public abstract ClientStream newSubstream(ClientStreamTracer.Factory factory, Metadata metadata);

    /* access modifiers changed from: package-private */
    public abstract void postCommit();

    /* access modifiers changed from: package-private */
    @CheckReturnValue
    @Nullable
    public abstract Status prestart();

    RetriableStream(MethodDescriptor<ReqT, ?> method2, Metadata headers2, ChannelBufferMeter channelBufferUsed2, long perRpcBufferLimit2, long channelBufferLimit2, Executor callExecutor2, ScheduledExecutorService scheduledExecutorService2, RetryPolicy.Provider retryPolicyProvider2, HedgingPolicy.Provider hedgingPolicyProvider2, @Nullable Throttle throttle2) {
        this.method = method2;
        this.channelBufferUsed = channelBufferUsed2;
        this.perRpcBufferLimit = perRpcBufferLimit2;
        this.channelBufferLimit = channelBufferLimit2;
        this.callExecutor = callExecutor2;
        this.scheduledExecutorService = scheduledExecutorService2;
        this.headers = headers2;
        this.retryPolicyProvider = (RetryPolicy.Provider) Preconditions.checkNotNull(retryPolicyProvider2, "retryPolicyProvider");
        this.hedgingPolicyProvider = (HedgingPolicy.Provider) Preconditions.checkNotNull(hedgingPolicyProvider2, "hedgingPolicyProvider");
        this.throttle = throttle2;
    }

    /* access modifiers changed from: private */
    @CheckReturnValue
    @Nullable
    public Runnable commit(Substream winningSubstream) {
        Future<?> retryFuture;
        Future<?> hedgingFuture;
        synchronized (this.lock) {
            if (this.state.winningSubstream != null) {
                return null;
            }
            final Collection<Substream> savedDrainedSubstreams = this.state.drainedSubstreams;
            this.state = this.state.committed(winningSubstream);
            this.channelBufferUsed.addAndGet(-this.perRpcBufferUsed);
            if (this.scheduledRetry != null) {
                retryFuture = this.scheduledRetry.markCancelled();
                this.scheduledRetry = null;
            } else {
                retryFuture = null;
            }
            if (this.scheduledHedging != null) {
                Future<?> hedgingFuture2 = this.scheduledHedging.markCancelled();
                this.scheduledHedging = null;
                hedgingFuture = hedgingFuture2;
            } else {
                hedgingFuture = null;
            }
            final Substream substream = winningSubstream;
            final Future<?> future = retryFuture;
            final Future<?> future2 = hedgingFuture;
            AnonymousClass1CommitTask r3 = new Runnable() {
                public void run() {
                    for (Substream substream : savedDrainedSubstreams) {
                        if (substream != substream) {
                            substream.stream.cancel(RetriableStream.CANCELLED_BECAUSE_COMMITTED);
                        }
                    }
                    Future future = future;
                    if (future != null) {
                        future.cancel(false);
                    }
                    Future future2 = future2;
                    if (future2 != null) {
                        future2.cancel(false);
                    }
                    RetriableStream.this.postCommit();
                }
            };
            return r3;
        }
    }

    /* access modifiers changed from: private */
    public void commitAndRun(Substream winningSubstream) {
        Runnable postCommitTask = commit(winningSubstream);
        if (postCommitTask != null) {
            postCommitTask.run();
        }
    }

    /* access modifiers changed from: private */
    public Substream createSubstream(int previousAttemptCount) {
        Substream sub = new Substream(previousAttemptCount);
        final ClientStreamTracer bufferSizeTracer = new BufferSizeTracer(sub);
        sub.stream = newSubstream(new ClientStreamTracer.Factory() {
            public ClientStreamTracer newClientStreamTracer(ClientStreamTracer.StreamInfo info, Metadata headers) {
                return bufferSizeTracer;
            }
        }, updateHeaders(this.headers, previousAttemptCount));
        return sub;
    }

    /* access modifiers changed from: package-private */
    public final Metadata updateHeaders(Metadata originalHeaders, int previousAttemptCount) {
        Metadata newHeaders = new Metadata();
        newHeaders.merge(originalHeaders);
        if (previousAttemptCount > 0) {
            newHeaders.put(GRPC_PREVIOUS_RPC_ATTEMPTS, String.valueOf(previousAttemptCount));
        }
        return newHeaders;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0059, code lost:
        r3 = r2.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0061, code lost:
        if (r3.hasNext() == false) goto L_0x0004;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0063, code lost:
        r5 = r3.next();
        r4 = r8.state;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x006d, code lost:
        if (r4.winningSubstream == null) goto L_0x0074;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0071, code lost:
        if (r4.winningSubstream == r9) goto L_0x0074;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0076, code lost:
        if (r4.cancelled == false) goto L_0x0085;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x007a, code lost:
        if (r4.winningSubstream != r9) goto L_0x007e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x007c, code lost:
        r3 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x007e, code lost:
        r3 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x007f, code lost:
        com.google.common.base.Preconditions.checkState(r3, "substream should be CANCELLED_BECAUSE_COMMITTED already");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0084, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0085, code lost:
        r5.runWith(r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0004, code lost:
        continue;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void drain(p003io.grpc.internal.RetriableStream.Substream r9) {
        /*
            r8 = this;
            r0 = 0
            r1 = 128(0x80, float:1.794E-43)
            r2 = 0
        L_0x0004:
            java.lang.Object r3 = r8.lock
            monitor-enter(r3)
            io.grpc.internal.RetriableStream$State r4 = r8.state     // Catch:{ all -> 0x008b }
            io.grpc.internal.RetriableStream$Substream r5 = r4.winningSubstream     // Catch:{ all -> 0x008b }
            if (r5 == 0) goto L_0x001a
            io.grpc.internal.RetriableStream$Substream r5 = r4.winningSubstream     // Catch:{ all -> 0x008b }
            if (r5 == r9) goto L_0x001a
            monitor-exit(r3)     // Catch:{ all -> 0x008b }
            io.grpc.internal.ClientStream r3 = r9.stream
            io.grpc.Status r4 = CANCELLED_BECAUSE_COMMITTED
            r3.cancel(r4)
            return
        L_0x001a:
            java.util.List<io.grpc.internal.RetriableStream$BufferEntry> r5 = r4.buffer     // Catch:{ all -> 0x008b }
            int r5 = r5.size()     // Catch:{ all -> 0x008b }
            if (r0 != r5) goto L_0x002a
            io.grpc.internal.RetriableStream$State r5 = r4.substreamDrained(r9)     // Catch:{ all -> 0x008b }
            r8.state = r5     // Catch:{ all -> 0x008b }
            monitor-exit(r3)     // Catch:{ all -> 0x008b }
            return
        L_0x002a:
            boolean r5 = r9.closed     // Catch:{ all -> 0x008b }
            if (r5 == 0) goto L_0x0030
            monitor-exit(r3)     // Catch:{ all -> 0x008b }
            return
        L_0x0030:
            int r5 = r0 + r1
            java.util.List<io.grpc.internal.RetriableStream$BufferEntry> r6 = r4.buffer     // Catch:{ all -> 0x008b }
            int r6 = r6.size()     // Catch:{ all -> 0x008b }
            int r5 = java.lang.Math.min(r5, r6)     // Catch:{ all -> 0x008b }
            if (r2 != 0) goto L_0x004b
            java.util.ArrayList r6 = new java.util.ArrayList     // Catch:{ all -> 0x008b }
            java.util.List<io.grpc.internal.RetriableStream$BufferEntry> r7 = r4.buffer     // Catch:{ all -> 0x008b }
            java.util.List r7 = r7.subList(r0, r5)     // Catch:{ all -> 0x008b }
            r6.<init>(r7)     // Catch:{ all -> 0x008b }
            r2 = r6
            goto L_0x0057
        L_0x004b:
            r2.clear()     // Catch:{ all -> 0x008b }
            java.util.List<io.grpc.internal.RetriableStream$BufferEntry> r6 = r4.buffer     // Catch:{ all -> 0x008b }
            java.util.List r6 = r6.subList(r0, r5)     // Catch:{ all -> 0x008b }
            r2.addAll(r6)     // Catch:{ all -> 0x008b }
        L_0x0057:
            r0 = r5
            monitor-exit(r3)     // Catch:{ all -> 0x008b }
            java.util.Iterator r3 = r2.iterator()
        L_0x005d:
            boolean r5 = r3.hasNext()
            if (r5 == 0) goto L_0x0089
            java.lang.Object r5 = r3.next()
            io.grpc.internal.RetriableStream$BufferEntry r5 = (p003io.grpc.internal.RetriableStream.BufferEntry) r5
            io.grpc.internal.RetriableStream$State r4 = r8.state
            io.grpc.internal.RetriableStream$Substream r6 = r4.winningSubstream
            if (r6 == 0) goto L_0x0074
            io.grpc.internal.RetriableStream$Substream r6 = r4.winningSubstream
            if (r6 == r9) goto L_0x0074
            goto L_0x0089
        L_0x0074:
            boolean r6 = r4.cancelled
            if (r6 == 0) goto L_0x0085
            io.grpc.internal.RetriableStream$Substream r3 = r4.winningSubstream
            if (r3 != r9) goto L_0x007e
            r3 = 1
            goto L_0x007f
        L_0x007e:
            r3 = 0
        L_0x007f:
            java.lang.String r6 = "substream should be CANCELLED_BECAUSE_COMMITTED already"
            com.google.common.base.Preconditions.checkState(r3, r6)
            return
        L_0x0085:
            r5.runWith(r9)
            goto L_0x005d
        L_0x0089:
            goto L_0x0004
        L_0x008b:
            r4 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x008b }
            goto L_0x008f
        L_0x008e:
            throw r4
        L_0x008f:
            goto L_0x008e
        */
        throw new UnsupportedOperationException("Method not decompiled: p003io.grpc.internal.RetriableStream.drain(io.grpc.internal.RetriableStream$Substream):void");
    }

    public final void start(ClientStreamListener listener) {
        this.masterListener = listener;
        Status shutdownStatus = prestart();
        if (shutdownStatus != null) {
            cancel(shutdownStatus);
            return;
        }
        synchronized (this.lock) {
            this.state.buffer.add(new BufferEntry() {
                public void runWith(Substream substream) {
                    substream.stream.start(new Sublistener(substream));
                }
            });
        }
        boolean z = false;
        Substream substream = createSubstream(0);
        if (this.hedgingPolicy == null) {
            z = true;
        }
        Preconditions.checkState(z, "hedgingPolicy has been initialized unexpectedly");
        this.hedgingPolicy = this.hedgingPolicyProvider.get();
        if (!HedgingPolicy.DEFAULT.equals(this.hedgingPolicy)) {
            this.isHedging = true;
            this.retryPolicy = RetryPolicy.DEFAULT;
            FutureCanceller scheduledHedgingRef = null;
            synchronized (this.lock) {
                this.state = this.state.addActiveHedge(substream);
                if (hasPotentialHedging(this.state) && (this.throttle == null || this.throttle.isAboveThreshold())) {
                    FutureCanceller futureCanceller = new FutureCanceller(this.lock);
                    scheduledHedgingRef = futureCanceller;
                    this.scheduledHedging = futureCanceller;
                }
            }
            if (scheduledHedgingRef != null) {
                scheduledHedgingRef.setFuture(this.scheduledExecutorService.schedule(new HedgingRunnable(scheduledHedgingRef), this.hedgingPolicy.hedgingDelayNanos, TimeUnit.NANOSECONDS));
            }
        }
        drain(substream);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0027, code lost:
        if (r1 == null) goto L_0x002d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0029, code lost:
        r1.cancel(false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x002d, code lost:
        r3.setFuture(r7.scheduledExecutorService.schedule(new p003io.grpc.internal.RetriableStream.HedgingRunnable(r7, r3), (long) r8.intValue(), java.util.concurrent.TimeUnit.MILLISECONDS));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0042, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void pushbackHedging(@javax.annotation.Nullable java.lang.Integer r8) {
        /*
            r7 = this;
            if (r8 != 0) goto L_0x0003
            return
        L_0x0003:
            int r0 = r8.intValue()
            if (r0 >= 0) goto L_0x000d
            r7.freezeHedging()
            return
        L_0x000d:
            java.lang.Object r0 = r7.lock
            monitor-enter(r0)
            io.grpc.internal.RetriableStream$FutureCanceller r1 = r7.scheduledHedging     // Catch:{ all -> 0x0043 }
            if (r1 != 0) goto L_0x0016
            monitor-exit(r0)     // Catch:{ all -> 0x0043 }
            return
        L_0x0016:
            io.grpc.internal.RetriableStream$FutureCanceller r1 = r7.scheduledHedging     // Catch:{ all -> 0x0043 }
            java.util.concurrent.Future r1 = r1.markCancelled()     // Catch:{ all -> 0x0043 }
            io.grpc.internal.RetriableStream$FutureCanceller r2 = new io.grpc.internal.RetriableStream$FutureCanceller     // Catch:{ all -> 0x0043 }
            java.lang.Object r3 = r7.lock     // Catch:{ all -> 0x0043 }
            r2.<init>(r3)     // Catch:{ all -> 0x0043 }
            r3 = r2
            r7.scheduledHedging = r2     // Catch:{ all -> 0x0043 }
            monitor-exit(r0)     // Catch:{ all -> 0x0043 }
            if (r1 == 0) goto L_0x002d
            r0 = 0
            r1.cancel(r0)
        L_0x002d:
            java.util.concurrent.ScheduledExecutorService r0 = r7.scheduledExecutorService
            io.grpc.internal.RetriableStream$HedgingRunnable r2 = new io.grpc.internal.RetriableStream$HedgingRunnable
            r2.<init>(r3)
            int r4 = r8.intValue()
            long r4 = (long) r4
            java.util.concurrent.TimeUnit r6 = java.util.concurrent.TimeUnit.MILLISECONDS
            java.util.concurrent.ScheduledFuture r0 = r0.schedule(r2, r4, r6)
            r3.setFuture(r0)
            return
        L_0x0043:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0043 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: p003io.grpc.internal.RetriableStream.pushbackHedging(java.lang.Integer):void");
    }

    /* renamed from: io.grpc.internal.RetriableStream$HedgingRunnable */
    private final class HedgingRunnable implements Runnable {
        final FutureCanceller scheduledHedgingRef;

        HedgingRunnable(FutureCanceller scheduledHedging) {
            this.scheduledHedgingRef = scheduledHedging;
        }

        public void run() {
            RetriableStream.this.callExecutor.execute(new Runnable() {
                public void run() {
                    Substream newSubstream = RetriableStream.this.createSubstream(RetriableStream.this.state.hedgingAttemptCount);
                    boolean cancelled = false;
                    FutureCanceller future = null;
                    synchronized (RetriableStream.this.lock) {
                        if (HedgingRunnable.this.scheduledHedgingRef.isCancelled()) {
                            cancelled = true;
                        } else {
                            State unused = RetriableStream.this.state = RetriableStream.this.state.addActiveHedge(newSubstream);
                            if (!RetriableStream.this.hasPotentialHedging(RetriableStream.this.state) || (RetriableStream.this.throttle != null && !RetriableStream.this.throttle.isAboveThreshold())) {
                                State unused2 = RetriableStream.this.state = RetriableStream.this.state.freezeHedging();
                                FutureCanceller unused3 = RetriableStream.this.scheduledHedging = null;
                            } else {
                                RetriableStream retriableStream = RetriableStream.this;
                                FutureCanceller futureCanceller = new FutureCanceller(RetriableStream.this.lock);
                                future = futureCanceller;
                                FutureCanceller unused4 = retriableStream.scheduledHedging = futureCanceller;
                            }
                        }
                    }
                    if (cancelled) {
                        newSubstream.stream.cancel(Status.CANCELLED.withDescription("Unneeded hedging"));
                        return;
                    }
                    if (future != null) {
                        future.setFuture(RetriableStream.this.scheduledExecutorService.schedule(new HedgingRunnable(future), RetriableStream.this.hedgingPolicy.hedgingDelayNanos, TimeUnit.NANOSECONDS));
                    }
                    RetriableStream.this.drain(newSubstream);
                }
            });
        }
    }

    public final void cancel(Status reason) {
        Substream noopSubstream = new Substream(0);
        noopSubstream.stream = new NoopClientStream();
        Runnable runnable = commit(noopSubstream);
        if (runnable != null) {
            this.masterListener.closed(reason, new Metadata());
            runnable.run();
            return;
        }
        this.state.winningSubstream.stream.cancel(reason);
        synchronized (this.lock) {
            this.state = this.state.cancelled();
        }
    }

    private void delayOrExecute(BufferEntry bufferEntry) {
        Collection<Substream> savedDrainedSubstreams;
        synchronized (this.lock) {
            if (!this.state.passThrough) {
                this.state.buffer.add(bufferEntry);
            }
            savedDrainedSubstreams = this.state.drainedSubstreams;
        }
        for (Substream substream : savedDrainedSubstreams) {
            bufferEntry.runWith(substream);
        }
    }

    public final void writeMessage(InputStream message) {
        throw new IllegalStateException("RetriableStream.writeMessage() should not be called directly");
    }

    /* access modifiers changed from: package-private */
    public final void sendMessage(final ReqT message) {
        State savedState = this.state;
        if (savedState.passThrough) {
            savedState.winningSubstream.stream.writeMessage(this.method.streamRequest(message));
        } else {
            delayOrExecute(new BufferEntry() {
                public void runWith(Substream substream) {
                    substream.stream.writeMessage(RetriableStream.this.method.streamRequest(message));
                }
            });
        }
    }

    public final void request(final int numMessages) {
        State savedState = this.state;
        if (savedState.passThrough) {
            savedState.winningSubstream.stream.request(numMessages);
        } else {
            delayOrExecute(new BufferEntry() {
                public void runWith(Substream substream) {
                    substream.stream.request(numMessages);
                }
            });
        }
    }

    public final void flush() {
        State savedState = this.state;
        if (savedState.passThrough) {
            savedState.winningSubstream.stream.flush();
        } else {
            delayOrExecute(new BufferEntry() {
                public void runWith(Substream substream) {
                    substream.stream.flush();
                }
            });
        }
    }

    public final boolean isReady() {
        for (Substream substream : this.state.drainedSubstreams) {
            if (substream.stream.isReady()) {
                return true;
            }
        }
        return false;
    }

    public final void setCompressor(final Compressor compressor) {
        delayOrExecute(new BufferEntry() {
            public void runWith(Substream substream) {
                substream.stream.setCompressor(compressor);
            }
        });
    }

    public final void setFullStreamDecompression(final boolean fullStreamDecompression) {
        delayOrExecute(new BufferEntry() {
            public void runWith(Substream substream) {
                substream.stream.setFullStreamDecompression(fullStreamDecompression);
            }
        });
    }

    public final void setMessageCompression(final boolean enable) {
        delayOrExecute(new BufferEntry() {
            public void runWith(Substream substream) {
                substream.stream.setMessageCompression(enable);
            }
        });
    }

    public final void halfClose() {
        delayOrExecute(new BufferEntry() {
            public void runWith(Substream substream) {
                substream.stream.halfClose();
            }
        });
    }

    public final void setAuthority(final String authority) {
        delayOrExecute(new BufferEntry() {
            public void runWith(Substream substream) {
                substream.stream.setAuthority(authority);
            }
        });
    }

    public final void setDecompressorRegistry(final DecompressorRegistry decompressorRegistry) {
        delayOrExecute(new BufferEntry() {
            public void runWith(Substream substream) {
                substream.stream.setDecompressorRegistry(decompressorRegistry);
            }
        });
    }

    public final void setMaxInboundMessageSize(final int maxSize) {
        delayOrExecute(new BufferEntry() {
            public void runWith(Substream substream) {
                substream.stream.setMaxInboundMessageSize(maxSize);
            }
        });
    }

    public final void setMaxOutboundMessageSize(final int maxSize) {
        delayOrExecute(new BufferEntry() {
            public void runWith(Substream substream) {
                substream.stream.setMaxOutboundMessageSize(maxSize);
            }
        });
    }

    public final void setDeadline(final Deadline deadline) {
        delayOrExecute(new BufferEntry() {
            public void runWith(Substream substream) {
                substream.stream.setDeadline(deadline);
            }
        });
    }

    public final Attributes getAttributes() {
        if (this.state.winningSubstream != null) {
            return this.state.winningSubstream.stream.getAttributes();
        }
        return Attributes.EMPTY;
    }

    public void appendTimeoutInsight(InsightBuilder insight) {
        State currentState;
        synchronized (this.lock) {
            insight.appendKeyValue("closed", this.closedSubstreamsInsight);
            currentState = this.state;
        }
        if (currentState.winningSubstream != null) {
            InsightBuilder substreamInsight = new InsightBuilder();
            currentState.winningSubstream.stream.appendTimeoutInsight(substreamInsight);
            insight.appendKeyValue("committed", substreamInsight);
            return;
        }
        InsightBuilder openSubstreamsInsight = new InsightBuilder();
        for (Substream sub : currentState.drainedSubstreams) {
            InsightBuilder substreamInsight2 = new InsightBuilder();
            sub.stream.appendTimeoutInsight(substreamInsight2);
            openSubstreamsInsight.append(substreamInsight2);
        }
        insight.appendKeyValue("open", openSubstreamsInsight);
    }

    static void setRandom(Random random2) {
        random = random2;
    }

    /* access modifiers changed from: private */
    public boolean hasPotentialHedging(State state2) {
        return state2.winningSubstream == null && state2.hedgingAttemptCount < this.hedgingPolicy.maxAttempts && !state2.hedgingFrozen;
    }

    /* access modifiers changed from: private */
    public void freezeHedging() {
        Future<?> futureToBeCancelled = null;
        synchronized (this.lock) {
            if (this.scheduledHedging != null) {
                futureToBeCancelled = this.scheduledHedging.markCancelled();
                this.scheduledHedging = null;
            }
            this.state = this.state.freezeHedging();
        }
        if (futureToBeCancelled != null) {
            futureToBeCancelled.cancel(false);
        }
    }

    /* renamed from: io.grpc.internal.RetriableStream$Sublistener */
    private final class Sublistener implements ClientStreamListener {
        final Substream substream;

        Sublistener(Substream substream2) {
            this.substream = substream2;
        }

        public void headersRead(Metadata headers) {
            RetriableStream.this.commitAndRun(this.substream);
            if (RetriableStream.this.state.winningSubstream == this.substream) {
                RetriableStream.this.masterListener.headersRead(headers);
                if (RetriableStream.this.throttle != null) {
                    RetriableStream.this.throttle.onSuccess();
                }
            }
        }

        public void closed(Status status, Metadata trailers) {
            closed(status, ClientStreamListener.RpcProgress.PROCESSED, trailers);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:75:0x01ac, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void closed(p003io.grpc.Status r9, p003io.grpc.internal.ClientStreamListener.RpcProgress r10, p003io.grpc.Metadata r11) {
            /*
                r8 = this;
                io.grpc.internal.RetriableStream r0 = p003io.grpc.internal.RetriableStream.this
                java.lang.Object r0 = r0.lock
                monitor-enter(r0)
                io.grpc.internal.RetriableStream r1 = p003io.grpc.internal.RetriableStream.this     // Catch:{ all -> 0x01cf }
                io.grpc.internal.RetriableStream r2 = p003io.grpc.internal.RetriableStream.this     // Catch:{ all -> 0x01cf }
                io.grpc.internal.RetriableStream$State r2 = r2.state     // Catch:{ all -> 0x01cf }
                io.grpc.internal.RetriableStream$Substream r3 = r8.substream     // Catch:{ all -> 0x01cf }
                io.grpc.internal.RetriableStream$State r2 = r2.substreamClosed(r3)     // Catch:{ all -> 0x01cf }
                p003io.grpc.internal.RetriableStream.State unused = r1.state = r2     // Catch:{ all -> 0x01cf }
                io.grpc.internal.RetriableStream r1 = p003io.grpc.internal.RetriableStream.this     // Catch:{ all -> 0x01cf }
                io.grpc.internal.InsightBuilder r1 = r1.closedSubstreamsInsight     // Catch:{ all -> 0x01cf }
                io.grpc.Status$Code r2 = r9.getCode()     // Catch:{ all -> 0x01cf }
                r1.append(r2)     // Catch:{ all -> 0x01cf }
                monitor-exit(r0)     // Catch:{ all -> 0x01cf }
                io.grpc.internal.RetriableStream$Substream r0 = r8.substream
                boolean r0 = r0.bufferLimitExceeded
                if (r0 == 0) goto L_0x0049
                io.grpc.internal.RetriableStream r0 = p003io.grpc.internal.RetriableStream.this
                io.grpc.internal.RetriableStream$Substream r1 = r8.substream
                r0.commitAndRun(r1)
                io.grpc.internal.RetriableStream r0 = p003io.grpc.internal.RetriableStream.this
                io.grpc.internal.RetriableStream$State r0 = r0.state
                io.grpc.internal.RetriableStream$Substream r0 = r0.winningSubstream
                io.grpc.internal.RetriableStream$Substream r1 = r8.substream
                if (r0 != r1) goto L_0x0048
                io.grpc.internal.RetriableStream r0 = p003io.grpc.internal.RetriableStream.this
                io.grpc.internal.ClientStreamListener r0 = r0.masterListener
                r0.closed(r9, r11)
            L_0x0048:
                return
            L_0x0049:
                io.grpc.internal.RetriableStream r0 = p003io.grpc.internal.RetriableStream.this
                io.grpc.internal.RetriableStream$State r0 = r0.state
                io.grpc.internal.RetriableStream$Substream r0 = r0.winningSubstream
                if (r0 != 0) goto L_0x01b2
                r0 = 0
                io.grpc.internal.ClientStreamListener$RpcProgress r1 = p003io.grpc.internal.ClientStreamListener.RpcProgress.REFUSED
                r2 = 1
                if (r10 != r1) goto L_0x00ee
                io.grpc.internal.RetriableStream r1 = p003io.grpc.internal.RetriableStream.this
                java.util.concurrent.atomic.AtomicBoolean r1 = r1.noMoreTransparentRetry
                r3 = 0
                boolean r1 = r1.compareAndSet(r3, r2)
                if (r1 == 0) goto L_0x00ee
                io.grpc.internal.RetriableStream r1 = p003io.grpc.internal.RetriableStream.this
                io.grpc.internal.RetriableStream$Substream r3 = r8.substream
                int r3 = r3.previousAttemptCount
                io.grpc.internal.RetriableStream$Substream r1 = r1.createSubstream(r3)
                io.grpc.internal.RetriableStream r3 = p003io.grpc.internal.RetriableStream.this
                boolean r3 = r3.isHedging
                if (r3 == 0) goto L_0x00bb
                r3 = 0
                io.grpc.internal.RetriableStream r4 = p003io.grpc.internal.RetriableStream.this
                java.lang.Object r4 = r4.lock
                monitor-enter(r4)
                io.grpc.internal.RetriableStream r5 = p003io.grpc.internal.RetriableStream.this     // Catch:{ all -> 0x00b8 }
                io.grpc.internal.RetriableStream r6 = p003io.grpc.internal.RetriableStream.this     // Catch:{ all -> 0x00b8 }
                io.grpc.internal.RetriableStream$State r6 = r6.state     // Catch:{ all -> 0x00b8 }
                io.grpc.internal.RetriableStream$Substream r7 = r8.substream     // Catch:{ all -> 0x00b8 }
                io.grpc.internal.RetriableStream$State r6 = r6.replaceActiveHedge(r7, r1)     // Catch:{ all -> 0x00b8 }
                p003io.grpc.internal.RetriableStream.State unused = r5.state = r6     // Catch:{ all -> 0x00b8 }
                io.grpc.internal.RetriableStream r5 = p003io.grpc.internal.RetriableStream.this     // Catch:{ all -> 0x00b8 }
                io.grpc.internal.RetriableStream r6 = p003io.grpc.internal.RetriableStream.this     // Catch:{ all -> 0x00b8 }
                io.grpc.internal.RetriableStream$State r6 = r6.state     // Catch:{ all -> 0x00b8 }
                boolean r5 = r5.hasPotentialHedging(r6)     // Catch:{ all -> 0x00b8 }
                if (r5 != 0) goto L_0x00af
                io.grpc.internal.RetriableStream r5 = p003io.grpc.internal.RetriableStream.this     // Catch:{ all -> 0x00b8 }
                io.grpc.internal.RetriableStream$State r5 = r5.state     // Catch:{ all -> 0x00b8 }
                java.util.Collection<io.grpc.internal.RetriableStream$Substream> r5 = r5.activeHedges     // Catch:{ all -> 0x00b8 }
                int r5 = r5.size()     // Catch:{ all -> 0x00b8 }
                if (r5 != r2) goto L_0x00af
                r2 = 1
                r3 = r2
            L_0x00af:
                monitor-exit(r4)     // Catch:{ all -> 0x00b8 }
                if (r3 == 0) goto L_0x00b7
                io.grpc.internal.RetriableStream r2 = p003io.grpc.internal.RetriableStream.this
                r2.commitAndRun(r1)
            L_0x00b7:
                goto L_0x00df
            L_0x00b8:
                r2 = move-exception
                monitor-exit(r4)     // Catch:{ all -> 0x00b8 }
                throw r2
            L_0x00bb:
                io.grpc.internal.RetriableStream r3 = p003io.grpc.internal.RetriableStream.this
                io.grpc.internal.RetryPolicy r3 = r3.retryPolicy
                if (r3 != 0) goto L_0x00d0
                io.grpc.internal.RetriableStream r3 = p003io.grpc.internal.RetriableStream.this
                io.grpc.internal.RetryPolicy$Provider r4 = r3.retryPolicyProvider
                io.grpc.internal.RetryPolicy r4 = r4.get()
                p003io.grpc.internal.RetryPolicy unused = r3.retryPolicy = r4
            L_0x00d0:
                io.grpc.internal.RetriableStream r3 = p003io.grpc.internal.RetriableStream.this
                io.grpc.internal.RetryPolicy r3 = r3.retryPolicy
                int r3 = r3.maxAttempts
                if (r3 != r2) goto L_0x00df
                io.grpc.internal.RetriableStream r2 = p003io.grpc.internal.RetriableStream.this
                r2.commitAndRun(r1)
            L_0x00df:
                io.grpc.internal.RetriableStream r2 = p003io.grpc.internal.RetriableStream.this
                java.util.concurrent.Executor r2 = r2.callExecutor
                io.grpc.internal.RetriableStream$Sublistener$1 r3 = new io.grpc.internal.RetriableStream$Sublistener$1
                r3.<init>(r1)
                r2.execute(r3)
                return
            L_0x00ee:
                io.grpc.internal.ClientStreamListener$RpcProgress r1 = p003io.grpc.internal.ClientStreamListener.RpcProgress.DROPPED
                if (r10 != r1) goto L_0x0100
                io.grpc.internal.RetriableStream r1 = p003io.grpc.internal.RetriableStream.this
                boolean r1 = r1.isHedging
                if (r1 == 0) goto L_0x016d
                io.grpc.internal.RetriableStream r1 = p003io.grpc.internal.RetriableStream.this
                r1.freezeHedging()
                goto L_0x016d
            L_0x0100:
                io.grpc.internal.RetriableStream r1 = p003io.grpc.internal.RetriableStream.this
                java.util.concurrent.atomic.AtomicBoolean r1 = r1.noMoreTransparentRetry
                r1.set(r2)
                io.grpc.internal.RetriableStream r1 = p003io.grpc.internal.RetriableStream.this
                io.grpc.internal.RetryPolicy r1 = r1.retryPolicy
                if (r1 != 0) goto L_0x0129
                io.grpc.internal.RetriableStream r1 = p003io.grpc.internal.RetriableStream.this
                io.grpc.internal.RetryPolicy$Provider r2 = r1.retryPolicyProvider
                io.grpc.internal.RetryPolicy r2 = r2.get()
                p003io.grpc.internal.RetryPolicy unused = r1.retryPolicy = r2
                io.grpc.internal.RetriableStream r1 = p003io.grpc.internal.RetriableStream.this
                io.grpc.internal.RetryPolicy r2 = r1.retryPolicy
                long r2 = r2.initialBackoffNanos
                long unused = r1.nextBackoffIntervalNanos = r2
            L_0x0129:
                io.grpc.internal.RetriableStream$RetryPlan r1 = r8.makeRetryDecision(r9, r11)
                boolean r2 = r1.shouldRetry
                if (r2 == 0) goto L_0x0164
                io.grpc.internal.RetriableStream r2 = p003io.grpc.internal.RetriableStream.this
                java.lang.Object r2 = r2.lock
                monitor-enter(r2)
                io.grpc.internal.RetriableStream r3 = p003io.grpc.internal.RetriableStream.this     // Catch:{ all -> 0x0161 }
                io.grpc.internal.RetriableStream$FutureCanceller r4 = new io.grpc.internal.RetriableStream$FutureCanceller     // Catch:{ all -> 0x0161 }
                io.grpc.internal.RetriableStream r5 = p003io.grpc.internal.RetriableStream.this     // Catch:{ all -> 0x0161 }
                java.lang.Object r5 = r5.lock     // Catch:{ all -> 0x0161 }
                r4.<init>(r5)     // Catch:{ all -> 0x0161 }
                r5 = r4
                p003io.grpc.internal.RetriableStream.FutureCanceller unused = r3.scheduledRetry = r4     // Catch:{ all -> 0x0161 }
                monitor-exit(r2)     // Catch:{ all -> 0x0161 }
                io.grpc.internal.RetriableStream r2 = p003io.grpc.internal.RetriableStream.this
                java.util.concurrent.ScheduledExecutorService r2 = r2.scheduledExecutorService
                io.grpc.internal.RetriableStream$Sublistener$2 r3 = new io.grpc.internal.RetriableStream$Sublistener$2
                r3.<init>()
                long r6 = r1.backoffNanos
                java.util.concurrent.TimeUnit r4 = java.util.concurrent.TimeUnit.NANOSECONDS
                java.util.concurrent.ScheduledFuture r2 = r2.schedule(r3, r6, r4)
                r5.setFuture(r2)
                return
            L_0x0161:
                r3 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x0161 }
                throw r3
            L_0x0164:
                boolean r0 = r1.isFatal
                io.grpc.internal.RetriableStream r2 = p003io.grpc.internal.RetriableStream.this
                java.lang.Integer r3 = r1.hedgingPushbackMillis
                r2.pushbackHedging(r3)
            L_0x016d:
                io.grpc.internal.RetriableStream r1 = p003io.grpc.internal.RetriableStream.this
                boolean r1 = r1.isHedging
                if (r1 == 0) goto L_0x01b2
                io.grpc.internal.RetriableStream r1 = p003io.grpc.internal.RetriableStream.this
                java.lang.Object r1 = r1.lock
                monitor-enter(r1)
                io.grpc.internal.RetriableStream r2 = p003io.grpc.internal.RetriableStream.this     // Catch:{ all -> 0x01af }
                io.grpc.internal.RetriableStream r3 = p003io.grpc.internal.RetriableStream.this     // Catch:{ all -> 0x01af }
                io.grpc.internal.RetriableStream$State r3 = r3.state     // Catch:{ all -> 0x01af }
                io.grpc.internal.RetriableStream$Substream r4 = r8.substream     // Catch:{ all -> 0x01af }
                io.grpc.internal.RetriableStream$State r3 = r3.removeActiveHedge(r4)     // Catch:{ all -> 0x01af }
                p003io.grpc.internal.RetriableStream.State unused = r2.state = r3     // Catch:{ all -> 0x01af }
                if (r0 != 0) goto L_0x01ad
                io.grpc.internal.RetriableStream r2 = p003io.grpc.internal.RetriableStream.this     // Catch:{ all -> 0x01af }
                io.grpc.internal.RetriableStream r3 = p003io.grpc.internal.RetriableStream.this     // Catch:{ all -> 0x01af }
                io.grpc.internal.RetriableStream$State r3 = r3.state     // Catch:{ all -> 0x01af }
                boolean r2 = r2.hasPotentialHedging(r3)     // Catch:{ all -> 0x01af }
                if (r2 != 0) goto L_0x01ab
                io.grpc.internal.RetriableStream r2 = p003io.grpc.internal.RetriableStream.this     // Catch:{ all -> 0x01af }
                io.grpc.internal.RetriableStream$State r2 = r2.state     // Catch:{ all -> 0x01af }
                java.util.Collection<io.grpc.internal.RetriableStream$Substream> r2 = r2.activeHedges     // Catch:{ all -> 0x01af }
                boolean r2 = r2.isEmpty()     // Catch:{ all -> 0x01af }
                if (r2 != 0) goto L_0x01ad
            L_0x01ab:
                monitor-exit(r1)     // Catch:{ all -> 0x01af }
                return
            L_0x01ad:
                monitor-exit(r1)     // Catch:{ all -> 0x01af }
                goto L_0x01b2
            L_0x01af:
                r2 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x01af }
                throw r2
            L_0x01b2:
                io.grpc.internal.RetriableStream r0 = p003io.grpc.internal.RetriableStream.this
                io.grpc.internal.RetriableStream$Substream r1 = r8.substream
                r0.commitAndRun(r1)
                io.grpc.internal.RetriableStream r0 = p003io.grpc.internal.RetriableStream.this
                io.grpc.internal.RetriableStream$State r0 = r0.state
                io.grpc.internal.RetriableStream$Substream r0 = r0.winningSubstream
                io.grpc.internal.RetriableStream$Substream r1 = r8.substream
                if (r0 != r1) goto L_0x01ce
                io.grpc.internal.RetriableStream r0 = p003io.grpc.internal.RetriableStream.this
                io.grpc.internal.ClientStreamListener r0 = r0.masterListener
                r0.closed(r9, r11)
            L_0x01ce:
                return
            L_0x01cf:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x01cf }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: p003io.grpc.internal.RetriableStream.Sublistener.closed(io.grpc.Status, io.grpc.internal.ClientStreamListener$RpcProgress, io.grpc.Metadata):void");
        }

        private RetryPlan makeRetryDecision(Status status, Metadata trailer) {
            boolean shouldRetry = false;
            long backoffNanos = 0;
            boolean isRetryableStatusCode = RetriableStream.this.retryPolicy.retryableStatusCodes.contains(status.getCode());
            boolean isNonFatalStatusCode = RetriableStream.this.hedgingPolicy.nonFatalStatusCodes.contains(status.getCode());
            if (RetriableStream.this.isHedging && !isNonFatalStatusCode) {
                return new RetryPlan(false, true, 0, (Integer) null);
            }
            String pushbackStr = (String) trailer.get(RetriableStream.GRPC_RETRY_PUSHBACK_MS);
            Integer pushbackMillis = null;
            if (pushbackStr != null) {
                try {
                    pushbackMillis = Integer.valueOf(pushbackStr);
                } catch (NumberFormatException e) {
                    NumberFormatException numberFormatException = e;
                    pushbackMillis = -1;
                }
            }
            boolean isThrottled = false;
            if (RetriableStream.this.throttle != null && (isRetryableStatusCode || isNonFatalStatusCode || (pushbackMillis != null && pushbackMillis.intValue() < 0))) {
                isThrottled = !RetriableStream.this.throttle.onQualifiedFailureThenCheckIsAboveThreshold();
            }
            if (RetriableStream.this.retryPolicy.maxAttempts > this.substream.previousAttemptCount + 1 && !isThrottled) {
                if (pushbackMillis == null) {
                    if (isRetryableStatusCode) {
                        shouldRetry = true;
                        double access$2000 = (double) RetriableStream.this.nextBackoffIntervalNanos;
                        double nextDouble = RetriableStream.random.nextDouble();
                        Double.isNaN(access$2000);
                        backoffNanos = (long) (access$2000 * nextDouble);
                        RetriableStream retriableStream = RetriableStream.this;
                        double access$20002 = (double) retriableStream.nextBackoffIntervalNanos;
                        double d = RetriableStream.this.retryPolicy.backoffMultiplier;
                        Double.isNaN(access$20002);
                        long unused = retriableStream.nextBackoffIntervalNanos = Math.min((long) (access$20002 * d), RetriableStream.this.retryPolicy.maxBackoffNanos);
                    }
                } else if (pushbackMillis.intValue() >= 0) {
                    shouldRetry = true;
                    backoffNanos = TimeUnit.MILLISECONDS.toNanos((long) pushbackMillis.intValue());
                    RetriableStream retriableStream2 = RetriableStream.this;
                    long unused2 = retriableStream2.nextBackoffIntervalNanos = retriableStream2.retryPolicy.initialBackoffNanos;
                }
            }
            return new RetryPlan(shouldRetry, false, backoffNanos, RetriableStream.this.isHedging ? pushbackMillis : null);
        }

        public void messagesAvailable(StreamListener.MessageProducer producer) {
            State savedState = RetriableStream.this.state;
            Preconditions.checkState(savedState.winningSubstream != null, "Headers should be received prior to messages.");
            if (savedState.winningSubstream == this.substream) {
                RetriableStream.this.masterListener.messagesAvailable(producer);
            }
        }

        public void onReady() {
            if (RetriableStream.this.state.drainedSubstreams.contains(this.substream)) {
                RetriableStream.this.masterListener.onReady();
            }
        }
    }

    /* renamed from: io.grpc.internal.RetriableStream$State */
    private static final class State {
        final Collection<Substream> activeHedges;
        @Nullable
        final List<BufferEntry> buffer;
        final boolean cancelled;
        final Collection<Substream> drainedSubstreams;
        final int hedgingAttemptCount;
        final boolean hedgingFrozen;
        final boolean passThrough;
        @Nullable
        final Substream winningSubstream;

        State(@Nullable List<BufferEntry> buffer2, Collection<Substream> drainedSubstreams2, Collection<Substream> activeHedges2, @Nullable Substream winningSubstream2, boolean cancelled2, boolean passThrough2, boolean hedgingFrozen2, int hedgingAttemptCount2) {
            this.buffer = buffer2;
            this.drainedSubstreams = (Collection) Preconditions.checkNotNull(drainedSubstreams2, "drainedSubstreams");
            this.winningSubstream = winningSubstream2;
            this.activeHedges = activeHedges2;
            this.cancelled = cancelled2;
            this.passThrough = passThrough2;
            this.hedgingFrozen = hedgingFrozen2;
            this.hedgingAttemptCount = hedgingAttemptCount2;
            boolean z = false;
            Preconditions.checkState(!passThrough2 || buffer2 == null, "passThrough should imply buffer is null");
            Preconditions.checkState(!passThrough2 || winningSubstream2 != null, "passThrough should imply winningSubstream != null");
            Preconditions.checkState(!passThrough2 || (drainedSubstreams2.size() == 1 && drainedSubstreams2.contains(winningSubstream2)) || (drainedSubstreams2.size() == 0 && winningSubstream2.closed), "passThrough should imply winningSubstream is drained");
            Preconditions.checkState((!cancelled2 || winningSubstream2 != null) ? true : z, "cancelled should imply committed");
        }

        /* access modifiers changed from: package-private */
        @CheckReturnValue
        public State cancelled() {
            return new State(this.buffer, this.drainedSubstreams, this.activeHedges, this.winningSubstream, true, this.passThrough, this.hedgingFrozen, this.hedgingAttemptCount);
        }

        /* access modifiers changed from: package-private */
        @CheckReturnValue
        public State substreamDrained(Substream substream) {
            Collection<Substream> drainedSubstreams2;
            List<BufferEntry> buffer2;
            boolean z = true;
            Preconditions.checkState(!this.passThrough, "Already passThrough");
            if (substream.closed) {
                drainedSubstreams2 = this.drainedSubstreams;
            } else if (this.drainedSubstreams.isEmpty()) {
                drainedSubstreams2 = Collections.singletonList(substream);
            } else {
                ArrayList arrayList = new ArrayList(this.drainedSubstreams);
                arrayList.add(substream);
                drainedSubstreams2 = Collections.unmodifiableCollection(arrayList);
            }
            boolean passThrough2 = this.winningSubstream != null;
            List<BufferEntry> buffer3 = this.buffer;
            if (passThrough2) {
                if (this.winningSubstream != substream) {
                    z = false;
                }
                Preconditions.checkState(z, "Another RPC attempt has already committed");
                buffer2 = null;
            } else {
                buffer2 = buffer3;
            }
            return new State(buffer2, drainedSubstreams2, this.activeHedges, this.winningSubstream, this.cancelled, passThrough2, this.hedgingFrozen, this.hedgingAttemptCount);
        }

        /* access modifiers changed from: package-private */
        @CheckReturnValue
        public State substreamClosed(Substream substream) {
            substream.closed = true;
            if (!this.drainedSubstreams.contains(substream)) {
                return this;
            }
            Collection<Substream> drainedSubstreams2 = new ArrayList<>(this.drainedSubstreams);
            drainedSubstreams2.remove(substream);
            return new State(this.buffer, Collections.unmodifiableCollection(drainedSubstreams2), this.activeHedges, this.winningSubstream, this.cancelled, this.passThrough, this.hedgingFrozen, this.hedgingAttemptCount);
        }

        /* access modifiers changed from: package-private */
        @CheckReturnValue
        public State committed(Substream winningSubstream2) {
            Collection<Substream> drainedSubstreams2;
            Preconditions.checkState(this.winningSubstream == null, "Already committed");
            boolean passThrough2 = false;
            List<BufferEntry> buffer2 = this.buffer;
            if (this.drainedSubstreams.contains(winningSubstream2)) {
                passThrough2 = true;
                buffer2 = null;
                drainedSubstreams2 = Collections.singleton(winningSubstream2);
            } else {
                drainedSubstreams2 = Collections.emptyList();
            }
            return new State(buffer2, drainedSubstreams2, this.activeHedges, winningSubstream2, this.cancelled, passThrough2, this.hedgingFrozen, this.hedgingAttemptCount);
        }

        /* access modifiers changed from: package-private */
        @CheckReturnValue
        public State freezeHedging() {
            if (this.hedgingFrozen) {
                return this;
            }
            return new State(this.buffer, this.drainedSubstreams, this.activeHedges, this.winningSubstream, this.cancelled, this.passThrough, true, this.hedgingAttemptCount);
        }

        /* access modifiers changed from: package-private */
        @CheckReturnValue
        public State addActiveHedge(Substream substream) {
            Collection<Substream> activeHedges2;
            Preconditions.checkState(!this.hedgingFrozen, "hedging frozen");
            Preconditions.checkState(this.winningSubstream == null, "already committed");
            if (this.activeHedges == null) {
                activeHedges2 = Collections.singleton(substream);
            } else {
                Collection<Substream> activeHedges3 = new ArrayList<>(this.activeHedges);
                activeHedges3.add(substream);
                activeHedges2 = Collections.unmodifiableCollection(activeHedges3);
            }
            return new State(this.buffer, this.drainedSubstreams, activeHedges2, this.winningSubstream, this.cancelled, this.passThrough, this.hedgingFrozen, 1 + this.hedgingAttemptCount);
        }

        /* access modifiers changed from: package-private */
        @CheckReturnValue
        public State removeActiveHedge(Substream substream) {
            Collection<Substream> activeHedges2 = new ArrayList<>(this.activeHedges);
            activeHedges2.remove(substream);
            return new State(this.buffer, this.drainedSubstreams, Collections.unmodifiableCollection(activeHedges2), this.winningSubstream, this.cancelled, this.passThrough, this.hedgingFrozen, this.hedgingAttemptCount);
        }

        /* access modifiers changed from: package-private */
        @CheckReturnValue
        public State replaceActiveHedge(Substream oldOne, Substream newOne) {
            Collection<Substream> activeHedges2 = new ArrayList<>(this.activeHedges);
            activeHedges2.remove(oldOne);
            activeHedges2.add(newOne);
            return new State(this.buffer, this.drainedSubstreams, Collections.unmodifiableCollection(activeHedges2), this.winningSubstream, this.cancelled, this.passThrough, this.hedgingFrozen, this.hedgingAttemptCount);
        }
    }

    /* renamed from: io.grpc.internal.RetriableStream$Substream */
    private static final class Substream {
        boolean bufferLimitExceeded;
        boolean closed;
        final int previousAttemptCount;
        ClientStream stream;

        Substream(int previousAttemptCount2) {
            this.previousAttemptCount = previousAttemptCount2;
        }
    }

    /* renamed from: io.grpc.internal.RetriableStream$BufferSizeTracer */
    class BufferSizeTracer extends ClientStreamTracer {
        long bufferNeeded;
        private final Substream substream;

        BufferSizeTracer(Substream substream2) {
            this.substream = substream2;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:25:0x007f, code lost:
            if (r0 == null) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x0081, code lost:
            r0.run();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:?, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void outboundWireSize(long r10) {
            /*
                r9 = this;
                io.grpc.internal.RetriableStream r0 = p003io.grpc.internal.RetriableStream.this
                io.grpc.internal.RetriableStream$State r0 = r0.state
                io.grpc.internal.RetriableStream$Substream r0 = r0.winningSubstream
                if (r0 == 0) goto L_0x000b
                return
            L_0x000b:
                r0 = 0
                io.grpc.internal.RetriableStream r1 = p003io.grpc.internal.RetriableStream.this
                java.lang.Object r1 = r1.lock
                monitor-enter(r1)
                io.grpc.internal.RetriableStream r2 = p003io.grpc.internal.RetriableStream.this     // Catch:{ all -> 0x0087 }
                io.grpc.internal.RetriableStream$State r2 = r2.state     // Catch:{ all -> 0x0087 }
                io.grpc.internal.RetriableStream$Substream r2 = r2.winningSubstream     // Catch:{ all -> 0x0087 }
                if (r2 != 0) goto L_0x0085
                io.grpc.internal.RetriableStream$Substream r2 = r9.substream     // Catch:{ all -> 0x0087 }
                boolean r2 = r2.closed     // Catch:{ all -> 0x0087 }
                if (r2 == 0) goto L_0x0024
                goto L_0x0085
            L_0x0024:
                long r2 = r9.bufferNeeded     // Catch:{ all -> 0x0087 }
                long r2 = r2 + r10
                r9.bufferNeeded = r2     // Catch:{ all -> 0x0087 }
                io.grpc.internal.RetriableStream r4 = p003io.grpc.internal.RetriableStream.this     // Catch:{ all -> 0x0087 }
                long r4 = r4.perRpcBufferUsed     // Catch:{ all -> 0x0087 }
                int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r6 > 0) goto L_0x0035
                monitor-exit(r1)     // Catch:{ all -> 0x0087 }
                return
            L_0x0035:
                long r2 = r9.bufferNeeded     // Catch:{ all -> 0x0087 }
                io.grpc.internal.RetriableStream r4 = p003io.grpc.internal.RetriableStream.this     // Catch:{ all -> 0x0087 }
                long r4 = r4.perRpcBufferLimit     // Catch:{ all -> 0x0087 }
                r6 = 1
                int r7 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r7 <= 0) goto L_0x0047
                io.grpc.internal.RetriableStream$Substream r2 = r9.substream     // Catch:{ all -> 0x0087 }
                r2.bufferLimitExceeded = r6     // Catch:{ all -> 0x0087 }
                goto L_0x006f
            L_0x0047:
                io.grpc.internal.RetriableStream r2 = p003io.grpc.internal.RetriableStream.this     // Catch:{ all -> 0x0087 }
                io.grpc.internal.RetriableStream$ChannelBufferMeter r2 = r2.channelBufferUsed     // Catch:{ all -> 0x0087 }
                long r3 = r9.bufferNeeded     // Catch:{ all -> 0x0087 }
                io.grpc.internal.RetriableStream r5 = p003io.grpc.internal.RetriableStream.this     // Catch:{ all -> 0x0087 }
                long r7 = r5.perRpcBufferUsed     // Catch:{ all -> 0x0087 }
                long r3 = r3 - r7
                long r2 = r2.addAndGet(r3)     // Catch:{ all -> 0x0087 }
                io.grpc.internal.RetriableStream r4 = p003io.grpc.internal.RetriableStream.this     // Catch:{ all -> 0x0087 }
                long r7 = r9.bufferNeeded     // Catch:{ all -> 0x0087 }
                long unused = r4.perRpcBufferUsed = r7     // Catch:{ all -> 0x0087 }
                io.grpc.internal.RetriableStream r4 = p003io.grpc.internal.RetriableStream.this     // Catch:{ all -> 0x0087 }
                long r4 = r4.channelBufferLimit     // Catch:{ all -> 0x0087 }
                int r7 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r7 <= 0) goto L_0x006f
                io.grpc.internal.RetriableStream$Substream r4 = r9.substream     // Catch:{ all -> 0x0087 }
                r4.bufferLimitExceeded = r6     // Catch:{ all -> 0x0087 }
            L_0x006f:
                io.grpc.internal.RetriableStream$Substream r2 = r9.substream     // Catch:{ all -> 0x0087 }
                boolean r2 = r2.bufferLimitExceeded     // Catch:{ all -> 0x0087 }
                if (r2 == 0) goto L_0x007e
                io.grpc.internal.RetriableStream r2 = p003io.grpc.internal.RetriableStream.this     // Catch:{ all -> 0x0087 }
                io.grpc.internal.RetriableStream$Substream r3 = r9.substream     // Catch:{ all -> 0x0087 }
                java.lang.Runnable r2 = r2.commit(r3)     // Catch:{ all -> 0x0087 }
                r0 = r2
            L_0x007e:
                monitor-exit(r1)     // Catch:{ all -> 0x0087 }
                if (r0 == 0) goto L_0x0084
                r0.run()
            L_0x0084:
                return
            L_0x0085:
                monitor-exit(r1)     // Catch:{ all -> 0x0087 }
                return
            L_0x0087:
                r2 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x0087 }
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: p003io.grpc.internal.RetriableStream.BufferSizeTracer.outboundWireSize(long):void");
        }
    }

    /* renamed from: io.grpc.internal.RetriableStream$ChannelBufferMeter */
    static final class ChannelBufferMeter {
        private final AtomicLong bufferUsed = new AtomicLong();

        ChannelBufferMeter() {
        }

        /* access modifiers changed from: package-private */
        public long addAndGet(long newBytesUsed) {
            return this.bufferUsed.addAndGet(newBytesUsed);
        }
    }

    /* renamed from: io.grpc.internal.RetriableStream$Throttle */
    static final class Throttle {
        private static final int THREE_DECIMAL_PLACES_SCALE_UP = 1000;
        final int maxTokens;
        final int threshold;
        final AtomicInteger tokenCount;
        final int tokenRatio;

        Throttle(float maxTokens2, float tokenRatio2) {
            AtomicInteger atomicInteger = new AtomicInteger();
            this.tokenCount = atomicInteger;
            this.tokenRatio = (int) (tokenRatio2 * 1000.0f);
            int i = (int) (1000.0f * maxTokens2);
            this.maxTokens = i;
            this.threshold = i / 2;
            atomicInteger.set(i);
        }

        /* access modifiers changed from: package-private */
        public boolean isAboveThreshold() {
            return this.tokenCount.get() > this.threshold;
        }

        /* access modifiers changed from: package-private */
        public boolean onQualifiedFailureThenCheckIsAboveThreshold() {
            int currentCount;
            int decremented;
            do {
                currentCount = this.tokenCount.get();
                if (currentCount == 0) {
                    return false;
                }
                decremented = currentCount + NotificationManagerCompat.IMPORTANCE_UNSPECIFIED;
            } while (!this.tokenCount.compareAndSet(currentCount, Math.max(decremented, 0)));
            if (decremented > this.threshold) {
                return true;
            }
            return false;
        }

        /*  JADX ERROR: StackOverflow in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: 
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
            	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
            */
        void onSuccess() {
            /*
                r4 = this;
            L_0x0000:
                java.util.concurrent.atomic.AtomicInteger r0 = r4.tokenCount
                int r0 = r0.get()
                int r1 = r4.maxTokens
                if (r0 != r1) goto L_0x000b
                goto L_0x001b
            L_0x000b:
                int r2 = r4.tokenRatio
                int r2 = r2 + r0
                java.util.concurrent.atomic.AtomicInteger r3 = r4.tokenCount
                int r1 = java.lang.Math.min(r2, r1)
                boolean r1 = r3.compareAndSet(r0, r1)
                if (r1 == 0) goto L_0x001c
            L_0x001b:
                return
            L_0x001c:
                goto L_0x0000
            */
            throw new UnsupportedOperationException("Method not decompiled: p003io.grpc.internal.RetriableStream.Throttle.onSuccess():void");
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Throttle)) {
                return false;
            }
            Throttle that = (Throttle) o;
            if (this.maxTokens == that.maxTokens && this.tokenRatio == that.tokenRatio) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return Objects.hashCode(Integer.valueOf(this.maxTokens), Integer.valueOf(this.tokenRatio));
        }
    }

    /* renamed from: io.grpc.internal.RetriableStream$RetryPlan */
    private static final class RetryPlan {
        final long backoffNanos;
        @Nullable
        final Integer hedgingPushbackMillis;
        final boolean isFatal;
        final boolean shouldRetry;

        RetryPlan(boolean shouldRetry2, boolean isFatal2, long backoffNanos2, @Nullable Integer hedgingPushbackMillis2) {
            this.shouldRetry = shouldRetry2;
            this.isFatal = isFatal2;
            this.backoffNanos = backoffNanos2;
            this.hedgingPushbackMillis = hedgingPushbackMillis2;
        }
    }

    /* renamed from: io.grpc.internal.RetriableStream$FutureCanceller */
    private static final class FutureCanceller {
        boolean cancelled;
        Future<?> future;
        final Object lock;

        FutureCanceller(Object lock2) {
            this.lock = lock2;
        }

        /* access modifiers changed from: package-private */
        public void setFuture(Future<?> future2) {
            synchronized (this.lock) {
                if (!this.cancelled) {
                    this.future = future2;
                }
            }
        }

        /* access modifiers changed from: package-private */
        @CheckForNull
        public Future<?> markCancelled() {
            this.cancelled = true;
            return this.future;
        }

        /* access modifiers changed from: package-private */
        public boolean isCancelled() {
            return this.cancelled;
        }
    }
}
