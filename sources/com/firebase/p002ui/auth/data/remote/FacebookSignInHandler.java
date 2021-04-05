package com.firebase.p002ui.auth.data.remote;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.WebDialog;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.firebase.p002ui.auth.AuthUI;
import com.firebase.p002ui.auth.FirebaseUiException;
import com.firebase.p002ui.auth.IdpResponse;
import com.firebase.p002ui.auth.data.model.Resource;
import com.firebase.p002ui.auth.data.model.User;
import com.firebase.p002ui.auth.p007ui.HelperActivityBase;
import com.firebase.p002ui.auth.util.ExtraConstants;
import com.firebase.p002ui.auth.viewmodel.ProviderSignInBase;
import com.google.android.gms.common.internal.ImagesContract;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.firebase.ui.auth.data.remote.FacebookSignInHandler */
public class FacebookSignInHandler extends ProviderSignInBase<AuthUI.IdpConfig> {
    private static final String EMAIL = "email";
    private static final String PUBLIC_PROFILE = "public_profile";
    private final FacebookCallback<LoginResult> mCallback = new Callback();
    private final CallbackManager mCallbackManager = CallbackManager.Factory.create();
    private List<String> mPermissions;

    public FacebookSignInHandler(Application application) {
        super(application);
    }

    /* access modifiers changed from: private */
    public static IdpResponse createIdpResponse(LoginResult result, String email, String name, Uri photoUri) {
        return new IdpResponse.Builder(new User.Builder("facebook.com", email).setName(name).setPhotoUri(photoUri).build()).setToken(result.getAccessToken().getToken()).build();
    }

    /* access modifiers changed from: protected */
    public void onCreate() {
        List<String> permissions = ((AuthUI.IdpConfig) getArguments()).getParams().getStringArrayList(ExtraConstants.FACEBOOK_PERMISSIONS);
        List<String> permissions2 = new ArrayList<>(permissions == null ? Collections.emptyList() : permissions);
        if (!permissions2.contains("email")) {
            permissions2.add("email");
        }
        if (!permissions2.contains(PUBLIC_PROFILE)) {
            permissions2.add(PUBLIC_PROFILE);
        }
        this.mPermissions = permissions2;
        LoginManager.getInstance().registerCallback(this.mCallbackManager, this.mCallback);
    }

    public void startSignIn(HelperActivityBase activity) {
        WebDialog.setWebDialogTheme(activity.getFlowParams().themeId);
        LoginManager.getInstance().logInWithReadPermissions(activity, this.mPermissions);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /* access modifiers changed from: protected */
    public void onCleared() {
        super.onCleared();
        LoginManager.getInstance().unregisterCallback(this.mCallbackManager);
    }

    /* renamed from: com.firebase.ui.auth.data.remote.FacebookSignInHandler$Callback */
    private class Callback implements FacebookCallback<LoginResult> {
        private Callback() {
        }

        public void onSuccess(LoginResult result) {
            FacebookSignInHandler.this.setResult(Resource.forLoading());
            GraphRequest request = GraphRequest.newMeRequest(result.getAccessToken(), new ProfileRequest(result));
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,picture");
            request.setParameters(parameters);
            request.executeAsync();
        }

        public void onCancel() {
            onError(new FacebookException());
        }

        public void onError(FacebookException e) {
            FacebookSignInHandler.this.setResult(Resource.forFailure(new FirebaseUiException(4, (Throwable) e)));
        }
    }

    /* renamed from: com.firebase.ui.auth.data.remote.FacebookSignInHandler$ProfileRequest */
    private class ProfileRequest implements GraphRequest.GraphJSONObjectCallback {
        private final LoginResult mResult;

        public ProfileRequest(LoginResult result) {
            this.mResult = result;
        }

        public void onCompleted(JSONObject object, GraphResponse response) {
            FacebookRequestError error = response.getError();
            if (error != null) {
                FacebookSignInHandler.this.setResult(Resource.forFailure(new FirebaseUiException(4, (Throwable) error.getException())));
            } else if (object == null) {
                FacebookSignInHandler.this.setResult(Resource.forFailure(new FirebaseUiException(4, "Facebook graph request failed")));
            } else {
                String email = null;
                String name = null;
                Uri photoUri = null;
                try {
                    email = object.getString("email");
                } catch (JSONException e) {
                }
                try {
                    name = object.getString("name");
                } catch (JSONException e2) {
                }
                try {
                    photoUri = Uri.parse(object.getJSONObject("picture").getJSONObject("data").getString(ImagesContract.URL));
                } catch (JSONException e3) {
                }
                FacebookSignInHandler.this.setResult(Resource.forSuccess(FacebookSignInHandler.createIdpResponse(this.mResult, email, name, photoUri)));
            }
        }
    }
}
