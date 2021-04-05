package com.firebase.p002ui.auth.util.p008ui.fieldvalidators;

import android.content.res.Resources;
import com.firebase.p002ui.auth.C0719R;
import com.google.android.material.textfield.TextInputLayout;

/* renamed from: com.firebase.ui.auth.util.ui.fieldvalidators.PasswordFieldValidator */
public class PasswordFieldValidator extends BaseValidator {
    private int mMinLength;

    public PasswordFieldValidator(TextInputLayout errorContainer, int minLength) {
        super(errorContainer);
        this.mMinLength = minLength;
        Resources resources = this.mErrorContainer.getResources();
        int i = C0719R.plurals.fui_error_weak_password;
        int i2 = this.mMinLength;
        this.mErrorMessage = resources.getQuantityString(i, i2, new Object[]{Integer.valueOf(i2)});
    }

    /* access modifiers changed from: protected */
    public boolean isValid(CharSequence charSequence) {
        return charSequence.length() >= this.mMinLength;
    }
}
