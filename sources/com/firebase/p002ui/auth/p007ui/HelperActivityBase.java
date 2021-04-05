package com.firebase.p002ui.auth.p007ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import androidx.appcompat.app.AppCompatActivity;
import com.firebase.p002ui.auth.AuthUI;
import com.firebase.p002ui.auth.IdpResponse;
import com.firebase.p002ui.auth.data.model.FlowParameters;
import com.firebase.p002ui.auth.p007ui.credentials.CredentialSaveActivity;
import com.firebase.p002ui.auth.util.CredentialUtils;
import com.firebase.p002ui.auth.util.ExtraConstants;
import com.firebase.p002ui.auth.util.Preconditions;
import com.firebase.p002ui.auth.util.data.ProviderUtils;
import com.google.firebase.auth.FirebaseUser;

/* renamed from: com.firebase.ui.auth.ui.HelperActivityBase */
public abstract class HelperActivityBase extends AppCompatActivity implements ProgressView {
    private FlowParameters mParams;

    protected static Intent createBaseIntent(Context context, Class<? extends Activity> target, FlowParameters flowParams) {
        Intent intent = new Intent((Context) Preconditions.checkNotNull(context, "context cannot be null", new Object[0]), (Class) Preconditions.checkNotNull(target, "target activity cannot be null", new Object[0])).putExtra(ExtraConstants.FLOW_PARAMS, (Parcelable) Preconditions.checkNotNull(flowParams, "flowParams cannot be null", new Object[0]));
        intent.setExtrasClassLoader(AuthUI.class.getClassLoader());
        return intent;
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 102 || resultCode == 5) {
            finish(resultCode, data);
        }
    }

    public FlowParameters getFlowParams() {
        if (this.mParams == null) {
            this.mParams = FlowParameters.fromIntent(getIntent());
        }
        return this.mParams;
    }

    public void finish(int resultCode, Intent intent) {
        setResult(resultCode, intent);
        finish();
    }

    public void startSaveCredentials(FirebaseUser firebaseUser, IdpResponse response, String password) {
        startActivityForResult(CredentialSaveActivity.createIntent(this, getFlowParams(), CredentialUtils.buildCredential(firebaseUser, password, ProviderUtils.idpResponseToAccountType(response)), response), 102);
    }
}
