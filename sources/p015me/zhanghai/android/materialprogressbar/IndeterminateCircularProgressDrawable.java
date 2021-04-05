package p015me.zhanghai.android.materialprogressbar;

import android.animation.Animator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/* renamed from: me.zhanghai.android.materialprogressbar.IndeterminateCircularProgressDrawable */
public class IndeterminateCircularProgressDrawable extends BaseIndeterminateProgressDrawable implements MaterialProgressDrawable {
    private static final int PADDED_INTRINSIC_SIZE_DP = 48;
    private static final int PROGRESS_INTRINSIC_SIZE_DP = 42;
    private static final RectF RECT_BOUND = new RectF(-21.0f, -21.0f, 21.0f, 21.0f);
    private static final RectF RECT_PADDED_BOUND = new RectF(-24.0f, -24.0f, 24.0f, 24.0f);
    private static final RectF RECT_PROGRESS = new RectF(-19.0f, -19.0f, 19.0f, 19.0f);
    private int mPaddedIntrinsicSize;
    private int mProgressIntrinsicSize;
    private RingPathTransform mRingPathTransform = new RingPathTransform();
    private RingRotation mRingRotation = new RingRotation();

    public /* bridge */ /* synthetic */ void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public /* bridge */ /* synthetic */ int getAlpha() {
        return super.getAlpha();
    }

    public /* bridge */ /* synthetic */ ColorFilter getColorFilter() {
        return super.getColorFilter();
    }

    public /* bridge */ /* synthetic */ Drawable.ConstantState getConstantState() {
        return super.getConstantState();
    }

    public /* bridge */ /* synthetic */ int getOpacity() {
        return super.getOpacity();
    }

    public /* bridge */ /* synthetic */ boolean getUseIntrinsicPadding() {
        return super.getUseIntrinsicPadding();
    }

    public /* bridge */ /* synthetic */ boolean isRunning() {
        return super.isRunning();
    }

    public /* bridge */ /* synthetic */ boolean isStateful() {
        return super.isStateful();
    }

    public /* bridge */ /* synthetic */ void setAlpha(int i) {
        super.setAlpha(i);
    }

    public /* bridge */ /* synthetic */ void setColorFilter(ColorFilter colorFilter) {
        super.setColorFilter(colorFilter);
    }

    public /* bridge */ /* synthetic */ void setTint(int i) {
        super.setTint(i);
    }

    public /* bridge */ /* synthetic */ void setTintList(ColorStateList colorStateList) {
        super.setTintList(colorStateList);
    }

    public /* bridge */ /* synthetic */ void setTintMode(PorterDuff.Mode mode) {
        super.setTintMode(mode);
    }

    public /* bridge */ /* synthetic */ void setUseIntrinsicPadding(boolean z) {
        super.setUseIntrinsicPadding(z);
    }

    public /* bridge */ /* synthetic */ void start() {
        super.start();
    }

    public /* bridge */ /* synthetic */ void stop() {
        super.stop();
    }

    public IndeterminateCircularProgressDrawable(Context context) {
        super(context);
        float density = context.getResources().getDisplayMetrics().density;
        this.mProgressIntrinsicSize = Math.round(42.0f * density);
        this.mPaddedIntrinsicSize = Math.round(48.0f * density);
        this.mAnimators = new Animator[]{Animators.createIndeterminate(this.mRingPathTransform), Animators.createIndeterminateRotation(this.mRingRotation)};
    }

    private int getIntrinsicSize() {
        return this.mUseIntrinsicPadding ? this.mPaddedIntrinsicSize : this.mProgressIntrinsicSize;
    }

    public int getIntrinsicWidth() {
        return getIntrinsicSize();
    }

    public int getIntrinsicHeight() {
        return getIntrinsicSize();
    }

    /* access modifiers changed from: protected */
    public void onPreparePaint(Paint paint) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4.0f);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        paint.setStrokeJoin(Paint.Join.MITER);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas, int width, int height, Paint paint) {
        if (this.mUseIntrinsicPadding) {
            canvas.scale(((float) width) / RECT_PADDED_BOUND.width(), ((float) height) / RECT_PADDED_BOUND.height());
            canvas.translate(RECT_PADDED_BOUND.width() / 2.0f, RECT_PADDED_BOUND.height() / 2.0f);
        } else {
            canvas.scale(((float) width) / RECT_BOUND.width(), ((float) height) / RECT_BOUND.height());
            canvas.translate(RECT_BOUND.width() / 2.0f, RECT_BOUND.height() / 2.0f);
        }
        drawRing(canvas, paint);
    }

    private void drawRing(Canvas canvas, Paint paint) {
        int saveCount = canvas.save();
        canvas.rotate(this.mRingRotation.mRotation);
        canvas.drawArc(RECT_PROGRESS, ((this.mRingPathTransform.mTrimPathOffset + this.mRingPathTransform.mTrimPathStart) * 360.0f) - 0.049804688f, (this.mRingPathTransform.mTrimPathEnd - this.mRingPathTransform.mTrimPathStart) * 360.0f, false, paint);
        canvas.restoreToCount(saveCount);
    }

    /* renamed from: me.zhanghai.android.materialprogressbar.IndeterminateCircularProgressDrawable$RingPathTransform */
    private static class RingPathTransform {
        public float mTrimPathEnd;
        public float mTrimPathOffset;
        public float mTrimPathStart;

        private RingPathTransform() {
        }

        public void setTrimPathStart(float trimPathStart) {
            this.mTrimPathStart = trimPathStart;
        }

        public void setTrimPathEnd(float trimPathEnd) {
            this.mTrimPathEnd = trimPathEnd;
        }

        public void setTrimPathOffset(float trimPathOffset) {
            this.mTrimPathOffset = trimPathOffset;
        }
    }

    /* renamed from: me.zhanghai.android.materialprogressbar.IndeterminateCircularProgressDrawable$RingRotation */
    private static class RingRotation {
        /* access modifiers changed from: private */
        public float mRotation;

        private RingRotation() {
        }

        public void setRotation(float rotation) {
            this.mRotation = rotation;
        }
    }
}
