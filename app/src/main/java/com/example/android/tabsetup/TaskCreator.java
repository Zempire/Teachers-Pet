package com.example.android.tabsetup;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import static android.R.layout.simple_spinner_dropdown_item;

public class TaskCreator extends AppCompatActivity {
    EditText taskName, taskDescription, taskLocation;
    Button saveTask, cancelTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        taskName = findViewById(R.id.taskName);
        taskDescription = findViewById(R.id.taskDescription);
        taskLocation = findViewById(R.id.taskLocation);
        cancelTask = findViewById(R.id.task_cancel_btn);
        saveTask = findViewById(R.id.task_save_btn);


        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
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
                finish();
            }
        });
    }
}
