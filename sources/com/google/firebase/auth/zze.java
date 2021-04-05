package com.google.firebase.auth;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.firebase_auth.zzgc;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public class zze extends OAuthCredential {
    public static final Parcelable.Creator<zze> CREATOR = new zzd();
    private final String zza;
    private final String zzb;
    private final String zzc;
    private final zzgc zzd;
    private final String zze;
    private final String zzf;
    private final String zzg;

    zze(String str, String str2, String str3, zzgc zzgc, String str4, String str5, String str6) {
        this.zza = str;
        this.zzb = str2;
        this.zzc = str3;
        this.zzd = zzgc;
        this.zze = str4;
        this.zzf = str5;
        this.zzg = str6;
    }

    public static zze zza(String str, String str2, String str3) {
        return zza(str, str2, str3, (String) null, (String) null);
    }

    public static zze zza(String str, String str2, String str3, String str4, String str5) {
        Preconditions.checkNotEmpty(str, "Must specify a non-empty providerId");
        if (!TextUtils.isEmpty(str2) || !TextUtils.isEmpty(str3)) {
            return new zze(str, str2, str3, (zzgc) null, str4, str5, (String) null);
        }
        throw new IllegalArgumentException("Must specify an idToken or an accessToken.");
    }

    public static zze zza(zzgc zzgc) {
        Preconditions.checkNotNull(zzgc, "Must specify a non-null webSignInCredential");
        return new zze((String) null, (String) null, (String) null, zzgc, (String) null, (String) null, (String) null);
    }

    static zze zza(String str, String str2, String str3, String str4) {
        Preconditions.checkNotEmpty(str, "Must specify a non-empty providerId");
        if (!TextUtils.isEmpty(str2) || !TextUtils.isEmpty(str3)) {
            return new zze(str, str2, str3, (zzgc) null, (String) null, (String) null, str4);
        }
        throw new IllegalArgumentException("Must specify an idToken or an accessToken.");
    }

    public String getProvider() {
        return this.zza;
    }

    public String getSignInMethod() {
        return this.zza;
    }

    public String getIdToken() {
        return this.zzb;
    }

    public String getAccessToken() {
        return this.zzc;
    }

    public String getSecret() {
        return this.zzf;
    }

    public static zzgc zza(zze zze2, String str) {
        Preconditions.checkNotNull(zze2);
        zzgc zzgc = zze2.zzd;
        if (zzgc != null) {
            return zzgc;
        }
        return new zzgc(zze2.getIdToken(), zze2.getAccessToken(), zze2.getProvider(), (String) null, zze2.getSecret(), (String) null, str, zze2.zze, zze2.zzg);
    }

    public final AuthCredential zza() {
        return new zze(this.zza, this.zzb, this.zzc, this.zzd, this.zze, this.zzf, this.zzg);
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, getProvider(), false);
        SafeParcelWriter.writeString(parcel, 2, getIdToken(), false);
        SafeParcelWriter.writeString(parcel, 3, getAccessToken(), false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzd, i, false);
        SafeParcelWriter.writeString(parcel, 5, this.zze, false);
        SafeParcelWriter.writeString(parcel, 6, getSecret(), false);
        SafeParcelWriter.writeString(parcel, 7, this.zzg, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
