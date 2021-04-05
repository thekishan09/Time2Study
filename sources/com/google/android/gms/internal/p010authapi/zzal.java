package com.google.android.gms.internal.p010authapi;

import android.util.Base64;
import java.util.Random;

/* renamed from: com.google.android.gms.internal.auth-api.zzal */
/* compiled from: com.google.android.gms:play-services-auth@@18.1.0 */
public final class zzal {
    private static final Random zzcy = new Random();

    public static String zzs() {
        byte[] bArr = new byte[16];
        zzcy.nextBytes(bArr);
        return Base64.encodeToString(bArr, 11);
    }
}
