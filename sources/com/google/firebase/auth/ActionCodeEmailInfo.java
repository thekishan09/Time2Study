package com.google.firebase.auth;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public abstract class ActionCodeEmailInfo extends ActionCodeInfo {
    public abstract String getPreviousEmail();

    public String getEmail() {
        return super.getEmail();
    }
}
