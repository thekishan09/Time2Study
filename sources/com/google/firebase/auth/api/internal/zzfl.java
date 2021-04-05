package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.PhoneAuthProvider;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzfl implements zzfn {
    private final /* synthetic */ Status zza;

    zzfl(zzfh zzfh, Status status) {
        this.zza = status;
    }

    public final void zza(PhoneAuthProvider.OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks, Object... objArr) {
        onVerificationStateChangedCallbacks.onVerificationFailed(zzej.zza(this.zza));
    }
}
