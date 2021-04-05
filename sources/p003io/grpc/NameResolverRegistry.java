package p003io.grpc;

import androidx.core.p005os.EnvironmentCompat;
import com.google.common.base.Preconditions;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import p003io.grpc.NameResolver;
import p003io.grpc.ServiceProviders;

/* renamed from: io.grpc.NameResolverRegistry */
public final class NameResolverRegistry {
    private static NameResolverRegistry instance;
    private static final Logger logger = Logger.getLogger(NameResolverRegistry.class.getName());
    private final LinkedHashSet<NameResolverProvider> allProviders = new LinkedHashSet<>();
    private List<NameResolverProvider> effectiveProviders = Collections.emptyList();
    private final NameResolver.Factory factory = new NameResolverFactory();

    public synchronized void register(NameResolverProvider provider) {
        addProvider(provider);
        refreshProviders();
    }

    private synchronized void addProvider(NameResolverProvider provider) {
        Preconditions.checkArgument(provider.isAvailable(), "isAvailable() returned false");
        this.allProviders.add(provider);
    }

    public synchronized void deregister(NameResolverProvider provider) {
        this.allProviders.remove(provider);
        refreshProviders();
    }

    private synchronized void refreshProviders() {
        List<NameResolverProvider> providers = new ArrayList<>(this.allProviders);
        Collections.sort(providers, Collections.reverseOrder(new Comparator<NameResolverProvider>() {
            public int compare(NameResolverProvider o1, NameResolverProvider o2) {
                return o1.priority() - o2.priority();
            }
        }));
        this.effectiveProviders = Collections.unmodifiableList(providers);
    }

    public static synchronized NameResolverRegistry getDefaultRegistry() {
        NameResolverRegistry nameResolverRegistry;
        synchronized (NameResolverRegistry.class) {
            if (instance == null) {
                List<NameResolverProvider> providerList = ServiceProviders.loadAll(NameResolverProvider.class, getHardCodedClasses(), NameResolverProvider.class.getClassLoader(), new NameResolverPriorityAccessor());
                if (providerList.isEmpty()) {
                    logger.warning("No NameResolverProviders found via ServiceLoader, including for DNS. This is probably due to a broken build. If using ProGuard, check your configuration");
                }
                instance = new NameResolverRegistry();
                for (NameResolverProvider provider : providerList) {
                    Logger logger2 = logger;
                    logger2.fine("Service loader found " + provider);
                    if (provider.isAvailable()) {
                        instance.addProvider(provider);
                    }
                }
                instance.refreshProviders();
            }
            nameResolverRegistry = instance;
        }
        return nameResolverRegistry;
    }

    /* access modifiers changed from: package-private */
    public synchronized List<NameResolverProvider> providers() {
        return this.effectiveProviders;
    }

    public NameResolver.Factory asFactory() {
        return this.factory;
    }

    static List<Class<?>> getHardCodedClasses() {
        ArrayList<Class<?>> list = new ArrayList<>();
        try {
            list.add(Class.forName("io.grpc.internal.DnsNameResolverProvider"));
        } catch (ClassNotFoundException e) {
            logger.log(Level.FINE, "Unable to find DNS NameResolver", e);
        }
        return Collections.unmodifiableList(list);
    }

    /* renamed from: io.grpc.NameResolverRegistry$NameResolverFactory */
    private final class NameResolverFactory extends NameResolver.Factory {
        private NameResolverFactory() {
        }

        @Nullable
        public NameResolver newNameResolver(URI targetUri, NameResolver.Args args) {
            for (NameResolverProvider provider : NameResolverRegistry.this.providers()) {
                NameResolver resolver = provider.newNameResolver(targetUri, args);
                if (resolver != null) {
                    return resolver;
                }
            }
            return null;
        }

        public String getDefaultScheme() {
            List<NameResolverProvider> providers = NameResolverRegistry.this.providers();
            if (providers.isEmpty()) {
                return EnvironmentCompat.MEDIA_UNKNOWN;
            }
            return providers.get(0).getDefaultScheme();
        }
    }

    /* renamed from: io.grpc.NameResolverRegistry$NameResolverPriorityAccessor */
    private static final class NameResolverPriorityAccessor implements ServiceProviders.PriorityAccessor<NameResolverProvider> {
        private NameResolverPriorityAccessor() {
        }

        public boolean isAvailable(NameResolverProvider provider) {
            return provider.isAvailable();
        }

        public int getPriority(NameResolverProvider provider) {
            return provider.priority();
        }
    }
}
