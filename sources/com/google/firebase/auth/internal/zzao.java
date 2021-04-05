package com.google.firebase.auth.internal;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzao implements Runnable {
    private final /* synthetic */ FederatedSignInActivity zza;

    zzao(FederatedSignInActivity federatedSignInActivity) {
        this.zza = federatedSignInActivity;
    }

    public final void run() {
        this.zza.zza();
        Runnable unused = FederatedSignInActivity.zze = null;
    }
}
