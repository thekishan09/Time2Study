package com.firebase.p002ui.auth.data.model;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.firebase.p002ui.auth.util.ExtraConstants;

/* renamed from: com.firebase.ui.auth.data.model.User */
public class User implements Parcelable {
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in.readString(), in.readString(), in.readString(), in.readString(), (Uri) in.readParcelable(Uri.class.getClassLoader()));
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
    private final String mEmail;
    private final String mName;
    private final String mPhoneNumber;
    private final Uri mPhotoUri;
    private final String mProviderId;

    private User(String providerId, String email, String phoneNumber, String name, Uri photoUri) {
        this.mProviderId = providerId;
        this.mEmail = email;
        this.mPhoneNumber = phoneNumber;
        this.mName = name;
        this.mPhotoUri = photoUri;
    }

    public static User getUser(Intent intent) {
        return (User) intent.getParcelableExtra(ExtraConstants.USER);
    }

    public static User getUser(Bundle arguments) {
        return (User) arguments.getParcelable(ExtraConstants.USER);
    }

    public String getProviderId() {
        return this.mProviderId;
    }

    public String getEmail() {
        return this.mEmail;
    }

    public String getPhoneNumber() {
        return this.mPhoneNumber;
    }

    public String getName() {
        return this.mName;
    }

    public Uri getPhotoUri() {
        return this.mPhotoUri;
    }

    public boolean equals(Object o) {
        String str;
        String str2;
        String str3;
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        if (this.mProviderId.equals(user.mProviderId) && ((str = this.mEmail) != null ? str.equals(user.mEmail) : user.mEmail == null) && ((str2 = this.mPhoneNumber) != null ? str2.equals(user.mPhoneNumber) : user.mPhoneNumber == null) && ((str3 = this.mName) != null ? str3.equals(user.mName) : user.mName == null)) {
            Uri uri = this.mPhotoUri;
            if (uri == null) {
                if (user.mPhotoUri == null) {
                    return true;
                }
            } else if (uri.equals(user.mPhotoUri)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int hashCode = this.mProviderId.hashCode() * 31;
        String str = this.mEmail;
        int i = 0;
        int result = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.mPhoneNumber;
        int result2 = (result + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.mName;
        int result3 = (result2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        Uri uri = this.mPhotoUri;
        if (uri != null) {
            i = uri.hashCode();
        }
        return result3 + i;
    }

    public String toString() {
        return "User{mProviderId='" + this.mProviderId + '\'' + ", mEmail='" + this.mEmail + '\'' + ", mPhoneNumber='" + this.mPhoneNumber + '\'' + ", mName='" + this.mName + '\'' + ", mPhotoUri=" + this.mPhotoUri + '}';
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mProviderId);
        dest.writeString(this.mEmail);
        dest.writeString(this.mPhoneNumber);
        dest.writeString(this.mName);
        dest.writeParcelable(this.mPhotoUri, flags);
    }

    /* renamed from: com.firebase.ui.auth.data.model.User$Builder */
    public static class Builder {
        private String mEmail;
        private String mName;
        private String mPhoneNumber;
        private Uri mPhotoUri;
        private String mProviderId;

        public Builder(String providerId, String email) {
            this.mProviderId = providerId;
            this.mEmail = email;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            this.mPhoneNumber = phoneNumber;
            return this;
        }

        public Builder setName(String name) {
            this.mName = name;
            return this;
        }

        public Builder setPhotoUri(Uri photoUri) {
            this.mPhotoUri = photoUri;
            return this;
        }

        public User build() {
            return new User(this.mProviderId, this.mEmail, this.mPhoneNumber, this.mName, this.mPhotoUri);
        }
    }
}
