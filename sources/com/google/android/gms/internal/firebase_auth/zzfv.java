package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.util.Strings;
import com.google.android.gms.internal.firebase_auth.zzp;
import com.google.firebase.auth.api.internal.zzen;
import java.util.List;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzfv implements zzen<zzfv, zzp.zzo> {
    private String zza;
    private String zzb;
    private Boolean zzc;
    private String zzd;
    private String zze;
    private zzfl zzf;
    private String zzg;
    private String zzh;
    private long zzi;

    public final String zzb() {
        return this.zzg;
    }

    public final String zzc() {
        return this.zzh;
    }

    public final long zzd() {
        return this.zzi;
    }

    public final String zze() {
        return this.zza;
    }

    public final List<zzfj> zzf() {
        zzfl zzfl = this.zzf;
        if (zzfl != null) {
            return zzfl.zza();
        }
        return null;
    }

    public final zzkb<zzp.zzo> zza() {
        return zzp.zzo.zzj();
    }

    public final /* synthetic */ zzen zza(zzjr zzjr) {
        if (zzjr instanceof zzp.zzo) {
            zzp.zzo zzo = (zzp.zzo) zzjr;
            this.zza = Strings.emptyToNull(zzo.zza());
            this.zzb = Strings.emptyToNull(zzo.zzh());
            this.zzc = Boolean.valueOf(zzo.zzi());
            this.zzd = Strings.emptyToNull(zzo.zzb());
            this.zze = Strings.emptyToNull(zzo.zze());
            this.zzf = zzfl.zza(zzo.zzd());
            this.zzg = Strings.emptyToNull(zzo.zzc());
            this.zzh = Strings.emptyToNull(zzo.zzf());
            this.zzi = zzo.zzg();
            return this;
        }
        throw new IllegalArgumentException("The passed proto must be an instance of SetAccountInfoResponse.");
    }
}
