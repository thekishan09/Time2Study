package com.google.firebase.auth.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzdu;
import com.google.android.gms.internal.firebase_auth.zze;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.internal.zzb;
import com.google.firebase.auth.internal.zzj;
import com.google.firebase.auth.internal.zzp;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzci extends zzff<AuthResult, zzb> {
    private final zzdu zza;

    public zzci(PhoneAuthCredential phoneAuthCredential, String str) {
        super(2);
        Preconditions.checkNotNull(phoneAuthCredential, "credential cannot be null");
        this.zza = new zzdu(phoneAuthCredential.zza(false), str);
    }

    public final String zza() {
        return "reauthenticateWithPhoneCredentialWithData";
    }

    public final TaskApiCall<zzeh, AuthResult> zzb() {
        return TaskApiCall.builder().setAutoResolveMissingFeatures(false).setFeatures((this.zzu || this.zzv) ? null : new Feature[]{zze.zza}).run(new zzcl(this)).build();
    }

    public final void zze() {
        zzp zza2 = zzas.zza(this.zzd, this.zzl);
        if (this.zze.getUid().equalsIgnoreCase(zza2.getUid())) {
            ((zzb) this.zzf).zza(this.zzk, zza2);
            zzb(new zzj(zza2));
            return;
        }
        zza(new Status(FirebaseError.ERROR_USER_MISMATCH));
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zza(zzeh zzeh, TaskCompletionSource taskCompletionSource) throws RemoteException {
        this.zzh = new zzfm(this, taskCompletionSource);
        if (this.zzu) {
            zzeh.zza().zza(this.zza.zza(), (zzem) this.zzc);
        } else {
            zzeh.zza().zza(this.zza, (zzem) this.zzc);
        }
    }
}
