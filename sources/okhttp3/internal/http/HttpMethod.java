package okhttp3.internal.http;

import p003io.grpc.internal.GrpcUtil;

public final class HttpMethod {
    public static boolean invalidatesCache(String method) {
        return method.equals(GrpcUtil.HTTP_METHOD) || method.equals("PATCH") || method.equals("PUT") || method.equals("DELETE") || method.equals("MOVE");
    }

    public static boolean requiresRequestBody(String method) {
        return method.equals(GrpcUtil.HTTP_METHOD) || method.equals("PUT") || method.equals("PATCH") || method.equals("PROPPATCH") || method.equals("REPORT");
    }

    public static boolean permitsRequestBody(String method) {
        return !method.equals("GET") && !method.equals("HEAD");
    }

    public static boolean redirectsWithBody(String method) {
        return method.equals("PROPFIND");
    }

    public static boolean redirectsToGet(String method) {
        return !method.equals("PROPFIND");
    }

    private HttpMethod() {
    }
}
