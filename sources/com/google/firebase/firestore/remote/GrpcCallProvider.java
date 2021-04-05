package com.google.firebase.firestore.remote;

import android.content.Context;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.core.DatabaseInfo;
import com.google.firebase.firestore.util.AsyncQueue;
import com.google.firebase.firestore.util.Executors;
import com.google.firebase.firestore.util.Logger;
import com.google.firebase.firestore.util.Supplier;
import com.google.firestore.p012v1.FirestoreGrpc;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import p003io.grpc.CallCredentials;
import p003io.grpc.CallOptions;
import p003io.grpc.ClientCall;
import p003io.grpc.ConnectivityState;
import p003io.grpc.ManagedChannel;
import p003io.grpc.ManagedChannelBuilder;
import p003io.grpc.MethodDescriptor;
import p003io.grpc.android.AndroidChannelBuilder;

public class GrpcCallProvider {
    private static final int CONNECTIVITY_ATTEMPT_TIMEOUT_MS = 15000;
    private static final String LOG_TAG = "GrpcCallProvider";
    private static Supplier<ManagedChannelBuilder<?>> overrideChannelBuilderSupplier;
    private final AsyncQueue asyncQueue;
    private CallOptions callOptions;
    private Task<ManagedChannel> channelTask;
    private AsyncQueue.DelayedTask connectivityAttemptTimer;
    private final Context context;
    private final DatabaseInfo databaseInfo;
    private final CallCredentials firestoreHeaders;

    public static void overrideChannelBuilder(Supplier<ManagedChannelBuilder<?>> channelBuilderSupplier) {
        overrideChannelBuilderSupplier = channelBuilderSupplier;
    }

    GrpcCallProvider(AsyncQueue asyncQueue2, Context context2, DatabaseInfo databaseInfo2, CallCredentials firestoreHeaders2) {
        this.asyncQueue = asyncQueue2;
        this.context = context2;
        this.databaseInfo = databaseInfo2;
        this.firestoreHeaders = firestoreHeaders2;
        initChannelTask();
    }

    private ManagedChannel initChannel(Context context2, DatabaseInfo databaseInfo2) {
        ManagedChannelBuilder<?> channelBuilder;
        try {
            ProviderInstaller.installIfNeeded(context2);
        } catch (GooglePlayServicesNotAvailableException | GooglePlayServicesRepairableException | IllegalStateException e) {
            Logger.warn(LOG_TAG, "Failed to update ssl context: %s", e);
        }
        Supplier<ManagedChannelBuilder<?>> supplier = overrideChannelBuilderSupplier;
        if (supplier != null) {
            channelBuilder = supplier.get();
        } else {
            channelBuilder = ManagedChannelBuilder.forTarget(databaseInfo2.getHost());
            if (!databaseInfo2.isSslEnabled()) {
                channelBuilder.usePlaintext();
            }
        }
        channelBuilder.keepAliveTime(30, TimeUnit.SECONDS);
        return AndroidChannelBuilder.usingBuilder(channelBuilder).context(context2).build();
    }

    /* access modifiers changed from: package-private */
    public <ReqT, RespT> Task<ClientCall<ReqT, RespT>> createClientCall(MethodDescriptor<ReqT, RespT> methodDescriptor) {
        return this.channelTask.continueWithTask(this.asyncQueue.getExecutor(), GrpcCallProvider$$Lambda$1.lambdaFactory$(this, methodDescriptor));
    }

    /* access modifiers changed from: package-private */
    public void shutdown() {
        try {
            ManagedChannel channel = (ManagedChannel) Tasks.await(this.channelTask);
            channel.shutdown();
            try {
                if (!channel.awaitTermination(1, TimeUnit.SECONDS)) {
                    Logger.debug(FirestoreChannel.class.getSimpleName(), "Unable to gracefully shutdown the gRPC ManagedChannel. Will attempt an immediate shutdown.", new Object[0]);
                    channel.shutdownNow();
                    if (!channel.awaitTermination(60, TimeUnit.SECONDS)) {
                        Logger.warn(FirestoreChannel.class.getSimpleName(), "Unable to forcefully shutdown the gRPC ManagedChannel.", new Object[0]);
                    }
                }
            } catch (InterruptedException e) {
                channel.shutdownNow();
                Logger.warn(FirestoreChannel.class.getSimpleName(), "Interrupted while shutting down the gRPC Managed Channel", new Object[0]);
                Thread.currentThread().interrupt();
            }
        } catch (ExecutionException e2) {
            Logger.warn(FirestoreChannel.class.getSimpleName(), "Channel is not initialized, shutdown will just do nothing. Channel initializing run into exception: %s", e2);
        } catch (InterruptedException e3) {
            Logger.warn(FirestoreChannel.class.getSimpleName(), "Interrupted while retrieving the gRPC Managed Channel", new Object[0]);
            Thread.currentThread().interrupt();
        }
    }

    /* access modifiers changed from: private */
    public void onConnectivityStateChange(ManagedChannel channel) {
        ConnectivityState newState = channel.getState(true);
        Logger.debug(LOG_TAG, "Current gRPC connectivity state: " + newState, new Object[0]);
        clearConnectivityAttemptTimer();
        if (newState == ConnectivityState.CONNECTING) {
            Logger.debug(LOG_TAG, "Setting the connectivityAttemptTimer", new Object[0]);
            this.connectivityAttemptTimer = this.asyncQueue.enqueueAfterDelay(AsyncQueue.TimerId.CONNECTIVITY_ATTEMPT_TIMER, 15000, GrpcCallProvider$$Lambda$2.lambdaFactory$(this, channel));
        }
        channel.notifyWhenStateChanged(newState, GrpcCallProvider$$Lambda$3.lambdaFactory$(this, channel));
    }

    static /* synthetic */ void lambda$onConnectivityStateChange$1(GrpcCallProvider grpcCallProvider, ManagedChannel channel) {
        Logger.debug(LOG_TAG, "connectivityAttemptTimer elapsed. Resetting the channel.", new Object[0]);
        grpcCallProvider.clearConnectivityAttemptTimer();
        grpcCallProvider.resetChannel(channel);
    }

    private void resetChannel(ManagedChannel channel) {
        this.asyncQueue.enqueueAndForget(GrpcCallProvider$$Lambda$4.lambdaFactory$(this, channel));
    }

    static /* synthetic */ void lambda$resetChannel$4(GrpcCallProvider grpcCallProvider, ManagedChannel channel) {
        channel.shutdownNow();
        grpcCallProvider.initChannelTask();
    }

    private void initChannelTask() {
        this.channelTask = Tasks.call(Executors.BACKGROUND_EXECUTOR, GrpcCallProvider$$Lambda$5.lambdaFactory$(this));
    }

    static /* synthetic */ ManagedChannel lambda$initChannelTask$6(GrpcCallProvider grpcCallProvider) throws Exception {
        ManagedChannel channel = grpcCallProvider.initChannel(grpcCallProvider.context, grpcCallProvider.databaseInfo);
        grpcCallProvider.asyncQueue.enqueueAndForget(GrpcCallProvider$$Lambda$6.lambdaFactory$(grpcCallProvider, channel));
        grpcCallProvider.callOptions = ((FirestoreGrpc.FirestoreStub) ((FirestoreGrpc.FirestoreStub) FirestoreGrpc.newStub(channel).withCallCredentials(grpcCallProvider.firestoreHeaders)).withExecutor(grpcCallProvider.asyncQueue.getExecutor())).getCallOptions();
        Logger.debug(LOG_TAG, "Channel successfully reset.", new Object[0]);
        return channel;
    }

    private void clearConnectivityAttemptTimer() {
        if (this.connectivityAttemptTimer != null) {
            Logger.debug(LOG_TAG, "Clearing the connectivityAttemptTimer", new Object[0]);
            this.connectivityAttemptTimer.cancel();
            this.connectivityAttemptTimer = null;
        }
    }
}
