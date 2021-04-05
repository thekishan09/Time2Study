package com.google.android.material.progressindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.google.android.material.C0822R;
import com.google.android.material.color.MaterialColors;

public final class ProgressIndicatorSpec {
    public int circularInset;
    public int circularRadius;
    public int growMode;
    public int[] indicatorColors;
    public int indicatorCornerRadius;
    public int indicatorType;
    public int indicatorWidth;
    public boolean inverse;
    public boolean linearSeamless;
    public int trackColor;

    public void loadFromAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        loadFromAttributes(context, attrs, defStyleAttr, ProgressIndicator.DEF_STYLE_RES);
    }

    public void loadFromAttributes(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = context.obtainStyledAttributes(attrs, C0822R.styleable.ProgressIndicator, defStyleAttr, defStyleRes);
        this.indicatorType = a.getInt(C0822R.styleable.ProgressIndicator_indicatorType, 0);
        this.indicatorWidth = getDimensionPixelSize(context, a, C0822R.styleable.ProgressIndicator_indicatorWidth, C0822R.dimen.mtrl_progress_indicator_width);
        this.circularInset = getDimensionPixelSize(context, a, C0822R.styleable.ProgressIndicator_circularInset, C0822R.dimen.mtrl_progress_circular_inset);
        this.circularRadius = getDimensionPixelSize(context, a, C0822R.styleable.ProgressIndicator_circularRadius, C0822R.dimen.mtrl_progress_circular_radius);
        this.inverse = a.getBoolean(C0822R.styleable.ProgressIndicator_inverse, false);
        this.growMode = a.getInt(C0822R.styleable.ProgressIndicator_growMode, 0);
        loadIndicatorColors(context, a);
        loadTrackColor(context, a);
        boolean z = true;
        if (!a.getBoolean(C0822R.styleable.ProgressIndicator_linearSeamless, true) || this.indicatorType != 0 || this.indicatorColors.length < 3) {
            z = false;
        }
        this.linearSeamless = z;
        this.indicatorCornerRadius = Math.min(a.getDimensionPixelSize(C0822R.styleable.ProgressIndicator_indicatorCornerRadius, 0), this.indicatorWidth / 2);
        a.recycle();
        validate();
    }

    public void validate() {
        if (this.indicatorType == 1 && this.circularRadius < this.indicatorWidth / 2) {
            throw new IllegalArgumentException("The circularRadius cannot be less than half of the indicatorWidth.");
        } else if (this.linearSeamless && this.indicatorCornerRadius > 0) {
            throw new IllegalArgumentException("Rounded corners are not supported in linear seamless mode.");
        }
    }

    private static int getDimensionPixelSize(Context context, TypedArray typedArray, int resId, int defaultResId) {
        return typedArray.getDimensionPixelSize(resId, context.getResources().getDimensionPixelSize(defaultResId));
    }

    private void loadIndicatorColors(Context context, TypedArray typedArray) {
        int i;
        if (typedArray.hasValue(C0822R.styleable.ProgressIndicator_indicatorColors)) {
            this.indicatorColors = context.getResources().getIntArray(typedArray.getResourceId(C0822R.styleable.ProgressIndicator_indicatorColors, -1));
            if (typedArray.hasValue(C0822R.styleable.ProgressIndicator_indicatorColor)) {
                throw new IllegalArgumentException("Attributes indicatorColors and indicatorColor cannot be used at the same time.");
            } else if (this.indicatorColors.length == 0) {
                throw new IllegalArgumentException("indicatorColors cannot be empty when indicatorColor is not used.");
            }
        } else {
            int[] iArr = new int[1];
            if (typedArray.hasValue(C0822R.styleable.ProgressIndicator_indicatorColor)) {
                i = typedArray.getColor(C0822R.styleable.ProgressIndicator_indicatorColor, -1);
            } else {
                i = MaterialColors.getColor(context, C0822R.attr.colorPrimary, -1);
            }
            iArr[0] = i;
            this.indicatorColors = iArr;
        }
    }

    private void loadTrackColor(Context context, TypedArray typedArray) {
        if (typedArray.hasValue(C0822R.styleable.ProgressIndicator_trackColor)) {
            this.trackColor = typedArray.getColor(C0822R.styleable.ProgressIndicator_trackColor, -1);
            return;
        }
        this.trackColor = this.indicatorColors[0];
        TypedArray disabledAlphaArray = context.getTheme().obtainStyledAttributes(new int[]{16842803});
        float defaultOpacity = disabledAlphaArray.getFloat(0, 0.2f);
        disabledAlphaArray.recycle();
        this.trackColor = MaterialColors.compositeARGBWithAlpha(this.trackColor, (int) (255.0f * defaultOpacity));
    }
}
