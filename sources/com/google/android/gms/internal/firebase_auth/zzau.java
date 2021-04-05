package com.google.android.gms.internal.firebase_auth;

import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzau {
    private static final Logger zza = Logger.getLogger(zzau.class.getName());
    private static final zzar zzb = new zza();

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    static final class zza implements zzar {
        private zza() {
        }

        public final zzao zza(String str) {
            return new zzaq(Pattern.compile(str));
        }
    }

    private zzau() {
    }

    static boolean zza(@NullableDecl String str) {
        return str == null || str.isEmpty();
    }

    static zzao zzb(String str) {
        zzav.zza(str);
        return zzb.zza(str);
    }
}
