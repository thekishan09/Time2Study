package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.firebase.auth.ActionCodeSettings;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzde extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzde> CREATOR = new zzdd();
    private final String zza;
    private final ActionCodeSettings zzb;
    private final String zzc;

    public zzde(String str, ActionCodeSettings actionCodeSettings, String str2) {
        this.zza = str;
        this.zzb = actionCodeSettings;
        this.zzc = str2;
    }

    public final String zza() {
        return this.zza;
    }

    public final ActionCodeSettings zzb() {
        return this.zzb;
    }

    public final String zzc() {
        return this.zzc;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zza, false);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzb, i, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzc, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
