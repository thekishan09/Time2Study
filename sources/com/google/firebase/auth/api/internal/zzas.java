package com.google.firebase.auth.api.internal;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzf;
import com.google.android.gms.internal.firebase_auth.zzfa;
import com.google.android.gms.internal.firebase_auth.zzfj;
import com.google.android.gms.internal.firebase_auth.zzfr;
import com.google.android.gms.internal.firebase_auth.zzgm;
import com.google.android.gms.internal.firebase_auth.zzk;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.ActionCodeResult;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.PhoneMultiFactorAssertion;
import com.google.firebase.auth.PhoneMultiFactorInfo;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.auth.internal.zzae;
import com.google.firebase.auth.internal.zzaf;
import com.google.firebase.auth.internal.zzar;
import com.google.firebase.auth.internal.zzb;
import com.google.firebase.auth.internal.zzbc;
import com.google.firebase.auth.internal.zzl;
import com.google.firebase.auth.internal.zzp;
import com.google.firebase.auth.internal.zzr;
import com.google.firebase.auth.internal.zzw;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzas extends zzak<zzew> {
    private final Context zza;
    private final zzew zzb;
    private final Future<zzan<zzew>> zzc = zza();

    zzas(Context context, zzew zzew) {
        this.zza = context;
        this.zzb = zzew;
    }

    /* access modifiers changed from: package-private */
    public final Future<zzan<zzew>> zza() {
        Future<zzan<zzew>> future = this.zzc;
        if (future != null) {
            return future;
        }
        return zzf.zza().zza(zzk.zzb).submit(new zzef(this.zzb, this.zza));
    }

    public final Task<GetTokenResult> zza(FirebaseApp firebaseApp, FirebaseUser firebaseUser, String str, zzbc zzbc) {
        zzbk zzbk = (zzbk) new zzbk(str).zza(firebaseApp).zza(firebaseUser).zza(zzbc).zza((zzae) zzbc);
        return zza(zza(zzbk), zzbk);
    }

    public final Task<AuthResult> zza(FirebaseApp firebaseApp, String str, String str2, zzb zzb2) {
        zzcw zzcw = (zzcw) new zzcw(str, str2).zza(firebaseApp).zza(zzb2);
        return zza(zzb(zzcw), zzcw);
    }

    public final Task<AuthResult> zza(FirebaseApp firebaseApp, AuthCredential authCredential, String str, zzb zzb2) {
        zzcu zzcu = (zzcu) new zzcu(authCredential, str).zza(firebaseApp).zza(zzb2);
        return zza(zzb(zzcu), zzcu);
    }

    public final Task<Void> zza(FirebaseApp firebaseApp, FirebaseUser firebaseUser, AuthCredential authCredential, String str, zzbc zzbc) {
        zzbu zzbu = (zzbu) new zzbu(authCredential, str).zza(firebaseApp).zza(firebaseUser).zza(zzbc).zza((zzae) zzbc);
        return zza(zzb(zzbu), zzbu);
    }

    public final Task<AuthResult> zzb(FirebaseApp firebaseApp, FirebaseUser firebaseUser, AuthCredential authCredential, String str, zzbc zzbc) {
        zzbw zzbw = (zzbw) new zzbw(authCredential, str).zza(firebaseApp).zza(firebaseUser).zza(zzbc).zza((zzae) zzbc);
        return zza(zzb(zzbw), zzbw);
    }

    public final Task<AuthResult> zza(FirebaseApp firebaseApp, zzb zzb2, String str) {
        zzcs zzcs = (zzcs) new zzcs(str).zza(firebaseApp).zza(zzb2);
        return zza(zzb(zzcs), zzcs);
    }

    public final void zza(FirebaseApp firebaseApp, zzfr zzfr, PhoneAuthProvider.OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks, Activity activity, Executor executor) {
        zzea zzea = (zzea) new zzea(zzfr).zza(firebaseApp).zza(onVerificationStateChangedCallbacks, activity, executor);
        zza(zzb(zzea), zzea);
    }

    public final Task<Void> zza(FirebaseApp firebaseApp, FirebaseUser firebaseUser, UserProfileChangeRequest userProfileChangeRequest, zzbc zzbc) {
        zzdu zzdu = (zzdu) new zzdu(userProfileChangeRequest).zza(firebaseApp).zza(firebaseUser).zza(zzbc).zza((zzae) zzbc);
        return zza(zzb(zzdu), zzdu);
    }

    public final Task<Void> zzb(FirebaseApp firebaseApp, FirebaseUser firebaseUser, String str, zzbc zzbc) {
        zzdo zzdo = (zzdo) new zzdo(str).zza(firebaseApp).zza(firebaseUser).zza(zzbc).zza((zzae) zzbc);
        return zza(zzb(zzdo), zzdo);
    }

    public final Task<Void> zzc(FirebaseApp firebaseApp, FirebaseUser firebaseUser, String str, zzbc zzbc) {
        zzdq zzdq = (zzdq) new zzdq(str).zza(firebaseApp).zza(firebaseUser).zza(zzbc).zza((zzae) zzbc);
        return zza(zzb(zzdq), zzdq);
    }

    public final Task<Void> zza(FirebaseApp firebaseApp, FirebaseUser firebaseUser, PhoneAuthCredential phoneAuthCredential, zzbc zzbc) {
        zzds zzds = (zzds) new zzds(phoneAuthCredential).zza(firebaseApp).zza(firebaseUser).zza(zzbc).zza((zzae) zzbc);
        return zza(zzb(zzds), zzds);
    }

    public final Task<AuthResult> zza(FirebaseApp firebaseApp, String str, String str2, String str3, zzb zzb2) {
        zzba zzba = (zzba) new zzba(str, str2, str3).zza(firebaseApp).zza(zzb2);
        return zza(zzb(zzba), zzba);
    }

    public final Task<AuthResult> zzb(FirebaseApp firebaseApp, String str, String str2, String str3, zzb zzb2) {
        zzcy zzcy = (zzcy) new zzcy(str, str2, str3).zza(firebaseApp).zza(zzb2);
        return zza(zzb(zzcy), zzcy);
    }

    public final Task<AuthResult> zza(FirebaseApp firebaseApp, EmailAuthCredential emailAuthCredential, zzb zzb2) {
        zzda zzda = (zzda) new zzda(emailAuthCredential).zza(firebaseApp).zza(zzb2);
        return zza(zzb(zzda), zzda);
    }

    public final Task<Void> zza(FirebaseApp firebaseApp, FirebaseUser firebaseUser, String str, String str2, String str3, zzbc zzbc) {
        zzcc zzcc = (zzcc) new zzcc(str, str2, str3).zza(firebaseApp).zza(firebaseUser).zza(zzbc).zza((zzae) zzbc);
        return zza(zzb(zzcc), zzcc);
    }

    public final Task<AuthResult> zzb(FirebaseApp firebaseApp, FirebaseUser firebaseUser, String str, String str2, String str3, zzbc zzbc) {
        zzce zzce = (zzce) new zzce(str, str2, str3).zza(firebaseApp).zza(firebaseUser).zza(zzbc).zza((zzae) zzbc);
        return zza(zzb(zzce), zzce);
    }

    public final Task<Void> zza(FirebaseApp firebaseApp, FirebaseUser firebaseUser, EmailAuthCredential emailAuthCredential, zzbc zzbc) {
        zzby zzby = (zzby) new zzby(emailAuthCredential).zza(firebaseApp).zza(firebaseUser).zza(zzbc).zza((zzae) zzbc);
        return zza(zzb(zzby), zzby);
    }

    public final Task<AuthResult> zzb(FirebaseApp firebaseApp, FirebaseUser firebaseUser, EmailAuthCredential emailAuthCredential, zzbc zzbc) {
        zzca zzca = (zzca) new zzca(emailAuthCredential).zza(firebaseApp).zza(firebaseUser).zza(zzbc).zza((zzae) zzbc);
        return zza(zzb(zzca), zzca);
    }

    public final Task<AuthResult> zza(FirebaseApp firebaseApp, PhoneAuthCredential phoneAuthCredential, String str, zzb zzb2) {
        zzdc zzdc = (zzdc) new zzdc(phoneAuthCredential, str).zza(firebaseApp).zza(zzb2);
        return zza(zzb(zzdc), zzdc);
    }

    public final Task<Void> zza(FirebaseApp firebaseApp, FirebaseUser firebaseUser, PhoneAuthCredential phoneAuthCredential, String str, zzbc zzbc) {
        zzcg zzcg = (zzcg) new zzcg(phoneAuthCredential, str).zza(firebaseApp).zza(firebaseUser).zza(zzbc).zza((zzae) zzbc);
        return zza(zzb(zzcg), zzcg);
    }

    public final Task<AuthResult> zzb(FirebaseApp firebaseApp, FirebaseUser firebaseUser, PhoneAuthCredential phoneAuthCredential, String str, zzbc zzbc) {
        zzci zzci = (zzci) new zzci(phoneAuthCredential, str).zza(firebaseApp).zza(firebaseUser).zza(zzbc).zza((zzae) zzbc);
        return zza(zzb(zzci), zzci);
    }

    public final Task<SignInMethodQueryResult> zza(FirebaseApp firebaseApp, String str, String str2) {
        zzbe zzbe = (zzbe) new zzbe(str, str2).zza(firebaseApp);
        return zza(zza(zzbe), zzbe);
    }

    public final Task<Void> zza(FirebaseApp firebaseApp, String str, ActionCodeSettings actionCodeSettings, String str2) {
        actionCodeSettings.zza(zzgm.PASSWORD_RESET);
        zzco zzco = (zzco) new zzco(str, actionCodeSettings, str2, "sendPasswordResetEmail").zza(firebaseApp);
        return zza(zzb(zzco), zzco);
    }

    public final Task<Void> zzb(FirebaseApp firebaseApp, String str, ActionCodeSettings actionCodeSettings, String str2) {
        actionCodeSettings.zza(zzgm.EMAIL_SIGNIN);
        zzco zzco = (zzco) new zzco(str, actionCodeSettings, str2, "sendSignInLinkToEmail").zza(firebaseApp);
        return zza(zzb(zzco), zzco);
    }

    public final Task<Void> zza(FirebaseApp firebaseApp, ActionCodeSettings actionCodeSettings, String str) {
        zzcm zzcm = (zzcm) new zzcm(str, actionCodeSettings).zza(firebaseApp);
        return zza(zzb(zzcm), zzcm);
    }

    public final Task<ActionCodeResult> zzb(FirebaseApp firebaseApp, String str, String str2) {
        zzaw zzaw = (zzaw) new zzaw(str, str2).zza(firebaseApp);
        return zza(zzb(zzaw), zzaw);
    }

    public final Task<Void> zzc(FirebaseApp firebaseApp, String str, String str2) {
        zzau zzau = (zzau) new zzau(str, str2).zza(firebaseApp);
        return zza(zzb(zzau), zzau);
    }

    public final Task<String> zzd(FirebaseApp firebaseApp, String str, String str2) {
        zzdy zzdy = (zzdy) new zzdy(str, str2).zza(firebaseApp);
        return zza(zzb(zzdy), zzdy);
    }

    public final Task<Void> zza(FirebaseApp firebaseApp, String str, String str2, String str3) {
        zzay zzay = (zzay) new zzay(str, str2, str3).zza(firebaseApp);
        return zza(zzb(zzay), zzay);
    }

    public final Task<AuthResult> zza(FirebaseApp firebaseApp, FirebaseUser firebaseUser, AuthCredential authCredential, zzbc zzbc) {
        Preconditions.checkNotNull(firebaseApp);
        Preconditions.checkNotNull(authCredential);
        Preconditions.checkNotNull(firebaseUser);
        Preconditions.checkNotNull(zzbc);
        List<String> zza2 = firebaseUser.zza();
        if (zza2 != null && zza2.contains(authCredential.getProvider())) {
            return Tasks.forException(zzej.zza(new Status(FirebaseError.ERROR_PROVIDER_ALREADY_LINKED)));
        }
        if (authCredential instanceof EmailAuthCredential) {
            EmailAuthCredential emailAuthCredential = (EmailAuthCredential) authCredential;
            if (!emailAuthCredential.zzg()) {
                zzbm zzbm = (zzbm) new zzbm(emailAuthCredential).zza(firebaseApp).zza(firebaseUser).zza(zzbc).zza((zzae) zzbc);
                return zza(zzb(zzbm), zzbm);
            }
            zzbs zzbs = (zzbs) new zzbs(emailAuthCredential).zza(firebaseApp).zza(firebaseUser).zza(zzbc).zza((zzae) zzbc);
            return zza(zzb(zzbs), zzbs);
        } else if (authCredential instanceof PhoneAuthCredential) {
            zzbq zzbq = (zzbq) new zzbq((PhoneAuthCredential) authCredential).zza(firebaseApp).zza(firebaseUser).zza(zzbc).zza((zzae) zzbc);
            return zza(zzb(zzbq), zzbq);
        } else {
            Preconditions.checkNotNull(firebaseApp);
            Preconditions.checkNotNull(authCredential);
            Preconditions.checkNotNull(firebaseUser);
            Preconditions.checkNotNull(zzbc);
            zzbo zzbo = (zzbo) new zzbo(authCredential).zza(firebaseApp).zza(firebaseUser).zza(zzbc).zza((zzae) zzbc);
            return zza(zzb(zzbo), zzbo);
        }
    }

    public final Task<AuthResult> zzd(FirebaseApp firebaseApp, FirebaseUser firebaseUser, String str, zzbc zzbc) {
        Preconditions.checkNotNull(firebaseApp);
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(firebaseUser);
        Preconditions.checkNotNull(zzbc);
        List<String> zza2 = firebaseUser.zza();
        if ((zza2 != null && !zza2.contains(str)) || firebaseUser.isAnonymous()) {
            return Tasks.forException(zzej.zza(new Status(FirebaseError.ERROR_NO_SUCH_PROVIDER, str)));
        }
        char c = 65535;
        if (str.hashCode() == 1216985755 && str.equals("password")) {
            c = 0;
        }
        if (c != 0) {
            zzdm zzdm = (zzdm) new zzdm(str).zza(firebaseApp).zza(firebaseUser).zza(zzbc).zza((zzae) zzbc);
            return zza(zzb(zzdm), zzdm);
        }
        zzdk zzdk = (zzdk) new zzdk().zza(firebaseApp).zza(firebaseUser).zza(zzbc).zza((zzae) zzbc);
        return zza(zzb(zzdk), zzdk);
    }

    public final Task<Void> zza(FirebaseApp firebaseApp, FirebaseUser firebaseUser, zzbc zzbc) {
        zzck zzck = (zzck) new zzck().zza(firebaseApp).zza(firebaseUser).zza(zzbc).zza((zzae) zzbc);
        return zza(zza(zzck), zzck);
    }

    public final Task<Void> zza(FirebaseUser firebaseUser, zzaf zzaf) {
        zzbc zzbc = (zzbc) new zzbc().zza(firebaseUser).zza(zzaf).zza((zzae) zzaf);
        return zza(zzb(zzbc), zzbc);
    }

    public final Task<Void> zza(String str) {
        zzcq zzcq = new zzcq(str);
        return zza(zzb(zzcq), zzcq);
    }

    public final Task<Void> zza(zzw zzw, String str, String str2, long j, boolean z, boolean z2, PhoneAuthProvider.OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks, Executor executor, Activity activity) {
        zzde zzde = new zzde(zzw, str, str2, j, z, z2);
        zzde.zza(onVerificationStateChangedCallbacks, activity, executor);
        return zzb(zzde);
    }

    public final Task<Void> zza(FirebaseApp firebaseApp, PhoneMultiFactorAssertion phoneMultiFactorAssertion, FirebaseUser firebaseUser, String str, zzb zzb2) {
        zzbg zzbg = new zzbg(phoneMultiFactorAssertion, firebaseUser.zzf(), str);
        zzbg.zza(firebaseApp).zza(zzb2);
        return zzb(zzbg);
    }

    public final Task<Void> zza(zzw zzw, PhoneMultiFactorInfo phoneMultiFactorInfo, String str, long j, boolean z, boolean z2, PhoneAuthProvider.OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks, Executor executor, Activity activity) {
        zzdg zzdg = new zzdg(phoneMultiFactorInfo, zzw.zzb(), str, j, z, z2);
        zzdg.zza(onVerificationStateChangedCallbacks, activity, executor);
        return zzb(zzdg);
    }

    public final Task<AuthResult> zza(FirebaseApp firebaseApp, FirebaseUser firebaseUser, PhoneMultiFactorAssertion phoneMultiFactorAssertion, String str, zzb zzb2) {
        zzbi zzbi = new zzbi(phoneMultiFactorAssertion, str);
        zzbi.zza(firebaseApp).zza(zzb2);
        if (firebaseUser != null) {
            zzbi.zza(firebaseUser);
        }
        return zzb(zzbi);
    }

    public final Task<Void> zze(FirebaseApp firebaseApp, FirebaseUser firebaseUser, String str, zzbc zzbc) {
        return zzb((zzdi) new zzdi(firebaseUser.zzf(), str).zza(firebaseApp).zza(firebaseUser).zza(zzbc).zza((zzae) zzbc));
    }

    public final Task<Void> zza(String str, String str2, ActionCodeSettings actionCodeSettings) {
        actionCodeSettings.zza(zzgm.VERIFY_AND_CHANGE_EMAIL);
        return zzb(new zzdw(str, str2, actionCodeSettings));
    }

    static zzp zza(FirebaseApp firebaseApp, zzfa zzfa) {
        Preconditions.checkNotNull(firebaseApp);
        Preconditions.checkNotNull(zzfa);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new zzl(zzfa, FirebaseAuthProvider.PROVIDER_ID));
        List<zzfj> zzj = zzfa.zzj();
        if (zzj != null && !zzj.isEmpty()) {
            for (int i = 0; i < zzj.size(); i++) {
                arrayList.add(new zzl(zzj.get(i)));
            }
        }
        zzp zzp = new zzp(firebaseApp, arrayList);
        zzp.zza(new zzr(zzfa.zzh(), zzfa.zzg()));
        zzp.zza(zzfa.zzi());
        zzp.zza(zzfa.zzl());
        zzp.zzb(zzar.zza(zzfa.zzm()));
        return zzp;
    }

    private final <ResultT> Task<ResultT> zza(Task<ResultT> task, zzar<zzeh, ResultT> zzar) {
        return task.continueWithTask(new zzav(this, zzar));
    }
}
