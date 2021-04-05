package com.google.android.gms.internal.firebase_auth;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public enum zzet {
    REFRESH_TOKEN("refresh_token"),
    AUTHORIZATION_CODE("authorization_code");
    
    private final String zzc;

    private zzet(String str) {
        this.zzc = str;
    }

    public final String toString() {
        return this.zzc;
    }
}
