package com.firebase.p002ui.auth.p007ui.phone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import com.firebase.p002ui.auth.C0719R;
import com.firebase.p002ui.auth.FirebaseAuthAnonymousUpgradeException;
import com.firebase.p002ui.auth.IdpResponse;
import com.firebase.p002ui.auth.data.model.FlowParameters;
import com.firebase.p002ui.auth.data.model.PhoneNumberVerificationRequiredException;
import com.firebase.p002ui.auth.data.model.User;
import com.firebase.p002ui.auth.p007ui.AppCompatBase;
import com.firebase.p002ui.auth.util.ExtraConstants;
import com.firebase.p002ui.auth.util.FirebaseAuthError;
import com.firebase.p002ui.auth.viewmodel.ResourceObserver;
import com.firebase.p002ui.auth.viewmodel.phone.PhoneProviderResponseHandler;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuthException;

/* renamed from: com.firebase.ui.auth.ui.phone.PhoneActivity */
public class PhoneActivity extends AppCompatBase {
    private PhoneNumberVerificationHandler mPhoneVerifier;

    public static Intent createIntent(Context context, FlowParameters params, Bundle args) {
        return createBaseIntent(context, PhoneActivity.class, params).putExtra(ExtraConstants.PARAMS, args);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0719R.layout.fui_activity_register_phone);
        final PhoneProviderResponseHandler handler = (PhoneProviderResponseHandler) ViewModelProviders.m16of((FragmentActivity) this).get(PhoneProviderResponseHandler.class);
        handler.init(getFlowParams());
        handler.getOperation().observe(this, new ResourceObserver<IdpResponse>(this, C0719R.string.fui_progress_dialog_signing_in) {
            /* access modifiers changed from: protected */
            public void onSuccess(IdpResponse response) {
                PhoneActivity.this.startSaveCredentials(handler.getCurrentUser(), response, (String) null);
            }

            /* access modifiers changed from: protected */
            public void onFailure(Exception e) {
                PhoneActivity.this.handleError(e);
            }
        });
        PhoneNumberVerificationHandler phoneNumberVerificationHandler = (PhoneNumberVerificationHandler) ViewModelProviders.m16of((FragmentActivity) this).get(PhoneNumberVerificationHandler.class);
        this.mPhoneVerifier = phoneNumberVerificationHandler;
        phoneNumberVerificationHandler.init(getFlowParams());
        this.mPhoneVerifier.onRestoreInstanceState(savedInstanceState);
        this.mPhoneVerifier.getOperation().observe(this, new ResourceObserver<PhoneVerification>(this, C0719R.string.fui_verifying) {
            /* access modifiers changed from: protected */
            public void onSuccess(PhoneVerification verification) {
                if (verification.isAutoVerified()) {
                    Toast.makeText(PhoneActivity.this, C0719R.string.fui_auto_verified, 1).show();
                }
                handler.startSignIn(verification.getCredential(), new IdpResponse.Builder(new User.Builder("phone", (String) null).setPhoneNumber(verification.getNumber()).build()).build());
            }

            /* access modifiers changed from: protected */
            public void onFailure(Exception e) {
                if (e instanceof PhoneNumberVerificationRequiredException) {
                    if (PhoneActivity.this.getSupportFragmentManager().findFragmentByTag(SubmitConfirmationCodeFragment.TAG) == null) {
                        PhoneActivity.this.showSubmitCodeFragment(((PhoneNumberVerificationRequiredException) e).getPhoneNumber());
                    }
                    PhoneActivity.this.handleError((Exception) null);
                    return;
                }
                PhoneActivity.this.handleError(e);
            }
        });
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(C0719R.C0722id.fragment_phone, CheckPhoneNumberFragment.newInstance(getIntent().getExtras().getBundle(ExtraConstants.PARAMS)), CheckPhoneNumberFragment.TAG).disallowAddToBackStack().commit();
        }
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.mPhoneVerifier.onSaveInstanceState(outState);
    }

    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    /* access modifiers changed from: private */
    public void handleError(Exception e) {
        TextInputLayout errorView = getErrorView();
        if (errorView != null) {
            if (e instanceof FirebaseAuthAnonymousUpgradeException) {
                finish(5, ((FirebaseAuthAnonymousUpgradeException) e).getResponse().toIntent());
            } else if (e instanceof FirebaseAuthException) {
                errorView.setError(getErrorMessage(FirebaseAuthError.fromException((FirebaseAuthException) e)));
            } else if (e != null) {
                errorView.setError(e.getLocalizedMessage());
            } else {
                errorView.setError((CharSequence) null);
            }
        }
    }

    private TextInputLayout getErrorView() {
        CheckPhoneNumberFragment checkFragment = (CheckPhoneNumberFragment) getSupportFragmentManager().findFragmentByTag(CheckPhoneNumberFragment.TAG);
        SubmitConfirmationCodeFragment submitFragment = (SubmitConfirmationCodeFragment) getSupportFragmentManager().findFragmentByTag(SubmitConfirmationCodeFragment.TAG);
        if (checkFragment != null && checkFragment.getView() != null) {
            return (TextInputLayout) checkFragment.getView().findViewById(C0719R.C0722id.phone_layout);
        }
        if (submitFragment == null || submitFragment.getView() == null) {
            return null;
        }
        return (TextInputLayout) submitFragment.getView().findViewById(C0719R.C0722id.confirmation_code_layout);
    }

    /* renamed from: com.firebase.ui.auth.ui.phone.PhoneActivity$3 */
    static /* synthetic */ class C07683 {
        static final /* synthetic */ int[] $SwitchMap$com$firebase$ui$auth$util$FirebaseAuthError;

        static {
            int[] iArr = new int[FirebaseAuthError.values().length];
            $SwitchMap$com$firebase$ui$auth$util$FirebaseAuthError = iArr;
            try {
                iArr[FirebaseAuthError.ERROR_INVALID_PHONE_NUMBER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$firebase$ui$auth$util$FirebaseAuthError[FirebaseAuthError.ERROR_TOO_MANY_REQUESTS.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$firebase$ui$auth$util$FirebaseAuthError[FirebaseAuthError.ERROR_QUOTA_EXCEEDED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$firebase$ui$auth$util$FirebaseAuthError[FirebaseAuthError.ERROR_INVALID_VERIFICATION_CODE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$firebase$ui$auth$util$FirebaseAuthError[FirebaseAuthError.ERROR_SESSION_EXPIRED.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    private String getErrorMessage(FirebaseAuthError error) {
        int i = C07683.$SwitchMap$com$firebase$ui$auth$util$FirebaseAuthError[error.ordinal()];
        if (i == 1) {
            return getString(C0719R.string.fui_invalid_phone_number);
        }
        if (i == 2) {
            return getString(C0719R.string.fui_error_too_many_attempts);
        }
        if (i == 3) {
            return getString(C0719R.string.fui_error_quota_exceeded);
        }
        if (i == 4) {
            return getString(C0719R.string.fui_incorrect_code_dialog_body);
        }
        if (i != 5) {
            return error.getDescription();
        }
        return getString(C0719R.string.fui_error_session_expired);
    }

    /* access modifiers changed from: private */
    public void showSubmitCodeFragment(String number) {
        getSupportFragmentManager().beginTransaction().replace(C0719R.C0722id.fragment_phone, SubmitConfirmationCodeFragment.newInstance(number), SubmitConfirmationCodeFragment.TAG).addToBackStack((String) null).commit();
    }

    public void showProgress(int message) {
        getActiveFragment().showProgress(message);
    }

    public void hideProgress() {
        getActiveFragment().hideProgress();
    }

    /* JADX WARNING: type inference failed for: r1v4, types: [androidx.fragment.app.Fragment] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.firebase.p002ui.auth.p007ui.FragmentBase getActiveFragment() {
        /*
            r3 = this;
            androidx.fragment.app.FragmentManager r0 = r3.getSupportFragmentManager()
            java.lang.String r1 = "VerifyPhoneFragment"
            androidx.fragment.app.Fragment r0 = r0.findFragmentByTag(r1)
            com.firebase.ui.auth.ui.phone.CheckPhoneNumberFragment r0 = (com.firebase.p002ui.auth.p007ui.phone.CheckPhoneNumberFragment) r0
            if (r0 == 0) goto L_0x0015
            android.view.View r1 = r0.getView()
            if (r1 != 0) goto L_0x0023
        L_0x0015:
            androidx.fragment.app.FragmentManager r1 = r3.getSupportFragmentManager()
            java.lang.String r2 = "SubmitConfirmationCodeFragment"
            androidx.fragment.app.Fragment r1 = r1.findFragmentByTag(r2)
            r0 = r1
            com.firebase.ui.auth.ui.phone.SubmitConfirmationCodeFragment r0 = (com.firebase.p002ui.auth.p007ui.phone.SubmitConfirmationCodeFragment) r0
        L_0x0023:
            if (r0 == 0) goto L_0x002c
            android.view.View r1 = r0.getView()
            if (r1 == 0) goto L_0x002c
            return r0
        L_0x002c:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "No fragments added"
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.firebase.p002ui.auth.p007ui.phone.PhoneActivity.getActiveFragment():com.firebase.ui.auth.ui.FragmentBase");
    }
}
