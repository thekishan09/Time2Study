package com.google.firebase.auth.api.internal;

import com.google.android.gms.internal.firebase_auth.zzff;
import com.google.android.gms.internal.firebase_auth.zzfx;
import com.google.firebase.auth.internal.zzy;
import com.google.firebase.auth.zze;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzd implements zzfr<zzfx> {
    private final /* synthetic */ zzee zza;
    private final /* synthetic */ zzb zzb;

    zzd(zzb zzb2, zzee zzee) {
        this.zzb = zzb2;
        this.zza = zzee;
    }

    public final void zza(String str) {
        this.zza.zza(zzy.zza(str));
    }

    public final /* synthetic */ void zza(Object obj) {
        zzfx zzfx = (zzfx) obj;
        this.zzb.zza(new zzff(zzfx.zzc(), zzfx.zzb(), Long.valueOf(zzfx.zzd()), "Bearer"), (String) null, (String) null, true, (zze) null, this.zza, this);
    }
}
