package com.example.android.tabsetup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

public class ExamList extends Fragment {

    FloatingActionButton examFab;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_exam_tab, container, false);

        AppDatabase db = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class,
                "production").allowMainThreadQueries().build();

        List<Exam> exams = db.ExamDao().getAllExams();

        recyclerView = rootView.findViewById(R.id.examRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ExamAdapter(exams);
        recyclerView.setAdapter(adapter);

        examFab = rootView.findViewById(R.id.examFab);
        examFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExamCreator newExam = new ExamCreator(getActivity() );
                newExam.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                newExam.setCancelable(false);
                newExam.show();
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


}
