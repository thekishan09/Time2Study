package com.google.firebase.auth.internal;

import android.os.Handler;
import android.os.HandlerThread;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.logging.Logger;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.internal.firebase_auth.zzj;
import com.google.firebase.FirebaseApp;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzaa {
    /* access modifiers changed from: private */
    public static Logger zzc = new Logger("TokenRefresher", "FirebaseAuth:");
    volatile long zza;
    volatile long zzb;
    private final FirebaseApp zzd;
    private long zze = 300000;
    private HandlerThread zzf;
    private Handler zzg = new zzj(this.zzf.getLooper());
    private Runnable zzh = new zzad(this, this.zzd.getName());

    public zzaa(FirebaseApp firebaseApp) {
        zzc.mo11935v("Initializing TokenRefresher", new Object[0]);
        this.zzd = (FirebaseApp) Preconditions.checkNotNull(firebaseApp);
        HandlerThread handlerThread = new HandlerThread("TokenRefresher", 10);
        this.zzf = handlerThread;
        handlerThread.start();
    }

    public final void zza() {
        Logger logger = zzc;
        long j = this.zza - this.zze;
        StringBuilder sb = new StringBuilder(43);
        sb.append("Scheduling refresh for ");
        sb.append(j);
        logger.mo11935v(sb.toString(), new Object[0]);
        zzc();
        this.zzb = Math.max((this.zza - DefaultClock.getInstance().currentTimeMillis()) - this.zze, 0) / 1000;
        this.zzg.postDelayed(this.zzh, this.zzb * 1000);
    }

    /* access modifiers changed from: package-private */
    public final void zzb() {
        long j;
        int i = (int) this.zzb;
        if (i == 30 || i == 60 || i == 120 || i == 240 || i == 480) {
            j = 2 * this.zzb;
        } else if (i != 960) {
            j = 30;
        } else {
            j = 960;
        }
        this.zzb = j;
        this.zza = DefaultClock.getInstance().currentTimeMillis() + (this.zzb * 1000);
        Logger logger = zzc;
        long j2 = this.zza;
        StringBuilder sb = new StringBuilder(43);
        sb.append("Scheduling refresh for ");
        sb.append(j2);
        logger.mo11935v(sb.toString(), new Object[0]);
        this.zzg.postDelayed(this.zzh, this.zzb * 1000);
    }

    public final void zzc() {
        this.zzg.removeCallbacks(this.zzh);
    }
}
