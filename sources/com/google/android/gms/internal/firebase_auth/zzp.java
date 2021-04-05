package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzig;
import java.util.List;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzp {

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static final class zza extends zzig<zza, C2504zza> implements zzjt {
        /* access modifiers changed from: private */
        public static final zza zzs;
        private static volatile zzkb<zza> zzt;
        private int zzc;
        private String zzd = "";
        private String zze = "";
        private String zzf = "";
        private String zzg = "";
        private String zzh = "";
        private String zzi = "";
        private String zzj = "";
        private String zzk = "";
        private String zzl = "";
        private String zzm = "";
        private String zzn = "";
        private String zzo = "";
        private String zzp = "";
        private zzio<zzl> zzq = zzad();
        private String zzr = "";

        private zza() {
        }

        /* renamed from: com.google.android.gms.internal.firebase_auth.zzp$zza$zza  reason: collision with other inner class name */
        /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
        public static final class C2504zza extends zzig.zzb<zza, C2504zza> implements zzjt {
            private C2504zza() {
                super(zza.zzs);
            }

            public final C2504zza zza(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zza) this.zza).zza(str);
                return this;
            }

            public final C2504zza zzb(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zza) this.zza).zzb(str);
                return this;
            }

            public final C2504zza zzc(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zza) this.zza).zzc(str);
                return this;
            }

            /* synthetic */ C2504zza(zzo zzo) {
                this();
            }
        }

        /* access modifiers changed from: private */
        public final void zza(String str) {
            str.getClass();
            this.zzc |= 1;
            this.zzd = str;
        }

        /* access modifiers changed from: private */
        public final void zzb(String str) {
            str.getClass();
            this.zzc |= 2;
            this.zze = str;
        }

        /* access modifiers changed from: private */
        public final void zzc(String str) {
            str.getClass();
            this.zzc |= 8192;
            this.zzr = str;
        }

        public static C2504zza zza() {
            return (C2504zza) zzs.zzz();
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            switch (zzo.zza[i - 1]) {
                case 1:
                    return new zza();
                case 2:
                    return new C2504zza((zzo) null);
                case 3:
                    return zza((zzjr) zzs, "\u0001\u000f\u0000\u0001\u0001\u000f\u000f\u0000\u0001\u0000\u0001ဈ\u0000\u0002ဈ\u0001\u0003ဈ\u0002\u0004ဈ\u0003\u0005ဈ\u0004\u0006ဈ\u0005\u0007ဈ\u0006\bဈ\u0007\tဈ\b\nဈ\t\u000bဈ\n\fဈ\u000b\rဈ\f\u000e\u001b\u000fဈ\r", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk", "zzl", "zzm", "zzn", "zzo", "zzp", "zzq", zzl.class, "zzr"});
                case 4:
                    return zzs;
                case 5:
                    zzkb<zza> zzkb = zzt;
                    if (zzkb == null) {
                        synchronized (zza.class) {
                            zzkb = zzt;
                            if (zzkb == null) {
                                zzkb = new zzig.zza<>(zzs);
                                zzt = zzkb;
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
            zzs = zza;
            zzig.zza(zza.class, zza);
        }
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static final class zzb extends zzig<zzb, zza> implements zzjt {
        /* access modifiers changed from: private */
        public static final zzb zzn;
        private static volatile zzkb<zzb> zzo;
        private int zzc;
        private String zzd = "";
        private String zze = "";
        private zzio<String> zzf = zzig.zzad();
        private boolean zzg;
        private String zzh = "";
        private boolean zzi;
        private boolean zzj;
        private String zzk = "";
        private zzio<String> zzl = zzig.zzad();
        private byte zzm = 2;

        private zzb() {
        }

        /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
        public static final class zza extends zzig.zzb<zzb, zza> implements zzjt {
            private zza() {
                super(zzb.zzn);
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        public final String zza() {
            return this.zze;
        }

        public final List<String> zzb() {
            return this.zzf;
        }

        public final int zzc() {
            return this.zzf.size();
        }

        public final boolean zzd() {
            return this.zzg;
        }

        public final String zze() {
            return this.zzh;
        }

        public final boolean zzf() {
            return this.zzi;
        }

        public final List<String> zzg() {
            return this.zzl;
        }

        public final int zzh() {
            return this.zzl.size();
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            int i2 = 1;
            switch (zzo.zza[i - 1]) {
                case 1:
                    return new zzb();
                case 2:
                    return new zza((zzo) null);
                case 3:
                    return zza((zzjr) zzn, "\u0001\t\u0000\u0001\u0001\t\t\u0000\u0002\u0001\u0001ᔈ\u0000\u0002ဈ\u0001\u0003\u001a\u0004ဇ\u0002\u0005ဈ\u0003\u0006ဇ\u0004\u0007ဇ\u0005\bဈ\u0006\t\u001a", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk", "zzl"});
                case 4:
                    return zzn;
                case 5:
                    zzkb<zzb> zzkb = zzo;
                    if (zzkb == null) {
                        synchronized (zzb.class) {
                            zzkb = zzo;
                            if (zzkb == null) {
                                zzkb = new zzig.zza<>(zzn);
                                zzo = zzkb;
                            }
                        }
                    }
                    return zzkb;
                case 6:
                    return Byte.valueOf(this.zzm);
                case 7:
                    if (obj == null) {
                        i2 = 0;
                    }
                    this.zzm = (byte) i2;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static zzkb<zzb> zzi() {
            return (zzkb) zzn.zza(zzig.zze.zzg, (Object) null, (Object) null);
        }

        static {
            zzb zzb = new zzb();
            zzn = zzb;
            zzig.zza(zzb.class, zzb);
        }
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static final class zzc extends zzig<zzc, zza> implements zzjt {
        /* access modifiers changed from: private */
        public static final zzc zzg;
        private static volatile zzkb<zzc> zzh;
        private int zzc;
        private String zzd = "";
        private long zze;
        private String zzf = "";

        private zzc() {
        }

        /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
        public static final class zza extends zzig.zzb<zzc, zza> implements zzjt {
            private zza() {
                super(zzc.zzg);
            }

            public final zza zza(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzc) this.zza).zza(str);
                return this;
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        /* access modifiers changed from: private */
        public final void zza(String str) {
            str.getClass();
            this.zzc |= 4;
            this.zzf = str;
        }

        public static zza zza() {
            return (zza) zzg.zzz();
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            switch (zzo.zza[i - 1]) {
                case 1:
                    return new zzc();
                case 2:
                    return new zza((zzo) null);
                case 3:
                    return zza((zzjr) zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဂ\u0001\u0003ဈ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzkb<zzc> zzkb = zzh;
                    if (zzkb == null) {
                        synchronized (zzc.class) {
                            zzkb = zzh;
                            if (zzkb == null) {
                                zzkb = new zzig.zza<>(zzg);
                                zzh = zzkb;
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
            zzc zzc2 = new zzc();
            zzg = zzc2;
            zzig.zza(zzc.class, zzc2);
        }
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static final class zzd extends zzig<zzd, zza> implements zzjt {
        /* access modifiers changed from: private */
        public static final zzd zzh;
        private static volatile zzkb<zzd> zzi;
        private int zzc;
        private String zzd = "";
        private String zze = "";
        private String zzf = "";
        private String zzg = "";

        private zzd() {
        }

        /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
        public static final class zza extends zzig.zzb<zzd, zza> implements zzjt {
            private zza() {
                super(zzd.zzh);
            }

            public final zza zza(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzd) this.zza).zza(str);
                return this;
            }

            public final zza zzb(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzd) this.zza).zzb(str);
                return this;
            }

            public final zza zzc(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzd) this.zza).zzc(str);
                return this;
            }

            public final zza zzd(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzd) this.zza).zzd(str);
                return this;
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        /* access modifiers changed from: private */
        public final void zza(String str) {
            str.getClass();
            this.zzc |= 1;
            this.zzd = str;
        }

        /* access modifiers changed from: private */
        public final void zzb(String str) {
            str.getClass();
            this.zzc |= 2;
            this.zze = str;
        }

        /* access modifiers changed from: private */
        public final void zzc(String str) {
            str.getClass();
            this.zzc |= 4;
            this.zzf = str;
        }

        /* access modifiers changed from: private */
        public final void zzd(String str) {
            str.getClass();
            this.zzc |= 8;
            this.zzg = str;
        }

        public static zza zza() {
            return (zza) zzh.zzz();
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            switch (zzo.zza[i - 1]) {
                case 1:
                    return new zzd();
                case 2:
                    return new zza((zzo) null);
                case 3:
                    return zza((zzjr) zzh, "\u0001\u0004\u0000\u0001\u0001\u0006\u0004\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဈ\u0001\u0003ဈ\u0002\u0006ဈ\u0003", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg"});
                case 4:
                    return zzh;
                case 5:
                    zzkb<zzd> zzkb = zzi;
                    if (zzkb == null) {
                        synchronized (zzd.class) {
                            zzkb = zzi;
                            if (zzkb == null) {
                                zzkb = new zzig.zza<>(zzh);
                                zzi = zzkb;
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
            zzd zzd2 = new zzd();
            zzh = zzd2;
            zzig.zza(zzd.class, zzd2);
        }
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static final class zze extends zzig<zze, zza> implements zzjt {
        /* access modifiers changed from: private */
        public static final zze zzn;
        private static volatile zzkb<zze> zzo;
        private int zzc;
        private String zzd = "";
        private String zze = "";
        private String zzf = "";
        private String zzg = "";
        private long zzh;
        private String zzi = "";
        private boolean zzj;
        private String zzk = "";
        private zzio<zzr> zzl = zzad();
        private byte zzm = 2;

        private zze() {
        }

        /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
        public static final class zza extends zzig.zzb<zze, zza> implements zzjt {
            private zza() {
                super(zze.zzn);
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        public final String zza() {
            return this.zze;
        }

        public final String zzb() {
            return this.zzf;
        }

        public final String zzc() {
            return this.zzg;
        }

        public final long zzd() {
            return this.zzh;
        }

        public final String zze() {
            return this.zzi;
        }

        public final boolean zzf() {
            return this.zzj;
        }

        public final String zzg() {
            return this.zzk;
        }

        public final List<zzr> zzh() {
            return this.zzl;
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            int i2 = 1;
            switch (zzo.zza[i - 1]) {
                case 1:
                    return new zze();
                case 2:
                    return new zza((zzo) null);
                case 3:
                    return zza((zzjr) zzn, "\u0001\t\u0000\u0001\u0001\t\t\u0000\u0001\u0001\u0001ᔈ\u0000\u0002ဈ\u0001\u0003ဈ\u0002\u0004ဈ\u0003\u0005ဂ\u0004\u0006ဈ\u0005\u0007ဇ\u0006\bဈ\u0007\t\u001b", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk", "zzl", zzr.class});
                case 4:
                    return zzn;
                case 5:
                    zzkb<zze> zzkb = zzo;
                    if (zzkb == null) {
                        synchronized (zze.class) {
                            zzkb = zzo;
                            if (zzkb == null) {
                                zzkb = new zzig.zza<>(zzn);
                                zzo = zzkb;
                            }
                        }
                    }
                    return zzkb;
                case 6:
                    return Byte.valueOf(this.zzm);
                case 7:
                    if (obj == null) {
                        i2 = 0;
                    }
                    this.zzm = (byte) i2;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static zzkb<zze> zzi() {
            return (zzkb) zzn.zza(zzig.zze.zzg, (Object) null, (Object) null);
        }

        static {
            zze zze2 = new zze();
            zzn = zze2;
            zzig.zza(zze.class, zze2);
        }
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static final class zzf extends zzig<zzf, zza> implements zzjt {
        /* access modifiers changed from: private */
        public static final zzf zzh;
        private static volatile zzkb<zzf> zzi;
        private int zzc;
        private String zzd = "";
        private zzio<String> zze = zzig.zzad();
        private zzio<String> zzf = zzig.zzad();
        private long zzg;

        private zzf() {
        }

        /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
        public static final class zza extends zzig.zzb<zzf, zza> implements zzjt {
            private zza() {
                super(zzf.zzh);
            }

            public final zza zza(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzf) this.zza).zza(str);
                return this;
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        /* access modifiers changed from: private */
        public final void zza(String str) {
            str.getClass();
            this.zzc |= 1;
            this.zzd = str;
        }

        public static zza zza() {
            return (zza) zzh.zzz();
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            switch (zzo.zza[i - 1]) {
                case 1:
                    return new zzf();
                case 2:
                    return new zza((zzo) null);
                case 3:
                    return zza((zzjr) zzh, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0002\u0000\u0001ဈ\u0000\u0002\u001a\u0003\u001a\u0004ဂ\u0001", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg"});
                case 4:
                    return zzh;
                case 5:
                    zzkb<zzf> zzkb = zzi;
                    if (zzkb == null) {
                        synchronized (zzf.class) {
                            zzkb = zzi;
                            if (zzkb == null) {
                                zzkb = new zzig.zza<>(zzh);
                                zzi = zzkb;
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
            zzf zzf2 = new zzf();
            zzh = zzf2;
            zzig.zza(zzf.class, zzf2);
        }
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static final class zzg extends zzig<zzg, zza> implements zzjt {
        /* access modifiers changed from: private */
        public static final zzg zzg;
        private static volatile zzkb<zzg> zzh;
        private int zzc;
        private String zzd = "";
        private zzio<zzz> zze = zzad();
        private byte zzf = 2;

        private zzg() {
        }

        /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
        public static final class zza extends zzig.zzb<zzg, zza> implements zzjt {
            private zza() {
                super(zzg.zzg);
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        public final int zza() {
            return this.zze.size();
        }

        public final zzz zza(int i) {
            return (zzz) this.zze.get(i);
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            int i2 = 1;
            switch (zzo.zza[i - 1]) {
                case 1:
                    return new zzg();
                case 2:
                    return new zza((zzo) null);
                case 3:
                    return zza((zzjr) zzg, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0001\u0001ᔈ\u0000\u0002\u001b", new Object[]{"zzc", "zzd", "zze", zzz.class});
                case 4:
                    return zzg;
                case 5:
                    zzkb<zzg> zzkb = zzh;
                    if (zzkb == null) {
                        synchronized (zzg.class) {
                            zzkb = zzh;
                            if (zzkb == null) {
                                zzkb = new zzig.zza<>(zzg);
                                zzh = zzkb;
                            }
                        }
                    }
                    return zzkb;
                case 6:
                    return Byte.valueOf(this.zzf);
                case 7:
                    if (obj == null) {
                        i2 = 0;
                    }
                    this.zzf = (byte) i2;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static zzkb<zzg> zzb() {
            return (zzkb) zzg.zza(zzig.zze.zzg, (Object) null, (Object) null);
        }

        static {
            zzg zzg2 = new zzg();
            zzg = zzg2;
            zzig.zza(zzg.class, zzg2);
        }
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static final class zzh extends zzig<zzh, zza> implements zzjt {
        /* access modifiers changed from: private */
        public static final zzh zzu;
        private static volatile zzkb<zzh> zzv;
        private int zzc;
        private int zzd;
        private String zze = "";
        private String zzf = "";
        private String zzg = "";
        private String zzh = "";
        private String zzi = "";
        private String zzj = "";
        private String zzk = "";
        private String zzl = "";
        private String zzm = "";
        private String zzn = "";
        private boolean zzo;
        private String zzp = "";
        private boolean zzq;
        private String zzr = "";
        private String zzs = "";
        private boolean zzt;

        private zzh() {
        }

        /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
        public static final class zza extends zzig.zzb<zzh, zza> implements zzjt {
            private zza() {
                super(zzh.zzu);
            }

            public final zza zza(zzgm zzgm) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzh) this.zza).zza(zzgm);
                return this;
            }

            public final zza zza(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzh) this.zza).zza(str);
                return this;
            }

            public final zza zzb(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzh) this.zza).zzb(str);
                return this;
            }

            public final zza zzc(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzh) this.zza).zzc(str);
                return this;
            }

            public final zza zzd(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzh) this.zza).zzd(str);
                return this;
            }

            public final zza zze(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzh) this.zza).zze(str);
                return this;
            }

            public final zza zzf(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzh) this.zza).zzf(str);
                return this;
            }

            public final zza zzg(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzh) this.zza).zzg(str);
                return this;
            }

            public final zza zza(boolean z) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzh) this.zza).zza(z);
                return this;
            }

            public final zza zzh(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzh) this.zza).zzh(str);
                return this;
            }

            public final zza zzb(boolean z) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzh) this.zza).zzb(z);
                return this;
            }

            public final zza zzi(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzh) this.zza).zzi(str);
                return this;
            }

            public final zza zzj(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzh) this.zza).zzj(str);
                return this;
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        /* access modifiers changed from: private */
        public final void zza(zzgm zzgm) {
            this.zzd = zzgm.zza();
            this.zzc |= 1;
        }

        /* access modifiers changed from: private */
        public final void zza(String str) {
            str.getClass();
            this.zzc |= 2;
            this.zze = str;
        }

        /* access modifiers changed from: private */
        public final void zzb(String str) {
            str.getClass();
            this.zzc |= 32;
            this.zzi = str;
        }

        /* access modifiers changed from: private */
        public final void zzc(String str) {
            str.getClass();
            this.zzc |= 64;
            this.zzj = str;
        }

        /* access modifiers changed from: private */
        public final void zzd(String str) {
            str.getClass();
            this.zzc |= 128;
            this.zzk = str;
        }

        /* access modifiers changed from: private */
        public final void zze(String str) {
            str.getClass();
            this.zzc |= 256;
            this.zzl = str;
        }

        /* access modifiers changed from: private */
        public final void zzf(String str) {
            str.getClass();
            this.zzc |= 512;
            this.zzm = str;
        }

        /* access modifiers changed from: private */
        public final void zzg(String str) {
            str.getClass();
            this.zzc |= 1024;
            this.zzn = str;
        }

        /* access modifiers changed from: private */
        public final void zza(boolean z) {
            this.zzc |= 2048;
            this.zzo = z;
        }

        /* access modifiers changed from: private */
        public final void zzh(String str) {
            str.getClass();
            this.zzc |= 4096;
            this.zzp = str;
        }

        /* access modifiers changed from: private */
        public final void zzb(boolean z) {
            this.zzc |= 8192;
            this.zzq = z;
        }

        /* access modifiers changed from: private */
        public final void zzi(String str) {
            str.getClass();
            this.zzc |= 16384;
            this.zzr = str;
        }

        /* access modifiers changed from: private */
        public final void zzj(String str) {
            str.getClass();
            this.zzc |= 32768;
            this.zzs = str;
        }

        public static zza zza() {
            return (zza) zzu.zzz();
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            switch (zzo.zza[i - 1]) {
                case 1:
                    return new zzh();
                case 2:
                    return new zza((zzo) null);
                case 3:
                    return zza((zzjr) zzu, "\u0001\u0011\u0000\u0001\u0001\u0013\u0011\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဈ\u0001\u0003ဈ\u0002\u0004ဈ\u0003\u0005ဈ\u0004\u0006ဈ\u0005\u0007ဈ\u0006\bဈ\u0007\tဈ\b\nဈ\t\u000bဈ\n\fဇ\u000b\rဈ\f\u000eဇ\r\u000fဈ\u000e\u0012ဈ\u000f\u0013ဇ\u0010", new Object[]{"zzc", "zzd", zzgm.zzb(), "zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk", "zzl", "zzm", "zzn", "zzo", "zzp", "zzq", "zzr", "zzs", "zzt"});
                case 4:
                    return zzu;
                case 5:
                    zzkb<zzh> zzkb = zzv;
                    if (zzkb == null) {
                        synchronized (zzh.class) {
                            zzkb = zzv;
                            if (zzkb == null) {
                                zzkb = new zzig.zza<>(zzu);
                                zzv = zzkb;
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
            zzh zzh2 = new zzh();
            zzu = zzh2;
            zzig.zza(zzh.class, zzh2);
        }
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static final class zzi extends zzig<zzi, zza> implements zzjt {
        /* access modifiers changed from: private */
        public static final zzi zzi;
        private static volatile zzkb<zzi> zzj;
        private int zzc;
        private String zzd = "";
        private String zze = "";
        private String zzf = "";
        private String zzg = "";
        private byte zzh = 2;

        private zzi() {
        }

        /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
        public static final class zza extends zzig.zzb<zzi, zza> implements zzjt {
            private zza() {
                super(zzi.zzi);
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            int i2 = 1;
            switch (zzo.zza[i - 1]) {
                case 1:
                    return new zzi();
                case 2:
                    return new zza((zzo) null);
                case 3:
                    return zza((zzjr) zzi, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0001\u0001ᔈ\u0000\u0002ဈ\u0001\u0003ဈ\u0002\u0004ဈ\u0003", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg"});
                case 4:
                    return zzi;
                case 5:
                    zzkb<zzi> zzkb = zzj;
                    if (zzkb == null) {
                        synchronized (zzi.class) {
                            zzkb = zzj;
                            if (zzkb == null) {
                                zzkb = new zzig.zza<>(zzi);
                                zzj = zzkb;
                            }
                        }
                    }
                    return zzkb;
                case 6:
                    return Byte.valueOf(this.zzh);
                case 7:
                    if (obj == null) {
                        i2 = 0;
                    }
                    this.zzh = (byte) i2;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static zzkb<zzi> zza() {
            return (zzkb) zzi.zza(zzig.zze.zzg, (Object) null, (Object) null);
        }

        static {
            zzi zzi2 = new zzi();
            zzi = zzi2;
            zzig.zza(zzi.class, zzi2);
        }
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static final class zzj extends zzig<zzj, zza> implements zzjt {
        /* access modifiers changed from: private */
        public static final zzj zzi;
        private static volatile zzkb<zzj> zzj;
        private int zzc;
        private String zzd = "";
        private String zze = "";
        private String zzf = "";
        private String zzg = "";
        private String zzh = "";

        private zzj() {
        }

        /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
        public static final class zza extends zzig.zzb<zzj, zza> implements zzjt {
            private zza() {
                super(zzj.zzi);
            }

            public final zza zza(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzj) this.zza).zza(str);
                return this;
            }

            public final zza zzb(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzj) this.zza).zzb(str);
                return this;
            }

            public final zza zzc(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzj) this.zza).zzc(str);
                return this;
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        /* access modifiers changed from: private */
        public final void zza(String str) {
            str.getClass();
            this.zzc |= 1;
            this.zzd = str;
        }

        /* access modifiers changed from: private */
        public final void zzb(String str) {
            str.getClass();
            this.zzc |= 2;
            this.zze = str;
        }

        /* access modifiers changed from: private */
        public final void zzc(String str) {
            str.getClass();
            this.zzc |= 16;
            this.zzh = str;
        }

        public static zza zza() {
            return (zza) zzi.zzz();
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            switch (zzo.zza[i - 1]) {
                case 1:
                    return new zzj();
                case 2:
                    return new zza((zzo) null);
                case 3:
                    return zza((zzjr) zzi, "\u0001\u0005\u0000\u0001\u0001\u0006\u0005\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဈ\u0001\u0003ဈ\u0002\u0004ဈ\u0003\u0006ဈ\u0004", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh"});
                case 4:
                    return zzi;
                case 5:
                    zzkb<zzj> zzkb = zzj;
                    if (zzkb == null) {
                        synchronized (zzj.class) {
                            zzkb = zzj;
                            if (zzkb == null) {
                                zzkb = new zzig.zza<>(zzi);
                                zzj = zzkb;
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
            zzj zzj2 = new zzj();
            zzi = zzj2;
            zzig.zza(zzj.class, zzj2);
        }
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static final class zzk extends zzig<zzk, zza> implements zzjt {
        /* access modifiers changed from: private */
        public static final zzk zzj;
        private static volatile zzkb<zzk> zzk;
        private int zzc;
        private String zzd = "";
        private String zze = "";
        private String zzf = "";
        private int zzg;
        private zzr zzh;
        private byte zzi = 2;

        private zzk() {
        }

        /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
        public static final class zza extends zzig.zzb<zzk, zza> implements zzjt {
            private zza() {
                super(zzk.zzj);
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        public final String zza() {
            return this.zze;
        }

        public final String zzb() {
            return this.zzf;
        }

        public final zzgm zzc() {
            zzgm zza2 = zzgm.zza(this.zzg);
            return zza2 == null ? zzgm.OOB_REQ_TYPE_UNSPECIFIED : zza2;
        }

        public final boolean zzd() {
            return (this.zzc & 16) != 0;
        }

        public final zzr zze() {
            zzr zzr = this.zzh;
            return zzr == null ? zzr.zzf() : zzr;
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            int i2 = 1;
            switch (zzo.zza[i - 1]) {
                case 1:
                    return new zzk();
                case 2:
                    return new zza((zzo) null);
                case 3:
                    return zza((zzjr) zzj, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0001\u0001ᔈ\u0000\u0002ဈ\u0001\u0003ဈ\u0002\u0004ဌ\u0003\u0005ဉ\u0004", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", zzgm.zzb(), "zzh"});
                case 4:
                    return zzj;
                case 5:
                    zzkb<zzk> zzkb = zzk;
                    if (zzkb == null) {
                        synchronized (zzk.class) {
                            zzkb = zzk;
                            if (zzkb == null) {
                                zzkb = new zzig.zza<>(zzj);
                                zzk = zzkb;
                            }
                        }
                    }
                    return zzkb;
                case 6:
                    return Byte.valueOf(this.zzi);
                case 7:
                    if (obj == null) {
                        i2 = 0;
                    }
                    this.zzi = (byte) i2;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static zzkb<zzk> zzf() {
            return (zzkb) zzj.zza(zzig.zze.zzg, (Object) null, (Object) null);
        }

        static {
            zzk zzk2 = new zzk();
            zzj = zzk2;
            zzig.zza(zzk.class, zzk2);
        }
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static final class zzl extends zzig<zzl, zzb> implements zzjt {
        /* access modifiers changed from: private */
        public static final zzl zzj;
        private static volatile zzkb<zzl> zzk;
        private int zzc;
        private String zzd = "";
        private String zze = "";
        private String zzf = "";
        private String zzg = "";
        private String zzh = "";
        private zza zzi;

        /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
        public static final class zza extends zzig<zza, C2505zza> implements zzjt {
            /* access modifiers changed from: private */
            public static final zza zze;
            private static volatile zzkb<zza> zzf;
            private int zzc;
            private String zzd = "";

            private zza() {
            }

            /* renamed from: com.google.android.gms.internal.firebase_auth.zzp$zzl$zza$zza  reason: collision with other inner class name */
            /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
            public static final class C2505zza extends zzig.zzb<zza, C2505zza> implements zzjt {
                private C2505zza() {
                    super(zza.zze);
                }

                public final C2505zza zza(String str) {
                    if (this.zzb) {
                        zzb();
                        this.zzb = false;
                    }
                    ((zza) this.zza).zza(str);
                    return this;
                }

                /* synthetic */ C2505zza(zzo zzo) {
                    this();
                }
            }

            /* access modifiers changed from: private */
            public final void zza(String str) {
                str.getClass();
                this.zzc |= 1;
                this.zzd = str;
            }

            public static C2505zza zza() {
                return (C2505zza) zze.zzz();
            }

            /* access modifiers changed from: protected */
            public final Object zza(int i, Object obj, Object obj2) {
                switch (zzo.zza[i - 1]) {
                    case 1:
                        return new zza();
                    case 2:
                        return new C2505zza((zzo) null);
                    case 3:
                        return zza((zzjr) zze, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ဈ\u0000", new Object[]{"zzc", "zzd"});
                    case 4:
                        return zze;
                    case 5:
                        zzkb<zza> zzkb = zzf;
                        if (zzkb == null) {
                            synchronized (zza.class) {
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

            static {
                zza zza = new zza();
                zze = zza;
                zzig.zza(zza.class, zza);
            }
        }

        private zzl() {
        }

        /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
        public static final class zzb extends zzig.zzb<zzl, zzb> implements zzjt {
            private zzb() {
                super(zzl.zzj);
            }

            public final zzb zza(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzl) this.zza).zza(str);
                return this;
            }

            public final zzb zzb(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzl) this.zza).zzb(str);
                return this;
            }

            public final zzb zzc(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzl) this.zza).zzc(str);
                return this;
            }

            public final zzb zza(zza zza) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzl) this.zza).zza(zza);
                return this;
            }

            /* synthetic */ zzb(zzo zzo) {
                this();
            }
        }

        /* access modifiers changed from: private */
        public final void zza(String str) {
            str.getClass();
            this.zzc |= 1;
            this.zzd = str;
        }

        /* access modifiers changed from: private */
        public final void zzb(String str) {
            str.getClass();
            this.zzc |= 8;
            this.zzg = str;
        }

        /* access modifiers changed from: private */
        public final void zzc(String str) {
            str.getClass();
            this.zzc |= 16;
            this.zzh = str;
        }

        /* access modifiers changed from: private */
        public final void zza(zza zza2) {
            zza2.getClass();
            this.zzi = zza2;
            this.zzc |= 32;
        }

        public static zzb zza() {
            return (zzb) zzj.zzz();
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            switch (zzo.zza[i - 1]) {
                case 1:
                    return new zzl();
                case 2:
                    return new zzb((zzo) null);
                case 3:
                    return zza((zzjr) zzj, "\u0001\u0006\u0000\u0001\u0001\u0007\u0006\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဈ\u0001\u0003ဈ\u0002\u0004ဈ\u0003\u0005ဈ\u0004\u0007ဉ\u0005", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh", "zzi"});
                case 4:
                    return zzj;
                case 5:
                    zzkb<zzl> zzkb = zzk;
                    if (zzkb == null) {
                        synchronized (zzl.class) {
                            zzkb = zzk;
                            if (zzkb == null) {
                                zzkb = new zzig.zza<>(zzj);
                                zzk = zzkb;
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
            zzj = zzl;
            zzig.zza(zzl.class, zzl);
        }
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static final class zzm extends zzig<zzm, zza> implements zzjt {
        /* access modifiers changed from: private */
        public static final zzm zze;
        private static volatile zzkb<zzm> zzf;
        private int zzc;
        private String zzd = "";

        private zzm() {
        }

        /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
        public static final class zza extends zzig.zzb<zzm, zza> implements zzjt {
            private zza() {
                super(zzm.zze);
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        public final String zza() {
            return this.zzd;
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            switch (zzo.zza[i - 1]) {
                case 1:
                    return new zzm();
                case 2:
                    return new zza((zzo) null);
                case 3:
                    return zza((zzjr) zze, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ဈ\u0000", new Object[]{"zzc", "zzd"});
                case 4:
                    return zze;
                case 5:
                    zzkb<zzm> zzkb = zzf;
                    if (zzkb == null) {
                        synchronized (zzm.class) {
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

        public static zzkb<zzm> zzb() {
            return (zzkb) zze.zza(zzig.zze.zzg, (Object) null, (Object) null);
        }

        static {
            zzm zzm = new zzm();
            zze = zzm;
            zzig.zza(zzm.class, zzm);
        }
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static final class zzn extends zzig<zzn, zzb> implements zzjt {
        /* access modifiers changed from: private */
        public static final zzn zzab;
        private static volatile zzkb<zzn> zzac;
        private static final zzip<Integer, zzv> zzu = new zzq();
        private zza zzaa;
        private int zzc;
        private String zzd = "";
        private String zze = "";
        private String zzf = "";
        private String zzg = "";
        private String zzh = "";
        private zzio<String> zzi = zzig.zzad();
        private String zzj = "";
        private boolean zzk;
        private boolean zzl;
        private String zzm = "";
        private String zzn = "";
        private zzkw zzo;
        private boolean zzp;
        private String zzq = "";
        private long zzr;
        private String zzs = "";
        private zzim zzt = zzac();
        private boolean zzv;
        private zzio<String> zzw = zzig.zzad();
        private long zzx;
        private long zzy;
        private String zzz = "";

        /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
        public static final class zza extends zzig<zza, C2506zza> implements zzjt {
            /* access modifiers changed from: private */
            public static final zza zzd;
            private static volatile zzkb<zza> zze;
            private zzio<zzr> zzc = zzad();

            private zza() {
            }

            /* renamed from: com.google.android.gms.internal.firebase_auth.zzp$zzn$zza$zza  reason: collision with other inner class name */
            /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
            public static final class C2506zza extends zzig.zzb<zza, C2506zza> implements zzjt {
                private C2506zza() {
                    super(zza.zzd);
                }

                /* synthetic */ C2506zza(zzo zzo) {
                    this();
                }
            }

            /* access modifiers changed from: protected */
            public final Object zza(int i, Object obj, Object obj2) {
                switch (zzo.zza[i - 1]) {
                    case 1:
                        return new zza();
                    case 2:
                        return new C2506zza((zzo) null);
                    case 3:
                        return zza((zzjr) zzd, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"zzc", zzr.class});
                    case 4:
                        return zzd;
                    case 5:
                        zzkb<zza> zzkb = zze;
                        if (zzkb == null) {
                            synchronized (zza.class) {
                                zzkb = zze;
                                if (zzkb == null) {
                                    zzkb = new zzig.zza<>(zzd);
                                    zze = zzkb;
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
                zzd = zza;
                zzig.zza(zza.class, zza);
            }
        }

        private zzn() {
        }

        /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
        public static final class zzb extends zzig.zzb<zzn, zzb> implements zzjt {
            private zzb() {
                super(zzn.zzab);
            }

            public final zzb zza(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzn) this.zza).zza(str);
                return this;
            }

            public final zzb zzb(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzn) this.zza).zzb(str);
                return this;
            }

            public final zzb zzc(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzn) this.zza).zzc(str);
                return this;
            }

            public final zzb zzd(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzn) this.zza).zzd(str);
                return this;
            }

            public final zzb zze(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzn) this.zza).zze(str);
                return this;
            }

            public final zzb zzf(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzn) this.zza).zzf(str);
                return this;
            }

            public final zzb zza(Iterable<? extends zzv> iterable) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzn) this.zza).zza(iterable);
                return this;
            }

            public final zzb zza(boolean z) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzn) this.zza).zza(z);
                return this;
            }

            public final zzb zzb(Iterable<String> iterable) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzn) this.zza).zzb(iterable);
                return this;
            }

            public final zzb zzg(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzn) this.zza).zzg(str);
                return this;
            }

            /* synthetic */ zzb(zzo zzo) {
                this();
            }
        }

        /* access modifiers changed from: private */
        public final void zza(String str) {
            str.getClass();
            this.zzc |= 1;
            this.zzd = str;
        }

        /* access modifiers changed from: private */
        public final void zzb(String str) {
            str.getClass();
            this.zzc |= 4;
            this.zzf = str;
        }

        /* access modifiers changed from: private */
        public final void zzc(String str) {
            str.getClass();
            this.zzc |= 8;
            this.zzg = str;
        }

        /* access modifiers changed from: private */
        public final void zzd(String str) {
            str.getClass();
            this.zzc |= 16;
            this.zzh = str;
        }

        /* access modifiers changed from: private */
        public final void zze(String str) {
            str.getClass();
            this.zzc |= 32;
            this.zzj = str;
        }

        /* access modifiers changed from: private */
        public final void zzf(String str) {
            str.getClass();
            this.zzc |= 16384;
            this.zzs = str;
        }

        /* access modifiers changed from: private */
        public final void zza(Iterable<? extends zzv> iterable) {
            zzim zzim = this.zzt;
            if (!zzim.zza()) {
                int size = zzim.size();
                this.zzt = zzim.zzb(size == 0 ? 10 : size << 1);
            }
            for (zzv zza2 : iterable) {
                this.zzt.zzd(zza2.zza());
            }
        }

        /* access modifiers changed from: private */
        public final void zza(boolean z) {
            this.zzc |= 32768;
            this.zzv = z;
        }

        /* access modifiers changed from: private */
        public final void zzb(Iterable<String> iterable) {
            zzio<String> zzio = this.zzw;
            if (!zzio.zza()) {
                int size = zzio.size();
                this.zzw = zzio.zza(size == 0 ? 10 : size << 1);
            }
            zzgn.zza(iterable, this.zzw);
        }

        /* access modifiers changed from: private */
        public final void zzg(String str) {
            str.getClass();
            this.zzc |= 262144;
            this.zzz = str;
        }

        public static zzb zza() {
            return (zzb) zzab.zzz();
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            switch (zzo.zza[i - 1]) {
                case 1:
                    return new zzn();
                case 2:
                    return new zzb((zzo) null);
                case 3:
                    return zza((zzjr) zzab, "\u0001\u0017\u0000\u0001\u0002\u001c\u0017\u0000\u0003\u0000\u0002ဈ\u0000\u0003ဈ\u0001\u0004ဈ\u0002\u0005ဈ\u0003\u0006ဈ\u0004\u0007\u001a\bဈ\u0005\tဇ\u0006\nဇ\u0007\u000bဈ\b\fဈ\t\rဉ\n\u000eဇ\u000b\u000fဈ\f\u0010ဂ\r\u0011ဈ\u000e\u0012\u001e\u0013ဇ\u000f\u0014\u001a\u0015ဂ\u0010\u0016ဂ\u0011\u0019ဈ\u0012\u001cဉ\u0013", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk", "zzl", "zzm", "zzn", "zzo", "zzp", "zzq", "zzr", "zzs", "zzt", zzv.zzb(), "zzv", "zzw", "zzx", "zzy", "zzz", "zzaa"});
                case 4:
                    return zzab;
                case 5:
                    zzkb<zzn> zzkb = zzac;
                    if (zzkb == null) {
                        synchronized (zzn.class) {
                            zzkb = zzac;
                            if (zzkb == null) {
                                zzkb = new zzig.zza<>(zzab);
                                zzac = zzkb;
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
            zzn zzn2 = new zzn();
            zzab = zzn2;
            zzig.zza(zzn.class, zzn2);
        }
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static final class zzo extends zzig<zzo, zza> implements zzjt {
        /* access modifiers changed from: private */
        public static final zzo zzr;
        private static volatile zzkb<zzo> zzs;
        private int zzc;
        private String zzd = "";
        private String zze = "";
        private String zzf = "";
        private String zzg = "";
        private zzio<String> zzh = zzig.zzad();
        private String zzi = "";
        private zzio<zzu> zzj = zzad();
        private String zzk = "";
        private String zzl = "";
        private String zzm = "";
        private long zzn;
        private String zzo = "";
        private boolean zzp;
        private byte zzq = 2;

        private zzo() {
        }

        /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
        public static final class zza extends zzig.zzb<zzo, zza> implements zzjt {
            private zza() {
                super(zzo.zzr);
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        @Deprecated
        public final String zza() {
            return this.zzf;
        }

        @Deprecated
        public final String zzb() {
            return this.zzg;
        }

        public final String zzc() {
            return this.zzi;
        }

        @Deprecated
        public final List<zzu> zzd() {
            return this.zzj;
        }

        @Deprecated
        public final String zze() {
            return this.zzl;
        }

        public final String zzf() {
            return this.zzm;
        }

        public final long zzg() {
            return this.zzn;
        }

        public final String zzh() {
            return this.zzo;
        }

        public final boolean zzi() {
            return this.zzp;
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            int i2 = 1;
            switch (zzo.zza[i - 1]) {
                case 1:
                    return new zzo();
                case 2:
                    return new zza((zzo) null);
                case 3:
                    return zza((zzjr) zzr, "\u0001\r\u0000\u0001\u0001\r\r\u0000\u0002\u0001\u0001ᔈ\u0000\u0002ဈ\u0001\u0003ဈ\u0002\u0004ဈ\u0003\u0005\u001a\u0006ဈ\u0004\u0007\u001b\bဈ\u0005\tဈ\u0006\nဈ\u0007\u000bဂ\b\fဈ\t\rဇ\n", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh", "zzi", "zzj", zzu.class, "zzk", "zzl", "zzm", "zzn", "zzo", "zzp"});
                case 4:
                    return zzr;
                case 5:
                    zzkb<zzo> zzkb = zzs;
                    if (zzkb == null) {
                        synchronized (zzo.class) {
                            zzkb = zzs;
                            if (zzkb == null) {
                                zzkb = new zzig.zza<>(zzr);
                                zzs = zzkb;
                            }
                        }
                    }
                    return zzkb;
                case 6:
                    return Byte.valueOf(this.zzq);
                case 7:
                    if (obj == null) {
                        i2 = 0;
                    }
                    this.zzq = (byte) i2;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static zzkb<zzo> zzj() {
            return (zzkb) zzr.zza(zzig.zze.zzg, (Object) null, (Object) null);
        }

        static {
            zzo zzo2 = new zzo();
            zzr = zzo2;
            zzig.zza(zzo.class, zzo2);
        }
    }

    /* renamed from: com.google.android.gms.internal.firebase_auth.zzp$zzp  reason: collision with other inner class name */
    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static final class C2507zzp extends zzig<C2507zzp, zza> implements zzjt {
        /* access modifiers changed from: private */
        public static final C2507zzp zzo;
        private static volatile zzkb<C2507zzp> zzp;
        private int zzc;
        private String zzd = "";
        private String zze = "";
        private String zzf = "";
        private String zzg = "";
        private String zzh = "";
        private String zzi = "";
        private String zzj = "";
        private boolean zzk;
        private String zzl = "";
        private boolean zzm;
        private String zzn = "";

        private C2507zzp() {
        }

        /* renamed from: com.google.android.gms.internal.firebase_auth.zzp$zzp$zza */
        /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
        public static final class zza extends zzig.zzb<C2507zzp, zza> implements zzjt {
            private zza() {
                super(C2507zzp.zzo);
            }

            public final zza zza(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((C2507zzp) this.zza).zza(str);
                return this;
            }

            public final zza zzb(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((C2507zzp) this.zza).zzb(str);
                return this;
            }

            public final zza zzc(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((C2507zzp) this.zza).zzc(str);
                return this;
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        /* access modifiers changed from: private */
        public final void zza(String str) {
            str.getClass();
            this.zzc |= 1;
            this.zzd = str;
        }

        /* access modifiers changed from: private */
        public final void zzb(String str) {
            str.getClass();
            this.zzc |= 2;
            this.zze = str;
        }

        /* access modifiers changed from: private */
        public final void zzc(String str) {
            str.getClass();
            this.zzc |= 1024;
            this.zzn = str;
        }

        public static zza zza() {
            return (zza) zzo.zzz();
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            switch (zzo.zza[i - 1]) {
                case 1:
                    return new C2507zzp();
                case 2:
                    return new zza((zzo) null);
                case 3:
                    return zza((zzjr) zzo, "\u0001\u000b\u0000\u0001\u0001\r\u000b\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဈ\u0001\u0003ဈ\u0002\u0004ဈ\u0003\u0005ဈ\u0004\u0006ဈ\u0005\u0007ဈ\u0006\bဇ\u0007\tဈ\b\nဇ\t\rဈ\n", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk", "zzl", "zzm", "zzn"});
                case 4:
                    return zzo;
                case 5:
                    zzkb<C2507zzp> zzkb = zzp;
                    if (zzkb == null) {
                        synchronized (C2507zzp.class) {
                            zzkb = zzp;
                            if (zzkb == null) {
                                zzkb = new zzig.zza<>(zzo);
                                zzp = zzkb;
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
            C2507zzp zzp2 = new C2507zzp();
            zzo = zzp2;
            zzig.zza(C2507zzp.class, zzp2);
        }
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static final class zzq extends zzig<zzq, zza> implements zzjt {
        /* access modifiers changed from: private */
        public static final zzq zzl;
        private static volatile zzkb<zzq> zzm;
        private int zzc;
        private String zzd = "";
        private String zze = "";
        private String zzf = "";
        private String zzg = "";
        private String zzh = "";
        private long zzi;
        private String zzj = "";
        private byte zzk = 2;

        private zzq() {
        }

        /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
        public static final class zza extends zzig.zzb<zzq, zza> implements zzjt {
            private zza() {
                super(zzq.zzl);
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        public final String zza() {
            return this.zze;
        }

        public final String zzb() {
            return this.zzf;
        }

        public final String zzc() {
            return this.zzg;
        }

        public final String zzd() {
            return this.zzh;
        }

        public final long zze() {
            return this.zzi;
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            int i2 = 1;
            switch (zzo.zza[i - 1]) {
                case 1:
                    return new zzq();
                case 2:
                    return new zza((zzo) null);
                case 3:
                    return zza((zzjr) zzl, "\u0001\u0007\u0000\u0001\u0001\b\u0007\u0000\u0000\u0001\u0001ᔈ\u0000\u0002ဈ\u0001\u0004ဈ\u0002\u0005ဈ\u0003\u0006ဈ\u0004\u0007ဂ\u0005\bဈ\u0006", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh", "zzi", "zzj"});
                case 4:
                    return zzl;
                case 5:
                    zzkb<zzq> zzkb = zzm;
                    if (zzkb == null) {
                        synchronized (zzq.class) {
                            zzkb = zzm;
                            if (zzkb == null) {
                                zzkb = new zzig.zza<>(zzl);
                                zzm = zzkb;
                            }
                        }
                    }
                    return zzkb;
                case 6:
                    return Byte.valueOf(this.zzk);
                case 7:
                    if (obj == null) {
                        i2 = 0;
                    }
                    this.zzk = (byte) i2;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static zzkb<zzq> zzf() {
            return (zzkb) zzl.zza(zzig.zze.zzg, (Object) null, (Object) null);
        }

        static {
            zzq zzq = new zzq();
            zzl = zzq;
            zzig.zza(zzq.class, zzq);
        }
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static final class zzr extends zzig<zzr, zza> implements zzjt {
        /* access modifiers changed from: private */
        public static final zzr zzq;
        private static volatile zzkb<zzr> zzr;
        private int zzc;
        private String zzd = "";
        private String zze = "";
        private String zzf = "";
        private boolean zzg;
        private String zzh = "";
        private String zzi = "";
        private long zzj;
        private String zzk = "";
        private boolean zzl;
        private boolean zzm;
        private boolean zzn = true;
        private String zzo = "";
        private String zzp = "";

        private zzr() {
        }

        /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
        public static final class zza extends zzig.zzb<zzr, zza> implements zzjt {
            private zza() {
                super(zzr.zzq);
            }

            public final zza zza(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzr) this.zza).zza(str);
                return this;
            }

            public final zza zzb(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzr) this.zza).zzb(str);
                return this;
            }

            public final zza zzc(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzr) this.zza).zzc(str);
                return this;
            }

            public final zza zzd(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzr) this.zza).zzd(str);
                return this;
            }

            public final zza zza(boolean z) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzr) this.zza).zza(z);
                return this;
            }

            public final zza zzb(boolean z) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzr) this.zza).zzb(z);
                return this;
            }

            public final zza zzc(boolean z) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzr) this.zza).zzc(z);
                return this;
            }

            public final zza zze(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzr) this.zza).zze(str);
                return this;
            }

            public final zza zzf(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzr) this.zza).zzf(str);
                return this;
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        /* access modifiers changed from: private */
        public final void zza(String str) {
            str.getClass();
            this.zzc |= 1;
            this.zzd = str;
        }

        /* access modifiers changed from: private */
        public final void zzb(String str) {
            str.getClass();
            this.zzc |= 2;
            this.zze = str;
        }

        /* access modifiers changed from: private */
        public final void zzc(String str) {
            str.getClass();
            this.zzc |= 16;
            this.zzh = str;
        }

        /* access modifiers changed from: private */
        public final void zzd(String str) {
            str.getClass();
            this.zzc |= 128;
            this.zzk = str;
        }

        /* access modifiers changed from: private */
        public final void zza(boolean z) {
            this.zzc |= 256;
            this.zzl = z;
        }

        /* access modifiers changed from: private */
        public final void zzb(boolean z) {
            this.zzc |= 512;
            this.zzm = z;
        }

        /* access modifiers changed from: private */
        public final void zzc(boolean z) {
            this.zzc |= 1024;
            this.zzn = z;
        }

        /* access modifiers changed from: private */
        public final void zze(String str) {
            str.getClass();
            this.zzc |= 2048;
            this.zzo = str;
        }

        /* access modifiers changed from: private */
        public final void zzf(String str) {
            str.getClass();
            this.zzc |= 4096;
            this.zzp = str;
        }

        public static zza zza() {
            return (zza) zzq.zzz();
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            switch (zzo.zza[i - 1]) {
                case 1:
                    return new zzr();
                case 2:
                    return new zza((zzo) null);
                case 3:
                    return zza((zzjr) zzq, "\u0001\r\u0000\u0001\u0001\u000f\r\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဈ\u0001\u0003ဈ\u0002\u0004ဇ\u0003\u0005ဈ\u0004\u0006ဈ\u0005\u0007ဂ\u0006\bဈ\u0007\tဇ\b\nဇ\t\u000bဇ\n\rဈ\u000b\u000fဈ\f", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk", "zzl", "zzm", "zzn", "zzo", "zzp"});
                case 4:
                    return zzq;
                case 5:
                    zzkb<zzr> zzkb = zzr;
                    if (zzkb == null) {
                        synchronized (zzr.class) {
                            zzkb = zzr;
                            if (zzkb == null) {
                                zzkb = new zzig.zza<>(zzq);
                                zzr = zzkb;
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
            zzr zzr2 = new zzr();
            zzq = zzr2;
            zzig.zza(zzr.class, zzr2);
        }
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static final class zzs extends zzig<zzs, zza> implements zzjt {
        /* access modifiers changed from: private */
        public static final zzs zzau;
        private static volatile zzkb<zzs> zzav;
        private zzio<String> zzaa = zzig.zzad();
        private boolean zzab;
        private String zzac = "";
        private String zzad = "";
        private String zzae = "";
        private long zzaf;
        private String zzag = "";
        private boolean zzah;
        private String zzai = "";
        private String zzaj = "";
        private long zzak;
        private String zzal = "";
        private String zzam = "";
        private String zzan = "";
        private String zzao = "";
        private boolean zzap;
        private String zzaq = "";
        private String zzar = "";
        private String zzas = "";
        private zzio<zzr> zzat = zzad();
        private int zzc;
        private int zzd;
        private String zze = "";
        private String zzf = "";
        private String zzg = "";
        private boolean zzh;
        private String zzi = "";
        private String zzj = "";
        private String zzk = "";
        private String zzl = "";
        private String zzm = "";
        private String zzn = "";
        private String zzo = "";
        private String zzp = "";
        private String zzq = "";
        private String zzr = "";
        private String zzs = "";
        private String zzt = "";
        private String zzu = "";
        private boolean zzv;
        private String zzw = "";
        private String zzx = "";
        private String zzy = "";
        private String zzz = "";

        private zzs() {
        }

        /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
        public static final class zza extends zzig.zzb<zzs, zza> implements zzjt {
            private zza() {
                super(zzs.zzau);
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        public final String zza() {
            return this.zzf;
        }

        public final String zzb() {
            return this.zzg;
        }

        public final String zzc() {
            return this.zzo;
        }

        public final String zzd() {
            return this.zzu;
        }

        public final String zze() {
            return this.zzw;
        }

        public final String zzf() {
            return this.zzx;
        }

        public final boolean zzg() {
            return this.zzab;
        }

        public final String zzh() {
            return this.zzad;
        }

        public final boolean zzi() {
            return this.zzah;
        }

        public final String zzj() {
            return this.zzai;
        }

        public final String zzk() {
            return this.zzaj;
        }

        public final long zzl() {
            return this.zzak;
        }

        public final String zzm() {
            return this.zzal;
        }

        public final String zzn() {
            return this.zzan;
        }

        public final String zzo() {
            return this.zzao;
        }

        public final boolean zzp() {
            return this.zzap;
        }

        public final String zzq() {
            return this.zzaq;
        }

        public final String zzr() {
            return this.zzar;
        }

        public final String zzs() {
            return this.zzas;
        }

        public final List<zzr> zzt() {
            return this.zzat;
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            switch (zzo.zza[i - 1]) {
                case 1:
                    return new zzs();
                case 2:
                    return new zza((zzo) null);
                case 3:
                    return zza((zzjr) zzau, "\u0001*\u0000\u0002\u0001-*\u0000\u0002\u0000\u0001ဈ\u0000\u0002ဈ\u0001\u0003ဈ\u0002\u0004ဇ\u0003\u0005ဈ\u0004\u0006ဈ\u0005\u0007ဈ\u0006\bဈ\u0007\tဈ\b\nဈ\t\u000bဈ\n\fဈ\u000b\rဈ\f\u000eဈ\r\u000fဈ\u000e\u0010ဈ\u000f\u0011ဈ\u0010\u0012ဇ\u0011\u0013ဈ\u0012\u0014ဈ\u0013\u0015ဈ\u0014\u0017ဈ\u0015\u0018\u001a\u0019ဇ\u0016\u001aဈ\u0017\u001cဈ\u0018\u001dဈ\u0019\u001eဂ\u001a\u001fဈ\u001b ဇ\u001c!ဈ\u001d\"ဈ\u001e#ဂ\u001f$ဈ %ဈ!&ဈ\"'ဈ#(ဇ$*ဈ%+ဈ&,ဈ'-\u001b", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk", "zzl", "zzm", "zzn", "zzo", "zzp", "zzq", "zzr", "zzs", "zzt", "zzu", "zzv", "zzw", "zzx", "zzy", "zzz", "zzaa", "zzab", "zzac", "zzad", "zzae", "zzaf", "zzag", "zzah", "zzai", "zzaj", "zzak", "zzal", "zzam", "zzan", "zzao", "zzap", "zzaq", "zzar", "zzas", "zzat", zzr.class});
                case 4:
                    return zzau;
                case 5:
                    zzkb<zzs> zzkb = zzav;
                    if (zzkb == null) {
                        synchronized (zzs.class) {
                            zzkb = zzav;
                            if (zzkb == null) {
                                zzkb = new zzig.zza<>(zzau);
                                zzav = zzkb;
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

        public static zzkb<zzs> zzu() {
            return (zzkb) zzau.zza(zzig.zze.zzg, (Object) null, (Object) null);
        }

        static {
            zzs zzs2 = new zzs();
            zzau = zzs2;
            zzig.zza(zzs.class, zzs2);
        }
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static final class zzt extends zzig<zzt, zza> implements zzjt {
        /* access modifiers changed from: private */
        public static final zzt zzi;
        private static volatile zzkb<zzt> zzj;
        private int zzc;
        private String zzd = "";
        private String zze = "";
        private boolean zzf;
        private long zzg;
        private String zzh = "";

        private zzt() {
        }

        /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
        public static final class zza extends zzig.zzb<zzt, zza> implements zzjt {
            private zza() {
                super(zzt.zzi);
            }

            public final zza zza(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzt) this.zza).zza(str);
                return this;
            }

            public final zza zza(boolean z) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzt) this.zza).zza(true);
                return this;
            }

            public final zza zzb(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzt) this.zza).zzb(str);
                return this;
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        /* access modifiers changed from: private */
        public final void zza(String str) {
            str.getClass();
            this.zzc |= 1;
            this.zzd = str;
        }

        /* access modifiers changed from: private */
        public final void zza(boolean z) {
            this.zzc |= 4;
            this.zzf = z;
        }

        /* access modifiers changed from: private */
        public final void zzb(String str) {
            str.getClass();
            this.zzc |= 16;
            this.zzh = str;
        }

        public static zza zza() {
            return (zza) zzi.zzz();
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            switch (zzo.zza[i - 1]) {
                case 1:
                    return new zzt();
                case 2:
                    return new zza((zzo) null);
                case 3:
                    return zza((zzjr) zzi, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဈ\u0001\u0003ဇ\u0002\u0004ဂ\u0003\u0005ဈ\u0004", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh"});
                case 4:
                    return zzi;
                case 5:
                    zzkb<zzt> zzkb = zzj;
                    if (zzkb == null) {
                        synchronized (zzt.class) {
                            zzkb = zzj;
                            if (zzkb == null) {
                                zzkb = new zzig.zza<>(zzi);
                                zzj = zzkb;
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
            zzt zzt = new zzt();
            zzi = zzt;
            zzig.zza(zzt.class, zzt);
        }
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static final class zzu extends zzig<zzu, zza> implements zzjt {
        /* access modifiers changed from: private */
        public static final zzu zzj;
        private static volatile zzkb<zzu> zzk;
        private int zzc;
        private String zzd = "";
        private String zze = "";
        private String zzf = "";
        private long zzg;
        private boolean zzh;
        private byte zzi = 2;

        private zzu() {
        }

        /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
        public static final class zza extends zzig.zzb<zzu, zza> implements zzjt {
            private zza() {
                super(zzu.zzj);
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        public final String zza() {
            return this.zze;
        }

        public final String zzb() {
            return this.zzf;
        }

        public final long zzc() {
            return this.zzg;
        }

        public final boolean zzd() {
            return this.zzh;
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            int i2 = 1;
            switch (zzo.zza[i - 1]) {
                case 1:
                    return new zzu();
                case 2:
                    return new zza((zzo) null);
                case 3:
                    return zza((zzjr) zzj, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0001\u0001ᔈ\u0000\u0002ဈ\u0001\u0003ဈ\u0002\u0004ဂ\u0003\u0005ဇ\u0004", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh"});
                case 4:
                    return zzj;
                case 5:
                    zzkb<zzu> zzkb = zzk;
                    if (zzkb == null) {
                        synchronized (zzu.class) {
                            zzkb = zzk;
                            if (zzkb == null) {
                                zzkb = new zzig.zza<>(zzj);
                                zzk = zzkb;
                            }
                        }
                    }
                    return zzkb;
                case 6:
                    return Byte.valueOf(this.zzi);
                case 7:
                    if (obj == null) {
                        i2 = 0;
                    }
                    this.zzi = (byte) i2;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static zzkb<zzu> zze() {
            return (zzkb) zzj.zza(zzig.zze.zzg, (Object) null, (Object) null);
        }

        static {
            zzu zzu = new zzu();
            zzj = zzu;
            zzig.zza(zzu.class, zzu);
        }
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static final class zzv extends zzig<zzv, zza> implements zzjt {
        /* access modifiers changed from: private */
        public static final zzv zzo;
        private static volatile zzkb<zzv> zzp;
        private int zzc;
        private String zzd = "";
        private String zze = "";
        private String zzf = "";
        private String zzg = "";
        private String zzh = "";
        private String zzi = "";
        private String zzj = "";
        private long zzk;
        private String zzl = "";
        private boolean zzm;
        private String zzn = "";

        private zzv() {
        }

        /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
        public static final class zza extends zzig.zzb<zzv, zza> implements zzjt {
            private zza() {
                super(zzv.zzo);
            }

            public final zza zza(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzv) this.zza).zza(str);
                return this;
            }

            public final zza zzb(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzv) this.zza).zzb(str);
                return this;
            }

            public final zza zza(boolean z) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzv) this.zza).zza(z);
                return this;
            }

            public final zza zzc(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzv) this.zza).zzc(str);
                return this;
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        /* access modifiers changed from: private */
        public final void zza(String str) {
            str.getClass();
            this.zzc |= 1;
            this.zzd = str;
        }

        /* access modifiers changed from: private */
        public final void zzb(String str) {
            str.getClass();
            this.zzc |= 2;
            this.zze = str;
        }

        /* access modifiers changed from: private */
        public final void zza(boolean z) {
            this.zzc |= 512;
            this.zzm = z;
        }

        /* access modifiers changed from: private */
        public final void zzc(String str) {
            str.getClass();
            this.zzc |= 1024;
            this.zzn = str;
        }

        public static zza zza() {
            return (zza) zzo.zzz();
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            switch (zzo.zza[i - 1]) {
                case 1:
                    return new zzv();
                case 2:
                    return new zza((zzo) null);
                case 3:
                    return zza((zzjr) zzo, "\u0001\u000b\u0000\u0001\u0001\u000b\u000b\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဈ\u0001\u0003ဈ\u0002\u0004ဈ\u0003\u0005ဈ\u0004\u0006ဈ\u0005\u0007ဈ\u0006\bဂ\u0007\tဈ\b\nဇ\t\u000bဈ\n", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk", "zzl", "zzm", "zzn"});
                case 4:
                    return zzo;
                case 5:
                    zzkb<zzv> zzkb = zzp;
                    if (zzkb == null) {
                        synchronized (zzv.class) {
                            zzkb = zzp;
                            if (zzkb == null) {
                                zzkb = new zzig.zza<>(zzo);
                                zzp = zzkb;
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
            zzv zzv = new zzv();
            zzo = zzv;
            zzig.zza(zzv.class, zzv);
        }
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static final class zzw extends zzig<zzw, zza> implements zzjt {
        /* access modifiers changed from: private */
        public static final zzw zzs;
        private static volatile zzkb<zzw> zzt;
        private int zzc;
        private String zzd = "";
        private String zze = "";
        private String zzf = "";
        private String zzg = "";
        private String zzh = "";
        private boolean zzi;
        private String zzj = "";
        private String zzk = "";
        private long zzl;
        private String zzm = "";
        private String zzn = "";
        private long zzo;
        private String zzp = "";
        private zzio<zzr> zzq = zzad();
        private byte zzr = 2;

        private zzw() {
        }

        /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
        public static final class zza extends zzig.zzb<zzw, zza> implements zzjt {
            private zza() {
                super(zzw.zzs);
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        public final String zza() {
            return this.zze;
        }

        public final String zzb() {
            return this.zzf;
        }

        public final String zzc() {
            return this.zzg;
        }

        public final String zzd() {
            return this.zzh;
        }

        public final String zze() {
            return this.zzj;
        }

        public final String zzf() {
            return this.zzn;
        }

        public final long zzg() {
            return this.zzo;
        }

        public final String zzh() {
            return this.zzp;
        }

        public final List<zzr> zzi() {
            return this.zzq;
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            int i2 = 1;
            switch (zzo.zza[i - 1]) {
                case 1:
                    return new zzw();
                case 2:
                    return new zza((zzo) null);
                case 3:
                    return zza((zzjr) zzs, "\u0001\u000e\u0000\u0001\u0001\u000f\u000e\u0000\u0001\u0001\u0001ᔈ\u0000\u0002ဈ\u0001\u0003ဈ\u0002\u0004ဈ\u0003\u0005ဈ\u0004\u0006ဇ\u0005\u0007ဈ\u0006\bဈ\u0007\tဂ\b\nဈ\t\u000bဈ\n\fဂ\u000b\u000eဈ\f\u000f\u001b", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk", "zzl", "zzm", "zzn", "zzo", "zzp", "zzq", zzr.class});
                case 4:
                    return zzs;
                case 5:
                    zzkb<zzw> zzkb = zzt;
                    if (zzkb == null) {
                        synchronized (zzw.class) {
                            zzkb = zzt;
                            if (zzkb == null) {
                                zzkb = new zzig.zza<>(zzs);
                                zzt = zzkb;
                            }
                        }
                    }
                    return zzkb;
                case 6:
                    return Byte.valueOf(this.zzr);
                case 7:
                    if (obj == null) {
                        i2 = 0;
                    }
                    this.zzr = (byte) i2;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static zzkb<zzw> zzj() {
            return (zzkb) zzs.zza(zzig.zze.zzg, (Object) null, (Object) null);
        }

        static {
            zzw zzw = new zzw();
            zzs = zzw;
            zzig.zza(zzw.class, zzw);
        }
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static final class zzx extends zzig<zzx, zza> implements zzjt {
        /* access modifiers changed from: private */
        public static final zzx zzl;
        private static volatile zzkb<zzx> zzm;
        private int zzc;
        private String zzd = "";
        private String zze = "";
        private String zzf = "";
        private String zzg = "";
        private String zzh = "";
        private String zzi = "";
        private int zzj;
        private String zzk = "";

        private zzx() {
        }

        /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
        public static final class zza extends zzig.zzb<zzx, zza> implements zzjt {
            private zza() {
                super(zzx.zzl);
            }

            public final zza zza(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzx) this.zza).zza(str);
                return this;
            }

            public final zza zzb(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzx) this.zza).zzb(str);
                return this;
            }

            public final zza zzc(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzx) this.zza).zzc(str);
                return this;
            }

            public final zza zzd(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzx) this.zza).zzd(str);
                return this;
            }

            public final zza zze(String str) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzx) this.zza).zze(str);
                return this;
            }

            public final zza zza(zzaa zzaa) {
                if (this.zzb) {
                    zzb();
                    this.zzb = false;
                }
                ((zzx) this.zza).zza(zzaa);
                return this;
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        /* access modifiers changed from: private */
        public final void zza(String str) {
            str.getClass();
            this.zzc |= 1;
            this.zzd = str;
        }

        /* access modifiers changed from: private */
        public final void zzb(String str) {
            str.getClass();
            this.zzc |= 2;
            this.zze = str;
        }

        /* access modifiers changed from: private */
        public final void zzc(String str) {
            str.getClass();
            this.zzc |= 4;
            this.zzf = str;
        }

        /* access modifiers changed from: private */
        public final void zzd(String str) {
            str.getClass();
            this.zzc |= 8;
            this.zzg = str;
        }

        /* access modifiers changed from: private */
        public final void zze(String str) {
            str.getClass();
            this.zzc |= 32;
            this.zzi = str;
        }

        /* access modifiers changed from: private */
        public final void zza(zzaa zzaa) {
            this.zzj = zzaa.zza();
            this.zzc |= 64;
        }

        public static zza zza() {
            return (zza) zzl.zzz();
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            switch (zzo.zza[i - 1]) {
                case 1:
                    return new zzx();
                case 2:
                    return new zza((zzo) null);
                case 3:
                    return zza((zzjr) zzl, "\u0001\b\u0000\u0001\u0001\b\b\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဈ\u0001\u0003ဈ\u0002\u0004ဈ\u0003\u0005ဈ\u0004\u0006ဈ\u0005\u0007ဌ\u0006\bဈ\u0007", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh", "zzi", "zzj", zzaa.zzb(), "zzk"});
                case 4:
                    return zzl;
                case 5:
                    zzkb<zzx> zzkb = zzm;
                    if (zzkb == null) {
                        synchronized (zzx.class) {
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
            zzx zzx = new zzx();
            zzl = zzx;
            zzig.zza(zzx.class, zzx);
        }
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static final class zzy extends zzig<zzy, zza> implements zzjt {
        /* access modifiers changed from: private */
        public static final zzy zzn;
        private static volatile zzkb<zzy> zzo;
        private int zzc;
        private String zzd = "";
        private String zze = "";
        private long zzf;
        private String zzg = "";
        private boolean zzh;
        private String zzi = "";
        private String zzj = "";
        private long zzk;
        private String zzl = "";
        private long zzm;

        /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
        public static final class zza extends zzig.zzb<zzy, zza> implements zzjt {
            private zza() {
                super(zzy.zzn);
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        static {
            zzy zzy = new zzy();
            zzn = zzy;
            zzig.zza(zzy.class, zzy);
        }

        private zzy() {
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            switch (zzo.zza[i - 1]) {
                case 1:
                    return new zzy();
                case 2:
                    return new zza((zzo) null);
                case 3:
                    return zza((zzjr) zzn, "\u0001\n\u0000\u0001\u0001\n\n\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဈ\u0001\u0003ဂ\u0002\u0004ဈ\u0003\u0005ဇ\u0004\u0006ဈ\u0005\u0007ဈ\u0006\bဂ\u0007\tဈ\b\nဂ\t", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk", "zzl", "zzm"});
                case 4:
                    return zzn;
                case 5:
                    zzkb<zzy> zzkb = zzo;
                    if (zzkb == null) {
                        synchronized (zzy.class) {
                            zzkb = zzo;
                            if (zzkb == null) {
                                zzkb = new zzig.zza<>(zzn);
                                zzo = zzkb;
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

        public final String zzd() {
            return this.zzg;
        }

        public final boolean zze() {
            return this.zzh;
        }

        public final String zzf() {
            return this.zzi;
        }

        public final String zzg() {
            return this.zzj;
        }

        public final long zzh() {
            return this.zzk;
        }

        public final String zzi() {
            return this.zzl;
        }

        public final String zza() {
            return this.zzd;
        }

        public final String zzb() {
            return this.zze;
        }

        public final long zzc() {
            return this.zzf;
        }

        public static zzkb<zzy> zzj() {
            return (zzkb) zzn.zza(zzig.zze.zzg, (Object) null, (Object) null);
        }
    }
}
