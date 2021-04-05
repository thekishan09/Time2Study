package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.internal.firebase_auth.zzec;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final /* synthetic */ class zzdn implements RemoteCall {
    private final zzdk zza;

    zzdn(zzdk zzdk) {
        this.zza = zzdk;
    }

    public final void accept(Object obj, Object obj2) {
        zzdk zzdk = this.zza;
        zzeh zzeh = (zzeh) obj;
        zzdk.zzh = new zzfm(zzdk, (TaskCompletionSource) obj2);
        if (zzdk.zzu) {
            zzeh.zza().zze(zzdk.zze.zzf(), zzdk.zzc);
        } else {
            zzeh.zza().zza(new zzec(zzdk.zze.zzf()), (zzem) zzdk.zzc);
        }
    }
}
