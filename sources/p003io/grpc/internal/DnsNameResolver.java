package p003io.grpc.internal;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.base.Throwables;
import com.google.common.base.Verify;
import com.google.common.base.VerifyException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import p003io.grpc.Attributes;
import p003io.grpc.EquivalentAddressGroup;
import p003io.grpc.NameResolver;
import p003io.grpc.ProxiedSocketAddress;
import p003io.grpc.ProxyDetector;
import p003io.grpc.Status;
import p003io.grpc.SynchronizationContext;
import p003io.grpc.internal.SharedResourceHolder;

/* renamed from: io.grpc.internal.DnsNameResolver */
final class DnsNameResolver extends NameResolver {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final long DEFAULT_NETWORK_CACHE_TTL_SECONDS = 30;
    private static final String GRPCLB_NAME_PREFIX = "_grpclb._tcp.";
    private static final String JNDI_LOCALHOST_PROPERTY = System.getProperty("io.grpc.internal.DnsNameResolverProvider.enable_jndi_localhost", "false");
    private static final String JNDI_PROPERTY = System.getProperty("io.grpc.internal.DnsNameResolverProvider.enable_jndi", "true");
    private static final String JNDI_TXT_PROPERTY = System.getProperty("io.grpc.internal.DnsNameResolverProvider.enable_service_config", "false");
    static final String NETWORKADDRESS_CACHE_TTL_PROPERTY = "networkaddress.cache.ttl";
    private static final String SERVICE_CONFIG_CHOICE_CLIENT_HOSTNAME_KEY = "clientHostname";
    private static final String SERVICE_CONFIG_CHOICE_CLIENT_LANGUAGE_KEY = "clientLanguage";
    private static final Set<String> SERVICE_CONFIG_CHOICE_KEYS = Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{SERVICE_CONFIG_CHOICE_CLIENT_LANGUAGE_KEY, SERVICE_CONFIG_CHOICE_PERCENTAGE_KEY, SERVICE_CONFIG_CHOICE_CLIENT_HOSTNAME_KEY, SERVICE_CONFIG_CHOICE_SERVICE_CONFIG_KEY})));
    private static final String SERVICE_CONFIG_CHOICE_PERCENTAGE_KEY = "percentage";
    private static final String SERVICE_CONFIG_CHOICE_SERVICE_CONFIG_KEY = "serviceConfig";
    private static final String SERVICE_CONFIG_NAME_PREFIX = "_grpc_config.";
    static final String SERVICE_CONFIG_PREFIX = "grpc_config=";
    static boolean enableJndi = Boolean.parseBoolean(JNDI_PROPERTY);
    static boolean enableJndiLocalhost = Boolean.parseBoolean(JNDI_LOCALHOST_PROPERTY);
    static boolean enableTxt = Boolean.parseBoolean(JNDI_TXT_PROPERTY);
    private static String localHostname;
    /* access modifiers changed from: private */
    public static final Logger logger;
    private static final ResourceResolverFactory resourceResolverFactory;
    /* access modifiers changed from: private */
    public volatile AddressResolver addressResolver = JdkAddressResolver.INSTANCE;
    private final String authority;
    /* access modifiers changed from: private */
    public final long cacheTtlNanos;
    /* access modifiers changed from: private */
    public ResolutionResults cachedResolutionResults;
    /* access modifiers changed from: private */
    public final boolean enableSrv;
    private Executor executor;
    private final SharedResourceHolder.Resource<Executor> executorResource;
    /* access modifiers changed from: private */
    public final String host;
    private NameResolver.Listener2 listener;
    /* access modifiers changed from: private */
    public final int port;
    final ProxyDetector proxyDetector;
    /* access modifiers changed from: private */
    public final Random random = new Random();
    /* access modifiers changed from: private */
    public boolean resolving;
    private final AtomicReference<ResourceResolver> resourceResolver = new AtomicReference<>();
    /* access modifiers changed from: private */
    public final NameResolver.ServiceConfigParser serviceConfigParser;
    private boolean shutdown;
    /* access modifiers changed from: private */
    public final Stopwatch stopwatch;
    /* access modifiers changed from: private */
    public final SynchronizationContext syncContext;
    private final boolean usingExecutorResource;

    /* renamed from: io.grpc.internal.DnsNameResolver$AddressResolver */
    interface AddressResolver {
        List<InetAddress> resolveAddress(String str) throws Exception;
    }

    /* renamed from: io.grpc.internal.DnsNameResolver$ResourceResolver */
    interface ResourceResolver {
        List<EquivalentAddressGroup> resolveSrv(AddressResolver addressResolver, String str) throws Exception;

        List<String> resolveTxt(String str) throws Exception;
    }

    /* renamed from: io.grpc.internal.DnsNameResolver$ResourceResolverFactory */
    interface ResourceResolverFactory {
        @Nullable
        ResourceResolver newResourceResolver();

        @Nullable
        Throwable unavailabilityCause();
    }

    static {
        Class<DnsNameResolver> cls = DnsNameResolver.class;
        logger = Logger.getLogger(cls.getName());
        resourceResolverFactory = getResourceResolverFactory(cls.getClassLoader());
    }

    DnsNameResolver(@Nullable String nsAuthority, String name, NameResolver.Args args, SharedResourceHolder.Resource<Executor> executorResource2, Stopwatch stopwatch2, boolean isAndroid, boolean enableSrv2) {
        Preconditions.checkNotNull(args, "args");
        this.executorResource = executorResource2;
        URI nameUri = URI.create("//" + ((String) Preconditions.checkNotNull(name, "name")));
        boolean z = true;
        Preconditions.checkArgument(nameUri.getHost() != null, "Invalid DNS name: %s", (Object) name);
        this.authority = (String) Preconditions.checkNotNull(nameUri.getAuthority(), "nameUri (%s) doesn't have an authority", (Object) nameUri);
        this.host = nameUri.getHost();
        if (nameUri.getPort() == -1) {
            this.port = args.getDefaultPort();
        } else {
            this.port = nameUri.getPort();
        }
        this.proxyDetector = (ProxyDetector) Preconditions.checkNotNull(args.getProxyDetector(), "proxyDetector");
        this.cacheTtlNanos = getNetworkAddressCacheTtlNanos(isAndroid);
        this.stopwatch = (Stopwatch) Preconditions.checkNotNull(stopwatch2, "stopwatch");
        this.syncContext = (SynchronizationContext) Preconditions.checkNotNull(args.getSynchronizationContext(), "syncContext");
        Executor offloadExecutor = args.getOffloadExecutor();
        this.executor = offloadExecutor;
        this.usingExecutorResource = offloadExecutor != null ? false : z;
        this.enableSrv = enableSrv2;
        this.serviceConfigParser = (NameResolver.ServiceConfigParser) Preconditions.checkNotNull(args.getServiceConfigParser(), "serviceConfigParser");
    }

    public String getServiceAuthority() {
        return this.authority;
    }

    public void start(NameResolver.Listener2 listener2) {
        Preconditions.checkState(this.listener == null, "already started");
        if (this.usingExecutorResource) {
            this.executor = (Executor) SharedResourceHolder.get(this.executorResource);
        }
        this.listener = (NameResolver.Listener2) Preconditions.checkNotNull(listener2, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        resolve();
    }

    public void refresh() {
        Preconditions.checkState(this.listener != null, "not started");
        resolve();
    }

    /* renamed from: io.grpc.internal.DnsNameResolver$Resolve */
    private final class Resolve implements Runnable {
        private final NameResolver.Listener2 savedListener;

        Resolve(NameResolver.Listener2 savedListener2) {
            this.savedListener = (NameResolver.Listener2) Preconditions.checkNotNull(savedListener2, "savedListener");
        }

        public void run() {
            if (DnsNameResolver.logger.isLoggable(Level.FINER)) {
                Logger access$000 = DnsNameResolver.logger;
                access$000.finer("Attempting DNS resolution of " + DnsNameResolver.this.host);
            }
            try {
                resolveInternal();
            } finally {
                DnsNameResolver.this.syncContext.execute(new Runnable() {
                    public void run() {
                        boolean unused = DnsNameResolver.this.resolving = false;
                    }
                });
            }
        }

        /* access modifiers changed from: package-private */
        public void resolveInternal() {
            try {
                ProxiedSocketAddress proxiedAddr = DnsNameResolver.this.proxyDetector.proxyFor(InetSocketAddress.createUnresolved(DnsNameResolver.this.host, DnsNameResolver.this.port));
                if (proxiedAddr != null) {
                    if (DnsNameResolver.logger.isLoggable(Level.FINER)) {
                        Logger access$000 = DnsNameResolver.logger;
                        access$000.finer("Using proxy address " + proxiedAddr);
                    }
                    this.savedListener.onResult(NameResolver.ResolutionResult.newBuilder().setAddresses(Collections.singletonList(new EquivalentAddressGroup((SocketAddress) proxiedAddr))).setAttributes(Attributes.EMPTY).build());
                    return;
                }
                ResourceResolver resourceResolver = null;
                try {
                    if (DnsNameResolver.shouldUseJndi(DnsNameResolver.enableJndi, DnsNameResolver.enableJndiLocalhost, DnsNameResolver.this.host)) {
                        resourceResolver = DnsNameResolver.this.getResourceResolver();
                    }
                    final ResolutionResults results = DnsNameResolver.resolveAll(DnsNameResolver.this.addressResolver, resourceResolver, DnsNameResolver.this.enableSrv, DnsNameResolver.enableTxt, DnsNameResolver.this.host);
                    ResolutionResults resolutionResults = results;
                    DnsNameResolver.this.syncContext.execute(new Runnable() {
                        public void run() {
                            ResolutionResults unused = DnsNameResolver.this.cachedResolutionResults = results;
                            if (DnsNameResolver.this.cacheTtlNanos > 0) {
                                DnsNameResolver.this.stopwatch.reset().start();
                            }
                        }
                    });
                    if (DnsNameResolver.logger.isLoggable(Level.FINER)) {
                        Logger access$0002 = DnsNameResolver.logger;
                        access$0002.finer("Found DNS results " + resolutionResults + " for " + DnsNameResolver.this.host);
                    }
                    List<EquivalentAddressGroup> servers = new ArrayList<>();
                    for (InetAddress inetAddr : resolutionResults.addresses) {
                        servers.add(new EquivalentAddressGroup((SocketAddress) new InetSocketAddress(inetAddr, DnsNameResolver.this.port)));
                    }
                    NameResolver.ResolutionResult.Builder resultBuilder = NameResolver.ResolutionResult.newBuilder().setAddresses(servers);
                    Attributes.Builder attributesBuilder = Attributes.newBuilder();
                    if (!resolutionResults.balancerAddresses.isEmpty()) {
                        attributesBuilder.set(GrpcAttributes.ATTR_LB_ADDRS, resolutionResults.balancerAddresses);
                    }
                    if (!resolutionResults.txtRecords.isEmpty()) {
                        NameResolver.ConfigOrError rawServiceConfig = DnsNameResolver.parseServiceConfig(resolutionResults.txtRecords, DnsNameResolver.this.random, DnsNameResolver.getLocalHostname());
                        if (rawServiceConfig != null) {
                            if (rawServiceConfig.getError() != null) {
                                this.savedListener.onError(rawServiceConfig.getError());
                                return;
                            }
                            Map<String, ?> verifiedRawServiceConfig = (Map) rawServiceConfig.getConfig();
                            resultBuilder.setServiceConfig(DnsNameResolver.this.serviceConfigParser.parseServiceConfig(verifiedRawServiceConfig));
                            attributesBuilder.set(GrpcAttributes.NAME_RESOLVER_SERVICE_CONFIG, verifiedRawServiceConfig);
                        }
                    } else {
                        DnsNameResolver.logger.log(Level.FINE, "No TXT records found for {0}", new Object[]{DnsNameResolver.this.host});
                    }
                    this.savedListener.onResult(resultBuilder.setAttributes(attributesBuilder.build()).build());
                } catch (Exception e) {
                    NameResolver.Listener2 listener2 = this.savedListener;
                    Status status = Status.UNAVAILABLE;
                    listener2.onError(status.withDescription("Unable to resolve host " + DnsNameResolver.this.host).withCause(e));
                }
            } catch (IOException e2) {
                NameResolver.Listener2 listener22 = this.savedListener;
                Status status2 = Status.UNAVAILABLE;
                listener22.onError(status2.withDescription("Unable to resolve host " + DnsNameResolver.this.host).withCause(e2));
            }
        }
    }

    @Nullable
    static NameResolver.ConfigOrError parseServiceConfig(List<String> rawTxtRecords, Random random2, String localHostname2) {
        try {
            Map<String, ?> possibleServiceConfig = null;
            for (Map<String, ?> possibleServiceConfigChoice : parseTxtResults(rawTxtRecords)) {
                try {
                    possibleServiceConfig = maybeChooseServiceConfig(possibleServiceConfigChoice, random2, localHostname2);
                    if (possibleServiceConfig != null) {
                        break;
                    }
                } catch (RuntimeException e) {
                    return NameResolver.ConfigOrError.fromError(Status.UNKNOWN.withDescription("failed to pick service config choice").withCause(e));
                }
            }
            if (possibleServiceConfig == null) {
                return null;
            }
            return NameResolver.ConfigOrError.fromConfig(possibleServiceConfig);
        } catch (IOException | RuntimeException e2) {
            return NameResolver.ConfigOrError.fromError(Status.UNKNOWN.withDescription("failed to parse TXT records").withCause(e2));
        }
    }

    private void resolve() {
        if (!this.resolving && !this.shutdown && cacheRefreshRequired()) {
            this.resolving = true;
            this.executor.execute(new Resolve(this.listener));
        }
    }

    private boolean cacheRefreshRequired() {
        if (this.cachedResolutionResults != null) {
            long j = this.cacheTtlNanos;
            return j == 0 || (j > 0 && this.stopwatch.elapsed(TimeUnit.NANOSECONDS) > this.cacheTtlNanos);
        }
    }

    public void shutdown() {
        if (!this.shutdown) {
            this.shutdown = true;
            Executor executor2 = this.executor;
            if (executor2 != null && this.usingExecutorResource) {
                this.executor = (Executor) SharedResourceHolder.release(this.executorResource, executor2);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final int getPort() {
        return this.port;
    }

    static ResolutionResults resolveAll(AddressResolver addressResolver2, @Nullable ResourceResolver resourceResolver2, boolean requestSrvRecords, boolean requestTxtRecords, String name) {
        AddressResolver addressResolver3 = addressResolver2;
        ResourceResolver resourceResolver3 = resourceResolver2;
        String str = name;
        List<InetAddress> emptyList = Collections.emptyList();
        Exception addressesException = null;
        List<EquivalentAddressGroup> balancerAddresses = Collections.emptyList();
        Exception balancerAddressesException = null;
        List<String> txtRecords = Collections.emptyList();
        Exception txtRecordsException = null;
        try {
            emptyList = addressResolver2.resolveAddress(str);
        } catch (Exception e) {
            addressesException = e;
        }
        if (resourceResolver3 != null) {
            if (requestSrvRecords) {
                try {
                    balancerAddresses = resourceResolver3.resolveSrv(addressResolver2, GRPCLB_NAME_PREFIX + str);
                } catch (Exception e2) {
                    balancerAddressesException = e2;
                }
            }
            if (requestTxtRecords) {
                boolean dontResolveTxt = false;
                boolean balancerLookupFailedOrNotAttempted = !requestSrvRecords || balancerAddressesException != null;
                if (addressesException != null && balancerLookupFailedOrNotAttempted) {
                    dontResolveTxt = true;
                }
                if (!dontResolveTxt) {
                    try {
                        txtRecords = resourceResolver3.resolveTxt(SERVICE_CONFIG_NAME_PREFIX + str);
                    } catch (Exception e3) {
                        txtRecordsException = e3;
                    }
                }
            }
        }
        if (addressesException != null) {
            if (balancerAddressesException == null) {
                try {
                    if (!balancerAddresses.isEmpty()) {
                    }
                } catch (Throwable th) {
                    if (addressesException != null) {
                        logger.log(Level.FINE, "Address resolution failure", addressesException);
                    }
                    if (balancerAddressesException != null) {
                        logger.log(Level.FINE, "Balancer resolution failure", balancerAddressesException);
                    }
                    if (txtRecordsException != null) {
                        logger.log(Level.FINE, "ServiceConfig resolution failure", txtRecordsException);
                    }
                    throw th;
                }
            }
            Throwables.throwIfUnchecked(addressesException);
            throw new RuntimeException(addressesException);
        }
        if (addressesException != null) {
            logger.log(Level.FINE, "Address resolution failure", addressesException);
        }
        if (balancerAddressesException != null) {
            logger.log(Level.FINE, "Balancer resolution failure", balancerAddressesException);
        }
        if (txtRecordsException != null) {
            logger.log(Level.FINE, "ServiceConfig resolution failure", txtRecordsException);
        }
        return new ResolutionResults(emptyList, txtRecords, balancerAddresses);
    }

    static List<Map<String, ?>> parseTxtResults(List<String> txtRecords) throws IOException {
        List<Map<String, ?>> possibleServiceConfigChoices = new ArrayList<>();
        for (String txtRecord : txtRecords) {
            if (!txtRecord.startsWith(SERVICE_CONFIG_PREFIX)) {
                logger.log(Level.FINE, "Ignoring non service config {0}", new Object[]{txtRecord});
            } else {
                Object rawChoices = JsonParser.parse(txtRecord.substring(SERVICE_CONFIG_PREFIX.length()));
                if (rawChoices instanceof List) {
                    possibleServiceConfigChoices.addAll(JsonUtil.checkObjectList((List) rawChoices));
                } else {
                    throw new ClassCastException("wrong type " + rawChoices);
                }
            }
        }
        return possibleServiceConfigChoices;
    }

    @Nullable
    private static final Double getPercentageFromChoice(Map<String, ?> serviceConfigChoice) {
        return JsonUtil.getNumber(serviceConfigChoice, SERVICE_CONFIG_CHOICE_PERCENTAGE_KEY);
    }

    @Nullable
    private static final List<String> getClientLanguagesFromChoice(Map<String, ?> serviceConfigChoice) {
        return JsonUtil.getListOfStrings(serviceConfigChoice, SERVICE_CONFIG_CHOICE_CLIENT_LANGUAGE_KEY);
    }

    @Nullable
    private static final List<String> getHostnamesFromChoice(Map<String, ?> serviceConfigChoice) {
        return JsonUtil.getListOfStrings(serviceConfigChoice, SERVICE_CONFIG_CHOICE_CLIENT_HOSTNAME_KEY);
    }

    private static long getNetworkAddressCacheTtlNanos(boolean isAndroid) {
        if (isAndroid) {
            return 0;
        }
        String cacheTtlPropertyValue = System.getProperty(NETWORKADDRESS_CACHE_TTL_PROPERTY);
        long cacheTtl = DEFAULT_NETWORK_CACHE_TTL_SECONDS;
        if (cacheTtlPropertyValue != null) {
            try {
                cacheTtl = Long.parseLong(cacheTtlPropertyValue);
            } catch (NumberFormatException e) {
                logger.log(Level.WARNING, "Property({0}) valid is not valid number format({1}), fall back to default({2})", new Object[]{NETWORKADDRESS_CACHE_TTL_PROPERTY, cacheTtlPropertyValue, Long.valueOf(DEFAULT_NETWORK_CACHE_TTL_SECONDS)});
            }
        }
        return cacheTtl > 0 ? TimeUnit.SECONDS.toNanos(cacheTtl) : cacheTtl;
    }

    @Nullable
    static Map<String, ?> maybeChooseServiceConfig(Map<String, ?> choice, Random random2, String hostname) {
        for (Map.Entry<String, ?> entry : choice.entrySet()) {
            Verify.verify(SERVICE_CONFIG_CHOICE_KEYS.contains(entry.getKey()), "Bad key: %s", (Object) entry);
        }
        List<String> clientLanguages = getClientLanguagesFromChoice(choice);
        if (clientLanguages != null && !clientLanguages.isEmpty()) {
            boolean javaPresent = false;
            Iterator<String> it = clientLanguages.iterator();
            while (true) {
                if (it.hasNext()) {
                    if ("java".equalsIgnoreCase(it.next())) {
                        javaPresent = true;
                        break;
                    }
                } else {
                    break;
                }
            }
            if (!javaPresent) {
                return null;
            }
        }
        Double percentage = getPercentageFromChoice(choice);
        if (percentage != null) {
            int pct = percentage.intValue();
            Verify.verify(pct >= 0 && pct <= 100, "Bad percentage: %s", (Object) percentage);
            if (random2.nextInt(100) >= pct) {
                return null;
            }
        }
        List<String> clientHostnames = getHostnamesFromChoice(choice);
        if (clientHostnames != null && !clientHostnames.isEmpty()) {
            boolean hostnamePresent = false;
            Iterator<String> it2 = clientHostnames.iterator();
            while (true) {
                if (it2.hasNext()) {
                    if (it2.next().equals(hostname)) {
                        hostnamePresent = true;
                        break;
                    }
                } else {
                    break;
                }
            }
            if (!hostnamePresent) {
                return null;
            }
        }
        Map<String, ?> sc = JsonUtil.getObject(choice, SERVICE_CONFIG_CHOICE_SERVICE_CONFIG_KEY);
        if (sc != null) {
            return sc;
        }
        throw new VerifyException(String.format("key '%s' missing in '%s'", new Object[]{choice, SERVICE_CONFIG_CHOICE_SERVICE_CONFIG_KEY}));
    }

    /* renamed from: io.grpc.internal.DnsNameResolver$ResolutionResults */
    static final class ResolutionResults {
        final List<? extends InetAddress> addresses;
        final List<EquivalentAddressGroup> balancerAddresses;
        final List<String> txtRecords;

        ResolutionResults(List<? extends InetAddress> addresses2, List<String> txtRecords2, List<EquivalentAddressGroup> balancerAddresses2) {
            this.addresses = Collections.unmodifiableList((List) Preconditions.checkNotNull(addresses2, "addresses"));
            this.txtRecords = Collections.unmodifiableList((List) Preconditions.checkNotNull(txtRecords2, "txtRecords"));
            this.balancerAddresses = Collections.unmodifiableList((List) Preconditions.checkNotNull(balancerAddresses2, "balancerAddresses"));
        }

        public String toString() {
            return MoreObjects.toStringHelper((Object) this).add("addresses", (Object) this.addresses).add("txtRecords", (Object) this.txtRecords).add("balancerAddresses", (Object) this.balancerAddresses).toString();
        }
    }

    /* access modifiers changed from: package-private */
    public void setAddressResolver(AddressResolver addressResolver2) {
        this.addressResolver = addressResolver2;
    }

    /* access modifiers changed from: package-private */
    public void setResourceResolver(ResourceResolver resourceResolver2) {
        this.resourceResolver.set(resourceResolver2);
    }

    /* renamed from: io.grpc.internal.DnsNameResolver$JdkAddressResolver */
    private enum JdkAddressResolver implements AddressResolver {
        INSTANCE;

        public List<InetAddress> resolveAddress(String host) throws UnknownHostException {
            return Collections.unmodifiableList(Arrays.asList(InetAddress.getAllByName(host)));
        }
    }

    /* access modifiers changed from: private */
    @Nullable
    public ResourceResolver getResourceResolver() {
        ResourceResolverFactory resourceResolverFactory2;
        ResourceResolver resourceResolver2 = this.resourceResolver.get();
        ResourceResolver rr = resourceResolver2;
        if (resourceResolver2 != null || (resourceResolverFactory2 = resourceResolverFactory) == null) {
            return rr;
        }
        return resourceResolverFactory2.newResourceResolver();
    }

    @Nullable
    static ResourceResolverFactory getResourceResolverFactory(ClassLoader loader) {
        try {
            try {
                try {
                    ResourceResolverFactory rrf = (ResourceResolverFactory) Class.forName("io.grpc.internal.JndiResourceResolverFactory", true, loader).asSubclass(ResourceResolverFactory.class).getConstructor(new Class[0]).newInstance(new Object[0]);
                    if (rrf.unavailabilityCause() == null) {
                        return rrf;
                    }
                    logger.log(Level.FINE, "JndiResourceResolverFactory not available, skipping.", rrf.unavailabilityCause());
                    return null;
                } catch (Exception e) {
                    logger.log(Level.FINE, "Can't construct JndiResourceResolverFactory, skipping.", e);
                    return null;
                }
            } catch (Exception e2) {
                logger.log(Level.FINE, "Can't find JndiResourceResolverFactory ctor, skipping.", e2);
                return null;
            }
        } catch (ClassNotFoundException e3) {
            logger.log(Level.FINE, "Unable to find JndiResourceResolverFactory, skipping.", e3);
            return null;
        } catch (ClassCastException e4) {
            logger.log(Level.FINE, "Unable to cast JndiResourceResolverFactory, skipping.", e4);
            return null;
        }
    }

    /* access modifiers changed from: private */
    public static String getLocalHostname() {
        if (localHostname == null) {
            try {
                localHostname = InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
        }
        return localHostname;
    }

    static boolean shouldUseJndi(boolean jndiEnabled, boolean jndiLocalhostEnabled, String target) {
        if (!jndiEnabled) {
            return false;
        }
        if ("localhost".equalsIgnoreCase(target)) {
            return jndiLocalhostEnabled;
        }
        if (target.contains(":")) {
            return false;
        }
        boolean alldigits = true;
        for (int i = 0; i < target.length(); i++) {
            char c = target.charAt(i);
            if (c != '.') {
                alldigits &= c >= '0' && c <= '9';
            }
        }
        return !alldigits;
    }
}
