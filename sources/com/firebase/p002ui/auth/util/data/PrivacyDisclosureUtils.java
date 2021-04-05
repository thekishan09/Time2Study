package com.firebase.p002ui.auth.util.data;

import android.content.Context;
import android.widget.TextView;
import com.firebase.p002ui.auth.C0719R;
import com.firebase.p002ui.auth.data.model.FlowParameters;
import com.firebase.p002ui.auth.util.p008ui.PreambleHandler;

/* renamed from: com.firebase.ui.auth.util.data.PrivacyDisclosureUtils */
public class PrivacyDisclosureUtils {
    private static final int NO_TOS_OR_PP = -1;

    public static void setupTermsOfServiceAndPrivacyPolicyText(Context context, FlowParameters flowParameters, TextView termsText) {
        PreambleHandler.setup(context, flowParameters, getGlobalTermsStringResource(flowParameters), termsText);
    }

    public static void setupTermsOfServiceFooter(Context context, FlowParameters flowParameters, TextView footerText) {
        PreambleHandler.setup(context, flowParameters, getGlobalTermsFooterStringResource(flowParameters), footerText);
    }

    public static void setupTermsOfServiceAndPrivacyPolicySmsText(Context context, FlowParameters flowParameters, TextView termsText) {
        PreambleHandler.setup(context, flowParameters, C0719R.string.fui_verify_phone_number, getTermsSmsStringResource(flowParameters), termsText);
    }

    private static int getGlobalTermsStringResource(FlowParameters flowParameters) {
        boolean termsOfServiceUrlProvided = flowParameters.isTermsOfServiceUrlProvided();
        boolean privacyPolicyUrlProvided = flowParameters.isPrivacyPolicyUrlProvided();
        if (!termsOfServiceUrlProvided || !privacyPolicyUrlProvided) {
            return -1;
        }
        return C0719R.string.fui_tos_and_pp;
    }

    private static int getGlobalTermsFooterStringResource(FlowParameters flowParameters) {
        boolean termsOfServiceUrlProvided = flowParameters.isTermsOfServiceUrlProvided();
        boolean privacyPolicyUrlProvided = flowParameters.isPrivacyPolicyUrlProvided();
        if (!termsOfServiceUrlProvided || !privacyPolicyUrlProvided) {
            return -1;
        }
        return C0719R.string.fui_tos_and_pp_footer;
    }

    private static int getTermsSmsStringResource(FlowParameters flowParameters) {
        boolean termsOfServiceUrlProvided = flowParameters.isTermsOfServiceUrlProvided();
        boolean privacyPolicyUrlProvided = flowParameters.isPrivacyPolicyUrlProvided();
        if (!termsOfServiceUrlProvided || !privacyPolicyUrlProvided) {
            return -1;
        }
        return C0719R.string.fui_sms_terms_of_service_and_privacy_policy_extended;
    }
}
