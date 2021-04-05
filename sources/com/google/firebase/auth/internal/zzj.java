package com.google.firebase.auth.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.firebase.auth.AdditionalUserInfo;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.zze;
import java.util.List;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzj implements AuthResult {
    public static final Parcelable.Creator<zzj> CREATOR = new zzi();
    private zzp zza;
    private zzh zzb;
    private zze zzc;

    zzj(zzp zzp, zzh zzh, zze zze) {
        this.zza = zzp;
        this.zzb = zzh;
        this.zzc = zze;
    }

    public zzj(zzp zzp) {
        zzp zzp2 = (zzp) Preconditions.checkNotNull(zzp);
        this.zza = zzp2;
        List<zzl> zzh = zzp2.zzh();
        this.zzb = null;
        for (int i = 0; i < zzh.size(); i++) {
            if (!TextUtils.isEmpty(zzh.get(i).zza())) {
                this.zzb = new zzh(zzh.get(i).getProviderId(), zzh.get(i).zza(), zzp.zzi());
            }
        }
        if (this.zzb == null) {
            this.zzb = new zzh(zzp.zzi());
        }
        this.zzc = zzp.zzj();
    }

    public final FirebaseUser getUser() {
        return this.zza;
    }

    public final AdditionalUserInfo getAdditionalUserInfo() {
        return this.zzb;
    }

    public final AuthCredential getCredential() {
        return this.zzc;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, getUser(), i, false);
        SafeParcelWriter.writeParcelable(parcel, 2, getAdditionalUserInfo(), i, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzc, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final int describeContents() {
        return 0;
    }
}
