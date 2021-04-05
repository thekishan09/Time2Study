package com.google.android.gms.auth.api.signin.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.internal.p010authapi.zzd;
import com.google.android.gms.internal.p010authapi.zzf;

/* compiled from: com.google.android.gms:play-services-auth@@18.1.0 */
public final class zzu extends zzd implements zzv {
    zzu(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.auth.api.signin.internal.ISignInService");
    }

    public final void zzc(zzt zzt, GoogleSignInOptions googleSignInOptions) throws RemoteException {
        Parcel zzc = zzc();
        zzf.zzc(zzc, (IInterface) zzt);
        zzf.zzc(zzc, (Parcelable) googleSignInOptions);
        zzc(101, zzc);
    }

    public final void zzd(zzt zzt, GoogleSignInOptions googleSignInOptions) throws RemoteException {
        Parcel zzc = zzc();
        zzf.zzc(zzc, (IInterface) zzt);
        zzf.zzc(zzc, (Parcelable) googleSignInOptions);
        zzc(102, zzc);
    }

    public final void zze(zzt zzt, GoogleSignInOptions googleSignInOptions) throws RemoteException {
        Parcel zzc = zzc();
        zzf.zzc(zzc, (IInterface) zzt);
        zzf.zzc(zzc, (Parcelable) googleSignInOptions);
        zzc(103, zzc);
    }
}
