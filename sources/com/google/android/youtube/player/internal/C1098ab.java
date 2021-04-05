package com.google.android.youtube.player.internal;

import android.text.TextUtils;

/* renamed from: com.google.android.youtube.player.internal.ab */
public final class C1098ab {
    /* renamed from: a */
    public static <T> T m123a(T t) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException("null reference");
    }

    /* renamed from: a */
    public static <T> T m124a(T t, Object obj) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(String.valueOf(obj));
    }

    /* renamed from: a */
    public static String m125a(String str, Object obj) {
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        throw new IllegalArgumentException(String.valueOf(obj));
    }

    /* renamed from: a */
    public static void m126a(boolean z) {
        if (!z) {
            throw new IllegalStateException();
        }
    }
}
