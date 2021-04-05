package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzp;
import com.google.firebase.auth.api.internal.zzgb;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzgd implements zzgb<zzp.zzt> {
    private String zza;
    private String zzb;

    public zzgd(String str, String str2) {
        this.zza = Preconditions.checkNotEmpty(str);
        this.zzb = str2;
    }

    public final /* synthetic */ zzjr zza() {
        zzp.zzt.zza zza2 = zzp.zzt.zza().zza(this.zza).zza(true);
        String str = this.zzb;
        if (str != null) {
            zza2.zzb(str);
        }
        return (zzp.zzt) ((zzig) zza2.zzf());
    }
}
