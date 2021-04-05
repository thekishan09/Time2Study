package com.google.android.gms.internal.firebase_auth;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzhw {
    private static final zzhv<?> zza = new zzhx();
    private static final zzhv<?> zzb = zzc();

    private static zzhv<?> zzc() {
        try {
            return (zzhv) Class.forName("com.google.protobuf.ExtensionSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            return null;
        }
    }

    static zzhv<?> zza() {
        return zza;
    }

    static zzhv<?> zzb() {
        zzhv<?> zzhv = zzb;
        if (zzhv != null) {
            return zzhv;
        }
        throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
    }
}
