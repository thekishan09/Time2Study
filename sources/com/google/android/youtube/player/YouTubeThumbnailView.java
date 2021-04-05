package com.google.android.youtube.player;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.google.android.youtube.player.internal.C1096a;
import com.google.android.youtube.player.internal.C1097aa;
import com.google.android.youtube.player.internal.C1098ab;
import com.google.android.youtube.player.internal.C1100b;
import com.google.android.youtube.player.internal.C1151t;

public final class YouTubeThumbnailView extends ImageView {
    /* access modifiers changed from: private */

    /* renamed from: a */
    public C1100b f177a;
    /* access modifiers changed from: private */

    /* renamed from: b */
    public C1096a f178b;

    public interface OnInitializedListener {
        void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult);

        void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader);
    }

    /* renamed from: com.google.android.youtube.player.YouTubeThumbnailView$a */
    private static final class C1095a implements C1151t.C1152a, C1151t.C1153b {

        /* renamed from: a */
        private YouTubeThumbnailView f179a;

        /* renamed from: b */
        private OnInitializedListener f180b;

        public C1095a(YouTubeThumbnailView youTubeThumbnailView, OnInitializedListener onInitializedListener) {
            this.f179a = (YouTubeThumbnailView) C1098ab.m124a(youTubeThumbnailView, (Object) "thumbnailView cannot be null");
            this.f180b = (OnInitializedListener) C1098ab.m124a(onInitializedListener, (Object) "onInitializedlistener cannot be null");
        }

        /* renamed from: c */
        private void m101c() {
            YouTubeThumbnailView youTubeThumbnailView = this.f179a;
            if (youTubeThumbnailView != null) {
                C1100b unused = youTubeThumbnailView.f177a = null;
                this.f179a = null;
                this.f180b = null;
            }
        }

        /* renamed from: a */
        public final void mo16519a() {
            YouTubeThumbnailView youTubeThumbnailView = this.f179a;
            if (youTubeThumbnailView != null && youTubeThumbnailView.f177a != null) {
                C1096a unused = this.f179a.f178b = C1097aa.m118a().mo16551a(this.f179a.f177a, this.f179a);
                OnInitializedListener onInitializedListener = this.f180b;
                YouTubeThumbnailView youTubeThumbnailView2 = this.f179a;
                onInitializedListener.onInitializationSuccess(youTubeThumbnailView2, youTubeThumbnailView2.f178b);
                m101c();
            }
        }

        /* renamed from: a */
        public final void mo16521a(YouTubeInitializationResult youTubeInitializationResult) {
            this.f180b.onInitializationFailure(this.f179a, youTubeInitializationResult);
            m101c();
        }

        /* renamed from: b */
        public final void mo16520b() {
            m101c();
        }
    }

    public YouTubeThumbnailView(Context context) {
        this(context, (AttributeSet) null);
    }

    public YouTubeThumbnailView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public YouTubeThumbnailView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public final void finalize() throws Throwable {
        C1096a aVar = this.f178b;
        if (aVar != null) {
            aVar.mo16543b();
            this.f178b = null;
        }
        super.finalize();
    }

    public final void initialize(String str, OnInitializedListener onInitializedListener) {
        C1095a aVar = new C1095a(this, onInitializedListener);
        C1100b a = C1097aa.m118a().mo16552a(getContext(), str, aVar, aVar);
        this.f177a = a;
        a.mo16667e();
    }
}
