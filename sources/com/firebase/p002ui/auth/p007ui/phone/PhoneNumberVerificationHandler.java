package com.firebase.p002ui.auth.p007ui.phone;

import android.app.Application;
import android.os.Bundle;
import com.firebase.p002ui.auth.data.model.PhoneNumberVerificationRequiredException;
import com.firebase.p002ui.auth.data.model.Resource;
import com.firebase.p002ui.auth.viewmodel.AuthViewModelBase;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;

/* renamed from: com.firebase.ui.auth.ui.phone.PhoneNumberVerificationHandler */
public class PhoneNumberVerificationHandler extends AuthViewModelBase<PhoneVerification> {
    private static final long AUTO_RETRIEVAL_TIMEOUT_SECONDS = 120;
    private static final String VERIFICATION_ID_KEY = "verification_id";
    /* access modifiers changed from: private */
    public PhoneAuthProvider.ForceResendingToken mForceResendingToken;
    /* access modifiers changed from: private */
    public String mVerificationId;

    public PhoneNumberVerificationHandler(Application application) {
        super(application);
    }

    public void verifyPhoneNumber(final String number, boolean force) {
        setResult(Resource.forLoading());
        getPhoneAuth().verifyPhoneNumber(number, (long) AUTO_RETRIEVAL_TIMEOUT_SECONDS, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD, (PhoneAuthProvider.OnVerificationStateChangedCallbacks) new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                PhoneNumberVerificationHandler.this.setResult(Resource.forSuccess(new PhoneVerification(number, credential, true)));
            }

            public void onVerificationFailed(FirebaseException e) {
                PhoneNumberVerificationHandler.this.setResult(Resource.forFailure(e));
            }

            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                String unused = PhoneNumberVerificationHandler.this.mVerificationId = verificationId;
                PhoneAuthProvider.ForceResendingToken unused2 = PhoneNumberVerificationHandler.this.mForceResendingToken = token;
                PhoneNumberVerificationHandler.this.setResult(Resource.forFailure(new PhoneNumberVerificationRequiredException(number)));
            }
        }, force ? this.mForceResendingToken : null);
    }

    public void submitVerificationCode(String number, String code) {
        setResult(Resource.forSuccess(new PhoneVerification(number, PhoneAuthProvider.getCredential(this.mVerificationId, code), false)));
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putString(VERIFICATION_ID_KEY, this.mVerificationId);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (this.mVerificationId == null && savedInstanceState != null) {
            this.mVerificationId = savedInstanceState.getString(VERIFICATION_ID_KEY);
        }
    }
}
