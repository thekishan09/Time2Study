package com.google.android.gms.auth.api.signin.internal;

import android.os.RemoteException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.Status;

/* compiled from: com.google.android.gms:play-services-auth@@18.1.0 */
final class zzi extends zzd {
    private final /* synthetic */ zzj zzch;

    zzi(zzj zzj) {
        this.zzch = zzj;
    }

    public final void zzc(GoogleSignInAccount googleSignInAccount, Status status) throws RemoteException {
        if (googleSignInAccount != null) {
            zzo.zzd(this.zzch.val$context).zzc(this.zzch.zzci, googleSignInAccount);
        }
        this.zzch.setResult(new GoogleSignInResult(googleSignInAccount, status));
    }
}
