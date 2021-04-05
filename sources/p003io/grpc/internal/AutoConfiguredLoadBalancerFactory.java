package p003io.grpc.internal;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import p003io.grpc.Attributes;
import p003io.grpc.ChannelLogger;
import p003io.grpc.ConnectivityState;
import p003io.grpc.ConnectivityStateInfo;
import p003io.grpc.EquivalentAddressGroup;
import p003io.grpc.LoadBalancer;
import p003io.grpc.LoadBalancerProvider;
import p003io.grpc.LoadBalancerRegistry;
import p003io.grpc.NameResolver;
import p003io.grpc.Status;
import p003io.grpc.internal.ServiceConfigUtil;

/* renamed from: io.grpc.internal.AutoConfiguredLoadBalancerFactory */
public final class AutoConfiguredLoadBalancerFactory {
    /* access modifiers changed from: private */
    public final String defaultPolicy;
    /* access modifiers changed from: private */
    public final LoadBalancerRegistry registry;

    public AutoConfiguredLoadBalancerFactory(String defaultPolicy2) {
        this(LoadBalancerRegistry.getDefaultRegistry(), defaultPolicy2);
    }

    AutoConfiguredLoadBalancerFactory(LoadBalancerRegistry registry2, String defaultPolicy2) {
        this.registry = (LoadBalancerRegistry) Preconditions.checkNotNull(registry2, "registry");
        this.defaultPolicy = (String) Preconditions.checkNotNull(defaultPolicy2, "defaultPolicy");
    }

    public AutoConfiguredLoadBalancer newLoadBalancer(LoadBalancer.Helper helper) {
        return new AutoConfiguredLoadBalancer(helper);
    }

    /* renamed from: io.grpc.internal.AutoConfiguredLoadBalancerFactory$NoopLoadBalancer */
    private static final class NoopLoadBalancer extends LoadBalancer {
        private NoopLoadBalancer() {
        }

        @Deprecated
        public void handleResolvedAddressGroups(List<EquivalentAddressGroup> list, Attributes a) {
        }

        public void handleResolvedAddresses(LoadBalancer.ResolvedAddresses resolvedAddresses) {
        }

        public void handleNameResolutionError(Status error) {
        }

        public void shutdown() {
        }
    }

    /* renamed from: io.grpc.internal.AutoConfiguredLoadBalancerFactory$AutoConfiguredLoadBalancer */
    public final class AutoConfiguredLoadBalancer {
        private LoadBalancer delegate;
        private LoadBalancerProvider delegateProvider;
        private final LoadBalancer.Helper helper;

        AutoConfiguredLoadBalancer(LoadBalancer.Helper helper2) {
            this.helper = helper2;
            LoadBalancerProvider provider = AutoConfiguredLoadBalancerFactory.this.registry.getProvider(AutoConfiguredLoadBalancerFactory.this.defaultPolicy);
            this.delegateProvider = provider;
            if (provider != null) {
                this.delegate = provider.newLoadBalancer(helper2);
                return;
            }
            throw new IllegalStateException("Could not find policy '" + AutoConfiguredLoadBalancerFactory.this.defaultPolicy + "'. Make sure its implementation is either registered to LoadBalancerRegistry or included in META-INF/services/io.grpc.LoadBalancerProvider from your jar files.");
        }

        public void handleResolvedAddresses(LoadBalancer.ResolvedAddresses resolvedAddresses) {
            tryHandleResolvedAddresses(resolvedAddresses);
        }

        /* access modifiers changed from: package-private */
        public Status tryHandleResolvedAddresses(LoadBalancer.ResolvedAddresses resolvedAddresses) {
            List<EquivalentAddressGroup> servers = resolvedAddresses.getAddresses();
            Attributes attributes = resolvedAddresses.getAttributes();
            if (attributes.get(LoadBalancer.ATTR_LOAD_BALANCING_CONFIG) == null) {
                PolicySelection policySelection = (PolicySelection) resolvedAddresses.getLoadBalancingPolicyConfig();
                if (policySelection == null) {
                    try {
                        policySelection = new PolicySelection(AutoConfiguredLoadBalancerFactory.this.getProviderOrThrow(AutoConfiguredLoadBalancerFactory.this.defaultPolicy, "using default policy"), (Map<String, ?>) null, (Object) null);
                    } catch (PolicyException e) {
                        this.helper.updateBalancingState(ConnectivityState.TRANSIENT_FAILURE, new FailingPicker(Status.INTERNAL.withDescription(e.getMessage())));
                        this.delegate.shutdown();
                        this.delegateProvider = null;
                        this.delegate = new NoopLoadBalancer();
                        return Status.f3OK;
                    }
                }
                if (this.delegateProvider == null || !policySelection.provider.getPolicyName().equals(this.delegateProvider.getPolicyName())) {
                    this.helper.updateBalancingState(ConnectivityState.CONNECTING, new EmptyPicker());
                    this.delegate.shutdown();
                    LoadBalancerProvider loadBalancerProvider = policySelection.provider;
                    this.delegateProvider = loadBalancerProvider;
                    LoadBalancer old = this.delegate;
                    this.delegate = loadBalancerProvider.newLoadBalancer(this.helper);
                    this.helper.getChannelLogger().log(ChannelLogger.ChannelLogLevel.INFO, "Load balancer changed from {0} to {1}", old.getClass().getSimpleName(), this.delegate.getClass().getSimpleName());
                }
                Object lbConfig = policySelection.config;
                if (lbConfig != null) {
                    this.helper.getChannelLogger().log(ChannelLogger.ChannelLogLevel.DEBUG, "Load-balancing config: {0}", policySelection.config);
                    attributes = attributes.toBuilder().set(LoadBalancer.ATTR_LOAD_BALANCING_CONFIG, policySelection.rawConfig).build();
                }
                LoadBalancer delegate2 = getDelegate();
                if (!resolvedAddresses.getAddresses().isEmpty() || delegate2.canHandleEmptyAddressListFromNameResolution()) {
                    delegate2.handleResolvedAddresses(LoadBalancer.ResolvedAddresses.newBuilder().setAddresses(resolvedAddresses.getAddresses()).setAttributes(attributes).setLoadBalancingPolicyConfig(lbConfig).build());
                    return Status.f3OK;
                }
                Status status = Status.UNAVAILABLE;
                return status.withDescription("NameResolver returned no usable address. addrs=" + servers + ", attrs=" + attributes);
            }
            throw new IllegalArgumentException("Unexpected ATTR_LOAD_BALANCING_CONFIG from upstream: " + attributes.get(LoadBalancer.ATTR_LOAD_BALANCING_CONFIG));
        }

        /* access modifiers changed from: package-private */
        public void handleNameResolutionError(Status error) {
            getDelegate().handleNameResolutionError(error);
        }

        /* access modifiers changed from: package-private */
        @Deprecated
        public void handleSubchannelState(LoadBalancer.Subchannel subchannel, ConnectivityStateInfo stateInfo) {
            getDelegate().handleSubchannelState(subchannel, stateInfo);
        }

        /* access modifiers changed from: package-private */
        public void requestConnection() {
            getDelegate().requestConnection();
        }

        /* access modifiers changed from: package-private */
        public void shutdown() {
            this.delegate.shutdown();
            this.delegate = null;
        }

        public LoadBalancer getDelegate() {
            return this.delegate;
        }

        /* access modifiers changed from: package-private */
        public void setDelegate(LoadBalancer lb) {
            this.delegate = lb;
        }

        /* access modifiers changed from: package-private */
        public LoadBalancerProvider getDelegateProvider() {
            return this.delegateProvider;
        }
    }

    /* access modifiers changed from: private */
    public LoadBalancerProvider getProviderOrThrow(String policy, String choiceReason) throws PolicyException {
        LoadBalancerProvider provider = this.registry.getProvider(policy);
        if (provider != null) {
            return provider;
        }
        throw new PolicyException("Trying to load '" + policy + "' because " + choiceReason + ", but it's unavailable");
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public NameResolver.ConfigOrError parseLoadBalancerPolicy(Map<String, ?> serviceConfig, ChannelLogger channelLogger) {
        List<ServiceConfigUtil.LbConfig> loadBalancerConfigs = null;
        if (serviceConfig != null) {
            try {
                loadBalancerConfigs = ServiceConfigUtil.unwrapLoadBalancingConfigList(ServiceConfigUtil.getLoadBalancingConfigsFromServiceConfig(serviceConfig));
            } catch (RuntimeException e) {
                return NameResolver.ConfigOrError.fromError(Status.UNKNOWN.withDescription("can't parse load balancer configuration").withCause(e));
            }
        }
        if (loadBalancerConfigs == null || loadBalancerConfigs.isEmpty()) {
            return null;
        }
        List<String> policiesTried = new ArrayList<>();
        for (ServiceConfigUtil.LbConfig lbConfig : loadBalancerConfigs) {
            String policy = lbConfig.getPolicyName();
            LoadBalancerProvider provider = this.registry.getProvider(policy);
            if (provider == null) {
                policiesTried.add(policy);
            } else {
                if (!policiesTried.isEmpty()) {
                    channelLogger.log(ChannelLogger.ChannelLogLevel.DEBUG, "{0} specified by Service Config are not available", policiesTried);
                }
                NameResolver.ConfigOrError parsedLbPolicyConfig = provider.parseLoadBalancingPolicyConfig(lbConfig.getRawConfigValue());
                if (parsedLbPolicyConfig.getError() != null) {
                    return parsedLbPolicyConfig;
                }
                return NameResolver.ConfigOrError.fromConfig(new PolicySelection(provider, lbConfig.getRawConfigValue(), parsedLbPolicyConfig.getConfig()));
            }
        }
        Status status = Status.UNKNOWN;
        return NameResolver.ConfigOrError.fromError(status.withDescription("None of " + policiesTried + " specified by Service Config are available."));
    }

    /* renamed from: io.grpc.internal.AutoConfiguredLoadBalancerFactory$PolicyException */
    static final class PolicyException extends Exception {
        private static final long serialVersionUID = 1;

        private PolicyException(String msg) {
            super(msg);
        }
    }

    /* renamed from: io.grpc.internal.AutoConfiguredLoadBalancerFactory$PolicySelection */
    static final class PolicySelection {
        @Nullable
        final Object config;
        final LoadBalancerProvider provider;
        @Nullable
        final Map<String, ?> rawConfig;

        PolicySelection(LoadBalancerProvider provider2, @Nullable Map<String, ?> rawConfig2, @Nullable Object config2) {
            this.provider = (LoadBalancerProvider) Preconditions.checkNotNull(provider2, "provider");
            this.rawConfig = rawConfig2;
            this.config = config2;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            PolicySelection that = (PolicySelection) o;
            if (!Objects.equal(this.provider, that.provider) || !Objects.equal(this.rawConfig, that.rawConfig) || !Objects.equal(this.config, that.config)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return Objects.hashCode(this.provider, this.rawConfig, this.config);
        }

        public String toString() {
            return MoreObjects.toStringHelper((Object) this).add("provider", (Object) this.provider).add("rawConfig", (Object) this.rawConfig).add("config", this.config).toString();
        }
    }

    /* renamed from: io.grpc.internal.AutoConfiguredLoadBalancerFactory$EmptyPicker */
    private static final class EmptyPicker extends LoadBalancer.SubchannelPicker {
        private EmptyPicker() {
        }

        public LoadBalancer.PickResult pickSubchannel(LoadBalancer.PickSubchannelArgs args) {
            return LoadBalancer.PickResult.withNoResult();
        }

        public String toString() {
            return MoreObjects.toStringHelper((Class<?>) EmptyPicker.class).toString();
        }
    }

    /* renamed from: io.grpc.internal.AutoConfiguredLoadBalancerFactory$FailingPicker */
    private static final class FailingPicker extends LoadBalancer.SubchannelPicker {
        private final Status failure;

        FailingPicker(Status failure2) {
            this.failure = failure2;
        }

        public LoadBalancer.PickResult pickSubchannel(LoadBalancer.PickSubchannelArgs args) {
            return LoadBalancer.PickResult.withError(this.failure);
        }
    }
}
