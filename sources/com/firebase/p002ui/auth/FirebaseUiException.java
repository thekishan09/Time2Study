package com.firebase.p002ui.auth;

/* renamed from: com.firebase.ui.auth.FirebaseUiException */
public class FirebaseUiException extends Exception {
    private final int mErrorCode;

    public FirebaseUiException(int code) {
        this(code, ErrorCodes.toFriendlyMessage(code));
    }

    public FirebaseUiException(int code, String message) {
        super(message);
        this.mErrorCode = code;
    }

    public FirebaseUiException(int code, Throwable cause) {
        this(code, ErrorCodes.toFriendlyMessage(code), cause);
    }

    public FirebaseUiException(int code, String message, Throwable cause) {
        super(message, cause);
        this.mErrorCode = code;
    }

    public final int getErrorCode() {
        return this.mErrorCode;
    }
}
