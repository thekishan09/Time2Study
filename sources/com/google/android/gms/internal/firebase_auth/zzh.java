package com.google.android.gms.internal.firebase_auth;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzh implements zzg {
    private zzh() {
    }

    public final ExecutorService zza(int i, int i2) {
        int i3 = i;
        int i4 = i;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(i3, i4, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), Executors.defaultThreadFactory());
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        return Executors.unconfigurableExecutorService(threadPoolExecutor);
    }

    public final ExecutorService zza(int i) {
        return zza(1, i);
    }
}
