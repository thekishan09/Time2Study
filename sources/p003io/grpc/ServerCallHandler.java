package p003io.grpc;

import p003io.grpc.ServerCall;

/* renamed from: io.grpc.ServerCallHandler */
public interface ServerCallHandler<RequestT, ResponseT> {
    ServerCall.Listener<RequestT> startCall(ServerCall<RequestT, ResponseT> serverCall, Metadata metadata);
}
