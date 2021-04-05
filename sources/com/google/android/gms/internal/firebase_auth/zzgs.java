package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzjr;
import java.io.InputStream;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public abstract class zzgs<MessageType extends zzjr> implements zzkb<MessageType> {
    private static final zzht zza = zzht.zza();

    private final MessageType zzb(InputStream inputStream, zzht zzht) throws zzir {
        zzhh zzhh;
        if (inputStream == null) {
            byte[] bArr = zzii.zzb;
            zzhh = zzhh.zza(bArr, 0, bArr.length, false);
        } else {
            zzhh = new zzhm(inputStream);
        }
        MessageType messagetype = (zzjr) zza(zzhh, zzht);
        try {
            zzhh.zza(0);
            return messagetype;
        } catch (zzir e) {
            throw e.zza(messagetype);
        }
    }

    public final /* synthetic */ Object zza(InputStream inputStream, zzht zzht) throws zzir {
        zzla zzla;
        zzjr zzb = zzb(inputStream, zzht);
        if (zzb == null || zzb.zzaa()) {
            return zzb;
        }
        if (zzb instanceof zzgn) {
            zzla = new zzla((zzgn) zzb);
        } else if (zzb instanceof zzgp) {
            zzla = new zzla((zzgp) zzb);
        } else {
            zzla = new zzla(zzb);
        }
        throw new zzir(zzla.getMessage()).zza(zzb);
    }
}
