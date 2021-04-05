package com.google.android.gms.auth.api.credentials;

import com.google.android.gms.common.api.Response;

/* compiled from: com.google.android.gms:play-services-auth@@18.1.0 */
public class CredentialRequestResponse extends Response<CredentialRequestResult> {
    public Credential getCredential() {
        return ((CredentialRequestResult) getResult()).getCredential();
    }
}
