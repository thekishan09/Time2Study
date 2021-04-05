package com.google.firebase.storage;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.internal.InternalAuthProvider;
import com.google.firebase.inject.Provider;
import java.util.HashMap;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-storage@@19.1.1 */
class FirebaseStorageComponent {
    private final FirebaseApp app;
    private final Provider<InternalAuthProvider> authProvider;
    private final Map<String, FirebaseStorage> instances = new HashMap();

    FirebaseStorageComponent(FirebaseApp app2, Provider<InternalAuthProvider> authProvider2) {
        this.app = app2;
        this.authProvider = authProvider2;
    }

    /* access modifiers changed from: package-private */
    public synchronized FirebaseStorage get(String bucketName) {
        FirebaseStorage storage;
        storage = this.instances.get(bucketName);
        if (storage == null) {
            storage = new FirebaseStorage(bucketName, this.app, this.authProvider);
            this.instances.put(bucketName, storage);
        }
        return storage;
    }

    /* access modifiers changed from: package-private */
    public synchronized void clearInstancesForTesting() {
        this.instances.clear();
    }
}
