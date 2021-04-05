package p003io.grpc;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

/* renamed from: io.grpc.ClientStreamTracer */
public abstract class ClientStreamTracer extends StreamTracer {
    public void outboundHeaders() {
    }

    public void inboundHeaders() {
    }

    public void inboundTrailers(Metadata trailers) {
    }

    /* renamed from: io.grpc.ClientStreamTracer$Factory */
    public static abstract class Factory {
        @Deprecated
        public ClientStreamTracer newClientStreamTracer(CallOptions callOptions, Metadata headers) {
            throw new UnsupportedOperationException("Not implemented");
        }

        public ClientStreamTracer newClientStreamTracer(StreamInfo info, Metadata headers) {
            return newClientStreamTracer(info.getCallOptions(), headers);
        }
    }

    /* renamed from: io.grpc.ClientStreamTracer$StreamInfo */
    public static final class StreamInfo {
        private final CallOptions callOptions;
        private final Attributes transportAttrs;

        StreamInfo(Attributes transportAttrs2, CallOptions callOptions2) {
            this.transportAttrs = (Attributes) Preconditions.checkNotNull(transportAttrs2, "transportAttrs");
            this.callOptions = (CallOptions) Preconditions.checkNotNull(callOptions2, "callOptions");
        }

        public Attributes getTransportAttrs() {
            return this.transportAttrs;
        }

        public CallOptions getCallOptions() {
            return this.callOptions;
        }

        public Builder toBuilder() {
            Builder builder = new Builder();
            builder.setTransportAttrs(this.transportAttrs);
            builder.setCallOptions(this.callOptions);
            return builder;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public String toString() {
            return MoreObjects.toStringHelper((Object) this).add("transportAttrs", (Object) this.transportAttrs).add("callOptions", (Object) this.callOptions).toString();
        }

        /* renamed from: io.grpc.ClientStreamTracer$StreamInfo$Builder */
        public static final class Builder {
            private CallOptions callOptions = CallOptions.DEFAULT;
            private Attributes transportAttrs = Attributes.EMPTY;

            Builder() {
            }

            public Builder setTransportAttrs(Attributes transportAttrs2) {
                this.transportAttrs = (Attributes) Preconditions.checkNotNull(transportAttrs2, "transportAttrs cannot be null");
                return this;
            }

            public Builder setCallOptions(CallOptions callOptions2) {
                this.callOptions = (CallOptions) Preconditions.checkNotNull(callOptions2, "callOptions cannot be null");
                return this;
            }

            public StreamInfo build() {
                return new StreamInfo(this.transportAttrs, this.callOptions);
            }
        }
    }
}
