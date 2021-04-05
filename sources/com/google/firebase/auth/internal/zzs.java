package com.google.firebase.auth.internal;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.MultiFactorSession;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzs implements Continuation<GetTokenResult, Task<MultiFactorSession>> {
    zzs(zzt zzt) {
    }

    public final /* synthetic */ Object then(Task task) throws Exception {
        if (!task.isSuccessful()) {
            return Tasks.forException(task.getException());
        }
        return Tasks.forResult(zzw.zza(((GetTokenResult) task.getResult()).getToken()));
    }
}
