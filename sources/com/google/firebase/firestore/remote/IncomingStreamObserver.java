package com.google.firebase.firestore.remote;

import p003io.grpc.Metadata;
import p003io.grpc.Status;

interface IncomingStreamObserver<RespT> {
    void onClose(Status status);

    void onHeaders(Metadata metadata);

    void onNext(RespT respt);

    void onOpen();
}