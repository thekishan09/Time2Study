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
public final class zzfl extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzfl> CREATOR = new zzfo();
    private List<zzfj> zza;

    public zzfl() {
        this.zza = new ArrayList();
    }

    zzfl(List<zzfj> list) {
        if (list == null || list.isEmpty()) {
            this.zza = Collections.emptyList();
        } else {
            this.zza = Collections.unmodifiableList(list);
        }
    }

    public final List<zzfj> zza() {
        return this.zza;
    }

    public static zzfl zza(zzfl zzfl) {
        List<zzfj> list = zzfl.zza;
        zzfl zzfl2 = new zzfl();
        if (list != null) {
            zzfl2.zza.addAll(list);
        }
        return zzfl2;
    }

    public static zzfl zza(List<zzu> list) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            zzu zzu = list.get(i);
            arrayList.add(new zzfj(Strings.emptyToNull(zzu.zzd()), Strings.emptyToNull(zzu.zzb()), Strings.emptyToNull(zzu.zzc()), Strings.emptyToNull(zzu.zza()), (String) null, Strings.emptyToNull(zzu.zzf()), Strings.emptyToNull(zzu.zze())));
        }
        return new zzfl(arrayList);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 2, this.zza, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
