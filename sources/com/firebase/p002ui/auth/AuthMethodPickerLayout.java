package com.firebase.p002ui.auth;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.firebase.ui.auth.AuthMethodPickerLayout */
public class AuthMethodPickerLayout implements Parcelable {
    public static final Parcelable.Creator<AuthMethodPickerLayout> CREATOR = new Parcelable.Creator<AuthMethodPickerLayout>() {
        public AuthMethodPickerLayout createFromParcel(Parcel in) {
            return new AuthMethodPickerLayout(in);
        }

        public AuthMethodPickerLayout[] newArray(int size) {
            return new AuthMethodPickerLayout[size];
        }
    };
    /* access modifiers changed from: private */
    public int mainLayout;
    /* access modifiers changed from: private */
    public Map<String, Integer> providersButton;
    /* access modifiers changed from: private */
    public int tosPpView;

    private AuthMethodPickerLayout() {
        this.tosPpView = -1;
    }

    private AuthMethodPickerLayout(Parcel in) {
        this.tosPpView = -1;
        this.mainLayout = in.readInt();
        this.tosPpView = in.readInt();
        Bundle buttonsBundle = in.readBundle(getClass().getClassLoader());
        this.providersButton = new HashMap();
        for (String key : buttonsBundle.keySet()) {
            this.providersButton.put(key, Integer.valueOf(buttonsBundle.getInt(key)));
        }
    }

    public int getMainLayout() {
        return this.mainLayout;
    }

    public int getTosPpView() {
        return this.tosPpView;
    }

    public Map<String, Integer> getProvidersButton() {
        return this.providersButton;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(this.mainLayout);
        parcel.writeInt(this.tosPpView);
        Bundle bundle = new Bundle();
        for (String key : this.providersButton.keySet()) {
            bundle.putInt(key, this.providersButton.get(key).intValue());
        }
        parcel.writeBundle(bundle);
    }

    /* renamed from: com.firebase.ui.auth.AuthMethodPickerLayout$Builder */
    public static class Builder {
        private AuthMethodPickerLayout instance;
        private Map<String, Integer> providersMapping = new HashMap();

        public Builder(int mainLayout) {
            AuthMethodPickerLayout authMethodPickerLayout = new AuthMethodPickerLayout();
            this.instance = authMethodPickerLayout;
            int unused = authMethodPickerLayout.mainLayout = mainLayout;
        }

        public Builder setGoogleButtonId(int googleBtn) {
            this.providersMapping.put("google.com", Integer.valueOf(googleBtn));
            return this;
        }

        public Builder setFacebookButtonId(int facebookBtn) {
            this.providersMapping.put("facebook.com", Integer.valueOf(facebookBtn));
            return this;
        }

        public Builder setTwitterButtonId(int twitterBtn) {
            this.providersMapping.put("twitter.com", Integer.valueOf(twitterBtn));
            return this;
        }

        public Builder setEmailButtonId(int emailButton) {
            this.providersMapping.put("password", Integer.valueOf(emailButton));
            return this;
        }

        public Builder setPhoneButtonId(int phoneButton) {
            this.providersMapping.put("phone", Integer.valueOf(phoneButton));
            return this;
        }

        public Builder setAnonymousButtonId(int anonymousButton) {
            this.providersMapping.put(AuthUI.ANONYMOUS_PROVIDER, Integer.valueOf(anonymousButton));
            return this;
        }

        public Builder setTosAndPrivacyPolicyId(int tosPpView) {
            int unused = this.instance.tosPpView = tosPpView;
            return this;
        }

        public AuthMethodPickerLayout build() {
            if (!this.providersMapping.isEmpty()) {
                for (String key : this.providersMapping.keySet()) {
                    if (!AuthUI.SUPPORTED_PROVIDERS.contains(key)) {
                        throw new IllegalArgumentException("Unknown provider: " + key);
                    }
                }
                Map unused = this.instance.providersButton = this.providersMapping;
                return this.instance;
            }
            throw new IllegalArgumentException("Must configure at least one button.");
        }
    }
}
