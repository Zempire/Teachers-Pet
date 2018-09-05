package com.example.android.tabsetup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ViewHolder> {
    List<Exam> exams;
    List<StudentResult> results;

    //For controlling expansion of just 1 ViewHolder.
    private int mExpandedPosition = -1;
    private int previousExpandPosition = -1;

    //Constructor for exams array.
    public ExamAdapter(List<Exam> exams) {
        this.exams = exams;
    }

    @Override
    public ExamAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ExamAdapter.ViewHolder holder, final int position) {
        holder.examName.setText(exams.get(position).getExamName());
        holder.examLocation.setText("Location: " + exams.get(position).getExamLocation());
        holder.examDateTime.setText(exams.get(position).getDateTime());

        AppDatabase db = Room.databaseBuilder(holder.examName.getContext(), AppDatabase.class,
                "production").allowMainThreadQueries().build();

        results = db.StudentExamDao().getResults(exams.get(position).getExam_ID());
        StudentResultAdapter adapter = new StudentResultAdapter(results);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder.examName.getContext()));
        holder.recyclerView.setAdapter(adapter);

        final boolean isExpanded = position == mExpandedPosition;
        holder.optionsContainer.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
//        holder.examContainer.setBackgroundResource(isExpanded ? R.color.taskExpanded : R.color.taskSmall);
        holder.itemView.setActivated(isExpanded);

        if (isExpanded)
            previousExpandPosition = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1 : position;
                notifyItemChanged(previousExpandPosition);
                notifyItemChanged(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return exams.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView examName;
        public TextView examDateTime;
        public TextView examLocation;
        public ConstraintLayout examContainer, optionsContainer;
        public Button deleteTaskBtn;
        RecyclerView recyclerView;
        RecyclerView.Adapter adapter;

        public ViewHolder(View itemView) {
            super(itemView);
            examName = itemView.findViewById(R.id.examName);
            examDateTime = itemView.findViewById(R.id.dateTime);
            examLocation = itemView.findViewById(R.id.examLocation);
            examContainer = itemView.findViewById(R.id.examContainer);
            optionsContainer = itemView.findViewById(R.id.optionsContainer);
            recyclerView = itemView.findViewById(R.id.resultsList);

        }
    }
}
