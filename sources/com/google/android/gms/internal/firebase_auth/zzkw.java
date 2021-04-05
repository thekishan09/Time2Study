package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzig;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzkw extends zzig<zzkw, zza> implements zzjt {
    /* access modifiers changed from: private */
    public static final zzkw zze;
    private static volatile zzkb<zzkw> zzf;
    private long zzc;
    private int zzd;

    private zzkw() {
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static final class zza extends zzig.zzb<zzkw, zza> implements zzjt {
        private zza() {
            super(zzkw.zze);
        }

        /* synthetic */ zza(zzky zzky) {
            this();
        }
    }

    public final long zza() {
        return this.zzc;
    }

    /* access modifiers changed from: protected */
    public final Object zza(int i, Object obj, Object obj2) {
        switch (zzky.zza[i - 1]) {
            case 1:
                return new zzkw();
            case 2:
                return new zza((zzky) null);
            case 3:
                return zza((zzjr) zze, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0002\u0002\u0004", new Object[]{"zzc", "zzd"});
            case 4:
                return zze;
            case 5:
                zzkb<zzkw> zzkb = zzf;
                if (zzkb == null) {
                    synchronized (zzkw.class) {
                        zzkb = zzf;
                        if (zzkb == null) {
                            zzkb = new zzig.zza<>(zze);
                            zzf = zzkb;
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

    public static zzkw zzb() {
        return zze;
    }

    static {
        zzkw zzkw = new zzkw();
        zze = zzkw;
        zzig.zza(zzkw.class, zzkw);
    }
}
