package com.google.android.gms.internal.p010authapi;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.auth.api.identity.SignInOptions;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.common.internal.ClientSettings;

/* renamed from: com.google.android.gms.internal.auth-api.zzag */
/* compiled from: com.google.android.gms:play-services-auth@@18.1.0 */
final class zzag extends Api.AbstractClientBuilder<zzak, SignInOptions> {
    zzag() {
    }

    public final /* synthetic */ Api.Client buildClient(Context context, Looper looper, ClientSettings clientSettings, Object obj, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        return new zzak(context, looper, (SignInOptions) obj, clientSettings, connectionCallbacks, onConnectionFailedListener);
    }
}
