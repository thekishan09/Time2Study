package com.google.firebase.auth.internal;

import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.auth.ActionCodeMultiFactorInfo;
import com.google.firebase.auth.MultiFactorInfo;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzf extends ActionCodeMultiFactorInfo {
    private final MultiFactorInfo zza;

    public zzf(String str, MultiFactorInfo multiFactorInfo) {
        this.email = Preconditions.checkNotEmpty(str);
        this.zza = (MultiFactorInfo) Preconditions.checkNotNull(multiFactorInfo);
    }

    public final MultiFactorInfo getMultiFactorInfo() {
        return this.zza;
    }
}
