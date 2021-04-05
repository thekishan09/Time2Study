package p003io.grpc.internal;

import androidx.core.app.NotificationCompat;
import com.google.common.base.Preconditions;
import p003io.grpc.Metadata;
import p003io.grpc.Status;
import p003io.grpc.internal.ClientStreamListener;

/* renamed from: io.grpc.internal.FailingClientStream */
public final class FailingClientStream extends NoopClientStream {
    private final Status error;
    private final ClientStreamListener.RpcProgress rpcProgress;
    private boolean started;

    public FailingClientStream(Status error2) {
        this(error2, ClientStreamListener.RpcProgress.PROCESSED);
    }

    public FailingClientStream(Status error2, ClientStreamListener.RpcProgress rpcProgress2) {
        Preconditions.checkArgument(!error2.isOk(), "error must not be OK");
        this.error = error2;
        this.rpcProgress = rpcProgress2;
    }

    public void start(ClientStreamListener listener) {
        Preconditions.checkState(!this.started, "already started");
        this.started = true;
        listener.closed(this.error, this.rpcProgress, new Metadata());
    }

    /* access modifiers changed from: package-private */
    public Status getError() {
        return this.error;
    }

    public void appendTimeoutInsight(InsightBuilder insight) {
        insight.appendKeyValue("error", this.error).appendKeyValue(NotificationCompat.CATEGORY_PROGRESS, this.rpcProgress);
    }
}
