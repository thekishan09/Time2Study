package com.firebase.p002ui.auth;

/* renamed from: com.firebase.ui.auth.FirebaseAuthAnonymousUpgradeException */
public class FirebaseAuthAnonymousUpgradeException extends Exception {
    private IdpResponse mResponse;

    public FirebaseAuthAnonymousUpgradeException(int code, IdpResponse response) {
        super(ErrorCodes.toFriendlyMessage(code));
        this.mResponse = response;
    }

    public IdpResponse getResponse() {
        return this.mResponse;
    }
}
