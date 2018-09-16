package com.example.android.tabsetup;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.room.Room;

public class TaskCreator extends Dialog {

    public Activity a;
    public Dialog d;
    EditText taskName, taskDescription, taskLocation, taskDate;
    Button saveTask, cancelTask;
    String date_time = "";
    int day, month, year, hour, minute;

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
        taskDate = findViewById(R.id.taskDate);
        cancelTask = findViewById(R.id.task_cancel_btn);
        saveTask = findViewById(R.id.task_save_btn);

        taskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker();
            }
        });

        final AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class,
                "production").allowMainThreadQueries().build();

        saveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task newTask = new Task(taskName.getText().toString(),
                        taskDescription.getText().toString(),
                        taskLocation.getText().toString(), taskDate.getText().toString());
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

    private void datePicker(){
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(a,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        date_time = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        //*************Call Time Picker Here ********************
                        timePicker();
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void timePicker(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(a,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        hour = hourOfDay;
                        minute = minute;
                        String formatMinute = minute < 10 ? "0" + Integer.toString(minute) : Integer.toString(minute);
                        taskDate.setText(date_time+" "+hourOfDay + ":" + formatMinute);
                    }
                }, hour, minute, false);
        timePickerDialog.show();
    }
}
