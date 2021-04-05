package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.firebase.auth.UserProfileChangeRequest;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzef implements Parcelable.Creator<zzeg> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new zzeg[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        UserProfileChangeRequest userProfileChangeRequest = null;
        String str = null;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readHeader = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(readHeader);
            if (fieldId == 1) {
                userProfileChangeRequest = (UserProfileChangeRequest) SafeParcelReader.createParcelable(parcel, readHeader, UserProfileChangeRequest.CREATOR);
            } else if (fieldId != 2) {
                SafeParcelReader.skipUnknownField(parcel, readHeader);
            } else {
                str = SafeParcelReader.createString(parcel, readHeader);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
        return new zzeg(userProfileChangeRequest, str);
    }
}
