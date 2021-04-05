package com.android.time2study;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
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

public class Login extends AppCompatActivity {
    public static String BroadcastString = "checkinternet";
    public static final String CHECKBOX = "checkbox";
    public static final String EMAIL = "email";
    public static final String PASS = "pass";
    public static final String SHARED_PREFS = "shared_prefs";
    int Sign_in = 1;
    Animation blink;
    Animation bounce;
    Boolean checkbox;
    AlertDialog dialog;
    TextInputLayout email;
    TextInputEditText et_email;
    TextInputEditText et_pass;
    Animation fadein;
    Animation flip;
    TextView forget;
    FirebaseFirestore fstore;
    ImageView google;
    GoogleSignInClient gsic;

    /* renamed from: hm */
    HashMap<Integer, String> f73hm;
    private IntentFilter intentFilter;
    Loading loading = new Loading(this);
    Button login;
    String logout;
    public BroadcastReceiver myReciever = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Login.BroadcastString) && !intent.getStringExtra("online_status").equals("true")) {
                Login.this.dialog.show();
            }
        }
    };
    TextInputLayout pass;
    TextView register;
    CheckBox rem;
    int temp;
    TextView tx1;
    String val_email;
    String val_pass;
    AlertDialog verifyemail;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0671R.layout.activity_login);
        this.email = (TextInputLayout) findViewById(C0671R.C0674id.email);
        this.pass = (TextInputLayout) findViewById(C0671R.C0674id.pass);
        this.login = (Button) findViewById(C0671R.C0674id.login);
        this.rem = (CheckBox) findViewById(C0671R.C0674id.checkbox);
        this.forget = (TextView) findViewById(C0671R.C0674id.fpass);
        this.tx1 = (TextView) findViewById(C0671R.C0674id.tx1);
        this.register = (TextView) findViewById(C0671R.C0674id.register);
        this.google = (ImageView) findViewById(C0671R.C0674id.google);
        this.et_email = (TextInputEditText) findViewById(C0671R.C0674id.etemail);
        this.et_pass = (TextInputEditText) findViewById(C0671R.C0674id.etpass);
        this.fadein = AnimationUtils.loadAnimation(getApplicationContext(), C0671R.anim.fade_in);
        this.flip = AnimationUtils.loadAnimation(getApplicationContext(), C0671R.anim.flip);
        this.bounce = AnimationUtils.loadAnimation(getApplicationContext(), C0671R.anim.bounce);
        this.fstore = FirebaseFirestore.getInstance();
        this.f73hm = new HashMap<>();
        checkMob();
        this.temp = 1;
        IntentFilter intentFilter2 = new IntentFilter();
        this.intentFilter = intentFilter2;
        intentFilter2.addAction(BroadcastString);
        startService(new Intent(this, InternetService.class));
        alertDialog();
        if (!isOnline(this)) {
            this.dialog.show();
        }
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().getString("logout").equals("logout")) {
                this.logout = "logout";
            }
            if (getIntent().getExtras().getString("new").equals("new")) {
                this.logout = "";
                Alerter.create(this).setTitle("New User Created").setIcon((int) C0671R.C0673drawable.ic_lightbulb_outline_black_24dp).enableVibration(true).setText("Now, you can login with your email and password or Google").enableSwipeToDismiss().show();
            }
        }
        TextView textView = this.forget;
        textView.setPaintFlags(textView.getPaintFlags() | 8);
        TextView textView2 = this.register;
        textView2.setPaintFlags(textView2.getPaintFlags() | 8);
        this.forget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Login.this.forget.startAnimation(Login.this.flip);
                final EditText resetmail = new EditText(Login.this);
                AlertDialog.Builder passreset = new AlertDialog.Builder(Login.this);
                passreset.setTitle((CharSequence) "Reset Password ?");
                passreset.setMessage((CharSequence) "Enter Your Email to recieve reset_ink");
                passreset.setView((View) resetmail);
                Login.this.loading.loadingdialog();
                passreset.setPositiveButton((CharSequence) "Yes", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (!resetmail.getText().toString().trim().isEmpty()) {
                            FirebaseAuth.getInstance().sendPasswordResetEmail(resetmail.getText().toString().trim()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                public void onSuccess(Void aVoid) {
                                    Login.this.loading.dismissDialog();
                                    Toast.makeText(Login.this, "Reset Link sent to your email\nCheck your Mail", 1).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                public void onFailure(Exception e) {
                                    Login login = Login.this;
                                    Toast.makeText(login, "" + e.getMessage(), 1).show();
                                    Login.this.loading.dismissDialog();
                                }
                            });
                            return;
                        }
                        Login.this.loading.dismissDialog();
                        Toast.makeText(Login.this, "Please enter a valid Email Id", 1).show();
                    }
                });
                passreset.setNegativeButton((CharSequence) "No", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Login.this.loading.dismissDialog();
                        dialog.dismiss();
                    }
                });
                passreset.create().show();
            }
        });
        this.register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Login.this.register.startAnimation(Login.this.bounce);
                Login.this.startActivity(new Intent(Login.this, Register.class));
            }
        });
        this.login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Login.this.login.startAnimation(Login.this.fadein);
                Login login = Login.this;
                login.val_email = login.email.getEditText().getText().toString().trim();
                Login login2 = Login.this;
                login2.val_pass = login2.pass.getEditText().getText().toString().trim();
                if (Login.this.val_email.isEmpty()) {
                    Login.this.email.setHelperText("Must enter an Email ID");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(Login.this.val_email).matches()) {
                    Login.this.email.setHelperText("Enter a valid Email ID");
                    Login.this.email.setEndIconMode(2);
                } else {
                    Login.this.email.setHelperText((CharSequence) null);
                    Login.this.email.setEndIconDrawable((int) C0671R.C0673drawable.ic_check_circle_black_24dp);
                    if (Login.this.val_pass.isEmpty()) {
                        Login.this.pass.setHelperText("Must enter a password");
                    } else if (Login.this.val_pass.length() < 6) {
                        Login.this.pass.setHelperText("Must have length of 6 character at least");
                    } else {
                        Login.this.pass.setHelperText((CharSequence) null);
                        Login.this.loading.loadingdialog();
                        FirebaseAuth.getInstance().signInWithEmailAndPassword(Login.this.val_email, Login.this.val_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            public void onComplete(Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Login.this.savedata();
                                    Login.this.loading.dismissDialog();
                                    Iterator<Map.Entry<Integer, String>> it = Login.this.f73hm.entrySet().iterator();
                                    while (true) {
                                        if (!it.hasNext()) {
                                            break;
                                        }
                                        Map.Entry r = it.next();
                                        if (r.getValue().equals(task.getResult().getUser().getUid())) {
                                            TextView textView = Login.this.tx1;
                                            textView.setText("" + r.getValue());
                                            break;
                                        }
                                    }
                                    if (Login.this.tx1.getText().equals("")) {
                                        Login.this.temp = 3;
                                        Login.this.verifyMob(task.getResult().getUser().getUid());
                                    } else if (task.getResult().getUser().isEmailVerified()) {
                                        SharedPreferences.Editor editor = Login.this.getSharedPreferences("shared_prefs", 0).edit();
                                        editor.putBoolean("checkbox", true);
                                        editor.apply();
                                        Login.this.logout = "";
                                        Login.this.temp = 2;
                                        Toast.makeText(Login.this, "Successfully Sign In", 1).show();
                                        Login.this.startActivity(new Intent(Login.this, Home.class));
                                        Login.this.finish();
                                    } else {
                                        Login.this.verify(task.getResult().getUser());
                                    }
                                } else {
                                    Login login = Login.this;
                                    Toast.makeText(login, "" + task.getException().getMessage(), 1).show();
                                    Login.this.loading.dismissDialog();
                                }
                            }
                        });
                    }
                }
            }
        });
        loadData();
        updateViews();
        this.google.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(Login.this.getString(C0671R.string.default_web_client_id)).requestEmail().build();
                Login login = Login.this;
                login.gsic = GoogleSignIn.getClient((Activity) login, gso);
                Intent signIn_intent = Login.this.gsic.getSignInIntent();
                Login login2 = Login.this;
                login2.startActivityForResult(signIn_intent, login2.Sign_in);
            }
        });
    }

    private void checkMob() {
        this.fstore.collection("User_Details").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int i = 0;
                for (DocumentSnapshot d : queryDocumentSnapshots.getDocuments()) {
                    Login.this.f73hm.put(Integer.valueOf(i), d.getId());
                    i++;
                }
            }
        });
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
                Intent i = new Intent(Login.this, Register1.class);
                i.putExtra("edit", "");
                i.putExtra("user", user);
                Login.this.temp = 2;
                Login.this.startActivity(i);
            }
        });
        error.create().show();
    }

    public void verify(final FirebaseUser user) {
        AlertDialog.Builder mailverification = new AlertDialog.Builder(this);
        mailverification.setTitle((CharSequence) "Verification !!!");
        mailverification.setMessage((CharSequence) "It seems like you haven't verified your email yet.Check your Email !!!\nWould you like to get an email verification?? Your Email:- \n" + user.getEmail());
        mailverification.setCancelable(false);
        mailverification.setPositiveButton((CharSequence) "No", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Login.this.verifyemail.dismiss();
            }
        }).setNegativeButton((CharSequence) "Yes", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Login.this.loading.loadingdialog();
                user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Login.this, "Mail verification has sent", 0).show();
                        int i = 1;
                        while (i <= 7) {
                            try {
                                Thread.sleep(1000);
                                i++;
                            } catch (Exception anfe) {
                                Login.this.loading.dismissDialog();
                                Login login = Login.this;
                                Toast.makeText(login, "" + anfe.getMessage(), 1).show();
                                return;
                            }
                        }
                        Login.this.loading.dismissDialog();
                        Login.this.startActivity(Login.this.getPackageManager().getLaunchIntentForPackage("com.google.android.gm"));
                        Login.this.moveTaskToBack(false);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    public void onFailure(Exception e) {
                        Login.this.loading.dismissDialog();
                        Login login = Login.this;
                        Toast.makeText(login, "" + e.getStackTrace(), 0).show();
                    }
                });
            }
        });
        AlertDialog create = mailverification.create();
        this.verifyemail = create;
        create.show();
    }

    private void loadData() {
        SharedPreferences preferences = getSharedPreferences("shared_prefs", 0);
        this.val_email = preferences.getString("email", "");
        this.val_pass = preferences.getString(PASS, "");
        this.checkbox = Boolean.valueOf(preferences.getBoolean("checkbox", false));
    }

    public void updateViews() {
        if (this.checkbox.booleanValue()) {
            if (!this.val_email.equals("email")) {
                this.et_email.setText(this.val_email);
            }
            if (!this.val_pass.equals(PASS)) {
                this.et_pass.setText(this.val_pass);
            }
        }
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
                        Iterator<Map.Entry<Integer, String>> it = Login.this.f73hm.entrySet().iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            Map.Entry r = it.next();
                            if (r.getValue().equals(task.getResult().getUser().getUid())) {
                                TextView textView = Login.this.tx1;
                                textView.setText("" + r.getValue());
                                break;
                            }
                        }
                        if (!Login.this.tx1.getText().equals("")) {
                            FirebaseUser user = task.getResult().getUser();
                            if (user.getMetadata().getCreationTimestamp() == user.getMetadata().getLastSignInTimestamp()) {
                                Login.this.startActivity(new Intent(Login.this, Register1.class));
                                Login.this.temp = 2;
                                return;
                            }
                            Login.this.loading.dismissDialog();
                            Login.this.logout = "";
                            Login.this.temp = 2;
                            Login.this.startActivity(new Intent(Login.this, Home.class));
                            Login.this.finish();
                            return;
                        }
                        Login.this.loading.dismissDialog();
                        Login.this.temp = 3;
                        Login.this.verifyMob(task.getResult().getUser().getUid());
                        return;
                    }
                    Login login = Login.this;
                    Toast.makeText(login, "" + task.getException(), 1).show();
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void savedata() {
        SharedPreferences.Editor editor = getSharedPreferences("shared_prefs", 0).edit();
        editor.putString("email", this.email.getEditText().getText().toString().trim());
        editor.putString(PASS, this.pass.getEditText().getText().toString().trim());
        editor.putBoolean("checkbox", this.rem.isChecked());
        editor.apply();
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
                Login login = Login.this;
                if (!login.isOnline(login)) {
                    Login.this.dialog.show();
                } else {
                    Login.this.dialog.dismiss();
                }
            }
        });
        this.dialog = error.create();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        if (this.logout.equals("logout") || this.temp == 3) {
            FirebaseAuth.getInstance().signOut();
        }
    }
}
