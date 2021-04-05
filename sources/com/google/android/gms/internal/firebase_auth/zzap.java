package com.google.android.gms.internal.firebase_auth;

import java.util.regex.Matcher;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzap extends zzal {
    private final Matcher zza;

    zzap(Matcher matcher) {
        this.zza = (Matcher) zzav.zza(matcher);
    }

    public final boolean zza() {
        return this.zza.matches();
    }

    public final boolean zza(int i) {
        return this.zza.find(i);
    }

    public final int zzb() {
        return this.zza.end();
    }

    public final int zzc() {
        return this.zza.start();
    }
}
