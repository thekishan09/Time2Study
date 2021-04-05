package com.google.android.gms.internal.firebase_auth;

import java.io.IOException;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzjj<K, V> {
    static <K, V> void zza(zzhq zzhq, zzji<K, V> zzji, K k, V v) throws IOException {
        zzhz.zza(zzhq, zzji.zza, 1, k);
        zzhz.zza(zzhq, zzji.zzc, 2, v);
    }

    static <K, V> int zza(zzji<K, V> zzji, K k, V v) {
        return zzhz.zza(zzji.zza, 1, k) + zzhz.zza(zzji.zzc, 2, v);
    }
}
