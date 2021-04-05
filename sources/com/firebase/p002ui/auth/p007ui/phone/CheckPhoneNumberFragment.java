package com.firebase.p002ui.auth.p007ui.phone;

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
import androidx.lifecycle.ViewModelProviders;
import com.firebase.p002ui.auth.C0719R;
import com.firebase.p002ui.auth.data.model.FlowParameters;
import com.firebase.p002ui.auth.data.model.PhoneNumber;
import com.firebase.p002ui.auth.p007ui.FragmentBase;
import com.firebase.p002ui.auth.util.ExtraConstants;
import com.firebase.p002ui.auth.util.data.PhoneNumberUtils;
import com.firebase.p002ui.auth.util.data.PrivacyDisclosureUtils;
import com.firebase.p002ui.auth.util.p008ui.ImeHelper;
import com.firebase.p002ui.auth.viewmodel.ResourceObserver;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Locale;

/* renamed from: com.firebase.ui.auth.ui.phone.CheckPhoneNumberFragment */
public class CheckPhoneNumberFragment extends FragmentBase implements View.OnClickListener {
    public static final String TAG = "VerifyPhoneFragment";
    private boolean mCalled;
    private CheckPhoneHandler mCheckPhoneHandler;
    private CountryListSpinner mCountryListSpinner;
    private TextView mFooterText;
    private EditText mPhoneEditText;
    /* access modifiers changed from: private */
    public TextInputLayout mPhoneInputLayout;
    private ProgressBar mProgressBar;
    private TextView mSmsTermsText;
    private Button mSubmitButton;
    private PhoneNumberVerificationHandler mVerificationHandler;

    public static CheckPhoneNumberFragment newInstance(Bundle params) {
        CheckPhoneNumberFragment fragment = new CheckPhoneNumberFragment();
        Bundle args = new Bundle();
        args.putBundle(ExtraConstants.PARAMS, params);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mVerificationHandler = (PhoneNumberVerificationHandler) ViewModelProviders.m16of(requireActivity()).get(PhoneNumberVerificationHandler.class);
        this.mCheckPhoneHandler = (CheckPhoneHandler) ViewModelProviders.m16of(requireActivity()).get(CheckPhoneHandler.class);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(C0719R.layout.fui_phone_layout, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.mProgressBar = (ProgressBar) view.findViewById(C0719R.C0722id.top_progress_bar);
        this.mSubmitButton = (Button) view.findViewById(C0719R.C0722id.send_code);
        this.mCountryListSpinner = (CountryListSpinner) view.findViewById(C0719R.C0722id.country_list);
        this.mPhoneInputLayout = (TextInputLayout) view.findViewById(C0719R.C0722id.phone_layout);
        this.mPhoneEditText = (EditText) view.findViewById(C0719R.C0722id.phone_number);
        this.mSmsTermsText = (TextView) view.findViewById(C0719R.C0722id.send_sms_tos);
        this.mFooterText = (TextView) view.findViewById(C0719R.C0722id.email_footer_tos_and_pp_text);
        this.mSmsTermsText.setText(getString(C0719R.string.fui_sms_terms_of_service, getString(C0719R.string.fui_verify_phone_number)));
        if (Build.VERSION.SDK_INT >= 26 && getFlowParams().enableHints) {
            this.mPhoneEditText.setImportantForAutofill(2);
        }
        requireActivity().setTitle(getString(C0719R.string.fui_verify_phone_number_title));
        ImeHelper.setImeOnDoneListener(this.mPhoneEditText, new ImeHelper.DonePressedListener() {
            public void onDonePressed() {
                CheckPhoneNumberFragment.this.onNext();
            }
        });
        this.mSubmitButton.setOnClickListener(this);
        setupPrivacyDisclosures();
        setupCountrySpinner();
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mCheckPhoneHandler.getOperation().observe(this, new ResourceObserver<PhoneNumber>(this) {
            /* access modifiers changed from: protected */
            public void onSuccess(PhoneNumber number) {
                CheckPhoneNumberFragment.this.start(number);
            }

            /* access modifiers changed from: protected */
            public void onFailure(Exception e) {
            }
        });
        if (savedInstanceState == null && !this.mCalled) {
            this.mCalled = true;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.mCheckPhoneHandler.onActivityResult(requestCode, resultCode, data);
    }

    public void onClick(View v) {
        onNext();
    }

    /* access modifiers changed from: private */
    public void start(PhoneNumber number) {
        if (!PhoneNumber.isValid(number)) {
            this.mPhoneInputLayout.setError(getString(C0719R.string.fui_invalid_phone_number));
            return;
        }
        this.mPhoneEditText.setText(number.getPhoneNumber());
        this.mPhoneEditText.setSelection(number.getPhoneNumber().length());
        String iso = number.getCountryIso();
        if (PhoneNumber.isCountryValid(number) && this.mCountryListSpinner.isValidIso(iso)) {
            setCountryCode(number);
            onNext();
        }
    }

    /* access modifiers changed from: private */
    public void onNext() {
        String phoneNumber = getPseudoValidPhoneNumber();
        if (phoneNumber == null) {
            this.mPhoneInputLayout.setError(getString(C0719R.string.fui_invalid_phone_number));
        } else {
            this.mVerificationHandler.verifyPhoneNumber(phoneNumber, false);
        }
    }

    private String getPseudoValidPhoneNumber() {
        String everythingElse = this.mPhoneEditText.getText().toString();
        if (TextUtils.isEmpty(everythingElse)) {
            return null;
        }
        return PhoneNumberUtils.format(everythingElse, this.mCountryListSpinner.getSelectedCountryInfo());
    }

    private void setupPrivacyDisclosures() {
        FlowParameters params = getFlowParams();
        boolean termsAndPrivacyUrlsProvided = params.isTermsOfServiceUrlProvided() && params.isPrivacyPolicyUrlProvided();
        if (params.shouldShowProviderChoice() || !termsAndPrivacyUrlsProvided) {
            PrivacyDisclosureUtils.setupTermsOfServiceFooter(requireContext(), params, this.mFooterText);
            this.mSmsTermsText.setText(getString(C0719R.string.fui_sms_terms_of_service, getString(C0719R.string.fui_verify_phone_number)));
            return;
        }
        PrivacyDisclosureUtils.setupTermsOfServiceAndPrivacyPolicySmsText(requireContext(), params, this.mSmsTermsText);
    }

    private void setCountryCode(PhoneNumber number) {
        this.mCountryListSpinner.setSelectedForCountry(new Locale("", number.getCountryIso()), number.getCountryCode());
    }

    private void setupCountrySpinner() {
        this.mCountryListSpinner.init(getArguments().getBundle(ExtraConstants.PARAMS));
        setDefaultCountryForSpinner();
        this.mCountryListSpinner.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckPhoneNumberFragment.this.mPhoneInputLayout.setError((CharSequence) null);
            }
        });
    }

    private void setDefaultCountryForSpinner() {
        Bundle params = getArguments().getBundle(ExtraConstants.PARAMS);
        String phone = null;
        String countryIso = null;
        String nationalNumber = null;
        if (params != null) {
            phone = params.getString(ExtraConstants.PHONE);
            countryIso = params.getString(ExtraConstants.COUNTRY_ISO);
            nationalNumber = params.getString(ExtraConstants.NATIONAL_NUMBER);
        }
        if (!TextUtils.isEmpty(phone)) {
            start(PhoneNumberUtils.getPhoneNumber(phone));
        } else if (!TextUtils.isEmpty(countryIso) && !TextUtils.isEmpty(nationalNumber)) {
            start(PhoneNumberUtils.getPhoneNumber(countryIso, nationalNumber));
        } else if (!TextUtils.isEmpty(countryIso)) {
            setCountryCode(new PhoneNumber("", countryIso, String.valueOf(PhoneNumberUtils.getCountryCode(countryIso))));
        } else if (getFlowParams().enableHints) {
            this.mCheckPhoneHandler.fetchCredential();
        }
    }

    public void showProgress(int message) {
        this.mSubmitButton.setEnabled(false);
        this.mProgressBar.setVisibility(0);
    }

    public void hideProgress() {
        this.mSubmitButton.setEnabled(true);
        this.mProgressBar.setVisibility(4);
    }
}
