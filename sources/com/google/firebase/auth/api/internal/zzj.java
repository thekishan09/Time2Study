package com.google.firebase.auth.api.internal;

import android.text.TextUtils;
import com.google.android.gms.internal.firebase_auth.zzey;
import com.google.android.gms.internal.firebase_auth.zzfa;
import com.google.android.gms.internal.firebase_auth.zzff;
import com.google.android.gms.internal.firebase_auth.zzfj;
import com.google.android.gms.internal.firebase_auth.zzfl;
import com.google.firebase.auth.zze;
import java.util.List;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzj implements zzfr<zzey> {
    private final /* synthetic */ zzfo zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ String zzc;
    private final /* synthetic */ Boolean zzd;
    private final /* synthetic */ zze zze;
    private final /* synthetic */ zzee zzf;
    private final /* synthetic */ zzff zzg;

    zzj(zzb zzb2, zzfo zzfo, String str, String str2, Boolean bool, zze zze2, zzee zzee, zzff zzff) {
        this.zza = zzfo;
        this.zzb = str;
        this.zzc = str2;
        this.zzd = bool;
        this.zze = zze2;
        this.zzf = zzee;
        this.zzg = zzff;
    }

    public final void zza(String str) {
        this.zza.zza(str);
    }

    public final /* synthetic */ void zza(Object obj) {
        List<zzfa> zzb2 = ((zzey) obj).zzb();
        if (zzb2 == null || zzb2.isEmpty()) {
            this.zza.zza("No users.");
            return;
        }
        boolean z = false;
        zzfa zzfa = zzb2.get(0);
        zzfl zzk = zzfa.zzk();
        List<zzfj> zza2 = zzk != null ? zzk.zza() : null;
        if (zza2 != null && !zza2.isEmpty()) {
            if (!TextUtils.isEmpty(this.zzb)) {
                int i = 0;
                while (true) {
                    if (i >= zza2.size()) {
                        break;
                    } else if (zza2.get(i).zzd().equals(this.zzb)) {
                        zza2.get(i).zza(this.zzc);
                        break;
                    } else {
                        i++;
                    }
                }
            } else {
                zza2.get(0).zza(this.zzc);
            }
        }
        Boolean bool = this.zzd;
        if (bool != null) {
            zzfa.zza(bool.booleanValue());
        } else {
            if (zzfa.zzh() - zzfa.zzg() < 1000) {
                z = true;
            }
            zzfa.zza(z);
        }
        zzfa.zza(this.zze);
        this.zzf.zza(this.zzg, zzfa);
    }
}
