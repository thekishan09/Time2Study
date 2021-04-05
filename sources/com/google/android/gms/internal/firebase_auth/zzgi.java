package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzp;
import com.google.firebase.auth.api.internal.zzgb;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzgi implements zzgb<zzp.zzv> {
    private String zza;
    private String zzb;
    private final String zzc;
    private boolean zzd = true;

    public zzgi(String str, String str2, String str3) {
        this.zza = Preconditions.checkNotEmpty(str);
        this.zzb = Preconditions.checkNotEmpty(str2);
        this.zzc = str3;
    }

    public final /* synthetic */ zzjr zza() {
        zzp.zzv.zza zza2 = zzp.zzv.zza().zza(this.zza).zzb(this.zzb).zza(this.zzd);
        String str = this.zzc;
        if (str != null) {
            zza2.zzc(str);
        }
        return (zzp.zzv) ((zzig) zza2.zzf());
    }
}
