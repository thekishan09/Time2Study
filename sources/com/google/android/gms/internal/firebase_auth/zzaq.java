package com.google.android.gms.internal.firebase_auth;

import java.io.Serializable;
import java.util.regex.Pattern;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzaq extends zzao implements Serializable {
    private final Pattern zza;

    zzaq(Pattern pattern) {
        this.zza = (Pattern) zzav.zza(pattern);
    }

    public final zzal zza(CharSequence charSequence) {
        return new zzap(this.zza.matcher(charSequence));
    }

    public final String toString() {
        return this.zza.toString();
    }
}
