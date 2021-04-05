package com.google.android.gms.internal.firebase_auth;

import android.text.TextUtils;
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.internal.firebase_auth.zzp;
import com.google.firebase.auth.api.internal.zzen;
import com.google.firebase.auth.zze;
import java.util.ArrayList;
import java.util.List;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzge implements zzen<zzge, zzp.zzs> {
    private boolean zza;
    private boolean zzb;
    private String zzc;
    private String zzd;
    private long zze;
    private String zzf;
    private String zzg;
    private String zzh;
    private String zzi;
    private String zzj;
    private String zzk;
    private boolean zzl;
    private String zzm;
    private String zzn;
    private String zzo;
    private String zzp;
    private String zzq;
    private String zzr;
    private List<zzfh> zzs;
    private String zzt;

    public final boolean zzb() {
        return this.zza;
    }

    public final String zzc() {
        return this.zzc;
    }

    public final String zzd() {
        return this.zzg;
    }

    public final String zze() {
        return this.zzj;
    }

    public final String zzf() {
        return this.zzk;
    }

    public final String zzg() {
        return this.zzd;
    }

    public final long zzh() {
        return this.zze;
    }

    public final boolean zzi() {
        return this.zzl;
    }

    public final String zzj() {
        return this.zzp;
    }

    public final boolean zzk() {
        return this.zza || !TextUtils.isEmpty(this.zzp);
    }

    public final String zzl() {
        return this.zzr;
    }

    public final List<zzfh> zzm() {
        return this.zzs;
    }

    public final String zzn() {
        return this.zzt;
    }

    public final boolean zzo() {
        return !TextUtils.isEmpty(this.zzt);
    }

    public final zze zzp() {
        if (!TextUtils.isEmpty(this.zzm) || !TextUtils.isEmpty(this.zzn)) {
            return zze.zza(this.zzj, this.zzn, this.zzm, this.zzq, this.zzo);
        }
        return null;
    }

    public final zzkb<zzp.zzs> zza() {
        return zzp.zzs.zzu();
    }

    public final /* synthetic */ zzen zza(zzjr zzjr) {
        if (zzjr instanceof zzp.zzs) {
            zzp.zzs zzs2 = (zzp.zzs) zzjr;
            this.zza = zzs2.zzg();
            this.zzb = zzs2.zzi();
            this.zzc = Strings.emptyToNull(zzs2.zzf());
            this.zzd = Strings.emptyToNull(zzs2.zzk());
            this.zze = zzs2.zzl();
            this.zzf = Strings.emptyToNull(zzs2.zzd());
            this.zzg = Strings.emptyToNull(zzs2.zzb());
            this.zzh = Strings.emptyToNull(zzs2.zze());
            this.zzi = Strings.emptyToNull(zzs2.zzc());
            this.zzj = Strings.emptyToNull(zzs2.zza());
            this.zzk = Strings.emptyToNull(zzs2.zzn());
            this.zzl = zzs2.zzp();
            this.zzm = zzs2.zzh();
            this.zzn = zzs2.zzm();
            this.zzp = Strings.emptyToNull(zzs2.zzo());
            this.zzq = Strings.emptyToNull(zzs2.zzq());
            this.zzr = Strings.emptyToNull(zzs2.zzr());
            this.zzs = new ArrayList();
            for (zzr zza2 : zzs2.zzt()) {
                this.zzs.add(zzfh.zza(zza2));
            }
            this.zzt = Strings.emptyToNull(zzs2.zzs());
            this.zzo = Strings.emptyToNull(zzs2.zzj());
            return this;
        }
        throw new IllegalArgumentException("The passed proto must be an instance of VerifyAssertionResponse.");
    }
}
