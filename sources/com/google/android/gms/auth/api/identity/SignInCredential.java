package com.google.android.gms.auth.api.identity;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: com.google.android.gms:play-services-auth@@18.1.0 */
public final class SignInCredential extends AbstractSafeParcelable {
    public static final Parcelable.Creator<SignInCredential> CREATOR = new zzg();
    private final String zzbd;
    private final String zzbe;
    private final String zzbf;
    private final String zzbg;
    private final Uri zzbh;
    private final String zzbi;
    private final String zzbj;

    public SignInCredential(String str, String str2, String str3, String str4, Uri uri, String str5, String str6) {
        this.zzbd = Preconditions.checkNotEmpty(str);
        this.zzbe = str2;
        this.zzbf = str3;
        this.zzbg = str4;
        this.zzbh = uri;
        this.zzbi = str5;
        this.zzbj = str6;
    }

    public final String getId() {
        return this.zzbd;
    }

    public final String getDisplayName() {
        return this.zzbe;
    }

    public final String getGivenName() {
        return this.zzbf;
    }

    public final String getFamilyName() {
        return this.zzbg;
    }

    public final Uri getProfilePictureUri() {
        return this.zzbh;
    }

    public final String getPassword() {
        return this.zzbi;
    }

    public final String getGoogleIdToken() {
        return this.zzbj;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzbd, this.zzbe, this.zzbf, this.zzbg, this.zzbh, this.zzbi, this.zzbj);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof SignInCredential)) {
            return false;
        }
        SignInCredential signInCredential = (SignInCredential) obj;
        if (!Objects.equal(this.zzbd, signInCredential.zzbd) || !Objects.equal(this.zzbe, signInCredential.zzbe) || !Objects.equal(this.zzbf, signInCredential.zzbf) || !Objects.equal(this.zzbg, signInCredential.zzbg) || !Objects.equal(this.zzbh, signInCredential.zzbh) || !Objects.equal(this.zzbi, signInCredential.zzbi) || !Objects.equal(this.zzbj, signInCredential.zzbj)) {
            return false;
        }
        return true;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, getId(), false);
        SafeParcelWriter.writeString(parcel, 2, getDisplayName(), false);
        SafeParcelWriter.writeString(parcel, 3, getGivenName(), false);
        SafeParcelWriter.writeString(parcel, 4, getFamilyName(), false);
        SafeParcelWriter.writeParcelable(parcel, 5, getProfilePictureUri(), i, false);
        SafeParcelWriter.writeString(parcel, 6, getPassword(), false);
        SafeParcelWriter.writeString(parcel, 7, getGoogleIdToken(), false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
