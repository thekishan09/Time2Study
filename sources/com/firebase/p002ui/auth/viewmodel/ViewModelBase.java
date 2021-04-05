package com.firebase.p002ui.auth.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.firebase.ui.auth.viewmodel.ViewModelBase */
public abstract class ViewModelBase<T> extends AndroidViewModel {
    private T mArguments;
    private final AtomicBoolean mIsInitialized = new AtomicBoolean();

    protected ViewModelBase(Application application) {
        super(application);
    }

    public void init(T args) {
        if (this.mIsInitialized.compareAndSet(false, true)) {
            this.mArguments = args;
            onCreate();
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate() {
    }

    /* access modifiers changed from: protected */
    public T getArguments() {
        return this.mArguments;
    }

    /* access modifiers changed from: protected */
    public void setArguments(T arguments) {
        this.mArguments = arguments;
    }

    /* access modifiers changed from: protected */
    public void onCleared() {
        this.mIsInitialized.set(false);
    }
}
