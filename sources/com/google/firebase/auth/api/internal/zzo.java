package com.google.firebase.auth.api.internal;

import com.google.android.gms.internal.firebase_auth.zzff;
import com.google.android.gms.internal.firebase_auth.zzgj;
import com.google.firebase.auth.internal.zzy;
import com.google.firebase.auth.zze;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzo implements zzfr<zzgj> {
    private final /* synthetic */ zzee zza;
    private final /* synthetic */ zzb zzb;

    zzo(zzb zzb2, zzee zzee) {
        this.zzb = zzb2;
        this.zza = zzee;
    }

    public final void zza(String str) {
        this.zza.zza(zzy.zza(str));
    }

    public final /* synthetic */ void zza(Object obj) {
        zzgj zzgj = (zzgj) obj;
        this.zzb.zza(new zzff(zzgj.zzc(), zzgj.zzb(), Long.valueOf(zzgj.zzd()), "Bearer"), (String) null, (String) null, Boolean.valueOf(zzgj.zze()), (zze) null, this.zza, this);
    }
}
