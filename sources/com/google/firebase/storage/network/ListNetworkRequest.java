package com.google.firebase.storage.network;

import android.net.Uri;
import android.text.TextUtils;
import com.google.firebase.FirebaseApp;
import java.util.HashMap;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-storage@@19.1.1 */
public class ListNetworkRequest extends NetworkRequest {
    private final Integer maxPageSize;
    private final String nextPageToken;

    public ListNetworkRequest(Uri gsUri, FirebaseApp app, Integer maxPageSize2, String nextPageToken2) {
        super(gsUri, app);
        this.maxPageSize = maxPageSize2;
        this.nextPageToken = nextPageToken2;
    }

    /* access modifiers changed from: protected */
    public String getAction() {
        return "GET";
    }

    /* access modifiers changed from: protected */
    public Uri getURL() {
        return Uri.parse(sNetworkRequestUrl + "/b/" + this.mGsUri.getAuthority() + "/o");
    }

    /* access modifiers changed from: protected */
    public Map<String, String> getQueryParameters() {
        Map<String, String> headers = new HashMap<>();
        String prefix = getPathWithoutBucket();
        if (!prefix.isEmpty()) {
            headers.put("prefix", prefix + "/");
        }
        headers.put("delimiter", "/");
        Integer num = this.maxPageSize;
        if (num != null) {
            headers.put("maxResults", Integer.toString(num.intValue()));
        }
        if (!TextUtils.isEmpty(this.nextPageToken)) {
            headers.put("pageToken", this.nextPageToken);
        }
        return headers;
    }
}
