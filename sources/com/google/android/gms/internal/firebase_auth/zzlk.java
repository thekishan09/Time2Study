package com.google.android.gms.internal.firebase_auth;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzlk {
    /* access modifiers changed from: private */
    public static boolean zzd(byte b) {
        return b >= 0;
    }

    /* access modifiers changed from: private */
    public static boolean zze(byte b) {
        return b < -32;
    }

    /* access modifiers changed from: private */
    public static boolean zzf(byte b) {
        return b < -16;
    }

    /* access modifiers changed from: private */
    public static void zzb(byte b, char[] cArr, int i) {
        cArr[i] = (char) b;
    }

    /* access modifiers changed from: private */
    public static void zzb(byte b, byte b2, char[] cArr, int i) throws zzir {
        if (b < -62 || zzg(b2)) {
            throw zzir.zzi();
        }
        cArr[i] = (char) (((b & Ascii.f325US) << 6) | (b2 & 63));
    }

    /* access modifiers changed from: private */
    public static void zzb(byte b, byte b2, byte b3, char[] cArr, int i) throws zzir {
        if (zzg(b2) || ((b == -32 && b2 < -96) || ((b == -19 && b2 >= -96) || zzg(b3)))) {
            throw zzir.zzi();
        }
        cArr[i] = (char) (((b & Ascii.f322SI) << Ascii.f315FF) | ((b2 & 63) << 6) | (b3 & 63));
    }

    /* access modifiers changed from: private */
    public static void zzb(byte b, byte b2, byte b3, byte b4, char[] cArr, int i) throws zzir {
        if (zzg(b2) || (((b << Ascii.f316FS) + (b2 + 112)) >> 30) != 0 || zzg(b3) || zzg(b4)) {
            throw zzir.zzi();
        }
        byte b5 = ((b & 7) << Ascii.DC2) | ((b2 & 63) << Ascii.f315FF) | ((b3 & 63) << 6) | (b4 & 63);
        cArr[i] = (char) ((b5 >>> 10) + 55232);
        cArr[i + 1] = (char) ((b5 & UnsignedBytes.MAX_VALUE) + Ascii.NUL);
    }

    private static boolean zzg(byte b) {
        return b > -65;
    }
}
