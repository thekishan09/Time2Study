package com.firebase.p002ui.auth.viewmodel;

import android.app.Application;
import com.firebase.p002ui.auth.FirebaseAuthAnonymousUpgradeException;
import com.firebase.p002ui.auth.IdpResponse;
import com.firebase.p002ui.auth.data.model.Resource;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;

/* renamed from: com.firebase.ui.auth.viewmodel.SignInViewModelBase */
public abstract class SignInViewModelBase extends AuthViewModelBase<IdpResponse> {
    protected SignInViewModelBase(Application application) {
        super(application);
    }

    /* access modifiers changed from: protected */
    public void setResult(Resource<IdpResponse> output) {
        super.setResult(output);
    }

    /* access modifiers changed from: protected */
    public void handleSuccess(IdpResponse response, AuthResult result) {
        setResult((Resource<IdpResponse>) Resource.forSuccess(response.withResult(result)));
    }

    /* access modifiers changed from: protected */
    public void handleMergeFailure(AuthCredential credential) {
        handleMergeFailure(new IdpResponse.Builder(credential).build());
    }

    /* access modifiers changed from: protected */
    public void handleMergeFailure(IdpResponse failureResponse) {
        setResult((Resource<IdpResponse>) Resource.forFailure(new FirebaseAuthAnonymousUpgradeException(5, failureResponse)));
    }
}
