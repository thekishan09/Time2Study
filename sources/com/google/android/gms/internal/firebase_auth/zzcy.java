package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.firebase.auth.PhoneAuthCredential;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzcy extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzcy> CREATOR = new zzcx();
    private final String zza;
    private final PhoneAuthCredential zzb;

    public zzcy(String str, PhoneAuthCredential phoneAuthCredential) {
        this.zza = str;
        this.zzb = phoneAuthCredential;
    }

    public final String zza() {
        return this.zza;
    }

    public final PhoneAuthCredential zzb() {
        return this.zzb;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zza, false);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzb, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
