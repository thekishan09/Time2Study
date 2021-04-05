package p003io.grpc.internal;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import java.lang.Thread;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import p003io.grpc.Attributes;
import p003io.grpc.CallOptions;
import p003io.grpc.Channel;
import p003io.grpc.ChannelLogger;
import p003io.grpc.ClientCall;
import p003io.grpc.ClientInterceptor;
import p003io.grpc.ClientInterceptors;
import p003io.grpc.ClientStreamTracer;
import p003io.grpc.CompressorRegistry;
import p003io.grpc.ConnectivityState;
import p003io.grpc.ConnectivityStateInfo;
import p003io.grpc.Context;
import p003io.grpc.DecompressorRegistry;
import p003io.grpc.EquivalentAddressGroup;
import p003io.grpc.InternalChannelz;
import p003io.grpc.InternalInstrumented;
import p003io.grpc.InternalLogId;
import p003io.grpc.InternalWithLogId;
import p003io.grpc.LoadBalancer;
import p003io.grpc.ManagedChannel;
import p003io.grpc.Metadata;
import p003io.grpc.MethodDescriptor;
import p003io.grpc.NameResolver;
import p003io.grpc.NameResolverRegistry;
import p003io.grpc.ProxyDetector;
import p003io.grpc.Status;
import p003io.grpc.SynchronizationContext;
import p003io.grpc.internal.AutoConfiguredLoadBalancerFactory;
import p003io.grpc.internal.BackoffPolicy;
import p003io.grpc.internal.CallTracer;
import p003io.grpc.internal.ClientCallImpl;
import p003io.grpc.internal.InternalSubchannel;
import p003io.grpc.internal.ManagedClientTransport;
import p003io.grpc.internal.RetriableStream;

/* renamed from: io.grpc.internal.ManagedChannelImpl */
final class ManagedChannelImpl extends ManagedChannel implements InternalInstrumented<InternalChannelz.ChannelStats> {
    /* access modifiers changed from: private */
    public static final ServiceConfigHolder EMPTY_SERVICE_CONFIG = new ServiceConfigHolder(Collections.emptyMap(), ManagedChannelServiceConfig.empty());
    static final long IDLE_TIMEOUT_MILLIS_DISABLE = -1;
    static final Status SHUTDOWN_NOW_STATUS = Status.UNAVAILABLE.withDescription("Channel shutdownNow invoked");
    static final Status SHUTDOWN_STATUS = Status.UNAVAILABLE.withDescription("Channel shutdown invoked");
    static final long SUBCHANNEL_SHUTDOWN_DELAY_SECONDS = 5;
    static final Status SUBCHANNEL_SHUTDOWN_STATUS = Status.UNAVAILABLE.withDescription("Subchannel shutdown invoked");
    static final Pattern URI_PATTERN = Pattern.compile("[a-zA-Z][a-zA-Z0-9+.-]*:/.*");
    static final Logger logger = Logger.getLogger(ManagedChannelImpl.class.getName());
    /* access modifiers changed from: private */
    public final BackoffPolicy.Provider backoffPolicyProvider;
    /* access modifiers changed from: private */
    public final ExecutorHolder balancerRpcExecutorHolder;
    /* access modifiers changed from: private */
    public final ObjectPool<? extends Executor> balancerRpcExecutorPool;
    /* access modifiers changed from: private */
    public final CallTracer.Factory callTracerFactory;
    /* access modifiers changed from: private */
    public final long channelBufferLimit;
    /* access modifiers changed from: private */
    public final RetriableStream.ChannelBufferMeter channelBufferUsed = new RetriableStream.ChannelBufferMeter();
    /* access modifiers changed from: private */
    public final CallTracer channelCallTracer;
    /* access modifiers changed from: private */
    public final ChannelLogger channelLogger;
    /* access modifiers changed from: private */
    public final ConnectivityStateManager channelStateManager = new ConnectivityStateManager();
    /* access modifiers changed from: private */
    public final ChannelTracer channelTracer;
    /* access modifiers changed from: private */
    public final InternalChannelz channelz;
    /* access modifiers changed from: private */
    public final CompressorRegistry compressorRegistry;
    /* access modifiers changed from: private */
    public final DecompressorRegistry decompressorRegistry;
    /* access modifiers changed from: private */
    @Nullable
    public final ServiceConfigHolder defaultServiceConfig;
    /* access modifiers changed from: private */
    public final DelayedClientTransport delayedTransport;
    private final ManagedClientTransport.Listener delayedTransportListener = new DelayedTransportListener();
    /* access modifiers changed from: private */
    public final Executor executor;
    private final ObjectPool<? extends Executor> executorPool;
    /* access modifiers changed from: private */
    public boolean fullStreamDecompression;
    private final long idleTimeoutMillis;
    private final Rescheduler idleTimer;
    final InUseStateAggregator<Object> inUseStateAggregator = new IdleModeStateAggregator();
    private final Channel interceptorChannel;
    /* access modifiers changed from: private */
    public ResolutionState lastResolutionState = ResolutionState.NO_RESOLUTION;
    /* access modifiers changed from: private */
    public ServiceConfigHolder lastServiceConfig = EMPTY_SERVICE_CONFIG;
    /* access modifiers changed from: private */
    @Nullable
    public LbHelperImpl lbHelper;
    private final AutoConfiguredLoadBalancerFactory loadBalancerFactory;
    private final InternalLogId logId;
    /* access modifiers changed from: private */
    public final boolean lookUpServiceConfig;
    /* access modifiers changed from: private */
    public final int maxTraceEvents;
    private NameResolver nameResolver;
    /* access modifiers changed from: private */
    public final NameResolver.Args nameResolverArgs;
    /* access modifiers changed from: private */
    @Nullable
    public BackoffPolicy nameResolverBackoffPolicy;
    /* access modifiers changed from: private */
    public final NameResolver.Factory nameResolverFactory;
    /* access modifiers changed from: private */
    public final NameResolverRegistry nameResolverRegistry;
    /* access modifiers changed from: private */
    public boolean nameResolverStarted;
    /* access modifiers changed from: private */
    public final ExecutorHolder offloadExecutorHolder;
    /* access modifiers changed from: private */
    public final Set<OobChannel> oobChannels = new HashSet(1, 0.75f);
    private boolean panicMode;
    /* access modifiers changed from: private */
    public final long perRpcBufferLimit;
    /* access modifiers changed from: private */
    public final boolean retryEnabled;
    /* access modifiers changed from: private */
    public final RestrictedScheduledExecutor scheduledExecutor;
    /* access modifiers changed from: private */
    @Nullable
    public SynchronizationContext.ScheduledHandle scheduledNameResolverRefresh;
    private final ServiceConfigInterceptor serviceConfigInterceptor;
    /* access modifiers changed from: private */
    public boolean serviceConfigUpdated = false;
    /* access modifiers changed from: private */
    public final AtomicBoolean shutdown = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public boolean shutdownNowed;
    /* access modifiers changed from: private */
    public final Supplier<Stopwatch> stopwatchSupplier;
    /* access modifiers changed from: private */
    @Nullable
    public volatile LoadBalancer.SubchannelPicker subchannelPicker;
    /* access modifiers changed from: private */
    public final Set<InternalSubchannel> subchannels = new HashSet(16, 0.75f);
    final SynchronizationContext syncContext = new SynchronizationContext(new Thread.UncaughtExceptionHandler() {
        public void uncaughtException(Thread t, Throwable e) {
            Logger logger = ManagedChannelImpl.logger;
            Level level = Level.SEVERE;
            logger.log(level, "[" + ManagedChannelImpl.this.getLogId() + "] Uncaught exception in the SynchronizationContext. Panic!", e);
            ManagedChannelImpl.this.panic(e);
        }
    });
    /* access modifiers changed from: private */
    public final String target;
    /* access modifiers changed from: private */
    public volatile boolean terminated;
    private final CountDownLatch terminatedLatch = new CountDownLatch(1);
    /* access modifiers changed from: private */
    public volatile boolean terminating;
    /* access modifiers changed from: private */
    public final TimeProvider timeProvider;
    /* access modifiers changed from: private */
    public final ClientTransportFactory transportFactory;
    /* access modifiers changed from: private */
    public final ClientCallImpl.ClientTransportProvider transportProvider = new ChannelTransportProvider();
    /* access modifiers changed from: private */
    public final UncommittedRetriableStreamsRegistry uncommittedRetriableStreamsRegistry = new UncommittedRetriableStreamsRegistry();
    /* access modifiers changed from: private */
    @Nullable
    public final String userAgent;

    /* renamed from: io.grpc.internal.ManagedChannelImpl$ResolutionState */
    enum ResolutionState {
        NO_RESOLUTION,
        SUCCESS,
        ERROR
    }

    /* access modifiers changed from: private */
    public void maybeShutdownNowSubchannels() {
        if (this.shutdownNowed) {
            for (InternalSubchannel subchannel : this.subchannels) {
                subchannel.shutdownNow(SHUTDOWN_NOW_STATUS);
            }
            for (OobChannel oobChannel : this.oobChannels) {
                oobChannel.getInternalSubchannel().shutdownNow(SHUTDOWN_NOW_STATUS);
            }
        }
    }

    public ListenableFuture<InternalChannelz.ChannelStats> getStats() {
        final SettableFuture<InternalChannelz.ChannelStats> ret = SettableFuture.create();
        this.syncContext.execute(new Runnable() {
            public void run() {
                InternalChannelz.ChannelStats.Builder builder = new InternalChannelz.ChannelStats.Builder();
                ManagedChannelImpl.this.channelCallTracer.updateBuilder(builder);
                ManagedChannelImpl.this.channelTracer.updateBuilder(builder);
                builder.setTarget(ManagedChannelImpl.this.target).setState(ManagedChannelImpl.this.channelStateManager.getState());
                List<InternalWithLogId> children = new ArrayList<>();
                children.addAll(ManagedChannelImpl.this.subchannels);
                children.addAll(ManagedChannelImpl.this.oobChannels);
                builder.setSubchannels(children);
                ret.set(builder.build());
            }
        });
        return ret;
    }

    public InternalLogId getLogId() {
        return this.logId;
    }

    /* renamed from: io.grpc.internal.ManagedChannelImpl$IdleModeTimer */
    private class IdleModeTimer implements Runnable {
        private IdleModeTimer() {
        }

        public void run() {
            ManagedChannelImpl.this.enterIdleMode();
        }
    }

    /* access modifiers changed from: private */
    public void shutdownNameResolverAndLoadBalancer(boolean channelIsActive) {
        this.syncContext.throwIfNotInThisSynchronizationContext();
        if (channelIsActive) {
            Preconditions.checkState(this.nameResolverStarted, "nameResolver is not started");
            Preconditions.checkState(this.lbHelper != null, "lbHelper is null");
        }
        if (this.nameResolver != null) {
            cancelNameResolverBackoff();
            this.nameResolver.shutdown();
            this.nameResolverStarted = false;
            if (channelIsActive) {
                this.nameResolver = getNameResolver(this.target, this.nameResolverFactory, this.nameResolverArgs);
            } else {
                this.nameResolver = null;
            }
        }
        LbHelperImpl lbHelperImpl = this.lbHelper;
        if (lbHelperImpl != null) {
            lbHelperImpl.f559lb.shutdown();
            this.lbHelper = null;
        }
        this.subchannelPicker = null;
    }

    /* access modifiers changed from: package-private */
    public void exitIdleMode() {
        this.syncContext.throwIfNotInThisSynchronizationContext();
        if (!this.shutdown.get() && !this.panicMode) {
            if (this.inUseStateAggregator.isInUse()) {
                cancelIdleTimer(false);
            } else {
                rescheduleIdleTimer();
            }
            if (this.lbHelper == null) {
                this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Exiting idle mode");
                LbHelperImpl lbHelper2 = new LbHelperImpl();
                lbHelper2.f559lb = this.loadBalancerFactory.newLoadBalancer(lbHelper2);
                this.lbHelper = lbHelper2;
                this.nameResolver.start((NameResolver.Listener2) new NameResolverListener(lbHelper2, this.nameResolver));
                this.nameResolverStarted = true;
            }
        }
    }

    /* access modifiers changed from: private */
    public void enterIdleMode() {
        shutdownNameResolverAndLoadBalancer(true);
        this.delayedTransport.reprocess((LoadBalancer.SubchannelPicker) null);
        this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Entering IDLE state");
        this.channelStateManager.gotoState(ConnectivityState.IDLE);
        if (this.inUseStateAggregator.isInUse()) {
            exitIdleMode();
        }
    }

    /* access modifiers changed from: private */
    public void cancelIdleTimer(boolean permanent) {
        this.idleTimer.cancel(permanent);
    }

    /* access modifiers changed from: private */
    public void rescheduleIdleTimer() {
        long j = this.idleTimeoutMillis;
        if (j != -1) {
            this.idleTimer.reschedule(j, TimeUnit.MILLISECONDS);
        }
    }

    /* renamed from: io.grpc.internal.ManagedChannelImpl$DelayedNameResolverRefresh */
    class DelayedNameResolverRefresh implements Runnable {
        DelayedNameResolverRefresh() {
        }

        public void run() {
            SynchronizationContext.ScheduledHandle unused = ManagedChannelImpl.this.scheduledNameResolverRefresh = null;
            ManagedChannelImpl.this.refreshNameResolution();
        }
    }

    private void cancelNameResolverBackoff() {
        this.syncContext.throwIfNotInThisSynchronizationContext();
        SynchronizationContext.ScheduledHandle scheduledHandle = this.scheduledNameResolverRefresh;
        if (scheduledHandle != null) {
            scheduledHandle.cancel();
            this.scheduledNameResolverRefresh = null;
            this.nameResolverBackoffPolicy = null;
        }
    }

    /* access modifiers changed from: private */
    public void refreshAndResetNameResolution() {
        this.syncContext.throwIfNotInThisSynchronizationContext();
        cancelNameResolverBackoff();
        refreshNameResolution();
    }

    /* access modifiers changed from: private */
    public void refreshNameResolution() {
        this.syncContext.throwIfNotInThisSynchronizationContext();
        if (this.nameResolverStarted) {
            this.nameResolver.refresh();
        }
    }

    /* renamed from: io.grpc.internal.ManagedChannelImpl$ChannelTransportProvider */
    private final class ChannelTransportProvider implements ClientCallImpl.ClientTransportProvider {
        private ChannelTransportProvider() {
        }

        public ClientTransport get(LoadBalancer.PickSubchannelArgs args) {
            LoadBalancer.SubchannelPicker pickerCopy = ManagedChannelImpl.this.subchannelPicker;
            if (ManagedChannelImpl.this.shutdown.get()) {
                return ManagedChannelImpl.this.delayedTransport;
            }
            if (pickerCopy == null) {
                ManagedChannelImpl.this.syncContext.execute(new Runnable() {
                    public void run() {
                        ManagedChannelImpl.this.exitIdleMode();
                    }
                });
                return ManagedChannelImpl.this.delayedTransport;
            }
            ClientTransport transport = GrpcUtil.getTransportFromPickResult(pickerCopy.pickSubchannel(args), args.getCallOptions().isWaitForReady());
            if (transport != null) {
                return transport;
            }
            return ManagedChannelImpl.this.delayedTransport;
        }

        public <ReqT> ClientStream newRetriableStream(MethodDescriptor<ReqT, ?> method, CallOptions callOptions, Metadata headers, Context context) {
            Preconditions.checkState(ManagedChannelImpl.this.retryEnabled, "retry should be enabled");
            return new RetriableStream<ReqT>(this, method, headers, callOptions, ManagedChannelImpl.this.lastServiceConfig.managedChannelServiceConfig.getRetryThrottling(), context) {
                final /* synthetic */ ChannelTransportProvider this$1;
                final /* synthetic */ CallOptions val$callOptions;
                final /* synthetic */ Context val$context;
                final /* synthetic */ Metadata val$headers;
                final /* synthetic */ MethodDescriptor val$method;
                final /* synthetic */ RetriableStream.Throttle val$throttle;

                {
                    ChannelTransportProvider channelTransportProvider = this$1;
                    CallOptions callOptions = r19;
                    this.this$1 = channelTransportProvider;
                    MethodDescriptor methodDescriptor = r17;
                    this.val$method = methodDescriptor;
                    Metadata metadata = r18;
                    this.val$headers = metadata;
                    this.val$callOptions = callOptions;
                    RetriableStream.Throttle throttle = r20;
                    this.val$throttle = throttle;
                    this.val$context = r21;
                }

                /* access modifiers changed from: package-private */
                public Status prestart() {
                    return ManagedChannelImpl.this.uncommittedRetriableStreamsRegistry.add(this);
                }

                /* access modifiers changed from: package-private */
                public void postCommit() {
                    ManagedChannelImpl.this.uncommittedRetriableStreamsRegistry.remove(this);
                }

                /* access modifiers changed from: package-private */
                public ClientStream newSubstream(ClientStreamTracer.Factory tracerFactory, Metadata newHeaders) {
                    CallOptions newOptions = this.val$callOptions.withStreamTracerFactory(tracerFactory);
                    ClientTransport transport = this.this$1.get(new PickSubchannelArgsImpl(this.val$method, newHeaders, newOptions));
                    Context origContext = this.val$context.attach();
                    try {
                        return transport.newStream(this.val$method, newHeaders, newOptions);
                    } finally {
                        this.val$context.detach(origContext);
                    }
                }
            };
        }
    }

    ManagedChannelImpl(AbstractManagedChannelImplBuilder<?> builder, ClientTransportFactory clientTransportFactory, BackoffPolicy.Provider backoffPolicyProvider2, ObjectPool<? extends Executor> balancerRpcExecutorPool2, Supplier<Stopwatch> stopwatchSupplier2, List<ClientInterceptor> interceptors, TimeProvider timeProvider2) {
        AbstractManagedChannelImplBuilder<?> abstractManagedChannelImplBuilder = builder;
        ObjectPool<? extends Executor> objectPool = balancerRpcExecutorPool2;
        final TimeProvider timeProvider3 = timeProvider2;
        String str = (String) Preconditions.checkNotNull(abstractManagedChannelImplBuilder.target, "target");
        this.target = str;
        this.logId = InternalLogId.allocate("Channel", str);
        this.timeProvider = (TimeProvider) Preconditions.checkNotNull(timeProvider3, "timeProvider");
        ObjectPool<? extends Executor> objectPool2 = (ObjectPool) Preconditions.checkNotNull(abstractManagedChannelImplBuilder.executorPool, "executorPool");
        this.executorPool = objectPool2;
        this.executor = (Executor) Preconditions.checkNotNull(objectPool2.getObject(), "executor");
        this.transportFactory = new CallCredentialsApplyingTransportFactory(clientTransportFactory, this.executor);
        this.scheduledExecutor = new RestrictedScheduledExecutor(this.transportFactory.getScheduledExecutorService());
        this.maxTraceEvents = abstractManagedChannelImplBuilder.maxTraceEvents;
        InternalLogId internalLogId = this.logId;
        int i = abstractManagedChannelImplBuilder.maxTraceEvents;
        long currentTimeNanos = timeProvider2.currentTimeNanos();
        this.channelTracer = new ChannelTracer(internalLogId, i, currentTimeNanos, "Channel for '" + this.target + "'");
        this.channelLogger = new ChannelLoggerImpl(this.channelTracer, timeProvider3);
        this.nameResolverFactory = builder.getNameResolverFactory();
        ProxyDetector proxyDetector = abstractManagedChannelImplBuilder.proxyDetector != null ? abstractManagedChannelImplBuilder.proxyDetector : GrpcUtil.DEFAULT_PROXY_DETECTOR;
        this.retryEnabled = abstractManagedChannelImplBuilder.retryEnabled && !abstractManagedChannelImplBuilder.temporarilyDisableRetry;
        this.loadBalancerFactory = new AutoConfiguredLoadBalancerFactory(abstractManagedChannelImplBuilder.defaultLbPolicy);
        this.offloadExecutorHolder = new ExecutorHolder((ObjectPool) Preconditions.checkNotNull(abstractManagedChannelImplBuilder.offloadExecutorPool, "offloadExecutorPool"));
        this.nameResolverRegistry = abstractManagedChannelImplBuilder.nameResolverRegistry;
        ScParser scParser = new ScParser(this.retryEnabled, abstractManagedChannelImplBuilder.maxRetryAttempts, abstractManagedChannelImplBuilder.maxHedgedAttempts, this.loadBalancerFactory, this.channelLogger);
        NameResolver.Args build = NameResolver.Args.newBuilder().setDefaultPort(builder.getDefaultPort()).setProxyDetector(proxyDetector).setSynchronizationContext(this.syncContext).setScheduledExecutorService(this.scheduledExecutor).setServiceConfigParser(scParser).setChannelLogger(this.channelLogger).setOffloadExecutor(new Executor() {
            public void execute(Runnable command) {
                ManagedChannelImpl.this.offloadExecutorHolder.getExecutor().execute(command);
            }
        }).build();
        this.nameResolverArgs = build;
        this.nameResolver = getNameResolver(this.target, this.nameResolverFactory, build);
        this.balancerRpcExecutorPool = (ObjectPool) Preconditions.checkNotNull(objectPool, "balancerRpcExecutorPool");
        this.balancerRpcExecutorHolder = new ExecutorHolder(objectPool);
        DelayedClientTransport delayedClientTransport = new DelayedClientTransport(this.executor, this.syncContext);
        this.delayedTransport = delayedClientTransport;
        delayedClientTransport.start(this.delayedTransportListener);
        this.backoffPolicyProvider = backoffPolicyProvider2;
        this.serviceConfigInterceptor = new ServiceConfigInterceptor(this.retryEnabled);
        if (abstractManagedChannelImplBuilder.defaultServiceConfig != null) {
            NameResolver.ConfigOrError parsedDefaultServiceConfig = scParser.parseServiceConfig(abstractManagedChannelImplBuilder.defaultServiceConfig);
            Preconditions.checkState(parsedDefaultServiceConfig.getError() == null, "Default config is invalid: %s", (Object) parsedDefaultServiceConfig.getError());
            ServiceConfigHolder serviceConfigHolder = new ServiceConfigHolder(abstractManagedChannelImplBuilder.defaultServiceConfig, (ManagedChannelServiceConfig) parsedDefaultServiceConfig.getConfig());
            this.defaultServiceConfig = serviceConfigHolder;
            this.lastServiceConfig = serviceConfigHolder;
        } else {
            this.defaultServiceConfig = null;
        }
        this.lookUpServiceConfig = abstractManagedChannelImplBuilder.lookUpServiceConfig;
        Channel channel = ClientInterceptors.intercept(new RealChannel(this.nameResolver.getServiceAuthority()), this.serviceConfigInterceptor);
        this.interceptorChannel = ClientInterceptors.intercept(abstractManagedChannelImplBuilder.binlog != null ? abstractManagedChannelImplBuilder.binlog.wrapChannel(channel) : channel, (List<? extends ClientInterceptor>) interceptors);
        this.stopwatchSupplier = (Supplier) Preconditions.checkNotNull(stopwatchSupplier2, "stopwatchSupplier");
        if (abstractManagedChannelImplBuilder.idleTimeoutMillis == -1) {
            this.idleTimeoutMillis = abstractManagedChannelImplBuilder.idleTimeoutMillis;
            ScParser scParser2 = scParser;
        } else {
            ScParser scParser3 = scParser;
            Preconditions.checkArgument(abstractManagedChannelImplBuilder.idleTimeoutMillis >= AbstractManagedChannelImplBuilder.IDLE_MODE_MIN_TIMEOUT_MILLIS, "invalid idleTimeoutMillis %s", abstractManagedChannelImplBuilder.idleTimeoutMillis);
            this.idleTimeoutMillis = abstractManagedChannelImplBuilder.idleTimeoutMillis;
        }
        this.idleTimer = new Rescheduler(new IdleModeTimer(), this.syncContext, this.transportFactory.getScheduledExecutorService(), stopwatchSupplier2.get());
        this.fullStreamDecompression = abstractManagedChannelImplBuilder.fullStreamDecompression;
        this.decompressorRegistry = (DecompressorRegistry) Preconditions.checkNotNull(abstractManagedChannelImplBuilder.decompressorRegistry, "decompressorRegistry");
        this.compressorRegistry = (CompressorRegistry) Preconditions.checkNotNull(abstractManagedChannelImplBuilder.compressorRegistry, "compressorRegistry");
        this.userAgent = abstractManagedChannelImplBuilder.userAgent;
        this.channelBufferLimit = abstractManagedChannelImplBuilder.retryBufferSize;
        this.perRpcBufferLimit = abstractManagedChannelImplBuilder.perRpcBufferLimit;
        AnonymousClass1ChannelCallTracerFactory r5 = new CallTracer.Factory() {
            public CallTracer create() {
                return new CallTracer(timeProvider3);
            }
        };
        this.callTracerFactory = r5;
        this.channelCallTracer = r5.create();
        InternalChannelz internalChannelz = (InternalChannelz) Preconditions.checkNotNull(abstractManagedChannelImplBuilder.channelz);
        this.channelz = internalChannelz;
        internalChannelz.addRootChannel(this);
        if (!this.lookUpServiceConfig) {
            if (this.defaultServiceConfig != null) {
                this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Service config look-up disabled, using default service config");
            }
            handleServiceConfigUpdate();
        }
    }

    /* access modifiers changed from: private */
    public void handleServiceConfigUpdate() {
        this.serviceConfigUpdated = true;
        this.serviceConfigInterceptor.handleUpdate(this.lastServiceConfig.managedChannelServiceConfig);
    }

    static NameResolver getNameResolver(String target2, NameResolver.Factory nameResolverFactory2, NameResolver.Args nameResolverArgs2) {
        NameResolver resolver;
        URI targetUri = null;
        StringBuilder uriSyntaxErrors = new StringBuilder();
        try {
            targetUri = new URI(target2);
        } catch (URISyntaxException e) {
            uriSyntaxErrors.append(e.getMessage());
        }
        if (targetUri != null && (resolver = nameResolverFactory2.newNameResolver(targetUri, nameResolverArgs2)) != null) {
            return resolver;
        }
        String str = "";
        if (!URI_PATTERN.matcher(target2).matches()) {
            try {
                NameResolver resolver2 = nameResolverFactory2.newNameResolver(new URI(nameResolverFactory2.getDefaultScheme(), str, "/" + target2, (String) null), nameResolverArgs2);
                if (resolver2 != null) {
                    return resolver2;
                }
            } catch (URISyntaxException e2) {
                throw new IllegalArgumentException(e2);
            }
        }
        Object[] objArr = new Object[2];
        objArr[0] = target2;
        if (uriSyntaxErrors.length() > 0) {
            str = " (" + uriSyntaxErrors + ")";
        }
        objArr[1] = str;
        throw new IllegalArgumentException(String.format("cannot find a NameResolver for %s%s", objArr));
    }

    public ManagedChannelImpl shutdown() {
        this.channelLogger.log(ChannelLogger.ChannelLogLevel.DEBUG, "shutdown() called");
        if (!this.shutdown.compareAndSet(false, true)) {
            return this;
        }
        this.syncContext.executeLater(new Runnable() {
            public void run() {
                ManagedChannelImpl.this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Entering SHUTDOWN state");
                ManagedChannelImpl.this.channelStateManager.gotoState(ConnectivityState.SHUTDOWN);
            }
        });
        this.uncommittedRetriableStreamsRegistry.onShutdown(SHUTDOWN_STATUS);
        this.syncContext.execute(new Runnable() {
            public void run() {
                ManagedChannelImpl.this.cancelIdleTimer(true);
            }
        });
        return this;
    }

    public ManagedChannelImpl shutdownNow() {
        this.channelLogger.log(ChannelLogger.ChannelLogLevel.DEBUG, "shutdownNow() called");
        shutdown();
        this.uncommittedRetriableStreamsRegistry.onShutdownNow(SHUTDOWN_NOW_STATUS);
        this.syncContext.execute(new Runnable() {
            public void run() {
                if (!ManagedChannelImpl.this.shutdownNowed) {
                    boolean unused = ManagedChannelImpl.this.shutdownNowed = true;
                    ManagedChannelImpl.this.maybeShutdownNowSubchannels();
                }
            }
        });
        return this;
    }

    /* access modifiers changed from: package-private */
    public void panic(final Throwable t) {
        if (!this.panicMode) {
            this.panicMode = true;
            cancelIdleTimer(true);
            shutdownNameResolverAndLoadBalancer(false);
            updateSubchannelPicker(new LoadBalancer.SubchannelPicker() {
                private final LoadBalancer.PickResult panicPickResult = LoadBalancer.PickResult.withDrop(Status.INTERNAL.withDescription("Panic! This is a bug!").withCause(t));

                public LoadBalancer.PickResult pickSubchannel(LoadBalancer.PickSubchannelArgs args) {
                    return this.panicPickResult;
                }

                public String toString() {
                    return MoreObjects.toStringHelper((Class<?>) AnonymousClass1PanicSubchannelPicker.class).add("panicPickResult", (Object) this.panicPickResult).toString();
                }
            });
            this.channelLogger.log(ChannelLogger.ChannelLogLevel.ERROR, "PANIC! Entering TRANSIENT_FAILURE");
            this.channelStateManager.gotoState(ConnectivityState.TRANSIENT_FAILURE);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isInPanicMode() {
        return this.panicMode;
    }

    /* access modifiers changed from: private */
    public void updateSubchannelPicker(LoadBalancer.SubchannelPicker newPicker) {
        this.subchannelPicker = newPicker;
        this.delayedTransport.reprocess(newPicker);
    }

    public boolean isShutdown() {
        return this.shutdown.get();
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return this.terminatedLatch.await(timeout, unit);
    }

    public boolean isTerminated() {
        return this.terminated;
    }

    public <ReqT, RespT> ClientCall<ReqT, RespT> newCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions) {
        return this.interceptorChannel.newCall(method, callOptions);
    }

    public String authority() {
        return this.interceptorChannel.authority();
    }

    /* access modifiers changed from: private */
    public Executor getCallExecutor(CallOptions callOptions) {
        Executor executor2 = callOptions.getExecutor();
        if (executor2 == null) {
            return this.executor;
        }
        return executor2;
    }

    /* renamed from: io.grpc.internal.ManagedChannelImpl$RealChannel */
    private class RealChannel extends Channel {
        private final String authority;

        private RealChannel(String authority2) {
            this.authority = (String) Preconditions.checkNotNull(authority2, "authority");
        }

        public <ReqT, RespT> ClientCall<ReqT, RespT> newCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions) {
            return new ClientCallImpl(method, ManagedChannelImpl.this.getCallExecutor(callOptions), callOptions, ManagedChannelImpl.this.transportProvider, ManagedChannelImpl.this.terminated ? null : ManagedChannelImpl.this.transportFactory.getScheduledExecutorService(), ManagedChannelImpl.this.channelCallTracer, ManagedChannelImpl.this.retryEnabled).setFullStreamDecompression(ManagedChannelImpl.this.fullStreamDecompression).setDecompressorRegistry(ManagedChannelImpl.this.decompressorRegistry).setCompressorRegistry(ManagedChannelImpl.this.compressorRegistry);
        }

        public String authority() {
            return this.authority;
        }
    }

    /* access modifiers changed from: private */
    public void maybeTerminateChannel() {
        if (!this.terminated && this.shutdown.get() && this.subchannels.isEmpty() && this.oobChannels.isEmpty()) {
            this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Terminated");
            this.channelz.removeRootChannel(this);
            this.executorPool.returnObject(this.executor);
            this.balancerRpcExecutorHolder.release();
            this.offloadExecutorHolder.release();
            this.transportFactory.close();
            this.terminated = true;
            this.terminatedLatch.countDown();
        }
    }

    /* access modifiers changed from: private */
    public void handleInternalSubchannelState(ConnectivityStateInfo newState) {
        if (newState.getState() == ConnectivityState.TRANSIENT_FAILURE || newState.getState() == ConnectivityState.IDLE) {
            refreshAndResetNameResolution();
        }
    }

    public ConnectivityState getState(boolean requestConnection) {
        ConnectivityState savedChannelState = this.channelStateManager.getState();
        if (requestConnection && savedChannelState == ConnectivityState.IDLE) {
            this.syncContext.execute(new Runnable() {
                public void run() {
                    ManagedChannelImpl.this.exitIdleMode();
                    if (ManagedChannelImpl.this.subchannelPicker != null) {
                        ManagedChannelImpl.this.subchannelPicker.requestConnection();
                    }
                    if (ManagedChannelImpl.this.lbHelper != null) {
                        ManagedChannelImpl.this.lbHelper.f559lb.requestConnection();
                    }
                }
            });
        }
        return savedChannelState;
    }

    public void notifyWhenStateChanged(final ConnectivityState source, final Runnable callback) {
        this.syncContext.execute(new Runnable() {
            public void run() {
                ManagedChannelImpl.this.channelStateManager.notifyWhenStateChanged(callback, ManagedChannelImpl.this.executor, source);
            }
        });
    }

    public void resetConnectBackoff() {
        this.syncContext.execute(new Runnable() {
            public void run() {
                if (!ManagedChannelImpl.this.shutdown.get()) {
                    if (ManagedChannelImpl.this.scheduledNameResolverRefresh != null && ManagedChannelImpl.this.scheduledNameResolverRefresh.isPending()) {
                        Preconditions.checkState(ManagedChannelImpl.this.nameResolverStarted, "name resolver must be started");
                        ManagedChannelImpl.this.refreshAndResetNameResolution();
                    }
                    for (InternalSubchannel subchannel : ManagedChannelImpl.this.subchannels) {
                        subchannel.resetConnectBackoff();
                    }
                    for (OobChannel oobChannel : ManagedChannelImpl.this.oobChannels) {
                        oobChannel.resetConnectBackoff();
                    }
                }
            }
        });
    }

    public void enterIdle() {
        this.syncContext.execute(new Runnable() {
            public void run() {
                if (!ManagedChannelImpl.this.shutdown.get() && ManagedChannelImpl.this.lbHelper != null) {
                    ManagedChannelImpl.this.cancelIdleTimer(false);
                    ManagedChannelImpl.this.enterIdleMode();
                }
            }
        });
    }

    /* renamed from: io.grpc.internal.ManagedChannelImpl$UncommittedRetriableStreamsRegistry */
    private final class UncommittedRetriableStreamsRegistry {
        final Object lock;
        Status shutdownStatus;
        Collection<ClientStream> uncommittedRetriableStreams;

        private UncommittedRetriableStreamsRegistry() {
            this.lock = new Object();
            this.uncommittedRetriableStreams = new HashSet();
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0016, code lost:
            if (r0 == false) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0018, code lost:
            p003io.grpc.internal.ManagedChannelImpl.access$1500(r3.this$0).shutdown(r4);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onShutdown(p003io.grpc.Status r4) {
            /*
                r3 = this;
                r0 = 0
                java.lang.Object r1 = r3.lock
                monitor-enter(r1)
                io.grpc.Status r2 = r3.shutdownStatus     // Catch:{ all -> 0x0022 }
                if (r2 == 0) goto L_0x000a
                monitor-exit(r1)     // Catch:{ all -> 0x0022 }
                return
            L_0x000a:
                r3.shutdownStatus = r4     // Catch:{ all -> 0x0022 }
                java.util.Collection<io.grpc.internal.ClientStream> r2 = r3.uncommittedRetriableStreams     // Catch:{ all -> 0x0022 }
                boolean r2 = r2.isEmpty()     // Catch:{ all -> 0x0022 }
                if (r2 == 0) goto L_0x0015
                r0 = 1
            L_0x0015:
                monitor-exit(r1)     // Catch:{ all -> 0x0022 }
                if (r0 == 0) goto L_0x0021
                io.grpc.internal.ManagedChannelImpl r1 = p003io.grpc.internal.ManagedChannelImpl.this
                io.grpc.internal.DelayedClientTransport r1 = r1.delayedTransport
                r1.shutdown(r4)
            L_0x0021:
                return
            L_0x0022:
                r2 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x0022 }
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: p003io.grpc.internal.ManagedChannelImpl.UncommittedRetriableStreamsRegistry.onShutdown(io.grpc.Status):void");
        }

        /* access modifiers changed from: package-private */
        public void onShutdownNow(Status reason) {
            Collection<ClientStream> streams;
            onShutdown(reason);
            synchronized (this.lock) {
                streams = new ArrayList<>(this.uncommittedRetriableStreams);
            }
            for (ClientStream stream : streams) {
                stream.cancel(reason);
            }
            ManagedChannelImpl.this.delayedTransport.shutdownNow(reason);
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public Status add(RetriableStream<?> retriableStream) {
            synchronized (this.lock) {
                if (this.shutdownStatus != null) {
                    Status status = this.shutdownStatus;
                    return status;
                }
                this.uncommittedRetriableStreams.add(retriableStream);
                return null;
            }
        }

        /* access modifiers changed from: package-private */
        public void remove(RetriableStream<?> retriableStream) {
            Status shutdownStatusCopy = null;
            synchronized (this.lock) {
                this.uncommittedRetriableStreams.remove(retriableStream);
                if (this.uncommittedRetriableStreams.isEmpty()) {
                    shutdownStatusCopy = this.shutdownStatus;
                    this.uncommittedRetriableStreams = new HashSet();
                }
            }
            if (shutdownStatusCopy != null) {
                ManagedChannelImpl.this.delayedTransport.shutdown(shutdownStatusCopy);
            }
        }
    }

    /* renamed from: io.grpc.internal.ManagedChannelImpl$LbHelperImpl */
    private class LbHelperImpl extends LoadBalancer.Helper {

        /* renamed from: lb */
        AutoConfiguredLoadBalancerFactory.AutoConfiguredLoadBalancer f559lb;

        private LbHelperImpl() {
        }

        @Deprecated
        public AbstractSubchannel createSubchannel(List<EquivalentAddressGroup> addressGroups, Attributes attrs) {
            ManagedChannelImpl.this.logWarningIfNotInSyncContext("createSubchannel()");
            Preconditions.checkNotNull(addressGroups, "addressGroups");
            Preconditions.checkNotNull(attrs, "attrs");
            final SubchannelImpl subchannel = createSubchannelInternal(LoadBalancer.CreateSubchannelArgs.newBuilder().setAddresses(addressGroups).setAttributes(attrs).build());
            subchannel.internalStart(new LoadBalancer.SubchannelStateListener() {
                public void onSubchannelState(ConnectivityStateInfo newState) {
                    LbHelperImpl lbHelperImpl = LbHelperImpl.this;
                    if (lbHelperImpl == ManagedChannelImpl.this.lbHelper) {
                        LbHelperImpl.this.f559lb.handleSubchannelState(subchannel, newState);
                    }
                }
            });
            return subchannel;
        }

        public AbstractSubchannel createSubchannel(LoadBalancer.CreateSubchannelArgs args) {
            ManagedChannelImpl.this.syncContext.throwIfNotInThisSynchronizationContext();
            return createSubchannelInternal(args);
        }

        private SubchannelImpl createSubchannelInternal(LoadBalancer.CreateSubchannelArgs args) {
            Preconditions.checkState(!ManagedChannelImpl.this.terminated, "Channel is terminated");
            return new SubchannelImpl(args, this);
        }

        public void updateBalancingState(final ConnectivityState newState, final LoadBalancer.SubchannelPicker newPicker) {
            Preconditions.checkNotNull(newState, "newState");
            Preconditions.checkNotNull(newPicker, "newPicker");
            ManagedChannelImpl.this.logWarningIfNotInSyncContext("updateBalancingState()");
            ManagedChannelImpl.this.syncContext.execute(new Runnable() {
                public void run() {
                    LbHelperImpl lbHelperImpl = LbHelperImpl.this;
                    if (lbHelperImpl == ManagedChannelImpl.this.lbHelper) {
                        ManagedChannelImpl.this.updateSubchannelPicker(newPicker);
                        if (newState != ConnectivityState.SHUTDOWN) {
                            ManagedChannelImpl.this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Entering {0} state with picker: {1}", newState, newPicker);
                            ManagedChannelImpl.this.channelStateManager.gotoState(newState);
                        }
                    }
                }
            });
        }

        public void refreshNameResolution() {
            ManagedChannelImpl.this.logWarningIfNotInSyncContext("refreshNameResolution()");
            ManagedChannelImpl.this.syncContext.execute(new Runnable() {
                public void run() {
                    ManagedChannelImpl.this.refreshAndResetNameResolution();
                }
            });
        }

        @Deprecated
        public void updateSubchannelAddresses(LoadBalancer.Subchannel subchannel, List<EquivalentAddressGroup> addrs) {
            Preconditions.checkArgument(subchannel instanceof SubchannelImpl, "subchannel must have been returned from createSubchannel");
            ManagedChannelImpl.this.logWarningIfNotInSyncContext("updateSubchannelAddresses()");
            ((InternalSubchannel) subchannel.getInternalSubchannel()).updateAddresses(addrs);
        }

        public ManagedChannel createOobChannel(EquivalentAddressGroup addressGroup, String authority) {
            EquivalentAddressGroup equivalentAddressGroup = addressGroup;
            Preconditions.checkState(!ManagedChannelImpl.this.terminated, "Channel is terminated");
            long oobChannelCreationTime = ManagedChannelImpl.this.timeProvider.currentTimeNanos();
            InternalLogId oobLogId = InternalLogId.allocate("OobChannel", (String) null);
            InternalLogId subchannelLogId = InternalLogId.allocate("Subchannel-OOB", authority);
            int access$4600 = ManagedChannelImpl.this.maxTraceEvents;
            long j = oobChannelCreationTime;
            ChannelTracer oobChannelTracer = new ChannelTracer(oobLogId, access$4600, j, "OobChannel for " + equivalentAddressGroup);
            String str = authority;
            final OobChannel oobChannel = new OobChannel(str, ManagedChannelImpl.this.balancerRpcExecutorPool, ManagedChannelImpl.this.transportFactory.getScheduledExecutorService(), ManagedChannelImpl.this.syncContext, ManagedChannelImpl.this.callTracerFactory.create(), oobChannelTracer, ManagedChannelImpl.this.channelz, ManagedChannelImpl.this.timeProvider);
            ManagedChannelImpl.this.channelTracer.reportEvent(new InternalChannelz.ChannelTrace.Event.Builder().setDescription("Child OobChannel created").setSeverity(InternalChannelz.ChannelTrace.Event.Severity.CT_INFO).setTimestampNanos(oobChannelCreationTime).setChannelRef(oobChannel).build());
            int access$46002 = ManagedChannelImpl.this.maxTraceEvents;
            ChannelTracer oobChannelTracer2 = oobChannelTracer;
            ChannelTracer subchannelTracer = new ChannelTracer(subchannelLogId, access$46002, j, "Subchannel for " + equivalentAddressGroup);
            List singletonList = Collections.singletonList(addressGroup);
            String access$5200 = ManagedChannelImpl.this.userAgent;
            BackoffPolicy.Provider access$5300 = ManagedChannelImpl.this.backoffPolicyProvider;
            ClientTransportFactory access$2200 = ManagedChannelImpl.this.transportFactory;
            ScheduledExecutorService scheduledExecutorService = ManagedChannelImpl.this.transportFactory.getScheduledExecutorService();
            Supplier access$5400 = ManagedChannelImpl.this.stopwatchSupplier;
            SynchronizationContext synchronizationContext = ManagedChannelImpl.this.syncContext;
            AnonymousClass1ManagedOobChannelCallback r8 = new InternalSubchannel.Callback() {
                /* access modifiers changed from: package-private */
                public void onTerminated(InternalSubchannel is) {
                    ManagedChannelImpl.this.oobChannels.remove(oobChannel);
                    ManagedChannelImpl.this.channelz.removeSubchannel(is);
                    oobChannel.handleSubchannelTerminated();
                    ManagedChannelImpl.this.maybeTerminateChannel();
                }

                /* access modifiers changed from: package-private */
                public void onStateChange(InternalSubchannel is, ConnectivityStateInfo newState) {
                    ManagedChannelImpl.this.handleInternalSubchannelState(newState);
                    oobChannel.handleSubchannelStateChange(newState);
                }
            };
            String str2 = authority;
            ChannelTracer oobChannelTracer3 = oobChannelTracer2;
            String str3 = access$5200;
            final OobChannel oobChannel2 = oobChannel;
            BackoffPolicy.Provider provider = access$5300;
            SynchronizationContext synchronizationContext2 = synchronizationContext;
            AnonymousClass1ManagedOobChannelCallback r20 = r8;
            InternalSubchannel internalSubchannel = new InternalSubchannel(singletonList, str2, str3, provider, access$2200, scheduledExecutorService, access$5400, synchronizationContext2, r20, ManagedChannelImpl.this.channelz, ManagedChannelImpl.this.callTracerFactory.create(), subchannelTracer, subchannelLogId, new ChannelLoggerImpl(subchannelTracer, ManagedChannelImpl.this.timeProvider));
            oobChannelTracer3.reportEvent(new InternalChannelz.ChannelTrace.Event.Builder().setDescription("Child Subchannel created").setSeverity(InternalChannelz.ChannelTrace.Event.Severity.CT_INFO).setTimestampNanos(oobChannelCreationTime).setSubchannelRef(internalSubchannel).build());
            ManagedChannelImpl.this.channelz.addSubchannel(oobChannel2);
            ManagedChannelImpl.this.channelz.addSubchannel(internalSubchannel);
            oobChannel2.setSubchannel(internalSubchannel);
            ManagedChannelImpl.this.syncContext.execute(new Runnable() {
                public void run() {
                    if (ManagedChannelImpl.this.terminating) {
                        oobChannel2.shutdown();
                    }
                    if (!ManagedChannelImpl.this.terminated) {
                        ManagedChannelImpl.this.oobChannels.add(oobChannel2);
                    }
                }
            });
            return oobChannel2;
        }

        public void updateOobChannelAddresses(ManagedChannel channel, EquivalentAddressGroup eag) {
            Preconditions.checkArgument(channel instanceof OobChannel, "channel must have been returned from createOobChannel");
            ((OobChannel) channel).updateAddresses(eag);
        }

        public String getAuthority() {
            return ManagedChannelImpl.this.authority();
        }

        @Deprecated
        public NameResolver.Factory getNameResolverFactory() {
            return ManagedChannelImpl.this.nameResolverFactory;
        }

        public SynchronizationContext getSynchronizationContext() {
            return ManagedChannelImpl.this.syncContext;
        }

        public ScheduledExecutorService getScheduledExecutorService() {
            return ManagedChannelImpl.this.scheduledExecutor;
        }

        public ChannelLogger getChannelLogger() {
            return ManagedChannelImpl.this.channelLogger;
        }

        public NameResolver.Args getNameResolverArgs() {
            return ManagedChannelImpl.this.nameResolverArgs;
        }

        public NameResolverRegistry getNameResolverRegistry() {
            return ManagedChannelImpl.this.nameResolverRegistry;
        }
    }

    /* renamed from: io.grpc.internal.ManagedChannelImpl$NameResolverListener */
    private final class NameResolverListener extends NameResolver.Listener2 {
        final LbHelperImpl helper;
        final NameResolver resolver;

        NameResolverListener(LbHelperImpl helperImpl, NameResolver resolver2) {
            this.helper = (LbHelperImpl) Preconditions.checkNotNull(helperImpl, "helperImpl");
            this.resolver = (NameResolver) Preconditions.checkNotNull(resolver2, "resolver");
        }

        public void onResult(final NameResolver.ResolutionResult resolutionResult) {
            ManagedChannelImpl.this.syncContext.execute(new Runnable() {
                public void run() {
                    ServiceConfigHolder effectiveServiceConfig;
                    ServiceConfigHolder effectiveServiceConfig2;
                    List<EquivalentAddressGroup> servers = resolutionResult.getAddresses();
                    Attributes attrs = resolutionResult.getAttributes();
                    ManagedChannelImpl.this.channelLogger.log(ChannelLogger.ChannelLogLevel.DEBUG, "Resolved address: {0}, config={1}", servers, attrs);
                    ResolutionState lastResolutionStateCopy = ManagedChannelImpl.this.lastResolutionState;
                    if (ManagedChannelImpl.this.lastResolutionState != ResolutionState.SUCCESS) {
                        ManagedChannelImpl.this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Address resolved: {0}", servers);
                        ResolutionState unused = ManagedChannelImpl.this.lastResolutionState = ResolutionState.SUCCESS;
                    }
                    ServiceConfigHolder serviceConfigHolder = null;
                    BackoffPolicy unused2 = ManagedChannelImpl.this.nameResolverBackoffPolicy = null;
                    NameResolver.ConfigOrError configOrError = resolutionResult.getServiceConfig();
                    ServiceConfigHolder validServiceConfig = null;
                    Status serviceConfigError = null;
                    if (configOrError != null) {
                        Map<String, ?> rawServiceConfig = (Map) resolutionResult.getAttributes().get(GrpcAttributes.NAME_RESOLVER_SERVICE_CONFIG);
                        if (configOrError.getConfig() != null) {
                            serviceConfigHolder = new ServiceConfigHolder(rawServiceConfig, (ManagedChannelServiceConfig) configOrError.getConfig());
                        }
                        validServiceConfig = serviceConfigHolder;
                        serviceConfigError = configOrError.getError();
                    }
                    if (!ManagedChannelImpl.this.lookUpServiceConfig) {
                        if (validServiceConfig != null) {
                            ManagedChannelImpl.this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Service config from name resolver discarded by channel settings");
                        }
                        effectiveServiceConfig = ManagedChannelImpl.this.defaultServiceConfig == null ? ManagedChannelImpl.EMPTY_SERVICE_CONFIG : ManagedChannelImpl.this.defaultServiceConfig;
                        attrs = attrs.toBuilder().discard(GrpcAttributes.NAME_RESOLVER_SERVICE_CONFIG).build();
                    } else {
                        if (validServiceConfig != null) {
                            effectiveServiceConfig2 = validServiceConfig;
                        } else if (ManagedChannelImpl.this.defaultServiceConfig != null) {
                            effectiveServiceConfig2 = ManagedChannelImpl.this.defaultServiceConfig;
                            ManagedChannelImpl.this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Received no service config, using default service config");
                        } else if (serviceConfigError == null) {
                            effectiveServiceConfig2 = ManagedChannelImpl.EMPTY_SERVICE_CONFIG;
                        } else if (!ManagedChannelImpl.this.serviceConfigUpdated) {
                            ManagedChannelImpl.this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Fallback to error due to invalid first service config without default config");
                            NameResolverListener.this.onError(configOrError.getError());
                            return;
                        } else {
                            effectiveServiceConfig2 = ManagedChannelImpl.this.lastServiceConfig;
                        }
                        if (!effectiveServiceConfig.equals(ManagedChannelImpl.this.lastServiceConfig)) {
                            ChannelLogger access$2900 = ManagedChannelImpl.this.channelLogger;
                            ChannelLogger.ChannelLogLevel channelLogLevel = ChannelLogger.ChannelLogLevel.INFO;
                            Object[] objArr = new Object[1];
                            objArr[0] = effectiveServiceConfig == ManagedChannelImpl.EMPTY_SERVICE_CONFIG ? " to empty" : "";
                            access$2900.log(channelLogLevel, "Service config changed{0}", objArr);
                            ServiceConfigHolder unused3 = ManagedChannelImpl.this.lastServiceConfig = effectiveServiceConfig;
                        }
                        try {
                            ManagedChannelImpl.this.handleServiceConfigUpdate();
                        } catch (RuntimeException re) {
                            Logger logger = ManagedChannelImpl.logger;
                            Level level = Level.WARNING;
                            logger.log(level, "[" + ManagedChannelImpl.this.getLogId() + "] Unexpected exception from parsing service config", re);
                        }
                    }
                    if (NameResolverListener.this.helper == ManagedChannelImpl.this.lbHelper) {
                        Attributes effectiveAttrs = attrs;
                        if (effectiveServiceConfig != validServiceConfig) {
                            effectiveAttrs = attrs.toBuilder().set(GrpcAttributes.NAME_RESOLVER_SERVICE_CONFIG, effectiveServiceConfig.rawServiceConfig).build();
                        }
                        Status handleResult = NameResolverListener.this.helper.f559lb.tryHandleResolvedAddresses(LoadBalancer.ResolvedAddresses.newBuilder().setAddresses(servers).setAttributes(effectiveAttrs).setLoadBalancingPolicyConfig(effectiveServiceConfig.managedChannelServiceConfig.getLoadBalancingConfig()).build());
                        if (handleResult.isOk()) {
                            return;
                        }
                        if (!servers.isEmpty() || lastResolutionStateCopy != ResolutionState.SUCCESS) {
                            NameResolverListener nameResolverListener = NameResolverListener.this;
                            nameResolverListener.handleErrorInSyncContext(handleResult.augmentDescription(NameResolverListener.this.resolver + " was used"));
                            return;
                        }
                        NameResolverListener.this.scheduleExponentialBackOffInSyncContext();
                    }
                }
            });
        }

        public void onError(final Status error) {
            Preconditions.checkArgument(!error.isOk(), "the error status must not be OK");
            ManagedChannelImpl.this.syncContext.execute(new Runnable() {
                public void run() {
                    NameResolverListener.this.handleErrorInSyncContext(error);
                }
            });
        }

        /* access modifiers changed from: private */
        public void handleErrorInSyncContext(Status error) {
            ManagedChannelImpl.logger.log(Level.WARNING, "[{0}] Failed to resolve name. status={1}", new Object[]{ManagedChannelImpl.this.getLogId(), error});
            if (ManagedChannelImpl.this.lastResolutionState != ResolutionState.ERROR) {
                ManagedChannelImpl.this.channelLogger.log(ChannelLogger.ChannelLogLevel.WARNING, "Failed to resolve name: {0}", error);
                ResolutionState unused = ManagedChannelImpl.this.lastResolutionState = ResolutionState.ERROR;
            }
            if (this.helper == ManagedChannelImpl.this.lbHelper) {
                this.helper.f559lb.handleNameResolutionError(error);
                scheduleExponentialBackOffInSyncContext();
            }
        }

        /* access modifiers changed from: private */
        public void scheduleExponentialBackOffInSyncContext() {
            if (ManagedChannelImpl.this.scheduledNameResolverRefresh == null || !ManagedChannelImpl.this.scheduledNameResolverRefresh.isPending()) {
                if (ManagedChannelImpl.this.nameResolverBackoffPolicy == null) {
                    ManagedChannelImpl managedChannelImpl = ManagedChannelImpl.this;
                    BackoffPolicy unused = managedChannelImpl.nameResolverBackoffPolicy = managedChannelImpl.backoffPolicyProvider.get();
                }
                long delayNanos = ManagedChannelImpl.this.nameResolverBackoffPolicy.nextBackoffNanos();
                ManagedChannelImpl.this.channelLogger.log(ChannelLogger.ChannelLogLevel.DEBUG, "Scheduling DNS resolution backoff for {0} ns", Long.valueOf(delayNanos));
                ManagedChannelImpl managedChannelImpl2 = ManagedChannelImpl.this;
                SynchronizationContext.ScheduledHandle unused2 = managedChannelImpl2.scheduledNameResolverRefresh = managedChannelImpl2.syncContext.schedule(new DelayedNameResolverRefresh(), delayNanos, TimeUnit.NANOSECONDS, ManagedChannelImpl.this.transportFactory.getScheduledExecutorService());
            }
        }
    }

    /* renamed from: io.grpc.internal.ManagedChannelImpl$SubchannelImpl */
    private final class SubchannelImpl extends AbstractSubchannel {
        final LoadBalancer.CreateSubchannelArgs args;
        SynchronizationContext.ScheduledHandle delayedShutdownTask;
        final LbHelperImpl helper;
        LoadBalancer.SubchannelStateListener listener;
        boolean shutdown;
        boolean started;
        InternalSubchannel subchannel;
        final InternalLogId subchannelLogId;
        final ChannelLoggerImpl subchannelLogger;
        final ChannelTracer subchannelTracer;

        SubchannelImpl(LoadBalancer.CreateSubchannelArgs args2, LbHelperImpl helper2) {
            this.args = (LoadBalancer.CreateSubchannelArgs) Preconditions.checkNotNull(args2, "args");
            this.helper = (LbHelperImpl) Preconditions.checkNotNull(helper2, "helper");
            this.subchannelLogId = InternalLogId.allocate("Subchannel", ManagedChannelImpl.this.authority());
            InternalLogId internalLogId = this.subchannelLogId;
            int access$4600 = ManagedChannelImpl.this.maxTraceEvents;
            long currentTimeNanos = ManagedChannelImpl.this.timeProvider.currentTimeNanos();
            this.subchannelTracer = new ChannelTracer(internalLogId, access$4600, currentTimeNanos, "Subchannel for " + args2.getAddresses());
            this.subchannelLogger = new ChannelLoggerImpl(this.subchannelTracer, ManagedChannelImpl.this.timeProvider);
        }

        /* access modifiers changed from: private */
        public void internalStart(LoadBalancer.SubchannelStateListener listener2) {
            final LoadBalancer.SubchannelStateListener subchannelStateListener = listener2;
            Preconditions.checkState(!this.started, "already started");
            Preconditions.checkState(!this.shutdown, "already shutdown");
            this.started = true;
            this.listener = subchannelStateListener;
            if (ManagedChannelImpl.this.terminating) {
                ManagedChannelImpl.this.syncContext.execute(new Runnable() {
                    public void run() {
                        subchannelStateListener.onSubchannelState(ConnectivityStateInfo.forNonError(ConnectivityState.SHUTDOWN));
                    }
                });
                return;
            }
            List<EquivalentAddressGroup> addresses = this.args.getAddresses();
            String authority = ManagedChannelImpl.this.authority();
            String access$5200 = ManagedChannelImpl.this.userAgent;
            BackoffPolicy.Provider access$5300 = ManagedChannelImpl.this.backoffPolicyProvider;
            ClientTransportFactory access$2200 = ManagedChannelImpl.this.transportFactory;
            ScheduledExecutorService scheduledExecutorService = ManagedChannelImpl.this.transportFactory.getScheduledExecutorService();
            Supplier access$5400 = ManagedChannelImpl.this.stopwatchSupplier;
            SynchronizationContext synchronizationContext = ManagedChannelImpl.this.syncContext;
            AnonymousClass1ManagedInternalSubchannelCallback r13 = new InternalSubchannel.Callback() {
                /* access modifiers changed from: package-private */
                public void onTerminated(InternalSubchannel is) {
                    ManagedChannelImpl.this.subchannels.remove(is);
                    ManagedChannelImpl.this.channelz.removeSubchannel(is);
                    ManagedChannelImpl.this.maybeTerminateChannel();
                }

                /* access modifiers changed from: package-private */
                public void onStateChange(InternalSubchannel is, ConnectivityStateInfo newState) {
                    ManagedChannelImpl.this.handleInternalSubchannelState(newState);
                    Preconditions.checkState(subchannelStateListener != null, "listener is null");
                    subchannelStateListener.onSubchannelState(newState);
                }

                /* access modifiers changed from: package-private */
                public void onInUse(InternalSubchannel is) {
                    ManagedChannelImpl.this.inUseStateAggregator.updateObjectInUse(is, true);
                }

                /* access modifiers changed from: package-private */
                public void onNotInUse(InternalSubchannel is) {
                    ManagedChannelImpl.this.inUseStateAggregator.updateObjectInUse(is, false);
                }
            };
            InternalChannelz access$4900 = ManagedChannelImpl.this.channelz;
            CallTracer create = ManagedChannelImpl.this.callTracerFactory.create();
            ChannelTracer channelTracer = this.subchannelTracer;
            ChannelTracer channelTracer2 = channelTracer;
            final InternalSubchannel internalSubchannel = new InternalSubchannel(addresses, authority, access$5200, access$5300, access$2200, scheduledExecutorService, access$5400, synchronizationContext, r13, access$4900, create, channelTracer2, this.subchannelLogId, this.subchannelLogger);
            ManagedChannelImpl.this.channelTracer.reportEvent(new InternalChannelz.ChannelTrace.Event.Builder().setDescription("Child Subchannel started").setSeverity(InternalChannelz.ChannelTrace.Event.Severity.CT_INFO).setTimestampNanos(ManagedChannelImpl.this.timeProvider.currentTimeNanos()).setSubchannelRef(internalSubchannel).build());
            this.subchannel = internalSubchannel;
            ManagedChannelImpl.this.syncContext.execute(new Runnable() {
                public void run() {
                    ManagedChannelImpl.this.channelz.addSubchannel(internalSubchannel);
                    ManagedChannelImpl.this.subchannels.add(internalSubchannel);
                }
            });
        }

        public void start(LoadBalancer.SubchannelStateListener listener2) {
            ManagedChannelImpl.this.syncContext.throwIfNotInThisSynchronizationContext();
            internalStart(listener2);
        }

        /* access modifiers changed from: package-private */
        public InternalInstrumented<InternalChannelz.ChannelStats> getInstrumentedInternalSubchannel() {
            Preconditions.checkState(this.started, "not started");
            return this.subchannel;
        }

        public void shutdown() {
            ManagedChannelImpl.this.logWarningIfNotInSyncContext("Subchannel.shutdown()");
            ManagedChannelImpl.this.syncContext.execute(new Runnable() {
                public void run() {
                    SubchannelImpl.this.internalShutdown();
                }
            });
        }

        /* access modifiers changed from: private */
        public void internalShutdown() {
            SynchronizationContext.ScheduledHandle scheduledHandle;
            ManagedChannelImpl.this.syncContext.throwIfNotInThisSynchronizationContext();
            if (this.subchannel == null) {
                this.shutdown = true;
                return;
            }
            if (!this.shutdown) {
                this.shutdown = true;
            } else if (ManagedChannelImpl.this.terminating && (scheduledHandle = this.delayedShutdownTask) != null) {
                scheduledHandle.cancel();
                this.delayedShutdownTask = null;
            } else {
                return;
            }
            if (!ManagedChannelImpl.this.terminating) {
                this.delayedShutdownTask = ManagedChannelImpl.this.syncContext.schedule(new LogExceptionRunnable(new Runnable() {
                    public void run() {
                        SubchannelImpl.this.subchannel.shutdown(ManagedChannelImpl.SUBCHANNEL_SHUTDOWN_STATUS);
                    }
                }), 5, TimeUnit.SECONDS, ManagedChannelImpl.this.transportFactory.getScheduledExecutorService());
            } else {
                this.subchannel.shutdown(ManagedChannelImpl.SHUTDOWN_STATUS);
            }
        }

        public void requestConnection() {
            ManagedChannelImpl.this.logWarningIfNotInSyncContext("Subchannel.requestConnection()");
            Preconditions.checkState(this.started, "not started");
            this.subchannel.obtainActiveTransport();
        }

        public List<EquivalentAddressGroup> getAllAddresses() {
            ManagedChannelImpl.this.logWarningIfNotInSyncContext("Subchannel.getAllAddresses()");
            Preconditions.checkState(this.started, "not started");
            return this.subchannel.getAddressGroups();
        }

        public Attributes getAttributes() {
            return this.args.getAttributes();
        }

        public String toString() {
            return this.subchannelLogId.toString();
        }

        public Channel asChannel() {
            Preconditions.checkState(this.started, "not started");
            return new SubchannelChannel(this.subchannel, ManagedChannelImpl.this.balancerRpcExecutorHolder.getExecutor(), ManagedChannelImpl.this.transportFactory.getScheduledExecutorService(), ManagedChannelImpl.this.callTracerFactory.create());
        }

        public Object getInternalSubchannel() {
            Preconditions.checkState(this.started, "Subchannel is not started");
            return this.subchannel;
        }

        public ChannelLogger getChannelLogger() {
            return this.subchannelLogger;
        }

        public void updateAddresses(List<EquivalentAddressGroup> addrs) {
            ManagedChannelImpl.this.syncContext.throwIfNotInThisSynchronizationContext();
            this.subchannel.updateAddresses(addrs);
        }
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object) this).add("logId", this.logId.getId()).add("target", (Object) this.target).toString();
    }

    /* renamed from: io.grpc.internal.ManagedChannelImpl$DelayedTransportListener */
    private final class DelayedTransportListener implements ManagedClientTransport.Listener {
        private DelayedTransportListener() {
        }

        public void transportShutdown(Status s) {
            Preconditions.checkState(ManagedChannelImpl.this.shutdown.get(), "Channel must have been shut down");
        }

        public void transportReady() {
        }

        public void transportInUse(boolean inUse) {
            ManagedChannelImpl.this.inUseStateAggregator.updateObjectInUse(ManagedChannelImpl.this.delayedTransport, inUse);
        }

        public void transportTerminated() {
            Preconditions.checkState(ManagedChannelImpl.this.shutdown.get(), "Channel must have been shut down");
            boolean unused = ManagedChannelImpl.this.terminating = true;
            ManagedChannelImpl.this.shutdownNameResolverAndLoadBalancer(false);
            ManagedChannelImpl.this.maybeShutdownNowSubchannels();
            ManagedChannelImpl.this.maybeTerminateChannel();
        }
    }

    /* renamed from: io.grpc.internal.ManagedChannelImpl$IdleModeStateAggregator */
    private final class IdleModeStateAggregator extends InUseStateAggregator<Object> {
        private IdleModeStateAggregator() {
        }

        /* access modifiers changed from: protected */
        public void handleInUse() {
            ManagedChannelImpl.this.exitIdleMode();
        }

        /* access modifiers changed from: protected */
        public void handleNotInUse() {
            if (!ManagedChannelImpl.this.shutdown.get()) {
                ManagedChannelImpl.this.rescheduleIdleTimer();
            }
        }
    }

    /* renamed from: io.grpc.internal.ManagedChannelImpl$ExecutorHolder */
    private static final class ExecutorHolder {
        private Executor executor;
        private final ObjectPool<? extends Executor> pool;

        ExecutorHolder(ObjectPool<? extends Executor> executorPool) {
            this.pool = (ObjectPool) Preconditions.checkNotNull(executorPool, "executorPool");
        }

        /* access modifiers changed from: package-private */
        public synchronized Executor getExecutor() {
            if (this.executor == null) {
                this.executor = (Executor) Preconditions.checkNotNull(this.pool.getObject(), "%s.getObject()", (Object) this.executor);
            }
            return this.executor;
        }

        /* access modifiers changed from: package-private */
        public synchronized void release() {
            if (this.executor != null) {
                this.executor = (Executor) this.pool.returnObject(this.executor);
            }
        }
    }

    /* renamed from: io.grpc.internal.ManagedChannelImpl$RestrictedScheduledExecutor */
    private static final class RestrictedScheduledExecutor implements ScheduledExecutorService {
        final ScheduledExecutorService delegate;

        private RestrictedScheduledExecutor(ScheduledExecutorService delegate2) {
            this.delegate = (ScheduledExecutorService) Preconditions.checkNotNull(delegate2, "delegate");
        }

        public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
            return this.delegate.schedule(callable, delay, unit);
        }

        public ScheduledFuture<?> schedule(Runnable cmd, long delay, TimeUnit unit) {
            return this.delegate.schedule(cmd, delay, unit);
        }

        public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
            return this.delegate.scheduleAtFixedRate(command, initialDelay, period, unit);
        }

        public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
            return this.delegate.scheduleWithFixedDelay(command, initialDelay, delay, unit);
        }

        public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
            return this.delegate.awaitTermination(timeout, unit);
        }

        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
            return this.delegate.invokeAll(tasks);
        }

        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
            return this.delegate.invokeAll(tasks, timeout, unit);
        }

        public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
            return this.delegate.invokeAny(tasks);
        }

        public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return this.delegate.invokeAny(tasks, timeout, unit);
        }

        public boolean isShutdown() {
            return this.delegate.isShutdown();
        }

        public boolean isTerminated() {
            return this.delegate.isTerminated();
        }

        public void shutdown() {
            throw new UnsupportedOperationException("Restricted: shutdown() is not allowed");
        }

        public List<Runnable> shutdownNow() {
            throw new UnsupportedOperationException("Restricted: shutdownNow() is not allowed");
        }

        public <T> Future<T> submit(Callable<T> task) {
            return this.delegate.submit(task);
        }

        public Future<?> submit(Runnable task) {
            return this.delegate.submit(task);
        }

        public <T> Future<T> submit(Runnable task, T result) {
            return this.delegate.submit(task, result);
        }

        public void execute(Runnable command) {
            this.delegate.execute(command);
        }
    }

    /* renamed from: io.grpc.internal.ManagedChannelImpl$ScParser */
    static final class ScParser extends NameResolver.ServiceConfigParser {
        private final AutoConfiguredLoadBalancerFactory autoLoadBalancerFactory;
        private final ChannelLogger channelLogger;
        private final int maxHedgedAttemptsLimit;
        private final int maxRetryAttemptsLimit;
        private final boolean retryEnabled;

        ScParser(boolean retryEnabled2, int maxRetryAttemptsLimit2, int maxHedgedAttemptsLimit2, AutoConfiguredLoadBalancerFactory autoLoadBalancerFactory2, ChannelLogger channelLogger2) {
            this.retryEnabled = retryEnabled2;
            this.maxRetryAttemptsLimit = maxRetryAttemptsLimit2;
            this.maxHedgedAttemptsLimit = maxHedgedAttemptsLimit2;
            this.autoLoadBalancerFactory = (AutoConfiguredLoadBalancerFactory) Preconditions.checkNotNull(autoLoadBalancerFactory2, "autoLoadBalancerFactory");
            this.channelLogger = (ChannelLogger) Preconditions.checkNotNull(channelLogger2, "channelLogger");
        }

        public NameResolver.ConfigOrError parseServiceConfig(Map<String, ?> rawServiceConfig) {
            Object loadBalancingPolicySelection;
            try {
                NameResolver.ConfigOrError choiceFromLoadBalancer = this.autoLoadBalancerFactory.parseLoadBalancerPolicy(rawServiceConfig, this.channelLogger);
                if (choiceFromLoadBalancer == null) {
                    loadBalancingPolicySelection = null;
                } else if (choiceFromLoadBalancer.getError() != null) {
                    return NameResolver.ConfigOrError.fromError(choiceFromLoadBalancer.getError());
                } else {
                    loadBalancingPolicySelection = choiceFromLoadBalancer.getConfig();
                }
                return NameResolver.ConfigOrError.fromConfig(ManagedChannelServiceConfig.fromServiceConfig(rawServiceConfig, this.retryEnabled, this.maxRetryAttemptsLimit, this.maxHedgedAttemptsLimit, loadBalancingPolicySelection));
            } catch (RuntimeException e) {
                return NameResolver.ConfigOrError.fromError(Status.UNKNOWN.withDescription("failed to parse service config").withCause(e));
            }
        }
    }

    /* access modifiers changed from: private */
    public void logWarningIfNotInSyncContext(String method) {
        try {
            this.syncContext.throwIfNotInThisSynchronizationContext();
        } catch (IllegalStateException e) {
            Logger logger2 = logger;
            Level level = Level.WARNING;
            logger2.log(level, method + " should be called from SynchronizationContext. This warning will become an exception in a future release. See https://github.com/grpc/grpc-java/issues/5015 for more details", e);
        }
    }

    /* renamed from: io.grpc.internal.ManagedChannelImpl$ServiceConfigHolder */
    private static final class ServiceConfigHolder {
        ManagedChannelServiceConfig managedChannelServiceConfig;
        Map<String, ?> rawServiceConfig;

        ServiceConfigHolder(Map<String, ?> rawServiceConfig2, ManagedChannelServiceConfig managedChannelServiceConfig2) {
            this.rawServiceConfig = (Map) Preconditions.checkNotNull(rawServiceConfig2, "rawServiceConfig");
            this.managedChannelServiceConfig = (ManagedChannelServiceConfig) Preconditions.checkNotNull(managedChannelServiceConfig2, "managedChannelServiceConfig");
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ServiceConfigHolder that = (ServiceConfigHolder) o;
            if (!Objects.equal(this.rawServiceConfig, that.rawServiceConfig) || !Objects.equal(this.managedChannelServiceConfig, that.managedChannelServiceConfig)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return Objects.hashCode(this.rawServiceConfig, this.managedChannelServiceConfig);
        }

        public String toString() {
            return MoreObjects.toStringHelper((Object) this).add("rawServiceConfig", (Object) this.rawServiceConfig).add("managedChannelServiceConfig", (Object) this.managedChannelServiceConfig).toString();
        }
    }
}
