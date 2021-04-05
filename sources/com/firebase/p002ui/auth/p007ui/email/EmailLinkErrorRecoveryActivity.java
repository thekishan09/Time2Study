package com.firebase.p002ui.auth.p007ui.email;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import com.firebase.p002ui.auth.C0719R;
import com.firebase.p002ui.auth.IdpResponse;
import com.firebase.p002ui.auth.data.model.FlowParameters;
import com.firebase.p002ui.auth.p007ui.AppCompatBase;
import com.firebase.p002ui.auth.p007ui.email.EmailLinkCrossDeviceLinkingFragment;
import com.firebase.p002ui.auth.p007ui.email.EmailLinkPromptEmailFragment;

/* renamed from: com.firebase.ui.auth.ui.email.EmailLinkErrorRecoveryActivity */
public class EmailLinkErrorRecoveryActivity extends AppCompatBase implements EmailLinkPromptEmailFragment.EmailLinkPromptEmailListener, EmailLinkCrossDeviceLinkingFragment.FinishEmailLinkSignInListener {
    private static final String RECOVERY_TYPE_KEY = "com.firebase.ui.auth.ui.email.recoveryTypeKey";

    public static Intent createIntent(Context context, FlowParameters flowParams, int flow) {
        return createBaseIntent(context, EmailLinkErrorRecoveryActivity.class, flowParams).putExtra(RECOVERY_TYPE_KEY, flow);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        Fragment fragment;
        super.onCreate(savedInstanceState);
        setContentView(C0719R.layout.fui_activity_register_email);
        if (savedInstanceState == null) {
            if (getIntent().getIntExtra(RECOVERY_TYPE_KEY, -1) == 116) {
                fragment = EmailLinkCrossDeviceLinkingFragment.newInstance();
            } else {
                fragment = EmailLinkPromptEmailFragment.newInstance();
            }
            switchFragment(fragment, C0719R.C0722id.fragment_register_email, EmailLinkPromptEmailFragment.TAG);
        }
    }

    public void onEmailPromptSuccess(IdpResponse response) {
        finish(-1, response.toIntent());
    }

    public void completeCrossDeviceEmailLinkFlow() {
        switchFragment(EmailLinkPromptEmailFragment.newInstance(), C0719R.C0722id.fragment_register_email, EmailLinkCrossDeviceLinkingFragment.TAG, true, true);
    }

    public void showProgress(int message) {
        throw new UnsupportedOperationException("Fragments must handle progress updates.");
    }

    public void hideProgress() {
        throw new UnsupportedOperationException("Fragments must handle progress updates.");
    }
}
