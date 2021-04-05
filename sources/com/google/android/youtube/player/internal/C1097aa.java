package com.google.android.youtube.player.internal;

import android.app.Activity;
import android.content.Context;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.android.youtube.player.internal.C1151t;
import com.google.android.youtube.player.internal.C1158w;

/* renamed from: com.google.android.youtube.player.internal.aa */
public abstract class C1097aa {

    /* renamed from: a */
    private static final C1097aa f185a = m119b();

    /* renamed from: a */
    public static C1097aa m118a() {
        return f185a;
    }

    /* renamed from: b */
    private static C1097aa m119b() {
        try {
            return (C1097aa) Class.forName("com.google.android.youtube.api.locallylinked.LocallyLinkedFactory").asSubclass(C1097aa.class).newInstance();
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        } catch (IllegalAccessException e2) {
            throw new IllegalStateException(e2);
        } catch (ClassNotFoundException e3) {
            return new C1099ac();
        }
    }

    /* renamed from: a */
    public abstract C1096a mo16551a(C1100b bVar, YouTubeThumbnailView youTubeThumbnailView);

    /* renamed from: a */
    public abstract C1100b mo16552a(Context context, String str, C1151t.C1152a aVar, C1151t.C1153b bVar);

    /* renamed from: a */
    public abstract C1104d mo16553a(Activity activity, C1100b bVar, boolean z) throws C1158w.C1159a;
}
