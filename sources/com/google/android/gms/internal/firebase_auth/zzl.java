package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzig;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzl extends zzig<zzl, zza> implements zzjt {
    /* access modifiers changed from: private */
    public static final zzl zzf;
    private static volatile zzkb<zzl> zzg;
    private int zzc;
    private String zzd = "";
    private String zze = "";

    private zzl() {
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static final class zza extends zzig.zzb<zzl, zza> implements zzjt {
        private zza() {
            super(zzl.zzf);
        }

        /* synthetic */ zza(zzn zzn) {
            this();
        }
    }

    /* access modifiers changed from: protected */
    public final Object zza(int i, Object obj, Object obj2) {
        switch (zzn.zza[i - 1]) {
            case 1:
                return new zzl();
            case 2:
                return new zza((zzn) null);
            case 3:
                return zza((zzjr) zzf, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဈ\u0001", new Object[]{"zzc", "zzd", "zze"});
            case 4:
                return zzf;
            case 5:
                zzkb<zzl> zzkb = zzg;
                if (zzkb == null) {
                    synchronized (zzl.class) {
                        zzkb = zzg;
                        if (zzkb == null) {
                            zzkb = new zzig.zza<>(zzf);
                            zzg = zzkb;
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
        zzl zzl = new zzl();
        zzf = zzl;
        zzig.zza(zzl.class, zzl);
    }
}
