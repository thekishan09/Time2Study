package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzib;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzhz<T extends zzib<T>> {
    private static final zzhz zzd = new zzhz(true);
    final zzki<T, Object> zza;
    private boolean zzb;
    private boolean zzc;

    private zzhz() {
        this.zza = zzki.zza(16);
    }

    private zzhz(boolean z) {
        this(zzki.zza(0));
        zzb();
    }

    private zzhz(zzki<T, Object> zzki) {
        this.zza = zzki;
        zzb();
    }

    public static <T extends zzib<T>> zzhz<T> zza() {
        return zzd;
    }

    public final void zzb() {
        if (!this.zzb) {
            this.zza.zza();
            this.zzb = true;
        }
    }

    public final boolean zzc() {
        return this.zzb;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzhz)) {
            return false;
        }
        return this.zza.equals(((zzhz) obj).zza);
    }

    public final int hashCode() {
        return this.zza.hashCode();
    }

    public final Iterator<Map.Entry<T, Object>> zzd() {
        if (this.zzc) {
            return new zzix(this.zza.entrySet().iterator());
        }
        return this.zza.entrySet().iterator();
    }

    /* access modifiers changed from: package-private */
    public final Iterator<Map.Entry<T, Object>> zze() {
        if (this.zzc) {
            return new zzix(this.zza.zze().iterator());
        }
        return this.zza.zze().iterator();
    }

    private final Object zza(T t) {
        Object obj = this.zza.get(t);
        if (!(obj instanceof zzis)) {
            return obj;
        }
        zzis zzis = (zzis) obj;
        return zzis.zza();
    }

    private final void zzb(T t, Object obj) {
        if (!t.zzd()) {
            zza(t.zzb(), obj);
        } else if (obj instanceof List) {
            ArrayList arrayList = new ArrayList();
            arrayList.addAll((List) obj);
            ArrayList arrayList2 = arrayList;
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                Object obj2 = arrayList2.get(i);
                i++;
                zza(t.zzb(), obj2);
            }
            obj = arrayList;
        } else {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
        if (obj instanceof zzis) {
            this.zzc = true;
        }
        this.zza.put(t, obj);
    }

    private static void zza(zzlq zzlq, Object obj) {
        zzii.zza(obj);
        boolean z = true;
        switch (zzhy.zza[zzlq.zza().ordinal()]) {
            case 1:
                z = obj instanceof Integer;
                break;
            case 2:
                z = obj instanceof Long;
                break;
            case 3:
                z = obj instanceof Float;
                break;
            case 4:
                z = obj instanceof Double;
                break;
            case 5:
                z = obj instanceof Boolean;
                break;
            case 6:
                z = obj instanceof String;
                break;
            case 7:
                if (!(obj instanceof zzgv) && !(obj instanceof byte[])) {
                    z = false;
                    break;
                }
            case 8:
                if (!(obj instanceof Integer) && !(obj instanceof zzil)) {
                    z = false;
                    break;
                }
            case 9:
                if (!(obj instanceof zzjr) && !(obj instanceof zzis)) {
                    z = false;
                    break;
                }
            default:
                z = false;
                break;
        }
        if (!z) {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
    }

    public final boolean zzf() {
        for (int i = 0; i < this.zza.zzc(); i++) {
            if (!zza(this.zza.zzb(i))) {
                return false;
            }
        }
        for (Map.Entry<T, Object> zza2 : this.zza.zzd()) {
            if (!zza(zza2)) {
                return false;
            }
        }
        return true;
    }

    private static <T extends zzib<T>> boolean zza(Map.Entry<T, Object> entry) {
        zzib zzib = (zzib) entry.getKey();
        if (zzib.zzc() == zzlt.MESSAGE) {
            if (zzib.zzd()) {
                for (zzjr zzaa : (List) entry.getValue()) {
                    if (!zzaa.zzaa()) {
                        return false;
                    }
                }
            } else {
                Object value = entry.getValue();
                if (value instanceof zzjr) {
                    if (!((zzjr) value).zzaa()) {
                        return false;
                    }
                } else if (value instanceof zzis) {
                    return true;
                } else {
                    throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
                }
            }
        }
        return true;
    }

    public final void zza(zzhz<T> zzhz) {
        for (int i = 0; i < zzhz.zza.zzc(); i++) {
            zzb(zzhz.zza.zzb(i));
        }
        for (Map.Entry<T, Object> zzb2 : zzhz.zza.zzd()) {
            zzb(zzb2);
        }
    }

    private static Object zza(Object obj) {
        if (obj instanceof zzjx) {
            return ((zzjx) obj).zza();
        }
        if (!(obj instanceof byte[])) {
            return obj;
        }
        byte[] bArr = (byte[]) obj;
        byte[] bArr2 = new byte[bArr.length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        return bArr2;
    }

    private final void zzb(Map.Entry<T, Object> entry) {
        Object obj;
        zzib zzib = (zzib) entry.getKey();
        Object value = entry.getValue();
        if (value instanceof zzis) {
            zzis zzis = (zzis) value;
            value = zzis.zza();
        }
        if (zzib.zzd()) {
            Object zza2 = zza(zzib);
            if (zza2 == null) {
                zza2 = new ArrayList();
            }
            for (Object zza3 : (List) value) {
                ((List) zza2).add(zza(zza3));
            }
            this.zza.put(zzib, zza2);
        } else if (zzib.zzc() == zzlt.MESSAGE) {
            Object zza4 = zza(zzib);
            if (zza4 == null) {
                this.zza.put(zzib, zza(value));
                return;
            }
            if (zza4 instanceof zzjx) {
                obj = zzib.zza((zzjx) zza4, (zzjx) value);
            } else {
                obj = zzib.zza(((zzjr) zza4).zzae(), (zzjr) value).zzf();
            }
            this.zza.put(zzib, obj);
        } else {
            this.zza.put(zzib, zza(value));
        }
    }

    static void zza(zzhq zzhq, zzlq zzlq, int i, Object obj) throws IOException {
        if (zzlq == zzlq.GROUP) {
            zzjr zzjr = (zzjr) obj;
            zzii.zza(zzjr);
            zzhq.zza(i, 3);
            zzjr.zza(zzhq);
            zzhq.zza(i, 4);
            return;
        }
        zzhq.zza(i, zzlq.zzb());
        switch (zzhy.zzb[zzlq.ordinal()]) {
            case 1:
                zzhq.zza(((Double) obj).doubleValue());
                return;
            case 2:
                zzhq.zza(((Float) obj).floatValue());
                return;
            case 3:
                zzhq.zza(((Long) obj).longValue());
                return;
            case 4:
                zzhq.zza(((Long) obj).longValue());
                return;
            case 5:
                zzhq.zza(((Integer) obj).intValue());
                return;
            case 6:
                zzhq.zzc(((Long) obj).longValue());
                return;
            case 7:
                zzhq.zzd(((Integer) obj).intValue());
                return;
            case 8:
                zzhq.zza(((Boolean) obj).booleanValue());
                return;
            case 9:
                ((zzjr) obj).zza(zzhq);
                return;
            case 10:
                zzhq.zza((zzjr) obj);
                return;
            case 11:
                if (obj instanceof zzgv) {
                    zzhq.zza((zzgv) obj);
                    return;
                } else {
                    zzhq.zza((String) obj);
                    return;
                }
            case 12:
                if (obj instanceof zzgv) {
                    zzhq.zza((zzgv) obj);
                    return;
                }
                byte[] bArr = (byte[]) obj;
                zzhq.zzb(bArr, 0, bArr.length);
                return;
            case 13:
                zzhq.zzb(((Integer) obj).intValue());
                return;
            case 14:
                zzhq.zzd(((Integer) obj).intValue());
                return;
            case 15:
                zzhq.zzc(((Long) obj).longValue());
                return;
            case 16:
                zzhq.zzc(((Integer) obj).intValue());
                return;
            case 17:
                zzhq.zzb(((Long) obj).longValue());
                return;
            case 18:
                if (obj instanceof zzil) {
                    zzhq.zza(((zzil) obj).zza());
                    return;
                } else {
                    zzhq.zza(((Integer) obj).intValue());
                    return;
                }
            default:
                return;
        }
    }

    public final int zzg() {
        int i = 0;
        for (int i2 = 0; i2 < this.zza.zzc(); i2++) {
            i += zzc(this.zza.zzb(i2));
        }
        for (Map.Entry<T, Object> zzc2 : this.zza.zzd()) {
            i += zzc(zzc2);
        }
        return i;
    }

    private static int zzc(Map.Entry<T, Object> entry) {
        zzib zzib = (zzib) entry.getKey();
        Object value = entry.getValue();
        if (zzib.zzc() != zzlt.MESSAGE || zzib.zzd() || zzib.zze()) {
            return zza((zzib<?>) zzib, value);
        }
        if (value instanceof zzis) {
            return zzhq.zzb(((zzib) entry.getKey()).zza(), (zziw) (zzis) value);
        }
        return zzhq.zzb(((zzib) entry.getKey()).zza(), (zzjr) value);
    }

    static int zza(zzlq zzlq, int i, Object obj) {
        int zze = zzhq.zze(i);
        if (zzlq == zzlq.GROUP) {
            zzii.zza((zzjr) obj);
            zze <<= 1;
        }
        return zze + zzb(zzlq, obj);
    }

    private static int zzb(zzlq zzlq, Object obj) {
        switch (zzhy.zzb[zzlq.ordinal()]) {
            case 1:
                return zzhq.zzb(((Double) obj).doubleValue());
            case 2:
                return zzhq.zzb(((Float) obj).floatValue());
            case 3:
                return zzhq.zzd(((Long) obj).longValue());
            case 4:
                return zzhq.zze(((Long) obj).longValue());
            case 5:
                return zzhq.zzf(((Integer) obj).intValue());
            case 6:
                return zzhq.zzg(((Long) obj).longValue());
            case 7:
                return zzhq.zzi(((Integer) obj).intValue());
            case 8:
                return zzhq.zzb(((Boolean) obj).booleanValue());
            case 9:
                return zzhq.zzc((zzjr) obj);
            case 10:
                if (obj instanceof zzis) {
                    return zzhq.zza((zziw) (zzis) obj);
                }
                return zzhq.zzb((zzjr) obj);
            case 11:
                if (obj instanceof zzgv) {
                    return zzhq.zzb((zzgv) obj);
                }
                return zzhq.zzb((String) obj);
            case 12:
                if (obj instanceof zzgv) {
                    return zzhq.zzb((zzgv) obj);
                }
                return zzhq.zzb((byte[]) obj);
            case 13:
                return zzhq.zzg(((Integer) obj).intValue());
            case 14:
                return zzhq.zzj(((Integer) obj).intValue());
            case 15:
                return zzhq.zzh(((Long) obj).longValue());
            case 16:
                return zzhq.zzh(((Integer) obj).intValue());
            case 17:
                return zzhq.zzf(((Long) obj).longValue());
            case 18:
                if (obj instanceof zzil) {
                    return zzhq.zzk(((zzil) obj).zza());
                }
                return zzhq.zzk(((Integer) obj).intValue());
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    public static int zza(zzib<?> zzib, Object obj) {
        zzlq zzb2 = zzib.zzb();
        int zza2 = zzib.zza();
        if (!zzib.zzd()) {
            return zza(zzb2, zza2, obj);
        }
        int i = 0;
        if (zzib.zze()) {
            for (Object zzb3 : (List) obj) {
                i += zzb(zzb2, zzb3);
            }
            return zzhq.zze(zza2) + i + zzhq.zzl(i);
        }
        for (Object zza3 : (List) obj) {
            i += zza(zzb2, zza2, zza3);
        }
        return i;
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        zzhz zzhz = new zzhz();
        for (int i = 0; i < this.zza.zzc(); i++) {
            Map.Entry<T, Object> zzb2 = this.zza.zzb(i);
            zzhz.zzb((zzib) zzb2.getKey(), zzb2.getValue());
        }
        for (Map.Entry next : this.zza.zzd()) {
            zzhz.zzb((zzib) next.getKey(), next.getValue());
        }
        zzhz.zzc = this.zzc;
        return zzhz;
    }
}
