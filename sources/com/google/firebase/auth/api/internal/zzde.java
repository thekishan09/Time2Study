package com.google.firebase.auth.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzdw;
import com.google.android.gms.internal.firebase_auth.zze;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.internal.zzb;
import com.google.firebase.auth.internal.zzw;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzde extends zzff<Void, zzb> {
    private final zzdw zza;

    public zzde(zzw zzw, String str, String str2, long j, boolean z, boolean z2) {
        super(8);
        Preconditions.checkNotNull(zzw);
        Preconditions.checkNotEmpty(str);
        this.zza = new zzdw(zzw.zza(), str, str2, j, z, z2);
    }

    public final String zza() {
        return "startMfaEnrollmentWithPhoneNumber";
    }

    public final TaskApiCall<zzeh, Void> zzb() {
        return TaskApiCall.builder().setFeatures(zze.zzb).setAutoResolveMissingFeatures(false).run(new zzdh(this)).build();
    }

    public final void zze() {
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zza(zzeh zzeh, TaskCompletionSource taskCompletionSource) throws RemoteException {
        this.zzh = new zzfm(this, taskCompletionSource);
        zzeh.zza().zza(this.zza, (zzem) this.zzc);
    }
}
