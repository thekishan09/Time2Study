package com.firebase.p002ui.auth.util.data;

import android.util.Log;
import com.google.android.gms.tasks.OnFailureListener;

/* renamed from: com.firebase.ui.auth.util.data.TaskFailureLogger */
public class TaskFailureLogger implements OnFailureListener {
    private String mMessage;
    private String mTag;

    public TaskFailureLogger(String tag, String message) {
        this.mTag = tag;
        this.mMessage = message;
    }

    public void onFailure(Exception e) {
        Log.w(this.mTag, this.mMessage, e);
    }
}
