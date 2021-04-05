package p003io.grpc;

import p003io.grpc.ServerCall;

/* renamed from: io.grpc.ServerInterceptor */
public interface ServerInterceptor {
    <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler);
}
