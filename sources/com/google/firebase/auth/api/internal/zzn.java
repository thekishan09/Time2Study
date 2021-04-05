package com.google.firebase.auth.api.internal;

import com.google.android.gms.internal.firebase_auth.zzff;
import com.google.android.gms.internal.firebase_auth.zzgg;
import com.google.firebase.auth.internal.zzy;
import com.google.firebase.auth.zze;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzn implements zzfr<zzgg> {
    private final /* synthetic */ zzee zza;
    private final /* synthetic */ zzb zzb;

    zzn(zzb zzb2, zzee zzee) {
        this.zzb = zzb2;
        this.zza = zzee;
    }

    public final void zza(String str) {
        this.zza.zza(zzy.zza(str));
    }

    public final /* synthetic */ void zza(Object obj) {
        zzgg zzgg = (zzgg) obj;
        this.zzb.zza(new zzff(zzgg.zzc(), zzgg.zzb(), Long.valueOf(zzgg.zzd()), "Bearer"), (String) null, (String) null, Boolean.valueOf(zzgg.zze()), (zze) null, this.zza, this);
    }
}
