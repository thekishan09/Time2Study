package com.google.android.material.switchmaterial;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.appcompat.widget.SwitchCompat;
import com.google.android.material.C0822R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.elevation.ElevationOverlayProvider;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class SwitchMaterial extends SwitchCompat {
    private static final int DEF_STYLE_RES = C0822R.style.Widget_MaterialComponents_CompoundButton_Switch;
    private static final int[][] ENABLED_CHECKED_STATES = {new int[]{16842910, 16842912}, new int[]{16842910, -16842912}, new int[]{-16842910, 16842912}, new int[]{-16842910, -16842912}};
    private final ElevationOverlayProvider elevationOverlayProvider;
    private ColorStateList materialThemeColorsThumbTintList;
    private ColorStateList materialThemeColorsTrackTintList;
    private boolean useMaterialThemeColors;

    public SwitchMaterial(Context context) {
        this(context, (AttributeSet) null);
    }

    public SwitchMaterial(Context context, AttributeSet attrs) {
        this(context, attrs, C0822R.attr.switchStyle);
    }

    public SwitchMaterial(Context context, AttributeSet attrs, int defStyleAttr) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, DEF_STYLE_RES), attrs, defStyleAttr);
        Context context2 = getContext();
        this.elevationOverlayProvider = new ElevationOverlayProvider(context2);
        TypedArray attributes = ThemeEnforcement.obtainStyledAttributes(context2, attrs, C0822R.styleable.SwitchMaterial, defStyleAttr, DEF_STYLE_RES, new int[0]);
        this.useMaterialThemeColors = attributes.getBoolean(C0822R.styleable.SwitchMaterial_useMaterialThemeColors, false);
        attributes.recycle();
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.useMaterialThemeColors && getThumbTintList() == null) {
            setThumbTintList(getMaterialThemeColorsThumbTintList());
        }
        if (this.useMaterialThemeColors && getTrackTintList() == null) {
            setTrackTintList(getMaterialThemeColorsTrackTintList());
        }
    }

    public void setUseMaterialThemeColors(boolean useMaterialThemeColors2) {
        this.useMaterialThemeColors = useMaterialThemeColors2;
        if (useMaterialThemeColors2) {
            setThumbTintList(getMaterialThemeColorsThumbTintList());
            setTrackTintList(getMaterialThemeColorsTrackTintList());
            return;
        }
        setThumbTintList((ColorStateList) null);
        setTrackTintList((ColorStateList) null);
    }

    public boolean isUseMaterialThemeColors() {
        return this.useMaterialThemeColors;
    }

    private ColorStateList getMaterialThemeColorsThumbTintList() {
        if (this.materialThemeColorsThumbTintList == null) {
            int colorSurface = MaterialColors.getColor(this, C0822R.attr.colorSurface);
            int colorControlActivated = MaterialColors.getColor(this, C0822R.attr.colorControlActivated);
            float thumbElevation = getResources().getDimension(C0822R.dimen.mtrl_switch_thumb_elevation);
            if (this.elevationOverlayProvider.isThemeElevationOverlayEnabled()) {
                thumbElevation += ViewUtils.getParentAbsoluteElevation(this);
            }
            int colorThumbOff = this.elevationOverlayProvider.compositeOverlayIfNeeded(colorSurface, thumbElevation);
            int[] switchThumbColorsList = new int[ENABLED_CHECKED_STATES.length];
            switchThumbColorsList[0] = MaterialColors.layer(colorSurface, colorControlActivated, 1.0f);
            switchThumbColorsList[1] = colorThumbOff;
            switchThumbColorsList[2] = MaterialColors.layer(colorSurface, colorControlActivated, 0.38f);
            switchThumbColorsList[3] = colorThumbOff;
            this.materialThemeColorsThumbTintList = new ColorStateList(ENABLED_CHECKED_STATES, switchThumbColorsList);
        }
        return this.materialThemeColorsThumbTintList;
    }

    private ColorStateList getMaterialThemeColorsTrackTintList() {
        if (this.materialThemeColorsTrackTintList == null) {
            int[] switchTrackColorsList = new int[ENABLED_CHECKED_STATES.length];
            int colorSurface = MaterialColors.getColor(this, C0822R.attr.colorSurface);
            int colorControlActivated = MaterialColors.getColor(this, C0822R.attr.colorControlActivated);
            int colorOnSurface = MaterialColors.getColor(this, C0822R.attr.colorOnSurface);
            switchTrackColorsList[0] = MaterialColors.layer(colorSurface, colorControlActivated, 0.54f);
            switchTrackColorsList[1] = MaterialColors.layer(colorSurface, colorOnSurface, 0.32f);
            switchTrackColorsList[2] = MaterialColors.layer(colorSurface, colorControlActivated, 0.12f);
            switchTrackColorsList[3] = MaterialColors.layer(colorSurface, colorOnSurface, 0.12f);
            this.materialThemeColorsTrackTintList = new ColorStateList(ENABLED_CHECKED_STATES, switchTrackColorsList);
        }
        return this.materialThemeColorsTrackTintList;
    }
}
