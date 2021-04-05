package com.google.android.gms.internal.firebase_auth;

import java.util.List;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
abstract class zzjb {
    private static final zzjb zza = new zzjd();
    private static final zzjb zzb = new zzjc();

    private zzjb() {
    }

    /* access modifiers changed from: package-private */
    public abstract <L> List<L> zza(Object obj, long j);

    /* access modifiers changed from: package-private */
    public abstract <L> void zza(Object obj, Object obj2, long j);

    /* access modifiers changed from: package-private */
    public abstract void zzb(Object obj, long j);

    static zzjb zza() {
        return zza;
    }

    static zzjb zzb() {
        return zzb;
    }
}
