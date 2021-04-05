package com.google.firebase.auth.api.internal;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.logging.Logger;
import com.google.android.gms.internal.firebase_auth.zzek;
import com.google.android.gms.internal.firebase_auth.zzen;
import com.google.android.gms.internal.firebase_auth.zzeq;
import com.google.android.gms.internal.firebase_auth.zzer;
import com.google.android.gms.internal.firebase_auth.zzeu;
import com.google.android.gms.internal.firebase_auth.zzev;
import com.google.android.gms.internal.firebase_auth.zzew;
import com.google.android.gms.internal.firebase_auth.zzey;
import com.google.android.gms.internal.firebase_auth.zzfa;
import com.google.android.gms.internal.firebase_auth.zzfe;
import com.google.android.gms.internal.firebase_auth.zzff;
import com.google.android.gms.internal.firebase_auth.zzfg;
import com.google.android.gms.internal.firebase_auth.zzfn;
import com.google.android.gms.internal.firebase_auth.zzfq;
import com.google.android.gms.internal.firebase_auth.zzfr;
import com.google.android.gms.internal.firebase_auth.zzft;
import com.google.android.gms.internal.firebase_auth.zzfv;
import com.google.android.gms.internal.firebase_auth.zzfw;
import com.google.android.gms.internal.firebase_auth.zzfx;
import com.google.android.gms.internal.firebase_auth.zzfy;
import com.google.android.gms.internal.firebase_auth.zzgc;
import com.google.android.gms.internal.firebase_auth.zzgd;
import com.google.android.gms.internal.firebase_auth.zzge;
import com.google.android.gms.internal.firebase_auth.zzgg;
import com.google.android.gms.internal.firebase_auth.zzgh;
import com.google.android.gms.internal.firebase_auth.zzgi;
import com.google.android.gms.internal.firebase_auth.zzgj;
import com.google.android.gms.internal.firebase_auth.zzgk;
import com.google.android.gms.internal.firebase_auth.zzgm;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.auth.internal.zzy;
import com.google.firebase.auth.zze;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzb {
    /* access modifiers changed from: private */
    public static final Logger zza = new Logger("FBAuthApiDispatcher", new String[0]);
    /* access modifiers changed from: private */
    public final zzfp zzb;
    /* access modifiers changed from: private */
    public final zzat zzc;

    public zzb(zzfp zzfp, zzat zzat) {
        this.zzb = (zzfp) Preconditions.checkNotNull(zzfp);
        this.zzc = (zzat) Preconditions.checkNotNull(zzat);
    }

    public final void zza(String str, zzee zzee) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzee);
        this.zzb.zza(new zzew(str), (zzfr<zzff>) new zza(this, zzee));
    }

    public final void zza(zzgd zzgd, zzee zzee) {
        Preconditions.checkNotNull(zzgd);
        Preconditions.checkNotNull(zzee);
        this.zzb.zza(zzgd, (zzfr<zzgg>) new zzn(this, zzee));
    }

    public final void zza(Context context, zzgc zzgc, zzee zzee) {
        Preconditions.checkNotNull(zzgc);
        Preconditions.checkNotNull(zzee);
        if (this.zzc.zza()) {
            zzgc.zzc(true);
        }
        this.zzb.zza((Context) null, zzgc, (zzfr<zzge>) new zzz(this, zzee));
    }

    public final void zzb(String str, zzee zzee) {
        Preconditions.checkNotNull(zzee);
        this.zzb.zza(new zzfy(str), (zzfr<zzfx>) new zzae(this, zzee));
    }

    public final void zza(String str, UserProfileChangeRequest userProfileChangeRequest, zzee zzee) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(userProfileChangeRequest);
        Preconditions.checkNotNull(zzee);
        zza(str, (zzfr<zzff>) new zzah(this, userProfileChangeRequest, zzee));
    }

    public final void zza(String str, String str2, zzee zzee) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        Preconditions.checkNotNull(zzee);
        zza(str, (zzfr<zzff>) new zzag(this, str2, zzee));
    }

    public final void zzb(String str, String str2, zzee zzee) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        Preconditions.checkNotNull(zzee);
        zza(str, (zzfr<zzff>) new zzaj(this, str2, zzee));
    }

    public final void zzc(String str, String str2, zzee zzee) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzee);
        zzfw zzfw = new zzfw();
        zzfw.zzh(str);
        zzfw.zzi(str2);
        this.zzb.zza(zzfw, (zzfr<zzfv>) new zzai(this, zzee));
    }

    private final void zza(String str, zzfr<zzff> zzfr) {
        Preconditions.checkNotNull(zzfr);
        Preconditions.checkNotEmpty(str);
        zzff zzb2 = zzff.zzb(str);
        if (zzb2.zzb()) {
            zzfr.zza(zzb2);
            return;
        }
        this.zzb.zza(new zzew(zzb2.zzc()), (zzfr<zzff>) new zzal(this, zzfr));
    }

    public final void zza(String str, String str2, String str3, zzee zzee) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        Preconditions.checkNotNull(zzee);
        this.zzb.zza(new zzfy(str, str2, (String) null, str3), (zzfr<zzfx>) new zzd(this, zzee));
    }

    public final void zza(Context context, String str, String str2, String str3, zzee zzee) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        Preconditions.checkNotNull(zzee);
        this.zzb.zza((Context) null, new zzgi(str, str2, str3), (zzfr<zzgh>) new zzc(this, zzee));
    }

    public final void zza(EmailAuthCredential emailAuthCredential, zzee zzee) {
        Preconditions.checkNotNull(emailAuthCredential);
        Preconditions.checkNotNull(zzee);
        if (emailAuthCredential.zzf()) {
            zza(emailAuthCredential.zze(), (zzfr<zzff>) new zzf(this, emailAuthCredential, zzee));
        } else {
            zza(new zzer(emailAuthCredential, (String) null), zzee);
        }
    }

    /* access modifiers changed from: private */
    public final void zza(zzer zzer, zzee zzee) {
        Preconditions.checkNotNull(zzer);
        Preconditions.checkNotNull(zzee);
        this.zzb.zza(zzer, (zzfr<zzeu>) new zze(this, zzee));
    }

    /* access modifiers changed from: private */
    public final void zza(zzee zzee, zzff zzff, zzfw zzfw, zzfo zzfo) {
        Preconditions.checkNotNull(zzee);
        Preconditions.checkNotNull(zzff);
        Preconditions.checkNotNull(zzfw);
        Preconditions.checkNotNull(zzfo);
        this.zzb.zza(new zzev(zzff.zzd()), (zzfr<zzey>) new zzh(this, zzfo, zzee, zzff, zzfw));
    }

    /* access modifiers changed from: private */
    public final void zza(zzee zzee, zzff zzff, zzfa zzfa, zzfw zzfw, zzfo zzfo) {
        Preconditions.checkNotNull(zzee);
        Preconditions.checkNotNull(zzff);
        Preconditions.checkNotNull(zzfa);
        Preconditions.checkNotNull(zzfw);
        Preconditions.checkNotNull(zzfo);
        this.zzb.zza(zzfw, (zzfr<zzfv>) new zzg(this, zzfw, zzfa, zzee, zzff, zzfo));
    }

    /* access modifiers changed from: private */
    public static zzff zza(zzff zzff, zzfv zzfv) {
        Preconditions.checkNotNull(zzff);
        Preconditions.checkNotNull(zzfv);
        String zzb2 = zzfv.zzb();
        String zzc2 = zzfv.zzc();
        if (TextUtils.isEmpty(zzb2) || TextUtils.isEmpty(zzc2)) {
            return zzff;
        }
        return new zzff(zzc2, zzb2, Long.valueOf(zzfv.zzd()), zzff.zzf());
    }

    /* access modifiers changed from: private */
    public final void zza(zzff zzff, String str, String str2, Boolean bool, zze zze, zzee zzee, zzfo zzfo) {
        Preconditions.checkNotNull(zzff);
        Preconditions.checkNotNull(zzfo);
        Preconditions.checkNotNull(zzee);
        this.zzb.zza(new zzev(zzff.zzd()), (zzfr<zzey>) new zzj(this, zzfo, str2, str, bool, zze, zzee, zzff));
    }

    public final void zzd(String str, String str2, zzee zzee) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzee);
        this.zzb.zza(new zzen(str, str2), (zzfr<zzeq>) new zzi(this, zzee));
    }

    public final void zza(String str, ActionCodeSettings actionCodeSettings, String str2, zzee zzee) {
        zzfe zzfe;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzee);
        zzgm zza2 = zzgm.zza(actionCodeSettings.zzd());
        if (zza2 != null) {
            zzfe = new zzfe(zza2);
        } else {
            zzfe = new zzfe(zzgm.OOB_REQ_TYPE_UNSPECIFIED);
        }
        zzfe.zza(str);
        zzfe.zza(actionCodeSettings);
        zzfe.zzc(str2);
        this.zzb.zza(zzfe, (zzfr<zzfg>) new zzl(this, zzee));
    }

    public final void zza(String str, ActionCodeSettings actionCodeSettings, zzee zzee) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzee);
        zzfe zzfe = new zzfe(zzgm.VERIFY_EMAIL);
        zzfe.zzb(str);
        if (actionCodeSettings != null) {
            zzfe.zza(actionCodeSettings);
        }
        zzb(zzfe, zzee);
    }

    public final void zze(String str, String str2, zzee zzee) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzee);
        this.zzb.zza(new zzfn(str, (String) null, str2), (zzfr<zzfq>) new zzk(this, zzee));
    }

    public final void zzb(String str, String str2, String str3, zzee zzee) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        Preconditions.checkNotNull(zzee);
        this.zzb.zza(new zzfn(str, str2, str3), (zzfr<zzfq>) new zzm(this, zzee));
    }

    public final void zza(zzfr zzfr, zzee zzee) {
        Preconditions.checkNotEmpty(zzfr.zzb());
        Preconditions.checkNotNull(zzee);
        this.zzb.zza(zzfr, (zzfr<zzft>) new zzp(this, zzee));
    }

    public final void zza(Context context, zzgk zzgk, zzee zzee) {
        Preconditions.checkNotNull(zzgk);
        Preconditions.checkNotNull(zzee);
        this.zzb.zza((Context) null, zzgk, (zzfr<zzgj>) new zzo(this, zzee));
    }

    public final void zzc(String str, String str2, String str3, zzee zzee) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        Preconditions.checkNotEmpty(str3);
        Preconditions.checkNotNull(zzee);
        zza(str3, (zzfr<zzff>) new zzr(this, str, str2, zzee));
    }

    public final void zza(Context context, String str, zzgk zzgk, zzee zzee) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzgk);
        Preconditions.checkNotNull(zzee);
        zza(str, (zzfr<zzff>) new zzq(this, zzgk, (Context) null, zzee));
    }

    public final void zza(String str, zzgc zzgc, zzee zzee) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzgc);
        Preconditions.checkNotNull(zzee);
        zza(str, (zzfr<zzff>) new zzs(this, zzgc, zzee));
    }

    public final void zzc(String str, zzee zzee) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzee);
        zza(str, (zzfr<zzff>) new zzu(this, zzee));
    }

    public final void zzf(String str, String str2, zzee zzee) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        Preconditions.checkNotNull(zzee);
        zza(str2, (zzfr<zzff>) new zzx(this, str, zzee));
    }

    public final void zza(zzfe zzfe, zzee zzee) {
        zzb(zzfe, zzee);
    }

    public final void zzd(String str, zzee zzee) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzee);
        zza(str, (zzfr<zzff>) new zzy(this, zzee));
    }

    public final void zze(String str, zzee zzee) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzee);
        zza(str, (zzfr<zzff>) new zzaa(this, zzee));
    }

    public final void zzf(String str, zzee zzee) {
        Preconditions.checkNotNull(zzee);
        this.zzb.zza(str, (zzfr<Void>) new zzac(this, zzee));
    }

    private final void zzb(zzfe zzfe, zzee zzee) {
        Preconditions.checkNotNull(zzfe);
        Preconditions.checkNotNull(zzee);
        this.zzb.zza(zzfe, (zzfr<zzfg>) new zzaf(this, zzee));
    }

    /* access modifiers changed from: private */
    public final void zza(zzge zzge, zzee zzee, zzfo zzfo) {
        Status status;
        if (zzge.zzk()) {
            zze zzp = zzge.zzp();
            String zzd = zzge.zzd();
            String zzl = zzge.zzl();
            if (zzge.zzb()) {
                status = new Status(FirebaseError.ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL);
            } else {
                status = zzy.zza(zzge.zzj());
            }
            if (this.zzc.zza()) {
                zzee.zza(new zzek(status, zzp, zzd, zzl));
            } else {
                zzee.zza(status);
            }
        } else {
            zza(new zzff(zzge.zzg(), zzge.zzc(), Long.valueOf(zzge.zzh()), "Bearer"), zzge.zzf(), zzge.zze(), Boolean.valueOf(zzge.zzi()), zzge.zzp(), zzee, zzfo);
        }
    }
}
