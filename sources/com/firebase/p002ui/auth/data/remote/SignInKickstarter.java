package com.firebase.p002ui.auth.data.remote;

import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;
import com.firebase.p002ui.auth.AuthUI;
import com.firebase.p002ui.auth.IdpResponse;
import com.firebase.p002ui.auth.data.model.FlowParameters;
import com.firebase.p002ui.auth.data.model.IntentRequiredException;
import com.firebase.p002ui.auth.data.model.PendingIntentRequiredException;
import com.firebase.p002ui.auth.data.model.Resource;
import com.firebase.p002ui.auth.data.model.User;
import com.firebase.p002ui.auth.data.model.UserCancellationException;
import com.firebase.p002ui.auth.p007ui.email.EmailLinkCatcherActivity;
import com.firebase.p002ui.auth.util.GoogleApiUtils;
import com.firebase.p002ui.auth.util.data.ProviderUtils;
import com.firebase.p002ui.auth.viewmodel.SignInViewModelBase;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialRequest;
import com.google.android.gms.auth.api.credentials.CredentialRequestResponse;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.firebase.ui.auth.data.remote.SignInKickstarter */
public class SignInKickstarter extends SignInViewModelBase {
    public SignInKickstarter(Application application) {
        super(application);
    }

    public void start() {
        if (!TextUtils.isEmpty(((FlowParameters) getArguments()).emailLink)) {
            setResult((Resource<IdpResponse>) Resource.forFailure(new IntentRequiredException(EmailLinkCatcherActivity.createIntent(getApplication(), (FlowParameters) getArguments()), 106)));
            return;
        }
        boolean willRequestCredentials = true;
        boolean supportPasswords = ProviderUtils.getConfigFromIdps(((FlowParameters) getArguments()).providers, "password") != null;
        List<String> accountTypes = getCredentialAccountTypes();
        if (!supportPasswords && accountTypes.size() <= 0) {
            willRequestCredentials = false;
        }
        if (!((FlowParameters) getArguments()).enableCredentials || !willRequestCredentials) {
            startAuthMethodChoice();
            return;
        }
        setResult((Resource<IdpResponse>) Resource.forLoading());
        GoogleApiUtils.getCredentialsClient(getApplication()).request(new CredentialRequest.Builder().setPasswordLoginSupported(supportPasswords).setAccountTypes((String[]) accountTypes.toArray(new String[accountTypes.size()])).build()).addOnCompleteListener(new OnCompleteListener<CredentialRequestResponse>() {
            public void onComplete(Task<CredentialRequestResponse> task) {
                try {
                    SignInKickstarter.this.handleCredential(task.getResult(ApiException.class).getCredential());
                } catch (ResolvableApiException e) {
                    if (e.getStatusCode() == 6) {
                        SignInKickstarter.this.setResult((Resource<IdpResponse>) Resource.forFailure(new PendingIntentRequiredException(e.getResolution(), 101)));
                    } else {
                        SignInKickstarter.this.startAuthMethodChoice();
                    }
                } catch (ApiException e2) {
                    SignInKickstarter.this.startAuthMethodChoice();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x003c, code lost:
        if (r2.equals("emailLink") == false) goto L_0x0053;
     */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0056 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void startAuthMethodChoice() {
        /*
            r8 = this;
            java.lang.Object r0 = r8.getArguments()
            com.firebase.ui.auth.data.model.FlowParameters r0 = (com.firebase.p002ui.auth.data.model.FlowParameters) r0
            boolean r0 = r0.shouldShowProviderChoice()
            if (r0 != 0) goto L_0x009e
            java.lang.Object r0 = r8.getArguments()
            com.firebase.ui.auth.data.model.FlowParameters r0 = (com.firebase.p002ui.auth.data.model.FlowParameters) r0
            java.util.List<com.firebase.ui.auth.AuthUI$IdpConfig> r0 = r0.providers
            r1 = 0
            java.lang.Object r0 = r0.get(r1)
            com.firebase.ui.auth.AuthUI$IdpConfig r0 = (com.firebase.p002ui.auth.AuthUI.IdpConfig) r0
            java.lang.String r2 = r0.getProviderId()
            r3 = -1
            int r4 = r2.hashCode()
            r5 = 106642798(0x65b3d6e, float:4.1234453E-35)
            r6 = 2
            r7 = 1
            if (r4 == r5) goto L_0x0049
            r5 = 1216985755(0x4889ba9b, float:282068.84)
            if (r4 == r5) goto L_0x003f
            r5 = 2120171958(0x7e5f41b6, float:7.418976E37)
            if (r4 == r5) goto L_0x0036
        L_0x0035:
            goto L_0x0053
        L_0x0036:
            java.lang.String r4 = "emailLink"
            boolean r4 = r2.equals(r4)
            if (r4 == 0) goto L_0x0035
            goto L_0x0054
        L_0x003f:
            java.lang.String r1 = "password"
            boolean r1 = r2.equals(r1)
            if (r1 == 0) goto L_0x0035
            r1 = 1
            goto L_0x0054
        L_0x0049:
            java.lang.String r1 = "phone"
            boolean r1 = r2.equals(r1)
            if (r1 == 0) goto L_0x0035
            r1 = 2
            goto L_0x0054
        L_0x0053:
            r1 = -1
        L_0x0054:
            if (r1 == 0) goto L_0x0080
            if (r1 == r7) goto L_0x0080
            if (r1 == r6) goto L_0x005f
            r1 = 0
            r8.redirectSignIn(r2, r1)
            goto L_0x009d
        L_0x005f:
            com.firebase.ui.auth.data.model.IntentRequiredException r1 = new com.firebase.ui.auth.data.model.IntentRequiredException
            android.app.Application r3 = r8.getApplication()
            java.lang.Object r4 = r8.getArguments()
            com.firebase.ui.auth.data.model.FlowParameters r4 = (com.firebase.p002ui.auth.data.model.FlowParameters) r4
            android.os.Bundle r5 = r0.getParams()
            android.content.Intent r3 = com.firebase.p002ui.auth.p007ui.phone.PhoneActivity.createIntent(r3, r4, r5)
            r4 = 107(0x6b, float:1.5E-43)
            r1.<init>(r3, r4)
            com.firebase.ui.auth.data.model.Resource r1 = com.firebase.p002ui.auth.data.model.Resource.forFailure(r1)
            r8.setResult((com.firebase.p002ui.auth.data.model.Resource<com.firebase.p002ui.auth.IdpResponse>) r1)
            goto L_0x009d
        L_0x0080:
            com.firebase.ui.auth.data.model.IntentRequiredException r1 = new com.firebase.ui.auth.data.model.IntentRequiredException
            android.app.Application r3 = r8.getApplication()
            java.lang.Object r4 = r8.getArguments()
            com.firebase.ui.auth.data.model.FlowParameters r4 = (com.firebase.p002ui.auth.data.model.FlowParameters) r4
            android.content.Intent r3 = com.firebase.p002ui.auth.p007ui.email.EmailActivity.createIntent(r3, r4)
            r4 = 106(0x6a, float:1.49E-43)
            r1.<init>(r3, r4)
            com.firebase.ui.auth.data.model.Resource r1 = com.firebase.p002ui.auth.data.model.Resource.forFailure(r1)
            r8.setResult((com.firebase.p002ui.auth.data.model.Resource<com.firebase.p002ui.auth.IdpResponse>) r1)
        L_0x009d:
            goto L_0x00ba
        L_0x009e:
            com.firebase.ui.auth.data.model.IntentRequiredException r0 = new com.firebase.ui.auth.data.model.IntentRequiredException
            android.app.Application r1 = r8.getApplication()
            java.lang.Object r2 = r8.getArguments()
            com.firebase.ui.auth.data.model.FlowParameters r2 = (com.firebase.p002ui.auth.data.model.FlowParameters) r2
            android.content.Intent r1 = com.firebase.p002ui.auth.p007ui.idp.AuthMethodPickerActivity.createIntent(r1, r2)
            r2 = 105(0x69, float:1.47E-43)
            r0.<init>(r1, r2)
            com.firebase.ui.auth.data.model.Resource r0 = com.firebase.p002ui.auth.data.model.Resource.forFailure(r0)
            r8.setResult((com.firebase.p002ui.auth.data.model.Resource<com.firebase.p002ui.auth.IdpResponse>) r0)
        L_0x00ba:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.firebase.p002ui.auth.data.remote.SignInKickstarter.startAuthMethodChoice():void");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void redirectSignIn(java.lang.String r7, java.lang.String r8) {
        /*
            r6 = this;
            int r0 = r7.hashCode()
            r1 = 5
            r2 = 4
            r3 = 3
            r4 = 2
            r5 = 1
            switch(r0) {
                case -1830313082: goto L_0x003f;
                case -1536293812: goto L_0x0035;
                case -364826023: goto L_0x002b;
                case 106642798: goto L_0x0021;
                case 1216985755: goto L_0x0017;
                case 1985010934: goto L_0x000d;
                default: goto L_0x000c;
            }
        L_0x000c:
            goto L_0x0049
        L_0x000d:
            java.lang.String r0 = "github.com"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x000c
            r0 = 5
            goto L_0x004a
        L_0x0017:
            java.lang.String r0 = "password"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x000c
            r0 = 0
            goto L_0x004a
        L_0x0021:
            java.lang.String r0 = "phone"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x000c
            r0 = 1
            goto L_0x004a
        L_0x002b:
            java.lang.String r0 = "facebook.com"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x000c
            r0 = 3
            goto L_0x004a
        L_0x0035:
            java.lang.String r0 = "google.com"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x000c
            r0 = 2
            goto L_0x004a
        L_0x003f:
            java.lang.String r0 = "twitter.com"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x000c
            r0 = 4
            goto L_0x004a
        L_0x0049:
            r0 = -1
        L_0x004a:
            if (r0 == 0) goto L_0x00a7
            if (r0 == r5) goto L_0x0080
            if (r0 == r4) goto L_0x005a
            if (r0 == r3) goto L_0x005a
            if (r0 == r2) goto L_0x005a
            if (r0 == r1) goto L_0x005a
            r6.startAuthMethodChoice()
            goto L_0x00c4
        L_0x005a:
            com.firebase.ui.auth.data.model.IntentRequiredException r0 = new com.firebase.ui.auth.data.model.IntentRequiredException
            android.app.Application r1 = r6.getApplication()
            java.lang.Object r2 = r6.getArguments()
            com.firebase.ui.auth.data.model.FlowParameters r2 = (com.firebase.p002ui.auth.data.model.FlowParameters) r2
            com.firebase.ui.auth.data.model.User$Builder r3 = new com.firebase.ui.auth.data.model.User$Builder
            r3.<init>(r7, r8)
            com.firebase.ui.auth.data.model.User r3 = r3.build()
            android.content.Intent r1 = com.firebase.p002ui.auth.p007ui.idp.SingleSignInActivity.createIntent(r1, r2, r3)
            r2 = 109(0x6d, float:1.53E-43)
            r0.<init>(r1, r2)
            com.firebase.ui.auth.data.model.Resource r0 = com.firebase.p002ui.auth.data.model.Resource.forFailure(r0)
            r6.setResult((com.firebase.p002ui.auth.data.model.Resource<com.firebase.p002ui.auth.IdpResponse>) r0)
            goto L_0x00c4
        L_0x0080:
            android.os.Bundle r0 = new android.os.Bundle
            r0.<init>()
            java.lang.String r1 = "extra_phone_number"
            r0.putString(r1, r8)
            com.firebase.ui.auth.data.model.IntentRequiredException r1 = new com.firebase.ui.auth.data.model.IntentRequiredException
            android.app.Application r2 = r6.getApplication()
            java.lang.Object r3 = r6.getArguments()
            com.firebase.ui.auth.data.model.FlowParameters r3 = (com.firebase.p002ui.auth.data.model.FlowParameters) r3
            android.content.Intent r2 = com.firebase.p002ui.auth.p007ui.phone.PhoneActivity.createIntent(r2, r3, r0)
            r3 = 107(0x6b, float:1.5E-43)
            r1.<init>(r2, r3)
            com.firebase.ui.auth.data.model.Resource r1 = com.firebase.p002ui.auth.data.model.Resource.forFailure(r1)
            r6.setResult((com.firebase.p002ui.auth.data.model.Resource<com.firebase.p002ui.auth.IdpResponse>) r1)
            goto L_0x00c4
        L_0x00a7:
            com.firebase.ui.auth.data.model.IntentRequiredException r0 = new com.firebase.ui.auth.data.model.IntentRequiredException
            android.app.Application r1 = r6.getApplication()
            java.lang.Object r2 = r6.getArguments()
            com.firebase.ui.auth.data.model.FlowParameters r2 = (com.firebase.p002ui.auth.data.model.FlowParameters) r2
            android.content.Intent r1 = com.firebase.p002ui.auth.p007ui.email.EmailActivity.createIntent(r1, r2, r8)
            r2 = 106(0x6a, float:1.49E-43)
            r0.<init>(r1, r2)
            com.firebase.ui.auth.data.model.Resource r0 = com.firebase.p002ui.auth.data.model.Resource.forFailure(r0)
            r6.setResult((com.firebase.p002ui.auth.data.model.Resource<com.firebase.p002ui.auth.IdpResponse>) r0)
        L_0x00c4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.firebase.p002ui.auth.data.remote.SignInKickstarter.redirectSignIn(java.lang.String, java.lang.String):void");
    }

    private List<String> getCredentialAccountTypes() {
        List<String> accounts = new ArrayList<>();
        for (AuthUI.IdpConfig idpConfig : ((FlowParameters) getArguments()).providers) {
            String providerId = idpConfig.getProviderId();
            if (providerId.equals("google.com")) {
                accounts.add(ProviderUtils.providerIdToAccountType(providerId));
            }
        }
        return accounts;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != 101) {
            if (requestCode != 109) {
                switch (requestCode) {
                    case 105:
                    case 106:
                    case 107:
                        break;
                    default:
                        return;
                }
            }
            if (resultCode == 113 || resultCode == 114) {
                startAuthMethodChoice();
                return;
            }
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (response == null) {
                setResult((Resource<IdpResponse>) Resource.forFailure(new UserCancellationException()));
            } else if (response.isSuccessful()) {
                setResult((Resource<IdpResponse>) Resource.forSuccess(response));
            } else if (response.getError().getErrorCode() == 5) {
                handleMergeFailure(response);
            } else {
                setResult((Resource<IdpResponse>) Resource.forFailure(response.getError()));
            }
        } else if (resultCode == -1) {
            handleCredential((Credential) data.getParcelableExtra(Credential.EXTRA_KEY));
        } else {
            startAuthMethodChoice();
        }
    }

    /* access modifiers changed from: private */
    public void handleCredential(final Credential credential) {
        String id = credential.getId();
        String password = credential.getPassword();
        if (!TextUtils.isEmpty(password)) {
            final IdpResponse response = new IdpResponse.Builder(new User.Builder("password", id).build()).build();
            setResult((Resource<IdpResponse>) Resource.forLoading());
            getAuth().signInWithEmailAndPassword(id, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                public void onSuccess(AuthResult result) {
                    SignInKickstarter.this.handleSuccess(response, result);
                }
            }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(Exception e) {
                    if ((e instanceof FirebaseAuthInvalidUserException) || (e instanceof FirebaseAuthInvalidCredentialsException)) {
                        GoogleApiUtils.getCredentialsClient(SignInKickstarter.this.getApplication()).delete(credential);
                    }
                    SignInKickstarter.this.startAuthMethodChoice();
                }
            });
        } else if (credential.getAccountType() == null) {
            startAuthMethodChoice();
        } else {
            redirectSignIn(ProviderUtils.accountTypeToProviderId(credential.getAccountType()), id);
        }
    }
}
