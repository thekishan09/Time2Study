package com.firebase.p002ui.auth.p007ui.phone;

import com.google.firebase.auth.PhoneAuthCredential;

/* renamed from: com.firebase.ui.auth.ui.phone.PhoneVerification */
public final class PhoneVerification {
    private final PhoneAuthCredential mCredential;
    private final boolean mIsAutoVerified;
    private final String mNumber;

    public PhoneVerification(String number, PhoneAuthCredential credential, boolean verified) {
        this.mNumber = number;
        this.mCredential = credential;
        this.mIsAutoVerified = verified;
    }

    public String getNumber() {
        return this.mNumber;
    }

    public PhoneAuthCredential getCredential() {
        return this.mCredential;
    }

    public boolean isAutoVerified() {
        return this.mIsAutoVerified;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PhoneVerification that = (PhoneVerification) o;
        if (this.mIsAutoVerified != that.mIsAutoVerified || !this.mNumber.equals(that.mNumber) || !this.mCredential.equals(that.mCredential)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((this.mNumber.hashCode() * 31) + this.mCredential.hashCode()) * 31) + (this.mIsAutoVerified ? 1 : 0);
    }

    public String toString() {
        return "PhoneVerification{mNumber='" + this.mNumber + '\'' + ", mCredential=" + this.mCredential + ", mIsAutoVerified=" + this.mIsAutoVerified + '}';
    }
}
