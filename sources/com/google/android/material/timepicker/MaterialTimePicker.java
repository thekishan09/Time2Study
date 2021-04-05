package com.google.android.material.timepicker;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.C0822R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.shape.MaterialShapeDrawable;

public final class MaterialTimePicker extends DialogFragment {
    private static final int CLOCK_ICON = C0822R.C0824drawable.ic_clock_black_24dp;
    public static final int INPUT_MODE_CLOCK = 0;
    static final String INPUT_MODE_EXTRA = "TIME_PICKER_INPUT_MODE";
    public static final int INPUT_MODE_KEYBOARD = 1;
    private static final int KEYBOARD_ICON = C0822R.C0824drawable.ic_keyboard_black_24dp;
    static final String TIME_MODEL_EXTRA = "TIME_PICKER_TIME_MODEL";
    private TimePickerPresenter activePresenter;
    /* access modifiers changed from: private */
    public int inputMode = 0;
    /* access modifiers changed from: private */
    public OnTimeSetListener listener;
    /* access modifiers changed from: private */
    public MaterialButton modeButton;
    private LinearLayout textInputView;
    private TimeModel time = new TimeModel();
    private TimePickerClockPresenter timePickerClockPresenter;
    private TimePickerTextInputPresenter timePickerTextInputPresenter;
    private TimePickerView timePickerView;

    public interface OnTimeSetListener {
        void onTimeSet(MaterialTimePicker materialTimePicker);
    }

    public static MaterialTimePicker newInstance() {
        return new MaterialTimePicker();
    }

    public void setHour(int hour) {
        this.time.setHourOfDay(hour);
    }

    public void setMinute(int minute) {
        this.time.setMinute(minute);
    }

    public int getMinute() {
        return this.time.minute;
    }

    public int getHour() {
        return this.time.hour % 24;
    }

    public void setTimeFormat(int format) {
        this.time = new TimeModel(format);
    }

    public void setInputMode(int inputMode2) {
        this.inputMode = inputMode2;
    }

    public int getInputMode() {
        return this.inputMode;
    }

    public final Dialog onCreateDialog(Bundle bundle) {
        TypedValue value = MaterialAttributes.resolve(requireContext(), C0822R.attr.materialTimePickerTheme);
        Dialog dialog = new Dialog(requireContext(), value == null ? 0 : value.data);
        Context context = dialog.getContext();
        int surfaceColor = MaterialAttributes.resolveOrThrow(context, C0822R.attr.colorSurface, MaterialTimePicker.class.getCanonicalName());
        MaterialShapeDrawable background = new MaterialShapeDrawable(context, (AttributeSet) null, 0, C0822R.style.Widget_MaterialComponents_TimePicker);
        background.initializeElevationOverlay(context);
        background.setFillColor(ColorStateList.valueOf(surfaceColor));
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(background);
        window.requestFeature(1);
        window.setLayout(-2, -2);
        return dialog;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        restoreState(bundle);
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelable(TIME_MODEL_EXTRA, this.time);
        bundle.putInt(INPUT_MODE_EXTRA, this.inputMode);
    }

    private void restoreState(Bundle bundle) {
        if (bundle != null) {
            TimeModel timeModel = (TimeModel) bundle.getParcelable(TIME_MODEL_EXTRA);
            this.time = timeModel;
            if (timeModel == null) {
                this.time = new TimeModel();
            }
            this.inputMode = bundle.getInt(INPUT_MODE_EXTRA, 0);
        }
    }

    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ViewGroup root = (ViewGroup) layoutInflater.inflate(C0822R.layout.material_timepicker_dialog, viewGroup);
        this.timePickerView = (TimePickerView) root.findViewById(C0822R.C0825id.material_timepicker_view);
        this.textInputView = (LinearLayout) root.findViewById(C0822R.C0825id.material_textinput_timepicker);
        MaterialButton materialButton = (MaterialButton) root.findViewById(C0822R.C0825id.material_timepicker_mode_button);
        this.modeButton = materialButton;
        updateInputMode(materialButton);
        ((MaterialButton) root.findViewById(C0822R.C0825id.material_timepicker_ok_button)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (MaterialTimePicker.this.listener != null) {
                    MaterialTimePicker.this.listener.onTimeSet(MaterialTimePicker.this);
                }
                MaterialTimePicker.this.dismiss();
            }
        });
        ((MaterialButton) root.findViewById(C0822R.C0825id.material_timepicker_cancel_button)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MaterialTimePicker.this.dismiss();
            }
        });
        this.modeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MaterialTimePicker materialTimePicker = MaterialTimePicker.this;
                int unused = materialTimePicker.inputMode = materialTimePicker.inputMode == 0 ? 1 : 0;
                MaterialTimePicker materialTimePicker2 = MaterialTimePicker.this;
                materialTimePicker2.updateInputMode(materialTimePicker2.modeButton);
            }
        });
        return root;
    }

    /* access modifiers changed from: private */
    public void updateInputMode(MaterialButton modeButton2) {
        modeButton2.setChecked(false);
        TimePickerPresenter timePickerPresenter = this.activePresenter;
        if (timePickerPresenter != null) {
            timePickerPresenter.hide();
        }
        TimePickerPresenter initializeOrRetrieveActivePresenterForMode = initializeOrRetrieveActivePresenterForMode(this.inputMode);
        this.activePresenter = initializeOrRetrieveActivePresenterForMode;
        initializeOrRetrieveActivePresenterForMode.show();
        this.activePresenter.invalidate();
        modeButton2.setIconResource(iconForMode(this.inputMode));
    }

    private TimePickerPresenter initializeOrRetrieveActivePresenterForMode(int mode) {
        if (mode == 0) {
            TimePickerClockPresenter timePickerClockPresenter2 = this.timePickerClockPresenter;
            if (timePickerClockPresenter2 == null) {
                timePickerClockPresenter2 = new TimePickerClockPresenter(this.timePickerView, this.time);
            }
            this.timePickerClockPresenter = timePickerClockPresenter2;
            return timePickerClockPresenter2;
        }
        if (this.timePickerTextInputPresenter == null) {
            this.timePickerTextInputPresenter = new TimePickerTextInputPresenter(this.textInputView, this.time);
        }
        return this.timePickerTextInputPresenter;
    }

    private static int iconForMode(int mode) {
        if (mode == 0) {
            return KEYBOARD_ICON;
        }
        if (mode == 1) {
            return CLOCK_ICON;
        }
        throw new IllegalArgumentException("no icon for mode: " + mode);
    }

    public void setListener(OnTimeSetListener listener2) {
        this.listener = listener2;
    }

    /* access modifiers changed from: package-private */
    public TimePickerClockPresenter getTimePickerClockPresenter() {
        return this.timePickerClockPresenter;
    }
}
