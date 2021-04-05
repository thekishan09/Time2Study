package com.google.android.gms.internal.firebase_auth;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public abstract class zzbg<E> extends AbstractCollection<E> implements Serializable {
    private static final Object[] zza = new Object[0];

    zzbg() {
    }

    public abstract boolean contains(@NullableDecl Object obj);

    /* renamed from: zza */
    public abstract zzbu<E> iterator();

    public final Object[] toArray() {
        return toArray(zza);
    }

    public final <T> T[] toArray(T[] tArr) {
        zzav.zza(tArr);
        int size = size();
        if (tArr.length < size) {
            Object[] zzb = zzb();
            if (zzb != null) {
                return Arrays.copyOfRange(zzb, zzc(), zzd(), tArr.getClass());
            }
            tArr = (Object[]) Array.newInstance(tArr.getClass().getComponentType(), size);
        } else if (tArr.length > size) {
            tArr[size] = null;
        }
        zza(tArr, 0);
        return tArr;
    }

    /* access modifiers changed from: package-private */
    @NullableDecl
    public Object[] zzb() {
        return null;
    }

    /* access modifiers changed from: package-private */
    public int zzc() {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: package-private */
    public int zzd() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final boolean add(E e) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final boolean remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final boolean addAll(Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    public zzbj<E> zze() {
        return isEmpty() ? zzbj.zzf() : zzbj.zza(toArray());
    }

    /* access modifiers changed from: package-private */
    public int zza(Object[] objArr, int i) {
        zzbu zzbu = (zzbu) iterator();
        while (zzbu.hasNext()) {
            objArr[i] = zzbu.next();
            i++;
        }
        return i;
    }
}
