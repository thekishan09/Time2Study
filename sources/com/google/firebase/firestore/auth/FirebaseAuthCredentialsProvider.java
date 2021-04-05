package com.google.firebase.firestore.auth;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.internal.IdTokenListener;
import com.google.firebase.auth.internal.InternalAuthProvider;
import com.google.firebase.firestore.util.Executors;
import com.google.firebase.firestore.util.Listener;
import com.google.firebase.firestore.util.Logger;
import com.google.firebase.internal.InternalTokenResult;

public final class FirebaseAuthCredentialsProvider extends CredentialsProvider {
    private static final String LOG_TAG = "FirebaseAuthCredentialsProvider";
    private final InternalAuthProvider authProvider;
    private Listener<User> changeListener;
    private User currentUser = getUser();
    private boolean forceRefresh;
    private final IdTokenListener idTokenListener = FirebaseAuthCredentialsProvider$$Lambda$1.lambdaFactory$(this);
    private int tokenCounter = 0;

    public FirebaseAuthCredentialsProvider(InternalAuthProvider authProvider2) {
        this.authProvider = authProvider2;
        authProvider2.addIdTokenListener(this.idTokenListener);
    }

    static /* synthetic */ void lambda$new$0(FirebaseAuthCredentialsProvider firebaseAuthCredentialsProvider, InternalTokenResult token) {
        synchronized (firebaseAuthCredentialsProvider) {
            User user = firebaseAuthCredentialsProvider.getUser();
            firebaseAuthCredentialsProvider.currentUser = user;
            firebaseAuthCredentialsProvider.tokenCounter++;
            if (firebaseAuthCredentialsProvider.changeListener != null) {
                firebaseAuthCredentialsProvider.changeListener.onValue(user);
            }
        }
    }

    public synchronized Task<String> getToken() {
        boolean doForceRefresh;
        doForceRefresh = this.forceRefresh;
        this.forceRefresh = false;
        return this.authProvider.getAccessToken(doForceRefresh).continueWithTask(Executors.DIRECT_EXECUTOR, FirebaseAuthCredentialsProvider$$Lambda$2.lambdaFactory$(this, this.tokenCounter));
    }

    static /* synthetic */ Task lambda$getToken$1(FirebaseAuthCredentialsProvider firebaseAuthCredentialsProvider, int savedCounter, Task task) throws Exception {
        synchronized (firebaseAuthCredentialsProvider) {
            if (savedCounter != firebaseAuthCredentialsProvider.tokenCounter) {
                Logger.debug(LOG_TAG, "getToken aborted due to token change", new Object[0]);
                Task<String> token = firebaseAuthCredentialsProvider.getToken();
                return token;
            } else if (task.isSuccessful()) {
                Task forResult = Tasks.forResult(((GetTokenResult) task.getResult()).getToken());
                return forResult;
            } else {
                Task forException = Tasks.forException(task.getException());
                return forException;
            }
        }
    }

    public synchronized void invalidateToken() {
        this.forceRefresh = true;
    }

    public synchronized void setChangeListener(Listener<User> changeListener2) {
        this.changeListener = changeListener2;
        changeListener2.onValue(this.currentUser);
    }

    public synchronized void removeChangeListener() {
        this.changeListener = null;
        this.authProvider.removeIdTokenListener(this.idTokenListener);
    }

    private User getUser() {
        String uid = this.authProvider.getUid();
        return uid != null ? new User(uid) : User.UNAUTHENTICATED;
    }
}
