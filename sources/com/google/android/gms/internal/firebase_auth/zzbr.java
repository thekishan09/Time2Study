package com.google.android.gms.internal.firebase_auth;

import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzbr<K, V> extends zzbm<Map.Entry<K, V>> {
    private final transient zzbk<K, V> zza;
    /* access modifiers changed from: private */
    public final transient Object[] zzb;
    private final transient int zzc = 0;
    /* access modifiers changed from: private */
    public final transient int zzd;

    zzbr(zzbk<K, V> zzbk, Object[] objArr, int i, int i2) {
        this.zza = zzbk;
        this.zzb = objArr;
        this.zzd = i2;
    }

    public final zzbu<Map.Entry<K, V>> zza() {
        return (zzbu) zze().iterator();
    }

    /* access modifiers changed from: package-private */
    public final int zza(Object[] objArr, int i) {
        return zze().zza(objArr, i);
    }

    /* access modifiers changed from: package-private */
    public final zzbj<Map.Entry<K, V>> zzf() {
        return new zzbq(this);
    }

    public final boolean contains(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        Object key = entry.getKey();
        Object value = entry.getValue();
        if (value == null || !value.equals(this.zza.get(key))) {
            return false;
        }
        return true;
    }

    public final int size() {
        return this.zzd;
    }

    public final /* synthetic */ Iterator iterator() {
        return iterator();
    }
}
