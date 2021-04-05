package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.internal.firebase_auth.zzda;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final /* synthetic */ class zzcn implements RemoteCall {
    private final zzck zza;

    zzcn(zzck zzck) {
        this.zza = zzck;
    }

    public final void accept(Object obj, Object obj2) {
        zzck zzck = this.zza;
        zzeh zzeh = (zzeh) obj;
        zzck.zzh = new zzfm(zzck, (TaskCompletionSource) obj2);
        if (zzck.zzu) {
            zzeh.zza().zzf(zzck.zze.zzf(), zzck.zzc);
        } else {
            zzeh.zza().zza(new zzda(zzck.zze.zzf()), (zzem) zzck.zzc);
        }
    }
}
