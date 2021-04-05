package com.google.android.gms.internal.firebase_auth;

import android.os.Parcelable;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzep implements Parcelable.Creator<zzeq> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new zzeq[i];
    }

    /* JADX WARNING: type inference failed for: r1v3, types: [android.os.Parcelable] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object createFromParcel(android.os.Parcel r11) {
        /*
            r10 = this;
            int r0 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.validateObjectHeader(r11)
            r1 = 0
            r2 = 0
            r4 = r2
            r6 = r4
            r8 = r6
            r9 = r8
            r5 = 0
            r7 = 0
        L_0x0012:
            int r1 = r11.dataPosition()
            if (r1 >= r0) goto L_0x004f
            int r1 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.readHeader(r11)
            int r2 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.getFieldId(r1)
            switch(r2) {
                case 2: goto L_0x0049;
                case 3: goto L_0x0043;
                case 4: goto L_0x003d;
                case 5: goto L_0x0037;
                case 6: goto L_0x002d;
                case 7: goto L_0x0027;
                default: goto L_0x0023;
            }
        L_0x0023:
            com.google.android.gms.common.internal.safeparcel.SafeParcelReader.skipUnknownField(r11, r1)
            goto L_0x0012
        L_0x0027:
            java.util.ArrayList r9 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createStringList(r11, r1)
            goto L_0x0012
        L_0x002d:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzga> r2 = com.google.android.gms.internal.firebase_auth.zzga.CREATOR
            android.os.Parcelable r1 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createParcelable(r11, r1, r2)
            r8 = r1
            com.google.android.gms.internal.firebase_auth.zzga r8 = (com.google.android.gms.internal.firebase_auth.zzga) r8
            goto L_0x0012
        L_0x0037:
            boolean r7 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.readBoolean(r11, r1)
            goto L_0x0012
        L_0x003d:
            java.lang.String r6 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createString(r11, r1)
            goto L_0x0012
        L_0x0043:
            boolean r5 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.readBoolean(r11, r1)
            goto L_0x0012
        L_0x0049:
            java.lang.String r4 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createString(r11, r1)
            goto L_0x0012
        L_0x004f:
            com.google.android.gms.common.internal.safeparcel.SafeParcelReader.ensureAtEnd(r11, r0)
            com.google.android.gms.internal.firebase_auth.zzeq r11 = new com.google.android.gms.internal.firebase_auth.zzeq
            r3 = r11
            r3.<init>(r4, r5, r6, r7, r8, r9)
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzep.createFromParcel(android.os.Parcel):java.lang.Object");
    }
}
