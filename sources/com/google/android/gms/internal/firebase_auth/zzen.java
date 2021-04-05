package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzp;
import com.google.firebase.auth.api.internal.zzgb;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzen implements zzgb<zzp.zza> {
    private String zza;
    private String zzb = "http://localhost";
    private final String zzc;

    public zzen(String str, String str2) {
        this.zza = Preconditions.checkNotEmpty(str);
        this.zzc = str2;
    }

    public final /* synthetic */ zzjr zza() {
        zzp.zza.C2504zza zzb2 = zzp.zza.zza().zza(this.zza).zzb(this.zzb);
        String str = this.zzc;
        if (str != null) {
            zzb2.zzc(str);
        }
        return (zzp.zza) ((zzig) zzb2.zzf());
    }
}
