package com.google.android.gms.internal.firebase_auth;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzkd {
    private static final zzkd zza = new zzkd();
    private final zzkg zzb = new zzje();
    private final ConcurrentMap<Class<?>, zzkh<?>> zzc = new ConcurrentHashMap();

    public static zzkd zza() {
        return zza;
    }

    public final <T> zzkh<T> zza(Class<T> cls) {
        zzii.zza(cls, "messageType");
        zzkh<T> zzkh = (zzkh) this.zzc.get(cls);
        if (zzkh != null) {
            return zzkh;
        }
        zzkh<T> zza2 = this.zzb.zza(cls);
        zzii.zza(cls, "messageType");
        zzii.zza(zza2, "schema");
        zzkh<T> putIfAbsent = this.zzc.putIfAbsent(cls, zza2);
        if (putIfAbsent != null) {
            return putIfAbsent;
        }
        return zza2;
    }

    public final <T> zzkh<T> zza(T t) {
        return zza(t.getClass());
    }

    private zzkd() {
    }
}
