package com.google.android.material.textfield;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.DrawableUtils;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.text.BidiFormatter;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.TextViewCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.C0822R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.CollapsingTextHelper;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class TextInputLayout extends LinearLayout {
    public static final int BOX_BACKGROUND_FILLED = 1;
    public static final int BOX_BACKGROUND_NONE = 0;
    public static final int BOX_BACKGROUND_OUTLINE = 2;
    private static final int DEF_STYLE_RES = C0822R.style.Widget_Design_TextInputLayout;
    public static final int END_ICON_CLEAR_TEXT = 2;
    public static final int END_ICON_CUSTOM = -1;
    public static final int END_ICON_DROPDOWN_MENU = 3;
    public static final int END_ICON_NONE = 0;
    public static final int END_ICON_PASSWORD_TOGGLE = 1;
    private static final int INVALID_MAX_LENGTH = -1;
    private static final int LABEL_SCALE_ANIMATION_DURATION = 167;
    private static final String LOG_TAG = "TextInputLayout";
    private ValueAnimator animator;
    private MaterialShapeDrawable boxBackground;
    private int boxBackgroundColor;
    private int boxBackgroundMode;
    private final int boxCollapsedPaddingTopPx;
    private final int boxLabelCutoutPaddingPx;
    private int boxStrokeColor;
    private int boxStrokeWidthDefaultPx;
    private int boxStrokeWidthFocusedPx;
    private int boxStrokeWidthPx;
    private MaterialShapeDrawable boxUnderline;
    final CollapsingTextHelper collapsingTextHelper;
    boolean counterEnabled;
    private int counterMaxLength;
    private int counterOverflowTextAppearance;
    private ColorStateList counterOverflowTextColor;
    private boolean counterOverflowed;
    private int counterTextAppearance;
    private ColorStateList counterTextColor;
    private TextView counterView;
    private int defaultFilledBackgroundColor;
    private ColorStateList defaultHintTextColor;
    private int defaultStrokeColor;
    private int disabledColor;
    private int disabledFilledBackgroundColor;
    EditText editText;
    private final LinkedHashSet<OnEditTextAttachedListener> editTextAttachedListeners;
    private Drawable endDummyDrawable;
    private int endDummyDrawableWidth;
    private final LinkedHashSet<OnEndIconChangedListener> endIconChangedListeners;
    private final SparseArray<EndIconDelegate> endIconDelegates;
    private final FrameLayout endIconFrame;
    private int endIconMode;
    private View.OnLongClickListener endIconOnLongClickListener;
    private ColorStateList endIconTintList;
    private PorterDuff.Mode endIconTintMode;
    /* access modifiers changed from: private */
    public final CheckableImageButton endIconView;
    private final LinearLayout endLayout;
    private View.OnLongClickListener errorIconOnLongClickListener;
    private ColorStateList errorIconTintList;
    private final CheckableImageButton errorIconView;
    private int focusedFilledBackgroundColor;
    private int focusedStrokeColor;
    private ColorStateList focusedTextColor;
    private boolean hasEndIconTintList;
    private boolean hasEndIconTintMode;
    private boolean hasStartIconTintList;
    private boolean hasStartIconTintMode;
    private CharSequence hint;
    private boolean hintAnimationEnabled;
    private boolean hintEnabled;
    private boolean hintExpanded;
    private int hoveredFilledBackgroundColor;
    private int hoveredStrokeColor;
    private boolean inDrawableStateChanged;
    private final IndicatorViewController indicatorViewController;
    private final FrameLayout inputFrame;
    private boolean isProvidingHint;
    private Drawable originalEditTextEndDrawable;
    private CharSequence originalHint;
    /* access modifiers changed from: private */
    public boolean placeholderEnabled;
    private CharSequence placeholderText;
    private int placeholderTextAppearance;
    private ColorStateList placeholderTextColor;
    private TextView placeholderTextView;
    private CharSequence prefixText;
    private final TextView prefixTextView;
    /* access modifiers changed from: private */
    public boolean restoringSavedState;
    private ShapeAppearanceModel shapeAppearanceModel;
    private Drawable startDummyDrawable;
    private int startDummyDrawableWidth;
    private View.OnLongClickListener startIconOnLongClickListener;
    private ColorStateList startIconTintList;
    private PorterDuff.Mode startIconTintMode;
    private final CheckableImageButton startIconView;
    private final LinearLayout startLayout;
    private ColorStateList strokeErrorColor;
    private CharSequence suffixText;
    private final TextView suffixTextView;
    private final Rect tmpBoundsRect;
    private final Rect tmpRect;
    private final RectF tmpRectF;
    private Typeface typeface;

    @Retention(RetentionPolicy.SOURCE)
    public @interface BoxBackgroundMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface EndIconMode {
    }

    public interface OnEditTextAttachedListener {
        void onEditTextAttached(TextInputLayout textInputLayout);
    }

    public interface OnEndIconChangedListener {
        void onEndIconChanged(TextInputLayout textInputLayout, int i);
    }

    public TextInputLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public TextInputLayout(Context context, AttributeSet attrs) {
        this(context, attrs, C0822R.attr.textInputStyle);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public TextInputLayout(android.content.Context r31, android.util.AttributeSet r32, int r33) {
        /*
            r30 = this;
            r0 = r30
            r7 = r32
            r8 = r33
            int r1 = DEF_STYLE_RES
            r2 = r31
            android.content.Context r1 = com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap(r2, r7, r8, r1)
            r0.<init>(r1, r7, r8)
            com.google.android.material.textfield.IndicatorViewController r1 = new com.google.android.material.textfield.IndicatorViewController
            r1.<init>(r0)
            r0.indicatorViewController = r1
            android.graphics.Rect r1 = new android.graphics.Rect
            r1.<init>()
            r0.tmpRect = r1
            android.graphics.Rect r1 = new android.graphics.Rect
            r1.<init>()
            r0.tmpBoundsRect = r1
            android.graphics.RectF r1 = new android.graphics.RectF
            r1.<init>()
            r0.tmpRectF = r1
            java.util.LinkedHashSet r1 = new java.util.LinkedHashSet
            r1.<init>()
            r0.editTextAttachedListeners = r1
            r9 = 0
            r0.endIconMode = r9
            android.util.SparseArray r1 = new android.util.SparseArray
            r1.<init>()
            r0.endIconDelegates = r1
            java.util.LinkedHashSet r1 = new java.util.LinkedHashSet
            r1.<init>()
            r0.endIconChangedListeners = r1
            com.google.android.material.internal.CollapsingTextHelper r1 = new com.google.android.material.internal.CollapsingTextHelper
            r1.<init>(r0)
            r0.collapsingTextHelper = r1
            android.content.Context r10 = r30.getContext()
            r11 = 1
            r0.setOrientation(r11)
            r0.setWillNotDraw(r9)
            r0.setAddStatesFromChildren(r11)
            android.widget.FrameLayout r1 = new android.widget.FrameLayout
            r1.<init>(r10)
            r0.inputFrame = r1
            r1.setAddStatesFromChildren(r11)
            android.widget.FrameLayout r1 = r0.inputFrame
            r0.addView(r1)
            android.widget.LinearLayout r1 = new android.widget.LinearLayout
            r1.<init>(r10)
            r0.startLayout = r1
            r1.setOrientation(r9)
            android.widget.LinearLayout r1 = r0.startLayout
            android.widget.FrameLayout$LayoutParams r2 = new android.widget.FrameLayout$LayoutParams
            r12 = -2
            r13 = -1
            r3 = 8388611(0x800003, float:1.1754948E-38)
            r2.<init>(r12, r13, r3)
            r1.setLayoutParams(r2)
            android.widget.FrameLayout r1 = r0.inputFrame
            android.widget.LinearLayout r2 = r0.startLayout
            r1.addView(r2)
            android.widget.LinearLayout r1 = new android.widget.LinearLayout
            r1.<init>(r10)
            r0.endLayout = r1
            r1.setOrientation(r9)
            android.widget.LinearLayout r1 = r0.endLayout
            android.widget.FrameLayout$LayoutParams r2 = new android.widget.FrameLayout$LayoutParams
            r3 = 8388613(0x800005, float:1.175495E-38)
            r2.<init>(r12, r13, r3)
            r1.setLayoutParams(r2)
            android.widget.FrameLayout r1 = r0.inputFrame
            android.widget.LinearLayout r2 = r0.endLayout
            r1.addView(r2)
            android.widget.FrameLayout r1 = new android.widget.FrameLayout
            r1.<init>(r10)
            r0.endIconFrame = r1
            android.widget.FrameLayout$LayoutParams r2 = new android.widget.FrameLayout$LayoutParams
            r2.<init>(r12, r13)
            r1.setLayoutParams(r2)
            com.google.android.material.internal.CollapsingTextHelper r1 = r0.collapsingTextHelper
            android.animation.TimeInterpolator r2 = com.google.android.material.animation.AnimationUtils.LINEAR_INTERPOLATOR
            r1.setTextSizeInterpolator(r2)
            com.google.android.material.internal.CollapsingTextHelper r1 = r0.collapsingTextHelper
            android.animation.TimeInterpolator r2 = com.google.android.material.animation.AnimationUtils.LINEAR_INTERPOLATOR
            r1.setPositionInterpolator(r2)
            com.google.android.material.internal.CollapsingTextHelper r1 = r0.collapsingTextHelper
            r2 = 8388659(0x800033, float:1.1755015E-38)
            r1.setCollapsedTextGravity(r2)
            int[] r3 = com.google.android.material.C0822R.styleable.TextInputLayout
            int r5 = DEF_STYLE_RES
            r1 = 5
            int[] r6 = new int[r1]
            int r1 = com.google.android.material.C0822R.styleable.TextInputLayout_counterTextAppearance
            r6[r9] = r1
            int r1 = com.google.android.material.C0822R.styleable.TextInputLayout_counterOverflowTextAppearance
            r6[r11] = r1
            int r1 = com.google.android.material.C0822R.styleable.TextInputLayout_errorTextAppearance
            r14 = 2
            r6[r14] = r1
            int r1 = com.google.android.material.C0822R.styleable.TextInputLayout_helperTextTextAppearance
            r15 = 3
            r6[r15] = r1
            int r1 = com.google.android.material.C0822R.styleable.TextInputLayout_hintTextAppearance
            r2 = 4
            r6[r2] = r1
            r1 = r10
            r2 = r32
            r4 = r33
            androidx.appcompat.widget.TintTypedArray r1 = com.google.android.material.internal.ThemeEnforcement.obtainTintedStyledAttributes(r1, r2, r3, r4, r5, r6)
            int r2 = com.google.android.material.C0822R.styleable.TextInputLayout_hintEnabled
            boolean r2 = r1.getBoolean(r2, r11)
            r0.hintEnabled = r2
            int r2 = com.google.android.material.C0822R.styleable.TextInputLayout_android_hint
            java.lang.CharSequence r2 = r1.getText(r2)
            r0.setHint(r2)
            int r2 = com.google.android.material.C0822R.styleable.TextInputLayout_hintAnimationEnabled
            boolean r2 = r1.getBoolean(r2, r11)
            r0.hintAnimationEnabled = r2
            int r2 = DEF_STYLE_RES
            com.google.android.material.shape.ShapeAppearanceModel$Builder r2 = com.google.android.material.shape.ShapeAppearanceModel.builder((android.content.Context) r10, (android.util.AttributeSet) r7, (int) r8, (int) r2)
            com.google.android.material.shape.ShapeAppearanceModel r2 = r2.build()
            r0.shapeAppearanceModel = r2
            android.content.res.Resources r2 = r10.getResources()
            int r3 = com.google.android.material.C0822R.dimen.mtrl_textinput_box_label_cutout_padding
            int r2 = r2.getDimensionPixelOffset(r3)
            r0.boxLabelCutoutPaddingPx = r2
            int r2 = com.google.android.material.C0822R.styleable.TextInputLayout_boxCollapsedPaddingTop
            int r2 = r1.getDimensionPixelOffset(r2, r9)
            r0.boxCollapsedPaddingTopPx = r2
            int r2 = com.google.android.material.C0822R.styleable.TextInputLayout_boxStrokeWidth
            android.content.res.Resources r3 = r10.getResources()
            int r4 = com.google.android.material.C0822R.dimen.mtrl_textinput_box_stroke_width_default
            int r3 = r3.getDimensionPixelSize(r4)
            int r2 = r1.getDimensionPixelSize(r2, r3)
            r0.boxStrokeWidthDefaultPx = r2
            int r2 = com.google.android.material.C0822R.styleable.TextInputLayout_boxStrokeWidthFocused
            android.content.res.Resources r3 = r10.getResources()
            int r4 = com.google.android.material.C0822R.dimen.mtrl_textinput_box_stroke_width_focused
            int r3 = r3.getDimensionPixelSize(r4)
            int r2 = r1.getDimensionPixelSize(r2, r3)
            r0.boxStrokeWidthFocusedPx = r2
            int r2 = r0.boxStrokeWidthDefaultPx
            r0.boxStrokeWidthPx = r2
            int r2 = com.google.android.material.C0822R.styleable.TextInputLayout_boxCornerRadiusTopStart
            r3 = -1082130432(0xffffffffbf800000, float:-1.0)
            float r2 = r1.getDimension(r2, r3)
            int r4 = com.google.android.material.C0822R.styleable.TextInputLayout_boxCornerRadiusTopEnd
            float r4 = r1.getDimension(r4, r3)
            int r5 = com.google.android.material.C0822R.styleable.TextInputLayout_boxCornerRadiusBottomEnd
            float r5 = r1.getDimension(r5, r3)
            int r6 = com.google.android.material.C0822R.styleable.TextInputLayout_boxCornerRadiusBottomStart
            float r3 = r1.getDimension(r6, r3)
            com.google.android.material.shape.ShapeAppearanceModel r6 = r0.shapeAppearanceModel
            com.google.android.material.shape.ShapeAppearanceModel$Builder r6 = r6.toBuilder()
            r16 = 0
            int r17 = (r2 > r16 ? 1 : (r2 == r16 ? 0 : -1))
            if (r17 < 0) goto L_0x017e
            r6.setTopLeftCornerSize((float) r2)
        L_0x017e:
            int r17 = (r4 > r16 ? 1 : (r4 == r16 ? 0 : -1))
            if (r17 < 0) goto L_0x0185
            r6.setTopRightCornerSize((float) r4)
        L_0x0185:
            int r17 = (r5 > r16 ? 1 : (r5 == r16 ? 0 : -1))
            if (r17 < 0) goto L_0x018c
            r6.setBottomRightCornerSize((float) r5)
        L_0x018c:
            int r16 = (r3 > r16 ? 1 : (r3 == r16 ? 0 : -1))
            if (r16 < 0) goto L_0x0193
            r6.setBottomLeftCornerSize((float) r3)
        L_0x0193:
            com.google.android.material.shape.ShapeAppearanceModel r12 = r6.build()
            r0.shapeAppearanceModel = r12
            int r12 = com.google.android.material.C0822R.styleable.TextInputLayout_boxBackgroundColor
            android.content.res.ColorStateList r12 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r10, (androidx.appcompat.widget.TintTypedArray) r1, (int) r12)
            if (r12 == 0) goto L_0x01f5
            int r15 = r12.getDefaultColor()
            r0.defaultFilledBackgroundColor = r15
            r0.boxBackgroundColor = r15
            boolean r15 = r12.isStateful()
            r17 = -16842910(0xfffffffffefeff62, float:-1.6947497E38)
            if (r15 == 0) goto L_0x01d3
            int[] r15 = new int[r11]
            r15[r9] = r17
            int r15 = r12.getColorForState(r15, r13)
            r0.disabledFilledBackgroundColor = r15
            int[] r15 = new int[r14]
            r15 = {16842908, 16842910} // fill-array
            int r15 = r12.getColorForState(r15, r13)
            r0.focusedFilledBackgroundColor = r15
            int[] r15 = new int[r14]
            r15 = {16843623, 16842910} // fill-array
            int r15 = r12.getColorForState(r15, r13)
            r0.hoveredFilledBackgroundColor = r15
            goto L_0x01ff
        L_0x01d3:
            int r15 = r0.defaultFilledBackgroundColor
            r0.focusedFilledBackgroundColor = r15
            int r15 = com.google.android.material.C0822R.C0823color.mtrl_filled_background_color
            android.content.res.ColorStateList r15 = androidx.appcompat.content.res.AppCompatResources.getColorStateList(r10, r15)
            int[] r14 = new int[r11]
            r14[r9] = r17
            int r14 = r15.getColorForState(r14, r13)
            r0.disabledFilledBackgroundColor = r14
            int[] r14 = new int[r11]
            r17 = 16843623(0x1010367, float:2.3696E-38)
            r14[r9] = r17
            int r14 = r15.getColorForState(r14, r13)
            r0.hoveredFilledBackgroundColor = r14
            goto L_0x01ff
        L_0x01f5:
            r0.boxBackgroundColor = r9
            r0.defaultFilledBackgroundColor = r9
            r0.disabledFilledBackgroundColor = r9
            r0.focusedFilledBackgroundColor = r9
            r0.hoveredFilledBackgroundColor = r9
        L_0x01ff:
            int r14 = com.google.android.material.C0822R.styleable.TextInputLayout_android_textColorHint
            boolean r14 = r1.hasValue(r14)
            if (r14 == 0) goto L_0x0211
            int r14 = com.google.android.material.C0822R.styleable.TextInputLayout_android_textColorHint
            android.content.res.ColorStateList r14 = r1.getColorStateList(r14)
            r0.focusedTextColor = r14
            r0.defaultHintTextColor = r14
        L_0x0211:
            int r14 = com.google.android.material.C0822R.styleable.TextInputLayout_boxStrokeColor
            android.content.res.ColorStateList r14 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r10, (androidx.appcompat.widget.TintTypedArray) r1, (int) r14)
            int r15 = com.google.android.material.C0822R.styleable.TextInputLayout_boxStrokeColor
            int r15 = r1.getColor(r15, r9)
            r0.focusedStrokeColor = r15
            int r15 = com.google.android.material.C0822R.C0823color.mtrl_textinput_default_box_stroke_color
            int r15 = androidx.core.content.ContextCompat.getColor(r10, r15)
            r0.defaultStrokeColor = r15
            int r15 = com.google.android.material.C0822R.C0823color.mtrl_textinput_disabled_color
            int r15 = androidx.core.content.ContextCompat.getColor(r10, r15)
            r0.disabledColor = r15
            int r15 = com.google.android.material.C0822R.C0823color.mtrl_textinput_hovered_box_stroke_color
            int r15 = androidx.core.content.ContextCompat.getColor(r10, r15)
            r0.hoveredStrokeColor = r15
            if (r14 == 0) goto L_0x023c
            r0.setBoxStrokeColorStateList(r14)
        L_0x023c:
            int r15 = com.google.android.material.C0822R.styleable.TextInputLayout_boxStrokeErrorColor
            boolean r15 = r1.hasValue(r15)
            if (r15 == 0) goto L_0x024d
            int r15 = com.google.android.material.C0822R.styleable.TextInputLayout_boxStrokeErrorColor
            android.content.res.ColorStateList r15 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r10, (androidx.appcompat.widget.TintTypedArray) r1, (int) r15)
            r0.setBoxStrokeErrorColor(r15)
        L_0x024d:
            int r15 = com.google.android.material.C0822R.styleable.TextInputLayout_hintTextAppearance
            int r15 = r1.getResourceId(r15, r13)
            if (r15 == r13) goto L_0x025e
            int r11 = com.google.android.material.C0822R.styleable.TextInputLayout_hintTextAppearance
            int r11 = r1.getResourceId(r11, r9)
            r0.setHintTextAppearance(r11)
        L_0x025e:
            int r11 = com.google.android.material.C0822R.styleable.TextInputLayout_errorTextAppearance
            int r11 = r1.getResourceId(r11, r9)
            int r13 = com.google.android.material.C0822R.styleable.TextInputLayout_errorContentDescription
            java.lang.CharSequence r13 = r1.getText(r13)
            r18 = r2
            int r2 = com.google.android.material.C0822R.styleable.TextInputLayout_errorEnabled
            boolean r2 = r1.getBoolean(r2, r9)
            android.content.Context r19 = r30.getContext()
            android.view.LayoutInflater r9 = android.view.LayoutInflater.from(r19)
            r19 = r3
            int r3 = com.google.android.material.C0822R.layout.design_text_input_end_icon
            r20 = r4
            android.widget.LinearLayout r4 = r0.endLayout
            r21 = r5
            r5 = 0
            android.view.View r3 = r9.inflate(r3, r4, r5)
            com.google.android.material.internal.CheckableImageButton r3 = (com.google.android.material.internal.CheckableImageButton) r3
            r0.errorIconView = r3
            r4 = 8
            r3.setVisibility(r4)
            int r3 = com.google.android.material.C0822R.styleable.TextInputLayout_errorIconDrawable
            boolean r3 = r1.hasValue(r3)
            if (r3 == 0) goto L_0x02a4
            int r3 = com.google.android.material.C0822R.styleable.TextInputLayout_errorIconDrawable
            android.graphics.drawable.Drawable r3 = r1.getDrawable(r3)
            r0.setErrorIconDrawable((android.graphics.drawable.Drawable) r3)
        L_0x02a4:
            int r3 = com.google.android.material.C0822R.styleable.TextInputLayout_errorIconTint
            boolean r3 = r1.hasValue(r3)
            if (r3 == 0) goto L_0x02b5
            int r3 = com.google.android.material.C0822R.styleable.TextInputLayout_errorIconTint
            android.content.res.ColorStateList r3 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r10, (androidx.appcompat.widget.TintTypedArray) r1, (int) r3)
            r0.setErrorIconTintList(r3)
        L_0x02b5:
            int r3 = com.google.android.material.C0822R.styleable.TextInputLayout_errorIconTintMode
            boolean r3 = r1.hasValue(r3)
            r5 = 0
            if (r3 == 0) goto L_0x02cc
            int r3 = com.google.android.material.C0822R.styleable.TextInputLayout_errorIconTintMode
            r9 = -1
            int r3 = r1.getInt(r3, r9)
            android.graphics.PorterDuff$Mode r3 = com.google.android.material.internal.ViewUtils.parseTintMode(r3, r5)
            r0.setErrorIconTintMode(r3)
        L_0x02cc:
            com.google.android.material.internal.CheckableImageButton r3 = r0.errorIconView
            android.content.res.Resources r9 = r30.getResources()
            int r5 = com.google.android.material.C0822R.string.error_icon_content_description
            java.lang.CharSequence r5 = r9.getText(r5)
            r3.setContentDescription(r5)
            com.google.android.material.internal.CheckableImageButton r3 = r0.errorIconView
            r5 = 2
            androidx.core.view.ViewCompat.setImportantForAccessibility(r3, r5)
            com.google.android.material.internal.CheckableImageButton r3 = r0.errorIconView
            r5 = 0
            r3.setClickable(r5)
            com.google.android.material.internal.CheckableImageButton r3 = r0.errorIconView
            r3.setPressable(r5)
            com.google.android.material.internal.CheckableImageButton r3 = r0.errorIconView
            r3.setFocusable(r5)
            int r3 = com.google.android.material.C0822R.styleable.TextInputLayout_helperTextTextAppearance
            int r3 = r1.getResourceId(r3, r5)
            int r9 = com.google.android.material.C0822R.styleable.TextInputLayout_helperTextEnabled
            boolean r9 = r1.getBoolean(r9, r5)
            int r4 = com.google.android.material.C0822R.styleable.TextInputLayout_helperText
            java.lang.CharSequence r4 = r1.getText(r4)
            r22 = r6
            int r6 = com.google.android.material.C0822R.styleable.TextInputLayout_placeholderTextAppearance
            int r6 = r1.getResourceId(r6, r5)
            int r5 = com.google.android.material.C0822R.styleable.TextInputLayout_placeholderText
            java.lang.CharSequence r5 = r1.getText(r5)
            int r7 = com.google.android.material.C0822R.styleable.TextInputLayout_prefixTextAppearance
            r8 = 0
            int r7 = r1.getResourceId(r7, r8)
            int r8 = com.google.android.material.C0822R.styleable.TextInputLayout_prefixText
            java.lang.CharSequence r8 = r1.getText(r8)
            r23 = r12
            int r12 = com.google.android.material.C0822R.styleable.TextInputLayout_suffixTextAppearance
            r24 = r14
            r14 = 0
            int r12 = r1.getResourceId(r12, r14)
            int r14 = com.google.android.material.C0822R.styleable.TextInputLayout_suffixText
            java.lang.CharSequence r14 = r1.getText(r14)
            r25 = r15
            int r15 = com.google.android.material.C0822R.styleable.TextInputLayout_counterEnabled
            r26 = r12
            r12 = 0
            boolean r15 = r1.getBoolean(r15, r12)
            int r12 = com.google.android.material.C0822R.styleable.TextInputLayout_counterMaxLength
            r27 = r15
            r15 = -1
            int r12 = r1.getInt(r12, r15)
            r0.setCounterMaxLength(r12)
            int r12 = com.google.android.material.C0822R.styleable.TextInputLayout_counterTextAppearance
            r15 = 0
            int r12 = r1.getResourceId(r12, r15)
            r0.counterTextAppearance = r12
            int r12 = com.google.android.material.C0822R.styleable.TextInputLayout_counterOverflowTextAppearance
            int r12 = r1.getResourceId(r12, r15)
            r0.counterOverflowTextAppearance = r12
            android.content.Context r12 = r30.getContext()
            android.view.LayoutInflater r12 = android.view.LayoutInflater.from(r12)
            int r15 = com.google.android.material.C0822R.layout.design_text_input_start_icon
            r28 = r14
            android.widget.LinearLayout r14 = r0.startLayout
            r29 = r7
            r7 = 0
            android.view.View r12 = r12.inflate(r15, r14, r7)
            com.google.android.material.internal.CheckableImageButton r12 = (com.google.android.material.internal.CheckableImageButton) r12
            r0.startIconView = r12
            r7 = 8
            r12.setVisibility(r7)
            r7 = 0
            r0.setStartIconOnClickListener(r7)
            r0.setStartIconOnLongClickListener(r7)
            int r7 = com.google.android.material.C0822R.styleable.TextInputLayout_startIconDrawable
            boolean r7 = r1.hasValue(r7)
            if (r7 == 0) goto L_0x03a9
            int r7 = com.google.android.material.C0822R.styleable.TextInputLayout_startIconDrawable
            android.graphics.drawable.Drawable r7 = r1.getDrawable(r7)
            r0.setStartIconDrawable((android.graphics.drawable.Drawable) r7)
            int r7 = com.google.android.material.C0822R.styleable.TextInputLayout_startIconContentDescription
            boolean r7 = r1.hasValue(r7)
            if (r7 == 0) goto L_0x039f
            int r7 = com.google.android.material.C0822R.styleable.TextInputLayout_startIconContentDescription
            java.lang.CharSequence r7 = r1.getText(r7)
            r0.setStartIconContentDescription((java.lang.CharSequence) r7)
        L_0x039f:
            int r7 = com.google.android.material.C0822R.styleable.TextInputLayout_startIconCheckable
            r12 = 1
            boolean r7 = r1.getBoolean(r7, r12)
            r0.setStartIconCheckable(r7)
        L_0x03a9:
            int r7 = com.google.android.material.C0822R.styleable.TextInputLayout_startIconTint
            boolean r7 = r1.hasValue(r7)
            if (r7 == 0) goto L_0x03ba
            int r7 = com.google.android.material.C0822R.styleable.TextInputLayout_startIconTint
            android.content.res.ColorStateList r7 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r10, (androidx.appcompat.widget.TintTypedArray) r1, (int) r7)
            r0.setStartIconTintList(r7)
        L_0x03ba:
            int r7 = com.google.android.material.C0822R.styleable.TextInputLayout_startIconTintMode
            boolean r7 = r1.hasValue(r7)
            if (r7 == 0) goto L_0x03d1
            int r7 = com.google.android.material.C0822R.styleable.TextInputLayout_startIconTintMode
            r12 = -1
            int r7 = r1.getInt(r7, r12)
            r12 = 0
            android.graphics.PorterDuff$Mode r7 = com.google.android.material.internal.ViewUtils.parseTintMode(r7, r12)
            r0.setStartIconTintMode(r7)
        L_0x03d1:
            int r7 = com.google.android.material.C0822R.styleable.TextInputLayout_boxBackgroundMode
            r12 = 0
            int r7 = r1.getInt(r7, r12)
            r0.setBoxBackgroundMode(r7)
            android.content.Context r7 = r30.getContext()
            android.view.LayoutInflater r7 = android.view.LayoutInflater.from(r7)
            int r14 = com.google.android.material.C0822R.layout.design_text_input_end_icon
            android.widget.FrameLayout r15 = r0.endIconFrame
            android.view.View r7 = r7.inflate(r14, r15, r12)
            com.google.android.material.internal.CheckableImageButton r7 = (com.google.android.material.internal.CheckableImageButton) r7
            r0.endIconView = r7
            android.widget.FrameLayout r12 = r0.endIconFrame
            r12.addView(r7)
            com.google.android.material.internal.CheckableImageButton r7 = r0.endIconView
            r12 = 8
            r7.setVisibility(r12)
            android.util.SparseArray<com.google.android.material.textfield.EndIconDelegate> r7 = r0.endIconDelegates
            com.google.android.material.textfield.CustomEndIconDelegate r12 = new com.google.android.material.textfield.CustomEndIconDelegate
            r12.<init>(r0)
            r14 = -1
            r7.append(r14, r12)
            android.util.SparseArray<com.google.android.material.textfield.EndIconDelegate> r7 = r0.endIconDelegates
            com.google.android.material.textfield.NoEndIconDelegate r12 = new com.google.android.material.textfield.NoEndIconDelegate
            r12.<init>(r0)
            r14 = 0
            r7.append(r14, r12)
            android.util.SparseArray<com.google.android.material.textfield.EndIconDelegate> r7 = r0.endIconDelegates
            com.google.android.material.textfield.PasswordToggleEndIconDelegate r12 = new com.google.android.material.textfield.PasswordToggleEndIconDelegate
            r12.<init>(r0)
            r14 = 1
            r7.append(r14, r12)
            android.util.SparseArray<com.google.android.material.textfield.EndIconDelegate> r7 = r0.endIconDelegates
            com.google.android.material.textfield.ClearTextEndIconDelegate r12 = new com.google.android.material.textfield.ClearTextEndIconDelegate
            r12.<init>(r0)
            r14 = 2
            r7.append(r14, r12)
            android.util.SparseArray<com.google.android.material.textfield.EndIconDelegate> r7 = r0.endIconDelegates
            com.google.android.material.textfield.DropdownMenuEndIconDelegate r12 = new com.google.android.material.textfield.DropdownMenuEndIconDelegate
            r12.<init>(r0)
            r14 = 3
            r7.append(r14, r12)
            int r7 = com.google.android.material.C0822R.styleable.TextInputLayout_endIconMode
            boolean r7 = r1.hasValue(r7)
            if (r7 == 0) goto L_0x0472
            int r7 = com.google.android.material.C0822R.styleable.TextInputLayout_endIconMode
            r12 = 0
            int r7 = r1.getInt(r7, r12)
            r0.setEndIconMode(r7)
            int r7 = com.google.android.material.C0822R.styleable.TextInputLayout_endIconDrawable
            boolean r7 = r1.hasValue(r7)
            if (r7 == 0) goto L_0x0456
            int r7 = com.google.android.material.C0822R.styleable.TextInputLayout_endIconDrawable
            android.graphics.drawable.Drawable r7 = r1.getDrawable(r7)
            r0.setEndIconDrawable((android.graphics.drawable.Drawable) r7)
        L_0x0456:
            int r7 = com.google.android.material.C0822R.styleable.TextInputLayout_endIconContentDescription
            boolean r7 = r1.hasValue(r7)
            if (r7 == 0) goto L_0x0467
            int r7 = com.google.android.material.C0822R.styleable.TextInputLayout_endIconContentDescription
            java.lang.CharSequence r7 = r1.getText(r7)
            r0.setEndIconContentDescription((java.lang.CharSequence) r7)
        L_0x0467:
            int r7 = com.google.android.material.C0822R.styleable.TextInputLayout_endIconCheckable
            r12 = 1
            boolean r7 = r1.getBoolean(r7, r12)
            r0.setEndIconCheckable(r7)
            goto L_0x04be
        L_0x0472:
            int r7 = com.google.android.material.C0822R.styleable.TextInputLayout_passwordToggleEnabled
            boolean r7 = r1.hasValue(r7)
            if (r7 == 0) goto L_0x04be
            int r7 = com.google.android.material.C0822R.styleable.TextInputLayout_passwordToggleEnabled
            r12 = 0
            boolean r7 = r1.getBoolean(r7, r12)
            r0.setEndIconMode(r7)
            int r12 = com.google.android.material.C0822R.styleable.TextInputLayout_passwordToggleDrawable
            android.graphics.drawable.Drawable r12 = r1.getDrawable(r12)
            r0.setEndIconDrawable((android.graphics.drawable.Drawable) r12)
            int r12 = com.google.android.material.C0822R.styleable.TextInputLayout_passwordToggleContentDescription
            java.lang.CharSequence r12 = r1.getText(r12)
            r0.setEndIconContentDescription((java.lang.CharSequence) r12)
            int r12 = com.google.android.material.C0822R.styleable.TextInputLayout_passwordToggleTint
            boolean r12 = r1.hasValue(r12)
            if (r12 == 0) goto L_0x04a7
            int r12 = com.google.android.material.C0822R.styleable.TextInputLayout_passwordToggleTint
            android.content.res.ColorStateList r12 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r10, (androidx.appcompat.widget.TintTypedArray) r1, (int) r12)
            r0.setEndIconTintList(r12)
        L_0x04a7:
            int r12 = com.google.android.material.C0822R.styleable.TextInputLayout_passwordToggleTintMode
            boolean r12 = r1.hasValue(r12)
            if (r12 == 0) goto L_0x04be
            int r12 = com.google.android.material.C0822R.styleable.TextInputLayout_passwordToggleTintMode
            r14 = -1
            int r12 = r1.getInt(r12, r14)
            r14 = 0
            android.graphics.PorterDuff$Mode r12 = com.google.android.material.internal.ViewUtils.parseTintMode(r12, r14)
            r0.setEndIconTintMode(r12)
        L_0x04be:
            int r7 = com.google.android.material.C0822R.styleable.TextInputLayout_passwordToggleEnabled
            boolean r7 = r1.hasValue(r7)
            if (r7 != 0) goto L_0x04ee
            int r7 = com.google.android.material.C0822R.styleable.TextInputLayout_endIconTint
            boolean r7 = r1.hasValue(r7)
            if (r7 == 0) goto L_0x04d7
            int r7 = com.google.android.material.C0822R.styleable.TextInputLayout_endIconTint
            android.content.res.ColorStateList r7 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r10, (androidx.appcompat.widget.TintTypedArray) r1, (int) r7)
            r0.setEndIconTintList(r7)
        L_0x04d7:
            int r7 = com.google.android.material.C0822R.styleable.TextInputLayout_endIconTintMode
            boolean r7 = r1.hasValue(r7)
            if (r7 == 0) goto L_0x04ee
            int r7 = com.google.android.material.C0822R.styleable.TextInputLayout_endIconTintMode
            r12 = -1
            int r7 = r1.getInt(r7, r12)
            r12 = 0
            android.graphics.PorterDuff$Mode r7 = com.google.android.material.internal.ViewUtils.parseTintMode(r7, r12)
            r0.setEndIconTintMode(r7)
        L_0x04ee:
            androidx.appcompat.widget.AppCompatTextView r7 = new androidx.appcompat.widget.AppCompatTextView
            r7.<init>(r10)
            r0.prefixTextView = r7
            int r12 = com.google.android.material.C0822R.C0825id.textinput_prefix_text
            r7.setId(r12)
            android.widget.TextView r7 = r0.prefixTextView
            android.widget.FrameLayout$LayoutParams r12 = new android.widget.FrameLayout$LayoutParams
            r14 = -2
            r12.<init>(r14, r14)
            r7.setLayoutParams(r12)
            android.widget.TextView r7 = r0.prefixTextView
            r12 = 1
            androidx.core.view.ViewCompat.setAccessibilityLiveRegion(r7, r12)
            android.widget.LinearLayout r7 = r0.startLayout
            com.google.android.material.internal.CheckableImageButton r12 = r0.startIconView
            r7.addView(r12)
            android.widget.LinearLayout r7 = r0.startLayout
            android.widget.TextView r12 = r0.prefixTextView
            r7.addView(r12)
            androidx.appcompat.widget.AppCompatTextView r7 = new androidx.appcompat.widget.AppCompatTextView
            r7.<init>(r10)
            r0.suffixTextView = r7
            int r12 = com.google.android.material.C0822R.C0825id.textinput_suffix_text
            r7.setId(r12)
            android.widget.TextView r7 = r0.suffixTextView
            android.widget.FrameLayout$LayoutParams r12 = new android.widget.FrameLayout$LayoutParams
            r14 = 80
            r15 = -2
            r12.<init>(r15, r15, r14)
            r7.setLayoutParams(r12)
            android.widget.TextView r7 = r0.suffixTextView
            r12 = 1
            androidx.core.view.ViewCompat.setAccessibilityLiveRegion(r7, r12)
            android.widget.LinearLayout r7 = r0.endLayout
            android.widget.TextView r12 = r0.suffixTextView
            r7.addView(r12)
            android.widget.LinearLayout r7 = r0.endLayout
            com.google.android.material.internal.CheckableImageButton r12 = r0.errorIconView
            r7.addView(r12)
            android.widget.LinearLayout r7 = r0.endLayout
            android.widget.FrameLayout r12 = r0.endIconFrame
            r7.addView(r12)
            r0.setHelperTextEnabled(r9)
            r0.setHelperText(r4)
            r0.setHelperTextTextAppearance(r3)
            r0.setErrorEnabled(r2)
            r0.setErrorTextAppearance(r11)
            r0.setErrorContentDescription(r13)
            int r7 = r0.counterTextAppearance
            r0.setCounterTextAppearance(r7)
            int r7 = r0.counterOverflowTextAppearance
            r0.setCounterOverflowTextAppearance(r7)
            r0.setPlaceholderText(r5)
            r0.setPlaceholderTextAppearance(r6)
            r0.setPrefixText(r8)
            r7 = r29
            r0.setPrefixTextAppearance(r7)
            r12 = r28
            r0.setSuffixText(r12)
            r14 = r26
            r0.setSuffixTextAppearance(r14)
            int r15 = com.google.android.material.C0822R.styleable.TextInputLayout_errorTextColor
            boolean r15 = r1.hasValue(r15)
            if (r15 == 0) goto L_0x0592
            int r15 = com.google.android.material.C0822R.styleable.TextInputLayout_errorTextColor
            android.content.res.ColorStateList r15 = r1.getColorStateList(r15)
            r0.setErrorTextColor(r15)
        L_0x0592:
            int r15 = com.google.android.material.C0822R.styleable.TextInputLayout_helperTextTextColor
            boolean r15 = r1.hasValue(r15)
            if (r15 == 0) goto L_0x05a3
            int r15 = com.google.android.material.C0822R.styleable.TextInputLayout_helperTextTextColor
            android.content.res.ColorStateList r15 = r1.getColorStateList(r15)
            r0.setHelperTextColor(r15)
        L_0x05a3:
            int r15 = com.google.android.material.C0822R.styleable.TextInputLayout_hintTextColor
            boolean r15 = r1.hasValue(r15)
            if (r15 == 0) goto L_0x05b4
            int r15 = com.google.android.material.C0822R.styleable.TextInputLayout_hintTextColor
            android.content.res.ColorStateList r15 = r1.getColorStateList(r15)
            r0.setHintTextColor(r15)
        L_0x05b4:
            int r15 = com.google.android.material.C0822R.styleable.TextInputLayout_counterTextColor
            boolean r15 = r1.hasValue(r15)
            if (r15 == 0) goto L_0x05c5
            int r15 = com.google.android.material.C0822R.styleable.TextInputLayout_counterTextColor
            android.content.res.ColorStateList r15 = r1.getColorStateList(r15)
            r0.setCounterTextColor(r15)
        L_0x05c5:
            int r15 = com.google.android.material.C0822R.styleable.TextInputLayout_counterOverflowTextColor
            boolean r15 = r1.hasValue(r15)
            if (r15 == 0) goto L_0x05d6
            int r15 = com.google.android.material.C0822R.styleable.TextInputLayout_counterOverflowTextColor
            android.content.res.ColorStateList r15 = r1.getColorStateList(r15)
            r0.setCounterOverflowTextColor(r15)
        L_0x05d6:
            int r15 = com.google.android.material.C0822R.styleable.TextInputLayout_placeholderTextColor
            boolean r15 = r1.hasValue(r15)
            if (r15 == 0) goto L_0x05e7
            int r15 = com.google.android.material.C0822R.styleable.TextInputLayout_placeholderTextColor
            android.content.res.ColorStateList r15 = r1.getColorStateList(r15)
            r0.setPlaceholderTextColor(r15)
        L_0x05e7:
            int r15 = com.google.android.material.C0822R.styleable.TextInputLayout_prefixTextColor
            boolean r15 = r1.hasValue(r15)
            if (r15 == 0) goto L_0x05f8
            int r15 = com.google.android.material.C0822R.styleable.TextInputLayout_prefixTextColor
            android.content.res.ColorStateList r15 = r1.getColorStateList(r15)
            r0.setPrefixTextColor(r15)
        L_0x05f8:
            int r15 = com.google.android.material.C0822R.styleable.TextInputLayout_suffixTextColor
            boolean r15 = r1.hasValue(r15)
            if (r15 == 0) goto L_0x0609
            int r15 = com.google.android.material.C0822R.styleable.TextInputLayout_suffixTextColor
            android.content.res.ColorStateList r15 = r1.getColorStateList(r15)
            r0.setSuffixTextColor(r15)
        L_0x0609:
            r15 = r27
            r0.setCounterEnabled(r15)
            r31 = r2
            int r2 = com.google.android.material.C0822R.styleable.TextInputLayout_android_enabled
            r16 = r3
            r3 = 1
            boolean r2 = r1.getBoolean(r2, r3)
            r0.setEnabled(r2)
            r1.recycle()
            r2 = 2
            androidx.core.view.ViewCompat.setImportantForAccessibility(r0, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.textfield.TextInputLayout.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (child instanceof EditText) {
            FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(params);
            flp.gravity = (flp.gravity & -113) | 16;
            this.inputFrame.addView(child, flp);
            this.inputFrame.setLayoutParams(params);
            updateInputLayoutMargins();
            setEditText((EditText) child);
            return;
        }
        super.addView(child, index, params);
    }

    /* access modifiers changed from: package-private */
    public MaterialShapeDrawable getBoxBackground() {
        int i = this.boxBackgroundMode;
        if (i == 1 || i == 2) {
            return this.boxBackground;
        }
        throw new IllegalStateException();
    }

    public void setBoxBackgroundMode(int boxBackgroundMode2) {
        if (boxBackgroundMode2 != this.boxBackgroundMode) {
            this.boxBackgroundMode = boxBackgroundMode2;
            if (this.editText != null) {
                onApplyBoxBackgroundMode();
            }
        }
    }

    public int getBoxBackgroundMode() {
        return this.boxBackgroundMode;
    }

    private void onApplyBoxBackgroundMode() {
        assignBoxBackgroundByMode();
        setEditTextBoxBackground();
        updateTextInputBoxState();
        if (this.boxBackgroundMode != 0) {
            updateInputLayoutMargins();
        }
    }

    private void assignBoxBackgroundByMode() {
        int i = this.boxBackgroundMode;
        if (i == 0) {
            this.boxBackground = null;
            this.boxUnderline = null;
        } else if (i == 1) {
            this.boxBackground = new MaterialShapeDrawable(this.shapeAppearanceModel);
            this.boxUnderline = new MaterialShapeDrawable();
        } else if (i == 2) {
            if (!this.hintEnabled || (this.boxBackground instanceof CutoutDrawable)) {
                this.boxBackground = new MaterialShapeDrawable(this.shapeAppearanceModel);
            } else {
                this.boxBackground = new CutoutDrawable(this.shapeAppearanceModel);
            }
            this.boxUnderline = null;
        } else {
            throw new IllegalArgumentException(this.boxBackgroundMode + " is illegal; only @BoxBackgroundMode constants are supported.");
        }
    }

    private void setEditTextBoxBackground() {
        if (shouldUseEditTextBackgroundForBoxBackground()) {
            ViewCompat.setBackground(this.editText, this.boxBackground);
        }
    }

    private boolean shouldUseEditTextBackgroundForBoxBackground() {
        EditText editText2 = this.editText;
        return (editText2 == null || this.boxBackground == null || editText2.getBackground() != null || this.boxBackgroundMode == 0) ? false : true;
    }

    public void setBoxStrokeWidthResource(int boxStrokeWidthResId) {
        setBoxStrokeWidth(getResources().getDimensionPixelSize(boxStrokeWidthResId));
    }

    public void setBoxStrokeWidth(int boxStrokeWidth) {
        this.boxStrokeWidthDefaultPx = boxStrokeWidth;
        updateTextInputBoxState();
    }

    public int getBoxStrokeWidth() {
        return this.boxStrokeWidthDefaultPx;
    }

    public void setBoxStrokeWidthFocusedResource(int boxStrokeWidthFocusedResId) {
        setBoxStrokeWidthFocused(getResources().getDimensionPixelSize(boxStrokeWidthFocusedResId));
    }

    public void setBoxStrokeWidthFocused(int boxStrokeWidthFocused) {
        this.boxStrokeWidthFocusedPx = boxStrokeWidthFocused;
        updateTextInputBoxState();
    }

    public int getBoxStrokeWidthFocused() {
        return this.boxStrokeWidthFocusedPx;
    }

    public void setBoxStrokeColor(int boxStrokeColor2) {
        if (this.focusedStrokeColor != boxStrokeColor2) {
            this.focusedStrokeColor = boxStrokeColor2;
            updateTextInputBoxState();
        }
    }

    public int getBoxStrokeColor() {
        return this.focusedStrokeColor;
    }

    public void setBoxStrokeColorStateList(ColorStateList boxStrokeColorStateList) {
        if (boxStrokeColorStateList.isStateful()) {
            this.defaultStrokeColor = boxStrokeColorStateList.getDefaultColor();
            this.disabledColor = boxStrokeColorStateList.getColorForState(new int[]{-16842910}, -1);
            this.hoveredStrokeColor = boxStrokeColorStateList.getColorForState(new int[]{16843623, 16842910}, -1);
            this.focusedStrokeColor = boxStrokeColorStateList.getColorForState(new int[]{16842908, 16842910}, -1);
        } else if (this.focusedStrokeColor != boxStrokeColorStateList.getDefaultColor()) {
            this.focusedStrokeColor = boxStrokeColorStateList.getDefaultColor();
        }
        updateTextInputBoxState();
    }

    public void setBoxStrokeErrorColor(ColorStateList strokeErrorColor2) {
        if (this.strokeErrorColor != strokeErrorColor2) {
            this.strokeErrorColor = strokeErrorColor2;
            updateTextInputBoxState();
        }
    }

    public ColorStateList getBoxStrokeErrorColor() {
        return this.strokeErrorColor;
    }

    public void setBoxBackgroundColorResource(int boxBackgroundColorId) {
        setBoxBackgroundColor(ContextCompat.getColor(getContext(), boxBackgroundColorId));
    }

    public void setBoxBackgroundColor(int boxBackgroundColor2) {
        if (this.boxBackgroundColor != boxBackgroundColor2) {
            this.boxBackgroundColor = boxBackgroundColor2;
            this.defaultFilledBackgroundColor = boxBackgroundColor2;
            this.focusedFilledBackgroundColor = boxBackgroundColor2;
            this.hoveredFilledBackgroundColor = boxBackgroundColor2;
            applyBoxAttributes();
        }
    }

    public void setBoxBackgroundColorStateList(ColorStateList boxBackgroundColorStateList) {
        int defaultColor = boxBackgroundColorStateList.getDefaultColor();
        this.defaultFilledBackgroundColor = defaultColor;
        this.boxBackgroundColor = defaultColor;
        this.disabledFilledBackgroundColor = boxBackgroundColorStateList.getColorForState(new int[]{-16842910}, -1);
        this.focusedFilledBackgroundColor = boxBackgroundColorStateList.getColorForState(new int[]{16842908, 16842910}, -1);
        this.hoveredFilledBackgroundColor = boxBackgroundColorStateList.getColorForState(new int[]{16843623, 16842910}, -1);
        applyBoxAttributes();
    }

    public int getBoxBackgroundColor() {
        return this.boxBackgroundColor;
    }

    public void setBoxCornerRadiiResources(int boxCornerRadiusTopStartId, int boxCornerRadiusTopEndId, int boxCornerRadiusBottomEndId, int boxCornerRadiusBottomStartId) {
        setBoxCornerRadii(getContext().getResources().getDimension(boxCornerRadiusTopStartId), getContext().getResources().getDimension(boxCornerRadiusTopEndId), getContext().getResources().getDimension(boxCornerRadiusBottomStartId), getContext().getResources().getDimension(boxCornerRadiusBottomEndId));
    }

    public void setBoxCornerRadii(float boxCornerRadiusTopStart, float boxCornerRadiusTopEnd, float boxCornerRadiusBottomStart, float boxCornerRadiusBottomEnd) {
        MaterialShapeDrawable materialShapeDrawable = this.boxBackground;
        if (materialShapeDrawable == null || materialShapeDrawable.getTopLeftCornerResolvedSize() != boxCornerRadiusTopStart || this.boxBackground.getTopRightCornerResolvedSize() != boxCornerRadiusTopEnd || this.boxBackground.getBottomRightCornerResolvedSize() != boxCornerRadiusBottomEnd || this.boxBackground.getBottomLeftCornerResolvedSize() != boxCornerRadiusBottomStart) {
            this.shapeAppearanceModel = this.shapeAppearanceModel.toBuilder().setTopLeftCornerSize(boxCornerRadiusTopStart).setTopRightCornerSize(boxCornerRadiusTopEnd).setBottomRightCornerSize(boxCornerRadiusBottomEnd).setBottomLeftCornerSize(boxCornerRadiusBottomStart).build();
            applyBoxAttributes();
        }
    }

    public float getBoxCornerRadiusTopStart() {
        return this.boxBackground.getTopLeftCornerResolvedSize();
    }

    public float getBoxCornerRadiusTopEnd() {
        return this.boxBackground.getTopRightCornerResolvedSize();
    }

    public float getBoxCornerRadiusBottomEnd() {
        return this.boxBackground.getBottomLeftCornerResolvedSize();
    }

    public float getBoxCornerRadiusBottomStart() {
        return this.boxBackground.getBottomRightCornerResolvedSize();
    }

    public void setTypeface(Typeface typeface2) {
        if (typeface2 != this.typeface) {
            this.typeface = typeface2;
            this.collapsingTextHelper.setTypefaces(typeface2);
            this.indicatorViewController.setTypefaces(typeface2);
            TextView textView = this.counterView;
            if (textView != null) {
                textView.setTypeface(typeface2);
            }
        }
    }

    public Typeface getTypeface() {
        return this.typeface;
    }

    public void dispatchProvideAutofillStructure(ViewStructure structure, int flags) {
        EditText editText2;
        if (this.originalHint == null || (editText2 = this.editText) == null) {
            super.dispatchProvideAutofillStructure(structure, flags);
            return;
        }
        boolean wasProvidingHint = this.isProvidingHint;
        this.isProvidingHint = false;
        CharSequence hint2 = editText2.getHint();
        this.editText.setHint(this.originalHint);
        try {
            super.dispatchProvideAutofillStructure(structure, flags);
        } finally {
            this.editText.setHint(hint2);
            this.isProvidingHint = wasProvidingHint;
        }
    }

    private void setEditText(EditText editText2) {
        if (this.editText == null) {
            if (this.endIconMode != 3 && !(editText2 instanceof TextInputEditText)) {
                Log.i(LOG_TAG, "EditText added is not a TextInputEditText. Please switch to using that class instead.");
            }
            this.editText = editText2;
            onApplyBoxBackgroundMode();
            setTextInputAccessibilityDelegate(new AccessibilityDelegate(this));
            this.collapsingTextHelper.setTypefaces(this.editText.getTypeface());
            this.collapsingTextHelper.setExpandedTextSize(this.editText.getTextSize());
            int editTextGravity = this.editText.getGravity();
            this.collapsingTextHelper.setCollapsedTextGravity((editTextGravity & -113) | 48);
            this.collapsingTextHelper.setExpandedTextGravity(editTextGravity);
            this.editText.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    TextInputLayout textInputLayout = TextInputLayout.this;
                    textInputLayout.updateLabelState(!textInputLayout.restoringSavedState);
                    if (TextInputLayout.this.counterEnabled) {
                        TextInputLayout.this.updateCounter(s.length());
                    }
                    if (TextInputLayout.this.placeholderEnabled) {
                        TextInputLayout.this.updatePlaceholderText(s.length());
                    }
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
            });
            if (this.defaultHintTextColor == null) {
                this.defaultHintTextColor = this.editText.getHintTextColors();
            }
            if (this.hintEnabled) {
                if (TextUtils.isEmpty(this.hint)) {
                    CharSequence hint2 = this.editText.getHint();
                    this.originalHint = hint2;
                    setHint(hint2);
                    this.editText.setHint((CharSequence) null);
                }
                this.isProvidingHint = true;
            }
            if (this.counterView != null) {
                updateCounter(this.editText.getText().length());
            }
            updateEditTextBackground();
            this.indicatorViewController.adjustIndicatorPadding();
            this.startLayout.bringToFront();
            this.endLayout.bringToFront();
            this.endIconFrame.bringToFront();
            this.errorIconView.bringToFront();
            dispatchOnEditTextAttached();
            updatePrefixTextViewPadding();
            updateSuffixTextViewPadding();
            if (!isEnabled()) {
                editText2.setEnabled(false);
            }
            updateLabelState(false, true);
            return;
        }
        throw new IllegalArgumentException("We already have an EditText, can only have one");
    }

    private void updateInputLayoutMargins() {
        if (this.boxBackgroundMode != 1) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.inputFrame.getLayoutParams();
            int newTopMargin = calculateLabelMarginTop();
            if (newTopMargin != lp.topMargin) {
                lp.topMargin = newTopMargin;
                this.inputFrame.requestLayout();
            }
        }
    }

    public int getBaseline() {
        EditText editText2 = this.editText;
        if (editText2 != null) {
            return editText2.getBaseline() + getPaddingTop() + calculateLabelMarginTop();
        }
        return super.getBaseline();
    }

    /* access modifiers changed from: package-private */
    public void updateLabelState(boolean animate) {
        updateLabelState(animate, false);
    }

    private void updateLabelState(boolean animate, boolean force) {
        ColorStateList colorStateList;
        TextView textView;
        boolean isEnabled = isEnabled();
        EditText editText2 = this.editText;
        boolean hasText = editText2 != null && !TextUtils.isEmpty(editText2.getText());
        EditText editText3 = this.editText;
        boolean hasFocus = editText3 != null && editText3.hasFocus();
        boolean errorShouldBeShown = this.indicatorViewController.errorShouldBeShown();
        ColorStateList colorStateList2 = this.defaultHintTextColor;
        if (colorStateList2 != null) {
            this.collapsingTextHelper.setCollapsedTextColor(colorStateList2);
            this.collapsingTextHelper.setExpandedTextColor(this.defaultHintTextColor);
        }
        if (!isEnabled) {
            ColorStateList colorStateList3 = this.defaultHintTextColor;
            int disabledHintColor = colorStateList3 != null ? colorStateList3.getColorForState(new int[]{-16842910}, this.disabledColor) : this.disabledColor;
            this.collapsingTextHelper.setCollapsedTextColor(ColorStateList.valueOf(disabledHintColor));
            this.collapsingTextHelper.setExpandedTextColor(ColorStateList.valueOf(disabledHintColor));
        } else if (errorShouldBeShown) {
            this.collapsingTextHelper.setCollapsedTextColor(this.indicatorViewController.getErrorViewTextColors());
        } else if (this.counterOverflowed && (textView = this.counterView) != null) {
            this.collapsingTextHelper.setCollapsedTextColor(textView.getTextColors());
        } else if (hasFocus && (colorStateList = this.focusedTextColor) != null) {
            this.collapsingTextHelper.setCollapsedTextColor(colorStateList);
        }
        if (hasText || (isEnabled() && (hasFocus || errorShouldBeShown))) {
            if (force || this.hintExpanded) {
                collapseHint(animate);
            }
        } else if (force || !this.hintExpanded) {
            expandHint(animate);
        }
    }

    public EditText getEditText() {
        return this.editText;
    }

    public void setHint(CharSequence hint2) {
        if (this.hintEnabled) {
            setHintInternal(hint2);
            sendAccessibilityEvent(2048);
        }
    }

    private void setHintInternal(CharSequence hint2) {
        if (!TextUtils.equals(hint2, this.hint)) {
            this.hint = hint2;
            this.collapsingTextHelper.setText(hint2);
            if (!this.hintExpanded) {
                openCutout();
            }
        }
    }

    public CharSequence getHint() {
        if (this.hintEnabled) {
            return this.hint;
        }
        return null;
    }

    public void setHintEnabled(boolean enabled) {
        if (enabled != this.hintEnabled) {
            this.hintEnabled = enabled;
            if (!enabled) {
                this.isProvidingHint = false;
                if (!TextUtils.isEmpty(this.hint) && TextUtils.isEmpty(this.editText.getHint())) {
                    this.editText.setHint(this.hint);
                }
                setHintInternal((CharSequence) null);
            } else {
                CharSequence editTextHint = this.editText.getHint();
                if (!TextUtils.isEmpty(editTextHint)) {
                    if (TextUtils.isEmpty(this.hint)) {
                        setHint(editTextHint);
                    }
                    this.editText.setHint((CharSequence) null);
                }
                this.isProvidingHint = true;
            }
            if (this.editText != null) {
                updateInputLayoutMargins();
            }
        }
    }

    public boolean isHintEnabled() {
        return this.hintEnabled;
    }

    public boolean isProvidingHint() {
        return this.isProvidingHint;
    }

    public void setHintTextAppearance(int resId) {
        this.collapsingTextHelper.setCollapsedTextAppearance(resId);
        this.focusedTextColor = this.collapsingTextHelper.getCollapsedTextColor();
        if (this.editText != null) {
            updateLabelState(false);
            updateInputLayoutMargins();
        }
    }

    public void setHintTextColor(ColorStateList hintTextColor) {
        if (this.focusedTextColor != hintTextColor) {
            if (this.defaultHintTextColor == null) {
                this.collapsingTextHelper.setCollapsedTextColor(hintTextColor);
            }
            this.focusedTextColor = hintTextColor;
            if (this.editText != null) {
                updateLabelState(false);
            }
        }
    }

    public ColorStateList getHintTextColor() {
        return this.focusedTextColor;
    }

    public void setDefaultHintTextColor(ColorStateList textColor) {
        this.defaultHintTextColor = textColor;
        this.focusedTextColor = textColor;
        if (this.editText != null) {
            updateLabelState(false);
        }
    }

    public ColorStateList getDefaultHintTextColor() {
        return this.defaultHintTextColor;
    }

    public void setErrorEnabled(boolean enabled) {
        this.indicatorViewController.setErrorEnabled(enabled);
    }

    public void setErrorTextAppearance(int errorTextAppearance) {
        this.indicatorViewController.setErrorTextAppearance(errorTextAppearance);
    }

    public void setErrorTextColor(ColorStateList errorTextColor) {
        this.indicatorViewController.setErrorViewTextColor(errorTextColor);
    }

    public int getErrorCurrentTextColors() {
        return this.indicatorViewController.getErrorViewCurrentTextColor();
    }

    public void setHelperTextTextAppearance(int helperTextTextAppearance) {
        this.indicatorViewController.setHelperTextAppearance(helperTextTextAppearance);
    }

    public void setHelperTextColor(ColorStateList helperTextColor) {
        this.indicatorViewController.setHelperTextViewTextColor(helperTextColor);
    }

    public boolean isErrorEnabled() {
        return this.indicatorViewController.isErrorEnabled();
    }

    public void setHelperTextEnabled(boolean enabled) {
        this.indicatorViewController.setHelperTextEnabled(enabled);
    }

    public void setHelperText(CharSequence helperText) {
        if (!TextUtils.isEmpty(helperText)) {
            if (!isHelperTextEnabled()) {
                setHelperTextEnabled(true);
            }
            this.indicatorViewController.showHelper(helperText);
        } else if (isHelperTextEnabled()) {
            setHelperTextEnabled(false);
        }
    }

    public boolean isHelperTextEnabled() {
        return this.indicatorViewController.isHelperTextEnabled();
    }

    public int getHelperTextCurrentTextColor() {
        return this.indicatorViewController.getHelperTextViewCurrentTextColor();
    }

    public void setErrorContentDescription(CharSequence errorContentDecription) {
        this.indicatorViewController.setErrorContentDescription(errorContentDecription);
    }

    public CharSequence getErrorContentDescription() {
        return this.indicatorViewController.getErrorContentDescription();
    }

    public void setError(CharSequence errorText) {
        if (!this.indicatorViewController.isErrorEnabled()) {
            if (!TextUtils.isEmpty(errorText)) {
                setErrorEnabled(true);
            } else {
                return;
            }
        }
        if (!TextUtils.isEmpty(errorText)) {
            this.indicatorViewController.showError(errorText);
        } else {
            this.indicatorViewController.hideError();
        }
    }

    public void setErrorIconDrawable(int resId) {
        setErrorIconDrawable(resId != 0 ? AppCompatResources.getDrawable(getContext(), resId) : null);
        refreshErrorIconDrawableState();
    }

    public void setErrorIconDrawable(Drawable errorIconDrawable) {
        this.errorIconView.setImageDrawable(errorIconDrawable);
        setErrorIconVisible(errorIconDrawable != null && this.indicatorViewController.isErrorEnabled());
    }

    public Drawable getErrorIconDrawable() {
        return this.errorIconView.getDrawable();
    }

    public void setErrorIconTintList(ColorStateList errorIconTintList2) {
        this.errorIconTintList = errorIconTintList2;
        Drawable icon = this.errorIconView.getDrawable();
        if (icon != null) {
            icon = DrawableCompat.wrap(icon).mutate();
            DrawableCompat.setTintList(icon, errorIconTintList2);
        }
        if (this.errorIconView.getDrawable() != icon) {
            this.errorIconView.setImageDrawable(icon);
        }
    }

    public void setErrorIconTintMode(PorterDuff.Mode errorIconTintMode) {
        Drawable icon = this.errorIconView.getDrawable();
        if (icon != null) {
            icon = DrawableCompat.wrap(icon).mutate();
            DrawableCompat.setTintMode(icon, errorIconTintMode);
        }
        if (this.errorIconView.getDrawable() != icon) {
            this.errorIconView.setImageDrawable(icon);
        }
    }

    public void setCounterEnabled(boolean enabled) {
        if (this.counterEnabled != enabled) {
            if (enabled) {
                AppCompatTextView appCompatTextView = new AppCompatTextView(getContext());
                this.counterView = appCompatTextView;
                appCompatTextView.setId(C0822R.C0825id.textinput_counter);
                Typeface typeface2 = this.typeface;
                if (typeface2 != null) {
                    this.counterView.setTypeface(typeface2);
                }
                this.counterView.setMaxLines(1);
                this.indicatorViewController.addIndicator(this.counterView, 2);
                MarginLayoutParamsCompat.setMarginStart((ViewGroup.MarginLayoutParams) this.counterView.getLayoutParams(), getResources().getDimensionPixelOffset(C0822R.dimen.mtrl_textinput_counter_margin_start));
                updateCounterTextAppearanceAndColor();
                updateCounter();
            } else {
                this.indicatorViewController.removeIndicator(this.counterView, 2);
                this.counterView = null;
            }
            this.counterEnabled = enabled;
        }
    }

    public void setCounterTextAppearance(int counterTextAppearance2) {
        if (this.counterTextAppearance != counterTextAppearance2) {
            this.counterTextAppearance = counterTextAppearance2;
            updateCounterTextAppearanceAndColor();
        }
    }

    public void setCounterTextColor(ColorStateList counterTextColor2) {
        if (this.counterTextColor != counterTextColor2) {
            this.counterTextColor = counterTextColor2;
            updateCounterTextAppearanceAndColor();
        }
    }

    public ColorStateList getCounterTextColor() {
        return this.counterTextColor;
    }

    public void setCounterOverflowTextAppearance(int counterOverflowTextAppearance2) {
        if (this.counterOverflowTextAppearance != counterOverflowTextAppearance2) {
            this.counterOverflowTextAppearance = counterOverflowTextAppearance2;
            updateCounterTextAppearanceAndColor();
        }
    }

    public void setCounterOverflowTextColor(ColorStateList counterOverflowTextColor2) {
        if (this.counterOverflowTextColor != counterOverflowTextColor2) {
            this.counterOverflowTextColor = counterOverflowTextColor2;
            updateCounterTextAppearanceAndColor();
        }
    }

    public ColorStateList getCounterOverflowTextColor() {
        return this.counterTextColor;
    }

    public boolean isCounterEnabled() {
        return this.counterEnabled;
    }

    public void setCounterMaxLength(int maxLength) {
        if (this.counterMaxLength != maxLength) {
            if (maxLength > 0) {
                this.counterMaxLength = maxLength;
            } else {
                this.counterMaxLength = -1;
            }
            if (this.counterEnabled) {
                updateCounter();
            }
        }
    }

    private void updateCounter() {
        if (this.counterView != null) {
            EditText editText2 = this.editText;
            updateCounter(editText2 == null ? 0 : editText2.getText().length());
        }
    }

    /* access modifiers changed from: package-private */
    public void updateCounter(int length) {
        boolean wasCounterOverflowed = this.counterOverflowed;
        int i = this.counterMaxLength;
        if (i == -1) {
            this.counterView.setText(String.valueOf(length));
            this.counterView.setContentDescription((CharSequence) null);
            this.counterOverflowed = false;
        } else {
            this.counterOverflowed = length > i;
            updateCounterContentDescription(getContext(), this.counterView, length, this.counterMaxLength, this.counterOverflowed);
            if (wasCounterOverflowed != this.counterOverflowed) {
                updateCounterTextAppearanceAndColor();
            }
            this.counterView.setText(BidiFormatter.getInstance().unicodeWrap(getContext().getString(C0822R.string.character_counter_pattern, new Object[]{Integer.valueOf(length), Integer.valueOf(this.counterMaxLength)})));
        }
        if (this.editText != null && wasCounterOverflowed != this.counterOverflowed) {
            updateLabelState(false);
            updateTextInputBoxState();
            updateEditTextBackground();
        }
    }

    private static void updateCounterContentDescription(Context context, TextView counterView2, int length, int counterMaxLength2, boolean counterOverflowed2) {
        counterView2.setContentDescription(context.getString(counterOverflowed2 ? C0822R.string.character_counter_overflowed_content_description : C0822R.string.character_counter_content_description, new Object[]{Integer.valueOf(length), Integer.valueOf(counterMaxLength2)}));
    }

    public void setPlaceholderText(CharSequence placeholderText2) {
        if (!this.placeholderEnabled || !TextUtils.isEmpty(placeholderText2)) {
            if (!this.placeholderEnabled) {
                setPlaceholderTextEnabled(true);
            }
            this.placeholderText = placeholderText2;
        } else {
            setPlaceholderTextEnabled(false);
        }
        updatePlaceholderText();
    }

    public CharSequence getPlaceholderText() {
        if (this.placeholderEnabled) {
            return this.placeholderText;
        }
        return null;
    }

    private void setPlaceholderTextEnabled(boolean placeholderEnabled2) {
        if (this.placeholderEnabled != placeholderEnabled2) {
            if (placeholderEnabled2) {
                AppCompatTextView appCompatTextView = new AppCompatTextView(getContext());
                this.placeholderTextView = appCompatTextView;
                appCompatTextView.setId(C0822R.C0825id.textinput_placeholder);
                ViewCompat.setAccessibilityLiveRegion(this.placeholderTextView, 1);
                setPlaceholderTextAppearance(this.placeholderTextAppearance);
                setPlaceholderTextColor(this.placeholderTextColor);
                addPlaceholderTextView();
            } else {
                removePlaceholderTextView();
                this.placeholderTextView = null;
            }
            this.placeholderEnabled = placeholderEnabled2;
        }
    }

    private void updatePlaceholderText() {
        EditText editText2 = this.editText;
        updatePlaceholderText(editText2 == null ? 0 : editText2.getText().length());
    }

    /* access modifiers changed from: private */
    public void updatePlaceholderText(int inputTextLength) {
        if (inputTextLength != 0 || this.hintExpanded) {
            hidePlaceholderText();
        } else {
            showPlaceholderText();
        }
    }

    private void showPlaceholderText() {
        TextView textView = this.placeholderTextView;
        if (textView != null && this.placeholderEnabled) {
            textView.setText(this.placeholderText);
            this.placeholderTextView.setVisibility(0);
            this.placeholderTextView.bringToFront();
        }
    }

    private void hidePlaceholderText() {
        TextView textView = this.placeholderTextView;
        if (textView != null && this.placeholderEnabled) {
            textView.setText((CharSequence) null);
            this.placeholderTextView.setVisibility(4);
        }
    }

    private void addPlaceholderTextView() {
        TextView textView = this.placeholderTextView;
        if (textView != null) {
            this.inputFrame.addView(textView);
            this.placeholderTextView.setVisibility(0);
        }
    }

    private void removePlaceholderTextView() {
        TextView textView = this.placeholderTextView;
        if (textView != null) {
            textView.setVisibility(8);
        }
    }

    public void setPlaceholderTextColor(ColorStateList placeholderTextColor2) {
        if (this.placeholderTextColor != placeholderTextColor2) {
            this.placeholderTextColor = placeholderTextColor2;
            TextView textView = this.placeholderTextView;
            if (textView != null && placeholderTextColor2 != null) {
                textView.setTextColor(placeholderTextColor2);
            }
        }
    }

    public ColorStateList getPlaceholderTextColor() {
        return this.placeholderTextColor;
    }

    public void setPlaceholderTextAppearance(int placeholderTextAppearance2) {
        this.placeholderTextAppearance = placeholderTextAppearance2;
        TextView textView = this.placeholderTextView;
        if (textView != null) {
            TextViewCompat.setTextAppearance(textView, placeholderTextAppearance2);
        }
    }

    public int getPlaceholderTextAppearance() {
        return this.placeholderTextAppearance;
    }

    public void setPrefixText(CharSequence prefixText2) {
        this.prefixText = TextUtils.isEmpty(prefixText2) ? null : prefixText2;
        this.prefixTextView.setText(prefixText2);
        updatePrefixTextVisibility();
    }

    public CharSequence getPrefixText() {
        return this.prefixText;
    }

    public TextView getPrefixTextView() {
        return this.prefixTextView;
    }

    private void updatePrefixTextVisibility() {
        this.prefixTextView.setVisibility((this.prefixText == null || isHintExpanded()) ? 8 : 0);
        updateDummyDrawables();
    }

    public void setPrefixTextColor(ColorStateList prefixTextColor) {
        this.prefixTextView.setTextColor(prefixTextColor);
    }

    public ColorStateList getPrefixTextColor() {
        return this.prefixTextView.getTextColors();
    }

    public void setPrefixTextAppearance(int prefixTextAppearance) {
        TextViewCompat.setTextAppearance(this.prefixTextView, prefixTextAppearance);
    }

    private void updatePrefixTextViewPadding() {
        if (this.editText != null) {
            ViewCompat.setPaddingRelative(this.prefixTextView, isStartIconVisible() ? 0 : ViewCompat.getPaddingStart(this.editText), this.editText.getCompoundPaddingTop(), 0, this.editText.getCompoundPaddingBottom());
        }
    }

    public void setSuffixText(CharSequence suffixText2) {
        this.suffixText = TextUtils.isEmpty(suffixText2) ? null : suffixText2;
        this.suffixTextView.setText(suffixText2);
        updateSuffixTextVisibility();
    }

    public CharSequence getSuffixText() {
        return this.suffixText;
    }

    public TextView getSuffixTextView() {
        return this.suffixTextView;
    }

    private void updateSuffixTextVisibility() {
        int oldSuffixVisibility = this.suffixTextView.getVisibility();
        int i = 0;
        boolean visible = this.suffixText != null && !isHintExpanded();
        TextView textView = this.suffixTextView;
        if (!visible) {
            i = 8;
        }
        textView.setVisibility(i);
        if (oldSuffixVisibility != this.suffixTextView.getVisibility()) {
            getEndIconDelegate().onSuffixVisibilityChanged(visible);
        }
        updateDummyDrawables();
    }

    public void setSuffixTextColor(ColorStateList suffixTextColor) {
        this.suffixTextView.setTextColor(suffixTextColor);
    }

    public ColorStateList getSuffixTextColor() {
        return this.suffixTextView.getTextColors();
    }

    public void setSuffixTextAppearance(int suffixTextAppearance) {
        TextViewCompat.setTextAppearance(this.suffixTextView, suffixTextAppearance);
    }

    private void updateSuffixTextViewPadding() {
        if (this.editText != null) {
            ViewCompat.setPaddingRelative(this.suffixTextView, 0, this.editText.getPaddingTop(), (isEndIconVisible() || isErrorIconVisible()) ? 0 : ViewCompat.getPaddingEnd(this.editText), this.editText.getPaddingBottom());
        }
    }

    public void setEnabled(boolean enabled) {
        recursiveSetEnabled(this, enabled);
        super.setEnabled(enabled);
    }

    private static void recursiveSetEnabled(ViewGroup vg, boolean enabled) {
        int count = vg.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = vg.getChildAt(i);
            child.setEnabled(enabled);
            if (child instanceof ViewGroup) {
                recursiveSetEnabled((ViewGroup) child, enabled);
            }
        }
    }

    public int getCounterMaxLength() {
        return this.counterMaxLength;
    }

    /* access modifiers changed from: package-private */
    public CharSequence getCounterOverflowDescription() {
        TextView textView;
        if (!this.counterEnabled || !this.counterOverflowed || (textView = this.counterView) == null) {
            return null;
        }
        return textView.getContentDescription();
    }

    private void updateCounterTextAppearanceAndColor() {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        TextView textView = this.counterView;
        if (textView != null) {
            setTextAppearanceCompatWithErrorFallback(textView, this.counterOverflowed ? this.counterOverflowTextAppearance : this.counterTextAppearance);
            if (!this.counterOverflowed && (colorStateList2 = this.counterTextColor) != null) {
                this.counterView.setTextColor(colorStateList2);
            }
            if (this.counterOverflowed && (colorStateList = this.counterOverflowTextColor) != null) {
                this.counterView.setTextColor(colorStateList);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setTextAppearanceCompatWithErrorFallback(TextView textView, int textAppearance) {
        boolean useDefaultColor = false;
        try {
            TextViewCompat.setTextAppearance(textView, textAppearance);
            if (Build.VERSION.SDK_INT >= 23 && textView.getTextColors().getDefaultColor() == -65281) {
                useDefaultColor = true;
            }
        } catch (Exception e) {
            useDefaultColor = true;
        }
        if (useDefaultColor) {
            TextViewCompat.setTextAppearance(textView, C0822R.style.TextAppearance_AppCompat_Caption);
            textView.setTextColor(ContextCompat.getColor(getContext(), C0822R.C0823color.design_error));
        }
    }

    private int calculateLabelMarginTop() {
        if (!this.hintEnabled) {
            return 0;
        }
        int i = this.boxBackgroundMode;
        if (i == 0 || i == 1) {
            return (int) this.collapsingTextHelper.getCollapsedTextHeight();
        }
        if (i != 2) {
            return 0;
        }
        return (int) (this.collapsingTextHelper.getCollapsedTextHeight() / 2.0f);
    }

    private Rect calculateCollapsedTextBounds(Rect rect) {
        if (this.editText != null) {
            Rect bounds = this.tmpBoundsRect;
            boolean isRtl = ViewCompat.getLayoutDirection(this) == 1;
            bounds.bottom = rect.bottom;
            int i = this.boxBackgroundMode;
            if (i == 1) {
                bounds.left = getLabelLeftBoundAlightWithPrefix(rect.left, isRtl);
                bounds.top = rect.top + this.boxCollapsedPaddingTopPx;
                bounds.right = getLabelRightBoundAlignedWithSuffix(rect.right, isRtl);
                return bounds;
            } else if (i != 2) {
                bounds.left = getLabelLeftBoundAlightWithPrefix(rect.left, isRtl);
                bounds.top = getPaddingTop();
                bounds.right = getLabelRightBoundAlignedWithSuffix(rect.right, isRtl);
                return bounds;
            } else {
                bounds.left = rect.left + this.editText.getPaddingLeft();
                bounds.top = rect.top - calculateLabelMarginTop();
                bounds.right = rect.right - this.editText.getPaddingRight();
                return bounds;
            }
        } else {
            throw new IllegalStateException();
        }
    }

    private int getLabelLeftBoundAlightWithPrefix(int rectLeft, boolean isRtl) {
        int left = this.editText.getCompoundPaddingLeft() + rectLeft;
        if (this.prefixText == null || isRtl) {
            return left;
        }
        return (left - this.prefixTextView.getMeasuredWidth()) + this.prefixTextView.getPaddingLeft();
    }

    private int getLabelRightBoundAlignedWithSuffix(int rectRight, boolean isRtl) {
        int right = rectRight - this.editText.getCompoundPaddingRight();
        if (this.prefixText == null || !isRtl) {
            return right;
        }
        return right + (this.prefixTextView.getMeasuredWidth() - this.prefixTextView.getPaddingRight());
    }

    private Rect calculateExpandedTextBounds(Rect rect) {
        if (this.editText != null) {
            Rect bounds = this.tmpBoundsRect;
            float labelHeight = this.collapsingTextHelper.getExpandedTextHeight();
            bounds.left = rect.left + this.editText.getCompoundPaddingLeft();
            bounds.top = calculateExpandedLabelTop(rect, labelHeight);
            bounds.right = rect.right - this.editText.getCompoundPaddingRight();
            bounds.bottom = calculateExpandedLabelBottom(rect, bounds, labelHeight);
            return bounds;
        }
        throw new IllegalStateException();
    }

    private int calculateExpandedLabelTop(Rect rect, float labelHeight) {
        if (isSingleLineFilledTextField()) {
            return (int) (((float) rect.centerY()) - (labelHeight / 2.0f));
        }
        return rect.top + this.editText.getCompoundPaddingTop();
    }

    private int calculateExpandedLabelBottom(Rect rect, Rect bounds, float labelHeight) {
        if (isSingleLineFilledTextField()) {
            return (int) (((float) bounds.top) + labelHeight);
        }
        return rect.bottom - this.editText.getCompoundPaddingBottom();
    }

    private boolean isSingleLineFilledTextField() {
        if (this.boxBackgroundMode != 1 || (Build.VERSION.SDK_INT >= 16 && this.editText.getMinLines() > 1)) {
            return false;
        }
        return true;
    }

    private int calculateBoxBackgroundColor() {
        int backgroundColor = this.boxBackgroundColor;
        if (this.boxBackgroundMode == 1) {
            return MaterialColors.layer(MaterialColors.getColor((View) this, C0822R.attr.colorSurface, 0), this.boxBackgroundColor);
        }
        return backgroundColor;
    }

    private void applyBoxAttributes() {
        MaterialShapeDrawable materialShapeDrawable = this.boxBackground;
        if (materialShapeDrawable != null) {
            materialShapeDrawable.setShapeAppearanceModel(this.shapeAppearanceModel);
            if (canDrawOutlineStroke()) {
                this.boxBackground.setStroke((float) this.boxStrokeWidthPx, this.boxStrokeColor);
            }
            int calculateBoxBackgroundColor = calculateBoxBackgroundColor();
            this.boxBackgroundColor = calculateBoxBackgroundColor;
            this.boxBackground.setFillColor(ColorStateList.valueOf(calculateBoxBackgroundColor));
            if (this.endIconMode == 3) {
                this.editText.getBackground().invalidateSelf();
            }
            applyBoxUnderlineAttributes();
            invalidate();
        }
    }

    private void applyBoxUnderlineAttributes() {
        if (this.boxUnderline != null) {
            if (canDrawStroke()) {
                this.boxUnderline.setFillColor(ColorStateList.valueOf(this.boxStrokeColor));
            }
            invalidate();
        }
    }

    private boolean canDrawOutlineStroke() {
        return this.boxBackgroundMode == 2 && canDrawStroke();
    }

    private boolean canDrawStroke() {
        return this.boxStrokeWidthPx > -1 && this.boxStrokeColor != 0;
    }

    /* access modifiers changed from: package-private */
    public void updateEditTextBackground() {
        Drawable editTextBackground;
        TextView textView;
        EditText editText2 = this.editText;
        if (editText2 != null && this.boxBackgroundMode == 0 && (editTextBackground = editText2.getBackground()) != null) {
            if (DrawableUtils.canSafelyMutateDrawable(editTextBackground)) {
                editTextBackground = editTextBackground.mutate();
            }
            if (this.indicatorViewController.errorShouldBeShown()) {
                editTextBackground.setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(this.indicatorViewController.getErrorViewCurrentTextColor(), PorterDuff.Mode.SRC_IN));
            } else if (!this.counterOverflowed || (textView = this.counterView) == null) {
                DrawableCompat.clearColorFilter(editTextBackground);
                this.editText.refreshDrawableState();
            } else {
                editTextBackground.setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(textView.getCurrentTextColor(), PorterDuff.Mode.SRC_IN));
            }
        }
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
        CharSequence error;
        boolean isEndIconChecked;

        SavedState(Parcelable superState) {
            super(superState);
        }

        SavedState(Parcel source, ClassLoader loader) {
            super(source, loader);
            this.error = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source);
            this.isEndIconChecked = source.readInt() != 1 ? false : true;
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            TextUtils.writeToParcel(this.error, dest, flags);
            dest.writeInt(this.isEndIconChecked ? 1 : 0);
        }

        public String toString() {
            return "TextInputLayout.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " error=" + this.error + "}";
        }
    }

    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        if (this.indicatorViewController.errorShouldBeShown()) {
            ss.error = getError();
        }
        ss.isEndIconChecked = hasEndIcon() && this.endIconView.isChecked();
        return ss;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setError(ss.error);
        if (ss.isEndIconChecked) {
            this.endIconView.post(new Runnable() {
                public void run() {
                    TextInputLayout.this.endIconView.performClick();
                    TextInputLayout.this.endIconView.jumpDrawablesToCurrentState();
                }
            });
        }
        requestLayout();
    }

    /* access modifiers changed from: protected */
    public void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        this.restoringSavedState = true;
        super.dispatchRestoreInstanceState(container);
        this.restoringSavedState = false;
    }

    public CharSequence getError() {
        if (this.indicatorViewController.isErrorEnabled()) {
            return this.indicatorViewController.getErrorText();
        }
        return null;
    }

    public CharSequence getHelperText() {
        if (this.indicatorViewController.isHelperTextEnabled()) {
            return this.indicatorViewController.getHelperText();
        }
        return null;
    }

    public boolean isHintAnimationEnabled() {
        return this.hintAnimationEnabled;
    }

    public void setHintAnimationEnabled(boolean enabled) {
        this.hintAnimationEnabled = enabled;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        boolean updatedHeight = updateEditTextHeightBasedOnIcon();
        boolean updatedIcon = updateDummyDrawables();
        if (updatedHeight || updatedIcon) {
            this.editText.post(new Runnable() {
                public void run() {
                    TextInputLayout.this.editText.requestLayout();
                }
            });
        }
        updatePlaceholderMeasurementsBasedOnEditText();
        updatePrefixTextViewPadding();
        updateSuffixTextViewPadding();
    }

    private boolean updateEditTextHeightBasedOnIcon() {
        int maxIconHeight;
        if (this.editText == null || this.editText.getMeasuredHeight() >= (maxIconHeight = Math.max(this.endLayout.getMeasuredHeight(), this.startLayout.getMeasuredHeight()))) {
            return false;
        }
        this.editText.setMinimumHeight(maxIconHeight);
        return true;
    }

    private void updatePlaceholderMeasurementsBasedOnEditText() {
        EditText editText2;
        if (this.placeholderTextView != null && (editText2 = this.editText) != null) {
            this.placeholderTextView.setGravity(editText2.getGravity());
            this.placeholderTextView.setPadding(this.editText.getCompoundPaddingLeft(), this.editText.getCompoundPaddingTop(), this.editText.getCompoundPaddingRight(), this.editText.getCompoundPaddingBottom());
        }
    }

    public void setStartIconDrawable(int resId) {
        setStartIconDrawable(resId != 0 ? AppCompatResources.getDrawable(getContext(), resId) : null);
    }

    public void setStartIconDrawable(Drawable startIconDrawable) {
        this.startIconView.setImageDrawable(startIconDrawable);
        if (startIconDrawable != null) {
            setStartIconVisible(true);
            refreshStartIconDrawableState();
            return;
        }
        setStartIconVisible(false);
        setStartIconOnClickListener((View.OnClickListener) null);
        setStartIconOnLongClickListener((View.OnLongClickListener) null);
        setStartIconContentDescription((CharSequence) null);
    }

    public Drawable getStartIconDrawable() {
        return this.startIconView.getDrawable();
    }

    public void setStartIconOnClickListener(View.OnClickListener startIconOnClickListener) {
        setIconOnClickListener(this.startIconView, startIconOnClickListener, this.startIconOnLongClickListener);
    }

    public void setStartIconOnLongClickListener(View.OnLongClickListener startIconOnLongClickListener2) {
        this.startIconOnLongClickListener = startIconOnLongClickListener2;
        setIconOnLongClickListener(this.startIconView, startIconOnLongClickListener2);
    }

    public void setStartIconVisible(boolean visible) {
        if (isStartIconVisible() != visible) {
            this.startIconView.setVisibility(visible ? 0 : 8);
            updatePrefixTextViewPadding();
            updateDummyDrawables();
        }
    }

    public boolean isStartIconVisible() {
        return this.startIconView.getVisibility() == 0;
    }

    public void refreshStartIconDrawableState() {
        refreshIconDrawableState(this.startIconView, this.startIconTintList);
    }

    public void setStartIconCheckable(boolean startIconCheckable) {
        this.startIconView.setCheckable(startIconCheckable);
    }

    public boolean isStartIconCheckable() {
        return this.startIconView.isCheckable();
    }

    public void setStartIconContentDescription(int resId) {
        setStartIconContentDescription(resId != 0 ? getResources().getText(resId) : null);
    }

    public void setStartIconContentDescription(CharSequence startIconContentDescription) {
        if (getStartIconContentDescription() != startIconContentDescription) {
            this.startIconView.setContentDescription(startIconContentDescription);
        }
    }

    public CharSequence getStartIconContentDescription() {
        return this.startIconView.getContentDescription();
    }

    public void setStartIconTintList(ColorStateList startIconTintList2) {
        if (this.startIconTintList != startIconTintList2) {
            this.startIconTintList = startIconTintList2;
            this.hasStartIconTintList = true;
            applyStartIconTint();
        }
    }

    public void setStartIconTintMode(PorterDuff.Mode startIconTintMode2) {
        if (this.startIconTintMode != startIconTintMode2) {
            this.startIconTintMode = startIconTintMode2;
            this.hasStartIconTintMode = true;
            applyStartIconTint();
        }
    }

    public void setEndIconMode(int endIconMode2) {
        int previousEndIconMode = this.endIconMode;
        this.endIconMode = endIconMode2;
        dispatchOnEndIconChanged(previousEndIconMode);
        setEndIconVisible(endIconMode2 != 0);
        if (getEndIconDelegate().isBoxBackgroundModeSupported(this.boxBackgroundMode)) {
            getEndIconDelegate().initialize();
            applyEndIconTint();
            return;
        }
        throw new IllegalStateException("The current box background mode " + this.boxBackgroundMode + " is not supported by the end icon mode " + endIconMode2);
    }

    public int getEndIconMode() {
        return this.endIconMode;
    }

    public void setEndIconOnClickListener(View.OnClickListener endIconOnClickListener) {
        setIconOnClickListener(this.endIconView, endIconOnClickListener, this.endIconOnLongClickListener);
    }

    public void setErrorIconOnClickListener(View.OnClickListener errorIconOnClickListener) {
        setIconOnClickListener(this.errorIconView, errorIconOnClickListener, this.errorIconOnLongClickListener);
    }

    public void setEndIconOnLongClickListener(View.OnLongClickListener endIconOnLongClickListener2) {
        this.endIconOnLongClickListener = endIconOnLongClickListener2;
        setIconOnLongClickListener(this.endIconView, endIconOnLongClickListener2);
    }

    public void setErrorIconOnLongClickListener(View.OnLongClickListener errorIconOnLongClickListener2) {
        this.errorIconOnLongClickListener = errorIconOnLongClickListener2;
        setIconOnLongClickListener(this.errorIconView, errorIconOnLongClickListener2);
    }

    public void refreshErrorIconDrawableState() {
        refreshIconDrawableState(this.errorIconView, this.errorIconTintList);
    }

    public void setEndIconVisible(boolean visible) {
        if (isEndIconVisible() != visible) {
            this.endIconView.setVisibility(visible ? 0 : 8);
            updateSuffixTextViewPadding();
            updateDummyDrawables();
        }
    }

    public boolean isEndIconVisible() {
        return this.endIconFrame.getVisibility() == 0 && this.endIconView.getVisibility() == 0;
    }

    public void setEndIconActivated(boolean endIconActivated) {
        this.endIconView.setActivated(endIconActivated);
    }

    public void refreshEndIconDrawableState() {
        refreshIconDrawableState(this.endIconView, this.endIconTintList);
    }

    public void setEndIconCheckable(boolean endIconCheckable) {
        this.endIconView.setCheckable(endIconCheckable);
    }

    public boolean isEndIconCheckable() {
        return this.endIconView.isCheckable();
    }

    public void setEndIconDrawable(int resId) {
        setEndIconDrawable(resId != 0 ? AppCompatResources.getDrawable(getContext(), resId) : null);
    }

    public void setEndIconDrawable(Drawable endIconDrawable) {
        this.endIconView.setImageDrawable(endIconDrawable);
        refreshEndIconDrawableState();
    }

    public Drawable getEndIconDrawable() {
        return this.endIconView.getDrawable();
    }

    public void setEndIconContentDescription(int resId) {
        setEndIconContentDescription(resId != 0 ? getResources().getText(resId) : null);
    }

    public void setEndIconContentDescription(CharSequence endIconContentDescription) {
        if (getEndIconContentDescription() != endIconContentDescription) {
            this.endIconView.setContentDescription(endIconContentDescription);
        }
    }

    public CharSequence getEndIconContentDescription() {
        return this.endIconView.getContentDescription();
    }

    public void setEndIconTintList(ColorStateList endIconTintList2) {
        if (this.endIconTintList != endIconTintList2) {
            this.endIconTintList = endIconTintList2;
            this.hasEndIconTintList = true;
            applyEndIconTint();
        }
    }

    public void setEndIconTintMode(PorterDuff.Mode endIconTintMode2) {
        if (this.endIconTintMode != endIconTintMode2) {
            this.endIconTintMode = endIconTintMode2;
            this.hasEndIconTintMode = true;
            applyEndIconTint();
        }
    }

    public void addOnEndIconChangedListener(OnEndIconChangedListener listener) {
        this.endIconChangedListeners.add(listener);
    }

    public void removeOnEndIconChangedListener(OnEndIconChangedListener listener) {
        this.endIconChangedListeners.remove(listener);
    }

    public void clearOnEndIconChangedListeners() {
        this.endIconChangedListeners.clear();
    }

    public void addOnEditTextAttachedListener(OnEditTextAttachedListener listener) {
        this.editTextAttachedListeners.add(listener);
        if (this.editText != null) {
            listener.onEditTextAttached(this);
        }
    }

    public void removeOnEditTextAttachedListener(OnEditTextAttachedListener listener) {
        this.editTextAttachedListeners.remove(listener);
    }

    public void clearOnEditTextAttachedListeners() {
        this.editTextAttachedListeners.clear();
    }

    @Deprecated
    public void setPasswordVisibilityToggleDrawable(int resId) {
        setPasswordVisibilityToggleDrawable(resId != 0 ? AppCompatResources.getDrawable(getContext(), resId) : null);
    }

    @Deprecated
    public void setPasswordVisibilityToggleDrawable(Drawable icon) {
        this.endIconView.setImageDrawable(icon);
    }

    @Deprecated
    public void setPasswordVisibilityToggleContentDescription(int resId) {
        setPasswordVisibilityToggleContentDescription(resId != 0 ? getResources().getText(resId) : null);
    }

    @Deprecated
    public void setPasswordVisibilityToggleContentDescription(CharSequence description) {
        this.endIconView.setContentDescription(description);
    }

    @Deprecated
    public Drawable getPasswordVisibilityToggleDrawable() {
        return this.endIconView.getDrawable();
    }

    @Deprecated
    public CharSequence getPasswordVisibilityToggleContentDescription() {
        return this.endIconView.getContentDescription();
    }

    @Deprecated
    public boolean isPasswordVisibilityToggleEnabled() {
        return this.endIconMode == 1;
    }

    @Deprecated
    public void setPasswordVisibilityToggleEnabled(boolean enabled) {
        if (enabled && this.endIconMode != 1) {
            setEndIconMode(1);
        } else if (!enabled) {
            setEndIconMode(0);
        }
    }

    @Deprecated
    public void setPasswordVisibilityToggleTintList(ColorStateList tintList) {
        this.endIconTintList = tintList;
        this.hasEndIconTintList = true;
        applyEndIconTint();
    }

    @Deprecated
    public void setPasswordVisibilityToggleTintMode(PorterDuff.Mode mode) {
        this.endIconTintMode = mode;
        this.hasEndIconTintMode = true;
        applyEndIconTint();
    }

    @Deprecated
    public void passwordVisibilityToggleRequested(boolean shouldSkipAnimations) {
        if (this.endIconMode == 1) {
            this.endIconView.performClick();
            if (shouldSkipAnimations) {
                this.endIconView.jumpDrawablesToCurrentState();
            }
        }
    }

    public void setTextInputAccessibilityDelegate(AccessibilityDelegate delegate) {
        EditText editText2 = this.editText;
        if (editText2 != null) {
            ViewCompat.setAccessibilityDelegate(editText2, delegate);
        }
    }

    /* access modifiers changed from: package-private */
    public CheckableImageButton getEndIconView() {
        return this.endIconView;
    }

    private EndIconDelegate getEndIconDelegate() {
        EndIconDelegate endIconDelegate = this.endIconDelegates.get(this.endIconMode);
        return endIconDelegate != null ? endIconDelegate : this.endIconDelegates.get(0);
    }

    private void dispatchOnEditTextAttached() {
        Iterator it = this.editTextAttachedListeners.iterator();
        while (it.hasNext()) {
            ((OnEditTextAttachedListener) it.next()).onEditTextAttached(this);
        }
    }

    private void applyStartIconTint() {
        applyIconTint(this.startIconView, this.hasStartIconTintList, this.startIconTintList, this.hasStartIconTintMode, this.startIconTintMode);
    }

    private boolean hasEndIcon() {
        return this.endIconMode != 0;
    }

    private void dispatchOnEndIconChanged(int previousIcon) {
        Iterator it = this.endIconChangedListeners.iterator();
        while (it.hasNext()) {
            ((OnEndIconChangedListener) it.next()).onEndIconChanged(this, previousIcon);
        }
    }

    private void tintEndIconOnError(boolean tintEndIconOnError) {
        if (!tintEndIconOnError || getEndIconDrawable() == null) {
            applyEndIconTint();
            return;
        }
        Drawable endIconDrawable = DrawableCompat.wrap(getEndIconDrawable()).mutate();
        DrawableCompat.setTint(endIconDrawable, this.indicatorViewController.getErrorViewCurrentTextColor());
        this.endIconView.setImageDrawable(endIconDrawable);
    }

    private void applyEndIconTint() {
        applyIconTint(this.endIconView, this.hasEndIconTintList, this.endIconTintList, this.hasEndIconTintMode, this.endIconTintMode);
    }

    private boolean updateDummyDrawables() {
        if (this.editText == null) {
            return false;
        }
        boolean updatedIcon = false;
        if (shouldUpdateStartDummyDrawable()) {
            int right = this.startLayout.getMeasuredWidth() - this.editText.getPaddingLeft();
            if (this.startDummyDrawable == null || this.startDummyDrawableWidth != right) {
                ColorDrawable colorDrawable = new ColorDrawable();
                this.startDummyDrawable = colorDrawable;
                this.startDummyDrawableWidth = right;
                colorDrawable.setBounds(0, 0, right, 1);
            }
            Drawable[] compounds = TextViewCompat.getCompoundDrawablesRelative(this.editText);
            Drawable drawable = compounds[0];
            Drawable drawable2 = this.startDummyDrawable;
            if (drawable != drawable2) {
                TextViewCompat.setCompoundDrawablesRelative(this.editText, drawable2, compounds[1], compounds[2], compounds[3]);
                updatedIcon = true;
            }
        } else if (this.startDummyDrawable != null) {
            Drawable[] compounds2 = TextViewCompat.getCompoundDrawablesRelative(this.editText);
            TextViewCompat.setCompoundDrawablesRelative(this.editText, (Drawable) null, compounds2[1], compounds2[2], compounds2[3]);
            this.startDummyDrawable = null;
            updatedIcon = true;
        }
        if (shouldUpdateEndDummyDrawable()) {
            int right2 = this.suffixTextView.getMeasuredWidth() - this.editText.getPaddingRight();
            View iconView = getEndIconToUpdateDummyDrawable();
            if (iconView != null) {
                right2 = iconView.getMeasuredWidth() + right2 + MarginLayoutParamsCompat.getMarginStart((ViewGroup.MarginLayoutParams) iconView.getLayoutParams());
            }
            Drawable[] compounds3 = TextViewCompat.getCompoundDrawablesRelative(this.editText);
            Drawable drawable3 = this.endDummyDrawable;
            if (drawable3 == null || this.endDummyDrawableWidth == right2) {
                if (this.endDummyDrawable == null) {
                    ColorDrawable colorDrawable2 = new ColorDrawable();
                    this.endDummyDrawable = colorDrawable2;
                    this.endDummyDrawableWidth = right2;
                    colorDrawable2.setBounds(0, 0, right2, 1);
                }
                Drawable drawable4 = compounds3[2];
                Drawable drawable5 = this.endDummyDrawable;
                if (drawable4 == drawable5) {
                    return updatedIcon;
                }
                this.originalEditTextEndDrawable = compounds3[2];
                TextViewCompat.setCompoundDrawablesRelative(this.editText, compounds3[0], compounds3[1], drawable5, compounds3[3]);
                return true;
            }
            this.endDummyDrawableWidth = right2;
            drawable3.setBounds(0, 0, right2, 1);
            TextViewCompat.setCompoundDrawablesRelative(this.editText, compounds3[0], compounds3[1], this.endDummyDrawable, compounds3[3]);
            return true;
        } else if (this.endDummyDrawable == null) {
            return updatedIcon;
        } else {
            Drawable[] compounds4 = TextViewCompat.getCompoundDrawablesRelative(this.editText);
            if (compounds4[2] == this.endDummyDrawable) {
                TextViewCompat.setCompoundDrawablesRelative(this.editText, compounds4[0], compounds4[1], this.originalEditTextEndDrawable, compounds4[3]);
                updatedIcon = true;
            }
            this.endDummyDrawable = null;
            return updatedIcon;
        }
    }

    private boolean shouldUpdateStartDummyDrawable() {
        return !(getStartIconDrawable() == null && this.prefixText == null) && this.startLayout.getMeasuredWidth() > 0;
    }

    private boolean shouldUpdateEndDummyDrawable() {
        return (this.errorIconView.getVisibility() == 0 || ((hasEndIcon() && isEndIconVisible()) || this.suffixText != null)) && this.endLayout.getMeasuredWidth() > 0;
    }

    private CheckableImageButton getEndIconToUpdateDummyDrawable() {
        if (this.errorIconView.getVisibility() == 0) {
            return this.errorIconView;
        }
        if (!hasEndIcon() || !isEndIconVisible()) {
            return null;
        }
        return this.endIconView;
    }

    private void applyIconTint(CheckableImageButton iconView, boolean hasIconTintList, ColorStateList iconTintList, boolean hasIconTintMode, PorterDuff.Mode iconTintMode) {
        Drawable icon = iconView.getDrawable();
        if (icon != null && (hasIconTintList || hasIconTintMode)) {
            icon = DrawableCompat.wrap(icon).mutate();
            if (hasIconTintList) {
                DrawableCompat.setTintList(icon, iconTintList);
            }
            if (hasIconTintMode) {
                DrawableCompat.setTintMode(icon, iconTintMode);
            }
        }
        if (iconView.getDrawable() != icon) {
            iconView.setImageDrawable(icon);
        }
    }

    private static void setIconOnClickListener(CheckableImageButton iconView, View.OnClickListener onClickListener, View.OnLongClickListener onLongClickListener) {
        iconView.setOnClickListener(onClickListener);
        setIconClickable(iconView, onLongClickListener);
    }

    private static void setIconOnLongClickListener(CheckableImageButton iconView, View.OnLongClickListener onLongClickListener) {
        iconView.setOnLongClickListener(onLongClickListener);
        setIconClickable(iconView, onLongClickListener);
    }

    private static void setIconClickable(CheckableImageButton iconView, View.OnLongClickListener onLongClickListener) {
        boolean iconClickable = ViewCompat.hasOnClickListeners(iconView);
        boolean iconFocusable = false;
        int i = 1;
        boolean iconLongClickable = onLongClickListener != null;
        if (iconClickable || iconLongClickable) {
            iconFocusable = true;
        }
        iconView.setFocusable(iconFocusable);
        iconView.setClickable(iconClickable);
        iconView.setPressable(iconClickable);
        iconView.setLongClickable(iconLongClickable);
        if (!iconFocusable) {
            i = 2;
        }
        ViewCompat.setImportantForAccessibility(iconView, i);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        EditText editText2 = this.editText;
        if (editText2 != null) {
            Rect rect = this.tmpRect;
            DescendantOffsetUtils.getDescendantRect(this, editText2, rect);
            updateBoxUnderlineBounds(rect);
            if (this.hintEnabled) {
                this.collapsingTextHelper.setExpandedTextSize(this.editText.getTextSize());
                int editTextGravity = this.editText.getGravity();
                this.collapsingTextHelper.setCollapsedTextGravity((editTextGravity & -113) | 48);
                this.collapsingTextHelper.setExpandedTextGravity(editTextGravity);
                this.collapsingTextHelper.setCollapsedBounds(calculateCollapsedTextBounds(rect));
                this.collapsingTextHelper.setExpandedBounds(calculateExpandedTextBounds(rect));
                this.collapsingTextHelper.recalculate();
                if (cutoutEnabled() && !this.hintExpanded) {
                    openCutout();
                }
            }
        }
    }

    private void updateBoxUnderlineBounds(Rect bounds) {
        if (this.boxUnderline != null) {
            this.boxUnderline.setBounds(bounds.left, bounds.bottom - this.boxStrokeWidthFocusedPx, bounds.right, bounds.bottom);
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawHint(canvas);
        drawBoxUnderline(canvas);
    }

    private void drawHint(Canvas canvas) {
        if (this.hintEnabled) {
            this.collapsingTextHelper.draw(canvas);
        }
    }

    private void drawBoxUnderline(Canvas canvas) {
        MaterialShapeDrawable materialShapeDrawable = this.boxUnderline;
        if (materialShapeDrawable != null) {
            Rect underlineBounds = materialShapeDrawable.getBounds();
            underlineBounds.top = underlineBounds.bottom - this.boxStrokeWidthPx;
            this.boxUnderline.draw(canvas);
        }
    }

    private void collapseHint(boolean animate) {
        ValueAnimator valueAnimator = this.animator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.animator.cancel();
        }
        if (!animate || !this.hintAnimationEnabled) {
            this.collapsingTextHelper.setExpansionFraction(1.0f);
        } else {
            animateToExpansionFraction(1.0f);
        }
        this.hintExpanded = false;
        if (cutoutEnabled()) {
            openCutout();
        }
        updatePlaceholderText();
        updatePrefixTextVisibility();
        updateSuffixTextVisibility();
    }

    private boolean cutoutEnabled() {
        return this.hintEnabled && !TextUtils.isEmpty(this.hint) && (this.boxBackground instanceof CutoutDrawable);
    }

    private void openCutout() {
        if (cutoutEnabled()) {
            RectF cutoutBounds = this.tmpRectF;
            this.collapsingTextHelper.getCollapsedTextActualBounds(cutoutBounds, this.editText.getWidth(), this.editText.getGravity());
            applyCutoutPadding(cutoutBounds);
            cutoutBounds.offset((float) (-getPaddingLeft()), (float) (-getPaddingTop()));
            ((CutoutDrawable) this.boxBackground).setCutout(cutoutBounds);
        }
    }

    private void closeCutout() {
        if (cutoutEnabled()) {
            ((CutoutDrawable) this.boxBackground).removeCutout();
        }
    }

    private void applyCutoutPadding(RectF cutoutBounds) {
        cutoutBounds.left -= (float) this.boxLabelCutoutPaddingPx;
        cutoutBounds.top -= (float) this.boxLabelCutoutPaddingPx;
        cutoutBounds.right += (float) this.boxLabelCutoutPaddingPx;
        cutoutBounds.bottom += (float) this.boxLabelCutoutPaddingPx;
    }

    /* access modifiers changed from: package-private */
    public boolean cutoutIsOpen() {
        return cutoutEnabled() && ((CutoutDrawable) this.boxBackground).hasCutout();
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        if (!this.inDrawableStateChanged) {
            boolean z = true;
            this.inDrawableStateChanged = true;
            super.drawableStateChanged();
            int[] state = getDrawableState();
            boolean changed = false;
            CollapsingTextHelper collapsingTextHelper2 = this.collapsingTextHelper;
            if (collapsingTextHelper2 != null) {
                changed = false | collapsingTextHelper2.setState(state);
            }
            if (this.editText != null) {
                if (!ViewCompat.isLaidOut(this) || !isEnabled()) {
                    z = false;
                }
                updateLabelState(z);
            }
            updateEditTextBackground();
            updateTextInputBoxState();
            if (changed) {
                invalidate();
            }
            this.inDrawableStateChanged = false;
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0012, code lost:
        r0 = r6.editText;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateTextInputBoxState() {
        /*
            r6 = this;
            com.google.android.material.shape.MaterialShapeDrawable r0 = r6.boxBackground
            if (r0 == 0) goto L_0x00f2
            int r0 = r6.boxBackgroundMode
            if (r0 != 0) goto L_0x000a
            goto L_0x00f2
        L_0x000a:
            boolean r0 = r6.isFocused()
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L_0x001f
            android.widget.EditText r0 = r6.editText
            if (r0 == 0) goto L_0x001d
            boolean r0 = r0.hasFocus()
            if (r0 == 0) goto L_0x001d
            goto L_0x001f
        L_0x001d:
            r0 = 0
            goto L_0x0020
        L_0x001f:
            r0 = 1
        L_0x0020:
            boolean r3 = r6.isHovered()
            if (r3 != 0) goto L_0x0033
            android.widget.EditText r3 = r6.editText
            if (r3 == 0) goto L_0x0031
            boolean r3 = r3.isHovered()
            if (r3 == 0) goto L_0x0031
            goto L_0x0033
        L_0x0031:
            r3 = 0
            goto L_0x0034
        L_0x0033:
            r3 = 1
        L_0x0034:
            boolean r4 = r6.isEnabled()
            if (r4 != 0) goto L_0x003f
            int r4 = r6.disabledColor
            r6.boxStrokeColor = r4
            goto L_0x0081
        L_0x003f:
            com.google.android.material.textfield.IndicatorViewController r4 = r6.indicatorViewController
            boolean r4 = r4.errorShouldBeShown()
            if (r4 == 0) goto L_0x0058
            android.content.res.ColorStateList r4 = r6.strokeErrorColor
            if (r4 == 0) goto L_0x004f
            r6.updateStrokeErrorColor(r0, r3)
            goto L_0x0081
        L_0x004f:
            com.google.android.material.textfield.IndicatorViewController r4 = r6.indicatorViewController
            int r4 = r4.getErrorViewCurrentTextColor()
            r6.boxStrokeColor = r4
            goto L_0x0081
        L_0x0058:
            boolean r4 = r6.counterOverflowed
            if (r4 == 0) goto L_0x006f
            android.widget.TextView r4 = r6.counterView
            if (r4 == 0) goto L_0x006f
            android.content.res.ColorStateList r5 = r6.strokeErrorColor
            if (r5 == 0) goto L_0x0068
            r6.updateStrokeErrorColor(r0, r3)
            goto L_0x0081
        L_0x0068:
            int r4 = r4.getCurrentTextColor()
            r6.boxStrokeColor = r4
            goto L_0x0081
        L_0x006f:
            if (r0 == 0) goto L_0x0076
            int r4 = r6.focusedStrokeColor
            r6.boxStrokeColor = r4
            goto L_0x0081
        L_0x0076:
            if (r3 == 0) goto L_0x007d
            int r4 = r6.hoveredStrokeColor
            r6.boxStrokeColor = r4
            goto L_0x0081
        L_0x007d:
            int r4 = r6.defaultStrokeColor
            r6.boxStrokeColor = r4
        L_0x0081:
            android.graphics.drawable.Drawable r4 = r6.getErrorIconDrawable()
            if (r4 == 0) goto L_0x009a
            com.google.android.material.textfield.IndicatorViewController r4 = r6.indicatorViewController
            boolean r4 = r4.isErrorEnabled()
            if (r4 == 0) goto L_0x009a
            com.google.android.material.textfield.IndicatorViewController r4 = r6.indicatorViewController
            boolean r4 = r4.errorShouldBeShown()
            if (r4 == 0) goto L_0x009a
            r1 = 1
            goto L_0x009b
        L_0x009a:
        L_0x009b:
            r6.setErrorIconVisible(r1)
            r6.refreshErrorIconDrawableState()
            r6.refreshStartIconDrawableState()
            r6.refreshEndIconDrawableState()
            com.google.android.material.textfield.EndIconDelegate r1 = r6.getEndIconDelegate()
            boolean r1 = r1.shouldTintIconOnError()
            if (r1 == 0) goto L_0x00ba
            com.google.android.material.textfield.IndicatorViewController r1 = r6.indicatorViewController
            boolean r1 = r1.errorShouldBeShown()
            r6.tintEndIconOnError(r1)
        L_0x00ba:
            if (r0 == 0) goto L_0x00c7
            boolean r1 = r6.isEnabled()
            if (r1 == 0) goto L_0x00c7
            int r1 = r6.boxStrokeWidthFocusedPx
            r6.boxStrokeWidthPx = r1
            goto L_0x00cb
        L_0x00c7:
            int r1 = r6.boxStrokeWidthDefaultPx
            r6.boxStrokeWidthPx = r1
        L_0x00cb:
            int r1 = r6.boxBackgroundMode
            if (r1 != r2) goto L_0x00ee
            boolean r1 = r6.isEnabled()
            if (r1 != 0) goto L_0x00da
            int r1 = r6.disabledFilledBackgroundColor
            r6.boxBackgroundColor = r1
            goto L_0x00ee
        L_0x00da:
            if (r3 == 0) goto L_0x00e3
            if (r0 != 0) goto L_0x00e3
            int r1 = r6.hoveredFilledBackgroundColor
            r6.boxBackgroundColor = r1
            goto L_0x00ee
        L_0x00e3:
            if (r0 == 0) goto L_0x00ea
            int r1 = r6.focusedFilledBackgroundColor
            r6.boxBackgroundColor = r1
            goto L_0x00ee
        L_0x00ea:
            int r1 = r6.defaultFilledBackgroundColor
            r6.boxBackgroundColor = r1
        L_0x00ee:
            r6.applyBoxAttributes()
            return
        L_0x00f2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.textfield.TextInputLayout.updateTextInputBoxState():void");
    }

    private void updateStrokeErrorColor(boolean hasFocus, boolean isHovered) {
        int defaultStrokeErrorColor = this.strokeErrorColor.getDefaultColor();
        int hoveredStrokeErrorColor = this.strokeErrorColor.getColorForState(new int[]{16843623, 16842910}, defaultStrokeErrorColor);
        int focusedStrokeErrorColor = this.strokeErrorColor.getColorForState(new int[]{16843518, 16842910}, defaultStrokeErrorColor);
        if (hasFocus) {
            this.boxStrokeColor = focusedStrokeErrorColor;
        } else if (isHovered) {
            this.boxStrokeColor = hoveredStrokeErrorColor;
        } else {
            this.boxStrokeColor = defaultStrokeErrorColor;
        }
    }

    private void setErrorIconVisible(boolean errorIconVisible) {
        int i = 0;
        this.errorIconView.setVisibility(errorIconVisible ? 0 : 8);
        FrameLayout frameLayout = this.endIconFrame;
        if (errorIconVisible) {
            i = 8;
        }
        frameLayout.setVisibility(i);
        updateSuffixTextViewPadding();
        if (!hasEndIcon()) {
            updateDummyDrawables();
        }
    }

    private boolean isErrorIconVisible() {
        return this.errorIconView.getVisibility() == 0;
    }

    private void refreshIconDrawableState(CheckableImageButton iconView, ColorStateList colorStateList) {
        Drawable icon = iconView.getDrawable();
        if (iconView.getDrawable() != null && colorStateList != null && colorStateList.isStateful()) {
            int color = colorStateList.getColorForState(mergeIconState(iconView), colorStateList.getDefaultColor());
            Drawable icon2 = DrawableCompat.wrap(icon).mutate();
            DrawableCompat.setTintList(icon2, ColorStateList.valueOf(color));
            iconView.setImageDrawable(icon2);
        }
    }

    private int[] mergeIconState(CheckableImageButton iconView) {
        int[] textInputStates = getDrawableState();
        int[] iconStates = iconView.getDrawableState();
        int index = textInputStates.length;
        int[] states = Arrays.copyOf(textInputStates, textInputStates.length + iconStates.length);
        System.arraycopy(iconStates, 0, states, index, iconStates.length);
        return states;
    }

    private void expandHint(boolean animate) {
        ValueAnimator valueAnimator = this.animator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.animator.cancel();
        }
        if (!animate || !this.hintAnimationEnabled) {
            this.collapsingTextHelper.setExpansionFraction(0.0f);
        } else {
            animateToExpansionFraction(0.0f);
        }
        if (cutoutEnabled() && ((CutoutDrawable) this.boxBackground).hasCutout()) {
            closeCutout();
        }
        this.hintExpanded = true;
        hidePlaceholderText();
        updatePrefixTextVisibility();
        updateSuffixTextVisibility();
    }

    /* access modifiers changed from: package-private */
    public void animateToExpansionFraction(float target) {
        if (this.collapsingTextHelper.getExpansionFraction() != target) {
            if (this.animator == null) {
                ValueAnimator valueAnimator = new ValueAnimator();
                this.animator = valueAnimator;
                valueAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
                this.animator.setDuration(167);
                this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animator) {
                        TextInputLayout.this.collapsingTextHelper.setExpansionFraction(((Float) animator.getAnimatedValue()).floatValue());
                    }
                });
            }
            this.animator.setFloatValues(new float[]{this.collapsingTextHelper.getExpansionFraction(), target});
            this.animator.start();
        }
    }

    /* access modifiers changed from: package-private */
    public final boolean isHintExpanded() {
        return this.hintExpanded;
    }

    /* access modifiers changed from: package-private */
    public final boolean isHelperTextDisplayed() {
        return this.indicatorViewController.helperTextIsDisplayed();
    }

    /* access modifiers changed from: package-private */
    public final int getHintCurrentCollapsedTextColor() {
        return this.collapsingTextHelper.getCurrentCollapsedTextColor();
    }

    /* access modifiers changed from: package-private */
    public final float getHintCollapsedTextHeight() {
        return this.collapsingTextHelper.getCollapsedTextHeight();
    }

    /* access modifiers changed from: package-private */
    public final int getErrorTextCurrentColor() {
        return this.indicatorViewController.getErrorViewCurrentTextColor();
    }

    public static class AccessibilityDelegate extends AccessibilityDelegateCompat {
        private final TextInputLayout layout;

        public AccessibilityDelegate(TextInputLayout layout2) {
            this.layout = layout2;
        }

        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
            String text;
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = info;
            super.onInitializeAccessibilityNodeInfo(host, info);
            EditText editText = this.layout.getEditText();
            CharSequence inputText = editText != null ? editText.getText() : null;
            CharSequence hintText = this.layout.getHint();
            CharSequence errorText = this.layout.getError();
            int maxCharLimit = this.layout.getCounterMaxLength();
            CharSequence counterOverflowDesc = this.layout.getCounterOverflowDescription();
            boolean showingText = !TextUtils.isEmpty(inputText);
            boolean hasHint = !TextUtils.isEmpty(hintText);
            boolean showingError = !TextUtils.isEmpty(errorText);
            boolean contentInvalid = showingError || !TextUtils.isEmpty(counterOverflowDesc);
            String hint = hasHint ? hintText.toString() : "";
            if (showingText) {
                accessibilityNodeInfoCompat.setText(inputText);
            } else if (!TextUtils.isEmpty(hint)) {
                accessibilityNodeInfoCompat.setText(hint);
            }
            if (!TextUtils.isEmpty(hint)) {
                if (Build.VERSION.SDK_INT >= 26) {
                    accessibilityNodeInfoCompat.setHintText(hint);
                } else {
                    if (showingText) {
                        text = inputText + ", " + hint;
                    } else {
                        text = hint;
                    }
                    accessibilityNodeInfoCompat.setText(text);
                }
                accessibilityNodeInfoCompat.setShowingHintText(!showingText);
            }
            accessibilityNodeInfoCompat.setMaxTextLength((inputText == null || inputText.length() != maxCharLimit) ? -1 : maxCharLimit);
            if (contentInvalid) {
                accessibilityNodeInfoCompat.setError(showingError ? errorText : counterOverflowDesc);
            }
            if (Build.VERSION.SDK_INT >= 17 && editText != null) {
                editText.setLabelFor(C0822R.C0825id.textinput_helper_text);
            }
        }
    }
}
