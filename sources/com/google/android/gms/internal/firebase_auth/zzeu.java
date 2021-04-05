package com.google.android.gms.internal.firebase_auth;

import android.text.TextUtils;
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.internal.firebase_auth.zzp;
import com.google.firebase.auth.api.internal.zzen;
import java.util.ArrayList;
import java.util.List;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzeu implements zzen<zzeu, zzp.zze> {
    private String zza;
    private String zzb;
    private String zzc;
    private String zzd;
    private boolean zze;
    private long zzf;
    private List<zzfh> zzg;
    private String zzh;

    public final String zzb() {
        return this.zzc;
    }

    public final String zzc() {
        return this.zzd;
    }

    public final boolean zzd() {
        return this.zze;
    }

    public final long zze() {
        return this.zzf;
    }

    public final List<zzfh> zzf() {
        return this.zzg;
    }

    public final String zzg() {
        return this.zzh;
    }

    public final boolean zzh() {
        return !TextUtils.isEmpty(this.zzh);
    }

    public final zzkb<zzp.zze> zza() {
        return zzp.zze.zzi();
    }

    public final /* synthetic */ zzen zza(zzjr zzjr) {
        if (zzjr instanceof zzp.zze) {
            zzp.zze zze2 = (zzp.zze) zzjr;
            this.zza = Strings.emptyToNull(zze2.zze());
            this.zzb = Strings.emptyToNull(zze2.zzb());
            this.zzc = Strings.emptyToNull(zze2.zza());
            this.zzd = Strings.emptyToNull(zze2.zzc());
            this.zze = zze2.zzf();
            this.zzf = zze2.zzd();
            this.zzg = new ArrayList();
            for (zzr zza2 : zze2.zzh()) {
                this.zzg.add(zzfh.zza(zza2));
            }
            this.zzh = zze2.zzg();
            return this;
        }
        throw new IllegalArgumentException("The passed proto must be an instance of EmailLinkSigninResponse.");
    }
}
