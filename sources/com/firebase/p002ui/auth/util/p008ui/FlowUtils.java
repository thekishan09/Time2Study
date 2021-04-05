package com.firebase.p002ui.auth.util.p008ui;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import com.firebase.p002ui.auth.IdpResponse;
import com.firebase.p002ui.auth.data.model.IntentRequiredException;
import com.firebase.p002ui.auth.data.model.PendingIntentRequiredException;
import com.firebase.p002ui.auth.p007ui.FragmentBase;
import com.firebase.p002ui.auth.p007ui.HelperActivityBase;

/* renamed from: com.firebase.ui.auth.util.ui.FlowUtils */
public final class FlowUtils {
    private FlowUtils() {
        throw new AssertionError("No instance for you!");
    }

    public static boolean unhandled(HelperActivityBase activity, Exception e) {
        if (e instanceof IntentRequiredException) {
            IntentRequiredException typed = (IntentRequiredException) e;
            activity.startActivityForResult(typed.getIntent(), typed.getRequestCode());
            return false;
        } else if (!(e instanceof PendingIntentRequiredException)) {
            return true;
        } else {
            PendingIntentRequiredException typed2 = (PendingIntentRequiredException) e;
            startIntentSenderForResult(activity, typed2.getPendingIntent(), typed2.getRequestCode());
            return false;
        }
    }

    public static boolean unhandled(FragmentBase fragment, Exception e) {
        if (e instanceof IntentRequiredException) {
            IntentRequiredException typed = (IntentRequiredException) e;
            fragment.startActivityForResult(typed.getIntent(), typed.getRequestCode());
            return false;
        } else if (!(e instanceof PendingIntentRequiredException)) {
            return true;
        } else {
            PendingIntentRequiredException typed2 = (PendingIntentRequiredException) e;
            startIntentSenderForResult(fragment, typed2.getPendingIntent(), typed2.getRequestCode());
            return false;
        }
    }

    private static void startIntentSenderForResult(HelperActivityBase activity, PendingIntent intent, int requestCode) {
        try {
            activity.startIntentSenderForResult(intent.getIntentSender(), requestCode, (Intent) null, 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
            activity.finish(0, IdpResponse.getErrorIntent(e));
        }
    }

    private static void startIntentSenderForResult(FragmentBase fragment, PendingIntent intent, int requestCode) {
        try {
            fragment.startIntentSenderForResult(intent.getIntentSender(), requestCode, (Intent) null, 0, 0, 0, (Bundle) null);
        } catch (IntentSender.SendIntentException e) {
            ((HelperActivityBase) fragment.requireActivity()).finish(0, IdpResponse.getErrorIntent(e));
        }
    }
}
