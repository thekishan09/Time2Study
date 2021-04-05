package com.google.firebase.auth.internal;

import android.os.Parcelable;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzx implements Parcelable.Creator<zzv> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new zzv[i];
    }

    /* JADX WARNING: type inference failed for: r1v3, types: [android.os.Parcelable] */
    /* JADX WARNING: type inference failed for: r1v4, types: [android.os.Parcelable] */
    /* JADX WARNING: type inference failed for: r1v5, types: [android.os.Parcelable] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object createFromParcel(android.os.Parcel r10) {
        /*
            r9 = this;
            int r0 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.validateObjectHeader(r10)
            r1 = 0
            r3 = r1
            r4 = r3
            r5 = r4
            r6 = r5
            r7 = r6
        L_0x000f:
            int r1 = r10.dataPosition()
            if (r1 >= r0) goto L_0x005b
            int r1 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.readHeader(r10)
            int r2 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.getFieldId(r1)
            r8 = 1
            if (r2 == r8) goto L_0x0054
            r8 = 2
            if (r2 == r8) goto L_0x004a
            r8 = 3
            if (r2 == r8) goto L_0x0044
            r8 = 4
            if (r2 == r8) goto L_0x003a
            r8 = 5
            if (r2 == r8) goto L_0x0030
            com.google.android.gms.common.internal.safeparcel.SafeParcelReader.skipUnknownField(r10, r1)
            goto L_0x000f
        L_0x0030:
            android.os.Parcelable$Creator<com.google.firebase.auth.internal.zzp> r2 = com.google.firebase.auth.internal.zzp.CREATOR
            android.os.Parcelable r1 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createParcelable(r10, r1, r2)
            r7 = r1
            com.google.firebase.auth.internal.zzp r7 = (com.google.firebase.auth.internal.zzp) r7
            goto L_0x000f
        L_0x003a:
            android.os.Parcelable$Creator<com.google.firebase.auth.zze> r2 = com.google.firebase.auth.zze.CREATOR
            android.os.Parcelable r1 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createParcelable(r10, r1, r2)
            r6 = r1
            com.google.firebase.auth.zze r6 = (com.google.firebase.auth.zze) r6
            goto L_0x000f
        L_0x0044:
            java.lang.String r5 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createString(r10, r1)
            goto L_0x000f
        L_0x004a:
            android.os.Parcelable$Creator<com.google.firebase.auth.internal.zzw> r2 = com.google.firebase.auth.internal.zzw.CREATOR
            android.os.Parcelable r1 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createParcelable(r10, r1, r2)
            r4 = r1
            com.google.firebase.auth.internal.zzw r4 = (com.google.firebase.auth.internal.zzw) r4
            goto L_0x000f
        L_0x0054:
            android.os.Parcelable$Creator<com.google.firebase.auth.PhoneMultiFactorInfo> r2 = com.google.firebase.auth.PhoneMultiFactorInfo.CREATOR
            java.util.ArrayList r3 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createTypedList(r10, r1, r2)
            goto L_0x000f
        L_0x005b:
            com.google.android.gms.common.internal.safeparcel.SafeParcelReader.ensureAtEnd(r10, r0)
            com.google.firebase.auth.internal.zzv r10 = new com.google.firebase.auth.internal.zzv
            r2 = r10
            r2.<init>(r3, r4, r5, r6, r7)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.auth.internal.zzx.createFromParcel(android.os.Parcel):java.lang.Object");
    }
}
