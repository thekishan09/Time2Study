package p003io.grpc.internal;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import p003io.grpc.CallOptions;
import p003io.grpc.LoadBalancer;
import p003io.grpc.Metadata;
import p003io.grpc.MethodDescriptor;

/* renamed from: io.grpc.internal.PickSubchannelArgsImpl */
final class PickSubchannelArgsImpl extends LoadBalancer.PickSubchannelArgs {
    private final CallOptions callOptions;
    private final Metadata headers;
    private final MethodDescriptor<?, ?> method;

    PickSubchannelArgsImpl(MethodDescriptor<?, ?> method2, Metadata headers2, CallOptions callOptions2) {
        this.method = (MethodDescriptor) Preconditions.checkNotNull(method2, "method");
        this.headers = (Metadata) Preconditions.checkNotNull(headers2, "headers");
        this.callOptions = (CallOptions) Preconditions.checkNotNull(callOptions2, "callOptions");
    }

    public Metadata getHeaders() {
        return this.headers;
    }

    public CallOptions getCallOptions() {
        return this.callOptions;
    }

    public MethodDescriptor<?, ?> getMethodDescriptor() {
        return this.method;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PickSubchannelArgsImpl that = (PickSubchannelArgsImpl) o;
        if (!Objects.equal(this.callOptions, that.callOptions) || !Objects.equal(this.headers, that.headers) || !Objects.equal(this.method, that.method)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hashCode(this.callOptions, this.headers, this.method);
    }

    public final String toString() {
        return "[method=" + this.method + " headers=" + this.headers + " callOptions=" + this.callOptions + "]";
    }
}
