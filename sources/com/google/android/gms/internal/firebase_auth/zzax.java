package com.google.android.gms.internal.firebase_auth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzax {
    /* access modifiers changed from: private */
    public final zzaf zza;
    private final boolean zzb;
    private final zzbd zzc;
    /* access modifiers changed from: private */
    public final int zzd;

    private zzax(zzbd zzbd) {
        this(zzbd, false, zzaj.zza, Integer.MAX_VALUE);
    }

    private zzax(zzbd zzbd, boolean z, zzaf zzaf, int i) {
        this.zzc = zzbd;
        this.zzb = false;
        this.zza = zzaf;
        this.zzd = Integer.MAX_VALUE;
    }

    public static zzax zza(char c) {
        zzah zzah = new zzah('.');
        zzav.zza(zzah);
        return new zzax(new zzaw(zzah));
    }

    public static zzax zza(String str) {
        zzao zzb2 = zzau.zzb(str);
        if (!zzb2.zza("").zza()) {
            return new zzax(new zzay(zzb2));
        }
        throw new IllegalArgumentException(zzbc.zza("The pattern may not match the empty string: %s", zzb2));
    }

    public final List<String> zza(CharSequence charSequence) {
        zzav.zza(charSequence);
        Iterator<String> zza2 = this.zzc.zza(this, charSequence);
        ArrayList arrayList = new ArrayList();
        while (zza2.hasNext()) {
            arrayList.add(zza2.next());
        }
        return Collections.unmodifiableList(arrayList);
    }
}
