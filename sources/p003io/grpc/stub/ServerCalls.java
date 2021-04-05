package p003io.grpc.stub;

import com.google.common.base.Preconditions;
import p003io.grpc.Metadata;
import p003io.grpc.MethodDescriptor;
import p003io.grpc.ServerCall;
import p003io.grpc.ServerCallHandler;
import p003io.grpc.Status;

/* renamed from: io.grpc.stub.ServerCalls */
public final class ServerCalls {
    static final String MISSING_REQUEST = "Half-closed without a request";
    static final String TOO_MANY_REQUESTS = "Too many requests";

    /* renamed from: io.grpc.stub.ServerCalls$BidiStreamingMethod */
    public interface BidiStreamingMethod<ReqT, RespT> extends StreamingRequestMethod<ReqT, RespT> {
    }

    /* renamed from: io.grpc.stub.ServerCalls$ClientStreamingMethod */
    public interface ClientStreamingMethod<ReqT, RespT> extends StreamingRequestMethod<ReqT, RespT> {
    }

    /* renamed from: io.grpc.stub.ServerCalls$ServerStreamingMethod */
    public interface ServerStreamingMethod<ReqT, RespT> extends UnaryRequestMethod<ReqT, RespT> {
    }

    /* renamed from: io.grpc.stub.ServerCalls$StreamingRequestMethod */
    private interface StreamingRequestMethod<ReqT, RespT> {
        StreamObserver<ReqT> invoke(StreamObserver<RespT> streamObserver);
    }

    /* renamed from: io.grpc.stub.ServerCalls$UnaryMethod */
    public interface UnaryMethod<ReqT, RespT> extends UnaryRequestMethod<ReqT, RespT> {
    }

    /* renamed from: io.grpc.stub.ServerCalls$UnaryRequestMethod */
    private interface UnaryRequestMethod<ReqT, RespT> {
        void invoke(ReqT reqt, StreamObserver<RespT> streamObserver);
    }

    private ServerCalls() {
    }

    public static <ReqT, RespT> ServerCallHandler<ReqT, RespT> asyncUnaryCall(UnaryMethod<ReqT, RespT> method) {
        return asyncUnaryRequestCall(method);
    }

    public static <ReqT, RespT> ServerCallHandler<ReqT, RespT> asyncServerStreamingCall(ServerStreamingMethod<ReqT, RespT> method) {
        return asyncUnaryRequestCall(method);
    }

    public static <ReqT, RespT> ServerCallHandler<ReqT, RespT> asyncClientStreamingCall(ClientStreamingMethod<ReqT, RespT> method) {
        return asyncStreamingRequestCall(method);
    }

    public static <ReqT, RespT> ServerCallHandler<ReqT, RespT> asyncBidiStreamingCall(BidiStreamingMethod<ReqT, RespT> method) {
        return asyncStreamingRequestCall(method);
    }

    /* renamed from: io.grpc.stub.ServerCalls$UnaryServerCallHandler */
    private static final class UnaryServerCallHandler<ReqT, RespT> implements ServerCallHandler<ReqT, RespT> {
        /* access modifiers changed from: private */
        public final UnaryRequestMethod<ReqT, RespT> method;

        UnaryServerCallHandler(UnaryRequestMethod<ReqT, RespT> method2) {
            this.method = method2;
        }

        public ServerCall.Listener<ReqT> startCall(ServerCall<ReqT, RespT> call, Metadata headers) {
            Preconditions.checkArgument(call.getMethodDescriptor().getType().clientSendsOneMessage(), "asyncUnaryRequestCall is only for clientSendsOneMessage methods");
            ServerCallStreamObserverImpl<ReqT, RespT> responseObserver = new ServerCallStreamObserverImpl<>(call);
            call.request(2);
            return new UnaryServerCallListener(responseObserver, call);
        }

        /* renamed from: io.grpc.stub.ServerCalls$UnaryServerCallHandler$UnaryServerCallListener */
        private final class UnaryServerCallListener extends ServerCall.Listener<ReqT> {
            private final ServerCall<ReqT, RespT> call;
            private boolean canInvoke = true;
            private ReqT request;
            private final ServerCallStreamObserverImpl<ReqT, RespT> responseObserver;
            private boolean wasReady;

            UnaryServerCallListener(ServerCallStreamObserverImpl<ReqT, RespT> responseObserver2, ServerCall<ReqT, RespT> call2) {
                this.call = call2;
                this.responseObserver = responseObserver2;
            }

            public void onMessage(ReqT request2) {
                if (this.request != null) {
                    this.call.close(Status.INTERNAL.withDescription(ServerCalls.TOO_MANY_REQUESTS), new Metadata());
                    this.canInvoke = false;
                    return;
                }
                this.request = request2;
            }

            public void onHalfClose() {
                if (this.canInvoke) {
                    if (this.request == null) {
                        this.call.close(Status.INTERNAL.withDescription(ServerCalls.MISSING_REQUEST), new Metadata());
                        return;
                    }
                    UnaryServerCallHandler.this.method.invoke(this.request, this.responseObserver);
                    this.request = null;
                    this.responseObserver.freeze();
                    if (this.wasReady) {
                        onReady();
                    }
                }
            }

            public void onCancel() {
                this.responseObserver.cancelled = true;
                if (this.responseObserver.onCancelHandler != null) {
                    this.responseObserver.onCancelHandler.run();
                }
            }

            public void onReady() {
                this.wasReady = true;
                if (this.responseObserver.onReadyHandler != null) {
                    this.responseObserver.onReadyHandler.run();
                }
            }
        }
    }

    private static <ReqT, RespT> ServerCallHandler<ReqT, RespT> asyncUnaryRequestCall(UnaryRequestMethod<ReqT, RespT> method) {
        return new UnaryServerCallHandler(method);
    }

    /* renamed from: io.grpc.stub.ServerCalls$StreamingServerCallHandler */
    private static final class StreamingServerCallHandler<ReqT, RespT> implements ServerCallHandler<ReqT, RespT> {
        private final StreamingRequestMethod<ReqT, RespT> method;

        StreamingServerCallHandler(StreamingRequestMethod<ReqT, RespT> method2) {
            this.method = method2;
        }

        public ServerCall.Listener<ReqT> startCall(ServerCall<ReqT, RespT> call, Metadata headers) {
            ServerCallStreamObserverImpl<ReqT, RespT> responseObserver = new ServerCallStreamObserverImpl<>(call);
            StreamObserver<ReqT> requestObserver = this.method.invoke(responseObserver);
            responseObserver.freeze();
            if (responseObserver.autoFlowControlEnabled) {
                call.request(1);
            }
            return new StreamingServerCallListener(requestObserver, responseObserver, call);
        }

        /* renamed from: io.grpc.stub.ServerCalls$StreamingServerCallHandler$StreamingServerCallListener */
        private final class StreamingServerCallListener extends ServerCall.Listener<ReqT> {
            private final ServerCall<ReqT, RespT> call;
            private boolean halfClosed = false;
            private final StreamObserver<ReqT> requestObserver;
            private final ServerCallStreamObserverImpl<ReqT, RespT> responseObserver;

            StreamingServerCallListener(StreamObserver<ReqT> requestObserver2, ServerCallStreamObserverImpl<ReqT, RespT> responseObserver2, ServerCall<ReqT, RespT> call2) {
                this.requestObserver = requestObserver2;
                this.responseObserver = responseObserver2;
                this.call = call2;
            }

            public void onMessage(ReqT request) {
                this.requestObserver.onNext(request);
                if (this.responseObserver.autoFlowControlEnabled) {
                    this.call.request(1);
                }
            }

            public void onHalfClose() {
                this.halfClosed = true;
                this.requestObserver.onCompleted();
            }

            public void onCancel() {
                this.responseObserver.cancelled = true;
                if (this.responseObserver.onCancelHandler != null) {
                    this.responseObserver.onCancelHandler.run();
                }
                if (!this.halfClosed) {
                    this.requestObserver.onError(Status.CANCELLED.withDescription("cancelled before receiving half close").asRuntimeException());
                }
            }

            public void onReady() {
                if (this.responseObserver.onReadyHandler != null) {
                    this.responseObserver.onReadyHandler.run();
                }
            }
        }
    }

    private static <ReqT, RespT> ServerCallHandler<ReqT, RespT> asyncStreamingRequestCall(StreamingRequestMethod<ReqT, RespT> method) {
        return new StreamingServerCallHandler(method);
    }

    /* renamed from: io.grpc.stub.ServerCalls$ServerCallStreamObserverImpl */
    private static final class ServerCallStreamObserverImpl<ReqT, RespT> extends ServerCallStreamObserver<RespT> {
        private boolean aborted = false;
        /* access modifiers changed from: private */
        public boolean autoFlowControlEnabled = true;
        final ServerCall<ReqT, RespT> call;
        volatile boolean cancelled;
        private boolean completed = false;
        private boolean frozen;
        /* access modifiers changed from: private */
        public Runnable onCancelHandler;
        /* access modifiers changed from: private */
        public Runnable onReadyHandler;
        private boolean sentHeaders;

        ServerCallStreamObserverImpl(ServerCall<ReqT, RespT> call2) {
            this.call = call2;
        }

        /* access modifiers changed from: private */
        public void freeze() {
            this.frozen = true;
        }

        public void setMessageCompression(boolean enable) {
            this.call.setMessageCompression(enable);
        }

        public void setCompression(String compression) {
            this.call.setCompression(compression);
        }

        public void onNext(RespT response) {
            if (!this.cancelled) {
                Preconditions.checkState(!this.aborted, "Stream was terminated by error, no further calls are allowed");
                Preconditions.checkState(!this.completed, "Stream is already completed, no further calls are allowed");
                if (!this.sentHeaders) {
                    this.call.sendHeaders(new Metadata());
                    this.sentHeaders = true;
                }
                this.call.sendMessage(response);
            } else if (this.onCancelHandler == null) {
                throw Status.CANCELLED.withDescription("call already cancelled").asRuntimeException();
            }
        }

        public void onError(Throwable t) {
            Metadata metadata = Status.trailersFromThrowable(t);
            if (metadata == null) {
                metadata = new Metadata();
            }
            this.call.close(Status.fromThrowable(t), metadata);
            this.aborted = true;
        }

        public void onCompleted() {
            if (!this.cancelled) {
                this.call.close(Status.f3OK, new Metadata());
                this.completed = true;
            } else if (this.onCancelHandler == null) {
                throw Status.CANCELLED.withDescription("call already cancelled").asRuntimeException();
            }
        }

        public boolean isReady() {
            return this.call.isReady();
        }

        public void setOnReadyHandler(Runnable r) {
            Preconditions.checkState(!this.frozen, "Cannot alter onReadyHandler after initialization. May only be called during the initial call to the application, before the service returns its StreamObserver");
            this.onReadyHandler = r;
        }

        public boolean isCancelled() {
            return this.call.isCancelled();
        }

        public void setOnCancelHandler(Runnable onCancelHandler2) {
            Preconditions.checkState(!this.frozen, "Cannot alter onCancelHandler after initialization. May only be called during the initial call to the application, before the service returns its StreamObserver");
            this.onCancelHandler = onCancelHandler2;
        }

        public void disableAutoInboundFlowControl() {
            Preconditions.checkState(!this.frozen, "Cannot disable auto flow control after initialization");
            this.autoFlowControlEnabled = false;
        }

        public void request(int count) {
            this.call.request(count);
        }
    }

    public static void asyncUnimplementedUnaryCall(MethodDescriptor<?, ?> methodDescriptor, StreamObserver<?> responseObserver) {
        Preconditions.checkNotNull(methodDescriptor, "methodDescriptor");
        Preconditions.checkNotNull(responseObserver, "responseObserver");
        responseObserver.onError(Status.UNIMPLEMENTED.withDescription(String.format("Method %s is unimplemented", new Object[]{methodDescriptor.getFullMethodName()})).asRuntimeException());
    }

    public static <T> StreamObserver<T> asyncUnimplementedStreamingCall(MethodDescriptor<?, ?> methodDescriptor, StreamObserver<?> responseObserver) {
        asyncUnimplementedUnaryCall(methodDescriptor, responseObserver);
        return new NoopStreamObserver();
    }

    /* renamed from: io.grpc.stub.ServerCalls$NoopStreamObserver */
    static class NoopStreamObserver<V> implements StreamObserver<V> {
        NoopStreamObserver() {
        }

        public void onNext(V v) {
        }

        public void onError(Throwable t) {
        }

        public void onCompleted() {
        }
    }
}
