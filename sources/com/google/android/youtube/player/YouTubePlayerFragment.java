package com.google.android.youtube.player;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.internal.C1098ab;

public class YouTubePlayerFragment extends Fragment implements YouTubePlayer.Provider {

    /* renamed from: a */
    private final C1089a f146a = new C1089a(this, (byte) 0);

    /* renamed from: b */
    private Bundle f147b;

    /* renamed from: c */
    private YouTubePlayerView f148c;

    /* renamed from: d */
    private String f149d;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public YouTubePlayer.OnInitializedListener f150e;

    /* renamed from: f */
    private boolean f151f;

    /* renamed from: com.google.android.youtube.player.YouTubePlayerFragment$a */
    private final class C1089a implements YouTubePlayerView.C1094b {
        private C1089a() {
        }

        /* synthetic */ C1089a(YouTubePlayerFragment youTubePlayerFragment, byte b) {
            this();
        }

        /* renamed from: a */
        public final void mo16423a(YouTubePlayerView youTubePlayerView) {
        }

        /* renamed from: a */
        public final void mo16424a(YouTubePlayerView youTubePlayerView, String str, YouTubePlayer.OnInitializedListener onInitializedListener) {
            YouTubePlayerFragment youTubePlayerFragment = YouTubePlayerFragment.this;
            youTubePlayerFragment.initialize(str, youTubePlayerFragment.f150e);
        }
    }

    /* renamed from: a */
    private void m62a() {
        YouTubePlayerView youTubePlayerView = this.f148c;
        if (youTubePlayerView != null && this.f150e != null) {
            youTubePlayerView.mo16493a(this.f151f);
            this.f148c.mo16492a(getActivity(), this, this.f149d, this.f150e, this.f147b);
            this.f147b = null;
            this.f150e = null;
        }
    }

    public static YouTubePlayerFragment newInstance() {
        return new YouTubePlayerFragment();
    }

    public void initialize(String str, YouTubePlayer.OnInitializedListener onInitializedListener) {
        this.f149d = C1098ab.m125a(str, (Object) "Developer key cannot be null or empty");
        this.f150e = onInitializedListener;
        m62a();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.f147b = bundle != null ? bundle.getBundle("YouTubePlayerFragment.KEY_PLAYER_VIEW_STATE") : null;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.f148c = new YouTubePlayerView(getActivity(), (AttributeSet) null, 0, this.f146a);
        m62a();
        return this.f148c;
    }

    public void onDestroy() {
        if (this.f148c != null) {
            Activity activity = getActivity();
            this.f148c.mo16502b(activity == null || activity.isFinishing());
        }
        super.onDestroy();
    }

    public void onDestroyView() {
        this.f148c.mo16504c(getActivity().isFinishing());
        this.f148c = null;
        super.onDestroyView();
    }

    public void onPause() {
        this.f148c.mo16503c();
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        this.f148c.mo16501b();
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        YouTubePlayerView youTubePlayerView = this.f148c;
        bundle.putBundle("YouTubePlayerFragment.KEY_PLAYER_VIEW_STATE", youTubePlayerView != null ? youTubePlayerView.mo16508e() : this.f147b);
    }

    public void onStart() {
        super.onStart();
        this.f148c.mo16491a();
    }

    public void onStop() {
        this.f148c.mo16506d();
        super.onStop();
    }
}
