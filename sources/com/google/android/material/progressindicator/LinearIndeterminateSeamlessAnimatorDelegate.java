package com.google.android.material.progressindicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.Property;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.math.MathUtils;

public final class LinearIndeterminateSeamlessAnimatorDelegate extends IndeterminateAnimatorDelegate<AnimatorSet> {
    private static final int DURATION_PER_COLOR = 667;
    private static final Property<LinearIndeterminateSeamlessAnimatorDelegate, Float> LINE_CONNECT_POINT_1_FRACTION = new Property<LinearIndeterminateSeamlessAnimatorDelegate, Float>(Float.class, "lineConnectPoint1Fraction") {
        public Float get(LinearIndeterminateSeamlessAnimatorDelegate drawable) {
            return Float.valueOf(drawable.getLineConnectPoint1Fraction());
        }

        public void set(LinearIndeterminateSeamlessAnimatorDelegate drawable, Float value) {
            drawable.setLineConnectPoint1Fraction(value.floatValue());
        }
    };
    private static final Property<LinearIndeterminateSeamlessAnimatorDelegate, Float> LINE_CONNECT_POINT_2_FRACTION = new Property<LinearIndeterminateSeamlessAnimatorDelegate, Float>(Float.class, "lineConnectPoint2Fraction") {
        public Float get(LinearIndeterminateSeamlessAnimatorDelegate drawable) {
            return Float.valueOf(drawable.getLineConnectPoint2Fraction());
        }

        public void set(LinearIndeterminateSeamlessAnimatorDelegate drawable, Float value) {
            drawable.setLineConnectPoint2Fraction(value.floatValue());
        }
    };
    private static final int NEXT_COLOR_DELAY = 333;
    Animatable2Compat.AnimationCallback animatorCompleteCallback = null;
    private final AnimatorSet animatorSet;
    private float lineConnectPoint1Fraction;
    private float lineConnectPoint2Fraction;
    private int referenceSegmentColorIndex;

    public LinearIndeterminateSeamlessAnimatorDelegate() {
        super(3);
        ObjectAnimator connectPoint1Animator = ObjectAnimator.ofFloat(this, LINE_CONNECT_POINT_1_FRACTION, new float[]{0.0f, 1.0f});
        connectPoint1Animator.setDuration(667);
        connectPoint1Animator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        connectPoint1Animator.setRepeatCount(-1);
        connectPoint1Animator.setRepeatMode(1);
        connectPoint1Animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                if (LinearIndeterminateSeamlessAnimatorDelegate.this.getLineConnectPoint2Fraction() > 0.0f && LinearIndeterminateSeamlessAnimatorDelegate.this.getLineConnectPoint2Fraction() < 1.0f) {
                    LinearIndeterminateSeamlessAnimatorDelegate.this.shiftSegmentColors();
                }
            }
        });
        ObjectAnimator connectPoint2StayAtZeroAnimator = ObjectAnimator.ofFloat(this, LINE_CONNECT_POINT_2_FRACTION, new float[]{0.0f, 0.0f});
        connectPoint2StayAtZeroAnimator.setDuration(333);
        ObjectAnimator connectPoint2Animator = ObjectAnimator.ofFloat(this, LINE_CONNECT_POINT_2_FRACTION, new float[]{0.0f, 1.0f});
        connectPoint2Animator.setDuration(667);
        connectPoint2Animator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        connectPoint2Animator.setRepeatCount(-1);
        connectPoint2Animator.setRepeatMode(1);
        connectPoint2Animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                if (LinearIndeterminateSeamlessAnimatorDelegate.this.getLineConnectPoint1Fraction() > 0.0f && LinearIndeterminateSeamlessAnimatorDelegate.this.getLineConnectPoint1Fraction() < 1.0f) {
                    LinearIndeterminateSeamlessAnimatorDelegate.this.shiftSegmentColors();
                }
            }
        });
        AnimatorSet connectPoint2AnimatorSet = new AnimatorSet();
        connectPoint2AnimatorSet.playSequentially(new Animator[]{connectPoint2StayAtZeroAnimator, connectPoint2Animator});
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.animatorSet = animatorSet2;
        animatorSet2.playTogether(new Animator[]{connectPoint1Animator, connectPoint2AnimatorSet});
    }

    public void startAnimator() {
        this.animatorSet.start();
    }

    public void resetPropertiesForNewStart() {
        setLineConnectPoint1Fraction(0.0f);
        setLineConnectPoint2Fraction(0.0f);
        resetSegmentColors();
    }

    public void resetPropertiesForNextCycle() {
    }

    public void cancelAnimatorImmediately() {
        this.animatorSet.cancel();
    }

    public void requestCancelAnimatorAfterCurrentCycle() {
    }

    public void invalidateSpecValues() {
        resetSegmentColors();
    }

    public void registerAnimatorsCompleteCallback(Animatable2Compat.AnimationCallback callback) {
    }

    public void unregisterAnimatorsCompleteCallback() {
    }

    /* access modifiers changed from: private */
    public void shiftSegmentColors() {
        this.referenceSegmentColorIndex = (this.referenceSegmentColorIndex + 1) % this.drawable.combinedIndicatorColorArray.length;
        updateSegmentColors();
    }

    private void resetSegmentColors() {
        this.referenceSegmentColorIndex = 0;
        updateSegmentColors();
    }

    private void updateSegmentColors() {
        int leftSegmentColorIndex = MathUtils.floorMod(this.referenceSegmentColorIndex + 2, this.drawable.combinedIndicatorColorArray.length);
        int centralSegmentColorIndex = MathUtils.floorMod(this.referenceSegmentColorIndex + 1, this.drawable.combinedIndicatorColorArray.length);
        this.segmentColors[0] = this.drawable.combinedIndicatorColorArray[leftSegmentColorIndex];
        this.segmentColors[1] = this.drawable.combinedIndicatorColorArray[centralSegmentColorIndex];
        this.segmentColors[2] = this.drawable.combinedIndicatorColorArray[this.referenceSegmentColorIndex];
    }

    private void updateSegmentPositions() {
        this.segmentPositions[0] = 0.0f;
        float[] fArr = this.segmentPositions;
        float[] fArr2 = this.segmentPositions;
        float min = Math.min(getLineConnectPoint1Fraction(), getLineConnectPoint2Fraction());
        fArr2[2] = min;
        fArr[1] = min;
        float[] fArr3 = this.segmentPositions;
        float[] fArr4 = this.segmentPositions;
        float max = Math.max(getLineConnectPoint1Fraction(), getLineConnectPoint2Fraction());
        fArr4[4] = max;
        fArr3[3] = max;
        this.segmentPositions[5] = 1.0f;
    }

    /* access modifiers changed from: private */
    public float getLineConnectPoint1Fraction() {
        return this.lineConnectPoint1Fraction;
    }

    /* access modifiers changed from: package-private */
    public void setLineConnectPoint1Fraction(float lineConnectPoint1Fraction2) {
        this.lineConnectPoint1Fraction = lineConnectPoint1Fraction2;
        updateSegmentPositions();
        this.drawable.invalidateSelf();
    }

    /* access modifiers changed from: private */
    public float getLineConnectPoint2Fraction() {
        return this.lineConnectPoint2Fraction;
    }

    /* access modifiers changed from: package-private */
    public void setLineConnectPoint2Fraction(float lineConnectPoint2Fraction2) {
        this.lineConnectPoint2Fraction = lineConnectPoint2Fraction2;
        updateSegmentPositions();
        this.drawable.invalidateSelf();
    }
}
