package com.android.time2study;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class PlayQuiz extends AppCompatActivity {
    public static String BroadcastString = "checkinternet";
    AlertDialog dialog;
    Animation fadein;
    FirebaseFirestore fstore;
    ImageView img;
    private IntentFilter intentFilter;
    public BroadcastReceiver myReciever = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(PlayQuiz.BroadcastString) && !intent.getStringExtra("online_status").equals("true")) {
                PlayQuiz.this.dialog.show();
            }
        }
    };
    int time;
    TextView tx1;
    TextView tx2;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0671R.layout.activity_play_quiz);
        this.tx1 = (TextView) findViewById(C0671R.C0674id.quiz_name);
        this.tx2 = (TextView) findViewById(C0671R.C0674id.time1);
        this.img = (ImageView) findViewById(C0671R.C0674id.click);
        this.fstore = FirebaseFirestore.getInstance();
        this.fadein = AnimationUtils.loadAnimation(this, C0671R.anim.fade_in);
        IntentFilter intentFilter2 = new IntentFilter();
        this.intentFilter = intentFilter2;
        intentFilter2.addAction(BroadcastString);
        startService(new Intent(this, InternetService.class));
        alertDialog();
        if (!isOnline(this)) {
            this.dialog.show();
        }
        this.tx1.setText(getIntent().getExtras().getString("name"));
        this.img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PlayQuiz.this.img.startAnimation(PlayQuiz.this.fadein);
                Intent in = new Intent(PlayQuiz.this, StartQuiz.class);
                in.putExtra("name", PlayQuiz.this.getIntent().getExtras().getString("name"));
                in.putExtra("time", PlayQuiz.this.time);
                PlayQuiz.this.startActivity(in);
                PlayQuiz.this.overridePendingTransition(0, 0);
                PlayQuiz.this.finish();
            }
        });
        this.fstore.collection("Quiz").document(getIntent().getExtras().getString("name")).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            public void onEvent(DocumentSnapshot value, FirebaseFirestoreException error) {
                if (error == null) {
                    PlayQuiz.this.time(value.getString("time"));
                    TextView textView = PlayQuiz.this.tx2;
                    textView.setText("Time :- " + value.getString("time") + " minutes");
                    return;
                }
                PlayQuiz playQuiz = PlayQuiz.this;
                Toast.makeText(playQuiz, "" + error.getMessage(), 0).show();
            }
        });
    }

    /* access modifiers changed from: private */
    public void time(String time2) {
        this.time = Integer.parseInt(time2);
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
                PlayQuiz playQuiz = PlayQuiz.this;
                if (!playQuiz.isOnline(playQuiz)) {
                    PlayQuiz.this.dialog.show();
                } else {
                    PlayQuiz.this.dialog.dismiss();
                }
            }
        });
        this.dialog = error.create();
    }
}
