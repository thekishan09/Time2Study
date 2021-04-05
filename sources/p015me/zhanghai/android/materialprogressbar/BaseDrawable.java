package p015me.zhanghai.android.materialprogressbar;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/* renamed from: me.zhanghai.android.materialprogressbar.BaseDrawable */
abstract class BaseDrawable extends Drawable implements TintableDrawable {
    protected int mAlpha = 255;
    protected ColorFilter mColorFilter;
    private DummyConstantState mConstantState = new DummyConstantState();
    protected PorterDuffColorFilter mTintFilter;
    protected ColorStateList mTintList;
    protected PorterDuff.Mode mTintMode = PorterDuff.Mode.SRC_IN;

    /* access modifiers changed from: protected */
    public abstract void onDraw(Canvas canvas, int i, int i2);

    BaseDrawable() {
    }

    public int getAlpha() {
        return this.mAlpha;
    }

    public void setAlpha(int alpha) {
        if (this.mAlpha != alpha) {
            this.mAlpha = alpha;
            invalidateSelf();
        }
    }

    public ColorFilter getColorFilter() {
        return this.mColorFilter;
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mColorFilter = colorFilter;
        invalidateSelf();
    }

    public void setTint(int tintColor) {
        setTintList(ColorStateList.valueOf(tintColor));
    }

    public void setTintList(ColorStateList tint) {
        this.mTintList = tint;
        if (updateTintFilter()) {
            invalidateSelf();
        }
    }

    public void setTintMode(PorterDuff.Mode tintMode) {
        this.mTintMode = tintMode;
        if (updateTintFilter()) {
            invalidateSelf();
        }
    }

    public boolean isStateful() {
        ColorStateList colorStateList = this.mTintList;
        return colorStateList != null && colorStateList.isStateful();
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] state) {
        return updateTintFilter();
    }

    private boolean updateTintFilter() {
        ColorStateList colorStateList = this.mTintList;
        boolean z = true;
        if (colorStateList == null || this.mTintMode == null) {
            if (this.mTintFilter == null) {
                z = false;
            }
            boolean hadTintFilter = z;
            this.mTintFilter = null;
            return hadTintFilter;
        }
        this.mTintFilter = new PorterDuffColorFilter(colorStateList.getColorForState(getState(), 0), this.mTintMode);
        return true;
    }

    public int getOpacity() {
        return -3;
    }

    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        if (bounds.width() != 0 && bounds.height() != 0) {
            int saveCount = canvas.save();
            canvas.translate((float) bounds.left, (float) bounds.top);
            onDraw(canvas, bounds.width(), bounds.height());
            canvas.restoreToCount(saveCount);
        }
    }

    /* access modifiers changed from: protected */
    public ColorFilter getColorFilterForDrawing() {
        ColorFilter colorFilter = this.mColorFilter;
        return colorFilter != null ? colorFilter : this.mTintFilter;
    }

    public Drawable.ConstantState getConstantState() {
        return this.mConstantState;
    }

    /* renamed from: me.zhanghai.android.materialprogressbar.BaseDrawable$DummyConstantState */
    private class DummyConstantState extends Drawable.ConstantState {
        private DummyConstantState() {
        }

        public int getChangingConfigurations() {
            return 0;
        }

        public Drawable newDrawable() {
            return BaseDrawable.this;
        }
    }
}
