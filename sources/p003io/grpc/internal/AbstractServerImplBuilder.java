package p003io.grpc.internal;

import androidx.core.app.NotificationCompat;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.MoreExecutors;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import p003io.grpc.BinaryLog;
import p003io.grpc.BindableService;
import p003io.grpc.CompressorRegistry;
import p003io.grpc.Context;
import p003io.grpc.Deadline;
import p003io.grpc.DecompressorRegistry;
import p003io.grpc.HandlerRegistry;
import p003io.grpc.InternalChannelz;
import p003io.grpc.InternalNotifyOnServerBuild;
import p003io.grpc.Server;
import p003io.grpc.ServerBuilder;
import p003io.grpc.ServerInterceptor;
import p003io.grpc.ServerMethodDefinition;
import p003io.grpc.ServerServiceDefinition;
import p003io.grpc.ServerStreamTracer;
import p003io.grpc.ServerTransportFilter;
import p003io.grpc.internal.AbstractServerImplBuilder;
import p003io.grpc.internal.CallTracer;
import p003io.grpc.internal.InternalHandlerRegistry;
import p003io.grpc.internal.TransportTracer;

/* renamed from: io.grpc.internal.AbstractServerImplBuilder */
public abstract class AbstractServerImplBuilder<T extends AbstractServerImplBuilder<T>> extends ServerBuilder<T> {
    private static final CompressorRegistry DEFAULT_COMPRESSOR_REGISTRY = CompressorRegistry.getDefaultInstance();
    private static final DecompressorRegistry DEFAULT_DECOMPRESSOR_REGISTRY = DecompressorRegistry.getDefaultInstance();
    private static final ObjectPool<? extends Executor> DEFAULT_EXECUTOR_POOL = SharedResourcePool.forResource(GrpcUtil.SHARED_CHANNEL_EXECUTOR);
    private static final HandlerRegistry DEFAULT_FALLBACK_REGISTRY = new DefaultFallbackRegistry();
    private static final long DEFAULT_HANDSHAKE_TIMEOUT_MILLIS = TimeUnit.SECONDS.toMillis(120);
    private static final Logger log = Logger.getLogger(AbstractServerImplBuilder.class.getName());
    @Nullable
    BinaryLog binlog;
    CallTracer.Factory callTracerFactory = CallTracer.getDefaultFactory();
    InternalChannelz channelz = InternalChannelz.instance();
    CompressorRegistry compressorRegistry = DEFAULT_COMPRESSOR_REGISTRY;
    DecompressorRegistry decompressorRegistry = DEFAULT_DECOMPRESSOR_REGISTRY;
    ObjectPool<? extends Executor> executorPool = DEFAULT_EXECUTOR_POOL;
    HandlerRegistry fallbackRegistry = DEFAULT_FALLBACK_REGISTRY;
    long handshakeTimeoutMillis = DEFAULT_HANDSHAKE_TIMEOUT_MILLIS;
    final List<ServerInterceptor> interceptors = new ArrayList();
    private final List<InternalNotifyOnServerBuild> notifyOnBuildList = new ArrayList();
    private boolean recordFinishedRpcs = true;
    private boolean recordRealTimeMetrics = false;
    private boolean recordStartedRpcs = true;
    final InternalHandlerRegistry.Builder registryBuilder = new InternalHandlerRegistry.Builder();
    private boolean statsEnabled = true;
    private final List<ServerStreamTracer.Factory> streamTracerFactories = new ArrayList();
    Deadline.Ticker ticker = Deadline.getSystemTicker();
    private boolean tracingEnabled = true;
    final List<ServerTransportFilter> transportFilters = new ArrayList();
    TransportTracer.Factory transportTracerFactory = TransportTracer.getDefaultFactory();

    /* access modifiers changed from: protected */
    public abstract List<? extends InternalServer> buildTransportServers(List<? extends ServerStreamTracer.Factory> list);

    public static ServerBuilder<?> forPort(int port) {
        throw new UnsupportedOperationException("Subclass failed to hide static factory");
    }

    public final T directExecutor() {
        return executor(MoreExecutors.directExecutor());
    }

    public final T executor(@Nullable Executor executor) {
        this.executorPool = executor != null ? new FixedObjectPool<>(executor) : DEFAULT_EXECUTOR_POOL;
        return thisT();
    }

    public final T addService(ServerServiceDefinition service) {
        this.registryBuilder.addService((ServerServiceDefinition) Preconditions.checkNotNull(service, NotificationCompat.CATEGORY_SERVICE));
        return thisT();
    }

    public final T addService(BindableService bindableService) {
        if (bindableService instanceof InternalNotifyOnServerBuild) {
            this.notifyOnBuildList.add((InternalNotifyOnServerBuild) bindableService);
        }
        return addService(((BindableService) Preconditions.checkNotNull(bindableService, "bindableService")).bindService());
    }

    public final T addTransportFilter(ServerTransportFilter filter) {
        this.transportFilters.add(Preconditions.checkNotNull(filter, "filter"));
        return thisT();
    }

    public final T intercept(ServerInterceptor interceptor) {
        this.interceptors.add(Preconditions.checkNotNull(interceptor, "interceptor"));
        return thisT();
    }

    public final T addStreamTracerFactory(ServerStreamTracer.Factory factory) {
        this.streamTracerFactories.add(Preconditions.checkNotNull(factory, "factory"));
        return thisT();
    }

    public final T fallbackHandlerRegistry(@Nullable HandlerRegistry registry) {
        this.fallbackRegistry = registry != null ? registry : DEFAULT_FALLBACK_REGISTRY;
        return thisT();
    }

    public final T decompressorRegistry(@Nullable DecompressorRegistry registry) {
        this.decompressorRegistry = registry != null ? registry : DEFAULT_DECOMPRESSOR_REGISTRY;
        return thisT();
    }

    public final T compressorRegistry(@Nullable CompressorRegistry registry) {
        this.compressorRegistry = registry != null ? registry : DEFAULT_COMPRESSOR_REGISTRY;
        return thisT();
    }

    public final T handshakeTimeout(long timeout, TimeUnit unit) {
        Preconditions.checkArgument(timeout > 0, "handshake timeout is %s, but must be positive", timeout);
        this.handshakeTimeoutMillis = ((TimeUnit) Preconditions.checkNotNull(unit, "unit")).toMillis(timeout);
        return thisT();
    }

    public final T setBinaryLog(@Nullable BinaryLog binaryLog) {
        this.binlog = binaryLog;
        return thisT();
    }

    public final T setTransportTracerFactory(TransportTracer.Factory transportTracerFactory2) {
        this.transportTracerFactory = transportTracerFactory2;
        return thisT();
    }

    /* access modifiers changed from: protected */
    public void setStatsEnabled(boolean value) {
        this.statsEnabled = value;
    }

    /* access modifiers changed from: protected */
    public void setStatsRecordStartedRpcs(boolean value) {
        this.recordStartedRpcs = value;
    }

    /* access modifiers changed from: protected */
    public void setStatsRecordFinishedRpcs(boolean value) {
        this.recordFinishedRpcs = value;
    }

    /* access modifiers changed from: protected */
    public void setStatsRecordRealTimeMetrics(boolean value) {
        this.recordRealTimeMetrics = value;
    }

    /* access modifiers changed from: protected */
    public void setTracingEnabled(boolean value) {
        this.tracingEnabled = value;
    }

    /* access modifiers changed from: protected */
    public void setDeadlineTicker(Deadline.Ticker ticker2) {
        this.ticker = (Deadline.Ticker) Preconditions.checkNotNull(ticker2, "ticker");
    }

    public final Server build() {
        ServerImpl server = new ServerImpl(this, buildTransportServers(getTracerFactories()), Context.ROOT);
        for (InternalNotifyOnServerBuild notifyTarget : this.notifyOnBuildList) {
            notifyTarget.notifyOnBuild(server);
        }
        return server;
    }

    /* access modifiers changed from: package-private */
    public final List<? extends ServerStreamTracer.Factory> getTracerFactories() {
        ArrayList<ServerStreamTracer.Factory> tracerFactories = new ArrayList<>();
        if (this.statsEnabled) {
            ServerStreamTracer.Factory censusStatsTracerFactory = null;
            try {
                censusStatsTracerFactory = (ServerStreamTracer.Factory) Class.forName("io.grpc.census.InternalCensusStatsAccessor").getDeclaredMethod("getServerStreamTracerFactory", new Class[]{Boolean.TYPE, Boolean.TYPE, Boolean.TYPE}).invoke((Object) null, new Object[]{Boolean.valueOf(this.recordStartedRpcs), Boolean.valueOf(this.recordFinishedRpcs), Boolean.valueOf(this.recordRealTimeMetrics)});
            } catch (ClassNotFoundException e) {
                log.log(Level.FINE, "Unable to apply census stats", e);
            } catch (NoSuchMethodException e2) {
                log.log(Level.FINE, "Unable to apply census stats", e2);
            } catch (IllegalAccessException e3) {
                log.log(Level.FINE, "Unable to apply census stats", e3);
            } catch (InvocationTargetException e4) {
                log.log(Level.FINE, "Unable to apply census stats", e4);
            }
            if (censusStatsTracerFactory != null) {
                tracerFactories.add(censusStatsTracerFactory);
            }
        }
        if (this.tracingEnabled) {
            ServerStreamTracer.Factory tracingStreamTracerFactory = null;
            try {
                tracingStreamTracerFactory = (ServerStreamTracer.Factory) Class.forName("io.grpc.census.InternalCensusTracingAccessor").getDeclaredMethod("getServerStreamTracerFactory", new Class[0]).invoke((Object) null, new Object[0]);
            } catch (ClassNotFoundException e5) {
                log.log(Level.FINE, "Unable to apply census stats", e5);
            } catch (NoSuchMethodException e6) {
                log.log(Level.FINE, "Unable to apply census stats", e6);
            } catch (IllegalAccessException e7) {
                log.log(Level.FINE, "Unable to apply census stats", e7);
            } catch (InvocationTargetException e8) {
                log.log(Level.FINE, "Unable to apply census stats", e8);
            }
            if (tracingStreamTracerFactory != null) {
                tracerFactories.add(tracingStreamTracerFactory);
            }
        }
        tracerFactories.addAll(this.streamTracerFactories);
        tracerFactories.trimToSize();
        return Collections.unmodifiableList(tracerFactories);
    }

    /* access modifiers changed from: protected */
    public final InternalChannelz getChannelz() {
        return this.channelz;
    }

    /* access modifiers changed from: protected */
    public final TransportTracer.Factory getTransportTracerFactory() {
        return this.transportTracerFactory;
    }

    private T thisT() {
        return this;
    }

    /* renamed from: io.grpc.internal.AbstractServerImplBuilder$DefaultFallbackRegistry */
    private static final class DefaultFallbackRegistry extends HandlerRegistry {
        private DefaultFallbackRegistry() {
        }

        public List<ServerServiceDefinition> getServices() {
            return Collections.emptyList();
        }

        @Nullable
        public ServerMethodDefinition<?, ?> lookupMethod(String methodName, @Nullable String authority) {
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public ObjectPool<? extends Executor> getExecutorPool() {
        return this.executorPool;
    }
}
