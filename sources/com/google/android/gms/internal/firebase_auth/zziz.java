package com.google.android.gms.internal.firebase_auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zziz extends zzgr<String> implements zziy, RandomAccess {
    private static final zziz zza;
    private static final zziy zzb = zza;
    private final List<Object> zzc;

    public zziz() {
        this(10);
    }

    public zziz(int i) {
        this((ArrayList<Object>) new ArrayList(i));
    }

    private zziz(ArrayList<Object> arrayList) {
        this.zzc = arrayList;
    }

    public final int size() {
        return this.zzc.size();
    }

    public final boolean addAll(Collection<? extends String> collection) {
        return addAll(size(), collection);
    }

    public final boolean addAll(int i, Collection<? extends String> collection) {
        zzc();
        if (collection instanceof zziy) {
            collection = ((zziy) collection).zzb();
        }
        boolean addAll = this.zzc.addAll(i, collection);
        this.modCount++;
        return addAll;
    }

    public final void clear() {
        zzc();
        this.zzc.clear();
        this.modCount++;
    }

    public final void zza(zzgv zzgv) {
        zzc();
        this.zzc.add(zzgv);
        this.modCount++;
    }

    public final Object zzb(int i) {
        return this.zzc.get(i);
    }

    private static String zza(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof zzgv) {
            return ((zzgv) obj).zzb();
        }
        return zzii.zzb((byte[]) obj);
    }

    public final List<?> zzb() {
        return Collections.unmodifiableList(this.zzc);
    }

    /* renamed from: b_ */
    public final zziy mo12992b_() {
        if (zza()) {
            return new zzle(this);
        }
        return this;
    }

    public final /* synthetic */ Object set(int i, Object obj) {
        zzc();
        return zza(this.zzc.set(i, (String) obj));
    }

    public final /* bridge */ /* synthetic */ boolean retainAll(Collection collection) {
        return super.retainAll(collection);
    }

    public final /* bridge */ /* synthetic */ boolean removeAll(Collection collection) {
        return super.removeAll(collection);
    }

    public final /* bridge */ /* synthetic */ boolean remove(Object obj) {
        return super.remove(obj);
    }

    public final /* synthetic */ Object remove(int i) {
        zzc();
        Object remove = this.zzc.remove(i);
        this.modCount++;
        return zza(remove);
    }

    public final /* bridge */ /* synthetic */ boolean zza() {
        return super.zza();
    }

    public final /* synthetic */ void add(int i, Object obj) {
        zzc();
        this.zzc.add(i, (String) obj);
        this.modCount++;
    }

    public final /* bridge */ /* synthetic */ boolean add(Object obj) {
        return super.add(obj);
    }

    public final /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public final /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    public final /* synthetic */ zzio zza(int i) {
        if (i >= size()) {
            ArrayList arrayList = new ArrayList(i);
            arrayList.addAll(this.zzc);
            return new zziz((ArrayList<Object>) arrayList);
        }
        throw new IllegalArgumentException();
    }

    public final /* synthetic */ Object get(int i) {
        Object obj = this.zzc.get(i);
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof zzgv) {
            zzgv zzgv = (zzgv) obj;
            String zzb2 = zzgv.zzb();
            if (zzgv.zzc()) {
                this.zzc.set(i, zzb2);
            }
            return zzb2;
        }
        byte[] bArr = (byte[]) obj;
        String zzb3 = zzii.zzb(bArr);
        if (zzii.zza(bArr)) {
            this.zzc.set(i, zzb3);
        }
        return zzb3;
    }

    static {
        zziz zziz = new zziz();
        zza = zziz;
        zziz.mo12718c_();
    }
}
