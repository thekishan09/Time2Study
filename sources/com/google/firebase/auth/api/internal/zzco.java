package com.google.firebase.auth.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzde;
import com.google.android.gms.internal.firebase_auth.zze;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.internal.zzb;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzco extends zzff<Void, zzb> {
    private final zzde zza;
    private final String zzx;

    public zzco(String str, ActionCodeSettings actionCodeSettings, String str2, String str3) {
        super(4);
        Preconditions.checkNotEmpty(str, "email cannot be null or empty");
        this.zza = new zzde(str, actionCodeSettings, str2);
        this.zzx = str3;
    }

    public final String zza() {
        return this.zzx;
    }

    public final TaskApiCall<zzeh, Void> zzb() {
        return TaskApiCall.builder().setAutoResolveMissingFeatures(false).setFeatures((this.zzu || this.zzv) ? null : new Feature[]{zze.zza}).run(new zzcr(this)).build();
    }

    public final void zze() {
        zzb(null);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zza(zzeh zzeh, TaskCompletionSource taskCompletionSource) throws RemoteException {
        this.zzh = new zzfm(this, taskCompletionSource);
        if (this.zzu) {
            zzeh.zza().zzc(this.zza.zza(), this.zza.zzb(), (zzem) this.zzc);
        } else {
            zzeh.zza().zza(this.zza, (zzem) this.zzc);
        }
    }
}
