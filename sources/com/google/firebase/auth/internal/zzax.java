package com.google.firebase.auth.internal;

import android.app.Application;
import android.content.Context;
import com.google.android.gms.common.api.internal.BackgroundDetector;
import com.google.android.gms.internal.firebase_auth.zzff;
import com.google.firebase.FirebaseApp;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzax {
    private volatile int zza;
    /* access modifiers changed from: private */
    public final zzaa zzb;
    /* access modifiers changed from: private */
    public volatile boolean zzc;

    public zzax(FirebaseApp firebaseApp) {
        this(firebaseApp.getApplicationContext(), new zzaa(firebaseApp));
    }

    private zzax(Context context, zzaa zzaa) {
        this.zzc = false;
        this.zza = 0;
        this.zzb = zzaa;
        BackgroundDetector.initialize((Application) context.getApplicationContext());
        BackgroundDetector.getInstance().addListener(new zzba(this));
    }

    public final void zza(int i) {
        if (i > 0 && this.zza == 0) {
            this.zza = i;
            if (zzb()) {
                this.zzb.zza();
            }
        } else if (i == 0 && this.zza != 0) {
            this.zzb.zzc();
        }
        this.zza = i;
    }

    public final void zza(zzff zzff) {
        if (zzff != null) {
            long zze = zzff.zze();
            if (zze <= 0) {
                zze = 3600;
            }
            zzaa zzaa = this.zzb;
            zzaa.zza = zzff.zzg() + (zze * 1000);
            zzaa.zzb = -1;
            if (zzb()) {
                this.zzb.zza();
            }
        }
    }

    public final void zza() {
        this.zzb.zzc();
    }

    /* access modifiers changed from: private */
    public final boolean zzb() {
        return this.zza > 0 && !this.zzc;
    }
}
