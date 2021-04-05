package com.google.android.gms.internal.p010authapi;

import com.google.android.gms.auth.api.identity.SignInOptions;
import com.google.android.gms.common.api.internal.IStatusCallback;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

/* renamed from: com.google.android.gms.internal.auth-api.zzah */
/* compiled from: com.google.android.gms:play-services-auth@@18.1.0 */
final /* synthetic */ class zzah implements RemoteCall {
    private final zzaf zzbk;

    zzah(zzaf zzaf) {
        this.zzbk = zzaf;
    }

    public final void accept(Object obj, Object obj2) {
        zzaf zzaf = this.zzbk;
        ((zzad) ((zzak) obj).getService()).zzc((IStatusCallback) new zzai(zzaf, (TaskCompletionSource) obj2), ((SignInOptions) zzaf.getApiOptions()).zzg());
    }
}
