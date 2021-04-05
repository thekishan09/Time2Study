package com.google.firebase.auth.api.internal;

import com.google.android.gms.internal.firebase_auth.zza;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public abstract class zzeq extends zza implements zzer {
    public zzeq() {
        super("com.google.firebase.auth.api.internal.IFirebaseAuthService");
    }

    /* JADX WARNING: type inference failed for: r5v16, types: [android.os.IInterface] */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean zza(int r4, android.os.Parcel r5, android.os.Parcel r6, int r7) throws android.os.RemoteException {
        /*
            r3 = this;
            r7 = 0
            java.lang.String r0 = "com.google.firebase.auth.api.internal.IFirebaseAuthCallbacks"
            switch(r4) {
                case 1: goto L_0x08aa;
                case 2: goto L_0x088a;
                case 3: goto L_0x0866;
                case 4: goto L_0x083e;
                case 5: goto L_0x0819;
                case 6: goto L_0x07f4;
                case 7: goto L_0x07cf;
                case 8: goto L_0x07aa;
                case 9: goto L_0x0789;
                case 10: goto L_0x0768;
                case 11: goto L_0x073f;
                case 12: goto L_0x0716;
                case 13: goto L_0x06f5;
                case 14: goto L_0x06d0;
                case 15: goto L_0x06af;
                case 16: goto L_0x0691;
                case 17: goto L_0x0670;
                case 18: goto L_0x064f;
                case 19: goto L_0x062e;
                case 20: goto L_0x060d;
                case 21: goto L_0x05e8;
                case 22: goto L_0x05c3;
                case 23: goto L_0x059e;
                case 24: goto L_0x0575;
                case 25: goto L_0x054c;
                case 26: goto L_0x0523;
                case 27: goto L_0x0502;
                case 28: goto L_0x04d9;
                case 29: goto L_0x04b4;
                default: goto L_0x0006;
            }
        L_0x0006:
            switch(r4) {
                case 101: goto L_0x048f;
                case 102: goto L_0x046a;
                case 103: goto L_0x0445;
                case 104: goto L_0x0420;
                case 105: goto L_0x03fb;
                case 106: goto L_0x03d6;
                case 107: goto L_0x03b1;
                case 108: goto L_0x038c;
                case 109: goto L_0x0367;
                default: goto L_0x0009;
            }
        L_0x0009:
            switch(r4) {
                case 111: goto L_0x0342;
                case 112: goto L_0x031d;
                case 113: goto L_0x02f8;
                case 114: goto L_0x02d3;
                case 115: goto L_0x02ae;
                case 116: goto L_0x0289;
                case 117: goto L_0x0264;
                default: goto L_0x000c;
            }
        L_0x000c:
            switch(r4) {
                case 119: goto L_0x023f;
                case 120: goto L_0x021a;
                case 121: goto L_0x01f5;
                case 122: goto L_0x01d0;
                case 123: goto L_0x01ab;
                case 124: goto L_0x0186;
                default: goto L_0x000f;
            }
        L_0x000f:
            switch(r4) {
                case 126: goto L_0x0161;
                case 127: goto L_0x013c;
                case 128: goto L_0x0117;
                case 129: goto L_0x00f2;
                case 130: goto L_0x00cd;
                case 131: goto L_0x00a8;
                case 132: goto L_0x0083;
                case 133: goto L_0x005e;
                case 134: goto L_0x0039;
                case 135: goto L_0x0014;
                default: goto L_0x0012;
            }
        L_0x0012:
            r4 = 0
            return r4
        L_0x0014:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzei> r4 = com.google.android.gms.internal.firebase_auth.zzei.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.android.gms.internal.firebase_auth.zzei r4 = (com.google.android.gms.internal.firebase_auth.zzei) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x0023
            goto L_0x0033
        L_0x0023:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x002e
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x0033
        L_0x002e:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x0033:
            r3.zza((com.google.android.gms.internal.firebase_auth.zzei) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x0039:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzco> r4 = com.google.android.gms.internal.firebase_auth.zzco.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.android.gms.internal.firebase_auth.zzco r4 = (com.google.android.gms.internal.firebase_auth.zzco) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x0048
            goto L_0x0058
        L_0x0048:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x0053
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x0058
        L_0x0053:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x0058:
            r3.zza((com.google.android.gms.internal.firebase_auth.zzco) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x005e:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzdy> r4 = com.google.android.gms.internal.firebase_auth.zzdy.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.android.gms.internal.firebase_auth.zzdy r4 = (com.google.android.gms.internal.firebase_auth.zzdy) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x006d
            goto L_0x007d
        L_0x006d:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x0078
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x007d
        L_0x0078:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x007d:
            r3.zza((com.google.android.gms.internal.firebase_auth.zzdy) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x0083:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzcm> r4 = com.google.android.gms.internal.firebase_auth.zzcm.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.android.gms.internal.firebase_auth.zzcm r4 = (com.google.android.gms.internal.firebase_auth.zzcm) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x0092
            goto L_0x00a2
        L_0x0092:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x009d
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x00a2
        L_0x009d:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x00a2:
            r3.zza((com.google.android.gms.internal.firebase_auth.zzcm) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x00a8:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzea> r4 = com.google.android.gms.internal.firebase_auth.zzea.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.android.gms.internal.firebase_auth.zzea r4 = (com.google.android.gms.internal.firebase_auth.zzea) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x00b7
            goto L_0x00c7
        L_0x00b7:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x00c2
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x00c7
        L_0x00c2:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x00c7:
            r3.zza((com.google.android.gms.internal.firebase_auth.zzea) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x00cd:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzdw> r4 = com.google.android.gms.internal.firebase_auth.zzdw.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.android.gms.internal.firebase_auth.zzdw r4 = (com.google.android.gms.internal.firebase_auth.zzdw) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x00dc
            goto L_0x00ec
        L_0x00dc:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x00e7
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x00ec
        L_0x00e7:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x00ec:
            r3.zza((com.google.android.gms.internal.firebase_auth.zzdw) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x00f2:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzds> r4 = com.google.android.gms.internal.firebase_auth.zzds.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.android.gms.internal.firebase_auth.zzds r4 = (com.google.android.gms.internal.firebase_auth.zzds) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x0101
            goto L_0x0111
        L_0x0101:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x010c
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x0111
        L_0x010c:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x0111:
            r3.zza((com.google.android.gms.internal.firebase_auth.zzds) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x0117:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzde> r4 = com.google.android.gms.internal.firebase_auth.zzde.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.android.gms.internal.firebase_auth.zzde r4 = (com.google.android.gms.internal.firebase_auth.zzde) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x0126
            goto L_0x0136
        L_0x0126:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x0131
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x0136
        L_0x0131:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x0136:
            r3.zza((com.google.android.gms.internal.firebase_auth.zzde) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x013c:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzdi> r4 = com.google.android.gms.internal.firebase_auth.zzdi.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.android.gms.internal.firebase_auth.zzdi r4 = (com.google.android.gms.internal.firebase_auth.zzdi) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x014b
            goto L_0x015b
        L_0x014b:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x0156
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x015b
        L_0x0156:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x015b:
            r3.zza((com.google.android.gms.internal.firebase_auth.zzdi) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x0161:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzdc> r4 = com.google.android.gms.internal.firebase_auth.zzdc.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.android.gms.internal.firebase_auth.zzdc r4 = (com.google.android.gms.internal.firebase_auth.zzdc) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x0170
            goto L_0x0180
        L_0x0170:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x017b
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x0180
        L_0x017b:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x0180:
            r3.zza((com.google.android.gms.internal.firebase_auth.zzdc) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x0186:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzcy> r4 = com.google.android.gms.internal.firebase_auth.zzcy.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.android.gms.internal.firebase_auth.zzcy r4 = (com.google.android.gms.internal.firebase_auth.zzcy) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x0195
            goto L_0x01a5
        L_0x0195:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x01a0
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x01a5
        L_0x01a0:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x01a5:
            r3.zza((com.google.android.gms.internal.firebase_auth.zzcy) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x01ab:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzdu> r4 = com.google.android.gms.internal.firebase_auth.zzdu.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.android.gms.internal.firebase_auth.zzdu r4 = (com.google.android.gms.internal.firebase_auth.zzdu) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x01ba
            goto L_0x01ca
        L_0x01ba:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x01c5
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x01ca
        L_0x01c5:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x01ca:
            r3.zza((com.google.android.gms.internal.firebase_auth.zzdu) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x01d0:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzdg> r4 = com.google.android.gms.internal.firebase_auth.zzdg.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.android.gms.internal.firebase_auth.zzdg r4 = (com.google.android.gms.internal.firebase_auth.zzdg) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x01df
            goto L_0x01ef
        L_0x01df:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x01ea
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x01ef
        L_0x01ea:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x01ef:
            r3.zza((com.google.android.gms.internal.firebase_auth.zzdg) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x01f5:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzcg> r4 = com.google.android.gms.internal.firebase_auth.zzcg.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.android.gms.internal.firebase_auth.zzcg r4 = (com.google.android.gms.internal.firebase_auth.zzcg) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x0204
            goto L_0x0214
        L_0x0204:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x020f
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x0214
        L_0x020f:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x0214:
            r3.zza((com.google.android.gms.internal.firebase_auth.zzcg) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x021a:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzby> r4 = com.google.android.gms.internal.firebase_auth.zzby.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.android.gms.internal.firebase_auth.zzby r4 = (com.google.android.gms.internal.firebase_auth.zzby) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x0229
            goto L_0x0239
        L_0x0229:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x0234
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x0239
        L_0x0234:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x0239:
            r3.zza((com.google.android.gms.internal.firebase_auth.zzby) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x023f:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzce> r4 = com.google.android.gms.internal.firebase_auth.zzce.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.android.gms.internal.firebase_auth.zzce r4 = (com.google.android.gms.internal.firebase_auth.zzce) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x024e
            goto L_0x025e
        L_0x024e:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x0259
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x025e
        L_0x0259:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x025e:
            r3.zza((com.google.android.gms.internal.firebase_auth.zzce) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x0264:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzck> r4 = com.google.android.gms.internal.firebase_auth.zzck.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.android.gms.internal.firebase_auth.zzck r4 = (com.google.android.gms.internal.firebase_auth.zzck) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x0273
            goto L_0x0283
        L_0x0273:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x027e
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x0283
        L_0x027e:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x0283:
            r3.zza((com.google.android.gms.internal.firebase_auth.zzck) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x0289:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzdk> r4 = com.google.android.gms.internal.firebase_auth.zzdk.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.android.gms.internal.firebase_auth.zzdk r4 = (com.google.android.gms.internal.firebase_auth.zzdk) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x0298
            goto L_0x02a8
        L_0x0298:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x02a3
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x02a8
        L_0x02a3:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x02a8:
            r3.zza((com.google.android.gms.internal.firebase_auth.zzdk) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x02ae:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzda> r4 = com.google.android.gms.internal.firebase_auth.zzda.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.android.gms.internal.firebase_auth.zzda r4 = (com.google.android.gms.internal.firebase_auth.zzda) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x02bd
            goto L_0x02cd
        L_0x02bd:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x02c8
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x02cd
        L_0x02c8:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x02cd:
            r3.zza((com.google.android.gms.internal.firebase_auth.zzda) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x02d3:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzee> r4 = com.google.android.gms.internal.firebase_auth.zzee.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.android.gms.internal.firebase_auth.zzee r4 = (com.google.android.gms.internal.firebase_auth.zzee) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x02e2
            goto L_0x02f2
        L_0x02e2:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x02ed
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x02f2
        L_0x02ed:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x02f2:
            r3.zza((com.google.android.gms.internal.firebase_auth.zzee) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x02f8:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzec> r4 = com.google.android.gms.internal.firebase_auth.zzec.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.android.gms.internal.firebase_auth.zzec r4 = (com.google.android.gms.internal.firebase_auth.zzec) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x0307
            goto L_0x0317
        L_0x0307:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x0312
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x0317
        L_0x0312:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x0317:
            r3.zza((com.google.android.gms.internal.firebase_auth.zzec) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x031d:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzcw> r4 = com.google.android.gms.internal.firebase_auth.zzcw.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.android.gms.internal.firebase_auth.zzcw r4 = (com.google.android.gms.internal.firebase_auth.zzcw) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x032c
            goto L_0x033c
        L_0x032c:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x0337
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x033c
        L_0x0337:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x033c:
            r3.zza((com.google.android.gms.internal.firebase_auth.zzcw) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x0342:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzcu> r4 = com.google.android.gms.internal.firebase_auth.zzcu.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.android.gms.internal.firebase_auth.zzcu r4 = (com.google.android.gms.internal.firebase_auth.zzcu) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x0351
            goto L_0x0361
        L_0x0351:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x035c
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x0361
        L_0x035c:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x0361:
            r3.zza((com.google.android.gms.internal.firebase_auth.zzcu) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x0367:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzcs> r4 = com.google.android.gms.internal.firebase_auth.zzcs.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.android.gms.internal.firebase_auth.zzcs r4 = (com.google.android.gms.internal.firebase_auth.zzcs) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x0376
            goto L_0x0386
        L_0x0376:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x0381
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x0386
        L_0x0381:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x0386:
            r3.zza((com.google.android.gms.internal.firebase_auth.zzcs) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x038c:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzdq> r4 = com.google.android.gms.internal.firebase_auth.zzdq.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.android.gms.internal.firebase_auth.zzdq r4 = (com.google.android.gms.internal.firebase_auth.zzdq) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x039b
            goto L_0x03ab
        L_0x039b:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x03a6
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x03ab
        L_0x03a6:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x03ab:
            r3.zza((com.google.android.gms.internal.firebase_auth.zzdq) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x03b1:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzci> r4 = com.google.android.gms.internal.firebase_auth.zzci.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.android.gms.internal.firebase_auth.zzci r4 = (com.google.android.gms.internal.firebase_auth.zzci) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x03c0
            goto L_0x03d0
        L_0x03c0:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x03cb
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x03d0
        L_0x03cb:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x03d0:
            r3.zza((com.google.android.gms.internal.firebase_auth.zzci) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x03d6:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzcc> r4 = com.google.android.gms.internal.firebase_auth.zzcc.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.android.gms.internal.firebase_auth.zzcc r4 = (com.google.android.gms.internal.firebase_auth.zzcc) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x03e5
            goto L_0x03f5
        L_0x03e5:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x03f0
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x03f5
        L_0x03f0:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x03f5:
            r3.zza((com.google.android.gms.internal.firebase_auth.zzcc) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x03fb:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzca> r4 = com.google.android.gms.internal.firebase_auth.zzca.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.android.gms.internal.firebase_auth.zzca r4 = (com.google.android.gms.internal.firebase_auth.zzca) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x040a
            goto L_0x041a
        L_0x040a:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x0415
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x041a
        L_0x0415:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x041a:
            r3.zza((com.google.android.gms.internal.firebase_auth.zzca) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x0420:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzeg> r4 = com.google.android.gms.internal.firebase_auth.zzeg.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.android.gms.internal.firebase_auth.zzeg r4 = (com.google.android.gms.internal.firebase_auth.zzeg) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x042f
            goto L_0x043f
        L_0x042f:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x043a
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x043f
        L_0x043a:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x043f:
            r3.zza((com.google.android.gms.internal.firebase_auth.zzeg) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x0445:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzdm> r4 = com.google.android.gms.internal.firebase_auth.zzdm.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.android.gms.internal.firebase_auth.zzdm r4 = (com.google.android.gms.internal.firebase_auth.zzdm) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x0454
            goto L_0x0464
        L_0x0454:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x045f
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x0464
        L_0x045f:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x0464:
            r3.zza((com.google.android.gms.internal.firebase_auth.zzdm) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x046a:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzdo> r4 = com.google.android.gms.internal.firebase_auth.zzdo.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.android.gms.internal.firebase_auth.zzdo r4 = (com.google.android.gms.internal.firebase_auth.zzdo) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x0479
            goto L_0x0489
        L_0x0479:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x0484
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x0489
        L_0x0484:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x0489:
            r3.zza((com.google.android.gms.internal.firebase_auth.zzdo) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x048f:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzcq> r4 = com.google.android.gms.internal.firebase_auth.zzcq.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.android.gms.internal.firebase_auth.zzcq r4 = (com.google.android.gms.internal.firebase_auth.zzcq) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x049e
            goto L_0x04ae
        L_0x049e:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x04a9
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x04ae
        L_0x04a9:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x04ae:
            r3.zza((com.google.android.gms.internal.firebase_auth.zzcq) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x04b4:
            android.os.Parcelable$Creator<com.google.firebase.auth.EmailAuthCredential> r4 = com.google.firebase.auth.EmailAuthCredential.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.firebase.auth.EmailAuthCredential r4 = (com.google.firebase.auth.EmailAuthCredential) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x04c3
            goto L_0x04d3
        L_0x04c3:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x04ce
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x04d3
        L_0x04ce:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x04d3:
            r3.zza((com.google.firebase.auth.EmailAuthCredential) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x04d9:
            java.lang.String r4 = r5.readString()
            android.os.Parcelable$Creator<com.google.firebase.auth.ActionCodeSettings> r1 = com.google.firebase.auth.ActionCodeSettings.CREATOR
            android.os.Parcelable r1 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r1)
            com.google.firebase.auth.ActionCodeSettings r1 = (com.google.firebase.auth.ActionCodeSettings) r1
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x04ec
            goto L_0x04fc
        L_0x04ec:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x04f7
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x04fc
        L_0x04f7:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x04fc:
            r3.zzc((java.lang.String) r4, (com.google.firebase.auth.ActionCodeSettings) r1, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x0502:
            java.lang.String r4 = r5.readString()
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x050d
            goto L_0x051d
        L_0x050d:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x0518
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x051d
        L_0x0518:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x051d:
            r3.zzk(r4, r7)
            goto L_0x08ca
        L_0x0523:
            java.lang.String r4 = r5.readString()
            android.os.Parcelable$Creator<com.google.firebase.auth.ActionCodeSettings> r1 = com.google.firebase.auth.ActionCodeSettings.CREATOR
            android.os.Parcelable r1 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r1)
            com.google.firebase.auth.ActionCodeSettings r1 = (com.google.firebase.auth.ActionCodeSettings) r1
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x0536
            goto L_0x0546
        L_0x0536:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x0541
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x0546
        L_0x0541:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x0546:
            r3.zzb((java.lang.String) r4, (com.google.firebase.auth.ActionCodeSettings) r1, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x054c:
            java.lang.String r4 = r5.readString()
            android.os.Parcelable$Creator<com.google.firebase.auth.ActionCodeSettings> r1 = com.google.firebase.auth.ActionCodeSettings.CREATOR
            android.os.Parcelable r1 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r1)
            com.google.firebase.auth.ActionCodeSettings r1 = (com.google.firebase.auth.ActionCodeSettings) r1
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x055f
            goto L_0x056f
        L_0x055f:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x056a
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x056f
        L_0x056a:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x056f:
            r3.zza((java.lang.String) r4, (com.google.firebase.auth.ActionCodeSettings) r1, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x0575:
            java.lang.String r4 = r5.readString()
            android.os.Parcelable$Creator<com.google.firebase.auth.PhoneAuthCredential> r1 = com.google.firebase.auth.PhoneAuthCredential.CREATOR
            android.os.Parcelable r1 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r1)
            com.google.firebase.auth.PhoneAuthCredential r1 = (com.google.firebase.auth.PhoneAuthCredential) r1
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x0588
            goto L_0x0598
        L_0x0588:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x0593
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x0598
        L_0x0593:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x0598:
            r3.zza((java.lang.String) r4, (com.google.firebase.auth.PhoneAuthCredential) r1, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x059e:
            android.os.Parcelable$Creator<com.google.firebase.auth.PhoneAuthCredential> r4 = com.google.firebase.auth.PhoneAuthCredential.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.firebase.auth.PhoneAuthCredential r4 = (com.google.firebase.auth.PhoneAuthCredential) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x05ad
            goto L_0x05bd
        L_0x05ad:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x05b8
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x05bd
        L_0x05b8:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x05bd:
            r3.zza((com.google.firebase.auth.PhoneAuthCredential) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x05c3:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzfr> r4 = com.google.android.gms.internal.firebase_auth.zzfr.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.android.gms.internal.firebase_auth.zzfr r4 = (com.google.android.gms.internal.firebase_auth.zzfr) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x05d2
            goto L_0x05e2
        L_0x05d2:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x05dd
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x05e2
        L_0x05dd:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x05e2:
            r3.zza((com.google.android.gms.internal.firebase_auth.zzfr) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x05e8:
            java.lang.String r4 = r5.readString()
            java.lang.String r1 = r5.readString()
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x05f7
            goto L_0x0607
        L_0x05f7:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x0602
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x0607
        L_0x0602:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x0607:
            r3.zzf(r4, r1, r7)
            goto L_0x08ca
        L_0x060d:
            java.lang.String r4 = r5.readString()
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x0618
            goto L_0x0628
        L_0x0618:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x0623
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x0628
        L_0x0623:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x0628:
            r3.zzj(r4, r7)
            goto L_0x08ca
        L_0x062e:
            java.lang.String r4 = r5.readString()
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x0639
            goto L_0x0649
        L_0x0639:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x0644
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x0649
        L_0x0644:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x0649:
            r3.zzi(r4, r7)
            goto L_0x08ca
        L_0x064f:
            java.lang.String r4 = r5.readString()
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x065a
            goto L_0x066a
        L_0x065a:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x0665
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x066a
        L_0x0665:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x066a:
            r3.zzh(r4, r7)
            goto L_0x08ca
        L_0x0670:
            java.lang.String r4 = r5.readString()
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x067b
            goto L_0x068b
        L_0x067b:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x0686
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x068b
        L_0x0686:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x068b:
            r3.zzg(r4, r7)
            goto L_0x08ca
        L_0x0691:
            android.os.IBinder r4 = r5.readStrongBinder()
            if (r4 != 0) goto L_0x0698
            goto L_0x06a9
        L_0x0698:
            android.os.IInterface r5 = r4.queryLocalInterface(r0)
            boolean r7 = r5 instanceof com.google.firebase.auth.api.internal.zzem
            if (r7 == 0) goto L_0x06a4
            r7 = r5
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x06a9
        L_0x06a4:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r4)
        L_0x06a9:
            r3.zza(r7)
            goto L_0x08ca
        L_0x06af:
            java.lang.String r4 = r5.readString()
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x06ba
            goto L_0x06ca
        L_0x06ba:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x06c5
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x06ca
        L_0x06c5:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x06ca:
            r3.zzf(r4, r7)
            goto L_0x08ca
        L_0x06d0:
            java.lang.String r4 = r5.readString()
            java.lang.String r1 = r5.readString()
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x06df
            goto L_0x06ef
        L_0x06df:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x06ea
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x06ef
        L_0x06ea:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x06ef:
            r3.zze(r4, r1, r7)
            goto L_0x08ca
        L_0x06f5:
            java.lang.String r4 = r5.readString()
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x0700
            goto L_0x0710
        L_0x0700:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x070b
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x0710
        L_0x070b:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x0710:
            r3.zze(r4, r7)
            goto L_0x08ca
        L_0x0716:
            java.lang.String r4 = r5.readString()
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzgc> r1 = com.google.android.gms.internal.firebase_auth.zzgc.CREATOR
            android.os.Parcelable r1 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r1)
            com.google.android.gms.internal.firebase_auth.zzgc r1 = (com.google.android.gms.internal.firebase_auth.zzgc) r1
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x0729
            goto L_0x0739
        L_0x0729:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x0734
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x0739
        L_0x0734:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x0739:
            r3.zza((java.lang.String) r4, (com.google.android.gms.internal.firebase_auth.zzgc) r1, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x073f:
            java.lang.String r4 = r5.readString()
            java.lang.String r1 = r5.readString()
            java.lang.String r2 = r5.readString()
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x0752
            goto L_0x0762
        L_0x0752:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x075d
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x0762
        L_0x075d:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x0762:
            r3.zza(r4, r1, r2, r7)
            goto L_0x08ca
        L_0x0768:
            java.lang.String r4 = r5.readString()
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x0773
            goto L_0x0783
        L_0x0773:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x077e
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x0783
        L_0x077e:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x0783:
            r3.zzd(r4, r7)
            goto L_0x08ca
        L_0x0789:
            java.lang.String r4 = r5.readString()
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x0794
            goto L_0x07a4
        L_0x0794:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x079f
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x07a4
        L_0x079f:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x07a4:
            r3.zzc(r4, r7)
            goto L_0x08ca
        L_0x07aa:
            java.lang.String r4 = r5.readString()
            java.lang.String r1 = r5.readString()
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x07b9
            goto L_0x07c9
        L_0x07b9:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x07c4
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x07c9
        L_0x07c4:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x07c9:
            r3.zzd(r4, r1, r7)
            goto L_0x08ca
        L_0x07cf:
            java.lang.String r4 = r5.readString()
            java.lang.String r1 = r5.readString()
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x07de
            goto L_0x07ee
        L_0x07de:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x07e9
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x07ee
        L_0x07e9:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x07ee:
            r3.zzc((java.lang.String) r4, (java.lang.String) r1, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x07f4:
            java.lang.String r4 = r5.readString()
            java.lang.String r1 = r5.readString()
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x0803
            goto L_0x0813
        L_0x0803:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x080e
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x0813
        L_0x080e:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x0813:
            r3.zzb((java.lang.String) r4, (java.lang.String) r1, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x0819:
            java.lang.String r4 = r5.readString()
            java.lang.String r1 = r5.readString()
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x0828
            goto L_0x0838
        L_0x0828:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x0833
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x0838
        L_0x0833:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x0838:
            r3.zza((java.lang.String) r4, (java.lang.String) r1, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x083e:
            java.lang.String r4 = r5.readString()
            android.os.Parcelable$Creator<com.google.firebase.auth.UserProfileChangeRequest> r1 = com.google.firebase.auth.UserProfileChangeRequest.CREATOR
            android.os.Parcelable r1 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r1)
            com.google.firebase.auth.UserProfileChangeRequest r1 = (com.google.firebase.auth.UserProfileChangeRequest) r1
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x0851
            goto L_0x0861
        L_0x0851:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x085c
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x0861
        L_0x085c:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x0861:
            r3.zza((java.lang.String) r4, (com.google.firebase.auth.UserProfileChangeRequest) r1, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x0866:
            android.os.Parcelable$Creator<com.google.android.gms.internal.firebase_auth.zzgc> r4 = com.google.android.gms.internal.firebase_auth.zzgc.CREATOR
            android.os.Parcelable r4 = com.google.android.gms.internal.firebase_auth.zzd.zza((android.os.Parcel) r5, r4)
            com.google.android.gms.internal.firebase_auth.zzgc r4 = (com.google.android.gms.internal.firebase_auth.zzgc) r4
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x0875
            goto L_0x0885
        L_0x0875:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x0880
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x0885
        L_0x0880:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x0885:
            r3.zza((com.google.android.gms.internal.firebase_auth.zzgc) r4, (com.google.firebase.auth.api.internal.zzem) r7)
            goto L_0x08ca
        L_0x088a:
            java.lang.String r4 = r5.readString()
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x0895
            goto L_0x08a5
        L_0x0895:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x08a0
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x08a5
        L_0x08a0:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x08a5:
            r3.zzb(r4, r7)
            goto L_0x08ca
        L_0x08aa:
            java.lang.String r4 = r5.readString()
            android.os.IBinder r5 = r5.readStrongBinder()
            if (r5 != 0) goto L_0x08b5
            goto L_0x08c5
        L_0x08b5:
            android.os.IInterface r7 = r5.queryLocalInterface(r0)
            boolean r0 = r7 instanceof com.google.firebase.auth.api.internal.zzem
            if (r0 == 0) goto L_0x08c0
            com.google.firebase.auth.api.internal.zzem r7 = (com.google.firebase.auth.api.internal.zzem) r7
            goto L_0x08c5
        L_0x08c0:
            com.google.firebase.auth.api.internal.zzeo r7 = new com.google.firebase.auth.api.internal.zzeo
            r7.<init>(r5)
        L_0x08c5:
            r3.zza((java.lang.String) r4, (com.google.firebase.auth.api.internal.zzem) r7)
        L_0x08ca:
            r6.writeNoException()
            r4 = 1
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.auth.api.internal.zzeq.zza(int, android.os.Parcel, android.os.Parcel, int):boolean");
    }
}
