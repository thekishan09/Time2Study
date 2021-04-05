package com.google.android.gms.internal.firebase_auth;

import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzp;
import com.google.firebase.auth.api.internal.zzgb;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzgk implements zzgb<zzp.zzx> {
    private boolean zza;
    private String zzb;
    private String zzc;
    private String zzd;
    private String zze;
    private String zzf;
    private boolean zzg;

    private zzgk() {
    }

    public static zzgk zza(String str, String str2, boolean z) {
        zzgk zzgk = new zzgk();
        zzgk.zza = false;
        zzgk.zzc = Preconditions.checkNotEmpty(str);
        zzgk.zzd = Preconditions.checkNotEmpty(str2);
        zzgk.zzg = z;
        return zzgk;
    }

    public static zzgk zzb(String str, String str2, boolean z) {
        zzgk zzgk = new zzgk();
        zzgk.zza = false;
        zzgk.zzb = Preconditions.checkNotEmpty(str);
        zzgk.zze = Preconditions.checkNotEmpty(str2);
        zzgk.zzg = z;
        return zzgk;
    }

    public final void zza(String str) {
        this.zzf = str;
    }

    public final /* synthetic */ zzjr zza() {
        zzp.zzx.zza zza2 = zzp.zzx.zza();
        if (!TextUtils.isEmpty(this.zze)) {
            zza2.zzd(this.zze).zzb(this.zzb);
        } else {
            zza2.zza(this.zzc).zzc(this.zzd);
        }
        String str = this.zzf;
        if (str != null) {
            zza2.zze(str);
        }
        if (!this.zzg) {
            zza2.zza(zzaa.REAUTH);
        }
        return (zzp.zzx) ((zzig) zza2.zzf());
    }
}
