package com.google.android.gms.internal.firebase_auth;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzgu {
    private static final Class<?> zza = zza("libcore.io.Memory");
    private static final boolean zzb = (zza("org.robolectric.Robolectric") != null);

    static boolean zza() {
        return zza != null && !zzb;
    }

    static Class<?> zzb() {
        return zza;
    }

    private static <T> Class<T> zza(String str) {
        try {
            return Class.forName(str);
        } catch (Throwable th) {
            return null;
        }
    }
}
