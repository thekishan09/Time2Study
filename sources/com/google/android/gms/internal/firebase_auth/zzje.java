package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzig;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzje implements zzkg {
    private static final zzjo zzb = new zzjh();
    private final zzjo zza;

    public zzje() {
        this(new zzjg(zzih.zza(), zza()));
    }

    private zzje(zzjo zzjo) {
        this.zza = (zzjo) zzii.zza(zzjo, "messageInfoFactory");
    }

    public final <T> zzkh<T> zza(Class<T> cls) {
        zzkj.zza((Class<?>) cls);
        zzjp zzb2 = this.zza.zzb(cls);
        if (zzb2.zzb()) {
            if (zzig.class.isAssignableFrom(cls)) {
                return zzju.zza(zzkj.zzc(), zzhw.zza(), zzb2.zzc());
            }
            return zzju.zza(zzkj.zza(), zzhw.zzb(), zzb2.zzc());
        } else if (zzig.class.isAssignableFrom(cls)) {
            if (zza(zzb2)) {
                return zzjv.zza(cls, zzb2, zzjy.zzb(), zzjb.zzb(), zzkj.zzc(), zzhw.zza(), zzjm.zzb());
            }
            return zzjv.zza(cls, zzb2, zzjy.zzb(), zzjb.zzb(), zzkj.zzc(), (zzhv<?>) null, zzjm.zzb());
        } else if (zza(zzb2)) {
            return zzjv.zza(cls, zzb2, zzjy.zza(), zzjb.zza(), zzkj.zza(), zzhw.zzb(), zzjm.zza());
        } else {
            return zzjv.zza(cls, zzb2, zzjy.zza(), zzjb.zza(), zzkj.zzb(), (zzhv<?>) null, zzjm.zza());
        }
    }

    private static boolean zza(zzjp zzjp) {
        return zzjp.zza() == zzig.zze.zzh;
    }

    private static zzjo zza() {
        try {
            return (zzjo) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke((Object) null, new Object[0]);
        } catch (Exception e) {
            return zzb;
        }
    }
}
