package p003io.grpc.internal;

import java.net.URI;
import javax.annotation.Nullable;
import p003io.grpc.NameResolver;

/* renamed from: io.grpc.internal.OverrideAuthorityNameResolverFactory */
final class OverrideAuthorityNameResolverFactory extends NameResolver.Factory {
    /* access modifiers changed from: private */
    public final String authorityOverride;
    private final NameResolver.Factory delegate;

    OverrideAuthorityNameResolverFactory(NameResolver.Factory delegate2, String authorityOverride2) {
        this.delegate = delegate2;
        this.authorityOverride = authorityOverride2;
    }

    @Nullable
    public NameResolver newNameResolver(URI targetUri, NameResolver.Args args) {
        NameResolver resolver = this.delegate.newNameResolver(targetUri, args);
        if (resolver == null) {
            return null;
        }
        return new ForwardingNameResolver(resolver) {
            public String getServiceAuthority() {
                return OverrideAuthorityNameResolverFactory.this.authorityOverride;
            }
        };
    }

    public String getDefaultScheme() {
        return this.delegate.getDefaultScheme();
    }
}
