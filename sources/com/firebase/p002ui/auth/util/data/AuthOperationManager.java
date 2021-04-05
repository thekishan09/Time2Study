package com.firebase.p002ui.auth.util.data;

import com.firebase.p002ui.auth.data.model.FlowParameters;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

/* renamed from: com.firebase.ui.auth.util.data.AuthOperationManager */
public class AuthOperationManager {
    private static String firebaseAppName = "FUIScratchApp";
    private static AuthOperationManager mAuthManager;
    public FirebaseAuth mScratchAuth;

    private AuthOperationManager() {
    }

    public static synchronized AuthOperationManager getInstance() {
        AuthOperationManager authOperationManager;
        synchronized (AuthOperationManager.class) {
            if (mAuthManager == null) {
                mAuthManager = new AuthOperationManager();
            }
            authOperationManager = mAuthManager;
        }
        return authOperationManager;
    }

    private FirebaseApp getScratchApp(FirebaseApp defaultApp) {
        try {
            return FirebaseApp.getInstance(firebaseAppName);
        } catch (IllegalStateException e) {
            return FirebaseApp.initializeApp(defaultApp.getApplicationContext(), defaultApp.getOptions(), firebaseAppName);
        }
    }

    private FirebaseAuth getScratchAuth(FlowParameters flowParameters) {
        if (this.mScratchAuth == null) {
            this.mScratchAuth = FirebaseAuth.getInstance(getScratchApp(FirebaseApp.getInstance(flowParameters.appName)));
        }
        return this.mScratchAuth;
    }

    public Task<AuthResult> createOrLinkUserWithEmailAndPassword(FirebaseAuth auth, FlowParameters flowParameters, String email, String password) {
        if (!canUpgradeAnonymous(auth, flowParameters)) {
            return auth.createUserWithEmailAndPassword(email, password);
        }
        return auth.getCurrentUser().linkWithCredential(EmailAuthProvider.getCredential(email, password));
    }

    public Task<AuthResult> signInAndLinkWithCredential(FirebaseAuth auth, FlowParameters flowParameters, AuthCredential credential) {
        if (canUpgradeAnonymous(auth, flowParameters)) {
            return auth.getCurrentUser().linkWithCredential(credential);
        }
        return auth.signInWithCredential(credential);
    }

    public boolean canUpgradeAnonymous(FirebaseAuth auth, FlowParameters flowParameters) {
        return flowParameters.isAnonymousUpgradeEnabled() && auth.getCurrentUser() != null && auth.getCurrentUser().isAnonymous();
    }

    public Task<AuthResult> validateCredential(AuthCredential credential, FlowParameters flowParameters) {
        return getScratchAuth(flowParameters).signInWithCredential(credential);
    }

    public Task<AuthResult> safeLink(AuthCredential credential, final AuthCredential credentialToLink, FlowParameters flowParameters) {
        return getScratchAuth(flowParameters).signInWithCredential(credential).continueWithTask(new Continuation<AuthResult, Task<AuthResult>>() {
            public Task<AuthResult> then(Task<AuthResult> task) throws Exception {
                if (task.isSuccessful()) {
                    return task.getResult().getUser().linkWithCredential(credentialToLink);
                }
                return task;
            }
        });
    }
}
