package com.firebase.p002ui.auth.viewmodel.email;

import android.app.Application;
import android.text.TextUtils;
import com.firebase.p002ui.auth.FirebaseUiException;
import com.firebase.p002ui.auth.IdpResponse;
import com.firebase.p002ui.auth.data.model.FlowParameters;
import com.firebase.p002ui.auth.data.model.Resource;
import com.firebase.p002ui.auth.data.model.User;
import com.firebase.p002ui.auth.data.remote.ProfileMerger;
import com.firebase.p002ui.auth.util.data.AuthOperationManager;
import com.firebase.p002ui.auth.util.data.EmailLinkParser;
import com.firebase.p002ui.auth.util.data.EmailLinkPersistenceManager;
import com.firebase.p002ui.auth.util.data.ProviderUtils;
import com.firebase.p002ui.auth.util.data.TaskFailureLogger;
import com.firebase.p002ui.auth.viewmodel.SignInViewModelBase;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeResult;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

/* renamed from: com.firebase.ui.auth.viewmodel.email.EmailLinkSignInHandler */
public class EmailLinkSignInHandler extends SignInViewModelBase {
    private static final String TAG = "EmailLinkSignInHandler";

    public EmailLinkSignInHandler(Application application) {
        super(application);
    }

    public void startSignIn() {
        setResult((Resource<IdpResponse>) Resource.forLoading());
        String link = ((FlowParameters) getArguments()).emailLink;
        if (!getAuth().isSignInWithEmailLink(link)) {
            setResult((Resource<IdpResponse>) Resource.forFailure(new FirebaseUiException(7)));
            return;
        }
        EmailLinkPersistenceManager.SessionRecord sessionRecord = EmailLinkPersistenceManager.getInstance().retrieveSessionRecord(getApplication());
        EmailLinkParser parser = new EmailLinkParser(link);
        String sessionIdFromLink = parser.getSessionId();
        String anonymousUserIdFromLink = parser.getAnonymousUserId();
        String oobCodeFromLink = parser.getOobCode();
        String providerIdFromLink = parser.getProviderId();
        boolean forceSameDevice = parser.getForceSameDeviceBit();
        if (isDifferentDeviceFlow(sessionRecord, sessionIdFromLink)) {
            if (TextUtils.isEmpty(sessionIdFromLink)) {
                setResult((Resource<IdpResponse>) Resource.forFailure(new FirebaseUiException(7)));
            } else if (forceSameDevice || !TextUtils.isEmpty(anonymousUserIdFromLink)) {
                setResult((Resource<IdpResponse>) Resource.forFailure(new FirebaseUiException(8)));
            } else {
                determineDifferentDeviceErrorFlowAndFinish(oobCodeFromLink, providerIdFromLink);
            }
        } else if (anonymousUserIdFromLink == null || (getAuth().getCurrentUser() != null && (!getAuth().getCurrentUser().isAnonymous() || anonymousUserIdFromLink.equals(getAuth().getCurrentUser().getUid())))) {
            finishSignIn(sessionRecord);
        } else {
            setResult((Resource<IdpResponse>) Resource.forFailure(new FirebaseUiException(11)));
        }
    }

    public void finishSignIn(String email) {
        setResult((Resource<IdpResponse>) Resource.forLoading());
        finishSignIn(email, (IdpResponse) null);
    }

    private void finishSignIn(EmailLinkPersistenceManager.SessionRecord sessionRecord) {
        finishSignIn(sessionRecord.getEmail(), sessionRecord.getIdpResponseForLinking());
    }

    private void finishSignIn(String email, IdpResponse response) {
        if (TextUtils.isEmpty(email)) {
            setResult((Resource<IdpResponse>) Resource.forFailure(new FirebaseUiException(6)));
            return;
        }
        AuthOperationManager authOperationManager = AuthOperationManager.getInstance();
        EmailLinkPersistenceManager persistenceManager = EmailLinkPersistenceManager.getInstance();
        String link = ((FlowParameters) getArguments()).emailLink;
        if (response == null) {
            handleNormalFlow(authOperationManager, persistenceManager, email, link);
        } else {
            handleLinkingFlow(authOperationManager, persistenceManager, response, link);
        }
    }

    private void determineDifferentDeviceErrorFlowAndFinish(String oobCode, final String providerId) {
        getAuth().checkActionCode(oobCode).addOnCompleteListener(new OnCompleteListener<ActionCodeResult>() {
            public void onComplete(Task<ActionCodeResult> task) {
                if (!task.isSuccessful()) {
                    EmailLinkSignInHandler.this.setResult((Resource<IdpResponse>) Resource.forFailure(new FirebaseUiException(7)));
                } else if (!TextUtils.isEmpty(providerId)) {
                    EmailLinkSignInHandler.this.setResult((Resource<IdpResponse>) Resource.forFailure(new FirebaseUiException(10)));
                } else {
                    EmailLinkSignInHandler.this.setResult((Resource<IdpResponse>) Resource.forFailure(new FirebaseUiException(9)));
                }
            }
        });
    }

    private void handleLinkingFlow(AuthOperationManager authOperationManager, final EmailLinkPersistenceManager persistenceManager, final IdpResponse response, String link) {
        final AuthCredential storedCredentialForLink = ProviderUtils.getAuthCredential(response);
        AuthCredential emailLinkCredential = EmailAuthProvider.getCredentialWithLink(response.getEmail(), link);
        if (authOperationManager.canUpgradeAnonymous(getAuth(), (FlowParameters) getArguments())) {
            authOperationManager.safeLink(emailLinkCredential, storedCredentialForLink, (FlowParameters) getArguments()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                public void onComplete(Task<AuthResult> task) {
                    persistenceManager.clearAllData(EmailLinkSignInHandler.this.getApplication());
                    if (task.isSuccessful()) {
                        EmailLinkSignInHandler.this.handleMergeFailure(storedCredentialForLink);
                    } else {
                        EmailLinkSignInHandler.this.setResult((Resource<IdpResponse>) Resource.forFailure(task.getException()));
                    }
                }
            });
        } else {
            getAuth().signInWithCredential(emailLinkCredential).continueWithTask(new Continuation<AuthResult, Task<AuthResult>>() {
                public Task<AuthResult> then(Task<AuthResult> task) {
                    persistenceManager.clearAllData(EmailLinkSignInHandler.this.getApplication());
                    if (!task.isSuccessful()) {
                        return task;
                    }
                    return task.getResult().getUser().linkWithCredential(storedCredentialForLink).continueWithTask(new ProfileMerger(response)).addOnFailureListener(new TaskFailureLogger(EmailLinkSignInHandler.TAG, "linkWithCredential+merge failed."));
                }
            }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                public void onSuccess(AuthResult authResult) {
                    FirebaseUser user = authResult.getUser();
                    EmailLinkSignInHandler.this.handleSuccess(new IdpResponse.Builder(new User.Builder("emailLink", user.getEmail()).setName(user.getDisplayName()).setPhotoUri(user.getPhotoUrl()).build()).build(), authResult);
                }
            }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(Exception e) {
                    EmailLinkSignInHandler.this.setResult((Resource<IdpResponse>) Resource.forFailure(e));
                }
            });
        }
    }

    private void handleNormalFlow(AuthOperationManager authOperationManager, final EmailLinkPersistenceManager persistenceManager, String email, String link) {
        AuthCredential emailLinkCredential = EmailAuthProvider.getCredentialWithLink(email, link);
        final AuthCredential emailLinkCredentialForLinking = EmailAuthProvider.getCredentialWithLink(email, link);
        authOperationManager.signInAndLinkWithCredential(getAuth(), (FlowParameters) getArguments(), emailLinkCredential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            public void onSuccess(AuthResult authResult) {
                persistenceManager.clearAllData(EmailLinkSignInHandler.this.getApplication());
                FirebaseUser user = authResult.getUser();
                EmailLinkSignInHandler.this.handleSuccess(new IdpResponse.Builder(new User.Builder("emailLink", user.getEmail()).setName(user.getDisplayName()).setPhotoUri(user.getPhotoUrl()).build()).build(), authResult);
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(Exception e) {
                persistenceManager.clearAllData(EmailLinkSignInHandler.this.getApplication());
                if (e instanceof FirebaseAuthUserCollisionException) {
                    EmailLinkSignInHandler.this.handleMergeFailure(emailLinkCredentialForLinking);
                } else {
                    EmailLinkSignInHandler.this.setResult((Resource<IdpResponse>) Resource.forFailure(e));
                }
            }
        });
    }

    private boolean isDifferentDeviceFlow(EmailLinkPersistenceManager.SessionRecord sessionRecord, String sessionIdFromLink) {
        return sessionRecord == null || TextUtils.isEmpty(sessionRecord.getSessionId()) || TextUtils.isEmpty(sessionIdFromLink) || !sessionIdFromLink.equals(sessionRecord.getSessionId());
    }
}
