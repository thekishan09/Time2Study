package com.google.firebase.auth;

import com.google.firebase.auth.FirebaseAuth;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzh implements Runnable {
    private final /* synthetic */ FirebaseAuth.AuthStateListener zza;
    private final /* synthetic */ FirebaseAuth zzb;

    zzh(FirebaseAuth firebaseAuth, FirebaseAuth.AuthStateListener authStateListener) {
        this.zzb = firebaseAuth;
        this.zza = authStateListener;
    }

    public final void run() {
        this.zza.onAuthStateChanged(this.zzb);
    }
}
