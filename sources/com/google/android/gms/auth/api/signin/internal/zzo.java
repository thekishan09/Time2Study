package com.google.android.gms.auth.api.signin.internal;

import android.content.Context;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

/* compiled from: com.google.android.gms:play-services-auth@@18.1.0 */
public final class zzo {
    private static zzo zzcl = null;
    private Storage zzcm;
    private GoogleSignInAccount zzcn;
    private GoogleSignInOptions zzco = this.zzcm.getSavedDefaultGoogleSignInOptions();

    private zzo(Context context) {
        Storage instance = Storage.getInstance(context);
        this.zzcm = instance;
        this.zzcn = instance.getSavedDefaultGoogleSignInAccount();
    }

    public static synchronized zzo zzd(Context context) {
        zzo zze;
        synchronized (zzo.class) {
            zze = zze(context.getApplicationContext());
        }
        return zze;
    }

    private static synchronized zzo zze(Context context) {
        synchronized (zzo.class) {
            if (zzcl != null) {
                zzo zzo = zzcl;
                return zzo;
            }
            zzo zzo2 = new zzo(context);
            zzcl = zzo2;
            return zzo2;
        }
    }

    public final synchronized void clear() {
        this.zzcm.clear();
        this.zzcn = null;
        this.zzco = null;
    }

    public final synchronized void zzc(GoogleSignInOptions googleSignInOptions, GoogleSignInAccount googleSignInAccount) {
        this.zzcm.saveDefaultGoogleSignInAccount(googleSignInAccount, googleSignInOptions);
        this.zzcn = googleSignInAccount;
        this.zzco = googleSignInOptions;
    }

    public final synchronized GoogleSignInAccount zzl() {
        return this.zzcn;
    }

    public final synchronized GoogleSignInOptions zzm() {
        return this.zzco;
    }
}
