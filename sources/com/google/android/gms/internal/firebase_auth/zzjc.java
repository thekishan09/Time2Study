package com.google.android.gms.internal.firebase_auth;

import java.util.List;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzjc extends zzjb {
    private zzjc() {
        super();
    }

    /* access modifiers changed from: package-private */
    public final <L> List<L> zza(Object obj, long j) {
        zzio zzc = zzc(obj, j);
        if (zzc.zza()) {
            return zzc;
        }
        int size = zzc.size();
        zzio zza = zzc.zza(size == 0 ? 10 : size << 1);
        zzlf.zza(obj, j, (Object) zza);
        return zza;
    }

    /* access modifiers changed from: package-private */
    public final void zzb(Object obj, long j) {
        zzc(obj, j).mo12718c_();
    }

    /* access modifiers changed from: package-private */
    public final <E> void zza(Object obj, Object obj2, long j) {
        zzio zzc = zzc(obj, j);
        zzio zzc2 = zzc(obj2, j);
        int size = zzc.size();
        int size2 = zzc2.size();
        if (size > 0 && size2 > 0) {
            if (!zzc.zza()) {
                zzc = zzc.zza(size2 + size);
            }
            zzc.addAll(zzc2);
        }
        if (size > 0) {
            zzc2 = zzc;
        }
        zzlf.zza(obj, j, (Object) zzc2);
    }

    private static <E> zzio<E> zzc(Object obj, long j) {
        return (zzio) zzlf.zzf(obj, j);
    }
}
