package com.google.android.gms.internal.firebase_auth;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzkk implements Iterator<Map.Entry<K, V>> {
    private int zza;
    private Iterator<Map.Entry<K, V>> zzb;
    private final /* synthetic */ zzki zzc;

    private zzkk(zzki zzki) {
        this.zzc = zzki;
        this.zza = this.zzc.zzb.size();
    }

    public final boolean hasNext() {
        int i = this.zza;
        return (i > 0 && i <= this.zzc.zzb.size()) || zza().hasNext();
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }

    private final Iterator<Map.Entry<K, V>> zza() {
        if (this.zzb == null) {
            this.zzb = this.zzc.zzf.entrySet().iterator();
        }
        return this.zzb;
    }

    public final /* synthetic */ Object next() {
        if (zza().hasNext()) {
            return (Map.Entry) zza().next();
        }
        List zzb2 = this.zzc.zzb;
        int i = this.zza - 1;
        this.zza = i;
        return (Map.Entry) zzb2.get(i);
    }

    /* synthetic */ zzkk(zzki zzki, zzkl zzkl) {
        this(zzki);
    }
}
