package com.google.firebase.auth.api.internal;

import android.content.Context;
import com.google.android.gms.common.api.Api;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzeu {
    public static final Api<zzew> zza = new Api<>("InternalFirebaseAuth.FIREBASE_AUTH_API", zzc, zzb);
    private static final Api.ClientKey<zzeh> zzb = new Api.ClientKey<>();
    private static final Api.AbstractClientBuilder<zzeh, zzew> zzc = new zzet();

    public static zzas zza(Context context, zzew zzew) {
        return new zzas(context, zzew);
    }
}
