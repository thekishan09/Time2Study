package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.logging.Logger;
import com.google.android.gms.internal.firebase_auth.zzp;
import com.google.firebase.auth.ActionCodeUrl;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.api.internal.zzgb;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzer implements zzgb<zzp.zzd> {
    private static final Logger zza = new Logger("EmailLinkSignInRequest", new String[0]);
    private final String zzb;
    private final String zzc;
    private final String zzd;

    public zzer(EmailAuthCredential emailAuthCredential, String str) {
        this.zzb = Preconditions.checkNotEmpty(emailAuthCredential.zzb());
        this.zzc = Preconditions.checkNotEmpty(emailAuthCredential.zzd());
        this.zzd = str;
    }

    public final /* synthetic */ zzjr zza() {
        zzp.zzd.zza zzb2 = zzp.zzd.zza().zzb(this.zzb);
        ActionCodeUrl parseLink = ActionCodeUrl.parseLink(this.zzc);
        String str = null;
        String code = parseLink != null ? parseLink.getCode() : null;
        if (parseLink != null) {
            str = parseLink.zza();
        }
        if (code != null) {
            zzb2.zza(code);
        }
        if (str != null) {
            zzb2.zzd(str);
        }
        String str2 = this.zzd;
        if (str2 != null) {
            zzb2.zzc(str2);
        }
        return (zzp.zzd) ((zzig) zzb2.zzf());
    }
}
