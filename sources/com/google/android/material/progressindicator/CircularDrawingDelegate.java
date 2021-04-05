package com.google.android.material.progressindicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public final class CircularDrawingDelegate implements DrawingDelegate {
    private RectF arcBound = new RectF();
    private int arcInverseFactor = 1;

    public int getPreferredWidth(ProgressIndicatorSpec spec) {
        return getSize(spec);
    }

    public int getPreferredHeight(ProgressIndicatorSpec spec) {
        return getSize(spec);
    }

    private static int getSize(ProgressIndicatorSpec spec) {
        return (spec.circularRadius * 2) + spec.indicatorWidth + (spec.circularInset * 2);
    }

    public void adjustCanvas(Canvas canvas, ProgressIndicatorSpec spec, float widthFraction) {
        int outerRadiusWithInset = spec.circularRadius + (spec.indicatorWidth / 2) + spec.circularInset;
        canvas.translate((float) outerRadiusWithInset, (float) outerRadiusWithInset);
        canvas.rotate(-90.0f);
        canvas.clipRect(-outerRadiusWithInset, -outerRadiusWithInset, outerRadiusWithInset, outerRadiusWithInset);
        float adjustedRadius = (float) spec.circularRadius;
        int i = 1;
        if (spec.growMode == 1) {
            adjustedRadius += ((1.0f - widthFraction) * ((float) spec.indicatorWidth)) / 2.0f;
        } else if (spec.growMode == 2) {
            adjustedRadius -= ((1.0f - widthFraction) * ((float) spec.indicatorWidth)) / 2.0f;
        }
        this.arcBound = new RectF(-adjustedRadius, -adjustedRadius, adjustedRadius, adjustedRadius);
        if (spec.inverse) {
            i = -1;
        }
        this.arcInverseFactor = i;
    }

    public void fillTrackWithColor(Canvas canvas, Paint paint, int color, float startFraction, float endFraction, float trackWidth, float cornerRadius) {
        Paint paint2 = paint;
        float f = cornerRadius;
        if (startFraction != endFraction) {
            paint2.setStyle(Paint.Style.STROKE);
            paint2.setStrokeCap(Paint.Cap.BUTT);
            paint2.setAntiAlias(true);
            paint.setColor(color);
            paint2.setStrokeWidth(trackWidth);
            int i = this.arcInverseFactor;
            float startDegree = startFraction * 360.0f * ((float) i);
            float arcDegree = (endFraction >= startFraction ? endFraction - startFraction : (endFraction + 1.0f) - startFraction) * 360.0f * ((float) i);
            canvas.drawArc(this.arcBound, startDegree, arcDegree, false, paint);
            if (f > 0.0f && Math.abs(arcDegree) < 360.0f) {
                paint2.setStyle(Paint.Style.FILL);
                RectF cornerPatternRectBound = new RectF(-f, -f, f, f);
                drawRoundedEnd(canvas, paint, trackWidth, cornerRadius, startDegree, true, cornerPatternRectBound);
                drawRoundedEnd(canvas, paint, trackWidth, cornerRadius, startDegree + arcDegree, false, cornerPatternRectBound);
            }
        }
    }

    private void drawRoundedEnd(Canvas canvas, Paint paint, float trackWidth, float cornerRadius, float positionInDeg, boolean isStartPosition, RectF cornerPatternRectBound) {
        Canvas canvas2 = canvas;
        float startOrEndFactor = isStartPosition ? -1.0f : 1.0f;
        canvas.save();
        canvas.rotate(positionInDeg);
        Paint paint2 = paint;
        canvas.drawRect((this.arcBound.right - (trackWidth / 2.0f)) + cornerRadius, Math.min(0.0f, startOrEndFactor * cornerRadius * ((float) this.arcInverseFactor)), (this.arcBound.right + (trackWidth / 2.0f)) - cornerRadius, Math.max(0.0f, startOrEndFactor * cornerRadius * ((float) this.arcInverseFactor)), paint2);
        canvas.translate((this.arcBound.right - (trackWidth / 2.0f)) + cornerRadius, 0.0f);
        RectF rectF = cornerPatternRectBound;
        canvas.drawArc(rectF, 180.0f, (-startOrEndFactor) * 90.0f * ((float) this.arcInverseFactor), true, paint2);
        canvas.translate(trackWidth - (cornerRadius * 2.0f), 0.0f);
        canvas.drawArc(rectF, 0.0f, 90.0f * startOrEndFactor * ((float) this.arcInverseFactor), true, paint2);
        canvas.restore();
    }
}
