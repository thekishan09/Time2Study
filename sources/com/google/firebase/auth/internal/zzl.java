package com.google.firebase.auth.internal;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.firebase_auth.zzfa;
import com.google.android.gms.internal.firebase_auth.zzfj;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.api.zza;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzl extends AbstractSafeParcelable implements UserInfo {
    public static final Parcelable.Creator<zzl> CREATOR = new zzk();
    private String zza;
    private String zzb;
    private String zzc;
    private String zzd;
    private Uri zze;
    private String zzf;
    private String zzg;
    private boolean zzh;
    private String zzi;

    public zzl(String str, String str2, String str3, String str4, String str5, String str6, boolean z, String str7) {
        this.zza = str;
        this.zzb = str2;
        this.zzf = str3;
        this.zzg = str4;
        this.zzc = str5;
        this.zzd = str6;
        if (!TextUtils.isEmpty(str6)) {
            this.zze = Uri.parse(this.zzd);
        }
        this.zzh = z;
        this.zzi = str7;
    }

    public zzl(zzfa zzfa, String str) {
        Preconditions.checkNotNull(zzfa);
        Preconditions.checkNotEmpty(str);
        this.zza = Preconditions.checkNotEmpty(zzfa.zzc());
        this.zzb = str;
        this.zzf = zzfa.zza();
        this.zzc = zzfa.zzd();
        Uri zze2 = zzfa.zze();
        if (zze2 != null) {
            this.zzd = zze2.toString();
            this.zze = zze2;
        }
        this.zzh = zzfa.zzb();
        this.zzi = null;
        this.zzg = zzfa.zzf();
    }

    public zzl(zzfj zzfj) {
        Preconditions.checkNotNull(zzfj);
        this.zza = zzfj.zza();
        this.zzb = Preconditions.checkNotEmpty(zzfj.zzd());
        this.zzc = zzfj.zzb();
        Uri zzc2 = zzfj.zzc();
        if (zzc2 != null) {
            this.zzd = zzc2.toString();
            this.zze = zzc2;
        }
        this.zzf = zzfj.zzg();
        this.zzg = zzfj.zze();
        this.zzh = false;
        this.zzi = zzfj.zzf();
    }

    public final String getUid() {
        return this.zza;
    }

    public final String getProviderId() {
        return this.zzb;
    }

    public final String getDisplayName() {
        return this.zzc;
    }

    public final Uri getPhotoUrl() {
        if (!TextUtils.isEmpty(this.zzd) && this.zze == null) {
            this.zze = Uri.parse(this.zzd);
        }
        return this.zze;
    }

    public final String getEmail() {
        return this.zzf;
    }

    public final String getPhoneNumber() {
        return this.zzg;
    }

    public final boolean isEmailVerified() {
        return this.zzh;
    }

    public final String zza() {
        return this.zzi;
    }

    public final String zzb() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.putOpt("userId", this.zza);
            jSONObject.putOpt("providerId", this.zzb);
            jSONObject.putOpt("displayName", this.zzc);
            jSONObject.putOpt("photoUrl", this.zzd);
            jSONObject.putOpt("email", this.zzf);
            jSONObject.putOpt("phoneNumber", this.zzg);
            jSONObject.putOpt("isEmailVerified", Boolean.valueOf(this.zzh));
            jSONObject.putOpt("rawUserInfo", this.zzi);
            return jSONObject.toString();
        } catch (JSONException e) {
            Log.d("DefaultAuthUserInfo", "Failed to jsonify this object");
            throw new zza((Throwable) e);
        }
    }

    public static zzl zza(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            return new zzl(jSONObject.optString("userId"), jSONObject.optString("providerId"), jSONObject.optString("email"), jSONObject.optString("phoneNumber"), jSONObject.optString("displayName"), jSONObject.optString("photoUrl"), jSONObject.optBoolean("isEmailVerified"), jSONObject.optString("rawUserInfo"));
        } catch (JSONException e) {
            Log.d("DefaultAuthUserInfo", "Failed to unpack UserInfo from JSON");
            throw new zza((Throwable) e);
        }
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, getUid(), false);
        SafeParcelWriter.writeString(parcel, 2, getProviderId(), false);
        SafeParcelWriter.writeString(parcel, 3, getDisplayName(), false);
        SafeParcelWriter.writeString(parcel, 4, this.zzd, false);
        SafeParcelWriter.writeString(parcel, 5, getEmail(), false);
        SafeParcelWriter.writeString(parcel, 6, getPhoneNumber(), false);
        SafeParcelWriter.writeBoolean(parcel, 7, isEmailVerified());
        SafeParcelWriter.writeString(parcel, 8, this.zzi, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
