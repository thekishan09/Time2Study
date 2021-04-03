package com.android.time2study;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.internal.view.SupportMenu;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class Profile extends AppCompatActivity {
    public static String BroadcastString = "checkinternet";
    public static final String CHECKBOX = "checkbox";
    public static final String SHARED_PREFS = "shared_prefs";
    private static final int STORAGE_PERMISSION_CODE = 29;
    Button about;
    TextView city;

    /* renamed from: cl */
    ConstraintLayout f74cl;
    TextView class1;
    TextView contact;
    AlertDialog dialog;
    AlertDialog dialog1;
    TextView dob;
    Button edit;
    ImageView edit_pic;
    TextView email;
    FirebaseFirestore fstore;
    TextView gender;
    File imgFile;
    private IntentFilter intentFilter;
    ProgressBar loading;
    Button logout;
    public BroadcastReceiver myReciever = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Profile.BroadcastString) && !intent.getStringExtra("online_status").equals("true")) {
                Profile.this.dialog.show();
            }
        }
    };
    TextView name;
    TextView name1;
    ImageView profile_pic;
    StorageReference sref;
    int temp1 = 1;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0671R.layout.activity_profile);
        BottomNavigationView bottom = (BottomNavigationView) findViewById(C0671R.C0674id.bottomNav);
        this.f74cl = (ConstraintLayout) findViewById(C0671R.C0674id.constraintLayout);
        this.email = (TextView) findViewById(C0671R.C0674id.emailid);
        this.dob = (TextView) findViewById(C0671R.C0674id.dob);
        this.name = (TextView) findViewById(C0671R.C0674id.name);
        this.name1 = (TextView) findViewById(C0671R.C0674id.name1);
        this.contact = (TextView) findViewById(C0671R.C0674id.contact);
        this.city = (TextView) findViewById(C0671R.C0674id.city);
        this.class1 = (TextView) findViewById(C0671R.C0674id.class_info);
        this.gender = (TextView) findViewById(C0671R.C0674id.gender);
        this.edit = (Button) findViewById(C0671R.C0674id.button);
        this.logout = (Button) findViewById(C0671R.C0674id.logout);
        this.about = (Button) findViewById(C0671R.C0674id.about);
        this.profile_pic = (ImageView) findViewById(C0671R.C0674id.profile_pic);
        this.edit_pic = (ImageView) findViewById(C0671R.C0674id.edit_pic);
        this.loading = (ProgressBar) findViewById(C0671R.C0674id.loading);
        this.fstore = FirebaseFirestore.getInstance();
        this.sref = FirebaseStorage.getInstance().getReference();
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory("Pictures");
        File file = new File(externalStoragePublicDirectory, FirebaseAuth.getInstance().getCurrentUser().getUid() + ".jpg");
        this.imgFile = file;
        if (file.exists() && checkIfAlreadyhavePermission()) {
            this.profile_pic.setImageBitmap(BitmapFactory.decodeFile(this.imgFile.getAbsolutePath()));
        } else if (checkIfAlreadyhavePermission()) {
            downloadImage();
        } else {
            loadImage();
        }
        IntentFilter intentFilter2 = new IntentFilter();
        this.intentFilter = intentFilter2;
        intentFilter2.addAction(BroadcastString);
        startService(new Intent(this, InternetService.class));
        alertDialog();
        if (!isOnline(this)) {
            this.dialog.show();
        }
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            this.email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
            this.fstore.collection("User_Details").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                public void onEvent(DocumentSnapshot value, FirebaseFirestoreException error) {
                    if (error != null) {
                        Profile profile = Profile.this;
                        Toast.makeText(profile, "" + error.getMessage() + "load", 0).show();
                    }
                    Profile.this.name.setText(value.getString("Full_Name"));
                    Profile.this.name1.setText(value.getString("Full_Name"));
                    Profile.this.dob.setText(value.getString("Date Of Birth"));
                    TextView textView = Profile.this.class1;
                    textView.setText("Class " + value.getString("Class"));
                    Profile.this.city.setText(value.getString("City"));
                    Profile.this.gender.setText(value.getString("Gender"));
                    Profile.this.contact.setText(value.getString("Mobile No"));
                }
            });
        }
        bottom.setSelectedItemId(C0671R.C0674id.profile);
        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case C0671R.C0674id.ebook /*2131296444*/:
                        Profile.this.startActivity(new Intent(Profile.this, Ebook.class));
                        Profile.this.overridePendingTransition(0, 0);
                        return true;
                    case C0671R.C0674id.home /*2131296505*/:
                        Profile.this.startActivity(new Intent(Profile.this, Home.class));
                        Profile.this.overridePendingTransition(0, 0);
                        return true;
                    case C0671R.C0674id.profile /*2131296644*/:
                        return true;
                    case C0671R.C0674id.quiz /*2131296652*/:
                        Profile.this.startActivity(new Intent(Profile.this, Quiz.class));
                        Profile.this.overridePendingTransition(0, 0);
                        return true;
                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }
            }
        });
        this.edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(Profile.this, Register1.class);
                i.putExtra("edit", "edit");
                i.putExtra("user", "");
                Profile.this.startActivity(i);
            }
        });
        this.logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(Profile.this, "You Are logged Out", 0).show();
                Intent in = new Intent(Profile.this, Login.class);
                in.putExtra("logout", "logout");
                in.putExtra("new", "");
                Profile.this.startActivity(in);
            }
        });
        this.about.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AlertDialog.Builder error = new AlertDialog.Builder(Profile.this);
                error.setTitle((CharSequence) "About Us");
                error.setMessage((CharSequence) "V.K Mathematics Classes\n\nThanks for your contribution as you are using our app ! Our Institute bring talents forward and show them a way so that they will do well in their career. Our Institute provides education for 6 to 12th standard students in Math and Science  Subject for BSEB and CBSE board . A Special Batch for Railway/SSC/Banking as well as Polytechnic Preparation \n For more details :- #9006089081 ,You can contact us on our centre at Behind of SBI ATM,Chandni Chowk,Sheikhpura");
                error.setIcon((int) C0671R.C0673drawable.ic_mode_edit_black_24dp);
                error.setCancelable(false);
                error.setPositiveButton((CharSequence) "Ok", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Profile.this.dialog1.dismiss();
                    }
                });
                Profile.this.dialog1 = error.create();
                Profile.this.dialog1.show();
            }
        });
        this.edit_pic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT < 23) {
                    Profile.this.startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 2929);
                } else if (!Profile.this.checkIfAlreadyhavePermission()) {
                    Profile.this.requestForSpecificPermission();
                } else {
                    Profile.this.startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 2929);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void requestForSpecificPermission() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.WRITE_EXTERNAL_STORAGE")) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 29);
            return;
        }
        Snackbar.make((View) this.f74cl, (CharSequence) "Permission needed", 0).setAction((CharSequence) "Settings", (View.OnClickListener) new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent.setData(Uri.fromParts("package", Profile.this.getPackageName(), (String) null));
                Profile.this.startActivity(intent);
            }
        }).setActionTextColor((int) SupportMenu.CATEGORY_MASK).show();
    }

    /* access modifiers changed from: private */
    public boolean checkIfAlreadyhavePermission() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            return true;
        }
        return false;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != 29) {
            return;
        }
        if (grantResults.length <= 0 || grantResults[0] != 0) {
            requestForSpecificPermission();
        } else {
            startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 2929);
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2929 && resultCode == -1) {
            this.profile_pic.setImageURI(data.getData());
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, new ByteArrayOutputStream());
                uploadImageToFirebase(Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "profile.jpg", (String) null)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    public void downloadImage() {
        StorageReference storageReference = this.sref;
        storageReference.child("users_pic/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/profile.jpg").getFile(this.imgFile).addOnSuccessListener((OnSuccessListener) new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Profile.this.profile_pic.setImageBitmap(BitmapFactory.decodeFile(Profile.this.imgFile.getAbsolutePath()));
            }
        }).addOnFailureListener((OnFailureListener) new OnFailureListener() {
            public void onFailure(Exception e) {
                Profile profile = Profile.this;
                Toast.makeText(profile, "" + e.getMessage(), 1).show();
            }
        });
    }

    private void uploadImageToFirebase(Uri img_uri) {
        StorageReference storageReference = this.sref;
        storageReference.child("users_pic/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/profile.jpg").putFile(img_uri).addOnSuccessListener((OnSuccessListener) new OnSuccessListener<UploadTask.TaskSnapshot>() {
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Profile.this.downloadImage();
                Toast.makeText(Profile.this, "Successfully Uploaded", 1).show();
            }
        }).addOnFailureListener((OnFailureListener) new OnFailureListener() {
            public void onFailure(Exception e) {
                Profile profile = Profile.this;
                Toast.makeText(profile, "" + e.getMessage(), 1).show();
            }
        });
    }

    private void loadImage() {
        StorageReference storageReference = this.sref;
        storageReference.child("users_pic/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/profile.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(Profile.this.profile_pic);
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(Exception e) {
                Profile profile = Profile.this;
                Toast.makeText(profile, "" + e.getMessage(), 1).show();
            }
        });
    }

    public void onBackPressed() {
        moveTaskToBack(true);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        this.temp1 = 0;
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
                Profile profile = Profile.this;
                if (!profile.isOnline(profile)) {
                    Profile.this.dialog.show();
                } else {
                    Profile.this.dialog.dismiss();
                }
            }
        });
        this.dialog = error.create();
    }
}
