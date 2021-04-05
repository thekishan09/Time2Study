package com.firebase.p002ui.auth.viewmodel;

import android.util.Log;
import androidx.lifecycle.Observer;
import com.firebase.p002ui.auth.AuthUI;
import com.firebase.p002ui.auth.C0719R;
import com.firebase.p002ui.auth.data.model.Resource;
import com.firebase.p002ui.auth.data.model.State;
import com.firebase.p002ui.auth.p007ui.FragmentBase;
import com.firebase.p002ui.auth.p007ui.HelperActivityBase;
import com.firebase.p002ui.auth.p007ui.ProgressView;
import com.firebase.p002ui.auth.util.p008ui.FlowUtils;

/* renamed from: com.firebase.ui.auth.viewmodel.ResourceObserver */
public abstract class ResourceObserver<T> implements Observer<Resource<T>> {
    private final HelperActivityBase mActivity;
    private final FragmentBase mFragment;
    private final int mLoadingMessage;
    private final ProgressView mProgressView;

    /* access modifiers changed from: protected */
    public abstract void onFailure(Exception exc);

    /* access modifiers changed from: protected */
    public abstract void onSuccess(T t);

    protected ResourceObserver(HelperActivityBase activity) {
        this(activity, (FragmentBase) null, activity, C0719R.string.fui_progress_dialog_loading);
    }

    protected ResourceObserver(HelperActivityBase activity, int message) {
        this(activity, (FragmentBase) null, activity, message);
    }

    protected ResourceObserver(FragmentBase fragment) {
        this((HelperActivityBase) null, fragment, fragment, C0719R.string.fui_progress_dialog_loading);
    }

    protected ResourceObserver(FragmentBase fragment, int message) {
        this((HelperActivityBase) null, fragment, fragment, message);
    }

    private ResourceObserver(HelperActivityBase activity, FragmentBase fragment, ProgressView progressView, int message) {
        this.mActivity = activity;
        this.mFragment = fragment;
        if (activity == null && fragment == null) {
            throw new IllegalStateException("ResourceObserver must be attached to an Activity or a Fragment");
        }
        this.mProgressView = progressView;
        this.mLoadingMessage = message;
    }

    public final void onChanged(Resource<T> resource) {
        boolean unhandled;
        if (resource.getState() == State.LOADING) {
            this.mProgressView.showProgress(this.mLoadingMessage);
            return;
        }
        this.mProgressView.hideProgress();
        if (!resource.isUsed()) {
            if (resource.getState() == State.SUCCESS) {
                onSuccess(resource.getValue());
            } else if (resource.getState() == State.FAILURE) {
                Exception e = resource.getException();
                FragmentBase fragmentBase = this.mFragment;
                if (fragmentBase == null) {
                    unhandled = FlowUtils.unhandled(this.mActivity, e);
                } else {
                    unhandled = FlowUtils.unhandled(fragmentBase, e);
                }
                if (unhandled) {
                    Log.e(AuthUI.TAG, "A sign-in error occurred.", e);
                    onFailure(e);
                }
            }
        }
    }
}
