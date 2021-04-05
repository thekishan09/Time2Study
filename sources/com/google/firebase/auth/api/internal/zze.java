package com.google.firebase.auth.api.internal;

import com.google.android.gms.internal.firebase_auth.zzem;
import com.google.android.gms.internal.firebase_auth.zzeu;
import com.google.android.gms.internal.firebase_auth.zzff;
import com.google.firebase.auth.internal.zzy;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zze implements zzfr<zzeu> {
    private final /* synthetic */ zzee zza;
    private final /* synthetic */ zzb zzb;

    zze(zzb zzb2, zzee zzee) {
        this.zzb = zzb2;
        this.zza = zzee;
    }

    public final void zza(String str) {
        this.zza.zza(zzy.zza(str));
    }

    public final /* synthetic */ void zza(Object obj) {
        zzeu zzeu = (zzeu) obj;
        if (!zzeu.zzh()) {
            this.zzb.zza(new zzff(zzeu.zzc(), zzeu.zzb(), Long.valueOf(zzeu.zze()), "Bearer"), (String) null, (String) null, Boolean.valueOf(zzeu.zzd()), (com.google.firebase.auth.zze) null, this.zza, this);
        } else if (this.zzb.zzc.zzb()) {
            this.zza.zza(new zzem(zzeu.zzg(), zzeu.zzf(), (com.google.firebase.auth.zze) null));
        } else {
            zzb.zza.mo11929e("Need to do multi-factor auth, but either the SDK does not support it, or the flag is disabled.", new Object[0]);
            zza("REQUIRES_SECOND_FACTOR_AUTH");
        }
    }
}
