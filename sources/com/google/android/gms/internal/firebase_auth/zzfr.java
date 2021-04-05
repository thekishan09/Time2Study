package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.firebase_auth.zzp;
import com.google.firebase.auth.api.internal.zzgb;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzfr extends AbstractSafeParcelable implements zzgb<zzp.zzl> {
    public static final Parcelable.Creator<zzfr> CREATOR = new zzfu();
    private final String zza;
    private final long zzb;
    private final boolean zzc;
    private final String zzd;
    private final String zze;
    private final String zzf;
    private zzeo zzg;

    public zzfr(String str, long j, boolean z, String str2, String str3, String str4) {
        this.zza = Preconditions.checkNotEmpty(str);
        this.zzb = j;
        this.zzc = z;
        this.zzd = str2;
        this.zze = str3;
        this.zzf = str4;
    }

    public final String zzb() {
        return this.zza;
    }

    public final void zza(zzeo zzeo) {
        this.zzg = zzeo;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zza, false);
        SafeParcelWriter.writeLong(parcel, 2, this.zzb);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzc);
        SafeParcelWriter.writeString(parcel, 4, this.zzd, false);
        SafeParcelWriter.writeString(parcel, 5, this.zze, false);
        SafeParcelWriter.writeString(parcel, 6, this.zzf, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final /* synthetic */ zzjr zza() {
        zzp.zzl.zzb zza2 = zzp.zzl.zza().zza(this.zza);
        String str = this.zze;
        if (str != null) {
            zza2.zzc(str);
        }
        String str2 = this.zzf;
        if (str2 != null) {
            zza2.zzb(str2);
        }
        zzeo zzeo = this.zzg;
        if (zzeo != null) {
            zza2.zza((zzp.zzl.zza) zzeo.zza());
        }
        return (zzp.zzl) ((zzig) zza2.zzf());
    }
}
