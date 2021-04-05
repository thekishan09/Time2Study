package com.google.android.gms.auth.api.signin.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;

/* compiled from: com.google.android.gms:play-services-auth@@18.1.0 */
final class zzk extends zzd {
    private final /* synthetic */ zzl zzcj;

    zzk(zzl zzl) {
        this.zzcj = zzl;
    }

    public final void zze(Status status) throws RemoteException {
        this.zzcj.setResult(status);
    }
}
