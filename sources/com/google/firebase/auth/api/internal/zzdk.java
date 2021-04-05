package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.internal.firebase_auth.zze;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.internal.zzb;
import com.google.firebase.auth.internal.zzj;
import com.google.firebase.auth.internal.zzp;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzdk extends zzff<AuthResult, zzb> {
    public zzdk() {
        super(2);
    }

    public final String zza() {
        return "unlinkEmailCredential";
    }

    public final TaskApiCall<zzeh, AuthResult> zzb() {
        return TaskApiCall.builder().setAutoResolveMissingFeatures(false).setFeatures((this.zzu || this.zzv) ? null : new Feature[]{zze.zza}).run(new zzdn(this)).build();
    }

    public final void zze() {
        zzp zza = zzas.zza(this.zzd, this.zzl);
        ((zzb) this.zzf).zza(this.zzk, zza);
        zzb(new zzj(zza));
    }
}
