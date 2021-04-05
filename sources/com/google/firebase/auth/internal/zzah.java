package com.google.firebase.auth.internal;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer;
import com.google.android.gms.internal.firebase_auth.zzgc;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.zze;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzah {
    private static zzah zzb;
    private boolean zza = false;

    private zzah() {
    }

    public static zzah zza() {
        if (zzb == null) {
            zzb = new zzah();
        }
        return zzb;
    }

    public final boolean zza(Activity activity, TaskCompletionSource<AuthResult> taskCompletionSource, FirebaseAuth firebaseAuth) {
        return zza(activity, taskCompletionSource, firebaseAuth, (FirebaseUser) null);
    }

    public final boolean zza(Activity activity, TaskCompletionSource<AuthResult> taskCompletionSource, FirebaseAuth firebaseAuth, FirebaseUser firebaseUser) {
        if (this.zza) {
            return false;
        }
        LocalBroadcastManager.getInstance(activity).registerReceiver(new zzam(this, activity, taskCompletionSource, firebaseAuth, firebaseUser), new IntentFilter("com.google.firebase.auth.ACTION_RECEIVE_FIREBASE_AUTH_INTENT"));
        this.zza = true;
        return true;
    }

    /* access modifiers changed from: private */
    public final void zza(Intent intent, TaskCompletionSource<AuthResult> taskCompletionSource, FirebaseAuth firebaseAuth) {
        firebaseAuth.signInWithCredential(zza(intent)).addOnSuccessListener(new zzaj(this, taskCompletionSource)).addOnFailureListener(new zzag(this, taskCompletionSource));
    }

    /* access modifiers changed from: private */
    public final void zza(Intent intent, TaskCompletionSource<AuthResult> taskCompletionSource, FirebaseUser firebaseUser) {
        firebaseUser.linkWithCredential(zza(intent)).addOnSuccessListener(new zzal(this, taskCompletionSource)).addOnFailureListener(new zzai(this, taskCompletionSource));
    }

    /* access modifiers changed from: private */
    public final void zzb(Intent intent, TaskCompletionSource<AuthResult> taskCompletionSource, FirebaseUser firebaseUser) {
        firebaseUser.reauthenticateAndRetrieveData(zza(intent)).addOnSuccessListener(new zzan(this, taskCompletionSource)).addOnFailureListener(new zzak(this, taskCompletionSource));
    }

    private static AuthCredential zza(Intent intent) {
        Preconditions.checkNotNull(intent);
        return zze.zza(((zzgc) SafeParcelableSerializer.deserializeFromIntentExtra(intent, "com.google.firebase.auth.internal.VERIFY_ASSERTION_REQUEST", zzgc.CREATOR)).zzb(true));
    }

    static void zzb() {
        zzb.zza = false;
    }
}
