package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.internal.firebase_auth.zze;
import com.google.firebase.auth.internal.zzaf;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzbc extends zzff<Void, zzaf> {
    public zzbc() {
        super(5);
    }

    public final String zza() {
        return "delete";
    }

    public final TaskApiCall<zzeh, Void> zzb() {
        return TaskApiCall.builder().setAutoResolveMissingFeatures(false).setFeatures((this.zzu || this.zzv) ? null : new Feature[]{zze.zza}).run(new zzbf(this)).build();
    }

    public final void zze() {
        ((zzaf) this.zzf).zza();
        zzb(null);
    }
}
