package p003io.grpc.internal;

import javax.annotation.Nullable;
import p003io.grpc.Attributes;
import p003io.grpc.Decompressor;
import p003io.grpc.Metadata;
import p003io.grpc.Status;

/* renamed from: io.grpc.internal.ServerStream */
public interface ServerStream extends Stream {
    void cancel(Status status);

    void close(Status status, Metadata metadata);

    Attributes getAttributes();

    @Nullable
    String getAuthority();

    void setDecompressor(Decompressor decompressor);

    void setListener(ServerStreamListener serverStreamListener);

    StatsTraceContext statsTraceContext();

    int streamId();

    void writeHeaders(Metadata metadata);
}
