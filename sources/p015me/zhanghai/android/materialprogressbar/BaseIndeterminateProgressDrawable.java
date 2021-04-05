package p015me.zhanghai.android.materialprogressbar;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Animatable;
import androidx.core.view.ViewCompat;
import p015me.zhanghai.android.materialprogressbar.internal.ThemeUtils;

/* renamed from: me.zhanghai.android.materialprogressbar.BaseIndeterminateProgressDrawable */
abstract class BaseIndeterminateProgressDrawable extends BaseProgressDrawable implements Animatable {
    protected Animator[] mAnimators;

    public BaseIndeterminateProgressDrawable(Context context) {
        setTint(ThemeUtils.getColorFromAttrRes(C2446R.attr.colorControlActivated, ViewCompat.MEASURED_STATE_MASK, context));
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (isStarted()) {
            invalidateSelf();
        }
    }

    public void start() {
        if (!isStarted()) {
            for (Animator animator : this.mAnimators) {
                animator.start();
            }
            invalidateSelf();
        }
    }

    private boolean isStarted() {
        for (Animator animator : this.mAnimators) {
            if (animator.isStarted()) {
                return true;
            }
        }
        return false;
    }

    public void stop() {
        for (Animator animator : this.mAnimators) {
            animator.end();
        }
    }

    public boolean isRunning() {
        for (Animator animator : this.mAnimators) {
            if (animator.isRunning()) {
                return true;
            }
        }
        return false;
    }
}
