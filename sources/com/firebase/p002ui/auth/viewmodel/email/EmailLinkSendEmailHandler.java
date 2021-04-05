package com.firebase.p002ui.auth.viewmodel.email;

import android.app.Application;
import com.firebase.p002ui.auth.IdpResponse;
import com.firebase.p002ui.auth.data.model.FlowParameters;
import com.firebase.p002ui.auth.data.model.Resource;
import com.firebase.p002ui.auth.util.data.AuthOperationManager;
import com.firebase.p002ui.auth.util.data.ContinueUrlBuilder;
import com.firebase.p002ui.auth.util.data.EmailLinkPersistenceManager;
import com.firebase.p002ui.auth.util.data.SessionUtils;
import com.firebase.p002ui.auth.viewmodel.AuthViewModelBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;

/* renamed from: com.firebase.ui.auth.viewmodel.email.EmailLinkSendEmailHandler */
public class EmailLinkSendEmailHandler extends AuthViewModelBase<String> {
    private static final int SESSION_ID_LENGTH = 10;

    public EmailLinkSendEmailHandler(Application application) {
        super(application);
    }

    public void sendSignInLinkToEmail(final String email, ActionCodeSettings actionCodeSettings, IdpResponse idpResponseForLinking, boolean forceSameDevice) {
        if (getAuth() != null) {
            setResult(Resource.forLoading());
            final String anonymousUserId = AuthOperationManager.getInstance().canUpgradeAnonymous(getAuth(), (FlowParameters) getArguments()) ? getAuth().getCurrentUser().getUid() : null;
            final String sessionId = SessionUtils.generateRandomAlphaNumericString(10);
            getAuth().sendSignInLinkToEmail(email, addSessionInfoToActionCodeSettings(actionCodeSettings, sessionId, anonymousUserId, idpResponseForLinking, forceSameDevice)).addOnCompleteListener(new OnCompleteListener<Void>() {
                public void onComplete(Task<Void> task) {
                    if (task.isSuccessful()) {
                        EmailLinkPersistenceManager.getInstance().saveEmail(EmailLinkSendEmailHandler.this.getApplication(), email, sessionId, anonymousUserId);
                        EmailLinkSendEmailHandler.this.setResult(Resource.forSuccess(email));
                        return;
                    }
                    EmailLinkSendEmailHandler.this.setResult(Resource.forFailure(task.getException()));
                }
            });
        }
    }

    private ActionCodeSettings addSessionInfoToActionCodeSettings(ActionCodeSettings actionCodeSettings, String sessionId, String anonymousUserId, IdpResponse response, boolean forceSameDevice) {
        ContinueUrlBuilder continueUrlBuilder = new ContinueUrlBuilder(actionCodeSettings.getUrl());
        continueUrlBuilder.appendSessionId(sessionId);
        continueUrlBuilder.appendAnonymousUserId(anonymousUserId);
        continueUrlBuilder.appendForceSameDeviceBit(forceSameDevice);
        if (response != null) {
            continueUrlBuilder.appendProviderId(response.getProviderType());
        }
        return ActionCodeSettings.newBuilder().setUrl(continueUrlBuilder.build()).setHandleCodeInApp(true).setAndroidPackageName(actionCodeSettings.getAndroidPackageName(), actionCodeSettings.getAndroidInstallApp(), actionCodeSettings.getAndroidMinimumVersion()).setIOSBundleId(actionCodeSettings.getIOSBundle()).build();
    }
}
