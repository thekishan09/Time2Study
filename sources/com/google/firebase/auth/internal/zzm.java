package com.google.firebase.auth.internal;

import com.google.firebase.auth.FirebaseAuthSettings;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzm extends FirebaseAuthSettings {
    private String zza;
    private String zzb;

    public final String zza() {
        return this.zza;
    }

    public final String zzb() {
        return this.zzb;
    }

    public final boolean zzc() {
        return (this.zza == null || this.zzb == null) ? false : true;
    }

    public final void setAutoRetrievedSmsCodeForPhoneNumber(String str, String str2) {
        this.zza = str;
        this.zzb = str2;
    }
}
