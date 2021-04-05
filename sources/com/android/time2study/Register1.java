package com.android.time2study;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.tapadoo.alerter.Alerter;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Register1 extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {
    public static String BroadcastString = "checkinternet";
    TextInputEditText calender;
    private TextInputLayout city;
    Spinner class1;
    AlertDialog dialog;
    String editProfile;
    TextInputEditText etcity;
    TextInputEditText etmob;
    TextInputEditText etname;
    Animation fadein;
    FirebaseFirestore fstore;
    Spinner gender;
    String[] gender_item = {"Gender", "Male", "Female"};
    Spinner info;
    private IntentFilter intentFilter;
    /* access modifiers changed from: private */
    public Loading loading;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            validateOtp(s);
        }

        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            Toast.makeText(Register1.this, "Your Number is automatically verified", 1).show();
            Register1.this.finalSubmit();
            Register1.this.loading.dismissDialog();
        }

        public void onVerificationFailed(FirebaseException e) {
            Register1 register1 = Register1.this;
            Toast.makeText(register1, "" + e.getMessage(), 1).show();
            Register1.this.loading.dismissDialog();
        }

        private void validateOtp(final String code) {
            final EditText codeByUser = new EditText(Register1.this);
            AlertDialog.Builder verify = new AlertDialog.Builder(Register1.this);
            verify.setCancelable(false);
            verify.setTitle((CharSequence) "Mobile Verification");
            verify.setMessage((CharSequence) "Enter Your Verification OTP");
            verify.setView((View) codeByUser);
            verify.setPositiveButton((CharSequence) "Confirm", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Register1.this.SignInWithPhone(PhoneAuthProvider.getCredential(code, codeByUser.getText().toString().trim()));
                    dialog.dismiss();
                }
            });
            verify.setNegativeButton((CharSequence) "Edit", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Register1.this.loading.dismissDialog();
                }
            });
            verify.create().show();
        }
    };
    private TextInputLayout mob;
    public BroadcastReceiver myReciever = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Register1.BroadcastString) && !intent.getStringExtra("online_status").equals("true")) {
                Register1.this.dialog.show();
            }
        }
    };
    private TextInputLayout name;
    RadioButton radioButton;
    String[] student_class = {"Class", "8", "9", "10", "11", "12"};
    String[] student_info = {"I am a Student", "I am Guardian", "I am a Teacher"};
    RadioGroup subject;
    Button submit;
    int temp;
    private TextInputLayout tx1;
    private String userID;
    String val_city;
    String val_class;
    String val_dob;
    String val_gender;
    String val_info;
    String val_mob;
    String val_name;
    String val_subject;
    TextView verify;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0671R.layout.activity_register1);
        this.tx1 = (TextInputLayout) findViewById(C0671R.C0674id.date);
        this.etname = (TextInputEditText) findViewById(C0671R.C0674id.etname);
        this.etcity = (TextInputEditText) findViewById(C0671R.C0674id.etcity);
        this.etmob = (TextInputEditText) findViewById(C0671R.C0674id.etmob);
        this.calender = (TextInputEditText) findViewById(C0671R.C0674id.etdate);
        this.mob = (TextInputLayout) findViewById(C0671R.C0674id.mob);
        this.city = (TextInputLayout) findViewById(C0671R.C0674id.city);
        this.name = (TextInputLayout) findViewById(C0671R.C0674id.name);
        this.submit = (Button) findViewById(C0671R.C0674id.submit);
        this.subject = (RadioGroup) findViewById(C0671R.C0674id.subject);
        this.gender = (Spinner) findViewById(C0671R.C0674id.gender);
        this.info = (Spinner) findViewById(C0671R.C0674id.info);
        this.class1 = (Spinner) findViewById(C0671R.C0674id.class1);
        this.fadein = AnimationUtils.loadAnimation(getApplicationContext(), C0671R.anim.fade_in);
        this.loading = new Loading(this);
        this.fstore = FirebaseFirestore.getInstance();
        IntentFilter intentFilter2 = new IntentFilter();
        this.intentFilter = intentFilter2;
        intentFilter2.addAction(BroadcastString);
        startService(new Intent(this, InternetService.class));
        alertDialog();
        if (!isOnline(this)) {
            this.dialog.show();
        }
        if (getIntent().getExtras() != null) {
            this.editProfile = getIntent().getExtras().getString("edit");
            this.userID = getIntent().getExtras().getString("user");
        }
        ArrayAdapter<String> aa = new ArrayAdapter<>(this, 17367048, this.gender_item);
        aa.setDropDownViewResource(17367049);
        this.gender.setAdapter(aa);
        this.gender.setOnItemSelectedListener(this);
        ArrayAdapter<String> aa_student = new ArrayAdapter<>(this, 17367048, this.student_info);
        aa_student.setDropDownViewResource(17367049);
        this.info.setAdapter(aa_student);
        this.info.setOnItemSelectedListener(this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, 17367048, this.student_class);
        arrayAdapter.setDropDownViewResource(17367049);
        this.class1.setAdapter(arrayAdapter);
        this.class1.setOnItemSelectedListener(this);
        this.calender.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new DatePickUpFragment().show(Register1.this.getSupportFragmentManager(), "datePicker");
            }
        });
        if (this.editProfile.equals("edit")) {
            edit();
        }
    }

    public void onDateSet(DatePicker datePicker, int Year, int Month, int Date) {
        Calendar c = Calendar.getInstance();
        c.set(1, Year);
        c.set(2, Month);
        c.set(5, Date);
        this.calender.setText(DateFormat.getDateInstance().format(c.getTime()));
    }

    public void onItemSelected(AdapterView<?> adapterView, View v, int position, long l) {
        Spinner spinner = (Spinner) adapterView;
        if (spinner.getId() == C0671R.C0674id.gender) {
            this.val_gender = adapterView.getItemAtPosition(position).toString();
        }
        if (spinner.getId() == C0671R.C0674id.info) {
            this.val_info = adapterView.getItemAtPosition(position).toString();
        }
        if (spinner.getId() == C0671R.C0674id.class1) {
            this.val_class = adapterView.getItemAtPosition(position).toString();
        }
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void subjectInfo(View v) {
        RadioButton radioButton2 = (RadioButton) findViewById(this.subject.getCheckedRadioButtonId());
        this.radioButton = radioButton2;
        this.val_subject = radioButton2.getText().toString();
    }

    public void submit(View view) {
        this.submit.startAnimation(this.fadein);
        this.val_city = this.city.getEditText().getText().toString().trim();
        this.val_name = this.name.getEditText().getText().toString().trim();
        this.val_mob = this.mob.getEditText().getText().toString().trim();
        this.val_dob = this.tx1.getEditText().getText().toString().trim();
        if (this.val_name.isEmpty()) {
            this.name.setHelperText("Name is required");
            return;
        }
        this.name.setEndIconDrawable((int) C0671R.C0673drawable.ic_check_circle_black_24dp);
        this.name.setHelperText((CharSequence) null);
        if (this.val_city.isEmpty()) {
            this.city.setHelperText("Field can't be empty");
            return;
        }
        this.city.setEndIconDrawable((int) C0671R.C0673drawable.ic_check_circle_black_24dp);
        this.city.setHelperText((CharSequence) null);
        if (this.val_dob.isEmpty()) {
            this.tx1.setHelperText("Field can't be empty");
            return;
        }
        this.name.setHelperText((CharSequence) null);
        if (this.val_gender.equals("Gender")) {
            Alerter.create(this).setTitle("Attention !!!!").setIcon((int) C0671R.C0673drawable.ic_block_black_24dp).setText("Please Enter Your Gender").enableProgress(true).enableVibration(true).show();
        } else if (this.val_mob.isEmpty()) {
            this.mob.setHelperText("Contact number is required");
        } else if (this.val_mob.length() != 10) {
            this.mob.setHelperText("Enter a valid contact number");
        } else {
            this.mob.setEndIconDrawable((int) C0671R.C0673drawable.ic_check_circle_black_24dp);
            this.mob.setHelperText((CharSequence) null);
            if (this.editProfile.equals("edit")) {
                this.loading.loadingdialog();
                finalSubmit();
                return;
            }
            sendVerificationToUser(this.val_mob);
        }
    }

    private void sendVerificationToUser(String phone) {
        PhoneAuthProvider instance = PhoneAuthProvider.getInstance();
        instance.verifyPhoneNumber("+91" + phone, 60, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD, this.mCallBack);
        this.loading.loadingdialog();
    }

    /* access modifiers changed from: private */
    public void SignInWithPhone(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Register1.this, "Phone is Verified", 0).show();
                    Register1.this.finalSubmit();
                    return;
                }
                Register1.this.loading.dismissDialog();
                Register1 register1 = Register1.this;
                Toast.makeText(register1, "" + task.getException(), 0).show();
            }
        });
    }

    /* access modifiers changed from: protected */
    public void finalSubmit() {
        DocumentReference dr = this.fstore.collection("User_Details").document(this.userID);
        Map<String, Object> data = new HashMap<>();
        data.put("Full_Name", this.val_name);
        data.put("Date Of Birth", this.val_dob);
        data.put("Gender", this.val_gender);
        data.put("Information", this.val_info);
        data.put("Class", this.val_class);
        data.put("City", this.val_city);
        data.put("Mobile No", this.val_mob);
        data.put("Subject", this.val_subject);
        dr.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            public void onSuccess(Void aVoid) {
                if (Register1.this.editProfile.equals("edit")) {
                    Register1.this.loading.dismissDialog();
                    Toast.makeText(Register1.this, "Profile Updated Successfully", 1).show();
                    Register1.this.startActivity(new Intent(Register1.this, Profile.class));
                    Register1.this.finish();
                    return;
                }
                Register1.this.loading.dismissDialog();
                Register1 register1 = Register1.this;
                Toast.makeText(register1, " New User Created For " + Register1.this.val_name, 1).show();
                Intent i = new Intent(Register1.this, Login.class);
                i.putExtra("new", "new");
                i.putExtra("logout", "");
                Register1.this.startActivity(i);
                Register1.this.finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(Exception e) {
                Register1 register1 = Register1.this;
                Toast.makeText(register1, "" + e.getStackTrace(), 1).show();
                Register1.this.loading.dismissDialog();
            }
        });
    }

    private void edit() {
        this.userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.fstore.collection("User_Details").document(this.userID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            public void onEvent(DocumentSnapshot value, FirebaseFirestoreException error) {
                if (error == null) {
                    Register1.this.etname.setText(value.getString("Full_Name"));
                    Register1.this.calender.setText(value.getString("Date Of Birth"));
                    Register1.this.val_class = value.getString("Class");
                    Register1.this.etcity.setText(value.getString("City"));
                    Register1.this.val_gender = value.getString("Gender");
                    Register1.this.etmob.setText(value.getString("Mobile No"));
                    Register1.this.val_subject = value.getString("Subject");
                    Register1.this.val_info = value.getString("Information");
                    return;
                }
                Register1 register1 = Register1.this;
                Toast.makeText(register1, "" + error.getMessage(), 1).show();
            }
        });
        this.mob.setEnabled(false);
        this.mob.setHelperText("You can't change mobile number for now");
    }

    public void onBackPressed() {
        if (this.editProfile.equals("edit")) {
            finish();
        } else {
            moveTaskToBack(true);
        }
    }

    public boolean isOnline(Context con) {
        NetworkInfo net = ((ConnectivityManager) con.getSystemService("connectivity")).getActiveNetworkInfo();
        if (net == null || !net.isConnectedOrConnecting()) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void onRestart() {
        super.onRestart();
        registerReceiver(this.myReciever, this.intentFilter);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        unregisterReceiver(this.myReciever);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        registerReceiver(this.myReciever, this.intentFilter);
    }

    private void alertDialog() {
        AlertDialog.Builder error = new AlertDialog.Builder(this);
        error.setTitle((CharSequence) "Oops!!!");
        error.setMessage((CharSequence) "It seems like you are disconnected to the Internet. Please turn on the Internet Connections");
        error.setCancelable(false);
        error.setPositiveButton((CharSequence) "Retry", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Register1 register1 = Register1.this;
                if (!register1.isOnline(register1)) {
                    Register1.this.dialog.show();
                } else {
                    Register1.this.dialog.dismiss();
                }
            }
        });
        this.dialog = error.create();
    }
}
