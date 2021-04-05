package com.firebase.p002ui.auth.util.data;

/* renamed from: com.firebase.ui.auth.util.data.ProviderAvailability */
public final class ProviderAvailability {
    public static final boolean IS_FACEBOOK_AVAILABLE = exists("com.facebook.login.LoginManager");
    public static final boolean IS_GITHUB_AVAILABLE = exists("com.firebase.ui.auth.data.remote.GitHubSignInHandler");
    public static final boolean IS_TWITTER_AVAILABLE = exists("com.twitter.sdk.android.core.identity.TwitterAuthClient");

    private ProviderAvailability() {
        throw new AssertionError("No instance for you!");
    }

    private static boolean exists(String name) {
        try {
            Class.forName(name);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
