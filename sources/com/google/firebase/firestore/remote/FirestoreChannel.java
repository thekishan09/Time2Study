package com.google.firebase.firestore.remote;

import android.content.Context;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.auth.CredentialsProvider;
import com.google.firebase.firestore.core.DatabaseInfo;
import com.google.firebase.firestore.model.DatabaseId;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.AsyncQueue;
import com.google.firebase.firestore.util.Util;
import java.util.ArrayList;
import java.util.List;
import p003io.grpc.ClientCall;
import p003io.grpc.ForwardingClientCall;
import p003io.grpc.Metadata;
import p003io.grpc.MethodDescriptor;
import p003io.grpc.Status;

class FirestoreChannel {
    private static final Metadata.Key<String> RESOURCE_PREFIX_HEADER = Metadata.Key.m590of("google-cloud-resource-prefix", Metadata.ASCII_STRING_MARSHALLER);
    private static final Metadata.Key<String> X_GOOG_API_CLIENT_HEADER = Metadata.Key.m590of("x-goog-api-client", Metadata.ASCII_STRING_MARSHALLER);
    private static final String X_GOOG_API_CLIENT_VALUE = "gl-java/ fire/21.5.0 grpc/";
    /* access modifiers changed from: private */
    public final AsyncQueue asyncQueue;
    private final GrpcCallProvider callProvider;
    private final CredentialsProvider credentialsProvider;
    private final GrpcMetadataProvider metadataProvider;
    private final String resourcePrefixValue;

    FirestoreChannel(AsyncQueue asyncQueue2, Context context, CredentialsProvider credentialsProvider2, DatabaseInfo databaseInfo, GrpcMetadataProvider metadataProvider2) {
        this.asyncQueue = asyncQueue2;
        this.metadataProvider = metadataProvider2;
        this.credentialsProvider = credentialsProvider2;
        this.callProvider = new GrpcCallProvider(asyncQueue2, context, databaseInfo, new FirestoreCallCredentials(credentialsProvider2));
        DatabaseId databaseId = databaseInfo.getDatabaseId();
        this.resourcePrefixValue = String.format("projects/%s/databases/%s", new Object[]{databaseId.getProjectId(), databaseId.getDatabaseId()});
    }

    public void shutdown() {
        this.callProvider.shutdown();
    }

    /* access modifiers changed from: package-private */
    public <ReqT, RespT> ClientCall<ReqT, RespT> runBidiStreamingRpc(MethodDescriptor<ReqT, RespT> method, IncomingStreamObserver<RespT> observer) {
        final ClientCall<ReqT, RespT>[] call = {null};
        final Task<ClientCall<ReqT, RespT>> clientCall = this.callProvider.createClientCall(method);
        clientCall.addOnCompleteListener(this.asyncQueue.getExecutor(), (OnCompleteListener<ClientCall<ReqT, RespT>>) FirestoreChannel$$Lambda$1.lambdaFactory$(this, call, observer));
        return new ForwardingClientCall<ReqT, RespT>() {
            /* access modifiers changed from: protected */
            public ClientCall<ReqT, RespT> delegate() {
                Assert.hardAssert(call[0] != null, "ClientCall used before onOpen() callback", new Object[0]);
                return call[0];
            }

            public void halfClose() {
                if (call[0] == null) {
                    clientCall.addOnSuccessListener(FirestoreChannel.this.asyncQueue.getExecutor(), FirestoreChannel$2$$Lambda$1.lambdaFactory$());
                } else {
                    super.halfClose();
                }
            }
        };
    }

    static /* synthetic */ void lambda$runBidiStreamingRpc$0(FirestoreChannel firestoreChannel, final ClientCall[] call, final IncomingStreamObserver observer, Task result) {
        call[0] = (ClientCall) result.getResult();
        call[0].start(new ClientCall.Listener<RespT>() {
            public void onHeaders(Metadata headers) {
                try {
                    observer.onHeaders(headers);
                } catch (Throwable t) {
                    FirestoreChannel.this.asyncQueue.panic(t);
                }
            }

            public void onMessage(RespT message) {
                try {
                    observer.onNext(message);
                    call[0].request(1);
                } catch (Throwable t) {
                    FirestoreChannel.this.asyncQueue.panic(t);
                }
            }

            public void onClose(Status status, Metadata trailers) {
                try {
                    observer.onClose(status);
                } catch (Throwable t) {
                    FirestoreChannel.this.asyncQueue.panic(t);
                }
            }

            public void onReady() {
            }
        }, firestoreChannel.requestHeaders());
        observer.onOpen();
        call[0].request(1);
    }

    /* access modifiers changed from: package-private */
    public <ReqT, RespT> Task<List<RespT>> runStreamingResponseRpc(MethodDescriptor<ReqT, RespT> method, ReqT request) {
        TaskCompletionSource<List<RespT>> tcs = new TaskCompletionSource<>();
        this.callProvider.createClientCall(method).addOnCompleteListener(this.asyncQueue.getExecutor(), FirestoreChannel$$Lambda$2.lambdaFactory$(this, tcs, request));
        return tcs.getTask();
    }

    static /* synthetic */ void lambda$runStreamingResponseRpc$1(FirestoreChannel firestoreChannel, final TaskCompletionSource tcs, Object request, Task result) {
        final ClientCall<ReqT, RespT> call = (ClientCall) result.getResult();
        final List<RespT> results = new ArrayList<>();
        call.start(new ClientCall.Listener<RespT>() {
            public void onMessage(RespT message) {
                results.add(message);
                call.request(1);
            }

            public void onClose(Status status, Metadata trailers) {
                if (status.isOk()) {
                    tcs.setResult(results);
                } else {
                    tcs.setException(FirestoreChannel.this.exceptionFromStatus(status));
                }
            }
        }, firestoreChannel.requestHeaders());
        call.request(1);
        call.sendMessage(request);
        call.halfClose();
    }

    /* access modifiers changed from: package-private */
    public <ReqT, RespT> Task<RespT> runRpc(MethodDescriptor<ReqT, RespT> method, ReqT request) {
        TaskCompletionSource<RespT> tcs = new TaskCompletionSource<>();
        this.callProvider.createClientCall(method).addOnCompleteListener(this.asyncQueue.getExecutor(), FirestoreChannel$$Lambda$3.lambdaFactory$(this, tcs, request));
        return tcs.getTask();
    }

    static /* synthetic */ void lambda$runRpc$2(FirestoreChannel firestoreChannel, final TaskCompletionSource tcs, Object request, Task result) {
        ClientCall<ReqT, RespT> call = (ClientCall) result.getResult();
        call.start(new ClientCall.Listener<RespT>() {
            public void onMessage(RespT message) {
                tcs.setResult(message);
            }

            public void onClose(Status status, Metadata trailers) {
                if (!status.isOk()) {
                    tcs.setException(FirestoreChannel.this.exceptionFromStatus(status));
                } else if (!tcs.getTask().isComplete()) {
                    tcs.setException(new FirebaseFirestoreException("Received onClose with status OK, but no message.", FirebaseFirestoreException.Code.INTERNAL));
                }
            }
        }, firestoreChannel.requestHeaders());
        call.request(2);
        call.sendMessage(request);
        call.halfClose();
    }

    /* access modifiers changed from: private */
    public FirebaseFirestoreException exceptionFromStatus(Status status) {
        if (Datastore.isMissingSslCiphers(status)) {
            return new FirebaseFirestoreException("The Cloud Firestore client failed to establish a secure connection. This is likely a problem with your app, rather than with Cloud Firestore itself. See https://bit.ly/2XFpdma for instructions on how to enable TLS on Android 4.x devices.", FirebaseFirestoreException.Code.fromValue(status.getCode().value()), status.getCause());
        }
        return Util.exceptionFromStatus(status);
    }

    public void invalidateToken() {
        this.credentialsProvider.invalidateToken();
    }

    private Metadata requestHeaders() {
        Metadata headers = new Metadata();
        headers.put(X_GOOG_API_CLIENT_HEADER, X_GOOG_API_CLIENT_VALUE);
        headers.put(RESOURCE_PREFIX_HEADER, this.resourcePrefixValue);
        GrpcMetadataProvider grpcMetadataProvider = this.metadataProvider;
        if (grpcMetadataProvider != null) {
            grpcMetadataProvider.updateMetadata(headers);
        }
        return headers;
    }
}
