package com.google.android.gms.internal.firebase_auth;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzjy {
    private static final zzjw zza = zzc();
    private static final zzjw zzb = new zzjz();

    static zzjw zza() {
        return zza;
    }

    static zzjw zzb() {
        return zzb;
    }

    private static zzjw zzc() {
        try {
            return (zzjw) Class.forName("com.google.protobuf.NewInstanceSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            return null;
        }
    }
}
