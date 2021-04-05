package com.google.android.youtube.player.internal;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.core.view.ViewCompat;

/* renamed from: com.google.android.youtube.player.internal.n */
public final class C1132n extends FrameLayout {

    /* renamed from: a */
    private final ProgressBar f206a;

    /* renamed from: b */
    private final TextView f207b;

    public C1132n(Context context) {
        super(context, (AttributeSet) null, C1162z.m384c(context));
        C1131m mVar = new C1131m(context);
        setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        ProgressBar progressBar = new ProgressBar(context);
        this.f206a = progressBar;
        progressBar.setVisibility(8);
        addView(this.f206a, new FrameLayout.LayoutParams(-2, -2, 17));
        int i = (int) ((context.getResources().getDisplayMetrics().density * 10.0f) + 0.5f);
        TextView textView = new TextView(context);
        this.f207b = textView;
        textView.setTextAppearance(context, 16973894);
        this.f207b.setTextColor(-1);
        this.f207b.setVisibility(8);
        this.f207b.setPadding(i, i, i, i);
        this.f207b.setGravity(17);
        this.f207b.setText(mVar.f196a);
        addView(this.f207b, new FrameLayout.LayoutParams(-2, -2, 17));
    }

    /* renamed from: a */
    public final void mo16654a() {
        this.f206a.setVisibility(8);
        this.f207b.setVisibility(8);
    }

    /* renamed from: b */
    public final void mo16655b() {
        this.f206a.setVisibility(0);
        this.f207b.setVisibility(8);
    }

    /* renamed from: c */
    public final void mo16656c() {
        this.f206a.setVisibility(8);
        this.f207b.setVisibility(0);
    }

    /* access modifiers changed from: protected */
    public final void onMeasure(int i, int i2) {
        float f;
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        if (!(mode == 1073741824 && mode2 == 1073741824)) {
            if (mode == 1073741824 || (mode == Integer.MIN_VALUE && mode2 == 0)) {
                size2 = (int) (((float) size) / 1.777f);
            } else {
                if (mode2 == 1073741824 || (mode2 == Integer.MIN_VALUE && mode == 0)) {
                    f = (float) size2;
                } else if (mode == Integer.MIN_VALUE && mode2 == Integer.MIN_VALUE) {
                    f = (float) size2;
                    float f2 = ((float) size) / 1.777f;
                    if (f >= f2) {
                        size2 = (int) f2;
                    }
                } else {
                    size = 0;
                    size2 = 0;
                }
                size = (int) (f * 1.777f);
            }
        }
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(resolveSize(size, i), 1073741824), View.MeasureSpec.makeMeasureSpec(resolveSize(size2, i2), 1073741824));
    }
}
