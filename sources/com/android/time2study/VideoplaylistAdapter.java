package com.android.time2study;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

class VideoplaylistAdapter extends RecyclerView.Adapter<ViewHolder> {
    Context context;
    private List<Videoplaylist> listData;
    private PlaylistrecyclerListener playlistrecyclerListener;

    public interface PlaylistrecyclerListener {
        void onplaylistClick(int i);
    }

    public VideoplaylistAdapter(List<Videoplaylist> listData2, PlaylistrecyclerListener playlistrecyclerListener2) {
        this.listData = listData2;
        this.playlistrecyclerListener = playlistrecyclerListener2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(C0671R.layout.youtube_playlist_view, parent, false);
        this.context = parent.getContext();
        return new ViewHolder(view, this.playlistrecyclerListener);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.topic_name.setText(this.listData.get(position).getTopic_Name());
    }

    public int getItemCount() {
        return this.listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final CardView card_view;
        PlaylistrecyclerListener playlistrecyclerListener;
        /* access modifiers changed from: private */
        public TextView topic_name;

        public ViewHolder(View itemView, PlaylistrecyclerListener playlistrecyclerListener2) {
            super(itemView);
            this.card_view = (CardView) itemView.findViewById(C0671R.C0674id.topic_playlist);
            this.topic_name = (TextView) itemView.findViewById(C0671R.C0674id.topic_name);
            itemView.setOnClickListener(this);
            this.playlistrecyclerListener = playlistrecyclerListener2;
        }

        public void onClick(View view) {
            this.playlistrecyclerListener.onplaylistClick(getAbsoluteAdapterPosition());
        }
    }
}
