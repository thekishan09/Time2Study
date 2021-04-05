package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.internal.firebase_auth.zzck;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final /* synthetic */ class zzbf implements RemoteCall {
    private final zzbc zza;

    zzbf(zzbc zzbc) {
        this.zza = zzbc;
    }

    public final void accept(Object obj, Object obj2) {
        zzbc zzbc = this.zza;
        zzeh zzeh = (zzeh) obj;
        zzbc.zzh = new zzfm(zzbc, (TaskCompletionSource) obj2);
        if (zzbc.zzu) {
            zzeh.zza().zzg(zzbc.zze.zzf(), zzbc.zzc);
        } else {
            zzeh.zza().zza(new zzck(zzbc.zze.zzf()), (zzem) zzbc.zzc);
        }
    }
}
