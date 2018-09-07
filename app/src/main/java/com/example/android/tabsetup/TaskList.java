package com.example.android.tabsetup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
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

public class TaskList extends Fragment {

    FloatingActionButton taskFab;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<Task> tasks;
    AppDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_task_tab, container, false);

        db = Room.databaseBuilder(getContext().getApplicationContext(), AppDatabase.class,
                "production").allowMainThreadQueries().build();

        tasks = db.TaskDao().getUncompletedTasks();

        recyclerView = rootView.findViewById(R.id.taskRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TaskAdapter(tasks);
        recyclerView.setAdapter(adapter);

        taskFab = rootView.findViewById(R.id.taskFab);
        taskFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskCreator newTask = new TaskCreator(getActivity() );
                newTask.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                newTask.setCancelable(false);
                newTask.show();
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
        inflater.inflate(R.menu.menu_task, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch(item.getItemId()) {
                case R.id.action_show_complete:
                    tasks = db.TaskDao().getAllTasks();
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    adapter = new TaskAdapter(tasks);
                    recyclerView.setAdapter(adapter);
                    break;
                case R.id.action_hide_complete:
                    tasks = db.TaskDao().getUncompletedTasks();
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    adapter = new TaskAdapter(tasks);
                    recyclerView.setAdapter(adapter);
                    break;
            }
        return true;
    }
}
