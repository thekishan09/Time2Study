package com.android.time2study;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class YoutubeViewHolder extends RecyclerView.ViewHolder {
    Button button;
    CardView cardView;
    WebView webView;

    public YoutubeViewHolder(View itemView, int size) {
        super(itemView);
        this.webView = (WebView) itemView.findViewById(C0671R.C0674id.web_view);
        this.button = (Button) itemView.findViewById(C0671R.C0674id.fullscreen);
        this.cardView = (CardView) itemView.findViewById(C0671R.C0674id.card_video_view);
        this.webView.setWebViewClient(new WebViewClient());
        this.webView.setWebChromeClient(new WebChromeClient());
        this.webView.getSettings().setJavaScriptEnabled(true);
    }
}
