package com.squareup.okhttp;

import java.io.UnsupportedEncodingException;
import okio.ByteString;

public final class Credentials {
    private Credentials() {
    }

    public static String basic(String userName, String password) {
        try {
            String encoded = ByteString.m601of((userName + ":" + password).getBytes("ISO-8859-1")).base64();
            return "Basic " + encoded;
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError();
        }
    }
}
