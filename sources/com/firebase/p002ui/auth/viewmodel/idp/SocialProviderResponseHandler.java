package com.firebase.p002ui.auth.viewmodel.idp;

import android.app.Application;
import android.content.Intent;
import com.firebase.p002ui.auth.AuthUI;
import com.firebase.p002ui.auth.FirebaseUiException;
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
import com.firebase.p002ui.auth.viewmodel.SignInViewModelBase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import java.util.List;

/* renamed from: com.firebase.ui.auth.viewmodel.idp.SocialProviderResponseHandler */
public class SocialProviderResponseHandler extends SignInViewModelBase {
    public SocialProviderResponseHandler(Application application) {
        super(application);
    }

    public void startSignIn(final IdpResponse response) {
        if (!response.isSuccessful()) {
            setResult((Resource<IdpResponse>) Resource.forFailure(response.getError()));
        } else if (AuthUI.SOCIAL_PROVIDERS.contains(response.getProviderType())) {
            setResult((Resource<IdpResponse>) Resource.forLoading());
            final AuthCredential credential = ProviderUtils.getAuthCredential(response);
            AuthOperationManager.getInstance().signInAndLinkWithCredential(getAuth(), (FlowParameters) getArguments(), credential).continueWithTask(new ProfileMerger(response)).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                public void onSuccess(AuthResult result) {
                    SocialProviderResponseHandler.this.handleSuccess(response, result);
                }
            }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(Exception e) {
                    if (e instanceof FirebaseAuthUserCollisionException) {
                        String email = response.getEmail();
                        if (email == null) {
                            SocialProviderResponseHandler.this.setResult((Resource<IdpResponse>) Resource.forFailure(e));
                        } else {
                            ProviderUtils.fetchSortedProviders(SocialProviderResponseHandler.this.getAuth(), (FlowParameters) SocialProviderResponseHandler.this.getArguments(), email).addOnSuccessListener(new OnSuccessListener<List<String>>() {
                                public void onSuccess(List<String> providers) {
                                    if (providers.contains(response.getProviderType())) {
                                        SocialProviderResponseHandler.this.handleMergeFailure(credential);
                                    } else if (providers.isEmpty()) {
                                        SocialProviderResponseHandler.this.setResult((Resource<IdpResponse>) Resource.forFailure(new FirebaseUiException(3, "No supported providers.")));
                                    } else {
                                        SocialProviderResponseHandler.this.startWelcomeBackFlowForLinking(providers.get(0), response);
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                public void onFailure(Exception e) {
                                    SocialProviderResponseHandler.this.setResult((Resource<IdpResponse>) Resource.forFailure(e));
                                }
                            });
                        }
                    }
                }
            });
        } else {
            throw new IllegalStateException("This handler cannot be used with email or phone providers");
        }
    }

    public void startWelcomeBackFlowForLinking(String provider, IdpResponse response) {
        if (provider == null) {
            throw new IllegalStateException("No provider even though we received a FirebaseAuthUserCollisionException");
        } else if (provider.equals("password")) {
            setResult((Resource<IdpResponse>) Resource.forFailure(new IntentRequiredException(WelcomeBackPasswordPrompt.createIntent(getApplication(), (FlowParameters) getArguments(), response), 108)));
        } else if (provider.equals("emailLink")) {
            setResult((Resource<IdpResponse>) Resource.forFailure(new IntentRequiredException(WelcomeBackEmailLinkPrompt.createIntent(getApplication(), (FlowParameters) getArguments(), response), 112)));
        } else {
            setResult((Resource<IdpResponse>) Resource.forFailure(new IntentRequiredException(WelcomeBackIdpPrompt.createIntent(getApplication(), (FlowParameters) getArguments(), new User.Builder(provider, response.getEmail()).build(), response), 108)));
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Exception e;
        if (requestCode == 108) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == -1) {
                setResult((Resource<IdpResponse>) Resource.forSuccess(response));
                return;
            }
            if (response == null) {
                e = new FirebaseUiException(0, "Link canceled by user.");
            } else {
                e = response.getError();
            }
            setResult((Resource<IdpResponse>) Resource.forFailure(e));
        }
    }
}
