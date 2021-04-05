package com.google.firebase.auth.api.internal;

import android.text.TextUtils;
import com.google.android.gms.internal.firebase_auth.zzgk;
import com.google.firebase.auth.PhoneAuthCredential;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzft {
    public static zzgk zza(PhoneAuthCredential phoneAuthCredential) {
        if (!TextUtils.isEmpty(phoneAuthCredential.zze())) {
            return zzgk.zzb(phoneAuthCredential.zzc(), phoneAuthCredential.zze(), phoneAuthCredential.zzd());
        }
        return zzgk.zza(phoneAuthCredential.zzb(), phoneAuthCredential.getSmsCode(), phoneAuthCredential.zzd());
    }
}
