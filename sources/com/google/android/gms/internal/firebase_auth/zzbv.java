package com.google.android.gms.internal.firebase_auth;

import java.util.Iterator;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzbv {
    static int zza(Set<?> set) {
        Iterator<?> it = set.iterator();
        int i = 0;
        while (it.hasNext()) {
            Object next = it.next();
            i = ((i + (next != null ? next.hashCode() : 0)) ^ -1) ^ -1;
        }
        return i;
    }

    static boolean zza(Set<?> set, @NullableDecl Object obj) {
        if (set == obj) {
            return true;
        }
        if (!(obj instanceof Set)) {
            return false;
        }
        Set set2 = (Set) obj;
        try {
            if (set.size() != set2.size() || !set.containsAll(set2)) {
                return false;
            }
            return true;
        } catch (ClassCastException | NullPointerException e) {
            return false;
        }
    }
}
