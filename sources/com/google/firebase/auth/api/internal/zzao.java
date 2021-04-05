package com.google.firebase.auth.api.internal;

import android.content.Context;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.StatusExceptionMapper;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzao<O extends Api.ApiOptions> extends GoogleApi<O> {
    public zzao(Context context, Api<O> api, O o, StatusExceptionMapper statusExceptionMapper) {
        super(context, api, o, statusExceptionMapper);
    }
}
