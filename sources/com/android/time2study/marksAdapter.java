package com.android.time2study;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class marksAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    private final List<markslist> data;

    public marksAdapter(List<markslist> data2, Context context2) {
        this.data = data2;
        this.context = context2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(C0671R.layout.marks_view, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.marks.setText(this.data.get(position).getMarks());
    }

    public int getItemCount() {
        return this.data.size();
    }

    public static class markslist {
        String marks;

        markslist() {
        }

        markslist(String marks2) {
            this.marks = marks2;
        }

        public String getMarks() {
            return this.marks;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView marks;

        public ViewHolder(View view) {
            super(view);
            this.marks = (TextView) view.findViewById(C0671R.C0674id.marks);
        }
    }
}
