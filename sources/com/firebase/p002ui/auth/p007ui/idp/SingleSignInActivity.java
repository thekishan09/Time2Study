package com.firebase.p002ui.auth.p007ui.idp;

import android.content.Context;
import android.content.Intent;
import com.firebase.p002ui.auth.data.model.FlowParameters;
import com.firebase.p002ui.auth.data.model.User;
import com.firebase.p002ui.auth.p007ui.InvisibleActivityBase;
import com.firebase.p002ui.auth.util.ExtraConstants;
import com.firebase.p002ui.auth.viewmodel.ProviderSignInBase;
import com.firebase.p002ui.auth.viewmodel.idp.SocialProviderResponseHandler;

/* renamed from: com.firebase.ui.auth.ui.idp.SingleSignInActivity */
public class SingleSignInActivity extends InvisibleActivityBase {
    /* access modifiers changed from: private */
    public SocialProviderResponseHandler mHandler;
    private ProviderSignInBase<?> mProvider;

    public static Intent createIntent(Context context, FlowParameters flowParams, User user) {
        return createBaseIntent(context, SingleSignInActivity.class, flowParams).putExtra(ExtraConstants.USER, user);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0076, code lost:
        if (r1.equals("google.com") != false) goto L_0x0084;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onCreate(android.os.Bundle r11) {
        /*
            r10 = this;
            super.onCreate(r11)
            android.content.Intent r0 = r10.getIntent()
            com.firebase.ui.auth.data.model.User r0 = com.firebase.p002ui.auth.data.model.User.getUser((android.content.Intent) r0)
            java.lang.String r1 = r0.getProviderId()
            com.firebase.ui.auth.data.model.FlowParameters r2 = r10.getFlowParams()
            java.util.List<com.firebase.ui.auth.AuthUI$IdpConfig> r2 = r2.providers
            com.firebase.ui.auth.AuthUI$IdpConfig r2 = com.firebase.p002ui.auth.util.data.ProviderUtils.getConfigFromIdps(r2, r1)
            r3 = 0
            r4 = 3
            if (r2 != 0) goto L_0x003c
            com.firebase.ui.auth.FirebaseUiException r5 = new com.firebase.ui.auth.FirebaseUiException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Provider not enabled: "
            r6.append(r7)
            r6.append(r1)
            java.lang.String r6 = r6.toString()
            r5.<init>((int) r4, (java.lang.String) r6)
            android.content.Intent r4 = com.firebase.p002ui.auth.IdpResponse.getErrorIntent(r5)
            r10.finish(r3, r4)
            return
        L_0x003c:
            androidx.lifecycle.ViewModelProvider r5 = androidx.lifecycle.ViewModelProviders.m16of((androidx.fragment.app.FragmentActivity) r10)
            java.lang.Class<com.firebase.ui.auth.viewmodel.idp.SocialProviderResponseHandler> r6 = com.firebase.p002ui.auth.viewmodel.idp.SocialProviderResponseHandler.class
            androidx.lifecycle.ViewModel r6 = r5.get(r6)
            com.firebase.ui.auth.viewmodel.idp.SocialProviderResponseHandler r6 = (com.firebase.p002ui.auth.viewmodel.idp.SocialProviderResponseHandler) r6
            r10.mHandler = r6
            com.firebase.ui.auth.data.model.FlowParameters r7 = r10.getFlowParams()
            r6.init(r7)
            r6 = -1
            int r7 = r1.hashCode()
            r8 = 2
            r9 = 1
            switch(r7) {
                case -1830313082: goto L_0x0079;
                case -1536293812: goto L_0x0070;
                case -364826023: goto L_0x0066;
                case 1985010934: goto L_0x005c;
                default: goto L_0x005b;
            }
        L_0x005b:
            goto L_0x0083
        L_0x005c:
            java.lang.String r3 = "github.com"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x005b
            r3 = 3
            goto L_0x0084
        L_0x0066:
            java.lang.String r3 = "facebook.com"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x005b
            r3 = 1
            goto L_0x0084
        L_0x0070:
            java.lang.String r7 = "google.com"
            boolean r7 = r1.equals(r7)
            if (r7 == 0) goto L_0x005b
            goto L_0x0084
        L_0x0079:
            java.lang.String r3 = "twitter.com"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x005b
            r3 = 2
            goto L_0x0084
        L_0x0083:
            r3 = -1
        L_0x0084:
            if (r3 == 0) goto L_0x00ce
            if (r3 == r9) goto L_0x00c0
            if (r3 == r8) goto L_0x00b1
            if (r3 != r4) goto L_0x009a
            java.lang.Class<com.firebase.ui.auth.viewmodel.ProviderSignInBase<com.firebase.ui.auth.AuthUI$IdpConfig>> r3 = com.firebase.p002ui.auth.data.remote.GitHubSignInHandlerBridge.HANDLER_CLASS
            androidx.lifecycle.ViewModel r3 = r5.get(r3)
            com.firebase.ui.auth.viewmodel.ProviderSignInBase r3 = (com.firebase.p002ui.auth.viewmodel.ProviderSignInBase) r3
            r3.init(r2)
            r10.mProvider = r3
            goto L_0x00e5
        L_0x009a:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r6 = "Invalid provider id: "
            r4.append(r6)
            r4.append(r1)
            java.lang.String r4 = r4.toString()
            r3.<init>(r4)
            throw r3
        L_0x00b1:
            java.lang.Class<com.firebase.ui.auth.data.remote.TwitterSignInHandler> r3 = com.firebase.p002ui.auth.data.remote.TwitterSignInHandler.class
            androidx.lifecycle.ViewModel r3 = r5.get(r3)
            com.firebase.ui.auth.data.remote.TwitterSignInHandler r3 = (com.firebase.p002ui.auth.data.remote.TwitterSignInHandler) r3
            r4 = 0
            r3.init(r4)
            r10.mProvider = r3
            goto L_0x00e5
        L_0x00c0:
            java.lang.Class<com.firebase.ui.auth.data.remote.FacebookSignInHandler> r3 = com.firebase.p002ui.auth.data.remote.FacebookSignInHandler.class
            androidx.lifecycle.ViewModel r3 = r5.get(r3)
            com.firebase.ui.auth.data.remote.FacebookSignInHandler r3 = (com.firebase.p002ui.auth.data.remote.FacebookSignInHandler) r3
            r3.init(r2)
            r10.mProvider = r3
            goto L_0x00e5
        L_0x00ce:
            java.lang.Class<com.firebase.ui.auth.data.remote.GoogleSignInHandler> r3 = com.firebase.p002ui.auth.data.remote.GoogleSignInHandler.class
            androidx.lifecycle.ViewModel r3 = r5.get(r3)
            com.firebase.ui.auth.data.remote.GoogleSignInHandler r3 = (com.firebase.p002ui.auth.data.remote.GoogleSignInHandler) r3
            com.firebase.ui.auth.data.remote.GoogleSignInHandler$Params r4 = new com.firebase.ui.auth.data.remote.GoogleSignInHandler$Params
            java.lang.String r6 = r0.getEmail()
            r4.<init>(r2, r6)
            r3.init(r4)
            r10.mProvider = r3
        L_0x00e5:
            com.firebase.ui.auth.viewmodel.ProviderSignInBase<?> r3 = r10.mProvider
            androidx.lifecycle.LiveData r3 = r3.getOperation()
            com.firebase.ui.auth.ui.idp.SingleSignInActivity$1 r4 = new com.firebase.ui.auth.ui.idp.SingleSignInActivity$1
            r4.<init>(r10)
            r3.observe(r10, r4)
            com.firebase.ui.auth.viewmodel.idp.SocialProviderResponseHandler r3 = r10.mHandler
            androidx.lifecycle.LiveData r3 = r3.getOperation()
            com.firebase.ui.auth.ui.idp.SingleSignInActivity$2 r4 = new com.firebase.ui.auth.ui.idp.SingleSignInActivity$2
            r4.<init>(r10)
            r3.observe(r10, r4)
            com.firebase.ui.auth.viewmodel.idp.SocialProviderResponseHandler r3 = r10.mHandler
            androidx.lifecycle.LiveData r3 = r3.getOperation()
            java.lang.Object r3 = r3.getValue()
            if (r3 != 0) goto L_0x0112
            com.firebase.ui.auth.viewmodel.ProviderSignInBase<?> r3 = r10.mProvider
            r3.startSignIn(r10)
        L_0x0112:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.firebase.p002ui.auth.p007ui.idp.SingleSignInActivity.onCreate(android.os.Bundle):void");
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.mHandler.onActivityResult(requestCode, resultCode, data);
        this.mProvider.onActivityResult(requestCode, resultCode, data);
    }
}
