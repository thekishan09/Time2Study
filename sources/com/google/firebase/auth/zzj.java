package com.google.firebase.auth;

import com.google.firebase.auth.FirebaseAuth;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzj implements Runnable {
    private final /* synthetic */ FirebaseAuth zza;

    zzj(FirebaseAuth firebaseAuth) {
        this.zza = firebaseAuth;
    }

    public final void run() {
        for (FirebaseAuth.AuthStateListener onAuthStateChanged : this.zza.zzd) {
            onAuthStateChanged.onAuthStateChanged(this.zza);
        }
    }
}
