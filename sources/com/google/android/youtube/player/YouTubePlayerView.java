package com.google.android.youtube.player;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.core.view.ViewCompat;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.internal.C1097aa;
import com.google.android.youtube.player.internal.C1098ab;
import com.google.android.youtube.player.internal.C1100b;
import com.google.android.youtube.player.internal.C1132n;
import com.google.android.youtube.player.internal.C1146s;
import com.google.android.youtube.player.internal.C1151t;
import com.google.android.youtube.player.internal.C1158w;
import com.google.android.youtube.player.internal.C1161y;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public final class YouTubePlayerView extends ViewGroup implements YouTubePlayer.Provider {

    /* renamed from: a */
    private final C1093a f160a;
    /* access modifiers changed from: private */

    /* renamed from: b */
    public final Set<View> f161b;

    /* renamed from: c */
    private final C1094b f162c;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public C1100b f163d;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public C1146s f164e;
    /* access modifiers changed from: private */

    /* renamed from: f */
    public View f165f;
    /* access modifiers changed from: private */

    /* renamed from: g */
    public C1132n f166g;

    /* renamed from: h */
    private YouTubePlayer.Provider f167h;

    /* renamed from: i */
    private Bundle f168i;

    /* renamed from: j */
    private YouTubePlayer.OnInitializedListener f169j;

    /* renamed from: k */
    private boolean f170k;
    /* access modifiers changed from: private */

    /* renamed from: l */
    public boolean f171l;

    /* renamed from: com.google.android.youtube.player.YouTubePlayerView$a */
    private final class C1093a implements ViewTreeObserver.OnGlobalFocusChangeListener {
        private C1093a() {
        }

        /* synthetic */ C1093a(YouTubePlayerView youTubePlayerView, byte b) {
            this();
        }

        public final void onGlobalFocusChanged(View view, View view2) {
            if (YouTubePlayerView.this.f164e != null && YouTubePlayerView.this.f161b.contains(view2) && !YouTubePlayerView.this.f161b.contains(view)) {
                YouTubePlayerView.this.f164e.mo16691g();
            }
        }
    }

    /* renamed from: com.google.android.youtube.player.YouTubePlayerView$b */
    interface C1094b {
        /* renamed from: a */
        void mo16423a(YouTubePlayerView youTubePlayerView);

        /* renamed from: a */
        void mo16424a(YouTubePlayerView youTubePlayerView, String str, YouTubePlayer.OnInitializedListener onInitializedListener);
    }

    public YouTubePlayerView(Context context) {
        this(context, (AttributeSet) null);
    }

    public YouTubePlayerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public YouTubePlayerView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, ((YouTubeBaseActivity) context).mo16415a());
        if (context instanceof YouTubeBaseActivity) {
            return;
        }
        throw new IllegalStateException("A YouTubePlayerView can only be created with an Activity  which extends YouTubeBaseActivity as its context.");
    }

    YouTubePlayerView(Context context, AttributeSet attributeSet, int i, C1094b bVar) {
        super((Context) C1098ab.m124a(context, (Object) "context cannot be null"), attributeSet, i);
        this.f162c = (C1094b) C1098ab.m124a(bVar, (Object) "listener cannot be null");
        if (getBackground() == null) {
            setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        }
        setClipToPadding(false);
        C1132n nVar = new C1132n(context);
        this.f166g = nVar;
        requestTransparentRegion(nVar);
        addView(this.f166g);
        this.f161b = new HashSet();
        this.f160a = new C1093a(this, (byte) 0);
    }

    /* renamed from: a */
    private void m70a(View view) {
        if (!(view == this.f166g || (this.f164e != null && view == this.f165f))) {
            throw new UnsupportedOperationException("No views can be added on top of the player");
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m71a(YouTubeInitializationResult youTubeInitializationResult) {
        this.f164e = null;
        this.f166g.mo16656c();
        YouTubePlayer.OnInitializedListener onInitializedListener = this.f169j;
        if (onInitializedListener != null) {
            onInitializedListener.onInitializationFailure(this.f167h, youTubeInitializationResult);
            this.f169j = null;
        }
    }

    /* renamed from: a */
    static /* synthetic */ void m72a(YouTubePlayerView youTubePlayerView, Activity activity) {
        try {
            C1146s sVar = new C1146s(youTubePlayerView.f163d, C1097aa.m118a().mo16553a(activity, youTubePlayerView.f163d, youTubePlayerView.f170k));
            youTubePlayerView.f164e = sVar;
            View a = sVar.mo16679a();
            youTubePlayerView.f165f = a;
            youTubePlayerView.addView(a);
            youTubePlayerView.removeView(youTubePlayerView.f166g);
            youTubePlayerView.f162c.mo16423a(youTubePlayerView);
            if (youTubePlayerView.f169j != null) {
                boolean z = false;
                Bundle bundle = youTubePlayerView.f168i;
                if (bundle != null) {
                    z = youTubePlayerView.f164e.mo16683a(bundle);
                    youTubePlayerView.f168i = null;
                }
                youTubePlayerView.f169j.onInitializationSuccess(youTubePlayerView.f167h, youTubePlayerView.f164e, z);
                youTubePlayerView.f169j = null;
            }
        } catch (C1158w.C1159a e) {
            C1161y.m374a("Error creating YouTubePlayerView", (Throwable) e);
            youTubePlayerView.m71a(YouTubeInitializationResult.INTERNAL_ERROR);
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo16491a() {
        C1146s sVar = this.f164e;
        if (sVar != null) {
            sVar.mo16684b();
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo16492a(final Activity activity, YouTubePlayer.Provider provider, String str, YouTubePlayer.OnInitializedListener onInitializedListener, Bundle bundle) {
        if (this.f164e == null && this.f169j == null) {
            C1098ab.m124a(activity, (Object) "activity cannot be null");
            this.f167h = (YouTubePlayer.Provider) C1098ab.m124a(provider, (Object) "provider cannot be null");
            this.f169j = (YouTubePlayer.OnInitializedListener) C1098ab.m124a(onInitializedListener, (Object) "listener cannot be null");
            this.f168i = bundle;
            this.f166g.mo16655b();
            C1100b a = C1097aa.m118a().mo16552a(getContext(), str, new C1151t.C1152a() {
                /* renamed from: a */
                public final void mo16519a() {
                    if (YouTubePlayerView.this.f163d != null) {
                        YouTubePlayerView.m72a(YouTubePlayerView.this, activity);
                    }
                    C1100b unused = YouTubePlayerView.this.f163d = null;
                }

                /* renamed from: b */
                public final void mo16520b() {
                    if (!YouTubePlayerView.this.f171l && YouTubePlayerView.this.f164e != null) {
                        YouTubePlayerView.this.f164e.mo16690f();
                    }
                    YouTubePlayerView.this.f166g.mo16654a();
                    YouTubePlayerView youTubePlayerView = YouTubePlayerView.this;
                    if (youTubePlayerView.indexOfChild(youTubePlayerView.f166g) < 0) {
                        YouTubePlayerView youTubePlayerView2 = YouTubePlayerView.this;
                        youTubePlayerView2.addView(youTubePlayerView2.f166g);
                        YouTubePlayerView youTubePlayerView3 = YouTubePlayerView.this;
                        youTubePlayerView3.removeView(youTubePlayerView3.f165f);
                    }
                    View unused = YouTubePlayerView.this.f165f = null;
                    C1146s unused2 = YouTubePlayerView.this.f164e = null;
                    C1100b unused3 = YouTubePlayerView.this.f163d = null;
                }
            }, new C1151t.C1153b() {
                /* renamed from: a */
                public final void mo16521a(YouTubeInitializationResult youTubeInitializationResult) {
                    YouTubePlayerView.this.m71a(youTubeInitializationResult);
                    C1100b unused = YouTubePlayerView.this.f163d = null;
                }
            });
            this.f163d = a;
            a.mo16667e();
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo16493a(boolean z) {
        if (!z || Build.VERSION.SDK_INT >= 14) {
            this.f170k = z;
            return;
        }
        C1161y.m375a("Could not enable TextureView because API level is lower than 14", new Object[0]);
        this.f170k = false;
    }

    public final void addFocusables(ArrayList<View> arrayList, int i) {
        ArrayList arrayList2 = new ArrayList();
        super.addFocusables(arrayList2, i);
        arrayList.addAll(arrayList2);
        this.f161b.clear();
        this.f161b.addAll(arrayList2);
    }

    public final void addFocusables(ArrayList<View> arrayList, int i, int i2) {
        ArrayList arrayList2 = new ArrayList();
        super.addFocusables(arrayList2, i, i2);
        arrayList.addAll(arrayList2);
        this.f161b.clear();
        this.f161b.addAll(arrayList2);
    }

    public final void addView(View view) {
        m70a(view);
        super.addView(view);
    }

    public final void addView(View view, int i) {
        m70a(view);
        super.addView(view, i);
    }

    public final void addView(View view, int i, int i2) {
        m70a(view);
        super.addView(view, i, i2);
    }

    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        m70a(view);
        super.addView(view, i, layoutParams);
    }

    public final void addView(View view, ViewGroup.LayoutParams layoutParams) {
        m70a(view);
        super.addView(view, layoutParams);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final void mo16501b() {
        C1146s sVar = this.f164e;
        if (sVar != null) {
            sVar.mo16687c();
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final void mo16502b(boolean z) {
        C1146s sVar = this.f164e;
        if (sVar != null) {
            sVar.mo16685b(z);
            mo16504c(z);
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public final void mo16503c() {
        C1146s sVar = this.f164e;
        if (sVar != null) {
            sVar.mo16688d();
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public final void mo16504c(boolean z) {
        this.f171l = true;
        C1146s sVar = this.f164e;
        if (sVar != null) {
            sVar.mo16681a(z);
        }
    }

    public final void clearChildFocus(View view) {
        if (hasFocusable()) {
            requestFocus();
        } else {
            super.clearChildFocus(view);
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public final void mo16506d() {
        C1146s sVar = this.f164e;
        if (sVar != null) {
            sVar.mo16689e();
        }
    }

    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (this.f164e != null) {
            if (keyEvent.getAction() == 0) {
                return this.f164e.mo16682a(keyEvent.getKeyCode(), keyEvent) || super.dispatchKeyEvent(keyEvent);
            }
            if (keyEvent.getAction() == 1) {
                return this.f164e.mo16686b(keyEvent.getKeyCode(), keyEvent) || super.dispatchKeyEvent(keyEvent);
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: e */
    public final Bundle mo16508e() {
        C1146s sVar = this.f164e;
        return sVar == null ? this.f168i : sVar.mo16692h();
    }

    public final void focusableViewAvailable(View view) {
        super.focusableViewAvailable(view);
        this.f161b.add(view);
    }

    public final void initialize(String str, YouTubePlayer.OnInitializedListener onInitializedListener) {
        C1098ab.m125a(str, (Object) "Developer key cannot be null or empty");
        this.f162c.mo16424a(this, str, onInitializedListener);
    }

    /* access modifiers changed from: protected */
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalFocusChangeListener(this.f160a);
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        C1146s sVar = this.f164e;
        if (sVar != null) {
            sVar.mo16680a(configuration);
        }
    }

    /* access modifiers changed from: protected */
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalFocusChangeListener(this.f160a);
    }

    /* access modifiers changed from: protected */
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (getChildCount() > 0) {
            getChildAt(0).layout(0, 0, i3 - i, i4 - i2);
        }
    }

    /* access modifiers changed from: protected */
    public final void onMeasure(int i, int i2) {
        if (getChildCount() > 0) {
            View childAt = getChildAt(0);
            childAt.measure(i, i2);
            setMeasuredDimension(childAt.getMeasuredWidth(), childAt.getMeasuredHeight());
            return;
        }
        setMeasuredDimension(0, 0);
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return true;
    }

    public final void requestChildFocus(View view, View view2) {
        super.requestChildFocus(view, view2);
        this.f161b.add(view2);
    }

    public final void setClipToPadding(boolean z) {
    }

    public final void setPadding(int i, int i2, int i3, int i4) {
    }
}
