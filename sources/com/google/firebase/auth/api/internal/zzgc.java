package com.google.firebase.auth.api.internal;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzgc {
    public static String zza(String str) {
        try {
            Object invoke = Class.forName("android.os.SystemProperties").getDeclaredMethod("get", new Class[]{String.class}).invoke((Object) null, new Object[]{str});
            if (invoke != null && String.class.isAssignableFrom(invoke.getClass())) {
                return (String) invoke;
            }
        } catch (Exception e) {
        }
        return null;
    }
}
