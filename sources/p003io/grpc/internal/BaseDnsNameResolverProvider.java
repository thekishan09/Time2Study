package p003io.grpc.internal;

import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import java.net.URI;
import p003io.grpc.InternalServiceProviders;
import p003io.grpc.NameResolver;
import p003io.grpc.NameResolverProvider;

/* renamed from: io.grpc.internal.BaseDnsNameResolverProvider */
public abstract class BaseDnsNameResolverProvider extends NameResolverProvider {
    public static final String ENABLE_GRPCLB_PROPERTY_NAME = "io.grpc.internal.DnsNameResolverProvider.enable_grpclb";
    private static final String SCHEME = "dns";

    /* access modifiers changed from: protected */
    public abstract boolean isSrvEnabled();

    public DnsNameResolver newNameResolver(URI targetUri, NameResolver.Args args) {
        if (!SCHEME.equals(targetUri.getScheme())) {
            return null;
        }
        String targetPath = (String) Preconditions.checkNotNull(targetUri.getPath(), "targetPath");
        Preconditions.checkArgument(targetPath.startsWith("/"), "the path component (%s) of the target (%s) must start with '/'", (Object) targetPath, (Object) targetUri);
        return new DnsNameResolver(targetUri.getAuthority(), targetPath.substring(1), args, GrpcUtil.SHARED_CHANNEL_EXECUTOR, Stopwatch.createUnstarted(), InternalServiceProviders.isAndroid(getClass().getClassLoader()), isSrvEnabled());
    }

    public String getDefaultScheme() {
        return SCHEME;
    }

    /* access modifiers changed from: protected */
    public boolean isAvailable() {
        return true;
    }
}
