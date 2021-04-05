package com.google.android.gms.internal.firebase_auth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzjd extends zzjb {
    private static final Class<?> zza = Collections.unmodifiableList(Collections.emptyList()).getClass();

    private zzjd() {
        super();
    }

    /* access modifiers changed from: package-private */
    public final <L> List<L> zza(Object obj, long j) {
        return zza(obj, j, 10);
    }

    /* access modifiers changed from: package-private */
    public final void zzb(Object obj, long j) {
        Object obj2;
        List list = (List) zzlf.zzf(obj, j);
        if (list instanceof zziy) {
            obj2 = ((zziy) list).mo12992b_();
        } else if (!zza.isAssignableFrom(list.getClass())) {
            if (!(list instanceof zzka) || !(list instanceof zzio)) {
                obj2 = Collections.unmodifiableList(list);
            } else {
                zzio zzio = (zzio) list;
                if (zzio.zza()) {
                    zzio.mo12718c_();
                    return;
                }
                return;
            }
        } else {
            return;
        }
        zzlf.zza(obj, j, obj2);
    }

    private static <L> List<L> zza(Object obj, long j, int i) {
        List<L> list;
        List<L> zzc = zzc(obj, j);
        if (zzc.isEmpty()) {
            if (zzc instanceof zziy) {
                list = new zziz(i);
            } else if (!(zzc instanceof zzka) || !(zzc instanceof zzio)) {
                list = new ArrayList<>(i);
            } else {
                list = ((zzio) zzc).zza(i);
            }
            zzlf.zza(obj, j, (Object) list);
            return list;
        } else if (zza.isAssignableFrom(zzc.getClass())) {
            ArrayList arrayList = new ArrayList(zzc.size() + i);
            arrayList.addAll(zzc);
            zzlf.zza(obj, j, (Object) arrayList);
            return arrayList;
        } else if (zzc instanceof zzle) {
            zziz zziz = new zziz(zzc.size() + i);
            zziz.addAll((zzle) zzc);
            zzlf.zza(obj, j, (Object) zziz);
            return zziz;
        } else if (!(zzc instanceof zzka) || !(zzc instanceof zzio)) {
            return zzc;
        } else {
            zzio zzio = (zzio) zzc;
            if (zzio.zza()) {
                return zzc;
            }
            zzio zza2 = zzio.zza(zzc.size() + i);
            zzlf.zza(obj, j, (Object) zza2);
            return zza2;
        }
    }

    /* access modifiers changed from: package-private */
    public final <E> void zza(Object obj, Object obj2, long j) {
        List zzc = zzc(obj2, j);
        List zza2 = zza(obj, j, zzc.size());
        int size = zza2.size();
        int size2 = zzc.size();
        if (size > 0 && size2 > 0) {
            zza2.addAll(zzc);
        }
        if (size > 0) {
            zzc = zza2;
        }
        zzlf.zza(obj, j, (Object) zzc);
    }

    private static <E> List<E> zzc(Object obj, long j) {
        return (List) zzlf.zzf(obj, j);
    }
}
