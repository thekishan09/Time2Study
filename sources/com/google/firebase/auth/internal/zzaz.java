package com.google.firebase.auth.internal;

import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.internal.firebase_auth.zzj;
import java.util.concurrent.Executor;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzaz implements Executor {
    private static zzaz zza = new zzaz();
    private Handler zzb = new zzj(Looper.getMainLooper());

    private zzaz() {
    }

    public final void execute(Runnable runnable) {
        this.zzb.post(runnable);
    }

    public static zzaz zza() {
        return zza;
    }
}
