package com.google.firebase.auth.internal;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.internal.firebase_auth.zzgc;
import com.google.android.gms.internal.firebase_auth.zzj;
import com.google.firebase.FirebaseError;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public class FederatedSignInActivity extends FragmentActivity {
    private static long zza = 0;
    private static final zzaq zzc = zzaq.zza();
    private static Handler zzd;
    /* access modifiers changed from: private */
    public static Runnable zze;
    private boolean zzb = false;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        String action = getIntent().getAction();
        if ("com.google.firebase.auth.internal.SIGN_IN".equals(action) || "com.google.firebase.auth.internal.LINK".equals(action) || "com.google.firebase.auth.internal.REAUTHENTICATE".equals(action)) {
            long currentTimeMillis = DefaultClock.getInstance().currentTimeMillis();
            if (currentTimeMillis - zza < 30000) {
                Log.e("IdpSignInActivity", "Could not start operation - already in progress");
                return;
            }
            zza = currentTimeMillis;
            if (bundle != null) {
                this.zzb = bundle.getBoolean("com.google.firebase.auth.internal.KEY_STARTED_SIGN_IN");
                return;
            }
            return;
        }
        String valueOf = String.valueOf(action);
        Log.e("IdpSignInActivity", valueOf.length() != 0 ? "Could not do operation - unknown action: ".concat(valueOf) : new String("Could not do operation - unknown action: "));
        zza();
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("com.google.firebase.auth.internal.KEY_STARTED_SIGN_IN", this.zzb);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        Intent intent = getIntent();
        boolean z = false;
        if ("com.google.firebase.auth.internal.WEB_SIGN_IN_FAILED".equals(intent.getAction())) {
            Log.e("IdpSignInActivity", "Web sign-in failed, finishing");
            if (zzbb.zza(intent)) {
                zza(zzbb.zzb(intent));
            } else {
                zza();
            }
            z = true;
        } else if (intent.hasExtra("com.google.firebase.auth.internal.OPERATION") && intent.hasExtra("com.google.firebase.auth.internal.VERIFY_ASSERTION_REQUEST")) {
            String stringExtra = intent.getStringExtra("com.google.firebase.auth.internal.OPERATION");
            if ("com.google.firebase.auth.internal.SIGN_IN".equals(stringExtra) || "com.google.firebase.auth.internal.LINK".equals(stringExtra) || "com.google.firebase.auth.internal.REAUTHENTICATE".equals(stringExtra)) {
                zzgc zzgc = (zzgc) SafeParcelableSerializer.deserializeFromIntentExtra(intent, "com.google.firebase.auth.internal.VERIFY_ASSERTION_REQUEST", zzgc.CREATOR);
                String stringExtra2 = intent.getStringExtra("com.google.firebase.auth.internal.EXTRA_TENANT_ID");
                zzgc.zzb(stringExtra2);
                zza = 0;
                this.zzb = false;
                Intent intent2 = new Intent();
                SafeParcelableSerializer.serializeToIntentExtra(zzgc, intent2, "com.google.firebase.auth.internal.VERIFY_ASSERTION_REQUEST");
                intent2.putExtra("com.google.firebase.auth.internal.OPERATION", stringExtra);
                intent2.setAction("com.google.firebase.auth.ACTION_RECEIVE_FIREBASE_AUTH_INTENT");
                if (!LocalBroadcastManager.getInstance(this).sendBroadcast(intent2)) {
                    zzaq.zza(getApplicationContext(), zzgc, stringExtra, stringExtra2);
                } else {
                    zzc.zza((Context) this);
                }
                finish();
                z = true;
            }
        }
        if (!z) {
            if (!this.zzb) {
                Intent intent3 = new Intent("com.google.firebase.auth.api.gms.ui.START_WEB_SIGN_IN");
                intent3.setPackage("com.google.android.gms");
                intent3.putExtras(getIntent().getExtras());
                intent3.putExtra("com.google.firebase.auth.internal.OPERATION", getIntent().getAction());
                try {
                    startActivityForResult(intent3, 40963);
                } catch (ActivityNotFoundException e) {
                    Log.w("IdpSignInActivity", "Could not launch web sign-in Intent. Google Play service is unavailable");
                    zza(new Status(FirebaseError.ERROR_INTERNAL_ERROR, "Could not launch web sign-in Intent. Google Play service is unavailable"));
                }
                this.zzb = true;
                return;
            }
            zze = new zzao(this);
            if (zzd == null) {
                zzd = new zzj();
            }
            zzd.postDelayed(zze, 800);
        }
    }

    public void onNewIntent(Intent intent) {
        Runnable runnable;
        super.onNewIntent(intent);
        Handler handler = zzd;
        if (!(handler == null || (runnable = zze) == null)) {
            handler.removeCallbacks(runnable);
            zze = null;
        }
        setIntent(intent);
    }

    /* access modifiers changed from: private */
    public final void zza() {
        zza = 0;
        this.zzb = false;
        Intent intent = new Intent();
        intent.putExtra("com.google.firebase.auth.internal.EXTRA_CANCELED", true);
        intent.setAction("com.google.firebase.auth.ACTION_RECEIVE_FIREBASE_AUTH_INTENT");
        if (!LocalBroadcastManager.getInstance(this).sendBroadcast(intent)) {
            zzaw.zza((Context) this, zzy.zza("WEB_CONTEXT_CANCELED"));
        } else {
            zzc.zza((Context) this);
        }
        finish();
    }

    private final void zza(Status status) {
        zza = 0;
        this.zzb = false;
        Intent intent = new Intent();
        zzbb.zza(intent, status);
        intent.setAction("com.google.firebase.auth.ACTION_RECEIVE_FIREBASE_AUTH_INTENT");
        if (!LocalBroadcastManager.getInstance(this).sendBroadcast(intent)) {
            zzaw.zza(getApplicationContext(), status);
        } else {
            zzc.zza((Context) this);
        }
        finish();
    }
}
