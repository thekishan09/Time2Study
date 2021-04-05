package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzew extends zzam implements Api.ApiOptions.HasOptions {
    private final String zzb;

    private zzew(String str) {
        this.zzb = Preconditions.checkNotEmpty(str, "A valid API key must be provided");
    }

    public final String zzb() {
        return this.zzb;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzew)) {
            return false;
        }
        zzew zzew = (zzew) obj;
        if (!Objects.equal(this.zzb, zzew.zzb) || this.zza != zzew.zza) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzb) + (true ^ this.zza ? 1 : 0);
    }

    public final /* synthetic */ zzam zza() {
        return (zzew) clone();
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        return new zzev(this.zzb).zza();
    }

    /* synthetic */ zzew(String str, zzet zzet) {
        this(str);
    }
}
