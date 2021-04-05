package com.firebase.p002ui.auth.data.model;

import android.app.PendingIntent;
import com.firebase.p002ui.auth.FirebaseUiException;

/* renamed from: com.firebase.ui.auth.data.model.PendingIntentRequiredException */
public class PendingIntentRequiredException extends FirebaseUiException {
    private final PendingIntent mPendingIntent;
    private final int mRequestCode;

    public PendingIntentRequiredException(PendingIntent pendingIntent, int requestCode) {
        super(0);
        this.mPendingIntent = pendingIntent;
        this.mRequestCode = requestCode;
    }

    public PendingIntent getPendingIntent() {
        return this.mPendingIntent;
    }

    public int getRequestCode() {
        return this.mRequestCode;
    }
}
