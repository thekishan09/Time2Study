package com.firebase.p002ui.auth.data.model;

import android.content.Intent;
import com.firebase.p002ui.auth.FirebaseUiException;

/* renamed from: com.firebase.ui.auth.data.model.IntentRequiredException */
public class IntentRequiredException extends FirebaseUiException {
    private final Intent mIntent;
    private final int mRequestCode;

    public IntentRequiredException(Intent intent, int requestCode) {
        super(0);
        this.mIntent = intent;
        this.mRequestCode = requestCode;
    }

    public Intent getIntent() {
        return this.mIntent;
    }

    public int getRequestCode() {
        return this.mRequestCode;
    }
}
