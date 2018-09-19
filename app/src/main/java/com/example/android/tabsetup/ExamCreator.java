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
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import androidx.room.Room;

public class ExamCreator extends Dialog {

    public Activity a;
    public Dialog d;
    EditText examName, examDate, examLocation;
    Button saveExam, cancelExam;
    String date_time = "";
    int day, month, year, hour, minute;


    public ExamCreator(Activity c) {
        super(c);
        this.a = c;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_create_exam);

        examName = findViewById(R.id.examName);
        examDate = findViewById(R.id.examDate);
        examLocation = findViewById(R.id.examLocation);
        cancelExam = findViewById(R.id.exam_cancel_btn);
        saveExam = findViewById(R.id.exam_save_btn);

        examDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker();
            }
        });


        final AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class,
                "production").allowMainThreadQueries().build();

        saveExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (examDate.getText().toString().equals("")) {
                    Toast.makeText(a, "Please enter a date.", Toast.LENGTH_SHORT).show();
                } else {
                    Exam newExam = new Exam(examName.getText().toString(),
                            examLocation.getText().toString(),
                            examDate.getText().toString());
                    Integer examID = (int) (long) db.ExamDao().insertAll(newExam);
                    List<Student> studentList = db.StudentDao().getAllStudents();
                    Random rand = new Random();
                    for (int i = 0; i < studentList.size(); i++) {
                        int score = rand.nextInt(51) + 45;
                        StudentExam newResult = new StudentExam(examID,
                                studentList.get(i).getStudent_ID(), score);
                        System.out.println(newResult.getExamID() + " " + newResult.getStudentID() + " " + newResult.getScore());
                        db.StudentExamDao().insert(newResult);
                    }
                    dismiss();
                    a.recreate();
                }
            }
        });

        cancelExam.setOnClickListener(new View.OnClickListener() {
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
                    public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {

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
                    public void onTimeSet(TimePicker view, int hourOfDay,int minute) {

                        hour = hourOfDay;
                        minute = minute;
                        String formatMinute = minute < 10 ? "0" + Integer.toString(minute) : Integer.toString(minute);
                        examDate.setText(date_time+" "+hourOfDay + ":" + formatMinute);
                    }
                }, hour, minute, false);
        timePickerDialog.show();
    }
}
