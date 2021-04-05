package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzgn;
import com.google.android.gms.internal.firebase_auth.zzgq;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public abstract class zzgn<MessageType extends zzgn<MessageType, BuilderType>, BuilderType extends zzgq<MessageType, BuilderType>> implements zzjr {
    protected int zza = 0;

    public final zzgv zzw() {
        try {
            zzhd zzc = zzgv.zzc(zzab());
            zza(zzc.zzb());
            return zzc.zza();
        } catch (IOException e) {
            String name = getClass().getName();
            StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 62 + String.valueOf("ByteString").length());
            sb.append("Serializing ");
            sb.append(name);
            sb.append(" to a ");
            sb.append("ByteString");
            sb.append(" threw an IOException (should never happen).");
            throw new RuntimeException(sb.toString(), e);
        }
    }

    public final byte[] zzx() {
        try {
            byte[] bArr = new byte[zzab()];
            zzhq zza2 = zzhq.zza(bArr);
            zza(zza2);
            zza2.zzb();
            return bArr;
        } catch (IOException e) {
            String name = getClass().getName();
            StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 62 + String.valueOf("byte array").length());
            sb.append("Serializing ");
            sb.append(name);
            sb.append(" to a ");
            sb.append("byte array");
            sb.append(" threw an IOException (should never happen).");
            throw new RuntimeException(sb.toString(), e);
        }
    }

    /* access modifiers changed from: package-private */
    public int zzy() {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: package-private */
    public void zzb(int i) {
        throw new UnsupportedOperationException();
    }

    protected static <T> void zza(Iterable<T> iterable, List<? super T> list) {
        zzii.zza(iterable);
        if (iterable instanceof zziy) {
            List<?> zzb = ((zziy) iterable).zzb();
            zziy zziy = (zziy) list;
            int size = list.size();
            for (Object next : zzb) {
                if (next == null) {
                    StringBuilder sb = new StringBuilder(37);
                    sb.append("Element at index ");
                    sb.append(zziy.size() - size);
                    sb.append(" is null.");
                    String sb2 = sb.toString();
                    for (int size2 = zziy.size() - 1; size2 >= size; size2--) {
                        zziy.remove(size2);
                    }
                    throw new NullPointerException(sb2);
                } else if (next instanceof zzgv) {
                    zziy.zza((zzgv) next);
                } else {
                    zziy.add((String) next);
                }
            }
        } else if (iterable instanceof zzka) {
            list.addAll((Collection) iterable);
        } else {
            if ((list instanceof ArrayList) && (iterable instanceof Collection)) {
                ((ArrayList) list).ensureCapacity(list.size() + ((Collection) iterable).size());
            }
            int size3 = list.size();
            for (T next2 : iterable) {
                if (next2 == null) {
                    StringBuilder sb3 = new StringBuilder(37);
                    sb3.append("Element at index ");
                    sb3.append(list.size() - size3);
                    sb3.append(" is null.");
                    String sb4 = sb3.toString();
                    for (int size4 = list.size() - 1; size4 >= size3; size4--) {
                        list.remove(size4);
                    }
                    throw new NullPointerException(sb4);
                }
                list.add(next2);
            }
        }
    }
}
