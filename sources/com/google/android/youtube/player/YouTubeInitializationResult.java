package com.google.android.youtube.player;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import com.google.android.youtube.player.internal.C1098ab;
import com.google.android.youtube.player.internal.C1161y;

public enum YouTubeInitializationResult {
    SUCCESS,
    INTERNAL_ERROR,
    UNKNOWN_ERROR,
    SERVICE_MISSING,
    SERVICE_VERSION_UPDATE_REQUIRED,
    SERVICE_DISABLED,
    SERVICE_INVALID,
    ERROR_CONNECTING_TO_SERVICE,
    CLIENT_LIBRARY_UPDATE_REQUIRED,
    NETWORK_ERROR,
    DEVELOPER_KEY_INVALID,
    INVALID_APPLICATION_SIGNATURE;

    /* renamed from: com.google.android.youtube.player.YouTubeInitializationResult$1 */
    static /* synthetic */ class C10871 {

        /* renamed from: a */
        static final /* synthetic */ int[] f140a = null;

        static {
            int[] iArr = new int[YouTubeInitializationResult.values().length];
            f140a = iArr;
            try {
                iArr[YouTubeInitializationResult.SERVICE_MISSING.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f140a[YouTubeInitializationResult.SERVICE_DISABLED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f140a[YouTubeInitializationResult.SERVICE_VERSION_UPDATE_REQUIRED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* renamed from: com.google.android.youtube.player.YouTubeInitializationResult$a */
    private static final class C1088a implements DialogInterface.OnClickListener {

        /* renamed from: a */
        private final Activity f141a;

        /* renamed from: b */
        private final Intent f142b;

        /* renamed from: c */
        private final int f143c;

        public C1088a(Activity activity, Intent intent, int i) {
            this.f141a = (Activity) C1098ab.m123a(activity);
            this.f142b = (Intent) C1098ab.m123a(intent);
            this.f143c = ((Integer) C1098ab.m123a(Integer.valueOf(i))).intValue();
        }

        public final void onClick(DialogInterface dialogInterface, int i) {
            try {
                this.f141a.startActivityForResult(this.f142b, this.f143c);
                dialogInterface.dismiss();
            } catch (ActivityNotFoundException e) {
                C1161y.m374a("Can't perform resolution for YouTubeInitalizationError", (Throwable) e);
            }
        }
    }

    public final Dialog getErrorDialog(Activity activity, int i) {
        return getErrorDialog(activity, i, (DialogInterface.OnCancelListener) null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0042  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x008d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.app.Dialog getErrorDialog(android.app.Activity r6, int r7, android.content.DialogInterface.OnCancelListener r8) {
        /*
            r5 = this;
            android.app.AlertDialog$Builder r0 = new android.app.AlertDialog$Builder
            r0.<init>(r6)
            if (r8 == 0) goto L_0x000a
            r0.setOnCancelListener(r8)
        L_0x000a:
            int[] r8 = com.google.android.youtube.player.YouTubeInitializationResult.C10871.f140a
            int r1 = r5.ordinal()
            r8 = r8[r1]
            r1 = 3
            r2 = 2
            r3 = 1
            if (r8 == r3) goto L_0x0026
            if (r8 == r2) goto L_0x001d
            if (r8 == r1) goto L_0x0026
            r8 = 0
            goto L_0x002e
        L_0x001d:
            java.lang.String r8 = com.google.android.youtube.player.internal.C1162z.m378a((android.content.Context) r6)
            android.content.Intent r8 = com.google.android.youtube.player.internal.C1162z.m376a((java.lang.String) r8)
            goto L_0x002e
        L_0x0026:
            java.lang.String r8 = com.google.android.youtube.player.internal.C1162z.m378a((android.content.Context) r6)
            android.content.Intent r8 = com.google.android.youtube.player.internal.C1162z.m382b((java.lang.String) r8)
        L_0x002e:
            com.google.android.youtube.player.YouTubeInitializationResult$a r4 = new com.google.android.youtube.player.YouTubeInitializationResult$a
            r4.<init>(r6, r8, r7)
            com.google.android.youtube.player.internal.m r7 = new com.google.android.youtube.player.internal.m
            r7.<init>(r6)
            int[] r6 = com.google.android.youtube.player.YouTubeInitializationResult.C10871.f140a
            int r8 = r5.ordinal()
            r6 = r6[r8]
            if (r6 == r3) goto L_0x008d
            if (r6 == r2) goto L_0x007e
            if (r6 == r1) goto L_0x0067
            java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException
            java.lang.String r7 = "Unexpected errorReason: "
            java.lang.String r8 = r5.name()
            java.lang.String r8 = java.lang.String.valueOf(r8)
            int r0 = r8.length()
            if (r0 == 0) goto L_0x005d
            java.lang.String r7 = r7.concat(r8)
            goto L_0x0063
        L_0x005d:
            java.lang.String r8 = new java.lang.String
            r8.<init>(r7)
            r7 = r8
        L_0x0063:
            r6.<init>(r7)
            throw r6
        L_0x0067:
            java.lang.String r6 = r7.f203h
            android.app.AlertDialog$Builder r6 = r0.setTitle(r6)
            java.lang.String r8 = r7.f204i
            android.app.AlertDialog$Builder r6 = r6.setMessage(r8)
            java.lang.String r7 = r7.f205j
        L_0x0075:
            android.app.AlertDialog$Builder r6 = r6.setPositiveButton(r7, r4)
            android.app.AlertDialog r6 = r6.create()
            return r6
        L_0x007e:
            java.lang.String r6 = r7.f200e
            android.app.AlertDialog$Builder r6 = r0.setTitle(r6)
            java.lang.String r8 = r7.f201f
            android.app.AlertDialog$Builder r6 = r6.setMessage(r8)
            java.lang.String r7 = r7.f202g
            goto L_0x0075
        L_0x008d:
            java.lang.String r6 = r7.f197b
            android.app.AlertDialog$Builder r6 = r0.setTitle(r6)
            java.lang.String r8 = r7.f198c
            android.app.AlertDialog$Builder r6 = r6.setMessage(r8)
            java.lang.String r7 = r7.f199d
            goto L_0x0075
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.youtube.player.YouTubeInitializationResult.getErrorDialog(android.app.Activity, int, android.content.DialogInterface$OnCancelListener):android.app.Dialog");
    }

    public final boolean isUserRecoverableError() {
        int i = C10871.f140a[ordinal()];
        return i == 1 || i == 2 || i == 3;
    }
}
