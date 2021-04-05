package com.google.android.gms.internal.firebase_auth;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzhd {
    private final zzhq zza;
    private final byte[] zzb;

    private zzhd(int i) {
        byte[] bArr = new byte[i];
        this.zzb = bArr;
        this.zza = zzhq.zza(bArr);
    }

    public final zzgv zza() {
        this.zza.zzb();
        return new zzhf(this.zzb);
    }

    public final zzhq zzb() {
        return this.zza;
    }

    /* synthetic */ zzhd(int i, zzgy zzgy) {
        this(i);
    }
}
