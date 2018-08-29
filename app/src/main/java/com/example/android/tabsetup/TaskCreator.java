package com.example.android.tabsetup;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class TaskCreator extends Dialog {

    public Activity a;
    public Dialog d;
    EditText taskName, taskDescription, taskLocation;
    Button saveTask, cancelTask;

    public TaskCreator(Activity c) {
        super(c);
        this.a = c;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_create_task);

        taskName = findViewById(R.id.taskName);
        taskDescription = findViewById(R.id.taskDescription);
        taskLocation = findViewById(R.id.taskLocation);
        cancelTask = findViewById(R.id.task_cancel_btn);
        saveTask = findViewById(R.id.task_save_btn);


        final AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class,
                "production").allowMainThreadQueries().build();

        saveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Task newTask = new Task(taskName.getText().toString(),
                            taskDescription.getText().toString(),
                            taskLocation.getText().toString(), "29/08/2018");
                    db.UserDao().insertAllTask(newTask);
                }
            });

        cancelTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Close the Dialog
            }
        });
    }
}
