package com.google.firebase.auth.api.internal;

import com.google.android.gms.internal.firebase_auth.zzff;
import com.google.android.gms.internal.firebase_auth.zzfw;
import com.google.firebase.auth.internal.zzy;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzag implements zzfr<zzff> {
    private final /* synthetic */ String zza;
    private final /* synthetic */ zzee zzb;
    private final /* synthetic */ zzb zzc;

    zzag(zzb zzb2, String str, zzee zzee) {
        this.zzc = zzb2;
        this.zza = str;
        this.zzb = zzee;
    }

    public final void zza(String str) {
        this.zzb.zza(zzy.zza(str));
    }

    public final /* synthetic */ void zza(Object obj) {
        zzff zzff = (zzff) obj;
        zzfw zzfw = new zzfw();
        zzfw.zzb(zzff.zzd()).zzc(this.zza);
        this.zzc.zza(this.zzb, zzff, zzfw, (zzfo) this);
    }
}
