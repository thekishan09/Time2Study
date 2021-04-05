package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzp;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.api.internal.zzgb;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzfe implements zzgb<zzp.zzh> {
    private String zza;
    private String zzb;
    private String zzc;
    private String zzd;
    private ActionCodeSettings zze;
    private String zzf;

    public zzfe(zzgm zzgm) {
        this.zza = zza(zzgm);
    }

    private zzfe(zzgm zzgm, ActionCodeSettings actionCodeSettings, String str, String str2, String str3, String str4) {
        this.zza = zza((zzgm) Preconditions.checkNotNull(zzgm));
        this.zze = (ActionCodeSettings) Preconditions.checkNotNull(actionCodeSettings);
        this.zzb = null;
        this.zzc = str2;
        this.zzd = str3;
        this.zzf = null;
    }

    public static zzfe zza(ActionCodeSettings actionCodeSettings, String str, String str2) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        Preconditions.checkNotNull(actionCodeSettings);
        return new zzfe(zzgm.VERIFY_AND_CHANGE_EMAIL, actionCodeSettings, (String) null, str2, str, (String) null);
    }

    public final zzfe zza(String str) {
        this.zzb = Preconditions.checkNotEmpty(str);
        return this;
    }

    public final zzfe zzb(String str) {
        this.zzd = Preconditions.checkNotEmpty(str);
        return this;
    }

    public final zzfe zza(ActionCodeSettings actionCodeSettings) {
        this.zze = (ActionCodeSettings) Preconditions.checkNotNull(actionCodeSettings);
        return this;
    }

    public final zzfe zzc(String str) {
        this.zzf = str;
        return this;
    }

    public final ActionCodeSettings zzb() {
        return this.zze;
    }

    private static String zza(zzgm zzgm) {
        int i = zzfd.zza[zzgm.ordinal()];
        if (i == 1) {
            return "PASSWORD_RESET";
        }
        if (i == 2) {
            return "VERIFY_EMAIL";
        }
        if (i == 3) {
            return "EMAIL_SIGNIN";
        }
        if (i != 4) {
            return "REQUEST_TYPE_UNSET_ENUM_VALUE";
        }
        return "VERIFY_BEFORE_UPDATE_EMAIL";
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ com.google.android.gms.internal.firebase_auth.zzjr zza() {
        /*
            r6 = this;
            com.google.android.gms.internal.firebase_auth.zzp$zzh$zza r0 = com.google.android.gms.internal.firebase_auth.zzp.zzh.zza()
            java.lang.String r1 = r6.zza
            int r2 = r1.hashCode()
            r3 = 3
            r4 = 2
            r5 = 1
            switch(r2) {
                case -1452371317: goto L_0x0030;
                case -1341836234: goto L_0x0026;
                case -1288726400: goto L_0x001c;
                case 870738373: goto L_0x0012;
                default: goto L_0x0011;
            }
        L_0x0011:
            goto L_0x003a
        L_0x0012:
            java.lang.String r2 = "EMAIL_SIGNIN"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0011
            r1 = 2
            goto L_0x003b
        L_0x001c:
            java.lang.String r2 = "VERIFY_BEFORE_UPDATE_EMAIL"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0011
            r1 = 3
            goto L_0x003b
        L_0x0026:
            java.lang.String r2 = "VERIFY_EMAIL"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0011
            r1 = 1
            goto L_0x003b
        L_0x0030:
            java.lang.String r2 = "PASSWORD_RESET"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0011
            r1 = 0
            goto L_0x003b
        L_0x003a:
            r1 = -1
        L_0x003b:
            if (r1 == 0) goto L_0x004f
            if (r1 == r5) goto L_0x004c
            if (r1 == r4) goto L_0x0049
            if (r1 == r3) goto L_0x0046
            com.google.android.gms.internal.firebase_auth.zzgm r1 = com.google.android.gms.internal.firebase_auth.zzgm.OOB_REQ_TYPE_UNSPECIFIED
            goto L_0x0051
        L_0x0046:
            com.google.android.gms.internal.firebase_auth.zzgm r1 = com.google.android.gms.internal.firebase_auth.zzgm.VERIFY_AND_CHANGE_EMAIL
            goto L_0x0051
        L_0x0049:
            com.google.android.gms.internal.firebase_auth.zzgm r1 = com.google.android.gms.internal.firebase_auth.zzgm.EMAIL_SIGNIN
            goto L_0x0051
        L_0x004c:
            com.google.android.gms.internal.firebase_auth.zzgm r1 = com.google.android.gms.internal.firebase_auth.zzgm.VERIFY_EMAIL
            goto L_0x0051
        L_0x004f:
            com.google.android.gms.internal.firebase_auth.zzgm r1 = com.google.android.gms.internal.firebase_auth.zzgm.PASSWORD_RESET
        L_0x0051:
            com.google.android.gms.internal.firebase_auth.zzp$zzh$zza r0 = r0.zza((com.google.android.gms.internal.firebase_auth.zzgm) r1)
            java.lang.String r1 = r6.zzb
            if (r1 == 0) goto L_0x005c
            r0.zza((java.lang.String) r1)
        L_0x005c:
            java.lang.String r1 = r6.zzc
            if (r1 == 0) goto L_0x0063
            r0.zzb((java.lang.String) r1)
        L_0x0063:
            java.lang.String r1 = r6.zzd
            if (r1 == 0) goto L_0x006a
            r0.zzc(r1)
        L_0x006a:
            com.google.firebase.auth.ActionCodeSettings r1 = r6.zze
            if (r1 == 0) goto L_0x00e6
            boolean r1 = r1.getAndroidInstallApp()
            com.google.android.gms.internal.firebase_auth.zzp$zzh$zza r1 = r0.zza((boolean) r1)
            com.google.firebase.auth.ActionCodeSettings r2 = r6.zze
            boolean r2 = r2.canHandleCodeInApp()
            r1.zzb((boolean) r2)
            com.google.firebase.auth.ActionCodeSettings r1 = r6.zze
            java.lang.String r1 = r1.getUrl()
            if (r1 == 0) goto L_0x0091
            com.google.firebase.auth.ActionCodeSettings r1 = r6.zze
            java.lang.String r1 = r1.getUrl()
            r0.zzd(r1)
        L_0x0091:
            com.google.firebase.auth.ActionCodeSettings r1 = r6.zze
            java.lang.String r1 = r1.getIOSBundle()
            if (r1 == 0) goto L_0x00a2
            com.google.firebase.auth.ActionCodeSettings r1 = r6.zze
            java.lang.String r1 = r1.getIOSBundle()
            r0.zze(r1)
        L_0x00a2:
            com.google.firebase.auth.ActionCodeSettings r1 = r6.zze
            java.lang.String r1 = r1.zzb()
            if (r1 == 0) goto L_0x00b3
            com.google.firebase.auth.ActionCodeSettings r1 = r6.zze
            java.lang.String r1 = r1.zzb()
            r0.zzf(r1)
        L_0x00b3:
            com.google.firebase.auth.ActionCodeSettings r1 = r6.zze
            java.lang.String r1 = r1.getAndroidPackageName()
            if (r1 == 0) goto L_0x00c4
            com.google.firebase.auth.ActionCodeSettings r1 = r6.zze
            java.lang.String r1 = r1.getAndroidPackageName()
            r0.zzg(r1)
        L_0x00c4:
            com.google.firebase.auth.ActionCodeSettings r1 = r6.zze
            java.lang.String r1 = r1.getAndroidMinimumVersion()
            if (r1 == 0) goto L_0x00d5
            com.google.firebase.auth.ActionCodeSettings r1 = r6.zze
            java.lang.String r1 = r1.getAndroidMinimumVersion()
            r0.zzh(r1)
        L_0x00d5:
            com.google.firebase.auth.ActionCodeSettings r1 = r6.zze
            java.lang.String r1 = r1.zze()
            if (r1 == 0) goto L_0x00e6
            com.google.firebase.auth.ActionCodeSettings r1 = r6.zze
            java.lang.String r1 = r1.zze()
            r0.zzj(r1)
        L_0x00e6:
            java.lang.String r1 = r6.zzf
            if (r1 == 0) goto L_0x00ed
            r0.zzi(r1)
        L_0x00ed:
            com.google.android.gms.internal.firebase_auth.zzjr r0 = r0.zzf()
            com.google.android.gms.internal.firebase_auth.zzig r0 = (com.google.android.gms.internal.firebase_auth.zzig) r0
            com.google.android.gms.internal.firebase_auth.zzp$zzh r0 = (com.google.android.gms.internal.firebase_auth.zzp.zzh) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzfe.zza():com.google.android.gms.internal.firebase_auth.zzjr");
    }
}
