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

public class TaskViewHolder extends RecyclerView.ViewHolder {

    boolean isExpanded = false;
    TextView taskID;
    TextView taskName;
    TextView taskDesc;
    EditText taskDescEdit;
    TextView taskDate;
    TextView taskLocation;
    ConstraintLayout taskContainer, optionsContainer;
    Button deleteTaskBtn, commitTaskBtn;
    ImageView toggleTaskInfo;
    CheckBox multiSelectBox, taskStatusBox;
    TaskList taskList; //Allows use of TaskList's onLongClickListener
    Task item;
    TaskListener listener;

    // Allows the passing of methods from the base activity for better communication between
    // the classes and also lightens the load on the ViewHolder too.
    public interface TaskListener{
        void deleteTask(Task item);
        void completeTask(Task item);
        void revisitTask(Task item);
        void commitChange(Task item);
        void prepareSelection(View view, int position);
        void expandView(boolean isExpanded, int position);
    }

    public TaskViewHolder(View itemView, final TaskListener listener, final TaskList taskList) {
        super(itemView);
        this.listener = listener;
        this.taskList = taskList;
        taskID = itemView.findViewById(R.id.task_ID);
        taskName = itemView.findViewById(R.id.task_name);
        taskDesc = itemView.findViewById(R.id.task_desc);
        taskDescEdit = itemView.findViewById(R.id.task_desc_edit);
        taskDate = itemView.findViewById(R.id.task_date);
        taskLocation = itemView.findViewById(R.id.task_location);
        taskContainer = itemView.findViewById(R.id.taskContainer);
        optionsContainer = itemView.findViewById(R.id.optionsContainer);
        deleteTaskBtn = itemView.findViewById(R.id.deleteTaskBtn);
        taskStatusBox = itemView.findViewById(R.id.taskStatusBox);
        toggleTaskInfo = itemView.findViewById(R.id.toggleTaskInfo);
        multiSelectBox = itemView.findViewById(R.id.taskMultiSelectBox);
        toggleTaskInfo.setEnabled(false);
        commitTaskBtn = itemView.findViewById(R.id.commitTaskBtn);

        taskContainer.setOnLongClickListener(taskList);

        taskContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (taskList.is_in_action_mode) {
                    if (multiSelectBox.isChecked()) {
                        multiSelectBox.setChecked(false);
                    } else {
                        multiSelectBox.setChecked(true);
                    }
                    taskContainer.setBackgroundResource(multiSelectBox.isChecked() ?
                            R.color.deleteObject : R.color.cardBackground);
                    listener.prepareSelection(multiSelectBox, getAdapterPosition());
                } else {
                    listener.expandView(isExpanded, getAdapterPosition());
                }
            }
        });
        taskDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskDesc.setVisibility(View.INVISIBLE);
                taskDescEdit.setVisibility(View.VISIBLE);
                commitTaskBtn.setVisibility(View.VISIBLE);
                taskDescEdit.requestFocus();
            }
        });
        deleteTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { listener.deleteTask(item);
            }
        });
        taskStatusBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (taskStatusBox.isChecked()) {
                    listener.completeTask(item);
                } else {
                    listener.revisitTask(item);
                }
            }
        });
        commitTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setTaskDesc(taskDescEdit.getText().toString());
                commitTaskBtn.setVisibility(View.GONE);
                taskDesc.setVisibility(View.VISIBLE);
                taskDescEdit.setVisibility(View.INVISIBLE);
                listener.commitChange(item);
                taskContainer.requestFocus();
            }
        });
    }

    public void setItem(Task item) {
        this.item = item;
        taskName.setText(item.getTaskName());
        taskID.setText(Integer.toString(item.getTask_ID()));
        taskLocation.setText(item.getTaskLocation());
        taskDesc.setText(item.getTaskDesc());
        taskDescEdit.setText(item.getTaskDesc());
        taskDate.setText(item.getTaskDate());

        if (item.getTaskStatus() == 1) {
            taskStatusBox.setChecked(true);
        } else {
            taskStatusBox.setChecked(false);
        }
    }
}