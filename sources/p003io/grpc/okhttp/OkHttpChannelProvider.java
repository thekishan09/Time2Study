package p003io.grpc.okhttp;

import p003io.grpc.InternalServiceProviders;
import p003io.grpc.ManagedChannelProvider;

/* renamed from: io.grpc.okhttp.OkHttpChannelProvider */
public final class OkHttpChannelProvider extends ManagedChannelProvider {
    public boolean isAvailable() {
        return true;
    }

    public int priority() {
        return InternalServiceProviders.isAndroid(getClass().getClassLoader()) ? 8 : 3;
    }

    public OkHttpChannelBuilder builderForAddress(String name, int port) {
        return OkHttpChannelBuilder.forAddress(name, port);
    }

    public OkHttpChannelBuilder builderForTarget(String target) {
        return OkHttpChannelBuilder.forTarget(target);
    }
}
