package com.firebase.p002ui.auth.p007ui.email;

import android.content.Context;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import com.firebase.p002ui.auth.C0719R;
import com.firebase.p002ui.auth.IdpResponse;
import com.firebase.p002ui.auth.p007ui.InvisibleFragmentBase;
import com.firebase.p002ui.auth.util.ExtraConstants;
import com.firebase.p002ui.auth.util.data.PrivacyDisclosureUtils;
import com.firebase.p002ui.auth.util.p008ui.TextHelper;
import com.firebase.p002ui.auth.viewmodel.ResourceObserver;
import com.firebase.p002ui.auth.viewmodel.email.EmailLinkSendEmailHandler;
import com.google.firebase.auth.ActionCodeSettings;

/* renamed from: com.firebase.ui.auth.ui.email.EmailLinkFragment */
public class EmailLinkFragment extends InvisibleFragmentBase {
    private static final String EMAIL_SENT = "emailSent";
    public static final String TAG = "EmailLinkFragment";
    private EmailLinkSendEmailHandler mEmailLinkSendEmailHandler;
    /* access modifiers changed from: private */
    public boolean mEmailSent;
    /* access modifiers changed from: private */
    public TroubleSigningInListener mListener;
    /* access modifiers changed from: private */
    public ScrollView mTopLevelView;

    /* renamed from: com.firebase.ui.auth.ui.email.EmailLinkFragment$TroubleSigningInListener */
    interface TroubleSigningInListener {
        void onSendEmailFailure(Exception exc);

        void onTroubleSigningIn(String str);
    }

    public static EmailLinkFragment newInstance(String email, ActionCodeSettings settings) {
        return newInstance(email, settings, (IdpResponse) null, false);
    }

    public static EmailLinkFragment newInstance(String email, ActionCodeSettings actionCodeSettings, IdpResponse idpResponseForLinking, boolean forceSameDevice) {
        EmailLinkFragment fragment = new EmailLinkFragment();
        Bundle args = new Bundle();
        args.putString(ExtraConstants.EMAIL, email);
        args.putParcelable(ExtraConstants.ACTION_CODE_SETTINGS, actionCodeSettings);
        args.putParcelable(ExtraConstants.IDP_RESPONSE, idpResponseForLinking);
        args.putBoolean(ExtraConstants.FORCE_SAME_DEVICE, forceSameDevice);
        fragment.setArguments(args);
        return fragment;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        FragmentActivity activity = getActivity();
        if (activity instanceof TroubleSigningInListener) {
            this.mListener = (TroubleSigningInListener) activity;
            return;
        }
        throw new IllegalStateException("Activity must implement TroubleSigningInListener");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(C0719R.layout.fui_email_link_sign_in_layout, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            this.mEmailSent = savedInstanceState.getBoolean(EMAIL_SENT);
        }
        ScrollView scrollView = (ScrollView) view.findViewById(C0719R.C0722id.top_level_view);
        this.mTopLevelView = scrollView;
        if (!this.mEmailSent) {
            scrollView.setVisibility(8);
        }
        String email = getArguments().getString(ExtraConstants.EMAIL);
        setBodyText(view, email);
        setOnClickListeners(view, email);
        setPrivacyFooter(view);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initHandler();
        String email = getArguments().getString(ExtraConstants.EMAIL);
        ActionCodeSettings actionCodeSettings = (ActionCodeSettings) getArguments().getParcelable(ExtraConstants.ACTION_CODE_SETTINGS);
        IdpResponse idpResponseForLinking = (IdpResponse) getArguments().getParcelable(ExtraConstants.IDP_RESPONSE);
        boolean forceSameDevice = getArguments().getBoolean(ExtraConstants.FORCE_SAME_DEVICE);
        if (!this.mEmailSent) {
            this.mEmailLinkSendEmailHandler.sendSignInLinkToEmail(email, actionCodeSettings, idpResponseForLinking, forceSameDevice);
        }
    }

    private void initHandler() {
        EmailLinkSendEmailHandler emailLinkSendEmailHandler = (EmailLinkSendEmailHandler) ViewModelProviders.m14of((Fragment) this).get(EmailLinkSendEmailHandler.class);
        this.mEmailLinkSendEmailHandler = emailLinkSendEmailHandler;
        emailLinkSendEmailHandler.init(getFlowParams());
        this.mEmailLinkSendEmailHandler.getOperation().observe(this, new ResourceObserver<String>(this, C0719R.string.fui_progress_dialog_sending) {
            /* access modifiers changed from: protected */
            public void onSuccess(String email) {
                Log.w(EmailLinkFragment.TAG, "Email for email link sign in sent successfully.");
                EmailLinkFragment.this.doAfterTimeout(new Runnable() {
                    public void run() {
                        EmailLinkFragment.this.mTopLevelView.setVisibility(0);
                    }
                });
                boolean unused = EmailLinkFragment.this.mEmailSent = true;
            }

            /* access modifiers changed from: protected */
            public void onFailure(Exception e) {
                EmailLinkFragment.this.mListener.onSendEmailFailure(e);
            }
        });
    }

    private void setBodyText(View view, String email) {
        String bodyText = getString(C0719R.string.fui_email_link_email_sent, email);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(bodyText);
        TextHelper.boldAllOccurencesOfText(spannableStringBuilder, bodyText, email);
        ((TextView) view.findViewById(C0719R.C0722id.sign_in_email_sent_text)).setText(spannableStringBuilder);
    }

    private void setOnClickListeners(View view, final String email) {
        view.findViewById(C0719R.C0722id.trouble_signing_in).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EmailLinkFragment.this.mListener.onTroubleSigningIn(email);
            }
        });
    }

    private void setPrivacyFooter(View view) {
        PrivacyDisclosureUtils.setupTermsOfServiceFooter(requireContext(), getFlowParams(), (TextView) view.findViewById(C0719R.C0722id.email_footer_tos_and_pp_text));
    }

    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putBoolean(EMAIL_SENT, this.mEmailSent);
    }
}
