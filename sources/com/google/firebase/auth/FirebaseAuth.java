package com.google.firebase.auth;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzff;
import com.google.android.gms.internal.firebase_auth.zzfr;
import com.google.android.gms.internal.firebase_auth.zzgm;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.api.internal.zzas;
import com.google.firebase.auth.api.internal.zzec;
import com.google.firebase.auth.api.internal.zzej;
import com.google.firebase.auth.api.internal.zzeu;
import com.google.firebase.auth.api.internal.zzev;
import com.google.firebase.auth.api.internal.zzfe;
import com.google.firebase.auth.internal.InternalAuthProvider;
import com.google.firebase.auth.internal.zzae;
import com.google.firebase.auth.internal.zzaf;
import com.google.firebase.auth.internal.zzap;
import com.google.firebase.auth.internal.zzaq;
import com.google.firebase.auth.internal.zzaw;
import com.google.firebase.auth.internal.zzax;
import com.google.firebase.auth.internal.zzay;
import com.google.firebase.auth.internal.zzaz;
import com.google.firebase.auth.internal.zzbc;
import com.google.firebase.auth.internal.zzj;
import com.google.firebase.auth.internal.zzm;
import com.google.firebase.auth.internal.zzp;
import com.google.firebase.auth.internal.zzw;
import com.google.firebase.internal.InternalTokenResult;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public class FirebaseAuth implements InternalAuthProvider {
    private FirebaseApp zza;
    /* access modifiers changed from: private */
    public final List<IdTokenListener> zzb;
    /* access modifiers changed from: private */
    public final List<com.google.firebase.auth.internal.IdTokenListener> zzc;
    /* access modifiers changed from: private */
    public List<AuthStateListener> zzd;
    private zzas zze;
    /* access modifiers changed from: private */
    public FirebaseUser zzf;
    /* access modifiers changed from: private */
    public zzm zzg;
    private final Object zzh;
    private String zzi;
    private final Object zzj;
    private String zzk;
    private final zzay zzl;
    private final zzaq zzm;
    private zzax zzn;
    private zzaz zzo;

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public interface AuthStateListener {
        void onAuthStateChanged(FirebaseAuth firebaseAuth);
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public interface IdTokenListener {
        void onIdTokenChanged(FirebaseAuth firebaseAuth);
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    class zza implements com.google.firebase.auth.internal.zzb {
        zza() {
        }

        public final void zza(zzff zzff, FirebaseUser firebaseUser) {
            Preconditions.checkNotNull(zzff);
            Preconditions.checkNotNull(firebaseUser);
            firebaseUser.zza(zzff);
            FirebaseAuth.this.zza(firebaseUser, zzff, true);
        }
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    class zzb implements zzae, com.google.firebase.auth.internal.zzb {
        zzb() {
        }

        public final void zza(zzff zzff, FirebaseUser firebaseUser) {
            Preconditions.checkNotNull(zzff);
            Preconditions.checkNotNull(firebaseUser);
            firebaseUser.zza(zzff);
            FirebaseAuth.this.zza(firebaseUser, zzff, true, true);
        }

        public final void zza(Status status) {
            if (status.getStatusCode() == 17011 || status.getStatusCode() == 17021 || status.getStatusCode() == 17005 || status.getStatusCode() == 17091) {
                FirebaseAuth.this.signOut();
            }
        }
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    class zzc extends zza implements zzae, com.google.firebase.auth.internal.zzb {
        zzc(FirebaseAuth firebaseAuth) {
            super();
        }

        public final void zza(Status status) {
        }
    }

    public static FirebaseAuth getInstance() {
        return (FirebaseAuth) FirebaseApp.getInstance().get(FirebaseAuth.class);
    }

    public static FirebaseAuth getInstance(FirebaseApp firebaseApp) {
        return (FirebaseAuth) firebaseApp.get(FirebaseAuth.class);
    }

    public FirebaseAuth(FirebaseApp firebaseApp) {
        this(firebaseApp, zzeu.zza(firebaseApp.getApplicationContext(), new zzev(firebaseApp.getOptions().getApiKey()).zza()), new zzay(firebaseApp.getApplicationContext(), firebaseApp.getPersistenceKey()), zzaq.zza());
    }

    private FirebaseAuth(FirebaseApp firebaseApp, zzas zzas, zzay zzay, zzaq zzaq) {
        zzff zzb2;
        this.zzh = new Object();
        this.zzj = new Object();
        this.zza = (FirebaseApp) Preconditions.checkNotNull(firebaseApp);
        this.zze = (zzas) Preconditions.checkNotNull(zzas);
        this.zzl = (zzay) Preconditions.checkNotNull(zzay);
        this.zzg = new zzm();
        this.zzm = (zzaq) Preconditions.checkNotNull(zzaq);
        this.zzb = new CopyOnWriteArrayList();
        this.zzc = new CopyOnWriteArrayList();
        this.zzd = new CopyOnWriteArrayList();
        this.zzo = zzaz.zza();
        FirebaseUser zza2 = this.zzl.zza();
        this.zzf = zza2;
        if (!(zza2 == null || (zzb2 = this.zzl.zzb(zza2)) == null)) {
            zza(this.zzf, zzb2, false);
        }
        this.zzm.zza(this);
    }

    public FirebaseUser getCurrentUser() {
        return this.zzf;
    }

    public String getUid() {
        FirebaseUser firebaseUser = this.zzf;
        if (firebaseUser == null) {
            return null;
        }
        return firebaseUser.getUid();
    }

    public final void zza(FirebaseUser firebaseUser, zzff zzff, boolean z) {
        zza(firebaseUser, zzff, z, false);
    }

    /* access modifiers changed from: package-private */
    public final void zza(FirebaseUser firebaseUser, zzff zzff, boolean z, boolean z2) {
        boolean z3;
        Preconditions.checkNotNull(firebaseUser);
        Preconditions.checkNotNull(zzff);
        boolean z4 = false;
        boolean z5 = true;
        boolean z6 = this.zzf != null && firebaseUser.getUid().equals(this.zzf.getUid());
        if (z6 || !z2) {
            FirebaseUser firebaseUser2 = this.zzf;
            if (firebaseUser2 == null) {
                z4 = true;
            } else {
                boolean z7 = !firebaseUser2.zze().zzd().equals(zzff.zzd());
                if (!z6 || z7) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                z5 = z3;
                if (!z6) {
                    z4 = true;
                }
            }
            Preconditions.checkNotNull(firebaseUser);
            FirebaseUser firebaseUser3 = this.zzf;
            if (firebaseUser3 == null) {
                this.zzf = firebaseUser;
            } else {
                firebaseUser3.zza(firebaseUser.getProviderData());
                if (!firebaseUser.isAnonymous()) {
                    this.zzf.zzb();
                }
                this.zzf.zzb(firebaseUser.getMultiFactor().getEnrolledFactors());
            }
            if (z) {
                this.zzl.zza(this.zzf);
            }
            if (z5) {
                FirebaseUser firebaseUser4 = this.zzf;
                if (firebaseUser4 != null) {
                    firebaseUser4.zza(zzff);
                }
                zzc(this.zzf);
            }
            if (z4) {
                zzd(this.zzf);
            }
            if (z) {
                this.zzl.zza(firebaseUser, zzff);
            }
            zzd().zza(this.zzf.zze());
        }
    }

    public final void zza() {
        FirebaseUser firebaseUser = this.zzf;
        if (firebaseUser != null) {
            zzay zzay = this.zzl;
            Preconditions.checkNotNull(firebaseUser);
            zzay.zza(String.format("com.google.firebase.auth.GET_TOKEN_RESPONSE.%s", new Object[]{firebaseUser.getUid()}));
            this.zzf = null;
        }
        this.zzl.zza("com.google.firebase.auth.FIREBASE_USER");
        zzc((FirebaseUser) null);
        zzd((FirebaseUser) null);
    }

    private final synchronized void zza(zzax zzax) {
        this.zzn = zzax;
    }

    private final synchronized zzax zzd() {
        if (this.zzn == null) {
            zza(new zzax(this.zza));
        }
        return this.zzn;
    }

    public FirebaseApp getApp() {
        return this.zza;
    }

    public final FirebaseApp zzb() {
        return this.zza;
    }

    public void addIdTokenListener(IdTokenListener idTokenListener) {
        this.zzb.add(idTokenListener);
        this.zzo.execute(new zzi(this, idTokenListener));
    }

    public void addIdTokenListener(com.google.firebase.auth.internal.IdTokenListener idTokenListener) {
        Preconditions.checkNotNull(idTokenListener);
        this.zzc.add(idTokenListener);
        zzd().zza(this.zzc.size());
    }

    public void removeIdTokenListener(IdTokenListener idTokenListener) {
        this.zzb.remove(idTokenListener);
    }

    public void removeIdTokenListener(com.google.firebase.auth.internal.IdTokenListener idTokenListener) {
        Preconditions.checkNotNull(idTokenListener);
        this.zzc.remove(idTokenListener);
        zzd().zza(this.zzc.size());
    }

    public void addAuthStateListener(AuthStateListener authStateListener) {
        this.zzd.add(authStateListener);
        this.zzo.execute(new zzh(this, authStateListener));
    }

    public void removeAuthStateListener(AuthStateListener authStateListener) {
        this.zzd.remove(authStateListener);
    }

    private final void zzc(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            String uid = firebaseUser.getUid();
            StringBuilder sb = new StringBuilder(String.valueOf(uid).length() + 45);
            sb.append("Notifying id token listeners about user ( ");
            sb.append(uid);
            sb.append(" ).");
            Log.d("FirebaseAuth", sb.toString());
        } else {
            Log.d("FirebaseAuth", "Notifying id token listeners about a sign-out event.");
        }
        this.zzo.execute(new zzk(this, new InternalTokenResult(firebaseUser != null ? firebaseUser.zzg() : null)));
    }

    private final void zzd(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            String uid = firebaseUser.getUid();
            StringBuilder sb = new StringBuilder(String.valueOf(uid).length() + 47);
            sb.append("Notifying auth state listeners about user ( ");
            sb.append(uid);
            sb.append(" ).");
            Log.d("FirebaseAuth", sb.toString());
        } else {
            Log.d("FirebaseAuth", "Notifying auth state listeners about a sign-out event.");
        }
        this.zzo.execute(new zzj(this));
    }

    public Task<GetTokenResult> getAccessToken(boolean z) {
        return zza(this.zzf, z);
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.google.firebase.auth.internal.zzbc, com.google.firebase.auth.zzm] */
    public final Task<GetTokenResult> zza(FirebaseUser firebaseUser, boolean z) {
        if (firebaseUser == null) {
            return Tasks.forException(zzej.zza(new Status(FirebaseError.ERROR_NO_SIGNED_IN_USER)));
        }
        zzff zze2 = firebaseUser.zze();
        if (!zze2.zzb() || z) {
            return this.zze.zza(this.zza, firebaseUser, zze2.zzc(), (zzbc) new zzm(this));
        }
        return Tasks.forResult(zzap.zza(zze2.zzd()));
    }

    public Task<AuthResult> signInWithCredential(AuthCredential authCredential) {
        Preconditions.checkNotNull(authCredential);
        AuthCredential zza2 = authCredential.zza();
        if (zza2 instanceof EmailAuthCredential) {
            EmailAuthCredential emailAuthCredential = (EmailAuthCredential) zza2;
            if (!emailAuthCredential.zzg()) {
                return this.zze.zzb(this.zza, emailAuthCredential.zzb(), emailAuthCredential.zzc(), this.zzk, (com.google.firebase.auth.internal.zzb) new zza());
            }
            if (zzb(emailAuthCredential.zzd())) {
                return Tasks.forException(zzej.zza(new Status(17072)));
            }
            return this.zze.zza(this.zza, emailAuthCredential, (com.google.firebase.auth.internal.zzb) new zza());
        } else if (!(zza2 instanceof PhoneAuthCredential)) {
            return this.zze.zza(this.zza, zza2, this.zzk, (com.google.firebase.auth.internal.zzb) new zza());
        } else {
            return this.zze.zza(this.zza, (PhoneAuthCredential) zza2, this.zzk, (com.google.firebase.auth.internal.zzb) new zza());
        }
    }

    /* JADX WARNING: type inference failed for: r5v0, types: [com.google.firebase.auth.internal.zzbc, com.google.firebase.auth.FirebaseAuth$zzb] */
    /* JADX WARNING: type inference failed for: r9v0, types: [com.google.firebase.auth.internal.zzbc, com.google.firebase.auth.FirebaseAuth$zzb] */
    /* JADX WARNING: type inference failed for: r1v1, types: [com.google.firebase.auth.internal.zzbc, com.google.firebase.auth.FirebaseAuth$zzb] */
    /* JADX WARNING: type inference failed for: r10v0, types: [com.google.firebase.auth.internal.zzbc, com.google.firebase.auth.FirebaseAuth$zzb] */
    public final Task<Void> zza(FirebaseUser firebaseUser, AuthCredential authCredential) {
        Preconditions.checkNotNull(firebaseUser);
        Preconditions.checkNotNull(authCredential);
        AuthCredential zza2 = authCredential.zza();
        if (zza2 instanceof EmailAuthCredential) {
            EmailAuthCredential emailAuthCredential = (EmailAuthCredential) zza2;
            if ("password".equals(emailAuthCredential.getSignInMethod())) {
                return this.zze.zza(this.zza, firebaseUser, emailAuthCredential.zzb(), emailAuthCredential.zzc(), firebaseUser.zzd(), new zzb());
            } else if (zzb(emailAuthCredential.zzd())) {
                return Tasks.forException(zzej.zza(new Status(17072)));
            } else {
                return this.zze.zza(this.zza, firebaseUser, emailAuthCredential, (zzbc) new zzb());
            }
        } else if (zza2 instanceof PhoneAuthCredential) {
            return this.zze.zza(this.zza, firebaseUser, (PhoneAuthCredential) zza2, this.zzk, (zzbc) new zzb());
        } else {
            return this.zze.zza(this.zza, firebaseUser, zza2, firebaseUser.zzd(), (zzbc) new zzb());
        }
    }

    /* JADX WARNING: type inference failed for: r5v0, types: [com.google.firebase.auth.internal.zzbc, com.google.firebase.auth.FirebaseAuth$zzb] */
    /* JADX WARNING: type inference failed for: r9v0, types: [com.google.firebase.auth.internal.zzbc, com.google.firebase.auth.FirebaseAuth$zzb] */
    /* JADX WARNING: type inference failed for: r1v1, types: [com.google.firebase.auth.internal.zzbc, com.google.firebase.auth.FirebaseAuth$zzb] */
    /* JADX WARNING: type inference failed for: r10v0, types: [com.google.firebase.auth.internal.zzbc, com.google.firebase.auth.FirebaseAuth$zzb] */
    public final Task<AuthResult> zzb(FirebaseUser firebaseUser, AuthCredential authCredential) {
        Preconditions.checkNotNull(firebaseUser);
        Preconditions.checkNotNull(authCredential);
        AuthCredential zza2 = authCredential.zza();
        if (zza2 instanceof EmailAuthCredential) {
            EmailAuthCredential emailAuthCredential = (EmailAuthCredential) zza2;
            if ("password".equals(emailAuthCredential.getSignInMethod())) {
                return this.zze.zzb(this.zza, firebaseUser, emailAuthCredential.zzb(), emailAuthCredential.zzc(), firebaseUser.zzd(), new zzb());
            } else if (zzb(emailAuthCredential.zzd())) {
                return Tasks.forException(zzej.zza(new Status(17072)));
            } else {
                return this.zze.zzb(this.zza, firebaseUser, emailAuthCredential, (zzbc) new zzb());
            }
        } else if (zza2 instanceof PhoneAuthCredential) {
            return this.zze.zzb(this.zza, firebaseUser, (PhoneAuthCredential) zza2, this.zzk, (zzbc) new zzb());
        } else {
            return this.zze.zzb(this.zza, firebaseUser, zza2, firebaseUser.zzd(), (zzbc) new zzb());
        }
    }

    public Task<AuthResult> signInWithCustomToken(String str) {
        Preconditions.checkNotEmpty(str);
        return this.zze.zza(this.zza, str, this.zzk, (com.google.firebase.auth.internal.zzb) new zza());
    }

    public Task<AuthResult> signInWithEmailAndPassword(String str, String str2) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        return this.zze.zzb(this.zza, str, str2, this.zzk, (com.google.firebase.auth.internal.zzb) new zza());
    }

    public Task<AuthResult> signInWithEmailLink(String str, String str2) {
        return signInWithCredential(EmailAuthProvider.getCredentialWithLink(str, str2));
    }

    public Task<AuthResult> signInAnonymously() {
        FirebaseUser firebaseUser = this.zzf;
        if (firebaseUser == null || !firebaseUser.isAnonymous()) {
            return this.zze.zza(this.zza, (com.google.firebase.auth.internal.zzb) new zza(), this.zzk);
        }
        zzp zzp = (zzp) this.zzf;
        zzp.zza(false);
        return Tasks.forResult(new zzj(zzp));
    }

    public final void zza(String str, long j, TimeUnit timeUnit, PhoneAuthProvider.OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks, Activity activity, Executor executor, boolean z, String str2) {
        long j2 = j;
        long convert = TimeUnit.SECONDS.convert(j, timeUnit);
        if (convert < 0 || convert > 120) {
            throw new IllegalArgumentException("We only support 0-120 seconds for sms-auto-retrieval timeout");
        }
        String str3 = str;
        this.zze.zza(this.zza, new zzfr(str, convert, z, this.zzi, this.zzk, str2), zza(str, onVerificationStateChangedCallbacks), activity, executor);
    }

    public static void zza(PhoneAuthOptions phoneAuthOptions) {
        if (phoneAuthOptions.zzl()) {
            FirebaseAuth zza2 = phoneAuthOptions.zza();
            long longValue = phoneAuthOptions.zzc().longValue();
            PhoneAuthProvider.OnVerificationStateChangedCallbacks zza3 = zza2.zza(phoneAuthOptions.zzb(), phoneAuthOptions.zzd());
            zzw zzw = (zzw) phoneAuthOptions.zzg();
            if (zzw.zzc()) {
                zza2.zze.zza(zzw, phoneAuthOptions.zzb(), zza2.zzi, longValue, phoneAuthOptions.zzf() != null, phoneAuthOptions.zzi(), zza3, phoneAuthOptions.zze(), phoneAuthOptions.zzj());
            } else {
                zza2.zze.zza(zzw, phoneAuthOptions.zzk(), zza2.zzi, longValue, phoneAuthOptions.zzf() != null, phoneAuthOptions.zzi(), zza3, phoneAuthOptions.zze(), phoneAuthOptions.zzj());
            }
        } else {
            phoneAuthOptions.zza().zza(phoneAuthOptions.zzb(), phoneAuthOptions.zzc().longValue(), TimeUnit.SECONDS, phoneAuthOptions.zzd(), phoneAuthOptions.zzj(), phoneAuthOptions.zze(), phoneAuthOptions.zzf() != null, phoneAuthOptions.zzh());
        }
    }

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks zza(String str, PhoneAuthProvider.OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks) {
        if (!this.zzg.zzc() || !str.equals(this.zzg.zza())) {
            return onVerificationStateChangedCallbacks;
        }
        return new zzl(this, onVerificationStateChangedCallbacks);
    }

    /* JADX WARNING: type inference failed for: r0v7, types: [com.google.firebase.auth.internal.zzbc, com.google.firebase.auth.FirebaseAuth$zzc] */
    public Task<Void> updateCurrentUser(FirebaseUser firebaseUser) {
        String str;
        if (firebaseUser == null) {
            throw new IllegalArgumentException("Cannot update current user with null user!");
        } else if ((firebaseUser.zzd() != null && !firebaseUser.zzd().equals(this.zzk)) || ((str = this.zzk) != null && !str.equals(firebaseUser.zzd()))) {
            return Tasks.forException(zzej.zza(new Status(17072)));
        } else {
            String apiKey = firebaseUser.zzc().getOptions().getApiKey();
            String apiKey2 = this.zza.getOptions().getApiKey();
            if (!firebaseUser.zze().zzb() || !apiKey2.equals(apiKey)) {
                return zza(firebaseUser, (zzbc) new zzc(this));
            }
            zza(zzp.zza(this.zza, firebaseUser), firebaseUser.zze(), true);
            return Tasks.forResult(null);
        }
    }

    /* JADX WARNING: type inference failed for: r0v0, types: [com.google.firebase.auth.internal.zzbc, com.google.firebase.auth.FirebaseAuth$zzb] */
    public final Task<Void> zza(FirebaseUser firebaseUser) {
        return zza(firebaseUser, (zzbc) new zzb());
    }

    private final Task<Void> zza(FirebaseUser firebaseUser, zzbc zzbc) {
        Preconditions.checkNotNull(firebaseUser);
        return this.zze.zza(this.zza, firebaseUser, zzbc);
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.google.firebase.auth.internal.zzbc, com.google.firebase.auth.FirebaseAuth$zzb] */
    public final Task<AuthResult> zzc(FirebaseUser firebaseUser, AuthCredential authCredential) {
        Preconditions.checkNotNull(authCredential);
        Preconditions.checkNotNull(firebaseUser);
        return this.zze.zza(this.zza, firebaseUser, authCredential.zza(), (zzbc) new zzb());
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.google.firebase.auth.internal.zzbc, com.google.firebase.auth.FirebaseAuth$zzb] */
    public final Task<AuthResult> zza(FirebaseUser firebaseUser, String str) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(firebaseUser);
        return this.zze.zzd(this.zza, firebaseUser, str, new zzb());
    }

    public Task<AuthResult> createUserWithEmailAndPassword(String str, String str2) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        return this.zze.zza(this.zza, str, str2, this.zzk, (com.google.firebase.auth.internal.zzb) new zza());
    }

    public Task<SignInMethodQueryResult> fetchSignInMethodsForEmail(String str) {
        Preconditions.checkNotEmpty(str);
        return this.zze.zza(this.zza, str, this.zzk);
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.google.firebase.auth.internal.zzbc, com.google.firebase.auth.FirebaseAuth$zzb] */
    public final Task<Void> zza(FirebaseUser firebaseUser, UserProfileChangeRequest userProfileChangeRequest) {
        Preconditions.checkNotNull(firebaseUser);
        Preconditions.checkNotNull(userProfileChangeRequest);
        return this.zze.zza(this.zza, firebaseUser, userProfileChangeRequest, (zzbc) new zzb());
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.google.firebase.auth.internal.zzbc, com.google.firebase.auth.FirebaseAuth$zzb] */
    public final Task<Void> zzb(FirebaseUser firebaseUser, String str) {
        Preconditions.checkNotNull(firebaseUser);
        Preconditions.checkNotEmpty(str);
        return this.zze.zzb(this.zza, firebaseUser, str, (zzbc) new zzb());
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.google.firebase.auth.internal.zzbc, com.google.firebase.auth.FirebaseAuth$zzb] */
    public final Task<Void> zza(FirebaseUser firebaseUser, PhoneAuthCredential phoneAuthCredential) {
        Preconditions.checkNotNull(firebaseUser);
        Preconditions.checkNotNull(phoneAuthCredential);
        return this.zze.zza(this.zza, firebaseUser, (PhoneAuthCredential) phoneAuthCredential.zza(), (zzbc) new zzb());
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.google.firebase.auth.internal.zzbc, com.google.firebase.auth.FirebaseAuth$zzb] */
    public final Task<Void> zzc(FirebaseUser firebaseUser, String str) {
        Preconditions.checkNotNull(firebaseUser);
        Preconditions.checkNotEmpty(str);
        return this.zze.zzc(this.zza, firebaseUser, str, new zzb());
    }

    public Task<Void> sendPasswordResetEmail(String str) {
        Preconditions.checkNotEmpty(str);
        return sendPasswordResetEmail(str, (ActionCodeSettings) null);
    }

    public Task<Void> sendPasswordResetEmail(String str, ActionCodeSettings actionCodeSettings) {
        Preconditions.checkNotEmpty(str);
        if (actionCodeSettings == null) {
            actionCodeSettings = ActionCodeSettings.zza();
        }
        String str2 = this.zzi;
        if (str2 != null) {
            actionCodeSettings.zza(str2);
        }
        actionCodeSettings.zza(zzgm.PASSWORD_RESET);
        return this.zze.zza(this.zza, str, actionCodeSettings, this.zzk);
    }

    public Task<Void> sendSignInLinkToEmail(String str, ActionCodeSettings actionCodeSettings) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(actionCodeSettings);
        if (actionCodeSettings.canHandleCodeInApp()) {
            String str2 = this.zzi;
            if (str2 != null) {
                actionCodeSettings.zza(str2);
            }
            return this.zze.zzb(this.zza, str, actionCodeSettings, this.zzk);
        }
        throw new IllegalArgumentException("You must set canHandleCodeInApp in your ActionCodeSettings to true for Email-Link Sign-in.");
    }

    public boolean isSignInWithEmailLink(String str) {
        return EmailAuthCredential.zza(str);
    }

    public final Task<Void> zza(ActionCodeSettings actionCodeSettings, String str) {
        Preconditions.checkNotEmpty(str);
        if (this.zzi != null) {
            if (actionCodeSettings == null) {
                actionCodeSettings = ActionCodeSettings.zza();
            }
            actionCodeSettings.zza(this.zzi);
        }
        return this.zze.zza(this.zza, actionCodeSettings, str);
    }

    public Task<ActionCodeResult> checkActionCode(String str) {
        Preconditions.checkNotEmpty(str);
        return this.zze.zzb(this.zza, str, this.zzk);
    }

    public Task<Void> applyActionCode(String str) {
        Preconditions.checkNotEmpty(str);
        return this.zze.zzc(this.zza, str, this.zzk);
    }

    public Task<String> verifyPasswordResetCode(String str) {
        Preconditions.checkNotEmpty(str);
        return this.zze.zzd(this.zza, str, this.zzk);
    }

    public Task<Void> confirmPasswordReset(String str, String str2) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        return this.zze.zza(this.zza, str, str2, this.zzk);
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.google.firebase.auth.internal.zzbc, com.google.firebase.auth.FirebaseAuth$zzb] */
    public final Task<Void> zzd(FirebaseUser firebaseUser, String str) {
        Preconditions.checkNotNull(firebaseUser);
        Preconditions.checkNotEmpty(str);
        return this.zze.zze(this.zza, firebaseUser, str, new zzb()).continueWithTask(new zzo(this));
    }

    public final Task<AuthResult> zza(MultiFactorAssertion multiFactorAssertion, zzw zzw, FirebaseUser firebaseUser) {
        Preconditions.checkNotNull(multiFactorAssertion);
        Preconditions.checkNotNull(zzw);
        return this.zze.zza(this.zza, firebaseUser, (PhoneMultiFactorAssertion) multiFactorAssertion, zzw.zzb(), (com.google.firebase.auth.internal.zzb) new zza());
    }

    public Task<AuthResult> startActivityForSignInWithProvider(Activity activity, FederatedAuthProvider federatedAuthProvider) {
        Preconditions.checkNotNull(federatedAuthProvider);
        Preconditions.checkNotNull(activity);
        if (!zzec.zza()) {
            return Tasks.forException(zzej.zza(new Status(17063)));
        }
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        if (!this.zzm.zza(activity, taskCompletionSource, this)) {
            return Tasks.forException(zzej.zza(new Status(17057)));
        }
        zzaw.zza(activity.getApplicationContext(), this);
        federatedAuthProvider.zza(activity);
        return taskCompletionSource.getTask();
    }

    public final Task<AuthResult> zza(Activity activity, FederatedAuthProvider federatedAuthProvider, FirebaseUser firebaseUser) {
        Preconditions.checkNotNull(activity);
        Preconditions.checkNotNull(federatedAuthProvider);
        Preconditions.checkNotNull(firebaseUser);
        if (!zzec.zza()) {
            return Tasks.forException(zzej.zza(new Status(17063)));
        }
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        if (!this.zzm.zza(activity, (TaskCompletionSource<AuthResult>) taskCompletionSource, this, firebaseUser)) {
            return Tasks.forException(zzej.zza(new Status(17057)));
        }
        zzaw.zza(activity.getApplicationContext(), this, firebaseUser);
        federatedAuthProvider.zzb(activity);
        return taskCompletionSource.getTask();
    }

    public final Task<AuthResult> zzb(Activity activity, FederatedAuthProvider federatedAuthProvider, FirebaseUser firebaseUser) {
        Preconditions.checkNotNull(activity);
        Preconditions.checkNotNull(federatedAuthProvider);
        Preconditions.checkNotNull(firebaseUser);
        if (!zzec.zza()) {
            return Tasks.forException(zzej.zza(new Status(17063)));
        }
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        if (!this.zzm.zza(activity, (TaskCompletionSource<AuthResult>) taskCompletionSource, this, firebaseUser)) {
            return Tasks.forException(zzej.zza(new Status(17057)));
        }
        zzaw.zza(activity.getApplicationContext(), this, firebaseUser);
        federatedAuthProvider.zzc(activity);
        return taskCompletionSource.getTask();
    }

    public Task<AuthResult> getPendingAuthResult() {
        return this.zzm.zzb();
    }

    public final Task<Void> zzb(FirebaseUser firebaseUser) {
        Preconditions.checkNotNull(firebaseUser);
        return this.zze.zza(firebaseUser, (zzaf) new zzn(this, firebaseUser));
    }

    public final Task<Void> zza(String str, String str2, ActionCodeSettings actionCodeSettings) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        if (actionCodeSettings == null) {
            actionCodeSettings = ActionCodeSettings.zza();
        }
        String str3 = this.zzi;
        if (str3 != null) {
            actionCodeSettings.zza(str3);
        }
        return this.zze.zza(str, str2, actionCodeSettings);
    }

    public final Task<Void> zza(FirebaseUser firebaseUser, MultiFactorAssertion multiFactorAssertion, String str) {
        Preconditions.checkNotNull(firebaseUser);
        Preconditions.checkNotNull(multiFactorAssertion);
        if (!(multiFactorAssertion instanceof PhoneMultiFactorAssertion)) {
            return Tasks.forException(zzej.zza(new Status(FirebaseError.ERROR_INTERNAL_ERROR)));
        }
        return this.zze.zza(this.zza, (PhoneMultiFactorAssertion) multiFactorAssertion, firebaseUser, str, (com.google.firebase.auth.internal.zzb) new zza());
    }

    public void signOut() {
        zza();
        zzax zzax = this.zzn;
        if (zzax != null) {
            zzax.zza();
        }
    }

    public void setLanguageCode(String str) {
        Preconditions.checkNotEmpty(str);
        synchronized (this.zzh) {
            this.zzi = str;
        }
    }

    public String getLanguageCode() {
        String str;
        synchronized (this.zzh) {
            str = this.zzi;
        }
        return str;
    }

    public final void zza(String str) {
        Preconditions.checkNotEmpty(str);
        synchronized (this.zzj) {
            this.zzk = str;
        }
    }

    public final String zzc() {
        String str;
        synchronized (this.zzj) {
            str = this.zzk;
        }
        return str;
    }

    public void useAppLanguage() {
        synchronized (this.zzh) {
            this.zzi = zzfe.zza();
        }
    }

    public FirebaseAuthSettings getFirebaseAuthSettings() {
        return this.zzg;
    }

    public Task<Void> setFirebaseUIVersion(String str) {
        return this.zze.zza(str);
    }

    private final boolean zzb(String str) {
        ActionCodeUrl parseLink = ActionCodeUrl.parseLink(str);
        return parseLink != null && !TextUtils.equals(this.zzk, parseLink.zza());
    }
}
