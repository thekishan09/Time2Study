package com.google.android.material.timepicker;

import android.content.res.Resources;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.google.android.material.C0822R;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.android.material.timepicker.TimePickerView;
import java.util.Locale;

class TimePickerTextInputPresenter implements TimePickerView.OnSelectionChange, TimePickerPresenter {
    private final TimePickerTextInputKeyController controller;
    private final EditText hourEditText;
    private final ChipTextInputComboView hourTextInput;
    private final TextWatcher hourTextWatcher = new TextWatcherAdapter() {
        public void afterTextChanged(Editable s) {
            try {
                if (TextUtils.isEmpty(s)) {
                    TimePickerTextInputPresenter.this.time.setHour(0);
                    return;
                }
                TimePickerTextInputPresenter.this.time.setHour(Integer.parseInt(s.toString()));
            } catch (NumberFormatException e) {
            }
        }
    };
    private final EditText minuteEditText;
    private final ChipTextInputComboView minuteTextInput;
    private final TextWatcher minuteTextWatcher = new TextWatcherAdapter() {
        public void afterTextChanged(Editable s) {
            try {
                if (TextUtils.isEmpty(s)) {
                    TimePickerTextInputPresenter.this.time.setMinute(0);
                    return;
                }
                TimePickerTextInputPresenter.this.time.setMinute(Integer.parseInt(s.toString()));
            } catch (NumberFormatException e) {
            }
        }
    };
    /* access modifiers changed from: private */
    public final TimeModel time;
    private final LinearLayout timePickerView;
    private MaterialButtonToggleGroup toggle;

    public TimePickerTextInputPresenter(LinearLayout timePickerView2, TimeModel time2) {
        this.timePickerView = timePickerView2;
        this.time = time2;
        Resources res = timePickerView2.getResources();
        this.minuteTextInput = (ChipTextInputComboView) timePickerView2.findViewById(C0822R.C0825id.material_minute_text_input);
        this.hourTextInput = (ChipTextInputComboView) timePickerView2.findViewById(C0822R.C0825id.material_hour_text_input);
        ((TextView) this.minuteTextInput.findViewById(C0822R.C0825id.material_label)).setText(res.getString(C0822R.string.material_timepicker_minute));
        ((TextView) this.hourTextInput.findViewById(C0822R.C0825id.material_label)).setText(res.getString(C0822R.string.material_timepicker_hour));
        this.minuteTextInput.setTag(C0822R.C0825id.selection_type, 12);
        this.hourTextInput.setTag(C0822R.C0825id.selection_type, 10);
        if (time2.format == 0) {
            setupPeriodToggle();
        }
        View.OnClickListener onClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                TimePickerTextInputPresenter.this.onSelectionChanged(((Integer) v.getTag(C0822R.C0825id.selection_type)).intValue());
            }
        };
        this.hourTextInput.setOnClickListener(onClickListener);
        this.minuteTextInput.setOnClickListener(onClickListener);
        this.hourTextInput.addInputFilter(time2.getHourInputValidator());
        this.minuteTextInput.addInputFilter(time2.getMinuteInputValidator());
        this.hourEditText = this.hourTextInput.getTextInput().getEditText();
        this.minuteEditText = this.minuteTextInput.getTextInput().getEditText();
        this.controller = new TimePickerTextInputKeyController(this.hourTextInput, this.minuteTextInput, time2);
        this.hourTextInput.setChipDelegate(new ClickActionDelegate(timePickerView2.getContext(), C0822R.string.material_hour_selection));
        this.minuteTextInput.setChipDelegate(new ClickActionDelegate(timePickerView2.getContext(), C0822R.string.material_minute_selection));
        initialize();
    }

    public void initialize() {
        addTextWatchers();
        setTime(this.time);
        this.controller.bind();
    }

    private void addTextWatchers() {
        this.hourEditText.addTextChangedListener(this.hourTextWatcher);
        this.minuteEditText.addTextChangedListener(this.minuteTextWatcher);
    }

    private void removeTextWatchers() {
        this.hourEditText.removeTextChangedListener(this.hourTextWatcher);
        this.minuteEditText.removeTextChangedListener(this.minuteTextWatcher);
    }

    private void setTime(TimeModel time2) {
        removeTextWatchers();
        Locale current = this.timePickerView.getResources().getConfiguration().locale;
        boolean z = true;
        String minuteFormatted = String.format(current, "%02d", new Object[]{Integer.valueOf(time2.minute)});
        String hourFormatted = String.format(current, "%02d", new Object[]{Integer.valueOf(time2.getHourForDisplay())});
        this.minuteTextInput.setText(minuteFormatted);
        this.hourTextInput.setText(hourFormatted);
        this.minuteTextInput.setChecked(time2.selection == 12);
        ChipTextInputComboView chipTextInputComboView = this.hourTextInput;
        if (time2.selection != 10) {
            z = false;
        }
        chipTextInputComboView.setChecked(z);
        addTextWatchers();
        updateSelection();
    }

    private void setupPeriodToggle() {
        MaterialButtonToggleGroup materialButtonToggleGroup = (MaterialButtonToggleGroup) this.timePickerView.findViewById(C0822R.C0825id.material_clock_period_toggle);
        this.toggle = materialButtonToggleGroup;
        materialButtonToggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                TimePickerTextInputPresenter.this.time.setPeriod(checkedId == C0822R.C0825id.material_clock_period_pm_button ? 1 : 0);
            }
        });
        this.toggle.setVisibility(0);
        updateSelection();
    }

    private void updateSelection() {
        MaterialButtonToggleGroup materialButtonToggleGroup = this.toggle;
        if (materialButtonToggleGroup != null) {
            materialButtonToggleGroup.check(this.time.period == 0 ? C0822R.C0825id.material_clock_period_am_button : C0822R.C0825id.material_clock_period_pm_button);
        }
    }

    public void onSelectionChanged(int selection) {
        this.time.selection = selection;
        boolean z = true;
        this.minuteTextInput.setChecked(selection == 12);
        ChipTextInputComboView chipTextInputComboView = this.hourTextInput;
        if (selection != 10) {
            z = false;
        }
        chipTextInputComboView.setChecked(z);
        updateSelection();
    }

    public void show() {
        this.timePickerView.setVisibility(0);
    }

    public void hide() {
        View currentFocus = this.timePickerView.getFocusedChild();
        if (currentFocus == null) {
            this.timePickerView.setVisibility(8);
            return;
        }
        InputMethodManager imm = (InputMethodManager) ContextCompat.getSystemService(this.timePickerView.getContext(), InputMethodManager.class);
        if (imm != null) {
            imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
        this.timePickerView.setVisibility(8);
    }

    public void invalidate() {
        setTime(this.time);
    }
}
