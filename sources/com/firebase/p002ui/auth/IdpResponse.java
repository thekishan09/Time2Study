package com.firebase.p002ui.auth;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.firebase.p002ui.auth.data.model.User;
import com.firebase.p002ui.auth.util.ExtraConstants;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/* renamed from: com.firebase.ui.auth.IdpResponse */
public class IdpResponse implements Parcelable {
    public static final Parcelable.Creator<IdpResponse> CREATOR = new Parcelable.Creator<IdpResponse>() {
        public IdpResponse createFromParcel(Parcel in) {
            User user = (User) in.readParcelable(User.class.getClassLoader());
            String readString = in.readString();
            String readString2 = in.readString();
            boolean z = true;
            if (in.readInt() != 1) {
                z = false;
            }
            return new IdpResponse(user, readString, readString2, z, (FirebaseUiException) in.readSerializable(), (AuthCredential) in.readParcelable(AuthCredential.class.getClassLoader()));
        }

        public IdpResponse[] newArray(int size) {
            return new IdpResponse[size];
        }
    };
    private final FirebaseUiException mException;
    /* access modifiers changed from: private */
    public final boolean mIsNewUser;
    /* access modifiers changed from: private */
    public final AuthCredential mPendingCredential;
    /* access modifiers changed from: private */
    public final String mSecret;
    /* access modifiers changed from: private */
    public final String mToken;
    /* access modifiers changed from: private */
    public final User mUser;

    private IdpResponse(FirebaseUiException e) {
        this((User) null, (String) null, (String) null, false, e, (AuthCredential) null);
    }

    private IdpResponse(User user, String token, String secret, boolean isNewUser) {
        this(user, token, secret, isNewUser, (FirebaseUiException) null, (AuthCredential) null);
    }

    private IdpResponse(AuthCredential credential, FirebaseUiException e) {
        this((User) null, (String) null, (String) null, false, e, credential);
    }

    private IdpResponse(User user, String token, String secret, boolean isNewUser, FirebaseUiException e, AuthCredential credential) {
        this.mUser = user;
        this.mToken = token;
        this.mSecret = secret;
        this.mIsNewUser = isNewUser;
        this.mException = e;
        this.mPendingCredential = credential;
    }

    public static IdpResponse fromResultIntent(Intent resultIntent) {
        if (resultIntent != null) {
            return (IdpResponse) resultIntent.getParcelableExtra(ExtraConstants.IDP_RESPONSE);
        }
        return null;
    }

    public static IdpResponse from(Exception e) {
        if (e instanceof FirebaseUiException) {
            return new IdpResponse((FirebaseUiException) e);
        }
        FirebaseUiException wrapped = new FirebaseUiException(0, (Throwable) e);
        wrapped.setStackTrace(e.getStackTrace());
        return new IdpResponse(wrapped);
    }

    public static Intent getErrorIntent(Exception e) {
        return from(e).toIntent();
    }

    public IdpResponse withResult(AuthResult result) {
        return mutate().setNewUser(result.getAdditionalUserInfo().isNewUser()).build();
    }

    public Intent toIntent() {
        return new Intent().putExtra(ExtraConstants.IDP_RESPONSE, this);
    }

    public Builder mutate() {
        if (isSuccessful()) {
            return new Builder(this);
        }
        throw new IllegalStateException("Cannot mutate an unsuccessful response.");
    }

    public boolean isSuccessful() {
        return this.mException == null;
    }

    public User getUser() {
        return this.mUser;
    }

    public String getProviderType() {
        return this.mUser.getProviderId();
    }

    public boolean isNewUser() {
        return this.mIsNewUser;
    }

    public String getEmail() {
        return this.mUser.getEmail();
    }

    public String getPhoneNumber() {
        return this.mUser.getPhoneNumber();
    }

    public String getIdpToken() {
        return this.mToken;
    }

    public String getIdpSecret() {
        return this.mSecret;
    }

    public FirebaseUiException getError() {
        return this.mException;
    }

    public AuthCredential getCredentialForLinking() {
        return this.mPendingCredential;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mUser, flags);
        dest.writeString(this.mToken);
        dest.writeString(this.mSecret);
        dest.writeInt(this.mIsNewUser ? 1 : 0);
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new ByteArrayOutputStream());
            oos.writeObject(this.mException);
            dest.writeSerializable(this.mException);
            try {
                oos.close();
            } catch (IOException e) {
            }
        } catch (IOException e2) {
            FirebaseUiException fake = new FirebaseUiException(0, "Exception serialization error, forced wrapping. Original: " + this.mException + ", original cause: " + this.mException.getCause());
            fake.setStackTrace(this.mException.getStackTrace());
            dest.writeSerializable(fake);
            if (oos != null) {
                oos.close();
            }
        } catch (Throwable th) {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e3) {
                }
            }
            throw th;
        }
        dest.writeParcelable(this.mPendingCredential, 0);
    }

    public boolean equals(Object o) {
        FirebaseUiException firebaseUiException;
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IdpResponse response = (IdpResponse) o;
        User user = this.mUser;
        if (user != null ? user.equals(response.mUser) : response.mUser == null) {
            String str = this.mToken;
            if (str != null ? str.equals(response.mToken) : response.mToken == null) {
                String str2 = this.mSecret;
                if (str2 != null ? str2.equals(response.mSecret) : response.mSecret == null) {
                    if (this.mIsNewUser == response.mIsNewUser && ((firebaseUiException = this.mException) != null ? firebaseUiException.equals(response.mException) : response.mException == null)) {
                        AuthCredential authCredential = this.mPendingCredential;
                        if (authCredential == null) {
                            if (response.mPendingCredential == null) {
                                return true;
                            }
                        } else if (authCredential.getProvider().equals(response.mPendingCredential.getProvider())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public int hashCode() {
        User user = this.mUser;
        int i = 0;
        int hashCode = (user == null ? 0 : user.hashCode()) * 31;
        String str = this.mToken;
        int result = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.mSecret;
        int result2 = (((result + (str2 == null ? 0 : str2.hashCode())) * 31) + (this.mIsNewUser ? 1 : 0)) * 31;
        FirebaseUiException firebaseUiException = this.mException;
        int result3 = (result2 + (firebaseUiException == null ? 0 : firebaseUiException.hashCode())) * 31;
        AuthCredential authCredential = this.mPendingCredential;
        if (authCredential != null) {
            i = authCredential.getProvider().hashCode();
        }
        return result3 + i;
    }

    public String toString() {
        return "IdpResponse{mUser=" + this.mUser + ", mToken='" + this.mToken + '\'' + ", mSecret='" + this.mSecret + '\'' + ", mIsNewUser='" + this.mIsNewUser + '\'' + ", mException=" + this.mException + ", mPendingCredential=" + this.mPendingCredential + '}';
    }

    /* renamed from: com.firebase.ui.auth.IdpResponse$Builder */
    public static class Builder {
        private boolean mIsNewUser;
        private final AuthCredential mPendingCredential;
        private String mSecret;
        private String mToken;
        private final User mUser;

        public Builder(User user) {
            this.mUser = user;
            this.mPendingCredential = null;
        }

        public Builder(AuthCredential pendingCredential) {
            this.mUser = null;
            this.mPendingCredential = pendingCredential;
        }

        public Builder(IdpResponse response) {
            this.mUser = response.mUser;
            this.mToken = response.mToken;
            this.mSecret = response.mSecret;
            this.mIsNewUser = response.mIsNewUser;
            this.mPendingCredential = response.mPendingCredential;
        }

        public Builder setNewUser(boolean newUser) {
            this.mIsNewUser = newUser;
            return this;
        }

        public Builder setToken(String token) {
            this.mToken = token;
            return this;
        }

        public Builder setSecret(String secret) {
            this.mSecret = secret;
            return this;
        }

        public IdpResponse build() {
            if (this.mPendingCredential != null) {
                return new IdpResponse(this.mPendingCredential, new FirebaseUiException(5));
            }
            String providerId = this.mUser.getProviderId();
            if (!AuthUI.SUPPORTED_PROVIDERS.contains(providerId)) {
                throw new IllegalStateException("Unknown provider: " + providerId);
            } else if (AuthUI.SOCIAL_PROVIDERS.contains(providerId) && TextUtils.isEmpty(this.mToken)) {
                throw new IllegalStateException("Token cannot be null when using a non-email provider.");
            } else if (!providerId.equals("twitter.com") || !TextUtils.isEmpty(this.mSecret)) {
                return new IdpResponse(this.mUser, this.mToken, this.mSecret, this.mIsNewUser);
            } else {
                throw new IllegalStateException("Secret cannot be null when using the Twitter provider.");
            }
        }
    }
}
