package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzem;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzfm<ResultT, CallbackT> implements zzfd<ResultT> {
    private final zzff<ResultT, CallbackT> zza;
    private final TaskCompletionSource<ResultT> zzb;

    public zzfm(zzff<ResultT, CallbackT> zzff, TaskCompletionSource<ResultT> taskCompletionSource) {
        this.zza = zzff;
        this.zzb = taskCompletionSource;
    }

    public final void zza(ResultT resultt, Status status) {
        FirebaseUser firebaseUser;
        Preconditions.checkNotNull(this.zzb, "completion source cannot be null");
        if (status == null) {
            this.zzb.setResult(resultt);
        } else if (this.zza.zzt != null) {
            TaskCompletionSource<ResultT> taskCompletionSource = this.zzb;
            FirebaseAuth instance = FirebaseAuth.getInstance(this.zza.zzd);
            zzem zzem = this.zza.zzt;
            if ("reauthenticateWithCredential".equals(this.zza.zza()) || "reauthenticateWithCredentialWithData".equals(this.zza.zza())) {
                firebaseUser = this.zza.zze;
            } else {
                firebaseUser = null;
            }
            taskCompletionSource.setException(zzej.zza(instance, zzem, firebaseUser));
        } else if (this.zza.zzq != null) {
            this.zzb.setException(zzej.zza(status, this.zza.zzq, this.zza.zzr, this.zza.zzs));
        } else {
            this.zzb.setException(zzej.zza(status));
        }
    }
}
