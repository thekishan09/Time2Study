package com.google.android.material.transition.platform;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.View;
import com.google.android.material.shape.AbsoluteCornerSize;
import com.google.android.material.shape.CornerSize;
import com.google.android.material.shape.RelativeCornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;

class TransitionUtils {
    private static final RectF transformAlphaRectF = new RectF();

    interface CanvasOperation {
        void run(Canvas canvas);
    }

    interface CornerSizeBinaryOperator {
        CornerSize apply(CornerSize cornerSize, CornerSize cornerSize2);
    }

    private TransitionUtils() {
    }

    static ShapeAppearanceModel convertToRelativeCornerSizes(ShapeAppearanceModel shapeAppearanceModel, final RectF bounds) {
        return shapeAppearanceModel.withTransformedCornerSizes(new ShapeAppearanceModel.CornerSizeUnaryOperator() {
            public CornerSize apply(CornerSize cornerSize) {
                if (cornerSize instanceof RelativeCornerSize) {
                    return cornerSize;
                }
                return new RelativeCornerSize(cornerSize.getCornerSize(bounds) / bounds.height());
            }
        });
    }

    static ShapeAppearanceModel transformCornerSizes(ShapeAppearanceModel shapeAppearanceModel1, ShapeAppearanceModel shapeAppearanceModel2, RectF shapeAppearanceModel1Bounds, CornerSizeBinaryOperator op) {
        return (isShapeAppearanceSignificant(shapeAppearanceModel1, shapeAppearanceModel1Bounds) ? shapeAppearanceModel1 : shapeAppearanceModel2).toBuilder().setTopLeftCornerSize(op.apply(shapeAppearanceModel1.getTopLeftCornerSize(), shapeAppearanceModel2.getTopLeftCornerSize())).setTopRightCornerSize(op.apply(shapeAppearanceModel1.getTopRightCornerSize(), shapeAppearanceModel2.getTopRightCornerSize())).setBottomLeftCornerSize(op.apply(shapeAppearanceModel1.getBottomLeftCornerSize(), shapeAppearanceModel2.getBottomLeftCornerSize())).setBottomRightCornerSize(op.apply(shapeAppearanceModel1.getBottomRightCornerSize(), shapeAppearanceModel2.getBottomRightCornerSize())).build();
    }

    private static boolean isShapeAppearanceSignificant(ShapeAppearanceModel shapeAppearanceModel, RectF bounds) {
        return (shapeAppearanceModel.getTopLeftCornerSize().getCornerSize(bounds) == 0.0f && shapeAppearanceModel.getTopRightCornerSize().getCornerSize(bounds) == 0.0f && shapeAppearanceModel.getBottomRightCornerSize().getCornerSize(bounds) == 0.0f && shapeAppearanceModel.getBottomLeftCornerSize().getCornerSize(bounds) == 0.0f) ? false : true;
    }

    static float lerp(float startValue, float endValue, float fraction) {
        return ((endValue - startValue) * fraction) + startValue;
    }

    static float lerp(float startValue, float endValue, float startFraction, float endFraction, float fraction) {
        if (fraction < startFraction) {
            return startValue;
        }
        if (fraction > endFraction) {
            return endValue;
        }
        return lerp(startValue, endValue, (fraction - startFraction) / (endFraction - startFraction));
    }

    static int lerp(int startValue, int endValue, float startFraction, float endFraction, float fraction) {
        if (fraction < startFraction) {
            return startValue;
        }
        if (fraction > endFraction) {
            return endValue;
        }
        return (int) lerp((float) startValue, (float) endValue, (fraction - startFraction) / (endFraction - startFraction));
    }

    static ShapeAppearanceModel lerp(ShapeAppearanceModel startValue, ShapeAppearanceModel endValue, RectF startBounds, RectF endBounds, float startFraction, float endFraction, float fraction) {
        if (fraction < startFraction) {
            return startValue;
        }
        if (fraction > endFraction) {
            return endValue;
        }
        final RectF rectF = startBounds;
        final RectF rectF2 = endBounds;
        final float f = startFraction;
        final float f2 = endFraction;
        final float f3 = fraction;
        return transformCornerSizes(startValue, endValue, startBounds, new CornerSizeBinaryOperator() {
            public CornerSize apply(CornerSize cornerSize1, CornerSize cornerSize2) {
                return new AbsoluteCornerSize(TransitionUtils.lerp(cornerSize1.getCornerSize(rectF), cornerSize2.getCornerSize(rectF2), f, f2, f3));
            }
        });
    }

    static Shader createColorShader(int color) {
        return new LinearGradient(0.0f, 0.0f, 0.0f, 0.0f, color, color, Shader.TileMode.CLAMP);
    }

    static View findDescendantOrAncestorById(View view, int viewId) {
        View descendant = view.findViewById(viewId);
        if (descendant != null) {
            return descendant;
        }
        return findAncestorById(view, viewId);
    }

    /* JADX WARNING: type inference failed for: r1v2, types: [android.view.ViewParent] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static android.view.View findAncestorById(android.view.View r4, int r5) {
        /*
            android.content.res.Resources r0 = r4.getResources()
            java.lang.String r0 = r0.getResourceName(r5)
        L_0x0008:
            if (r4 == 0) goto L_0x001d
            int r1 = r4.getId()
            if (r1 != r5) goto L_0x0011
            return r4
        L_0x0011:
            android.view.ViewParent r1 = r4.getParent()
            boolean r2 = r1 instanceof android.view.View
            if (r2 == 0) goto L_0x001d
            r4 = r1
            android.view.View r4 = (android.view.View) r4
            goto L_0x0008
        L_0x001d:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r0)
            java.lang.String r3 = " is not a valid ancestor"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            goto L_0x0035
        L_0x0034:
            throw r1
        L_0x0035:
            goto L_0x0034
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.transition.platform.TransitionUtils.findAncestorById(android.view.View, int):android.view.View");
    }

    static RectF getRelativeBounds(View view) {
        return new RectF((float) view.getLeft(), (float) view.getTop(), (float) view.getRight(), (float) view.getBottom());
    }

    static Rect getRelativeBoundsRect(View view) {
        return new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    }

    static RectF getLocationOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        return new RectF((float) left, (float) top, (float) (view.getWidth() + left), (float) (view.getHeight() + top));
    }

    static <T> T defaultIfNull(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }

    static float calculateArea(RectF bounds) {
        return bounds.width() * bounds.height();
    }

    private static int saveLayerAlphaCompat(Canvas canvas, Rect bounds, int alpha) {
        transformAlphaRectF.set(bounds);
        if (Build.VERSION.SDK_INT >= 21) {
            return canvas.saveLayerAlpha(transformAlphaRectF, alpha);
        }
        return canvas.saveLayerAlpha(transformAlphaRectF.left, transformAlphaRectF.top, transformAlphaRectF.right, transformAlphaRectF.bottom, alpha, 31);
    }

    static void transform(Canvas canvas, Rect bounds, float dx, float dy, float scale, int alpha, CanvasOperation op) {
        if (alpha > 0) {
            int checkpoint = canvas.save();
            canvas.translate(dx, dy);
            canvas.scale(scale, scale);
            if (alpha < 255) {
                saveLayerAlphaCompat(canvas, bounds, alpha);
            }
            op.run(canvas);
            canvas.restoreToCount(checkpoint);
        }
    }

    static void maybeAddTransition(TransitionSet transitionSet, Transition transition) {
        if (transition != null) {
            transitionSet.addTransition(transition);
        }
    }

    static void maybeRemoveTransition(TransitionSet transitionSet, Transition transition) {
        if (transition != null) {
            transitionSet.removeTransition(transition);
        }
    }
}
