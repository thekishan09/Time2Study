package com.google.android.gms.auth.api.signin.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;

/* compiled from: com.google.android.gms:play-services-auth@@18.1.0 */
final class zzm extends zzd {
    private final /* synthetic */ zzn zzck;

    zzm(zzn zzn) {
        this.zzck = zzn;
    }

    public final void zzf(Status status) throws RemoteException {
        this.zzck.setResult(status);
    }
}
