package com.google.firebase.auth;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzq implements Continuation<GetTokenResult, Task<Void>> {
    private final /* synthetic */ FirebaseUser zza;

    zzq(FirebaseUser firebaseUser) {
        this.zza = firebaseUser;
    }

    public final /* synthetic */ Object then(Task task) throws Exception {
        return FirebaseAuth.getInstance(this.zza.zzc()).zza((ActionCodeSettings) null, ((GetTokenResult) task.getResult()).getToken());
    }
}
