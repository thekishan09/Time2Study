package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.firebase_auth.zzp;
import com.google.firebase.auth.api.internal.zzen;
import java.util.List;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzey extends AbstractSafeParcelable implements zzen<zzey, zzp.zzg> {
    public static final Parcelable.Creator<zzey> CREATOR = new zzex();
    private zzfc zza;

    public zzey() {
    }

    zzey(zzfc zzfc) {
        zzfc zzfc2;
        if (zzfc == null) {
            zzfc2 = new zzfc();
        } else {
            zzfc2 = zzfc.zza(zzfc);
        }
        this.zza = zzfc2;
    }

    public final List<zzfa> zzb() {
        return this.zza.zza();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zza, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final zzkb<zzp.zzg> zza() {
        return zzp.zzg.zzb();
    }

    public final /* synthetic */ zzen zza(zzjr zzjr) {
        if (zzjr instanceof zzp.zzg) {
            zzp.zzg zzg = (zzp.zzg) zzjr;
            if (zzg.zza() == 0) {
                this.zza = new zzfc();
            } else {
                this.zza = zzfc.zza(zzg);
            }
            return this;
        }
        throw new IllegalArgumentException("The passed proto must be an instance of GetAccountInfoResponse.");
    }
}
