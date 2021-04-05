package com.google.android.youtube.player.internal;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import com.google.android.youtube.player.internal.C1104d;
import java.lang.reflect.InvocationTargetException;

/* renamed from: com.google.android.youtube.player.internal.w */
public final class C1158w {

    /* renamed from: com.google.android.youtube.player.internal.w$a */
    public static final class C1159a extends Exception {
        public C1159a(String str) {
            super(str);
        }

        public C1159a(String str, Throwable th) {
            super(str, th);
        }
    }

    /* renamed from: a */
    private static IBinder m369a(Class<?> cls, IBinder iBinder, IBinder iBinder2, IBinder iBinder3, boolean z) throws C1159a {
        try {
            return (IBinder) cls.getConstructor(new Class[]{IBinder.class, IBinder.class, IBinder.class, Boolean.TYPE}).newInstance(new Object[]{iBinder, iBinder2, iBinder3, Boolean.valueOf(z)});
        } catch (NoSuchMethodException e) {
            String valueOf = String.valueOf(cls.getName());
            throw new C1159a(valueOf.length() != 0 ? "Could not find the right constructor for ".concat(valueOf) : new String("Could not find the right constructor for "), e);
        } catch (InvocationTargetException e2) {
            String valueOf2 = String.valueOf(cls.getName());
            throw new C1159a(valueOf2.length() != 0 ? "Exception thrown by invoked constructor in ".concat(valueOf2) : new String("Exception thrown by invoked constructor in "), e2);
        } catch (InstantiationException e3) {
            String valueOf3 = String.valueOf(cls.getName());
            throw new C1159a(valueOf3.length() != 0 ? "Unable to instantiate the dynamic class ".concat(valueOf3) : new String("Unable to instantiate the dynamic class "), e3);
        } catch (IllegalAccessException e4) {
            String valueOf4 = String.valueOf(cls.getName());
            throw new C1159a(valueOf4.length() != 0 ? "Unable to call the default constructor of ".concat(valueOf4) : new String("Unable to call the default constructor of "), e4);
        }
    }

    /* renamed from: a */
    private static IBinder m370a(ClassLoader classLoader, String str, IBinder iBinder, IBinder iBinder2, IBinder iBinder3, boolean z) throws C1159a {
        try {
            return m369a(classLoader.loadClass(str), iBinder, iBinder2, iBinder3, z);
        } catch (ClassNotFoundException e) {
            String valueOf = String.valueOf(str);
            throw new C1159a(valueOf.length() != 0 ? "Unable to find dynamic class ".concat(valueOf) : new String("Unable to find dynamic class "), e);
        }
    }

    /* renamed from: a */
    public static C1104d m371a(Activity activity, IBinder iBinder, boolean z) throws C1159a {
        C1098ab.m123a(activity);
        C1098ab.m123a(iBinder);
        Context b = C1162z.m381b((Context) activity);
        if (b != null) {
            return C1104d.C1105a.m178a(m370a(b.getClassLoader(), "com.google.android.youtube.api.jar.client.RemoteEmbeddedPlayer", C1157v.m367a(b).asBinder(), C1157v.m367a(activity).asBinder(), iBinder, z));
        }
        throw new C1159a("Could not create remote context");
    }
}
