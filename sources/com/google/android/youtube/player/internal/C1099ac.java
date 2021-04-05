package com.google.android.youtube.player.internal;

import android.app.Activity;
import android.content.Context;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.android.youtube.player.internal.C1151t;
import com.google.android.youtube.player.internal.C1158w;

/* renamed from: com.google.android.youtube.player.internal.ac */
public final class C1099ac extends C1097aa {
    /* renamed from: a */
    public final C1096a mo16551a(C1100b bVar, YouTubeThumbnailView youTubeThumbnailView) {
        return new C1134p(bVar, youTubeThumbnailView);
    }

    /* renamed from: a */
    public final C1100b mo16552a(Context context, String str, C1151t.C1152a aVar, C1151t.C1153b bVar) {
        return new C1133o(context, str, context.getPackageName(), C1162z.m385d(context), aVar, bVar);
    }

    /* renamed from: a */
    public final C1104d mo16553a(Activity activity, C1100b bVar, boolean z) throws C1158w.C1159a {
        return C1158w.m371a(activity, bVar.mo16554a(), z);
    }
}
