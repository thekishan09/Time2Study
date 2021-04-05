package p003io.grpc.util;

import com.google.common.base.MoreObjects;
import p003io.grpc.ClientStreamTracer;
import p003io.grpc.Metadata;
import p003io.grpc.Status;

/* renamed from: io.grpc.util.ForwardingClientStreamTracer */
public abstract class ForwardingClientStreamTracer extends ClientStreamTracer {
    /* access modifiers changed from: protected */
    public abstract ClientStreamTracer delegate();

    public void outboundHeaders() {
        delegate().outboundHeaders();
    }

    public void inboundHeaders() {
        delegate().inboundHeaders();
    }

    public void inboundTrailers(Metadata trailers) {
        delegate().inboundTrailers(trailers);
    }

    public void streamClosed(Status status) {
        delegate().streamClosed(status);
    }

    public void outboundMessage(int seqNo) {
        delegate().outboundMessage(seqNo);
    }

    public void inboundMessage(int seqNo) {
        delegate().inboundMessage(seqNo);
    }

    public void outboundMessageSent(int seqNo, long optionalWireSize, long optionalUncompressedSize) {
        delegate().outboundMessageSent(seqNo, optionalWireSize, optionalUncompressedSize);
    }

    public void inboundMessageRead(int seqNo, long optionalWireSize, long optionalUncompressedSize) {
        delegate().inboundMessageRead(seqNo, optionalWireSize, optionalUncompressedSize);
    }

    public void outboundWireSize(long bytes) {
        delegate().outboundWireSize(bytes);
    }

    public void outboundUncompressedSize(long bytes) {
        delegate().outboundUncompressedSize(bytes);
    }

    public void inboundWireSize(long bytes) {
        delegate().inboundWireSize(bytes);
    }

    public void inboundUncompressedSize(long bytes) {
        delegate().inboundUncompressedSize(bytes);
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object) this).add("delegate", (Object) delegate()).toString();
    }
}
