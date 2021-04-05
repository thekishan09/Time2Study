package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzig;
import com.google.android.gms.internal.firebase_auth.zzig.zzb;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public abstract class zzig<MessageType extends zzig<MessageType, BuilderType>, BuilderType extends zzb<MessageType, BuilderType>> extends zzgn<MessageType, BuilderType> {
    private static Map<Object, zzig<?, ?>> zzd = new ConcurrentHashMap();
    protected zzlc zzb = zzlc.zza();
    private int zzc = -1;

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    static final class zzc implements zzib<zzc> {
        public final int zza() {
            throw new NoSuchMethodError();
        }

        public final zzlq zzb() {
            throw new NoSuchMethodError();
        }

        public final zzlt zzc() {
            throw new NoSuchMethodError();
        }

        public final boolean zzd() {
            throw new NoSuchMethodError();
        }

        public final boolean zze() {
            throw new NoSuchMethodError();
        }

        public final zzjq zza(zzjq zzjq, zzjr zzjr) {
            throw new NoSuchMethodError();
        }

        public final zzjx zza(zzjx zzjx, zzjx zzjx2) {
            throw new NoSuchMethodError();
        }

        public final /* synthetic */ int compareTo(Object obj) {
            throw new NoSuchMethodError();
        }
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static abstract class zzd<MessageType extends zzd<MessageType, BuilderType>, BuilderType> extends zzig<MessageType, BuilderType> implements zzjt {
        protected zzhz<zzc> zzc = zzhz.zza();
    }

    /* 'enum' modifier removed */
    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static final class zze {
        public static final int zza = 1;
        public static final int zzb = 2;
        public static final int zzc = 3;
        public static final int zzd = 4;
        public static final int zze = 5;
        public static final int zzf = 6;
        public static final int zzg = 7;
        public static final int zzh = 1;
        public static final int zzi = 2;
        public static final int zzj = 1;
        public static final int zzk = 2;
        private static final /* synthetic */ int[] zzl = {1, 2, 3, 4, 5, 6, 7};
        private static final /* synthetic */ int[] zzm = {1, 2};
        private static final /* synthetic */ int[] zzn = {1, 2};

        public static int[] zza() {
            return (int[]) zzl.clone();
        }
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static class zzf<ContainingType extends zzjr, Type> extends zzhu<ContainingType, Type> {
    }

    /* access modifiers changed from: protected */
    public abstract Object zza(int i, Object obj, Object obj2);

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static class zza<T extends zzig<T, ?>> extends zzgs<T> {
        private final T zza;

        public zza(T t) {
            this.zza = t;
        }

        public final /* synthetic */ Object zza(zzhh zzhh, zzht zzht) throws zzir {
            return zzig.zza(this.zza, zzhh, zzht);
        }
    }

    public String toString() {
        return zzjs.zza(this, super.toString());
    }

    public int hashCode() {
        if (this.zza != 0) {
            return this.zza;
        }
        this.zza = zzkd.zza().zza(this).zza(this);
        return this.zza;
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static abstract class zzb<MessageType extends zzig<MessageType, BuilderType>, BuilderType extends zzb<MessageType, BuilderType>> extends zzgq<MessageType, BuilderType> {
        protected MessageType zza;
        protected boolean zzb = false;
        private final MessageType zzc;

        protected zzb(MessageType messagetype) {
            this.zzc = messagetype;
            this.zza = (zzig) messagetype.zza(zze.zzd, (Object) null, (Object) null);
        }

        /* access modifiers changed from: protected */
        public void zzb() {
            MessageType messagetype = (zzig) this.zza.zza(zze.zzd, (Object) null, (Object) null);
            zza(messagetype, this.zza);
            this.zza = messagetype;
        }

        public final boolean zzaa() {
            return zzig.zza(this.zza, false);
        }

        /* renamed from: zzc */
        public MessageType zze() {
            if (this.zzb) {
                return this.zza;
            }
            MessageType messagetype = this.zza;
            zzkd.zza().zza(messagetype).zzb(messagetype);
            this.zzb = true;
            return this.zza;
        }

        /* renamed from: zzd */
        public final MessageType zzf() {
            MessageType messagetype = (zzig) zze();
            if (messagetype.zzaa()) {
                return messagetype;
            }
            throw new zzla(messagetype);
        }

        public final BuilderType zza(MessageType messagetype) {
            if (this.zzb) {
                zzb();
                this.zzb = false;
            }
            zza(this.zza, messagetype);
            return this;
        }

        private static void zza(MessageType messagetype, MessageType messagetype2) {
            zzkd.zza().zza(messagetype).zzb(messagetype, messagetype2);
        }

        public final /* synthetic */ zzgq zza() {
            return (zzb) clone();
        }

        public final /* synthetic */ zzjr zzag() {
            return this.zzc;
        }

        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            zzb zzb2 = (zzb) ((zzig) this.zzc).zza(zze.zze, (Object) null, (Object) null);
            zzb2.zza((zzig) zze());
            return zzb2;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            return zzkd.zza().zza(this).zza(this, (zzig) obj);
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public final <MessageType extends zzig<MessageType, BuilderType>, BuilderType extends zzb<MessageType, BuilderType>> BuilderType zzz() {
        return (zzb) zza(zze.zze, (Object) null, (Object) null);
    }

    public final boolean zzaa() {
        return zza(this, Boolean.TRUE.booleanValue());
    }

    /* access modifiers changed from: package-private */
    public final int zzy() {
        return this.zzc;
    }

    /* access modifiers changed from: package-private */
    public final void zzb(int i) {
        this.zzc = i;
    }

    public final void zza(zzhq zzhq) throws IOException {
        zzkd.zza().zza(this).zza(this, (zzlw) zzhs.zza(zzhq));
    }

    public final int zzab() {
        if (this.zzc == -1) {
            this.zzc = zzkd.zza().zza(this).zzd(this);
        }
        return this.zzc;
    }

    static <T extends zzig<?, ?>> T zza(Class<T> cls) {
        T t = (zzig) zzd.get(cls);
        if (t == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                t = (zzig) zzd.get(cls);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Class initialization cannot fail.", e);
            }
        }
        if (t == null) {
            t = (zzig) ((zzig) zzlf.zza(cls)).zza(zze.zzf, (Object) null, (Object) null);
            if (t != null) {
                zzd.put(cls, t);
            } else {
                throw new IllegalStateException();
            }
        }
        return t;
    }

    protected static <T extends zzig<?, ?>> void zza(Class<T> cls, T t) {
        zzd.put(cls, t);
    }

    protected static Object zza(zzjr zzjr, String str, Object[] objArr) {
        return new zzkf(zzjr, str, objArr);
    }

    static Object zza(Method method, Object obj, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e);
        } catch (InvocationTargetException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            } else if (cause instanceof Error) {
                throw ((Error) cause);
            } else {
                throw new RuntimeException("Unexpected exception thrown by generated accessor method.", cause);
            }
        }
    }

    protected static final <T extends zzig<T, ?>> boolean zza(T t, boolean z) {
        byte byteValue = ((Byte) t.zza(zze.zza, (Object) null, (Object) null)).byteValue();
        if (byteValue == 1) {
            return true;
        }
        if (byteValue == 0) {
            return false;
        }
        boolean zzc2 = zzkd.zza().zza(t).zzc(t);
        if (z) {
            t.zza(zze.zzb, (Object) zzc2 ? t : null, (Object) null);
        }
        return zzc2;
    }

    protected static zzim zzac() {
        return zzij.zzd();
    }

    protected static <E> zzio<E> zzad() {
        return zzkc.zzd();
    }

    static <T extends zzig<T, ?>> T zza(T t, zzhh zzhh, zzht zzht) throws zzir {
        T t2 = (zzig) t.zza(zze.zzd, (Object) null, (Object) null);
        try {
            zzkh zza2 = zzkd.zza().zza(t2);
            zza2.zza(t2, zzho.zza(zzhh), zzht);
            zza2.zzb(t2);
            return t2;
        } catch (IOException e) {
            if (e.getCause() instanceof zzir) {
                throw ((zzir) e.getCause());
            }
            throw new zzir(e.getMessage()).zza(t2);
        } catch (RuntimeException e2) {
            if (e2.getCause() instanceof zzir) {
                throw ((zzir) e2.getCause());
            }
            throw e2;
        }
    }

    public final /* synthetic */ zzjq zzae() {
        zzb zzb2 = (zzb) zza(zze.zze, (Object) null, (Object) null);
        zzb2.zza(this);
        return zzb2;
    }

    public final /* synthetic */ zzjq zzaf() {
        return (zzb) zza(zze.zze, (Object) null, (Object) null);
    }

    public final /* synthetic */ zzjr zzag() {
        return (zzig) zza(zze.zzf, (Object) null, (Object) null);
    }
}
