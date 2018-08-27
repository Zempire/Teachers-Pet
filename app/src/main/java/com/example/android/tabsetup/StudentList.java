package com.example.android.tabsetup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StudentList extends Fragment {

    FloatingActionButton studentFab;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ArrayList<String> students;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_student_tab, container, false);
        recyclerView = rootView.findViewById(R.id.studentRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // Note the reference for LinearLayoutManager is getActivity() not "this"
        students = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            students.add("Gino's clone number " + i);
        }
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
