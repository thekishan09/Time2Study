package com.google.android.gms.internal.firebase_auth;

import java.util.ListIterator;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzld implements ListIterator<String> {
    private ListIterator<String> zza = this.zzc.zza.listIterator(this.zzb);
    private final /* synthetic */ int zzb;
    private final /* synthetic */ zzle zzc;

    zzld(zzle zzle, int i) {
        this.zzc = zzle;
        this.zzb = i;
    }

    public final boolean hasNext() {
        return this.zza.hasNext();
    }

    public final boolean hasPrevious() {
        return this.zza.hasPrevious();
    }

    public final int nextIndex() {
        return this.zza.nextIndex();
    }

    public final int previousIndex() {
        return this.zza.previousIndex();
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }

    public final /* synthetic */ void add(Object obj) {
        String str = (String) obj;
        throw new UnsupportedOperationException();
    }

    public final /* synthetic */ void set(Object obj) {
        String str = (String) obj;
        throw new UnsupportedOperationException();
    }

    public final /* synthetic */ Object previous() {
        return this.zza.previous();
    }

    public final /* synthetic */ Object next() {
        return this.zza.next();
    }
}
