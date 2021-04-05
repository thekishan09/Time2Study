package com.google.firebase.auth.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzdy;
import com.google.android.gms.internal.firebase_auth.zze;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.PhoneMultiFactorInfo;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzdg extends zzff<Void, PhoneAuthProvider.OnVerificationStateChangedCallbacks> {
    private final zzdy zza;

    public zzdg(PhoneMultiFactorInfo phoneMultiFactorInfo, String str, String str2, long j, boolean z, boolean z2) {
        super(8);
        Preconditions.checkNotNull(phoneMultiFactorInfo);
        Preconditions.checkNotEmpty(str);
        this.zza = new zzdy(phoneMultiFactorInfo, str, str2, j, z, z2);
    }

    public final String zza() {
        return "startMfaSignInWithPhoneNumber";
    }

    public final TaskApiCall<zzeh, Void> zzb() {
        return TaskApiCall.builder().setFeatures(zze.zzb).setAutoResolveMissingFeatures(false).run(new zzdj(this)).build();
    }

    public final void zze() {
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zza(zzeh zzeh, TaskCompletionSource taskCompletionSource) throws RemoteException {
        this.zzh = new zzfm(this, taskCompletionSource);
        zzeh.zza().zza(this.zza, (zzem) this.zzc);
    }
}
