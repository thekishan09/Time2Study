package p015me.zhanghai.android.materialprogressbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import androidx.core.graphics.ColorUtils;
import androidx.core.view.ViewCompat;
import p015me.zhanghai.android.materialprogressbar.IntrinsicPaddingDrawable;
import p015me.zhanghai.android.materialprogressbar.ShowBackgroundDrawable;
import p015me.zhanghai.android.materialprogressbar.TintableDrawable;
import p015me.zhanghai.android.materialprogressbar.internal.ThemeUtils;

/* renamed from: me.zhanghai.android.materialprogressbar.BaseProgressLayerDrawable */
class BaseProgressLayerDrawable<ProgressDrawableType extends IntrinsicPaddingDrawable & ShowBackgroundDrawable & TintableDrawable, BackgroundDrawableType extends IntrinsicPaddingDrawable & ShowBackgroundDrawable & TintableDrawable> extends LayerDrawable implements IntrinsicPaddingDrawable, MaterialProgressDrawable, ShowBackgroundDrawable, TintableDrawable {
    private float mBackgroundAlpha;
    private BackgroundDrawableType mBackgroundDrawable = ((IntrinsicPaddingDrawable) getDrawable(0));
    private ProgressDrawableType mProgressDrawable;
    private ProgressDrawableType mSecondaryProgressDrawable;

    public BaseProgressLayerDrawable(Drawable[] layers, Context context) {
        super(layers);
        this.mBackgroundAlpha = ThemeUtils.getFloatFromAttrRes(16842803, 0.0f, context);
        setId(0, 16908288);
        setId(1, 16908303);
        this.mSecondaryProgressDrawable = (IntrinsicPaddingDrawable) getDrawable(1);
        setId(2, 16908301);
        this.mProgressDrawable = (IntrinsicPaddingDrawable) getDrawable(2);
        setTint(ThemeUtils.getColorFromAttrRes(C2446R.attr.colorControlActivated, ViewCompat.MEASURED_STATE_MASK, context));
    }

    public boolean getShowBackground() {
        return ((ShowBackgroundDrawable) this.mBackgroundDrawable).getShowBackground();
    }

    public void setShowBackground(boolean show) {
        if (((ShowBackgroundDrawable) this.mBackgroundDrawable).getShowBackground() != show) {
            ((ShowBackgroundDrawable) this.mBackgroundDrawable).setShowBackground(show);
            ((ShowBackgroundDrawable) this.mSecondaryProgressDrawable).setShowBackground(!show);
        }
    }

    public boolean getUseIntrinsicPadding() {
        return this.mBackgroundDrawable.getUseIntrinsicPadding();
    }

    public void setUseIntrinsicPadding(boolean useIntrinsicPadding) {
        this.mBackgroundDrawable.setUseIntrinsicPadding(useIntrinsicPadding);
        this.mSecondaryProgressDrawable.setUseIntrinsicPadding(useIntrinsicPadding);
        this.mProgressDrawable.setUseIntrinsicPadding(useIntrinsicPadding);
    }

    public void setTint(int tintColor) {
        int backgroundTintColor = ColorUtils.setAlphaComponent(tintColor, Math.round(((float) Color.alpha(tintColor)) * this.mBackgroundAlpha));
        ((TintableDrawable) this.mBackgroundDrawable).setTint(backgroundTintColor);
        ((TintableDrawable) this.mSecondaryProgressDrawable).setTint(backgroundTintColor);
        ((TintableDrawable) this.mProgressDrawable).setTint(tintColor);
    }

    public void setTintList(ColorStateList tint) {
        ColorStateList backgroundTint;
        if (tint != null) {
            if (!tint.isOpaque()) {
                Log.w(getClass().getSimpleName(), "setTintList() called with a non-opaque ColorStateList, its original alpha will be discarded");
            }
            backgroundTint = tint.withAlpha(Math.round(this.mBackgroundAlpha * 255.0f));
        } else {
            backgroundTint = null;
        }
        ((TintableDrawable) this.mBackgroundDrawable).setTintList(backgroundTint);
        ((TintableDrawable) this.mSecondaryProgressDrawable).setTintList(backgroundTint);
        ((TintableDrawable) this.mProgressDrawable).setTintList(tint);
    }

    public void setTintMode(PorterDuff.Mode tintMode) {
        ((TintableDrawable) this.mBackgroundDrawable).setTintMode(tintMode);
        ((TintableDrawable) this.mSecondaryProgressDrawable).setTintMode(tintMode);
        ((TintableDrawable) this.mProgressDrawable).setTintMode(tintMode);
    }
}
