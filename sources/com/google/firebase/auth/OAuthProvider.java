package com.google.firebase.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.auth.internal.FederatedSignInActivity;
import com.google.firebase.auth.internal.zzav;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public class OAuthProvider extends FederatedAuthProvider {
    private final Bundle zza;

    private OAuthProvider(Bundle bundle) {
        this.zza = bundle;
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static class CredentialBuilder {
        private final String zza;
        private String zzb;
        private String zzc;
        private String zzd;

        private CredentialBuilder(String str) {
            this.zza = str;
        }

        public String getIdToken() {
            return this.zzb;
        }

        public CredentialBuilder setIdToken(String str) {
            this.zzb = str;
            return this;
        }

        public CredentialBuilder setIdTokenWithRawNonce(String str, String str2) {
            this.zzb = str;
            this.zzd = str2;
            return this;
        }

        public String getAccessToken() {
            return this.zzc;
        }

        public CredentialBuilder setAccessToken(String str) {
            this.zzc = str;
            return this;
        }

        public AuthCredential build() {
            return zze.zza(this.zza, this.zzb, this.zzc, this.zzd);
        }
    }

    public static Builder newBuilder(String str) {
        return newBuilder(str, FirebaseAuth.getInstance());
    }

    public static Builder newBuilder(String str, FirebaseAuth firebaseAuth) {
        GoogleApiAvailability instance = GoogleApiAvailability.getInstance();
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(firebaseAuth);
        if (!"facebook.com".equals(str)) {
            return new Builder(str, firebaseAuth, instance);
        }
        throw new IllegalArgumentException("Sign in with Facebook is not supported via this method; the Facebook TOS dictate that you must use the Facebook Android SDK for Facebook login.");
    }

    public String getProviderId() {
        Bundle bundle = this.zza;
        if (bundle == null) {
            return null;
        }
        return bundle.getString("com.google.firebase.auth.KEY_PROVIDER_ID", (String) null);
    }

    public final void zza(Activity activity) {
        zzav.zza();
        zzav.zzb();
        Intent intent = new Intent("com.google.firebase.auth.internal.SIGN_IN");
        intent.setClass(activity, FederatedSignInActivity.class);
        intent.setPackage(activity.getPackageName());
        intent.putExtras(this.zza);
        activity.startActivity(intent);
    }

    /* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
    public static class Builder {
        private final FirebaseAuth zza;
        private final Bundle zzb;
        private final Bundle zzc;

        private Builder(String str, FirebaseAuth firebaseAuth, GoogleApiAvailability googleApiAvailability) {
            this.zzb = new Bundle();
            this.zzc = new Bundle();
            this.zza = firebaseAuth;
            this.zzb.putString("com.google.firebase.auth.KEY_API_KEY", firebaseAuth.zzb().getOptions().getApiKey());
            this.zzb.putString("com.google.firebase.auth.KEY_PROVIDER_ID", str);
            this.zzb.putBundle("com.google.firebase.auth.KEY_PROVIDER_CUSTOM_PARAMS", this.zzc);
            zzav.zza();
            zzav.zzb();
            this.zzb.putString("com.google.firebase.auth.internal.CLIENT_VERSION", Integer.toString(googleApiAvailability.getClientVersion(this.zza.zzb().getApplicationContext())));
            this.zzb.putString("com.google.firebase.auth.KEY_TENANT_ID", this.zza.zzc());
        }

        public List<String> getScopes() {
            ArrayList<String> stringArrayList = this.zzb.getStringArrayList("com.google.firebase.auth.KEY_PROVIDER_SCOPES");
            return stringArrayList != null ? stringArrayList : Collections.emptyList();
        }

        public Builder setScopes(List<String> list) {
            this.zzb.putStringArrayList("com.google.firebase.auth.KEY_PROVIDER_SCOPES", new ArrayList(list));
            return this;
        }

        public Builder addCustomParameter(String str, String str2) {
            this.zzc.putString(str, str2);
            return this;
        }

        public Builder addCustomParameters(Map<String, String> map) {
            for (Map.Entry next : map.entrySet()) {
                this.zzc.putString((String) next.getKey(), (String) next.getValue());
            }
            return this;
        }

        public OAuthProvider build() {
            return new OAuthProvider(this.zzb);
        }
    }

    public final void zzb(Activity activity) {
        zzav.zza();
        zzav.zzb();
        Intent intent = new Intent("com.google.firebase.auth.internal.LINK");
        intent.setClass(activity, FederatedSignInActivity.class);
        intent.setPackage(activity.getPackageName());
        intent.putExtras(this.zza);
        activity.startActivity(intent);
    }

    public final void zzc(Activity activity) {
        zzav.zza();
        zzav.zzb();
        Intent intent = new Intent("com.google.firebase.auth.internal.REAUTHENTICATE");
        intent.setClass(activity, FederatedSignInActivity.class);
        intent.setPackage(activity.getPackageName());
        intent.putExtras(this.zza);
        activity.startActivity(intent);
    }

    public static CredentialBuilder newCredentialBuilder(String str) {
        return new CredentialBuilder(Preconditions.checkNotEmpty(str));
    }

    @Deprecated
    public static AuthCredential getCredential(String str, String str2, String str3) {
        return zze.zza(str, str2, str3);
    }
}
