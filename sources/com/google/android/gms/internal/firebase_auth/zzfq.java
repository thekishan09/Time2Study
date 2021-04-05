package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.internal.firebase_auth.zzp;
import com.google.firebase.auth.api.internal.zzen;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzfq extends AbstractSafeParcelable implements zzen<zzfq, zzp.zzk> {
    public static final Parcelable.Creator<zzfq> CREATOR = new zzfs();
    private String zza;
    private String zzb;
    private String zzc;
    private zzfh zzd;

    public zzfq() {
    }

    zzfq(String str, String str2, String str3, zzfh zzfh) {
        this.zza = str;
        this.zzb = str2;
        this.zzc = str3;
        this.zzd = zzfh;
    }

    public final String zzb() {
        return this.zza;
    }

    public final String zzc() {
        return this.zzb;
    }

    public final String zzd() {
        return this.zzc;
    }

    public final zzfh zze() {
        return this.zzd;
    }

    public final boolean zzf() {
        return this.zza != null;
    }

    public final boolean zzg() {
        return this.zzb != null;
    }

    public final boolean zzh() {
        return this.zzc != null;
    }

    public final boolean zzi() {
        return this.zzd != null;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zza, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzb, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzc, false);
        SafeParcelWriter.writeParcelable(parcel, 5, this.zzd, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final zzkb<zzp.zzk> zza() {
        return zzp.zzk.zzf();
    }

    public final /* synthetic */ zzen zza(zzjr zzjr) {
        String str;
        if (zzjr instanceof zzp.zzk) {
            zzp.zzk zzk = (zzp.zzk) zzjr;
            this.zza = Strings.emptyToNull(zzk.zza());
            this.zzb = Strings.emptyToNull(zzk.zzb());
            switch (zzfp.zza[zzk.zzc().ordinal()]) {
                case 1:
                    str = "VERIFY_EMAIL";
                    break;
                case 2:
                    str = "PASSWORD_RESET";
                    break;
                case 3:
                    str = "EMAIL_SIGNIN";
                    break;
                case 4:
                    str = "VERIFY_BEFORE_UPDATE_EMAIL";
                    break;
                case 5:
                    str = "RECOVER_EMAIL";
                    break;
                case 6:
                    str = "REVERT_SECOND_FACTOR_ADDITION";
                    break;
                default:
                    str = null;
                    break;
            }
            this.zzc = str;
            if (zzk.zzd()) {
                this.zzd = zzfh.zza(zzk.zze());
            }
            return this;
        }
        throw new IllegalArgumentException("The passed proto must be an instance of ResetPasswordResponse.");
    }
}
