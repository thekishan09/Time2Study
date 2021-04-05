package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzp;
import com.google.firebase.auth.api.internal.zzgb;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzfy implements zzgb<zzp.C2507zzp> {
    private String zza;
    private String zzb;
    private String zzc;
    private String zzd;

    public zzfy(String str) {
        this.zzd = str;
    }

    public zzfy(String str, String str2, String str3, String str4) {
        this.zza = Preconditions.checkNotEmpty(str);
        this.zzb = Preconditions.checkNotEmpty(str2);
        this.zzc = null;
        this.zzd = str4;
    }

    public final /* synthetic */ zzjr zza() {
        zzp.C2507zzp.zza zza2 = zzp.C2507zzp.zza();
        String str = this.zza;
        if (str != null) {
            zza2.zza(str);
        }
        String str2 = this.zzb;
        if (str2 != null) {
            zza2.zzb(str2);
        }
        String str3 = this.zzd;
        if (str3 != null) {
            zza2.zzc(str3);
        }
        return (zzp.C2507zzp) ((zzig) zza2.zzf());
    }
}
