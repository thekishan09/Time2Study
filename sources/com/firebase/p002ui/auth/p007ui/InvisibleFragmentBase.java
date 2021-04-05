package com.firebase.p002ui.auth.p007ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.FrameLayout;
import com.firebase.p002ui.auth.C0719R;
import p015me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/* renamed from: com.firebase.ui.auth.ui.InvisibleFragmentBase */
public class InvisibleFragmentBase extends FragmentBase {
    private static final long MIN_SPINNER_MS = 750;
    protected FrameLayout mFrameLayout;
    private Handler mHandler = new Handler();
    /* access modifiers changed from: private */
    public long mLastShownTime = 0;
    /* access modifiers changed from: private */
    public MaterialProgressBar mProgressBar;
    protected View mTopLevelView;

    public void onViewCreated(View view, Bundle savedInstanceState) {
        MaterialProgressBar materialProgressBar = new MaterialProgressBar(new ContextThemeWrapper(getContext(), getFlowParams().themeId));
        this.mProgressBar = materialProgressBar;
        materialProgressBar.setIndeterminate(true);
        this.mProgressBar.setVisibility(8);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-2, -2);
        params.gravity = 17;
        FrameLayout frameLayout = (FrameLayout) view.findViewById(C0719R.C0722id.invisible_frame);
        this.mFrameLayout = frameLayout;
        frameLayout.addView(this.mProgressBar, params);
    }

    public void showProgress(int message) {
        if (this.mProgressBar.getVisibility() == 0) {
            this.mHandler.removeCallbacksAndMessages((Object) null);
            return;
        }
        this.mLastShownTime = System.currentTimeMillis();
        this.mProgressBar.setVisibility(0);
    }

    public void hideProgress() {
        doAfterTimeout(new Runnable() {
            public void run() {
                long unused = InvisibleFragmentBase.this.mLastShownTime = 0;
                InvisibleFragmentBase.this.mProgressBar.setVisibility(8);
                InvisibleFragmentBase.this.mFrameLayout.setVisibility(8);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void doAfterTimeout(Runnable runnable) {
        this.mHandler.postDelayed(runnable, Math.max(MIN_SPINNER_MS - (System.currentTimeMillis() - this.mLastShownTime), 0));
    }
}
