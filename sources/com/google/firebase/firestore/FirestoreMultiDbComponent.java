package com.google.firebase.firestore;

import android.content.Context;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseAppLifecycleListener;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.internal.InternalAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.remote.GrpcMetadataProvider;
import java.util.HashMap;
import java.util.Map;

class FirestoreMultiDbComponent implements FirebaseAppLifecycleListener, FirebaseFirestore.InstanceRegistry {
    private final FirebaseApp app;
    private final InternalAuthProvider authProvider;
    private final Context context;
    private final Map<String, FirebaseFirestore> instances = new HashMap();
    private final GrpcMetadataProvider metadataProvider;

    FirestoreMultiDbComponent(Context context2, FirebaseApp app2, InternalAuthProvider authProvider2, GrpcMetadataProvider metadataProvider2) {
        this.context = context2;
        this.app = app2;
        this.authProvider = authProvider2;
        this.metadataProvider = metadataProvider2;
        app2.addLifecycleEventListener(this);
    }

    /* access modifiers changed from: package-private */
    public synchronized FirebaseFirestore get(String databaseId) {
        FirebaseFirestore firestore;
        firestore = this.instances.get(databaseId);
        if (firestore == null) {
            firestore = FirebaseFirestore.newInstance(this.context, this.app, this.authProvider, databaseId, this, this.metadataProvider);
            this.instances.put(databaseId, firestore);
        }
        return firestore;
    }

    public synchronized void remove(String databaseId) {
        this.instances.remove(databaseId);
    }

    public synchronized void onDeleted(String firebaseAppName, FirebaseOptions options) {
        for (Map.Entry<String, FirebaseFirestore> entry : this.instances.entrySet()) {
            entry.getValue().terminateInternal();
            this.instances.remove(entry.getKey());
        }
    }
}
