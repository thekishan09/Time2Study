package com.firebase.p002ui.auth.util.p008ui.fieldvalidators;

import android.util.Patterns;
import com.firebase.p002ui.auth.C0719R;
import com.google.android.material.textfield.TextInputLayout;

/* renamed from: com.firebase.ui.auth.util.ui.fieldvalidators.EmailFieldValidator */
public class EmailFieldValidator extends BaseValidator {
    public EmailFieldValidator(TextInputLayout errorContainer) {
        super(errorContainer);
        this.mErrorMessage = this.mErrorContainer.getResources().getString(C0719R.string.fui_invalid_email_address);
        this.mEmptyMessage = this.mErrorContainer.getResources().getString(C0719R.string.fui_missing_email_address);
    }

    /* access modifiers changed from: protected */
    public boolean isValid(CharSequence charSequence) {
        return Patterns.EMAIL_ADDRESS.matcher(charSequence).matches();
    }
}
