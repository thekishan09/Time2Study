package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final /* synthetic */ class zzbd implements RemoteCall {
    private final zzba zza;

    zzbd(zzba zzba) {
        this.zza = zzba;
    }

    public final void accept(Object obj, Object obj2) {
        zzba zzba = this.zza;
        zzeh zzeh = (zzeh) obj;
        zzba.zzh = new zzfm(zzba, (TaskCompletionSource) obj2);
        if (zzba.zzu) {
            zzeh.zza().zzc(zzba.zza.zza(), zzba.zza.zzb(), (zzem) zzba.zzc);
        } else {
            zzeh.zza().zza(zzba.zza, (zzem) zzba.zzc);
        }
    }
}
