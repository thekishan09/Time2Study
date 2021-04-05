package com.google.android.gms.internal.firebase_auth;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzjg implements zzjo {
    private zzjo[] zza;

    zzjg(zzjo... zzjoArr) {
        this.zza = zzjoArr;
    }

    public final boolean zza(Class<?> cls) {
        for (zzjo zza2 : this.zza) {
            if (zza2.zza(cls)) {
                return true;
            }
        }
        return false;
    }

    public final zzjp zzb(Class<?> cls) {
        for (zzjo zzjo : this.zza) {
            if (zzjo.zza(cls)) {
                return zzjo.zzb(cls);
            }
        }
        String valueOf = String.valueOf(cls.getName());
        throw new UnsupportedOperationException(valueOf.length() != 0 ? "No factory is available for message type: ".concat(valueOf) : new String("No factory is available for message type: "));
    }
}
