package com.google.android.gms.internal.firebase_auth;

import java.util.Iterator;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public abstract class zzbm<E> extends zzbg<E> implements Set<E> {
    @NullableDecl
    private transient zzbj<E> zza;

    zzbm() {
    }

    public boolean equals(@NullableDecl Object obj) {
        if (obj == this) {
            return true;
        }
        return zzbv.zza(this, obj);
    }

    public int hashCode() {
        return zzbv.zza(this);
    }

    public zzbj<E> zze() {
        zzbj<E> zzbj = this.zza;
        if (zzbj != null) {
            return zzbj;
        }
        zzbj<E> zzf = zzf();
        this.zza = zzf;
        return zzf;
    }

    /* access modifiers changed from: package-private */
    public zzbj<E> zzf() {
        return zzbj.zza(toArray());
    }

    public /* synthetic */ Iterator iterator() {
        return iterator();
    }
}
