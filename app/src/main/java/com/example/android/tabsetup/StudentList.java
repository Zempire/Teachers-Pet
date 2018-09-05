package com.example.android.tabsetup;

import android.app.Activity;
import android.content.Intent;
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

public class StudentList extends Fragment {

    FloatingActionButton studentFab;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_student_tab, container, false);

        AppDatabase db = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class,
                "production").allowMainThreadQueries().build();

        List<Student> students = db.StudentDao().getAllStudents();

        recyclerView = rootView.findViewById(R.id.studentRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // Note the reference for LinearLayoutManager is getActivity() not "this"
        adapter = new StudentAdapter(students);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        studentFab = getView().findViewById(R.id.studentFab);
        studentFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), StudentCreator.class);
                startActivity(intent);
            }
        });
    }


}
