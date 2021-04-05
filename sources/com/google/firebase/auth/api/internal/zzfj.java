package com.google.firebase.auth.api.internal;

import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzfj implements zzfn {
    private final /* synthetic */ PhoneAuthCredential zza;

    zzfj(zzfh zzfh, PhoneAuthCredential phoneAuthCredential) {
        this.zza = phoneAuthCredential;
    }

    public final void zza(PhoneAuthProvider.OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks, Object... objArr) {
        onVerificationStateChangedCallbacks.onVerificationCompleted(this.zza);
    }
}
