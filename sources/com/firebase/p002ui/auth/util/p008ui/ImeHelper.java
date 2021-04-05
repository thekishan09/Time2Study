package com.firebase.p002ui.auth.util.p008ui;

import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

/* renamed from: com.firebase.ui.auth.util.ui.ImeHelper */
public class ImeHelper {

    /* renamed from: com.firebase.ui.auth.util.ui.ImeHelper$DonePressedListener */
    public interface DonePressedListener {
        void onDonePressed();
    }

    public static void setImeOnDoneListener(EditText doneEditText, final DonePressedListener listener) {
        doneEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                if (event != null && event.getKeyCode() == 66) {
                    if (event.getAction() == 1) {
                        listener.onDonePressed();
                    }
                    return true;
                } else if (actionId != 6) {
                    return false;
                } else {
                    listener.onDonePressed();
                    return true;
                }
            }
        });
    }
}
