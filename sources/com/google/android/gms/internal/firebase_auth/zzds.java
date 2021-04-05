package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.firebase.auth.EmailAuthCredential;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzds extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzds> CREATOR = new zzdr();
    private final EmailAuthCredential zza;

    public zzds(EmailAuthCredential emailAuthCredential) {
        this.zza = emailAuthCredential;
    }

    public final EmailAuthCredential zza() {
        return this.zza;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zza, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
