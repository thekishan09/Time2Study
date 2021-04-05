package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.firebase.auth.PhoneAuthCredential;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzdu extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzdu> CREATOR = new zzdt();
    private final PhoneAuthCredential zza;
    private final String zzb;

    public zzdu(PhoneAuthCredential phoneAuthCredential, String str) {
        this.zza = phoneAuthCredential;
        this.zzb = str;
    }

    public final PhoneAuthCredential zza() {
        return this.zza;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zza, i, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzb, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
