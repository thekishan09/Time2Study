package com.android.time2study;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

public class InternetService extends Service {
    Handler handler = new Handler();
    /* access modifiers changed from: private */
    public Runnable periodicUpdate = new Runnable() {
        public void run() {
            InternetService.this.handler.postDelayed(InternetService.this.periodicUpdate, 500 - (SystemClock.elapsedRealtime() % 1000));
            Intent intent = new Intent();
            intent.setAction(Login.BroadcastString);
            StringBuilder sb = new StringBuilder();
            sb.append("");
            InternetService internetService = InternetService.this;
            sb.append(internetService.isOnline(internetService));
            intent.putExtra("online_status", sb.toString());
            InternetService.this.sendBroadcast(intent);
        }
    };

    public void onCreate() {
        super.onCreate();
        Log.d("inside", "Oncreate");
    }

    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Note Yet Implemented");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        this.handler.post(this.periodicUpdate);
        return 1;
    }

    public boolean isOnline(Context con) {
        NetworkInfo net = ((ConnectivityManager) con.getSystemService("connectivity")).getActiveNetworkInfo();
        if (net == null || !net.isConnectedOrConnecting()) {
            return false;
        }
        return true;
    }
}
