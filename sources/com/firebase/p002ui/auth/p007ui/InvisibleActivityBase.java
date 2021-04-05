package com.firebase.p002ui.auth.p007ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextThemeWrapper;
import android.widget.FrameLayout;
import com.firebase.p002ui.auth.C0719R;
import p015me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/* renamed from: com.firebase.ui.auth.ui.InvisibleActivityBase */
public class InvisibleActivityBase extends HelperActivityBase {
    private static final long MIN_SPINNER_MS = 750;
    private Handler mHandler = new Handler();
    /* access modifiers changed from: private */
    public long mLastShownTime = 0;
    /* access modifiers changed from: private */
    public MaterialProgressBar mProgressBar;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0719R.layout.fui_activity_invisible);
        MaterialProgressBar materialProgressBar = new MaterialProgressBar(new ContextThemeWrapper(this, getFlowParams().themeId));
        this.mProgressBar = materialProgressBar;
        materialProgressBar.setIndeterminate(true);
        this.mProgressBar.setVisibility(8);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-2, -2);
        params.gravity = 17;
        ((FrameLayout) findViewById(C0719R.C0722id.invisible_frame)).addView(this.mProgressBar, params);
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
                long unused = InvisibleActivityBase.this.mLastShownTime = 0;
                InvisibleActivityBase.this.mProgressBar.setVisibility(8);
            }
        });
    }

    public void finish(int resultCode, Intent intent) {
        setResult(resultCode, intent);
        doAfterTimeout(new Runnable() {
            public void run() {
                InvisibleActivityBase.this.finish();
            }
        });
    }

    private void doAfterTimeout(Runnable runnable) {
        this.mHandler.postDelayed(runnable, Math.max(MIN_SPINNER_MS - (System.currentTimeMillis() - this.mLastShownTime), 0));
    }
}
