package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzev {
    private String zza;

    public zzev(String str) {
        this.zza = Preconditions.checkNotEmpty(str);
    }

    public final zzew zza() {
        return new zzew(this.zza, (zzet) null);
    }
}
