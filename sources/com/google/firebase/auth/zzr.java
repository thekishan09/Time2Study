package com.google.firebase.auth;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzr implements Continuation<GetTokenResult, Task<Void>> {
    private final /* synthetic */ String zza;
    private final /* synthetic */ ActionCodeSettings zzb;
    private final /* synthetic */ FirebaseUser zzc;

    zzr(FirebaseUser firebaseUser, String str, ActionCodeSettings actionCodeSettings) {
        this.zzc = firebaseUser;
        this.zza = str;
        this.zzb = actionCodeSettings;
    }

    public final /* synthetic */ Object then(Task task) throws Exception {
        return FirebaseAuth.getInstance(this.zzc.zzc()).zza(((GetTokenResult) task.getResult()).getToken(), this.zza, this.zzb);
    }
}
