package com.tapadoo.alerter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;

class SwipeDismissTouchListener implements View.OnTouchListener {
    private final long mAnimationTime;
    /* access modifiers changed from: private */
    public final DismissCallbacks mCallbacks;
    private float mDownX;
    private float mDownY;
    private final int mMinFlingVelocity;
    private final int mSlop;
    private boolean mSwiping;
    private int mSwipingSlop;
    /* access modifiers changed from: private */
    public Object mToken;
    private float mTranslationX;
    private VelocityTracker mVelocityTracker;
    /* access modifiers changed from: private */
    public final View mView;
    private int mViewWidth = 1;

    interface DismissCallbacks {
        boolean canDismiss(Object obj);

        void onDismiss(View view, Object obj);

        void onTouch(View view, boolean z);
    }

    SwipeDismissTouchListener(View view, Object token, DismissCallbacks callbacks) {
        ViewConfiguration vc = ViewConfiguration.get(view.getContext());
        this.mSlop = vc.getScaledTouchSlop();
        this.mMinFlingVelocity = vc.getScaledMinimumFlingVelocity() * 16;
        this.mAnimationTime = (long) view.getContext().getResources().getInteger(17694720);
        this.mView = view;
        this.mToken = token;
        this.mCallbacks = callbacks;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        VelocityTracker velocityTracker;
        View view2 = view;
        MotionEvent motionEvent2 = motionEvent;
        motionEvent2.offsetLocation(this.mTranslationX, 0.0f);
        if (this.mViewWidth < 2) {
            this.mViewWidth = this.mView.getWidth();
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mDownX = motionEvent.getRawX();
            this.mDownY = motionEvent.getRawY();
            if (this.mCallbacks.canDismiss(this.mToken)) {
                VelocityTracker obtain = VelocityTracker.obtain();
                this.mVelocityTracker = obtain;
                obtain.addMovement(motionEvent2);
            }
            this.mCallbacks.onTouch(view2, true);
            return false;
        } else if (actionMasked != 1) {
            if (actionMasked == 2) {
                VelocityTracker velocityTracker2 = this.mVelocityTracker;
                if (velocityTracker2 == null) {
                    return false;
                }
                velocityTracker2.addMovement(motionEvent2);
                float deltaX = motionEvent.getRawX() - this.mDownX;
                float deltaY = motionEvent.getRawY() - this.mDownY;
                if (Math.abs(deltaX) > ((float) this.mSlop) && Math.abs(deltaY) < Math.abs(deltaX) / 2.0f) {
                    this.mSwiping = true;
                    this.mSwipingSlop = deltaX > 0.0f ? this.mSlop : -this.mSlop;
                    this.mView.getParent().requestDisallowInterceptTouchEvent(true);
                    MotionEvent cancelEvent = MotionEvent.obtain(motionEvent);
                    cancelEvent.setAction(3 | (motionEvent.getActionIndex() << 8));
                    this.mView.onTouchEvent(cancelEvent);
                    cancelEvent.recycle();
                }
                if (!this.mSwiping) {
                    return false;
                }
                this.mTranslationX = deltaX;
                this.mView.setTranslationX(deltaX - ((float) this.mSwipingSlop));
                this.mView.setAlpha(Math.max(0.0f, Math.min(1.0f, 1.0f - ((Math.abs(deltaX) * 2.0f) / ((float) this.mViewWidth)))));
                return true;
            } else if (actionMasked != 3) {
                view.performClick();
                return false;
            } else if (this.mVelocityTracker == null) {
                return false;
            } else {
                this.mView.animate().translationX(0.0f).alpha(1.0f).setDuration(this.mAnimationTime).setListener((Animator.AnimatorListener) null);
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
                this.mTranslationX = 0.0f;
                this.mDownX = 0.0f;
                this.mDownY = 0.0f;
                this.mSwiping = false;
                return false;
            }
        } else if (this.mVelocityTracker == null) {
            return false;
        } else {
            float deltaX2 = motionEvent.getRawX() - this.mDownX;
            this.mVelocityTracker.addMovement(motionEvent2);
            this.mVelocityTracker.computeCurrentVelocity(1000);
            float velocityX = this.mVelocityTracker.getXVelocity();
            float absVelocityX = Math.abs(velocityX);
            float absVelocityY = Math.abs(this.mVelocityTracker.getYVelocity());
            boolean dismiss = false;
            boolean dismissRight = false;
            if (Math.abs(deltaX2) > ((float) (this.mViewWidth / 2)) && this.mSwiping) {
                dismiss = true;
                dismissRight = deltaX2 > 0.0f;
            } else if (((float) this.mMinFlingVelocity) <= absVelocityX && absVelocityY < absVelocityX && this.mSwiping) {
                dismiss = ((velocityX > 0.0f ? 1 : (velocityX == 0.0f ? 0 : -1)) < 0) == ((deltaX2 > 0.0f ? 1 : (deltaX2 == 0.0f ? 0 : -1)) < 0);
                dismissRight = this.mVelocityTracker.getXVelocity() > 0.0f;
            }
            if (dismiss) {
                ViewPropertyAnimator animate = this.mView.animate();
                int i = this.mViewWidth;
                if (!dismissRight) {
                    i = -i;
                }
                animate.translationX((float) i).alpha(0.0f).setDuration(this.mAnimationTime).setListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        SwipeDismissTouchListener.this.performDismiss();
                    }
                });
                velocityTracker = null;
            } else if (this.mSwiping) {
                velocityTracker = null;
                this.mView.animate().translationX(0.0f).alpha(1.0f).setDuration(this.mAnimationTime).setListener((Animator.AnimatorListener) null);
                this.mCallbacks.onTouch(view2, false);
            } else {
                velocityTracker = null;
            }
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = velocityTracker;
            this.mTranslationX = 0.0f;
            this.mDownX = 0.0f;
            this.mDownY = 0.0f;
            this.mSwiping = false;
            return false;
        }
    }

    /* access modifiers changed from: private */
    public void performDismiss() {
        final ViewGroup.LayoutParams lp = this.mView.getLayoutParams();
        final int originalHeight = this.mView.getHeight();
        ValueAnimator animator = ValueAnimator.ofInt(new int[]{originalHeight, 1}).setDuration(this.mAnimationTime);
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                SwipeDismissTouchListener.this.mCallbacks.onDismiss(SwipeDismissTouchListener.this.mView, SwipeDismissTouchListener.this.mToken);
                SwipeDismissTouchListener.this.mView.setAlpha(1.0f);
                SwipeDismissTouchListener.this.mView.setTranslationX(0.0f);
                lp.height = originalHeight;
                SwipeDismissTouchListener.this.mView.setLayoutParams(lp);
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                lp.height = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                SwipeDismissTouchListener.this.mView.setLayoutParams(lp);
            }
        });
        animator.start();
    }
}
