package p003io.grpc.internal;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import java.util.concurrent.Executor;
import p003io.grpc.CallOptions;
import p003io.grpc.InternalChannelz;
import p003io.grpc.InternalLogId;
import p003io.grpc.Metadata;
import p003io.grpc.MethodDescriptor;
import p003io.grpc.Status;
import p003io.grpc.internal.ClientStreamListener;
import p003io.grpc.internal.ClientTransport;

/* renamed from: io.grpc.internal.FailingClientTransport */
class FailingClientTransport implements ClientTransport {
    final Status error;
    private final ClientStreamListener.RpcProgress rpcProgress;

    FailingClientTransport(Status error2, ClientStreamListener.RpcProgress rpcProgress2) {
        Preconditions.checkArgument(!error2.isOk(), "error must not be OK");
        this.error = error2;
        this.rpcProgress = rpcProgress2;
    }

    public ClientStream newStream(MethodDescriptor<?, ?> methodDescriptor, Metadata headers, CallOptions callOptions) {
        return new FailingClientStream(this.error, this.rpcProgress);
    }

    public void ping(final ClientTransport.PingCallback callback, Executor executor) {
        executor.execute(new Runnable() {
            public void run() {
                callback.onFailure(FailingClientTransport.this.error.asException());
            }
        });
    }

    public ListenableFuture<InternalChannelz.SocketStats> getStats() {
        SettableFuture<InternalChannelz.SocketStats> ret = SettableFuture.create();
        ret.set(null);
        return ret;
    }

    public InternalLogId getLogId() {
        throw new UnsupportedOperationException("Not a real transport");
    }
}
