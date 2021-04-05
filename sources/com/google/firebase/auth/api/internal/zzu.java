package com.google.firebase.auth.api.internal;

import com.google.android.gms.internal.firebase_auth.zzff;
import com.google.android.gms.internal.firebase_auth.zzfw;
import com.google.firebase.auth.internal.zzy;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzu implements zzfr<zzff> {
    private final /* synthetic */ zzee zza;
    private final /* synthetic */ zzb zzb;

    zzu(zzb zzb2, zzee zzee) {
        this.zzb = zzb2;
        this.zza = zzee;
    }

    public final void zza(String str) {
        this.zza.zza(zzy.zza(str));
    }

    public final /* synthetic */ void zza(Object obj) {
        zzff zzff = (zzff) obj;
        zzfw zzfw = new zzfw();
        zzfw.zzb(zzff.zzd()).zzc((String) null).zzd((String) null);
        this.zzb.zza(this.zza, zzff, zzfw, (zzfo) this);
    }
}
