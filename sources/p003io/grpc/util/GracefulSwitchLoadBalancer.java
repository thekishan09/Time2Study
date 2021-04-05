package p003io.grpc.util;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import javax.annotation.Nullable;
import p003io.grpc.ConnectivityState;
import p003io.grpc.ConnectivityStateInfo;
import p003io.grpc.LoadBalancer;
import p003io.grpc.Status;

/* renamed from: io.grpc.util.GracefulSwitchLoadBalancer */
public final class GracefulSwitchLoadBalancer extends ForwardingLoadBalancer {
    static final LoadBalancer.SubchannelPicker BUFFER_PICKER = new LoadBalancer.SubchannelPicker() {
        public LoadBalancer.PickResult pickSubchannel(LoadBalancer.PickSubchannelArgs args) {
            return LoadBalancer.PickResult.withNoResult();
        }

        public String toString() {
            return "BUFFER_PICKER";
        }
    };
    @Nullable
    private LoadBalancer.Factory currentBalancerFactory;
    /* access modifiers changed from: private */
    public LoadBalancer currentLb;
    /* access modifiers changed from: private */
    public boolean currentLbIsReady;
    /* access modifiers changed from: private */
    public final LoadBalancer defaultBalancer;
    /* access modifiers changed from: private */
    public final LoadBalancer.Helper helper;
    @Nullable
    private LoadBalancer.Factory pendingBalancerFactory;
    /* access modifiers changed from: private */
    public LoadBalancer pendingLb;
    /* access modifiers changed from: private */
    public LoadBalancer.SubchannelPicker pendingPicker;
    /* access modifiers changed from: private */
    public ConnectivityState pendingState;

    public GracefulSwitchLoadBalancer(LoadBalancer.Helper helper2) {
        C24291 r0 = new LoadBalancer() {
            public void handleResolvedAddresses(LoadBalancer.ResolvedAddresses resolvedAddresses) {
                throw new IllegalStateException("GracefulSwitchLoadBalancer must switch to a load balancing policy before handling ResolvedAddresses");
            }

            public void handleNameResolutionError(final Status error) {
                GracefulSwitchLoadBalancer.this.helper.updateBalancingState(ConnectivityState.TRANSIENT_FAILURE, new LoadBalancer.SubchannelPicker() {
                    public LoadBalancer.PickResult pickSubchannel(LoadBalancer.PickSubchannelArgs args) {
                        return LoadBalancer.PickResult.withError(error);
                    }

                    public String toString() {
                        return MoreObjects.toStringHelper((Class<?>) AnonymousClass1ErrorPicker.class).add("error", (Object) error).toString();
                    }
                });
            }

            public void shutdown() {
            }
        };
        this.defaultBalancer = r0;
        this.currentLb = r0;
        this.pendingLb = r0;
        this.helper = (LoadBalancer.Helper) Preconditions.checkNotNull(helper2, "helper");
    }

    public void switchTo(LoadBalancer.Factory newBalancerFactory) {
        Preconditions.checkNotNull(newBalancerFactory, "newBalancerFactory");
        if (!newBalancerFactory.equals(this.pendingBalancerFactory)) {
            this.pendingLb.shutdown();
            this.pendingLb = this.defaultBalancer;
            this.pendingBalancerFactory = null;
            this.pendingState = ConnectivityState.CONNECTING;
            this.pendingPicker = BUFFER_PICKER;
            if (!newBalancerFactory.equals(this.currentBalancerFactory)) {
                AnonymousClass1PendingHelper pendingHelper = new ForwardingLoadBalancerHelper() {

                    /* renamed from: lb */
                    LoadBalancer f562lb;

                    /* access modifiers changed from: protected */
                    public LoadBalancer.Helper delegate() {
                        return GracefulSwitchLoadBalancer.this.helper;
                    }

                    public void updateBalancingState(ConnectivityState newState, LoadBalancer.SubchannelPicker newPicker) {
                        if (this.f562lb == GracefulSwitchLoadBalancer.this.pendingLb) {
                            Preconditions.checkState(GracefulSwitchLoadBalancer.this.currentLbIsReady, "there's pending lb while current lb has been out of READY");
                            ConnectivityState unused = GracefulSwitchLoadBalancer.this.pendingState = newState;
                            LoadBalancer.SubchannelPicker unused2 = GracefulSwitchLoadBalancer.this.pendingPicker = newPicker;
                            if (newState == ConnectivityState.READY) {
                                GracefulSwitchLoadBalancer.this.swap();
                            }
                        } else if (this.f562lb == GracefulSwitchLoadBalancer.this.currentLb) {
                            boolean unused3 = GracefulSwitchLoadBalancer.this.currentLbIsReady = newState == ConnectivityState.READY;
                            if (GracefulSwitchLoadBalancer.this.currentLbIsReady || GracefulSwitchLoadBalancer.this.pendingLb == GracefulSwitchLoadBalancer.this.defaultBalancer) {
                                GracefulSwitchLoadBalancer.this.helper.updateBalancingState(newState, newPicker);
                            } else {
                                GracefulSwitchLoadBalancer.this.swap();
                            }
                        }
                    }
                };
                pendingHelper.f562lb = newBalancerFactory.newLoadBalancer(pendingHelper);
                this.pendingLb = pendingHelper.f562lb;
                this.pendingBalancerFactory = newBalancerFactory;
                if (!this.currentLbIsReady) {
                    swap();
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void swap() {
        this.helper.updateBalancingState(this.pendingState, this.pendingPicker);
        this.currentLb.shutdown();
        this.currentLb = this.pendingLb;
        this.currentBalancerFactory = this.pendingBalancerFactory;
        this.pendingLb = this.defaultBalancer;
        this.pendingBalancerFactory = null;
    }

    /* access modifiers changed from: protected */
    public LoadBalancer delegate() {
        LoadBalancer loadBalancer = this.pendingLb;
        return loadBalancer == this.defaultBalancer ? this.currentLb : loadBalancer;
    }

    @Deprecated
    public void handleSubchannelState(LoadBalancer.Subchannel subchannel, ConnectivityStateInfo stateInfo) {
        throw new UnsupportedOperationException("handleSubchannelState() is not supported by " + getClass().getName());
    }

    public void shutdown() {
        this.pendingLb.shutdown();
        this.currentLb.shutdown();
    }
}
