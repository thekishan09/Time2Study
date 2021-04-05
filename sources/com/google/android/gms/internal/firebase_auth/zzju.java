package com.google.android.gms.internal.firebase_auth;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzju<T> implements zzkh<T> {
    private final zzjr zza;
    private final zzkz<?, ?> zzb;
    private final boolean zzc;
    private final zzhv<?> zzd;

    private zzju(zzkz<?, ?> zzkz, zzhv<?> zzhv, zzjr zzjr) {
        this.zzb = zzkz;
        this.zzc = zzhv.zza(zzjr);
        this.zzd = zzhv;
        this.zza = zzjr;
    }

    static <T> zzju<T> zza(zzkz<?, ?> zzkz, zzhv<?> zzhv, zzjr zzjr) {
        return new zzju<>(zzkz, zzhv, zzjr);
    }

    public final T zza() {
        return this.zza.zzaf().zze();
    }

    public final boolean zza(T t, T t2) {
        if (!this.zzb.zzb(t).equals(this.zzb.zzb(t2))) {
            return false;
        }
        if (this.zzc) {
            return this.zzd.zza((Object) t).equals(this.zzd.zza((Object) t2));
        }
        return true;
    }

    public final int zza(T t) {
        int hashCode = this.zzb.zzb(t).hashCode();
        if (this.zzc) {
            return (hashCode * 53) + this.zzd.zza((Object) t).hashCode();
        }
        return hashCode;
    }

    public final void zzb(T t, T t2) {
        zzkj.zza(this.zzb, t, t2);
        if (this.zzc) {
            zzkj.zza(this.zzd, t, t2);
        }
    }

    public final void zza(T t, zzlw zzlw) throws IOException {
        Iterator<Map.Entry<?, Object>> zzd2 = this.zzd.zza((Object) t).zzd();
        while (zzd2.hasNext()) {
            Map.Entry next = zzd2.next();
            zzib zzib = (zzib) next.getKey();
            if (zzib.zzc() != zzlt.MESSAGE || zzib.zzd() || zzib.zze()) {
                throw new IllegalStateException("Found invalid MessageSet item.");
            } else if (next instanceof zziu) {
                zzlw.zza(zzib.zza(), (Object) ((zziu) next).zza().zzc());
            } else {
                zzlw.zza(zzib.zza(), next.getValue());
            }
        }
        zzkz<?, ?> zzkz = this.zzb;
        zzkz.zzb(zzkz.zzb(t), zzlw);
    }

    public final void zza(T t, zzke zzke, zzht zzht) throws IOException {
        boolean z;
        zzkz<?, ?> zzkz = this.zzb;
        zzhv<?> zzhv = this.zzd;
        Object zzc2 = zzkz.zzc(t);
        zzhz<?> zzb2 = zzhv.zzb(t);
        do {
            try {
                if (zzke.zza() == Integer.MAX_VALUE) {
                    zzkz.zzb((Object) t, zzc2);
                    return;
                }
                int zzb3 = zzke.zzb();
                if (zzb3 == 11) {
                    int i = 0;
                    Object obj = null;
                    zzgv zzgv = null;
                    while (zzke.zza() != Integer.MAX_VALUE) {
                        int zzb4 = zzke.zzb();
                        if (zzb4 == 16) {
                            i = zzke.zzo();
                            obj = zzhv.zza(zzht, this.zza, i);
                        } else if (zzb4 == 26) {
                            if (obj != null) {
                                zzhv.zza(zzke, obj, zzht, zzb2);
                            } else {
                                zzgv = zzke.zzn();
                            }
                        } else if (!zzke.zzc()) {
                            break;
                        }
                    }
                    if (zzke.zzb() != 12) {
                        throw zzir.zze();
                    } else if (zzgv != null) {
                        if (obj != null) {
                            zzhv.zza(zzgv, obj, zzht, zzb2);
                        } else {
                            zzkz.zza(zzc2, i, zzgv);
                        }
                    }
                } else if ((zzb3 & 7) == 2) {
                    Object zza2 = zzhv.zza(zzht, this.zza, zzb3 >>> 3);
                    if (zza2 != null) {
                        zzhv.zza(zzke, zza2, zzht, zzb2);
                    } else {
                        z = zzkz.zza(zzc2, zzke);
                        continue;
                    }
                } else {
                    z = zzke.zzc();
                    continue;
                }
                z = true;
                continue;
            } finally {
                zzkz.zzb((Object) t, zzc2);
            }
        } while (z);
    }

    public final void zzb(T t) {
        this.zzb.zzd(t);
        this.zzd.zzc(t);
    }

    public final boolean zzc(T t) {
        return this.zzd.zza((Object) t).zzf();
    }

    public final int zzd(T t) {
        zzkz<?, ?> zzkz = this.zzb;
        int zze = zzkz.zze(zzkz.zzb(t)) + 0;
        if (this.zzc) {
            return zze + this.zzd.zza((Object) t).zzg();
        }
        return zze;
    }
}
