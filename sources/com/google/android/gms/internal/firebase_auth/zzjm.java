package com.google.android.gms.internal.firebase_auth;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzjm {
    private static final zzjk zza = zzc();
    private static final zzjk zzb = new zzjn();

    static zzjk zza() {
        return zza;
    }

    static zzjk zzb() {
        return zzb;
    }

    private static zzjk zzc() {
        try {
            return (zzjk) Class.forName("com.google.protobuf.MapFieldSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            return null;
        }
    }
}
