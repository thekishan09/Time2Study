package com.google.firebase.auth.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.firebase.auth.FirebaseUserMetadata;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzr implements FirebaseUserMetadata {
    public static final Parcelable.Creator<zzr> CREATOR = new zzq();
    private long zza;
    private long zzb;

    public zzr(long j, long j2) {
        this.zza = j;
        this.zzb = j2;
    }

    public final long getLastSignInTimestamp() {
        return this.zza;
    }

    public final long getCreationTimestamp() {
        return this.zzb;
    }

    public final JSONObject zza() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("lastSignInTimestamp", this.zza);
            jSONObject.put("creationTimestamp", this.zzb);
        } catch (JSONException e) {
        }
        return jSONObject;
    }

    public static zzr zza(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        try {
            return new zzr(jSONObject.getLong("lastSignInTimestamp"), jSONObject.getLong("creationTimestamp"));
        } catch (JSONException e) {
            return null;
        }
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeLong(parcel, 1, getLastSignInTimestamp());
        SafeParcelWriter.writeLong(parcel, 2, getCreationTimestamp());
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final int describeContents() {
        return 0;
    }
}
