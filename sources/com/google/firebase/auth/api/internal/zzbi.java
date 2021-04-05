package com.google.firebase.auth.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzco;
import com.google.android.gms.internal.firebase_auth.zze;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.PhoneMultiFactorAssertion;
import com.google.firebase.auth.internal.zzb;
import com.google.firebase.auth.internal.zzj;
import com.google.firebase.auth.internal.zzp;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzbi extends zzff<AuthResult, zzb> {
    private final zzco zza;

    public zzbi(PhoneMultiFactorAssertion phoneMultiFactorAssertion, String str) {
        super(2);
        Preconditions.checkNotNull(phoneMultiFactorAssertion);
        Preconditions.checkNotEmpty(str);
        this.zza = new zzco(phoneMultiFactorAssertion.zza(), str);
    }

    public final String zza() {
        return "finalizeMfaSignIn";
    }

    public final TaskApiCall<zzeh, AuthResult> zzb() {
        return TaskApiCall.builder().setFeatures(zze.zzb).setAutoResolveMissingFeatures(false).run(new zzbl(this)).build();
    }

    public final void zze() {
        zzp zza2 = zzas.zza(this.zzd, this.zzl);
        if (this.zze == null || this.zze.getUid().equalsIgnoreCase(zza2.getUid())) {
            ((zzb) this.zzf).zza(this.zzk, zza2);
            zzb(new zzj(zza2));
            return;
        }
        zza(new Status(FirebaseError.ERROR_USER_MISMATCH));
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zza(zzeh zzeh, TaskCompletionSource taskCompletionSource) throws RemoteException {
        this.zzh = new zzfm(this, taskCompletionSource);
        zzeh.zza().zza(this.zza, (zzem) this.zzc);
    }
}
