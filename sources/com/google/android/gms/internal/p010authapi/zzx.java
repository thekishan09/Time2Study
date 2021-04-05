package com.google.android.gms.internal.p010authapi;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.auth.api.credentials.CredentialRequest;

/* renamed from: com.google.android.gms.internal.auth-api.zzx */
/* compiled from: com.google.android.gms:play-services-auth@@18.1.0 */
public interface zzx extends IInterface {
    void zzc(zzv zzv) throws RemoteException;

    void zzc(zzv zzv, CredentialRequest credentialRequest) throws RemoteException;

    void zzc(zzv zzv, zzt zzt) throws RemoteException;

    void zzc(zzv zzv, zzz zzz) throws RemoteException;
}
