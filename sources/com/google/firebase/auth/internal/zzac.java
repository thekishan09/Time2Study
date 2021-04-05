package com.google.firebase.auth.internal;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.FirebaseNetworkException;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzac implements OnFailureListener {
    private final /* synthetic */ zzad zza;

    zzac(zzad zzad) {
        this.zza = zzad;
    }

    public final void onFailure(Exception exc) {
        if (exc instanceof FirebaseNetworkException) {
            zzaa.zzc.mo11935v("Failure to refresh token; scheduling refresh after failure", new Object[0]);
            this.zza.zza.zzb();
        }
    }
}
