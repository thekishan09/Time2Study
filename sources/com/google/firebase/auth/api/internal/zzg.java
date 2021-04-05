package com.google.firebase.auth.api.internal;

import android.text.TextUtils;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.internal.firebase_auth.zzfa;
import com.google.android.gms.internal.firebase_auth.zzff;
import com.google.android.gms.internal.firebase_auth.zzfj;
import com.google.android.gms.internal.firebase_auth.zzfv;
import com.google.android.gms.internal.firebase_auth.zzfw;
import java.util.ArrayList;
import java.util.List;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzg implements zzfr<zzfv> {
    private final /* synthetic */ zzfw zza;
    private final /* synthetic */ zzfa zzb;
    private final /* synthetic */ zzee zzc;
    private final /* synthetic */ zzff zzd;
    private final /* synthetic */ zzfo zze;
    private final /* synthetic */ zzb zzf;

    zzg(zzb zzb2, zzfw zzfw, zzfa zzfa, zzee zzee, zzff zzff, zzfo zzfo) {
        this.zzf = zzb2;
        this.zza = zzfw;
        this.zzb = zzfa;
        this.zzc = zzee;
        this.zzd = zzff;
        this.zze = zzfo;
    }

    public final void zza(String str) {
        this.zze.zza(str);
    }

    public final /* synthetic */ void zza(Object obj) {
        zzfv zzfv = (zzfv) obj;
        if (this.zza.zza("EMAIL")) {
            this.zzb.zza((String) null);
        } else if (this.zza.zzb() != null) {
            this.zzb.zza(this.zza.zzb());
        }
        if (this.zza.zza("DISPLAY_NAME")) {
            this.zzb.zzb((String) null);
        } else if (this.zza.zzd() != null) {
            this.zzb.zzb(this.zza.zzd());
        }
        if (this.zza.zza("PHOTO_URL")) {
            this.zzb.zzc((String) null);
        } else if (this.zza.zze() != null) {
            this.zzb.zzc(this.zza.zze());
        }
        if (!TextUtils.isEmpty(this.zza.zzc())) {
            this.zzb.zzd(Base64Utils.encode("redacted".getBytes()));
        }
        List zzf2 = zzfv.zzf();
        if (zzf2 == null) {
            zzf2 = new ArrayList();
        }
        this.zzb.zza((List<zzfj>) zzf2);
        this.zzc.zza(zzb.zza(this.zzd, zzfv), this.zzb);
    }
}
