package p015me.zhanghai.android.materialprogressbar;

import android.graphics.Canvas;
import android.graphics.Paint;

/* renamed from: me.zhanghai.android.materialprogressbar.CircularProgressBackgroundDrawable */
class CircularProgressBackgroundDrawable extends BaseSingleCircularProgressDrawable implements ShowBackgroundDrawable {
    private boolean mShow = true;

    CircularProgressBackgroundDrawable() {
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

    /* access modifiers changed from: protected */
    public void onDrawRing(Canvas canvas, Paint paint) {
        drawRing(canvas, paint, 0.0f, 360.0f);
    }
}
