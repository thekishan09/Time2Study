package p003io.grpc.internal;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.MoreExecutors;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import p003io.grpc.Attributes;
import p003io.grpc.CallOptions;
import p003io.grpc.ClientCall;
import p003io.grpc.Codec;
import p003io.grpc.Compressor;
import p003io.grpc.CompressorRegistry;
import p003io.grpc.Context;
import p003io.grpc.Contexts;
import p003io.grpc.Deadline;
import p003io.grpc.DecompressorRegistry;
import p003io.grpc.InternalDecompressorRegistry;
import p003io.grpc.LoadBalancer;
import p003io.grpc.Metadata;
import p003io.grpc.MethodDescriptor;
import p003io.grpc.Status;
import p003io.grpc.internal.ClientStreamListener;
import p003io.grpc.internal.StreamListener;
import p003io.perfmark.Link;
import p003io.perfmark.PerfMark;
import p003io.perfmark.Tag;

/* renamed from: io.grpc.internal.ClientCallImpl */
final class ClientCallImpl<ReqT, RespT> extends ClientCall<ReqT, RespT> {
    static final long DEADLINE_EXPIRATION_CANCEL_DELAY_NANOS = TimeUnit.SECONDS.toNanos(1);
    private static final byte[] FULL_STREAM_DECOMPRESSION_ENCODINGS = "gzip".getBytes(Charset.forName("US-ASCII"));
    private static final Logger log = Logger.getLogger(ClientCallImpl.class.getName());
    /* access modifiers changed from: private */
    public final Executor callExecutor;
    private final CallOptions callOptions;
    private boolean cancelCalled;
    /* access modifiers changed from: private */
    public volatile boolean cancelListenersShouldBeRemoved;
    private ClientCallImpl<ReqT, RespT>.ContextCancellationListener cancellationListener;
    /* access modifiers changed from: private */
    public final CallTracer channelCallsTracer;
    private final ClientTransportProvider clientTransportProvider;
    private CompressorRegistry compressorRegistry = CompressorRegistry.getDefaultInstance();
    /* access modifiers changed from: private */
    public final Context context;
    private final ScheduledExecutorService deadlineCancellationExecutor;
    private volatile ScheduledFuture<?> deadlineCancellationNotifyApplicationFuture;
    private volatile ScheduledFuture<?> deadlineCancellationSendToServerFuture;
    private DecompressorRegistry decompressorRegistry = DecompressorRegistry.getDefaultInstance();
    private boolean fullStreamDecompression;
    private boolean halfCloseCalled;
    /* access modifiers changed from: private */
    public final MethodDescriptor<ReqT, RespT> method;
    private boolean observerClosed;
    private final boolean retryEnabled;
    /* access modifiers changed from: private */
    public ClientStream stream;
    /* access modifiers changed from: private */
    public final Tag tag;
    private final boolean unaryRequest;

    /* renamed from: io.grpc.internal.ClientCallImpl$ClientTransportProvider */
    interface ClientTransportProvider {
        ClientTransport get(LoadBalancer.PickSubchannelArgs pickSubchannelArgs);

        <ReqT> ClientStream newRetriableStream(MethodDescriptor<ReqT, ?> methodDescriptor, CallOptions callOptions, Metadata metadata, Context context);
    }

    ClientCallImpl(MethodDescriptor<ReqT, RespT> method2, Executor executor, CallOptions callOptions2, ClientTransportProvider clientTransportProvider2, ScheduledExecutorService deadlineCancellationExecutor2, CallTracer channelCallsTracer2, boolean retryEnabled2) {
        Executor executor2;
        boolean z = false;
        this.observerClosed = false;
        this.method = method2;
        this.tag = PerfMark.createTag(method2.getFullMethodName(), (long) System.identityHashCode(this));
        if (executor == MoreExecutors.directExecutor()) {
            executor2 = new SerializeReentrantCallsDirectExecutor();
        } else {
            executor2 = new SerializingExecutor(executor);
        }
        this.callExecutor = executor2;
        this.channelCallsTracer = channelCallsTracer2;
        this.context = Context.current();
        this.unaryRequest = (method2.getType() == MethodDescriptor.MethodType.UNARY || method2.getType() == MethodDescriptor.MethodType.SERVER_STREAMING) ? true : z;
        this.callOptions = callOptions2;
        this.clientTransportProvider = clientTransportProvider2;
        this.deadlineCancellationExecutor = deadlineCancellationExecutor2;
        this.retryEnabled = retryEnabled2;
        PerfMark.event("ClientCall.<init>", this.tag);
    }

    /* renamed from: io.grpc.internal.ClientCallImpl$ContextCancellationListener */
    private final class ContextCancellationListener implements Context.CancellationListener {
        private ClientCall.Listener<RespT> observer;

        private ContextCancellationListener(ClientCall.Listener<RespT> observer2) {
            this.observer = observer2;
        }

        public void cancelled(Context context) {
            if (context.getDeadline() == null || !context.getDeadline().isExpired()) {
                ClientCallImpl.this.stream.cancel(Contexts.statusFromCancelled(context));
                return;
            }
            ClientCallImpl.this.delayedCancelOnDeadlineExceeded(Contexts.statusFromCancelled(context), this.observer);
        }
    }

    /* access modifiers changed from: package-private */
    public ClientCallImpl<ReqT, RespT> setFullStreamDecompression(boolean fullStreamDecompression2) {
        this.fullStreamDecompression = fullStreamDecompression2;
        return this;
    }

    /* access modifiers changed from: package-private */
    public ClientCallImpl<ReqT, RespT> setDecompressorRegistry(DecompressorRegistry decompressorRegistry2) {
        this.decompressorRegistry = decompressorRegistry2;
        return this;
    }

    /* access modifiers changed from: package-private */
    public ClientCallImpl<ReqT, RespT> setCompressorRegistry(CompressorRegistry compressorRegistry2) {
        this.compressorRegistry = compressorRegistry2;
        return this;
    }

    static void prepareHeaders(Metadata headers, DecompressorRegistry decompressorRegistry2, Compressor compressor, boolean fullStreamDecompression2) {
        headers.discardAll(GrpcUtil.MESSAGE_ENCODING_KEY);
        if (compressor != Codec.Identity.NONE) {
            headers.put(GrpcUtil.MESSAGE_ENCODING_KEY, compressor.getMessageEncoding());
        }
        headers.discardAll(GrpcUtil.MESSAGE_ACCEPT_ENCODING_KEY);
        byte[] advertisedEncodings = InternalDecompressorRegistry.getRawAdvertisedMessageEncodings(decompressorRegistry2);
        if (advertisedEncodings.length != 0) {
            headers.put(GrpcUtil.MESSAGE_ACCEPT_ENCODING_KEY, advertisedEncodings);
        }
        headers.discardAll(GrpcUtil.CONTENT_ENCODING_KEY);
        headers.discardAll(GrpcUtil.CONTENT_ACCEPT_ENCODING_KEY);
        if (fullStreamDecompression2) {
            headers.put(GrpcUtil.CONTENT_ACCEPT_ENCODING_KEY, FULL_STREAM_DECOMPRESSION_ENCODINGS);
        }
    }

    public void start(ClientCall.Listener<RespT> observer, Metadata headers) {
        PerfMark.startTask("ClientCall.start", this.tag);
        try {
            startInternal(observer, headers);
        } finally {
            PerfMark.stopTask("ClientCall.start", this.tag);
        }
    }

    private void startInternal(ClientCall.Listener<RespT> observer, Metadata headers) {
        Compressor compressor;
        boolean deadlineExceeded = false;
        Preconditions.checkState(this.stream == null, "Already started");
        Preconditions.checkState(!this.cancelCalled, "call was cancelled");
        Preconditions.checkNotNull(observer, "observer");
        Preconditions.checkNotNull(headers, "headers");
        if (this.context.isCancelled()) {
            this.stream = NoopClientStream.INSTANCE;
            executeCloseObserverInContext(observer, Contexts.statusFromCancelled(this.context));
            return;
        }
        String compressorName = this.callOptions.getCompressor();
        if (compressorName != null) {
            compressor = this.compressorRegistry.lookupCompressor(compressorName);
            if (compressor == null) {
                this.stream = NoopClientStream.INSTANCE;
                executeCloseObserverInContext(observer, Status.INTERNAL.withDescription(String.format("Unable to find compressor by name %s", new Object[]{compressorName})));
                return;
            }
        } else {
            compressor = Codec.Identity.NONE;
        }
        prepareHeaders(headers, this.decompressorRegistry, compressor, this.fullStreamDecompression);
        Deadline effectiveDeadline = effectiveDeadline();
        if (effectiveDeadline != null && effectiveDeadline.isExpired()) {
            deadlineExceeded = true;
        }
        if (!deadlineExceeded) {
            logIfContextNarrowedTimeout(effectiveDeadline, this.context.getDeadline(), this.callOptions.getDeadline());
            if (this.retryEnabled) {
                this.stream = this.clientTransportProvider.newRetriableStream(this.method, this.callOptions, headers, this.context);
            } else {
                ClientTransport transport = this.clientTransportProvider.get(new PickSubchannelArgsImpl(this.method, headers, this.callOptions));
                Context origContext = this.context.attach();
                try {
                    this.stream = transport.newStream(this.method, headers, this.callOptions);
                } finally {
                    this.context.detach(origContext);
                }
            }
        } else {
            Status status = Status.DEADLINE_EXCEEDED;
            this.stream = new FailingClientStream(status.withDescription("ClientCall started after deadline exceeded: " + effectiveDeadline));
        }
        if (this.callOptions.getAuthority() != null) {
            this.stream.setAuthority(this.callOptions.getAuthority());
        }
        if (this.callOptions.getMaxInboundMessageSize() != null) {
            this.stream.setMaxInboundMessageSize(this.callOptions.getMaxInboundMessageSize().intValue());
        }
        if (this.callOptions.getMaxOutboundMessageSize() != null) {
            this.stream.setMaxOutboundMessageSize(this.callOptions.getMaxOutboundMessageSize().intValue());
        }
        if (effectiveDeadline != null) {
            this.stream.setDeadline(effectiveDeadline);
        }
        this.stream.setCompressor(compressor);
        boolean z = this.fullStreamDecompression;
        if (z) {
            this.stream.setFullStreamDecompression(z);
        }
        this.stream.setDecompressorRegistry(this.decompressorRegistry);
        this.channelCallsTracer.reportCallStarted();
        this.cancellationListener = new ContextCancellationListener(observer);
        this.stream.start(new ClientStreamListenerImpl(observer));
        this.context.addListener(this.cancellationListener, MoreExecutors.directExecutor());
        if (effectiveDeadline != null && !effectiveDeadline.equals(this.context.getDeadline()) && this.deadlineCancellationExecutor != null && !(this.stream instanceof FailingClientStream)) {
            this.deadlineCancellationNotifyApplicationFuture = startDeadlineNotifyApplicationTimer(effectiveDeadline, observer);
        }
        if (this.cancelListenersShouldBeRemoved) {
            removeContextListenerAndCancelDeadlineFuture();
        }
    }

    private static void logIfContextNarrowedTimeout(Deadline effectiveDeadline, @Nullable Deadline outerCallDeadline, @Nullable Deadline callDeadline) {
        if (log.isLoggable(Level.FINE) && effectiveDeadline != null && effectiveDeadline.equals(outerCallDeadline)) {
            StringBuilder builder = new StringBuilder(String.format("Call timeout set to '%d' ns, due to context deadline.", new Object[]{Long.valueOf(Math.max(0, effectiveDeadline.timeRemaining(TimeUnit.NANOSECONDS)))}));
            if (callDeadline == null) {
                builder.append(" Explicit call timeout was not set.");
            } else {
                builder.append(String.format(" Explicit call timeout was '%d' ns.", new Object[]{Long.valueOf(callDeadline.timeRemaining(TimeUnit.NANOSECONDS))}));
            }
            log.fine(builder.toString());
        }
    }

    /* access modifiers changed from: private */
    public void removeContextListenerAndCancelDeadlineFuture() {
        this.context.removeListener(this.cancellationListener);
        ScheduledFuture<?> f = this.deadlineCancellationSendToServerFuture;
        if (f != null) {
            f.cancel(false);
        }
        ScheduledFuture<?> f2 = this.deadlineCancellationNotifyApplicationFuture;
        if (f2 != null) {
            f2.cancel(false);
        }
    }

    private ScheduledFuture<?> startDeadlineNotifyApplicationTimer(Deadline deadline, final ClientCall.Listener<RespT> observer) {
        final long remainingNanos = deadline.timeRemaining(TimeUnit.NANOSECONDS);
        return this.deadlineCancellationExecutor.schedule(new LogExceptionRunnable(new Runnable() {
            public void run() {
                ClientCallImpl.this.delayedCancelOnDeadlineExceeded(ClientCallImpl.this.buildDeadlineExceededStatusWithRemainingNanos(remainingNanos), observer);
            }
        }), remainingNanos, TimeUnit.NANOSECONDS);
    }

    /* access modifiers changed from: private */
    public Status buildDeadlineExceededStatusWithRemainingNanos(long remainingNanos) {
        InsightBuilder insight = new InsightBuilder();
        this.stream.appendTimeoutInsight(insight);
        long seconds = Math.abs(remainingNanos) / TimeUnit.SECONDS.toNanos(1);
        long nanos = Math.abs(remainingNanos) % TimeUnit.SECONDS.toNanos(1);
        StringBuilder buf = new StringBuilder();
        buf.append("deadline exceeded after ");
        if (remainingNanos < 0) {
            buf.append('-');
        }
        buf.append(seconds);
        buf.append(String.format(".%09d", new Object[]{Long.valueOf(nanos)}));
        buf.append("s. ");
        buf.append(insight);
        return Status.DEADLINE_EXCEEDED.augmentDescription(buf.toString());
    }

    /* access modifiers changed from: private */
    public void delayedCancelOnDeadlineExceeded(final Status status, ClientCall.Listener<RespT> observer) {
        if (this.deadlineCancellationSendToServerFuture == null) {
            this.deadlineCancellationSendToServerFuture = this.deadlineCancellationExecutor.schedule(new LogExceptionRunnable(new Runnable() {
                public void run() {
                    ClientCallImpl.this.stream.cancel(status);
                }
            }), DEADLINE_EXPIRATION_CANCEL_DELAY_NANOS, TimeUnit.NANOSECONDS);
            executeCloseObserverInContext(observer, status);
        }
    }

    private void executeCloseObserverInContext(final ClientCall.Listener<RespT> observer, final Status status) {
        this.callExecutor.execute(new ContextRunnable() {
            public void runInContext() {
                ClientCallImpl.this.closeObserver(observer, status, new Metadata());
            }
        });
    }

    /* access modifiers changed from: private */
    public void closeObserver(ClientCall.Listener<RespT> observer, Status status, Metadata trailers) {
        if (!this.observerClosed) {
            this.observerClosed = true;
            observer.onClose(status, trailers);
        }
    }

    /* access modifiers changed from: private */
    @Nullable
    public Deadline effectiveDeadline() {
        return min(this.callOptions.getDeadline(), this.context.getDeadline());
    }

    @Nullable
    private static Deadline min(@Nullable Deadline deadline0, @Nullable Deadline deadline1) {
        if (deadline0 == null) {
            return deadline1;
        }
        if (deadline1 == null) {
            return deadline0;
        }
        return deadline0.minimum(deadline1);
    }

    public void request(int numMessages) {
        PerfMark.startTask("ClientCall.request", this.tag);
        try {
            boolean z = true;
            Preconditions.checkState(this.stream != null, "Not started");
            if (numMessages < 0) {
                z = false;
            }
            Preconditions.checkArgument(z, "Number requested must be non-negative");
            this.stream.request(numMessages);
        } finally {
            PerfMark.stopTask("ClientCall.cancel", this.tag);
        }
    }

    public void cancel(@Nullable String message, @Nullable Throwable cause) {
        PerfMark.startTask("ClientCall.cancel", this.tag);
        try {
            cancelInternal(message, cause);
        } finally {
            PerfMark.stopTask("ClientCall.cancel", this.tag);
        }
    }

    private void cancelInternal(@Nullable String message, @Nullable Throwable cause) {
        Status status;
        if (message == null && cause == null) {
            cause = new CancellationException("Cancelled without a message or cause");
            log.log(Level.WARNING, "Cancelling without a message or cause is suboptimal", cause);
        }
        if (!this.cancelCalled) {
            this.cancelCalled = true;
            try {
                if (this.stream != null) {
                    Status status2 = Status.CANCELLED;
                    if (message != null) {
                        status = status2.withDescription(message);
                    } else {
                        status = status2.withDescription("Call cancelled without message");
                    }
                    if (cause != null) {
                        status = status.withCause(cause);
                    }
                    this.stream.cancel(status);
                }
            } finally {
                removeContextListenerAndCancelDeadlineFuture();
            }
        }
    }

    public void halfClose() {
        PerfMark.startTask("ClientCall.halfClose", this.tag);
        try {
            halfCloseInternal();
        } finally {
            PerfMark.stopTask("ClientCall.halfClose", this.tag);
        }
    }

    private void halfCloseInternal() {
        Preconditions.checkState(this.stream != null, "Not started");
        Preconditions.checkState(!this.cancelCalled, "call was cancelled");
        Preconditions.checkState(!this.halfCloseCalled, "call already half-closed");
        this.halfCloseCalled = true;
        this.stream.halfClose();
    }

    public void sendMessage(ReqT message) {
        PerfMark.startTask("ClientCall.sendMessage", this.tag);
        try {
            sendMessageInternal(message);
        } finally {
            PerfMark.stopTask("ClientCall.sendMessage", this.tag);
        }
    }

    private void sendMessageInternal(ReqT message) {
        Preconditions.checkState(this.stream != null, "Not started");
        Preconditions.checkState(!this.cancelCalled, "call was cancelled");
        Preconditions.checkState(!this.halfCloseCalled, "call was half-closed");
        try {
            if (this.stream instanceof RetriableStream) {
                ((RetriableStream) this.stream).sendMessage(message);
            } else {
                this.stream.writeMessage(this.method.streamRequest(message));
            }
            if (!this.unaryRequest) {
                this.stream.flush();
            }
        } catch (RuntimeException e) {
            this.stream.cancel(Status.CANCELLED.withCause(e).withDescription("Failed to stream message"));
        } catch (Error e2) {
            this.stream.cancel(Status.CANCELLED.withDescription("Client sendMessage() failed with Error"));
            throw e2;
        }
    }

    public void setMessageCompression(boolean enabled) {
        Preconditions.checkState(this.stream != null, "Not started");
        this.stream.setMessageCompression(enabled);
    }

    public boolean isReady() {
        return this.stream.isReady();
    }

    public Attributes getAttributes() {
        ClientStream clientStream = this.stream;
        if (clientStream != null) {
            return clientStream.getAttributes();
        }
        return Attributes.EMPTY;
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object) this).add("method", (Object) this.method).toString();
    }

    /* renamed from: io.grpc.internal.ClientCallImpl$ClientStreamListenerImpl */
    private class ClientStreamListenerImpl implements ClientStreamListener {
        /* access modifiers changed from: private */
        public boolean closed;
        /* access modifiers changed from: private */
        public final ClientCall.Listener<RespT> observer;

        public ClientStreamListenerImpl(ClientCall.Listener<RespT> observer2) {
            this.observer = (ClientCall.Listener) Preconditions.checkNotNull(observer2, "observer");
        }

        public void headersRead(final Metadata headers) {
            PerfMark.startTask("ClientStreamListener.headersRead", ClientCallImpl.this.tag);
            final Link link = PerfMark.linkOut();
            try {
                ClientCallImpl.this.callExecutor.execute(new ContextRunnable() {
                    public void runInContext() {
                        PerfMark.startTask("ClientCall$Listener.headersRead", ClientCallImpl.this.tag);
                        PerfMark.linkIn(link);
                        try {
                            runInternal();
                        } finally {
                            PerfMark.stopTask("ClientCall$Listener.headersRead", ClientCallImpl.this.tag);
                        }
                    }

                    private void runInternal() {
                        if (!ClientStreamListenerImpl.this.closed) {
                            try {
                                ClientStreamListenerImpl.this.observer.onHeaders(headers);
                            } catch (Throwable t) {
                                Status status = Status.CANCELLED.withCause(t).withDescription("Failed to read headers");
                                ClientCallImpl.this.stream.cancel(status);
                                ClientStreamListenerImpl.this.close(status, new Metadata());
                            }
                        }
                    }
                });
            } finally {
                PerfMark.stopTask("ClientStreamListener.headersRead", ClientCallImpl.this.tag);
            }
        }

        public void messagesAvailable(final StreamListener.MessageProducer producer) {
            PerfMark.startTask("ClientStreamListener.messagesAvailable", ClientCallImpl.this.tag);
            final Link link = PerfMark.linkOut();
            try {
                ClientCallImpl.this.callExecutor.execute(new ContextRunnable() {
                    public void runInContext() {
                        PerfMark.startTask("ClientCall$Listener.messagesAvailable", ClientCallImpl.this.tag);
                        PerfMark.linkIn(link);
                        try {
                            runInternal();
                        } finally {
                            PerfMark.stopTask("ClientCall$Listener.messagesAvailable", ClientCallImpl.this.tag);
                        }
                    }

                    private void runInternal() {
                        InputStream message;
                        if (ClientStreamListenerImpl.this.closed) {
                            GrpcUtil.closeQuietly(producer);
                            return;
                        }
                        while (true) {
                            try {
                                InputStream next = producer.next();
                                message = next;
                                if (next != null) {
                                    ClientStreamListenerImpl.this.observer.onMessage(ClientCallImpl.this.method.parseResponse(message));
                                    message.close();
                                } else {
                                    return;
                                }
                            } catch (Throwable t) {
                                GrpcUtil.closeQuietly(producer);
                                Status status = Status.CANCELLED.withCause(t).withDescription("Failed to read message.");
                                ClientCallImpl.this.stream.cancel(status);
                                ClientStreamListenerImpl.this.close(status, new Metadata());
                                return;
                            }
                        }
                    }
                });
            } finally {
                PerfMark.stopTask("ClientStreamListener.messagesAvailable", ClientCallImpl.this.tag);
            }
        }

        /* access modifiers changed from: private */
        public void close(Status status, Metadata trailers) {
            this.closed = true;
            boolean unused = ClientCallImpl.this.cancelListenersShouldBeRemoved = true;
            try {
                ClientCallImpl.this.closeObserver(this.observer, status, trailers);
            } finally {
                ClientCallImpl.this.removeContextListenerAndCancelDeadlineFuture();
                ClientCallImpl.this.channelCallsTracer.reportCallEnded(status.isOk());
            }
        }

        public void closed(Status status, Metadata trailers) {
            closed(status, ClientStreamListener.RpcProgress.PROCESSED, trailers);
        }

        public void closed(Status status, ClientStreamListener.RpcProgress rpcProgress, Metadata trailers) {
            PerfMark.startTask("ClientStreamListener.closed", ClientCallImpl.this.tag);
            try {
                closedInternal(status, rpcProgress, trailers);
            } finally {
                PerfMark.stopTask("ClientStreamListener.closed", ClientCallImpl.this.tag);
            }
        }

        private void closedInternal(Status status, ClientStreamListener.RpcProgress rpcProgress, Metadata trailers) {
            Deadline deadline = ClientCallImpl.this.effectiveDeadline();
            if (status.getCode() == Status.Code.CANCELLED && deadline != null && deadline.isExpired()) {
                InsightBuilder insight = new InsightBuilder();
                ClientCallImpl.this.stream.appendTimeoutInsight(insight);
                Status status2 = Status.DEADLINE_EXCEEDED;
                status = status2.augmentDescription("ClientCall was cancelled at or after deadline. " + insight);
                trailers = new Metadata();
            }
            final Status savedStatus = status;
            final Metadata savedTrailers = trailers;
            final Link link = PerfMark.linkOut();
            ClientCallImpl.this.callExecutor.execute(new ContextRunnable() {
                public void runInContext() {
                    PerfMark.startTask("ClientCall$Listener.onClose", ClientCallImpl.this.tag);
                    PerfMark.linkIn(link);
                    try {
                        runInternal();
                    } finally {
                        PerfMark.stopTask("ClientCall$Listener.onClose", ClientCallImpl.this.tag);
                    }
                }

                private void runInternal() {
                    if (!ClientStreamListenerImpl.this.closed) {
                        ClientStreamListenerImpl.this.close(savedStatus, savedTrailers);
                    }
                }
            });
        }

        public void onReady() {
            if (!ClientCallImpl.this.method.getType().clientSendsOneMessage()) {
                PerfMark.startTask("ClientStreamListener.onReady", ClientCallImpl.this.tag);
                final Link link = PerfMark.linkOut();
                try {
                    ClientCallImpl.this.callExecutor.execute(new ContextRunnable() {
                        public void runInContext() {
                            PerfMark.startTask("ClientCall$Listener.onReady", ClientCallImpl.this.tag);
                            PerfMark.linkIn(link);
                            try {
                                runInternal();
                            } finally {
                                PerfMark.stopTask("ClientCall$Listener.onReady", ClientCallImpl.this.tag);
                            }
                        }

                        private void runInternal() {
                            try {
                                ClientStreamListenerImpl.this.observer.onReady();
                            } catch (Throwable t) {
                                Status status = Status.CANCELLED.withCause(t).withDescription("Failed to call onReady.");
                                ClientCallImpl.this.stream.cancel(status);
                                ClientStreamListenerImpl.this.close(status, new Metadata());
                            }
                        }
                    });
                } finally {
                    PerfMark.stopTask("ClientStreamListener.onReady", ClientCallImpl.this.tag);
                }
            }
        }
    }
}
