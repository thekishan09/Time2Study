package com.google.android.gms.internal.firebase_auth;

import java.util.List;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzbl extends zzbj<E> {
    private final transient int zza;
    private final transient int zzb;
    private final /* synthetic */ zzbj zzc;

    zzbl(zzbj zzbj, int i, int i2) {
        this.zzc = zzbj;
        this.zza = i;
        this.zzb = i2;
    }

    public final int size() {
        return this.zzb;
    }

    /* access modifiers changed from: package-private */
    public final Object[] zzb() {
        return this.zzc.zzb();
    }

    /* access modifiers changed from: package-private */
    public final int zzc() {
        return this.zzc.zzc() + this.zza;
    }

    /* access modifiers changed from: package-private */
    public final int zzd() {
        return this.zzc.zzc() + this.zza + this.zzb;
    }

    public final E get(int i) {
        zzav.zza(i, this.zzb);
        return this.zzc.get(i + this.zza);
    }

    public final zzbj<E> zza(int i, int i2) {
        zzav.zza(i, i2, this.zzb);
        zzbj zzbj = this.zzc;
        int i3 = this.zza;
        return (zzbj) zzbj.subList(i + i3, i2 + i3);
    }

    public final /* synthetic */ List subList(int i, int i2) {
        return subList(i, i2);
    }
}
