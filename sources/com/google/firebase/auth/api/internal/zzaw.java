package com.google.firebase.auth.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzce;
import com.google.android.gms.internal.firebase_auth.zze;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.ActionCodeResult;
import com.google.firebase.auth.internal.zzb;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzaw extends zzff<ActionCodeResult, zzb> {
    private final zzce zza;

    public zzaw(String str, String str2) {
        super(4);
        Preconditions.checkNotEmpty(str, "code cannot be null or empty");
        this.zza = new zzce(str, str2);
    }

    public final String zza() {
        return "checkActionCode";
    }

    public final TaskApiCall<zzeh, ActionCodeResult> zzb() {
        return TaskApiCall.builder().setAutoResolveMissingFeatures(false).setFeatures((this.zzu || this.zzv) ? null : new Feature[]{zze.zza}).run(new zzaz(this)).build();
    }

    public final void zze() {
        zzb(new com.google.firebase.auth.internal.zze(this.zzn));
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zza(zzeh zzeh, TaskCompletionSource taskCompletionSource) throws RemoteException {
        this.zzh = new zzfm(this, taskCompletionSource);
        if (this.zzu) {
            zzeh.zza().zzi(this.zza.zza(), this.zzc);
        } else {
            zzeh.zza().zza(this.zza, (zzem) this.zzc);
        }
    }
}
