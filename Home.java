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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.time2study.VideoplaylistAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Home extends AppCompatActivity implements VideoplaylistAdapter.PlaylistrecyclerListener {
    public static String BroadcastString = "checkinternet";
    /* access modifiers changed from: private */
    public VideoplaylistAdapter adapter;
    AlertDialog dialog;
    FirebaseFirestore fstore;
    private IntentFilter intentFilter;
    /* access modifiers changed from: private */
    public List<Videoplaylist> listData = new ArrayList();
    private Loading loading;
    public BroadcastReceiver myReciever = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Home.BroadcastString) && !intent.getStringExtra("online_status").equals("true")) {
                Home.this.dialog.show();
            }
        }
    };
    RecyclerView recyclerView;
    TextView tx1;
    AlertDialog verifyemail;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0671R.layout.activity_home);
        this.loading = new Loading(this);
        this.recyclerView = (RecyclerView) findViewById(C0671R.C0674id.home_view);
        this.tx1 = (TextView) findViewById(C0671R.C0674id.wait1);
        this.fstore = FirebaseFirestore.getInstance();
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        IntentFilter intentFilter2 = new IntentFilter();
        this.intentFilter = intentFilter2;
        intentFilter2.addAction(BroadcastString);
        startService(new Intent(this, InternetService.class));
        alertDialog();
        if (!isOnline(this)) {
            this.dialog.show();
        }
        this.fstore.collection("Video_Links").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    Iterator<QueryDocumentSnapshot> it = task.getResult().iterator();
                    while (it.hasNext()) {
                        Home.this.listData.add(new Videoplaylist(it.next().getId()));
                    }
                    VideoplaylistAdapter unused = Home.this.adapter = new VideoplaylistAdapter(Home.this.listData, Home.this);
                    Home.this.tx1.setVisibility(8);
                    Home.this.recyclerView.setAdapter(Home.this.adapter);
                }
            }
        });
        BottomNavigationView bottom = (BottomNavigationView) findViewById(C0671R.C0674id.bottomNav);
        bottom.setSelectedItemId(C0671R.C0674id.home);
        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case C0671R.C0674id.ebook /*2131296444*/:
                        Home.this.startActivity(new Intent(Home.this, Ebook.class));
                        Home.this.overridePendingTransition(0, 0);
                        return true;
                    case C0671R.C0674id.home /*2131296505*/:
                        return true;
                    case C0671R.C0674id.profile /*2131296644*/:
                        Home.this.startActivity(new Intent(Home.this, Profile.class));
                        Home.this.overridePendingTransition(0, 0);
                        return true;
                    case C0671R.C0674id.quiz /*2131296652*/:
                        Home.this.startActivity(new Intent(Home.this, Quiz.class));
                        Home.this.overridePendingTransition(0, 0);
                        return true;
                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }
            }
        });
    }

    public void onplaylistClick(int position) {
        Intent i = new Intent(this, Home1.class);
        i.putExtra("name", this.listData.get(position).getTopic_Name());
        startActivity(i);
        overridePendingTransition(0, 0);
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
                Home home = Home.this;
                if (!home.isOnline(home)) {
                    Home.this.dialog.show();
                } else {
                    Home.this.dialog.dismiss();
                }
            }
        });
        this.dialog = error.create();
    }
}
