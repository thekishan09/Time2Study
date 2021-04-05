package com.google.android.material.bottomappbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.C0822R;
import com.google.android.material.animation.TransformationCallback;
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BottomAppBar extends Toolbar implements CoordinatorLayout.AttachedBehavior {
    private static final long ANIMATION_DURATION = 300;
    private static final int DEF_STYLE_RES = C0822R.style.Widget_MaterialComponents_BottomAppBar;
    public static final int FAB_ALIGNMENT_MODE_CENTER = 0;
    public static final int FAB_ALIGNMENT_MODE_END = 1;
    public static final int FAB_ANIMATION_MODE_SCALE = 0;
    public static final int FAB_ANIMATION_MODE_SLIDE = 1;
    private static final int NO_MENU_RES_ID = 0;
    private int animatingModeChangeCounter;
    private ArrayList<AnimationListener> animationListeners;
    private Behavior behavior;
    /* access modifiers changed from: private */
    public int bottomInset;
    /* access modifiers changed from: private */
    public int fabAlignmentMode;
    AnimatorListenerAdapter fabAnimationListener;
    private int fabAnimationMode;
    /* access modifiers changed from: private */
    public boolean fabAttached;
    /* access modifiers changed from: private */
    public final int fabOffsetEndMode;
    TransformationCallback<FloatingActionButton> fabTransformationCallback;
    private boolean hideOnScroll;
    /* access modifiers changed from: private */
    public int leftInset;
    /* access modifiers changed from: private */
    public final MaterialShapeDrawable materialShapeDrawable;
    /* access modifiers changed from: private */
    public boolean menuAnimatingWithFabAlignmentMode;
    /* access modifiers changed from: private */
    public Animator menuAnimator;
    /* access modifiers changed from: private */
    public Animator modeAnimator;
    /* access modifiers changed from: private */
    public final boolean paddingBottomSystemWindowInsets;
    /* access modifiers changed from: private */
    public final boolean paddingLeftSystemWindowInsets;
    /* access modifiers changed from: private */
    public final boolean paddingRightSystemWindowInsets;
    /* access modifiers changed from: private */
    public int pendingMenuResId;
    /* access modifiers changed from: private */
    public int rightInset;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FabAlignmentMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FabAnimationMode {
    }

    interface AnimationListener {
        void onAnimationEnd(BottomAppBar bottomAppBar);

        void onAnimationStart(BottomAppBar bottomAppBar);
    }

    public BottomAppBar(Context context) {
        this(context, (AttributeSet) null, 0);
    }

    public BottomAppBar(Context context, AttributeSet attrs) {
        this(context, attrs, C0822R.attr.bottomAppBarStyle);
    }

    public BottomAppBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, DEF_STYLE_RES), attrs, defStyleAttr);
        this.materialShapeDrawable = new MaterialShapeDrawable();
        this.animatingModeChangeCounter = 0;
        this.pendingMenuResId = 0;
        this.menuAnimatingWithFabAlignmentMode = false;
        this.fabAttached = true;
        this.fabAnimationListener = new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animation) {
                if (!BottomAppBar.this.menuAnimatingWithFabAlignmentMode) {
                    BottomAppBar bottomAppBar = BottomAppBar.this;
                    bottomAppBar.maybeAnimateMenuView(bottomAppBar.fabAlignmentMode, BottomAppBar.this.fabAttached);
                }
            }
        };
        this.fabTransformationCallback = new TransformationCallback<FloatingActionButton>() {
            public void onScaleChanged(FloatingActionButton fab) {
                BottomAppBar.this.materialShapeDrawable.setInterpolation(fab.getVisibility() == 0 ? fab.getScaleY() : 0.0f);
            }

            public void onTranslationChanged(FloatingActionButton fab) {
                float horizontalOffset = fab.getTranslationX();
                if (BottomAppBar.this.getTopEdgeTreatment().getHorizontalOffset() != horizontalOffset) {
                    BottomAppBar.this.getTopEdgeTreatment().setHorizontalOffset(horizontalOffset);
                    BottomAppBar.this.materialShapeDrawable.invalidateSelf();
                }
                float f = 0.0f;
                float verticalOffset = Math.max(0.0f, -fab.getTranslationY());
                if (BottomAppBar.this.getTopEdgeTreatment().getCradleVerticalOffset() != verticalOffset) {
                    BottomAppBar.this.getTopEdgeTreatment().setCradleVerticalOffset(verticalOffset);
                    BottomAppBar.this.materialShapeDrawable.invalidateSelf();
                }
                MaterialShapeDrawable access$400 = BottomAppBar.this.materialShapeDrawable;
                if (fab.getVisibility() == 0) {
                    f = fab.getScaleY();
                }
                access$400.setInterpolation(f);
            }
        };
        Context context2 = getContext();
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context2, attrs, C0822R.styleable.BottomAppBar, defStyleAttr, DEF_STYLE_RES, new int[0]);
        ColorStateList backgroundTint = MaterialResources.getColorStateList(context2, a, C0822R.styleable.BottomAppBar_backgroundTint);
        int elevation = a.getDimensionPixelSize(C0822R.styleable.BottomAppBar_elevation, 0);
        this.fabAlignmentMode = a.getInt(C0822R.styleable.BottomAppBar_fabAlignmentMode, 0);
        this.fabAnimationMode = a.getInt(C0822R.styleable.BottomAppBar_fabAnimationMode, 0);
        this.hideOnScroll = a.getBoolean(C0822R.styleable.BottomAppBar_hideOnScroll, false);
        this.paddingBottomSystemWindowInsets = a.getBoolean(C0822R.styleable.BottomAppBar_paddingBottomSystemWindowInsets, false);
        this.paddingLeftSystemWindowInsets = a.getBoolean(C0822R.styleable.BottomAppBar_paddingLeftSystemWindowInsets, false);
        this.paddingRightSystemWindowInsets = a.getBoolean(C0822R.styleable.BottomAppBar_paddingRightSystemWindowInsets, false);
        a.recycle();
        this.fabOffsetEndMode = getResources().getDimensionPixelOffset(C0822R.dimen.mtrl_bottomappbar_fabOffsetEndMode);
        this.materialShapeDrawable.setShapeAppearanceModel(ShapeAppearanceModel.builder().setTopEdge(new BottomAppBarTopEdgeTreatment((float) a.getDimensionPixelOffset(C0822R.styleable.BottomAppBar_fabCradleMargin, 0), (float) a.getDimensionPixelOffset(C0822R.styleable.BottomAppBar_fabCradleRoundedCornerRadius, 0), (float) a.getDimensionPixelOffset(C0822R.styleable.BottomAppBar_fabCradleVerticalOffset, 0))).build());
        this.materialShapeDrawable.setShadowCompatibilityMode(2);
        this.materialShapeDrawable.setPaintStyle(Paint.Style.FILL);
        this.materialShapeDrawable.initializeElevationOverlay(context2);
        setElevation((float) elevation);
        DrawableCompat.setTintList(this.materialShapeDrawable, backgroundTint);
        ViewCompat.setBackground(this, this.materialShapeDrawable);
        ViewUtils.doOnApplyWindowInsets(this, attrs, defStyleAttr, DEF_STYLE_RES, new ViewUtils.OnApplyWindowInsetsListener() {
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets, ViewUtils.RelativePadding initialPadding) {
                boolean leftInsetsChanged = false;
                boolean rightInsetsChanged = false;
                if (BottomAppBar.this.paddingBottomSystemWindowInsets) {
                    int unused = BottomAppBar.this.bottomInset = insets.getSystemWindowInsetBottom();
                }
                boolean z = true;
                if (BottomAppBar.this.paddingLeftSystemWindowInsets) {
                    leftInsetsChanged = BottomAppBar.this.leftInset != insets.getSystemWindowInsetLeft();
                    int unused2 = BottomAppBar.this.leftInset = insets.getSystemWindowInsetLeft();
                }
                if (BottomAppBar.this.paddingRightSystemWindowInsets) {
                    if (BottomAppBar.this.rightInset == insets.getSystemWindowInsetRight()) {
                        z = false;
                    }
                    rightInsetsChanged = z;
                    int unused3 = BottomAppBar.this.rightInset = insets.getSystemWindowInsetRight();
                }
                if (leftInsetsChanged || rightInsetsChanged) {
                    BottomAppBar.this.cancelAnimations();
                    BottomAppBar.this.setCutoutState();
                    BottomAppBar.this.setActionMenuViewPosition();
                }
                return insets;
            }
        });
    }

    public int getFabAlignmentMode() {
        return this.fabAlignmentMode;
    }

    public void setFabAlignmentMode(int fabAlignmentMode2) {
        setFabAlignmentModeAndReplaceMenu(fabAlignmentMode2, 0);
    }

    public void setFabAlignmentModeAndReplaceMenu(int fabAlignmentMode2, int newMenu) {
        this.pendingMenuResId = newMenu;
        this.menuAnimatingWithFabAlignmentMode = true;
        maybeAnimateMenuView(fabAlignmentMode2, this.fabAttached);
        maybeAnimateModeChange(fabAlignmentMode2);
        this.fabAlignmentMode = fabAlignmentMode2;
    }

    public int getFabAnimationMode() {
        return this.fabAnimationMode;
    }

    public void setFabAnimationMode(int fabAnimationMode2) {
        this.fabAnimationMode = fabAnimationMode2;
    }

    public void setBackgroundTint(ColorStateList backgroundTint) {
        DrawableCompat.setTintList(this.materialShapeDrawable, backgroundTint);
    }

    public ColorStateList getBackgroundTint() {
        return this.materialShapeDrawable.getTintList();
    }

    public float getFabCradleMargin() {
        return getTopEdgeTreatment().getFabCradleMargin();
    }

    public void setFabCradleMargin(float cradleMargin) {
        if (cradleMargin != getFabCradleMargin()) {
            getTopEdgeTreatment().setFabCradleMargin(cradleMargin);
            this.materialShapeDrawable.invalidateSelf();
        }
    }

    public float getFabCradleRoundedCornerRadius() {
        return getTopEdgeTreatment().getFabCradleRoundedCornerRadius();
    }

    public void setFabCradleRoundedCornerRadius(float roundedCornerRadius) {
        if (roundedCornerRadius != getFabCradleRoundedCornerRadius()) {
            getTopEdgeTreatment().setFabCradleRoundedCornerRadius(roundedCornerRadius);
            this.materialShapeDrawable.invalidateSelf();
        }
    }

    public float getCradleVerticalOffset() {
        return getTopEdgeTreatment().getCradleVerticalOffset();
    }

    public void setCradleVerticalOffset(float verticalOffset) {
        if (verticalOffset != getCradleVerticalOffset()) {
            getTopEdgeTreatment().setCradleVerticalOffset(verticalOffset);
            this.materialShapeDrawable.invalidateSelf();
            setCutoutState();
        }
    }

    public boolean getHideOnScroll() {
        return this.hideOnScroll;
    }

    public void setHideOnScroll(boolean hide) {
        this.hideOnScroll = hide;
    }

    public void performHide() {
        getBehavior().slideDown(this);
    }

    public void performShow() {
        getBehavior().slideUp(this);
    }

    public void setElevation(float elevation) {
        this.materialShapeDrawable.setElevation(elevation);
        getBehavior().setAdditionalHiddenOffsetY(this, this.materialShapeDrawable.getShadowRadius() - this.materialShapeDrawable.getShadowOffsetY());
    }

    public void replaceMenu(int newMenu) {
        getMenu().clear();
        inflateMenu(newMenu);
    }

    /* access modifiers changed from: package-private */
    public void addAnimationListener(AnimationListener listener) {
        if (this.animationListeners == null) {
            this.animationListeners = new ArrayList<>();
        }
        this.animationListeners.add(listener);
    }

    /* access modifiers changed from: package-private */
    public void removeAnimationListener(AnimationListener listener) {
        ArrayList<AnimationListener> arrayList = this.animationListeners;
        if (arrayList != null) {
            arrayList.remove(listener);
        }
    }

    /* access modifiers changed from: private */
    public void dispatchAnimationStart() {
        ArrayList<AnimationListener> arrayList;
        int i = this.animatingModeChangeCounter;
        this.animatingModeChangeCounter = i + 1;
        if (i == 0 && (arrayList = this.animationListeners) != null) {
            Iterator<AnimationListener> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().onAnimationStart(this);
            }
        }
    }

    /* access modifiers changed from: private */
    public void dispatchAnimationEnd() {
        ArrayList<AnimationListener> arrayList;
        int i = this.animatingModeChangeCounter - 1;
        this.animatingModeChangeCounter = i;
        if (i == 0 && (arrayList = this.animationListeners) != null) {
            Iterator<AnimationListener> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().onAnimationEnd(this);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean setFabDiameter(int diameter) {
        if (((float) diameter) == getTopEdgeTreatment().getFabDiameter()) {
            return false;
        }
        getTopEdgeTreatment().setFabDiameter((float) diameter);
        this.materialShapeDrawable.invalidateSelf();
        return true;
    }

    private void maybeAnimateModeChange(int targetMode) {
        if (this.fabAlignmentMode != targetMode && ViewCompat.isLaidOut(this)) {
            Animator animator = this.modeAnimator;
            if (animator != null) {
                animator.cancel();
            }
            List<Animator> animators = new ArrayList<>();
            if (this.fabAnimationMode == 1) {
                createFabTranslationXAnimation(targetMode, animators);
            } else {
                createFabDefaultXAnimation(targetMode, animators);
            }
            AnimatorSet set = new AnimatorSet();
            set.playTogether(animators);
            this.modeAnimator = set;
            set.addListener(new AnimatorListenerAdapter() {
                public void onAnimationStart(Animator animation) {
                    BottomAppBar.this.dispatchAnimationStart();
                }

                public void onAnimationEnd(Animator animation) {
                    BottomAppBar.this.dispatchAnimationEnd();
                    Animator unused = BottomAppBar.this.modeAnimator = null;
                }
            });
            this.modeAnimator.start();
        }
    }

    /* access modifiers changed from: private */
    public FloatingActionButton findDependentFab() {
        View view = findDependentView();
        if (view instanceof FloatingActionButton) {
            return (FloatingActionButton) view;
        }
        return null;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:6:0x001e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View findDependentView() {
        /*
            r5 = this;
            android.view.ViewParent r0 = r5.getParent()
            boolean r0 = r0 instanceof androidx.coordinatorlayout.widget.CoordinatorLayout
            r1 = 0
            if (r0 != 0) goto L_0x000a
            return r1
        L_0x000a:
            android.view.ViewParent r0 = r5.getParent()
            androidx.coordinatorlayout.widget.CoordinatorLayout r0 = (androidx.coordinatorlayout.widget.CoordinatorLayout) r0
            java.util.List r0 = r0.getDependents(r5)
            java.util.Iterator r2 = r0.iterator()
        L_0x0018:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x002f
            java.lang.Object r3 = r2.next()
            android.view.View r3 = (android.view.View) r3
            boolean r4 = r3 instanceof com.google.android.material.floatingactionbutton.FloatingActionButton
            if (r4 != 0) goto L_0x002e
            boolean r4 = r3 instanceof com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
            if (r4 == 0) goto L_0x002d
            goto L_0x002e
        L_0x002d:
            goto L_0x0018
        L_0x002e:
            return r3
        L_0x002f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.bottomappbar.BottomAppBar.findDependentView():android.view.View");
    }

    private boolean isFabVisibleOrWillBeShown() {
        FloatingActionButton fab = findDependentFab();
        return fab != null && fab.isOrWillBeShown();
    }

    /* access modifiers changed from: protected */
    public void createFabDefaultXAnimation(final int targetMode, List<Animator> list) {
        FloatingActionButton fab = findDependentFab();
        if (fab != null && !fab.isOrWillBeHidden()) {
            dispatchAnimationStart();
            fab.hide(new FloatingActionButton.OnVisibilityChangedListener() {
                public void onHidden(FloatingActionButton fab) {
                    fab.setTranslationX(BottomAppBar.this.getFabTranslationX(targetMode));
                    fab.show(new FloatingActionButton.OnVisibilityChangedListener() {
                        public void onShown(FloatingActionButton fab) {
                            BottomAppBar.this.dispatchAnimationEnd();
                        }
                    });
                }
            });
        }
    }

    private void createFabTranslationXAnimation(int targetMode, List<Animator> animators) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(findDependentFab(), "translationX", new float[]{getFabTranslationX(targetMode)});
        animator.setDuration(ANIMATION_DURATION);
        animators.add(animator);
    }

    /* access modifiers changed from: private */
    public void maybeAnimateMenuView(int targetMode, boolean newFabAttached) {
        if (!ViewCompat.isLaidOut(this)) {
            this.menuAnimatingWithFabAlignmentMode = false;
            return;
        }
        Animator animator = this.menuAnimator;
        if (animator != null) {
            animator.cancel();
        }
        List<Animator> animators = new ArrayList<>();
        if (!isFabVisibleOrWillBeShown()) {
            targetMode = 0;
            newFabAttached = false;
        }
        createMenuViewTranslationAnimation(targetMode, newFabAttached, animators);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animators);
        this.menuAnimator = set;
        set.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animation) {
                BottomAppBar.this.dispatchAnimationStart();
            }

            public void onAnimationEnd(Animator animation) {
                BottomAppBar.this.dispatchAnimationEnd();
                boolean unused = BottomAppBar.this.menuAnimatingWithFabAlignmentMode = false;
                Animator unused2 = BottomAppBar.this.menuAnimator = null;
            }
        });
        this.menuAnimator.start();
    }

    private void createMenuViewTranslationAnimation(final int targetMode, final boolean targetAttached, List<Animator> animators) {
        final ActionMenuView actionMenuView = getActionMenuView();
        if (actionMenuView != null) {
            Animator fadeIn = ObjectAnimator.ofFloat(actionMenuView, "alpha", new float[]{1.0f});
            if (Math.abs(actionMenuView.getTranslationX() - ((float) getActionMenuViewTranslationX(actionMenuView, targetMode, targetAttached))) > 1.0f) {
                Animator fadeOut = ObjectAnimator.ofFloat(actionMenuView, "alpha", new float[]{0.0f});
                fadeOut.addListener(new AnimatorListenerAdapter() {
                    public boolean cancelled;

                    public void onAnimationCancel(Animator animation) {
                        this.cancelled = true;
                    }

                    public void onAnimationEnd(Animator animation) {
                        if (!this.cancelled) {
                            BottomAppBar.this.translateActionMenuView(actionMenuView, targetMode, targetAttached);
                            if (BottomAppBar.this.pendingMenuResId != 0) {
                                BottomAppBar bottomAppBar = BottomAppBar.this;
                                bottomAppBar.replaceMenu(bottomAppBar.pendingMenuResId);
                                int unused = BottomAppBar.this.pendingMenuResId = 0;
                            }
                        }
                    }
                });
                AnimatorSet set = new AnimatorSet();
                set.setDuration(150);
                set.playSequentially(new Animator[]{fadeOut, fadeIn});
                animators.add(set);
            } else if (actionMenuView.getAlpha() < 1.0f) {
                animators.add(fadeIn);
            }
        }
    }

    private float getFabTranslationY() {
        return -getTopEdgeTreatment().getCradleVerticalOffset();
    }

    /* access modifiers changed from: private */
    public float getFabTranslationX(int fabAlignmentMode2) {
        boolean isRtl = ViewUtils.isLayoutRtl(this);
        int i = 1;
        if (fabAlignmentMode2 != 1) {
            return 0.0f;
        }
        int measuredWidth = (getMeasuredWidth() / 2) - (this.fabOffsetEndMode + (isRtl ? this.leftInset : this.rightInset));
        if (isRtl) {
            i = -1;
        }
        return (float) (measuredWidth * i);
    }

    /* access modifiers changed from: private */
    public float getFabTranslationX() {
        return getFabTranslationX(this.fabAlignmentMode);
    }

    private ActionMenuView getActionMenuView() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof ActionMenuView) {
                return (ActionMenuView) view;
            }
        }
        return null;
    }

    /* access modifiers changed from: private */
    public void translateActionMenuView(ActionMenuView actionMenuView, int fabAlignmentMode2, boolean fabAttached2) {
        actionMenuView.setTranslationX((float) getActionMenuViewTranslationX(actionMenuView, fabAlignmentMode2, fabAttached2));
    }

    /* access modifiers changed from: protected */
    public int getActionMenuViewTranslationX(ActionMenuView actionMenuView, int fabAlignmentMode2, boolean fabAttached2) {
        int i;
        if (fabAlignmentMode2 != 1 || !fabAttached2) {
            return 0;
        }
        boolean isRtl = ViewUtils.isLayoutRtl(this);
        int toolbarLeftContentEnd = isRtl ? getMeasuredWidth() : 0;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            View view = getChildAt(i2);
            if ((view.getLayoutParams() instanceof Toolbar.LayoutParams) && (((Toolbar.LayoutParams) view.getLayoutParams()).gravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK) == 8388611) {
                if (isRtl) {
                    i = Math.min(toolbarLeftContentEnd, view.getLeft());
                } else {
                    i = Math.max(toolbarLeftContentEnd, view.getRight());
                }
                toolbarLeftContentEnd = i;
            }
        }
        return toolbarLeftContentEnd - ((isRtl ? actionMenuView.getRight() : actionMenuView.getLeft()) + (isRtl ? this.rightInset : -this.leftInset));
    }

    /* access modifiers changed from: private */
    public void cancelAnimations() {
        Animator animator = this.menuAnimator;
        if (animator != null) {
            animator.cancel();
        }
        Animator animator2 = this.modeAnimator;
        if (animator2 != null) {
            animator2.cancel();
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            cancelAnimations();
            setCutoutState();
        }
        setActionMenuViewPosition();
    }

    /* access modifiers changed from: private */
    public BottomAppBarTopEdgeTreatment getTopEdgeTreatment() {
        return (BottomAppBarTopEdgeTreatment) this.materialShapeDrawable.getShapeAppearanceModel().getTopEdge();
    }

    /* access modifiers changed from: private */
    public void setCutoutState() {
        getTopEdgeTreatment().setHorizontalOffset(getFabTranslationX());
        View fab = findDependentView();
        this.materialShapeDrawable.setInterpolation((!this.fabAttached || !isFabVisibleOrWillBeShown()) ? 0.0f : 1.0f);
        if (fab != null) {
            fab.setTranslationY(getFabTranslationY());
            fab.setTranslationX(getFabTranslationX());
        }
    }

    /* access modifiers changed from: private */
    public void setActionMenuViewPosition() {
        ActionMenuView actionMenuView = getActionMenuView();
        if (actionMenuView != null && this.menuAnimator == null) {
            actionMenuView.setAlpha(1.0f);
            if (!isFabVisibleOrWillBeShown()) {
                translateActionMenuView(actionMenuView, 0, false);
            } else {
                translateActionMenuView(actionMenuView, this.fabAlignmentMode, this.fabAttached);
            }
        }
    }

    /* access modifiers changed from: private */
    public void addFabAnimationListeners(FloatingActionButton fab) {
        fab.addOnHideAnimationListener(this.fabAnimationListener);
        fab.addOnShowAnimationListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animation) {
                BottomAppBar.this.fabAnimationListener.onAnimationStart(animation);
                FloatingActionButton fab = BottomAppBar.this.findDependentFab();
                if (fab != null) {
                    fab.setTranslationX(BottomAppBar.this.getFabTranslationX());
                }
            }
        });
        fab.addTransformationCallback(this.fabTransformationCallback);
    }

    /* access modifiers changed from: private */
    public int getBottomInset() {
        return this.bottomInset;
    }

    /* access modifiers changed from: private */
    public int getRightInset() {
        return this.rightInset;
    }

    /* access modifiers changed from: private */
    public int getLeftInset() {
        return this.leftInset;
    }

    public void setTitle(CharSequence title) {
    }

    public void setSubtitle(CharSequence subtitle) {
    }

    public Behavior getBehavior() {
        if (this.behavior == null) {
            this.behavior = new Behavior();
        }
        return this.behavior;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this, this.materialShapeDrawable);
        if (getParent() instanceof ViewGroup) {
            ((ViewGroup) getParent()).setClipChildren(false);
        }
    }

    public static class Behavior extends HideBottomViewOnScrollBehavior<BottomAppBar> {
        /* access modifiers changed from: private */
        public final Rect fabContentRect = new Rect();
        private final View.OnLayoutChangeListener fabLayoutListener = new View.OnLayoutChangeListener() {
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                View view = v;
                BottomAppBar child = (BottomAppBar) Behavior.this.viewRef.get();
                if (child == null || !(view instanceof FloatingActionButton)) {
                    v.removeOnLayoutChangeListener(this);
                    return;
                }
                FloatingActionButton fab = (FloatingActionButton) view;
                fab.getMeasuredContentRect(Behavior.this.fabContentRect);
                int height = Behavior.this.fabContentRect.height();
                child.setFabDiameter(height);
                CoordinatorLayout.LayoutParams fabLayoutParams = (CoordinatorLayout.LayoutParams) v.getLayoutParams();
                if (Behavior.this.originalBottomMargin == 0) {
                    fabLayoutParams.bottomMargin = child.getBottomInset() + (child.getResources().getDimensionPixelOffset(C0822R.dimen.mtrl_bottomappbar_fab_bottom_margin) - ((fab.getMeasuredHeight() - height) / 2));
                    fabLayoutParams.leftMargin = child.getLeftInset();
                    fabLayoutParams.rightMargin = child.getRightInset();
                    if (ViewUtils.isLayoutRtl(fab)) {
                        fabLayoutParams.leftMargin += child.fabOffsetEndMode;
                    } else {
                        fabLayoutParams.rightMargin += child.fabOffsetEndMode;
                    }
                }
            }
        };
        /* access modifiers changed from: private */
        public int originalBottomMargin;
        /* access modifiers changed from: private */
        public WeakReference<BottomAppBar> viewRef;

        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public boolean onLayoutChild(CoordinatorLayout parent, BottomAppBar child, int layoutDirection) {
            this.viewRef = new WeakReference<>(child);
            View dependentView = child.findDependentView();
            if (dependentView != null && !ViewCompat.isLaidOut(dependentView)) {
                CoordinatorLayout.LayoutParams fabLayoutParams = (CoordinatorLayout.LayoutParams) dependentView.getLayoutParams();
                fabLayoutParams.anchorGravity = 49;
                this.originalBottomMargin = fabLayoutParams.bottomMargin;
                if (dependentView instanceof FloatingActionButton) {
                    FloatingActionButton fab = (FloatingActionButton) dependentView;
                    fab.addOnLayoutChangeListener(this.fabLayoutListener);
                    child.addFabAnimationListeners(fab);
                }
                child.setCutoutState();
            }
            parent.onLayoutChild(child, layoutDirection);
            return super.onLayoutChild(parent, child, layoutDirection);
        }

        public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, BottomAppBar child, View directTargetChild, View target, int axes, int type) {
            return child.getHideOnScroll() && super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.fabAlignmentMode = this.fabAlignmentMode;
        savedState.fabAttached = this.fabAttached;
        return savedState;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.fabAlignmentMode = savedState.fabAlignmentMode;
        this.fabAttached = savedState.fabAttached;
    }

    static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() {
            public SavedState createFromParcel(Parcel in, ClassLoader loader) {
                return new SavedState(in, loader);
            }

            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in, (ClassLoader) null);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        int fabAlignmentMode;
        boolean fabAttached;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel in, ClassLoader loader) {
            super(in, loader);
            this.fabAlignmentMode = in.readInt();
            this.fabAttached = in.readInt() != 0;
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.fabAlignmentMode);
            out.writeInt(this.fabAttached ? 1 : 0);
        }
    }
}
