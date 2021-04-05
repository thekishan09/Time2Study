package com.google.android.gms.auth.api.signin.internal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResults;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.logging.Logger;

/* compiled from: com.google.android.gms:play-services-auth@@18.1.0 */
public final class zzg {
    private static Logger zzcb = new Logger("GoogleSignInCommon", new String[0]);

    public static Intent zzc(Context context, GoogleSignInOptions googleSignInOptions) {
        zzcb.mo11927d("getSignInIntent()", new Object[0]);
        SignInConfiguration signInConfiguration = new SignInConfiguration(context.getPackageName(), googleSignInOptions);
        Intent intent = new Intent("com.google.android.gms.auth.GOOGLE_SIGN_IN");
        intent.setPackage(context.getPackageName());
        intent.setClass(context, SignInHubActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("config", signInConfiguration);
        intent.putExtra("config", bundle);
        return intent;
    }

    public static Intent zzd(Context context, GoogleSignInOptions googleSignInOptions) {
        zzcb.mo11927d("getFallbackSignInIntent()", new Object[0]);
        Intent zzc = zzc(context, googleSignInOptions);
        zzc.setAction("com.google.android.gms.auth.APPAUTH_SIGN_IN");
        return zzc;
    }

    public static Intent zze(Context context, GoogleSignInOptions googleSignInOptions) {
        zzcb.mo11927d("getNoImplementationSignInIntent()", new Object[0]);
        Intent zzc = zzc(context, googleSignInOptions);
        zzc.setAction("com.google.android.gms.auth.NO_IMPL");
        return zzc;
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x008f  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x009d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.gms.common.api.OptionalPendingResult<com.google.android.gms.auth.api.signin.GoogleSignInResult> zzc(com.google.android.gms.common.api.GoogleApiClient r5, android.content.Context r6, com.google.android.gms.auth.api.signin.GoogleSignInOptions r7, boolean r8) {
        /*
            com.google.android.gms.common.logging.Logger r0 = zzcb
            r1 = 0
            java.lang.Object[] r2 = new java.lang.Object[r1]
            java.lang.String r3 = "silentSignIn()"
            r0.mo11927d(r3, r2)
            com.google.android.gms.common.logging.Logger r0 = zzcb
            java.lang.Object[] r2 = new java.lang.Object[r1]
            java.lang.String r3 = "getEligibleSavedSignInResult()"
            r0.mo11927d(r3, r2)
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r7)
            com.google.android.gms.auth.api.signin.internal.zzo r0 = com.google.android.gms.auth.api.signin.internal.zzo.zzd(r6)
            com.google.android.gms.auth.api.signin.GoogleSignInOptions r0 = r0.zzm()
            r2 = 0
            if (r0 == 0) goto L_0x008b
            android.accounts.Account r3 = r0.getAccount()
            android.accounts.Account r4 = r7.getAccount()
            if (r3 != 0) goto L_0x0033
            if (r4 != 0) goto L_0x0031
            r3 = 1
            goto L_0x0037
        L_0x0031:
            r3 = 0
            goto L_0x0037
        L_0x0033:
            boolean r3 = r3.equals(r4)
        L_0x0037:
            if (r3 == 0) goto L_0x008b
            boolean r3 = r7.isServerAuthCodeRequested()
            if (r3 != 0) goto L_0x008b
            boolean r3 = r7.isIdTokenRequested()
            if (r3 == 0) goto L_0x005a
            boolean r3 = r0.isIdTokenRequested()
            if (r3 == 0) goto L_0x008b
            java.lang.String r3 = r7.getServerClientId()
            java.lang.String r4 = r0.getServerClientId()
            boolean r3 = com.google.android.gms.common.internal.Objects.equal(r3, r4)
            if (r3 == 0) goto L_0x008b
        L_0x005a:
            java.util.HashSet r3 = new java.util.HashSet
            java.util.ArrayList r0 = r0.getScopes()
            r3.<init>(r0)
            java.util.HashSet r0 = new java.util.HashSet
            java.util.ArrayList r4 = r7.getScopes()
            r0.<init>(r4)
            boolean r0 = r3.containsAll(r0)
            if (r0 == 0) goto L_0x008b
            com.google.android.gms.auth.api.signin.internal.zzo r0 = com.google.android.gms.auth.api.signin.internal.zzo.zzd(r6)
            com.google.android.gms.auth.api.signin.GoogleSignInAccount r0 = r0.zzl()
            if (r0 == 0) goto L_0x008b
            boolean r3 = r0.isExpired()
            if (r3 != 0) goto L_0x008b
            com.google.android.gms.auth.api.signin.GoogleSignInResult r3 = new com.google.android.gms.auth.api.signin.GoogleSignInResult
            com.google.android.gms.common.api.Status r4 = com.google.android.gms.common.api.Status.RESULT_SUCCESS
            r3.<init>(r0, r4)
            goto L_0x008c
        L_0x008b:
            r3 = r2
        L_0x008c:
            if (r3 == 0) goto L_0x009d
            com.google.android.gms.common.logging.Logger r6 = zzcb
            java.lang.Object[] r7 = new java.lang.Object[r1]
            java.lang.String r8 = "Eligible saved sign in result found"
            r6.mo11927d(r8, r7)
            com.google.android.gms.common.api.OptionalPendingResult r5 = com.google.android.gms.common.api.PendingResults.immediatePendingResult(r3, (com.google.android.gms.common.api.GoogleApiClient) r5)
            return r5
        L_0x009d:
            if (r8 == 0) goto L_0x00af
            com.google.android.gms.auth.api.signin.GoogleSignInResult r6 = new com.google.android.gms.auth.api.signin.GoogleSignInResult
            com.google.android.gms.common.api.Status r7 = new com.google.android.gms.common.api.Status
            r8 = 4
            r7.<init>(r8)
            r6.<init>(r2, r7)
            com.google.android.gms.common.api.OptionalPendingResult r5 = com.google.android.gms.common.api.PendingResults.immediatePendingResult(r6, (com.google.android.gms.common.api.GoogleApiClient) r5)
            return r5
        L_0x00af:
            com.google.android.gms.common.logging.Logger r8 = zzcb
            java.lang.Object[] r0 = new java.lang.Object[r1]
            java.lang.String r1 = "trySilentSignIn()"
            r8.mo11927d(r1, r0)
            com.google.android.gms.auth.api.signin.internal.zzj r8 = new com.google.android.gms.auth.api.signin.internal.zzj
            r8.<init>(r5, r6, r7)
            com.google.android.gms.common.api.internal.BaseImplementation$ApiMethodImpl r5 = r5.enqueue(r8)
            com.google.android.gms.common.api.internal.OptionalPendingResultImpl r6 = new com.google.android.gms.common.api.internal.OptionalPendingResultImpl
            r6.<init>(r5)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.auth.api.signin.internal.zzg.zzc(com.google.android.gms.common.api.GoogleApiClient, android.content.Context, com.google.android.gms.auth.api.signin.GoogleSignInOptions, boolean):com.google.android.gms.common.api.OptionalPendingResult");
    }

    public static PendingResult<Status> zzc(GoogleApiClient googleApiClient, Context context, boolean z) {
        zzcb.mo11927d("Signing out", new Object[0]);
        zzc(context);
        if (z) {
            return PendingResults.immediatePendingResult(Status.RESULT_SUCCESS, googleApiClient);
        }
        return googleApiClient.execute(new zzl(googleApiClient));
    }

    public static PendingResult<Status> zzd(GoogleApiClient googleApiClient, Context context, boolean z) {
        zzcb.mo11927d("Revoking access", new Object[0]);
        String savedRefreshToken = Storage.getInstance(context).getSavedRefreshToken();
        zzc(context);
        if (z) {
            return zzc.zzf(savedRefreshToken);
        }
        return googleApiClient.execute(new zzn(googleApiClient));
    }

    private static void zzc(Context context) {
        zzo.zzd(context).clear();
        for (GoogleApiClient maybeSignOut : GoogleApiClient.getAllClients()) {
            maybeSignOut.maybeSignOut();
        }
        GoogleApiManager.reportSignOut();
    }

    public static GoogleSignInResult getSignInResultFromIntent(Intent intent) {
        if (intent == null) {
            return new GoogleSignInResult((GoogleSignInAccount) null, Status.RESULT_INTERNAL_ERROR);
        }
        Status status = (Status) intent.getParcelableExtra("googleSignInStatus");
        GoogleSignInAccount googleSignInAccount = (GoogleSignInAccount) intent.getParcelableExtra("googleSignInAccount");
        if (googleSignInAccount != null) {
            return new GoogleSignInResult(googleSignInAccount, Status.RESULT_SUCCESS);
        }
        if (status == null) {
            status = Status.RESULT_INTERNAL_ERROR;
        }
        return new GoogleSignInResult((GoogleSignInAccount) null, status);
    }
}
