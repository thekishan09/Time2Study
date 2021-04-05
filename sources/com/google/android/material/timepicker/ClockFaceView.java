package com.google.android.material.timepicker;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.C0822R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.timepicker.ClockHandView;

public class ClockFaceView extends RadialViewGroup implements ClockHandView.OnRotateListener {
    private static final float EPSILON = 0.001f;
    /* access modifiers changed from: private */
    public final int clockHandPadding;
    /* access modifiers changed from: private */
    public final ClockHandView clockHandView;
    private float currentHandRotation;
    private final int[] gradientColors;
    private final float[] gradientPositions;
    private final RectF scratch;
    private final int textColor;
    /* access modifiers changed from: private */
    public final SparseArray<TextView> textViewPool;
    private final Rect textViewRect;
    private final AccessibilityDelegateCompat valueAccessibilityDelegate;
    private String[] values;

    public ClockFaceView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ClockFaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockFaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.textViewRect = new Rect();
        this.scratch = new RectF();
        this.textViewPool = new SparseArray<>();
        this.gradientPositions = new float[]{0.0f, 0.9f, 1.0f};
        TypedArray a = context.obtainStyledAttributes(attrs, C0822R.styleable.ClockFaceView, defStyleAttr, 0);
        Resources res = getResources();
        this.textColor = a.getColor(C0822R.styleable.ClockFaceView_valueTextColor, ViewCompat.MEASURED_STATE_MASK);
        LayoutInflater.from(context).inflate(C0822R.layout.material_clockface_view, this, true);
        this.clockHandView = (ClockHandView) findViewById(C0822R.C0825id.material_clock_hand);
        this.clockHandPadding = res.getDimensionPixelSize(C0822R.dimen.material_clock_hand_padding);
        int colorOnSurface = MaterialColors.getColor(this, C0822R.attr.colorOnSurface);
        int colorOnPrimary = MaterialColors.getColor(this, C0822R.attr.colorOnPrimary);
        this.gradientColors = new int[]{colorOnPrimary, colorOnPrimary, colorOnSurface};
        this.clockHandView.addOnRotateListener(this);
        setBackgroundColor(AppCompatResources.getColorStateList(context, C0822R.C0823color.material_timepicker_clockface).getDefaultColor());
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                if (!ClockFaceView.this.isShown()) {
                    return true;
                }
                ClockFaceView.this.getViewTreeObserver().removeOnPreDrawListener(this);
                ClockFaceView.this.setRadius(((ClockFaceView.this.getHeight() / 2) - ClockFaceView.this.clockHandView.getSelectorRadius()) - ClockFaceView.this.clockHandPadding);
                return true;
            }
        });
        setFocusable(true);
        a.recycle();
        this.valueAccessibilityDelegate = new AccessibilityDelegateCompat() {
            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
                super.onInitializeAccessibilityNodeInfo(host, info);
                int index = ((Integer) host.getTag(C0822R.C0825id.material_value_index)).intValue();
                if (index > 0) {
                    info.setTraversalAfter((View) ClockFaceView.this.textViewPool.get(index - 1));
                }
                info.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(0, 1, index, 1, false, host.isSelected()));
            }
        };
    }

    public void setValues(String[] values2, int contentDescription) {
        this.values = values2;
        updateTextViews(contentDescription);
    }

    /* JADX WARNING: type inference failed for: r4v7, types: [android.view.View] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void updateTextViews(int r8) {
        /*
            r7 = this;
            android.content.Context r0 = r7.getContext()
            android.view.LayoutInflater r0 = android.view.LayoutInflater.from(r0)
            r1 = 0
        L_0x0009:
            java.lang.String[] r2 = r7.values
            int r2 = r2.length
            android.util.SparseArray<android.widget.TextView> r3 = r7.textViewPool
            int r3 = r3.size()
            int r2 = java.lang.Math.max(r2, r3)
            if (r1 >= r2) goto L_0x0073
            android.util.SparseArray<android.widget.TextView> r2 = r7.textViewPool
            java.lang.Object r2 = r2.get(r1)
            android.widget.TextView r2 = (android.widget.TextView) r2
            java.lang.String[] r3 = r7.values
            int r3 = r3.length
            if (r1 < r3) goto L_0x002e
            r7.removeView(r2)
            android.util.SparseArray<android.widget.TextView> r3 = r7.textViewPool
            r3.remove(r1)
            goto L_0x0070
        L_0x002e:
            r3 = 0
            if (r2 != 0) goto L_0x0042
            int r4 = com.google.android.material.C0822R.layout.material_clockface_textview
            android.view.View r4 = r0.inflate(r4, r7, r3)
            r2 = r4
            android.widget.TextView r2 = (android.widget.TextView) r2
            r7.addView(r2)
            android.util.SparseArray<android.widget.TextView> r4 = r7.textViewPool
            r4.put(r1, r2)
        L_0x0042:
            java.lang.String[] r4 = r7.values
            r4 = r4[r1]
            r2.setText(r4)
            int r4 = com.google.android.material.C0822R.C0825id.material_value_index
            java.lang.Integer r5 = java.lang.Integer.valueOf(r1)
            r2.setTag(r4, r5)
            androidx.core.view.AccessibilityDelegateCompat r4 = r7.valueAccessibilityDelegate
            androidx.core.view.ViewCompat.setAccessibilityDelegate(r2, r4)
            int r4 = r7.textColor
            r2.setTextColor(r4)
            android.content.res.Resources r4 = r7.getResources()
            r5 = 1
            java.lang.Object[] r5 = new java.lang.Object[r5]
            java.lang.String[] r6 = r7.values
            r6 = r6[r1]
            r5[r3] = r6
            java.lang.String r3 = r4.getString(r8, r5)
            r2.setContentDescription(r3)
        L_0x0070:
            int r1 = r1 + 1
            goto L_0x0009
        L_0x0073:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.timepicker.ClockFaceView.updateTextViews(int):void");
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        AccessibilityNodeInfoCompat.wrap(info).setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(1, this.values.length, false, 1));
    }

    public void setRadius(int radius) {
        if (radius != getRadius()) {
            super.setRadius(radius);
            this.clockHandView.setCircleRadius(getRadius());
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        findIntersectingTextView();
    }

    public void setHandRotation(float rotation) {
        this.clockHandView.setHandRotation(rotation);
        findIntersectingTextView();
    }

    private void findIntersectingTextView() {
        RectF selectorBox = this.clockHandView.getCurrentSelectorBox();
        for (int i = 0; i < this.textViewPool.size(); i++) {
            TextView tv = this.textViewPool.get(i);
            tv.getDrawingRect(this.textViewRect);
            this.textViewRect.offset(tv.getPaddingLeft(), getPaddingTop());
            offsetDescendantRectToMyCoords(tv, this.textViewRect);
            this.scratch.set(this.textViewRect);
            tv.getPaint().setShader(getGradientForTextView(selectorBox, this.scratch));
            tv.invalidate();
        }
    }

    private RadialGradient getGradientForTextView(RectF selectorBox, RectF tvBox) {
        if (!RectF.intersects(selectorBox, tvBox)) {
            return null;
        }
        return new RadialGradient(selectorBox.centerX() - this.scratch.left, selectorBox.centerY() - this.scratch.top, 0.5f * selectorBox.width(), this.gradientColors, this.gradientPositions, Shader.TileMode.CLAMP);
    }

    public void onRotate(float rotation, boolean animating) {
        if (Math.abs(this.currentHandRotation - rotation) > EPSILON) {
            this.currentHandRotation = rotation;
            findIntersectingTextView();
        }
    }
}
