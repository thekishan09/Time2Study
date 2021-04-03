package com.android.time2study;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class EbooklistAdapter extends RecyclerView.Adapter<ViewHolder> {
    Context context;
    private ebookListener ebookListener;
    private List<Ebooklist> listData;

    public interface ebookListener {
        void onebookClick(int i);
    }

    public EbooklistAdapter(List<Ebooklist> listData2, ebookListener ebookListener2) {
        this.listData = listData2;
        this.ebookListener = ebookListener2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(C0671R.layout.ebooklist_view, parent, false);
        this.context = parent.getContext();
        return new ViewHolder(view, this.ebookListener);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.ebook_name.setText(this.listData.get(position).getEbook_Name());
    }

    public int getItemCount() {
        return this.listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ebookListener ebookListener;
        TextView ebook_name;

        public ViewHolder(View itemView, ebookListener ebookListener2) {
            super(itemView);
            this.ebook_name = (TextView) itemView.findViewById(C0671R.C0674id.ebook_name);
            itemView.setOnClickListener(this);
            this.ebookListener = ebookListener2;
        }

        public void onClick(View view) {
            this.ebookListener.onebookClick(getAbsoluteAdapterPosition());
        }
    }
}
