package com.google.firebase.auth.api.internal;

import android.app.Activity;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.LifecycleCallback;
import com.google.android.gms.common.api.internal.LifecycleFragment;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzem;
import com.google.android.gms.internal.firebase_auth.zzeq;
import com.google.android.gms.internal.firebase_auth.zzfa;
import com.google.android.gms.internal.firebase_auth.zzfq;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.internal.zzae;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
abstract class zzff<ResultT, CallbackT> implements zzar<zzeh, ResultT> {
    /* access modifiers changed from: private */
    public boolean zza;
    protected final int zzb;
    final zzfh zzc = new zzfh(this);
    protected FirebaseApp zzd;
    protected FirebaseUser zze;
    protected CallbackT zzf;
    protected zzae zzg;
    protected zzfd<ResultT> zzh;
    protected final List<PhoneAuthProvider.OnVerificationStateChangedCallbacks> zzi = new ArrayList();
    protected Executor zzj;
    protected com.google.android.gms.internal.firebase_auth.zzff zzk;
    protected zzfa zzl;
    protected zzeq zzm;
    protected zzfq zzn;
    protected String zzo;
    protected String zzp;
    protected AuthCredential zzq;
    protected String zzr;
    protected String zzs;
    protected zzem zzt;
    protected boolean zzu;
    protected boolean zzv;
    boolean zzw;
    private ResultT zzx;
    private Status zzy;

    public zzff(int i) {
        this.zzb = i;
    }

    public abstract void zze();

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    static class zza extends LifecycleCallback {
        private final List<PhoneAuthProvider.OnVerificationStateChangedCallbacks> zza;

        public static void zza(Activity activity, List<PhoneAuthProvider.OnVerificationStateChangedCallbacks> list) {
            LifecycleFragment fragment = getFragment(activity);
            if (((zza) fragment.getCallbackOrNull("PhoneAuthActivityStopCallback", zza.class)) == null) {
                new zza(fragment, list);
            }
        }

        private zza(LifecycleFragment lifecycleFragment, List<PhoneAuthProvider.OnVerificationStateChangedCallbacks> list) {
            super(lifecycleFragment);
            this.mLifecycleFragment.addCallback("PhoneAuthActivityStopCallback", this);
            this.zza = list;
        }

        public void onStop() {
            synchronized (this.zza) {
                this.zza.clear();
            }
        }
    }

    public final zzff<ResultT, CallbackT> zza(FirebaseApp firebaseApp) {
        this.zzd = (FirebaseApp) Preconditions.checkNotNull(firebaseApp, "firebaseApp cannot be null");
        return this;
    }

    public final zzff<ResultT, CallbackT> zza(FirebaseUser firebaseUser) {
        this.zze = (FirebaseUser) Preconditions.checkNotNull(firebaseUser, "firebaseUser cannot be null");
        return this;
    }

    public final zzff<ResultT, CallbackT> zza(CallbackT callbackt) {
        this.zzf = Preconditions.checkNotNull(callbackt, "external callback cannot be null");
        return this;
    }

    public final zzff<ResultT, CallbackT> zza(zzae zzae) {
        this.zzg = (zzae) Preconditions.checkNotNull(zzae, "external failure callback cannot be null");
        return this;
    }

    public final zzff<ResultT, CallbackT> zza(PhoneAuthProvider.OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks, Activity activity, Executor executor) {
        synchronized (this.zzi) {
            this.zzi.add((PhoneAuthProvider.OnVerificationStateChangedCallbacks) Preconditions.checkNotNull(onVerificationStateChangedCallbacks));
        }
        if (activity != null) {
            zza.zza(activity, this.zzi);
        }
        this.zzj = (Executor) Preconditions.checkNotNull(executor);
        return this;
    }

    public final zzar<zzeh, ResultT> zzc() {
        this.zzu = true;
        return this;
    }

    public final zzar<zzeh, ResultT> zzd() {
        this.zzv = true;
        return this;
    }

    public final void zzb(ResultT resultt) {
        this.zza = true;
        this.zzw = true;
        this.zzx = resultt;
        this.zzh.zza(resultt, (Status) null);
    }

    public final void zza(Status status) {
        this.zza = true;
        this.zzw = false;
        this.zzy = status;
        this.zzh.zza(null, status);
    }

    /* access modifiers changed from: private */
    public final void zzf() {
        zze();
        Preconditions.checkState(this.zza, "no success or failure set on method implementation");
    }

    /* access modifiers changed from: private */
    public final void zzb(Status status) {
        zzae zzae = this.zzg;
        if (zzae != null) {
            zzae.zza(status);
        }
    }
}
