package com.google.android.gms.internal.firebase_auth;

import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzix<K> implements Iterator<Map.Entry<K, Object>> {
    private Iterator<Map.Entry<K, Object>> zza;

    public zzix(Iterator<Map.Entry<K, Object>> it) {
        this.zza = it;
    }

    public final boolean hasNext() {
        return this.zza.hasNext();
    }

    public final void remove() {
        this.zza.remove();
    }

    public final /* synthetic */ Object next() {
        Map.Entry next = this.zza.next();
        if (next.getValue() instanceof zzis) {
            return new zziu(next);
        }
        return next;
    }
}
