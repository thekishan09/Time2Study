package com.google.firebase.storage.network;

import android.net.Uri;
import com.google.common.net.HttpHeaders;
import com.google.firebase.FirebaseApp;
import java.util.Collections;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-storage@@19.1.1 */
public class GetNetworkRequest extends NetworkRequest {
    private static final String TAG = "GetNetworkRequest";

    public GetNetworkRequest(Uri gsUri, FirebaseApp app, long startByte) {
        super(gsUri, app);
        if (startByte != 0) {
            super.setCustomHeader(HttpHeaders.RANGE, "bytes=" + startByte + "-");
        }
    }

    /* access modifiers changed from: protected */
    public String getAction() {
        return "GET";
    }

    /* access modifiers changed from: protected */
    public Map<String, String> getQueryParameters() {
        return Collections.singletonMap("alt", "media");
    }
}
