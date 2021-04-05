package com.android.time2study;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.ViewGroup;

public class Loading {
    private Activity activity;
    private AlertDialog dialog;

    Loading(Activity myActivity) {
        this.activity = myActivity;
    }

    /* access modifiers changed from: package-private */
    public void loadingdialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);
        builder.setView(this.activity.getLayoutInflater().inflate(C0671R.layout.cursor_loading, (ViewGroup) null));
        builder.setCancelable(false);
        AlertDialog create = builder.create();
        this.dialog = create;
        create.show();
    }

    /* access modifiers changed from: package-private */
    public void dismissDialog() {
        this.dialog.dismiss();
    }
}
