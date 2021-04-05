package com.firebase.p002ui.auth.data.model;

import com.firebase.p002ui.auth.FirebaseUiException;

/* renamed from: com.firebase.ui.auth.data.model.PhoneNumberVerificationRequiredException */
public class PhoneNumberVerificationRequiredException extends FirebaseUiException {
    private final String mPhoneNumber;

    public PhoneNumberVerificationRequiredException(String number) {
        super(4, "Phone number requires verification.");
        this.mPhoneNumber = number;
    }

    public String getPhoneNumber() {
        return this.mPhoneNumber;
    }
}
