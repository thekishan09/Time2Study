package com.google.firebase.auth.api.internal;

import com.google.android.gms.internal.firebase_auth.zzev;
import com.google.android.gms.internal.firebase_auth.zzey;
import com.google.android.gms.internal.firebase_auth.zzff;
import com.google.firebase.auth.internal.zzy;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzx implements zzfr<zzff> {
    final /* synthetic */ String zza;
    final /* synthetic */ zzee zzb;
    final /* synthetic */ zzb zzc;

    zzx(zzb zzb2, String str, zzee zzee) {
        this.zzc = zzb2;
        this.zza = str;
        this.zzb = zzee;
    }

    public final void zza(String str) {
        this.zzb.zza(zzy.zza(str));
    }

    public final /* synthetic */ void zza(Object obj) {
        zzff zzff = (zzff) obj;
        this.zzc.zzb.zza(new zzev(zzff.zzd()), (zzfr<zzey>) new zzw(this, this, zzff));
    }
}
