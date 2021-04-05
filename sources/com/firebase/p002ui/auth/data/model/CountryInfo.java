package com.firebase.p002ui.auth.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.text.Collator;
import java.util.Locale;

/* renamed from: com.firebase.ui.auth.data.model.CountryInfo */
public final class CountryInfo implements Comparable<CountryInfo>, Parcelable {
    public static final Parcelable.Creator<CountryInfo> CREATOR = new Parcelable.Creator<CountryInfo>() {
        public CountryInfo createFromParcel(Parcel source) {
            return new CountryInfo(source);
        }

        public CountryInfo[] newArray(int size) {
            return new CountryInfo[size];
        }
    };
    private final Collator mCollator;
    private final int mCountryCode;
    private final Locale mLocale;

    public CountryInfo(Locale locale, int countryCode) {
        Collator instance = Collator.getInstance(Locale.getDefault());
        this.mCollator = instance;
        instance.setStrength(0);
        this.mLocale = locale;
        this.mCountryCode = countryCode;
    }

    protected CountryInfo(Parcel in) {
        Collator instance = Collator.getInstance(Locale.getDefault());
        this.mCollator = instance;
        instance.setStrength(0);
        this.mLocale = (Locale) in.readSerializable();
        this.mCountryCode = in.readInt();
    }

    public static String localeToEmoji(Locale locale) {
        String countryCode = locale.getCountry();
        return new String(Character.toChars((Character.codePointAt(countryCode, 0) - 65) + 127462)) + new String(Character.toChars((Character.codePointAt(countryCode, 1) - 65) + 127462));
    }

    public Locale getLocale() {
        return this.mLocale;
    }

    public int getCountryCode() {
        return this.mCountryCode;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CountryInfo that = (CountryInfo) o;
        if (this.mCountryCode == that.mCountryCode) {
            Locale locale = this.mLocale;
            if (locale != null) {
                if (locale.equals(that.mLocale)) {
                    return true;
                }
            } else if (that.mLocale == null) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        Locale locale = this.mLocale;
        return ((locale != null ? locale.hashCode() : 0) * 31) + this.mCountryCode;
    }

    public String toString() {
        return localeToEmoji(this.mLocale) + " " + this.mLocale.getDisplayCountry() + " +" + this.mCountryCode;
    }

    public int compareTo(CountryInfo info) {
        return this.mCollator.compare(this.mLocale.getDisplayCountry(), info.mLocale.getDisplayCountry());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.mLocale);
        dest.writeInt(this.mCountryCode);
    }
}
