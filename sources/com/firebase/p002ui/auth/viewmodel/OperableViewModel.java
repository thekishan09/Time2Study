package com.firebase.p002ui.auth.viewmodel;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/* renamed from: com.firebase.ui.auth.viewmodel.OperableViewModel */
public abstract class OperableViewModel<I, O> extends ViewModelBase<I> {
    private MutableLiveData<O> mOperation = new MutableLiveData<>();

    protected OperableViewModel(Application application) {
        super(application);
    }

    public LiveData<O> getOperation() {
        return this.mOperation;
    }

    /* access modifiers changed from: protected */
    public void setResult(O output) {
        this.mOperation.setValue(output);
    }
}
