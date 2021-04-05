package p003io.grpc.internal;

import com.google.common.base.Verify;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.CheckForNull;
import javax.annotation.Nullable;
import p003io.grpc.CallOptions;
import p003io.grpc.Channel;
import p003io.grpc.ClientCall;
import p003io.grpc.ClientInterceptor;
import p003io.grpc.Deadline;
import p003io.grpc.MethodDescriptor;
import p003io.grpc.internal.HedgingPolicy;
import p003io.grpc.internal.ManagedChannelServiceConfig;
import p003io.grpc.internal.RetryPolicy;

/* renamed from: io.grpc.internal.ServiceConfigInterceptor */
final class ServiceConfigInterceptor implements ClientInterceptor {
    static final CallOptions.Key<HedgingPolicy.Provider> HEDGING_POLICY_KEY = CallOptions.Key.create("internal-hedging-policy");
    static final CallOptions.Key<RetryPolicy.Provider> RETRY_POLICY_KEY = CallOptions.Key.create("internal-retry-policy");
    /* access modifiers changed from: private */
    public volatile boolean initComplete;
    final AtomicReference<ManagedChannelServiceConfig> managedChannelServiceConfig = new AtomicReference<>();
    private final boolean retryEnabled;

    ServiceConfigInterceptor(boolean retryEnabled2) {
        this.retryEnabled = retryEnabled2;
    }

    /* access modifiers changed from: package-private */
    public void handleUpdate(@Nullable ManagedChannelServiceConfig serviceConfig) {
        this.managedChannelServiceConfig.set(serviceConfig);
        this.initComplete = true;
    }

    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(final MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        if (this.retryEnabled) {
            if (this.initComplete) {
                final RetryPolicy retryPolicy = getRetryPolicyFromConfig(method);
                final HedgingPolicy hedgingPolicy = getHedgingPolicyFromConfig(method);
                Verify.verify(retryPolicy.equals(RetryPolicy.DEFAULT) || hedgingPolicy.equals(HedgingPolicy.DEFAULT), "Can not apply both retry and hedging policy for the method '%s'", (Object) method);
                callOptions = callOptions.withOption(RETRY_POLICY_KEY, new RetryPolicy.Provider() {
                    public RetryPolicy get() {
                        return retryPolicy;
                    }
                }).withOption(HEDGING_POLICY_KEY, new HedgingPolicy.Provider() {
                    public HedgingPolicy get() {
                        return hedgingPolicy;
                    }
                });
            } else {
                callOptions = callOptions.withOption(RETRY_POLICY_KEY, new RetryPolicy.Provider() {
                    public RetryPolicy get() {
                        if (!ServiceConfigInterceptor.this.initComplete) {
                            return RetryPolicy.DEFAULT;
                        }
                        return ServiceConfigInterceptor.this.getRetryPolicyFromConfig(method);
                    }
                }).withOption(HEDGING_POLICY_KEY, new HedgingPolicy.Provider() {
                    public HedgingPolicy get() {
                        if (!ServiceConfigInterceptor.this.initComplete) {
                            return HedgingPolicy.DEFAULT;
                        }
                        HedgingPolicy hedgingPolicy = ServiceConfigInterceptor.this.getHedgingPolicyFromConfig(method);
                        Verify.verify(hedgingPolicy.equals(HedgingPolicy.DEFAULT) || ServiceConfigInterceptor.this.getRetryPolicyFromConfig(method).equals(RetryPolicy.DEFAULT), "Can not apply both retry and hedging policy for the method '%s'", (Object) method);
                        return hedgingPolicy;
                    }
                });
            }
        }
        ManagedChannelServiceConfig.MethodInfo info = getMethodInfo(method);
        if (info == null) {
            return next.newCall(method, callOptions);
        }
        if (info.timeoutNanos != null) {
            Deadline newDeadline = Deadline.after(info.timeoutNanos.longValue(), TimeUnit.NANOSECONDS);
            Deadline existingDeadline = callOptions.getDeadline();
            if (existingDeadline == null || newDeadline.compareTo(existingDeadline) < 0) {
                callOptions = callOptions.withDeadline(newDeadline);
            }
        }
        if (info.waitForReady != null) {
            callOptions = info.waitForReady.booleanValue() ? callOptions.withWaitForReady() : callOptions.withoutWaitForReady();
        }
        if (info.maxInboundMessageSize != null) {
            Integer existingLimit = callOptions.getMaxInboundMessageSize();
            if (existingLimit != null) {
                callOptions = callOptions.withMaxInboundMessageSize(Math.min(existingLimit.intValue(), info.maxInboundMessageSize.intValue()));
            } else {
                callOptions = callOptions.withMaxInboundMessageSize(info.maxInboundMessageSize.intValue());
            }
        }
        if (info.maxOutboundMessageSize != null) {
            Integer existingLimit2 = callOptions.getMaxOutboundMessageSize();
            if (existingLimit2 != null) {
                callOptions = callOptions.withMaxOutboundMessageSize(Math.min(existingLimit2.intValue(), info.maxOutboundMessageSize.intValue()));
            } else {
                callOptions = callOptions.withMaxOutboundMessageSize(info.maxOutboundMessageSize.intValue());
            }
        }
        return next.newCall(method, callOptions);
    }

    @CheckForNull
    private ManagedChannelServiceConfig.MethodInfo getMethodInfo(MethodDescriptor<?, ?> method) {
        ManagedChannelServiceConfig mcsc = this.managedChannelServiceConfig.get();
        ManagedChannelServiceConfig.MethodInfo info = null;
        if (mcsc != null) {
            info = mcsc.getServiceMethodMap().get(method.getFullMethodName());
        }
        if (info != null || mcsc == null) {
            return info;
        }
        return mcsc.getServiceMap().get(method.getServiceName());
    }

    /* access modifiers changed from: package-private */
    public RetryPolicy getRetryPolicyFromConfig(MethodDescriptor<?, ?> method) {
        ManagedChannelServiceConfig.MethodInfo info = getMethodInfo(method);
        return info == null ? RetryPolicy.DEFAULT : info.retryPolicy;
    }

    /* access modifiers changed from: package-private */
    public HedgingPolicy getHedgingPolicyFromConfig(MethodDescriptor<?, ?> method) {
        ManagedChannelServiceConfig.MethodInfo info = getMethodInfo(method);
        return info == null ? HedgingPolicy.DEFAULT : info.hedgingPolicy;
    }
}
