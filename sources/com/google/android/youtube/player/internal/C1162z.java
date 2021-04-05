package com.google.android.youtube.player.internal;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;

/* renamed from: com.google.android.youtube.player.internal.z */
public final class C1162z {

    /* renamed from: a */
    private static final Uri f259a = Uri.parse("http://play.google.com/store/apps/details");

    /* renamed from: b */
    private static final String[] f260b = {"com.google.android.youtube", "com.google.android.youtube.tv", "com.google.android.youtube.googletv", "com.google.android.gms", null};

    /* renamed from: a */
    public static Intent m376a(String str) {
        Uri fromParts = Uri.fromParts("package", str, (String) null);
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(fromParts);
        return intent;
    }

    /* renamed from: a */
    public static String m377a() {
        StringBuilder sb = new StringBuilder(35);
        sb.append(1);
        sb.append(".2.2");
        return sb.toString();
    }

    /* renamed from: a */
    public static String m378a(Context context) {
        PackageManager packageManager = context.getPackageManager();
        for (String str : f260b) {
            ResolveInfo resolveService = packageManager.resolveService(new Intent("com.google.android.youtube.api.service.START").setPackage(str), 0);
            if (resolveService != null && resolveService.serviceInfo != null && resolveService.serviceInfo.packageName != null) {
                return resolveService.serviceInfo.packageName;
            }
        }
        return packageManager.hasSystemFeature("android.software.leanback") ? "com.google.android.youtube.tv" : packageManager.hasSystemFeature("com.google.android.tv") ? "com.google.android.youtube.googletv" : "com.google.android.youtube";
    }

    /* renamed from: a */
    public static boolean m379a(Context context, String str) {
        try {
            Resources resourcesForApplication = context.getPackageManager().getResourcesForApplication(str);
            if (str.equals("com.google.android.youtube.googletvdev")) {
                str = "com.google.android.youtube.googletv";
            }
            int identifier = resourcesForApplication.getIdentifier("youtube_api_version_code", "integer", str);
            return identifier == 0 || 12 > resourcesForApplication.getInteger(identifier) / 100;
        } catch (PackageManager.NameNotFoundException e) {
            return true;
        }
    }

    /* renamed from: a */
    public static boolean m380a(PackageManager packageManager) {
        return packageManager.hasSystemFeature("com.google.android.tv");
    }

    /* renamed from: b */
    public static Context m381b(Context context) {
        try {
            return context.createPackageContext(m378a(context), 3);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    /* renamed from: b */
    public static Intent m382b(String str) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(f259a.buildUpon().appendQueryParameter("id", str).build());
        intent.setPackage("com.android.vending");
        intent.addFlags(524288);
        return intent;
    }

    /* renamed from: b */
    public static boolean m383b(PackageManager packageManager) {
        return packageManager.hasSystemFeature("android.software.leanback");
    }

    /* renamed from: c */
    public static int m384c(Context context) {
        Context b = m381b(context);
        int identifier = b != null ? b.getResources().getIdentifier("clientTheme", "style", m378a(context)) : 0;
        if (identifier != 0) {
            return identifier;
        }
        if (Build.VERSION.SDK_INT >= 14) {
            return 16974120;
        }
        return Build.VERSION.SDK_INT >= 11 ? 16973931 : 16973829;
    }

    /* renamed from: d */
    public static String m385d(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalStateException("Cannot retrieve calling Context's PackageInfo", e);
        }
    }
}
