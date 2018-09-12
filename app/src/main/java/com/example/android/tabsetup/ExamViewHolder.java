package com.example.android.tabsetup;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

public class ExamViewHolder extends RecyclerView.ViewHolder {
    TextView examName;
    TextView examID;
    TextView examDateTime;
    TextView examLocation;
    ConstraintLayout examContainer, optionsContainer;
    Button deleteExamBtn;
    ToggleButton toggleTaskInfo;
    CheckBox multiSelectBox;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;


    ExamList examList; //Allows use of ExamList's onLongClickListener
    Exam item;
    ExamListener listener;

    // Allows the passing of methods from the base activity for better communication between
    // the classes and also lightens the load on the ViewHolder too.
    public interface ExamListener{
        void deleteExam(final Exam item);
        void completeTask(Task item);
        void revisitTask(Task item);
        void commitChange(Task item);
        void prepareSelection(View view, int position);
    }

    public ExamViewHolder(View itemView, final ExamListener listener, ExamList examList) {
        super(itemView);
        this.listener = listener;
        this.examList = examList;
        examID = itemView.findViewById(R.id.examID);
        examName = itemView.findViewById(R.id.examName);
        examDateTime = itemView.findViewById(R.id.dateTime);
        examLocation = itemView.findViewById(R.id.examLocation);
        examContainer = itemView.findViewById(R.id.examContainer);
        optionsContainer = itemView.findViewById(R.id.optionsContainer);
        deleteExamBtn = itemView.findViewById(R.id.deleteExamBtn);
        recyclerView = itemView.findViewById(R.id.resultsList);
        toggleTaskInfo = itemView.findViewById(R.id.toggleExamInfo);
        multiSelectBox = itemView.findViewById(R.id.multiSelectBox);

        deleteExamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.deleteExam(item);
            }
        });

    }

    public void setItem(Exam item) {
        this.item = item;
        examID.setText(Integer.toString(item.getExam_ID()));
        examName.setText(item.getExamName());
        examLocation.setText(item.getExamLocation());
        examDateTime.setText(item.getDateTime());

    }
}