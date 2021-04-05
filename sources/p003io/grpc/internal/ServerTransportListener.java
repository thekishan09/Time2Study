package p003io.grpc.internal;

import p003io.grpc.Attributes;
import p003io.grpc.Metadata;

/* renamed from: io.grpc.internal.ServerTransportListener */
public interface ServerTransportListener {
    void streamCreated(ServerStream serverStream, String str, Metadata metadata);

    Attributes transportReady(Attributes attributes);

    void transportTerminated();
}
