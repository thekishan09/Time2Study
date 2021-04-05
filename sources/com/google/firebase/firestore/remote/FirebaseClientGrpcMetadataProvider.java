package com.google.firebase.firestore.remote;

import com.google.firebase.heartbeatinfo.HeartBeatInfo;
import com.google.firebase.inject.Provider;
import com.google.firebase.platforminfo.UserAgentPublisher;
import p003io.grpc.Metadata;

public class FirebaseClientGrpcMetadataProvider implements GrpcMetadataProvider {
    private static final Metadata.Key<String> HEART_BEAT_HEADER = Metadata.Key.m590of("x-firebase-client-log-type", Metadata.ASCII_STRING_MARSHALLER);
    private static final Metadata.Key<String> USER_AGENT_HEADER = Metadata.Key.m590of("x-firebase-client", Metadata.ASCII_STRING_MARSHALLER);
    private final String firebaseFirestoreHeartBeatTag = "fire-fst";
    private final Provider<HeartBeatInfo> heartBeatInfoProvider;
    private final Provider<UserAgentPublisher> userAgentPublisherProvider;

    public FirebaseClientGrpcMetadataProvider(Provider<UserAgentPublisher> userAgentPublisherProvider2, Provider<HeartBeatInfo> heartBeatInfoProvider2) {
        this.userAgentPublisherProvider = userAgentPublisherProvider2;
        this.heartBeatInfoProvider = heartBeatInfoProvider2;
    }

    public void updateMetadata(Metadata metadata) {
        int heartBeatCode;
        if (this.heartBeatInfoProvider.get() != null && this.userAgentPublisherProvider.get() != null && (heartBeatCode = this.heartBeatInfoProvider.get().getHeartBeatCode("fire-fst").getCode()) != 0) {
            metadata.put(HEART_BEAT_HEADER, Integer.toString(heartBeatCode));
            metadata.put(USER_AGENT_HEADER, this.userAgentPublisherProvider.get().getUserAgent());
        }
    }
}
