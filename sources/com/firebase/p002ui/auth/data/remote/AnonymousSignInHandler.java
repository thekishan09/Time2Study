package com.firebase.p002ui.auth.data.remote;

import android.app.Application;
import android.content.Intent;
import com.firebase.p002ui.auth.AuthUI;
import com.firebase.p002ui.auth.IdpResponse;
import com.firebase.p002ui.auth.data.model.FlowParameters;
import com.firebase.p002ui.auth.data.model.Resource;
import com.firebase.p002ui.auth.data.model.User;
import com.firebase.p002ui.auth.p007ui.HelperActivityBase;
import com.firebase.p002ui.auth.viewmodel.ProviderSignInBase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/* renamed from: com.firebase.ui.auth.data.remote.AnonymousSignInHandler */
public class AnonymousSignInHandler extends ProviderSignInBase<FlowParameters> {
    public FirebaseAuth mAuth;

    public AnonymousSignInHandler(Application application) {
        super(application);
    }

    /* access modifiers changed from: protected */
    public void onCreate() {
        this.mAuth = getAuth();
    }

    public void startSignIn(HelperActivityBase activity) {
        setResult(Resource.forLoading());
        this.mAuth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            public void onSuccess(AuthResult result) {
                AnonymousSignInHandler anonymousSignInHandler = AnonymousSignInHandler.this;
                anonymousSignInHandler.setResult(Resource.forSuccess(anonymousSignInHandler.initResponse(result.getAdditionalUserInfo().isNewUser())));
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(Exception e) {
                AnonymousSignInHandler.this.setResult(Resource.forFailure(e));
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    /* access modifiers changed from: private */
    public IdpResponse initResponse(boolean isNewUser) {
        return new IdpResponse.Builder(new User.Builder(AuthUI.ANONYMOUS_PROVIDER, (String) null).build()).setNewUser(isNewUser).build();
    }

    private FirebaseAuth getAuth() {
        return FirebaseAuth.getInstance(FirebaseApp.getInstance(((FlowParameters) getArguments()).appName));
    }
}
