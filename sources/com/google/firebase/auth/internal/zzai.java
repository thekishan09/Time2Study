package com.google.firebase.auth.internal;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzai implements OnFailureListener {
    private final /* synthetic */ TaskCompletionSource zza;

    zzai(zzah zzah, TaskCompletionSource taskCompletionSource) {
        this.zza = taskCompletionSource;
    }

    public final void onFailure(Exception exc) {
        this.zza.setException(exc);
        zzah.zzb();
    }
}
