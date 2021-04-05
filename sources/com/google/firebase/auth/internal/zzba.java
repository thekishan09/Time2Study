package com.google.firebase.auth.internal;

import com.google.android.gms.common.api.internal.BackgroundDetector;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzba implements BackgroundDetector.BackgroundStateChangeListener {
    private final /* synthetic */ zzax zza;

    zzba(zzax zzax) {
        this.zza = zzax;
    }

    public final void onBackgroundStateChanged(boolean z) {
        if (z) {
            boolean unused = this.zza.zzc = true;
            this.zza.zza();
            return;
        }
        boolean unused2 = this.zza.zzc = false;
        if (this.zza.zzb()) {
            this.zza.zzb.zza();
        }
    }
}
