package com.google.firebase.auth.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zze;
import com.google.android.gms.internal.firebase_auth.zzei;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.ActionCodeSettings;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzdw extends zzff<Void, Void> {
    private final zzei zza;

    public zzdw(String str, String str2, ActionCodeSettings actionCodeSettings) {
        super(6);
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        Preconditions.checkNotNull(actionCodeSettings);
        this.zza = new zzei(str, str2, actionCodeSettings);
    }

    public final String zza() {
        return "verifyBeforeUpdateEmail";
    }

    public final TaskApiCall<zzeh, Void> zzb() {
        return TaskApiCall.builder().setAutoResolveMissingFeatures(false).setFeatures(zze.zzb).run(new zzdz(this)).build();
    }

    public final void zze() {
        zzb(null);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zza(zzeh zzeh, TaskCompletionSource taskCompletionSource) throws RemoteException {
        this.zzh = new zzfm(this, taskCompletionSource);
        zzeh.zza().zza(this.zza, (zzem) this.zzc);
    }
}
