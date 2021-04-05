package com.google.android.gms.internal.p010authapi;

import android.os.RemoteException;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.tasks.TaskCompletionSource;

/* renamed from: com.google.android.gms.internal.auth-api.zzaj */
/* compiled from: com.google.android.gms:play-services-auth@@18.1.0 */
final class zzaj extends zzaa {
    private final /* synthetic */ TaskCompletionSource zzbn;

    zzaj(zzaf zzaf, TaskCompletionSource taskCompletionSource) {
        this.zzbn = taskCompletionSource;
    }

    public final void zzc(Status status, BeginSignInResult beginSignInResult) throws RemoteException {
        TaskUtil.setResultOrApiException(status, beginSignInResult, this.zzbn);
    }
}
