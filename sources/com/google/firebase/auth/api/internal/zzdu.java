package com.google.firebase.auth.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zze;
import com.google.android.gms.internal.firebase_auth.zzeg;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.auth.internal.zzb;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzdu extends zzff<Void, zzb> {
    private final UserProfileChangeRequest zza;

    public zzdu(UserProfileChangeRequest userProfileChangeRequest) {
        super(2);
        this.zza = (UserProfileChangeRequest) Preconditions.checkNotNull(userProfileChangeRequest, "request cannot be null");
    }

    public final String zza() {
        return "updateProfile";
    }

    public final TaskApiCall<zzeh, Void> zzb() {
        return TaskApiCall.builder().setAutoResolveMissingFeatures(false).setFeatures((this.zzu || this.zzv) ? null : new Feature[]{zze.zza}).run(new zzdx(this)).build();
    }

    public final void zze() {
        ((zzb) this.zzf).zza(this.zzk, zzas.zza(this.zzd, this.zzl));
        zzb(null);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zza(zzeh zzeh, TaskCompletionSource taskCompletionSource) throws RemoteException {
        this.zzh = new zzfm(this, taskCompletionSource);
        if (this.zzu) {
            zzeh.zza().zza(this.zze.zzf(), this.zza, (zzem) this.zzc);
        } else {
            zzeh.zza().zza(new zzeg(this.zza, this.zze.zzf()), (zzem) this.zzc);
        }
    }
}
