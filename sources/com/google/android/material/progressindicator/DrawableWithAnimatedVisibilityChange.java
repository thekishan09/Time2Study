package com.google.android.material.progressindicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Property;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.color.MaterialColors;
import java.util.ArrayList;
import java.util.List;

abstract class DrawableWithAnimatedVisibilityChange extends Drawable implements Animatable2Compat {
    private static final boolean DEFAULT_DRAWABLE_RESTART = false;
    private static final int GROW_DURATION = 500;
    private static final Property<DrawableWithAnimatedVisibilityChange, Float> GROW_FRACTION = new Property<DrawableWithAnimatedVisibilityChange, Float>(Float.class, "growFraction") {
        public Float get(DrawableWithAnimatedVisibilityChange drawable) {
            return Float.valueOf(drawable.getGrowFraction());
        }

        public void set(DrawableWithAnimatedVisibilityChange drawable, Float value) {
            drawable.setGrowFraction(value.floatValue());
        }
    };
    private List<Animatable2Compat.AnimationCallback> animationCallbacks;
    AnimatorDurationScaleProvider animatorDurationScaleProvider;
    int[] combinedIndicatorColorArray;
    int combinedTrackColor;
    final Context context;
    private float growFraction;
    private ValueAnimator hideAnimator;
    final Paint paint = new Paint();
    private ValueAnimator showAnimator;
    final ProgressIndicatorSpec spec;
    private int totalAlpha;

    DrawableWithAnimatedVisibilityChange(Context context2, ProgressIndicatorSpec spec2) {
        this.context = context2;
        this.spec = spec2;
        this.animatorDurationScaleProvider = new AnimatorDurationScaleProvider();
        setAlpha(255);
        initializeShowAnimator();
        initializeHideAnimator();
    }

    private void initializeShowAnimator() {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, GROW_FRACTION, new float[]{0.0f, 1.0f});
        this.showAnimator = ofFloat;
        ofFloat.setDuration(500);
        this.showAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        setShowAnimator(this.showAnimator);
    }

    private void initializeHideAnimator() {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, GROW_FRACTION, new float[]{1.0f, 0.0f});
        this.hideAnimator = ofFloat;
        ofFloat.setDuration(500);
        this.hideAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        setHideAnimator(this.hideAnimator);
    }

    public void registerAnimationCallback(Animatable2Compat.AnimationCallback callback) {
        if (this.animationCallbacks == null) {
            this.animationCallbacks = new ArrayList();
        }
        if (!this.animationCallbacks.contains(callback)) {
            this.animationCallbacks.add(callback);
        }
    }

    public boolean unregisterAnimationCallback(Animatable2Compat.AnimationCallback callback) {
        List<Animatable2Compat.AnimationCallback> list = this.animationCallbacks;
        if (list == null || !list.contains(callback)) {
            return false;
        }
        this.animationCallbacks.remove(callback);
        if (!this.animationCallbacks.isEmpty()) {
            return true;
        }
        this.animationCallbacks = null;
        return true;
    }

    public void clearAnimationCallbacks() {
        this.animationCallbacks.clear();
        this.animationCallbacks = null;
    }

    /* access modifiers changed from: private */
    public void dispatchAnimationStart() {
        List<Animatable2Compat.AnimationCallback> list = this.animationCallbacks;
        if (list != null) {
            for (Animatable2Compat.AnimationCallback callback : list) {
                callback.onAnimationStart(this);
            }
        }
    }

    /* access modifiers changed from: private */
    public void dispatchAnimationEnd() {
        List<Animatable2Compat.AnimationCallback> list = this.animationCallbacks;
        if (list != null) {
            for (Animatable2Compat.AnimationCallback callback : list) {
                callback.onAnimationEnd(this);
            }
        }
    }

    public void start() {
        setVisible(true, true);
    }

    public void stop() {
        setVisible(false, true);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x000a, code lost:
        r0 = r1.hideAnimator;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isRunning() {
        /*
            r1 = this;
            android.animation.ValueAnimator r0 = r1.showAnimator
            if (r0 == 0) goto L_0x000a
            boolean r0 = r0.isRunning()
            if (r0 != 0) goto L_0x0014
        L_0x000a:
            android.animation.ValueAnimator r0 = r1.hideAnimator
            if (r0 == 0) goto L_0x0016
            boolean r0 = r0.isRunning()
            if (r0 == 0) goto L_0x0016
        L_0x0014:
            r0 = 1
            goto L_0x0017
        L_0x0016:
            r0 = 0
        L_0x0017:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.progressindicator.DrawableWithAnimatedVisibilityChange.isRunning():boolean");
    }

    public boolean hideNow() {
        return setVisible(false, false, false);
    }

    public boolean setVisible(boolean visible, boolean restart) {
        return setVisible(visible, restart, this.animatorDurationScaleProvider.getSystemAnimatorDurationScale(this.context.getContentResolver()) > 0.0f);
    }

    public boolean setVisible(boolean visible, boolean restart, boolean animationDesired) {
        boolean z = false;
        if (!isVisible() && !visible) {
            return false;
        }
        if (animationDesired) {
            if ((visible ? this.showAnimator : this.hideAnimator).isRunning()) {
                return false;
            }
        }
        ValueAnimator animationInAction = visible ? this.showAnimator : this.hideAnimator;
        boolean changed = !visible || super.setVisible(visible, false);
        if (this.spec.growMode != 0) {
            z = true;
        }
        if (!animationDesired || !z) {
            animationInAction.end();
            return changed;
        }
        if (restart || Build.VERSION.SDK_INT < 19 || !animationInAction.isPaused()) {
            animationInAction.start();
        } else {
            animationInAction.resume();
        }
        return changed;
    }

    /* access modifiers changed from: package-private */
    public void recalculateColors() {
        this.combinedTrackColor = MaterialColors.compositeARGBWithAlpha(this.spec.trackColor, getAlpha());
        this.combinedIndicatorColorArray = (int[]) this.spec.indicatorColors.clone();
        int i = 0;
        while (true) {
            int[] iArr = this.combinedIndicatorColorArray;
            if (i < iArr.length) {
                iArr[i] = MaterialColors.compositeARGBWithAlpha(iArr[i], getAlpha());
                i++;
            } else {
                return;
            }
        }
    }

    public void setAlpha(int alpha) {
        this.totalAlpha = alpha;
        recalculateColors();
        invalidateSelf();
    }

    public int getAlpha() {
        return this.totalAlpha;
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.paint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    public int getOpacity() {
        return -3;
    }

    private void setShowAnimator(ValueAnimator showAnimator2) {
        ValueAnimator valueAnimator = this.showAnimator;
        if (valueAnimator == null || !valueAnimator.isRunning()) {
            this.showAnimator = showAnimator2;
            showAnimator2.addListener(new AnimatorListenerAdapter() {
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    DrawableWithAnimatedVisibilityChange.this.dispatchAnimationStart();
                }
            });
            return;
        }
        throw new IllegalArgumentException("Cannot set showAnimator while the current showAnimator is running.");
    }

    /* access modifiers changed from: package-private */
    public ValueAnimator getHideAnimator() {
        return this.hideAnimator;
    }

    private void setHideAnimator(ValueAnimator hideAnimator2) {
        ValueAnimator valueAnimator = this.hideAnimator;
        if (valueAnimator == null || !valueAnimator.isRunning()) {
            this.hideAnimator = hideAnimator2;
            hideAnimator2.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    boolean unused = DrawableWithAnimatedVisibilityChange.super.setVisible(false, false);
                    DrawableWithAnimatedVisibilityChange.this.dispatchAnimationEnd();
                }
            });
            return;
        }
        throw new IllegalArgumentException("Cannot set hideAnimator while the current hideAnimator is running.");
    }

    /* access modifiers changed from: package-private */
    public float getGrowFraction() {
        return this.growFraction;
    }

    /* access modifiers changed from: package-private */
    public void setGrowFraction(float growFraction2) {
        if (this.spec.growMode == 0) {
            growFraction2 = 1.0f;
        }
        if (this.growFraction != growFraction2) {
            this.growFraction = growFraction2;
            invalidateSelf();
        }
    }
}
