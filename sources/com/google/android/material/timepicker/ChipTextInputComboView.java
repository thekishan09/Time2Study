package com.google.android.material.timepicker;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.C0822R;
import com.google.android.material.chip.Chip;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Arrays;

class ChipTextInputComboView extends FrameLayout implements Checkable {
    /* access modifiers changed from: private */
    public final Chip chip;
    private final EditText editText;
    private TextView label;
    /* access modifiers changed from: private */
    public final TextInputLayout textInputLayout;
    private TextWatcher watcher;

    public ChipTextInputComboView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ChipTextInputComboView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChipTextInputComboView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = LayoutInflater.from(context);
        this.chip = (Chip) inflater.inflate(C0822R.layout.material_time_chip, this, false);
        TextInputLayout textInputLayout2 = (TextInputLayout) inflater.inflate(C0822R.layout.material_time_input, this, false);
        this.textInputLayout = textInputLayout2;
        EditText editText2 = textInputLayout2.getEditText();
        this.editText = editText2;
        editText2.setVisibility(4);
        HintSetterTextWatcher hintSetterTextWatcher = new HintSetterTextWatcher();
        this.watcher = hintSetterTextWatcher;
        this.editText.addTextChangedListener(hintSetterTextWatcher);
        addView(this.chip);
        addView(this.textInputLayout);
        this.label = (TextView) findViewById(C0822R.C0825id.material_label);
    }

    public boolean isChecked() {
        return this.chip.isChecked();
    }

    public void setChecked(boolean checked) {
        this.chip.setChecked(checked);
        int i = 0;
        this.editText.setVisibility(checked ? 0 : 4);
        Chip chip2 = this.chip;
        if (checked) {
            i = 8;
        }
        chip2.setVisibility(i);
        if (isChecked()) {
            this.editText.requestFocus();
        }
    }

    public void toggle() {
        this.chip.toggle();
    }

    public void setText(CharSequence text) {
        this.chip.setText(text);
        EditText editText2 = this.textInputLayout.getEditText();
        if (!TextUtils.isEmpty(editText2.getText())) {
            editText2.removeTextChangedListener(this.watcher);
            editText2.setText((CharSequence) null);
            editText2.setHint("00");
            editText2.addTextChangedListener(this.watcher);
        }
        editText2.setHint(text);
    }

    public void setOnClickListener(View.OnClickListener l) {
        this.chip.setOnClickListener(l);
    }

    public void setTag(int key, Object tag) {
        this.chip.setTag(key, tag);
    }

    public void setHelperText(CharSequence helperText) {
        this.label.setText(helperText);
    }

    public void setCursorVisible(boolean visible) {
        this.editText.setCursorVisible(visible);
    }

    public void addInputFilter(InputFilter filter) {
        InputFilter[] current = this.editText.getFilters();
        InputFilter[] arr = (InputFilter[]) Arrays.copyOf(current, current.length + 1);
        arr[current.length] = filter;
        this.editText.setFilters(arr);
    }

    public TextInputLayout getTextInput() {
        return this.textInputLayout;
    }

    public void setChipDelegate(AccessibilityDelegateCompat clickActionDelegate) {
        ViewCompat.setAccessibilityDelegate(this.chip, clickActionDelegate);
    }

    private class HintSetterTextWatcher extends TextWatcherAdapter {
        private static final String DEFAULT_HINT = "00";

        private HintSetterTextWatcher() {
        }

        public void afterTextChanged(Editable editable) {
            if (TextUtils.isEmpty(editable)) {
                ChipTextInputComboView.this.setText(DEFAULT_HINT);
                return;
            }
            ChipTextInputComboView.this.textInputLayout.getEditText().setHint((CharSequence) null);
            ChipTextInputComboView.this.chip.setText(editable.toString());
        }
    }
}
