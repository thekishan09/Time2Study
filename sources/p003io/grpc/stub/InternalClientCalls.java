package p003io.grpc.stub;

import p003io.grpc.CallOptions;
import p003io.grpc.stub.ClientCalls;

/* renamed from: io.grpc.stub.InternalClientCalls */
public final class InternalClientCalls {
    public static CallOptions.Key<ClientCalls.StubType> getStubTypeOption() {
        return ClientCalls.STUB_TYPE_OPTION;
    }

    public static StubType getStubType(CallOptions callOptions) {
        return StubType.m596of((ClientCalls.StubType) callOptions.getOption(ClientCalls.STUB_TYPE_OPTION));
    }

    /* renamed from: io.grpc.stub.InternalClientCalls$StubType */
    public enum StubType {
        BLOCKING(ClientCalls.StubType.BLOCKING),
        ASYNC(ClientCalls.StubType.ASYNC),
        FUTURE(ClientCalls.StubType.FUTURE);
        
        private final ClientCalls.StubType internalType;

        private StubType(ClientCalls.StubType internalType2) {
            this.internalType = internalType2;
        }

        /* renamed from: of */
        public static StubType m596of(ClientCalls.StubType internal) {
            for (StubType value : values()) {
                if (value.internalType == internal) {
                    return value;
                }
            }
            throw new AssertionError("Unknown StubType: " + internal.name());
        }
    }
}
