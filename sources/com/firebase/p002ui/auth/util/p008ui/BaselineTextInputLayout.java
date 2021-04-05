package com.firebase.p002ui.auth.util.p008ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import com.google.android.material.textfield.TextInputLayout;

/* renamed from: com.firebase.ui.auth.util.ui.BaselineTextInputLayout */
public class BaselineTextInputLayout extends TextInputLayout {
    public BaselineTextInputLayout(Context context) {
        super(context);
    }

    public BaselineTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaselineTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int getBaseline() {
        EditText text = getEditText();
        return text == null ? super.getBaseline() : text.getPaddingTop() + text.getBaseline();
    }
}
