package com.firebase.p002ui.auth.data.model;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.firebase.p002ui.auth.AuthMethodPickerLayout;
import com.firebase.p002ui.auth.AuthUI;
import com.firebase.p002ui.auth.util.ExtraConstants;
import com.firebase.p002ui.auth.util.Preconditions;
import java.util.Collections;
import java.util.List;

/* renamed from: com.firebase.ui.auth.data.model.FlowParameters */
public class FlowParameters implements Parcelable {
    public static final Parcelable.Creator<FlowParameters> CREATOR = new Parcelable.Creator<FlowParameters>() {
        public FlowParameters createFromParcel(Parcel in) {
            Parcel parcel = in;
            return new FlowParameters(in.readString(), parcel.createTypedArrayList(AuthUI.IdpConfig.CREATOR), in.readInt(), in.readInt(), in.readString(), in.readString(), in.readInt() != 0, in.readInt() != 0, in.readInt() != 0, in.readInt() != 0, in.readString(), (AuthMethodPickerLayout) parcel.readParcelable(AuthMethodPickerLayout.class.getClassLoader()));
        }

        public FlowParameters[] newArray(int size) {
            return new FlowParameters[size];
        }
    };
    public final boolean alwaysShowProviderChoice;
    public final String appName;
    public final AuthMethodPickerLayout authMethodPickerLayout;
    public String emailLink;
    public final boolean enableAnonymousUpgrade;
    public final boolean enableCredentials;
    public final boolean enableHints;
    public final int logoId;
    public final String privacyPolicyUrl;
    public final List<AuthUI.IdpConfig> providers;
    public final String termsOfServiceUrl;
    public final int themeId;

    public FlowParameters(String appName2, List<AuthUI.IdpConfig> providers2, int themeId2, int logoId2, String termsOfServiceUrl2, String privacyPolicyUrl2, boolean enableCredentials2, boolean enableHints2, boolean enableAnonymousUpgrade2, boolean alwaysShowProviderChoice2, String emailLink2, AuthMethodPickerLayout authMethodPickerLayout2) {
        this.appName = (String) Preconditions.checkNotNull(appName2, "appName cannot be null", new Object[0]);
        this.providers = Collections.unmodifiableList((List) Preconditions.checkNotNull(providers2, "providers cannot be null", new Object[0]));
        this.themeId = themeId2;
        this.logoId = logoId2;
        this.termsOfServiceUrl = termsOfServiceUrl2;
        this.privacyPolicyUrl = privacyPolicyUrl2;
        this.enableCredentials = enableCredentials2;
        this.enableHints = enableHints2;
        this.enableAnonymousUpgrade = enableAnonymousUpgrade2;
        this.alwaysShowProviderChoice = alwaysShowProviderChoice2;
        this.emailLink = emailLink2;
        this.authMethodPickerLayout = authMethodPickerLayout2;
    }

    public static FlowParameters fromIntent(Intent intent) {
        return (FlowParameters) intent.getParcelableExtra(ExtraConstants.FLOW_PARAMS);
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.appName);
        dest.writeTypedList(this.providers);
        dest.writeInt(this.themeId);
        dest.writeInt(this.logoId);
        dest.writeString(this.termsOfServiceUrl);
        dest.writeString(this.privacyPolicyUrl);
        dest.writeInt(this.enableCredentials ? 1 : 0);
        dest.writeInt(this.enableHints ? 1 : 0);
        dest.writeInt(this.enableAnonymousUpgrade ? 1 : 0);
        dest.writeInt(this.alwaysShowProviderChoice ? 1 : 0);
        dest.writeString(this.emailLink);
        dest.writeParcelable(this.authMethodPickerLayout, flags);
    }

    public int describeContents() {
        return 0;
    }

    public boolean isSingleProviderFlow() {
        return this.providers.size() == 1;
    }

    public boolean isTermsOfServiceUrlProvided() {
        return !TextUtils.isEmpty(this.termsOfServiceUrl);
    }

    public boolean isPrivacyPolicyUrlProvided() {
        return !TextUtils.isEmpty(this.privacyPolicyUrl);
    }

    public boolean isAnonymousUpgradeEnabled() {
        return this.enableAnonymousUpgrade;
    }

    public boolean shouldShowProviderChoice() {
        return !isSingleProviderFlow() || this.alwaysShowProviderChoice;
    }
}
