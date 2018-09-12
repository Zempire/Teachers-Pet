package com.example.android.tabsetup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

public class ExamList extends Fragment implements ExamViewHolder.ExamListener, View.OnLongClickListener {

    boolean is_in_action_mode = false;
    FloatingActionButton examFab;
    RecyclerView recyclerView;
    NewExamAdapter adapter;
    AppDatabase db;
    List<Exam> exams;
    List<Exam> selectedExams = new ArrayList<>();
    androidx.appcompat.widget.Toolbar toolbar;
    AppCompatActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_exam_tab, container, false);
        recyclerView = rootView.findViewById(R.id.examRecycler);
        examFab = rootView.findViewById(R.id.examFab);

        //Setting up Fragment access to the toolbar and menu.
        toolbar = getActivity().findViewById(R.id.mainToolbar);
        activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        db = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class,
                "production").allowMainThreadQueries().build();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new NewExamAdapter(getLayoutInflater(), this, this);
        recyclerView.setAdapter(adapter);
        exams = db.ExamDao().getAllExams();
        adapter.updateItems(exams);

        examFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExamCreator newExam = new ExamCreator(getActivity() );
                Window window = newExam.getWindow();
                newExam.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                newExam.setCancelable(false);
                newExam.show();
                window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

            }
        });
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_exam, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return true;
    }


    @Override
    public boolean onLongClick(View view) {
        return false;
    }

    @Override
    public void deleteExam(final Exam item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Delete Exam?")
                .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.StudentExamDao().deleteExam(item.getExam_ID());
                        db.ExamDao().deleteExam(item.getExam_ID());
                        exams = db.ExamDao().getAllExams();
                        adapter.updateItems(exams);
                    }
                }).setNegativeButton("CANCEL", null);
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void completeTask(Task item) {

    }

    @Override
    public void revisitTask(Task item) {

    }

    @Override
    public void commitChange(Task item) {

    }

    @Override
    public void prepareSelection(View view, int position) {

    }
}
