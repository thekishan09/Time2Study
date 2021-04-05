package com.firebase.p002ui.auth.util.data;

import android.content.Context;
import android.content.SharedPreferences;
import com.firebase.p002ui.auth.IdpResponse;
import com.firebase.p002ui.auth.data.model.User;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* renamed from: com.firebase.ui.auth.util.data.EmailLinkPersistenceManager */
public class EmailLinkPersistenceManager {
    private static final Set<String> KEYS = Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{KEY_EMAIL, KEY_PROVIDER, KEY_IDP_TOKEN, KEY_IDP_SECRET})));
    private static final String KEY_ANONYMOUS_USER_ID = "com.firebase.ui.auth.data.client.auid";
    private static final String KEY_EMAIL = "com.firebase.ui.auth.data.client.email";
    private static final String KEY_IDP_SECRET = "com.firebase.ui.auth.data.client.idpSecret";
    private static final String KEY_IDP_TOKEN = "com.firebase.ui.auth.data.client.idpToken";
    private static final String KEY_PROVIDER = "com.firebase.ui.auth.data.client.provider";
    private static final String KEY_SESSION_ID = "com.firebase.ui.auth.data.client.sid";
    private static final String SHARED_PREF_NAME = "com.firebase.ui.auth.util.data.EmailLinkPersistenceManager";
    private static final EmailLinkPersistenceManager instance = new EmailLinkPersistenceManager();

    public static EmailLinkPersistenceManager getInstance() {
        return instance;
    }

    public void saveEmail(Context context, String email, String sessionId, String anonymousUserId) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(email);
        SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREF_NAME, 0).edit();
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_ANONYMOUS_USER_ID, anonymousUserId);
        editor.putString(KEY_SESSION_ID, sessionId);
        editor.apply();
    }

    public void saveIdpResponseForLinking(Context context, IdpResponse idpResponseForLinking) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(idpResponseForLinking);
        SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREF_NAME, 0).edit();
        editor.putString(KEY_EMAIL, idpResponseForLinking.getEmail());
        editor.putString(KEY_PROVIDER, idpResponseForLinking.getProviderType());
        editor.putString(KEY_IDP_TOKEN, idpResponseForLinking.getIdpToken());
        editor.putString(KEY_IDP_SECRET, idpResponseForLinking.getIdpSecret());
        editor.apply();
    }

    public SessionRecord retrieveSessionRecord(Context context) {
        Preconditions.checkNotNull(context);
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, 0);
        String email = sharedPreferences.getString(KEY_EMAIL, (String) null);
        String sessionId = sharedPreferences.getString(KEY_SESSION_ID, (String) null);
        if (email == null || sessionId == null) {
            return null;
        }
        String anonymousUserId = sharedPreferences.getString(KEY_ANONYMOUS_USER_ID, (String) null);
        String provider = sharedPreferences.getString(KEY_PROVIDER, (String) null);
        String idpToken = sharedPreferences.getString(KEY_IDP_TOKEN, (String) null);
        String idpSecret = sharedPreferences.getString(KEY_IDP_SECRET, (String) null);
        SessionRecord sessionRecord = new SessionRecord(sessionId, anonymousUserId).setEmail(email);
        if (!(provider == null || idpToken == null)) {
            sessionRecord.setIdpResponseForLinking(new IdpResponse.Builder(new User.Builder(provider, email).build()).setToken(idpToken).setSecret(idpSecret).setNewUser(false).build());
        }
        return sessionRecord;
    }

    public void clearAllData(Context context) {
        Preconditions.checkNotNull(context);
        SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREF_NAME, 0).edit();
        for (String key : KEYS) {
            editor.remove(key);
        }
        editor.apply();
    }

    /* renamed from: com.firebase.ui.auth.util.data.EmailLinkPersistenceManager$SessionRecord */
    public static class SessionRecord {
        private String mAnonymousUserId;
        private String mEmail;
        private IdpResponse mIdpResponseForLinking;
        private String mSessionId;

        public SessionRecord(String sessionId, String anonymousUserId) {
            Preconditions.checkNotNull(sessionId);
            this.mSessionId = sessionId;
            this.mAnonymousUserId = anonymousUserId;
        }

        public String getSessionId() {
            return this.mSessionId;
        }

        public String getEmail() {
            return this.mEmail;
        }

        public SessionRecord setEmail(String email) {
            this.mEmail = email;
            return this;
        }

        public IdpResponse getIdpResponseForLinking() {
            return this.mIdpResponseForLinking;
        }

        public SessionRecord setIdpResponseForLinking(IdpResponse idpResponseForLinking) {
            this.mIdpResponseForLinking = idpResponseForLinking;
            return this;
        }

        public String getAnonymousUserId() {
            return this.mAnonymousUserId;
        }
    }
}
