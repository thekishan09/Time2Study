package com.google.android.gms.internal.firebase_auth;

import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzjn implements zzjk {
    zzjn() {
    }

    public final Map<?, ?> zza(Object obj) {
        return (zzjl) obj;
    }

    public final zzji<?, ?> zzf(Object obj) {
        zzjj zzjj = (zzjj) obj;
        throw new NoSuchMethodError();
    }

    public final Map<?, ?> zzb(Object obj) {
        return (zzjl) obj;
    }

    public final boolean zzc(Object obj) {
        return !((zzjl) obj).zzd();
    }

    public final Object zzd(Object obj) {
        ((zzjl) obj).zzc();
        return obj;
    }

    public final Object zze(Object obj) {
        return zzjl.zza().zzb();
    }

    public final Object zza(Object obj, Object obj2) {
        zzjl zzjl = (zzjl) obj;
        zzjl zzjl2 = (zzjl) obj2;
        if (!zzjl2.isEmpty()) {
            if (!zzjl.zzd()) {
                zzjl = zzjl.zzb();
            }
            zzjl.zza(zzjl2);
        }
        return zzjl;
    }

    public final int zza(int i, Object obj, Object obj2) {
        zzjl zzjl = (zzjl) obj;
        zzjj zzjj = (zzjj) obj2;
        if (zzjl.isEmpty()) {
            return 0;
        }
        Iterator it = zzjl.entrySet().iterator();
        if (!it.hasNext()) {
            return 0;
        }
        Map.Entry entry = (Map.Entry) it.next();
        entry.getKey();
        entry.getValue();
        throw new NoSuchMethodError();
    }
}
