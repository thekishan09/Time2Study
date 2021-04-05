package com.google.android.gms.auth.api.signin.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.p010authapi.zzc;

/* compiled from: com.google.android.gms:play-services-auth@@18.1.0 */
public abstract class zzq extends zzc implements zzr {
    public zzq() {
        super("com.google.android.gms.auth.api.signin.internal.IRevocationService");
    }

    /* access modifiers changed from: protected */
    public final boolean zzc(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (i == 1) {
            zzn();
        } else if (i != 2) {
            return false;
        } else {
            zzo();
        }
        return true;
    }
}
