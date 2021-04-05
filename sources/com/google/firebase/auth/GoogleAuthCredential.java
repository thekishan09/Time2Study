package com.google.firebase.auth;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.firebase_auth.zzgc;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public class GoogleAuthCredential extends AuthCredential {
    public static final Parcelable.Creator<GoogleAuthCredential> CREATOR = new zzu();
    private final String zza;
    private final String zzb;

    GoogleAuthCredential(String str, String str2) {
        if (str == null && str2 == null) {
            throw new IllegalArgumentException("Must specify an idToken or an accessToken.");
        }
        this.zza = zza(str, "idToken");
        this.zzb = zza(str2, "accessToken");
    }

    public String getProvider() {
        return "google.com";
    }

    public String getSignInMethod() {
        return "google.com";
    }

    public static zzgc zza(GoogleAuthCredential googleAuthCredential, String str) {
        Preconditions.checkNotNull(googleAuthCredential);
        return new zzgc(googleAuthCredential.zza, googleAuthCredential.zzb, googleAuthCredential.getProvider(), (String) null, (String) null, (String) null, str, (String) null, (String) null);
    }

    private static String zza(String str, String str2) {
        if (str == null || !TextUtils.isEmpty(str)) {
            return str;
        }
        throw new IllegalArgumentException(String.valueOf(str2).concat(" must not be empty"));
    }

    public final AuthCredential zza() {
        return new GoogleAuthCredential(this.zza, this.zzb);
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zza, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzb, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
