package p015me.zhanghai.android.materialprogressbar;

import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.core.view.ViewCompat;

/* renamed from: me.zhanghai.android.materialprogressbar.BasePaintDrawable */
abstract class BasePaintDrawable extends BaseDrawable {
    private Paint mPaint;

    /* access modifiers changed from: protected */
    public abstract void onDraw(Canvas canvas, int i, int i2, Paint paint);

    /* access modifiers changed from: protected */
    public abstract void onPreparePaint(Paint paint);

    BasePaintDrawable() {
    }

    /* access modifiers changed from: protected */
    public final void onDraw(Canvas canvas, int width, int height) {
        if (this.mPaint == null) {
            Paint paint = new Paint();
            this.mPaint = paint;
            paint.setAntiAlias(true);
            this.mPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
            onPreparePaint(this.mPaint);
        }
        this.mPaint.setAlpha(this.mAlpha);
        this.mPaint.setColorFilter(getColorFilterForDrawing());
        onDraw(canvas, width, height, this.mPaint);
    }
}
