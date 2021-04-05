package p003io.grpc.stub;

import javax.annotation.CheckReturnValue;
import p003io.grpc.CallOptions;
import p003io.grpc.Channel;
import p003io.grpc.stub.AbstractAsyncStub;
import p003io.grpc.stub.AbstractStub;
import p003io.grpc.stub.ClientCalls;

@CheckReturnValue
/* renamed from: io.grpc.stub.AbstractAsyncStub */
public abstract class AbstractAsyncStub<S extends AbstractAsyncStub<S>> extends AbstractStub<S> {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    protected AbstractAsyncStub(Channel channel, CallOptions callOptions) {
        super(channel, callOptions);
    }

    public static <T extends AbstractStub<T>> T newStub(AbstractStub.StubFactory<T> factory, Channel channel) {
        return newStub(factory, channel, CallOptions.DEFAULT);
    }

    public static <T extends AbstractStub<T>> T newStub(AbstractStub.StubFactory<T> factory, Channel channel, CallOptions callOptions) {
        return factory.newStub(channel, callOptions.withOption(ClientCalls.STUB_TYPE_OPTION, ClientCalls.StubType.ASYNC));
    }
}
