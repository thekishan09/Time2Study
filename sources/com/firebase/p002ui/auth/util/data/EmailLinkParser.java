package com.firebase.p002ui.auth.util.data;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.firebase.ui.auth.util.data.EmailLinkParser */
public class EmailLinkParser {
    private static final String CONTINUE_URL = "continueUrl";
    private static String LINK = "link";
    private static final String OOB_CODE = "oobCode";
    private Map<String, String> mParams;

    /* renamed from: com.firebase.ui.auth.util.data.EmailLinkParser$LinkParameters */
    public static class LinkParameters {
        public static final String ANONYMOUS_USER_ID_IDENTIFIER = "ui_auid";
        public static final String FORCE_SAME_DEVICE_IDENTIFIER = "ui_sd";
        public static final String PROVIDER_ID_IDENTIFIER = "ui_pid";
        public static final String SESSION_IDENTIFIER = "ui_sid";
    }

    public EmailLinkParser(String link) {
        Preconditions.checkNotEmpty(link);
        Map<String, String> parseUri = parseUri(Uri.parse(link));
        this.mParams = parseUri;
        if (parseUri.isEmpty()) {
            throw new IllegalArgumentException("Invalid link: no parameters found");
        }
    }

    public String getOobCode() {
        return this.mParams.get(OOB_CODE);
    }

    public String getSessionId() {
        return this.mParams.get(LinkParameters.SESSION_IDENTIFIER);
    }

    public String getAnonymousUserId() {
        return this.mParams.get(LinkParameters.ANONYMOUS_USER_ID_IDENTIFIER);
    }

    public boolean getForceSameDeviceBit() {
        String forceSameDeviceBit = this.mParams.get(LinkParameters.FORCE_SAME_DEVICE_IDENTIFIER);
        if (TextUtils.isEmpty(forceSameDeviceBit)) {
            return false;
        }
        return forceSameDeviceBit.equals("1");
    }

    public String getProviderId() {
        return this.mParams.get(LinkParameters.PROVIDER_ID_IDENTIFIER);
    }

    private Map<String, String> parseUri(Uri uri) {
        Map<String, String> map = new HashMap<>();
        try {
            for (String param : uri.getQueryParameterNames()) {
                if (!param.equalsIgnoreCase(LINK)) {
                    if (!param.equalsIgnoreCase(CONTINUE_URL)) {
                        String value = uri.getQueryParameter(param);
                        if (value != null) {
                            map.put(param, value);
                        }
                    }
                }
                Map<String, String> innerValues = parseUri(Uri.parse(uri.getQueryParameter(param)));
                if (innerValues != null) {
                    map.putAll(innerValues);
                }
            }
        } catch (Exception e) {
        }
        return map;
    }
}
