package com.google.android.gms.internal.firebase_auth;

import java.util.Map;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zziu<K> implements Map.Entry<K, Object> {
    private Map.Entry<K, zzis> zza;

    private zziu(Map.Entry<K, zzis> entry) {
        this.zza = entry;
    }

    public final K getKey() {
        return this.zza.getKey();
    }

    public final Object getValue() {
        if (this.zza.getValue() == null) {
            return null;
        }
        return zzis.zza();
    }

    public final zzis zza() {
        return this.zza.getValue();
    }

    public final Object setValue(Object obj) {
        if (obj instanceof zzjr) {
            return this.zza.getValue().zza((zzjr) obj);
        }
        throw new IllegalArgumentException("LazyField now only used for MessageSet, and the value of MessageSet must be an instance of MessageLite");
    }
}
