package com.google.firebase.auth.api.internal;

import com.google.android.gms.internal.firebase_auth.zzev;
import com.google.android.gms.internal.firebase_auth.zzey;
import com.google.android.gms.internal.firebase_auth.zzff;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzy implements zzfr<zzff> {
    final /* synthetic */ zzee zza;
    private final /* synthetic */ zzb zzb;

    zzy(zzb zzb2, zzee zzee) {
        this.zzb = zzb2;
        this.zza = zzee;
    }

    public final void zza(String str) {
        this.zza.zza(com.google.firebase.auth.internal.zzy.zza(str));
    }

    public final /* synthetic */ void zza(Object obj) {
        zzff zzff = (zzff) obj;
        this.zzb.zzb.zza(new zzev(zzff.zzd()), (zzfr<zzey>) new zzab(this, this, zzff));
    }
}
