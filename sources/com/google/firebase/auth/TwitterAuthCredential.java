package com.google.firebase.auth;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.firebase_auth.zzgc;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public class TwitterAuthCredential extends AuthCredential {
    public static final Parcelable.Creator<TwitterAuthCredential> CREATOR = new zzaa();
    private String zza;
    private String zzb;

    TwitterAuthCredential(String str, String str2) {
        this.zza = Preconditions.checkNotEmpty(str);
        this.zzb = Preconditions.checkNotEmpty(str2);
    }

    public String getProvider() {
        return "twitter.com";
    }

    public String getSignInMethod() {
        return "twitter.com";
    }

    public static zzgc zza(TwitterAuthCredential twitterAuthCredential, String str) {
        Preconditions.checkNotNull(twitterAuthCredential);
        return new zzgc((String) null, twitterAuthCredential.zza, twitterAuthCredential.getProvider(), (String) null, twitterAuthCredential.zzb, (String) null, str, (String) null, (String) null);
    }

    public final AuthCredential zza() {
        return new TwitterAuthCredential(this.zza, this.zzb);
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zza, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzb, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
