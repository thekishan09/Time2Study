package p003io.grpc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import p003io.grpc.ServiceProviders;

/* renamed from: io.grpc.ManagedChannelProvider */
public abstract class ManagedChannelProvider {
    static final Iterable<Class<?>> HARDCODED_CLASSES;
    private static final ManagedChannelProvider provider;

    /* access modifiers changed from: protected */
    public abstract ManagedChannelBuilder<?> builderForAddress(String str, int i);

    /* access modifiers changed from: protected */
    public abstract ManagedChannelBuilder<?> builderForTarget(String str);

    /* access modifiers changed from: protected */
    public abstract boolean isAvailable();

    /* access modifiers changed from: protected */
    public abstract int priority();

    static {
        Class cls = ManagedChannelProvider.class;
        HardcodedClasses hardcodedClasses = new HardcodedClasses();
        HARDCODED_CLASSES = hardcodedClasses;
        provider = (ManagedChannelProvider) ServiceProviders.load(cls, hardcodedClasses, cls.getClassLoader(), new ServiceProviders.PriorityAccessor<ManagedChannelProvider>() {
            public boolean isAvailable(ManagedChannelProvider provider) {
                return provider.isAvailable();
            }

            public int getPriority(ManagedChannelProvider provider) {
                return provider.priority();
            }
        });
    }

    public static ManagedChannelProvider provider() {
        ManagedChannelProvider managedChannelProvider = provider;
        if (managedChannelProvider != null) {
            return managedChannelProvider;
        }
        throw new ProviderNotFoundException("No functional channel service provider found. Try adding a dependency on the grpc-okhttp, grpc-netty, or grpc-netty-shaded artifact");
    }

    /* renamed from: io.grpc.ManagedChannelProvider$ProviderNotFoundException */
    public static final class ProviderNotFoundException extends RuntimeException {
        private static final long serialVersionUID = 1;

        public ProviderNotFoundException(String msg) {
            super(msg);
        }
    }

    /* renamed from: io.grpc.ManagedChannelProvider$HardcodedClasses */
    private static final class HardcodedClasses implements Iterable<Class<?>> {
        private HardcodedClasses() {
        }

        public Iterator<Class<?>> iterator() {
            List<Class<?>> list = new ArrayList<>();
            try {
                list.add(Class.forName("io.grpc.okhttp.OkHttpChannelProvider"));
            } catch (ClassNotFoundException e) {
            }
            try {
                list.add(Class.forName("io.grpc.netty.NettyChannelProvider"));
            } catch (ClassNotFoundException e2) {
            }
            return list.iterator();
        }
    }
}
