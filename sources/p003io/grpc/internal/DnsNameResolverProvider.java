package p003io.grpc.internal;

/* renamed from: io.grpc.internal.DnsNameResolverProvider */
public final class DnsNameResolverProvider extends BaseDnsNameResolverProvider {
    private static final boolean SRV_ENABLED = Boolean.parseBoolean(System.getProperty(BaseDnsNameResolverProvider.ENABLE_GRPCLB_PROPERTY_NAME, "false"));

    /* access modifiers changed from: protected */
    public boolean isSrvEnabled() {
        return SRV_ENABLED;
    }

    public int priority() {
        return 5;
    }
}
