package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzdm extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzdm> CREATOR = new zzdl();
    private final zzgc zza;

    public zzdm(zzgc zzgc) {
        this.zza = zzgc;
    }

    public final zzgc zza() {
        return this.zza;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zza, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
