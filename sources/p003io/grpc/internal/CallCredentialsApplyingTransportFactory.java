package p003io.grpc.internal;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import java.net.SocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import p003io.grpc.Attributes;
import p003io.grpc.CallCredentials;
import p003io.grpc.CallOptions;
import p003io.grpc.ChannelLogger;
import p003io.grpc.Metadata;
import p003io.grpc.MethodDescriptor;
import p003io.grpc.SecurityLevel;
import p003io.grpc.Status;
import p003io.grpc.internal.ClientTransportFactory;

/* renamed from: io.grpc.internal.CallCredentialsApplyingTransportFactory */
final class CallCredentialsApplyingTransportFactory implements ClientTransportFactory {
    /* access modifiers changed from: private */
    public final Executor appExecutor;
    private final ClientTransportFactory delegate;

    CallCredentialsApplyingTransportFactory(ClientTransportFactory delegate2, Executor appExecutor2) {
        this.delegate = (ClientTransportFactory) Preconditions.checkNotNull(delegate2, "delegate");
        this.appExecutor = (Executor) Preconditions.checkNotNull(appExecutor2, "appExecutor");
    }

    public ConnectionClientTransport newClientTransport(SocketAddress serverAddress, ClientTransportFactory.ClientTransportOptions options, ChannelLogger channelLogger) {
        return new CallCredentialsApplyingTransport(this.delegate.newClientTransport(serverAddress, options, channelLogger), options.getAuthority());
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return this.delegate.getScheduledExecutorService();
    }

    public void close() {
        this.delegate.close();
    }

    /* renamed from: io.grpc.internal.CallCredentialsApplyingTransportFactory$CallCredentialsApplyingTransport */
    private class CallCredentialsApplyingTransport extends ForwardingConnectionClientTransport {
        /* access modifiers changed from: private */
        public final String authority;
        /* access modifiers changed from: private */
        public final ConnectionClientTransport delegate;

        CallCredentialsApplyingTransport(ConnectionClientTransport delegate2, String authority2) {
            this.delegate = (ConnectionClientTransport) Preconditions.checkNotNull(delegate2, "delegate");
            this.authority = (String) Preconditions.checkNotNull(authority2, "authority");
        }

        /* access modifiers changed from: protected */
        public ConnectionClientTransport delegate() {
            return this.delegate;
        }

        public ClientStream newStream(final MethodDescriptor<?, ?> method, Metadata headers, final CallOptions callOptions) {
            CallCredentials creds = callOptions.getCredentials();
            if (creds == null) {
                return this.delegate.newStream(method, headers, callOptions);
            }
            MetadataApplierImpl applier = new MetadataApplierImpl(this.delegate, method, headers, callOptions);
            try {
                creds.applyRequestMetadata(new CallCredentials.RequestInfo() {
                    public MethodDescriptor<?, ?> getMethodDescriptor() {
                        return method;
                    }

                    public SecurityLevel getSecurityLevel() {
                        return (SecurityLevel) MoreObjects.firstNonNull(CallCredentialsApplyingTransport.this.delegate.getAttributes().get(GrpcAttributes.ATTR_SECURITY_LEVEL), SecurityLevel.NONE);
                    }

                    public String getAuthority() {
                        return (String) MoreObjects.firstNonNull(callOptions.getAuthority(), CallCredentialsApplyingTransport.this.authority);
                    }

                    public Attributes getTransportAttrs() {
                        return CallCredentialsApplyingTransport.this.delegate.getAttributes();
                    }
                }, (Executor) MoreObjects.firstNonNull(callOptions.getExecutor(), CallCredentialsApplyingTransportFactory.this.appExecutor), applier);
            } catch (Throwable t) {
                applier.fail(Status.UNAUTHENTICATED.withDescription("Credentials should use fail() instead of throwing exceptions").withCause(t));
            }
            return applier.returnStream();
        }
    }
}
