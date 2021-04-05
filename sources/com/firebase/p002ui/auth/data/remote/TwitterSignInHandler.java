package com.firebase.p002ui.auth.data.remote;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.firebase.p002ui.auth.AuthUI;
import com.firebase.p002ui.auth.C0719R;
import com.firebase.p002ui.auth.FirebaseUiException;
import com.firebase.p002ui.auth.IdpResponse;
import com.firebase.p002ui.auth.data.model.Resource;
import com.firebase.p002ui.auth.data.model.User;
import com.firebase.p002ui.auth.p007ui.HelperActivityBase;
import com.firebase.p002ui.auth.util.data.ProviderAvailability;
import com.firebase.p002ui.auth.viewmodel.ProviderSignInBase;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

/* renamed from: com.firebase.ui.auth.data.remote.TwitterSignInHandler */
public class TwitterSignInHandler extends ProviderSignInBase<Void> {
    private final TwitterSessionResult mCallback = new TwitterSessionResult();
    private final TwitterAuthClient mClient = new TwitterAuthClient();

    static {
        if (ProviderAvailability.IS_TWITTER_AVAILABLE) {
            Context context = AuthUI.getApplicationContext();
            Twitter.initialize(new TwitterConfig.Builder(context).twitterAuthConfig(new TwitterAuthConfig(context.getString(C0719R.string.twitter_consumer_key), context.getString(C0719R.string.twitter_consumer_secret))).build());
        }
    }

    public TwitterSignInHandler(Application application) {
        super(application);
    }

    public static void initializeTwitter() {
    }

    /* access modifiers changed from: private */
    public static IdpResponse createIdpResponse(TwitterSession session, String email, String name, Uri photoUri) {
        return new IdpResponse.Builder(new User.Builder("twitter.com", email).setName(name).setPhotoUri(photoUri).build()).setToken(session.getAuthToken().token).setSecret(session.getAuthToken().secret).build();
    }

    public void startSignIn(HelperActivityBase activity) {
        this.mClient.authorize(activity, this.mCallback);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.mClient.onActivityResult(requestCode, resultCode, data);
    }

    /* renamed from: com.firebase.ui.auth.data.remote.TwitterSignInHandler$TwitterSessionResult */
    private class TwitterSessionResult extends Callback<TwitterSession> {
        private TwitterSessionResult() {
        }

        public void success(final Result<TwitterSession> sessionResult) {
            TwitterSignInHandler.this.setResult(Resource.forLoading());
            TwitterCore.getInstance().getApiClient().getAccountService().verifyCredentials(false, false, true).enqueue(new Callback<com.twitter.sdk.android.core.models.User>() {
                public void success(Result<com.twitter.sdk.android.core.models.User> result) {
                    com.twitter.sdk.android.core.models.User user = (com.twitter.sdk.android.core.models.User) result.data;
                    TwitterSignInHandler.this.setResult(Resource.forSuccess(TwitterSignInHandler.createIdpResponse((TwitterSession) sessionResult.data, user.email, user.name, Uri.parse(user.profileImageUrlHttps))));
                }

                public void failure(TwitterException e) {
                    TwitterSignInHandler.this.setResult(Resource.forFailure(new FirebaseUiException(4, (Throwable) e)));
                }
            });
        }

        public void failure(TwitterException e) {
            TwitterSignInHandler.this.setResult(Resource.forFailure(new FirebaseUiException(4, (Throwable) e)));
        }
    }
}
