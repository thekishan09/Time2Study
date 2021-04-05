package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.util.Strings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzga extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzga> CREATOR = new zzfz();
    private final int zza;
    private List<String> zzb;

    public final List<String> zza() {
        return this.zzb;
    }

    public zzga() {
        this((List<String>) null);
    }

    private zzga(List<String> list) {
        this.zza = 1;
        this.zzb = new ArrayList();
        if (list != null && !list.isEmpty()) {
            this.zzb.addAll(list);
        }
    }

    zzga(int i, List<String> list) {
        this.zza = i;
        if (list == null || list.isEmpty()) {
            this.zzb = Collections.emptyList();
            return;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            list.set(i2, Strings.emptyToNull(list.get(i2)));
        }
        this.zzb = Collections.unmodifiableList(list);
    }

    public static zzga zzb() {
        return new zzga((List<String>) null);
    }

    public static zzga zza(zzga zzga) {
        List<String> list;
        if (zzga != null) {
            list = zzga.zzb;
        } else {
            list = null;
        }
        return new zzga(list);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zza);
        SafeParcelWriter.writeStringList(parcel, 2, this.zzb, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
