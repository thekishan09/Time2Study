package com.google.firebase.firestore.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Preconditions {
    public static void checkArgument(boolean expression, @Nullable String errorMessageTemplate, @Nullable Object... errorMessageArgs) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(errorMessageTemplate, errorMessageArgs));
        }
    }

    public static <T> T checkNotNull(@Nonnull T reference) {
        if (reference != null) {
            return reference;
        }
        throw null;
    }

    public static <T> T checkNotNull(@Nonnull T reference, @Nullable Object errorMessage) {
        if (reference != null) {
            return reference;
        }
        throw new NullPointerException(String.valueOf(errorMessage));
    }

    public static void checkState(boolean expression) {
        if (!expression) {
            throw new IllegalStateException();
        }
    }
}
