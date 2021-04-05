package p015me.zhanghai.android.materialprogressbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ProgressBar;
import androidx.appcompat.widget.TintTypedArray;
import p015me.zhanghai.android.materialprogressbar.internal.DrawableCompat;

/* renamed from: me.zhanghai.android.materialprogressbar.MaterialProgressBar */
public class MaterialProgressBar extends ProgressBar {
    public static final int DETERMINATE_CIRCULAR_PROGRESS_STYLE_DYNAMIC = 1;
    public static final int DETERMINATE_CIRCULAR_PROGRESS_STYLE_NORMAL = 0;
    public static final int PROGRESS_STYLE_CIRCULAR = 0;
    public static final int PROGRESS_STYLE_HORIZONTAL = 1;
    private static final String TAG = MaterialProgressBar.class.getSimpleName();
    private int mProgressStyle;
    private TintInfo mProgressTintInfo = new TintInfo();
    private boolean mSuperInitialized = true;

    public MaterialProgressBar(Context context) {
        super(context);
        init((AttributeSet) null, 0, 0);
    }

    public MaterialProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0, 0);
    }

    public MaterialProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, 0);
    }

    public MaterialProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr, defStyleRes);
    }

    private void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        Context context = getContext();
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attrs, C2446R.styleable.MaterialProgressBar, defStyleAttr, defStyleRes);
        this.mProgressStyle = a.getInt(C2446R.styleable.MaterialProgressBar_mpb_progressStyle, 0);
        boolean setBothDrawables = a.getBoolean(C2446R.styleable.MaterialProgressBar_mpb_setBothDrawables, false);
        boolean useIntrinsicPadding = a.getBoolean(C2446R.styleable.MaterialProgressBar_mpb_useIntrinsicPadding, true);
        boolean showProgressBackground = a.getBoolean(C2446R.styleable.MaterialProgressBar_mpb_showProgressBackground, this.mProgressStyle == 1);
        int determinateCircularProgressStyle = a.getInt(C2446R.styleable.MaterialProgressBar_mpb_determinateCircularProgressStyle, 0);
        if (a.hasValue(C2446R.styleable.MaterialProgressBar_mpb_progressTint)) {
            this.mProgressTintInfo.mProgressTint = a.getColorStateList(C2446R.styleable.MaterialProgressBar_mpb_progressTint);
            this.mProgressTintInfo.mHasProgressTint = true;
        }
        if (a.hasValue(C2446R.styleable.MaterialProgressBar_mpb_progressTintMode)) {
            this.mProgressTintInfo.mProgressTintMode = DrawableCompat.parseTintMode(a.getInt(C2446R.styleable.MaterialProgressBar_mpb_progressTintMode, -1), (PorterDuff.Mode) null);
            this.mProgressTintInfo.mHasProgressTintMode = true;
        }
        if (a.hasValue(C2446R.styleable.MaterialProgressBar_mpb_secondaryProgressTint)) {
            this.mProgressTintInfo.mSecondaryProgressTint = a.getColorStateList(C2446R.styleable.MaterialProgressBar_mpb_secondaryProgressTint);
            this.mProgressTintInfo.mHasSecondaryProgressTint = true;
        }
        if (a.hasValue(C2446R.styleable.MaterialProgressBar_mpb_secondaryProgressTintMode)) {
            this.mProgressTintInfo.mSecondaryProgressTintMode = DrawableCompat.parseTintMode(a.getInt(C2446R.styleable.MaterialProgressBar_mpb_secondaryProgressTintMode, -1), (PorterDuff.Mode) null);
            this.mProgressTintInfo.mHasSecondaryProgressTintMode = true;
        }
        if (a.hasValue(C2446R.styleable.MaterialProgressBar_mpb_progressBackgroundTint)) {
            this.mProgressTintInfo.mProgressBackgroundTint = a.getColorStateList(C2446R.styleable.MaterialProgressBar_mpb_progressBackgroundTint);
            this.mProgressTintInfo.mHasProgressBackgroundTint = true;
        }
        if (a.hasValue(C2446R.styleable.MaterialProgressBar_mpb_progressBackgroundTintMode)) {
            this.mProgressTintInfo.mProgressBackgroundTintMode = DrawableCompat.parseTintMode(a.getInt(C2446R.styleable.MaterialProgressBar_mpb_progressBackgroundTintMode, -1), (PorterDuff.Mode) null);
            this.mProgressTintInfo.mHasProgressBackgroundTintMode = true;
        }
        if (a.hasValue(C2446R.styleable.MaterialProgressBar_mpb_indeterminateTint)) {
            this.mProgressTintInfo.mIndeterminateTint = a.getColorStateList(C2446R.styleable.MaterialProgressBar_mpb_indeterminateTint);
            this.mProgressTintInfo.mHasIndeterminateTint = true;
        }
        if (a.hasValue(C2446R.styleable.MaterialProgressBar_mpb_indeterminateTintMode)) {
            this.mProgressTintInfo.mIndeterminateTintMode = DrawableCompat.parseTintMode(a.getInt(C2446R.styleable.MaterialProgressBar_mpb_indeterminateTintMode, -1), (PorterDuff.Mode) null);
            this.mProgressTintInfo.mHasIndeterminateTintMode = true;
        }
        a.recycle();
        int i = this.mProgressStyle;
        if (i == 0) {
            if ((isIndeterminate() || setBothDrawables) && !isInEditMode()) {
                setIndeterminateDrawable(new IndeterminateCircularProgressDrawable(context));
            }
            if (!isIndeterminate() || setBothDrawables) {
                setProgressDrawable(new CircularProgressDrawable(determinateCircularProgressStyle, context));
            }
        } else if (i == 1) {
            if ((isIndeterminate() || setBothDrawables) && !isInEditMode()) {
                setIndeterminateDrawable(new IndeterminateHorizontalProgressDrawable(context));
            }
            if (!isIndeterminate() || setBothDrawables) {
                setProgressDrawable(new HorizontalProgressDrawable(context));
            }
        } else {
            throw new IllegalArgumentException("Unknown progress style: " + this.mProgressStyle);
        }
        setUseIntrinsicPadding(useIntrinsicPadding);
        setShowProgressBackground(showProgressBackground);
    }

    public synchronized void setIndeterminate(boolean indeterminate) {
        super.setIndeterminate(indeterminate);
        if (this.mSuperInitialized && !(getCurrentDrawable() instanceof MaterialProgressDrawable)) {
            Log.w(TAG, "Current drawable is not a MaterialProgressDrawable, you may want to set app:mpb_setBothDrawables");
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        fixCanvasScalingAndColorFilterWhenHardwareAccelerated();
    }

    private void fixCanvasScalingAndColorFilterWhenHardwareAccelerated() {
        if (Build.VERSION.SDK_INT < 21 && isHardwareAccelerated() && getLayerType() != 1) {
            setLayerType(1, (Paint) null);
        }
    }

    public int getProgressStyle() {
        return this.mProgressStyle;
    }

    public Drawable getCurrentDrawable() {
        return isIndeterminate() ? getIndeterminateDrawable() : getProgressDrawable();
    }

    public boolean getUseIntrinsicPadding() {
        Drawable drawable = getCurrentDrawable();
        if (drawable instanceof IntrinsicPaddingDrawable) {
            return ((IntrinsicPaddingDrawable) drawable).getUseIntrinsicPadding();
        }
        throw new IllegalStateException("Drawable does not implement IntrinsicPaddingDrawable");
    }

    public void setUseIntrinsicPadding(boolean useIntrinsicPadding) {
        Drawable drawable = getCurrentDrawable();
        if (drawable instanceof IntrinsicPaddingDrawable) {
            ((IntrinsicPaddingDrawable) drawable).setUseIntrinsicPadding(useIntrinsicPadding);
        }
        Drawable indeterminateDrawable = getIndeterminateDrawable();
        if (indeterminateDrawable instanceof IntrinsicPaddingDrawable) {
            ((IntrinsicPaddingDrawable) indeterminateDrawable).setUseIntrinsicPadding(useIntrinsicPadding);
        }
    }

    public boolean getShowProgressBackground() {
        Drawable drawable = getCurrentDrawable();
        if (drawable instanceof ShowBackgroundDrawable) {
            return ((ShowBackgroundDrawable) drawable).getShowBackground();
        }
        return false;
    }

    public void setShowProgressBackground(boolean show) {
        Drawable drawable = getCurrentDrawable();
        if (drawable instanceof ShowBackgroundDrawable) {
            ((ShowBackgroundDrawable) drawable).setShowBackground(show);
        }
        Drawable indeterminateDrawable = getIndeterminateDrawable();
        if (indeterminateDrawable instanceof ShowBackgroundDrawable) {
            ((ShowBackgroundDrawable) indeterminateDrawable).setShowBackground(show);
        }
    }

    public void setProgressDrawable(Drawable d) {
        super.setProgressDrawable(d);
        if (this.mProgressTintInfo != null) {
            applyProgressTints();
        }
    }

    public void setIndeterminateDrawable(Drawable d) {
        super.setIndeterminateDrawable(d);
        if (this.mProgressTintInfo != null) {
            applyIndeterminateTint();
        }
    }

    public ColorStateList getProgressTintList() {
        return this.mProgressTintInfo.mProgressTint;
    }

    public void setProgressTintList(ColorStateList tint) {
        this.mProgressTintInfo.mProgressTint = tint;
        this.mProgressTintInfo.mHasProgressTint = true;
        applyPrimaryProgressTint();
    }

    public PorterDuff.Mode getProgressTintMode() {
        return this.mProgressTintInfo.mProgressTintMode;
    }

    public void setProgressTintMode(PorterDuff.Mode tintMode) {
        this.mProgressTintInfo.mProgressTintMode = tintMode;
        this.mProgressTintInfo.mHasProgressTintMode = true;
        applyPrimaryProgressTint();
    }

    public ColorStateList getSecondaryProgressTintList() {
        return this.mProgressTintInfo.mSecondaryProgressTint;
    }

    public void setSecondaryProgressTintList(ColorStateList tint) {
        this.mProgressTintInfo.mSecondaryProgressTint = tint;
        this.mProgressTintInfo.mHasSecondaryProgressTint = true;
        applySecondaryProgressTint();
    }

    public PorterDuff.Mode getSecondaryProgressTintMode() {
        return this.mProgressTintInfo.mSecondaryProgressTintMode;
    }

    public void setSecondaryProgressTintMode(PorterDuff.Mode tintMode) {
        this.mProgressTintInfo.mSecondaryProgressTintMode = tintMode;
        this.mProgressTintInfo.mHasSecondaryProgressTintMode = true;
        applySecondaryProgressTint();
    }

    public ColorStateList getProgressBackgroundTintList() {
        return this.mProgressTintInfo.mProgressBackgroundTint;
    }

    public void setProgressBackgroundTintList(ColorStateList tint) {
        this.mProgressTintInfo.mProgressBackgroundTint = tint;
        this.mProgressTintInfo.mHasProgressBackgroundTint = true;
        applyProgressBackgroundTint();
    }

    public PorterDuff.Mode getProgressBackgroundTintMode() {
        return this.mProgressTintInfo.mProgressBackgroundTintMode;
    }

    public void setProgressBackgroundTintMode(PorterDuff.Mode tintMode) {
        this.mProgressTintInfo.mProgressBackgroundTintMode = tintMode;
        this.mProgressTintInfo.mHasProgressBackgroundTintMode = true;
        applyProgressBackgroundTint();
    }

    public ColorStateList getIndeterminateTintList() {
        return this.mProgressTintInfo.mIndeterminateTint;
    }

    public void setIndeterminateTintList(ColorStateList tint) {
        this.mProgressTintInfo.mIndeterminateTint = tint;
        this.mProgressTintInfo.mHasIndeterminateTint = true;
        applyIndeterminateTint();
    }

    public PorterDuff.Mode getIndeterminateTintMode() {
        return this.mProgressTintInfo.mIndeterminateTintMode;
    }

    public void setIndeterminateTintMode(PorterDuff.Mode tintMode) {
        this.mProgressTintInfo.mIndeterminateTintMode = tintMode;
        this.mProgressTintInfo.mHasIndeterminateTintMode = true;
        applyIndeterminateTint();
    }

    private void applyProgressTints() {
        if (getProgressDrawable() != null) {
            applyPrimaryProgressTint();
            applyProgressBackgroundTint();
            applySecondaryProgressTint();
        }
    }

    private void applyPrimaryProgressTint() {
        Drawable target;
        if (getProgressDrawable() != null) {
            if ((this.mProgressTintInfo.mHasProgressTint || this.mProgressTintInfo.mHasProgressTintMode) && (target = getTintTargetFromProgressDrawable(16908301, true)) != null) {
                applyTintForDrawable(target, this.mProgressTintInfo.mProgressTint, this.mProgressTintInfo.mHasProgressTint, this.mProgressTintInfo.mProgressTintMode, this.mProgressTintInfo.mHasProgressTintMode);
            }
        }
    }

    private void applySecondaryProgressTint() {
        Drawable target;
        if (getProgressDrawable() != null) {
            if ((this.mProgressTintInfo.mHasSecondaryProgressTint || this.mProgressTintInfo.mHasSecondaryProgressTintMode) && (target = getTintTargetFromProgressDrawable(16908303, false)) != null) {
                applyTintForDrawable(target, this.mProgressTintInfo.mSecondaryProgressTint, this.mProgressTintInfo.mHasSecondaryProgressTint, this.mProgressTintInfo.mSecondaryProgressTintMode, this.mProgressTintInfo.mHasSecondaryProgressTintMode);
            }
        }
    }

    private void applyProgressBackgroundTint() {
        Drawable target;
        if (getProgressDrawable() != null) {
            if ((this.mProgressTintInfo.mHasProgressBackgroundTint || this.mProgressTintInfo.mHasProgressBackgroundTintMode) && (target = getTintTargetFromProgressDrawable(16908288, false)) != null) {
                applyTintForDrawable(target, this.mProgressTintInfo.mProgressBackgroundTint, this.mProgressTintInfo.mHasProgressBackgroundTint, this.mProgressTintInfo.mProgressBackgroundTintMode, this.mProgressTintInfo.mHasProgressBackgroundTintMode);
            }
        }
    }

    private Drawable getTintTargetFromProgressDrawable(int layerId, boolean shouldFallback) {
        Drawable progressDrawable = getProgressDrawable();
        if (progressDrawable == null) {
            return null;
        }
        progressDrawable.mutate();
        Drawable layerDrawable = null;
        if (progressDrawable instanceof LayerDrawable) {
            layerDrawable = ((LayerDrawable) progressDrawable).findDrawableByLayerId(layerId);
        }
        if (layerDrawable != null || !shouldFallback) {
            return layerDrawable;
        }
        return progressDrawable;
    }

    private void applyIndeterminateTint() {
        Drawable indeterminateDrawable = getIndeterminateDrawable();
        if (indeterminateDrawable != null) {
            if (this.mProgressTintInfo.mHasIndeterminateTint || this.mProgressTintInfo.mHasIndeterminateTintMode) {
                indeterminateDrawable.mutate();
                applyTintForDrawable(indeterminateDrawable, this.mProgressTintInfo.mIndeterminateTint, this.mProgressTintInfo.mHasIndeterminateTint, this.mProgressTintInfo.mIndeterminateTintMode, this.mProgressTintInfo.mHasIndeterminateTintMode);
            }
        }
    }

    private void applyTintForDrawable(Drawable drawable, ColorStateList tint, boolean hasTint, PorterDuff.Mode tintMode, boolean hasTintMode) {
        if (hasTint || hasTintMode) {
            if (hasTint) {
                if (drawable instanceof TintableDrawable) {
                    ((TintableDrawable) drawable).setTintList(tint);
                } else {
                    Log.w(TAG, "Drawable did not implement TintableDrawable, it won't be tinted below Lollipop");
                    if (Build.VERSION.SDK_INT >= 21) {
                        drawable.setTintList(tint);
                    }
                }
            }
            if (hasTintMode) {
                if (drawable instanceof TintableDrawable) {
                    ((TintableDrawable) drawable).setTintMode(tintMode);
                } else {
                    Log.w(TAG, "Drawable did not implement TintableDrawable, it won't be tinted below Lollipop");
                    if (Build.VERSION.SDK_INT >= 21) {
                        drawable.setTintMode(tintMode);
                    }
                }
            }
            if (drawable.isStateful()) {
                drawable.setState(getDrawableState());
            }
        }
    }

    /* renamed from: me.zhanghai.android.materialprogressbar.MaterialProgressBar$TintInfo */
    private static class TintInfo {
        public boolean mHasIndeterminateTint;
        public boolean mHasIndeterminateTintMode;
        public boolean mHasProgressBackgroundTint;
        public boolean mHasProgressBackgroundTintMode;
        public boolean mHasProgressTint;
        public boolean mHasProgressTintMode;
        public boolean mHasSecondaryProgressTint;
        public boolean mHasSecondaryProgressTintMode;
        public ColorStateList mIndeterminateTint;
        public PorterDuff.Mode mIndeterminateTintMode;
        public ColorStateList mProgressBackgroundTint;
        public PorterDuff.Mode mProgressBackgroundTintMode;
        public ColorStateList mProgressTint;
        public PorterDuff.Mode mProgressTintMode;
        public ColorStateList mSecondaryProgressTint;
        public PorterDuff.Mode mSecondaryProgressTintMode;

        private TintInfo() {
        }
    }
}
