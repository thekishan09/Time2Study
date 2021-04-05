package com.google.firebase.auth.api.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.firebase_auth.zzb;
import com.google.android.gms.internal.firebase_auth.zzd;
import com.google.android.gms.internal.firebase_auth.zzek;
import com.google.android.gms.internal.firebase_auth.zzem;
import com.google.android.gms.internal.firebase_auth.zzeq;
import com.google.android.gms.internal.firebase_auth.zzfa;
import com.google.android.gms.internal.firebase_auth.zzff;
import com.google.android.gms.internal.firebase_auth.zzfq;
import com.google.firebase.auth.PhoneAuthCredential;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzeo extends zzb implements zzem {
    zzeo(IBinder iBinder) {
        super(iBinder, "com.google.firebase.auth.api.internal.IFirebaseAuthCallbacks");
    }

    public final void zza(zzff zzff) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzff);
        zzb(1, zza);
    }

    public final void zza(zzff zzff, zzfa zzfa) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzff);
        zzd.zza(zza, (Parcelable) zzfa);
        zzb(2, zza);
    }

    public final void zza(zzeq zzeq) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzeq);
        zzb(3, zza);
    }

    public final void zza(zzfq zzfq) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzfq);
        zzb(4, zza);
    }

    public final void zza(Status status) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) status);
        zzb(5, zza);
    }

    /* renamed from: a_ */
    public final void mo23337a_() throws RemoteException {
        zzb(6, zza());
    }

    public final void zzb() throws RemoteException {
        zzb(7, zza());
    }

    public final void zza(String str) throws RemoteException {
        Parcel zza = zza();
        zza.writeString(str);
        zzb(8, zza);
    }

    public final void zzb(String str) throws RemoteException {
        Parcel zza = zza();
        zza.writeString(str);
        zzb(9, zza);
    }

    public final void zza(PhoneAuthCredential phoneAuthCredential) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) phoneAuthCredential);
        zzb(10, zza);
    }

    public final void zzc(String str) throws RemoteException {
        Parcel zza = zza();
        zza.writeString(str);
        zzb(11, zza);
    }

    public final void zza(Status status, PhoneAuthCredential phoneAuthCredential) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) status);
        zzd.zza(zza, (Parcelable) phoneAuthCredential);
        zzb(12, zza);
    }

    public final void zzc() throws RemoteException {
        zzb(13, zza());
    }

    public final void zza(zzek zzek) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzek);
        zzb(14, zza);
    }

    public final void zza(zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzem);
        zzb(15, zza);
    }
}
