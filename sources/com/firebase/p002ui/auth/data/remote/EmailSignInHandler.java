package com.firebase.p002ui.auth.data.remote;

import android.app.Application;
import android.content.Intent;
import com.firebase.p002ui.auth.IdpResponse;
import com.firebase.p002ui.auth.data.model.Resource;
import com.firebase.p002ui.auth.data.model.UserCancellationException;
import com.firebase.p002ui.auth.p007ui.HelperActivityBase;
import com.firebase.p002ui.auth.p007ui.email.EmailActivity;
import com.firebase.p002ui.auth.viewmodel.ProviderSignInBase;

/* renamed from: com.firebase.ui.auth.data.remote.EmailSignInHandler */
public class EmailSignInHandler extends ProviderSignInBase<Void> {
    public EmailSignInHandler(Application application) {
        super(application);
    }

    public void startSignIn(HelperActivityBase activity) {
        activity.startActivityForResult(EmailActivity.createIntent(activity, activity.getFlowParams()), 106);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != 5 && requestCode == 106) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (response == null) {
                setResult(Resource.forFailure(new UserCancellationException()));
            } else {
                setResult(Resource.forSuccess(response));
            }
        }
    }
}
