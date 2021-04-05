package com.google.android.gms.internal.firebase_auth;

import java.util.Iterator;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzlg implements Iterator<String> {
    private Iterator<String> zza = this.zzb.zza.iterator();
    private final /* synthetic */ zzle zzb;

    zzlg(zzle zzle) {
        this.zzb = zzle;
    }

    public final boolean hasNext() {
        return this.zza.hasNext();
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }

    public final /* synthetic */ Object next() {
        return this.zza.next();
    }
}
