package com.google.android.gms.internal.firebase_auth;

import java.util.List;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzla extends RuntimeException {
    private final List<String> zza = null;

    public zzla(zzjr zzjr) {
        super("Message was missing required fields.  (Lite runtime could not determine which fields were missing).");
    }
}
