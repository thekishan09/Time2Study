package com.google.android.gms.internal.p010authapi;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.IStatusCallback;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.tasks.TaskCompletionSource;

/* renamed from: com.google.android.gms.internal.auth-api.zzai */
/* compiled from: com.google.android.gms:play-services-auth@@18.1.0 */
final class zzai extends IStatusCallback.Stub {
    private final /* synthetic */ TaskCompletionSource zzbn;

    zzai(zzaf zzaf, TaskCompletionSource taskCompletionSource) {
        this.zzbn = taskCompletionSource;
    }

    public final void onResult(Status status) throws RemoteException {
        TaskUtil.setResultOrApiException(status, this.zzbn);
    }
}
