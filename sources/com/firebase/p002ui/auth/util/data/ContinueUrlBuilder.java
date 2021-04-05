package com.firebase.p002ui.auth.util.data;

import android.text.TextUtils;
import com.firebase.p002ui.auth.util.data.EmailLinkParser;
import com.google.android.gms.common.internal.Preconditions;

/* renamed from: com.firebase.ui.auth.util.data.ContinueUrlBuilder */
public class ContinueUrlBuilder {
    private StringBuilder mContinueUrl;

    public ContinueUrlBuilder(String url) {
        Preconditions.checkNotEmpty(url);
        this.mContinueUrl = new StringBuilder(url + "?");
    }

    public ContinueUrlBuilder appendSessionId(String sessionId) {
        addQueryParam(EmailLinkParser.LinkParameters.SESSION_IDENTIFIER, sessionId);
        return this;
    }

    public ContinueUrlBuilder appendAnonymousUserId(String anonymousUserId) {
        addQueryParam(EmailLinkParser.LinkParameters.ANONYMOUS_USER_ID_IDENTIFIER, anonymousUserId);
        return this;
    }

    public ContinueUrlBuilder appendProviderId(String providerId) {
        addQueryParam(EmailLinkParser.LinkParameters.PROVIDER_ID_IDENTIFIER, providerId);
        return this;
    }

    public ContinueUrlBuilder appendForceSameDeviceBit(boolean forceSameDevice) {
        addQueryParam(EmailLinkParser.LinkParameters.FORCE_SAME_DEVICE_IDENTIFIER, forceSameDevice ? "1" : "0");
        return this;
    }

    private void addQueryParam(String key, String value) {
        if (!TextUtils.isEmpty(value)) {
            StringBuilder sb = this.mContinueUrl;
            this.mContinueUrl.append(String.format("%s%s=%s", new Object[]{sb.charAt(sb.length() - 1) == '?' ? "" : "&", key, value}));
        }
    }

    public String build() {
        StringBuilder sb = this.mContinueUrl;
        if (sb.charAt(sb.length() - 1) == '?') {
            StringBuilder sb2 = this.mContinueUrl;
            sb2.setLength(sb2.length() - 1);
        }
        return this.mContinueUrl.toString();
    }
}
