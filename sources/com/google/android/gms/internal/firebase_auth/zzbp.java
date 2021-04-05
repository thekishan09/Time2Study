package com.google.android.gms.internal.firebase_auth;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzbp<E> extends zzbj<E> {
    static final zzbj<Object> zza = new zzbp(new Object[0], 0);
    private final transient Object[] zzb;
    private final transient int zzc;

    zzbp(Object[] objArr, int i) {
        this.zzb = objArr;
        this.zzc = i;
    }

    public final int size() {
        return this.zzc;
    }

    /* access modifiers changed from: package-private */
    public final Object[] zzb() {
        return this.zzb;
    }

    /* access modifiers changed from: package-private */
    public final int zzc() {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public final int zzd() {
        return this.zzc;
    }

    /* access modifiers changed from: package-private */
    public final int zza(Object[] objArr, int i) {
        System.arraycopy(this.zzb, 0, objArr, i, this.zzc);
        return i + this.zzc;
    }

    public final E get(int i) {
        zzav.zza(i, this.zzc);
        return this.zzb[i];
    }
}
