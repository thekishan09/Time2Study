package com.google.firebase.auth.api.internal;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzfk implements Runnable {
    private final /* synthetic */ zzfn zza;
    private final /* synthetic */ zzfh zzb;

    zzfk(zzfh zzfh, zzfn zzfn) {
        this.zzb = zzfh;
        this.zza = zzfn;
    }

    public final void run() {
        synchronized (this.zzb.zza.zzi) {
            if (!this.zzb.zza.zzi.isEmpty()) {
                this.zza.zza(this.zzb.zza.zzi.get(0), new Object[0]);
            }
        }
    }
}
