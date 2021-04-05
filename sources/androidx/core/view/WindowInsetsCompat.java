package androidx.core.view;

import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.WindowInsets;
import androidx.core.graphics.Insets;
import androidx.core.util.ObjectsCompat;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Objects;

public class WindowInsetsCompat {
    private static final String TAG = "WindowInsetsCompat";
    private final Object mInsets;
    private Insets mMandatorySystemGestureInsets;
    private Insets mStableInsets;
    private Insets mSystemGestureInsets;
    private Insets mSystemWindowInsets;
    private Insets mTappableElementInsets;

    WindowInsetsCompat(Object insets) {
        this.mInsets = insets;
    }

    public WindowInsetsCompat(WindowInsetsCompat src) {
        WindowInsets windowInsets = null;
        if (Build.VERSION.SDK_INT >= 20) {
            this.mInsets = src != null ? new WindowInsets((WindowInsets) src.mInsets) : windowInsets;
        } else {
            this.mInsets = null;
        }
    }

    public int getSystemWindowInsetLeft() {
        if (Build.VERSION.SDK_INT >= 20) {
            return ((WindowInsets) this.mInsets).getSystemWindowInsetLeft();
        }
        return 0;
    }

    public int getSystemWindowInsetTop() {
        if (Build.VERSION.SDK_INT >= 20) {
            return ((WindowInsets) this.mInsets).getSystemWindowInsetTop();
        }
        return 0;
    }

    public int getSystemWindowInsetRight() {
        if (Build.VERSION.SDK_INT >= 20) {
            return ((WindowInsets) this.mInsets).getSystemWindowInsetRight();
        }
        return 0;
    }

    public int getSystemWindowInsetBottom() {
        if (Build.VERSION.SDK_INT >= 20) {
            return ((WindowInsets) this.mInsets).getSystemWindowInsetBottom();
        }
        return 0;
    }

    public boolean hasSystemWindowInsets() {
        if (Build.VERSION.SDK_INT >= 20) {
            return ((WindowInsets) this.mInsets).hasSystemWindowInsets();
        }
        return false;
    }

    public boolean hasInsets() {
        if (Build.VERSION.SDK_INT >= 20) {
            return ((WindowInsets) this.mInsets).hasInsets();
        }
        return false;
    }

    public boolean isConsumed() {
        if (Build.VERSION.SDK_INT >= 21) {
            return ((WindowInsets) this.mInsets).isConsumed();
        }
        return false;
    }

    public boolean isRound() {
        if (Build.VERSION.SDK_INT >= 20) {
            return ((WindowInsets) this.mInsets).isRound();
        }
        return false;
    }

    public WindowInsetsCompat consumeSystemWindowInsets() {
        if (Build.VERSION.SDK_INT >= 20) {
            return new WindowInsetsCompat((Object) ((WindowInsets) this.mInsets).consumeSystemWindowInsets());
        }
        return null;
    }

    @Deprecated
    public WindowInsetsCompat replaceSystemWindowInsets(int left, int top, int right, int bottom) {
        if (Build.VERSION.SDK_INT >= 20) {
            return new WindowInsetsCompat((Object) ((WindowInsets) this.mInsets).replaceSystemWindowInsets(left, top, right, bottom));
        }
        return null;
    }

    @Deprecated
    public WindowInsetsCompat replaceSystemWindowInsets(Rect systemWindowInsets) {
        if (Build.VERSION.SDK_INT >= 20) {
            return replaceSystemWindowInsets(systemWindowInsets.left, systemWindowInsets.top, systemWindowInsets.right, systemWindowInsets.bottom);
        }
        return null;
    }

    public int getStableInsetTop() {
        if (Build.VERSION.SDK_INT >= 21) {
            return ((WindowInsets) this.mInsets).getStableInsetTop();
        }
        return 0;
    }

    public int getStableInsetLeft() {
        if (Build.VERSION.SDK_INT >= 21) {
            return ((WindowInsets) this.mInsets).getStableInsetLeft();
        }
        return 0;
    }

    public int getStableInsetRight() {
        if (Build.VERSION.SDK_INT >= 21) {
            return ((WindowInsets) this.mInsets).getStableInsetRight();
        }
        return 0;
    }

    public int getStableInsetBottom() {
        if (Build.VERSION.SDK_INT >= 21) {
            return ((WindowInsets) this.mInsets).getStableInsetBottom();
        }
        return 0;
    }

    public boolean hasStableInsets() {
        if (Build.VERSION.SDK_INT >= 21) {
            return ((WindowInsets) this.mInsets).hasStableInsets();
        }
        return false;
    }

    public WindowInsetsCompat consumeStableInsets() {
        if (Build.VERSION.SDK_INT >= 21) {
            return new WindowInsetsCompat((Object) ((WindowInsets) this.mInsets).consumeStableInsets());
        }
        return null;
    }

    public DisplayCutoutCompat getDisplayCutout() {
        if (Build.VERSION.SDK_INT >= 28) {
            return DisplayCutoutCompat.wrap(((WindowInsets) this.mInsets).getDisplayCutout());
        }
        return null;
    }

    public WindowInsetsCompat consumeDisplayCutout() {
        if (Build.VERSION.SDK_INT >= 28) {
            return new WindowInsetsCompat((Object) ((WindowInsets) this.mInsets).consumeDisplayCutout());
        }
        return this;
    }

    public Insets getSystemWindowInsets() {
        if (this.mSystemWindowInsets == null) {
            if (Build.VERSION.SDK_INT >= 29) {
                this.mSystemWindowInsets = Insets.toCompatInsets(((WindowInsets) this.mInsets).getSystemWindowInsets());
            } else {
                this.mSystemWindowInsets = Insets.m8of(getSystemWindowInsetLeft(), getSystemWindowInsetTop(), getSystemWindowInsetRight(), getSystemWindowInsetBottom());
            }
        }
        return this.mSystemWindowInsets;
    }

    public Insets getStableInsets() {
        if (this.mStableInsets == null) {
            if (Build.VERSION.SDK_INT >= 29) {
                this.mStableInsets = Insets.toCompatInsets(((WindowInsets) this.mInsets).getStableInsets());
            } else {
                this.mStableInsets = Insets.m8of(getStableInsetLeft(), getStableInsetTop(), getStableInsetRight(), getStableInsetBottom());
            }
        }
        return this.mStableInsets;
    }

    public Insets getMandatorySystemGestureInsets() {
        if (this.mMandatorySystemGestureInsets == null) {
            if (Build.VERSION.SDK_INT >= 29) {
                this.mMandatorySystemGestureInsets = Insets.toCompatInsets(((WindowInsets) this.mInsets).getMandatorySystemGestureInsets());
            } else {
                this.mMandatorySystemGestureInsets = getSystemWindowInsets();
            }
        }
        return this.mMandatorySystemGestureInsets;
    }

    public Insets getTappableElementInsets() {
        if (this.mTappableElementInsets == null) {
            if (Build.VERSION.SDK_INT >= 29) {
                this.mTappableElementInsets = Insets.toCompatInsets(((WindowInsets) this.mInsets).getTappableElementInsets());
            } else {
                this.mTappableElementInsets = getSystemWindowInsets();
            }
        }
        return this.mTappableElementInsets;
    }

    public Insets getSystemGestureInsets() {
        if (this.mSystemGestureInsets == null) {
            if (Build.VERSION.SDK_INT >= 29) {
                this.mSystemGestureInsets = Insets.toCompatInsets(((WindowInsets) this.mInsets).getSystemGestureInsets());
            } else {
                this.mSystemGestureInsets = getSystemWindowInsets();
            }
        }
        return this.mSystemGestureInsets;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WindowInsetsCompat)) {
            return false;
        }
        return ObjectsCompat.equals(this.mInsets, ((WindowInsetsCompat) o).mInsets);
    }

    public int hashCode() {
        Object obj = this.mInsets;
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }

    public WindowInsets toWindowInsets() {
        return (WindowInsets) this.mInsets;
    }

    public static WindowInsetsCompat toWindowInsetsCompat(WindowInsets insets) {
        return new WindowInsetsCompat(Objects.requireNonNull(insets));
    }

    public static final class Builder {
        private final BuilderImpl mImpl;

        public Builder() {
            if (Build.VERSION.SDK_INT >= 29) {
                this.mImpl = new BuilderImpl29();
            } else if (Build.VERSION.SDK_INT >= 20) {
                this.mImpl = new BuilderImpl20();
            } else {
                this.mImpl = new BuilderImpl();
            }
        }

        public Builder(WindowInsetsCompat insets) {
            if (Build.VERSION.SDK_INT >= 29) {
                this.mImpl = new BuilderImpl29(insets);
            } else if (Build.VERSION.SDK_INT >= 20) {
                this.mImpl = new BuilderImpl20(insets);
            } else {
                this.mImpl = new BuilderImpl(insets);
            }
        }

        public Builder setSystemWindowInsets(Insets insets) {
            this.mImpl.setSystemWindowInsets(insets);
            return this;
        }

        public Builder setSystemGestureInsets(Insets insets) {
            this.mImpl.setSystemGestureInsets(insets);
            return this;
        }

        public Builder setMandatorySystemGestureInsets(Insets insets) {
            this.mImpl.setMandatorySystemGestureInsets(insets);
            return this;
        }

        public Builder setTappableElementInsets(Insets insets) {
            this.mImpl.setTappableElementInsets(insets);
            return this;
        }

        public Builder setStableInsets(Insets insets) {
            this.mImpl.setStableInsets(insets);
            return this;
        }

        public Builder setDisplayCutout(DisplayCutoutCompat displayCutout) {
            this.mImpl.setDisplayCutout(displayCutout);
            return this;
        }

        public WindowInsetsCompat build() {
            return this.mImpl.build();
        }
    }

    private static class BuilderImpl {
        private WindowInsetsCompat mInsets;

        BuilderImpl() {
            this.mInsets = new WindowInsetsCompat((WindowInsetsCompat) null);
        }

        BuilderImpl(WindowInsetsCompat insets) {
            this.mInsets = insets;
        }

        public void setSystemWindowInsets(Insets insets) {
        }

        public void setSystemGestureInsets(Insets insets) {
        }

        public void setMandatorySystemGestureInsets(Insets insets) {
        }

        public void setTappableElementInsets(Insets insets) {
        }

        public void setStableInsets(Insets insets) {
        }

        public void setDisplayCutout(DisplayCutoutCompat displayCutout) {
        }

        public WindowInsetsCompat build() {
            return this.mInsets;
        }
    }

    private static class BuilderImpl20 extends BuilderImpl {
        private static Constructor<WindowInsets> sConstructor;
        private static boolean sConstructorFetched = false;
        private static Field sConsumedField;
        private static boolean sConsumedFieldFetched = false;
        private WindowInsets mInsets;

        BuilderImpl20() {
            this.mInsets = createWindowInsetsInstance();
        }

        BuilderImpl20(WindowInsetsCompat insets) {
            this.mInsets = insets.toWindowInsets();
        }

        public void setSystemWindowInsets(Insets insets) {
            WindowInsets windowInsets = this.mInsets;
            if (windowInsets != null) {
                this.mInsets = windowInsets.replaceSystemWindowInsets(insets.left, insets.top, insets.right, insets.bottom);
            }
        }

        public WindowInsetsCompat build() {
            return WindowInsetsCompat.toWindowInsetsCompat(this.mInsets);
        }

        private static WindowInsets createWindowInsetsInstance() {
            if (!sConsumedFieldFetched) {
                try {
                    sConsumedField = WindowInsets.class.getDeclaredField("CONSUMED");
                } catch (ReflectiveOperationException e) {
                    Log.i(WindowInsetsCompat.TAG, "Could not retrieve WindowInsets.CONSUMED field", e);
                }
                sConsumedFieldFetched = true;
            }
            Field field = sConsumedField;
            if (field != null) {
                try {
                    WindowInsets consumed = (WindowInsets) field.get((Object) null);
                    if (consumed != null) {
                        return new WindowInsets(consumed);
                    }
                } catch (ReflectiveOperationException e2) {
                    Log.i(WindowInsetsCompat.TAG, "Could not get value from WindowInsets.CONSUMED field", e2);
                }
            }
            if (!sConstructorFetched) {
                try {
                    sConstructor = WindowInsets.class.getConstructor(new Class[]{Rect.class});
                } catch (ReflectiveOperationException e3) {
                    Log.i(WindowInsetsCompat.TAG, "Could not retrieve WindowInsets(Rect) constructor", e3);
                }
                sConstructorFetched = true;
            }
            Constructor<WindowInsets> constructor = sConstructor;
            if (constructor != null) {
                try {
                    return constructor.newInstance(new Object[]{new Rect()});
                } catch (ReflectiveOperationException e4) {
                    Log.i(WindowInsetsCompat.TAG, "Could not invoke WindowInsets(Rect) constructor", e4);
                }
            }
            return null;
        }
    }

    private static class BuilderImpl29 extends BuilderImpl {
        private final WindowInsets.Builder mPlatBuilder;

        BuilderImpl29() {
            this.mPlatBuilder = new WindowInsets.Builder();
        }

        BuilderImpl29(WindowInsetsCompat insets) {
            this.mPlatBuilder = new WindowInsets.Builder(insets.toWindowInsets());
        }

        public void setSystemWindowInsets(Insets insets) {
            this.mPlatBuilder.setSystemWindowInsets(insets.toPlatformInsets());
        }

        public void setSystemGestureInsets(Insets insets) {
            this.mPlatBuilder.setSystemGestureInsets(insets.toPlatformInsets());
        }

        public void setMandatorySystemGestureInsets(Insets insets) {
            this.mPlatBuilder.setMandatorySystemGestureInsets(insets.toPlatformInsets());
        }

        public void setTappableElementInsets(Insets insets) {
            this.mPlatBuilder.setTappableElementInsets(insets.toPlatformInsets());
        }

        public void setStableInsets(Insets insets) {
            this.mPlatBuilder.setStableInsets(insets.toPlatformInsets());
        }

        public void setDisplayCutout(DisplayCutoutCompat displayCutout) {
            this.mPlatBuilder.setDisplayCutout(displayCutout != null ? displayCutout.unwrap() : null);
        }

        public WindowInsetsCompat build() {
            return WindowInsetsCompat.toWindowInsetsCompat(this.mPlatBuilder.build());
        }
    }
}
