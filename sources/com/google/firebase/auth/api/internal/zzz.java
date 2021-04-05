package com.google.firebase.auth.api.internal;

import com.google.android.gms.internal.firebase_auth.zzem;
import com.google.android.gms.internal.firebase_auth.zzge;
import com.google.firebase.auth.internal.zzy;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzz implements zzfr<zzge> {
    private final /* synthetic */ zzee zza;
    private final /* synthetic */ zzb zzb;

    zzz(zzb zzb2, zzee zzee) {
        this.zzb = zzb2;
        this.zza = zzee;
    }

    public final void zza(String str) {
        this.zza.zza(zzy.zza(str));
    }

    public final /* synthetic */ void zza(Object obj) {
        zzge zzge = (zzge) obj;
        if (!zzge.zzo()) {
            this.zzb.zza(zzge, this.zza, (zzfo) this);
        } else if (this.zzb.zzc.zzb()) {
            this.zza.zza(new zzem(zzge.zzn(), zzge.zzm(), zzge.zzp()));
        } else {
            zzb.zza.mo11929e("Need to do multi-factor auth, but SDK does not support it.", new Object[0]);
            zza("REQUIRES_SECOND_FACTOR_AUTH");
        }
    }
}
