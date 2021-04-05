package com.google.firebase.auth.api.internal;

import android.os.RemoteException;
import android.text.TextUtils;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzcq;
import com.google.android.gms.internal.firebase_auth.zze;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.internal.zzap;
import com.google.firebase.auth.internal.zzb;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzbk extends zzff<GetTokenResult, zzb> {
    private final zzcq zza;

    public zzbk(String str) {
        super(1);
        Preconditions.checkNotEmpty(str, "refresh token cannot be null");
        this.zza = new zzcq(str);
    }

    public final String zza() {
        return "getAccessToken";
    }

    public final TaskApiCall<zzeh, GetTokenResult> zzb() {
        return TaskApiCall.builder().setAutoResolveMissingFeatures(false).setFeatures((this.zzu || this.zzv) ? null : new Feature[]{zze.zza}).run(new zzbn(this)).build();
    }

    public final void zze() {
        if (TextUtils.isEmpty(this.zzk.zzc())) {
            this.zzk.zza(this.zza.zza());
        }
        ((zzb) this.zzf).zza(this.zzk, this.zze);
        zzb(zzap.zza(this.zzk.zzd()));
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
