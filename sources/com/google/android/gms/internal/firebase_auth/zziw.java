package com.google.android.gms.internal.firebase_auth;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public class zziw {
    private static final zzht zza = zzht.zza();
    private zzgv zzb;
    private volatile zzjr zzc;
    private volatile zzgv zzd;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zziw)) {
            return false;
        }
        zziw zziw = (zziw) obj;
        zzjr zzjr = this.zzc;
        zzjr zzjr2 = zziw.zzc;
        if (zzjr == null && zzjr2 == null) {
            return zzc().equals(zziw.zzc());
        }
        if (zzjr != null && zzjr2 != null) {
            return zzjr.equals(zzjr2);
        }
        if (zzjr != null) {
            return zzjr.equals(zziw.zzb(zzjr.zzag()));
        }
        return zzb(zzjr2.zzag()).equals(zzjr2);
    }

    public int hashCode() {
        return 1;
    }

    private final zzjr zzb(zzjr zzjr) {
        if (this.zzc == null) {
            synchronized (this) {
                if (this.zzc == null) {
                    try {
                        this.zzc = zzjr;
                        this.zzd = zzgv.zza;
                    } catch (zzir e) {
                        this.zzc = zzjr;
                        this.zzd = zzgv.zza;
                    }
                }
            }
        }
        return this.zzc;
    }

    public final zzjr zza(zzjr zzjr) {
        zzjr zzjr2 = this.zzc;
        this.zzb = null;
        this.zzd = null;
        this.zzc = zzjr;
        return zzjr2;
    }

    public final int zzb() {
        if (this.zzd != null) {
            return this.zzd.zza();
        }
        if (this.zzc != null) {
            return this.zzc.zzab();
        }
        return 0;
    }

    public final zzgv zzc() {
        if (this.zzd != null) {
            return this.zzd;
        }
        synchronized (this) {
            if (this.zzd != null) {
                zzgv zzgv = this.zzd;
                return zzgv;
            }
            if (this.zzc == null) {
                this.zzd = zzgv.zza;
            } else {
                this.zzd = this.zzc.zzw();
            }
            zzgv zzgv2 = this.zzd;
            return zzgv2;
        }
    }
}
