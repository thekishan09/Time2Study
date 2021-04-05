package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzlv;
import com.google.firebase.auth.api.internal.zzgb;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzew implements zzgb<zzlv.zza> {
    private String zza = zzet.REFRESH_TOKEN.toString();
    private String zzb;

    public zzew(String str) {
        this.zzb = Preconditions.checkNotEmpty(str);
    }

    public final /* synthetic */ zzjr zza() {
        return (zzlv.zza) ((zzig) zzlv.zza.zza().zza(this.zza).zzb(this.zzb).zzf());
    }
}
