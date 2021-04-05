package com.google.firebase.firestore.auth;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.util.Listener;

public class EmptyCredentialsProvider extends CredentialsProvider {
    public Task<String> getToken() {
        TaskCompletionSource<String> source = new TaskCompletionSource<>();
        source.setResult(null);
        return source.getTask();
    }

    public void invalidateToken() {
    }

    public void setChangeListener(Listener<User> changeListener) {
        changeListener.onValue(User.UNAUTHENTICATED);
    }

    public void removeChangeListener() {
    }
}
