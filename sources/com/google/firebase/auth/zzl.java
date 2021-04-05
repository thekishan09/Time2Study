package com.google.firebase.auth;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthProvider;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzl extends PhoneAuthProvider.OnVerificationStateChangedCallbacks {
    private final /* synthetic */ PhoneAuthProvider.OnVerificationStateChangedCallbacks zza;
    private final /* synthetic */ FirebaseAuth zzb;

    zzl(FirebaseAuth firebaseAuth, PhoneAuthProvider.OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks) {
        this.zzb = firebaseAuth;
        this.zza = onVerificationStateChangedCallbacks;
    }

    public final void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
        this.zza.onVerificationCompleted(phoneAuthCredential);
    }

    public final void onCodeSent(String str, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
        this.zza.onVerificationCompleted(PhoneAuthProvider.getCredential(str, this.zzb.zzg.zzb()));
    }

    public final void onCodeAutoRetrievalTimeOut(String str) {
    }

    public final void onVerificationFailed(FirebaseException firebaseException) {
        this.zza.onVerificationFailed(firebaseException);
    }
}
