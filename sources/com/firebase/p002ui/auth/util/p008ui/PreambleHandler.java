package com.firebase.p002ui.auth.util.p008ui;

import android.content.Context;
import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import com.firebase.p002ui.auth.C0719R;
import com.firebase.p002ui.auth.data.model.FlowParameters;
import java.lang.ref.WeakReference;

/* renamed from: com.firebase.ui.auth.util.ui.PreambleHandler */
public class PreambleHandler {
    private static final String BTN_TARGET = "%BTN%";
    private static final int NO_BUTTON = -1;
    private static final String PP_TARGET = "%PP%";
    private static final String TOS_TARGET = "%TOS%";
    private SpannableStringBuilder mBuilder;
    private final int mButtonText;
    private final Context mContext;
    private final FlowParameters mFlowParameters;
    private final ForegroundColorSpan mLinkSpan = new ForegroundColorSpan(ContextCompat.getColor(this.mContext, C0719R.C0720color.fui_linkColor));

    private PreambleHandler(Context context, FlowParameters parameters, int buttonText) {
        this.mContext = context;
        this.mFlowParameters = parameters;
        this.mButtonText = buttonText;
    }

    public static void setup(Context context, FlowParameters parameters, int textViewText, TextView textView) {
        setup(context, parameters, -1, textViewText, textView);
    }

    public static void setup(Context context, FlowParameters parameters, int buttonText, int textViewText, TextView textView) {
        PreambleHandler handler = new PreambleHandler(context, parameters, buttonText);
        handler.initPreamble(textViewText);
        handler.setPreamble(textView);
    }

    private void setPreamble(TextView textView) {
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(this.mBuilder);
    }

    private void initPreamble(int textViewText) {
        String withTargets = getPreambleStringWithTargets(textViewText, this.mButtonText != -1);
        if (withTargets != null) {
            this.mBuilder = new SpannableStringBuilder(withTargets);
            replaceTarget(BTN_TARGET, this.mButtonText);
            replaceUrlTarget(TOS_TARGET, C0719R.string.fui_terms_of_service, this.mFlowParameters.termsOfServiceUrl);
            replaceUrlTarget(PP_TARGET, C0719R.string.fui_privacy_policy, this.mFlowParameters.privacyPolicyUrl);
        }
    }

    private void replaceTarget(String target, int replacementRes) {
        int targetIndex = this.mBuilder.toString().indexOf(target);
        if (targetIndex != -1) {
            this.mBuilder.replace(targetIndex, target.length() + targetIndex, this.mContext.getString(replacementRes));
        }
    }

    private void replaceUrlTarget(String target, int replacementRes, String url) {
        int targetIndex = this.mBuilder.toString().indexOf(target);
        if (targetIndex != -1) {
            String replacement = this.mContext.getString(replacementRes);
            this.mBuilder.replace(targetIndex, target.length() + targetIndex, replacement);
            int end = replacement.length() + targetIndex;
            this.mBuilder.setSpan(this.mLinkSpan, targetIndex, end, 0);
            this.mBuilder.setSpan(new CustomTabsSpan(this.mContext, url), targetIndex, end, 0);
        }
    }

    private String getPreambleStringWithTargets(int textViewText, boolean hasButton) {
        boolean termsOfServiceUrlProvided = !TextUtils.isEmpty(this.mFlowParameters.termsOfServiceUrl);
        boolean privacyPolicyUrlProvided = !TextUtils.isEmpty(this.mFlowParameters.privacyPolicyUrl);
        if (!termsOfServiceUrlProvided || !privacyPolicyUrlProvided) {
            return null;
        }
        return this.mContext.getString(textViewText, hasButton ? new Object[]{BTN_TARGET, TOS_TARGET, PP_TARGET} : new Object[]{TOS_TARGET, PP_TARGET});
    }

    /* renamed from: com.firebase.ui.auth.util.ui.PreambleHandler$CustomTabsSpan */
    private static final class CustomTabsSpan extends ClickableSpan {
        private final WeakReference<Context> mContext;
        private final CustomTabsIntent mCustomTabsIntent;
        private final String mUrl;

        public CustomTabsSpan(Context context, String url) {
            this.mContext = new WeakReference<>(context);
            this.mUrl = url;
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(C0719R.attr.colorPrimary, typedValue, true);
            this.mCustomTabsIntent = new CustomTabsIntent.Builder().setToolbarColor(typedValue.data).setShowTitle(true).build();
        }

        public void onClick(View widget) {
            Context context = (Context) this.mContext.get();
            if (context != null) {
                this.mCustomTabsIntent.launchUrl(context, Uri.parse(this.mUrl));
            }
        }
    }
}
