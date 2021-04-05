package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzig;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzu extends zzig<zzu, zza> implements zzjt {
    /* access modifiers changed from: private */
    public static final zzu zzl;
    private static volatile zzkb<zzu> zzm;
    private int zzc;
    private String zzd = "";
    private String zze = "";
    private String zzf = "";
    private String zzg = "";
    private String zzh = "";
    private String zzi = "";
    private String zzj = "";
    private String zzk = "";

    private zzu() {
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static final class zza extends zzig.zzb<zzu, zza> implements zzjt {
        private zza() {
            super(zzu.zzl);
        }

        /* synthetic */ zza(zzt zzt) {
            this();
        }
    }

    public final String zza() {
        return this.zzd;
    }

    public final String zzb() {
        return this.zze;
    }

    public final String zzc() {
        return this.zzf;
    }

    public final String zzd() {
        return this.zzg;
    }

    public final String zze() {
        return this.zzh;
    }

    public final String zzf() {
        return this.zzk;
    }

    /* access modifiers changed from: protected */
    public final Object zza(int i, Object obj, Object obj2) {
        switch (zzt.zza[i - 1]) {
            case 1:
                return new zzu();
            case 2:
                return new zza((zzt) null);
            case 3:
                return zza((zzjr) zzl, "\u0001\b\u0000\u0001\u0001\t\b\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဈ\u0001\u0003ဈ\u0002\u0004ဈ\u0003\u0005ဈ\u0004\u0006ဈ\u0005\u0007ဈ\u0006\tဈ\u0007", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk"});
            case 4:
                return zzl;
            case 5:
                zzkb<zzu> zzkb = zzm;
                if (zzkb == null) {
                    synchronized (zzu.class) {
                        zzkb = zzm;
                        if (zzkb == null) {
                            zzkb = new zzig.zza<>(zzl);
                            zzm = zzkb;
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

    static {
        zzu zzu = new zzu();
        zzl = zzu;
        zzig.zza(zzu.class, zzu);
    }
}
