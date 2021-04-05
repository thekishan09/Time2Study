package com.google.firebase.storage.network;

import android.net.Uri;
import com.google.firebase.FirebaseApp;
import org.json.JSONObject;

/* compiled from: com.google.firebase:firebase-storage@@19.1.1 */
public class UpdateMetadataNetworkRequest extends NetworkRequest {
    private final JSONObject metadata;

    public UpdateMetadataNetworkRequest(Uri gsUri, FirebaseApp app, JSONObject metadata2) {
        super(gsUri, app);
        this.metadata = metadata2;
        setCustomHeader("X-HTTP-Method-Override", "PATCH");
    }

    /* access modifiers changed from: protected */
    public String getAction() {
        return "PUT";
    }

    /* access modifiers changed from: protected */
    public JSONObject getOutputJSON() {
        return this.metadata;
    }
}
