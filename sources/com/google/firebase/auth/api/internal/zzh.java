package com.google.firebase.auth.api.internal;

import com.google.android.gms.internal.firebase_auth.zzey;
import com.google.android.gms.internal.firebase_auth.zzfa;
import com.google.android.gms.internal.firebase_auth.zzff;
import com.google.android.gms.internal.firebase_auth.zzfw;
import java.util.List;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzh implements zzfr<zzey> {
    private final /* synthetic */ zzfo zza;
    private final /* synthetic */ zzee zzb;
    private final /* synthetic */ zzff zzc;
    private final /* synthetic */ zzfw zzd;
    private final /* synthetic */ zzb zze;

    zzh(zzb zzb2, zzfo zzfo, zzee zzee, zzff zzff, zzfw zzfw) {
        this.zze = zzb2;
        this.zza = zzfo;
        this.zzb = zzee;
        this.zzc = zzff;
        this.zzd = zzfw;
    }

    public final void zza(String str) {
        this.zza.zza(str);
    }

    public final /* synthetic */ void zza(Object obj) {
        List<zzfa> zzb2 = ((zzey) obj).zzb();
        if (zzb2 == null || zzb2.isEmpty()) {
            this.zza.zza("No users");
        } else {
            this.zze.zza(this.zzb, this.zzc, zzb2.get(0), this.zzd, this.zza);
        }
    }
}
