package com.google.android.gms.internal.firebase_auth;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzkj {
    private static final Class<?> zza = zzd();
    private static final zzkz<?, ?> zzb = zza(false);
    private static final zzkz<?, ?> zzc = zza(true);
    private static final zzkz<?, ?> zzd = new zzlb();

    public static void zza(Class<?> cls) {
        Class<?> cls2;
        if (!zzig.class.isAssignableFrom(cls) && (cls2 = zza) != null && !cls2.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
        }
    }

    public static void zza(int i, List<Double> list, zzlw zzlw, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlw.zzg(i, list, z);
        }
    }

    public static void zzb(int i, List<Float> list, zzlw zzlw, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlw.zzf(i, list, z);
        }
    }

    public static void zzc(int i, List<Long> list, zzlw zzlw, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlw.zzc(i, list, z);
        }
    }

    public static void zzd(int i, List<Long> list, zzlw zzlw, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlw.zzd(i, list, z);
        }
    }

    public static void zze(int i, List<Long> list, zzlw zzlw, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlw.zzn(i, list, z);
        }
    }

    public static void zzf(int i, List<Long> list, zzlw zzlw, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlw.zze(i, list, z);
        }
    }

    public static void zzg(int i, List<Long> list, zzlw zzlw, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlw.zzl(i, list, z);
        }
    }

    public static void zzh(int i, List<Integer> list, zzlw zzlw, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlw.zza(i, list, z);
        }
    }

    public static void zzi(int i, List<Integer> list, zzlw zzlw, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlw.zzj(i, list, z);
        }
    }

    public static void zzj(int i, List<Integer> list, zzlw zzlw, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlw.zzm(i, list, z);
        }
    }

    public static void zzk(int i, List<Integer> list, zzlw zzlw, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlw.zzb(i, list, z);
        }
    }

    public static void zzl(int i, List<Integer> list, zzlw zzlw, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlw.zzk(i, list, z);
        }
    }

    public static void zzm(int i, List<Integer> list, zzlw zzlw, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlw.zzh(i, list, z);
        }
    }

    public static void zzn(int i, List<Boolean> list, zzlw zzlw, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlw.zzi(i, list, z);
        }
    }

    public static void zza(int i, List<String> list, zzlw zzlw) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlw.zza(i, list);
        }
    }

    public static void zzb(int i, List<zzgv> list, zzlw zzlw) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlw.zzb(i, list);
        }
    }

    public static void zza(int i, List<?> list, zzlw zzlw, zzkh zzkh) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlw.zza(i, list, zzkh);
        }
    }

    public static void zzb(int i, List<?> list, zzlw zzlw, zzkh zzkh) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlw.zzb(i, list, zzkh);
        }
    }

    static int zza(List<Long> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzjf) {
            zzjf zzjf = (zzjf) list;
            i = 0;
            while (i2 < size) {
                i += zzhq.zzd(zzjf.zzb(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzhq.zzd(list.get(i2).longValue());
                i2++;
            }
        }
        return i;
    }

    static int zza(int i, List<Long> list, boolean z) {
        if (list.size() == 0) {
            return 0;
        }
        return zza(list) + (list.size() * zzhq.zze(i));
    }

    static int zzb(List<Long> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzjf) {
            zzjf zzjf = (zzjf) list;
            i = 0;
            while (i2 < size) {
                i += zzhq.zze(zzjf.zzb(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzhq.zze(list.get(i2).longValue());
                i2++;
            }
        }
        return i;
    }

    static int zzb(int i, List<Long> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzb(list) + (size * zzhq.zze(i));
    }

    static int zzc(List<Long> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzjf) {
            zzjf zzjf = (zzjf) list;
            i = 0;
            while (i2 < size) {
                i += zzhq.zzf(zzjf.zzb(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzhq.zzf(list.get(i2).longValue());
                i2++;
            }
        }
        return i;
    }

    static int zzc(int i, List<Long> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzc(list) + (size * zzhq.zze(i));
    }

    static int zzd(List<Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzij) {
            zzij zzij = (zzij) list;
            i = 0;
            while (i2 < size) {
                i += zzhq.zzk(zzij.zzc(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzhq.zzk(list.get(i2).intValue());
                i2++;
            }
        }
        return i;
    }

    static int zzd(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzd(list) + (size * zzhq.zze(i));
    }

    static int zze(List<Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzij) {
            zzij zzij = (zzij) list;
            i = 0;
            while (i2 < size) {
                i += zzhq.zzf(zzij.zzc(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzhq.zzf(list.get(i2).intValue());
                i2++;
            }
        }
        return i;
    }

    static int zze(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zze(list) + (size * zzhq.zze(i));
    }

    static int zzf(List<Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzij) {
            zzij zzij = (zzij) list;
            i = 0;
            while (i2 < size) {
                i += zzhq.zzg(zzij.zzc(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzhq.zzg(list.get(i2).intValue());
                i2++;
            }
        }
        return i;
    }

    static int zzf(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzf(list) + (size * zzhq.zze(i));
    }

    static int zzg(List<Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzij) {
            zzij zzij = (zzij) list;
            i = 0;
            while (i2 < size) {
                i += zzhq.zzh(zzij.zzc(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzhq.zzh(list.get(i2).intValue());
                i2++;
            }
        }
        return i;
    }

    static int zzg(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzg(list) + (size * zzhq.zze(i));
    }

    static int zzh(List<?> list) {
        return list.size() << 2;
    }

    static int zzh(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzhq.zzi(i, 0);
    }

    static int zzi(List<?> list) {
        return list.size() << 3;
    }

    static int zzi(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzhq.zzg(i, 0);
    }

    static int zzj(List<?> list) {
        return list.size();
    }

    static int zzj(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzhq.zzb(i, true);
    }

    static int zza(int i, List<?> list) {
        int i2;
        int i3;
        int size = list.size();
        int i4 = 0;
        if (size == 0) {
            return 0;
        }
        int zze = zzhq.zze(i) * size;
        if (list instanceof zziy) {
            zziy zziy = (zziy) list;
            while (i4 < size) {
                Object zzb2 = zziy.zzb(i4);
                if (zzb2 instanceof zzgv) {
                    i3 = zzhq.zzb((zzgv) zzb2);
                } else {
                    i3 = zzhq.zzb((String) zzb2);
                }
                zze += i3;
                i4++;
            }
        } else {
            while (i4 < size) {
                Object obj = list.get(i4);
                if (obj instanceof zzgv) {
                    i2 = zzhq.zzb((zzgv) obj);
                } else {
                    i2 = zzhq.zzb((String) obj);
                }
                zze += i2;
                i4++;
            }
        }
        return zze;
    }

    static int zza(int i, Object obj, zzkh zzkh) {
        if (obj instanceof zziw) {
            return zzhq.zza(i, (zziw) obj);
        }
        return zzhq.zzb(i, (zzjr) obj, zzkh);
    }

    static int zza(int i, List<?> list, zzkh zzkh) {
        int i2;
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int zze = zzhq.zze(i) * size;
        for (int i3 = 0; i3 < size; i3++) {
            Object obj = list.get(i3);
            if (obj instanceof zziw) {
                i2 = zzhq.zza((zziw) obj);
            } else {
                i2 = zzhq.zza((zzjr) obj, zzkh);
            }
            zze += i2;
        }
        return zze;
    }

    static int zzb(int i, List<zzgv> list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int zze = size * zzhq.zze(i);
        for (int i2 = 0; i2 < list.size(); i2++) {
            zze += zzhq.zzb(list.get(i2));
        }
        return zze;
    }

    static int zzb(int i, List<zzjr> list, zzkh zzkh) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            i2 += zzhq.zzc(i, list.get(i3), zzkh);
        }
        return i2;
    }

    public static zzkz<?, ?> zza() {
        return zzb;
    }

    public static zzkz<?, ?> zzb() {
        return zzc;
    }

    public static zzkz<?, ?> zzc() {
        return zzd;
    }

    private static zzkz<?, ?> zza(boolean z) {
        try {
            Class<?> zze = zze();
            if (zze == null) {
                return null;
            }
            return (zzkz) zze.getConstructor(new Class[]{Boolean.TYPE}).newInstance(new Object[]{Boolean.valueOf(z)});
        } catch (Throwable th) {
            return null;
        }
    }

    private static Class<?> zzd() {
        try {
            return Class.forName("com.google.protobuf.GeneratedMessage");
        } catch (Throwable th) {
            return null;
        }
    }

    private static Class<?> zze() {
        try {
            return Class.forName("com.google.protobuf.UnknownFieldSetSchema");
        } catch (Throwable th) {
            return null;
        }
    }

    static boolean zza(Object obj, Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }

    static <T> void zza(zzjk zzjk, T t, T t2, long j) {
        zzlf.zza((Object) t, j, zzjk.zza(zzlf.zzf(t, j), zzlf.zzf(t2, j)));
    }

    static <T, FT extends zzib<FT>> void zza(zzhv<FT> zzhv, T t, T t2) {
        zzhz<FT> zza2 = zzhv.zza((Object) t2);
        if (!zza2.zza.isEmpty()) {
            zzhv.zzb(t).zza(zza2);
        }
    }

    static <T, UT, UB> void zza(zzkz<UT, UB> zzkz, T t, T t2) {
        zzkz.zza((Object) t, zzkz.zzc(zzkz.zzb(t), zzkz.zzb(t2)));
    }

    static <UT, UB> UB zza(int i, List<Integer> list, zzin zzin, UB ub, zzkz<UT, UB> zzkz) {
        if (zzin == null) {
            return ub;
        }
        if (list instanceof RandomAccess) {
            int size = list.size();
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                int intValue = list.get(i3).intValue();
                if (zzin.zza(intValue)) {
                    if (i3 != i2) {
                        list.set(i2, Integer.valueOf(intValue));
                    }
                    i2++;
                } else {
                    ub = zza(i, intValue, ub, zzkz);
                }
            }
            if (i2 != size) {
                list.subList(i2, size).clear();
            }
        } else {
            Iterator<Integer> it = list.iterator();
            while (it.hasNext()) {
                int intValue2 = it.next().intValue();
                if (!zzin.zza(intValue2)) {
                    ub = zza(i, intValue2, ub, zzkz);
                    it.remove();
                }
            }
        }
        return ub;
    }

    static <UT, UB> UB zza(int i, int i2, UB ub, zzkz<UT, UB> zzkz) {
        if (ub == null) {
            ub = zzkz.zza();
        }
        zzkz.zza(ub, i, (long) i2);
        return ub;
    }
}
