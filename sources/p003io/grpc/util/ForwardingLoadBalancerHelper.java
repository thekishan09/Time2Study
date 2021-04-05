package p003io.grpc.util;

import com.google.common.base.MoreObjects;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import p003io.grpc.Attributes;
import p003io.grpc.ChannelLogger;
import p003io.grpc.ConnectivityState;
import p003io.grpc.EquivalentAddressGroup;
import p003io.grpc.LoadBalancer;
import p003io.grpc.ManagedChannel;
import p003io.grpc.NameResolver;
import p003io.grpc.NameResolverRegistry;
import p003io.grpc.SynchronizationContext;

/* renamed from: io.grpc.util.ForwardingLoadBalancerHelper */
public abstract class ForwardingLoadBalancerHelper extends LoadBalancer.Helper {
    /* access modifiers changed from: protected */
    public abstract LoadBalancer.Helper delegate();

    @Deprecated
    public LoadBalancer.Subchannel createSubchannel(List<EquivalentAddressGroup> addrs, Attributes attrs) {
        return delegate().createSubchannel(addrs, attrs);
    }

    public LoadBalancer.Subchannel createSubchannel(LoadBalancer.CreateSubchannelArgs args) {
        return delegate().createSubchannel(args);
    }

    @Deprecated
    public void updateSubchannelAddresses(LoadBalancer.Subchannel subchannel, List<EquivalentAddressGroup> addrs) {
        delegate().updateSubchannelAddresses(subchannel, addrs);
    }

    public ManagedChannel createOobChannel(EquivalentAddressGroup eag, String authority) {
        return delegate().createOobChannel(eag, authority);
    }

    public void updateOobChannelAddresses(ManagedChannel channel, EquivalentAddressGroup eag) {
        delegate().updateOobChannelAddresses(channel, eag);
    }

    public ManagedChannel createResolvingOobChannel(String target) {
        return delegate().createResolvingOobChannel(target);
    }

    public void updateBalancingState(ConnectivityState newState, LoadBalancer.SubchannelPicker newPicker) {
        delegate().updateBalancingState(newState, newPicker);
    }

    public void refreshNameResolution() {
        delegate().refreshNameResolution();
    }

    @Deprecated
    public void runSerialized(Runnable task) {
        delegate().runSerialized(task);
    }

    @Deprecated
    public NameResolver.Factory getNameResolverFactory() {
        return delegate().getNameResolverFactory();
    }

    public String getAuthority() {
        return delegate().getAuthority();
    }

    public SynchronizationContext getSynchronizationContext() {
        return delegate().getSynchronizationContext();
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return delegate().getScheduledExecutorService();
    }

    public ChannelLogger getChannelLogger() {
        return delegate().getChannelLogger();
    }

    public NameResolver.Args getNameResolverArgs() {
        return delegate().getNameResolverArgs();
    }

    public NameResolverRegistry getNameResolverRegistry() {
        return delegate().getNameResolverRegistry();
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object) this).add("delegate", (Object) delegate()).toString();
    }
}
