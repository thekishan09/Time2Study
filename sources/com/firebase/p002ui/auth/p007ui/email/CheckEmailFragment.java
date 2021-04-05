package com.firebase.p002ui.auth.p007ui.email;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import com.firebase.p002ui.auth.C0719R;
import com.firebase.p002ui.auth.FirebaseUiException;
import com.firebase.p002ui.auth.data.model.FlowParameters;
import com.firebase.p002ui.auth.data.model.User;
import com.firebase.p002ui.auth.p007ui.FragmentBase;
import com.firebase.p002ui.auth.util.ExtraConstants;
import com.firebase.p002ui.auth.util.data.PrivacyDisclosureUtils;
import com.firebase.p002ui.auth.util.p008ui.ImeHelper;
import com.firebase.p002ui.auth.util.p008ui.fieldvalidators.EmailFieldValidator;
import com.firebase.p002ui.auth.viewmodel.ResourceObserver;
import com.google.android.material.textfield.TextInputLayout;

/* renamed from: com.firebase.ui.auth.ui.email.CheckEmailFragment */
public class CheckEmailFragment extends FragmentBase implements View.OnClickListener, ImeHelper.DonePressedListener {
    public static final String TAG = "CheckEmailFragment";
    /* access modifiers changed from: private */
    public EditText mEmailEditText;
    private EmailFieldValidator mEmailFieldValidator;
    private TextInputLayout mEmailLayout;
    private CheckEmailHandler mHandler;
    /* access modifiers changed from: private */
    public CheckEmailListener mListener;
    private Button mNextButton;
    private ProgressBar mProgressBar;

    /* renamed from: com.firebase.ui.auth.ui.email.CheckEmailFragment$CheckEmailListener */
    interface CheckEmailListener {
        void onDeveloperFailure(Exception exc);

        void onExistingEmailUser(User user);

        void onExistingIdpUser(User user);

        void onNewUser(User user);
    }

    public static CheckEmailFragment newInstance(String email) {
        CheckEmailFragment fragment = new CheckEmailFragment();
        Bundle args = new Bundle();
        args.putString(ExtraConstants.EMAIL, email);
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(C0719R.layout.fui_check_email_layout, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.mNextButton = (Button) view.findViewById(C0719R.C0722id.button_next);
        this.mProgressBar = (ProgressBar) view.findViewById(C0719R.C0722id.top_progress_bar);
        this.mEmailLayout = (TextInputLayout) view.findViewById(C0719R.C0722id.email_layout);
        this.mEmailEditText = (EditText) view.findViewById(C0719R.C0722id.email);
        this.mEmailFieldValidator = new EmailFieldValidator(this.mEmailLayout);
        this.mEmailLayout.setOnClickListener(this);
        this.mEmailEditText.setOnClickListener(this);
        TextView headerText = (TextView) view.findViewById(C0719R.C0722id.header_text);
        if (headerText != null) {
            headerText.setVisibility(8);
        }
        ImeHelper.setImeOnDoneListener(this.mEmailEditText, this);
        if (Build.VERSION.SDK_INT >= 26 && getFlowParams().enableHints) {
            this.mEmailEditText.setImportantForAutofill(2);
        }
        this.mNextButton.setOnClickListener(this);
        TextView termsText = (TextView) view.findViewById(C0719R.C0722id.email_tos_and_pp_text);
        TextView footerText = (TextView) view.findViewById(C0719R.C0722id.email_footer_tos_and_pp_text);
        FlowParameters flowParameters = getFlowParams();
        if (!flowParameters.shouldShowProviderChoice()) {
            PrivacyDisclosureUtils.setupTermsOfServiceAndPrivacyPolicyText(requireContext(), flowParameters, termsText);
            return;
        }
        termsText.setVisibility(8);
        PrivacyDisclosureUtils.setupTermsOfServiceFooter(requireContext(), flowParameters, footerText);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CheckEmailHandler checkEmailHandler = (CheckEmailHandler) ViewModelProviders.m14of((Fragment) this).get(CheckEmailHandler.class);
        this.mHandler = checkEmailHandler;
        checkEmailHandler.init(getFlowParams());
        FragmentActivity activity = getActivity();
        if (activity instanceof CheckEmailListener) {
            this.mListener = (CheckEmailListener) activity;
            this.mHandler.getOperation().observe(this, new ResourceObserver<User>(this, C0719R.string.fui_progress_dialog_checking_accounts) {
                /* access modifiers changed from: protected */
                public void onSuccess(User user) {
                    String email = user.getEmail();
                    String provider = user.getProviderId();
                    CheckEmailFragment.this.mEmailEditText.setText(email);
                    if (provider == null) {
                        CheckEmailFragment.this.mListener.onNewUser(new User.Builder("password", email).setName(user.getName()).setPhotoUri(user.getPhotoUri()).build());
                    } else if (provider.equals("password") || provider.equals("emailLink")) {
                        CheckEmailFragment.this.mListener.onExistingEmailUser(user);
                    } else {
                        CheckEmailFragment.this.mListener.onExistingIdpUser(user);
                    }
                }

                /* access modifiers changed from: protected */
                public void onFailure(Exception e) {
                    if ((e instanceof FirebaseUiException) && ((FirebaseUiException) e).getErrorCode() == 3) {
                        CheckEmailFragment.this.mListener.onDeveloperFailure(e);
                    }
                }
            });
            if (savedInstanceState == null) {
                String email = getArguments().getString(ExtraConstants.EMAIL);
                if (!TextUtils.isEmpty(email)) {
                    this.mEmailEditText.setText(email);
                    validateAndProceed();
                } else if (getFlowParams().enableHints) {
                    this.mHandler.fetchCredential();
                }
            }
        } else {
            throw new IllegalStateException("Activity must implement CheckEmailListener");
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.mHandler.onActivityResult(requestCode, resultCode, data);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == C0719R.C0722id.button_next) {
            validateAndProceed();
        } else if (id == C0719R.C0722id.email_layout || id == C0719R.C0722id.email) {
            this.mEmailLayout.setError((CharSequence) null);
        }
    }

    public void onDonePressed() {
        validateAndProceed();
    }

    private void validateAndProceed() {
        String email = this.mEmailEditText.getText().toString();
        if (this.mEmailFieldValidator.validate(email)) {
            this.mHandler.fetchProvider(email);
        }
    }

    public void showProgress(int message) {
        this.mNextButton.setEnabled(false);
        this.mProgressBar.setVisibility(0);
    }

    public void hideProgress() {
        this.mNextButton.setEnabled(true);
        this.mProgressBar.setVisibility(4);
    }
}
