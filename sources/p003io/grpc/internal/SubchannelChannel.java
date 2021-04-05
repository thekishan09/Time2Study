package p003io.grpc.internal;

import com.google.common.base.Preconditions;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import p003io.grpc.CallOptions;
import p003io.grpc.Channel;
import p003io.grpc.ClientCall;
import p003io.grpc.Context;
import p003io.grpc.LoadBalancer;
import p003io.grpc.Metadata;
import p003io.grpc.MethodDescriptor;
import p003io.grpc.Status;
import p003io.grpc.internal.ClientCallImpl;
import p003io.grpc.internal.ClientStreamListener;

/* renamed from: io.grpc.internal.SubchannelChannel */
final class SubchannelChannel extends Channel {
    static final Status NOT_READY_ERROR = Status.UNAVAILABLE.withDescription("Subchannel is NOT READY");
    static final Status WAIT_FOR_READY_ERROR = Status.UNAVAILABLE.withDescription("wait-for-ready RPC is not supported on Subchannel.asChannel()");
    /* access modifiers changed from: private */
    public static final FailingClientTransport notReadyTransport = new FailingClientTransport(NOT_READY_ERROR, ClientStreamListener.RpcProgress.REFUSED);
    private final CallTracer callsTracer;
    private final ScheduledExecutorService deadlineCancellationExecutor;
    private final Executor executor;
    /* access modifiers changed from: private */
    public final InternalSubchannel subchannel;
    private final ClientCallImpl.ClientTransportProvider transportProvider = new ClientCallImpl.ClientTransportProvider() {
        public ClientTransport get(LoadBalancer.PickSubchannelArgs args) {
            ClientTransport transport = SubchannelChannel.this.subchannel.getTransport();
            if (transport == null) {
                return SubchannelChannel.notReadyTransport;
            }
            return transport;
        }

        public <ReqT> ClientStream newRetriableStream(MethodDescriptor<ReqT, ?> methodDescriptor, CallOptions callOptions, Metadata headers, Context context) {
            throw new UnsupportedOperationException("OobChannel should not create retriable streams");
        }
    };

    SubchannelChannel(InternalSubchannel subchannel2, Executor executor2, ScheduledExecutorService deadlineCancellationExecutor2, CallTracer callsTracer2) {
        this.subchannel = (InternalSubchannel) Preconditions.checkNotNull(subchannel2, "subchannel");
        this.executor = (Executor) Preconditions.checkNotNull(executor2, "executor");
        this.deadlineCancellationExecutor = (ScheduledExecutorService) Preconditions.checkNotNull(deadlineCancellationExecutor2, "deadlineCancellationExecutor");
        this.callsTracer = (CallTracer) Preconditions.checkNotNull(callsTracer2, "callsTracer");
    }

    public <RequestT, ResponseT> ClientCall<RequestT, ResponseT> newCall(MethodDescriptor<RequestT, ResponseT> methodDescriptor, CallOptions callOptions) {
        final Executor effectiveExecutor = callOptions.getExecutor() == null ? this.executor : callOptions.getExecutor();
        if (callOptions.isWaitForReady()) {
            return new ClientCall<RequestT, ResponseT>() {
                public void start(final ClientCall.Listener<ResponseT> listener, Metadata headers) {
                    effectiveExecutor.execute(new Runnable() {
                        public void run() {
                            listener.onClose(SubchannelChannel.WAIT_FOR_READY_ERROR, new Metadata());
                        }
                    });
                }

                public void request(int numMessages) {
                }

                public void cancel(String message, Throwable cause) {
                }

                public void halfClose() {
                }

                public void sendMessage(RequestT requestt) {
                }
            };
        }
        return new ClientCallImpl(methodDescriptor, effectiveExecutor, callOptions.withOption(GrpcUtil.CALL_OPTIONS_RPC_OWNED_BY_BALANCER, Boolean.TRUE), this.transportProvider, this.deadlineCancellationExecutor, this.callsTracer, false);
    }

    public String authority() {
        return this.subchannel.getAuthority();
    }
}
