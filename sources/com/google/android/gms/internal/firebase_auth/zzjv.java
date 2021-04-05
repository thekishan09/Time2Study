package com.google.android.gms.internal.firebase_auth;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import sun.misc.Unsafe;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzjv<T> implements zzkh<T> {
    private static final int[] zza = new int[0];
    private static final Unsafe zzb = zzlf.zzc();
    private final int[] zzc;
    private final Object[] zzd;
    private final int zze;
    private final int zzf;
    private final zzjr zzg;
    private final boolean zzh;
    private final boolean zzi;
    private final boolean zzj;
    private final boolean zzk;
    private final int[] zzl;
    private final int zzm;
    private final int zzn;
    private final zzjw zzo;
    private final zzjb zzp;
    private final zzkz<?, ?> zzq;
    private final zzhv<?> zzr;
    private final zzjk zzs;

    private zzjv(int[] iArr, Object[] objArr, int i, int i2, zzjr zzjr, boolean z, boolean z2, int[] iArr2, int i3, int i4, zzjw zzjw, zzjb zzjb, zzkz<?, ?> zzkz, zzhv<?> zzhv, zzjk zzjk) {
        this.zzc = iArr;
        this.zzd = objArr;
        this.zze = i;
        this.zzf = i2;
        this.zzi = zzjr instanceof zzig;
        this.zzj = z;
        this.zzh = zzhv != null && zzhv.zza(zzjr);
        this.zzk = false;
        this.zzl = iArr2;
        this.zzm = i3;
        this.zzn = i4;
        this.zzo = zzjw;
        this.zzp = zzjb;
        this.zzq = zzkz;
        this.zzr = zzhv;
        this.zzg = zzjr;
        this.zzs = zzjk;
    }

    /* JADX WARNING: Removed duplicated region for block: B:155:0x0349  */
    /* JADX WARNING: Removed duplicated region for block: B:171:0x03a2  */
    /* JADX WARNING: Removed duplicated region for block: B:175:0x03af A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static <T> com.google.android.gms.internal.firebase_auth.zzjv<T> zza(java.lang.Class<T> r33, com.google.android.gms.internal.firebase_auth.zzjp r34, com.google.android.gms.internal.firebase_auth.zzjw r35, com.google.android.gms.internal.firebase_auth.zzjb r36, com.google.android.gms.internal.firebase_auth.zzkz<?, ?> r37, com.google.android.gms.internal.firebase_auth.zzhv<?> r38, com.google.android.gms.internal.firebase_auth.zzjk r39) {
        /*
            r0 = r34
            boolean r1 = r0 instanceof com.google.android.gms.internal.firebase_auth.zzkf
            if (r1 == 0) goto L_0x0422
            com.google.android.gms.internal.firebase_auth.zzkf r0 = (com.google.android.gms.internal.firebase_auth.zzkf) r0
            int r1 = r0.zza()
            int r2 = com.google.android.gms.internal.firebase_auth.zzig.zze.zzi
            r3 = 0
            if (r1 != r2) goto L_0x0013
            r11 = 1
            goto L_0x0014
        L_0x0013:
            r11 = 0
        L_0x0014:
            java.lang.String r1 = r0.zzd()
            int r2 = r1.length()
            char r5 = r1.charAt(r3)
            r6 = 55296(0xd800, float:7.7486E-41)
            if (r5 < r6) goto L_0x0033
            r5 = 1
        L_0x0028:
            int r7 = r5 + 1
            char r5 = r1.charAt(r5)
            if (r5 < r6) goto L_0x0034
            r5 = r7
            goto L_0x0028
        L_0x0033:
            r7 = 1
        L_0x0034:
            int r5 = r7 + 1
            char r7 = r1.charAt(r7)
            if (r7 < r6) goto L_0x0053
            r7 = r7 & 8191(0x1fff, float:1.1478E-41)
            r9 = 13
        L_0x0040:
            int r10 = r5 + 1
            char r5 = r1.charAt(r5)
            if (r5 < r6) goto L_0x0050
            r5 = r5 & 8191(0x1fff, float:1.1478E-41)
            int r5 = r5 << r9
            r7 = r7 | r5
            int r9 = r9 + 13
            r5 = r10
            goto L_0x0040
        L_0x0050:
            int r5 = r5 << r9
            r7 = r7 | r5
            r5 = r10
        L_0x0053:
            if (r7 != 0) goto L_0x0067
            int[] r7 = zza
            r14 = r7
            r7 = 0
            r9 = 0
            r10 = 0
            r12 = 0
            r13 = 0
            r15 = 0
            goto L_0x0180
        L_0x0067:
            int r7 = r5 + 1
            char r5 = r1.charAt(r5)
            if (r5 < r6) goto L_0x0086
            r5 = r5 & 8191(0x1fff, float:1.1478E-41)
            r9 = 13
        L_0x0073:
            int r10 = r7 + 1
            char r7 = r1.charAt(r7)
            if (r7 < r6) goto L_0x0083
            r7 = r7 & 8191(0x1fff, float:1.1478E-41)
            int r7 = r7 << r9
            r5 = r5 | r7
            int r9 = r9 + 13
            r7 = r10
            goto L_0x0073
        L_0x0083:
            int r7 = r7 << r9
            r5 = r5 | r7
            r7 = r10
        L_0x0086:
            int r9 = r7 + 1
            char r7 = r1.charAt(r7)
            if (r7 < r6) goto L_0x00a6
            r7 = r7 & 8191(0x1fff, float:1.1478E-41)
            r10 = 13
        L_0x0093:
            int r12 = r9 + 1
            char r9 = r1.charAt(r9)
            if (r9 < r6) goto L_0x00a3
            r9 = r9 & 8191(0x1fff, float:1.1478E-41)
            int r9 = r9 << r10
            r7 = r7 | r9
            int r10 = r10 + 13
            r9 = r12
            goto L_0x0093
        L_0x00a3:
            int r9 = r9 << r10
            r7 = r7 | r9
            r9 = r12
        L_0x00a6:
            int r10 = r9 + 1
            char r9 = r1.charAt(r9)
            if (r9 < r6) goto L_0x00c6
            r9 = r9 & 8191(0x1fff, float:1.1478E-41)
            r12 = 13
        L_0x00b3:
            int r13 = r10 + 1
            char r10 = r1.charAt(r10)
            if (r10 < r6) goto L_0x00c3
            r10 = r10 & 8191(0x1fff, float:1.1478E-41)
            int r10 = r10 << r12
            r9 = r9 | r10
            int r12 = r12 + 13
            r10 = r13
            goto L_0x00b3
        L_0x00c3:
            int r10 = r10 << r12
            r9 = r9 | r10
            r10 = r13
        L_0x00c6:
            int r12 = r10 + 1
            char r10 = r1.charAt(r10)
            if (r10 < r6) goto L_0x00e6
            r10 = r10 & 8191(0x1fff, float:1.1478E-41)
            r13 = 13
        L_0x00d3:
            int r14 = r12 + 1
            char r12 = r1.charAt(r12)
            if (r12 < r6) goto L_0x00e3
            r12 = r12 & 8191(0x1fff, float:1.1478E-41)
            int r12 = r12 << r13
            r10 = r10 | r12
            int r13 = r13 + 13
            r12 = r14
            goto L_0x00d3
        L_0x00e3:
            int r12 = r12 << r13
            r10 = r10 | r12
            r12 = r14
        L_0x00e6:
            int r13 = r12 + 1
            char r12 = r1.charAt(r12)
            if (r12 < r6) goto L_0x0106
            r12 = r12 & 8191(0x1fff, float:1.1478E-41)
            r14 = 13
        L_0x00f3:
            int r15 = r13 + 1
            char r13 = r1.charAt(r13)
            if (r13 < r6) goto L_0x0103
            r13 = r13 & 8191(0x1fff, float:1.1478E-41)
            int r13 = r13 << r14
            r12 = r12 | r13
            int r14 = r14 + 13
            r13 = r15
            goto L_0x00f3
        L_0x0103:
            int r13 = r13 << r14
            r12 = r12 | r13
            r13 = r15
        L_0x0106:
            int r14 = r13 + 1
            char r13 = r1.charAt(r13)
            if (r13 < r6) goto L_0x0128
            r13 = r13 & 8191(0x1fff, float:1.1478E-41)
            r15 = 13
        L_0x0113:
            int r16 = r14 + 1
            char r14 = r1.charAt(r14)
            if (r14 < r6) goto L_0x0124
            r14 = r14 & 8191(0x1fff, float:1.1478E-41)
            int r14 = r14 << r15
            r13 = r13 | r14
            int r15 = r15 + 13
            r14 = r16
            goto L_0x0113
        L_0x0124:
            int r14 = r14 << r15
            r13 = r13 | r14
            r14 = r16
        L_0x0128:
            int r15 = r14 + 1
            char r14 = r1.charAt(r14)
            if (r14 < r6) goto L_0x014c
            r14 = r14 & 8191(0x1fff, float:1.1478E-41)
            r16 = 13
        L_0x0135:
            int r17 = r15 + 1
            char r15 = r1.charAt(r15)
            if (r15 < r6) goto L_0x0147
            r15 = r15 & 8191(0x1fff, float:1.1478E-41)
            int r15 = r15 << r16
            r14 = r14 | r15
            int r16 = r16 + 13
            r15 = r17
            goto L_0x0135
        L_0x0147:
            int r15 = r15 << r16
            r14 = r14 | r15
            r15 = r17
        L_0x014c:
            int r16 = r15 + 1
            char r15 = r1.charAt(r15)
            if (r15 < r6) goto L_0x0172
            r15 = r15 & 8191(0x1fff, float:1.1478E-41)
            r3 = r16
            r16 = 13
        L_0x015b:
            int r17 = r3 + 1
            char r3 = r1.charAt(r3)
            if (r3 < r6) goto L_0x016d
            r3 = r3 & 8191(0x1fff, float:1.1478E-41)
            int r3 = r3 << r16
            r15 = r15 | r3
            int r16 = r16 + 13
            r3 = r17
            goto L_0x015b
        L_0x016d:
            int r3 = r3 << r16
            r15 = r15 | r3
            r16 = r17
        L_0x0172:
            int r3 = r15 + r13
            int r3 = r3 + r14
            int[] r3 = new int[r3]
            int r14 = r5 << 1
            int r14 = r14 + r7
            r7 = r14
            r14 = r3
            r3 = r5
            r5 = r16
        L_0x0180:
            sun.misc.Unsafe r8 = zzb
            java.lang.Object[] r16 = r0.zze()
            com.google.android.gms.internal.firebase_auth.zzjr r17 = r0.zzc()
            java.lang.Class r6 = r17.getClass()
            int r4 = r12 * 3
            int[] r4 = new int[r4]
            r17 = 1
            int r12 = r12 << 1
            java.lang.Object[] r12 = new java.lang.Object[r12]
            int r19 = r15 + r13
            r21 = r15
            r22 = r19
            r13 = 0
            r20 = 0
        L_0x01a3:
            if (r5 >= r2) goto L_0x03f7
            int r23 = r5 + 1
            char r5 = r1.charAt(r5)
            r24 = r2
            r2 = 55296(0xd800, float:7.7486E-41)
            if (r5 < r2) goto L_0x01d7
            r5 = r5 & 8191(0x1fff, float:1.1478E-41)
            r2 = r23
            r23 = 13
        L_0x01b8:
            int r25 = r2 + 1
            char r2 = r1.charAt(r2)
            r26 = r15
            r15 = 55296(0xd800, float:7.7486E-41)
            if (r2 < r15) goto L_0x01d1
            r2 = r2 & 8191(0x1fff, float:1.1478E-41)
            int r2 = r2 << r23
            r5 = r5 | r2
            int r23 = r23 + 13
            r2 = r25
            r15 = r26
            goto L_0x01b8
        L_0x01d1:
            int r2 = r2 << r23
            r5 = r5 | r2
            r2 = r25
            goto L_0x01db
        L_0x01d7:
            r26 = r15
            r2 = r23
        L_0x01db:
            int r15 = r2 + 1
            char r2 = r1.charAt(r2)
            r23 = r15
            r15 = 55296(0xd800, float:7.7486E-41)
            if (r2 < r15) goto L_0x020e
            r2 = r2 & 8191(0x1fff, float:1.1478E-41)
            r15 = r23
            r23 = 13
        L_0x01ef:
            int r25 = r15 + 1
            char r15 = r1.charAt(r15)
            r27 = r10
            r10 = 55296(0xd800, float:7.7486E-41)
            if (r15 < r10) goto L_0x0208
            r10 = r15 & 8191(0x1fff, float:1.1478E-41)
            int r10 = r10 << r23
            r2 = r2 | r10
            int r23 = r23 + 13
            r15 = r25
            r10 = r27
            goto L_0x01ef
        L_0x0208:
            int r10 = r15 << r23
            r2 = r2 | r10
            r15 = r25
            goto L_0x0212
        L_0x020e:
            r27 = r10
            r15 = r23
        L_0x0212:
            r10 = r2 & 255(0xff, float:3.57E-43)
            r23 = r9
            r9 = r2 & 1024(0x400, float:1.435E-42)
            if (r9 == 0) goto L_0x0220
            int r9 = r13 + 1
            r14[r13] = r20
            r13 = r9
        L_0x0220:
            r9 = 51
            r28 = r13
            if (r10 < r9) goto L_0x02bf
            int r9 = r15 + 1
            char r15 = r1.charAt(r15)
            r13 = 55296(0xd800, float:7.7486E-41)
            if (r15 < r13) goto L_0x024f
            r15 = r15 & 8191(0x1fff, float:1.1478E-41)
            r29 = 13
        L_0x0235:
            int r30 = r9 + 1
            char r9 = r1.charAt(r9)
            if (r9 < r13) goto L_0x024a
            r9 = r9 & 8191(0x1fff, float:1.1478E-41)
            int r9 = r9 << r29
            r15 = r15 | r9
            int r29 = r29 + 13
            r9 = r30
            r13 = 55296(0xd800, float:7.7486E-41)
            goto L_0x0235
        L_0x024a:
            int r9 = r9 << r29
            r15 = r15 | r9
            r9 = r30
        L_0x024f:
            int r13 = r10 + -51
            r29 = r9
            r9 = 9
            if (r13 == r9) goto L_0x0273
            r9 = 17
            if (r13 != r9) goto L_0x025d
            goto L_0x0273
        L_0x025d:
            r9 = 12
            if (r13 != r9) goto L_0x0271
            if (r11 != 0) goto L_0x0271
            int r9 = r20 / 3
            r13 = 1
            int r9 = r9 << r13
            int r9 = r9 + r13
            int r13 = r7 + 1
            r7 = r16[r7]
            r12[r9] = r7
            r7 = r13
            r13 = 1
            goto L_0x0280
        L_0x0271:
            r13 = 1
            goto L_0x0280
        L_0x0273:
            int r9 = r20 / 3
            r13 = 1
            int r9 = r9 << r13
            int r9 = r9 + r13
            int r17 = r7 + 1
            r7 = r16[r7]
            r12[r9] = r7
            r7 = r17
        L_0x0280:
            int r9 = r15 << 1
            r13 = r16[r9]
            boolean r15 = r13 instanceof java.lang.reflect.Field
            if (r15 == 0) goto L_0x028b
            java.lang.reflect.Field r13 = (java.lang.reflect.Field) r13
            goto L_0x0293
        L_0x028b:
            java.lang.String r13 = (java.lang.String) r13
            java.lang.reflect.Field r13 = zza((java.lang.Class<?>) r6, (java.lang.String) r13)
            r16[r9] = r13
        L_0x0293:
            r30 = r4
            r31 = r5
            long r4 = r8.objectFieldOffset(r13)
            int r5 = (int) r4
            int r9 = r9 + 1
            r4 = r16[r9]
            boolean r13 = r4 instanceof java.lang.reflect.Field
            if (r13 == 0) goto L_0x02a7
            java.lang.reflect.Field r4 = (java.lang.reflect.Field) r4
            goto L_0x02af
        L_0x02a7:
            java.lang.String r4 = (java.lang.String) r4
            java.lang.reflect.Field r4 = zza((java.lang.Class<?>) r6, (java.lang.String) r4)
            r16[r9] = r4
        L_0x02af:
            r9 = r5
            long r4 = r8.objectFieldOffset(r4)
            int r5 = (int) r4
            r17 = r3
            r4 = r5
            r5 = r9
            r15 = r29
            r13 = 0
            goto L_0x03ba
        L_0x02bf:
            r30 = r4
            r31 = r5
            int r4 = r7 + 1
            r5 = r16[r7]
            java.lang.String r5 = (java.lang.String) r5
            java.lang.reflect.Field r5 = zza((java.lang.Class<?>) r6, (java.lang.String) r5)
            r7 = 49
            r9 = 9
            if (r10 == r9) goto L_0x0332
            r9 = 17
            if (r10 != r9) goto L_0x02d9
            r13 = 1
            goto L_0x0333
        L_0x02d9:
            r9 = 27
            if (r10 == r9) goto L_0x0324
            if (r10 != r7) goto L_0x02e0
            goto L_0x0324
        L_0x02e0:
            r9 = 12
            if (r10 == r9) goto L_0x0315
            r9 = 30
            if (r10 == r9) goto L_0x0315
            r9 = 44
            if (r10 != r9) goto L_0x02ed
            goto L_0x0315
        L_0x02ed:
            r9 = 50
            if (r10 != r9) goto L_0x033d
            int r9 = r21 + 1
            r14[r21] = r20
            int r13 = r20 / 3
            r17 = 1
            int r13 = r13 << 1
            int r21 = r4 + 1
            r4 = r16[r4]
            r12[r13] = r4
            r4 = r2 & 2048(0x800, float:2.87E-42)
            if (r4 == 0) goto L_0x0310
            int r13 = r13 + 1
            int r4 = r21 + 1
            r21 = r16[r21]
            r12[r13] = r21
            r21 = r9
            goto L_0x033d
        L_0x0310:
            r4 = r21
            r21 = r9
            goto L_0x033d
        L_0x0315:
            if (r11 != 0) goto L_0x033d
            int r9 = r20 / 3
            r13 = 1
            int r9 = r9 << r13
            int r9 = r9 + r13
            int r13 = r4 + 1
            r4 = r16[r4]
            r12[r9] = r4
            r4 = r13
            goto L_0x033d
        L_0x0324:
            int r9 = r20 / 3
            r13 = 1
            int r9 = r9 << r13
            int r9 = r9 + r13
            int r17 = r4 + 1
            r4 = r16[r4]
            r12[r9] = r4
            r4 = r17
            goto L_0x033d
        L_0x0332:
            r13 = 1
        L_0x0333:
            int r9 = r20 / 3
            int r9 = r9 << r13
            int r9 = r9 + r13
            java.lang.Class r13 = r5.getType()
            r12[r9] = r13
        L_0x033d:
            r9 = r4
            long r4 = r8.objectFieldOffset(r5)
            int r5 = (int) r4
            r4 = r2 & 4096(0x1000, float:5.74E-42)
            r13 = 4096(0x1000, float:5.74E-42)
            if (r4 != r13) goto L_0x03a2
            r4 = 17
            if (r10 > r4) goto L_0x039c
            int r4 = r15 + 1
            char r13 = r1.charAt(r15)
            r15 = 55296(0xd800, float:7.7486E-41)
            if (r13 < r15) goto L_0x0372
            r13 = r13 & 8191(0x1fff, float:1.1478E-41)
            r18 = 13
        L_0x035c:
            int r25 = r4 + 1
            char r4 = r1.charAt(r4)
            if (r4 < r15) goto L_0x036e
            r4 = r4 & 8191(0x1fff, float:1.1478E-41)
            int r4 = r4 << r18
            r13 = r13 | r4
            int r18 = r18 + 13
            r4 = r25
            goto L_0x035c
        L_0x036e:
            int r4 = r4 << r18
            r13 = r13 | r4
            goto L_0x0374
        L_0x0372:
            r25 = r4
        L_0x0374:
            r4 = 1
            int r17 = r3 << 1
            int r18 = r13 / 32
            int r17 = r17 + r18
            r4 = r16[r17]
            boolean r15 = r4 instanceof java.lang.reflect.Field
            if (r15 == 0) goto L_0x0385
            java.lang.reflect.Field r4 = (java.lang.reflect.Field) r4
            goto L_0x038d
        L_0x0385:
            java.lang.String r4 = (java.lang.String) r4
            java.lang.reflect.Field r4 = zza((java.lang.Class<?>) r6, (java.lang.String) r4)
            r16[r17] = r4
        L_0x038d:
            r17 = r3
            long r3 = r8.objectFieldOffset(r4)
            int r4 = (int) r3
            int r13 = r13 % 32
            r15 = r25
            r3 = 55296(0xd800, float:7.7486E-41)
            goto L_0x03ab
        L_0x039c:
            r17 = r3
            r3 = 55296(0xd800, float:7.7486E-41)
            goto L_0x03a7
        L_0x03a2:
            r17 = r3
            r3 = 55296(0xd800, float:7.7486E-41)
        L_0x03a7:
            r4 = 1048575(0xfffff, float:1.469367E-39)
            r13 = 0
        L_0x03ab:
            r3 = 18
            if (r10 < r3) goto L_0x03b9
            if (r10 > r7) goto L_0x03b9
            int r3 = r22 + 1
            r14[r22] = r5
            r22 = r3
            r7 = r9
            goto L_0x03ba
        L_0x03b9:
            r7 = r9
        L_0x03ba:
            int r3 = r20 + 1
            r30[r20] = r31
            int r9 = r3 + 1
            r20 = r1
            r1 = r2 & 512(0x200, float:7.175E-43)
            if (r1 == 0) goto L_0x03c9
            r1 = 536870912(0x20000000, float:1.0842022E-19)
            goto L_0x03ca
        L_0x03c9:
            r1 = 0
        L_0x03ca:
            r2 = r2 & 256(0x100, float:3.59E-43)
            if (r2 == 0) goto L_0x03d1
            r2 = 268435456(0x10000000, float:2.5243549E-29)
            goto L_0x03d2
        L_0x03d1:
            r2 = 0
        L_0x03d2:
            r1 = r1 | r2
            int r2 = r10 << 20
            r1 = r1 | r2
            r1 = r1 | r5
            r30[r3] = r1
            int r1 = r9 + 1
            int r2 = r13 << 20
            r2 = r2 | r4
            r30[r9] = r2
            r5 = r15
            r3 = r17
            r9 = r23
            r2 = r24
            r15 = r26
            r10 = r27
            r13 = r28
            r4 = r30
            r32 = r20
            r20 = r1
            r1 = r32
            goto L_0x01a3
        L_0x03f7:
            r30 = r4
            r23 = r9
            r27 = r10
            r26 = r15
            com.google.android.gms.internal.firebase_auth.zzjv r1 = new com.google.android.gms.internal.firebase_auth.zzjv
            com.google.android.gms.internal.firebase_auth.zzjr r10 = r0.zzc()
            r0 = 0
            r5 = r1
            r6 = r30
            r7 = r12
            r8 = r23
            r9 = r27
            r12 = r0
            r13 = r14
            r14 = r26
            r15 = r19
            r16 = r35
            r17 = r36
            r18 = r37
            r19 = r38
            r20 = r39
            r5.<init>(r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20)
            return r1
        L_0x0422:
            com.google.android.gms.internal.firebase_auth.zzks r0 = (com.google.android.gms.internal.firebase_auth.zzks) r0
            int r0 = r0.zza()
            int r1 = com.google.android.gms.internal.firebase_auth.zzig.zze.zzi
            java.lang.NoSuchMethodError r0 = new java.lang.NoSuchMethodError
            r0.<init>()
            goto L_0x0431
        L_0x0430:
            throw r0
        L_0x0431:
            goto L_0x0430
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzjv.zza(java.lang.Class, com.google.android.gms.internal.firebase_auth.zzjp, com.google.android.gms.internal.firebase_auth.zzjw, com.google.android.gms.internal.firebase_auth.zzjb, com.google.android.gms.internal.firebase_auth.zzkz, com.google.android.gms.internal.firebase_auth.zzhv, com.google.android.gms.internal.firebase_auth.zzjk):com.google.android.gms.internal.firebase_auth.zzjv");
    }

    private static Field zza(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException e) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            String name = cls.getName();
            String arrays = Arrays.toString(declaredFields);
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 40 + String.valueOf(name).length() + String.valueOf(arrays).length());
            sb.append("Field ");
            sb.append(str);
            sb.append(" for ");
            sb.append(name);
            sb.append(" not found. Known fields are ");
            sb.append(arrays);
            throw new RuntimeException(sb.toString());
        }
    }

    public final T zza() {
        return this.zzo.zza(this.zzg);
    }

    public final boolean zza(T t, T t2) {
        int length = this.zzc.length;
        int i = 0;
        while (true) {
            boolean z = true;
            if (i < length) {
                int zzd2 = zzd(i);
                long j = (long) (zzd2 & 1048575);
                switch ((zzd2 & 267386880) >>> 20) {
                    case 0:
                        if (!zzc(t, t2, i) || Double.doubleToLongBits(zzlf.zze(t, j)) != Double.doubleToLongBits(zzlf.zze(t2, j))) {
                            z = false;
                            break;
                        }
                    case 1:
                        if (!zzc(t, t2, i) || Float.floatToIntBits(zzlf.zzd(t, j)) != Float.floatToIntBits(zzlf.zzd(t2, j))) {
                            z = false;
                            break;
                        }
                    case 2:
                        if (!zzc(t, t2, i) || zzlf.zzb(t, j) != zzlf.zzb(t2, j)) {
                            z = false;
                            break;
                        }
                    case 3:
                        if (!zzc(t, t2, i) || zzlf.zzb(t, j) != zzlf.zzb(t2, j)) {
                            z = false;
                            break;
                        }
                    case 4:
                        if (!zzc(t, t2, i) || zzlf.zza((Object) t, j) != zzlf.zza((Object) t2, j)) {
                            z = false;
                            break;
                        }
                    case 5:
                        if (!zzc(t, t2, i) || zzlf.zzb(t, j) != zzlf.zzb(t2, j)) {
                            z = false;
                            break;
                        }
                    case 6:
                        if (!zzc(t, t2, i) || zzlf.zza((Object) t, j) != zzlf.zza((Object) t2, j)) {
                            z = false;
                            break;
                        }
                    case 7:
                        if (!zzc(t, t2, i) || zzlf.zzc(t, j) != zzlf.zzc(t2, j)) {
                            z = false;
                            break;
                        }
                    case 8:
                        if (!zzc(t, t2, i) || !zzkj.zza(zzlf.zzf(t, j), zzlf.zzf(t2, j))) {
                            z = false;
                            break;
                        }
                    case 9:
                        if (!zzc(t, t2, i) || !zzkj.zza(zzlf.zzf(t, j), zzlf.zzf(t2, j))) {
                            z = false;
                            break;
                        }
                    case 10:
                        if (!zzc(t, t2, i) || !zzkj.zza(zzlf.zzf(t, j), zzlf.zzf(t2, j))) {
                            z = false;
                            break;
                        }
                    case 11:
                        if (!zzc(t, t2, i) || zzlf.zza((Object) t, j) != zzlf.zza((Object) t2, j)) {
                            z = false;
                            break;
                        }
                    case 12:
                        if (!zzc(t, t2, i) || zzlf.zza((Object) t, j) != zzlf.zza((Object) t2, j)) {
                            z = false;
                            break;
                        }
                    case 13:
                        if (!zzc(t, t2, i) || zzlf.zza((Object) t, j) != zzlf.zza((Object) t2, j)) {
                            z = false;
                            break;
                        }
                    case 14:
                        if (!zzc(t, t2, i) || zzlf.zzb(t, j) != zzlf.zzb(t2, j)) {
                            z = false;
                            break;
                        }
                    case 15:
                        if (!zzc(t, t2, i) || zzlf.zza((Object) t, j) != zzlf.zza((Object) t2, j)) {
                            z = false;
                            break;
                        }
                    case 16:
                        if (!zzc(t, t2, i) || zzlf.zzb(t, j) != zzlf.zzb(t2, j)) {
                            z = false;
                            break;
                        }
                    case 17:
                        if (!zzc(t, t2, i) || !zzkj.zza(zzlf.zzf(t, j), zzlf.zzf(t2, j))) {
                            z = false;
                            break;
                        }
                    case 18:
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                    case 23:
                    case 24:
                    case 25:
                    case 26:
                    case 27:
                    case 28:
                    case 29:
                    case 30:
                    case 31:
                    case 32:
                    case 33:
                    case 34:
                    case 35:
                    case 36:
                    case 37:
                    case 38:
                    case 39:
                    case 40:
                    case 41:
                    case 42:
                    case 43:
                    case 44:
                    case 45:
                    case 46:
                    case 47:
                    case 48:
                    case 49:
                        z = zzkj.zza(zzlf.zzf(t, j), zzlf.zzf(t2, j));
                        break;
                    case 50:
                        z = zzkj.zza(zzlf.zzf(t, j), zzlf.zzf(t2, j));
                        break;
                    case 51:
                    case 52:
                    case 53:
                    case 54:
                    case 55:
                    case 56:
                    case 57:
                    case 58:
                    case 59:
                    case 60:
                    case 61:
                    case 62:
                    case 63:
                    case 64:
                    case 65:
                    case 66:
                    case 67:
                    case 68:
                        long zze2 = (long) (zze(i) & 1048575);
                        if (zzlf.zza((Object) t, zze2) != zzlf.zza((Object) t2, zze2) || !zzkj.zza(zzlf.zzf(t, j), zzlf.zzf(t2, j))) {
                            z = false;
                            break;
                        }
                }
                if (!z) {
                    return false;
                }
                i += 3;
            } else if (!this.zzq.zzb(t).equals(this.zzq.zzb(t2))) {
                return false;
            } else {
                if (this.zzh) {
                    return this.zzr.zza((Object) t).equals(this.zzr.zza((Object) t2));
                }
                return true;
            }
        }
    }

    public final int zza(T t) {
        int length = this.zzc.length;
        int i = 0;
        for (int i2 = 0; i2 < length; i2 += 3) {
            int zzd2 = zzd(i2);
            int i3 = this.zzc[i2];
            long j = (long) (1048575 & zzd2);
            int i4 = 37;
            switch ((zzd2 & 267386880) >>> 20) {
                case 0:
                    i = (i * 53) + zzii.zza(Double.doubleToLongBits(zzlf.zze(t, j)));
                    break;
                case 1:
                    i = (i * 53) + Float.floatToIntBits(zzlf.zzd(t, j));
                    break;
                case 2:
                    i = (i * 53) + zzii.zza(zzlf.zzb(t, j));
                    break;
                case 3:
                    i = (i * 53) + zzii.zza(zzlf.zzb(t, j));
                    break;
                case 4:
                    i = (i * 53) + zzlf.zza((Object) t, j);
                    break;
                case 5:
                    i = (i * 53) + zzii.zza(zzlf.zzb(t, j));
                    break;
                case 6:
                    i = (i * 53) + zzlf.zza((Object) t, j);
                    break;
                case 7:
                    i = (i * 53) + zzii.zza(zzlf.zzc(t, j));
                    break;
                case 8:
                    i = (i * 53) + ((String) zzlf.zzf(t, j)).hashCode();
                    break;
                case 9:
                    Object zzf2 = zzlf.zzf(t, j);
                    if (zzf2 != null) {
                        i4 = zzf2.hashCode();
                    }
                    i = (i * 53) + i4;
                    break;
                case 10:
                    i = (i * 53) + zzlf.zzf(t, j).hashCode();
                    break;
                case 11:
                    i = (i * 53) + zzlf.zza((Object) t, j);
                    break;
                case 12:
                    i = (i * 53) + zzlf.zza((Object) t, j);
                    break;
                case 13:
                    i = (i * 53) + zzlf.zza((Object) t, j);
                    break;
                case 14:
                    i = (i * 53) + zzii.zza(zzlf.zzb(t, j));
                    break;
                case 15:
                    i = (i * 53) + zzlf.zza((Object) t, j);
                    break;
                case 16:
                    i = (i * 53) + zzii.zza(zzlf.zzb(t, j));
                    break;
                case 17:
                    Object zzf3 = zzlf.zzf(t, j);
                    if (zzf3 != null) {
                        i4 = zzf3.hashCode();
                    }
                    i = (i * 53) + i4;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    i = (i * 53) + zzlf.zzf(t, j).hashCode();
                    break;
                case 50:
                    i = (i * 53) + zzlf.zzf(t, j).hashCode();
                    break;
                case 51:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzii.zza(Double.doubleToLongBits(zzb(t, j)));
                        break;
                    }
                case 52:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + Float.floatToIntBits(zzc(t, j));
                        break;
                    }
                case 53:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzii.zza(zze(t, j));
                        break;
                    }
                case 54:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzii.zza(zze(t, j));
                        break;
                    }
                case 55:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzd(t, j);
                        break;
                    }
                case 56:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzii.zza(zze(t, j));
                        break;
                    }
                case 57:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzd(t, j);
                        break;
                    }
                case 58:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzii.zza(zzf(t, j));
                        break;
                    }
                case 59:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + ((String) zzlf.zzf(t, j)).hashCode();
                        break;
                    }
                case 60:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzlf.zzf(t, j).hashCode();
                        break;
                    }
                case 61:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzlf.zzf(t, j).hashCode();
                        break;
                    }
                case 62:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzd(t, j);
                        break;
                    }
                case 63:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzd(t, j);
                        break;
                    }
                case 64:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzd(t, j);
                        break;
                    }
                case 65:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzii.zza(zze(t, j));
                        break;
                    }
                case 66:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzd(t, j);
                        break;
                    }
                case 67:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzii.zza(zze(t, j));
                        break;
                    }
                case 68:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzlf.zzf(t, j).hashCode();
                        break;
                    }
            }
        }
        int hashCode = (i * 53) + this.zzq.zzb(t).hashCode();
        if (this.zzh) {
            return (hashCode * 53) + this.zzr.zza((Object) t).hashCode();
        }
        return hashCode;
    }

    public final void zzb(T t, T t2) {
        if (t2 != null) {
            for (int i = 0; i < this.zzc.length; i += 3) {
                int zzd2 = zzd(i);
                long j = (long) (1048575 & zzd2);
                int i2 = this.zzc[i];
                switch ((zzd2 & 267386880) >>> 20) {
                    case 0:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzlf.zza((Object) t, j, zzlf.zze(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 1:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzlf.zza((Object) t, j, zzlf.zzd(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 2:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzlf.zza((Object) t, j, zzlf.zzb(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 3:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzlf.zza((Object) t, j, zzlf.zzb(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 4:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzlf.zza((Object) t, j, zzlf.zza((Object) t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 5:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzlf.zza((Object) t, j, zzlf.zzb(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 6:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzlf.zza((Object) t, j, zzlf.zza((Object) t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 7:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzlf.zza((Object) t, j, zzlf.zzc(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 8:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzlf.zza((Object) t, j, zzlf.zzf(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 9:
                        zza(t, t2, i);
                        break;
                    case 10:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzlf.zza((Object) t, j, zzlf.zzf(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 11:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzlf.zza((Object) t, j, zzlf.zza((Object) t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 12:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzlf.zza((Object) t, j, zzlf.zza((Object) t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 13:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzlf.zza((Object) t, j, zzlf.zza((Object) t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 14:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzlf.zza((Object) t, j, zzlf.zzb(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 15:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzlf.zza((Object) t, j, zzlf.zza((Object) t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 16:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzlf.zza((Object) t, j, zzlf.zzb(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 17:
                        zza(t, t2, i);
                        break;
                    case 18:
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                    case 23:
                    case 24:
                    case 25:
                    case 26:
                    case 27:
                    case 28:
                    case 29:
                    case 30:
                    case 31:
                    case 32:
                    case 33:
                    case 34:
                    case 35:
                    case 36:
                    case 37:
                    case 38:
                    case 39:
                    case 40:
                    case 41:
                    case 42:
                    case 43:
                    case 44:
                    case 45:
                    case 46:
                    case 47:
                    case 48:
                    case 49:
                        this.zzp.zza(t, t2, j);
                        break;
                    case 50:
                        zzkj.zza(this.zzs, t, t2, j);
                        break;
                    case 51:
                    case 52:
                    case 53:
                    case 54:
                    case 55:
                    case 56:
                    case 57:
                    case 58:
                    case 59:
                        if (!zza(t2, i2, i)) {
                            break;
                        } else {
                            zzlf.zza((Object) t, j, zzlf.zzf(t2, j));
                            zzb(t, i2, i);
                            break;
                        }
                    case 60:
                        zzb(t, t2, i);
                        break;
                    case 61:
                    case 62:
                    case 63:
                    case 64:
                    case 65:
                    case 66:
                    case 67:
                        if (!zza(t2, i2, i)) {
                            break;
                        } else {
                            zzlf.zza((Object) t, j, zzlf.zzf(t2, j));
                            zzb(t, i2, i);
                            break;
                        }
                    case 68:
                        zzb(t, t2, i);
                        break;
                }
            }
            zzkj.zza(this.zzq, t, t2);
            if (this.zzh) {
                zzkj.zza(this.zzr, t, t2);
                return;
            }
            return;
        }
        throw null;
    }

    private final void zza(T t, T t2, int i) {
        long zzd2 = (long) (zzd(i) & 1048575);
        if (zza(t2, i)) {
            Object zzf2 = zzlf.zzf(t, zzd2);
            Object zzf3 = zzlf.zzf(t2, zzd2);
            if (zzf2 != null && zzf3 != null) {
                zzlf.zza((Object) t, zzd2, zzii.zza(zzf2, zzf3));
                zzb(t, i);
            } else if (zzf3 != null) {
                zzlf.zza((Object) t, zzd2, zzf3);
                zzb(t, i);
            }
        }
    }

    private final void zzb(T t, T t2, int i) {
        int zzd2 = zzd(i);
        int i2 = this.zzc[i];
        long j = (long) (zzd2 & 1048575);
        if (zza(t2, i2, i)) {
            Object zzf2 = zzlf.zzf(t, j);
            Object zzf3 = zzlf.zzf(t2, j);
            if (zzf2 != null && zzf3 != null) {
                zzlf.zza((Object) t, j, zzii.zza(zzf2, zzf3));
                zzb(t, i2, i);
            } else if (zzf3 != null) {
                zzlf.zza((Object) t, j, zzf3);
                zzb(t, i2, i);
            }
        }
    }

    public final int zzd(T t) {
        int i;
        int i2;
        long j;
        int i3;
        T t2 = t;
        int i4 = 267386880;
        if (this.zzj) {
            Unsafe unsafe = zzb;
            int i5 = 0;
            int i6 = 0;
            while (i5 < this.zzc.length) {
                int zzd2 = zzd(i5);
                int i7 = (zzd2 & i4) >>> 20;
                int i8 = this.zzc[i5];
                long j2 = (long) (zzd2 & 1048575);
                if (i7 < zzia.DOUBLE_LIST_PACKED.zza() || i7 > zzia.SINT64_LIST_PACKED.zza()) {
                    i3 = 0;
                } else {
                    i3 = this.zzc[i5 + 2] & 1048575;
                }
                switch (i7) {
                    case 0:
                        if (!zza(t2, i5)) {
                            break;
                        } else {
                            i6 += zzhq.zzb(i8, 0.0d);
                            break;
                        }
                    case 1:
                        if (!zza(t2, i5)) {
                            break;
                        } else {
                            i6 += zzhq.zzb(i8, 0.0f);
                            break;
                        }
                    case 2:
                        if (!zza(t2, i5)) {
                            break;
                        } else {
                            i6 += zzhq.zzd(i8, zzlf.zzb(t2, j2));
                            break;
                        }
                    case 3:
                        if (!zza(t2, i5)) {
                            break;
                        } else {
                            i6 += zzhq.zze(i8, zzlf.zzb(t2, j2));
                            break;
                        }
                    case 4:
                        if (!zza(t2, i5)) {
                            break;
                        } else {
                            i6 += zzhq.zzf(i8, zzlf.zza((Object) t2, j2));
                            break;
                        }
                    case 5:
                        if (!zza(t2, i5)) {
                            break;
                        } else {
                            i6 += zzhq.zzg(i8, 0);
                            break;
                        }
                    case 6:
                        if (!zza(t2, i5)) {
                            break;
                        } else {
                            i6 += zzhq.zzi(i8, 0);
                            break;
                        }
                    case 7:
                        if (!zza(t2, i5)) {
                            break;
                        } else {
                            i6 += zzhq.zzb(i8, true);
                            break;
                        }
                    case 8:
                        if (!zza(t2, i5)) {
                            break;
                        } else {
                            Object zzf2 = zzlf.zzf(t2, j2);
                            if (!(zzf2 instanceof zzgv)) {
                                i6 += zzhq.zzb(i8, (String) zzf2);
                                break;
                            } else {
                                i6 += zzhq.zzc(i8, (zzgv) zzf2);
                                break;
                            }
                        }
                    case 9:
                        if (!zza(t2, i5)) {
                            break;
                        } else {
                            i6 += zzkj.zza(i8, zzlf.zzf(t2, j2), zza(i5));
                            break;
                        }
                    case 10:
                        if (!zza(t2, i5)) {
                            break;
                        } else {
                            i6 += zzhq.zzc(i8, (zzgv) zzlf.zzf(t2, j2));
                            break;
                        }
                    case 11:
                        if (!zza(t2, i5)) {
                            break;
                        } else {
                            i6 += zzhq.zzg(i8, zzlf.zza((Object) t2, j2));
                            break;
                        }
                    case 12:
                        if (!zza(t2, i5)) {
                            break;
                        } else {
                            i6 += zzhq.zzk(i8, zzlf.zza((Object) t2, j2));
                            break;
                        }
                    case 13:
                        if (!zza(t2, i5)) {
                            break;
                        } else {
                            i6 += zzhq.zzj(i8, 0);
                            break;
                        }
                    case 14:
                        if (!zza(t2, i5)) {
                            break;
                        } else {
                            i6 += zzhq.zzh(i8, 0);
                            break;
                        }
                    case 15:
                        if (!zza(t2, i5)) {
                            break;
                        } else {
                            i6 += zzhq.zzh(i8, zzlf.zza((Object) t2, j2));
                            break;
                        }
                    case 16:
                        if (!zza(t2, i5)) {
                            break;
                        } else {
                            i6 += zzhq.zzf(i8, zzlf.zzb(t2, j2));
                            break;
                        }
                    case 17:
                        if (!zza(t2, i5)) {
                            break;
                        } else {
                            i6 += zzhq.zzc(i8, (zzjr) zzlf.zzf(t2, j2), zza(i5));
                            break;
                        }
                    case 18:
                        i6 += zzkj.zzi(i8, zza((Object) t2, j2), false);
                        break;
                    case 19:
                        i6 += zzkj.zzh(i8, zza((Object) t2, j2), false);
                        break;
                    case 20:
                        i6 += zzkj.zza(i8, (List<Long>) zza((Object) t2, j2), false);
                        break;
                    case 21:
                        i6 += zzkj.zzb(i8, (List<Long>) zza((Object) t2, j2), false);
                        break;
                    case 22:
                        i6 += zzkj.zze(i8, zza((Object) t2, j2), false);
                        break;
                    case 23:
                        i6 += zzkj.zzi(i8, zza((Object) t2, j2), false);
                        break;
                    case 24:
                        i6 += zzkj.zzh(i8, zza((Object) t2, j2), false);
                        break;
                    case 25:
                        i6 += zzkj.zzj(i8, zza((Object) t2, j2), false);
                        break;
                    case 26:
                        i6 += zzkj.zza(i8, zza((Object) t2, j2));
                        break;
                    case 27:
                        i6 += zzkj.zza(i8, zza((Object) t2, j2), zza(i5));
                        break;
                    case 28:
                        i6 += zzkj.zzb(i8, zza((Object) t2, j2));
                        break;
                    case 29:
                        i6 += zzkj.zzf(i8, zza((Object) t2, j2), false);
                        break;
                    case 30:
                        i6 += zzkj.zzd(i8, zza((Object) t2, j2), false);
                        break;
                    case 31:
                        i6 += zzkj.zzh(i8, zza((Object) t2, j2), false);
                        break;
                    case 32:
                        i6 += zzkj.zzi(i8, zza((Object) t2, j2), false);
                        break;
                    case 33:
                        i6 += zzkj.zzg(i8, zza((Object) t2, j2), false);
                        break;
                    case 34:
                        i6 += zzkj.zzc(i8, zza((Object) t2, j2), false);
                        break;
                    case 35:
                        int zzi2 = zzkj.zzi((List) unsafe.getObject(t2, j2));
                        if (zzi2 > 0) {
                            if (this.zzk) {
                                unsafe.putInt(t2, (long) i3, zzi2);
                            }
                            i6 += zzhq.zze(i8) + zzhq.zzg(zzi2) + zzi2;
                            break;
                        } else {
                            break;
                        }
                    case 36:
                        int zzh2 = zzkj.zzh((List) unsafe.getObject(t2, j2));
                        if (zzh2 > 0) {
                            if (this.zzk) {
                                unsafe.putInt(t2, (long) i3, zzh2);
                            }
                            i6 += zzhq.zze(i8) + zzhq.zzg(zzh2) + zzh2;
                            break;
                        } else {
                            break;
                        }
                    case 37:
                        int zza2 = zzkj.zza((List<Long>) (List) unsafe.getObject(t2, j2));
                        if (zza2 > 0) {
                            if (this.zzk) {
                                unsafe.putInt(t2, (long) i3, zza2);
                            }
                            i6 += zzhq.zze(i8) + zzhq.zzg(zza2) + zza2;
                            break;
                        } else {
                            break;
                        }
                    case 38:
                        int zzb2 = zzkj.zzb((List) unsafe.getObject(t2, j2));
                        if (zzb2 > 0) {
                            if (this.zzk) {
                                unsafe.putInt(t2, (long) i3, zzb2);
                            }
                            i6 += zzhq.zze(i8) + zzhq.zzg(zzb2) + zzb2;
                            break;
                        } else {
                            break;
                        }
                    case 39:
                        int zze2 = zzkj.zze((List) unsafe.getObject(t2, j2));
                        if (zze2 > 0) {
                            if (this.zzk) {
                                unsafe.putInt(t2, (long) i3, zze2);
                            }
                            i6 += zzhq.zze(i8) + zzhq.zzg(zze2) + zze2;
                            break;
                        } else {
                            break;
                        }
                    case 40:
                        int zzi3 = zzkj.zzi((List) unsafe.getObject(t2, j2));
                        if (zzi3 > 0) {
                            if (this.zzk) {
                                unsafe.putInt(t2, (long) i3, zzi3);
                            }
                            i6 += zzhq.zze(i8) + zzhq.zzg(zzi3) + zzi3;
                            break;
                        } else {
                            break;
                        }
                    case 41:
                        int zzh3 = zzkj.zzh((List) unsafe.getObject(t2, j2));
                        if (zzh3 > 0) {
                            if (this.zzk) {
                                unsafe.putInt(t2, (long) i3, zzh3);
                            }
                            i6 += zzhq.zze(i8) + zzhq.zzg(zzh3) + zzh3;
                            break;
                        } else {
                            break;
                        }
                    case 42:
                        int zzj2 = zzkj.zzj((List) unsafe.getObject(t2, j2));
                        if (zzj2 > 0) {
                            if (this.zzk) {
                                unsafe.putInt(t2, (long) i3, zzj2);
                            }
                            i6 += zzhq.zze(i8) + zzhq.zzg(zzj2) + zzj2;
                            break;
                        } else {
                            break;
                        }
                    case 43:
                        int zzf3 = zzkj.zzf((List) unsafe.getObject(t2, j2));
                        if (zzf3 > 0) {
                            if (this.zzk) {
                                unsafe.putInt(t2, (long) i3, zzf3);
                            }
                            i6 += zzhq.zze(i8) + zzhq.zzg(zzf3) + zzf3;
                            break;
                        } else {
                            break;
                        }
                    case 44:
                        int zzd3 = zzkj.zzd((List) unsafe.getObject(t2, j2));
                        if (zzd3 > 0) {
                            if (this.zzk) {
                                unsafe.putInt(t2, (long) i3, zzd3);
                            }
                            i6 += zzhq.zze(i8) + zzhq.zzg(zzd3) + zzd3;
                            break;
                        } else {
                            break;
                        }
                    case 45:
                        int zzh4 = zzkj.zzh((List) unsafe.getObject(t2, j2));
                        if (zzh4 > 0) {
                            if (this.zzk) {
                                unsafe.putInt(t2, (long) i3, zzh4);
                            }
                            i6 += zzhq.zze(i8) + zzhq.zzg(zzh4) + zzh4;
                            break;
                        } else {
                            break;
                        }
                    case 46:
                        int zzi4 = zzkj.zzi((List) unsafe.getObject(t2, j2));
                        if (zzi4 > 0) {
                            if (this.zzk) {
                                unsafe.putInt(t2, (long) i3, zzi4);
                            }
                            i6 += zzhq.zze(i8) + zzhq.zzg(zzi4) + zzi4;
                            break;
                        } else {
                            break;
                        }
                    case 47:
                        int zzg2 = zzkj.zzg((List) unsafe.getObject(t2, j2));
                        if (zzg2 > 0) {
                            if (this.zzk) {
                                unsafe.putInt(t2, (long) i3, zzg2);
                            }
                            i6 += zzhq.zze(i8) + zzhq.zzg(zzg2) + zzg2;
                            break;
                        } else {
                            break;
                        }
                    case 48:
                        int zzc2 = zzkj.zzc((List) unsafe.getObject(t2, j2));
                        if (zzc2 > 0) {
                            if (this.zzk) {
                                unsafe.putInt(t2, (long) i3, zzc2);
                            }
                            i6 += zzhq.zze(i8) + zzhq.zzg(zzc2) + zzc2;
                            break;
                        } else {
                            break;
                        }
                    case 49:
                        i6 += zzkj.zzb(i8, (List<zzjr>) zza((Object) t2, j2), zza(i5));
                        break;
                    case 50:
                        i6 += this.zzs.zza(i8, zzlf.zzf(t2, j2), zzb(i5));
                        break;
                    case 51:
                        if (!zza(t2, i8, i5)) {
                            break;
                        } else {
                            i6 += zzhq.zzb(i8, 0.0d);
                            break;
                        }
                    case 52:
                        if (!zza(t2, i8, i5)) {
                            break;
                        } else {
                            i6 += zzhq.zzb(i8, 0.0f);
                            break;
                        }
                    case 53:
                        if (!zza(t2, i8, i5)) {
                            break;
                        } else {
                            i6 += zzhq.zzd(i8, zze(t2, j2));
                            break;
                        }
                    case 54:
                        if (!zza(t2, i8, i5)) {
                            break;
                        } else {
                            i6 += zzhq.zze(i8, zze(t2, j2));
                            break;
                        }
                    case 55:
                        if (!zza(t2, i8, i5)) {
                            break;
                        } else {
                            i6 += zzhq.zzf(i8, zzd(t2, j2));
                            break;
                        }
                    case 56:
                        if (!zza(t2, i8, i5)) {
                            break;
                        } else {
                            i6 += zzhq.zzg(i8, 0);
                            break;
                        }
                    case 57:
                        if (!zza(t2, i8, i5)) {
                            break;
                        } else {
                            i6 += zzhq.zzi(i8, 0);
                            break;
                        }
                    case 58:
                        if (!zza(t2, i8, i5)) {
                            break;
                        } else {
                            i6 += zzhq.zzb(i8, true);
                            break;
                        }
                    case 59:
                        if (!zza(t2, i8, i5)) {
                            break;
                        } else {
                            Object zzf4 = zzlf.zzf(t2, j2);
                            if (!(zzf4 instanceof zzgv)) {
                                i6 += zzhq.zzb(i8, (String) zzf4);
                                break;
                            } else {
                                i6 += zzhq.zzc(i8, (zzgv) zzf4);
                                break;
                            }
                        }
                    case 60:
                        if (!zza(t2, i8, i5)) {
                            break;
                        } else {
                            i6 += zzkj.zza(i8, zzlf.zzf(t2, j2), zza(i5));
                            break;
                        }
                    case 61:
                        if (!zza(t2, i8, i5)) {
                            break;
                        } else {
                            i6 += zzhq.zzc(i8, (zzgv) zzlf.zzf(t2, j2));
                            break;
                        }
                    case 62:
                        if (!zza(t2, i8, i5)) {
                            break;
                        } else {
                            i6 += zzhq.zzg(i8, zzd(t2, j2));
                            break;
                        }
                    case 63:
                        if (!zza(t2, i8, i5)) {
                            break;
                        } else {
                            i6 += zzhq.zzk(i8, zzd(t2, j2));
                            break;
                        }
                    case 64:
                        if (!zza(t2, i8, i5)) {
                            break;
                        } else {
                            i6 += zzhq.zzj(i8, 0);
                            break;
                        }
                    case 65:
                        if (!zza(t2, i8, i5)) {
                            break;
                        } else {
                            i6 += zzhq.zzh(i8, 0);
                            break;
                        }
                    case 66:
                        if (!zza(t2, i8, i5)) {
                            break;
                        } else {
                            i6 += zzhq.zzh(i8, zzd(t2, j2));
                            break;
                        }
                    case 67:
                        if (!zza(t2, i8, i5)) {
                            break;
                        } else {
                            i6 += zzhq.zzf(i8, zze(t2, j2));
                            break;
                        }
                    case 68:
                        if (!zza(t2, i8, i5)) {
                            break;
                        } else {
                            i6 += zzhq.zzc(i8, (zzjr) zzlf.zzf(t2, j2), zza(i5));
                            break;
                        }
                }
                i5 += 3;
                i4 = 267386880;
            }
            return i6 + zza(this.zzq, t2);
        }
        Unsafe unsafe2 = zzb;
        int i9 = 0;
        int i10 = 0;
        int i11 = 1048575;
        int i12 = 0;
        while (i9 < this.zzc.length) {
            int zzd4 = zzd(i9);
            int[] iArr = this.zzc;
            int i13 = iArr[i9];
            int i14 = (zzd4 & 267386880) >>> 20;
            if (i14 <= 17) {
                int i15 = iArr[i9 + 2];
                int i16 = i15 & 1048575;
                i = 1 << (i15 >>> 20);
                if (i16 != i11) {
                    i12 = unsafe2.getInt(t2, (long) i16);
                    i11 = i16;
                }
                i2 = i15;
            } else if (!this.zzk || i14 < zzia.DOUBLE_LIST_PACKED.zza() || i14 > zzia.SINT64_LIST_PACKED.zza()) {
                i2 = 0;
                i = 0;
            } else {
                i2 = this.zzc[i9 + 2] & 1048575;
                i = 0;
            }
            long j3 = (long) (zzd4 & 1048575);
            switch (i14) {
                case 0:
                    j = 0;
                    if ((i12 & i) == 0) {
                        break;
                    } else {
                        i10 += zzhq.zzb(i13, 0.0d);
                        break;
                    }
                case 1:
                    j = 0;
                    if ((i12 & i) == 0) {
                        break;
                    } else {
                        i10 += zzhq.zzb(i13, 0.0f);
                        break;
                    }
                case 2:
                    j = 0;
                    if ((i12 & i) == 0) {
                        break;
                    } else {
                        i10 += zzhq.zzd(i13, unsafe2.getLong(t2, j3));
                        break;
                    }
                case 3:
                    j = 0;
                    if ((i12 & i) == 0) {
                        break;
                    } else {
                        i10 += zzhq.zze(i13, unsafe2.getLong(t2, j3));
                        break;
                    }
                case 4:
                    j = 0;
                    if ((i12 & i) == 0) {
                        break;
                    } else {
                        i10 += zzhq.zzf(i13, unsafe2.getInt(t2, j3));
                        break;
                    }
                case 5:
                    if ((i12 & i) == 0) {
                        j = 0;
                        break;
                    } else {
                        j = 0;
                        i10 += zzhq.zzg(i13, 0);
                        break;
                    }
                case 6:
                    if ((i12 & i) == 0) {
                        j = 0;
                        break;
                    } else {
                        i10 += zzhq.zzi(i13, 0);
                        j = 0;
                        break;
                    }
                case 7:
                    if ((i12 & i) == 0) {
                        j = 0;
                        break;
                    } else {
                        i10 += zzhq.zzb(i13, true);
                        j = 0;
                        break;
                    }
                case 8:
                    if ((i12 & i) == 0) {
                        j = 0;
                        break;
                    } else {
                        Object object = unsafe2.getObject(t2, j3);
                        if (!(object instanceof zzgv)) {
                            i10 += zzhq.zzb(i13, (String) object);
                            j = 0;
                            break;
                        } else {
                            i10 += zzhq.zzc(i13, (zzgv) object);
                            j = 0;
                            break;
                        }
                    }
                case 9:
                    if ((i12 & i) == 0) {
                        j = 0;
                        break;
                    } else {
                        i10 += zzkj.zza(i13, unsafe2.getObject(t2, j3), zza(i9));
                        j = 0;
                        break;
                    }
                case 10:
                    if ((i12 & i) == 0) {
                        j = 0;
                        break;
                    } else {
                        i10 += zzhq.zzc(i13, (zzgv) unsafe2.getObject(t2, j3));
                        j = 0;
                        break;
                    }
                case 11:
                    if ((i12 & i) == 0) {
                        j = 0;
                        break;
                    } else {
                        i10 += zzhq.zzg(i13, unsafe2.getInt(t2, j3));
                        j = 0;
                        break;
                    }
                case 12:
                    if ((i12 & i) == 0) {
                        j = 0;
                        break;
                    } else {
                        i10 += zzhq.zzk(i13, unsafe2.getInt(t2, j3));
                        j = 0;
                        break;
                    }
                case 13:
                    if ((i12 & i) == 0) {
                        j = 0;
                        break;
                    } else {
                        i10 += zzhq.zzj(i13, 0);
                        j = 0;
                        break;
                    }
                case 14:
                    if ((i12 & i) == 0) {
                        j = 0;
                        break;
                    } else {
                        i10 += zzhq.zzh(i13, 0);
                        j = 0;
                        break;
                    }
                case 15:
                    if ((i12 & i) == 0) {
                        j = 0;
                        break;
                    } else {
                        i10 += zzhq.zzh(i13, unsafe2.getInt(t2, j3));
                        j = 0;
                        break;
                    }
                case 16:
                    if ((i12 & i) == 0) {
                        j = 0;
                        break;
                    } else {
                        i10 += zzhq.zzf(i13, unsafe2.getLong(t2, j3));
                        j = 0;
                        break;
                    }
                case 17:
                    if ((i12 & i) == 0) {
                        j = 0;
                        break;
                    } else {
                        i10 += zzhq.zzc(i13, (zzjr) unsafe2.getObject(t2, j3), zza(i9));
                        j = 0;
                        break;
                    }
                case 18:
                    i10 += zzkj.zzi(i13, (List) unsafe2.getObject(t2, j3), false);
                    j = 0;
                    break;
                case 19:
                    i10 += zzkj.zzh(i13, (List) unsafe2.getObject(t2, j3), false);
                    j = 0;
                    break;
                case 20:
                    i10 += zzkj.zza(i13, (List<Long>) (List) unsafe2.getObject(t2, j3), false);
                    j = 0;
                    break;
                case 21:
                    i10 += zzkj.zzb(i13, (List<Long>) (List) unsafe2.getObject(t2, j3), false);
                    j = 0;
                    break;
                case 22:
                    i10 += zzkj.zze(i13, (List) unsafe2.getObject(t2, j3), false);
                    j = 0;
                    break;
                case 23:
                    i10 += zzkj.zzi(i13, (List) unsafe2.getObject(t2, j3), false);
                    j = 0;
                    break;
                case 24:
                    i10 += zzkj.zzh(i13, (List) unsafe2.getObject(t2, j3), false);
                    j = 0;
                    break;
                case 25:
                    i10 += zzkj.zzj(i13, (List) unsafe2.getObject(t2, j3), false);
                    j = 0;
                    break;
                case 26:
                    i10 += zzkj.zza(i13, (List<?>) (List) unsafe2.getObject(t2, j3));
                    j = 0;
                    break;
                case 27:
                    i10 += zzkj.zza(i13, (List<?>) (List) unsafe2.getObject(t2, j3), zza(i9));
                    j = 0;
                    break;
                case 28:
                    i10 += zzkj.zzb(i13, (List) unsafe2.getObject(t2, j3));
                    j = 0;
                    break;
                case 29:
                    i10 += zzkj.zzf(i13, (List) unsafe2.getObject(t2, j3), false);
                    j = 0;
                    break;
                case 30:
                    i10 += zzkj.zzd(i13, (List) unsafe2.getObject(t2, j3), false);
                    j = 0;
                    break;
                case 31:
                    i10 += zzkj.zzh(i13, (List) unsafe2.getObject(t2, j3), false);
                    j = 0;
                    break;
                case 32:
                    i10 += zzkj.zzi(i13, (List) unsafe2.getObject(t2, j3), false);
                    j = 0;
                    break;
                case 33:
                    i10 += zzkj.zzg(i13, (List) unsafe2.getObject(t2, j3), false);
                    j = 0;
                    break;
                case 34:
                    i10 += zzkj.zzc(i13, (List) unsafe2.getObject(t2, j3), false);
                    j = 0;
                    break;
                case 35:
                    int zzi5 = zzkj.zzi((List) unsafe2.getObject(t2, j3));
                    if (zzi5 <= 0) {
                        j = 0;
                        break;
                    } else {
                        if (this.zzk) {
                            unsafe2.putInt(t2, (long) i2, zzi5);
                        }
                        i10 += zzhq.zze(i13) + zzhq.zzg(zzi5) + zzi5;
                        j = 0;
                        break;
                    }
                case 36:
                    int zzh5 = zzkj.zzh((List) unsafe2.getObject(t2, j3));
                    if (zzh5 <= 0) {
                        j = 0;
                        break;
                    } else {
                        if (this.zzk) {
                            unsafe2.putInt(t2, (long) i2, zzh5);
                        }
                        i10 += zzhq.zze(i13) + zzhq.zzg(zzh5) + zzh5;
                        j = 0;
                        break;
                    }
                case 37:
                    int zza3 = zzkj.zza((List<Long>) (List) unsafe2.getObject(t2, j3));
                    if (zza3 <= 0) {
                        j = 0;
                        break;
                    } else {
                        if (this.zzk) {
                            unsafe2.putInt(t2, (long) i2, zza3);
                        }
                        i10 += zzhq.zze(i13) + zzhq.zzg(zza3) + zza3;
                        j = 0;
                        break;
                    }
                case 38:
                    int zzb3 = zzkj.zzb((List) unsafe2.getObject(t2, j3));
                    if (zzb3 <= 0) {
                        j = 0;
                        break;
                    } else {
                        if (this.zzk) {
                            unsafe2.putInt(t2, (long) i2, zzb3);
                        }
                        i10 += zzhq.zze(i13) + zzhq.zzg(zzb3) + zzb3;
                        j = 0;
                        break;
                    }
                case 39:
                    int zze3 = zzkj.zze((List) unsafe2.getObject(t2, j3));
                    if (zze3 <= 0) {
                        j = 0;
                        break;
                    } else {
                        if (this.zzk) {
                            unsafe2.putInt(t2, (long) i2, zze3);
                        }
                        i10 += zzhq.zze(i13) + zzhq.zzg(zze3) + zze3;
                        j = 0;
                        break;
                    }
                case 40:
                    int zzi6 = zzkj.zzi((List) unsafe2.getObject(t2, j3));
                    if (zzi6 <= 0) {
                        j = 0;
                        break;
                    } else {
                        if (this.zzk) {
                            unsafe2.putInt(t2, (long) i2, zzi6);
                        }
                        i10 += zzhq.zze(i13) + zzhq.zzg(zzi6) + zzi6;
                        j = 0;
                        break;
                    }
                case 41:
                    int zzh6 = zzkj.zzh((List) unsafe2.getObject(t2, j3));
                    if (zzh6 <= 0) {
                        j = 0;
                        break;
                    } else {
                        if (this.zzk) {
                            unsafe2.putInt(t2, (long) i2, zzh6);
                        }
                        i10 += zzhq.zze(i13) + zzhq.zzg(zzh6) + zzh6;
                        j = 0;
                        break;
                    }
                case 42:
                    int zzj3 = zzkj.zzj((List) unsafe2.getObject(t2, j3));
                    if (zzj3 <= 0) {
                        j = 0;
                        break;
                    } else {
                        if (this.zzk) {
                            unsafe2.putInt(t2, (long) i2, zzj3);
                        }
                        i10 += zzhq.zze(i13) + zzhq.zzg(zzj3) + zzj3;
                        j = 0;
                        break;
                    }
                case 43:
                    int zzf5 = zzkj.zzf((List) unsafe2.getObject(t2, j3));
                    if (zzf5 <= 0) {
                        j = 0;
                        break;
                    } else {
                        if (this.zzk) {
                            unsafe2.putInt(t2, (long) i2, zzf5);
                        }
                        i10 += zzhq.zze(i13) + zzhq.zzg(zzf5) + zzf5;
                        j = 0;
                        break;
                    }
                case 44:
                    int zzd5 = zzkj.zzd((List) unsafe2.getObject(t2, j3));
                    if (zzd5 <= 0) {
                        j = 0;
                        break;
                    } else {
                        if (this.zzk) {
                            unsafe2.putInt(t2, (long) i2, zzd5);
                        }
                        i10 += zzhq.zze(i13) + zzhq.zzg(zzd5) + zzd5;
                        j = 0;
                        break;
                    }
                case 45:
                    int zzh7 = zzkj.zzh((List) unsafe2.getObject(t2, j3));
                    if (zzh7 <= 0) {
                        j = 0;
                        break;
                    } else {
                        if (this.zzk) {
                            unsafe2.putInt(t2, (long) i2, zzh7);
                        }
                        i10 += zzhq.zze(i13) + zzhq.zzg(zzh7) + zzh7;
                        j = 0;
                        break;
                    }
                case 46:
                    int zzi7 = zzkj.zzi((List) unsafe2.getObject(t2, j3));
                    if (zzi7 <= 0) {
                        j = 0;
                        break;
                    } else {
                        if (this.zzk) {
                            unsafe2.putInt(t2, (long) i2, zzi7);
                        }
                        i10 += zzhq.zze(i13) + zzhq.zzg(zzi7) + zzi7;
                        j = 0;
                        break;
                    }
                case 47:
                    int zzg3 = zzkj.zzg((List) unsafe2.getObject(t2, j3));
                    if (zzg3 <= 0) {
                        j = 0;
                        break;
                    } else {
                        if (this.zzk) {
                            unsafe2.putInt(t2, (long) i2, zzg3);
                        }
                        i10 += zzhq.zze(i13) + zzhq.zzg(zzg3) + zzg3;
                        j = 0;
                        break;
                    }
                case 48:
                    int zzc3 = zzkj.zzc((List) unsafe2.getObject(t2, j3));
                    if (zzc3 <= 0) {
                        j = 0;
                        break;
                    } else {
                        if (this.zzk) {
                            unsafe2.putInt(t2, (long) i2, zzc3);
                        }
                        i10 += zzhq.zze(i13) + zzhq.zzg(zzc3) + zzc3;
                        j = 0;
                        break;
                    }
                case 49:
                    i10 += zzkj.zzb(i13, (List<zzjr>) (List) unsafe2.getObject(t2, j3), zza(i9));
                    j = 0;
                    break;
                case 50:
                    i10 += this.zzs.zza(i13, unsafe2.getObject(t2, j3), zzb(i9));
                    j = 0;
                    break;
                case 51:
                    if (!zza(t2, i13, i9)) {
                        j = 0;
                        break;
                    } else {
                        i10 += zzhq.zzb(i13, 0.0d);
                        j = 0;
                        break;
                    }
                case 52:
                    if (!zza(t2, i13, i9)) {
                        j = 0;
                        break;
                    } else {
                        i10 += zzhq.zzb(i13, 0.0f);
                        j = 0;
                        break;
                    }
                case 53:
                    if (!zza(t2, i13, i9)) {
                        j = 0;
                        break;
                    } else {
                        i10 += zzhq.zzd(i13, zze(t2, j3));
                        j = 0;
                        break;
                    }
                case 54:
                    if (!zza(t2, i13, i9)) {
                        j = 0;
                        break;
                    } else {
                        i10 += zzhq.zze(i13, zze(t2, j3));
                        j = 0;
                        break;
                    }
                case 55:
                    if (!zza(t2, i13, i9)) {
                        j = 0;
                        break;
                    } else {
                        i10 += zzhq.zzf(i13, zzd(t2, j3));
                        j = 0;
                        break;
                    }
                case 56:
                    if (!zza(t2, i13, i9)) {
                        j = 0;
                        break;
                    } else {
                        i10 += zzhq.zzg(i13, 0);
                        j = 0;
                        break;
                    }
                case 57:
                    if (!zza(t2, i13, i9)) {
                        j = 0;
                        break;
                    } else {
                        i10 += zzhq.zzi(i13, 0);
                        j = 0;
                        break;
                    }
                case 58:
                    if (!zza(t2, i13, i9)) {
                        j = 0;
                        break;
                    } else {
                        i10 += zzhq.zzb(i13, true);
                        j = 0;
                        break;
                    }
                case 59:
                    if (!zza(t2, i13, i9)) {
                        j = 0;
                        break;
                    } else {
                        Object object2 = unsafe2.getObject(t2, j3);
                        if (!(object2 instanceof zzgv)) {
                            i10 += zzhq.zzb(i13, (String) object2);
                            j = 0;
                            break;
                        } else {
                            i10 += zzhq.zzc(i13, (zzgv) object2);
                            j = 0;
                            break;
                        }
                    }
                case 60:
                    if (!zza(t2, i13, i9)) {
                        j = 0;
                        break;
                    } else {
                        i10 += zzkj.zza(i13, unsafe2.getObject(t2, j3), zza(i9));
                        j = 0;
                        break;
                    }
                case 61:
                    if (!zza(t2, i13, i9)) {
                        j = 0;
                        break;
                    } else {
                        i10 += zzhq.zzc(i13, (zzgv) unsafe2.getObject(t2, j3));
                        j = 0;
                        break;
                    }
                case 62:
                    if (!zza(t2, i13, i9)) {
                        j = 0;
                        break;
                    } else {
                        i10 += zzhq.zzg(i13, zzd(t2, j3));
                        j = 0;
                        break;
                    }
                case 63:
                    if (!zza(t2, i13, i9)) {
                        j = 0;
                        break;
                    } else {
                        i10 += zzhq.zzk(i13, zzd(t2, j3));
                        j = 0;
                        break;
                    }
                case 64:
                    if (!zza(t2, i13, i9)) {
                        j = 0;
                        break;
                    } else {
                        i10 += zzhq.zzj(i13, 0);
                        j = 0;
                        break;
                    }
                case 65:
                    if (!zza(t2, i13, i9)) {
                        j = 0;
                        break;
                    } else {
                        i10 += zzhq.zzh(i13, 0);
                        j = 0;
                        break;
                    }
                case 66:
                    if (!zza(t2, i13, i9)) {
                        j = 0;
                        break;
                    } else {
                        i10 += zzhq.zzh(i13, zzd(t2, j3));
                        j = 0;
                        break;
                    }
                case 67:
                    if (!zza(t2, i13, i9)) {
                        j = 0;
                        break;
                    } else {
                        i10 += zzhq.zzf(i13, zze(t2, j3));
                        j = 0;
                        break;
                    }
                case 68:
                    if (!zza(t2, i13, i9)) {
                        j = 0;
                        break;
                    } else {
                        i10 += zzhq.zzc(i13, (zzjr) unsafe2.getObject(t2, j3), zza(i9));
                        j = 0;
                        break;
                    }
                default:
                    j = 0;
                    break;
            }
            i9 += 3;
            long j4 = j;
        }
        int i17 = 0;
        int zza4 = i10 + zza(this.zzq, t2);
        if (!this.zzh) {
            return zza4;
        }
        zzhz<?> zza5 = this.zzr.zza((Object) t2);
        for (int i18 = 0; i18 < zza5.zza.zzc(); i18++) {
            Map.Entry<T, Object> zzb4 = zza5.zza.zzb(i18);
            i17 += zzhz.zza((zzib<?>) (zzib) zzb4.getKey(), zzb4.getValue());
        }
        for (Map.Entry next : zza5.zza.zzd()) {
            i17 += zzhz.zza((zzib<?>) (zzib) next.getKey(), next.getValue());
        }
        return zza4 + i17;
    }

    private static <UT, UB> int zza(zzkz<UT, UB> zzkz, T t) {
        return zzkz.zzf(zzkz.zzb(t));
    }

    private static List<?> zza(Object obj, long j) {
        return (List) zzlf.zzf(obj, j);
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x003f  */
    /* JADX WARNING: Removed duplicated region for block: B:163:0x05ad  */
    /* JADX WARNING: Removed duplicated region for block: B:178:0x05f0  */
    /* JADX WARNING: Removed duplicated region for block: B:331:0x0b5e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(T r14, com.google.android.gms.internal.firebase_auth.zzlw r15) throws java.io.IOException {
        /*
            r13 = this;
            int r0 = r15.zza()
            int r1 = com.google.android.gms.internal.firebase_auth.zzig.zze.zzk
            r2 = 267386880(0xff00000, float:2.3665827E-29)
            r3 = 0
            r4 = 1
            r5 = 0
            r6 = 1048575(0xfffff, float:1.469367E-39)
            if (r0 != r1) goto L_0x05c3
            com.google.android.gms.internal.firebase_auth.zzkz<?, ?> r0 = r13.zzq
            zza(r0, r14, (com.google.android.gms.internal.firebase_auth.zzlw) r15)
            boolean r0 = r13.zzh
            if (r0 == 0) goto L_0x0036
            com.google.android.gms.internal.firebase_auth.zzhv<?> r0 = r13.zzr
            com.google.android.gms.internal.firebase_auth.zzhz r0 = r0.zza((java.lang.Object) r14)
            com.google.android.gms.internal.firebase_auth.zzki<T, java.lang.Object> r1 = r0.zza
            boolean r1 = r1.isEmpty()
            if (r1 != 0) goto L_0x0036
            java.util.Iterator r0 = r0.zze()
            java.lang.Object r1 = r0.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            goto L_0x0038
        L_0x0036:
            r0 = r3
            r1 = r0
        L_0x0038:
            int[] r7 = r13.zzc
            int r7 = r7.length
            int r7 = r7 + -3
        L_0x003d:
            if (r7 < 0) goto L_0x05ab
            int r8 = r13.zzd((int) r7)
            int[] r9 = r13.zzc
            r9 = r9[r7]
        L_0x0049:
            if (r1 == 0) goto L_0x0067
            com.google.android.gms.internal.firebase_auth.zzhv<?> r10 = r13.zzr
            int r10 = r10.zza((java.util.Map.Entry<?, ?>) r1)
            if (r10 <= r9) goto L_0x0067
            com.google.android.gms.internal.firebase_auth.zzhv<?> r10 = r13.zzr
            r10.zza(r15, r1)
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0065
            java.lang.Object r1 = r0.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            goto L_0x0049
        L_0x0065:
            r1 = r3
            goto L_0x0049
        L_0x0067:
            r10 = r8 & r2
            int r10 = r10 >>> 20
            switch(r10) {
                case 0: goto L_0x0596;
                case 1: goto L_0x0584;
                case 2: goto L_0x0572;
                case 3: goto L_0x0560;
                case 4: goto L_0x054e;
                case 5: goto L_0x053c;
                case 6: goto L_0x052a;
                case 7: goto L_0x0517;
                case 8: goto L_0x0505;
                case 9: goto L_0x04ef;
                case 10: goto L_0x04db;
                case 11: goto L_0x04c8;
                case 12: goto L_0x04b5;
                case 13: goto L_0x04a2;
                case 14: goto L_0x048f;
                case 15: goto L_0x047c;
                case 16: goto L_0x0469;
                case 17: goto L_0x0453;
                case 18: goto L_0x043f;
                case 19: goto L_0x042b;
                case 20: goto L_0x0417;
                case 21: goto L_0x0403;
                case 22: goto L_0x03ef;
                case 23: goto L_0x03db;
                case 24: goto L_0x03c7;
                case 25: goto L_0x03b3;
                case 26: goto L_0x039f;
                case 27: goto L_0x0387;
                case 28: goto L_0x0373;
                case 29: goto L_0x035f;
                case 30: goto L_0x034b;
                case 31: goto L_0x0337;
                case 32: goto L_0x0323;
                case 33: goto L_0x030f;
                case 34: goto L_0x02fb;
                case 35: goto L_0x02e7;
                case 36: goto L_0x02d3;
                case 37: goto L_0x02bf;
                case 38: goto L_0x02ab;
                case 39: goto L_0x0297;
                case 40: goto L_0x0283;
                case 41: goto L_0x026f;
                case 42: goto L_0x025b;
                case 43: goto L_0x0247;
                case 44: goto L_0x0233;
                case 45: goto L_0x021f;
                case 46: goto L_0x020b;
                case 47: goto L_0x01f7;
                case 48: goto L_0x01e3;
                case 49: goto L_0x01cb;
                case 50: goto L_0x01bf;
                case 51: goto L_0x01ad;
                case 52: goto L_0x019b;
                case 53: goto L_0x0189;
                case 54: goto L_0x0177;
                case 55: goto L_0x0165;
                case 56: goto L_0x0153;
                case 57: goto L_0x0141;
                case 58: goto L_0x012f;
                case 59: goto L_0x011d;
                case 60: goto L_0x0107;
                case 61: goto L_0x00f3;
                case 62: goto L_0x00e1;
                case 63: goto L_0x00cf;
                case 64: goto L_0x00bd;
                case 65: goto L_0x00ab;
                case 66: goto L_0x0099;
                case 67: goto L_0x0087;
                case 68: goto L_0x0071;
                default: goto L_0x006f;
            }
        L_0x006f:
            goto L_0x05a7
        L_0x0071:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            com.google.android.gms.internal.firebase_auth.zzkh r10 = r13.zza((int) r7)
            r15.zzb((int) r9, (java.lang.Object) r8, (com.google.android.gms.internal.firebase_auth.zzkh) r10)
            goto L_0x05a7
        L_0x0087:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = zze(r14, r10)
            r15.zze((int) r9, (long) r10)
            goto L_0x05a7
        L_0x0099:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = zzd(r14, r10)
            r15.zzf(r9, r8)
            goto L_0x05a7
        L_0x00ab:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = zze(r14, r10)
            r15.zzb((int) r9, (long) r10)
            goto L_0x05a7
        L_0x00bd:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = zzd(r14, r10)
            r15.zza((int) r9, (int) r8)
            goto L_0x05a7
        L_0x00cf:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = zzd(r14, r10)
            r15.zzb((int) r9, (int) r8)
            goto L_0x05a7
        L_0x00e1:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = zzd(r14, r10)
            r15.zze((int) r9, (int) r8)
            goto L_0x05a7
        L_0x00f3:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            com.google.android.gms.internal.firebase_auth.zzgv r8 = (com.google.android.gms.internal.firebase_auth.zzgv) r8
            r15.zza((int) r9, (com.google.android.gms.internal.firebase_auth.zzgv) r8)
            goto L_0x05a7
        L_0x0107:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            com.google.android.gms.internal.firebase_auth.zzkh r10 = r13.zza((int) r7)
            r15.zza((int) r9, (java.lang.Object) r8, (com.google.android.gms.internal.firebase_auth.zzkh) r10)
            goto L_0x05a7
        L_0x011d:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            zza((int) r9, (java.lang.Object) r8, (com.google.android.gms.internal.firebase_auth.zzlw) r15)
            goto L_0x05a7
        L_0x012f:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            boolean r8 = zzf(r14, r10)
            r15.zza((int) r9, (boolean) r8)
            goto L_0x05a7
        L_0x0141:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = zzd(r14, r10)
            r15.zzd((int) r9, (int) r8)
            goto L_0x05a7
        L_0x0153:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = zze(r14, r10)
            r15.zzd((int) r9, (long) r10)
            goto L_0x05a7
        L_0x0165:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = zzd(r14, r10)
            r15.zzc((int) r9, (int) r8)
            goto L_0x05a7
        L_0x0177:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = zze(r14, r10)
            r15.zzc((int) r9, (long) r10)
            goto L_0x05a7
        L_0x0189:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = zze(r14, r10)
            r15.zza((int) r9, (long) r10)
            goto L_0x05a7
        L_0x019b:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            float r8 = zzc(r14, r10)
            r15.zza((int) r9, (float) r8)
            goto L_0x05a7
        L_0x01ad:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            double r10 = zzb(r14, (long) r10)
            r15.zza((int) r9, (double) r10)
            goto L_0x05a7
        L_0x01bf:
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            r13.zza((com.google.android.gms.internal.firebase_auth.zzlw) r15, (int) r9, (java.lang.Object) r8, (int) r7)
            goto L_0x05a7
        L_0x01cb:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkh r10 = r13.zza((int) r7)
            com.google.android.gms.internal.firebase_auth.zzkj.zzb((int) r9, (java.util.List<?>) r8, (com.google.android.gms.internal.firebase_auth.zzlw) r15, (com.google.android.gms.internal.firebase_auth.zzkh) r10)
            goto L_0x05a7
        L_0x01e3:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkj.zze(r9, r8, r15, r4)
            goto L_0x05a7
        L_0x01f7:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkj.zzj(r9, r8, r15, r4)
            goto L_0x05a7
        L_0x020b:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkj.zzg(r9, r8, r15, r4)
            goto L_0x05a7
        L_0x021f:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkj.zzl(r9, r8, r15, r4)
            goto L_0x05a7
        L_0x0233:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkj.zzm(r9, r8, r15, r4)
            goto L_0x05a7
        L_0x0247:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkj.zzi(r9, r8, r15, r4)
            goto L_0x05a7
        L_0x025b:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkj.zzn(r9, r8, r15, r4)
            goto L_0x05a7
        L_0x026f:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkj.zzk(r9, r8, r15, r4)
            goto L_0x05a7
        L_0x0283:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkj.zzf(r9, r8, r15, r4)
            goto L_0x05a7
        L_0x0297:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkj.zzh(r9, r8, r15, r4)
            goto L_0x05a7
        L_0x02ab:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkj.zzd(r9, r8, r15, r4)
            goto L_0x05a7
        L_0x02bf:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkj.zzc(r9, r8, r15, r4)
            goto L_0x05a7
        L_0x02d3:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkj.zzb((int) r9, (java.util.List<java.lang.Float>) r8, (com.google.android.gms.internal.firebase_auth.zzlw) r15, (boolean) r4)
            goto L_0x05a7
        L_0x02e7:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkj.zza((int) r9, (java.util.List<java.lang.Double>) r8, (com.google.android.gms.internal.firebase_auth.zzlw) r15, (boolean) r4)
            goto L_0x05a7
        L_0x02fb:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkj.zze(r9, r8, r15, r5)
            goto L_0x05a7
        L_0x030f:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkj.zzj(r9, r8, r15, r5)
            goto L_0x05a7
        L_0x0323:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkj.zzg(r9, r8, r15, r5)
            goto L_0x05a7
        L_0x0337:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkj.zzl(r9, r8, r15, r5)
            goto L_0x05a7
        L_0x034b:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkj.zzm(r9, r8, r15, r5)
            goto L_0x05a7
        L_0x035f:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkj.zzi(r9, r8, r15, r5)
            goto L_0x05a7
        L_0x0373:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkj.zzb((int) r9, (java.util.List<com.google.android.gms.internal.firebase_auth.zzgv>) r8, (com.google.android.gms.internal.firebase_auth.zzlw) r15)
            goto L_0x05a7
        L_0x0387:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkh r10 = r13.zza((int) r7)
            com.google.android.gms.internal.firebase_auth.zzkj.zza((int) r9, (java.util.List<?>) r8, (com.google.android.gms.internal.firebase_auth.zzlw) r15, (com.google.android.gms.internal.firebase_auth.zzkh) r10)
            goto L_0x05a7
        L_0x039f:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkj.zza((int) r9, (java.util.List<java.lang.String>) r8, (com.google.android.gms.internal.firebase_auth.zzlw) r15)
            goto L_0x05a7
        L_0x03b3:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkj.zzn(r9, r8, r15, r5)
            goto L_0x05a7
        L_0x03c7:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkj.zzk(r9, r8, r15, r5)
            goto L_0x05a7
        L_0x03db:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkj.zzf(r9, r8, r15, r5)
            goto L_0x05a7
        L_0x03ef:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkj.zzh(r9, r8, r15, r5)
            goto L_0x05a7
        L_0x0403:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkj.zzd(r9, r8, r15, r5)
            goto L_0x05a7
        L_0x0417:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkj.zzc(r9, r8, r15, r5)
            goto L_0x05a7
        L_0x042b:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkj.zzb((int) r9, (java.util.List<java.lang.Float>) r8, (com.google.android.gms.internal.firebase_auth.zzlw) r15, (boolean) r5)
            goto L_0x05a7
        L_0x043f:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkj.zza((int) r9, (java.util.List<java.lang.Double>) r8, (com.google.android.gms.internal.firebase_auth.zzlw) r15, (boolean) r5)
            goto L_0x05a7
        L_0x0453:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            com.google.android.gms.internal.firebase_auth.zzkh r10 = r13.zza((int) r7)
            r15.zzb((int) r9, (java.lang.Object) r8, (com.google.android.gms.internal.firebase_auth.zzkh) r10)
            goto L_0x05a7
        L_0x0469:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = com.google.android.gms.internal.firebase_auth.zzlf.zzb(r14, r10)
            r15.zze((int) r9, (long) r10)
            goto L_0x05a7
        L_0x047c:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = com.google.android.gms.internal.firebase_auth.zzlf.zza((java.lang.Object) r14, (long) r10)
            r15.zzf(r9, r8)
            goto L_0x05a7
        L_0x048f:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = com.google.android.gms.internal.firebase_auth.zzlf.zzb(r14, r10)
            r15.zzb((int) r9, (long) r10)
            goto L_0x05a7
        L_0x04a2:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = com.google.android.gms.internal.firebase_auth.zzlf.zza((java.lang.Object) r14, (long) r10)
            r15.zza((int) r9, (int) r8)
            goto L_0x05a7
        L_0x04b5:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = com.google.android.gms.internal.firebase_auth.zzlf.zza((java.lang.Object) r14, (long) r10)
            r15.zzb((int) r9, (int) r8)
            goto L_0x05a7
        L_0x04c8:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = com.google.android.gms.internal.firebase_auth.zzlf.zza((java.lang.Object) r14, (long) r10)
            r15.zze((int) r9, (int) r8)
            goto L_0x05a7
        L_0x04db:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            com.google.android.gms.internal.firebase_auth.zzgv r8 = (com.google.android.gms.internal.firebase_auth.zzgv) r8
            r15.zza((int) r9, (com.google.android.gms.internal.firebase_auth.zzgv) r8)
            goto L_0x05a7
        L_0x04ef:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            com.google.android.gms.internal.firebase_auth.zzkh r10 = r13.zza((int) r7)
            r15.zza((int) r9, (java.lang.Object) r8, (com.google.android.gms.internal.firebase_auth.zzkh) r10)
            goto L_0x05a7
        L_0x0505:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r10)
            zza((int) r9, (java.lang.Object) r8, (com.google.android.gms.internal.firebase_auth.zzlw) r15)
            goto L_0x05a7
        L_0x0517:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            boolean r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzc(r14, r10)
            r15.zza((int) r9, (boolean) r8)
            goto L_0x05a7
        L_0x052a:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = com.google.android.gms.internal.firebase_auth.zzlf.zza((java.lang.Object) r14, (long) r10)
            r15.zzd((int) r9, (int) r8)
            goto L_0x05a7
        L_0x053c:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = com.google.android.gms.internal.firebase_auth.zzlf.zzb(r14, r10)
            r15.zzd((int) r9, (long) r10)
            goto L_0x05a7
        L_0x054e:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = com.google.android.gms.internal.firebase_auth.zzlf.zza((java.lang.Object) r14, (long) r10)
            r15.zzc((int) r9, (int) r8)
            goto L_0x05a7
        L_0x0560:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = com.google.android.gms.internal.firebase_auth.zzlf.zzb(r14, r10)
            r15.zzc((int) r9, (long) r10)
            goto L_0x05a7
        L_0x0572:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = com.google.android.gms.internal.firebase_auth.zzlf.zzb(r14, r10)
            r15.zza((int) r9, (long) r10)
            goto L_0x05a7
        L_0x0584:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            float r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzd(r14, r10)
            r15.zza((int) r9, (float) r8)
            goto L_0x05a7
        L_0x0596:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            double r10 = com.google.android.gms.internal.firebase_auth.zzlf.zze(r14, r10)
            r15.zza((int) r9, (double) r10)
        L_0x05a7:
            int r7 = r7 + -3
            goto L_0x003d
        L_0x05ab:
            if (r1 == 0) goto L_0x05c2
            com.google.android.gms.internal.firebase_auth.zzhv<?> r14 = r13.zzr
            r14.zza(r15, r1)
            boolean r14 = r0.hasNext()
            if (r14 == 0) goto L_0x05c0
            java.lang.Object r14 = r0.next()
            java.util.Map$Entry r14 = (java.util.Map.Entry) r14
            r1 = r14
            goto L_0x05ab
        L_0x05c0:
            r1 = r3
            goto L_0x05ab
        L_0x05c2:
            return
        L_0x05c3:
            boolean r0 = r13.zzj
            if (r0 == 0) goto L_0x0b78
            boolean r0 = r13.zzh
            if (r0 == 0) goto L_0x05e8
            com.google.android.gms.internal.firebase_auth.zzhv<?> r0 = r13.zzr
            com.google.android.gms.internal.firebase_auth.zzhz r0 = r0.zza((java.lang.Object) r14)
            com.google.android.gms.internal.firebase_auth.zzki<T, java.lang.Object> r1 = r0.zza
            boolean r1 = r1.isEmpty()
            if (r1 != 0) goto L_0x05e8
            java.util.Iterator r0 = r0.zzd()
            java.lang.Object r1 = r0.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            goto L_0x05ea
        L_0x05e8:
            r0 = r3
            r1 = r0
        L_0x05ea:
            int[] r7 = r13.zzc
            int r7 = r7.length
            r8 = 0
        L_0x05ee:
            if (r8 >= r7) goto L_0x0b5c
            int r9 = r13.zzd((int) r8)
            int[] r10 = r13.zzc
            r10 = r10[r8]
        L_0x05fa:
            if (r1 == 0) goto L_0x0618
            com.google.android.gms.internal.firebase_auth.zzhv<?> r11 = r13.zzr
            int r11 = r11.zza((java.util.Map.Entry<?, ?>) r1)
            if (r11 > r10) goto L_0x0618
            com.google.android.gms.internal.firebase_auth.zzhv<?> r11 = r13.zzr
            r11.zza(r15, r1)
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0616
            java.lang.Object r1 = r0.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            goto L_0x05fa
        L_0x0616:
            r1 = r3
            goto L_0x05fa
        L_0x0618:
            r11 = r9 & r2
            int r11 = r11 >>> 20
            switch(r11) {
                case 0: goto L_0x0b47;
                case 1: goto L_0x0b35;
                case 2: goto L_0x0b23;
                case 3: goto L_0x0b11;
                case 4: goto L_0x0aff;
                case 5: goto L_0x0aed;
                case 6: goto L_0x0adb;
                case 7: goto L_0x0ac8;
                case 8: goto L_0x0ab6;
                case 9: goto L_0x0aa0;
                case 10: goto L_0x0a8c;
                case 11: goto L_0x0a79;
                case 12: goto L_0x0a66;
                case 13: goto L_0x0a53;
                case 14: goto L_0x0a40;
                case 15: goto L_0x0a2d;
                case 16: goto L_0x0a1a;
                case 17: goto L_0x0a04;
                case 18: goto L_0x09f0;
                case 19: goto L_0x09dc;
                case 20: goto L_0x09c8;
                case 21: goto L_0x09b4;
                case 22: goto L_0x09a0;
                case 23: goto L_0x098c;
                case 24: goto L_0x0978;
                case 25: goto L_0x0964;
                case 26: goto L_0x0950;
                case 27: goto L_0x0938;
                case 28: goto L_0x0924;
                case 29: goto L_0x0910;
                case 30: goto L_0x08fc;
                case 31: goto L_0x08e8;
                case 32: goto L_0x08d4;
                case 33: goto L_0x08c0;
                case 34: goto L_0x08ac;
                case 35: goto L_0x0898;
                case 36: goto L_0x0884;
                case 37: goto L_0x0870;
                case 38: goto L_0x085c;
                case 39: goto L_0x0848;
                case 40: goto L_0x0834;
                case 41: goto L_0x0820;
                case 42: goto L_0x080c;
                case 43: goto L_0x07f8;
                case 44: goto L_0x07e4;
                case 45: goto L_0x07d0;
                case 46: goto L_0x07bc;
                case 47: goto L_0x07a8;
                case 48: goto L_0x0794;
                case 49: goto L_0x077c;
                case 50: goto L_0x0770;
                case 51: goto L_0x075e;
                case 52: goto L_0x074c;
                case 53: goto L_0x073a;
                case 54: goto L_0x0728;
                case 55: goto L_0x0716;
                case 56: goto L_0x0704;
                case 57: goto L_0x06f2;
                case 58: goto L_0x06e0;
                case 59: goto L_0x06ce;
                case 60: goto L_0x06b8;
                case 61: goto L_0x06a4;
                case 62: goto L_0x0692;
                case 63: goto L_0x0680;
                case 64: goto L_0x066e;
                case 65: goto L_0x065c;
                case 66: goto L_0x064a;
                case 67: goto L_0x0638;
                case 68: goto L_0x0622;
                default: goto L_0x0620;
            }
        L_0x0620:
            goto L_0x0b58
        L_0x0622:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            com.google.android.gms.internal.firebase_auth.zzkh r11 = r13.zza((int) r8)
            r15.zzb((int) r10, (java.lang.Object) r9, (com.google.android.gms.internal.firebase_auth.zzkh) r11)
            goto L_0x0b58
        L_0x0638:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = zze(r14, r11)
            r15.zze((int) r10, (long) r11)
            goto L_0x0b58
        L_0x064a:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = zzd(r14, r11)
            r15.zzf(r10, r9)
            goto L_0x0b58
        L_0x065c:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = zze(r14, r11)
            r15.zzb((int) r10, (long) r11)
            goto L_0x0b58
        L_0x066e:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = zzd(r14, r11)
            r15.zza((int) r10, (int) r9)
            goto L_0x0b58
        L_0x0680:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = zzd(r14, r11)
            r15.zzb((int) r10, (int) r9)
            goto L_0x0b58
        L_0x0692:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = zzd(r14, r11)
            r15.zze((int) r10, (int) r9)
            goto L_0x0b58
        L_0x06a4:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            com.google.android.gms.internal.firebase_auth.zzgv r9 = (com.google.android.gms.internal.firebase_auth.zzgv) r9
            r15.zza((int) r10, (com.google.android.gms.internal.firebase_auth.zzgv) r9)
            goto L_0x0b58
        L_0x06b8:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            com.google.android.gms.internal.firebase_auth.zzkh r11 = r13.zza((int) r8)
            r15.zza((int) r10, (java.lang.Object) r9, (com.google.android.gms.internal.firebase_auth.zzkh) r11)
            goto L_0x0b58
        L_0x06ce:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            zza((int) r10, (java.lang.Object) r9, (com.google.android.gms.internal.firebase_auth.zzlw) r15)
            goto L_0x0b58
        L_0x06e0:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            boolean r9 = zzf(r14, r11)
            r15.zza((int) r10, (boolean) r9)
            goto L_0x0b58
        L_0x06f2:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = zzd(r14, r11)
            r15.zzd((int) r10, (int) r9)
            goto L_0x0b58
        L_0x0704:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = zze(r14, r11)
            r15.zzd((int) r10, (long) r11)
            goto L_0x0b58
        L_0x0716:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = zzd(r14, r11)
            r15.zzc((int) r10, (int) r9)
            goto L_0x0b58
        L_0x0728:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = zze(r14, r11)
            r15.zzc((int) r10, (long) r11)
            goto L_0x0b58
        L_0x073a:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = zze(r14, r11)
            r15.zza((int) r10, (long) r11)
            goto L_0x0b58
        L_0x074c:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            float r9 = zzc(r14, r11)
            r15.zza((int) r10, (float) r9)
            goto L_0x0b58
        L_0x075e:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            double r11 = zzb(r14, (long) r11)
            r15.zza((int) r10, (double) r11)
            goto L_0x0b58
        L_0x0770:
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            r13.zza((com.google.android.gms.internal.firebase_auth.zzlw) r15, (int) r10, (java.lang.Object) r9, (int) r8)
            goto L_0x0b58
        L_0x077c:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzkh r11 = r13.zza((int) r8)
            com.google.android.gms.internal.firebase_auth.zzkj.zzb((int) r10, (java.util.List<?>) r9, (com.google.android.gms.internal.firebase_auth.zzlw) r15, (com.google.android.gms.internal.firebase_auth.zzkh) r11)
            goto L_0x0b58
        L_0x0794:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzkj.zze(r10, r9, r15, r4)
            goto L_0x0b58
        L_0x07a8:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzkj.zzj(r10, r9, r15, r4)
            goto L_0x0b58
        L_0x07bc:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzkj.zzg(r10, r9, r15, r4)
            goto L_0x0b58
        L_0x07d0:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzkj.zzl(r10, r9, r15, r4)
            goto L_0x0b58
        L_0x07e4:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzkj.zzm(r10, r9, r15, r4)
            goto L_0x0b58
        L_0x07f8:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzkj.zzi(r10, r9, r15, r4)
            goto L_0x0b58
        L_0x080c:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzkj.zzn(r10, r9, r15, r4)
            goto L_0x0b58
        L_0x0820:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzkj.zzk(r10, r9, r15, r4)
            goto L_0x0b58
        L_0x0834:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzkj.zzf(r10, r9, r15, r4)
            goto L_0x0b58
        L_0x0848:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzkj.zzh(r10, r9, r15, r4)
            goto L_0x0b58
        L_0x085c:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzkj.zzd(r10, r9, r15, r4)
            goto L_0x0b58
        L_0x0870:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzkj.zzc(r10, r9, r15, r4)
            goto L_0x0b58
        L_0x0884:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzkj.zzb((int) r10, (java.util.List<java.lang.Float>) r9, (com.google.android.gms.internal.firebase_auth.zzlw) r15, (boolean) r4)
            goto L_0x0b58
        L_0x0898:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzkj.zza((int) r10, (java.util.List<java.lang.Double>) r9, (com.google.android.gms.internal.firebase_auth.zzlw) r15, (boolean) r4)
            goto L_0x0b58
        L_0x08ac:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzkj.zze(r10, r9, r15, r5)
            goto L_0x0b58
        L_0x08c0:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzkj.zzj(r10, r9, r15, r5)
            goto L_0x0b58
        L_0x08d4:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzkj.zzg(r10, r9, r15, r5)
            goto L_0x0b58
        L_0x08e8:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzkj.zzl(r10, r9, r15, r5)
            goto L_0x0b58
        L_0x08fc:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzkj.zzm(r10, r9, r15, r5)
            goto L_0x0b58
        L_0x0910:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzkj.zzi(r10, r9, r15, r5)
            goto L_0x0b58
        L_0x0924:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzkj.zzb((int) r10, (java.util.List<com.google.android.gms.internal.firebase_auth.zzgv>) r9, (com.google.android.gms.internal.firebase_auth.zzlw) r15)
            goto L_0x0b58
        L_0x0938:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzkh r11 = r13.zza((int) r8)
            com.google.android.gms.internal.firebase_auth.zzkj.zza((int) r10, (java.util.List<?>) r9, (com.google.android.gms.internal.firebase_auth.zzlw) r15, (com.google.android.gms.internal.firebase_auth.zzkh) r11)
            goto L_0x0b58
        L_0x0950:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzkj.zza((int) r10, (java.util.List<java.lang.String>) r9, (com.google.android.gms.internal.firebase_auth.zzlw) r15)
            goto L_0x0b58
        L_0x0964:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzkj.zzn(r10, r9, r15, r5)
            goto L_0x0b58
        L_0x0978:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzkj.zzk(r10, r9, r15, r5)
            goto L_0x0b58
        L_0x098c:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzkj.zzf(r10, r9, r15, r5)
            goto L_0x0b58
        L_0x09a0:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzkj.zzh(r10, r9, r15, r5)
            goto L_0x0b58
        L_0x09b4:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzkj.zzd(r10, r9, r15, r5)
            goto L_0x0b58
        L_0x09c8:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzkj.zzc(r10, r9, r15, r5)
            goto L_0x0b58
        L_0x09dc:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzkj.zzb((int) r10, (java.util.List<java.lang.Float>) r9, (com.google.android.gms.internal.firebase_auth.zzlw) r15, (boolean) r5)
            goto L_0x0b58
        L_0x09f0:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzkj.zza((int) r10, (java.util.List<java.lang.Double>) r9, (com.google.android.gms.internal.firebase_auth.zzlw) r15, (boolean) r5)
            goto L_0x0b58
        L_0x0a04:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            com.google.android.gms.internal.firebase_auth.zzkh r11 = r13.zza((int) r8)
            r15.zzb((int) r10, (java.lang.Object) r9, (com.google.android.gms.internal.firebase_auth.zzkh) r11)
            goto L_0x0b58
        L_0x0a1a:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = com.google.android.gms.internal.firebase_auth.zzlf.zzb(r14, r11)
            r15.zze((int) r10, (long) r11)
            goto L_0x0b58
        L_0x0a2d:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = com.google.android.gms.internal.firebase_auth.zzlf.zza((java.lang.Object) r14, (long) r11)
            r15.zzf(r10, r9)
            goto L_0x0b58
        L_0x0a40:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = com.google.android.gms.internal.firebase_auth.zzlf.zzb(r14, r11)
            r15.zzb((int) r10, (long) r11)
            goto L_0x0b58
        L_0x0a53:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = com.google.android.gms.internal.firebase_auth.zzlf.zza((java.lang.Object) r14, (long) r11)
            r15.zza((int) r10, (int) r9)
            goto L_0x0b58
        L_0x0a66:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = com.google.android.gms.internal.firebase_auth.zzlf.zza((java.lang.Object) r14, (long) r11)
            r15.zzb((int) r10, (int) r9)
            goto L_0x0b58
        L_0x0a79:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = com.google.android.gms.internal.firebase_auth.zzlf.zza((java.lang.Object) r14, (long) r11)
            r15.zze((int) r10, (int) r9)
            goto L_0x0b58
        L_0x0a8c:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            com.google.android.gms.internal.firebase_auth.zzgv r9 = (com.google.android.gms.internal.firebase_auth.zzgv) r9
            r15.zza((int) r10, (com.google.android.gms.internal.firebase_auth.zzgv) r9)
            goto L_0x0b58
        L_0x0aa0:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            com.google.android.gms.internal.firebase_auth.zzkh r11 = r13.zza((int) r8)
            r15.zza((int) r10, (java.lang.Object) r9, (com.google.android.gms.internal.firebase_auth.zzkh) r11)
            goto L_0x0b58
        L_0x0ab6:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzf(r14, r11)
            zza((int) r10, (java.lang.Object) r9, (com.google.android.gms.internal.firebase_auth.zzlw) r15)
            goto L_0x0b58
        L_0x0ac8:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            boolean r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzc(r14, r11)
            r15.zza((int) r10, (boolean) r9)
            goto L_0x0b58
        L_0x0adb:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = com.google.android.gms.internal.firebase_auth.zzlf.zza((java.lang.Object) r14, (long) r11)
            r15.zzd((int) r10, (int) r9)
            goto L_0x0b58
        L_0x0aed:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = com.google.android.gms.internal.firebase_auth.zzlf.zzb(r14, r11)
            r15.zzd((int) r10, (long) r11)
            goto L_0x0b58
        L_0x0aff:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = com.google.android.gms.internal.firebase_auth.zzlf.zza((java.lang.Object) r14, (long) r11)
            r15.zzc((int) r10, (int) r9)
            goto L_0x0b58
        L_0x0b11:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = com.google.android.gms.internal.firebase_auth.zzlf.zzb(r14, r11)
            r15.zzc((int) r10, (long) r11)
            goto L_0x0b58
        L_0x0b23:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = com.google.android.gms.internal.firebase_auth.zzlf.zzb(r14, r11)
            r15.zza((int) r10, (long) r11)
            goto L_0x0b58
        L_0x0b35:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            float r9 = com.google.android.gms.internal.firebase_auth.zzlf.zzd(r14, r11)
            r15.zza((int) r10, (float) r9)
            goto L_0x0b58
        L_0x0b47:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            double r11 = com.google.android.gms.internal.firebase_auth.zzlf.zze(r14, r11)
            r15.zza((int) r10, (double) r11)
        L_0x0b58:
            int r8 = r8 + 3
            goto L_0x05ee
        L_0x0b5c:
            if (r1 == 0) goto L_0x0b72
            com.google.android.gms.internal.firebase_auth.zzhv<?> r2 = r13.zzr
            r2.zza(r15, r1)
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0b70
            java.lang.Object r1 = r0.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            goto L_0x0b5c
        L_0x0b70:
            r1 = r3
            goto L_0x0b5c
        L_0x0b72:
            com.google.android.gms.internal.firebase_auth.zzkz<?, ?> r0 = r13.zzq
            zza(r0, r14, (com.google.android.gms.internal.firebase_auth.zzlw) r15)
            return
        L_0x0b78:
            r13.zzb(r14, (com.google.android.gms.internal.firebase_auth.zzlw) r15)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzjv.zza(java.lang.Object, com.google.android.gms.internal.firebase_auth.zzlw):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:188:0x0559  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0035  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void zzb(T r18, com.google.android.gms.internal.firebase_auth.zzlw r19) throws java.io.IOException {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            r2 = r19
            boolean r3 = r0.zzh
            if (r3 == 0) goto L_0x0025
            com.google.android.gms.internal.firebase_auth.zzhv<?> r3 = r0.zzr
            com.google.android.gms.internal.firebase_auth.zzhz r3 = r3.zza((java.lang.Object) r1)
            com.google.android.gms.internal.firebase_auth.zzki<T, java.lang.Object> r5 = r3.zza
            boolean r5 = r5.isEmpty()
            if (r5 != 0) goto L_0x0025
            java.util.Iterator r3 = r3.zzd()
            java.lang.Object r5 = r3.next()
            java.util.Map$Entry r5 = (java.util.Map.Entry) r5
            goto L_0x0027
        L_0x0025:
            r3 = 0
            r5 = 0
        L_0x0027:
            int[] r6 = r0.zzc
            int r6 = r6.length
            sun.misc.Unsafe r7 = zzb
            r10 = 0
            r11 = 1048575(0xfffff, float:1.469367E-39)
            r12 = 0
        L_0x0033:
            if (r10 >= r6) goto L_0x0557
            int r13 = r0.zzd((int) r10)
            int[] r14 = r0.zzc
            r15 = r14[r10]
            r16 = 267386880(0xff00000, float:2.3665827E-29)
            r16 = r13 & r16
            int r4 = r16 >>> 20
            boolean r9 = r0.zzj
            if (r9 != 0) goto L_0x0068
            r9 = 17
            if (r4 > r9) goto L_0x0068
            int r9 = r10 + 2
            r9 = r14[r9]
            r14 = 1048575(0xfffff, float:1.469367E-39)
            r8 = r9 & r14
            if (r8 == r11) goto L_0x0062
            long r11 = (long) r8
            int r12 = r7.getInt(r1, r11)
            r11 = r8
        L_0x0062:
            int r8 = r9 >>> 20
            r9 = 1
            int r8 = r9 << r8
            goto L_0x0069
        L_0x0068:
            r8 = 0
        L_0x0069:
            if (r5 == 0) goto L_0x0087
            com.google.android.gms.internal.firebase_auth.zzhv<?> r9 = r0.zzr
            int r9 = r9.zza((java.util.Map.Entry<?, ?>) r5)
            if (r9 > r15) goto L_0x0087
            com.google.android.gms.internal.firebase_auth.zzhv<?> r9 = r0.zzr
            r9.zza(r2, r5)
            boolean r5 = r3.hasNext()
            if (r5 == 0) goto L_0x0085
            java.lang.Object r5 = r3.next()
            java.util.Map$Entry r5 = (java.util.Map.Entry) r5
            goto L_0x0069
        L_0x0085:
            r5 = 0
            goto L_0x0069
        L_0x0087:
            r9 = 1048575(0xfffff, float:1.469367E-39)
            r13 = r13 & r9
            long r13 = (long) r13
            switch(r4) {
                case 0: goto L_0x0547;
                case 1: goto L_0x053a;
                case 2: goto L_0x052e;
                case 3: goto L_0x0522;
                case 4: goto L_0x0516;
                case 5: goto L_0x050a;
                case 6: goto L_0x04fe;
                case 7: goto L_0x04f1;
                case 8: goto L_0x04e5;
                case 9: goto L_0x04d4;
                case 10: goto L_0x04c5;
                case 11: goto L_0x04b8;
                case 12: goto L_0x04ab;
                case 13: goto L_0x049e;
                case 14: goto L_0x0491;
                case 15: goto L_0x0484;
                case 16: goto L_0x0477;
                case 17: goto L_0x0465;
                case 18: goto L_0x0452;
                case 19: goto L_0x043f;
                case 20: goto L_0x042c;
                case 21: goto L_0x0419;
                case 22: goto L_0x0406;
                case 23: goto L_0x03f3;
                case 24: goto L_0x03e0;
                case 25: goto L_0x03cd;
                case 26: goto L_0x03bb;
                case 27: goto L_0x03a4;
                case 28: goto L_0x0392;
                case 29: goto L_0x037f;
                case 30: goto L_0x036c;
                case 31: goto L_0x0359;
                case 32: goto L_0x0346;
                case 33: goto L_0x0333;
                case 34: goto L_0x0320;
                case 35: goto L_0x030d;
                case 36: goto L_0x02fa;
                case 37: goto L_0x02e7;
                case 38: goto L_0x02d4;
                case 39: goto L_0x02c1;
                case 40: goto L_0x02ae;
                case 41: goto L_0x029b;
                case 42: goto L_0x0288;
                case 43: goto L_0x0275;
                case 44: goto L_0x0262;
                case 45: goto L_0x024f;
                case 46: goto L_0x023c;
                case 47: goto L_0x0229;
                case 48: goto L_0x0216;
                case 49: goto L_0x01ff;
                case 50: goto L_0x01f5;
                case 51: goto L_0x01e2;
                case 52: goto L_0x01cf;
                case 53: goto L_0x01bc;
                case 54: goto L_0x01a9;
                case 55: goto L_0x0196;
                case 56: goto L_0x0183;
                case 57: goto L_0x0170;
                case 58: goto L_0x015d;
                case 59: goto L_0x014a;
                case 60: goto L_0x0133;
                case 61: goto L_0x011e;
                case 62: goto L_0x010b;
                case 63: goto L_0x00f8;
                case 64: goto L_0x00e5;
                case 65: goto L_0x00d2;
                case 66: goto L_0x00bf;
                case 67: goto L_0x00ac;
                case 68: goto L_0x0094;
                default: goto L_0x0091;
            }
        L_0x0091:
            r4 = 0
            goto L_0x0553
        L_0x0094:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x00a9
            java.lang.Object r4 = r7.getObject(r1, r13)
            com.google.android.gms.internal.firebase_auth.zzkh r8 = r0.zza((int) r10)
            r2.zzb((int) r15, (java.lang.Object) r4, (com.google.android.gms.internal.firebase_auth.zzkh) r8)
            r4 = 0
            goto L_0x0553
        L_0x00a9:
            r4 = 0
            goto L_0x0553
        L_0x00ac:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x00bc
            long r13 = zze(r1, r13)
            r2.zze((int) r15, (long) r13)
            r4 = 0
            goto L_0x0553
        L_0x00bc:
            r4 = 0
            goto L_0x0553
        L_0x00bf:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x00cf
            int r4 = zzd(r1, r13)
            r2.zzf(r15, r4)
            r4 = 0
            goto L_0x0553
        L_0x00cf:
            r4 = 0
            goto L_0x0553
        L_0x00d2:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x00e2
            long r13 = zze(r1, r13)
            r2.zzb((int) r15, (long) r13)
            r4 = 0
            goto L_0x0553
        L_0x00e2:
            r4 = 0
            goto L_0x0553
        L_0x00e5:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x00f5
            int r4 = zzd(r1, r13)
            r2.zza((int) r15, (int) r4)
            r4 = 0
            goto L_0x0553
        L_0x00f5:
            r4 = 0
            goto L_0x0553
        L_0x00f8:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x0108
            int r4 = zzd(r1, r13)
            r2.zzb((int) r15, (int) r4)
            r4 = 0
            goto L_0x0553
        L_0x0108:
            r4 = 0
            goto L_0x0553
        L_0x010b:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x011b
            int r4 = zzd(r1, r13)
            r2.zze((int) r15, (int) r4)
            r4 = 0
            goto L_0x0553
        L_0x011b:
            r4 = 0
            goto L_0x0553
        L_0x011e:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x0130
            java.lang.Object r4 = r7.getObject(r1, r13)
            com.google.android.gms.internal.firebase_auth.zzgv r4 = (com.google.android.gms.internal.firebase_auth.zzgv) r4
            r2.zza((int) r15, (com.google.android.gms.internal.firebase_auth.zzgv) r4)
            r4 = 0
            goto L_0x0553
        L_0x0130:
            r4 = 0
            goto L_0x0553
        L_0x0133:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x0147
            java.lang.Object r4 = r7.getObject(r1, r13)
            com.google.android.gms.internal.firebase_auth.zzkh r8 = r0.zza((int) r10)
            r2.zza((int) r15, (java.lang.Object) r4, (com.google.android.gms.internal.firebase_auth.zzkh) r8)
            r4 = 0
            goto L_0x0553
        L_0x0147:
            r4 = 0
            goto L_0x0553
        L_0x014a:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x015a
            java.lang.Object r4 = r7.getObject(r1, r13)
            zza((int) r15, (java.lang.Object) r4, (com.google.android.gms.internal.firebase_auth.zzlw) r2)
            r4 = 0
            goto L_0x0553
        L_0x015a:
            r4 = 0
            goto L_0x0553
        L_0x015d:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x016d
            boolean r4 = zzf(r1, r13)
            r2.zza((int) r15, (boolean) r4)
            r4 = 0
            goto L_0x0553
        L_0x016d:
            r4 = 0
            goto L_0x0553
        L_0x0170:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x0180
            int r4 = zzd(r1, r13)
            r2.zzd((int) r15, (int) r4)
            r4 = 0
            goto L_0x0553
        L_0x0180:
            r4 = 0
            goto L_0x0553
        L_0x0183:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x0193
            long r13 = zze(r1, r13)
            r2.zzd((int) r15, (long) r13)
            r4 = 0
            goto L_0x0553
        L_0x0193:
            r4 = 0
            goto L_0x0553
        L_0x0196:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x01a6
            int r4 = zzd(r1, r13)
            r2.zzc((int) r15, (int) r4)
            r4 = 0
            goto L_0x0553
        L_0x01a6:
            r4 = 0
            goto L_0x0553
        L_0x01a9:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x01b9
            long r13 = zze(r1, r13)
            r2.zzc((int) r15, (long) r13)
            r4 = 0
            goto L_0x0553
        L_0x01b9:
            r4 = 0
            goto L_0x0553
        L_0x01bc:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x01cc
            long r13 = zze(r1, r13)
            r2.zza((int) r15, (long) r13)
            r4 = 0
            goto L_0x0553
        L_0x01cc:
            r4 = 0
            goto L_0x0553
        L_0x01cf:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x01df
            float r4 = zzc(r1, r13)
            r2.zza((int) r15, (float) r4)
            r4 = 0
            goto L_0x0553
        L_0x01df:
            r4 = 0
            goto L_0x0553
        L_0x01e2:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x01f2
            double r13 = zzb(r1, (long) r13)
            r2.zza((int) r15, (double) r13)
            r4 = 0
            goto L_0x0553
        L_0x01f2:
            r4 = 0
            goto L_0x0553
        L_0x01f5:
            java.lang.Object r4 = r7.getObject(r1, r13)
            r0.zza((com.google.android.gms.internal.firebase_auth.zzlw) r2, (int) r15, (java.lang.Object) r4, (int) r10)
            r4 = 0
            goto L_0x0553
        L_0x01ff:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkh r13 = r0.zza((int) r10)
            com.google.android.gms.internal.firebase_auth.zzkj.zzb((int) r4, (java.util.List<?>) r8, (com.google.android.gms.internal.firebase_auth.zzlw) r2, (com.google.android.gms.internal.firebase_auth.zzkh) r13)
            r4 = 0
            goto L_0x0553
        L_0x0216:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.firebase_auth.zzkj.zze(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x0229:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.firebase_auth.zzkj.zzj(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x023c:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.firebase_auth.zzkj.zzg(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x024f:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.firebase_auth.zzkj.zzl(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x0262:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.firebase_auth.zzkj.zzm(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x0275:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.firebase_auth.zzkj.zzi(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x0288:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.firebase_auth.zzkj.zzn(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x029b:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.firebase_auth.zzkj.zzk(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x02ae:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.firebase_auth.zzkj.zzf(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x02c1:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.firebase_auth.zzkj.zzh(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x02d4:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.firebase_auth.zzkj.zzd(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x02e7:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.firebase_auth.zzkj.zzc(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x02fa:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.firebase_auth.zzkj.zzb((int) r4, (java.util.List<java.lang.Float>) r8, (com.google.android.gms.internal.firebase_auth.zzlw) r2, (boolean) r13)
            r4 = 0
            goto L_0x0553
        L_0x030d:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.firebase_auth.zzkj.zza((int) r4, (java.util.List<java.lang.Double>) r8, (com.google.android.gms.internal.firebase_auth.zzlw) r2, (boolean) r13)
            r4 = 0
            goto L_0x0553
        L_0x0320:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.firebase_auth.zzkj.zze(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x0333:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.firebase_auth.zzkj.zzj(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x0346:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.firebase_auth.zzkj.zzg(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x0359:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.firebase_auth.zzkj.zzl(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x036c:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.firebase_auth.zzkj.zzm(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x037f:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.firebase_auth.zzkj.zzi(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x0392:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkj.zzb((int) r4, (java.util.List<com.google.android.gms.internal.firebase_auth.zzgv>) r8, (com.google.android.gms.internal.firebase_auth.zzlw) r2)
            r4 = 0
            goto L_0x0553
        L_0x03a4:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkh r13 = r0.zza((int) r10)
            com.google.android.gms.internal.firebase_auth.zzkj.zza((int) r4, (java.util.List<?>) r8, (com.google.android.gms.internal.firebase_auth.zzlw) r2, (com.google.android.gms.internal.firebase_auth.zzkh) r13)
            r4 = 0
            goto L_0x0553
        L_0x03bb:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzkj.zza((int) r4, (java.util.List<java.lang.String>) r8, (com.google.android.gms.internal.firebase_auth.zzlw) r2)
            r4 = 0
            goto L_0x0553
        L_0x03cd:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.firebase_auth.zzkj.zzn(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x03e0:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.firebase_auth.zzkj.zzk(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x03f3:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.firebase_auth.zzkj.zzf(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x0406:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.firebase_auth.zzkj.zzh(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x0419:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.firebase_auth.zzkj.zzd(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x042c:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.firebase_auth.zzkj.zzc(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x043f:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.firebase_auth.zzkj.zzb((int) r4, (java.util.List<java.lang.Float>) r8, (com.google.android.gms.internal.firebase_auth.zzlw) r2, (boolean) r13)
            r4 = 0
            goto L_0x0553
        L_0x0452:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.firebase_auth.zzkj.zza((int) r4, (java.util.List<java.lang.Double>) r8, (com.google.android.gms.internal.firebase_auth.zzlw) r2, (boolean) r13)
            r4 = 0
            goto L_0x0553
        L_0x0465:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            java.lang.Object r8 = r7.getObject(r1, r13)
            com.google.android.gms.internal.firebase_auth.zzkh r13 = r0.zza((int) r10)
            r2.zzb((int) r15, (java.lang.Object) r8, (com.google.android.gms.internal.firebase_auth.zzkh) r13)
            goto L_0x0553
        L_0x0477:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            long r13 = r7.getLong(r1, r13)
            r2.zze((int) r15, (long) r13)
            goto L_0x0553
        L_0x0484:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            int r8 = r7.getInt(r1, r13)
            r2.zzf(r15, r8)
            goto L_0x0553
        L_0x0491:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            long r13 = r7.getLong(r1, r13)
            r2.zzb((int) r15, (long) r13)
            goto L_0x0553
        L_0x049e:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            int r8 = r7.getInt(r1, r13)
            r2.zza((int) r15, (int) r8)
            goto L_0x0553
        L_0x04ab:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            int r8 = r7.getInt(r1, r13)
            r2.zzb((int) r15, (int) r8)
            goto L_0x0553
        L_0x04b8:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            int r8 = r7.getInt(r1, r13)
            r2.zze((int) r15, (int) r8)
            goto L_0x0553
        L_0x04c5:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            java.lang.Object r8 = r7.getObject(r1, r13)
            com.google.android.gms.internal.firebase_auth.zzgv r8 = (com.google.android.gms.internal.firebase_auth.zzgv) r8
            r2.zza((int) r15, (com.google.android.gms.internal.firebase_auth.zzgv) r8)
            goto L_0x0553
        L_0x04d4:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            java.lang.Object r8 = r7.getObject(r1, r13)
            com.google.android.gms.internal.firebase_auth.zzkh r13 = r0.zza((int) r10)
            r2.zza((int) r15, (java.lang.Object) r8, (com.google.android.gms.internal.firebase_auth.zzkh) r13)
            goto L_0x0553
        L_0x04e5:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            java.lang.Object r8 = r7.getObject(r1, r13)
            zza((int) r15, (java.lang.Object) r8, (com.google.android.gms.internal.firebase_auth.zzlw) r2)
            goto L_0x0553
        L_0x04f1:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            boolean r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzc(r1, r13)
            r2.zza((int) r15, (boolean) r8)
            goto L_0x0553
        L_0x04fe:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            int r8 = r7.getInt(r1, r13)
            r2.zzd((int) r15, (int) r8)
            goto L_0x0553
        L_0x050a:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            long r13 = r7.getLong(r1, r13)
            r2.zzd((int) r15, (long) r13)
            goto L_0x0553
        L_0x0516:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            int r8 = r7.getInt(r1, r13)
            r2.zzc((int) r15, (int) r8)
            goto L_0x0553
        L_0x0522:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            long r13 = r7.getLong(r1, r13)
            r2.zzc((int) r15, (long) r13)
            goto L_0x0553
        L_0x052e:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            long r13 = r7.getLong(r1, r13)
            r2.zza((int) r15, (long) r13)
            goto L_0x0553
        L_0x053a:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            float r8 = com.google.android.gms.internal.firebase_auth.zzlf.zzd(r1, r13)
            r2.zza((int) r15, (float) r8)
            goto L_0x0553
        L_0x0547:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            double r13 = com.google.android.gms.internal.firebase_auth.zzlf.zze(r1, r13)
            r2.zza((int) r15, (double) r13)
        L_0x0553:
            int r10 = r10 + 3
            goto L_0x0033
        L_0x0557:
            if (r5 == 0) goto L_0x056e
            com.google.android.gms.internal.firebase_auth.zzhv<?> r4 = r0.zzr
            r4.zza(r2, r5)
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x056c
            java.lang.Object r4 = r3.next()
            java.util.Map$Entry r4 = (java.util.Map.Entry) r4
            r5 = r4
            goto L_0x0557
        L_0x056c:
            r5 = 0
            goto L_0x0557
        L_0x056e:
            com.google.android.gms.internal.firebase_auth.zzkz<?, ?> r3 = r0.zzq
            zza(r3, r1, (com.google.android.gms.internal.firebase_auth.zzlw) r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzjv.zzb(java.lang.Object, com.google.android.gms.internal.firebase_auth.zzlw):void");
    }

    private final <K, V> void zza(zzlw zzlw, int i, Object obj, int i2) throws IOException {
        if (obj != null) {
            zzlw.zza(i, this.zzs.zzf(zzb(i2)), this.zzs.zzb(obj));
        }
    }

    private static <UT, UB> void zza(zzkz<UT, UB> zzkz, T t, zzlw zzlw) throws IOException {
        zzkz.zza(zzkz.zzb(t), zzlw);
    }

    public final void zza(T t, zzke zzke, zzht zzht) throws IOException {
        int i;
        Object obj;
        zzhz<?> zzhz;
        T t2 = t;
        zzke zzke2 = zzke;
        zzht zzht2 = zzht;
        if (zzht2 != null) {
            zzkz<?, ?> zzkz = this.zzq;
            zzhv<?> zzhv = this.zzr;
            zzhz<?> zzhz2 = null;
            Object obj2 = null;
            while (true) {
                try {
                    int zza2 = zzke.zza();
                    if (zza2 < this.zze || zza2 > this.zzf) {
                        i = -1;
                    } else {
                        int i2 = 0;
                        int length = (this.zzc.length / 3) - 1;
                        while (true) {
                            if (i2 > length) {
                                i = -1;
                            } else {
                                int i3 = (length + i2) >>> 1;
                                int i4 = i3 * 3;
                                int i5 = this.zzc[i4];
                                if (zza2 == i5) {
                                    i = i4;
                                } else if (zza2 < i5) {
                                    length = i3 - 1;
                                } else {
                                    i2 = i3 + 1;
                                }
                            }
                        }
                    }
                    if (i >= 0) {
                        int zzd2 = zzd(i);
                        switch ((267386880 & zzd2) >>> 20) {
                            case 0:
                                zzlf.zza((Object) t2, (long) (zzd2 & 1048575), zzke.zzd());
                                zzb(t2, i);
                                break;
                            case 1:
                                zzlf.zza((Object) t2, (long) (zzd2 & 1048575), zzke.zze());
                                zzb(t2, i);
                                break;
                            case 2:
                                zzlf.zza((Object) t2, (long) (zzd2 & 1048575), zzke.zzg());
                                zzb(t2, i);
                                break;
                            case 3:
                                zzlf.zza((Object) t2, (long) (zzd2 & 1048575), zzke.zzf());
                                zzb(t2, i);
                                break;
                            case 4:
                                zzlf.zza((Object) t2, (long) (zzd2 & 1048575), zzke.zzh());
                                zzb(t2, i);
                                break;
                            case 5:
                                zzlf.zza((Object) t2, (long) (zzd2 & 1048575), zzke.zzi());
                                zzb(t2, i);
                                break;
                            case 6:
                                zzlf.zza((Object) t2, (long) (zzd2 & 1048575), zzke.zzj());
                                zzb(t2, i);
                                break;
                            case 7:
                                zzlf.zza((Object) t2, (long) (zzd2 & 1048575), zzke.zzk());
                                zzb(t2, i);
                                break;
                            case 8:
                                zza((Object) t2, zzd2, zzke2);
                                zzb(t2, i);
                                break;
                            case 9:
                                if (!zza(t2, i)) {
                                    zzlf.zza((Object) t2, (long) (zzd2 & 1048575), zzke2.zza(zza(i), zzht2));
                                    zzb(t2, i);
                                    break;
                                } else {
                                    long j = (long) (zzd2 & 1048575);
                                    zzlf.zza((Object) t2, j, zzii.zza(zzlf.zzf(t2, j), zzke2.zza(zza(i), zzht2)));
                                    break;
                                }
                            case 10:
                                zzlf.zza((Object) t2, (long) (zzd2 & 1048575), (Object) zzke.zzn());
                                zzb(t2, i);
                                break;
                            case 11:
                                zzlf.zza((Object) t2, (long) (zzd2 & 1048575), zzke.zzo());
                                zzb(t2, i);
                                break;
                            case 12:
                                int zzp2 = zzke.zzp();
                                zzin zzc2 = zzc(i);
                                if (zzc2 != null) {
                                    if (!zzc2.zza(zzp2)) {
                                        obj2 = zzkj.zza(zza2, zzp2, obj2, zzkz);
                                        break;
                                    }
                                }
                                zzlf.zza((Object) t2, (long) (zzd2 & 1048575), zzp2);
                                zzb(t2, i);
                                break;
                            case 13:
                                zzlf.zza((Object) t2, (long) (zzd2 & 1048575), zzke.zzq());
                                zzb(t2, i);
                                break;
                            case 14:
                                zzlf.zza((Object) t2, (long) (zzd2 & 1048575), zzke.zzr());
                                zzb(t2, i);
                                break;
                            case 15:
                                zzlf.zza((Object) t2, (long) (zzd2 & 1048575), zzke.zzs());
                                zzb(t2, i);
                                break;
                            case 16:
                                zzlf.zza((Object) t2, (long) (zzd2 & 1048575), zzke.zzt());
                                zzb(t2, i);
                                break;
                            case 17:
                                if (!zza(t2, i)) {
                                    zzlf.zza((Object) t2, (long) (zzd2 & 1048575), zzke2.zzb(zza(i), zzht2));
                                    zzb(t2, i);
                                    break;
                                } else {
                                    long j2 = (long) (zzd2 & 1048575);
                                    zzlf.zza((Object) t2, j2, zzii.zza(zzlf.zzf(t2, j2), zzke2.zzb(zza(i), zzht2)));
                                    break;
                                }
                            case 18:
                                zzke2.zza(this.zzp.zza(t2, (long) (zzd2 & 1048575)));
                                break;
                            case 19:
                                zzke2.zzb(this.zzp.zza(t2, (long) (zzd2 & 1048575)));
                                break;
                            case 20:
                                zzke2.zzd(this.zzp.zza(t2, (long) (zzd2 & 1048575)));
                                break;
                            case 21:
                                zzke2.zzc(this.zzp.zza(t2, (long) (zzd2 & 1048575)));
                                break;
                            case 22:
                                zzke2.zze(this.zzp.zza(t2, (long) (zzd2 & 1048575)));
                                break;
                            case 23:
                                zzke2.zzf(this.zzp.zza(t2, (long) (zzd2 & 1048575)));
                                break;
                            case 24:
                                zzke2.zzg(this.zzp.zza(t2, (long) (zzd2 & 1048575)));
                                break;
                            case 25:
                                zzke2.zzh(this.zzp.zza(t2, (long) (zzd2 & 1048575)));
                                break;
                            case 26:
                                if (!zzf(zzd2)) {
                                    zzke2.zzi(this.zzp.zza(t2, (long) (zzd2 & 1048575)));
                                    break;
                                } else {
                                    zzke2.zzj(this.zzp.zza(t2, (long) (zzd2 & 1048575)));
                                    break;
                                }
                            case 27:
                                zzke2.zza(this.zzp.zza(t2, (long) (zzd2 & 1048575)), zza(i), zzht2);
                                break;
                            case 28:
                                zzke2.zzk(this.zzp.zza(t2, (long) (zzd2 & 1048575)));
                                break;
                            case 29:
                                zzke2.zzl(this.zzp.zza(t2, (long) (zzd2 & 1048575)));
                                break;
                            case 30:
                                List zza3 = this.zzp.zza(t2, (long) (zzd2 & 1048575));
                                zzke2.zzm(zza3);
                                obj2 = zzkj.zza(zza2, zza3, zzc(i), obj2, zzkz);
                                break;
                            case 31:
                                zzke2.zzn(this.zzp.zza(t2, (long) (zzd2 & 1048575)));
                                break;
                            case 32:
                                zzke2.zzo(this.zzp.zza(t2, (long) (zzd2 & 1048575)));
                                break;
                            case 33:
                                zzke2.zzp(this.zzp.zza(t2, (long) (zzd2 & 1048575)));
                                break;
                            case 34:
                                zzke2.zzq(this.zzp.zza(t2, (long) (zzd2 & 1048575)));
                                break;
                            case 35:
                                zzke2.zza(this.zzp.zza(t2, (long) (zzd2 & 1048575)));
                                break;
                            case 36:
                                zzke2.zzb(this.zzp.zza(t2, (long) (zzd2 & 1048575)));
                                break;
                            case 37:
                                zzke2.zzd(this.zzp.zza(t2, (long) (zzd2 & 1048575)));
                                break;
                            case 38:
                                zzke2.zzc(this.zzp.zza(t2, (long) (zzd2 & 1048575)));
                                break;
                            case 39:
                                zzke2.zze(this.zzp.zza(t2, (long) (zzd2 & 1048575)));
                                break;
                            case 40:
                                zzke2.zzf(this.zzp.zza(t2, (long) (zzd2 & 1048575)));
                                break;
                            case 41:
                                zzke2.zzg(this.zzp.zza(t2, (long) (zzd2 & 1048575)));
                                break;
                            case 42:
                                zzke2.zzh(this.zzp.zza(t2, (long) (zzd2 & 1048575)));
                                break;
                            case 43:
                                zzke2.zzl(this.zzp.zza(t2, (long) (zzd2 & 1048575)));
                                break;
                            case 44:
                                List zza4 = this.zzp.zza(t2, (long) (zzd2 & 1048575));
                                zzke2.zzm(zza4);
                                obj2 = zzkj.zza(zza2, zza4, zzc(i), obj2, zzkz);
                                break;
                            case 45:
                                zzke2.zzn(this.zzp.zza(t2, (long) (zzd2 & 1048575)));
                                break;
                            case 46:
                                zzke2.zzo(this.zzp.zza(t2, (long) (zzd2 & 1048575)));
                                break;
                            case 47:
                                zzke2.zzp(this.zzp.zza(t2, (long) (zzd2 & 1048575)));
                                break;
                            case 48:
                                zzke2.zzq(this.zzp.zza(t2, (long) (zzd2 & 1048575)));
                                break;
                            case 49:
                                zzke2.zzb(this.zzp.zza(t2, (long) (zzd2 & 1048575)), zza(i), zzht2);
                                break;
                            case 50:
                                Object zzb2 = zzb(i);
                                long zzd3 = (long) (zzd(i) & 1048575);
                                Object zzf2 = zzlf.zzf(t2, zzd3);
                                if (zzf2 == null) {
                                    zzf2 = this.zzs.zze(zzb2);
                                    zzlf.zza((Object) t2, zzd3, zzf2);
                                } else if (this.zzs.zzc(zzf2)) {
                                    Object zze2 = this.zzs.zze(zzb2);
                                    this.zzs.zza(zze2, zzf2);
                                    zzlf.zza((Object) t2, zzd3, zze2);
                                    zzf2 = zze2;
                                }
                                zzke2.zza(this.zzs.zza(zzf2), this.zzs.zzf(zzb2), zzht2);
                                break;
                            case 51:
                                zzlf.zza((Object) t2, (long) (zzd2 & 1048575), (Object) Double.valueOf(zzke.zzd()));
                                zzb(t2, zza2, i);
                                break;
                            case 52:
                                zzlf.zza((Object) t2, (long) (zzd2 & 1048575), (Object) Float.valueOf(zzke.zze()));
                                zzb(t2, zza2, i);
                                break;
                            case 53:
                                zzlf.zza((Object) t2, (long) (zzd2 & 1048575), (Object) Long.valueOf(zzke.zzg()));
                                zzb(t2, zza2, i);
                                break;
                            case 54:
                                zzlf.zza((Object) t2, (long) (zzd2 & 1048575), (Object) Long.valueOf(zzke.zzf()));
                                zzb(t2, zza2, i);
                                break;
                            case 55:
                                zzlf.zza((Object) t2, (long) (zzd2 & 1048575), (Object) Integer.valueOf(zzke.zzh()));
                                zzb(t2, zza2, i);
                                break;
                            case 56:
                                zzlf.zza((Object) t2, (long) (zzd2 & 1048575), (Object) Long.valueOf(zzke.zzi()));
                                zzb(t2, zza2, i);
                                break;
                            case 57:
                                zzlf.zza((Object) t2, (long) (zzd2 & 1048575), (Object) Integer.valueOf(zzke.zzj()));
                                zzb(t2, zza2, i);
                                break;
                            case 58:
                                zzlf.zza((Object) t2, (long) (zzd2 & 1048575), (Object) Boolean.valueOf(zzke.zzk()));
                                zzb(t2, zza2, i);
                                break;
                            case 59:
                                zza((Object) t2, zzd2, zzke2);
                                zzb(t2, zza2, i);
                                break;
                            case 60:
                                if (zza(t2, zza2, i)) {
                                    long j3 = (long) (zzd2 & 1048575);
                                    zzlf.zza((Object) t2, j3, zzii.zza(zzlf.zzf(t2, j3), zzke2.zza(zza(i), zzht2)));
                                } else {
                                    zzlf.zza((Object) t2, (long) (zzd2 & 1048575), zzke2.zza(zza(i), zzht2));
                                    zzb(t2, i);
                                }
                                zzb(t2, zza2, i);
                                break;
                            case 61:
                                zzlf.zza((Object) t2, (long) (zzd2 & 1048575), (Object) zzke.zzn());
                                zzb(t2, zza2, i);
                                break;
                            case 62:
                                zzlf.zza((Object) t2, (long) (zzd2 & 1048575), (Object) Integer.valueOf(zzke.zzo()));
                                zzb(t2, zza2, i);
                                break;
                            case 63:
                                int zzp3 = zzke.zzp();
                                zzin zzc3 = zzc(i);
                                if (zzc3 != null) {
                                    if (!zzc3.zza(zzp3)) {
                                        obj2 = zzkj.zza(zza2, zzp3, obj2, zzkz);
                                        break;
                                    }
                                }
                                zzlf.zza((Object) t2, (long) (zzd2 & 1048575), (Object) Integer.valueOf(zzp3));
                                zzb(t2, zza2, i);
                                break;
                            case 64:
                                zzlf.zza((Object) t2, (long) (zzd2 & 1048575), (Object) Integer.valueOf(zzke.zzq()));
                                zzb(t2, zza2, i);
                                break;
                            case 65:
                                zzlf.zza((Object) t2, (long) (zzd2 & 1048575), (Object) Long.valueOf(zzke.zzr()));
                                zzb(t2, zza2, i);
                                break;
                            case 66:
                                zzlf.zza((Object) t2, (long) (zzd2 & 1048575), (Object) Integer.valueOf(zzke.zzs()));
                                zzb(t2, zza2, i);
                                break;
                            case 67:
                                zzlf.zza((Object) t2, (long) (zzd2 & 1048575), (Object) Long.valueOf(zzke.zzt()));
                                zzb(t2, zza2, i);
                                break;
                            case 68:
                                zzlf.zza((Object) t2, (long) (zzd2 & 1048575), zzke2.zzb(zza(i), zzht2));
                                zzb(t2, zza2, i);
                                break;
                            default:
                                if (obj2 == null) {
                                    obj2 = zzkz.zza();
                                }
                                if (zzkz.zza(obj2, zzke2)) {
                                    break;
                                } else {
                                    for (int i6 = this.zzm; i6 < this.zzn; i6++) {
                                        obj2 = zza((Object) t2, this.zzl[i6], obj2, zzkz);
                                    }
                                    if (obj2 != null) {
                                        zzkz.zzb((Object) t2, obj2);
                                        return;
                                    }
                                    return;
                                }
                        }
                    } else if (zza2 == Integer.MAX_VALUE) {
                        for (int i7 = this.zzm; i7 < this.zzn; i7++) {
                            obj2 = zza((Object) t2, this.zzl[i7], obj2, zzkz);
                        }
                        if (obj2 != null) {
                            zzkz.zzb((Object) t2, obj2);
                            return;
                        }
                        return;
                    } else {
                        if (!this.zzh) {
                            obj = null;
                        } else {
                            obj = zzhv.zza(zzht2, this.zzg, zza2);
                        }
                        if (obj != null) {
                            if (zzhz2 == null) {
                                zzhz = zzhv.zzb(t2);
                            } else {
                                zzhz = zzhz2;
                            }
                            obj2 = zzhv.zza(zzke, obj, zzht, zzhz, obj2, zzkz);
                            zzhz2 = zzhz;
                        } else {
                            zzkz.zza(zzke2);
                            if (obj2 == null) {
                                obj2 = zzkz.zzc(t2);
                            }
                            if (!zzkz.zza(obj2, zzke2)) {
                                for (int i8 = this.zzm; i8 < this.zzn; i8++) {
                                    obj2 = zza((Object) t2, this.zzl[i8], obj2, zzkz);
                                }
                                if (obj2 != null) {
                                    zzkz.zzb((Object) t2, obj2);
                                    return;
                                }
                                return;
                            }
                        }
                    }
                } catch (zziq e) {
                    zzkz.zza(zzke2);
                    if (obj2 == null) {
                        obj2 = zzkz.zzc(t2);
                    }
                    if (!zzkz.zza(obj2, zzke2)) {
                        for (int i9 = this.zzm; i9 < this.zzn; i9++) {
                            obj2 = zza((Object) t2, this.zzl[i9], obj2, zzkz);
                        }
                        if (obj2 != null) {
                            zzkz.zzb((Object) t2, obj2);
                            return;
                        }
                        return;
                    }
                } catch (Throwable th) {
                    Throwable th2 = th;
                    for (int i10 = this.zzm; i10 < this.zzn; i10++) {
                        obj2 = zza((Object) t2, this.zzl[i10], obj2, zzkz);
                    }
                    if (obj2 != null) {
                        zzkz.zzb((Object) t2, obj2);
                    }
                    throw th2;
                }
            }
        } else {
            throw null;
        }
    }

    private final zzkh zza(int i) {
        int i2 = (i / 3) << 1;
        zzkh zzkh = (zzkh) this.zzd[i2];
        if (zzkh != null) {
            return zzkh;
        }
        zzkh zza2 = zzkd.zza().zza((Class) this.zzd[i2 + 1]);
        this.zzd[i2] = zza2;
        return zza2;
    }

    private final Object zzb(int i) {
        return this.zzd[(i / 3) << 1];
    }

    private final zzin zzc(int i) {
        return (zzin) this.zzd[((i / 3) << 1) + 1];
    }

    public final void zzb(T t) {
        int i;
        int i2 = this.zzm;
        while (true) {
            i = this.zzn;
            if (i2 >= i) {
                break;
            }
            long zzd2 = (long) (zzd(this.zzl[i2]) & 1048575);
            Object zzf2 = zzlf.zzf(t, zzd2);
            if (zzf2 != null) {
                zzlf.zza((Object) t, zzd2, this.zzs.zzd(zzf2));
            }
            i2++;
        }
        int length = this.zzl.length;
        while (i < length) {
            this.zzp.zzb(t, (long) this.zzl[i]);
            i++;
        }
        this.zzq.zzd(t);
        if (this.zzh) {
            this.zzr.zzc(t);
        }
    }

    private final <UT, UB> UB zza(Object obj, int i, UB ub, zzkz<UT, UB> zzkz) {
        zzin zzc2;
        int i2 = this.zzc[i];
        Object zzf2 = zzlf.zzf(obj, (long) (zzd(i) & 1048575));
        if (zzf2 == null || (zzc2 = zzc(i)) == null) {
            return ub;
        }
        return zza(i, i2, this.zzs.zza(zzf2), zzc2, ub, zzkz);
    }

    private final <K, V, UT, UB> UB zza(int i, int i2, Map<K, V> map, zzin zzin, UB ub, zzkz<UT, UB> zzkz) {
        zzji<?, ?> zzf2 = this.zzs.zzf(zzb(i));
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry next = it.next();
            if (!zzin.zza(((Integer) next.getValue()).intValue())) {
                if (ub == null) {
                    ub = zzkz.zza();
                }
                zzhd zzc2 = zzgv.zzc(zzjj.zza(zzf2, next.getKey(), next.getValue()));
                try {
                    zzjj.zza(zzc2.zzb(), zzf2, next.getKey(), next.getValue());
                    zzkz.zza(ub, i2, zzc2.zza());
                    it.remove();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return ub;
    }

    public final boolean zzc(T t) {
        int i;
        int i2;
        T t2 = t;
        int i3 = 1048575;
        int i4 = 0;
        int i5 = 0;
        while (true) {
            boolean z = true;
            if (i5 >= this.zzm) {
                return !this.zzh || this.zzr.zza((Object) t2).zzf();
            }
            int i6 = this.zzl[i5];
            int i7 = this.zzc[i6];
            int zzd2 = zzd(i6);
            int i8 = this.zzc[i6 + 2];
            int i9 = i8 & 1048575;
            int i10 = 1 << (i8 >>> 20);
            if (i9 == i3) {
                i2 = i3;
                i = i4;
            } else if (i9 != 1048575) {
                i = zzb.getInt(t2, (long) i9);
                i2 = i9;
            } else {
                i = i4;
                i2 = i9;
            }
            if (((268435456 & zzd2) != 0) && !zza(t, i6, i2, i, i10)) {
                return false;
            }
            int i11 = (267386880 & zzd2) >>> 20;
            if (i11 != 9 && i11 != 17) {
                if (i11 != 27) {
                    if (i11 == 60 || i11 == 68) {
                        if (zza(t2, i7, i6) && !zza((Object) t2, zzd2, zza(i6))) {
                            return false;
                        }
                    } else if (i11 != 49) {
                        if (i11 != 50) {
                            continue;
                        } else {
                            Map<?, ?> zzb2 = this.zzs.zzb(zzlf.zzf(t2, (long) (zzd2 & 1048575)));
                            if (!zzb2.isEmpty()) {
                                if (this.zzs.zzf(zzb(i6)).zzc.zza() == zzlt.MESSAGE) {
                                    zzkh<?> zzkh = null;
                                    Iterator<?> it = zzb2.values().iterator();
                                    while (true) {
                                        if (!it.hasNext()) {
                                            break;
                                        }
                                        Object next = it.next();
                                        if (zzkh == null) {
                                            zzkh = zzkd.zza().zza(next.getClass());
                                        }
                                        if (!zzkh.zzc(next)) {
                                            z = false;
                                            break;
                                        }
                                    }
                                }
                            }
                            if (!z) {
                                return false;
                            }
                        }
                    }
                }
                List list = (List) zzlf.zzf(t2, (long) (zzd2 & 1048575));
                if (!list.isEmpty()) {
                    zzkh zza2 = zza(i6);
                    int i12 = 0;
                    while (true) {
                        if (i12 >= list.size()) {
                            break;
                        } else if (!zza2.zzc(list.get(i12))) {
                            z = false;
                            break;
                        } else {
                            i12++;
                        }
                    }
                }
                if (!z) {
                    return false;
                }
            } else if (zza(t, i6, i2, i, i10) && !zza((Object) t2, zzd2, zza(i6))) {
                return false;
            }
            i5++;
            i3 = i2;
            i4 = i;
        }
    }

    private static boolean zza(Object obj, int i, zzkh zzkh) {
        return zzkh.zzc(zzlf.zzf(obj, (long) (i & 1048575)));
    }

    private static void zza(int i, Object obj, zzlw zzlw) throws IOException {
        if (obj instanceof String) {
            zzlw.zza(i, (String) obj);
        } else {
            zzlw.zza(i, (zzgv) obj);
        }
    }

    private final void zza(Object obj, int i, zzke zzke) throws IOException {
        if (zzf(i)) {
            zzlf.zza(obj, (long) (i & 1048575), (Object) zzke.zzm());
        } else if (this.zzi) {
            zzlf.zza(obj, (long) (i & 1048575), (Object) zzke.zzl());
        } else {
            zzlf.zza(obj, (long) (i & 1048575), (Object) zzke.zzn());
        }
    }

    private final int zzd(int i) {
        return this.zzc[i + 1];
    }

    private final int zze(int i) {
        return this.zzc[i + 2];
    }

    private static boolean zzf(int i) {
        return (i & 536870912) != 0;
    }

    private static <T> double zzb(T t, long j) {
        return ((Double) zzlf.zzf(t, j)).doubleValue();
    }

    private static <T> float zzc(T t, long j) {
        return ((Float) zzlf.zzf(t, j)).floatValue();
    }

    private static <T> int zzd(T t, long j) {
        return ((Integer) zzlf.zzf(t, j)).intValue();
    }

    private static <T> long zze(T t, long j) {
        return ((Long) zzlf.zzf(t, j)).longValue();
    }

    private static <T> boolean zzf(T t, long j) {
        return ((Boolean) zzlf.zzf(t, j)).booleanValue();
    }

    private final boolean zzc(T t, T t2, int i) {
        return zza(t, i) == zza(t2, i);
    }

    private final boolean zza(T t, int i, int i2, int i3, int i4) {
        if (i2 == 1048575) {
            return zza(t, i);
        }
        return (i3 & i4) != 0;
    }

    private final boolean zza(T t, int i) {
        int zze2 = zze(i);
        long j = (long) (zze2 & 1048575);
        if (j == 1048575) {
            int zzd2 = zzd(i);
            long j2 = (long) (zzd2 & 1048575);
            switch ((zzd2 & 267386880) >>> 20) {
                case 0:
                    return zzlf.zze(t, j2) != 0.0d;
                case 1:
                    return zzlf.zzd(t, j2) != 0.0f;
                case 2:
                    return zzlf.zzb(t, j2) != 0;
                case 3:
                    return zzlf.zzb(t, j2) != 0;
                case 4:
                    return zzlf.zza((Object) t, j2) != 0;
                case 5:
                    return zzlf.zzb(t, j2) != 0;
                case 6:
                    return zzlf.zza((Object) t, j2) != 0;
                case 7:
                    return zzlf.zzc(t, j2);
                case 8:
                    Object zzf2 = zzlf.zzf(t, j2);
                    if (zzf2 instanceof String) {
                        return !((String) zzf2).isEmpty();
                    }
                    if (zzf2 instanceof zzgv) {
                        return !zzgv.zza.equals(zzf2);
                    }
                    throw new IllegalArgumentException();
                case 9:
                    return zzlf.zzf(t, j2) != null;
                case 10:
                    return !zzgv.zza.equals(zzlf.zzf(t, j2));
                case 11:
                    return zzlf.zza((Object) t, j2) != 0;
                case 12:
                    return zzlf.zza((Object) t, j2) != 0;
                case 13:
                    return zzlf.zza((Object) t, j2) != 0;
                case 14:
                    return zzlf.zzb(t, j2) != 0;
                case 15:
                    return zzlf.zza((Object) t, j2) != 0;
                case 16:
                    return zzlf.zzb(t, j2) != 0;
                case 17:
                    return zzlf.zzf(t, j2) != null;
                default:
                    throw new IllegalArgumentException();
            }
        } else {
            return (zzlf.zza((Object) t, j) & (1 << (zze2 >>> 20))) != 0;
        }
    }

    private final void zzb(T t, int i) {
        int zze2 = zze(i);
        long j = (long) (1048575 & zze2);
        if (j != 1048575) {
            zzlf.zza((Object) t, j, (1 << (zze2 >>> 20)) | zzlf.zza((Object) t, j));
        }
    }

    private final boolean zza(T t, int i, int i2) {
        return zzlf.zza((Object) t, (long) (zze(i2) & 1048575)) == i;
    }

    private final void zzb(T t, int i, int i2) {
        zzlf.zza((Object) t, (long) (zze(i2) & 1048575), i);
    }
}
