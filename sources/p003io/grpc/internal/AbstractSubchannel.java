package p003io.grpc.internal;

import p003io.grpc.InternalChannelz;
import p003io.grpc.InternalInstrumented;
import p003io.grpc.LoadBalancer;

/* renamed from: io.grpc.internal.AbstractSubchannel */
abstract class AbstractSubchannel extends LoadBalancer.Subchannel {
    /* access modifiers changed from: package-private */
    public abstract InternalInstrumented<InternalChannelz.ChannelStats> getInstrumentedInternalSubchannel();

    AbstractSubchannel() {
    }
}
