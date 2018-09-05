package com.example.android.tabsetup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class StudentResultAdapter extends RecyclerView.Adapter<StudentResultAdapter.ViewHolder> {
    List<StudentResult> results;

    //Constructor for results array.
    public StudentResultAdapter(List<StudentResult> results) {
        this.results = results;
    }

    @Override
    public StudentResultAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_result_row, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final StudentResultAdapter.ViewHolder holder, final int position) {
        holder.studentName.setText(results.get(position).getFirst_name() + " " + results.get(position).getLast_name());
        holder.studentResult.setText(Integer.toString(results.get(position).getScore()));
        holder.studentResultID.setText(Integer.toString(results.get(position).getStudentID()));

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView studentName;
        public TextView studentResult;
        public TextView studentResultID;



        public ViewHolder(View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.studentName);
            studentResult = itemView.findViewById(R.id.studentResult);
            studentResultID = itemView.findViewById(R.id.studentResultID);

        }
    }
}
