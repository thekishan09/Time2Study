package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.internal.firebase_auth.zze;
import com.google.firebase.auth.internal.zzb;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzck extends zzff<Void, zzb> {
    public zzck() {
        super(2);
    }

    public final String zza() {
        return "reload";
    }

    public final TaskApiCall<zzeh, Void> zzb() {
        return TaskApiCall.builder().setAutoResolveMissingFeatures(false).setFeatures((this.zzu || this.zzv) ? null : new Feature[]{zze.zza}).run(new zzcn(this)).build();
    }

    public final void zze() {
        ((zzb) this.zzf).zza(this.zzk, zzas.zza(this.zzd, this.zzl));
        zzb(null);
    }
}
