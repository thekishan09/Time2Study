package com.google.firebase.auth.api.internal;

import com.google.android.gms.internal.firebase_auth.zzff;
import com.google.android.gms.internal.firebase_auth.zzfw;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.auth.internal.zzy;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzah implements zzfr<zzff> {
    private final /* synthetic */ UserProfileChangeRequest zza;
    private final /* synthetic */ zzee zzb;
    private final /* synthetic */ zzb zzc;

    zzah(zzb zzb2, UserProfileChangeRequest userProfileChangeRequest, zzee zzee) {
        this.zzc = zzb2;
        this.zza = userProfileChangeRequest;
        this.zzb = zzee;
    }

    public final void zza(String str) {
        this.zzb.zza(zzy.zza(str));
    }

    public final /* synthetic */ void zza(Object obj) {
        zzff zzff = (zzff) obj;
        zzfw zzfw = new zzfw();
        zzfw.zzb(zzff.zzd());
        if (this.zza.zzb() || this.zza.getDisplayName() != null) {
            zzfw.zze(this.zza.getDisplayName());
        }
        if (this.zza.zzc() || this.zza.getPhotoUri() != null) {
            zzfw.zzf(this.zza.zza());
        }
        this.zzc.zza(this.zzb, zzff, zzfw, (zzfo) this);
    }
}
