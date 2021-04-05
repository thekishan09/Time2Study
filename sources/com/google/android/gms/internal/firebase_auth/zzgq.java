package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzgn;
import com.google.android.gms.internal.firebase_auth.zzgq;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public abstract class zzgq<MessageType extends zzgn<MessageType, BuilderType>, BuilderType extends zzgq<MessageType, BuilderType>> implements zzjq {
    /* renamed from: zza */
    public abstract BuilderType clone();

    /* access modifiers changed from: protected */
    public abstract BuilderType zza(MessageType messagetype);

    public final /* synthetic */ zzjq zza(zzjr zzjr) {
        if (zzag().getClass().isInstance(zzjr)) {
            return zza((zzgn) zzjr);
        }
        throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
    }
}
