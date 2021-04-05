package com.google.android.youtube.player;

import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.internal.C1098ab;

public class YouTubePlayerSupportFragment extends Fragment implements YouTubePlayer.Provider {

    /* renamed from: a */
    private final C1090a f153a = new C1090a(this, (byte) 0);

    /* renamed from: b */
    private Bundle f154b;

    /* renamed from: c */
    private YouTubePlayerView f155c;

    /* renamed from: d */
    private String f156d;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public YouTubePlayer.OnInitializedListener f157e;

    /* renamed from: f */
    private boolean f158f;

    /* renamed from: com.google.android.youtube.player.YouTubePlayerSupportFragment$a */
    private final class C1090a implements YouTubePlayerView.C1094b {
        private C1090a() {
        }

        /* synthetic */ C1090a(YouTubePlayerSupportFragment youTubePlayerSupportFragment, byte b) {
            this();
        }

        /* renamed from: a */
        public final void mo16423a(YouTubePlayerView youTubePlayerView) {
        }

        /* renamed from: a */
        public final void mo16424a(YouTubePlayerView youTubePlayerView, String str, YouTubePlayer.OnInitializedListener onInitializedListener) {
            YouTubePlayerSupportFragment youTubePlayerSupportFragment = YouTubePlayerSupportFragment.this;
            youTubePlayerSupportFragment.initialize(str, youTubePlayerSupportFragment.f157e);
        }
    }

    /* renamed from: a */
    private void m66a() {
        YouTubePlayerView youTubePlayerView = this.f155c;
        if (youTubePlayerView != null && this.f157e != null) {
            youTubePlayerView.mo16493a(this.f158f);
            this.f155c.mo16492a(getActivity(), this, this.f156d, this.f157e, this.f154b);
            this.f154b = null;
            this.f157e = null;
        }
    }

    public static YouTubePlayerSupportFragment newInstance() {
        return new YouTubePlayerSupportFragment();
    }

    public void initialize(String str, YouTubePlayer.OnInitializedListener onInitializedListener) {
        this.f156d = C1098ab.m125a(str, (Object) "Developer key cannot be null or empty");
        this.f157e = onInitializedListener;
        m66a();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.f154b = bundle != null ? bundle.getBundle("YouTubePlayerSupportFragment.KEY_PLAYER_VIEW_STATE") : null;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.f155c = new YouTubePlayerView(getActivity(), (AttributeSet) null, 0, this.f153a);
        m66a();
        return this.f155c;
    }

    public void onDestroy() {
        if (this.f155c != null) {
            FragmentActivity activity = getActivity();
            this.f155c.mo16502b(activity == null || activity.isFinishing());
        }
        super.onDestroy();
    }

    public void onDestroyView() {
        this.f155c.mo16504c(getActivity().isFinishing());
        this.f155c = null;
        super.onDestroyView();
    }

    public void onPause() {
        this.f155c.mo16503c();
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        this.f155c.mo16501b();
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        YouTubePlayerView youTubePlayerView = this.f155c;
        bundle.putBundle("YouTubePlayerSupportFragment.KEY_PLAYER_VIEW_STATE", youTubePlayerView != null ? youTubePlayerView.mo16508e() : this.f154b);
    }

    public void onStart() {
        super.onStart();
        this.f155c.mo16491a();
    }

    public void onStop() {
        this.f155c.mo16506d();
        super.onStop();
    }
}
