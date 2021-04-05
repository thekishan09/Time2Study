package com.google.android.material.progressindicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

final class LinearDrawingDelegate implements DrawingDelegate {
    private float trackLength = 300.0f;

    LinearDrawingDelegate() {
    }

    public int getPreferredWidth(ProgressIndicatorSpec spec) {
        return -1;
    }

    public int getPreferredHeight(ProgressIndicatorSpec spec) {
        return spec.indicatorWidth;
    }

    public void adjustCanvas(Canvas canvas, ProgressIndicatorSpec spec, float widthFraction) {
        Rect clipBounds = canvas.getClipBounds();
        this.trackLength = (float) clipBounds.width();
        float trackWidth = (float) spec.indicatorWidth;
        canvas.translate(((float) clipBounds.width()) / 2.0f, (((float) clipBounds.height()) / 2.0f) + Math.max(0.0f, ((float) (clipBounds.height() - spec.indicatorWidth)) / 2.0f));
        if (spec.inverse) {
            canvas.scale(-1.0f, 1.0f);
        }
        if (spec.growMode == 2) {
            canvas.scale(1.0f, -1.0f);
        }
        if (spec.growMode == 1 || spec.growMode == 2) {
            canvas.translate(0.0f, (((float) spec.indicatorWidth) * (widthFraction - 1.0f)) / 2.0f);
        }
        float f = this.trackLength;
        canvas.clipRect((-f) / 2.0f, (-trackWidth) / 2.0f, f / 2.0f, trackWidth / 2.0f);
    }

    public void fillTrackWithColor(Canvas canvas, Paint paint, int color, float startFraction, float endFraction, float trackWidth, float cornerRadius) {
        float f;
        PointF rightBottomCornerCenter;
        PointF leftTopCornerCenter;
        Paint paint2 = paint;
        float f2 = trackWidth;
        float f3 = cornerRadius;
        if (startFraction != endFraction) {
            paint2.setStyle(Paint.Style.FILL);
            paint2.setAntiAlias(true);
            paint.setColor(color);
            float f4 = this.trackLength;
            PointF leftTopCornerCenter2 = new PointF(((-f4) / 2.0f) + f3 + ((f4 - (f3 * 2.0f)) * startFraction), ((-f2) / 2.0f) + f3);
            float f5 = this.trackLength;
            PointF rightBottomCornerCenter2 = new PointF(((-f5) / 2.0f) + f3 + ((f5 - (f3 * 2.0f)) * endFraction), (f2 / 2.0f) - f3);
            if (f3 > 0.0f) {
                RectF cornerPatternRectBound = new RectF(-f3, -f3, f3, f3);
                drawRoundedCorner(canvas, paint, leftTopCornerCenter2.x, leftTopCornerCenter2.y, 180.0f, 90.0f, cornerPatternRectBound);
                Canvas canvas2 = canvas;
                Paint paint3 = paint;
                PointF rightBottomCornerCenter3 = rightBottomCornerCenter2;
                PointF leftTopCornerCenter3 = leftTopCornerCenter2;
                float f6 = f2;
                RectF rectF = cornerPatternRectBound;
                drawRoundedCorner(canvas2, paint3, leftTopCornerCenter2.x, rightBottomCornerCenter2.y, 180.0f, -90.0f, rectF);
                drawRoundedCorner(canvas2, paint3, rightBottomCornerCenter3.x, leftTopCornerCenter3.y, 0.0f, -90.0f, rectF);
                drawRoundedCorner(canvas2, paint3, rightBottomCornerCenter3.x, rightBottomCornerCenter3.y, 0.0f, 90.0f, rectF);
                if (f6 > cornerRadius * 2.0f) {
                    float f7 = leftTopCornerCenter3.x - cornerRadius;
                    float f8 = leftTopCornerCenter3.y;
                    f = f6;
                    float f9 = leftTopCornerCenter3.x;
                    leftTopCornerCenter = leftTopCornerCenter3;
                    float f10 = rightBottomCornerCenter3.y;
                    rightBottomCornerCenter = rightBottomCornerCenter3;
                    Paint paint4 = paint;
                    canvas.drawRect(f7, f8, f9, f10, paint4);
                    canvas.drawRect(rightBottomCornerCenter.x, leftTopCornerCenter.y, rightBottomCornerCenter.x + cornerRadius, rightBottomCornerCenter.y, paint4);
                } else {
                    f = f6;
                    leftTopCornerCenter = leftTopCornerCenter3;
                    rightBottomCornerCenter = rightBottomCornerCenter3;
                }
            } else {
                rightBottomCornerCenter = rightBottomCornerCenter2;
                leftTopCornerCenter = leftTopCornerCenter2;
                f = f2;
            }
            canvas.drawRect(leftTopCornerCenter.x, (-f) / 2.0f, rightBottomCornerCenter.x, f / 2.0f, paint);
        }
    }

    private static void drawRoundedCorner(Canvas canvas, Paint paint, float centerX, float centerY, float startAngle, float sweepAngle, RectF cornerPatternRectBound) {
        canvas.save();
        canvas.translate(centerX, centerY);
        canvas.drawArc(cornerPatternRectBound, startAngle, sweepAngle, true, paint);
        canvas.restore();
    }
}
