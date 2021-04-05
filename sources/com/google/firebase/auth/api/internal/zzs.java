package com.google.firebase.auth.api.internal;

import android.content.Context;
import com.google.android.gms.internal.firebase_auth.zzff;
import com.google.android.gms.internal.firebase_auth.zzgc;
import com.google.android.gms.internal.firebase_auth.zzge;
import com.google.firebase.auth.internal.zzy;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzs implements zzfr<zzff> {
    final /* synthetic */ zzee zza;
    final /* synthetic */ zzb zzb;
    private final /* synthetic */ zzgc zzc;

    zzs(zzb zzb2, zzgc zzgc, zzee zzee) {
        this.zzb = zzb2;
        this.zzc = zzgc;
        this.zza = zzee;
    }

    public final void zza(String str) {
        this.zza.zza(zzy.zza(str));
    }

    public final /* synthetic */ void zza(Object obj) {
        zzff zzff = (zzff) obj;
        if (this.zzb.zzc.zza()) {
            this.zzc.zzc(true);
        }
        this.zzc.zza(zzff.zzd());
        this.zzb.zzb.zza((Context) null, this.zzc, (zzfr<zzge>) new zzv(this, this));
    }
}
