package com.google.firebase.storage.network;

import android.net.Uri;
import com.google.firebase.FirebaseApp;
import p003io.grpc.internal.GrpcUtil;

/* compiled from: com.google.firebase:firebase-storage@@19.1.1 */
public class ResumableUploadCancelRequest extends ResumableNetworkRequest {
    public static boolean cancelCalled = false;
    private final Uri uploadURL;

    public ResumableUploadCancelRequest(Uri gsUri, FirebaseApp app, Uri uploadURL2) {
        super(gsUri, app);
        cancelCalled = true;
        this.uploadURL = uploadURL2;
        super.setCustomHeader("X-Goog-Upload-Protocol", "resumable");
        super.setCustomHeader("X-Goog-Upload-Command", "cancel");
    }

    /* access modifiers changed from: protected */
    public String getAction() {
        return GrpcUtil.HTTP_METHOD;
    }

    /* access modifiers changed from: protected */
    public Uri getURL() {
        return this.uploadURL;
    }
}
