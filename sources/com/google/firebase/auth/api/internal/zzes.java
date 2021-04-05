package com.google.firebase.auth.api.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.internal.firebase_auth.zzb;
import com.google.android.gms.internal.firebase_auth.zzby;
import com.google.android.gms.internal.firebase_auth.zzca;
import com.google.android.gms.internal.firebase_auth.zzcc;
import com.google.android.gms.internal.firebase_auth.zzce;
import com.google.android.gms.internal.firebase_auth.zzcg;
import com.google.android.gms.internal.firebase_auth.zzci;
import com.google.android.gms.internal.firebase_auth.zzck;
import com.google.android.gms.internal.firebase_auth.zzcm;
import com.google.android.gms.internal.firebase_auth.zzco;
import com.google.android.gms.internal.firebase_auth.zzcq;
import com.google.android.gms.internal.firebase_auth.zzcs;
import com.google.android.gms.internal.firebase_auth.zzcu;
import com.google.android.gms.internal.firebase_auth.zzcw;
import com.google.android.gms.internal.firebase_auth.zzcy;
import com.google.android.gms.internal.firebase_auth.zzd;
import com.google.android.gms.internal.firebase_auth.zzda;
import com.google.android.gms.internal.firebase_auth.zzdc;
import com.google.android.gms.internal.firebase_auth.zzde;
import com.google.android.gms.internal.firebase_auth.zzdg;
import com.google.android.gms.internal.firebase_auth.zzdi;
import com.google.android.gms.internal.firebase_auth.zzdk;
import com.google.android.gms.internal.firebase_auth.zzdm;
import com.google.android.gms.internal.firebase_auth.zzdo;
import com.google.android.gms.internal.firebase_auth.zzdq;
import com.google.android.gms.internal.firebase_auth.zzds;
import com.google.android.gms.internal.firebase_auth.zzdu;
import com.google.android.gms.internal.firebase_auth.zzdw;
import com.google.android.gms.internal.firebase_auth.zzdy;
import com.google.android.gms.internal.firebase_auth.zzea;
import com.google.android.gms.internal.firebase_auth.zzec;
import com.google.android.gms.internal.firebase_auth.zzee;
import com.google.android.gms.internal.firebase_auth.zzeg;
import com.google.android.gms.internal.firebase_auth.zzei;
import com.google.android.gms.internal.firebase_auth.zzfr;
import com.google.android.gms.internal.firebase_auth.zzgc;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.UserProfileChangeRequest;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzes extends zzb implements zzer {
    zzes(IBinder iBinder) {
        super(iBinder, "com.google.firebase.auth.api.internal.IFirebaseAuthService");
    }

    public final void zza(String str, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zza.writeString(str);
        zzd.zza(zza, (IInterface) zzem);
        zza(1, zza);
    }

    public final void zzb(String str, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zza.writeString(str);
        zzd.zza(zza, (IInterface) zzem);
        zza(2, zza);
    }

    public final void zza(zzgc zzgc, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzgc);
        zzd.zza(zza, (IInterface) zzem);
        zza(3, zza);
    }

    public final void zza(String str, UserProfileChangeRequest userProfileChangeRequest, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zza.writeString(str);
        zzd.zza(zza, (Parcelable) userProfileChangeRequest);
        zzd.zza(zza, (IInterface) zzem);
        zza(4, zza);
    }

    public final void zza(String str, String str2, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zza.writeString(str);
        zza.writeString(str2);
        zzd.zza(zza, (IInterface) zzem);
        zza(5, zza);
    }

    public final void zzb(String str, String str2, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zza.writeString(str);
        zza.writeString(str2);
        zzd.zza(zza, (IInterface) zzem);
        zza(6, zza);
    }

    public final void zzc(String str, String str2, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zza.writeString(str);
        zza.writeString(str2);
        zzd.zza(zza, (IInterface) zzem);
        zza(7, zza);
    }

    public final void zzd(String str, String str2, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zza.writeString(str);
        zza.writeString(str2);
        zzd.zza(zza, (IInterface) zzem);
        zza(8, zza);
    }

    public final void zzc(String str, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zza.writeString(str);
        zzd.zza(zza, (IInterface) zzem);
        zza(9, zza);
    }

    public final void zzd(String str, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zza.writeString(str);
        zzd.zza(zza, (IInterface) zzem);
        zza(10, zza);
    }

    public final void zza(String str, String str2, String str3, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zza.writeString(str);
        zza.writeString(str2);
        zza.writeString(str3);
        zzd.zza(zza, (IInterface) zzem);
        zza(11, zza);
    }

    public final void zza(String str, zzgc zzgc, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zza.writeString(str);
        zzd.zza(zza, (Parcelable) zzgc);
        zzd.zza(zza, (IInterface) zzem);
        zza(12, zza);
    }

    public final void zze(String str, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zza.writeString(str);
        zzd.zza(zza, (IInterface) zzem);
        zza(13, zza);
    }

    public final void zze(String str, String str2, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zza.writeString(str);
        zza.writeString(str2);
        zzd.zza(zza, (IInterface) zzem);
        zza(14, zza);
    }

    public final void zzf(String str, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zza.writeString(str);
        zzd.zza(zza, (IInterface) zzem);
        zza(15, zza);
    }

    public final void zza(zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (IInterface) zzem);
        zza(16, zza);
    }

    public final void zzg(String str, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zza.writeString(str);
        zzd.zza(zza, (IInterface) zzem);
        zza(17, zza);
    }

    public final void zzh(String str, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zza.writeString(str);
        zzd.zza(zza, (IInterface) zzem);
        zza(18, zza);
    }

    public final void zzi(String str, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zza.writeString(str);
        zzd.zza(zza, (IInterface) zzem);
        zza(19, zza);
    }

    public final void zzj(String str, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zza.writeString(str);
        zzd.zza(zza, (IInterface) zzem);
        zza(20, zza);
    }

    public final void zzf(String str, String str2, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zza.writeString(str);
        zza.writeString(str2);
        zzd.zza(zza, (IInterface) zzem);
        zza(21, zza);
    }

    public final void zza(zzfr zzfr, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzfr);
        zzd.zza(zza, (IInterface) zzem);
        zza(22, zza);
    }

    public final void zza(PhoneAuthCredential phoneAuthCredential, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) phoneAuthCredential);
        zzd.zza(zza, (IInterface) zzem);
        zza(23, zza);
    }

    public final void zza(String str, PhoneAuthCredential phoneAuthCredential, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zza.writeString(str);
        zzd.zza(zza, (Parcelable) phoneAuthCredential);
        zzd.zza(zza, (IInterface) zzem);
        zza(24, zza);
    }

    public final void zza(String str, ActionCodeSettings actionCodeSettings, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zza.writeString(str);
        zzd.zza(zza, (Parcelable) actionCodeSettings);
        zzd.zza(zza, (IInterface) zzem);
        zza(25, zza);
    }

    public final void zzb(String str, ActionCodeSettings actionCodeSettings, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zza.writeString(str);
        zzd.zza(zza, (Parcelable) actionCodeSettings);
        zzd.zza(zza, (IInterface) zzem);
        zza(26, zza);
    }

    public final void zzk(String str, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zza.writeString(str);
        zzd.zza(zza, (IInterface) zzem);
        zza(27, zza);
    }

    public final void zzc(String str, ActionCodeSettings actionCodeSettings, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zza.writeString(str);
        zzd.zza(zza, (Parcelable) actionCodeSettings);
        zzd.zza(zza, (IInterface) zzem);
        zza(28, zza);
    }

    public final void zza(EmailAuthCredential emailAuthCredential, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) emailAuthCredential);
        zzd.zza(zza, (IInterface) zzem);
        zza(29, zza);
    }

    public final void zza(zzcq zzcq, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzcq);
        zzd.zza(zza, (IInterface) zzem);
        zza(101, zza);
    }

    public final void zza(zzdo zzdo, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzdo);
        zzd.zza(zza, (IInterface) zzem);
        zza(102, zza);
    }

    public final void zza(zzdm zzdm, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzdm);
        zzd.zza(zza, (IInterface) zzem);
        zza(103, zza);
    }

    public final void zza(zzeg zzeg, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzeg);
        zzd.zza(zza, (IInterface) zzem);
        zza(104, zza);
    }

    public final void zza(zzca zzca, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzca);
        zzd.zza(zza, (IInterface) zzem);
        zza(105, zza);
    }

    public final void zza(zzcc zzcc, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzcc);
        zzd.zza(zza, (IInterface) zzem);
        zza(106, zza);
    }

    public final void zza(zzci zzci, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzci);
        zzd.zza(zza, (IInterface) zzem);
        zza(107, zza);
    }

    public final void zza(zzdq zzdq, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzdq);
        zzd.zza(zza, (IInterface) zzem);
        zza(108, zza);
    }

    public final void zza(zzcs zzcs, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzcs);
        zzd.zza(zza, (IInterface) zzem);
        zza(109, zza);
    }

    public final void zza(zzcu zzcu, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzcu);
        zzd.zza(zza, (IInterface) zzem);
        zza(111, zza);
    }

    public final void zza(zzcw zzcw, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzcw);
        zzd.zza(zza, (IInterface) zzem);
        zza(112, zza);
    }

    public final void zza(zzec zzec, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzec);
        zzd.zza(zza, (IInterface) zzem);
        zza(113, zza);
    }

    public final void zza(zzee zzee, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzee);
        zzd.zza(zza, (IInterface) zzem);
        zza(114, zza);
    }

    public final void zza(zzda zzda, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzda);
        zzd.zza(zza, (IInterface) zzem);
        zza(115, zza);
    }

    public final void zza(zzdk zzdk, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzdk);
        zzd.zza(zza, (IInterface) zzem);
        zza(116, zza);
    }

    public final void zza(zzck zzck, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzck);
        zzd.zza(zza, (IInterface) zzem);
        zza(117, zza);
    }

    public final void zza(zzce zzce, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzce);
        zzd.zza(zza, (IInterface) zzem);
        zza(119, zza);
    }

    public final void zza(zzby zzby, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzby);
        zzd.zza(zza, (IInterface) zzem);
        zza(120, zza);
    }

    public final void zza(zzcg zzcg, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzcg);
        zzd.zza(zza, (IInterface) zzem);
        zza(121, zza);
    }

    public final void zza(zzdg zzdg, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzdg);
        zzd.zza(zza, (IInterface) zzem);
        zza(122, zza);
    }

    public final void zza(zzdu zzdu, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzdu);
        zzd.zza(zza, (IInterface) zzem);
        zza(123, zza);
    }

    public final void zza(zzcy zzcy, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzcy);
        zzd.zza(zza, (IInterface) zzem);
        zza(124, zza);
    }

    public final void zza(zzdc zzdc, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzdc);
        zzd.zza(zza, (IInterface) zzem);
        zza(126, zza);
    }

    public final void zza(zzdi zzdi, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzdi);
        zzd.zza(zza, (IInterface) zzem);
        zza(127, zza);
    }

    public final void zza(zzde zzde, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzde);
        zzd.zza(zza, (IInterface) zzem);
        zza(128, zza);
    }

    public final void zza(zzds zzds, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzds);
        zzd.zza(zza, (IInterface) zzem);
        zza(129, zza);
    }

    public final void zza(zzdw zzdw, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzdw);
        zzd.zza(zza, (IInterface) zzem);
        zza(130, zza);
    }

    public final void zza(zzea zzea, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzea);
        zzd.zza(zza, (IInterface) zzem);
        zza(131, zza);
    }

    public final void zza(zzcm zzcm, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzcm);
        zzd.zza(zza, (IInterface) zzem);
        zza(132, zza);
    }

    public final void zza(zzdy zzdy, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzdy);
        zzd.zza(zza, (IInterface) zzem);
        zza(133, zza);
    }

    public final void zza(zzco zzco, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzco);
        zzd.zza(zza, (IInterface) zzem);
        zza(134, zza);
    }

    public final void zza(zzei zzei, zzem zzem) throws RemoteException {
        Parcel zza = zza();
        zzd.zza(zza, (Parcelable) zzei);
        zzd.zza(zza, (IInterface) zzem);
        zza(135, zza);
    }
}
