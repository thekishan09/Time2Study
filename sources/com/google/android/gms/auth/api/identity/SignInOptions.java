package com.google.android.gms.auth.api.identity;

import android.os.Bundle;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-auth@@18.1.0 */
public final class SignInOptions implements Api.ApiOptions.Optional {
    private final String zzau;

    public static Builder builder() {
        return new Builder();
    }

    public SignInOptions(String str) {
        this.zzau = str;
    }

    /* compiled from: com.google.android.gms:play-services-auth@@18.1.0 */
    public static class Builder {
        private String zzau;

        public static Builder zzc(SignInOptions signInOptions) {
            Builder builder = new Builder();
            String zzg = signInOptions.zzg();
            if (zzg != null) {
                builder.zze(zzg);
            }
            return builder;
        }

        private Builder() {
        }

        public final Builder zze(String str) {
            this.zzau = Preconditions.checkNotEmpty(str);
            return this;
        }

        public SignInOptions build() {
            return new SignInOptions(this.zzau);
        }
    }

    public final String zzg() {
        return this.zzau;
    }

    public final boolean equals(Object obj) {
        return obj instanceof SignInOptions;
    }

    public final int hashCode() {
        return Objects.hashCode(SignInOptions.class);
    }

    public final Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("session_id", this.zzau);
        return bundle;
    }
}
