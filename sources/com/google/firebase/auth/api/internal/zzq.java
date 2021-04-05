package com.google.firebase.auth.api.internal;

import android.content.Context;
import com.google.android.gms.internal.firebase_auth.zzff;
import com.google.android.gms.internal.firebase_auth.zzgj;
import com.google.android.gms.internal.firebase_auth.zzgk;
import com.google.firebase.auth.internal.zzy;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzq implements zzfr<zzff> {
    final /* synthetic */ zzee zza;
    final /* synthetic */ zzb zzb;
    private final /* synthetic */ zzgk zzc;
    private final /* synthetic */ Context zzd = null;

    zzq(zzb zzb2, zzgk zzgk, Context context, zzee zzee) {
        this.zzb = zzb2;
        this.zzc = zzgk;
        this.zza = zzee;
    }

    public final void zza(String str) {
        this.zza.zza(zzy.zza(str));
    }

    public final /* synthetic */ void zza(Object obj) {
        this.zzc.zza(((zzff) obj).zzd());
        this.zzb.zzb.zza(this.zzd, this.zzc, (zzfr<zzgj>) new zzt(this, this));
    }
}
