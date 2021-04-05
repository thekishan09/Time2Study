package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzci;
import com.google.android.gms.internal.firebase_auth.zze;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.internal.zzb;
import com.google.firebase.auth.internal.zzj;
import com.google.firebase.auth.internal.zzp;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzba extends zzff<AuthResult, zzb> {
    final zzci zza;

    public zzba(String str, String str2, String str3) {
        super(2);
        Preconditions.checkNotEmpty(str, "email cannot be null or empty");
        Preconditions.checkNotEmpty(str2, "password cannot be null or empty");
        this.zza = new zzci(str, str2, str3);
    }

    public final String zza() {
        return "createUserWithEmailAndPassword";
    }

    public final TaskApiCall<zzeh, AuthResult> zzb() {
        return TaskApiCall.builder().setAutoResolveMissingFeatures(false).setFeatures((this.zzu || this.zzv) ? null : new Feature[]{zze.zza}).run(new zzbd(this)).build();
    }

    public final void zze() {
        zzp zza2 = zzas.zza(this.zzd, this.zzl);
        ((zzb) this.zzf).zza(this.zzk, zza2);
        zzb(new zzj(zza2));
    }
}
