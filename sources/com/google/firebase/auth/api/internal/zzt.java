package com.google.firebase.auth.api.internal;

import android.text.TextUtils;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.firebase_auth.zzff;
import com.google.android.gms.internal.firebase_auth.zzgj;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.zze;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzt implements zzfr<zzgj> {
    private final /* synthetic */ zzfr zza;
    private final /* synthetic */ zzq zzb;

    zzt(zzq zzq, zzfr zzfr) {
        this.zzb = zzq;
        this.zza = zzfr;
    }

    public final void zza(String str) {
        this.zza.zza(str);
    }

    public final /* synthetic */ void zza(Object obj) {
        zzgj zzgj = (zzgj) obj;
        if (!TextUtils.isEmpty(zzgj.zzf())) {
            this.zzb.zza.zza(new Status(FirebaseError.ERROR_CREDENTIAL_ALREADY_IN_USE), PhoneAuthCredential.zzb(zzgj.zzg(), zzgj.zzf()));
            return;
        }
        this.zzb.zzb.zza(new zzff(zzgj.zzc(), zzgj.zzb(), Long.valueOf(zzgj.zzd()), "Bearer"), (String) null, "phone", Boolean.valueOf(zzgj.zze()), (zze) null, this.zzb.zza, this.zza);
    }
}
