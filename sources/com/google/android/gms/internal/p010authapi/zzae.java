package com.google.android.gms.internal.p010authapi;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.TaskCompletionSource;

/* renamed from: com.google.android.gms.internal.auth-api.zzae */
/* compiled from: com.google.android.gms:play-services-auth@@18.1.0 */
final /* synthetic */ class zzae implements RemoteCall {
    private final zzaf zzbk;
    private final BeginSignInRequest zzbl;

    zzae(zzaf zzaf, BeginSignInRequest beginSignInRequest) {
        this.zzbk = zzaf;
        this.zzbl = beginSignInRequest;
    }

    public final void accept(Object obj, Object obj2) {
        zzaf zzaf = this.zzbk;
        BeginSignInRequest beginSignInRequest = this.zzbl;
        ((zzad) ((zzak) obj).getService()).zzc((zzab) new zzaj(zzaf, (TaskCompletionSource) obj2), (BeginSignInRequest) Preconditions.checkNotNull(beginSignInRequest));
    }
}
