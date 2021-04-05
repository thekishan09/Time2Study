package com.google.android.material.progressindicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.util.Property;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.ArgbEvaluatorCompat;
import com.google.android.material.math.MathUtils;

public final class CircularIndeterminateAnimatorDelegate extends IndeterminateAnimatorDelegate<AnimatorSet> {
    private static final int COLOR_FADING_DELAY = 1000;
    private static final int COLOR_FADING_DURATION = 333;
    private static final Property<CircularIndeterminateAnimatorDelegate, Integer> DISPLAYED_INDICATOR_COLOR = new Property<CircularIndeterminateAnimatorDelegate, Integer>(Integer.class, "displayedIndicatorColor") {
        public Integer get(CircularIndeterminateAnimatorDelegate delegate) {
            return Integer.valueOf(delegate.getDisplayedIndicatorColor());
        }

        public void set(CircularIndeterminateAnimatorDelegate delegate, Integer value) {
            delegate.setDisplayedIndicatorColor(value.intValue());
        }
    };
    private static final int DURATION_PER_COLOR_IN_MS = 1333;
    private static final float INDICATOR_DELTA_DEGREES = 250.0f;
    private static final Property<CircularIndeterminateAnimatorDelegate, Float> INDICATOR_HEAD_CHANGE_FRACTION = new Property<CircularIndeterminateAnimatorDelegate, Float>(Float.class, "indicatorHeadChangeFraction") {
        public Float get(CircularIndeterminateAnimatorDelegate delegate) {
            return Float.valueOf(delegate.getIndicatorHeadChangeFraction());
        }

        public void set(CircularIndeterminateAnimatorDelegate delegate, Float value) {
            delegate.setIndicatorHeadChangeFraction(value.floatValue());
        }
    };
    private static final Property<CircularIndeterminateAnimatorDelegate, Float> INDICATOR_IN_CYCLE_OFFSET = new Property<CircularIndeterminateAnimatorDelegate, Float>(Float.class, "indicatorInCycleOffset") {
        public Float get(CircularIndeterminateAnimatorDelegate delegate) {
            return Float.valueOf(delegate.getIndicatorInCycleOffset());
        }

        public void set(CircularIndeterminateAnimatorDelegate delegate, Float value) {
            delegate.setIndicatorInCycleOffset(value.floatValue());
        }
    };
    private static final float INDICATOR_MAX_DEGREES = 270.0f;
    private static final float INDICATOR_MIN_DEGREES = 20.0f;
    private static final float INDICATOR_OFFSET_PER_COLOR_DEGREES = 360.0f;
    private static final Property<CircularIndeterminateAnimatorDelegate, Float> INDICATOR_TAIL_CHANGE_FRACTION = new Property<CircularIndeterminateAnimatorDelegate, Float>(Float.class, "indicatorTailChangeFraction") {
        public Float get(CircularIndeterminateAnimatorDelegate delegate) {
            return Float.valueOf(delegate.getIndicatorTailChangeFraction());
        }

        public void set(CircularIndeterminateAnimatorDelegate delegate, Float value) {
            delegate.setIndicatorTailChangeFraction(value.floatValue());
        }
    };
    Animatable2Compat.AnimationCallback animatorCompleteCallback = null;
    boolean animatorCompleteEndRequested = false;
    private final AnimatorSet animatorSet;
    private ObjectAnimator colorFadingAnimator;
    private int displayedIndicatorColor;
    /* access modifiers changed from: private */
    public final ObjectAnimator indicatorCollapsingAnimator;
    private int indicatorColorIndex;
    private float indicatorHeadChangeFraction;
    private float indicatorInCycleOffset;
    private float indicatorStartOffset;
    private float indicatorTailChangeFraction;

    public CircularIndeterminateAnimatorDelegate() {
        super(1);
        ObjectAnimator constantlyRotateAnimator = ObjectAnimator.ofFloat(this, INDICATOR_IN_CYCLE_OFFSET, new float[]{0.0f, INDICATOR_OFFSET_PER_COLOR_DEGREES});
        constantlyRotateAnimator.setDuration(1333);
        constantlyRotateAnimator.setInterpolator((TimeInterpolator) null);
        ObjectAnimator expandAnimator = ObjectAnimator.ofFloat(this, INDICATOR_HEAD_CHANGE_FRACTION, new float[]{0.0f, 1.0f});
        expandAnimator.setDuration(666);
        expandAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        expandAnimator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (CircularIndeterminateAnimatorDelegate.this.animatorCompleteEndRequested) {
                    CircularIndeterminateAnimatorDelegate.this.indicatorCollapsingAnimator.setFloatValues(new float[]{0.0f, 1.08f});
                }
            }
        });
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, INDICATOR_TAIL_CHANGE_FRACTION, new float[]{0.0f, 1.0f});
        this.indicatorCollapsingAnimator = ofFloat;
        ofFloat.setDuration(666);
        this.indicatorCollapsingAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.animatorSet = animatorSet2;
        animatorSet2.playSequentially(new Animator[]{expandAnimator, this.indicatorCollapsingAnimator});
        this.animatorSet.playTogether(new Animator[]{constantlyRotateAnimator});
        this.animatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (CircularIndeterminateAnimatorDelegate.this.animatorCompleteEndRequested && CircularIndeterminateAnimatorDelegate.this.segmentPositions[0] == CircularIndeterminateAnimatorDelegate.this.segmentPositions[1]) {
                    CircularIndeterminateAnimatorDelegate.this.animatorCompleteCallback.onAnimationEnd(CircularIndeterminateAnimatorDelegate.this.drawable);
                    CircularIndeterminateAnimatorDelegate.this.animatorCompleteEndRequested = false;
                } else if (CircularIndeterminateAnimatorDelegate.this.drawable.isVisible()) {
                    CircularIndeterminateAnimatorDelegate.this.resetPropertiesForNextCycle();
                    CircularIndeterminateAnimatorDelegate.this.startAnimator();
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void registerDrawable(IndeterminateDrawable drawable) {
        super.registerDrawable(drawable);
        ObjectAnimator ofObject = ObjectAnimator.ofObject(this, DISPLAYED_INDICATOR_COLOR, new ArgbEvaluatorCompat(), new Integer[]{Integer.valueOf(drawable.combinedIndicatorColorArray[this.indicatorColorIndex]), Integer.valueOf(drawable.combinedIndicatorColorArray[getNextIndicatorColorIndex()])});
        this.colorFadingAnimator = ofObject;
        ofObject.setDuration(333);
        this.colorFadingAnimator.setStartDelay(1000);
        this.colorFadingAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        this.animatorSet.playTogether(new Animator[]{this.colorFadingAnimator});
    }

    /* access modifiers changed from: package-private */
    public void startAnimator() {
        this.animatorSet.start();
    }

    /* access modifiers changed from: package-private */
    public void cancelAnimatorImmediately() {
        this.animatorSet.cancel();
    }

    /* access modifiers changed from: package-private */
    public void requestCancelAnimatorAfterCurrentCycle() {
        if (!this.animatorCompleteEndRequested) {
            if (this.drawable.isVisible()) {
                this.animatorCompleteEndRequested = true;
            } else {
                cancelAnimatorImmediately();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void resetPropertiesForNewStart() {
        setIndicatorHeadChangeFraction(0.0f);
        setIndicatorTailChangeFraction(0.0f);
        setIndicatorStartOffset(0.0f);
        this.indicatorCollapsingAnimator.setFloatValues(new float[]{0.0f, 1.0f});
        resetSegmentColors();
    }

    /* access modifiers changed from: package-private */
    public void resetPropertiesForNextCycle() {
        setIndicatorHeadChangeFraction(0.0f);
        setIndicatorTailChangeFraction(0.0f);
        setIndicatorStartOffset(MathUtils.floorMod(getIndicatorStartOffset() + INDICATOR_OFFSET_PER_COLOR_DEGREES + INDICATOR_DELTA_DEGREES, 360));
        shiftSegmentColors();
    }

    public void invalidateSpecValues() {
        resetSegmentColors();
    }

    public void registerAnimatorsCompleteCallback(Animatable2Compat.AnimationCallback callback) {
        this.animatorCompleteCallback = callback;
    }

    public void unregisterAnimatorsCompleteCallback() {
        this.animatorCompleteCallback = null;
    }

    private int getNextIndicatorColorIndex() {
        return (this.indicatorColorIndex + 1) % this.drawable.combinedIndicatorColorArray.length;
    }

    private void updateSegmentPositions() {
        this.segmentPositions[0] = (((getIndicatorStartOffset() + getIndicatorInCycleOffset()) - INDICATOR_MIN_DEGREES) + (getIndicatorTailChangeFraction() * INDICATOR_DELTA_DEGREES)) / INDICATOR_OFFSET_PER_COLOR_DEGREES;
        this.segmentPositions[1] = ((getIndicatorStartOffset() + getIndicatorInCycleOffset()) + (getIndicatorHeadChangeFraction() * INDICATOR_DELTA_DEGREES)) / INDICATOR_OFFSET_PER_COLOR_DEGREES;
    }

    private void shiftSegmentColors() {
        this.indicatorColorIndex = getNextIndicatorColorIndex();
        this.colorFadingAnimator.setIntValues(new int[]{this.drawable.combinedIndicatorColorArray[this.indicatorColorIndex], this.drawable.combinedIndicatorColorArray[getNextIndicatorColorIndex()]});
        setDisplayedIndicatorColor(this.drawable.combinedIndicatorColorArray[this.indicatorColorIndex]);
    }

    private void resetSegmentColors() {
        this.indicatorColorIndex = 0;
        this.colorFadingAnimator.setIntValues(new int[]{this.drawable.combinedIndicatorColorArray[this.indicatorColorIndex], this.drawable.combinedIndicatorColorArray[getNextIndicatorColorIndex()]});
        setDisplayedIndicatorColor(this.drawable.combinedIndicatorColorArray[this.indicatorColorIndex]);
    }

    /* access modifiers changed from: private */
    public int getDisplayedIndicatorColor() {
        return this.displayedIndicatorColor;
    }

    /* access modifiers changed from: private */
    public void setDisplayedIndicatorColor(int displayedIndicatorColor2) {
        this.displayedIndicatorColor = displayedIndicatorColor2;
        this.segmentColors[0] = displayedIndicatorColor2;
        this.drawable.invalidateSelf();
    }

    private float getIndicatorStartOffset() {
        return this.indicatorStartOffset;
    }

    /* access modifiers changed from: package-private */
    public void setIndicatorStartOffset(float indicatorStartOffset2) {
        this.indicatorStartOffset = indicatorStartOffset2;
        updateSegmentPositions();
        this.drawable.invalidateSelf();
    }

    /* access modifiers changed from: private */
    public float getIndicatorInCycleOffset() {
        return this.indicatorInCycleOffset;
    }

    /* access modifiers changed from: package-private */
    public void setIndicatorInCycleOffset(float indicatorInCycleOffset2) {
        this.indicatorInCycleOffset = indicatorInCycleOffset2;
        updateSegmentPositions();
        this.drawable.invalidateSelf();
    }

    /* access modifiers changed from: private */
    public float getIndicatorHeadChangeFraction() {
        return this.indicatorHeadChangeFraction;
    }

    /* access modifiers changed from: package-private */
    public void setIndicatorHeadChangeFraction(float indicatorHeadChangeFraction2) {
        this.indicatorHeadChangeFraction = indicatorHeadChangeFraction2;
        updateSegmentPositions();
        this.drawable.invalidateSelf();
    }

    /* access modifiers changed from: private */
    public float getIndicatorTailChangeFraction() {
        return this.indicatorTailChangeFraction;
    }

    /* access modifiers changed from: package-private */
    public void setIndicatorTailChangeFraction(float indicatorTailChangeFraction2) {
        this.indicatorTailChangeFraction = indicatorTailChangeFraction2;
        updateSegmentPositions();
        this.drawable.invalidateSelf();
    }
}
