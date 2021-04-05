package p003io.grpc.internal;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import p003io.grpc.MethodDescriptor;
import p003io.grpc.internal.RetriableStream;

/* renamed from: io.grpc.internal.ManagedChannelServiceConfig */
final class ManagedChannelServiceConfig {
    @Nullable
    private final Object loadBalancingConfig;
    @Nullable
    private final RetriableStream.Throttle retryThrottling;
    private final Map<String, MethodInfo> serviceMap;
    private final Map<String, MethodInfo> serviceMethodMap;

    ManagedChannelServiceConfig(Map<String, MethodInfo> serviceMethodMap2, Map<String, MethodInfo> serviceMap2, @Nullable RetriableStream.Throttle retryThrottling2, @Nullable Object loadBalancingConfig2) {
        this.serviceMethodMap = Collections.unmodifiableMap(new HashMap(serviceMethodMap2));
        this.serviceMap = Collections.unmodifiableMap(new HashMap(serviceMap2));
        this.retryThrottling = retryThrottling2;
        this.loadBalancingConfig = loadBalancingConfig2;
    }

    static ManagedChannelServiceConfig empty() {
        return new ManagedChannelServiceConfig(new HashMap(), new HashMap(), (RetriableStream.Throttle) null, (Object) null);
    }

    static ManagedChannelServiceConfig fromServiceConfig(Map<String, ?> serviceConfig, boolean retryEnabled, int maxRetryAttemptsLimit, int maxHedgedAttemptsLimit, @Nullable Object loadBalancingConfig2) {
        List<Map<String, ?>> methodConfigs;
        boolean z = retryEnabled;
        Object obj = loadBalancingConfig2;
        RetriableStream.Throttle retryThrottling2 = null;
        if (z) {
            retryThrottling2 = ServiceConfigUtil.getThrottlePolicy(serviceConfig);
        }
        Map<String, MethodInfo> serviceMethodMap2 = new HashMap<>();
        Map<String, MethodInfo> serviceMap2 = new HashMap<>();
        List<Map<String, ?>> methodConfigs2 = ServiceConfigUtil.getMethodConfigFromServiceConfig(serviceConfig);
        if (methodConfigs2 == null) {
            return new ManagedChannelServiceConfig(serviceMethodMap2, serviceMap2, retryThrottling2, obj);
        }
        for (Map<String, ?> methodConfig : methodConfigs2) {
            MethodInfo info = new MethodInfo(methodConfig, z, maxRetryAttemptsLimit, maxHedgedAttemptsLimit);
            List<Map<String, ?>> nameList = ServiceConfigUtil.getNameListFromMethodConfig(methodConfig);
            Preconditions.checkArgument(nameList != null && !nameList.isEmpty(), "no names in method config %s", (Object) methodConfig);
            for (Map<String, ?> name : nameList) {
                String serviceName = ServiceConfigUtil.getServiceFromName(name);
                Preconditions.checkArgument(!Strings.isNullOrEmpty(serviceName), "missing service name");
                String methodName = ServiceConfigUtil.getMethodFromName(name);
                if (Strings.isNullOrEmpty(methodName)) {
                    methodConfigs = methodConfigs2;
                    Preconditions.checkArgument(!serviceMap2.containsKey(serviceName), "Duplicate service %s", (Object) serviceName);
                    serviceMap2.put(serviceName, info);
                } else {
                    methodConfigs = methodConfigs2;
                    String fullMethodName = MethodDescriptor.generateFullMethodName(serviceName, methodName);
                    String str = methodName;
                    Preconditions.checkArgument(!serviceMethodMap2.containsKey(fullMethodName), "Duplicate method name %s", (Object) fullMethodName);
                    serviceMethodMap2.put(fullMethodName, info);
                }
                boolean z2 = retryEnabled;
                methodConfigs2 = methodConfigs;
            }
            z = retryEnabled;
        }
        return new ManagedChannelServiceConfig(serviceMethodMap2, serviceMap2, retryThrottling2, obj);
    }

    /* access modifiers changed from: package-private */
    public Map<String, MethodInfo> getServiceMap() {
        return this.serviceMap;
    }

    /* access modifiers changed from: package-private */
    public Map<String, MethodInfo> getServiceMethodMap() {
        return this.serviceMethodMap;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public Object getLoadBalancingConfig() {
        return this.loadBalancingConfig;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public RetriableStream.Throttle getRetryThrottling() {
        return this.retryThrottling;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ManagedChannelServiceConfig that = (ManagedChannelServiceConfig) o;
        if (!Objects.equal(this.serviceMethodMap, that.serviceMethodMap) || !Objects.equal(this.serviceMap, that.serviceMap) || !Objects.equal(this.retryThrottling, that.retryThrottling) || !Objects.equal(this.loadBalancingConfig, that.loadBalancingConfig)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hashCode(this.serviceMethodMap, this.serviceMap, this.retryThrottling, this.loadBalancingConfig);
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object) this).add("serviceMethodMap", (Object) this.serviceMethodMap).add("serviceMap", (Object) this.serviceMap).add("retryThrottling", (Object) this.retryThrottling).add("loadBalancingConfig", this.loadBalancingConfig).toString();
    }

    /* renamed from: io.grpc.internal.ManagedChannelServiceConfig$MethodInfo */
    static final class MethodInfo {
        final HedgingPolicy hedgingPolicy;
        final Integer maxInboundMessageSize;
        final Integer maxOutboundMessageSize;
        final RetryPolicy retryPolicy;
        final Long timeoutNanos;
        final Boolean waitForReady;

        MethodInfo(Map<String, ?> methodConfig, boolean retryEnabled, int maxRetryAttemptsLimit, int maxHedgedAttemptsLimit) {
            this.timeoutNanos = ServiceConfigUtil.getTimeoutFromMethodConfig(methodConfig);
            this.waitForReady = ServiceConfigUtil.getWaitForReadyFromMethodConfig(methodConfig);
            Integer maxResponseMessageBytesFromMethodConfig = ServiceConfigUtil.getMaxResponseMessageBytesFromMethodConfig(methodConfig);
            this.maxInboundMessageSize = maxResponseMessageBytesFromMethodConfig;
            boolean z = true;
            if (maxResponseMessageBytesFromMethodConfig != null) {
                Preconditions.checkArgument(maxResponseMessageBytesFromMethodConfig.intValue() >= 0, "maxInboundMessageSize %s exceeds bounds", (Object) this.maxInboundMessageSize);
            }
            Integer maxRequestMessageBytesFromMethodConfig = ServiceConfigUtil.getMaxRequestMessageBytesFromMethodConfig(methodConfig);
            this.maxOutboundMessageSize = maxRequestMessageBytesFromMethodConfig;
            if (maxRequestMessageBytesFromMethodConfig != null) {
                Preconditions.checkArgument(maxRequestMessageBytesFromMethodConfig.intValue() < 0 ? false : z, "maxOutboundMessageSize %s exceeds bounds", (Object) this.maxOutboundMessageSize);
            }
            Map<String, ?> hedgingPolicyMap = null;
            Map<String, ?> retryPolicyMap = retryEnabled ? ServiceConfigUtil.getRetryPolicyFromMethodConfig(methodConfig) : null;
            this.retryPolicy = retryPolicyMap == null ? RetryPolicy.DEFAULT : retryPolicy(retryPolicyMap, maxRetryAttemptsLimit);
            hedgingPolicyMap = retryEnabled ? ServiceConfigUtil.getHedgingPolicyFromMethodConfig(methodConfig) : hedgingPolicyMap;
            this.hedgingPolicy = hedgingPolicyMap == null ? HedgingPolicy.DEFAULT : hedgingPolicy(hedgingPolicyMap, maxHedgedAttemptsLimit);
        }

        public int hashCode() {
            return Objects.hashCode(this.timeoutNanos, this.waitForReady, this.maxInboundMessageSize, this.maxOutboundMessageSize, this.retryPolicy, this.hedgingPolicy);
        }

        public boolean equals(Object other) {
            if (!(other instanceof MethodInfo)) {
                return false;
            }
            MethodInfo that = (MethodInfo) other;
            if (!Objects.equal(this.timeoutNanos, that.timeoutNanos) || !Objects.equal(this.waitForReady, that.waitForReady) || !Objects.equal(this.maxInboundMessageSize, that.maxInboundMessageSize) || !Objects.equal(this.maxOutboundMessageSize, that.maxOutboundMessageSize) || !Objects.equal(this.retryPolicy, that.retryPolicy) || !Objects.equal(this.hedgingPolicy, that.hedgingPolicy)) {
                return false;
            }
            return true;
        }

        public String toString() {
            return MoreObjects.toStringHelper((Object) this).add("timeoutNanos", (Object) this.timeoutNanos).add("waitForReady", (Object) this.waitForReady).add("maxInboundMessageSize", (Object) this.maxInboundMessageSize).add("maxOutboundMessageSize", (Object) this.maxOutboundMessageSize).add("retryPolicy", (Object) this.retryPolicy).add("hedgingPolicy", (Object) this.hedgingPolicy).toString();
        }

        private static RetryPolicy retryPolicy(Map<String, ?> retryPolicy2, int maxAttemptsLimit) {
            int maxAttempts = ((Integer) Preconditions.checkNotNull(ServiceConfigUtil.getMaxAttemptsFromRetryPolicy(retryPolicy2), "maxAttempts cannot be empty")).intValue();
            boolean z = true;
            Preconditions.checkArgument(maxAttempts >= 2, "maxAttempts must be greater than 1: %s", maxAttempts);
            int maxAttempts2 = Math.min(maxAttempts, maxAttemptsLimit);
            long initialBackoffNanos = ((Long) Preconditions.checkNotNull(ServiceConfigUtil.getInitialBackoffNanosFromRetryPolicy(retryPolicy2), "initialBackoff cannot be empty")).longValue();
            Preconditions.checkArgument(initialBackoffNanos > 0, "initialBackoffNanos must be greater than 0: %s", initialBackoffNanos);
            long maxBackoffNanos = ((Long) Preconditions.checkNotNull(ServiceConfigUtil.getMaxBackoffNanosFromRetryPolicy(retryPolicy2), "maxBackoff cannot be empty")).longValue();
            Preconditions.checkArgument(maxBackoffNanos > 0, "maxBackoff must be greater than 0: %s", maxBackoffNanos);
            double backoffMultiplier = ((Double) Preconditions.checkNotNull(ServiceConfigUtil.getBackoffMultiplierFromRetryPolicy(retryPolicy2), "backoffMultiplier cannot be empty")).doubleValue();
            if (backoffMultiplier <= 0.0d) {
                z = false;
            }
            Preconditions.checkArgument(z, "backoffMultiplier must be greater than 0: %s", (Object) Double.valueOf(backoffMultiplier));
            long j = maxBackoffNanos;
            return new RetryPolicy(maxAttempts2, initialBackoffNanos, maxBackoffNanos, backoffMultiplier, ServiceConfigUtil.getRetryableStatusCodesFromRetryPolicy(retryPolicy2));
        }

        private static HedgingPolicy hedgingPolicy(Map<String, ?> hedgingPolicy2, int maxAttemptsLimit) {
            int maxAttempts = ((Integer) Preconditions.checkNotNull(ServiceConfigUtil.getMaxAttemptsFromHedgingPolicy(hedgingPolicy2), "maxAttempts cannot be empty")).intValue();
            boolean z = true;
            Preconditions.checkArgument(maxAttempts >= 2, "maxAttempts must be greater than 1: %s", maxAttempts);
            int maxAttempts2 = Math.min(maxAttempts, maxAttemptsLimit);
            long hedgingDelayNanos = ((Long) Preconditions.checkNotNull(ServiceConfigUtil.getHedgingDelayNanosFromHedgingPolicy(hedgingPolicy2), "hedgingDelay cannot be empty")).longValue();
            if (hedgingDelayNanos < 0) {
                z = false;
            }
            Preconditions.checkArgument(z, "hedgingDelay must not be negative: %s", hedgingDelayNanos);
            return new HedgingPolicy(maxAttempts2, hedgingDelayNanos, ServiceConfigUtil.getNonFatalStatusCodesFromHedgingPolicy(hedgingPolicy2));
        }
    }
}
