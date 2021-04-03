package com.android.time2study;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.time2study.QuizAdapter;
import com.android.time2study.marksAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Quiz extends AppCompatActivity implements QuizAdapter.QuizListener {
    public static String BroadcastString = "checkinternet";
    /* access modifiers changed from: private */
    public List<marksAdapter.markslist> Data = new ArrayList();
    QuizAdapter adapter;
    ArrayList<DataSetList> arrayList;
    AlertDialog dialog;
    FirebaseFirestore fstore;
    private IntentFilter intentFilter;
    /* access modifiers changed from: private */
    public List<Quizlist> listData = new ArrayList();
    marksAdapter marksAdapter;
    RecyclerView mrecyclerView;
    public BroadcastReceiver myReciever = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Quiz.BroadcastString) && !intent.getStringExtra("online_status").equals("true")) {
                Quiz.this.dialog.show();
            }
        }
    };
    RecyclerView recyclerView;
    TextView tx1;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0671R.layout.activity_quiz);
        IntentFilter intentFilter2 = new IntentFilter();
        this.intentFilter = intentFilter2;
        intentFilter2.addAction(BroadcastString);
        this.fstore = FirebaseFirestore.getInstance();
        this.recyclerView = (RecyclerView) findViewById(C0671R.C0674id.quiz_recycler);
        this.mrecyclerView = (RecyclerView) findViewById(C0671R.C0674id.marks_recycler);
        this.recyclerView.setHasFixedSize(true);
        this.mrecyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.mrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.tx1 = (TextView) findViewById(C0671R.C0674id.wait4);
        startService(new Intent(this, InternetService.class));
        alertDialog();
        if (!isOnline(this)) {
            this.dialog.show();
        }
        this.fstore.collection("Quiz").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    Iterator<QueryDocumentSnapshot> it = task.getResult().iterator();
                    while (it.hasNext()) {
                        Quiz.this.listData.add(new Quizlist("" + it.next().getId()));
                    }
                    Quiz.this.adapter = new QuizAdapter(Quiz.this.listData, Quiz.this);
                    Quiz.this.tx1.setVisibility(8);
                    Quiz.this.recyclerView.setAdapter(Quiz.this.adapter);
                }
            }
        });
        this.Data.add(new marksAdapter.markslist("Your Quiz marks displayed below "));
        marksAdapter marksadapter = new marksAdapter(this.Data, this);
        this.marksAdapter = marksadapter;
        this.mrecyclerView.setAdapter(marksadapter);
        this.fstore.collection("Quiz_Marks").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            public void onEvent(DocumentSnapshot value, FirebaseFirestoreException error) {
                if (value == null || error != null) {
                    Quiz quiz = Quiz.this;
                    Toast.makeText(quiz, "" + error.getStackTrace(), 0).show();
                    return;
                }
                Map<String, Object> m = value.getData();
                if (m != null) {
                    for (Map.Entry e : m.entrySet()) {
                        Quiz.this.Data.add(new marksAdapter.markslist("" + e.getKey() + " :- " + e.getValue() + " Marks"));
                    }
                    Quiz.this.marksAdapter = new marksAdapter(Quiz.this.Data, Quiz.this);
                    Quiz.this.mrecyclerView.setAdapter(Quiz.this.marksAdapter);
                    return;
                }
                Quiz.this.Data.add(new marksAdapter.markslist("You haven't played quiz yet"));
                Quiz.this.marksAdapter = new marksAdapter(Quiz.this.Data, Quiz.this);
                Quiz.this.mrecyclerView.setAdapter(Quiz.this.marksAdapter);
            }
        });
        BottomNavigationView bottom = (BottomNavigationView) findViewById(C0671R.C0674id.bottomNav);
        bottom.setSelectedItemId(C0671R.C0674id.quiz);
        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case C0671R.C0674id.ebook /*2131296444*/:
                        Quiz.this.startActivity(new Intent(Quiz.this, Ebook.class));
                        Quiz.this.overridePendingTransition(0, 0);
                        return true;
                    case C0671R.C0674id.home /*2131296505*/:
                        Quiz.this.startActivity(new Intent(Quiz.this, Home.class));
                        Quiz.this.overridePendingTransition(0, 0);
                        return true;
                    case C0671R.C0674id.profile /*2131296644*/:
                        Quiz.this.startActivity(new Intent(Quiz.this, Profile.class));
                        Quiz.this.overridePendingTransition(0, 0);
                        return true;
                    case C0671R.C0674id.quiz /*2131296652*/:
                        return true;
                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }
            }
        });
    }

    public void onQuizClick(int position) {
        final String Quiz_name = this.listData.get(position).getQuiz_Name();
        AlertDialog.Builder error = new AlertDialog.Builder(this);
        error.setTitle((CharSequence) "Attention !!!!");
        error.setIcon((int) C0671R.C0673drawable.ic_lightbulb_outline_black_24dp);
        error.setMessage((CharSequence) "Do you want to play quiz ?");
        error.setCancelable(false);
        error.setPositiveButton((CharSequence) "Yes", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent in = new Intent(Quiz.this, PlayQuiz.class);
                in.putExtra("name", Quiz_name);
                Quiz.this.startActivity(in);
            }
        }).setNegativeButton((CharSequence) "No", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        error.create().show();
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
                Quiz quiz = Quiz.this;
                if (!quiz.isOnline(quiz)) {
                    Quiz.this.dialog.show();
                } else {
                    Quiz.this.dialog.dismiss();
                }
            }
        });
        this.dialog = error.create();
    }
}
