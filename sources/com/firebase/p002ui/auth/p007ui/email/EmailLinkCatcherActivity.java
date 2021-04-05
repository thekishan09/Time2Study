package com.firebase.p002ui.auth.p007ui.email;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import com.firebase.p002ui.auth.C0719R;
import com.firebase.p002ui.auth.FirebaseAuthAnonymousUpgradeException;
import com.firebase.p002ui.auth.FirebaseUiException;
import com.firebase.p002ui.auth.IdpResponse;
import com.firebase.p002ui.auth.data.model.FlowParameters;
import com.firebase.p002ui.auth.data.model.UserCancellationException;
import com.firebase.p002ui.auth.p007ui.InvisibleActivityBase;
import com.firebase.p002ui.auth.util.ExtraConstants;
import com.firebase.p002ui.auth.viewmodel.ResourceObserver;
import com.firebase.p002ui.auth.viewmodel.email.EmailLinkSignInHandler;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

/* renamed from: com.firebase.ui.auth.ui.email.EmailLinkCatcherActivity */
public class EmailLinkCatcherActivity extends InvisibleActivityBase {
    private EmailLinkSignInHandler mHandler;

    public static Intent createIntent(Context context, FlowParameters flowParams) {
        return createBaseIntent(context, EmailLinkCatcherActivity.class, flowParams);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHandler();
        if (getFlowParams().emailLink != null) {
            this.mHandler.startSignIn();
        }
    }

    private void initHandler() {
        EmailLinkSignInHandler emailLinkSignInHandler = (EmailLinkSignInHandler) ViewModelProviders.m16of((FragmentActivity) this).get(EmailLinkSignInHandler.class);
        this.mHandler = emailLinkSignInHandler;
        emailLinkSignInHandler.init(getFlowParams());
        this.mHandler.getOperation().observe(this, new ResourceObserver<IdpResponse>(this) {
            /* access modifiers changed from: protected */
            public void onSuccess(IdpResponse response) {
                EmailLinkCatcherActivity.this.finish(-1, response.toIntent());
            }

            /* access modifiers changed from: protected */
            public void onFailure(Exception e) {
                if (e instanceof UserCancellationException) {
                    EmailLinkCatcherActivity.this.finish(0, (Intent) null);
                } else if (e instanceof FirebaseAuthAnonymousUpgradeException) {
                    EmailLinkCatcherActivity.this.finish(0, new Intent().putExtra(ExtraConstants.IDP_RESPONSE, ((FirebaseAuthAnonymousUpgradeException) e).getResponse()));
                } else if (e instanceof FirebaseUiException) {
                    int errorCode = ((FirebaseUiException) e).getErrorCode();
                    if (errorCode == 8 || errorCode == 7 || errorCode == 11) {
                        EmailLinkCatcherActivity.this.buildAlertDialog(errorCode).show();
                    } else if (errorCode == 9 || errorCode == 6) {
                        EmailLinkCatcherActivity.this.startErrorRecoveryFlow(115);
                    } else if (errorCode == 10) {
                        EmailLinkCatcherActivity.this.startErrorRecoveryFlow(116);
                    }
                } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    EmailLinkCatcherActivity.this.startErrorRecoveryFlow(115);
                } else {
                    EmailLinkCatcherActivity.this.finish(0, IdpResponse.getErrorIntent(e));
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void startErrorRecoveryFlow(int flow) {
        if (flow == 116 || flow == 115) {
            startActivityForResult(EmailLinkErrorRecoveryActivity.createIntent(getApplicationContext(), getFlowParams(), flow), flow);
            return;
        }
        throw new IllegalStateException("Invalid flow param. It must be either RequestCodes.EMAIL_LINK_CROSS_DEVICE_LINKING_FLOW or RequestCodes.EMAIL_LINK_PROMPT_FOR_EMAIL_FLOW");
    }

    /* access modifiers changed from: private */
    public AlertDialog buildAlertDialog(final int errorCode) {
        String messageText;
        String titleText;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        if (errorCode == 11) {
            titleText = getString(C0719R.string.fui_email_link_different_anonymous_user_header);
            messageText = getString(C0719R.string.fui_email_link_different_anonymous_user_message);
        } else if (errorCode == 7) {
            titleText = getString(C0719R.string.fui_email_link_invalid_link_header);
            messageText = getString(C0719R.string.fui_email_link_invalid_link_message);
        } else {
            titleText = getString(C0719R.string.fui_email_link_wrong_device_header);
            messageText = getString(C0719R.string.fui_email_link_wrong_device_message);
        }
        return alertDialog.setTitle(titleText).setMessage(messageText).setPositiveButton(C0719R.string.fui_email_link_dismiss_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                EmailLinkCatcherActivity.this.finish(errorCode, (Intent) null);
            }
        }).create();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 115 || requestCode == 116) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == -1) {
                finish(-1, response.toIntent());
            } else {
                finish(0, (Intent) null);
            }
        }
    }
}
