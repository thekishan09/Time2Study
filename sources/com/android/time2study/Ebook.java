package com.android.time2study;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.time2study.EbooklistAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.tapadoo.alerter.Alerter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Ebook extends AppCompatActivity implements EbooklistAdapter.ebookListener {
    public static String BroadcastString = "checkinternet";
    /* access modifiers changed from: private */
    public EbooklistAdapter adapter;
    AlertDialog dialog;
    AlertDialog dialog1;
    FirebaseFirestore fstore;
    private IntentFilter intentFilter;
    /* access modifiers changed from: private */
    public List<Ebooklist> listData = new ArrayList();
    public BroadcastReceiver myReciever = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Ebook.BroadcastString) && !intent.getStringExtra("online_status").equals("true")) {
                Ebook.this.dialog.show();
            }
        }
    };
    File path;
    RecyclerView recyclerView;
    StorageReference sref;
    TextView tx1;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0671R.layout.activity_ebook);
        this.recyclerView = (RecyclerView) findViewById(C0671R.C0674id.ebook_list);
        this.tx1 = (TextView) findViewById(C0671R.C0674id.wait2);
        this.sref = FirebaseStorage.getInstance().getReference();
        this.fstore = FirebaseFirestore.getInstance();
        IntentFilter intentFilter2 = new IntentFilter();
        this.intentFilter = intentFilter2;
        intentFilter2.addAction(BroadcastString);
        startService(new Intent(this, InternetService.class));
        alertDialog();
        if (!isOnline(this)) {
            this.dialog.show();
        }
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.sref.child("Ebooks").listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            public void onSuccess(ListResult listResult) {
                for (StorageReference list : listResult.getItems()) {
                    Ebook.this.listData.add(new Ebooklist(list.getName()));
                }
                EbooklistAdapter unused = Ebook.this.adapter = new EbooklistAdapter(Ebook.this.listData, Ebook.this);
                Ebook.this.tx1.setVisibility(8);
                Ebook.this.recyclerView.setAdapter(Ebook.this.adapter);
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(Exception e) {
                Ebook ebook = Ebook.this;
                Toast.makeText(ebook, "" + e.getMessage(), 0).show();
            }
        });
        BottomNavigationView bottom = (BottomNavigationView) findViewById(C0671R.C0674id.bottomNav);
        bottom.setSelectedItemId(C0671R.C0674id.ebook);
        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case C0671R.C0674id.ebook /*2131296444*/:
                        return true;
                    case C0671R.C0674id.home /*2131296505*/:
                        Ebook.this.startActivity(new Intent(Ebook.this, Home.class));
                        Ebook.this.overridePendingTransition(0, 0);
                        return true;
                    case C0671R.C0674id.profile /*2131296644*/:
                        Ebook.this.startActivity(new Intent(Ebook.this, Profile.class));
                        Ebook.this.overridePendingTransition(0, 0);
                        return true;
                    case C0671R.C0674id.quiz /*2131296652*/:
                        Ebook.this.startActivity(new Intent(Ebook.this, Quiz.class));
                        Ebook.this.overridePendingTransition(0, 0);
                        return true;
                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }
            }
        });
    }

    public void onebookClick(int position) {
        final String ebook_name = this.listData.get(position).getEbook_Name();
        StorageReference storageReference = this.sref;
        final StorageReference islandRef = storageReference.child("Ebooks/" + ebook_name);
        AlertDialog.Builder error = new AlertDialog.Builder(this);
        error.setTitle((CharSequence) "Download");
        error.setIcon((int) C0671R.C0673drawable.ic_file_download_black_24dp);
        error.setMessage((CharSequence) "Do you want to download this file ? \n" + ebook_name);
        error.setCancelable(false);
        error.setPositiveButton((CharSequence) "Yes", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Ebook.this.download(islandRef, ebook_name);
            }
        }).setNegativeButton((CharSequence) "No", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        this.dialog1 = error.create();
        error.show();
    }

    public void download(StorageReference stref, final String e_name) {
        stref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            public void onSuccess(Uri uri) {
                Ebook ebook = Ebook.this;
                ebook.downloadFile(ebook, e_name, uri);
                Alerter.create(Ebook.this).setIcon((int) C0671R.C0673drawable.ic_file_download_black_24dp).setTitle("Your Downloading has started !!! ").setText("Check Documents folder after completion of download").enableVibration(true).enableProgress(true).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(Exception e) {
                Ebook ebook = Ebook.this;
                Toast.makeText(ebook, "" + e.getMessage(), 1).show();
            }
        });
    }

    /* access modifiers changed from: private */
    public void downloadFile(Ebook ebook, String ebook_name, Uri uri) {
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(1);
        request.setDestinationInExternalPublicDir("Documents", "/Time2Study/" + ebook_name);
        ((DownloadManager) ebook.getSystemService("download")).enqueue(request);
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
                Ebook ebook = Ebook.this;
                if (!ebook.isOnline(ebook)) {
                    Ebook.this.dialog.show();
                } else {
                    Ebook.this.dialog.dismiss();
                }
            }
        });
        this.dialog = error.create();
    }
}
