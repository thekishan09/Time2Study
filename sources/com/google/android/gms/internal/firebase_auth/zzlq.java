package com.google.android.gms.internal.firebase_auth;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public enum zzlq {
    DOUBLE(zzlt.DOUBLE, 1),
    FLOAT(zzlt.FLOAT, 5),
    INT64(zzlt.LONG, 0),
    UINT64(zzlt.LONG, 0),
    INT32(zzlt.INT, 0),
    FIXED64(zzlt.LONG, 1),
    FIXED32(zzlt.INT, 5),
    BOOL(zzlt.BOOLEAN, 0),
    STRING(zzlt.STRING, 2),
    GROUP(zzlt.MESSAGE, 3),
    MESSAGE(zzlt.MESSAGE, 2),
    BYTES(zzlt.BYTE_STRING, 2),
    UINT32(zzlt.INT, 0),
    ENUM(zzlt.ENUM, 0),
    SFIXED32(zzlt.INT, 5),
    SFIXED64(zzlt.LONG, 1),
    SINT32(zzlt.INT, 0),
    SINT64(zzlt.LONG, 0);
    
    private final zzlt zzs;
    private final int zzt;

    private zzlq(zzlt zzlt, int i) {
        this.zzs = zzlt;
        this.zzt = i;
    }

    public final zzlt zza() {
        return this.zzs;
    }

    public final int zzb() {
        return this.zzt;
    }
}
