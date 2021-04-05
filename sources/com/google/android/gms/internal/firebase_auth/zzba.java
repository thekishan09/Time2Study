package com.google.android.gms.internal.firebase_auth;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
abstract class zzba extends zzae<String> {
    final CharSequence zza;
    private final zzaf zzb;
    private final boolean zzc;
    private int zzd = 0;
    private int zze;

    protected zzba(zzax zzax, CharSequence charSequence) {
        this.zzb = zzax.zza;
        this.zzc = false;
        this.zze = zzax.zzd;
        this.zza = charSequence;
    }

    /* access modifiers changed from: package-private */
    public abstract int zza(int i);

    /* access modifiers changed from: package-private */
    public abstract int zzb(int i);

    /* access modifiers changed from: protected */
    public final /* synthetic */ Object zza() {
        int i;
        int i2 = this.zzd;
        while (true) {
            int i3 = this.zzd;
            if (i3 != -1) {
                int zza2 = zza(i3);
                if (zza2 == -1) {
                    zza2 = this.zza.length();
                    this.zzd = -1;
                } else {
                    this.zzd = zzb(zza2);
                }
                int i4 = this.zzd;
                if (i4 == i2) {
                    int i5 = i4 + 1;
                    this.zzd = i5;
                    if (i5 > this.zza.length()) {
                        this.zzd = -1;
                    }
                } else {
                    while (i2 < zza2 && this.zzb.zza(this.zza.charAt(i2))) {
                        i2++;
                    }
                    while (i > i2 && this.zzb.zza(this.zza.charAt(i - 1))) {
                        zza2 = i - 1;
                    }
                    if (!this.zzc || i2 != i) {
                        int i6 = this.zze;
                    } else {
                        i2 = this.zzd;
                    }
                }
            } else {
                zzb();
                return null;
            }
        }
        int i62 = this.zze;
        if (i62 == 1) {
            i = this.zza.length();
            this.zzd = -1;
            while (i > i2 && this.zzb.zza(this.zza.charAt(i - 1))) {
                i--;
            }
        } else {
            this.zze = i62 - 1;
        }
        return this.zza.subSequence(i2, i).toString();
    }
}
