package com.google.android.gms.internal.p010authapi;

import com.google.android.gms.common.Feature;

/* renamed from: com.google.android.gms.internal.auth-api.zzam */
/* compiled from: com.google.android.gms:play-services-auth@@18.1.0 */
public final class zzam {
    public static final Feature zzcz = new Feature("auth_api_credentials_begin_sign_in", 4);
    public static final Feature zzda = new Feature("auth_api_credentials_sign_out", 2);
    private static final Feature zzdb = new Feature("auth_api_credentials_authorize", 1);
    private static final Feature zzdc = new Feature("auth_api_credentials_revoke_access", 1);
    private static final Feature zzdd = new Feature("auth_api_credentials_save_password", 3);
    private static final Feature zzde = new Feature("auth_api_credentials_get_sign_in_intent", 3);
    private static final Feature zzdf;
    public static final Feature[] zzdg;

    static {
        Feature feature = new Feature("auth_api_credentials_save_account_linking_token", 2);
        zzdf = feature;
        zzdg = new Feature[]{zzcz, zzda, zzdb, zzdc, zzdd, zzde, feature};
    }
}
