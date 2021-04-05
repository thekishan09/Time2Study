package p003io.grpc.internal;

import java.util.concurrent.ScheduledExecutorService;
import p003io.grpc.InternalChannelz;
import p003io.grpc.InternalInstrumented;
import p003io.grpc.Status;

/* renamed from: io.grpc.internal.ServerTransport */
public interface ServerTransport extends InternalInstrumented<InternalChannelz.SocketStats> {
    ScheduledExecutorService getScheduledExecutorService();

    void shutdown();

    void shutdownNow(Status status);
}
