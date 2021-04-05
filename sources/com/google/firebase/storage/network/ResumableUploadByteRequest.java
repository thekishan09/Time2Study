package com.google.firebase.storage.network;

import android.net.Uri;
import com.google.firebase.FirebaseApp;
import p003io.grpc.internal.GrpcUtil;

/* compiled from: com.google.firebase:firebase-storage@@19.1.1 */
public class ResumableUploadByteRequest extends ResumableNetworkRequest {
    private final int bytesToWrite;
    private final byte[] chunk;
    private final boolean isFinal;
    private final long offset;
    private final Uri uploadURL;

    public ResumableUploadByteRequest(Uri gsUri, FirebaseApp app, Uri uploadURL2, byte[] chunk2, long offset2, int bytesToWrite2, boolean isFinal2) {
        super(gsUri, app);
        if (chunk2 == null && bytesToWrite2 != -1) {
            this.mException = new IllegalArgumentException("contentType is null or empty");
        }
        if (offset2 < 0) {
            this.mException = new IllegalArgumentException("offset cannot be negative");
        }
        this.bytesToWrite = bytesToWrite2;
        this.uploadURL = uploadURL2;
        this.chunk = bytesToWrite2 <= 0 ? null : chunk2;
        this.offset = offset2;
        this.isFinal = isFinal2;
        super.setCustomHeader("X-Goog-Upload-Protocol", "resumable");
        if (this.isFinal && this.bytesToWrite > 0) {
            super.setCustomHeader("X-Goog-Upload-Command", "upload, finalize");
        } else if (this.isFinal) {
            super.setCustomHeader("X-Goog-Upload-Command", "finalize");
        } else {
            super.setCustomHeader("X-Goog-Upload-Command", "upload");
        }
        super.setCustomHeader("X-Goog-Upload-Offset", Long.toString(this.offset));
    }

    /* access modifiers changed from: protected */
    public String getAction() {
        return GrpcUtil.HTTP_METHOD;
    }

    /* access modifiers changed from: protected */
    public Uri getURL() {
        return this.uploadURL;
    }

    /* access modifiers changed from: protected */
    public byte[] getOutputRaw() {
        return this.chunk;
    }

    /* access modifiers changed from: protected */
    public int getOutputRawSize() {
        int i = this.bytesToWrite;
        if (i > 0) {
            return i;
        }
        return 0;
    }
}
