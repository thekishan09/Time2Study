package com.firebase.p002ui.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import com.firebase.p002ui.auth.data.model.FlowParameters;
import com.firebase.p002ui.auth.data.model.UserCancellationException;
import com.firebase.p002ui.auth.data.remote.SignInKickstarter;
import com.firebase.p002ui.auth.p007ui.InvisibleActivityBase;
import com.firebase.p002ui.auth.util.ExtraConstants;
import com.firebase.p002ui.auth.viewmodel.ResourceObserver;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

/* renamed from: com.firebase.ui.auth.KickoffActivity */
public class KickoffActivity extends InvisibleActivityBase {
    /* access modifiers changed from: private */
    public SignInKickstarter mKickstarter;

    public static Intent createIntent(Context context, FlowParameters flowParams) {
        return createBaseIntent(context, KickoffActivity.class, flowParams);
    }

    /* access modifiers changed from: protected */
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SignInKickstarter signInKickstarter = (SignInKickstarter) ViewModelProviders.m16of((FragmentActivity) this).get(SignInKickstarter.class);
        this.mKickstarter = signInKickstarter;
        signInKickstarter.init(getFlowParams());
        this.mKickstarter.getOperation().observe(this, new ResourceObserver<IdpResponse>(this) {
            /* access modifiers changed from: protected */
            public void onSuccess(IdpResponse response) {
                KickoffActivity.this.finish(-1, response.toIntent());
            }

            /* access modifiers changed from: protected */
            public void onFailure(Exception e) {
                if (e instanceof UserCancellationException) {
                    KickoffActivity.this.finish(0, (Intent) null);
                } else if (e instanceof FirebaseAuthAnonymousUpgradeException) {
                    KickoffActivity.this.finish(0, new Intent().putExtra(ExtraConstants.IDP_RESPONSE, ((FirebaseAuthAnonymousUpgradeException) e).getResponse()));
                } else {
                    KickoffActivity.this.finish(0, IdpResponse.getErrorIntent(e));
                }
            }
        });
        GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(this).addOnSuccessListener((Activity) this, new OnSuccessListener<Void>() {
            public void onSuccess(Void aVoid) {
                if (savedInstanceState == null) {
                    if (KickoffActivity.this.isOffline()) {
                        KickoffActivity.this.finish(0, IdpResponse.getErrorIntent(new FirebaseUiException(1)));
                    } else {
                        KickoffActivity.this.mKickstarter.start();
                    }
                }
            }
        }).addOnFailureListener((Activity) this, (OnFailureListener) new OnFailureListener() {
            public void onFailure(Exception e) {
                KickoffActivity.this.finish(0, IdpResponse.getErrorIntent(new FirebaseUiException(2, (Throwable) e)));
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 106 && (resultCode == 113 || resultCode == 114)) {
            invalidateEmailLink();
        }
        this.mKickstarter.onActivityResult(requestCode, resultCode, data);
    }

    public void invalidateEmailLink() {
        FlowParameters flowParameters = getFlowParams();
        flowParameters.emailLink = null;
        setIntent(getIntent().putExtra(ExtraConstants.FLOW_PARAMS, flowParameters));
    }

    /* access modifiers changed from: private */
    public boolean isOffline() {
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService("connectivity");
        return manager == null || manager.getActiveNetworkInfo() == null || !manager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
