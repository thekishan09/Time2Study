package com.google.firebase.storage;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.internal.InternalAuthProvider;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.components.Dependency;
import com.google.firebase.platforminfo.LibraryVersionComponent;
import java.util.Arrays;
import java.util.List;

/* compiled from: com.google.firebase:firebase-storage@@19.1.1 */
public class StorageRegistrar implements ComponentRegistrar {
    public List<Component<?>> getComponents() {
        return Arrays.asList(new Component[]{Component.builder(FirebaseStorageComponent.class).add(Dependency.required(FirebaseApp.class)).add(Dependency.optionalProvider(InternalAuthProvider.class)).factory(StorageRegistrar$$Lambda$1.lambdaFactory$()).build(), LibraryVersionComponent.create("fire-gcs", BuildConfig.VERSION_NAME)});
    }

    static /* synthetic */ FirebaseStorageComponent lambda$getComponents$0(ComponentContainer c) {
        return new FirebaseStorageComponent((FirebaseApp) c.get(FirebaseApp.class), c.getProvider(InternalAuthProvider.class));
    }
}
