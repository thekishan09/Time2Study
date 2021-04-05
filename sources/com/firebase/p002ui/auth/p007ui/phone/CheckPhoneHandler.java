package com.firebase.p002ui.auth.p007ui.phone;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import com.firebase.p002ui.auth.data.model.PendingIntentRequiredException;
import com.firebase.p002ui.auth.data.model.PhoneNumber;
import com.firebase.p002ui.auth.data.model.Resource;
import com.firebase.p002ui.auth.util.data.PhoneNumberUtils;
import com.firebase.p002ui.auth.viewmodel.AuthViewModelBase;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.HintRequest;

/* renamed from: com.firebase.ui.auth.ui.phone.CheckPhoneHandler */
public class CheckPhoneHandler extends AuthViewModelBase<PhoneNumber> {
    public CheckPhoneHandler(Application application) {
        super(application);
    }

    public void fetchCredential() {
        setResult(Resource.forFailure(new PendingIntentRequiredException(Credentials.getClient((Context) getApplication()).getHintPickerIntent(new HintRequest.Builder().setPhoneNumberIdentifierSupported(true).build()), 101)));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String formattedPhone;
        if (requestCode == 101 && resultCode == -1 && (formattedPhone = PhoneNumberUtils.formatUsingCurrentCountry(((Credential) data.getParcelableExtra(Credential.EXTRA_KEY)).getId(), getApplication())) != null) {
            setResult(Resource.forSuccess(PhoneNumberUtils.getPhoneNumber(formattedPhone)));
        }
    }
}
