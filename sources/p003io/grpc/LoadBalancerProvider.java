package p003io.grpc;

import com.google.common.base.MoreObjects;
import java.util.Map;
import p003io.grpc.LoadBalancer;
import p003io.grpc.NameResolver;

/* renamed from: io.grpc.LoadBalancerProvider */
public abstract class LoadBalancerProvider extends LoadBalancer.Factory {
    private static final NameResolver.ConfigOrError UNKNOWN_CONFIG = NameResolver.ConfigOrError.fromConfig(new UnknownConfig());

    public abstract String getPolicyName();

    public abstract int getPriority();

    public abstract boolean isAvailable();

    public NameResolver.ConfigOrError parseLoadBalancingPolicyConfig(Map<String, ?> map) {
        return UNKNOWN_CONFIG;
    }

    public final String toString() {
        return MoreObjects.toStringHelper((Object) this).add("policy", (Object) getPolicyName()).add("priority", getPriority()).add("available", isAvailable()).toString();
    }

    public final boolean equals(Object other) {
        return this == other;
    }

    public final int hashCode() {
        return super.hashCode();
    }

    /* renamed from: io.grpc.LoadBalancerProvider$UnknownConfig */
    private static final class UnknownConfig {
        UnknownConfig() {
        }

        public String toString() {
            return "service config is unused";
        }
    }
}
