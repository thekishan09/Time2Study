package com.google.android.gms.auth.api.signin;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import javax.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-auth@@18.1.0 */
public class SignInAccount extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Parcelable.Creator<SignInAccount> CREATOR = new zzd();
    @Deprecated
    private String zzby;
    private GoogleSignInAccount zzbz;
    @Deprecated
    private String zzca;

    SignInAccount(String str, GoogleSignInAccount googleSignInAccount, String str2) {
        this.zzbz = googleSignInAccount;
        this.zzby = Preconditions.checkNotEmpty(str, "8.3 and 8.4 SDKs require non-null email");
        this.zzca = Preconditions.checkNotEmpty(str2, "8.3 and 8.4 SDKs require non-null userId");
    }

    @Nullable
    public final GoogleSignInAccount getGoogleSignInAccount() {
        return this.zzbz;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 4, this.zzby, false);
        SafeParcelWriter.writeParcelable(parcel, 7, this.zzbz, i, false);
        SafeParcelWriter.writeString(parcel, 8, this.zzca, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
