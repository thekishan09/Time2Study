package com.tapadoo.alerter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import com.tapadoo.android.C2264R;
import java.lang.ref.WeakReference;

public final class Alerter {
    private static WeakReference<Activity> activityWeakReference;
    private Alert alert;

    private Alerter() {
    }

    public static Alerter create(Activity activity) {
        if (activity != null) {
            Alerter alerter = new Alerter();
            clearCurrent(activity);
            alerter.setActivity(activity);
            alerter.setAlert(new Alert(activity));
            return alerter;
        }
        throw new IllegalArgumentException("Activity cannot be null!");
    }

    public static void clearCurrent(Activity activity) {
        if (activity != null) {
            try {
                ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
                for (int i = 0; i < decorView.getChildCount(); i++) {
                    Alert childView = decorView.getChildAt(i) instanceof Alert ? (Alert) decorView.getChildAt(i) : null;
                    if (!(childView == null || childView.getWindowToken() == null)) {
                        ViewCompat.animate(childView).alpha(0.0f).withEndAction(getRemoveViewRunnable(childView));
                    }
                }
            } catch (Exception ex) {
                Log.e(Alerter.class.getClass().getSimpleName(), Log.getStackTraceString(ex));
            }
        }
    }

    public static void hide() {
        WeakReference<Activity> weakReference = activityWeakReference;
        if (weakReference != null && weakReference.get() != null) {
            clearCurrent((Activity) activityWeakReference.get());
        }
    }

    private static Runnable getRemoveViewRunnable(final Alert childView) {
        return new Runnable() {
            public void run() {
                try {
                    if (childView != null) {
                        ((ViewGroup) childView.getParent()).removeView(childView);
                    }
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), Log.getStackTraceString(e));
                }
            }
        };
    }

    public static boolean isShowing() {
        WeakReference<Activity> weakReference = activityWeakReference;
        if (weakReference == null || weakReference.get() == null) {
            return false;
        }
        return ((Activity) activityWeakReference.get()).findViewById(C2264R.C2267id.flAlertBackground) != null;
    }

    public Alert show() {
        if (getActivityWeakReference() != null) {
            ((Activity) getActivityWeakReference().get()).runOnUiThread(new Runnable() {
                public void run() {
                    ViewGroup decorView = Alerter.this.getActivityDecorView();
                    if (decorView != null && Alerter.this.getAlert().getParent() == null) {
                        decorView.addView(Alerter.this.getAlert());
                    }
                }
            });
        }
        return getAlert();
    }

    public Alerter setTitle(int titleId) {
        if (getAlert() != null) {
            getAlert().setTitle(titleId);
        }
        return this;
    }

    public Alerter setTitle(String title) {
        if (getAlert() != null) {
            getAlert().setTitle(title);
        }
        return this;
    }

    public Alerter setTitleTypeface(Typeface typeface) {
        if (getAlert() != null) {
            getAlert().setTitleTypeface(typeface);
        }
        return this;
    }

    public Alerter setTitleAppearance(int textAppearance) {
        if (getAlert() != null) {
            getAlert().setTitleAppearance(textAppearance);
        }
        return this;
    }

    public Alerter setContentGravity(int gravity) {
        if (getAlert() != null) {
            getAlert().setContentGravity(gravity);
        }
        return this;
    }

    public Alerter setText(int textId) {
        if (getAlert() != null) {
            getAlert().setText(textId);
        }
        return this;
    }

    public Alerter setText(String text) {
        if (getAlert() != null) {
            getAlert().setText(text);
        }
        return this;
    }

    public Alerter setTextTypeface(Typeface typeface) {
        if (getAlert() != null) {
            getAlert().setTextTypeface(typeface);
        }
        return this;
    }

    public Alerter setTextAppearance(int textAppearance) {
        if (getAlert() != null) {
            getAlert().setTextAppearance(textAppearance);
        }
        return this;
    }

    public Alerter setBackgroundColorInt(int colorInt) {
        if (getAlert() != null) {
            getAlert().setAlertBackgroundColor(colorInt);
        }
        return this;
    }

    public Alerter setBackgroundColorRes(int colorResId) {
        if (!(getAlert() == null || getActivityWeakReference() == null)) {
            getAlert().setAlertBackgroundColor(ContextCompat.getColor((Context) getActivityWeakReference().get(), colorResId));
        }
        return this;
    }

    public Alerter setBackgroundDrawable(Drawable drawable) {
        if (getAlert() != null) {
            getAlert().setAlertBackgroundDrawable(drawable);
        }
        return this;
    }

    public Alerter setBackgroundResource(int drawableResId) {
        if (getAlert() != null) {
            getAlert().setAlertBackgroundResource(drawableResId);
        }
        return this;
    }

    public Alerter setIcon(int iconId) {
        if (getAlert() != null) {
            getAlert().setIcon(iconId);
        }
        return this;
    }

    public Alerter setIcon(Bitmap bitmap) {
        if (getAlert() != null) {
            getAlert().setIcon(bitmap);
        }
        return this;
    }

    public Alerter setIcon(Drawable drawable) {
        if (getAlert() != null) {
            getAlert().setIcon(drawable);
        }
        return this;
    }

    public Alerter setIconColorFilter(int color) {
        if (getAlert() != null) {
            getAlert().setIconColorFilter(color);
        }
        return this;
    }

    public Alerter setIconColorFilter(ColorFilter colorFilter) {
        if (getAlert() != null) {
            getAlert().setIconColorFilter(colorFilter);
        }
        return this;
    }

    public Alerter setIconColorFilter(int color, PorterDuff.Mode mode) {
        if (getAlert() != null) {
            getAlert().setIconColorFilter(color, mode);
        }
        return this;
    }

    public Alerter hideIcon() {
        if (getAlert() != null) {
            getAlert().getIcon().setVisibility(8);
        }
        return this;
    }

    public Alerter setOnClickListener(View.OnClickListener onClickListener) {
        if (getAlert() != null) {
            getAlert().setOnClickListener(onClickListener);
        }
        return this;
    }

    public Alerter setDuration(long milliseconds) {
        if (getAlert() != null) {
            getAlert().setDuration(milliseconds);
        }
        return this;
    }

    public Alerter enableIconPulse(boolean pulse) {
        if (getAlert() != null) {
            getAlert().pulseIcon(pulse);
        }
        return this;
    }

    public Alerter showIcon(boolean showIcon) {
        if (getAlert() != null) {
            getAlert().showIcon(showIcon);
        }
        return this;
    }

    public Alerter enableInfiniteDuration(boolean infiniteDuration) {
        if (getAlert() != null) {
            getAlert().setEnableInfiniteDuration(infiniteDuration);
        }
        return this;
    }

    public Alerter setOnShowListener(OnShowAlertListener listener) {
        if (getAlert() != null) {
            getAlert().setOnShowListener(listener);
        }
        return this;
    }

    public Alerter setOnHideListener(OnHideAlertListener listener) {
        if (getAlert() != null) {
            getAlert().setOnHideListener(listener);
        }
        return this;
    }

    public Alerter enableSwipeToDismiss() {
        if (getAlert() != null) {
            getAlert().enableSwipeToDismiss();
        }
        return this;
    }

    public Alerter enableVibration(boolean enable) {
        if (getAlert() != null) {
            getAlert().setVibrationEnabled(enable);
        }
        return this;
    }

    public Alerter disableOutsideTouch() {
        if (getAlert() != null) {
            getAlert().disableOutsideTouch();
        }
        return this;
    }

    public Alerter enableProgress(boolean enable) {
        if (getAlert() != null) {
            getAlert().setEnableProgress(enable);
        }
        return this;
    }

    public Alerter setProgressColorRes(int color) {
        if (getAlert() != null) {
            getAlert().setProgressColorRes(color);
        }
        return this;
    }

    public Alerter setProgressColorInt(int color) {
        if (getAlert() != null) {
            getAlert().setProgressColorInt(color);
        }
        return this;
    }

    /* access modifiers changed from: package-private */
    public Alert getAlert() {
        return this.alert;
    }

    private void setAlert(Alert alert2) {
        this.alert = alert2;
    }

    /* access modifiers changed from: package-private */
    public WeakReference<Activity> getActivityWeakReference() {
        return activityWeakReference;
    }

    /* access modifiers changed from: package-private */
    public ViewGroup getActivityDecorView() {
        if (getActivityWeakReference() == null || getActivityWeakReference().get() == null) {
            return null;
        }
        return (ViewGroup) ((Activity) getActivityWeakReference().get()).getWindow().getDecorView();
    }

    private void setActivity(Activity activity) {
        activityWeakReference = new WeakReference<>(activity);
    }
}
