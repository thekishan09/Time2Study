package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzig;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzlv {

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static final class zza extends zzig<zza, C2503zza> implements zzjt {
        /* access modifiers changed from: private */
        public static final zza zzl;
        private static volatile zzkb<zza> zzm;
        private String zzc = "";
        private String zzd = "";
        private String zze = "";
        private String zzf = "";
        private zzio<String> zzg = zzig.zzad();
        private String zzh = "";
        private String zzi = "";
        private String zzj = "";
        private String zzk = "";

        private zza() {
        }

        /* renamed from: com.google.android.gms.internal.firebase_auth.zzlv$zza$zza  reason: collision with other inner class name */
        /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
        public static final class C2503zza extends zzig.zzb<zza, C2503zza> implements zzjt {
            private C2503zza() {
                super(zza.zzl);
            }

            public final C2503zza zza(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zza) this.zza).zza(str);
                return this;
            }

            public final C2503zza zzb(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zza) this.zza).zzb(str);
                return this;
            }

            /* synthetic */ C2503zza(zzlx zzlx) {
                this();
            }
        }

        /* access modifiers changed from: private */
        public final void zza(String str) {
            str.getClass();
            this.zzc = str;
        }

        /* access modifiers changed from: private */
        public final void zzb(String str) {
            str.getClass();
            this.zze = str;
        }

        public static C2503zza zza() {
            return (C2503zza) zzl.zzz();
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            switch (zzlx.zza[i - 1]) {
                case 1:
                    return new zza();
                case 2:
                    return new C2503zza((zzlx) null);
                case 3:
                    return zza((zzjr) zzl, "\u0000\t\u0000\u0000\u0001\u000b\t\u0000\u0001\u0000\u0001Ȉ\u0002Ȉ\u0003Ȉ\u0006Ȉ\u0007Ț\bȈ\tȈ\nȈ\u000bȈ", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk"});
                case 4:
                    return zzl;
                case 5:
                    zzkb<zza> zzkb = zzm;
                    if (zzkb == null) {
                        synchronized (zza.class) {
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
            zza zza = new zza();
            zzl = zza;
            zzig.zza(zza.class, zza);
        }
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static final class zzb extends zzig<zzb, zza> implements zzjt {
        /* access modifiers changed from: private */
        public static final zzb zzk;
        private static volatile zzkb<zzb> zzl;
        private String zzc = "";
        private long zzd;
        private String zze = "";
        private String zzf = "";
        private String zzg = "";
        private String zzh = "";
        private long zzi;
        private String zzj = "";

        private zzb() {
        }

        /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
        public static final class zza extends zzig.zzb<zzb, zza> implements zzjt {
            private zza() {
                super(zzb.zzk);
            }

            /* synthetic */ zza(zzlx zzlx) {
                this();
            }
        }

        public final String zza() {
            return this.zzc;
        }

        public final long zzb() {
            return this.zzd;
        }

        public final String zzc() {
            return this.zze;
        }

        public final String zzd() {
            return this.zzf;
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            switch (zzlx.zza[i - 1]) {
                case 1:
                    return new zzb();
                case 2:
                    return new zza((zzlx) null);
                case 3:
                    return zza((zzjr) zzk, "\u0000\b\u0000\u0000\u0001\b\b\u0000\u0000\u0000\u0001Ȉ\u0002\u0002\u0003Ȉ\u0004Ȉ\u0005Ȉ\u0006Ȉ\u0007\u0002\bȈ", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh", "zzi", "zzj"});
                case 4:
                    return zzk;
                case 5:
                    zzkb<zzb> zzkb = zzl;
                    if (zzkb == null) {
                        synchronized (zzb.class) {
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

        public static zzkb<zzb> zze() {
            return (zzkb) zzk.zza(zzig.zze.zzg, (Object) null, (Object) null);
        }

        static {
            zzb zzb = new zzb();
            zzk = zzb;
            zzig.zza(zzb.class, zzb);
        }
    }
}
