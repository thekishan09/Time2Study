package com.google.firebase.auth;

import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.internal.zzaf;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzn implements zzaf {
    private final /* synthetic */ FirebaseUser zza;
    private final /* synthetic */ FirebaseAuth zzb;

    zzn(FirebaseAuth firebaseAuth, FirebaseUser firebaseUser) {
        this.zzb = firebaseAuth;
        this.zza = firebaseUser;
    }

    public final void zza() {
        if (this.zzb.zzf.getUid().equalsIgnoreCase(this.zza.getUid())) {
            this.zzb.zza();
        }
    }

    public final void zza(Status status) {
        if (status.getStatusCode() == 17011 || status.getStatusCode() == 17021 || status.getStatusCode() == 17005) {
            this.zzb.signOut();
        }
    }
}
