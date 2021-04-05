package com.google.android.gms.internal.firebase_auth;

import java.util.Comparator;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzgx implements Comparator<zzgv> {
    zzgx() {
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        zzgv zzgv = (zzgv) obj;
        zzgv zzgv2 = (zzgv) obj2;
        zzhe zzhe = (zzhe) zzgv.iterator();
        zzhe zzhe2 = (zzhe) zzgv2.iterator();
        while (zzhe.hasNext() && zzhe2.hasNext()) {
            int compare = Integer.compare(zzgv.zzb(zzhe.zza()), zzgv.zzb(zzhe2.zza()));
            if (compare != 0) {
                return compare;
            }
        }
        return Integer.compare(zzgv.zza(), zzgv2.zza());
    }
}
