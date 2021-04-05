package com.google.android.gms.internal.firebase_auth;

import android.text.TextUtils;
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.internal.firebase_auth.zzp;
import com.google.firebase.auth.api.internal.zzen;
import java.util.ArrayList;
import java.util.List;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzgh implements zzen<zzgh, zzp.zzw> {
    private String zza;
    private String zzb;
    private String zzc;
    private String zzd;
    private String zze;
    private String zzf;
    private long zzg;
    private List<zzfh> zzh;
    private String zzi;

    public final String zzb() {
        return this.zzd;
    }

    public final String zzc() {
        return this.zzf;
    }

    public final long zzd() {
        return this.zzg;
    }

    public final List<zzfh> zze() {
        return this.zzh;
    }

    public final String zzf() {
        return this.zzi;
    }

    public final boolean zzg() {
        return !TextUtils.isEmpty(this.zzi);
    }

    public final zzkb<zzp.zzw> zza() {
        return zzp.zzw.zzj();
    }

    public final /* synthetic */ zzen zza(zzjr zzjr) {
        if (zzjr instanceof zzp.zzw) {
            zzp.zzw zzw = (zzp.zzw) zzjr;
            this.zza = Strings.emptyToNull(zzw.zza());
            this.zzb = Strings.emptyToNull(zzw.zzb());
            this.zzc = Strings.emptyToNull(zzw.zzc());
            this.zzd = Strings.emptyToNull(zzw.zzd());
            this.zze = Strings.emptyToNull(zzw.zze());
            this.zzf = Strings.emptyToNull(zzw.zzf());
            this.zzg = zzw.zzg();
            this.zzh = new ArrayList();
            for (zzr zza2 : zzw.zzi()) {
                this.zzh.add(zzfh.zza(zza2));
            }
            this.zzi = zzw.zzh();
            return this;
        }
        throw new IllegalArgumentException("The passed proto must be an instance of VerifyPasswordResponse.");
    }
}
