package com.google.firebase.auth.internal;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.MultiFactor;
import com.google.firebase.auth.MultiFactorAssertion;
import com.google.firebase.auth.MultiFactorInfo;
import com.google.firebase.auth.MultiFactorSession;
import java.util.List;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzt extends MultiFactor {
    private final zzp zza;

    public zzt(zzp zzp) {
        Preconditions.checkNotNull(zzp);
        this.zza = zzp;
    }

    public final Task<MultiFactorSession> getSession() {
        return this.zza.getIdToken(false).continueWithTask(new zzs(this));
    }

    public final Task<Void> enroll(MultiFactorAssertion multiFactorAssertion, String str) {
        Preconditions.checkNotNull(multiFactorAssertion);
        zzp zzp = this.zza;
        return FirebaseAuth.getInstance(zzp.zzc()).zza((FirebaseUser) zzp, multiFactorAssertion, str);
    }

    public final List<MultiFactorInfo> getEnrolledFactors() {
        return this.zza.zzk();
    }

    public final Task<Void> unenroll(MultiFactorInfo multiFactorInfo) {
        Preconditions.checkNotNull(multiFactorInfo);
        return unenroll(multiFactorInfo.getUid());
    }

    public final Task<Void> unenroll(String str) {
        Preconditions.checkNotEmpty(str);
        zzp zzp = this.zza;
        return FirebaseAuth.getInstance(zzp.zzc()).zzd(zzp, str);
    }
}
