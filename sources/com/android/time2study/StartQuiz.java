package com.android.time2study;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import p014de.hdodenhof.circleimageview.CircleImageView;

public class StartQuiz extends AppCompatActivity implements View.OnClickListener {
    public static String BroadcastString = "checkinternet";
    RadioGroup answer;
    boolean bool = true;
    Map<Integer, String> check = new HashMap();
    int correct = 0;
    Map<Integer, String> data = new HashMap();
    AlertDialog dialog;
    Button finish;
    private FirebaseFirestore fstore;
    private Handler handler = new Handler();
    private IntentFilter intentFilter;
    public BroadcastReceiver myReciever = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(StartQuiz.BroadcastString) && !intent.getStringExtra("online_status").equals("true")) {
                StartQuiz.this.dialog.show();
            }
        }
    };
    private String name;
    CircleImageView next;
    RadioButton optA;
    RadioButton optB;
    RadioButton optC;
    RadioButton optD;
    CircleImageView prev;
    ProgressBar progress;
    TextView ques;
    TextView quesno;
    int[] radId;
    TextView result1;
    Button submit;
    int temp = 1;
    int time = 0;
    TextView timer;
    TextView topicname;
    int totalques;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0671R.layout.activity_start_quiz);
        this.timer = (TextView) findViewById(C0671R.C0674id.timer);
        this.ques = (TextView) findViewById(C0671R.C0674id.ques);
        this.topicname = (TextView) findViewById(C0671R.C0674id.topicname);
        this.quesno = (TextView) findViewById(C0671R.C0674id.quesno);
        this.answer = (RadioGroup) findViewById(C0671R.C0674id.ans);
        this.progress = (ProgressBar) findViewById(C0671R.C0674id.progress);
        this.next = (CircleImageView) findViewById(C0671R.C0674id.next);
        this.prev = (CircleImageView) findViewById(C0671R.C0674id.prev);
        this.submit = (Button) findViewById(C0671R.C0674id.submit);
        this.finish = (Button) findViewById(C0671R.C0674id.finish);
        this.optA = (RadioButton) findViewById(C0671R.C0674id.optA);
        this.optB = (RadioButton) findViewById(C0671R.C0674id.optB);
        this.optC = (RadioButton) findViewById(C0671R.C0674id.optC);
        this.optD = (RadioButton) findViewById(C0671R.C0674id.optD);
        this.result1 = (TextView) findViewById(C0671R.C0674id.final1);
        this.fstore = FirebaseFirestore.getInstance();
        this.name = getIntent().getExtras().getString("name");
        this.time = getIntent().getExtras().getInt("time");
        this.submit.setOnClickListener(this);
        this.prev.setOnClickListener(this);
        this.next.setOnClickListener(this);
        this.finish.setOnClickListener(this);
        this.topicname.setText(this.name);
        IntentFilter intentFilter2 = new IntentFilter();
        this.intentFilter = intentFilter2;
        intentFilter2.addAction(BroadcastString);
        startService(new Intent(this, InternetService.class));
        alertDialog();
        if (!isOnline(this)) {
            this.dialog.show();
        }
        quesNo();
    }

    private void quesNo() {
        this.fstore.collection(this.name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            public void onComplete(Task<QuerySnapshot> task) {
                int count = 0;
                if (task.isSuccessful() && task.getResult() != null) {
                    Iterator<QueryDocumentSnapshot> it = task.getResult().iterator();
                    while (it.hasNext()) {
                        DocumentSnapshot next = it.next();
                        count++;
                    }
                    StartQuiz.this.quantity(count);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void quantity(int count) {
        this.totalques = count;
        this.radId = new int[(count * 2)];
        displayQues();
        setTimer();
    }

    private void displayQues() {
        if (this.temp != 1) {
            this.prev.setVisibility(0);
        } else {
            this.prev.setVisibility(8);
        }
        if (this.temp == this.totalques) {
            this.next.setVisibility(8);
            this.finish.setVisibility(0);
        } else {
            this.next.setVisibility(0);
            this.finish.setVisibility(8);
        }
        TextView textView = this.quesno;
        textView.setText(this.temp + "/" + this.totalques);
        int[] iArr = this.radId;
        int i = this.temp;
        if (iArr[i - 1] != 0) {
            ((RadioButton) findViewById(iArr[i - 1])).setChecked(true);
        }
        CollectionReference collection = this.fstore.collection(this.name);
        collection.document("" + this.temp).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            public void onEvent(DocumentSnapshot value, FirebaseFirestoreException error) {
                if (error == null) {
                    StartQuiz.this.ques.setText(value.getString("ques"));
                    StartQuiz.this.optA.setText(value.getString("opt1"));
                    StartQuiz.this.optB.setText(value.getString("opt2"));
                    StartQuiz.this.optC.setText(value.getString("opt3"));
                    StartQuiz.this.optD.setText(value.getString("opt4"));
                    StartQuiz.this.check.put(Integer.valueOf(StartQuiz.this.temp), value.getString("ans"));
                    return;
                }
                StartQuiz startQuiz = StartQuiz.this;
                Toast.makeText(startQuiz, "" + error.getMessage(), 0).show();
            }
        });
    }

    public void setTimer() {
        new CountDownTimer((long) (this.time * 60 * 1000), 1000) {
            public void onTick(long l) {
                StartQuiz.this.timer.setText(String.format(Locale.getDefault(), "%01d:%02d", new Object[]{Integer.valueOf(((int) (l / 1000)) / 60), Integer.valueOf(((int) (l / 1000)) % 60)}));
            }

            public void onFinish() {
                StartQuiz.this.next.setVisibility(8);
                StartQuiz.this.prev.setVisibility(8);
                StartQuiz.this.submit.setVisibility(8);
                StartQuiz.this.finish.setVisibility(8);
                StartQuiz.this.result1.setVisibility(0);
                StartQuiz.this.timer.setVisibility(8);
                StartQuiz.this.ques.setVisibility(8);
                StartQuiz.this.quesno.setVisibility(8);
                StartQuiz.this.topicname.setVisibility(8);
                StartQuiz.this.answer.setVisibility(8);
                StartQuiz.this.progress.setVisibility(8);
                StartQuiz.this.validate();
            }
        }.start();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case C0671R.C0674id.finish /*2131296476*/:
                AlertDialog.Builder error = new AlertDialog.Builder(this);
                error.setTitle((CharSequence) "Attention !!!!");
                error.setIcon((int) C0671R.C0673drawable.ic_lightbulb_outline_black_24dp);
                error.setMessage((CharSequence) "Do you want to end the quiz ?");
                error.setPositiveButton((CharSequence) "Yes", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StartQuiz.this.next.setVisibility(8);
                        StartQuiz.this.prev.setVisibility(8);
                        StartQuiz.this.submit.setVisibility(8);
                        StartQuiz.this.finish.setVisibility(8);
                        StartQuiz.this.result1.setVisibility(0);
                        StartQuiz.this.timer.setVisibility(8);
                        StartQuiz.this.ques.setVisibility(8);
                        StartQuiz.this.quesno.setVisibility(8);
                        StartQuiz.this.topicname.setVisibility(8);
                        StartQuiz.this.answer.setVisibility(8);
                        StartQuiz.this.progress.setVisibility(8);
                        StartQuiz.this.validate();
                    }
                }).setNegativeButton((CharSequence) "No", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                error.create().show();
                return;
            case C0671R.C0674id.next /*2131296607*/:
                this.temp++;
                this.answer.clearCheck();
                check();
                return;
            case C0671R.C0674id.prev /*2131296643*/:
                this.temp--;
                this.answer.clearCheck();
                check();
                return;
            case C0671R.C0674id.submit /*2131296736*/:
                RadioButton rad = (RadioButton) findViewById(this.answer.getCheckedRadioButtonId());
                int i = this.temp;
                if (i <= this.totalques && rad != null) {
                    this.radId[i - 1] = this.answer.getCheckedRadioButtonId();
                    this.data.put(Integer.valueOf(this.temp), "" + rad.getText());
                    if (this.temp < this.totalques) {
                        this.answer.clearCheck();
                    }
                    this.temp++;
                    check();
                    return;
                }
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: private */
    public void validate() {
        for (Map.Entry m : this.data.entrySet()) {
            Iterator<Map.Entry<Integer, String>> it = this.check.entrySet().iterator();
            while (true) {
                if (it.hasNext()) {
                    if (m.getValue().equals(it.next().getValue())) {
                        this.correct++;
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        result();
    }

    public void result() {
        TextView textView = this.result1;
        textView.setText("-: Your Selected Options :- \n\n" + this.name + "\n\n" + this.data + "\n\nTotal Correct :- " + this.correct + "\n\nWrong :- " + (this.totalques - this.correct) + "\n\nYou can take screenshot for futher use");
        if (this.bool) {
            Map<String, Object> marks = new HashMap<>();
            String str = this.name;
            marks.put(str, "" + this.correct + "/" + this.totalques);
            this.fstore.collection("Quiz_Marks").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(marks, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                public void onSuccess(Void aVoid) {
                    Toast.makeText(StartQuiz.this, "Marks will be updated", 0).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(Exception e) {
                    StartQuiz startQuiz = StartQuiz.this;
                    Toast.makeText(startQuiz, "" + e.getStackTrace(), 0).show();
                }
            });
            this.bool = false;
        }
    }

    private void check() {
        if (this.temp <= this.totalques) {
            displayQues();
        } else {
            Toast.makeText(this, "This is the last question", 0).show();
        }
    }

    public void onBackPressed() {
        startActivity(new Intent(this, Quiz.class));
        overridePendingTransition(0, 0);
        finish();
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
                StartQuiz startQuiz = StartQuiz.this;
                if (!startQuiz.isOnline(startQuiz)) {
                    StartQuiz.this.dialog.show();
                } else {
                    StartQuiz.this.dialog.dismiss();
                }
            }
        });
        this.dialog = error.create();
    }
}
