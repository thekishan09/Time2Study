package com.google.firebase.auth.internal;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.internal.firebase_auth.zzgc;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzaq {
    private static final zzaq zzc = new zzaq();
    private final zzaw zza;
    private final zzah zzb;

    private zzaq() {
        this(zzaw.zza(), zzah.zza());
    }

    private zzaq(zzaw zzaw, zzah zzah) {
        this.zza = zzaw;
        this.zzb = zzah;
    }

    public static zzaq zza() {
        return zzc;
    }

    public final Task<AuthResult> zzb() {
        return this.zza.zzb();
    }

    public final void zza(FirebaseAuth firebaseAuth) {
        this.zza.zza(firebaseAuth);
    }

    public static void zza(Context context, zzgc zzgc, String str, String str2) {
        zzaw.zza(context, zzgc, str, str2);
    }

    public final void zza(Context context) {
        this.zza.zza(context);
    }

    public final boolean zza(Activity activity, TaskCompletionSource<AuthResult> taskCompletionSource, FirebaseAuth firebaseAuth) {
        return this.zzb.zza(activity, taskCompletionSource, firebaseAuth);
    }

    public final boolean zza(Activity activity, TaskCompletionSource<AuthResult> taskCompletionSource, FirebaseAuth firebaseAuth, FirebaseUser firebaseUser) {
        return this.zzb.zza(activity, taskCompletionSource, firebaseAuth, firebaseUser);
    }
}
