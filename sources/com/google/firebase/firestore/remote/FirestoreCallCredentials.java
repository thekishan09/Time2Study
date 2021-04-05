package com.google.firebase.firestore.remote;

import com.google.common.net.HttpHeaders;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.firestore.auth.CredentialsProvider;
import com.google.firebase.firestore.util.Logger;
import com.google.firebase.internal.api.FirebaseNoSignedInUserException;
import java.util.concurrent.Executor;
import p003io.grpc.CallCredentials;
import p003io.grpc.Metadata;
import p003io.grpc.Status;

final class FirestoreCallCredentials extends CallCredentials {
    private static final Metadata.Key<String> AUTHORIZATION_HEADER = Metadata.Key.m590of(HttpHeaders.AUTHORIZATION, Metadata.ASCII_STRING_MARSHALLER);
    private static final String LOG_TAG = "FirestoreCallCredentials";
    private final CredentialsProvider credentialsProvider;

    FirestoreCallCredentials(CredentialsProvider provider) {
        this.credentialsProvider = provider;
    }

    public void thisUsesUnstableApi() {
    }

    public void applyRequestMetadata(CallCredentials.RequestInfo requestInfo, Executor executor, CallCredentials.MetadataApplier metadataApplier) {
        this.credentialsProvider.getToken().addOnSuccessListener(executor, FirestoreCallCredentials$$Lambda$1.lambdaFactory$(metadataApplier)).addOnFailureListener(executor, FirestoreCallCredentials$$Lambda$2.lambdaFactory$(metadataApplier));
    }

    static /* synthetic */ void lambda$applyRequestMetadata$0(CallCredentials.MetadataApplier metadataApplier, String token) {
        Logger.debug(LOG_TAG, "Successfully fetched token.", new Object[0]);
        Metadata metadata = new Metadata();
        if (token != null) {
            Metadata.Key<String> key = AUTHORIZATION_HEADER;
            metadata.put(key, "Bearer " + token);
        }
        metadataApplier.apply(metadata);
    }

    static /* synthetic */ void lambda$applyRequestMetadata$1(CallCredentials.MetadataApplier metadataApplier, Exception exception) {
        if (exception instanceof FirebaseApiNotAvailableException) {
            Logger.debug(LOG_TAG, "Firebase Auth API not available, not using authentication.", new Object[0]);
            metadataApplier.apply(new Metadata());
        } else if (exception instanceof FirebaseNoSignedInUserException) {
            Logger.debug(LOG_TAG, "No user signed in, not using authentication.", new Object[0]);
            metadataApplier.apply(new Metadata());
        } else {
            Logger.warn(LOG_TAG, "Failed to get token: %s.", exception);
            metadataApplier.fail(Status.UNAUTHENTICATED.withCause(exception));
        }
    }
}
