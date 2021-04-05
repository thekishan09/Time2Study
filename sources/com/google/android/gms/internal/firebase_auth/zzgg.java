package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.internal.firebase_auth.zzp;
import com.google.firebase.auth.api.internal.zzen;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzgg extends AbstractSafeParcelable implements zzen<zzgg, zzp.zzu> {
    public static final Parcelable.Creator<zzgg> CREATOR = new zzgf();
    private String zza;
    private String zzb;
    private long zzc;
    private boolean zzd;

    public zzgg() {
    }

    zzgg(String str, String str2, long j, boolean z) {
        this.zza = str;
        this.zzb = str2;
        this.zzc = j;
        this.zzd = z;
    }

    public final String zzb() {
        return this.zza;
    }

    public final String zzc() {
        return this.zzb;
    }

    public final long zzd() {
        return this.zzc;
    }

    public final boolean zze() {
        return this.zzd;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zza, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzb, false);
        SafeParcelWriter.writeLong(parcel, 4, this.zzc);
        SafeParcelWriter.writeBoolean(parcel, 5, this.zzd);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final zzkb<zzp.zzu> zza() {
        return zzp.zzu.zze();
    }

    public final /* synthetic */ zzen zza(zzjr zzjr) {
        if (zzjr instanceof zzp.zzu) {
            zzp.zzu zzu = (zzp.zzu) zzjr;
            this.zza = Strings.emptyToNull(zzu.zza());
            this.zzb = Strings.emptyToNull(zzu.zzb());
            this.zzc = zzu.zzc();
            this.zzd = zzu.zzd();
            return this;
        }
        throw new IllegalArgumentException("The passed proto must be an instance of VerifyCustomTokenResponse.");
    }
}
