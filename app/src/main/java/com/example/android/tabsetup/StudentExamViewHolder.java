package com.example.android.tabsetup;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class StudentExamViewHolder extends RecyclerView.ViewHolder {
    TextView examName;
    CheckBox examCheckBox;
    ConstraintLayout examChooserContainer;
    Exam item;
    StudentExamListener listener;

    // Allows the passing of methods from the base activity for better communication between
    // the classes and also lightens the load on the ViewHolder too.
    public interface StudentExamListener{
        void prepareSelection(View view, int position);
    }

    public StudentExamViewHolder(View itemView, final StudentExamListener listener) {
        super(itemView);
        this.listener = listener;
        examName = itemView.findViewById(R.id.examName);
        examCheckBox = itemView.findViewById(R.id.examCheckBox);
        examChooserContainer = itemView.findViewById(R.id.examChooserContainer);


    }

    public void setItem(Exam item) {
        this.item = item;
        examName.setText(item.getExamName());

    }
}