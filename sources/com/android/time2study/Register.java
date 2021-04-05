package com.android.time2study;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.tapadoo.alerter.Alerter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity implements View.OnClickListener {
    public static String BroadcastString = "checkinternet";
    private static final Pattern Password = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{5,}");
    int Sign_in = 1;
    AlertDialog dialog;
    TextInputLayout email;
    FirebaseAuth fAuth;
    Animation fadein;
    FirebaseFirestore fstore;
    ImageView google;
    GoogleSignInClient gsic;

    /* renamed from: hm */
    HashMap<Integer, String> f96hm;
    IntentFilter intentFilter;
    Loading loading;
    TextView login;
    public BroadcastReceiver myReciever = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Register.BroadcastString) && !intent.getStringExtra("online_status").equals("true")) {
                Register.this.dialog.show();
            }
        }
    };
    TextInputLayout pass;
    Button register;
    TextInputLayout repass;
    TextView tx1;
    AlertDialog verifyemail;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0671R.layout.activity_register);
        this.register = (Button) findViewById(C0671R.C0674id.signup);
        this.login = (TextView) findViewById(C0671R.C0674id.login);
        this.google = (ImageView) findViewById(C0671R.C0674id.google);
        this.email = (TextInputLayout) findViewById(C0671R.C0674id.email);
        this.pass = (TextInputLayout) findViewById(C0671R.C0674id.pass);
        this.repass = (TextInputLayout) findViewById(C0671R.C0674id.repass);
        this.google.setOnClickListener(this);
        this.register.setOnClickListener(this);
        this.loading = new Loading(this);
        this.fadein = AnimationUtils.loadAnimation(this, C0671R.anim.fade_in);
        this.fstore = FirebaseFirestore.getInstance();
        this.tx1 = (TextView) findViewById(C0671R.C0674id.tx2);
        TextView textView = this.login;
        textView.setPaintFlags(textView.getPaintFlags() | 8);
        this.login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Register.this.startActivity(new Intent(Register.this, Login.class));
            }
        });
        IntentFilter intentFilter2 = new IntentFilter();
        this.intentFilter = intentFilter2;
        intentFilter2.addAction(BroadcastString);
        startService(new Intent(this, InternetService.class));
        alertDialog();
        if (!isOnline(this)) {
            this.dialog.show();
        }
        this.f96hm = new HashMap<>();
        checkMob();
    }

    public void onClick(View v) {
        if (v.getId() == C0671R.C0674id.google) {
            this.gsic = GoogleSignIn.getClient((Activity) this, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(C0671R.string.default_web_client_id)).requestEmail().build());
            signIn();
        }
        if (v.getId() == C0671R.C0674id.signup) {
            this.register.startAnimation(this.fadein);
            String val_email = this.email.getEditText().getText().toString().trim();
            String val_pass = this.pass.getEditText().getText().toString().trim();
            String val_repass = this.repass.getEditText().getText().toString().trim();
            if (val_email.isEmpty()) {
                this.email.setHelperText("Must enter an Email ID");
            } else if (!Patterns.EMAIL_ADDRESS.matcher(val_email).matches()) {
                this.email.setHelperText("Enter a valid Email ID");
            } else {
                this.email.setHelperText("Verification Link will be send");
                this.email.setEndIconDrawable((int) C0671R.C0673drawable.ic_check_circle_black_24dp);
                if (val_pass.isEmpty()) {
                    this.pass.setHelperText("Must enter a password");
                } else if (!Password.matcher(val_pass).matches()) {
                    this.pass.setHelperText("Must have length of 6 character");
                    Alerter.create(this).setTitle("Password Builder").setIcon((int) C0671R.C0673drawable.ic_block_black_24dp).setText("Must have combination of\nat least 1 digit\n 1 lowercase character\n1 uppercase character").enableProgress(true).show();
                } else {
                    this.pass.setHelperText((CharSequence) null);
                    if (!val_pass.equals(val_repass)) {
                        this.repass.setHelperText("Password must be same");
                        return;
                    }
                    this.repass.setHelperText((CharSequence) null);
                    this.loading.loadingdialog();
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(val_email, val_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Register.this.loading.dismissDialog();
                                Toast.makeText(Register.this, "Registration Successful", 1).show();
                                Intent i = new Intent(Register.this, Register1.class);
                                i.putExtra("edit", "");
                                i.putExtra("user", task.getResult().getUser().getUid());
                                Register.this.startActivity(i);
                                Register.this.finish();
                                return;
                            }
                            Register.this.loading.dismissDialog();
                            Register register = Register.this;
                            Toast.makeText(register, "" + task.getException().getMessage(), 1).show();
                        }
                    });
                }
            }
        }
    }

    private void checkMob() {
        this.fstore.collection("User_Details").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int i = 0;
                for (DocumentSnapshot d : queryDocumentSnapshots.getDocuments()) {
                    Register.this.f96hm.put(Integer.valueOf(i), d.getId());
                    i++;
                }
            }
        });
    }

    private void signIn() {
        startActivityForResult(this.gsic.getSignInIntent(), this.Sign_in);
    }

    /* access modifiers changed from: private */
    public void verifyMob(final String user) {
        AlertDialog.Builder error = new AlertDialog.Builder(this);
        error.setTitle((CharSequence) "Oops!!!");
        error.setIcon((int) C0671R.C0673drawable.ic_lightbulb_outline_black_24dp);
        error.setMessage((CharSequence) "It seems like you didn't fill your personal details.Would you like to provide us ??");
        error.setCancelable(false);
        error.setPositiveButton((CharSequence) "No", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setNegativeButton((CharSequence) "Yes", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int in) {
                Intent i = new Intent(Register.this, Register1.class);
                i.putExtra("edit", "");
                i.putExtra("user", user);
                Register.this.startActivity(i);
            }
        });
        error.create().show();
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.Sign_in) {
            handleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(data));
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            FirebaseGoogleAuth(completedTask.getResult(ApiException.class));
        } catch (ApiException e) {
            Toast.makeText(this, "" + e.getMessage(), 1).show();
            FirebaseGoogleAuth((GoogleSignInAccount) null);
        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acc) {
        this.loading.loadingdialog();
        if (acc.getIdToken() != null) {
            FirebaseAuth.getInstance().signInWithCredential(GoogleAuthProvider.getCredential(acc.getIdToken(), (String) null)).addOnCompleteListener((Activity) this, new OnCompleteListener<AuthResult>() {
                public void onComplete(Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Iterator<Map.Entry<Integer, String>> it = Register.this.f96hm.entrySet().iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            Map.Entry r = it.next();
                            if (r.getValue().equals(task.getResult().getUser().getUid())) {
                                TextView textView = Register.this.tx1;
                                textView.setText("" + r.getValue());
                                break;
                            }
                        }
                        if (!Register.this.tx1.getText().equals("")) {
                            FirebaseUser user = task.getResult().getUser();
                            if (user.getMetadata().getCreationTimestamp() == user.getMetadata().getLastSignInTimestamp()) {
                                Register.this.loading.dismissDialog();
                                Intent i = new Intent(Register.this, Register1.class);
                                i.putExtra("edit", "");
                                i.putExtra("user", task.getResult().getUser().getUid());
                                Register.this.startActivity(i);
                                return;
                            }
                            Register.this.loading.dismissDialog();
                            Register.this.startActivity(new Intent(Register.this, Home.class));
                            Register.this.finish();
                            return;
                        }
                        Register.this.loading.dismissDialog();
                        Register.this.verifyMob(task.getResult().getUser().getUid());
                        return;
                    }
                    Register register = Register.this;
                    Toast.makeText(register, "" + task.getException().getMessage(), 1).show();
                    Register.this.loading.dismissDialog();
                }
            });
        }
    }

    public void onBackPressed() {
        moveTaskToBack(true);
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
                Register register = Register.this;
                if (!register.isOnline(register)) {
                    Register.this.dialog.show();
                } else {
                    Register.this.dialog.dismiss();
                }
            }
        });
        this.dialog = error.create();
    }
}
