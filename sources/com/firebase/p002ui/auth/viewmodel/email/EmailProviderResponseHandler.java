package com.firebase.p002ui.auth.viewmodel.email;

import android.app.Application;
import com.firebase.p002ui.auth.IdpResponse;
import com.firebase.p002ui.auth.data.model.FlowParameters;
import com.firebase.p002ui.auth.data.model.IntentRequiredException;
import com.firebase.p002ui.auth.data.model.Resource;
import com.firebase.p002ui.auth.data.model.User;
import com.firebase.p002ui.auth.data.remote.ProfileMerger;
import com.firebase.p002ui.auth.p007ui.email.WelcomeBackEmailLinkPrompt;
import com.firebase.p002ui.auth.p007ui.email.WelcomeBackPasswordPrompt;
import com.firebase.p002ui.auth.p007ui.idp.WelcomeBackIdpPrompt;
import com.firebase.p002ui.auth.util.data.AuthOperationManager;
import com.firebase.p002ui.auth.util.data.ProviderUtils;
import com.firebase.p002ui.auth.util.data.TaskFailureLogger;
import com.firebase.p002ui.auth.viewmodel.SignInViewModelBase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

/* renamed from: com.firebase.ui.auth.viewmodel.email.EmailProviderResponseHandler */
public class EmailProviderResponseHandler extends SignInViewModelBase {
    private static final String TAG = "EmailProviderResponseHa";

    public EmailProviderResponseHandler(Application application) {
        super(application);
    }

    public void startSignIn(final IdpResponse response, final String password) {
        if (!response.isSuccessful()) {
            setResult((Resource<IdpResponse>) Resource.forFailure(response.getError()));
        } else if (response.getProviderType().equals("password")) {
            setResult((Resource<IdpResponse>) Resource.forLoading());
            final AuthOperationManager authOperationManager = AuthOperationManager.getInstance();
            final String email = response.getEmail();
            authOperationManager.createOrLinkUserWithEmailAndPassword(getAuth(), (FlowParameters) getArguments(), email, password).continueWithTask(new ProfileMerger(response)).addOnFailureListener(new TaskFailureLogger(TAG, "Error creating user")).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                public void onSuccess(AuthResult result) {
                    EmailProviderResponseHandler.this.handleSuccess(response, result);
                }
            }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(Exception e) {
                    if (!(e instanceof FirebaseAuthUserCollisionException)) {
                        EmailProviderResponseHandler.this.setResult((Resource<IdpResponse>) Resource.forFailure(e));
                    } else if (authOperationManager.canUpgradeAnonymous(EmailProviderResponseHandler.this.getAuth(), (FlowParameters) EmailProviderResponseHandler.this.getArguments())) {
                        EmailProviderResponseHandler.this.handleMergeFailure(EmailAuthProvider.getCredential(email, password));
                    } else {
                        ProviderUtils.fetchTopProvider(EmailProviderResponseHandler.this.getAuth(), (FlowParameters) EmailProviderResponseHandler.this.getArguments(), email).addOnSuccessListener(new StartWelcomeBackFlow(email)).addOnFailureListener(new OnFailureListener() {
                            public void onFailure(Exception e) {
                                EmailProviderResponseHandler.this.setResult((Resource<IdpResponse>) Resource.forFailure(e));
                            }
                        });
                    }
                }
            });
        } else {
            throw new IllegalStateException("This handler can only be used with the email provider");
        }
    }

    /* renamed from: com.firebase.ui.auth.viewmodel.email.EmailProviderResponseHandler$StartWelcomeBackFlow */
    private class StartWelcomeBackFlow implements OnSuccessListener<String> {
        private final String mEmail;

        public StartWelcomeBackFlow(String email) {
            this.mEmail = email;
        }

        public void onSuccess(String provider) {
            if (provider == null) {
                throw new IllegalStateException("User has no providers even though we got a collision.");
            } else if ("password".equalsIgnoreCase(provider)) {
                EmailProviderResponseHandler.this.setResult((Resource<IdpResponse>) Resource.forFailure(new IntentRequiredException(WelcomeBackPasswordPrompt.createIntent(EmailProviderResponseHandler.this.getApplication(), (FlowParameters) EmailProviderResponseHandler.this.getArguments(), new IdpResponse.Builder(new User.Builder("password", this.mEmail).build()).build()), 104)));
            } else if ("emailLink".equalsIgnoreCase(provider)) {
                EmailProviderResponseHandler.this.setResult((Resource<IdpResponse>) Resource.forFailure(new IntentRequiredException(WelcomeBackEmailLinkPrompt.createIntent(EmailProviderResponseHandler.this.getApplication(), (FlowParameters) EmailProviderResponseHandler.this.getArguments(), new IdpResponse.Builder(new User.Builder("emailLink", this.mEmail).build()).build()), 112)));
            } else {
                EmailProviderResponseHandler.this.setResult((Resource<IdpResponse>) Resource.forFailure(new IntentRequiredException(WelcomeBackIdpPrompt.createIntent(EmailProviderResponseHandler.this.getApplication(), (FlowParameters) EmailProviderResponseHandler.this.getArguments(), new User.Builder(provider, this.mEmail).build()), 103)));
            }
        }
    }
}
