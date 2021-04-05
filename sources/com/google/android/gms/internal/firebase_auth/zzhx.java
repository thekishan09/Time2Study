package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzig;
import java.io.IOException;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzhx extends zzhv<zzig.zzc> {
    zzhx() {
    }

    /* access modifiers changed from: package-private */
    public final boolean zza(zzjr zzjr) {
        return zzjr instanceof zzig.zzd;
    }

    /* access modifiers changed from: package-private */
    public final zzhz<zzig.zzc> zza(Object obj) {
        return ((zzig.zzd) obj).zzc;
    }

    /* access modifiers changed from: package-private */
    public final zzhz<zzig.zzc> zzb(Object obj) {
        zzig.zzd zzd = (zzig.zzd) obj;
        if (zzd.zzc.zzc()) {
            zzd.zzc = (zzhz) zzd.zzc.clone();
        }
        return zzd.zzc;
    }

    /* access modifiers changed from: package-private */
    public final void zzc(Object obj) {
        zza(obj).zzb();
    }

    /* access modifiers changed from: package-private */
    public final <UT, UB> UB zza(zzke zzke, Object obj, zzht zzht, zzhz<zzig.zzc> zzhz, UB ub, zzkz<UT, UB> zzkz) throws IOException {
        zzig.zzf zzf = (zzig.zzf) obj;
        throw new NoSuchMethodError();
    }

    /* access modifiers changed from: package-private */
    public final int zza(Map.Entry<?, ?> entry) {
        zzig.zzc zzc = (zzig.zzc) entry.getKey();
        throw new NoSuchMethodError();
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzlw zzlw, Map.Entry<?, ?> entry) throws IOException {
        zzig.zzc zzc = (zzig.zzc) entry.getKey();
        throw new NoSuchMethodError();
    }

    /* access modifiers changed from: package-private */
    public final Object zza(zzht zzht, zzjr zzjr, int i) {
        return zzht.zza(zzjr, i);
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzke zzke, Object obj, zzht zzht, zzhz<zzig.zzc> zzhz) throws IOException {
        zzig.zzf zzf = (zzig.zzf) obj;
        throw new NoSuchMethodError();
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzgv zzgv, Object obj, zzht zzht, zzhz<zzig.zzc> zzhz) throws IOException {
        zzig.zzf zzf = (zzig.zzf) obj;
        throw new NoSuchMethodError();
    }
}
