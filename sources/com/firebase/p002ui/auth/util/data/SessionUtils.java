package com.firebase.p002ui.auth.util.data;

import java.util.Random;

/* renamed from: com.firebase.ui.auth.util.data.SessionUtils */
public class SessionUtils {
    private static final String VALID_CHARS = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String generateRandomAlphaNumericString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(VALID_CHARS.charAt(random.nextInt(length)));
        }
        return sb.toString();
    }
}
