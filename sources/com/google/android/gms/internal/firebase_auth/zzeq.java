package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.internal.firebase_auth.zzp;
import com.google.firebase.auth.api.internal.zzen;
import java.util.ArrayList;
import java.util.List;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzeq extends AbstractSafeParcelable implements zzen<zzeq, zzp.zzb> {
    public static final Parcelable.Creator<zzeq> CREATOR = new zzep();
    private String zza;
    private boolean zzb;
    private String zzc;
    private boolean zzd;
    private zzga zze;
    private List<String> zzf;

    public zzeq() {
        this.zze = zzga.zzb();
    }

    public zzeq(String str, boolean z, String str2, boolean z2, zzga zzga, List<String> list) {
        this.zza = str;
        this.zzb = z;
        this.zzc = str2;
        this.zzd = z2;
        this.zze = zzga == null ? zzga.zzb() : zzga.zza(zzga);
        this.zzf = list;
    }

    public final List<String> zzb() {
        return this.zzf;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zza, false);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzb);
        SafeParcelWriter.writeString(parcel, 4, this.zzc, false);
        SafeParcelWriter.writeBoolean(parcel, 5, this.zzd);
        SafeParcelWriter.writeParcelable(parcel, 6, this.zze, i, false);
        SafeParcelWriter.writeStringList(parcel, 7, this.zzf, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final zzkb<zzp.zzb> zza() {
        return zzp.zzb.zzi();
    }

    public final /* synthetic */ zzen zza(zzjr zzjr) {
        zzga zzga;
        if (zzjr instanceof zzp.zzb) {
            zzp.zzb zzb2 = (zzp.zzb) zzjr;
            this.zza = Strings.emptyToNull(zzb2.zza());
            this.zzb = zzb2.zzd();
            this.zzc = Strings.emptyToNull(zzb2.zze());
            this.zzd = zzb2.zzf();
            if (zzb2.zzc() == 0) {
                zzga = zzga.zzb();
            } else {
                zzga = new zzga(1, new ArrayList(zzb2.zzb()));
            }
            this.zze = zzga;
            this.zzf = zzb2.zzh() == 0 ? new ArrayList<>(0) : zzb2.zzg();
            return this;
        }
        throw new IllegalArgumentException("The passed proto must be an instance of CreateAuthUriResponse.");
    }
}
