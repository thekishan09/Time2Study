package p003io.grpc.okhttp;

import com.google.common.base.Preconditions;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import javax.net.ssl.SSLSocket;
import p003io.grpc.okhttp.internal.OptionalMethod;
import p003io.grpc.okhttp.internal.Platform;
import p003io.grpc.okhttp.internal.Protocol;
import p003io.grpc.okhttp.internal.Util;

/* renamed from: io.grpc.okhttp.OkHttpProtocolNegotiator */
class OkHttpProtocolNegotiator {
    private static final Platform DEFAULT_PLATFORM = Platform.get();
    private static OkHttpProtocolNegotiator NEGOTIATOR;
    /* access modifiers changed from: private */
    public static final Logger logger;
    protected final Platform platform;

    static {
        Class<OkHttpProtocolNegotiator> cls = OkHttpProtocolNegotiator.class;
        logger = Logger.getLogger(cls.getName());
        NEGOTIATOR = createNegotiator(cls.getClassLoader());
    }

    OkHttpProtocolNegotiator(Platform platform2) {
        this.platform = (Platform) Preconditions.checkNotNull(platform2, "platform");
    }

    public static OkHttpProtocolNegotiator get() {
        return NEGOTIATOR;
    }

    static OkHttpProtocolNegotiator createNegotiator(ClassLoader loader) {
        boolean android2 = true;
        try {
            loader.loadClass("com.android.org.conscrypt.OpenSSLSocketImpl");
        } catch (ClassNotFoundException e1) {
            logger.log(Level.FINE, "Unable to find Conscrypt. Skipping", e1);
            try {
                loader.loadClass("org.apache.harmony.xnet.provider.jsse.OpenSSLSocketImpl");
            } catch (ClassNotFoundException e2) {
                logger.log(Level.FINE, "Unable to find any OpenSSLSocketImpl. Skipping", e2);
                android2 = false;
            }
        }
        if (android2) {
            return new AndroidNegotiator(DEFAULT_PLATFORM);
        }
        return new OkHttpProtocolNegotiator(DEFAULT_PLATFORM);
    }

    public String negotiate(SSLSocket sslSocket, String hostname, @Nullable List<Protocol> protocols) throws IOException {
        if (protocols != null) {
            configureTlsExtensions(sslSocket, hostname, protocols);
        }
        try {
            sslSocket.startHandshake();
            String negotiatedProtocol = getSelectedProtocol(sslSocket);
            if (negotiatedProtocol != null) {
                return negotiatedProtocol;
            }
            throw new RuntimeException("TLS ALPN negotiation failed with protocols: " + protocols);
        } finally {
            this.platform.afterHandshake(sslSocket);
        }
    }

    /* access modifiers changed from: protected */
    public void configureTlsExtensions(SSLSocket sslSocket, String hostname, List<Protocol> protocols) {
        this.platform.configureTlsExtensions(sslSocket, hostname, protocols);
    }

    public String getSelectedProtocol(SSLSocket socket) {
        return this.platform.getSelectedProtocol(socket);
    }

    /* renamed from: io.grpc.okhttp.OkHttpProtocolNegotiator$AndroidNegotiator */
    static final class AndroidNegotiator extends OkHttpProtocolNegotiator {
        private static final OptionalMethod<Socket> GET_ALPN_SELECTED_PROTOCOL;
        private static final OptionalMethod<Socket> GET_NPN_SELECTED_PROTOCOL;
        private static final OptionalMethod<Socket> SET_ALPN_PROTOCOLS;
        private static final OptionalMethod<Socket> SET_HOSTNAME = new OptionalMethod<>((Class<?>) null, "setHostname", String.class);
        private static final OptionalMethod<Socket> SET_NPN_PROTOCOLS;
        private static final OptionalMethod<Socket> SET_USE_SESSION_TICKETS = new OptionalMethod<>((Class<?>) null, "setUseSessionTickets", Boolean.TYPE);

        static {
            Class<byte[]> cls = byte[].class;
            GET_ALPN_SELECTED_PROTOCOL = new OptionalMethod<>(cls, "getAlpnSelectedProtocol", new Class[0]);
            SET_ALPN_PROTOCOLS = new OptionalMethod<>((Class<?>) null, "setAlpnProtocols", cls);
            GET_NPN_SELECTED_PROTOCOL = new OptionalMethod<>(cls, "getNpnSelectedProtocol", new Class[0]);
            SET_NPN_PROTOCOLS = new OptionalMethod<>((Class<?>) null, "setNpnProtocols", cls);
        }

        AndroidNegotiator(Platform platform) {
            super(platform);
        }

        public String negotiate(SSLSocket sslSocket, String hostname, List<Protocol> protocols) throws IOException {
            String negotiatedProtocol = getSelectedProtocol(sslSocket);
            if (negotiatedProtocol == null) {
                return OkHttpProtocolNegotiator.super.negotiate(sslSocket, hostname, protocols);
            }
            return negotiatedProtocol;
        }

        /* access modifiers changed from: protected */
        public void configureTlsExtensions(SSLSocket sslSocket, String hostname, List<Protocol> protocols) {
            if (hostname != null) {
                SET_USE_SESSION_TICKETS.invokeOptionalWithoutCheckedException(sslSocket, true);
                SET_HOSTNAME.invokeOptionalWithoutCheckedException(sslSocket, hostname);
            }
            Object[] parameters = {Platform.concatLengthPrefixed(protocols)};
            if (this.platform.getTlsExtensionType() == Platform.TlsExtensionType.ALPN_AND_NPN) {
                SET_ALPN_PROTOCOLS.invokeWithoutCheckedException(sslSocket, parameters);
            }
            if (this.platform.getTlsExtensionType() != Platform.TlsExtensionType.NONE) {
                SET_NPN_PROTOCOLS.invokeWithoutCheckedException(sslSocket, parameters);
                return;
            }
            throw new RuntimeException("We can not do TLS handshake on this Android version, please install the Google Play Services Dynamic Security Provider to use TLS");
        }

        public String getSelectedProtocol(SSLSocket socket) {
            if (this.platform.getTlsExtensionType() == Platform.TlsExtensionType.ALPN_AND_NPN) {
                try {
                    byte[] alpnResult = (byte[]) GET_ALPN_SELECTED_PROTOCOL.invokeWithoutCheckedException(socket, new Object[0]);
                    if (alpnResult != null) {
                        return new String(alpnResult, Util.UTF_8);
                    }
                } catch (Exception e) {
                    OkHttpProtocolNegotiator.logger.log(Level.FINE, "Failed calling getAlpnSelectedProtocol()", e);
                }
            }
            if (this.platform.getTlsExtensionType() == Platform.TlsExtensionType.NONE) {
                return null;
            }
            try {
                byte[] npnResult = (byte[]) GET_NPN_SELECTED_PROTOCOL.invokeWithoutCheckedException(socket, new Object[0]);
                if (npnResult != null) {
                    return new String(npnResult, Util.UTF_8);
                }
                return null;
            } catch (Exception e2) {
                OkHttpProtocolNegotiator.logger.log(Level.FINE, "Failed calling getNpnSelectedProtocol()", e2);
                return null;
            }
        }
    }
}
