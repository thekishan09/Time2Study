package com.firebase.p002ui.auth.viewmodel.email;

import android.app.Application;
import com.firebase.p002ui.auth.AuthUI;
import com.firebase.p002ui.auth.IdpResponse;
import com.firebase.p002ui.auth.data.model.FlowParameters;
import com.firebase.p002ui.auth.data.model.Resource;
import com.firebase.p002ui.auth.data.model.User;
import com.firebase.p002ui.auth.data.remote.ProfileMerger;
import com.firebase.p002ui.auth.util.data.AuthOperationManager;
import com.firebase.p002ui.auth.util.data.TaskFailureLogger;
import com.firebase.p002ui.auth.viewmodel.SignInViewModelBase;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;

/* renamed from: com.firebase.ui.auth.viewmodel.email.WelcomeBackPasswordHandler */
public class WelcomeBackPasswordHandler extends SignInViewModelBase {
    private static final String TAG = "WBPasswordHandler";
    private String mPendingPassword;

    public WelcomeBackPasswordHandler(Application application) {
        super(application);
    }

    public void startSignIn(String email, String password, IdpResponse inputResponse, final AuthCredential credential) {
        final IdpResponse outputResponse;
        setResult((Resource<IdpResponse>) Resource.forLoading());
        this.mPendingPassword = password;
        if (credential == null) {
            outputResponse = new IdpResponse.Builder(new User.Builder("password", email).build()).build();
        } else {
            outputResponse = new IdpResponse.Builder(inputResponse.getUser()).setToken(inputResponse.getIdpToken()).setSecret(inputResponse.getIdpSecret()).build();
        }
        AuthOperationManager authOperationManager = AuthOperationManager.getInstance();
        if (authOperationManager.canUpgradeAnonymous(getAuth(), (FlowParameters) getArguments())) {
            final AuthCredential credToValidate = EmailAuthProvider.getCredential(email, password);
            if (AuthUI.SOCIAL_PROVIDERS.contains(inputResponse.getProviderType())) {
                authOperationManager.safeLink(credToValidate, credential, (FlowParameters) getArguments()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    public void onSuccess(AuthResult result) {
                        WelcomeBackPasswordHandler.this.handleMergeFailure(credToValidate);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    public void onFailure(Exception e) {
                        WelcomeBackPasswordHandler.this.setResult((Resource<IdpResponse>) Resource.forFailure(e));
                    }
                });
            } else {
                authOperationManager.validateCredential(credToValidate, (FlowParameters) getArguments()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            WelcomeBackPasswordHandler.this.handleMergeFailure(credToValidate);
                        } else {
                            WelcomeBackPasswordHandler.this.setResult((Resource<IdpResponse>) Resource.forFailure(task.getException()));
                        }
                    }
                });
            }
        } else {
            getAuth().signInWithEmailAndPassword(email, password).continueWithTask(new Continuation<AuthResult, Task<AuthResult>>() {
                public Task<AuthResult> then(Task<AuthResult> task) throws Exception {
                    AuthResult result = task.getResult(Exception.class);
                    if (credential == null) {
                        return Tasks.forResult(result);
                    }
                    return result.getUser().linkWithCredential(credential).continueWithTask(new ProfileMerger(outputResponse)).addOnFailureListener(new TaskFailureLogger(WelcomeBackPasswordHandler.TAG, "linkWithCredential+merge failed."));
                }
            }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                public void onSuccess(AuthResult result) {
                    WelcomeBackPasswordHandler.this.handleSuccess(outputResponse, result);
                }
            }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(Exception e) {
                    WelcomeBackPasswordHandler.this.setResult((Resource<IdpResponse>) Resource.forFailure(e));
                }
            }).addOnFailureListener(new TaskFailureLogger(TAG, "signInWithEmailAndPassword failed."));
        }
    }

    public String getPendingPassword() {
        return this.mPendingPassword;
    }
}
