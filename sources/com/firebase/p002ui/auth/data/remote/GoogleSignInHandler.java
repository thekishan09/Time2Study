package com.firebase.p002ui.auth.data.remote;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import com.firebase.p002ui.auth.AuthUI;
import com.firebase.p002ui.auth.FirebaseUiException;
import com.firebase.p002ui.auth.IdpResponse;
import com.firebase.p002ui.auth.data.model.IntentRequiredException;
import com.firebase.p002ui.auth.data.model.Resource;
import com.firebase.p002ui.auth.data.model.User;
import com.firebase.p002ui.auth.data.model.UserCancellationException;
import com.firebase.p002ui.auth.p007ui.HelperActivityBase;
import com.firebase.p002ui.auth.util.ExtraConstants;
import com.firebase.p002ui.auth.viewmodel.ProviderSignInBase;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;

/* renamed from: com.firebase.ui.auth.data.remote.GoogleSignInHandler */
public class GoogleSignInHandler extends ProviderSignInBase<Params> {
    private static final String TAG = "GoogleSignInHandler";
    private AuthUI.IdpConfig mConfig;
    private String mEmail;

    public GoogleSignInHandler(Application application) {
        super(application);
    }

    private static IdpResponse createIdpResponse(GoogleSignInAccount account) {
        return new IdpResponse.Builder(new User.Builder("google.com", account.getEmail()).setName(account.getDisplayName()).setPhotoUri(account.getPhotoUrl()).build()).setToken(account.getIdToken()).build();
    }

    /* access modifiers changed from: protected */
    public void onCreate() {
        Params params = (Params) getArguments();
        this.mConfig = params.config;
        this.mEmail = params.email;
    }

    public void startSignIn(HelperActivityBase activity) {
        start();
    }

    private void start() {
        setResult(Resource.forLoading());
        setResult(Resource.forFailure(new IntentRequiredException(GoogleSignIn.getClient((Context) getApplication(), getSignInOptions()).getSignInIntent(), 110)));
    }

    private GoogleSignInOptions getSignInOptions() {
        GoogleSignInOptions.Builder builder = new GoogleSignInOptions.Builder((GoogleSignInOptions) this.mConfig.getParams().getParcelable(ExtraConstants.GOOGLE_SIGN_IN_OPTIONS));
        if (!TextUtils.isEmpty(this.mEmail)) {
            builder.setAccountName(this.mEmail);
        }
        return builder.build();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 110) {
            try {
                setResult(Resource.forSuccess(createIdpResponse(GoogleSignIn.getSignedInAccountFromIntent(data).getResult(ApiException.class))));
            } catch (ApiException e) {
                if (e.getStatusCode() == 5) {
                    this.mEmail = null;
                    start();
                } else if (e.getStatusCode() == 12502) {
                    start();
                } else if (e.getStatusCode() == 12501) {
                    setResult(Resource.forFailure(new UserCancellationException()));
                } else {
                    if (e.getStatusCode() == 10) {
                        Log.w(TAG, "Developer error: this application is misconfigured. Check your SHA1 and package name in the Firebase console.");
                    }
                    setResult(Resource.forFailure(new FirebaseUiException(4, "Code: " + e.getStatusCode() + ", message: " + e.getMessage())));
                }
            }
        }
    }

    /* renamed from: com.firebase.ui.auth.data.remote.GoogleSignInHandler$Params */
    public static final class Params {
        /* access modifiers changed from: private */
        public final AuthUI.IdpConfig config;
        /* access modifiers changed from: private */
        public final String email;

        public Params(AuthUI.IdpConfig config2) {
            this(config2, (String) null);
        }

        public Params(AuthUI.IdpConfig config2, String email2) {
            this.config = config2;
            this.email = email2;
        }
    }
}
