package com.google.firebase.auth;

import android.os.Parcelable;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzd implements Parcelable.Creator<zze> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new zze[i];
    }

    /* JADX WARNING: type inference failed for: r1v3, types: [android.os.Parcelable] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object createFromParcel(android.os.Parcel r11) {
        /*
            r10 = this;
            int r0 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.validateObjectHeader(r11)
            r1 = 0
            r3 = r1
            r4 = r3
            r5 = r4
            r6 = r5
            r7 = r6
            r8 = r7
            r9 = r8
        L_0x0013:
            int r1 = r11.dataPosition()
            if (r1 >= r0) goto L_0x0056
            int r1 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.readHeader(r11)
            int r2 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.getFieldId(r1)
            switch(r2) {
                case 1: goto L_0x0050;
                case 2: goto L_0x004a;
                case 3: goto L_0x0044;
                case 4: goto L_0x003a;
                case 5: goto L_0x0034;
                case 6: goto L_0x002e;
                case 7: goto L_0x0028;
                default: goto L_0x0024;
            }
        L_0x0024:
            com.google.android.gms.common.internal.safeparcel.SafeParcelReader.skipUnknownField(r11, r1)
            goto L_0x0013
        L_0x0028:
            java.lang.String r9 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createString(r11, r1)
            goto L_0x0013
        L_0x002e:
            java.lang.String r8 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createString(r11, r1)
            goto L_0x0013
        L_0x0034:
            java.lang.String r7 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createString(r11, r1)
            goto L_0x0013
        L_0x003a:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzgc> r2 = com.google.android.gms.internal.firebase_auth.zzgc.CREATOR
            android.os.Parcelable r1 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createParcelable(r11, r1, r2)
            r6 = r1
            com.google.android.gms.internal.firebase_auth.zzgc r6 = (com.google.android.gms.internal.firebase_auth.zzgc) r6
            goto L_0x0013
        L_0x0044:
            java.lang.String r5 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createString(r11, r1)
            goto L_0x0013
        L_0x004a:
            java.lang.String r4 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createString(r11, r1)
            goto L_0x0013
        L_0x0050:
            java.lang.String r3 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createString(r11, r1)
            goto L_0x0013
        L_0x0056:
            com.google.android.gms.common.internal.safeparcel.SafeParcelReader.ensureAtEnd(r11, r0)
            com.google.firebase.auth.zze r11 = new com.google.firebase.auth.zze
            r2 = r11
            r2.<init>(r3, r4, r5, r6, r7, r8, r9)
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.auth.zzd.createFromParcel(android.os.Parcel):java.lang.Object");
    }
}
