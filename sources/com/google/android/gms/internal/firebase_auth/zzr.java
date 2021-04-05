package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzig;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzr extends zzig<zzr, zza> implements zzjt {
    /* access modifiers changed from: private */
    public static final zzr zzk;
    private static volatile zzkb<zzr> zzl;
    private int zzc;
    private int zzd = 0;
    private Object zze;
    private int zzf = 0;
    private Object zzg;
    private String zzh = "";
    private String zzi = "";
    private zzkw zzj;

    private zzr() {
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static final class zza extends zzig.zzb<zzr, zza> implements zzjt {
        private zza() {
            super(zzr.zzk);
        }

        /* synthetic */ zza(zzs zzs) {
            this();
        }
    }

    public final String zza() {
        if (this.zzd == 1) {
            return (String) this.zze;
        }
        return "";
    }

    public final String zzb() {
        return this.zzh;
    }

    public final String zzc() {
        return this.zzi;
    }

    public final zzkw zzd() {
        zzkw zzkw = this.zzj;
        return zzkw == null ? zzkw.zzb() : zzkw;
    }

    public final String zze() {
        if (this.zzf == 5) {
            return (String) this.zzg;
        }
        return "";
    }

    /* access modifiers changed from: protected */
    public final Object zza(int i, Object obj, Object obj2) {
        switch (zzs.zza[i - 1]) {
            case 1:
                return new zzr();
            case 2:
                return new zza((zzs) null);
            case 3:
                return zza((zzjr) zzk, "\u0001\u0005\u0002\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001ျ\u0000\u0002ဈ\u0001\u0003ဈ\u0002\u0004ဉ\u0003\u0005ျ\u0001", new Object[]{"zze", "zzd", "zzg", "zzf", "zzc", "zzh", "zzi", "zzj"});
            case 4:
                return zzk;
            case 5:
                zzkb<zzr> zzkb = zzl;
                if (zzkb == null) {
                    synchronized (zzr.class) {
                        zzkb = zzl;
                        if (zzkb == null) {
                            zzkb = new zzig.zza<>(zzk);
                            zzl = zzkb;
                        }
                    }
                }
                return zzkb;
            case 6:
                return (byte) 1;
            case 7:
                return null;
            default:
                throw new UnsupportedOperationException();
        }
    }

    public static zzr zzf() {
        return zzk;
    }

    static {
        zzr zzr = new zzr();
        zzk = zzr;
        zzig.zza(zzr.class, zzr);
    }
}
