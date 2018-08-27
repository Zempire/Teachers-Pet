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
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

public class StudentList extends Fragment {

    FloatingActionButton studentFab;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
//    ArrayList<Student> students;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_student_tab, container, false);
        recyclerView = rootView.findViewById(R.id.studentRecycler);

//        students = new ArrayList<>();
//
//        for (int i = 0; i < 30; i++) {
//            Student student = new Student(19191212, "Gino Clone" + i, "Zem",
//                    "123 Hello Road", "09/031987", "Male", "Bachelor of Shit");
//            students.add(student);
//        }

        AppDatabase db = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class,
                "production").allowMainThreadQueries().build();

        List<Student> students = db.UserDao().getAllStudents();


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
