package com.android.time2study;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    public static final String CHECKBOX = "checkbox";
    public static final String SHARED_PREFS = "shared_prefs";
    private static int SPLASH_TIME_OUT = CredentialsApi.CREDENTIAL_PICKER_REQUEST_CODE;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0671R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (FirebaseAuth.getInstance().getCurrentUser() == null || !FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
                    MainActivity.this.startActivity(new Intent(MainActivity.this, Login.class));
                    MainActivity.this.finish();
                    return;
                }
                MainActivity.this.startActivity(new Intent(MainActivity.this, Home.class));
                MainActivity.this.finish();
            }
        }, (long) SPLASH_TIME_OUT);
    }

    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
