package com.google.android.material.progressindicator;

import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.widget.ProgressBar;
import androidx.core.view.ViewCompat;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.google.android.material.C0822R;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ProgressIndicator extends ProgressBar {
    public static final int CIRCULAR = 1;
    public static final int CUSTOM = 2;
    protected static final float DEFAULT_OPACITY = 0.2f;
    protected static final int DEF_STYLE_RES = C0822R.style.Widget_MaterialComponents_ProgressIndicator_Linear_Determinate;
    public static final int GROW_MODE_BIDIRECTIONAL = 3;
    public static final int GROW_MODE_INCOMING = 1;
    public static final int GROW_MODE_NONE = 0;
    public static final int GROW_MODE_OUTGOING = 2;
    public static final int LINEAR = 0;
    protected static final int MAX_ALPHA = 255;
    private static final int MAX_HIDE_DELAY = 1000;
    private AnimatorDurationScaleProvider animatorDurationScaleProvider;
    private final Runnable delayedHide;
    private final Animatable2Compat.AnimationCallback hideAnimationCallback;
    /* access modifiers changed from: private */
    public boolean isIndeterminateModeChangeRequested;
    private boolean isParentDoneInitializing;
    /* access modifiers changed from: private */
    public long lastShowStartTime;
    private int minHideDelay;
    private final ProgressIndicatorSpec spec;
    /* access modifiers changed from: private */
    public int storedProgress;
    /* access modifiers changed from: private */
    public boolean storedProgressAnimated;
    private final Animatable2Compat.AnimationCallback switchIndeterminateModeCallback;

    @Retention(RetentionPolicy.SOURCE)
    public @interface GrowMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface IndicatorType {
    }

    public ProgressIndicator(Context context) {
        this(context, (AttributeSet) null);
    }

    public ProgressIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, C0822R.attr.progressIndicatorStyle);
    }

    public ProgressIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, DEF_STYLE_RES);
    }

    public ProgressIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, DEF_STYLE_RES), attrs, defStyleAttr);
        this.lastShowStartTime = -1;
        this.isIndeterminateModeChangeRequested = false;
        this.delayedHide = new Runnable() {
            public void run() {
                ProgressIndicator.this.internalHide();
                long unused = ProgressIndicator.this.lastShowStartTime = -1;
            }
        };
        this.switchIndeterminateModeCallback = new Animatable2Compat.AnimationCallback() {
            public void onAnimationEnd(Drawable drawable) {
                ProgressIndicator.this.setIndeterminate(false);
                ProgressIndicator.this.setProgressCompat(0, false);
                ProgressIndicator progressIndicator = ProgressIndicator.this;
                progressIndicator.setProgressCompat(progressIndicator.storedProgress, ProgressIndicator.this.storedProgressAnimated);
            }
        };
        this.hideAnimationCallback = new Animatable2Compat.AnimationCallback() {
            public void onAnimationEnd(Drawable drawable) {
                super.onAnimationEnd(drawable);
                if (!ProgressIndicator.this.isIndeterminateModeChangeRequested && ProgressIndicator.this.visibleToUser()) {
                    ProgressIndicator.this.setVisibility(4);
                }
            }
        };
        this.animatorDurationScaleProvider = new AnimatorDurationScaleProvider();
        this.isParentDoneInitializing = true;
        Context context2 = getContext();
        ProgressIndicatorSpec progressIndicatorSpec = new ProgressIndicatorSpec();
        this.spec = progressIndicatorSpec;
        progressIndicatorSpec.loadFromAttributes(context2, attrs, defStyleAttr, defStyleRes);
        loadExtraAttributes(context2, attrs, defStyleAttr, defStyleRes);
        if (this.spec.indicatorType != 2) {
            initializeDrawables();
        }
    }

    private void loadExtraAttributes(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = context.obtainStyledAttributes(attrs, C0822R.styleable.ProgressIndicator, defStyleAttr, defStyleRes);
        if (a.hasValue(C0822R.styleable.ProgressIndicator_minHideDelay)) {
            this.minHideDelay = Math.min(a.getInt(C0822R.styleable.ProgressIndicator_minHideDelay, -1), 1000);
        }
        a.recycle();
    }

    private void initializeDrawables() {
        IndeterminateAnimatorDelegate<AnimatorSet> animatorDelegate;
        if (this.spec.indicatorType == 0) {
            DrawingDelegate drawingDelegate = new LinearDrawingDelegate();
            if (isLinearSeamless()) {
                animatorDelegate = new LinearIndeterminateSeamlessAnimatorDelegate();
            } else {
                animatorDelegate = new LinearIndeterminateNonSeamlessAnimatorDelegate(getContext());
            }
            setIndeterminateDrawable(new IndeterminateDrawable(getContext(), this.spec, drawingDelegate, animatorDelegate));
            setProgressDrawable(new DeterminateDrawable(getContext(), this.spec, drawingDelegate));
        } else {
            DrawingDelegate drawingDelegate2 = new CircularDrawingDelegate();
            setIndeterminateDrawable(new IndeterminateDrawable(getContext(), this.spec, drawingDelegate2, new CircularIndeterminateAnimatorDelegate()));
            setProgressDrawable(new DeterminateDrawable(getContext(), this.spec, drawingDelegate2));
        }
        applyNewVisibility();
    }

    public void initializeDrawables(IndeterminateDrawable indeterminateDrawable, DeterminateDrawable determinateDrawable) {
        if (this.spec.indicatorType != 2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Manually setting drawables can only be done while indicator type is custom. Current indicator type is ");
            sb.append(this.spec.indicatorType == 0 ? "linear" : "circular");
            throw new IllegalStateException(sb.toString());
        } else if (indeterminateDrawable == null && determinateDrawable == null) {
            throw new IllegalArgumentException("Indeterminate and determinate drawables cannot be null at the same time.");
        } else {
            setIndeterminateDrawable(indeterminateDrawable);
            setProgressDrawable(determinateDrawable);
            setIndeterminate(indeterminateDrawable != null && (determinateDrawable == null || isIndeterminate()));
            applyNewVisibility();
        }
    }

    private void registerAnimationCallbacks() {
        if (!(getProgressDrawable() == null || getIndeterminateDrawable() == null)) {
            getIndeterminateDrawable().getAnimatorDelegate().registerAnimatorsCompleteCallback(this.switchIndeterminateModeCallback);
        }
        if (getProgressDrawable() != null) {
            getProgressDrawable().registerAnimationCallback(this.hideAnimationCallback);
        }
        if (getIndeterminateDrawable() != null) {
            getIndeterminateDrawable().registerAnimationCallback(this.hideAnimationCallback);
        }
    }

    private void unregisterAnimationCallbacks() {
        if (getIndeterminateDrawable() != null) {
            getIndeterminateDrawable().unregisterAnimationCallback(this.hideAnimationCallback);
            getIndeterminateDrawable().getAnimatorDelegate().unregisterAnimatorsCompleteCallback();
        }
        if (getProgressDrawable() != null) {
            getProgressDrawable().unregisterAnimationCallback(this.hideAnimationCallback);
        }
    }

    public void show() {
        if (this.minHideDelay > 0) {
            this.lastShowStartTime = SystemClock.uptimeMillis();
        }
        setVisibility(0);
    }

    public void hide() {
        removeCallbacks(this.delayedHide);
        long timeElapsedSinceShowStart = SystemClock.uptimeMillis() - this.lastShowStartTime;
        if (timeElapsedSinceShowStart >= ((long) this.minHideDelay)) {
            this.delayedHide.run();
        } else {
            postDelayed(this.delayedHide, ((long) this.minHideDelay) - timeElapsedSinceShowStart);
        }
    }

    /* access modifiers changed from: private */
    public void internalHide() {
        getCurrentDrawable().setVisible(false, false);
        if (isNoLongerNeedToBeVisible()) {
            setVisibility(4);
        }
    }

    /* access modifiers changed from: protected */
    public void onVisibilityChanged(View changeView, int visibility) {
        super.onVisibilityChanged(changeView, visibility);
        applyNewVisibility();
    }

    /* access modifiers changed from: protected */
    public void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        applyNewVisibility();
    }

    private void applyNewVisibility() {
        if (this.isParentDoneInitializing) {
            getCurrentDrawable().setVisible(visibleToUser(), false);
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        registerAnimationCallbacks();
        if (visibleToUser()) {
            show();
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        removeCallbacks(this.delayedHide);
        getCurrentDrawable().hideNow();
        unregisterAnimationCallbacks();
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: protected */
    public synchronized void onDraw(Canvas canvas) {
        int saveCount = canvas.save();
        if (!(getPaddingLeft() == 0 && getPaddingTop() == 0)) {
            canvas.translate((float) getPaddingLeft(), (float) getPaddingTop());
        }
        if (!(getPaddingRight() == 0 && getPaddingBottom() == 0)) {
            canvas.clipRect(0, 0, getWidth() - (getPaddingLeft() + getPaddingRight()), getHeight() - (getPaddingTop() + getPaddingBottom()));
        }
        getCurrentDrawable().draw(canvas);
        canvas.restoreToCount(saveCount);
    }

    /* access modifiers changed from: protected */
    public synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i;
        int i2;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        DrawingDelegate drawingDelegate = getCurrentDrawingDelegate();
        int drawableMeasuredWidth = drawingDelegate.getPreferredWidth(this.spec);
        int drawableMeasuredHeight = drawingDelegate.getPreferredHeight(this.spec);
        if (drawableMeasuredWidth < 0) {
            i = getMeasuredWidth();
        } else {
            i = getPaddingLeft() + drawableMeasuredWidth + getPaddingRight();
        }
        if (drawableMeasuredHeight < 0) {
            i2 = getMeasuredHeight();
        } else {
            i2 = getPaddingTop() + drawableMeasuredHeight + getPaddingBottom();
        }
        setMeasuredDimension(i, i2);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (this.spec.indicatorType == 0) {
            int contentWidth = w - (getPaddingLeft() + getPaddingRight());
            int contentHeight = h - (getPaddingTop() + getPaddingBottom());
            Drawable drawable = getIndeterminateDrawable();
            if (drawable != null) {
                drawable.setBounds(0, 0, contentWidth, contentHeight);
            }
            Drawable drawable2 = getProgressDrawable();
            if (drawable2 != null) {
                drawable2.setBounds(0, 0, contentWidth, contentHeight);
                return;
            }
            return;
        }
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public void invalidate() {
        super.invalidate();
        if (getCurrentDrawable() != null) {
            getCurrentDrawable().invalidateSelf();
        }
    }

    private boolean isEligibleToSeamless() {
        return isIndeterminate() && this.spec.indicatorType == 0 && this.spec.indicatorColors.length >= 3;
    }

    public DrawableWithAnimatedVisibilityChange getCurrentDrawable() {
        return isIndeterminate() ? getIndeterminateDrawable() : getProgressDrawable();
    }

    public DrawingDelegate getCurrentDrawingDelegate() {
        if (isIndeterminate()) {
            return getIndeterminateDrawable().getDrawingDelegate();
        }
        return getProgressDrawable().getDrawingDelegate();
    }

    public void setProgressDrawable(Drawable drawable) {
        if (drawable == null) {
            super.setProgressDrawable((Drawable) null);
        } else if (drawable instanceof DeterminateDrawable) {
            DeterminateDrawable determinateDrawable = (DeterminateDrawable) drawable;
            determinateDrawable.hideNow();
            super.setProgressDrawable(determinateDrawable);
            determinateDrawable.setLevelByFraction(((float) getProgress()) / ((float) getMax()));
        } else {
            throw new IllegalArgumentException("Cannot set framework drawable as progress drawable.");
        }
    }

    public void setIndeterminateDrawable(Drawable drawable) {
        if (drawable == null) {
            super.setIndeterminateDrawable((Drawable) null);
        } else if (drawable instanceof IndeterminateDrawable) {
            ((IndeterminateDrawable) drawable).hideNow();
            super.setIndeterminateDrawable(drawable);
        } else {
            throw new IllegalArgumentException("Cannot set framework drawable as indeterminate drawable.");
        }
    }

    public DeterminateDrawable getProgressDrawable() {
        return (DeterminateDrawable) super.getProgressDrawable();
    }

    public IndeterminateDrawable getIndeterminateDrawable() {
        return (IndeterminateDrawable) super.getIndeterminateDrawable();
    }

    /* access modifiers changed from: private */
    public boolean visibleToUser() {
        return ViewCompat.isAttachedToWindow(this) && getWindowVisibility() == 0 && isEffectivelyVisible();
    }

    /* access modifiers changed from: protected */
    public boolean isEffectivelyVisible() {
        View current = this;
        while (current.getVisibility() == 0) {
            ViewParent parent = current.getParent();
            if (parent == null) {
                if (getWindowVisibility() == 0) {
                    return true;
                }
                return false;
            } else if (!(parent instanceof View)) {
                return true;
            } else {
                current = (View) parent;
            }
        }
        return false;
    }

    private void updateColorsInDrawables() {
        getProgressDrawable().recalculateColors();
        getIndeterminateDrawable().recalculateColors();
        getIndeterminateDrawable().getAnimatorDelegate().invalidateSpecValues();
    }

    private boolean isNoLongerNeedToBeVisible() {
        return (getProgressDrawable() == null || !getProgressDrawable().isVisible()) && (getIndeterminateDrawable() == null || !getIndeterminateDrawable().isVisible());
    }

    public ProgressIndicatorSpec getSpec() {
        return this.spec;
    }

    public int getIndicatorType() {
        return this.spec.indicatorType;
    }

    public void setIndicatorType(int indicatorType) {
        if (!visibleToUser() || this.spec.indicatorType == indicatorType) {
            this.spec.indicatorType = indicatorType;
            initializeDrawables();
            requestLayout();
            return;
        }
        throw new IllegalStateException("Cannot change indicatorType while the progress indicator is visible.");
    }

    public synchronized void setIndeterminate(boolean indeterminate) {
        if (indeterminate != isIndeterminate()) {
            if (!indeterminate) {
                if (isLinearSeamless()) {
                    return;
                }
            }
            if (visibleToUser()) {
                if (indeterminate) {
                    throw new IllegalStateException("Cannot switch to indeterminate mode while the progress indicator is visible.");
                }
            }
            DrawableWithAnimatedVisibilityChange oldDrawable = getCurrentDrawable();
            if (oldDrawable != null) {
                oldDrawable.hideNow();
            }
            super.setIndeterminate(indeterminate);
            DrawableWithAnimatedVisibilityChange newDrawable = getCurrentDrawable();
            if (newDrawable != null) {
                newDrawable.setVisible(visibleToUser(), false, false);
            }
            this.isIndeterminateModeChangeRequested = false;
        }
    }

    public int getIndicatorWidth() {
        return this.spec.indicatorWidth;
    }

    public void setIndicatorWidth(int indicatorWidth) {
        if (this.spec.indicatorWidth != indicatorWidth) {
            this.spec.indicatorWidth = indicatorWidth;
            requestLayout();
        }
    }

    public int[] getIndicatorColors() {
        return this.spec.indicatorColors;
    }

    public void setIndicatorColors(int[] indicatorColors) {
        this.spec.indicatorColors = indicatorColors;
        updateColorsInDrawables();
        if (!isEligibleToSeamless()) {
            this.spec.linearSeamless = false;
        }
        invalidate();
    }

    public int getTrackColor() {
        return this.spec.trackColor;
    }

    public void setTrackColor(int trackColor) {
        if (this.spec.trackColor != trackColor) {
            this.spec.trackColor = trackColor;
            updateColorsInDrawables();
            invalidate();
        }
    }

    public boolean isInverse() {
        return this.spec.inverse;
    }

    public void setInverse(boolean inverse) {
        if (this.spec.inverse != inverse) {
            this.spec.inverse = inverse;
            invalidate();
        }
    }

    public int getGrowMode() {
        return this.spec.growMode;
    }

    public void setGrowMode(int growMode) {
        if (this.spec.growMode != growMode) {
            this.spec.growMode = growMode;
            invalidate();
        }
    }

    public boolean isLinearSeamless() {
        return this.spec.linearSeamless;
    }

    public void setLinearSeamless(boolean linearSeamless) {
        if (this.spec.linearSeamless != linearSeamless) {
            if (!visibleToUser() || !isIndeterminate()) {
                if (isEligibleToSeamless()) {
                    this.spec.linearSeamless = linearSeamless;
                    if (linearSeamless) {
                        this.spec.indicatorCornerRadius = 0;
                    }
                    if (linearSeamless) {
                        getIndeterminateDrawable().setAnimatorDelegate(new LinearIndeterminateSeamlessAnimatorDelegate());
                    } else {
                        getIndeterminateDrawable().setAnimatorDelegate(new LinearIndeterminateNonSeamlessAnimatorDelegate(getContext()));
                    }
                } else {
                    this.spec.linearSeamless = false;
                }
                invalidate();
                return;
            }
            throw new IllegalStateException("Cannot change linearSeamless while the progress indicator is shown in indeterminate mode.");
        }
    }

    public int getIndicatorCornerRadius() {
        return this.spec.indicatorCornerRadius;
    }

    public void setIndicatorCornerRadius(int indicatorCornerRadius) {
        if (this.spec.indicatorCornerRadius != indicatorCornerRadius) {
            ProgressIndicatorSpec progressIndicatorSpec = this.spec;
            progressIndicatorSpec.indicatorCornerRadius = Math.min(indicatorCornerRadius, progressIndicatorSpec.indicatorWidth / 2);
            if (this.spec.linearSeamless && indicatorCornerRadius > 0) {
                throw new IllegalArgumentException("Rounded corners are not supported in linear seamless mode.");
            }
        }
    }

    public int getCircularInset() {
        return this.spec.circularInset;
    }

    public void setCircularInset(int circularInset) {
        if (this.spec.indicatorType == 1 && this.spec.circularInset != circularInset) {
            this.spec.circularInset = circularInset;
            invalidate();
        }
    }

    public int getCircularRadius() {
        return this.spec.circularRadius;
    }

    public void setCircularRadius(int circularRadius) {
        if (this.spec.indicatorType == 1 && this.spec.circularRadius != circularRadius) {
            this.spec.circularRadius = circularRadius;
            invalidate();
        }
    }

    public synchronized void setProgress(int progress) {
        setProgressCompat(progress, false);
    }

    public void setProgressCompat(int progress, boolean animated) {
        if (!isIndeterminate()) {
            super.setProgress(progress);
            if (getProgressDrawable() != null && !animated) {
                getProgressDrawable().jumpToCurrentState();
            }
        } else if (getProgressDrawable() != null && !isLinearSeamless()) {
            this.storedProgress = progress;
            this.storedProgressAnimated = animated;
            this.isIndeterminateModeChangeRequested = true;
            if (this.animatorDurationScaleProvider.getSystemAnimatorDurationScale(getContext().getContentResolver()) == 0.0f) {
                this.switchIndeterminateModeCallback.onAnimationEnd(getIndeterminateDrawable());
            } else {
                getIndeterminateDrawable().getAnimatorDelegate().requestCancelAnimatorAfterCurrentCycle();
            }
        }
    }

    public void setAnimatorDurationScaleProvider(AnimatorDurationScaleProvider animatorDurationScaleProvider2) {
        this.animatorDurationScaleProvider = animatorDurationScaleProvider2;
        if (getProgressDrawable() != null) {
            getProgressDrawable().animatorDurationScaleProvider = animatorDurationScaleProvider2;
        }
        if (getIndeterminateDrawable() != null) {
            getIndeterminateDrawable().animatorDurationScaleProvider = animatorDurationScaleProvider2;
        }
    }
}
