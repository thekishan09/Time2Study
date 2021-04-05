package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final /* synthetic */ class zzcd implements RemoteCall {
    private final zzca zza;

    zzcd(zzca zzca) {
        this.zza = zzca;
    }

    public final void accept(Object obj, Object obj2) {
        this.zza.zza((zzeh) obj, (TaskCompletionSource) obj2);
    }
}
