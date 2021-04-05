package p003io.grpc.inprocess;

import com.google.common.base.Preconditions;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import p003io.grpc.Deadline;
import p003io.grpc.ServerStreamTracer;
import p003io.grpc.internal.AbstractServerImplBuilder;
import p003io.grpc.internal.FixedObjectPool;
import p003io.grpc.internal.GrpcUtil;
import p003io.grpc.internal.ObjectPool;
import p003io.grpc.internal.SharedResourcePool;

/* renamed from: io.grpc.inprocess.InProcessServerBuilder */
public final class InProcessServerBuilder extends AbstractServerImplBuilder<InProcessServerBuilder> {
    int maxInboundMetadataSize = Integer.MAX_VALUE;
    final String name;
    ObjectPool<ScheduledExecutorService> schedulerPool = SharedResourcePool.forResource(GrpcUtil.TIMER_SERVICE);

    public static InProcessServerBuilder forName(String name2) {
        return new InProcessServerBuilder(name2);
    }

    public static InProcessServerBuilder forPort(int port) {
        throw new UnsupportedOperationException("call forName() instead");
    }

    public static String generateName() {
        return UUID.randomUUID().toString();
    }

    private InProcessServerBuilder(String name2) {
        this.name = (String) Preconditions.checkNotNull(name2, "name");
        setStatsRecordStartedRpcs(false);
        setStatsRecordFinishedRpcs(false);
        handshakeTimeout(Long.MAX_VALUE, TimeUnit.SECONDS);
    }

    public InProcessServerBuilder scheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
        this.schedulerPool = new FixedObjectPool(Preconditions.checkNotNull(scheduledExecutorService, "scheduledExecutorService"));
        return this;
    }

    public InProcessServerBuilder deadlineTicker(Deadline.Ticker ticker) {
        setDeadlineTicker(ticker);
        return this;
    }

    public InProcessServerBuilder maxInboundMetadataSize(int bytes) {
        Preconditions.checkArgument(bytes > 0, "maxInboundMetadataSize must be > 0");
        this.maxInboundMetadataSize = bytes;
        return this;
    }

    /* access modifiers changed from: protected */
    public List<InProcessServer> buildTransportServers(List<? extends ServerStreamTracer.Factory> streamTracerFactories) {
        return Collections.singletonList(new InProcessServer(this, streamTracerFactories));
    }

    public InProcessServerBuilder useTransportSecurity(File certChain, File privateKey) {
        throw new UnsupportedOperationException("TLS not supported in InProcessServer");
    }
}
