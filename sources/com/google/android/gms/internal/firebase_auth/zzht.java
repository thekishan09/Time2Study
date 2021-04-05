package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzig;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public class zzht {
    private static volatile boolean zza = false;
    private static boolean zzb = true;
    private static volatile zzht zzc;
    private static volatile zzht zzd;
    private static final zzht zze = new zzht(true);
    private final Map<zza, zzig.zzf<?, ?>> zzf;

    public static zzht zza() {
        zzht zzht = zzc;
        if (zzht == null) {
            synchronized (zzht.class) {
                zzht = zzc;
                if (zzht == null) {
                    zzht = zze;
                    zzc = zzht;
                }
            }
        }
        return zzht;
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    static final class zza {
        private final Object zza;
        private final int zzb;

        zza(Object obj, int i) {
            this.zza = obj;
            this.zzb = i;
        }

        public final int hashCode() {
            return (System.identityHashCode(this.zza) * 65535) + this.zzb;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof zza)) {
                return false;
            }
            zza zza2 = (zza) obj;
            if (this.zza == zza2.zza && this.zzb == zza2.zzb) {
                return true;
            }
            return false;
        }
    }

    public static zzht zzb() {
        Class<zzht> cls = zzht.class;
        zzht zzht = zzd;
        if (zzht != null) {
            return zzht;
        }
        synchronized (cls) {
            zzht zzht2 = zzd;
            if (zzht2 != null) {
                return zzht2;
            }
            zzht zza2 = zzie.zza(cls);
            zzd = zza2;
            return zza2;
        }
    }

    public final <ContainingType extends zzjr> zzig.zzf<ContainingType, ?> zza(ContainingType containingtype, int i) {
        return this.zzf.get(new zza(containingtype, i));
    }

    zzht() {
        this.zzf = new HashMap();
    }

    private zzht(boolean z) {
        this.zzf = Collections.emptyMap();
    }
}
