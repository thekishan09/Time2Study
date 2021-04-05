package com.google.android.gms.auth.api.signin.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: com.google.android.gms:play-services-auth@@18.1.0 */
public final class SignInConfiguration extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Parcelable.Creator<SignInConfiguration> CREATOR = new zzx();
    private final String zzcp;
    private GoogleSignInOptions zzcq;

    public SignInConfiguration(String str, GoogleSignInOptions googleSignInOptions) {
        this.zzcp = Preconditions.checkNotEmpty(str);
        this.zzcq = googleSignInOptions;
    }

    public final GoogleSignInOptions zzp() {
        return this.zzcq;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzcp, false);
        SafeParcelWriter.writeParcelable(parcel, 5, this.zzcq, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof SignInConfiguration)) {
            return false;
        }
        SignInConfiguration signInConfiguration = (SignInConfiguration) obj;
        if (this.zzcp.equals(signInConfiguration.zzcp)) {
            GoogleSignInOptions googleSignInOptions = this.zzcq;
            if (googleSignInOptions == null) {
                if (signInConfiguration.zzcq == null) {
                    return true;
                }
            } else if (googleSignInOptions.equals(signInConfiguration.zzcq)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return new HashAccumulator().addObject(this.zzcp).addObject(this.zzcq).hash();
    }
}
