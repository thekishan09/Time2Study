package com.google.firebase.firestore.remote;

import com.google.firebase.firestore.remote.Stream;
import com.google.firebase.firestore.remote.Stream.StreamCallback;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.AsyncQueue;
import com.google.firebase.firestore.util.ExponentialBackoff;
import com.google.firebase.firestore.util.Logger;
import com.google.firebase.firestore.util.Util;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import p003io.grpc.ClientCall;
import p003io.grpc.Metadata;
import p003io.grpc.MethodDescriptor;
import p003io.grpc.Status;

abstract class AbstractStream<ReqT, RespT, CallbackT extends Stream.StreamCallback> implements Stream<CallbackT> {
    private static final long BACKOFF_CLIENT_NETWORK_FAILURE_MAX_DELAY_MS = TimeUnit.SECONDS.toMillis(10);
    private static final double BACKOFF_FACTOR = 1.5d;
    private static final long BACKOFF_INITIAL_DELAY_MS = TimeUnit.SECONDS.toMillis(1);
    private static final long BACKOFF_MAX_DELAY_MS = TimeUnit.MINUTES.toMillis(1);
    private static final long IDLE_TIMEOUT_MS = TimeUnit.MINUTES.toMillis(1);
    final ExponentialBackoff backoff;
    private ClientCall<ReqT, RespT> call;
    /* access modifiers changed from: private */
    public long closeCount = 0;
    private final FirestoreChannel firestoreChannel;
    private final AbstractStream<ReqT, RespT, CallbackT>.IdleTimeoutRunnable idleTimeoutRunnable;
    private AsyncQueue.DelayedTask idleTimer;
    private final AsyncQueue.TimerId idleTimerId;
    final CallbackT listener;
    private final MethodDescriptor<ReqT, RespT> methodDescriptor;
    private Stream.State state = Stream.State.Initial;
    /* access modifiers changed from: private */
    public final AsyncQueue workerQueue;

    public abstract void onNext(RespT respt);

    class CloseGuardedRunner {
        private final long initialCloseCount;

        CloseGuardedRunner(long initialCloseCount2) {
            this.initialCloseCount = initialCloseCount2;
        }

        /* access modifiers changed from: package-private */
        public void run(Runnable task) {
            AbstractStream.this.workerQueue.verifyIsCurrentThread();
            if (AbstractStream.this.closeCount == this.initialCloseCount) {
                task.run();
            } else {
                Logger.debug(AbstractStream.this.getClass().getSimpleName(), "stream callback skipped by CloseGuardedRunner.", new Object[0]);
            }
        }
    }

    class StreamObserver implements IncomingStreamObserver<RespT> {
        private final AbstractStream<ReqT, RespT, CallbackT>.CloseGuardedRunner dispatcher;

        StreamObserver(AbstractStream<ReqT, RespT, CallbackT>.CloseGuardedRunner dispatcher2) {
            this.dispatcher = dispatcher2;
        }

        public void onHeaders(Metadata headers) {
            this.dispatcher.run(AbstractStream$StreamObserver$$Lambda$1.lambdaFactory$(this, headers));
        }

        static /* synthetic */ void lambda$onHeaders$0(StreamObserver streamObserver, Metadata headers) {
            if (Logger.isDebugEnabled()) {
                Map<String, String> whitelistedHeaders = new HashMap<>();
                for (String header : headers.keys()) {
                    if (Datastore.WHITE_LISTED_HEADERS.contains(header.toLowerCase(Locale.ENGLISH))) {
                        whitelistedHeaders.put(header, (String) headers.get(Metadata.Key.m590of(header, Metadata.ASCII_STRING_MARSHALLER)));
                    }
                }
                if (!whitelistedHeaders.isEmpty()) {
                    Logger.debug(AbstractStream.this.getClass().getSimpleName(), "(%x) Stream received headers: %s", Integer.valueOf(System.identityHashCode(AbstractStream.this)), whitelistedHeaders);
                }
            }
        }

        public void onNext(RespT response) {
            this.dispatcher.run(AbstractStream$StreamObserver$$Lambda$2.lambdaFactory$(this, response));
        }

        static /* synthetic */ void lambda$onNext$1(StreamObserver streamObserver, Object response) {
            if (Logger.isDebugEnabled()) {
                Logger.debug(AbstractStream.this.getClass().getSimpleName(), "(%x) Stream received: %s", Integer.valueOf(System.identityHashCode(AbstractStream.this)), response);
            }
            AbstractStream.this.onNext(response);
        }

        public void onOpen() {
            this.dispatcher.run(AbstractStream$StreamObserver$$Lambda$3.lambdaFactory$(this));
        }

        static /* synthetic */ void lambda$onOpen$2(StreamObserver streamObserver) {
            Logger.debug(AbstractStream.this.getClass().getSimpleName(), "(%x) Stream is open", Integer.valueOf(System.identityHashCode(AbstractStream.this)));
            AbstractStream.this.onOpen();
        }

        public void onClose(Status status) {
            this.dispatcher.run(AbstractStream$StreamObserver$$Lambda$4.lambdaFactory$(this, status));
        }

        static /* synthetic */ void lambda$onClose$3(StreamObserver streamObserver, Status status) {
            if (status.isOk()) {
                Logger.debug(AbstractStream.this.getClass().getSimpleName(), "(%x) Stream closed.", Integer.valueOf(System.identityHashCode(AbstractStream.this)));
            } else {
                Logger.warn(AbstractStream.this.getClass().getSimpleName(), "(%x) Stream closed with status: %s.", Integer.valueOf(System.identityHashCode(AbstractStream.this)), status);
            }
            AbstractStream.this.handleServerClose(status);
        }
    }

    class IdleTimeoutRunnable implements Runnable {
        IdleTimeoutRunnable() {
        }

        public void run() {
            AbstractStream.this.handleIdleCloseTimer();
        }
    }

    AbstractStream(FirestoreChannel channel, MethodDescriptor<ReqT, RespT> methodDescriptor2, AsyncQueue workerQueue2, AsyncQueue.TimerId connectionTimerId, AsyncQueue.TimerId idleTimerId2, CallbackT listener2) {
        this.firestoreChannel = channel;
        this.methodDescriptor = methodDescriptor2;
        this.workerQueue = workerQueue2;
        this.idleTimerId = idleTimerId2;
        this.listener = listener2;
        this.idleTimeoutRunnable = new IdleTimeoutRunnable();
        this.backoff = new ExponentialBackoff(workerQueue2, connectionTimerId, BACKOFF_INITIAL_DELAY_MS, 1.5d, BACKOFF_MAX_DELAY_MS);
    }

    public boolean isStarted() {
        this.workerQueue.verifyIsCurrentThread();
        return this.state == Stream.State.Starting || this.state == Stream.State.Open || this.state == Stream.State.Backoff;
    }

    public boolean isOpen() {
        this.workerQueue.verifyIsCurrentThread();
        return this.state == Stream.State.Open;
    }

    public void start() {
        this.workerQueue.verifyIsCurrentThread();
        boolean z = true;
        Assert.hardAssert(this.call == null, "Last call still set", new Object[0]);
        Assert.hardAssert(this.idleTimer == null, "Idle timer still set", new Object[0]);
        if (this.state == Stream.State.Error) {
            performBackoff();
            return;
        }
        if (this.state != Stream.State.Initial) {
            z = false;
        }
        Assert.hardAssert(z, "Already started", new Object[0]);
        this.call = this.firestoreChannel.runBidiStreamingRpc(this.methodDescriptor, new StreamObserver(new CloseGuardedRunner(this.closeCount)));
        this.state = Stream.State.Starting;
    }

    private void close(Stream.State finalState, Status status) {
        Assert.hardAssert(isStarted(), "Only started streams should be closed.", new Object[0]);
        Assert.hardAssert(finalState == Stream.State.Error || status.equals(Status.f3OK), "Can't provide an error when not in an error state.", new Object[0]);
        this.workerQueue.verifyIsCurrentThread();
        if (Datastore.isMissingSslCiphers(status)) {
            Util.crashMainThread(new IllegalStateException("The Cloud Firestore client failed to establish a secure connection. This is likely a problem with your app, rather than with Cloud Firestore itself. See https://bit.ly/2XFpdma for instructions on how to enable TLS on Android 4.x devices.", status.getCause()));
        }
        cancelIdleCheck();
        this.backoff.cancel();
        this.closeCount++;
        Status.Code code = status.getCode();
        if (code == Status.Code.OK) {
            this.backoff.reset();
        } else if (code == Status.Code.RESOURCE_EXHAUSTED) {
            Logger.debug(getClass().getSimpleName(), "(%x) Using maximum backoff delay to prevent overloading the backend.", Integer.valueOf(System.identityHashCode(this)));
            this.backoff.resetToMax();
        } else if (code == Status.Code.UNAUTHENTICATED) {
            this.firestoreChannel.invalidateToken();
        } else if (code == Status.Code.UNAVAILABLE && ((status.getCause() instanceof UnknownHostException) || (status.getCause() instanceof ConnectException))) {
            this.backoff.setTemporaryMaxDelay(BACKOFF_CLIENT_NETWORK_FAILURE_MAX_DELAY_MS);
        }
        if (finalState != Stream.State.Error) {
            Logger.debug(getClass().getSimpleName(), "(%x) Performing stream teardown", Integer.valueOf(System.identityHashCode(this)));
            tearDown();
        }
        if (this.call != null) {
            if (status.isOk()) {
                Logger.debug(getClass().getSimpleName(), "(%x) Closing stream client-side", Integer.valueOf(System.identityHashCode(this)));
                this.call.halfClose();
            }
            this.call = null;
        }
        this.state = finalState;
        this.listener.onClose(status);
    }

    /* access modifiers changed from: protected */
    public void tearDown() {
    }

    public void stop() {
        if (isStarted()) {
            close(Stream.State.Initial, Status.f3OK);
        }
    }

    public void inhibitBackoff() {
        Assert.hardAssert(!isStarted(), "Can only inhibit backoff after in a stopped state", new Object[0]);
        this.workerQueue.verifyIsCurrentThread();
        this.state = Stream.State.Initial;
        this.backoff.reset();
    }

    /* access modifiers changed from: protected */
    public void writeRequest(ReqT message) {
        this.workerQueue.verifyIsCurrentThread();
        Logger.debug(getClass().getSimpleName(), "(%x) Stream sending: %s", Integer.valueOf(System.identityHashCode(this)), message);
        cancelIdleCheck();
        this.call.sendMessage(message);
    }

    /* access modifiers changed from: private */
    public void handleIdleCloseTimer() {
        if (isOpen()) {
            close(Stream.State.Initial, Status.f3OK);
        }
    }

    /* access modifiers changed from: package-private */
    public void handleServerClose(Status status) {
        Assert.hardAssert(isStarted(), "Can't handle server close on non-started stream!", new Object[0]);
        close(Stream.State.Error, status);
    }

    /* access modifiers changed from: private */
    public void onOpen() {
        this.state = Stream.State.Open;
        this.listener.onOpen();
    }

    private void performBackoff() {
        Assert.hardAssert(this.state == Stream.State.Error, "Should only perform backoff in an error state", new Object[0]);
        this.state = Stream.State.Backoff;
        this.backoff.backoffAndRun(AbstractStream$$Lambda$1.lambdaFactory$(this));
    }

    static /* synthetic */ void lambda$performBackoff$0(AbstractStream abstractStream) {
        Assert.hardAssert(abstractStream.state == Stream.State.Backoff, "State should still be backoff but was %s", abstractStream.state);
        abstractStream.state = Stream.State.Initial;
        abstractStream.start();
        Assert.hardAssert(abstractStream.isStarted(), "Stream should have started", new Object[0]);
    }

    /* access modifiers changed from: package-private */
    public void markIdle() {
        if (isOpen() && this.idleTimer == null) {
            this.idleTimer = this.workerQueue.enqueueAfterDelay(this.idleTimerId, IDLE_TIMEOUT_MS, this.idleTimeoutRunnable);
        }
    }

    private void cancelIdleCheck() {
        AsyncQueue.DelayedTask delayedTask = this.idleTimer;
        if (delayedTask != null) {
            delayedTask.cancel();
            this.idleTimer = null;
        }
    }
}
