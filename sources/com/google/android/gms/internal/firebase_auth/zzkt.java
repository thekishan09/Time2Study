package com.google.android.gms.internal.firebase_auth;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
class zzkt extends AbstractSet<Map.Entry<K, V>> {
    private final /* synthetic */ zzki zza;

    private zzkt(zzki zzki) {
        this.zza = zzki;
    }

    public Iterator<Map.Entry<K, V>> iterator() {
        return new zzkq(this.zza, (zzkl) null);
    }

    public int size() {
        return this.zza.size();
    }

    public boolean contains(Object obj) {
        Map.Entry entry = (Map.Entry) obj;
        Object obj2 = this.zza.get(entry.getKey());
        Object value = entry.getValue();
        if (obj2 != value) {
            return obj2 != null && obj2.equals(value);
        }
        return true;
    }

    public boolean remove(Object obj) {
        Map.Entry entry = (Map.Entry) obj;
        if (!contains(entry)) {
            return false;
        }
        this.zza.remove(entry.getKey());
        return true;
    }

    public void clear() {
        this.zza.clear();
    }

    public /* synthetic */ boolean add(Object obj) {
        Map.Entry entry = (Map.Entry) obj;
        if (contains(entry)) {
            return false;
        }
        this.zza.put((Comparable) entry.getKey(), entry.getValue());
        return true;
    }

    /* synthetic */ zzkt(zzki zzki, zzkl zzkl) {
        this(zzki);
    }
}
