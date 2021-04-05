package com.google.firebase.auth.internal;

import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.auth.ActionCodeInfo;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzc extends ActionCodeInfo {
    public zzc(String str) {
        this.email = Preconditions.checkNotEmpty(str);
    }
}
