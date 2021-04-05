package com.google.android.youtube.player;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import com.google.android.gms.actions.SearchIntents;
import com.google.android.youtube.player.internal.C1162z;
import java.util.List;

public final class YouTubeIntents {
    private YouTubeIntents() {
    }

    /* renamed from: a */
    static Intent m55a(Intent intent, Context context) {
        intent.putExtra("app_package", context.getPackageName()).putExtra("app_version", C1162z.m385d(context)).putExtra("client_library_version", C1162z.m377a());
        return intent;
    }

    /* renamed from: a */
    private static Uri m56a(String str) {
        String valueOf = String.valueOf("https://www.youtube.com/playlist?list=");
        String valueOf2 = String.valueOf(str);
        return Uri.parse(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
    }

    /* renamed from: a */
    private static String m57a(Context context) {
        PackageManager packageManager = context.getPackageManager();
        return C1162z.m383b(packageManager) ? "com.google.android.youtube.tv" : C1162z.m380a(packageManager) ? "com.google.android.youtube.googletv" : "com.google.android.youtube";
    }

    /* renamed from: a */
    private static boolean m58a(Context context, Intent intent) {
        List<ResolveInfo> queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 65536);
        return queryIntentActivities != null && !queryIntentActivities.isEmpty();
    }

    /* renamed from: a */
    private static boolean m59a(Context context, Uri uri) {
        return m58a(context, new Intent("android.intent.action.VIEW", uri).setPackage(m57a(context)));
    }

    /* renamed from: b */
    private static Intent m60b(Context context, Uri uri) {
        return m55a(new Intent("android.intent.action.VIEW", uri).setPackage(m57a(context)), context);
    }

    public static boolean canResolveChannelIntent(Context context) {
        return m59a(context, Uri.parse("https://www.youtube.com/channel/"));
    }

    public static boolean canResolveOpenPlaylistIntent(Context context) {
        return m59a(context, Uri.parse("https://www.youtube.com/playlist?list="));
    }

    public static boolean canResolvePlayPlaylistIntent(Context context) {
        int installedYouTubeVersionCode = getInstalledYouTubeVersionCode(context);
        return (!C1162z.m380a(context.getPackageManager()) ? installedYouTubeVersionCode >= 4000 : installedYouTubeVersionCode >= 4700) && canResolveOpenPlaylistIntent(context);
    }

    public static boolean canResolvePlayVideoIntent(Context context) {
        return m59a(context, Uri.parse("https://www.youtube.com/watch?v="));
    }

    public static boolean canResolvePlayVideoIntentWithOptions(Context context) {
        int installedYouTubeVersionCode = getInstalledYouTubeVersionCode(context);
        PackageManager packageManager = context.getPackageManager();
        return (C1162z.m383b(packageManager) || (!C1162z.m380a(packageManager) ? installedYouTubeVersionCode >= 3300 : installedYouTubeVersionCode >= Integer.MAX_VALUE)) && canResolvePlayVideoIntent(context);
    }

    public static boolean canResolveSearchIntent(Context context) {
        if (!C1162z.m380a(context.getPackageManager()) || getInstalledYouTubeVersionCode(context) >= 4700) {
            return m58a(context, new Intent("android.intent.action.SEARCH").setPackage(m57a(context)));
        }
        return false;
    }

    public static boolean canResolveUploadIntent(Context context) {
        return m58a(context, new Intent("com.google.android.youtube.intent.action.UPLOAD").setPackage(m57a(context)).setType("video/*"));
    }

    public static boolean canResolveUserIntent(Context context) {
        return m59a(context, Uri.parse("https://www.youtube.com/user/"));
    }

    public static Intent createChannelIntent(Context context, String str) {
        String valueOf = String.valueOf("https://www.youtube.com/channel/");
        String valueOf2 = String.valueOf(str);
        return m60b(context, Uri.parse(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf)));
    }

    public static Intent createOpenPlaylistIntent(Context context, String str) {
        return m55a(m60b(context, m56a(str)), context);
    }

    public static Intent createPlayPlaylistIntent(Context context, String str) {
        return m55a(m60b(context, m56a(str).buildUpon().appendQueryParameter("playnext", "1").build()), context);
    }

    public static Intent createPlayVideoIntent(Context context, String str) {
        return createPlayVideoIntentWithOptions(context, str, false, false);
    }

    public static Intent createPlayVideoIntentWithOptions(Context context, String str, boolean z, boolean z2) {
        String valueOf = String.valueOf("https://www.youtube.com/watch?v=");
        String valueOf2 = String.valueOf(str);
        return m60b(context, Uri.parse(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf))).putExtra("force_fullscreen", z).putExtra("finish_on_ended", z2);
    }

    public static Intent createSearchIntent(Context context, String str) {
        return m55a(new Intent("android.intent.action.SEARCH").setPackage(m57a(context)).putExtra(SearchIntents.EXTRA_QUERY, str), context);
    }

    public static Intent createUploadIntent(Context context, Uri uri) throws IllegalArgumentException {
        if (uri == null) {
            throw new IllegalArgumentException("videoUri parameter cannot be null.");
        } else if (uri.toString().startsWith("content://media/")) {
            return m55a(new Intent("com.google.android.youtube.intent.action.UPLOAD").setPackage(m57a(context)).setDataAndType(uri, "video/*"), context);
        } else {
            throw new IllegalArgumentException("videoUri parameter must be a URI pointing to a valid video file. It must begin with \"content://media/\"");
        }
    }

    public static Intent createUserIntent(Context context, String str) {
        String valueOf = String.valueOf("https://www.youtube.com/user/");
        String valueOf2 = String.valueOf(str);
        return m60b(context, Uri.parse(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf)));
    }

    public static int getInstalledYouTubeVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(m57a(context), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return -1;
        }
    }

    public static String getInstalledYouTubeVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(m57a(context), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static boolean isYouTubeInstalled(Context context) {
        try {
            context.getPackageManager().getApplicationInfo(m57a(context), 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
