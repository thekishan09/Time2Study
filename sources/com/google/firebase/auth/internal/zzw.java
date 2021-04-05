package com.google.firebase.auth.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.firebase.auth.MultiFactorInfo;
import com.google.firebase.auth.MultiFactorSession;
import com.google.firebase.auth.PhoneMultiFactorInfo;
import java.util.ArrayList;
import java.util.List;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzw extends MultiFactorSession {
    public static final Parcelable.Creator<zzw> CREATOR = new zzz();
    private String zza;
    private String zzb;
    private List<PhoneMultiFactorInfo> zzc;

    private zzw() {
    }

    zzw(String str, String str2, List<PhoneMultiFactorInfo> list) {
        this.zza = str;
        this.zzb = str2;
        this.zzc = list;
    }

    public static zzw zza(String str) {
        Preconditions.checkNotEmpty(str);
        zzw zzw = new zzw();
        zzw.zza = str;
        return zzw;
    }

    public static zzw zza(List<MultiFactorInfo> list, String str) {
        Preconditions.checkNotNull(list);
        Preconditions.checkNotEmpty(str);
        zzw zzw = new zzw();
        zzw.zzc = new ArrayList();
        for (MultiFactorInfo next : list) {
            if (next instanceof PhoneMultiFactorInfo) {
                zzw.zzc.add((PhoneMultiFactorInfo) next);
            }
        }
        zzw.zzb = str;
        return zzw;
    }

    public final String zza() {
        return this.zza;
    }

    public final String zzb() {
        return this.zzb;
    }

    public final boolean zzc() {
        return this.zza != null;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zza, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzb, false);
        SafeParcelWriter.writeTypedList(parcel, 3, this.zzc, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
