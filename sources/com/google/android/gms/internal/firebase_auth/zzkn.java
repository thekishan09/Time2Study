package com.google.android.gms.internal.firebase_auth;

import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzkn extends zzkt {
    private final /* synthetic */ zzki zza;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    private zzkn(zzki zzki) {
        super(zzki, (zzkl) null);
        this.zza = zzki;
    }

    public final Iterator<Map.Entry<K, V>> iterator() {
        return new zzkk(this.zza, (zzkl) null);
    }

    /* synthetic */ zzkn(zzki zzki, zzkl zzkl) {
        this(zzki);
    }
}
