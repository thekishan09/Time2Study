package com.google.android.material.checkbox;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.widget.CompoundButtonCompat;
import com.google.android.material.C0822R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class MaterialCheckBox extends AppCompatCheckBox {
    private static final int DEF_STYLE_RES = C0822R.style.Widget_MaterialComponents_CompoundButton_CheckBox;
    private static final int[][] ENABLED_CHECKED_STATES = {new int[]{16842910, 16842912}, new int[]{16842910, -16842912}, new int[]{-16842910, 16842912}, new int[]{-16842910, -16842912}};
    private ColorStateList materialThemeColorsTintList;
    private boolean useMaterialThemeColors;

    public MaterialCheckBox(Context context) {
        this(context, (AttributeSet) null);
    }

    public MaterialCheckBox(Context context, AttributeSet attrs) {
        this(context, attrs, C0822R.attr.checkboxStyle);
    }

    public MaterialCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, DEF_STYLE_RES), attrs, defStyleAttr);
        Context context2 = getContext();
        TypedArray attributes = ThemeEnforcement.obtainStyledAttributes(context2, attrs, C0822R.styleable.MaterialCheckBox, defStyleAttr, DEF_STYLE_RES, new int[0]);
        if (attributes.hasValue(C0822R.styleable.MaterialCheckBox_buttonTint)) {
            CompoundButtonCompat.setButtonTintList(this, MaterialResources.getColorStateList(context2, attributes, C0822R.styleable.MaterialCheckBox_buttonTint));
        }
        this.useMaterialThemeColors = attributes.getBoolean(C0822R.styleable.MaterialCheckBox_useMaterialThemeColors, false);
        attributes.recycle();
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.useMaterialThemeColors && CompoundButtonCompat.getButtonTintList(this) == null) {
            setUseMaterialThemeColors(true);
        }
    }

    public void setUseMaterialThemeColors(boolean useMaterialThemeColors2) {
        this.useMaterialThemeColors = useMaterialThemeColors2;
        if (useMaterialThemeColors2) {
            CompoundButtonCompat.setButtonTintList(this, getMaterialThemeColorsTintList());
        } else {
            CompoundButtonCompat.setButtonTintList(this, (ColorStateList) null);
        }
    }

    public boolean isUseMaterialThemeColors() {
        return this.useMaterialThemeColors;
    }

    private ColorStateList getMaterialThemeColorsTintList() {
        if (this.materialThemeColorsTintList == null) {
            int[] checkBoxColorsList = new int[ENABLED_CHECKED_STATES.length];
            int colorControlActivated = MaterialColors.getColor(this, C0822R.attr.colorControlActivated);
            int colorSurface = MaterialColors.getColor(this, C0822R.attr.colorSurface);
            int colorOnSurface = MaterialColors.getColor(this, C0822R.attr.colorOnSurface);
            checkBoxColorsList[0] = MaterialColors.layer(colorSurface, colorControlActivated, 1.0f);
            checkBoxColorsList[1] = MaterialColors.layer(colorSurface, colorOnSurface, 0.54f);
            checkBoxColorsList[2] = MaterialColors.layer(colorSurface, colorOnSurface, 0.38f);
            checkBoxColorsList[3] = MaterialColors.layer(colorSurface, colorOnSurface, 0.38f);
            this.materialThemeColorsTintList = new ColorStateList(ENABLED_CHECKED_STATES, checkBoxColorsList);
        }
        return this.materialThemeColorsTintList;
    }
}
