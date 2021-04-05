package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final /* synthetic */ class zzdr implements RemoteCall {
    private final zzdo zza;

    zzdr(zzdo zzdo) {
        this.zza = zzdo;
    }

    public final void accept(Object obj, Object obj2) {
        this.zza.zza((zzeh) obj, (TaskCompletionSource) obj2);
    }
}
