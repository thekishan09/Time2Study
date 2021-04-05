package com.firebase.p002ui.auth.p007ui.idp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.firebase.p002ui.auth.AuthMethodPickerLayout;
import com.firebase.p002ui.auth.AuthUI;
import com.firebase.p002ui.auth.C0719R;
import com.firebase.p002ui.auth.FirebaseAuthAnonymousUpgradeException;
import com.firebase.p002ui.auth.FirebaseUiException;
import com.firebase.p002ui.auth.IdpResponse;
import com.firebase.p002ui.auth.data.model.FlowParameters;
import com.firebase.p002ui.auth.data.model.UserCancellationException;
import com.firebase.p002ui.auth.p007ui.AppCompatBase;
import com.firebase.p002ui.auth.util.data.PrivacyDisclosureUtils;
import com.firebase.p002ui.auth.viewmodel.ProviderSignInBase;
import com.firebase.p002ui.auth.viewmodel.ResourceObserver;
import com.firebase.p002ui.auth.viewmodel.idp.SocialProviderResponseHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* renamed from: com.firebase.ui.auth.ui.idp.AuthMethodPickerActivity */
public class AuthMethodPickerActivity extends AppCompatBase {
    private AuthMethodPickerLayout customLayout;
    /* access modifiers changed from: private */
    public SocialProviderResponseHandler mHandler;
    private ProgressBar mProgressBar;
    private ViewGroup mProviderHolder;
    private List<ProviderSignInBase<?>> mProviders;

    public static Intent createIntent(Context context, FlowParameters flowParams) {
        return createBaseIntent(context, AuthMethodPickerActivity.class, flowParams);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        int termsTextId;
        super.onCreate(savedInstanceState);
        FlowParameters params = getFlowParams();
        this.customLayout = params.authMethodPickerLayout;
        SocialProviderResponseHandler socialProviderResponseHandler = (SocialProviderResponseHandler) ViewModelProviders.m16of((FragmentActivity) this).get(SocialProviderResponseHandler.class);
        this.mHandler = socialProviderResponseHandler;
        socialProviderResponseHandler.init(params);
        this.mProviders = new ArrayList();
        AuthMethodPickerLayout authMethodPickerLayout = this.customLayout;
        if (authMethodPickerLayout != null) {
            setContentView(authMethodPickerLayout.getMainLayout());
            populateIdpListCustomLayout(params.providers);
        } else {
            setContentView(C0719R.layout.fui_auth_method_picker_layout);
            this.mProgressBar = (ProgressBar) findViewById(C0719R.C0722id.top_progress_bar);
            this.mProviderHolder = (ViewGroup) findViewById(C0719R.C0722id.btn_holder);
            populateIdpList(params.providers);
            int logoId = params.logoId;
            if (logoId == -1) {
                findViewById(C0719R.C0722id.logo).setVisibility(8);
                ConstraintLayout layout = (ConstraintLayout) findViewById(C0719R.C0722id.root);
                ConstraintSet constraints = new ConstraintSet();
                constraints.clone(layout);
                constraints.setHorizontalBias(C0719R.C0722id.container, 0.5f);
                constraints.setVerticalBias(C0719R.C0722id.container, 0.5f);
                constraints.applyTo(layout);
            } else {
                ((ImageView) findViewById(C0719R.C0722id.logo)).setImageResource(logoId);
            }
        }
        boolean tosAndPpConfigured = getFlowParams().isPrivacyPolicyUrlProvided() && getFlowParams().isTermsOfServiceUrlProvided();
        AuthMethodPickerLayout authMethodPickerLayout2 = this.customLayout;
        if (authMethodPickerLayout2 == null) {
            termsTextId = C0719R.C0722id.main_tos_and_pp;
        } else {
            termsTextId = authMethodPickerLayout2.getTosPpView();
        }
        if (termsTextId >= 0) {
            TextView termsText = (TextView) findViewById(termsTextId);
            if (!tosAndPpConfigured) {
                termsText.setVisibility(8);
            } else {
                PrivacyDisclosureUtils.setupTermsOfServiceAndPrivacyPolicyText(this, getFlowParams(), termsText);
            }
        }
        this.mHandler.getOperation().observe(this, new ResourceObserver<IdpResponse>(this, C0719R.string.fui_progress_dialog_signing_in) {
            /* access modifiers changed from: protected */
            public void onSuccess(IdpResponse response) {
                AuthMethodPickerActivity authMethodPickerActivity = AuthMethodPickerActivity.this;
                authMethodPickerActivity.startSaveCredentials(authMethodPickerActivity.mHandler.getCurrentUser(), response, (String) null);
            }

            /* access modifiers changed from: protected */
            public void onFailure(Exception e) {
                String text;
                if (e instanceof FirebaseAuthAnonymousUpgradeException) {
                    AuthMethodPickerActivity.this.finish(5, ((FirebaseAuthAnonymousUpgradeException) e).getResponse().toIntent());
                } else if (!(e instanceof UserCancellationException)) {
                    if (e instanceof FirebaseUiException) {
                        text = e.getMessage();
                    } else {
                        text = AuthMethodPickerActivity.this.getString(C0719R.string.fui_error_unknown);
                    }
                    Toast.makeText(AuthMethodPickerActivity.this, text, 0).show();
                }
            }
        });
    }

    private void populateIdpList(List<AuthUI.IdpConfig> providerConfigs) {
        int buttonLayout;
        ViewModelProvider of = ViewModelProviders.m16of((FragmentActivity) this);
        this.mProviders = new ArrayList();
        for (AuthUI.IdpConfig idpConfig : providerConfigs) {
            String providerId = idpConfig.getProviderId();
            char c = 65535;
            switch (providerId.hashCode()) {
                case -2095811475:
                    if (providerId.equals(AuthUI.ANONYMOUS_PROVIDER)) {
                        c = 7;
                        break;
                    }
                    break;
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
                case 106642798:
                    if (providerId.equals("phone")) {
                        c = 6;
                        break;
                    }
                    break;
                case 1216985755:
                    if (providerId.equals("password")) {
                        c = 5;
                        break;
                    }
                    break;
                case 1985010934:
                    if (providerId.equals("github.com")) {
                        c = 3;
                        break;
                    }
                    break;
                case 2120171958:
                    if (providerId.equals("emailLink")) {
                        c = 4;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    buttonLayout = C0719R.layout.fui_idp_button_google;
                    break;
                case 1:
                    buttonLayout = C0719R.layout.fui_idp_button_facebook;
                    break;
                case 2:
                    buttonLayout = C0719R.layout.fui_idp_button_twitter;
                    break;
                case 3:
                    buttonLayout = C0719R.layout.fui_idp_button_github;
                    break;
                case 4:
                case 5:
                    buttonLayout = C0719R.layout.fui_provider_button_email;
                    break;
                case 6:
                    buttonLayout = C0719R.layout.fui_provider_button_phone;
                    break;
                case 7:
                    buttonLayout = C0719R.layout.fui_provider_button_anonymous;
                    break;
                default:
                    throw new IllegalStateException("Unknown provider: " + providerId);
            }
            View loginButton = getLayoutInflater().inflate(buttonLayout, this.mProviderHolder, false);
            handleSignInOperation(idpConfig, loginButton);
            this.mProviderHolder.addView(loginButton);
        }
    }

    private void populateIdpListCustomLayout(List<AuthUI.IdpConfig> providerConfigs) {
        Map<String, Integer> providerButtonIds = this.customLayout.getProvidersButton();
        for (AuthUI.IdpConfig idpConfig : providerConfigs) {
            String providerId = idpConfig.getProviderId();
            if (providerButtonIds.containsKey(providerId)) {
                handleSignInOperation(idpConfig, findViewById(providerButtonIds.get(providerId).intValue()));
            } else {
                throw new IllegalStateException("No button found for auth provider: " + providerId);
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: com.firebase.ui.auth.data.remote.GoogleSignInHandler} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: com.firebase.ui.auth.data.remote.GoogleSignInHandler} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v10, resolved type: com.firebase.ui.auth.data.remote.FacebookSignInHandler} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: com.firebase.ui.auth.data.remote.GoogleSignInHandler} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v13, resolved type: com.firebase.ui.auth.data.remote.TwitterSignInHandler} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: com.firebase.ui.auth.data.remote.GoogleSignInHandler} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v7, resolved type: com.firebase.ui.auth.data.remote.EmailSignInHandler} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v8, resolved type: com.firebase.ui.auth.data.remote.PhoneSignInHandler} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v10, resolved type: com.firebase.ui.auth.data.remote.AnonymousSignInHandler} */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void handleSignInOperation(com.firebase.p002ui.auth.AuthUI.IdpConfig r6, android.view.View r7) {
        /*
            r5 = this;
            androidx.lifecycle.ViewModelProvider r0 = androidx.lifecycle.ViewModelProviders.m16of((androidx.fragment.app.FragmentActivity) r5)
            java.lang.String r1 = r6.getProviderId()
            int r2 = r1.hashCode()
            switch(r2) {
                case -2095811475: goto L_0x0056;
                case -1830313082: goto L_0x004c;
                case -1536293812: goto L_0x0042;
                case -364826023: goto L_0x0038;
                case 106642798: goto L_0x002e;
                case 1216985755: goto L_0x0024;
                case 1985010934: goto L_0x001a;
                case 2120171958: goto L_0x0010;
                default: goto L_0x000f;
            }
        L_0x000f:
            goto L_0x0060
        L_0x0010:
            java.lang.String r2 = "emailLink"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x000f
            r2 = 4
            goto L_0x0061
        L_0x001a:
            java.lang.String r2 = "github.com"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x000f
            r2 = 3
            goto L_0x0061
        L_0x0024:
            java.lang.String r2 = "password"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x000f
            r2 = 5
            goto L_0x0061
        L_0x002e:
            java.lang.String r2 = "phone"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x000f
            r2 = 6
            goto L_0x0061
        L_0x0038:
            java.lang.String r2 = "facebook.com"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x000f
            r2 = 1
            goto L_0x0061
        L_0x0042:
            java.lang.String r2 = "google.com"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x000f
            r2 = 0
            goto L_0x0061
        L_0x004c:
            java.lang.String r2 = "twitter.com"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x000f
            r2 = 2
            goto L_0x0061
        L_0x0056:
            java.lang.String r2 = "anonymous"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x000f
            r2 = 7
            goto L_0x0061
        L_0x0060:
            r2 = -1
        L_0x0061:
            r3 = 0
            switch(r2) {
                case 0: goto L_0x00ce;
                case 1: goto L_0x00c1;
                case 2: goto L_0x00b4;
                case 3: goto L_0x00a7;
                case 4: goto L_0x009a;
                case 5: goto L_0x009a;
                case 6: goto L_0x008d;
                case 7: goto L_0x007c;
                default: goto L_0x0065;
            }
        L_0x0065:
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Unknown provider: "
            r3.append(r4)
            r3.append(r1)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        L_0x007c:
            java.lang.Class<com.firebase.ui.auth.data.remote.AnonymousSignInHandler> r2 = com.firebase.p002ui.auth.data.remote.AnonymousSignInHandler.class
            androidx.lifecycle.ViewModel r2 = r0.get(r2)
            com.firebase.ui.auth.data.remote.AnonymousSignInHandler r2 = (com.firebase.p002ui.auth.data.remote.AnonymousSignInHandler) r2
            com.firebase.ui.auth.data.model.FlowParameters r3 = r5.getFlowParams()
            r2.init(r3)
            r3 = r2
            goto L_0x00e0
        L_0x008d:
            java.lang.Class<com.firebase.ui.auth.data.remote.PhoneSignInHandler> r2 = com.firebase.p002ui.auth.data.remote.PhoneSignInHandler.class
            androidx.lifecycle.ViewModel r2 = r0.get(r2)
            com.firebase.ui.auth.data.remote.PhoneSignInHandler r2 = (com.firebase.p002ui.auth.data.remote.PhoneSignInHandler) r2
            r2.init(r6)
            r3 = r2
            goto L_0x00e0
        L_0x009a:
            java.lang.Class<com.firebase.ui.auth.data.remote.EmailSignInHandler> r2 = com.firebase.p002ui.auth.data.remote.EmailSignInHandler.class
            androidx.lifecycle.ViewModel r2 = r0.get(r2)
            com.firebase.ui.auth.data.remote.EmailSignInHandler r2 = (com.firebase.p002ui.auth.data.remote.EmailSignInHandler) r2
            r2.init(r3)
            r3 = r2
            goto L_0x00e0
        L_0x00a7:
            java.lang.Class<com.firebase.ui.auth.viewmodel.ProviderSignInBase<com.firebase.ui.auth.AuthUI$IdpConfig>> r2 = com.firebase.p002ui.auth.data.remote.GitHubSignInHandlerBridge.HANDLER_CLASS
            androidx.lifecycle.ViewModel r2 = r0.get(r2)
            com.firebase.ui.auth.viewmodel.ProviderSignInBase r2 = (com.firebase.p002ui.auth.viewmodel.ProviderSignInBase) r2
            r2.init(r6)
            r3 = r2
            goto L_0x00e0
        L_0x00b4:
            java.lang.Class<com.firebase.ui.auth.data.remote.TwitterSignInHandler> r2 = com.firebase.p002ui.auth.data.remote.TwitterSignInHandler.class
            androidx.lifecycle.ViewModel r2 = r0.get(r2)
            com.firebase.ui.auth.data.remote.TwitterSignInHandler r2 = (com.firebase.p002ui.auth.data.remote.TwitterSignInHandler) r2
            r2.init(r3)
            r3 = r2
            goto L_0x00e0
        L_0x00c1:
            java.lang.Class<com.firebase.ui.auth.data.remote.FacebookSignInHandler> r2 = com.firebase.p002ui.auth.data.remote.FacebookSignInHandler.class
            androidx.lifecycle.ViewModel r2 = r0.get(r2)
            com.firebase.ui.auth.data.remote.FacebookSignInHandler r2 = (com.firebase.p002ui.auth.data.remote.FacebookSignInHandler) r2
            r2.init(r6)
            r3 = r2
            goto L_0x00e0
        L_0x00ce:
            java.lang.Class<com.firebase.ui.auth.data.remote.GoogleSignInHandler> r2 = com.firebase.p002ui.auth.data.remote.GoogleSignInHandler.class
            androidx.lifecycle.ViewModel r2 = r0.get(r2)
            com.firebase.ui.auth.data.remote.GoogleSignInHandler r2 = (com.firebase.p002ui.auth.data.remote.GoogleSignInHandler) r2
            com.firebase.ui.auth.data.remote.GoogleSignInHandler$Params r3 = new com.firebase.ui.auth.data.remote.GoogleSignInHandler$Params
            r3.<init>(r6)
            r2.init(r3)
            r3 = r2
        L_0x00e0:
            java.util.List<com.firebase.ui.auth.viewmodel.ProviderSignInBase<?>> r2 = r5.mProviders
            r2.add(r3)
            androidx.lifecycle.LiveData r2 = r3.getOperation()
            com.firebase.ui.auth.ui.idp.AuthMethodPickerActivity$2 r4 = new com.firebase.ui.auth.ui.idp.AuthMethodPickerActivity$2
            r4.<init>(r5, r1)
            r2.observe(r5, r4)
            com.firebase.ui.auth.ui.idp.AuthMethodPickerActivity$3 r2 = new com.firebase.ui.auth.ui.idp.AuthMethodPickerActivity$3
            r2.<init>(r3)
            r7.setOnClickListener(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.firebase.p002ui.auth.p007ui.idp.AuthMethodPickerActivity.handleSignInOperation(com.firebase.ui.auth.AuthUI$IdpConfig, android.view.View):void");
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.mHandler.onActivityResult(requestCode, resultCode, data);
        for (ProviderSignInBase<?> provider : this.mProviders) {
            provider.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void showProgress(int message) {
        if (this.customLayout == null) {
            this.mProgressBar.setVisibility(0);
            for (int i = 0; i < this.mProviderHolder.getChildCount(); i++) {
                View child = this.mProviderHolder.getChildAt(i);
                child.setEnabled(false);
                child.setAlpha(0.75f);
            }
        }
    }

    public void hideProgress() {
        if (this.customLayout == null) {
            this.mProgressBar.setVisibility(4);
            for (int i = 0; i < this.mProviderHolder.getChildCount(); i++) {
                View child = this.mProviderHolder.getChildAt(i);
                child.setEnabled(true);
                child.setAlpha(1.0f);
            }
        }
    }
}
