package com.google.firebase.auth;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzo implements Continuation<Void, Task<Void>> {
    zzo(FirebaseAuth firebaseAuth) {
    }

    public final /* synthetic */ Object then(Task task) throws Exception {
        if (task.isSuccessful() || !(task.getException() instanceof FirebaseAuthException) || !((FirebaseAuthException) task.getException()).getErrorCode().equals("ERROR_INTERNAL_SUCCESS_SIGN_OUT")) {
            return task;
        }
        return Tasks.forResult(null);
    }
}
