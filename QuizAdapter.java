package com.android.time2study;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<ViewHolder> {
    List<Quizlist> arrayList;
    Context context;
    QuizListener quizlistener;

    public interface QuizListener {
        void onQuizClick(int i);
    }

    public QuizAdapter(List<Quizlist> arrayList2, QuizListener quizlistener2) {
        this.arrayList = arrayList2;
        this.quizlistener = quizlistener2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context2 = parent.getContext();
        this.context = context2;
        return new ViewHolder(LayoutInflater.from(context2).inflate(C0671R.layout.quiz_view, parent, false), this.quizlistener);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(this.arrayList.get(position).getQuiz_Name());
    }

    public int getItemCount() {
        return this.arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        QuizListener quizlistener;

        public ViewHolder(View itemView, QuizListener quizlistener2) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(C0671R.C0674id.name);
            itemView.setOnClickListener(this);
            this.quizlistener = quizlistener2;
        }

        public void onClick(View view) {
            this.quizlistener.onQuizClick(getAbsoluteAdapterPosition());
        }
    }
}
