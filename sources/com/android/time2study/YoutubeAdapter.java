package com.android.time2study;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class YoutubeAdapter extends RecyclerView.Adapter<YoutubeViewHolder> {
    ArrayList<DataSetList> arrayList;
    Context context;

    public YoutubeAdapter(ArrayList<DataSetList> arrayList2, Context context2) {
        this.arrayList = arrayList2;
        this.context = context2;
    }

    public YoutubeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new YoutubeViewHolder(LayoutInflater.from(this.context).inflate(C0671R.layout.video_view, viewGroup, false), this.arrayList.size());
    }

    public void onBindViewHolder(YoutubeViewHolder youtubeViewHolder, int i) {
        final DataSetList current = this.arrayList.get(i);
        youtubeViewHolder.webView.loadUrl(current.getLink());
        youtubeViewHolder.button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(YoutubeAdapter.this.context, VideoFullScreen.class);
                i.putExtra("link", current.getLink());
                YoutubeAdapter.this.context.startActivity(i);
            }
        });
    }

    public int getItemCount() {
        return this.arrayList.size();
    }
}
