package com.firebase.p002ui.auth.p007ui.credentials;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import com.firebase.p002ui.auth.IdpResponse;
import com.firebase.p002ui.auth.data.model.FlowParameters;
import com.firebase.p002ui.auth.data.model.Resource;
import com.firebase.p002ui.auth.p007ui.InvisibleActivityBase;
import com.firebase.p002ui.auth.util.ExtraConstants;
import com.firebase.p002ui.auth.viewmodel.ResourceObserver;
import com.firebase.p002ui.auth.viewmodel.smartlock.SmartLockHandler;
import com.google.android.gms.auth.api.credentials.Credential;

/* renamed from: com.firebase.ui.auth.ui.credentials.CredentialSaveActivity */
public class CredentialSaveActivity extends InvisibleActivityBase {
    private static final String TAG = "CredentialSaveActivity";
    private SmartLockHandler mHandler;

    public static Intent createIntent(Context context, FlowParameters flowParams, Credential credential, IdpResponse response) {
        return createBaseIntent(context, CredentialSaveActivity.class, flowParams).putExtra(ExtraConstants.CREDENTIAL, credential).putExtra(ExtraConstants.IDP_RESPONSE, response);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final IdpResponse response = (IdpResponse) getIntent().getParcelableExtra(ExtraConstants.IDP_RESPONSE);
        Credential credential = (Credential) getIntent().getParcelableExtra(ExtraConstants.CREDENTIAL);
        SmartLockHandler smartLockHandler = (SmartLockHandler) ViewModelProviders.m16of((FragmentActivity) this).get(SmartLockHandler.class);
        this.mHandler = smartLockHandler;
        smartLockHandler.init(getFlowParams());
        this.mHandler.setResponse(response);
        this.mHandler.getOperation().observe(this, new ResourceObserver<IdpResponse>(this) {
            /* access modifiers changed from: protected */
            public void onSuccess(IdpResponse response) {
                CredentialSaveActivity.this.finish(-1, response.toIntent());
            }

            /* access modifiers changed from: protected */
            public void onFailure(Exception e) {
                CredentialSaveActivity.this.finish(-1, response.toIntent());
            }
        });
        if (((Resource) this.mHandler.getOperation().getValue()) == null) {
            Log.d(TAG, "Launching save operation.");
            this.mHandler.saveCredentials(credential);
            return;
        }
        Log.d(TAG, "Save operation in progress, doing nothing.");
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.mHandler.onActivityResult(requestCode, resultCode);
    }
}
