package com.google.firebase.firestore.remote;

import p003io.grpc.Metadata;

public interface GrpcMetadataProvider {
    void updateMetadata(Metadata metadata);
}
