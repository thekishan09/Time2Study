package com.google.android.gms.internal.firebase_auth;

import java.io.IOException;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzlb extends zzkz<zzlc, zzlc> {
    zzlb() {
    }

    /* access modifiers changed from: package-private */
    public final boolean zza(zzke zzke) {
        return false;
    }

    private static void zza(Object obj, zzlc zzlc) {
        ((zzig) obj).zzb = zzlc;
    }

    /* access modifiers changed from: package-private */
    public final void zzd(Object obj) {
        ((zzig) obj).zzb.zzc();
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ int zzf(Object obj) {
        return ((zzlc) obj).zze();
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ int zze(Object obj) {
        return ((zzlc) obj).zzd();
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ Object zzc(Object obj, Object obj2) {
        zzlc zzlc = (zzlc) obj;
        zzlc zzlc2 = (zzlc) obj2;
        if (zzlc2.equals(zzlc.zza())) {
            return zzlc;
        }
        return zzlc.zza(zzlc, zzlc2);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zzb(Object obj, zzlw zzlw) throws IOException {
        ((zzlc) obj).zza(zzlw);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zza(Object obj, zzlw zzlw) throws IOException {
        ((zzlc) obj).zzb(zzlw);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zzb(Object obj, Object obj2) {
        zza(obj, (zzlc) obj2);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ Object zzc(Object obj) {
        zzlc zzlc = ((zzig) obj).zzb;
        if (zzlc != zzlc.zza()) {
            return zzlc;
        }
        zzlc zzb = zzlc.zzb();
        zza(obj, zzb);
        return zzb;
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ Object zzb(Object obj) {
        return ((zzig) obj).zzb;
    }

    /* access modifiers changed from: package-private */
    public final /* bridge */ /* synthetic */ void zza(Object obj, Object obj2) {
        zza(obj, (zzlc) obj2);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ Object zza(Object obj) {
        zzlc zzlc = (zzlc) obj;
        zzlc.zzc();
        return zzlc;
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ Object zza() {
        return zzlc.zzb();
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zza(Object obj, int i, Object obj2) {
        ((zzlc) obj).zza((i << 3) | 3, (Object) (zzlc) obj2);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zza(Object obj, int i, zzgv zzgv) {
        ((zzlc) obj).zza((i << 3) | 2, (Object) zzgv);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zzb(Object obj, int i, long j) {
        ((zzlc) obj).zza((i << 3) | 1, (Object) Long.valueOf(j));
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zza(Object obj, int i, int i2) {
        ((zzlc) obj).zza((i << 3) | 5, (Object) Integer.valueOf(i2));
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zza(Object obj, int i, long j) {
        ((zzlc) obj).zza(i << 3, (Object) Long.valueOf(j));
    }
}
