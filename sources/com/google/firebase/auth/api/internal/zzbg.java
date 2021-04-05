package com.google.firebase.auth.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzcm;
import com.google.android.gms.internal.firebase_auth.zze;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.PhoneMultiFactorAssertion;
import com.google.firebase.auth.internal.zzb;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzbg extends zzff<Void, zzb> {
    private final zzcm zza;

    public zzbg(PhoneMultiFactorAssertion phoneMultiFactorAssertion, String str, String str2) {
        super(2);
        Preconditions.checkNotNull(phoneMultiFactorAssertion);
        Preconditions.checkNotEmpty(str);
        this.zza = new zzcm(phoneMultiFactorAssertion.zza(), str, str2);
    }

    public final String zza() {
        return "finalizeMfaEnrollment";
    }

    public final TaskApiCall<zzeh, Void> zzb() {
        return TaskApiCall.builder().setFeatures(zze.zzb).setAutoResolveMissingFeatures(false).run(new zzbj(this)).build();
    }

    public final void zze() {
        ((zzb) this.zzf).zza(this.zzk, zzas.zza(this.zzd, this.zzl));
        zzb(null);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zza(zzeh zzeh, TaskCompletionSource taskCompletionSource) throws RemoteException {
        this.zzh = new zzfm(this, taskCompletionSource);
        zzeh.zza().zza(this.zza, (zzem) this.zzc);
    }
}
