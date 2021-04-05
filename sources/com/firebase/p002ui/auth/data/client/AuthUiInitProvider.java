package com.firebase.p002ui.auth.data.client;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import com.firebase.p002ui.auth.AuthUI;
import com.firebase.p002ui.auth.util.Preconditions;

/* renamed from: com.firebase.ui.auth.data.client.AuthUiInitProvider */
public class AuthUiInitProvider extends ContentProvider {
    public void attachInfo(Context context, ProviderInfo info) {
        Preconditions.checkNotNull(info, "AuthUiInitProvider ProviderInfo cannot be null.", new Object[0]);
        if (!"com.firebase.ui.auth.authuiinitprovider".equals(info.authority)) {
            super.attachInfo(context, info);
            return;
        }
        throw new IllegalStateException("Incorrect provider authority in manifest. Most likely due to a missing applicationId variable in application's build.gradle.");
    }

    public boolean onCreate() {
        AuthUI.setApplicationContext(getContext());
        return false;
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
