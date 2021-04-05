package com.google.firebase.auth.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzdg;
import com.google.android.gms.internal.firebase_auth.zze;
import com.google.android.gms.internal.firebase_auth.zzfr;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.PhoneAuthProvider;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzea extends zzff<Void, PhoneAuthProvider.OnVerificationStateChangedCallbacks> {
    private final zzdg zza;

    public zzea(zzfr zzfr) {
        super(8);
        Preconditions.checkNotNull(zzfr);
        this.zza = new zzdg(zzfr);
    }

    public final String zza() {
        return "verifyPhoneNumber";
    }

    public final TaskApiCall<zzeh, Void> zzb() {
        return TaskApiCall.builder().setAutoResolveMissingFeatures(false).setFeatures((this.zzu || this.zzv) ? null : new Feature[]{zze.zza}).run(new zzed(this)).build();
    }

    public final void zze() {
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
