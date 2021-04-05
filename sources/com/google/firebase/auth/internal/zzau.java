package com.google.firebase.auth.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.firebase_auth.zzbj;
import com.google.firebase.auth.MultiFactorInfo;
import com.google.firebase.auth.PhoneMultiFactorInfo;
import java.util.ArrayList;
import java.util.List;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzau extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzau> CREATOR = new zzat();
    private final List<PhoneMultiFactorInfo> zza;

    zzau(List<PhoneMultiFactorInfo> list) {
        this.zza = list == null ? zzbj.zzf() : list;
    }

    public static zzau zza(List<MultiFactorInfo> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (MultiFactorInfo next : list) {
            if (next instanceof PhoneMultiFactorInfo) {
                arrayList.add((PhoneMultiFactorInfo) next);
            }
        }
        return new zzau(arrayList);
    }

    public final List<MultiFactorInfo> zza() {
        ArrayList arrayList = new ArrayList();
        for (PhoneMultiFactorInfo add : this.zza) {
            arrayList.add(add);
        }
        return arrayList;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 1, this.zza, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
