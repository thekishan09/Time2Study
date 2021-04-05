package p003io.grpc.internal;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.MoreExecutors;
import java.lang.reflect.InvocationTargetException;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import p003io.grpc.Attributes;
import p003io.grpc.BinaryLog;
import p003io.grpc.ClientInterceptor;
import p003io.grpc.CompressorRegistry;
import p003io.grpc.DecompressorRegistry;
import p003io.grpc.EquivalentAddressGroup;
import p003io.grpc.InternalChannelz;
import p003io.grpc.ManagedChannel;
import p003io.grpc.ManagedChannelBuilder;
import p003io.grpc.NameResolver;
import p003io.grpc.NameResolverRegistry;
import p003io.grpc.ProxyDetector;
import p003io.grpc.internal.AbstractManagedChannelImplBuilder;
import p003io.grpc.internal.ExponentialBackoffPolicy;
import p003io.grpc.internal.TransportTracer;

/* renamed from: io.grpc.internal.AbstractManagedChannelImplBuilder */
public abstract class AbstractManagedChannelImplBuilder<T extends AbstractManagedChannelImplBuilder<T>> extends ManagedChannelBuilder<T> {
    private static final CompressorRegistry DEFAULT_COMPRESSOR_REGISTRY = CompressorRegistry.getDefaultInstance();
    private static final DecompressorRegistry DEFAULT_DECOMPRESSOR_REGISTRY = DecompressorRegistry.getDefaultInstance();
    private static final ObjectPool<? extends Executor> DEFAULT_EXECUTOR_POOL = SharedResourcePool.forResource(GrpcUtil.SHARED_CHANNEL_EXECUTOR);
    private static final long DEFAULT_PER_RPC_BUFFER_LIMIT_IN_BYTES = 1048576;
    private static final long DEFAULT_RETRY_BUFFER_SIZE_IN_BYTES = 16777216;
    private static final String DIRECT_ADDRESS_SCHEME = "directaddress";
    static final long IDLE_MODE_DEFAULT_TIMEOUT_MILLIS = TimeUnit.MINUTES.toMillis(IDLE_MODE_MAX_TIMEOUT_DAYS);
    static final long IDLE_MODE_MAX_TIMEOUT_DAYS = 30;
    static final long IDLE_MODE_MIN_TIMEOUT_MILLIS = TimeUnit.SECONDS.toMillis(1);
    private static final Logger log = Logger.getLogger(AbstractManagedChannelImplBuilder.class.getName());
    @Nullable
    String authorityOverride;
    @Nullable
    BinaryLog binlog;
    InternalChannelz channelz;
    CompressorRegistry compressorRegistry;
    DecompressorRegistry decompressorRegistry;
    String defaultLbPolicy;
    @Nullable
    Map<String, ?> defaultServiceConfig;
    @Nullable
    private final SocketAddress directServerAddress;
    ObjectPool<? extends Executor> executorPool;
    boolean fullStreamDecompression;
    long idleTimeoutMillis;
    private final List<ClientInterceptor> interceptors = new ArrayList();
    boolean lookUpServiceConfig;
    int maxHedgedAttempts;
    private int maxInboundMessageSize;
    int maxRetryAttempts;
    int maxTraceEvents;
    private NameResolver.Factory nameResolverFactory;
    final NameResolverRegistry nameResolverRegistry;
    ObjectPool<? extends Executor> offloadExecutorPool;
    long perRpcBufferLimit;
    @Nullable
    ProxyDetector proxyDetector;
    private boolean recordFinishedRpcs;
    private boolean recordRealTimeMetrics;
    private boolean recordStartedRpcs;
    long retryBufferSize;
    boolean retryEnabled;
    private boolean statsEnabled;
    final String target;
    boolean temporarilyDisableRetry;
    private boolean tracingEnabled;
    protected TransportTracer.Factory transportTracerFactory;
    @Nullable
    String userAgent;

    /* access modifiers changed from: protected */
    public abstract ClientTransportFactory buildTransportFactory();

    public static ManagedChannelBuilder<?> forAddress(String name, int port) {
        throw new UnsupportedOperationException("Subclass failed to hide static factory");
    }

    public static ManagedChannelBuilder<?> forTarget(String target2) {
        throw new UnsupportedOperationException("Subclass failed to hide static factory");
    }

    public T maxInboundMessageSize(int max) {
        Preconditions.checkArgument(max >= 0, "negative max");
        this.maxInboundMessageSize = max;
        return thisT();
    }

    /* access modifiers changed from: protected */
    public final int maxInboundMessageSize() {
        return this.maxInboundMessageSize;
    }

    protected AbstractManagedChannelImplBuilder(String target2) {
        ObjectPool<? extends Executor> objectPool = DEFAULT_EXECUTOR_POOL;
        this.executorPool = objectPool;
        this.offloadExecutorPool = objectPool;
        NameResolverRegistry defaultRegistry = NameResolverRegistry.getDefaultRegistry();
        this.nameResolverRegistry = defaultRegistry;
        this.nameResolverFactory = defaultRegistry.asFactory();
        this.defaultLbPolicy = GrpcUtil.DEFAULT_LB_POLICY;
        this.decompressorRegistry = DEFAULT_DECOMPRESSOR_REGISTRY;
        this.compressorRegistry = DEFAULT_COMPRESSOR_REGISTRY;
        this.idleTimeoutMillis = IDLE_MODE_DEFAULT_TIMEOUT_MILLIS;
        this.maxRetryAttempts = 5;
        this.maxHedgedAttempts = 5;
        this.retryBufferSize = DEFAULT_RETRY_BUFFER_SIZE_IN_BYTES;
        this.perRpcBufferLimit = 1048576;
        this.retryEnabled = false;
        this.channelz = InternalChannelz.instance();
        this.lookUpServiceConfig = true;
        this.transportTracerFactory = TransportTracer.getDefaultFactory();
        this.maxInboundMessageSize = 4194304;
        this.statsEnabled = true;
        this.recordStartedRpcs = true;
        this.recordFinishedRpcs = true;
        this.recordRealTimeMetrics = false;
        this.tracingEnabled = true;
        this.target = (String) Preconditions.checkNotNull(target2, "target");
        this.directServerAddress = null;
    }

    static String makeTargetStringForDirectAddress(SocketAddress address) {
        try {
            return new URI(DIRECT_ADDRESS_SCHEME, "", "/" + address, (String) null).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    protected AbstractManagedChannelImplBuilder(SocketAddress directServerAddress2, String authority) {
        ObjectPool<? extends Executor> objectPool = DEFAULT_EXECUTOR_POOL;
        this.executorPool = objectPool;
        this.offloadExecutorPool = objectPool;
        NameResolverRegistry defaultRegistry = NameResolverRegistry.getDefaultRegistry();
        this.nameResolverRegistry = defaultRegistry;
        this.nameResolverFactory = defaultRegistry.asFactory();
        this.defaultLbPolicy = GrpcUtil.DEFAULT_LB_POLICY;
        this.decompressorRegistry = DEFAULT_DECOMPRESSOR_REGISTRY;
        this.compressorRegistry = DEFAULT_COMPRESSOR_REGISTRY;
        this.idleTimeoutMillis = IDLE_MODE_DEFAULT_TIMEOUT_MILLIS;
        this.maxRetryAttempts = 5;
        this.maxHedgedAttempts = 5;
        this.retryBufferSize = DEFAULT_RETRY_BUFFER_SIZE_IN_BYTES;
        this.perRpcBufferLimit = 1048576;
        this.retryEnabled = false;
        this.channelz = InternalChannelz.instance();
        this.lookUpServiceConfig = true;
        this.transportTracerFactory = TransportTracer.getDefaultFactory();
        this.maxInboundMessageSize = 4194304;
        this.statsEnabled = true;
        this.recordStartedRpcs = true;
        this.recordFinishedRpcs = true;
        this.recordRealTimeMetrics = false;
        this.tracingEnabled = true;
        this.target = makeTargetStringForDirectAddress(directServerAddress2);
        this.directServerAddress = directServerAddress2;
        this.nameResolverFactory = new DirectAddressNameResolverFactory(directServerAddress2, authority);
    }

    public final T directExecutor() {
        return executor(MoreExecutors.directExecutor());
    }

    public final T executor(Executor executor) {
        if (executor != null) {
            this.executorPool = new FixedObjectPool(executor);
        } else {
            this.executorPool = DEFAULT_EXECUTOR_POOL;
        }
        return thisT();
    }

    public final T offloadExecutor(Executor executor) {
        if (executor != null) {
            this.offloadExecutorPool = new FixedObjectPool(executor);
        } else {
            this.offloadExecutorPool = DEFAULT_EXECUTOR_POOL;
        }
        return thisT();
    }

    public final T intercept(List<ClientInterceptor> interceptors2) {
        this.interceptors.addAll(interceptors2);
        return thisT();
    }

    public final T intercept(ClientInterceptor... interceptors2) {
        return intercept(Arrays.asList(interceptors2));
    }

    public final T nameResolverFactory(NameResolver.Factory resolverFactory) {
        Preconditions.checkState(this.directServerAddress == null, "directServerAddress is set (%s), which forbids the use of NameResolverFactory", (Object) this.directServerAddress);
        if (resolverFactory != null) {
            this.nameResolverFactory = resolverFactory;
        } else {
            this.nameResolverFactory = this.nameResolverRegistry.asFactory();
        }
        return thisT();
    }

    public final T defaultLoadBalancingPolicy(String policy) {
        boolean z = true;
        Preconditions.checkState(this.directServerAddress == null, "directServerAddress is set (%s), which forbids the use of load-balancing policy", (Object) this.directServerAddress);
        if (policy == null) {
            z = false;
        }
        Preconditions.checkArgument(z, "policy cannot be null");
        this.defaultLbPolicy = policy;
        return thisT();
    }

    public final T enableFullStreamDecompression() {
        this.fullStreamDecompression = true;
        return thisT();
    }

    public final T decompressorRegistry(DecompressorRegistry registry) {
        if (registry != null) {
            this.decompressorRegistry = registry;
        } else {
            this.decompressorRegistry = DEFAULT_DECOMPRESSOR_REGISTRY;
        }
        return thisT();
    }

    public final T compressorRegistry(CompressorRegistry registry) {
        if (registry != null) {
            this.compressorRegistry = registry;
        } else {
            this.compressorRegistry = DEFAULT_COMPRESSOR_REGISTRY;
        }
        return thisT();
    }

    public final T userAgent(@Nullable String userAgent2) {
        this.userAgent = userAgent2;
        return thisT();
    }

    public final T overrideAuthority(String authority) {
        this.authorityOverride = checkAuthority(authority);
        return thisT();
    }

    public final T idleTimeout(long value, TimeUnit unit) {
        Preconditions.checkArgument(value > 0, "idle timeout is %s, but must be positive", value);
        if (unit.toDays(value) >= IDLE_MODE_MAX_TIMEOUT_DAYS) {
            this.idleTimeoutMillis = -1;
        } else {
            this.idleTimeoutMillis = Math.max(unit.toMillis(value), IDLE_MODE_MIN_TIMEOUT_MILLIS);
        }
        return thisT();
    }

    public final T maxRetryAttempts(int maxRetryAttempts2) {
        this.maxRetryAttempts = maxRetryAttempts2;
        return thisT();
    }

    public final T maxHedgedAttempts(int maxHedgedAttempts2) {
        this.maxHedgedAttempts = maxHedgedAttempts2;
        return thisT();
    }

    public final T retryBufferSize(long bytes) {
        Preconditions.checkArgument(bytes > 0, "retry buffer size must be positive");
        this.retryBufferSize = bytes;
        return thisT();
    }

    public final T perRpcBufferLimit(long bytes) {
        Preconditions.checkArgument(bytes > 0, "per RPC buffer limit must be positive");
        this.perRpcBufferLimit = bytes;
        return thisT();
    }

    public final T disableRetry() {
        this.retryEnabled = false;
        return thisT();
    }

    public final T enableRetry() {
        this.retryEnabled = true;
        this.statsEnabled = false;
        this.tracingEnabled = false;
        return thisT();
    }

    public final T setBinaryLog(BinaryLog binlog2) {
        this.binlog = binlog2;
        return thisT();
    }

    public T maxTraceEvents(int maxTraceEvents2) {
        Preconditions.checkArgument(maxTraceEvents2 >= 0, "maxTraceEvents must be non-negative");
        this.maxTraceEvents = maxTraceEvents2;
        return thisT();
    }

    public T proxyDetector(@Nullable ProxyDetector proxyDetector2) {
        this.proxyDetector = proxyDetector2;
        return thisT();
    }

    public T defaultServiceConfig(@Nullable Map<String, ?> serviceConfig) {
        this.defaultServiceConfig = checkMapEntryTypes(serviceConfig);
        return thisT();
    }

    @Nullable
    private static Map<String, ?> checkMapEntryTypes(@Nullable Map<?, ?> map) {
        if (map == null) {
            return null;
        }
        Map<String, Object> parsedMap = new LinkedHashMap<>();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            Preconditions.checkArgument(entry.getKey() instanceof String, "The key of the entry '%s' is not of String type", (Object) entry);
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                parsedMap.put(key, (Object) null);
            } else if (value instanceof Map) {
                parsedMap.put(key, checkMapEntryTypes((Map) value));
            } else if (value instanceof List) {
                parsedMap.put(key, checkListEntryTypes((List) value));
            } else if (value instanceof String) {
                parsedMap.put(key, value);
            } else if (value instanceof Double) {
                parsedMap.put(key, value);
            } else if (value instanceof Boolean) {
                parsedMap.put(key, value);
            } else {
                throw new IllegalArgumentException("The value of the map entry '" + entry + "' is of type '" + value.getClass() + "', which is not supported");
            }
        }
        return Collections.unmodifiableMap(parsedMap);
    }

    private static List<?> checkListEntryTypes(List<?> list) {
        List<Object> parsedList = new ArrayList<>(list.size());
        for (Object value : list) {
            if (value == null) {
                parsedList.add((Object) null);
            } else if (value instanceof Map) {
                parsedList.add(checkMapEntryTypes((Map) value));
            } else if (value instanceof List) {
                parsedList.add(checkListEntryTypes((List) value));
            } else if (value instanceof String) {
                parsedList.add(value);
            } else if (value instanceof Double) {
                parsedList.add(value);
            } else if (value instanceof Boolean) {
                parsedList.add(value);
            } else {
                throw new IllegalArgumentException("The entry '" + value + "' is of type '" + value.getClass() + "', which is not supported");
            }
        }
        return Collections.unmodifiableList(parsedList);
    }

    public T disableServiceConfigLookUp() {
        this.lookUpServiceConfig = false;
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

    /* access modifiers changed from: package-private */
    public final long getIdleTimeoutMillis() {
        return this.idleTimeoutMillis;
    }

    /* access modifiers changed from: protected */
    public String checkAuthority(String authority) {
        return GrpcUtil.checkAuthority(authority);
    }

    public ManagedChannel build() {
        return new ManagedChannelOrphanWrapper(new ManagedChannelImpl(this, buildTransportFactory(), new ExponentialBackoffPolicy.Provider(), SharedResourcePool.forResource(GrpcUtil.SHARED_CHANNEL_EXECUTOR), GrpcUtil.STOPWATCH_SUPPLIER, getEffectiveInterceptors(), TimeProvider.SYSTEM_TIME_PROVIDER));
    }

    /* access modifiers changed from: package-private */
    public final List<ClientInterceptor> getEffectiveInterceptors() {
        List<ClientInterceptor> effectiveInterceptors = new ArrayList<>(this.interceptors);
        this.temporarilyDisableRetry = false;
        if (this.statsEnabled) {
            this.temporarilyDisableRetry = true;
            ClientInterceptor statsInterceptor = null;
            try {
                statsInterceptor = (ClientInterceptor) Class.forName("io.grpc.census.InternalCensusStatsAccessor").getDeclaredMethod("getClientInterceptor", new Class[]{Boolean.TYPE, Boolean.TYPE, Boolean.TYPE}).invoke((Object) null, new Object[]{Boolean.valueOf(this.recordStartedRpcs), Boolean.valueOf(this.recordFinishedRpcs), Boolean.valueOf(this.recordRealTimeMetrics)});
            } catch (ClassNotFoundException e) {
                log.log(Level.FINE, "Unable to apply census stats", e);
            } catch (NoSuchMethodException e2) {
                log.log(Level.FINE, "Unable to apply census stats", e2);
            } catch (IllegalAccessException e3) {
                log.log(Level.FINE, "Unable to apply census stats", e3);
            } catch (InvocationTargetException e4) {
                log.log(Level.FINE, "Unable to apply census stats", e4);
            }
            if (statsInterceptor != null) {
                effectiveInterceptors.add(0, statsInterceptor);
            }
        }
        if (this.tracingEnabled) {
            this.temporarilyDisableRetry = true;
            ClientInterceptor tracingInterceptor = null;
            try {
                tracingInterceptor = (ClientInterceptor) Class.forName("io.grpc.census.InternalCensusTracingAccessor").getDeclaredMethod("getClientInterceptor", new Class[0]).invoke((Object) null, new Object[0]);
            } catch (ClassNotFoundException e5) {
                log.log(Level.FINE, "Unable to apply census stats", e5);
            } catch (NoSuchMethodException e6) {
                log.log(Level.FINE, "Unable to apply census stats", e6);
            } catch (IllegalAccessException e7) {
                log.log(Level.FINE, "Unable to apply census stats", e7);
            } catch (InvocationTargetException e8) {
                log.log(Level.FINE, "Unable to apply census stats", e8);
            }
            if (tracingInterceptor != null) {
                effectiveInterceptors.add(0, tracingInterceptor);
            }
        }
        return effectiveInterceptors;
    }

    /* access modifiers changed from: protected */
    public int getDefaultPort() {
        return GrpcUtil.DEFAULT_PORT_SSL;
    }

    /* access modifiers changed from: package-private */
    public NameResolver.Factory getNameResolverFactory() {
        if (this.authorityOverride == null) {
            return this.nameResolverFactory;
        }
        return new OverrideAuthorityNameResolverFactory(this.nameResolverFactory, this.authorityOverride);
    }

    /* renamed from: io.grpc.internal.AbstractManagedChannelImplBuilder$DirectAddressNameResolverFactory */
    private static class DirectAddressNameResolverFactory extends NameResolver.Factory {
        final SocketAddress address;
        final String authority;

        DirectAddressNameResolverFactory(SocketAddress address2, String authority2) {
            this.address = address2;
            this.authority = authority2;
        }

        public NameResolver newNameResolver(URI notUsedUri, NameResolver.Args args) {
            return new NameResolver() {
                public String getServiceAuthority() {
                    return DirectAddressNameResolverFactory.this.authority;
                }

                public void start(NameResolver.Listener2 listener) {
                    listener.onResult(NameResolver.ResolutionResult.newBuilder().setAddresses(Collections.singletonList(new EquivalentAddressGroup(DirectAddressNameResolverFactory.this.address))).setAttributes(Attributes.EMPTY).build());
                }

                public void shutdown() {
                }
            };
        }

        public String getDefaultScheme() {
            return AbstractManagedChannelImplBuilder.DIRECT_ADDRESS_SCHEME;
        }
    }

    private T thisT() {
        return this;
    }

    /* access modifiers changed from: protected */
    public ObjectPool<? extends Executor> getOffloadExecutorPool() {
        return this.offloadExecutorPool;
    }
}
