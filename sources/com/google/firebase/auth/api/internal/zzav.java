package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.UnsupportedApiCallException;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzav implements Continuation<ResultT, Task<ResultT>> {
    private final /* synthetic */ zzar zza;
    private final /* synthetic */ zzas zzb;

    zzav(zzas zzas, zzar zzar) {
        this.zzb = zzas;
        this.zza = zzar;
    }

    public final /* synthetic */ Object then(Task task) throws Exception {
        if (task.getException() instanceof UnsupportedApiCallException) {
            return this.zzb.zza(this.zza.zzc());
        }
        return task;
    }
}
