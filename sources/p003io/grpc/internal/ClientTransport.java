package p003io.grpc.internal;

import java.util.concurrent.Executor;
import p003io.grpc.CallOptions;
import p003io.grpc.InternalChannelz;
import p003io.grpc.InternalInstrumented;
import p003io.grpc.Metadata;
import p003io.grpc.MethodDescriptor;

/* renamed from: io.grpc.internal.ClientTransport */
public interface ClientTransport extends InternalInstrumented<InternalChannelz.SocketStats> {

    /* renamed from: io.grpc.internal.ClientTransport$PingCallback */
    public interface PingCallback {
        void onFailure(Throwable th);

        void onSuccess(long j);
    }

    ClientStream newStream(MethodDescriptor<?, ?> methodDescriptor, Metadata metadata, CallOptions callOptions);

    void ping(PingCallback pingCallback, Executor executor);
}
