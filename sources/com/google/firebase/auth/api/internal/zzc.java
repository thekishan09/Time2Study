package com.google.firebase.auth.api.internal;

import com.google.android.gms.internal.firebase_auth.zzem;
import com.google.android.gms.internal.firebase_auth.zzff;
import com.google.android.gms.internal.firebase_auth.zzgh;
import com.google.firebase.auth.internal.zzy;
import com.google.firebase.auth.zze;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzc implements zzfr<zzgh> {
    private final /* synthetic */ zzee zza;
    private final /* synthetic */ zzb zzb;

    zzc(zzb zzb2, zzee zzee) {
        this.zzb = zzb2;
        this.zza = zzee;
    }

    public final void zza(String str) {
        this.zza.zza(zzy.zza(str));
    }

    public final /* synthetic */ void zza(Object obj) {
        zzgh zzgh = (zzgh) obj;
        if (!zzgh.zzg()) {
            this.zzb.zza(new zzff(zzgh.zzc(), zzgh.zzb(), Long.valueOf(zzgh.zzd()), "Bearer"), (String) null, (String) null, false, (zze) null, this.zza, this);
        } else if (this.zzb.zzc.zzb()) {
            this.zza.zza(new zzem(zzgh.zzf(), zzgh.zze(), (zze) null));
        } else {
            zzb.zza.mo11929e("Need to do multi-factor auth, but SDK does not support it.", new Object[0]);
            zza("REQUIRES_SECOND_FACTOR_AUTH");
        }
    }
}
