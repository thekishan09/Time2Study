package com.google.firebase.auth.api.internal;

import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.LibraryVersion;
import com.google.android.gms.internal.firebase_auth.zzax;
import java.util.List;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzey {
    private final String zza;
    private final int zzb;
    private final int zzc = -1;

    private zzey(String str, int i) {
        this.zza = str;
        this.zzb = zzd(str);
    }

    public final String zza() {
        int i = this.zzb;
        if (i == -1) {
            return Integer.toString(this.zzc);
        }
        return String.format("X%s", new Object[]{Integer.toString(i)});
    }

    public static zzey zzb() {
        return new zzey(zzc("firebase-auth-compat"), -1);
    }

    /* access modifiers changed from: package-private */
    public final boolean zza(String str) {
        return this.zzb >= zzd(str);
    }

    /* access modifiers changed from: package-private */
    public final boolean zzb(String str) {
        return this.zzb >= zzd(str);
    }

    static String zzc() {
        return zzc("firebase-auth");
    }

    private static String zzc(String str) {
        String version = LibraryVersion.getInstance().getVersion(str);
        if (TextUtils.isEmpty(version) || version.equals("UNKNOWN")) {
            return "-1";
        }
        return version;
    }

    private static int zzd(String str) {
        try {
            List<String> zza2 = zzax.zza("[.-]").zza((CharSequence) str);
            if (zza2.size() == 1) {
                return Integer.parseInt(str);
            }
            if (zza2.size() >= 3) {
                return (Integer.parseInt(zza2.get(0)) * 1000000) + (Integer.parseInt(zza2.get(1)) * 1000) + Integer.parseInt(zza2.get(2));
            }
            return -1;
        } catch (IllegalArgumentException e) {
            if (!Log.isLoggable("LibraryVersionContainer", 3)) {
                return -1;
            }
            Log.d("LibraryVersionContainer", String.format("Version code parsing failed for: %s with exception %s.", new Object[]{str, e}));
            return -1;
        }
    }
}
