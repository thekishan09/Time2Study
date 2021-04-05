package com.firebase.p002ui.auth.p007ui.email;

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
import com.firebase.p002ui.auth.FirebaseAuthAnonymousUpgradeException;
import com.firebase.p002ui.auth.IdpResponse;
import com.firebase.p002ui.auth.data.model.User;
import com.firebase.p002ui.auth.p007ui.FragmentBase;
import com.firebase.p002ui.auth.util.ExtraConstants;
import com.firebase.p002ui.auth.util.data.PrivacyDisclosureUtils;
import com.firebase.p002ui.auth.util.data.ProviderUtils;
import com.firebase.p002ui.auth.util.p008ui.ImeHelper;
import com.firebase.p002ui.auth.util.p008ui.fieldvalidators.BaseValidator;
import com.firebase.p002ui.auth.util.p008ui.fieldvalidators.EmailFieldValidator;
import com.firebase.p002ui.auth.util.p008ui.fieldvalidators.NoOpValidator;
import com.firebase.p002ui.auth.util.p008ui.fieldvalidators.PasswordFieldValidator;
import com.firebase.p002ui.auth.util.p008ui.fieldvalidators.RequiredFieldValidator;
import com.firebase.p002ui.auth.viewmodel.ResourceObserver;
import com.firebase.p002ui.auth.viewmodel.email.EmailProviderResponseHandler;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

/* renamed from: com.firebase.ui.auth.ui.email.RegisterEmailFragment */
public class RegisterEmailFragment extends FragmentBase implements View.OnClickListener, View.OnFocusChangeListener, ImeHelper.DonePressedListener {
    public static final String TAG = "RegisterEmailFragment";
    private EditText mEmailEditText;
    private EmailFieldValidator mEmailFieldValidator;
    /* access modifiers changed from: private */
    public TextInputLayout mEmailInput;
    /* access modifiers changed from: private */
    public EmailProviderResponseHandler mHandler;
    /* access modifiers changed from: private */
    public AnonymousUpgradeListener mListener;
    private EditText mNameEditText;
    private BaseValidator mNameValidator;
    private Button mNextButton;
    /* access modifiers changed from: private */
    public EditText mPasswordEditText;
    private PasswordFieldValidator mPasswordFieldValidator;
    /* access modifiers changed from: private */
    public TextInputLayout mPasswordInput;
    private ProgressBar mProgressBar;
    private User mUser;

    /* renamed from: com.firebase.ui.auth.ui.email.RegisterEmailFragment$AnonymousUpgradeListener */
    interface AnonymousUpgradeListener {
        void onMergeFailure(IdpResponse idpResponse);
    }

    public static RegisterEmailFragment newInstance(User user) {
        RegisterEmailFragment fragment = new RegisterEmailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ExtraConstants.USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            this.mUser = User.getUser(getArguments());
        } else {
            this.mUser = User.getUser(savedInstanceState);
        }
        EmailProviderResponseHandler emailProviderResponseHandler = (EmailProviderResponseHandler) ViewModelProviders.m14of((Fragment) this).get(EmailProviderResponseHandler.class);
        this.mHandler = emailProviderResponseHandler;
        emailProviderResponseHandler.init(getFlowParams());
        this.mHandler.getOperation().observe(this, new ResourceObserver<IdpResponse>(this, C0719R.string.fui_progress_dialog_signing_up) {
            /* access modifiers changed from: protected */
            public void onSuccess(IdpResponse response) {
                RegisterEmailFragment registerEmailFragment = RegisterEmailFragment.this;
                registerEmailFragment.startSaveCredentials(registerEmailFragment.mHandler.getCurrentUser(), response, RegisterEmailFragment.this.mPasswordEditText.getText().toString());
            }

            /* access modifiers changed from: protected */
            public void onFailure(Exception e) {
                if (e instanceof FirebaseAuthWeakPasswordException) {
                    RegisterEmailFragment.this.mPasswordInput.setError(RegisterEmailFragment.this.getResources().getQuantityString(C0719R.plurals.fui_error_weak_password, C0719R.integer.fui_min_password_length));
                } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    RegisterEmailFragment.this.mEmailInput.setError(RegisterEmailFragment.this.getString(C0719R.string.fui_invalid_email_address));
                } else if (e instanceof FirebaseAuthAnonymousUpgradeException) {
                    RegisterEmailFragment.this.mListener.onMergeFailure(((FirebaseAuthAnonymousUpgradeException) e).getResponse());
                } else {
                    RegisterEmailFragment.this.mEmailInput.setError(RegisterEmailFragment.this.getString(C0719R.string.fui_email_account_creation_error));
                }
            }
        });
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(C0719R.layout.fui_register_email_layout, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.mNextButton = (Button) view.findViewById(C0719R.C0722id.button_create);
        this.mProgressBar = (ProgressBar) view.findViewById(C0719R.C0722id.top_progress_bar);
        this.mEmailEditText = (EditText) view.findViewById(C0719R.C0722id.email);
        this.mNameEditText = (EditText) view.findViewById(C0719R.C0722id.name);
        this.mPasswordEditText = (EditText) view.findViewById(C0719R.C0722id.password);
        this.mEmailInput = (TextInputLayout) view.findViewById(C0719R.C0722id.email_layout);
        this.mPasswordInput = (TextInputLayout) view.findViewById(C0719R.C0722id.password_layout);
        TextInputLayout nameInput = (TextInputLayout) view.findViewById(C0719R.C0722id.name_layout);
        boolean requireName = ProviderUtils.getConfigFromIdpsOrThrow(getFlowParams().providers, "password").getParams().getBoolean(ExtraConstants.REQUIRE_NAME, true);
        this.mPasswordFieldValidator = new PasswordFieldValidator(this.mPasswordInput, getResources().getInteger(C0719R.integer.fui_min_password_length));
        this.mNameValidator = requireName ? new RequiredFieldValidator(nameInput) : new NoOpValidator(nameInput);
        this.mEmailFieldValidator = new EmailFieldValidator(this.mEmailInput);
        ImeHelper.setImeOnDoneListener(this.mPasswordEditText, this);
        this.mEmailEditText.setOnFocusChangeListener(this);
        this.mNameEditText.setOnFocusChangeListener(this);
        this.mPasswordEditText.setOnFocusChangeListener(this);
        this.mNextButton.setOnClickListener(this);
        nameInput.setVisibility(requireName ? 0 : 8);
        if (Build.VERSION.SDK_INT >= 26 && getFlowParams().enableCredentials) {
            this.mEmailEditText.setImportantForAutofill(2);
        }
        PrivacyDisclosureUtils.setupTermsOfServiceFooter(requireContext(), getFlowParams(), (TextView) view.findViewById(C0719R.C0722id.email_footer_tos_and_pp_text));
        if (savedInstanceState == null) {
            String email = this.mUser.getEmail();
            if (!TextUtils.isEmpty(email)) {
                this.mEmailEditText.setText(email);
            }
            String name = this.mUser.getName();
            if (!TextUtils.isEmpty(name)) {
                this.mNameEditText.setText(name);
            }
            if (!requireName || !TextUtils.isEmpty(this.mNameEditText.getText())) {
                safeRequestFocus(this.mPasswordEditText);
            } else if (!TextUtils.isEmpty(this.mEmailEditText.getText())) {
                safeRequestFocus(this.mNameEditText);
            } else {
                safeRequestFocus(this.mEmailEditText);
            }
        }
    }

    private void safeRequestFocus(final View v) {
        v.post(new Runnable() {
            public void run() {
                v.requestFocus();
            }
        });
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = requireActivity();
        activity.setTitle(C0719R.string.fui_title_register_email);
        if (activity instanceof AnonymousUpgradeListener) {
            this.mListener = (AnonymousUpgradeListener) activity;
            return;
        }
        throw new IllegalStateException("Activity must implement CheckEmailListener");
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(ExtraConstants.USER, new User.Builder("password", this.mEmailEditText.getText().toString()).setName(this.mNameEditText.getText().toString()).setPhotoUri(this.mUser.getPhotoUri()).build());
    }

    public void onFocusChange(View view, boolean hasFocus) {
        if (!hasFocus) {
            int id = view.getId();
            if (id == C0719R.C0722id.email) {
                this.mEmailFieldValidator.validate(this.mEmailEditText.getText());
            } else if (id == C0719R.C0722id.name) {
                this.mNameValidator.validate(this.mNameEditText.getText());
            } else if (id == C0719R.C0722id.password) {
                this.mPasswordFieldValidator.validate(this.mPasswordEditText.getText());
            }
        }
    }

    public void onClick(View view) {
        if (view.getId() == C0719R.C0722id.button_create) {
            validateAndRegisterUser();
        }
    }

    public void onDonePressed() {
        validateAndRegisterUser();
    }

    public void showProgress(int message) {
        this.mNextButton.setEnabled(false);
        this.mProgressBar.setVisibility(0);
    }

    public void hideProgress() {
        this.mNextButton.setEnabled(true);
        this.mProgressBar.setVisibility(4);
    }

    private void validateAndRegisterUser() {
        String email = this.mEmailEditText.getText().toString();
        String password = this.mPasswordEditText.getText().toString();
        String name = this.mNameEditText.getText().toString();
        boolean emailValid = this.mEmailFieldValidator.validate(email);
        boolean passwordValid = this.mPasswordFieldValidator.validate(password);
        boolean nameValid = this.mNameValidator.validate(name);
        if (emailValid && passwordValid && nameValid) {
            this.mHandler.startSignIn(new IdpResponse.Builder(new User.Builder("password", email).setName(name).setPhotoUri(this.mUser.getPhotoUri()).build()).build(), password);
        }
    }
}
