package com.google.android.material.progressindicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;

public final class IndeterminateDrawable extends DrawableWithAnimatedVisibilityChange {
    /* access modifiers changed from: private */
    public IndeterminateAnimatorDelegate<AnimatorSet> animatorDelegate;
    private final DrawingDelegate drawingDelegate;

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

    public IndeterminateDrawable(Context context, ProgressIndicatorSpec spec, DrawingDelegate drawingDelegate2, IndeterminateAnimatorDelegate<AnimatorSet> animatorDelegate2) {
        super(context, spec);
        this.drawingDelegate = drawingDelegate2;
        setAnimatorDelegate(animatorDelegate2);
    }

    public boolean setVisible(boolean visible, boolean restart, boolean animationDesired) {
        boolean changed = super.setVisible(visible, restart, animationDesired);
        if (!isRunning()) {
            this.animatorDelegate.cancelAnimatorImmediately();
            this.animatorDelegate.resetPropertiesForNewStart();
        }
        if (visible && animationDesired) {
            this.animatorDelegate.startAnimator();
        }
        return changed;
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
            this.drawingDelegate.fillTrackWithColor(canvas, this.paint, this.combinedTrackColor, 0.0f, 1.0f, displayedIndicatorWidth, displayedRoundedCornerRadius);
            for (int segmentIndex = 0; segmentIndex < this.animatorDelegate.segmentColors.length; segmentIndex++) {
                this.drawingDelegate.fillTrackWithColor(canvas, this.paint, this.animatorDelegate.segmentColors[segmentIndex], this.animatorDelegate.segmentPositions[segmentIndex * 2], this.animatorDelegate.segmentPositions[(segmentIndex * 2) + 1], displayedIndicatorWidth, displayedRoundedCornerRadius);
            }
        }
    }

    public IndeterminateAnimatorDelegate<AnimatorSet> getAnimatorDelegate() {
        return this.animatorDelegate;
    }

    public void setAnimatorDelegate(IndeterminateAnimatorDelegate<AnimatorSet> animatorDelegate2) {
        this.animatorDelegate = animatorDelegate2;
        animatorDelegate2.registerDrawable(this);
        getHideAnimator().addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                IndeterminateDrawable.this.animatorDelegate.cancelAnimatorImmediately();
                IndeterminateDrawable.this.animatorDelegate.resetPropertiesForNewStart();
            }
        });
        setGrowFraction(1.0f);
    }

    public DrawingDelegate getDrawingDelegate() {
        return this.drawingDelegate;
    }
}
