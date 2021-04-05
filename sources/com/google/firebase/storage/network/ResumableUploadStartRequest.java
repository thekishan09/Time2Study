package com.google.firebase.storage.network;

import android.net.Uri;
import android.text.TextUtils;
import com.google.firebase.FirebaseApp;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import p003io.grpc.internal.GrpcUtil;

/* compiled from: com.google.firebase:firebase-storage@@19.1.1 */
public class ResumableUploadStartRequest extends ResumableNetworkRequest {
    private final String contentType;
    private final JSONObject metadata;

    public ResumableUploadStartRequest(Uri gsUri, FirebaseApp app, JSONObject metadata2, String contentType2) {
        super(gsUri, app);
        this.metadata = metadata2;
        this.contentType = contentType2;
        if (TextUtils.isEmpty(contentType2)) {
            this.mException = new IllegalArgumentException("mContentType is null or empty");
        }
        super.setCustomHeader("X-Goog-Upload-Protocol", "resumable");
        super.setCustomHeader("X-Goog-Upload-Command", "start");
        super.setCustomHeader("X-Goog-Upload-Header-Content-Type", this.contentType);
    }

    /* access modifiers changed from: protected */
    public Uri getURL() {
        Uri.Builder uriBuilder = sNetworkRequestUrl.buildUpon();
        uriBuilder.appendPath("b");
        uriBuilder.appendPath(this.mGsUri.getAuthority());
        uriBuilder.appendPath("o");
        return uriBuilder.build();
    }

    /* access modifiers changed from: protected */
    public String getAction() {
        return GrpcUtil.HTTP_METHOD;
    }

    /* access modifiers changed from: protected */
    public Map<String, String> getQueryParameters() {
        Map<String, String> headers = new HashMap<>();
        headers.put("name", getPathWithoutBucket());
        headers.put("uploadType", "resumable");
        return headers;
    }

    /* access modifiers changed from: protected */
    public JSONObject getOutputJSON() {
        return this.metadata;
    }
}
