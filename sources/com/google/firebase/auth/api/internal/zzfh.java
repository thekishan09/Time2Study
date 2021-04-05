package com.google.firebase.auth.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzek;
import com.google.android.gms.internal.firebase_auth.zzem;
import com.google.android.gms.internal.firebase_auth.zzeq;
import com.google.android.gms.internal.firebase_auth.zzfa;
import com.google.android.gms.internal.firebase_auth.zzff;
import com.google.android.gms.internal.firebase_auth.zzfq;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.internal.zzy;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzfh extends zzep {
    final /* synthetic */ zzff zza;

    zzfh(zzff zzff) {
        this.zza = zzff;
    }

    public final void zza(zzff zzff) throws RemoteException {
        boolean z = true;
        if (this.zza.zzb != 1) {
            z = false;
        }
        int i = this.zza.zzb;
        StringBuilder sb = new StringBuilder(37);
        sb.append("Unexpected response type: ");
        sb.append(i);
        Preconditions.checkState(z, sb.toString());
        this.zza.zzk = zzff;
        this.zza.zzf();
    }

    public final void zza(zzff zzff, zzfa zzfa) throws RemoteException {
        boolean z = this.zza.zzb == 2;
        int i = this.zza.zzb;
        StringBuilder sb = new StringBuilder(37);
        sb.append("Unexpected response type: ");
        sb.append(i);
        Preconditions.checkState(z, sb.toString());
        this.zza.zzk = zzff;
        this.zza.zzl = zzfa;
        this.zza.zzf();
    }

    public final void zza(zzeq zzeq) throws RemoteException {
        boolean z = this.zza.zzb == 3;
        int i = this.zza.zzb;
        StringBuilder sb = new StringBuilder(36);
        sb.append("Unexpected response type ");
        sb.append(i);
        Preconditions.checkState(z, sb.toString());
        this.zza.zzm = zzeq;
        this.zza.zzf();
    }

    public final void zza(zzfq zzfq) throws RemoteException {
        boolean z = this.zza.zzb == 4;
        int i = this.zza.zzb;
        StringBuilder sb = new StringBuilder(36);
        sb.append("Unexpected response type ");
        sb.append(i);
        Preconditions.checkState(z, sb.toString());
        this.zza.zzn = zzfq;
        this.zza.zzf();
    }

    /* renamed from: a_ */
    public final void mo23337a_() throws RemoteException {
        boolean z = this.zza.zzb == 5;
        int i = this.zza.zzb;
        StringBuilder sb = new StringBuilder(36);
        sb.append("Unexpected response type ");
        sb.append(i);
        Preconditions.checkState(z, sb.toString());
        this.zza.zzf();
    }

    public final void zzb() throws RemoteException {
        boolean z = this.zza.zzb == 6;
        int i = this.zza.zzb;
        StringBuilder sb = new StringBuilder(36);
        sb.append("Unexpected response type ");
        sb.append(i);
        Preconditions.checkState(z, sb.toString());
        this.zza.zzf();
    }

    public final void zza(String str) throws RemoteException {
        boolean z = this.zza.zzb == 7;
        int i = this.zza.zzb;
        StringBuilder sb = new StringBuilder(36);
        sb.append("Unexpected response type ");
        sb.append(i);
        Preconditions.checkState(z, sb.toString());
        this.zza.zzo = str;
        this.zza.zzf();
    }

    public final void zzb(String str) throws RemoteException {
        boolean z = this.zza.zzb == 8;
        int i = this.zza.zzb;
        StringBuilder sb = new StringBuilder(36);
        sb.append("Unexpected response type ");
        sb.append(i);
        Preconditions.checkState(z, sb.toString());
        this.zza.zzp = str;
        zza((zzfn) new zzfg(this, str));
    }

    public final void zza(PhoneAuthCredential phoneAuthCredential) throws RemoteException {
        boolean z = this.zza.zzb == 8;
        int i = this.zza.zzb;
        StringBuilder sb = new StringBuilder(36);
        sb.append("Unexpected response type ");
        sb.append(i);
        Preconditions.checkState(z, sb.toString());
        boolean unused = this.zza.zza = true;
        this.zza.zzw = true;
        zza((zzfn) new zzfj(this, phoneAuthCredential));
    }

    public final void zzc(String str) throws RemoteException {
        boolean z = this.zza.zzb == 8;
        int i = this.zza.zzb;
        StringBuilder sb = new StringBuilder(36);
        sb.append("Unexpected response type ");
        sb.append(i);
        Preconditions.checkState(z, sb.toString());
        this.zza.zzp = str;
        boolean unused = this.zza.zza = true;
        this.zza.zzw = true;
        zza((zzfn) new zzfi(this, str));
    }

    public final void zza(Status status) throws RemoteException {
        String statusMessage = status.getStatusMessage();
        if (statusMessage != null) {
            if (statusMessage.contains("MISSING_MFA_PENDING_CREDENTIAL")) {
                status = new Status(17081);
            } else if (statusMessage.contains("MISSING_MFA_ENROLLMENT_ID")) {
                status = new Status(17082);
            } else if (statusMessage.contains("INVALID_MFA_PENDING_CREDENTIAL")) {
                status = new Status(17083);
            } else if (statusMessage.contains("MFA_ENROLLMENT_NOT_FOUND")) {
                status = new Status(17084);
            } else if (statusMessage.contains("ADMIN_ONLY_OPERATION")) {
                status = new Status(17085);
            } else if (statusMessage.contains("UNVERIFIED_EMAIL")) {
                status = new Status(17086);
            } else if (statusMessage.contains("SECOND_FACTOR_EXISTS")) {
                status = new Status(17087);
            } else if (statusMessage.contains("SECOND_FACTOR_LIMIT_EXCEEDED")) {
                status = new Status(17088);
            } else if (statusMessage.contains("UNSUPPORTED_FIRST_FACTOR")) {
                status = new Status(17089);
            } else if (statusMessage.contains("EMAIL_CHANGE_NEEDS_VERIFICATION")) {
                status = new Status(17090);
            }
        }
        if (this.zza.zzb == 8) {
            boolean unused = this.zza.zza = true;
            this.zza.zzw = false;
            zza((zzfn) new zzfl(this, status));
            return;
        }
        this.zza.zzb(status);
        this.zza.zza(status);
    }

    public final void zza(Status status, PhoneAuthCredential phoneAuthCredential) throws RemoteException {
        boolean z = this.zza.zzb == 2;
        int i = this.zza.zzb;
        StringBuilder sb = new StringBuilder(36);
        sb.append("Unexpected response type ");
        sb.append(i);
        Preconditions.checkState(z, sb.toString());
        zza(status, phoneAuthCredential, (String) null, (String) null);
    }

    public final void zza(zzek zzek) {
        zza(zzek.zza(), zzek.zzb(), zzek.zzc(), zzek.zzd());
    }

    public final void zza(zzem zzem) {
        this.zza.zzt = zzem;
        this.zza.zza(zzy.zza("REQUIRES_SECOND_FACTOR_AUTH"));
    }

    private final void zza(Status status, AuthCredential authCredential, String str, String str2) {
        this.zza.zzb(status);
        this.zza.zzq = authCredential;
        this.zza.zzr = str;
        this.zza.zzs = str2;
        if (this.zza.zzg != null) {
            this.zza.zzg.zza(status);
        }
        this.zza.zza(status);
    }

    public final void zzc() throws RemoteException {
        boolean z = this.zza.zzb == 9;
        int i = this.zza.zzb;
        StringBuilder sb = new StringBuilder(36);
        sb.append("Unexpected response type ");
        sb.append(i);
        Preconditions.checkState(z, sb.toString());
        this.zza.zzf();
    }

    private final void zza(zzfn zzfn) {
        this.zza.zzj.execute(new zzfk(this, zzfn));
    }
}
