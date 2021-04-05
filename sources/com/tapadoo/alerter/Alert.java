package com.tapadoo.alerter;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import com.tapadoo.alerter.SwipeDismissTouchListener;
import com.tapadoo.android.C2264R;

public class Alert extends FrameLayout implements View.OnClickListener, Animation.AnimationListener, SwipeDismissTouchListener.DismissCallbacks {
    private static final int CLEAN_UP_DELAY_MILLIS = 100;
    private static final long DISPLAY_TIME_IN_SECONDS = 3000;
    private static final int MUL = -16777216;
    private long duration = DISPLAY_TIME_IN_SECONDS;
    private boolean enableIconPulse = true;
    private boolean enableInfiniteDuration;
    private boolean enableProgress;
    private FrameLayout flBackground;
    private FrameLayout flClickShield;
    private ImageView ivIcon;
    private boolean marginSet;
    private OnHideAlertListener onHideListener;
    private OnShowAlertListener onShowListener;
    private ProgressBar pbProgress;
    private ViewGroup rlContainer;
    private Runnable runningAnimation;
    private Animation slideInAnimation;
    private Animation slideOutAnimation;
    private TextView tvText;
    private TextView tvTitle;
    private boolean vibrationEnabled = true;

    public Alert(Context context) {
        super(context, (AttributeSet) null, C2264R.attr.alertStyle);
        initView();
    }

    public Alert(Context context, AttributeSet attrs) {
        super(context, attrs, C2264R.attr.alertStyle);
        initView();
    }

    public Alert(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        inflate(getContext(), C2264R.layout.alerter_alert_view, this);
        setHapticFeedbackEnabled(true);
        ViewCompat.setTranslationZ(this, 2.14748365E9f);
        this.flBackground = (FrameLayout) findViewById(C2264R.C2267id.flAlertBackground);
        this.flClickShield = (FrameLayout) findViewById(C2264R.C2267id.flClickShield);
        this.ivIcon = (ImageView) findViewById(C2264R.C2267id.ivIcon);
        this.tvTitle = (TextView) findViewById(C2264R.C2267id.tvTitle);
        this.tvText = (TextView) findViewById(C2264R.C2267id.tvText);
        this.rlContainer = (ViewGroup) findViewById(C2264R.C2267id.rlContainer);
        this.pbProgress = (ProgressBar) findViewById(C2264R.C2267id.pbProgress);
        this.flBackground.setOnClickListener(this);
        this.slideInAnimation = AnimationUtils.loadAnimation(getContext(), C2264R.anim.alerter_slide_in_from_top);
        this.slideOutAnimation = AnimationUtils.loadAnimation(getContext(), C2264R.anim.alerter_slide_out_to_top);
        this.slideInAnimation.setAnimationListener(this);
        setAnimation(this.slideInAnimation);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!this.marginSet) {
            this.marginSet = true;
            ((ViewGroup.MarginLayoutParams) getLayoutParams()).topMargin = getContext().getResources().getDimensionPixelSize(C2264R.dimen.alerter_alert_negative_margin_top);
            requestLayout();
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.slideInAnimation.setAnimationListener((Animation.AnimationListener) null);
    }

    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        return super.onTouchEvent(event);
    }

    public void onClick(View v) {
        hide();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.flBackground.setOnClickListener(listener);
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setVisibility(visibility);
        }
    }

    public void onAnimationStart(Animation animation) {
        if (!isInEditMode()) {
            if (this.vibrationEnabled) {
                performHapticFeedback(1);
            }
            setVisibility(0);
        }
    }

    public void onAnimationEnd(Animation animation) {
        if (this.enableIconPulse && this.ivIcon.getVisibility() == 0) {
            try {
                this.ivIcon.startAnimation(AnimationUtils.loadAnimation(getContext(), C2264R.anim.alerter_pulse));
            } catch (Exception ex) {
                Log.e(getClass().getSimpleName(), Log.getStackTraceString(ex));
            }
        }
        OnShowAlertListener onShowAlertListener = this.onShowListener;
        if (onShowAlertListener != null) {
            onShowAlertListener.onShow();
        }
        startHideAnimation();
    }

    private void startHideAnimation() {
        if (!this.enableInfiniteDuration) {
            C22551 r0 = new Runnable() {
                public void run() {
                    Alert.this.hide();
                }
            };
            this.runningAnimation = r0;
            postDelayed(r0, this.duration);
        }
        if (this.enableProgress && Build.VERSION.SDK_INT >= 11) {
            this.pbProgress.setVisibility(0);
            ValueAnimator valueAnimator = ValueAnimator.ofInt(new int[]{0, 100});
            valueAnimator.setDuration(getDuration());
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    Alert.this.getProgressBar().setProgress(((Integer) animation.getAnimatedValue()).intValue());
                }
            });
            valueAnimator.start();
        }
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void hide() {
        try {
            this.slideOutAnimation.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {
                    Alert.this.getAlertBackground().setOnClickListener((View.OnClickListener) null);
                    Alert.this.getAlertBackground().setClickable(false);
                }

                public void onAnimationEnd(Animation animation) {
                    Alert.this.removeFromParent();
                }

                public void onAnimationRepeat(Animation animation) {
                }
            });
            startAnimation(this.slideOutAnimation);
        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), Log.getStackTraceString(ex));
        }
    }

    /* access modifiers changed from: package-private */
    public void removeFromParent() {
        postDelayed(new Runnable() {
            public void run() {
                try {
                    if (Alert.this.getParent() == null) {
                        Log.e(getClass().getSimpleName(), "getParent() returning Null");
                        return;
                    }
                    try {
                        ((ViewGroup) Alert.this.getParent()).removeView(Alert.this);
                        if (Alert.this.getOnHideListener() != null) {
                            Alert.this.getOnHideListener().onHide();
                        }
                    } catch (Exception e) {
                        Log.e(getClass().getSimpleName(), "Cannot remove from parent layout");
                    }
                } catch (Exception ex) {
                    Log.e(getClass().getSimpleName(), Log.getStackTraceString(ex));
                }
            }
        }, 100);
    }

    public void setAlertBackgroundColor(int color) {
        this.flBackground.setBackgroundColor(color);
    }

    public void setAlertBackgroundResource(int resource) {
        this.flBackground.setBackgroundResource(resource);
    }

    public void setAlertBackgroundDrawable(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 16) {
            this.flBackground.setBackground(drawable);
        } else {
            this.flBackground.setBackgroundDrawable(drawable);
        }
    }

    public void setTitle(int titleId) {
        setTitle(getContext().getString(titleId));
    }

    public void setText(int textId) {
        setText(getContext().getString(textId));
    }

    public int getContentGravity() {
        return ((FrameLayout.LayoutParams) this.rlContainer.getLayoutParams()).gravity;
    }

    public void setContentGravity(int contentGravity) {
        LinearLayout.LayoutParams paramsTitle = (LinearLayout.LayoutParams) this.tvTitle.getLayoutParams();
        paramsTitle.gravity = contentGravity;
        this.tvTitle.setLayoutParams(paramsTitle);
        LinearLayout.LayoutParams paramsText = (LinearLayout.LayoutParams) this.tvText.getLayoutParams();
        paramsText.gravity = contentGravity;
        this.tvText.setLayoutParams(paramsText);
    }

    public void disableOutsideTouch() {
        this.flClickShield.setClickable(true);
    }

    public FrameLayout getAlertBackground() {
        return this.flBackground;
    }

    public TextView getTitle() {
        return this.tvTitle;
    }

    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            this.tvTitle.setVisibility(0);
            this.tvTitle.setText(title);
        }
    }

    public void setTitleAppearance(int textAppearance) {
        if (Build.VERSION.SDK_INT >= 23) {
            this.tvTitle.setTextAppearance(textAppearance);
            return;
        }
        TextView textView = this.tvTitle;
        textView.setTextAppearance(textView.getContext(), textAppearance);
    }

    public void setTitleTypeface(Typeface typeface) {
        this.tvTitle.setTypeface(typeface);
    }

    public void setTextTypeface(Typeface typeface) {
        this.tvText.setTypeface(typeface);
    }

    public TextView getText() {
        return this.tvText;
    }

    public void setText(String text) {
        if (!TextUtils.isEmpty(text)) {
            this.tvText.setVisibility(0);
            this.tvText.setText(text);
        }
    }

    public void setTextAppearance(int textAppearance) {
        if (Build.VERSION.SDK_INT >= 23) {
            this.tvText.setTextAppearance(textAppearance);
            return;
        }
        TextView textView = this.tvText;
        textView.setTextAppearance(textView.getContext(), textAppearance);
    }

    public ImageView getIcon() {
        return this.ivIcon;
    }

    public void setIcon(int iconId) {
        this.ivIcon.setImageDrawable(AppCompatResources.getDrawable(getContext(), iconId));
    }

    public void setIconColorFilter(int color) {
        ImageView imageView = this.ivIcon;
        if (imageView != null) {
            imageView.setColorFilter(color);
        }
    }

    public void setIconColorFilter(ColorFilter colorFilter) {
        ImageView imageView = this.ivIcon;
        if (imageView != null) {
            imageView.setColorFilter(colorFilter);
        }
    }

    public void setIconColorFilter(int color, PorterDuff.Mode mode) {
        ImageView imageView = this.ivIcon;
        if (imageView != null) {
            imageView.setColorFilter(color, mode);
        }
    }

    public void setIcon(Bitmap bitmap) {
        this.ivIcon.setImageBitmap(bitmap);
    }

    public void setIcon(Drawable drawable) {
        this.ivIcon.setImageDrawable(drawable);
    }

    public void showIcon(boolean showIcon) {
        this.ivIcon.setVisibility(showIcon ? 0 : 8);
    }

    public void enableSwipeToDismiss() {
        this.flBackground.setOnTouchListener(new SwipeDismissTouchListener(this.flBackground, (Object) null, this));
    }

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(long duration2) {
        this.duration = duration2;
    }

    public void pulseIcon(boolean shouldPulse) {
        this.enableIconPulse = shouldPulse;
    }

    public void setEnableInfiniteDuration(boolean enableInfiniteDuration2) {
        this.enableInfiniteDuration = enableInfiniteDuration2;
    }

    public void setEnableProgress(boolean enableProgress2) {
        this.enableProgress = enableProgress2;
    }

    public void setProgressColorRes(int color) {
        this.pbProgress.getProgressDrawable().setColorFilter(new LightingColorFilter(-16777216, ContextCompat.getColor(getContext(), color)));
    }

    public void setProgressColorInt(int color) {
        this.pbProgress.getProgressDrawable().setColorFilter(new LightingColorFilter(-16777216, color));
    }

    public void setOnShowListener(OnShowAlertListener listener) {
        this.onShowListener = listener;
    }

    public void setOnHideListener(OnHideAlertListener listener) {
        this.onHideListener = listener;
    }

    public void setVibrationEnabled(boolean vibrationEnabled2) {
        this.vibrationEnabled = vibrationEnabled2;
    }

    public boolean canDismiss(Object token) {
        return true;
    }

    public void onDismiss(View view, Object token) {
        this.flClickShield.removeView(this.flBackground);
    }

    public void onTouch(View view, boolean touch) {
        if (touch) {
            removeCallbacks(this.runningAnimation);
        } else {
            startHideAnimation();
        }
    }

    public ProgressBar getProgressBar() {
        return this.pbProgress;
    }

    public OnHideAlertListener getOnHideListener() {
        return this.onHideListener;
    }
}
