package com.firebase.p002ui.auth.p007ui.idp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.firebase.p002ui.auth.AuthUI;
import com.firebase.p002ui.auth.C0719R;
import com.firebase.p002ui.auth.FirebaseAuthAnonymousUpgradeException;
import com.firebase.p002ui.auth.FirebaseUiException;
import com.firebase.p002ui.auth.IdpResponse;
import com.firebase.p002ui.auth.data.model.FlowParameters;
import com.firebase.p002ui.auth.data.model.User;
import com.firebase.p002ui.auth.data.remote.FacebookSignInHandler;
import com.firebase.p002ui.auth.data.remote.GitHubSignInHandlerBridge;
import com.firebase.p002ui.auth.data.remote.GoogleSignInHandler;
import com.firebase.p002ui.auth.data.remote.TwitterSignInHandler;
import com.firebase.p002ui.auth.p007ui.AppCompatBase;
import com.firebase.p002ui.auth.util.ExtraConstants;
import com.firebase.p002ui.auth.util.data.PrivacyDisclosureUtils;
import com.firebase.p002ui.auth.util.data.ProviderUtils;
import com.firebase.p002ui.auth.viewmodel.ProviderSignInBase;
import com.firebase.p002ui.auth.viewmodel.ResourceObserver;
import com.firebase.p002ui.auth.viewmodel.idp.LinkingSocialProviderResponseHandler;

/* renamed from: com.firebase.ui.auth.ui.idp.WelcomeBackIdpPrompt */
public class WelcomeBackIdpPrompt extends AppCompatBase {
    private Button mDoneButton;
    private ProgressBar mProgressBar;
    /* access modifiers changed from: private */
    public ProviderSignInBase<?> mProvider;

    public static Intent createIntent(Context context, FlowParameters flowParams, User existingUser) {
        return createIntent(context, flowParams, existingUser, (IdpResponse) null);
    }

    public static Intent createIntent(Context context, FlowParameters flowParams, User existingUser, IdpResponse requestedUserResponse) {
        return createBaseIntent(context, WelcomeBackIdpPrompt.class, flowParams).putExtra(ExtraConstants.IDP_RESPONSE, requestedUserResponse).putExtra(ExtraConstants.USER, existingUser);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        int providerName;
        super.onCreate(savedInstanceState);
        setContentView(C0719R.layout.fui_welcome_back_idp_prompt_layout);
        this.mDoneButton = (Button) findViewById(C0719R.C0722id.welcome_back_idp_button);
        this.mProgressBar = (ProgressBar) findViewById(C0719R.C0722id.top_progress_bar);
        User existingUser = User.getUser(getIntent());
        IdpResponse requestedUserResponse = IdpResponse.fromResultIntent(getIntent());
        ViewModelProvider supplier = ViewModelProviders.m16of((FragmentActivity) this);
        final LinkingSocialProviderResponseHandler handler = (LinkingSocialProviderResponseHandler) supplier.get(LinkingSocialProviderResponseHandler.class);
        handler.init(getFlowParams());
        if (requestedUserResponse != null) {
            handler.setRequestedSignInCredentialForEmail(ProviderUtils.getAuthCredential(requestedUserResponse), existingUser.getEmail());
        }
        String providerId = existingUser.getProviderId();
        AuthUI.IdpConfig config = ProviderUtils.getConfigFromIdps(getFlowParams().providers, providerId);
        if (config == null) {
            finish(0, IdpResponse.getErrorIntent(new FirebaseUiException(3, "Firebase login unsuccessful. Account linking failed due to provider not enabled by application: " + providerId)));
            return;
        }
        char c = 65535;
        switch (providerId.hashCode()) {
            case -1830313082:
                if (providerId.equals("twitter.com")) {
                    c = 2;
                    break;
                }
                break;
            case -1536293812:
                if (providerId.equals("google.com")) {
                    c = 0;
                    break;
                }
                break;
            case -364826023:
                if (providerId.equals("facebook.com")) {
                    c = 1;
                    break;
                }
                break;
            case 1985010934:
                if (providerId.equals("github.com")) {
                    c = 3;
                    break;
                }
                break;
        }
        if (c == 0) {
            GoogleSignInHandler google = (GoogleSignInHandler) supplier.get(GoogleSignInHandler.class);
            google.init(new GoogleSignInHandler.Params(config, existingUser.getEmail()));
            this.mProvider = google;
            providerName = C0719R.string.fui_idp_name_google;
        } else if (c == 1) {
            FacebookSignInHandler facebook = (FacebookSignInHandler) supplier.get(FacebookSignInHandler.class);
            facebook.init(config);
            this.mProvider = facebook;
            providerName = C0719R.string.fui_idp_name_facebook;
        } else if (c == 2) {
            TwitterSignInHandler twitter = (TwitterSignInHandler) supplier.get(TwitterSignInHandler.class);
            twitter.init(null);
            this.mProvider = twitter;
            providerName = C0719R.string.fui_idp_name_twitter;
        } else if (c == 3) {
            ProviderSignInBase<AuthUI.IdpConfig> github = (ProviderSignInBase) supplier.get(GitHubSignInHandlerBridge.HANDLER_CLASS);
            github.init(config);
            this.mProvider = github;
            providerName = C0719R.string.fui_idp_name_github;
        } else {
            throw new IllegalStateException("Invalid provider id: " + providerId);
        }
        this.mProvider.getOperation().observe(this, new ResourceObserver<IdpResponse>(this) {
            /* access modifiers changed from: protected */
            public void onSuccess(IdpResponse response) {
                handler.startSignIn(response);
            }

            /* access modifiers changed from: protected */
            public void onFailure(Exception e) {
                handler.startSignIn(IdpResponse.from(e));
            }
        });
        ((TextView) findViewById(C0719R.C0722id.welcome_back_idp_prompt)).setText(getString(C0719R.string.fui_welcome_back_idp_prompt, new Object[]{existingUser.getEmail(), getString(providerName)}));
        this.mDoneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                WelcomeBackIdpPrompt.this.mProvider.startSignIn(WelcomeBackIdpPrompt.this);
            }
        });
        handler.getOperation().observe(this, new ResourceObserver<IdpResponse>(this) {
            /* access modifiers changed from: protected */
            public void onSuccess(IdpResponse response) {
                WelcomeBackIdpPrompt.this.finish(-1, response.toIntent());
            }

            /* access modifiers changed from: protected */
            public void onFailure(Exception e) {
                if (e instanceof FirebaseAuthAnonymousUpgradeException) {
                    WelcomeBackIdpPrompt.this.finish(5, ((FirebaseAuthAnonymousUpgradeException) e).getResponse().toIntent());
                    return;
                }
                WelcomeBackIdpPrompt.this.finish(0, IdpResponse.getErrorIntent(e));
            }
        });
        PrivacyDisclosureUtils.setupTermsOfServiceFooter(this, getFlowParams(), (TextView) findViewById(C0719R.C0722id.email_footer_tos_and_pp_text));
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.mProvider.onActivityResult(requestCode, resultCode, data);
    }

    public void showProgress(int message) {
        this.mDoneButton.setEnabled(false);
        this.mProgressBar.setVisibility(0);
    }

    public void hideProgress() {
        this.mDoneButton.setEnabled(true);
        this.mProgressBar.setVisibility(4);
    }
}
