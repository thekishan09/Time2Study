package com.google.android.youtube.player.internal;

import android.content.Context;
import android.content.res.Resources;
import java.util.Locale;
import java.util.Map;

/* renamed from: com.google.android.youtube.player.internal.m */
public final class C1131m {

    /* renamed from: a */
    public final String f196a;

    /* renamed from: b */
    public final String f197b;

    /* renamed from: c */
    public final String f198c;

    /* renamed from: d */
    public final String f199d;

    /* renamed from: e */
    public final String f200e;

    /* renamed from: f */
    public final String f201f;

    /* renamed from: g */
    public final String f202g;

    /* renamed from: h */
    public final String f203h;

    /* renamed from: i */
    public final String f204i;

    /* renamed from: j */
    public final String f205j;

    public C1131m(Context context) {
        Resources resources = context.getResources();
        Map<String, String> a = C1160x.m372a((resources == null || resources.getConfiguration() == null || resources.getConfiguration().locale == null) ? Locale.getDefault() : resources.getConfiguration().locale);
        this.f196a = a.get("error_initializing_player");
        this.f197b = a.get("get_youtube_app_title");
        this.f198c = a.get("get_youtube_app_text");
        this.f199d = a.get("get_youtube_app_action");
        this.f200e = a.get("enable_youtube_app_title");
        this.f201f = a.get("enable_youtube_app_text");
        this.f202g = a.get("enable_youtube_app_action");
        this.f203h = a.get("update_youtube_app_title");
        this.f204i = a.get("update_youtube_app_text");
        this.f205j = a.get("update_youtube_app_action");
    }
}
