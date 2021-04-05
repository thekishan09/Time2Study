package com.google.android.gms.internal.firebase_auth;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.firebase.auth.zze;
import java.util.List;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzfa extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzfa> CREATOR = new zzez();
    private String zza;
    private String zzb;
    private boolean zzc;
    private String zzd;
    private String zze;
    private zzfl zzf;
    private String zzg;
    private String zzh;
    private long zzi;
    private long zzj;
    private boolean zzk;
    private zze zzl;
    private List<zzfh> zzm;

    public zzfa() {
        this.zzf = new zzfl();
    }

    public zzfa(String str, String str2, boolean z, String str3, String str4, zzfl zzfl, String str5, String str6, long j, long j2, boolean z2, zze zze2, List<zzfh> list) {
        zzfl zzfl2;
        this.zza = str;
        this.zzb = str2;
        this.zzc = z;
        this.zzd = str3;
        this.zze = str4;
        if (zzfl == null) {
            zzfl2 = new zzfl();
        } else {
            zzfl2 = zzfl.zza(zzfl);
        }
        this.zzf = zzfl2;
        this.zzg = str5;
        this.zzh = str6;
        this.zzi = j;
        this.zzj = j2;
        this.zzk = z2;
        this.zzl = zze2;
        this.zzm = list == null ? zzbj.zzf() : list;
    }

    public final String zza() {
        return this.zzb;
    }

    public final boolean zzb() {
        return this.zzc;
    }

    public final String zzc() {
        return this.zza;
    }

    public final String zzd() {
        return this.zzd;
    }

    public final Uri zze() {
        if (!TextUtils.isEmpty(this.zze)) {
            return Uri.parse(this.zze);
        }
        return null;
    }

    public final String zzf() {
        return this.zzh;
    }

    public final long zzg() {
        return this.zzi;
    }

    public final long zzh() {
        return this.zzj;
    }

    public final boolean zzi() {
        return this.zzk;
    }

    public final zzfa zza(String str) {
        this.zzb = str;
        return this;
    }

    public final zzfa zzb(String str) {
        this.zzd = str;
        return this;
    }

    public final zzfa zzc(String str) {
        this.zze = str;
        return this;
    }

    public final zzfa zzd(String str) {
        Preconditions.checkNotEmpty(str);
        this.zzg = str;
        return this;
    }

    public final zzfa zza(List<zzfj> list) {
        Preconditions.checkNotNull(list);
        zzfl zzfl = new zzfl();
        this.zzf = zzfl;
        zzfl.zza().addAll(list);
        return this;
    }

    public final zzfa zza(boolean z) {
        this.zzk = z;
        return this;
    }

    public final List<zzfj> zzj() {
        return this.zzf.zza();
    }

    public final zzfl zzk() {
        return this.zzf;
    }

    public final zze zzl() {
        return this.zzl;
    }

    public final zzfa zza(zze zze2) {
        this.zzl = zze2;
        return this;
    }

    public final List<zzfh> zzm() {
        return this.zzm;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zza, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzb, false);
        SafeParcelWriter.writeBoolean(parcel, 4, this.zzc);
        SafeParcelWriter.writeString(parcel, 5, this.zzd, false);
        SafeParcelWriter.writeString(parcel, 6, this.zze, false);
        SafeParcelWriter.writeParcelable(parcel, 7, this.zzf, i, false);
        SafeParcelWriter.writeString(parcel, 8, this.zzg, false);
        SafeParcelWriter.writeString(parcel, 9, this.zzh, false);
        SafeParcelWriter.writeLong(parcel, 10, this.zzi);
        SafeParcelWriter.writeLong(parcel, 11, this.zzj);
        SafeParcelWriter.writeBoolean(parcel, 12, this.zzk);
        SafeParcelWriter.writeParcelable(parcel, 13, this.zzl, i, false);
        SafeParcelWriter.writeTypedList(parcel, 14, this.zzm, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
