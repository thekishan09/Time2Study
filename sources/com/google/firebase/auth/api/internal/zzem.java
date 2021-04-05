package com.google.firebase.auth.api.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.firebase_auth.zzek;
import com.google.android.gms.internal.firebase_auth.zzeq;
import com.google.android.gms.internal.firebase_auth.zzfa;
import com.google.android.gms.internal.firebase_auth.zzff;
import com.google.android.gms.internal.firebase_auth.zzfq;
import com.google.firebase.auth.PhoneAuthCredential;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public interface zzem extends IInterface {
    /* renamed from: a_ */
    void mo23337a_() throws RemoteException;

    void zza(Status status) throws RemoteException;

    void zza(Status status, PhoneAuthCredential phoneAuthCredential) throws RemoteException;

    void zza(zzek zzek) throws RemoteException;

    void zza(com.google.android.gms.internal.firebase_auth.zzem zzem) throws RemoteException;

    void zza(zzeq zzeq) throws RemoteException;

    void zza(zzff zzff) throws RemoteException;

    void zza(zzff zzff, zzfa zzfa) throws RemoteException;

    void zza(zzfq zzfq) throws RemoteException;

    void zza(PhoneAuthCredential phoneAuthCredential) throws RemoteException;

    void zza(String str) throws RemoteException;

    void zzb() throws RemoteException;

    void zzb(String str) throws RemoteException;

    void zzc() throws RemoteException;

    void zzc(String str) throws RemoteException;
}
