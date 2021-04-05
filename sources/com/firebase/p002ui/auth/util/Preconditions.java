package com.firebase.p002ui.auth.util;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import com.firebase.p002ui.auth.AuthUI;

/* renamed from: com.firebase.ui.auth.util.Preconditions */
public final class Preconditions {
    private Preconditions() {
    }

    public static <T> T checkNotNull(T val, String errorMessageTemplate, Object... errorMessageArgs) {
        if (val != null) {
            return val;
        }
        throw new NullPointerException(String.format(errorMessageTemplate, errorMessageArgs));
    }

    public static int checkValidStyle(Context context, int styleId, String errorMessageTemplate, Object... errorMessageArguments) {
        try {
            if ("style".equals(context.getResources().getResourceTypeName(styleId))) {
                return styleId;
            }
            throw new IllegalArgumentException(String.format(errorMessageTemplate, errorMessageArguments));
        } catch (Resources.NotFoundException e) {
            throw new IllegalArgumentException(String.format(errorMessageTemplate, errorMessageArguments));
        }
    }

    public static void checkUnset(Bundle b, String message, String... keys) {
        int length = keys.length;
        int i = 0;
        while (i < length) {
            if (!b.containsKey(keys[i])) {
                i++;
            } else {
                throw new IllegalStateException(message);
            }
        }
    }

    public static void checkConfigured(Context context, String message, int... ids) {
        int length = ids.length;
        int i = 0;
        while (i < length) {
            if (!context.getString(ids[i]).equals(AuthUI.UNCONFIGURED_CONFIG_VALUE)) {
                i++;
            } else {
                throw new IllegalStateException(message);
            }
        }
    }

    public static void checkArgument(boolean expression, String errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
