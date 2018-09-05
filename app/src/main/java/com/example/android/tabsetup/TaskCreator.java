package com.example.android.tabsetup;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                SimpleDateFormat df = new SimpleDateFormat("kk:mm");
                String currentTime = df.format(c.getTime());
                Task newTask = new Task(taskName.getText().toString(),
                        taskDescription.getText().toString(),
                        "Location: " + taskLocation.getText().toString(),
                        (day + "/" + (month + 1) + "/" + year), currentTime);
                db.TaskDao().insertAllTask(newTask);

                dismiss();
                a.recreate();

                }
            });

        cancelTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Close the Dialog
                dismiss();
            }
        });
    }
}
