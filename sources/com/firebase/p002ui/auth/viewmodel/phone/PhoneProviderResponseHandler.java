package com.firebase.p002ui.auth.viewmodel.phone;

import android.app.Application;
import com.firebase.p002ui.auth.IdpResponse;
import com.firebase.p002ui.auth.data.model.FlowParameters;
import com.firebase.p002ui.auth.data.model.Resource;
import com.firebase.p002ui.auth.util.data.AuthOperationManager;
import com.firebase.p002ui.auth.viewmodel.SignInViewModelBase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.PhoneAuthCredential;

/* renamed from: com.firebase.ui.auth.viewmodel.phone.PhoneProviderResponseHandler */
public class PhoneProviderResponseHandler extends SignInViewModelBase {
    public PhoneProviderResponseHandler(Application application) {
        super(application);
    }

    public void startSignIn(PhoneAuthCredential credential, final IdpResponse response) {
        if (!response.isSuccessful()) {
            setResult((Resource<IdpResponse>) Resource.forFailure(response.getError()));
        } else if (response.getProviderType().equals("phone")) {
            setResult((Resource<IdpResponse>) Resource.forLoading());
            AuthOperationManager.getInstance().signInAndLinkWithCredential(getAuth(), (FlowParameters) getArguments(), credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                public void onSuccess(AuthResult result) {
                    PhoneProviderResponseHandler.this.handleSuccess(response, result);
                }
            }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(Exception e) {
                    if (e instanceof FirebaseAuthUserCollisionException) {
                        PhoneProviderResponseHandler.this.handleMergeFailure(((FirebaseAuthUserCollisionException) e).getUpdatedCredential());
                    } else {
                        PhoneProviderResponseHandler.this.setResult((Resource<IdpResponse>) Resource.forFailure(e));
                    }
                }
            });
        } else {
            throw new IllegalStateException("This handler cannot be used without a phone response.");
        }
    }
}
