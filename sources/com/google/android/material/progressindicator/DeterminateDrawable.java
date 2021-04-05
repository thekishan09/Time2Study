package com.google.android.material.progressindicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;

public final class DeterminateDrawable extends DrawableWithAnimatedVisibilityChange {
    private static final FloatPropertyCompat<DeterminateDrawable> INDICATOR_LENGTH_FRACTION = new FloatPropertyCompat<DeterminateDrawable>("indicatorFraction") {
        public float getValue(DeterminateDrawable drawable) {
            return drawable.getIndicatorFraction();
        }

        public void setValue(DeterminateDrawable drawable, float value) {
            drawable.setIndicatorFraction(value);
        }
    };
    private static final int MAX_DRAWABLE_LEVEL = 10000;
    private static final float SPRING_FORCE_STIFFNESS = 50.0f;
    private final DrawingDelegate drawingDelegate;
    private float indicatorFraction;
    private boolean skipAnimationOnLevelChange = false;
    private final SpringAnimation springAnimator;
    private final SpringForce springForce;

    public /* bridge */ /* synthetic */ void clearAnimationCallbacks() {
        super.clearAnimationCallbacks();
    }

    public /* bridge */ /* synthetic */ int getAlpha() {
        return super.getAlpha();
    }

    public /* bridge */ /* synthetic */ int getOpacity() {
        return super.getOpacity();
    }

    public /* bridge */ /* synthetic */ boolean hideNow() {
        return super.hideNow();
    }

    public /* bridge */ /* synthetic */ boolean isRunning() {
        return super.isRunning();
    }

    public /* bridge */ /* synthetic */ void registerAnimationCallback(Animatable2Compat.AnimationCallback animationCallback) {
        super.registerAnimationCallback(animationCallback);
    }

    public /* bridge */ /* synthetic */ void setAlpha(int i) {
        super.setAlpha(i);
    }

    public /* bridge */ /* synthetic */ void setColorFilter(ColorFilter colorFilter) {
        super.setColorFilter(colorFilter);
    }

    public /* bridge */ /* synthetic */ boolean setVisible(boolean z, boolean z2) {
        return super.setVisible(z, z2);
    }

    public /* bridge */ /* synthetic */ void start() {
        super.start();
    }

    public /* bridge */ /* synthetic */ void stop() {
        super.stop();
    }

    public /* bridge */ /* synthetic */ boolean unregisterAnimationCallback(Animatable2Compat.AnimationCallback animationCallback) {
        return super.unregisterAnimationCallback(animationCallback);
    }

    public DeterminateDrawable(Context context, ProgressIndicatorSpec spec, DrawingDelegate drawingDelegate2) {
        super(context, spec);
        this.drawingDelegate = drawingDelegate2;
        SpringForce springForce2 = new SpringForce();
        this.springForce = springForce2;
        springForce2.setDampingRatio(1.0f);
        this.springForce.setStiffness(50.0f);
        SpringAnimation springAnimation = new SpringAnimation(this, INDICATOR_LENGTH_FRACTION);
        this.springAnimator = springAnimation;
        springAnimation.setSpring(this.springForce);
        this.springAnimator.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
            public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
                DeterminateDrawable.this.setIndicatorFraction(value / 10000.0f);
            }
        });
        setGrowFraction(1.0f);
    }

    /* access modifiers changed from: package-private */
    public void setLevelByFraction(float fraction) {
        setLevel((int) (10000.0f * fraction));
    }

    public boolean setVisible(boolean visible, boolean restart, boolean animationDesired) {
        boolean changed = super.setVisible(visible, restart, animationDesired);
        float systemAnimatorDurationScale = this.animatorDurationScaleProvider.getSystemAnimatorDurationScale(this.context.getContentResolver());
        if (systemAnimatorDurationScale == 0.0f) {
            this.skipAnimationOnLevelChange = true;
        } else {
            this.skipAnimationOnLevelChange = false;
            this.springForce.setStiffness(50.0f / systemAnimatorDurationScale);
        }
        return changed;
    }

    public void jumpToCurrentState() {
        this.springAnimator.cancel();
        setIndicatorFraction(((float) getLevel()) / 10000.0f);
    }

    /* access modifiers changed from: protected */
    public boolean onLevelChange(int level) {
        if (this.skipAnimationOnLevelChange) {
            this.springAnimator.cancel();
            setIndicatorFraction(((float) level) / 10000.0f);
            return true;
        }
        this.springAnimator.setStartValue(getIndicatorFraction() * 10000.0f);
        this.springAnimator.animateToFinalPosition((float) level);
        return true;
    }

    public int getIntrinsicWidth() {
        return this.drawingDelegate.getPreferredWidth(this.spec);
    }

    public int getIntrinsicHeight() {
        return this.drawingDelegate.getPreferredHeight(this.spec);
    }

    public void draw(Canvas canvas) {
        Rect clipBounds = new Rect();
        if (!getBounds().isEmpty() && isVisible() && canvas.getClipBounds(clipBounds)) {
            canvas.save();
            this.drawingDelegate.adjustCanvas(canvas, this.spec, getGrowFraction());
            float displayedIndicatorWidth = ((float) this.spec.indicatorWidth) * getGrowFraction();
            float displayedRoundedCornerRadius = ((float) this.spec.indicatorCornerRadius) * getGrowFraction();
            float f = displayedIndicatorWidth;
            float f2 = displayedRoundedCornerRadius;
            this.drawingDelegate.fillTrackWithColor(canvas, this.paint, this.spec.trackColor, 0.0f, 1.0f, f, f2);
            this.drawingDelegate.fillTrackWithColor(canvas, this.paint, this.combinedIndicatorColorArray[0], 0.0f, getIndicatorFraction(), f, f2);
            canvas.restore();
        }
    }

    /* access modifiers changed from: private */
    public float getIndicatorFraction() {
        return this.indicatorFraction;
    }

    /* access modifiers changed from: private */
    public void setIndicatorFraction(float indicatorFraction2) {
        this.indicatorFraction = indicatorFraction2;
        invalidateSelf();
    }

    public DrawingDelegate getDrawingDelegate() {
        return this.drawingDelegate;
    }
}
