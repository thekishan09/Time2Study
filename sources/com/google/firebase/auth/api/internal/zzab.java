package com.google.firebase.auth.api.internal;

import com.google.android.gms.internal.firebase_auth.zzey;
import com.google.android.gms.internal.firebase_auth.zzfa;
import com.google.android.gms.internal.firebase_auth.zzff;
import java.util.List;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzab implements zzfr<zzey> {
    private final /* synthetic */ zzfr zza;
    private final /* synthetic */ zzff zzb;
    private final /* synthetic */ zzy zzc;

    zzab(zzy zzy, zzfr zzfr, zzff zzff) {
        this.zzc = zzy;
        this.zza = zzfr;
        this.zzb = zzff;
    }

    public final void zza(String str) {
        this.zza.zza(str);
    }

    public final /* synthetic */ void zza(Object obj) {
        List<zzfa> zzb2 = ((zzey) obj).zzb();
        if (zzb2 == null || zzb2.isEmpty()) {
            this.zza.zza("No users");
        } else {
            this.zzc.zza.zza(this.zzb, zzb2.get(0));
        }
    }
}
