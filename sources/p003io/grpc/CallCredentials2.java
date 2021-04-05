package p003io.grpc;

import java.util.concurrent.Executor;
import p003io.grpc.CallCredentials;

@Deprecated
/* renamed from: io.grpc.CallCredentials2 */
public abstract class CallCredentials2 extends CallCredentials {

    /* renamed from: io.grpc.CallCredentials2$MetadataApplier */
    public static abstract class MetadataApplier extends CallCredentials.MetadataApplier {
    }

    public abstract void applyRequestMetadata(CallCredentials.RequestInfo requestInfo, Executor executor, MetadataApplier metadataApplier);

    public final void applyRequestMetadata(CallCredentials.RequestInfo requestInfo, Executor appExecutor, final CallCredentials.MetadataApplier applier) {
        applyRequestMetadata(requestInfo, appExecutor, (MetadataApplier) new MetadataApplier() {
            public void apply(Metadata headers) {
                applier.apply(headers);
            }

            public void fail(Status status) {
                applier.fail(status);
            }
        });
    }
}
