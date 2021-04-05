package com.firebase.p002ui.auth.viewmodel.idp;

import android.app.Application;
import com.firebase.p002ui.auth.AuthUI;
import com.firebase.p002ui.auth.FirebaseUiException;
import com.firebase.p002ui.auth.IdpResponse;
import com.firebase.p002ui.auth.data.model.FlowParameters;
import com.firebase.p002ui.auth.data.model.Resource;
import com.firebase.p002ui.auth.util.data.AuthOperationManager;
import com.firebase.p002ui.auth.util.data.ProviderUtils;
import com.firebase.p002ui.auth.viewmodel.SignInViewModelBase;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;

/* renamed from: com.firebase.ui.auth.viewmodel.idp.LinkingSocialProviderResponseHandler */
public class LinkingSocialProviderResponseHandler extends SignInViewModelBase {
    private String mEmail;
    /* access modifiers changed from: private */
    public AuthCredential mRequestedSignInCredential;

    public LinkingSocialProviderResponseHandler(Application application) {
        super(application);
    }

    public void setRequestedSignInCredentialForEmail(AuthCredential credential, String email) {
        this.mRequestedSignInCredential = credential;
        this.mEmail = email;
    }

    public void startSignIn(final IdpResponse response) {
        if (!response.isSuccessful()) {
            setResult((Resource<IdpResponse>) Resource.forFailure(response.getError()));
        } else if (AuthUI.SOCIAL_PROVIDERS.contains(response.getProviderType())) {
            String str = this.mEmail;
            if (str == null || str.equals(response.getEmail())) {
                setResult((Resource<IdpResponse>) Resource.forLoading());
                AuthOperationManager authOperationManager = AuthOperationManager.getInstance();
                final AuthCredential credential = ProviderUtils.getAuthCredential(response);
                if (authOperationManager.canUpgradeAnonymous(getAuth(), (FlowParameters) getArguments())) {
                    AuthCredential authCredential = this.mRequestedSignInCredential;
                    if (authCredential == null) {
                        handleMergeFailure(credential);
                    } else {
                        authOperationManager.safeLink(credential, authCredential, (FlowParameters) getArguments()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            public void onSuccess(AuthResult result) {
                                LinkingSocialProviderResponseHandler.this.handleMergeFailure(credential);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            public void onFailure(Exception e) {
                                LinkingSocialProviderResponseHandler.this.setResult((Resource<IdpResponse>) Resource.forFailure(e));
                            }
                        });
                    }
                } else {
                    getAuth().signInWithCredential(credential).continueWithTask(new Continuation<AuthResult, Task<AuthResult>>() {
                        public Task<AuthResult> then(Task<AuthResult> task) {
                            final AuthResult result = task.getResult();
                            if (LinkingSocialProviderResponseHandler.this.mRequestedSignInCredential == null) {
                                return Tasks.forResult(result);
                            }
                            return result.getUser().linkWithCredential(LinkingSocialProviderResponseHandler.this.mRequestedSignInCredential).continueWith(new Continuation<AuthResult, AuthResult>() {
                                public AuthResult then(Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        return task.getResult();
                                    }
                                    return result;
                                }
                            });
                        }
                    }).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                LinkingSocialProviderResponseHandler.this.handleSuccess(response, task.getResult());
                            } else {
                                LinkingSocialProviderResponseHandler.this.setResult((Resource<IdpResponse>) Resource.forFailure(task.getException()));
                            }
                        }
                    });
                }
            } else {
                setResult((Resource<IdpResponse>) Resource.forFailure(new FirebaseUiException(6)));
            }
        } else {
            throw new IllegalStateException("This handler cannot be used to link email or phone providers");
        }
    }
}
