package com.google.android.gms.internal.firebase_auth;

import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzbt<K> extends zzbm<K> {
    private final transient zzbk<K, ?> zza;
    private final transient zzbj<K> zzb;

    zzbt(zzbk<K, ?> zzbk, zzbj<K> zzbj) {
        this.zza = zzbk;
        this.zzb = zzbj;
    }

    public final zzbu<K> zza() {
        return (zzbu) zze().iterator();
    }

    /* access modifiers changed from: package-private */
    public final int zza(Object[] objArr, int i) {
        return zze().zza(objArr, i);
    }

    public final zzbj<K> zze() {
        return this.zzb;
    }

    public final boolean contains(@NullableDecl Object obj) {
        return this.zza.get(obj) != null;
    }

    public final int size() {
        return this.zza.size();
    }

    public final /* synthetic */ Iterator iterator() {
        return iterator();
    }
}
