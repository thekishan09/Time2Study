package com.google.firebase.auth.api.internal;

import com.google.android.gms.internal.firebase_auth.zzey;
import com.google.android.gms.internal.firebase_auth.zzfa;
import com.google.android.gms.internal.firebase_auth.zzff;
import com.google.android.gms.internal.firebase_auth.zzfw;
import com.google.firebase.auth.internal.zzy;
import java.util.List;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzw implements zzfr<zzey> {
    private final /* synthetic */ zzfr zza;
    private final /* synthetic */ zzff zzb;
    private final /* synthetic */ zzx zzc;

    zzw(zzx zzx, zzfr zzfr, zzff zzff) {
        this.zzc = zzx;
        this.zza = zzfr;
        this.zzb = zzff;
    }

    public final void zza(String str) {
        this.zzc.zzb.zza(zzy.zza(str));
    }

    public final /* synthetic */ void zza(Object obj) {
        List<zzfa> zzb2 = ((zzey) obj).zzb();
        if (zzb2 == null || zzb2.isEmpty()) {
            this.zza.zza("No users.");
            return;
        }
        zzfw zzfw = new zzfw();
        zzfw.zzb(this.zzb.zzd()).zzg(this.zzc.zza);
        this.zzc.zzc.zza(this.zzc.zzb, this.zzb, zzb2.get(0), zzfw, (zzfo) this.zza);
    }
}
