package com.google.firebase.auth.api.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.firebase_auth.zza;
import com.google.android.gms.internal.firebase_auth.zzd;
import com.google.android.gms.internal.firebase_auth.zzek;
import com.google.android.gms.internal.firebase_auth.zzem;
import com.google.android.gms.internal.firebase_auth.zzeq;
import com.google.android.gms.internal.firebase_auth.zzfa;
import com.google.android.gms.internal.firebase_auth.zzff;
import com.google.android.gms.internal.firebase_auth.zzfq;
import com.google.firebase.auth.PhoneAuthCredential;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public abstract class zzep extends zza implements zzem {
    public zzep() {
        super("com.google.firebase.auth.api.internal.IFirebaseAuthCallbacks");
    }

    /* access modifiers changed from: protected */
    public final boolean zza(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        switch (i) {
            case 1:
                zza((zzff) zzd.zza(parcel, zzff.CREATOR));
                return true;
            case 2:
                zza((zzff) zzd.zza(parcel, zzff.CREATOR), (zzfa) zzd.zza(parcel, zzfa.CREATOR));
                return true;
            case 3:
                zza((zzeq) zzd.zza(parcel, zzeq.CREATOR));
                return true;
            case 4:
                zza((zzfq) zzd.zza(parcel, zzfq.CREATOR));
                return true;
            case 5:
                zza((Status) zzd.zza(parcel, Status.CREATOR));
                return true;
            case 6:
                mo23337a_();
                return true;
            case 7:
                zzb();
                return true;
            case 8:
                zza(parcel.readString());
                return true;
            case 9:
                zzb(parcel.readString());
                return true;
            case 10:
                zza((PhoneAuthCredential) zzd.zza(parcel, PhoneAuthCredential.CREATOR));
                return true;
            case 11:
                zzc(parcel.readString());
                return true;
            case 12:
                zza((Status) zzd.zza(parcel, Status.CREATOR), (PhoneAuthCredential) zzd.zza(parcel, PhoneAuthCredential.CREATOR));
                return true;
            case 13:
                zzc();
                return true;
            case 14:
                zza((zzek) zzd.zza(parcel, zzek.CREATOR));
                return true;
            case 15:
                zza((zzem) zzd.zza(parcel, zzem.CREATOR));
                return true;
            default:
                return false;
        }
    }
}
