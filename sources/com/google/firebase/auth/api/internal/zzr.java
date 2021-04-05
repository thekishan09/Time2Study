package com.google.firebase.auth.api.internal;

import com.google.android.gms.internal.firebase_auth.zzff;
import com.google.android.gms.internal.firebase_auth.zzfw;
import com.google.firebase.auth.internal.zzy;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzr implements zzfr<zzff> {
    private final /* synthetic */ String zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ zzee zzc;
    private final /* synthetic */ zzb zzd;

    zzr(zzb zzb2, String str, String str2, zzee zzee) {
        this.zzd = zzb2;
        this.zza = str;
        this.zzb = str2;
        this.zzc = zzee;
    }

    public final void zza(String str) {
        this.zzc.zza(zzy.zza(str));
    }

    public final /* synthetic */ void zza(Object obj) {
        zzff zzff = (zzff) obj;
        zzfw zzfw = new zzfw();
        zzfw.zzb(zzff.zzd()).zzc(this.zza).zzd(this.zzb);
        this.zzd.zza(this.zzc, zzff, zzfw, (zzfo) this);
    }
}
