package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzp;
import com.google.firebase.auth.api.internal.zzgb;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzeo implements zzgb<zzp.zzl.zza> {
    private final String zza;

    public zzeo(String str) {
        this.zza = Preconditions.checkNotEmpty(str);
    }

    public final /* synthetic */ zzjr zza() {
        return (zzp.zzl.zza) ((zzig) zzp.zzl.zza.zza().zza(this.zza).zzf());
    }
}
