package p015me.zhanghai.android.materialprogressbar;

import android.content.Context;
import android.graphics.Canvas;

/* renamed from: me.zhanghai.android.materialprogressbar.HorizontalProgressBackgroundDrawable */
class HorizontalProgressBackgroundDrawable extends BaseSingleHorizontalProgressDrawable implements ShowBackgroundDrawable {
    private boolean mShow = true;

    public HorizontalProgressBackgroundDrawable(Context context) {
        super(context);
    }

    public boolean getShowBackground() {
        return this.mShow;
    }

    public void setShowBackground(boolean show) {
        if (this.mShow != show) {
            this.mShow = show;
            invalidateSelf();
        }
    }

    public void draw(Canvas canvas) {
        if (this.mShow) {
            super.draw(canvas);
        }
    }
}
