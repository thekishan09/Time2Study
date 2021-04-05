package com.google.android.gms.internal.p010authapi;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.common.api.internal.IStatusCallback;

/* renamed from: com.google.android.gms.internal.auth-api.zzac */
/* compiled from: com.google.android.gms:play-services-auth@@18.1.0 */
public final class zzac extends zzd implements zzad {
    zzac(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.auth.api.identity.internal.ISignInService");
    }

    public final void zzc(zzab zzab, BeginSignInRequest beginSignInRequest) throws RemoteException {
        Parcel zzc = zzc();
        zzf.zzc(zzc, (IInterface) zzab);
        zzf.zzc(zzc, (Parcelable) beginSignInRequest);
        zzc(1, zzc);
    }

    public final void zzc(IStatusCallback iStatusCallback, String str) throws RemoteException {
        Parcel zzc = zzc();
        zzf.zzc(zzc, (IInterface) iStatusCallback);
        zzc.writeString(str);
        zzc(2, zzc);
    }
}
