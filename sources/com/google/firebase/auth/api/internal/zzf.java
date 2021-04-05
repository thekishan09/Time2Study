package com.google.firebase.auth.api.internal;

import com.google.android.gms.internal.firebase_auth.zzer;
import com.google.android.gms.internal.firebase_auth.zzff;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.internal.zzy;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzf implements zzfr<zzff> {
    private final /* synthetic */ EmailAuthCredential zza;
    private final /* synthetic */ zzee zzb;
    private final /* synthetic */ zzb zzc;

    zzf(zzb zzb2, EmailAuthCredential emailAuthCredential, zzee zzee) {
        this.zzc = zzb2;
        this.zza = emailAuthCredential;
        this.zzb = zzee;
    }

    public final void zza(String str) {
        this.zzb.zza(zzy.zza(str));
    }

    public final /* synthetic */ void zza(Object obj) {
        this.zzc.zza(new zzer(this.zza, ((zzff) obj).zzd()), this.zzb);
    }
}
