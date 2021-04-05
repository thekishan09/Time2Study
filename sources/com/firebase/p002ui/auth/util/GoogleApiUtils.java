package com.firebase.p002ui.auth.util;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.CredentialsClient;
import com.google.android.gms.auth.api.credentials.CredentialsOptions;

/* renamed from: com.firebase.ui.auth.util.GoogleApiUtils */
public final class GoogleApiUtils {
    private GoogleApiUtils() {
        throw new AssertionError("No instance for you!");
    }

    public static CredentialsClient getCredentialsClient(Context context) {
        CredentialsOptions options = new CredentialsOptions.Builder().forceEnableSaveDialog().zze();
        if (context instanceof Activity) {
            return Credentials.getClient((Activity) context, options);
        }
        return Credentials.getClient(context, options);
    }
}
