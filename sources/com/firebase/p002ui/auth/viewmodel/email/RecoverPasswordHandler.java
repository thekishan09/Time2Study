package com.firebase.p002ui.auth.viewmodel.email;

import android.app.Application;
import com.firebase.p002ui.auth.data.model.Resource;
import com.firebase.p002ui.auth.viewmodel.AuthViewModelBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/* renamed from: com.firebase.ui.auth.viewmodel.email.RecoverPasswordHandler */
public class RecoverPasswordHandler extends AuthViewModelBase<String> {
    public RecoverPasswordHandler(Application application) {
        super(application);
    }

    public void startReset(final String email) {
        setResult(Resource.forLoading());
        getAuth().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(Task<Void> task) {
                Resource<String> resource;
                if (task.isSuccessful()) {
                    resource = Resource.forSuccess(email);
                } else {
                    resource = Resource.forFailure(task.getException());
                }
                RecoverPasswordHandler.this.setResult(resource);
            }
        });
    }
}
