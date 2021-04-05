package com.google.firebase.auth.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.logging.Logger;
import com.google.android.gms.internal.firebase_auth.zzek;
import com.google.android.gms.internal.firebase_auth.zzem;
import com.google.android.gms.internal.firebase_auth.zzeq;
import com.google.android.gms.internal.firebase_auth.zzfa;
import com.google.android.gms.internal.firebase_auth.zzff;
import com.google.android.gms.internal.firebase_auth.zzfq;
import com.google.firebase.auth.PhoneAuthCredential;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public class zzee {
    private final zzem zza;
    private final Logger zzb;

    public zzee(zzem zzem, Logger logger) {
        this.zza = (zzem) Preconditions.checkNotNull(zzem);
        this.zzb = (Logger) Preconditions.checkNotNull(logger);
    }

    public zzee(zzee zzee) {
        this(zzee.zza, zzee.zzb);
    }

    public final void zza(zzff zzff) {
        try {
            this.zza.zza(zzff);
        } catch (RemoteException e) {
            this.zzb.mo11928e("RemoteException when sending token result.", e, new Object[0]);
        }
    }

    public final void zza(zzff zzff, zzfa zzfa) {
        try {
            this.zza.zza(zzff, zzfa);
        } catch (RemoteException e) {
            this.zzb.mo11928e("RemoteException when sending get token and account info user response", e, new Object[0]);
        }
    }

    public final void zza(zzeq zzeq) {
        try {
            this.zza.zza(zzeq);
        } catch (RemoteException e) {
            this.zzb.mo11928e("RemoteException when sending create auth uri response.", e, new Object[0]);
        }
    }

    public final void zza(zzfq zzfq) {
        try {
            this.zza.zza(zzfq);
        } catch (RemoteException e) {
            this.zzb.mo11928e("RemoteException when sending password reset response.", e, new Object[0]);
        }
    }

    public final void zza() {
        try {
            this.zza.mo23337a_();
        } catch (RemoteException e) {
            this.zzb.mo11928e("RemoteException when sending delete account response.", e, new Object[0]);
        }
    }

    public final void zzb() {
        try {
            this.zza.zzb();
        } catch (RemoteException e) {
            this.zzb.mo11928e("RemoteException when sending email verification response.", e, new Object[0]);
        }
    }

    public final void zza(String str) {
        try {
            this.zza.zza(str);
        } catch (RemoteException e) {
            this.zzb.mo11928e("RemoteException when sending set account info response.", e, new Object[0]);
        }
    }

    public void zzb(String str) {
        try {
            this.zza.zzb(str);
        } catch (RemoteException e) {
            this.zzb.mo11928e("RemoteException when sending send verification code response.", e, new Object[0]);
        }
    }

    public final void zza(PhoneAuthCredential phoneAuthCredential) {
        try {
            this.zza.zza(phoneAuthCredential);
        } catch (RemoteException e) {
            this.zzb.mo11928e("RemoteException when sending verification completed response.", e, new Object[0]);
        }
    }

    public void zza(Status status) {
        try {
            this.zza.zza(status);
        } catch (RemoteException e) {
            this.zzb.mo11928e("RemoteException when sending failure result.", e, new Object[0]);
        }
    }

    public final void zza(Status status, PhoneAuthCredential phoneAuthCredential) {
        try {
            this.zza.zza(status, phoneAuthCredential);
        } catch (RemoteException e) {
            this.zzb.mo11928e("RemoteException when sending failure result.", e, new Object[0]);
        }
    }

    public final void zzc() {
        try {
            this.zza.zzc();
        } catch (RemoteException e) {
            this.zzb.mo11928e("RemoteException when setting FirebaseUI Version", e, new Object[0]);
        }
    }

    public final void zza(zzek zzek) {
        try {
            this.zza.zza(zzek);
        } catch (RemoteException e) {
            this.zzb.mo11928e("RemoteException when sending failure result with credential", e, new Object[0]);
        }
    }

    public final void zza(zzem zzem) {
        try {
            this.zza.zza(zzem);
        } catch (RemoteException e) {
            this.zzb.mo11928e("RemoteException when sending failure result for mfa", e, new Object[0]);
        }
    }
}
