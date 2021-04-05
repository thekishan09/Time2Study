package com.google.firebase.auth.api.internal;

import com.google.firebase.auth.PhoneAuthProvider;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzfg implements zzfn {
    private final /* synthetic */ String zza;

    zzfg(zzfh zzfh, String str) {
        this.zza = str;
    }

    public final void zza(PhoneAuthProvider.OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks, Object... objArr) {
        onVerificationStateChangedCallbacks.onCodeSent(this.zza, PhoneAuthProvider.ForceResendingToken.zza());
    }
}
