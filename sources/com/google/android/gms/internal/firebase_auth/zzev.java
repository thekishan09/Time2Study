package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzp;
import com.google.firebase.auth.api.internal.zzgb;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzev implements zzgb<zzp.zzf> {
    private String zza;

    public zzev(String str) {
        this.zza = Preconditions.checkNotEmpty(str);
    }

    public final /* synthetic */ zzjr zza() {
        return (zzp.zzf) ((zzig) zzp.zzf.zza().zza(this.zza).zzf());
    }
}
