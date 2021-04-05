package com.firebase.p002ui.auth.p007ui.email;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import com.firebase.p002ui.auth.data.model.FlowParameters;
import com.firebase.p002ui.auth.data.model.PendingIntentRequiredException;
import com.firebase.p002ui.auth.data.model.Resource;
import com.firebase.p002ui.auth.data.model.User;
import com.firebase.p002ui.auth.util.data.ProviderUtils;
import com.firebase.p002ui.auth.viewmodel.AuthViewModelBase;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/* renamed from: com.firebase.ui.auth.ui.email.CheckEmailHandler */
public class CheckEmailHandler extends AuthViewModelBase<User> {
    public CheckEmailHandler(Application application) {
        super(application);
    }

    public void fetchCredential() {
        setResult(Resource.forFailure(new PendingIntentRequiredException(Credentials.getClient((Context) getApplication()).getHintPickerIntent(new HintRequest.Builder().setEmailAddressIdentifierSupported(true).build()), 101)));
    }

    public void fetchProvider(final String email) {
        setResult(Resource.forLoading());
        ProviderUtils.fetchTopProvider(getAuth(), (FlowParameters) getArguments(), email).addOnCompleteListener(new OnCompleteListener<String>() {
            public void onComplete(Task<String> task) {
                if (task.isSuccessful()) {
                    CheckEmailHandler.this.setResult(Resource.forSuccess(new User.Builder(task.getResult(), email).build()));
                } else {
                    CheckEmailHandler.this.setResult(Resource.forFailure(task.getException()));
                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101 && resultCode == -1) {
            setResult(Resource.forLoading());
            final Credential credential = (Credential) data.getParcelableExtra(Credential.EXTRA_KEY);
            final String email = credential.getId();
            ProviderUtils.fetchTopProvider(getAuth(), (FlowParameters) getArguments(), email).addOnCompleteListener(new OnCompleteListener<String>() {
                public void onComplete(Task<String> task) {
                    if (task.isSuccessful()) {
                        CheckEmailHandler.this.setResult(Resource.forSuccess(new User.Builder(task.getResult(), email).setName(credential.getName()).setPhotoUri(credential.getProfilePictureUri()).build()));
                    } else {
                        CheckEmailHandler.this.setResult(Resource.forFailure(task.getException()));
                    }
                }
            });
        }
    }
}
