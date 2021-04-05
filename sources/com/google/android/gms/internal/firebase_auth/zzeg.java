package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.firebase.auth.UserProfileChangeRequest;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzeg extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzeg> CREATOR = new zzef();
    private final UserProfileChangeRequest zza;
    private final String zzb;

    public zzeg(UserProfileChangeRequest userProfileChangeRequest, String str) {
        this.zza = userProfileChangeRequest;
        this.zzb = str;
    }

    public final UserProfileChangeRequest zza() {
        return this.zza;
    }

    public final String zzb() {
        return this.zzb;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zza, i, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzb, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
