package com.google.android.material.progressindicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Property;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import androidx.vectordrawable.graphics.drawable.AnimationUtilsCompat;
import com.google.android.material.C0822R;
import java.util.Arrays;

public final class LinearIndeterminateNonSeamlessAnimatorDelegate extends IndeterminateAnimatorDelegate<AnimatorSet> {
    private static final Property<LinearIndeterminateNonSeamlessAnimatorDelegate, Float> LINE_1_HEAD_FRACTION = new Property<LinearIndeterminateNonSeamlessAnimatorDelegate, Float>(Float.class, "line1HeadFraction") {
        public Float get(LinearIndeterminateNonSeamlessAnimatorDelegate drawable) {
            return Float.valueOf(drawable.getLine1HeadFraction());
        }

        public void set(LinearIndeterminateNonSeamlessAnimatorDelegate drawable, Float value) {
            drawable.setLine1HeadFraction(value.floatValue());
        }
    };
    private static final Property<LinearIndeterminateNonSeamlessAnimatorDelegate, Float> LINE_1_TAIL_FRACTION = new Property<LinearIndeterminateNonSeamlessAnimatorDelegate, Float>(Float.class, "line1TailFraction") {
        public Float get(LinearIndeterminateNonSeamlessAnimatorDelegate drawable) {
            return Float.valueOf(drawable.getLine1TailFraction());
        }

        public void set(LinearIndeterminateNonSeamlessAnimatorDelegate drawable, Float value) {
            drawable.setLine1TailFraction(value.floatValue());
        }
    };
    private static final Property<LinearIndeterminateNonSeamlessAnimatorDelegate, Float> LINE_2_HEAD_FRACTION = new Property<LinearIndeterminateNonSeamlessAnimatorDelegate, Float>(Float.class, "line2HeadFraction") {
        public Float get(LinearIndeterminateNonSeamlessAnimatorDelegate drawable) {
            return Float.valueOf(drawable.getLine2HeadFraction());
        }

        public void set(LinearIndeterminateNonSeamlessAnimatorDelegate drawable, Float value) {
            drawable.setLine2HeadFraction(value.floatValue());
        }
    };
    private static final Property<LinearIndeterminateNonSeamlessAnimatorDelegate, Float> LINE_2_TAIL_FRACTION = new Property<LinearIndeterminateNonSeamlessAnimatorDelegate, Float>(Float.class, "line2TailFraction") {
        public Float get(LinearIndeterminateNonSeamlessAnimatorDelegate drawable) {
            return Float.valueOf(drawable.getLine2TailFraction());
        }

        public void set(LinearIndeterminateNonSeamlessAnimatorDelegate drawable, Float value) {
            drawable.setLine2TailFraction(value.floatValue());
        }
    };
    private static final int MAIN_LINE_1_HEAD_DURATION = 750;
    private static final int MAIN_LINE_1_TAIL_DELAY = 333;
    private static final int MAIN_LINE_1_TAIL_DURATION = 850;
    private static final int MAIN_LINE_2_HEAD_DELAY = 1000;
    private static final int MAIN_LINE_2_HEAD_DURATION = 567;
    private static final int MAIN_LINE_2_TAIL_DELAY = 1267;
    private static final int MAIN_LINE_2_TAIL_DURATION = 533;
    Animatable2Compat.AnimationCallback animatorCompleteCallback = null;
    boolean animatorCompleteEndRequested = false;
    private final AnimatorSet animatorSet;
    private int displayedSegmentColorIndex;
    private float line1HeadFraction;
    private float line1TailFraction;
    private float line2HeadFraction;
    private float line2TailFraction;

    public LinearIndeterminateNonSeamlessAnimatorDelegate(Context context) {
        super(2);
        ObjectAnimator line1HeadAnimator = ObjectAnimator.ofFloat(this, LINE_1_HEAD_FRACTION, new float[]{0.0f, 1.0f});
        line1HeadAnimator.setDuration(750);
        line1HeadAnimator.setInterpolator(AnimationUtilsCompat.loadInterpolator(context, C0822R.animator.linear_indeterminate_line1_head_interpolator));
        ObjectAnimator line1TailAnimator = ObjectAnimator.ofFloat(this, LINE_1_TAIL_FRACTION, new float[]{0.0f, 1.0f});
        line1TailAnimator.setStartDelay(333);
        line1TailAnimator.setDuration(850);
        line1TailAnimator.setInterpolator(AnimationUtilsCompat.loadInterpolator(context, C0822R.animator.linear_indeterminate_line1_tail_interpolator));
        ObjectAnimator line2HeadAnimator = ObjectAnimator.ofFloat(this, LINE_2_HEAD_FRACTION, new float[]{0.0f, 1.0f});
        line2HeadAnimator.setStartDelay(1000);
        line2HeadAnimator.setDuration(567);
        line2HeadAnimator.setInterpolator(AnimationUtilsCompat.loadInterpolator(context, C0822R.animator.linear_indeterminate_line2_head_interpolator));
        ObjectAnimator line2TailAnimator = ObjectAnimator.ofFloat(this, LINE_2_TAIL_FRACTION, new float[]{0.0f, 1.0f});
        line2TailAnimator.setStartDelay(1267);
        line2TailAnimator.setDuration(533);
        line2TailAnimator.setInterpolator(AnimationUtilsCompat.loadInterpolator(context, C0822R.animator.linear_indeterminate_line2_tail_interpolator));
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.animatorSet = animatorSet2;
        animatorSet2.playTogether(new Animator[]{line1HeadAnimator, line1TailAnimator, line2HeadAnimator, line2TailAnimator});
        this.animatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (LinearIndeterminateNonSeamlessAnimatorDelegate.this.animatorCompleteEndRequested) {
                    LinearIndeterminateNonSeamlessAnimatorDelegate.this.animatorCompleteEndRequested = false;
                    LinearIndeterminateNonSeamlessAnimatorDelegate.this.animatorCompleteCallback.onAnimationEnd(LinearIndeterminateNonSeamlessAnimatorDelegate.this.drawable);
                    LinearIndeterminateNonSeamlessAnimatorDelegate.this.resetPropertiesForNewStart();
                } else if (LinearIndeterminateNonSeamlessAnimatorDelegate.this.drawable.isVisible()) {
                    LinearIndeterminateNonSeamlessAnimatorDelegate.this.resetPropertiesForNextCycle();
                    LinearIndeterminateNonSeamlessAnimatorDelegate.this.startAnimator();
                } else {
                    LinearIndeterminateNonSeamlessAnimatorDelegate.this.resetPropertiesForNewStart();
                }
            }
        });
    }

    public void startAnimator() {
        this.animatorSet.start();
    }

    public void resetPropertiesForNewStart() {
        resetPropertiesForNextCycle();
        resetSegmentColors();
    }

    public void resetPropertiesForNextCycle() {
        setLine1HeadFraction(0.0f);
        setLine1TailFraction(0.0f);
        setLine2HeadFraction(0.0f);
        setLine2TailFraction(0.0f);
        rotateSegmentColors();
    }

    public void cancelAnimatorImmediately() {
        this.animatorSet.cancel();
    }

    public void requestCancelAnimatorAfterCurrentCycle() {
        if (!this.animatorCompleteEndRequested) {
            if (!this.drawable.isVisible()) {
                cancelAnimatorImmediately();
            } else {
                this.animatorCompleteEndRequested = true;
            }
        }
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

    private void rotateSegmentColors() {
        this.displayedSegmentColorIndex = (this.displayedSegmentColorIndex + 1) % this.drawable.combinedIndicatorColorArray.length;
        Arrays.fill(this.segmentColors, this.drawable.combinedIndicatorColorArray[this.displayedSegmentColorIndex]);
    }

    private void resetSegmentColors() {
        this.displayedSegmentColorIndex = 0;
        Arrays.fill(this.segmentColors, this.drawable.combinedIndicatorColorArray[this.displayedSegmentColorIndex]);
    }

    /* access modifiers changed from: private */
    public float getLine1HeadFraction() {
        return this.line1HeadFraction;
    }

    /* access modifiers changed from: package-private */
    public void setLine1HeadFraction(float line1HeadFraction2) {
        this.line1HeadFraction = line1HeadFraction2;
        this.segmentPositions[3] = line1HeadFraction2;
        this.drawable.invalidateSelf();
    }

    /* access modifiers changed from: private */
    public float getLine1TailFraction() {
        return this.line1TailFraction;
    }

    /* access modifiers changed from: package-private */
    public void setLine1TailFraction(float line1TailFraction2) {
        this.line1TailFraction = line1TailFraction2;
        this.segmentPositions[2] = line1TailFraction2;
        this.drawable.invalidateSelf();
    }

    /* access modifiers changed from: private */
    public float getLine2HeadFraction() {
        return this.line2HeadFraction;
    }

    /* access modifiers changed from: package-private */
    public void setLine2HeadFraction(float line2HeadFraction2) {
        this.line2HeadFraction = line2HeadFraction2;
        this.segmentPositions[1] = line2HeadFraction2;
        this.drawable.invalidateSelf();
    }

    /* access modifiers changed from: private */
    public float getLine2TailFraction() {
        return this.line2TailFraction;
    }

    /* access modifiers changed from: package-private */
    public void setLine2TailFraction(float line2TailFraction2) {
        this.line2TailFraction = line2TailFraction2;
        this.segmentPositions[0] = line2TailFraction2;
        this.drawable.invalidateSelf();
    }
}
