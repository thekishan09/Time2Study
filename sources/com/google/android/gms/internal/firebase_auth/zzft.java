package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzp;
import com.google.firebase.auth.api.internal.zzen;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzft implements zzen<zzft, zzp.zzm> {
    private String zza;

    public final String zzb() {
        return this.zza;
    }

    public final zzkb<zzp.zzm> zza() {
        return zzp.zzm.zzb();
    }

    public final /* synthetic */ zzen zza(zzjr zzjr) {
        if (zzjr instanceof zzp.zzm) {
            this.zza = ((zzp.zzm) zzjr).zza();
            return this;
        }
        throw new IllegalArgumentException("The passed proto must be an instance of SendVerificationCodeResponse.");
    }
}
