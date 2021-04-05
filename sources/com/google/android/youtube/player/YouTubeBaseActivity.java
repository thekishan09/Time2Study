package com.google.android.youtube.player;

import android.app.Activity;
import android.os.Bundle;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YouTubeBaseActivity extends Activity {

    /* renamed from: a */
    private C1086a f134a;
    /* access modifiers changed from: private */

    /* renamed from: b */
    public YouTubePlayerView f135b;
    /* access modifiers changed from: private */

    /* renamed from: c */
    public int f136c;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public Bundle f137d;

    /* renamed from: com.google.android.youtube.player.YouTubeBaseActivity$a */
    private final class C1086a implements YouTubePlayerView.C1094b {
        private C1086a() {
        }

        /* synthetic */ C1086a(YouTubeBaseActivity youTubeBaseActivity, byte b) {
            this();
        }

        /* renamed from: a */
        public final void mo16423a(YouTubePlayerView youTubePlayerView) {
            if (!(YouTubeBaseActivity.this.f135b == null || YouTubeBaseActivity.this.f135b == youTubePlayerView)) {
                YouTubeBaseActivity.this.f135b.mo16504c(true);
            }
            YouTubePlayerView unused = YouTubeBaseActivity.this.f135b = youTubePlayerView;
            if (YouTubeBaseActivity.this.f136c > 0) {
                youTubePlayerView.mo16491a();
            }
            if (YouTubeBaseActivity.this.f136c >= 2) {
                youTubePlayerView.mo16501b();
            }
        }

        /* renamed from: a */
        public final void mo16424a(YouTubePlayerView youTubePlayerView, String str, YouTubePlayer.OnInitializedListener onInitializedListener) {
            YouTubeBaseActivity youTubeBaseActivity = YouTubeBaseActivity.this;
            youTubePlayerView.mo16492a(youTubeBaseActivity, youTubePlayerView, str, onInitializedListener, youTubeBaseActivity.f137d);
            Bundle unused = YouTubeBaseActivity.this.f137d = null;
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final YouTubePlayerView.C1094b mo16415a() {
        return this.f134a;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.f134a = new C1086a(this, (byte) 0);
        this.f137d = bundle != null ? bundle.getBundle("YouTubeBaseActivity.KEY_PLAYER_VIEW_STATE") : null;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        YouTubePlayerView youTubePlayerView = this.f135b;
        if (youTubePlayerView != null) {
            youTubePlayerView.mo16502b(isFinishing());
        }
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        this.f136c = 1;
        YouTubePlayerView youTubePlayerView = this.f135b;
        if (youTubePlayerView != null) {
            youTubePlayerView.mo16503c();
        }
        super.onPause();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.f136c = 2;
        YouTubePlayerView youTubePlayerView = this.f135b;
        if (youTubePlayerView != null) {
            youTubePlayerView.mo16501b();
        }
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        YouTubePlayerView youTubePlayerView = this.f135b;
        bundle.putBundle("YouTubeBaseActivity.KEY_PLAYER_VIEW_STATE", youTubePlayerView != null ? youTubePlayerView.mo16508e() : this.f137d);
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        this.f136c = 1;
        YouTubePlayerView youTubePlayerView = this.f135b;
        if (youTubePlayerView != null) {
            youTubePlayerView.mo16491a();
        }
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        this.f136c = 0;
        YouTubePlayerView youTubePlayerView = this.f135b;
        if (youTubePlayerView != null) {
            youTubePlayerView.mo16506d();
        }
        super.onStop();
    }
}
