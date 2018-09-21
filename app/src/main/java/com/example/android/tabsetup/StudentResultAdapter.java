package com.example.android.tabsetup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class StudentResultAdapter extends RecyclerView.Adapter<StudentResultAdapter.ViewHolder> {
    List<StudentExamResult> results;

    //Constructor for results array.
    public StudentResultAdapter(List<StudentExamResult> results) {
        this.results = results;
    }

    @Override
    public StudentResultAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_result_row, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final StudentResultAdapter.ViewHolder holder, final int position) {
        holder.examName.setText(results.get(position).getExam_name());
        System.out.println("MY EXAM NAME IS " + results.get(position).getExam_name());
        holder.studentResult.setText(Integer.toString(results.get(position).getScore()));
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView examName;
        public TextView studentResult;


        public ViewHolder(View itemView) {
            super(itemView);
            examName = itemView.findViewById(R.id.examName);
            studentResult = itemView.findViewById(R.id.studentResult);

        }
    }
}
