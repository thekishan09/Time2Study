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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import java.util.ArrayList;

public class Home1 extends AppCompatActivity {
    public static String BroadcastString = "checkinternet";
    ArrayList<DataSetList> arrayList;
    AlertDialog dialog;
    FirebaseFirestore fstore;
    private IntentFilter intentFilter;
    public BroadcastReceiver myReciever = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Home1.BroadcastString) && !intent.getStringExtra("online_status").equals("true")) {
                Home1.this.dialog.show();
            }
        }
    };
    private String playlist_name;
    RecyclerView recyclerView;
    TextView tx1;
    YoutubeAdapter youtubeAdapter;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0671R.layout.activity_home1);
        this.recyclerView = (RecyclerView) findViewById(C0671R.C0674id.recycler_view);
        this.tx1 = (TextView) findViewById(C0671R.C0674id.wait);
        this.fstore = FirebaseFirestore.getInstance();
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (getIntent().getExtras() != null) {
            this.playlist_name = getIntent().getExtras().getString("name");
        } else {
            this.playlist_name = "";
        }
        this.arrayList = new ArrayList<>();
        IntentFilter intentFilter2 = new IntentFilter();
        this.intentFilter = intentFilter2;
        intentFilter2.addAction(BroadcastString);
        startService(new Intent(this, InternetService.class));
        alertDialog();
        if (!isOnline(this)) {
            this.dialog.show();
        }
        this.fstore.collection("Video_Links").document(this.playlist_name).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            public void onEvent(DocumentSnapshot value, FirebaseFirestoreException error) {
                int i = 1;
                while (true) {
                    if (value.getString("link" + i) != null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("https://www.youtube.com/embed/");
                        sb.append(value.getString("link" + i));
                        Home1.this.arrayList.add(new DataSetList(sb.toString()));
                        i++;
                    } else {
                        Home1.this.youtubeAdapter = new YoutubeAdapter(Home1.this.arrayList, Home1.this);
                        Home1.this.tx1.setVisibility(8);
                        Home1.this.recyclerView.setAdapter(Home1.this.youtubeAdapter);
                        return;
                    }
                }
            }
        });
        BottomNavigationView bottom = (BottomNavigationView) findViewById(C0671R.C0674id.bottomNav);
        bottom.setSelectedItemId(C0671R.C0674id.home);
        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case C0671R.C0674id.ebook /*2131296444*/:
                        Home1.this.startActivity(new Intent(Home1.this, Ebook.class));
                        Home1.this.overridePendingTransition(0, 0);
                        return true;
                    case C0671R.C0674id.home /*2131296505*/:
                        Home1.this.startActivity(new Intent(Home1.this, Home.class));
                        Home1.this.overridePendingTransition(0, 0);
                        return true;
                    case C0671R.C0674id.profile /*2131296644*/:
                        Home1.this.startActivity(new Intent(Home1.this, Profile.class));
                        Home1.this.overridePendingTransition(0, 0);
                        return true;
                    case C0671R.C0674id.quiz /*2131296652*/:
                        Home1.this.startActivity(new Intent(Home1.this, Quiz.class));
                        Home1.this.overridePendingTransition(0, 0);
                        return true;
                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }
            }
        });
    }

    public void onBackPressed() {
        moveTaskToBack(false);
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
                Home1 home1 = Home1.this;
                if (!home1.isOnline(home1)) {
                    Home1.this.dialog.show();
                } else {
                    Home1.this.dialog.dismiss();
                }
            }
        });
        this.dialog = error.create();
    }
}
