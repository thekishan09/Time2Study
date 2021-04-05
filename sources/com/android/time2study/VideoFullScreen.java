package com.android.time2study;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class VideoFullScreen extends AppCompatActivity {
    WebView webView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0671R.layout.activity_video_full_screen);
        this.webView = (WebView) findViewById(C0671R.C0674id.full_video);
        this.webView.loadUrl(getIntent().getStringExtra("link"));
        this.webView.setWebViewClient(new WebViewClient());
        this.webView.setWebChromeClient(new WebChromeClient());
        this.webView.getSettings().setJavaScriptEnabled(true);
    }
}
