package com.google.android.gms.auth.api.signin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.internal.zzg;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.ApiExceptionMapper;
import com.google.android.gms.common.api.internal.StatusExceptionMapper;
import com.google.android.gms.common.internal.PendingResultUtil;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.tasks.Task;

/* compiled from: com.google.android.gms:play-services-auth@@18.1.0 */
public class GoogleSignInClient extends GoogleApi<GoogleSignInOptions> {
    private static final zzc zzbp = new zzc((zzc) null);
    private static int zzbq = zzd.zzbt;

    /* compiled from: com.google.android.gms:play-services-auth@@18.1.0 */
    private static class zzc implements PendingResultUtil.ResultConverter<GoogleSignInResult, GoogleSignInAccount> {
        private zzc() {
        }

        public final /* synthetic */ Object convert(Result result) {
            return ((GoogleSignInResult) result).getSignInAccount();
        }

        /* synthetic */ zzc(zzc zzc) {
            this();
        }
    }

    /* 'enum' modifier removed */
    /* compiled from: com.google.android.gms:play-services-auth@@18.1.0 */
    static final class zzd {
        public static final int zzbt = 1;
        public static final int zzbu = 2;
        public static final int zzbv = 3;
        public static final int zzbw = 4;
        private static final /* synthetic */ int[] zzbx = {1, 2, 3, 4};

        public static int[] zzi() {
            return (int[]) zzbx.clone();
        }
    }

    GoogleSignInClient(Context context, GoogleSignInOptions googleSignInOptions) {
        super(context, Auth.GOOGLE_SIGN_IN_API, googleSignInOptions, (StatusExceptionMapper) new ApiExceptionMapper());
    }

    GoogleSignInClient(Activity activity, GoogleSignInOptions googleSignInOptions) {
        super(activity, Auth.GOOGLE_SIGN_IN_API, googleSignInOptions, (StatusExceptionMapper) new ApiExceptionMapper());
    }

    private final synchronized int zzh() {
        if (zzbq == zzd.zzbt) {
            Context applicationContext = getApplicationContext();
            GoogleApiAvailability instance = GoogleApiAvailability.getInstance();
            int isGooglePlayServicesAvailable = instance.isGooglePlayServicesAvailable(applicationContext, 12451000);
            if (isGooglePlayServicesAvailable == 0) {
                zzbq = zzd.zzbw;
            } else if (instance.getErrorResolutionIntent(applicationContext, isGooglePlayServicesAvailable, (String) null) != null || DynamiteModule.getLocalVersion(applicationContext, "com.google.android.gms.auth.api.fallback") == 0) {
                zzbq = zzd.zzbu;
            } else {
                zzbq = zzd.zzbv;
            }
        }
        return zzbq;
    }

    public Intent getSignInIntent() {
        Context applicationContext = getApplicationContext();
        int i = zzc.zzbr[zzh() - 1];
        if (i == 1) {
            return zzg.zzd(applicationContext, (GoogleSignInOptions) getApiOptions());
        }
        if (i != 2) {
            return zzg.zze(applicationContext, (GoogleSignInOptions) getApiOptions());
        }
        return zzg.zzc(applicationContext, (GoogleSignInOptions) getApiOptions());
    }

    public Task<GoogleSignInAccount> silentSignIn() {
        return PendingResultUtil.toTask(zzg.zzc(asGoogleApiClient(), getApplicationContext(), (GoogleSignInOptions) getApiOptions(), zzh() == zzd.zzbv), zzbp);
    }

    public Task<Void> signOut() {
        return PendingResultUtil.toVoidTask(zzg.zzc(asGoogleApiClient(), getApplicationContext(), zzh() == zzd.zzbv));
    }

    public Task<Void> revokeAccess() {
        return PendingResultUtil.toVoidTask(zzg.zzd(asGoogleApiClient(), getApplicationContext(), zzh() == zzd.zzbv));
    }
}
