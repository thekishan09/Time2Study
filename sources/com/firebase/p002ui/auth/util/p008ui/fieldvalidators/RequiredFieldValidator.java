package com.firebase.p002ui.auth.util.p008ui.fieldvalidators;

import com.firebase.p002ui.auth.C0719R;
import com.google.android.material.textfield.TextInputLayout;

/* renamed from: com.firebase.ui.auth.util.ui.fieldvalidators.RequiredFieldValidator */
public class RequiredFieldValidator extends BaseValidator {
    public RequiredFieldValidator(TextInputLayout errorContainer) {
        super(errorContainer);
        this.mErrorMessage = this.mErrorContainer.getResources().getString(C0719R.string.fui_required_field);
    }

    /* access modifiers changed from: protected */
    public boolean isValid(CharSequence charSequence) {
        return charSequence != null && charSequence.length() > 0;
    }
}
