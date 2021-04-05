package com.google.firebase.auth;

import com.google.firebase.auth.FirebaseAuth;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzi implements Runnable {
    private final /* synthetic */ FirebaseAuth.IdTokenListener zza;
    private final /* synthetic */ FirebaseAuth zzb;

    zzi(FirebaseAuth firebaseAuth, FirebaseAuth.IdTokenListener idTokenListener) {
        this.zzb = firebaseAuth;
        this.zza = idTokenListener;
    }

    public final void run() {
        this.zza.onIdTokenChanged(this.zzb);
    }
}
