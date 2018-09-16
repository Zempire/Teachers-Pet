package com.example.android.tabsetup;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.CheckBox;
import android.widget.TextView;

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

public class TaskList extends Fragment implements TaskViewHolder.TaskListener, View.OnLongClickListener {

    boolean is_in_action_mode = false;
    int mExpandedPosition = -1;
    int previousExpandPosition = -1;
    int deleteCount = 0;
    FloatingActionButton taskFab;
    TextView toolbarText;
    RecyclerView recyclerView;
    TaskAdapter adapter;
    AppDatabase db;
    List<Task> tasks;
    List<Task> selectedTasks = new ArrayList<>();
    androidx.appcompat.widget.Toolbar toolbar;
    AppCompatActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_task_tab, container, false);
        recyclerView = rootView.findViewById(R.id.taskRecycler);
        taskFab = rootView.findViewById(R.id.taskFab);

        //Setting up Fragment access to the toolbar and menu.
        toolbar = getActivity().findViewById(R.id.mainToolbar);
        activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbarText = toolbar.findViewById(R.id.toolbar_text);
        toolbarText.setText("0 SELECTED");
        toolbarText.setVisibility(View.GONE);

        db = Room.databaseBuilder(getContext().getApplicationContext(), AppDatabase.class,
                "production").allowMainThreadQueries().build();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TaskAdapter(getLayoutInflater(), this, this);
        recyclerView.setAdapter(adapter);
        tasks = db.TaskDao().getUncompletedTasks();
        adapter.updateItems(tasks);

        taskFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskCreator newTask = new TaskCreator(getActivity() );
                Window window = newTask.getWindow();
                newTask.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                newTask.setCancelable(false);
                newTask.show();
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
        inflater.inflate(R.menu.menu_task, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch(item.getItemId()) {
                case R.id.massDelete:                   // Deletes all chosen tasks.
                    deleteAllSelected(selectedTasks);
                    tasks = db.TaskDao().getAllTasks();
                    finishActionMode();
                    break;
                case R.id.action_delete_task:           // Starts mode for deleting multiple tasks.
                    startActionMode();
                    break;
                case android.R.id.home:                // Closes mode for deleting multiple tasks.
                    tasks = db.TaskDao().getAllTasks();
                    finishActionMode();
                    break;
                case R.id.action_show_complete:
                    tasks = db.TaskDao().getAllTasks();
                    adapter.updateItems(tasks);
                    break;
                case R.id.action_hide_complete:
                    tasks = db.TaskDao().getUncompletedTasks();
                    adapter.updateItems(tasks);
                    break;
            }
        return true;
    }

    @Override
    public boolean onLongClick(View view) {
        startActionMode();
        return true;
    }

    //Implemented Methods to be passed to the ViewHolder Interface.
    @Override
    public void deleteTask(final Task item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Delete Task?")
                .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.TaskDao().deleteTask(item.getTask_ID());
                        tasks = db.TaskDao().getUncompletedTasks();
                        adapter.updateItems(tasks);
                    }
                }).setNegativeButton("CANCEL", null);

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void completeTask(Task item) {
        item.setTaskStatus(1);
        db.TaskDao().updateTask(item);
        tasks = db.TaskDao().getUncompletedTasks();
        adapter.updateItems(tasks);
    }

    @Override
    public void revisitTask(Task item) {
        item.setTaskStatus(0);
        db.TaskDao().updateTask(item);
        tasks = db.TaskDao().getAllTasks();
        adapter.updateItems(tasks);
    }

    @Override
    public void commitChange(Task item) {
        db.TaskDao().updateTask(item);
        tasks = db.TaskDao().getUncompletedTasks();
        adapter.updateItems(tasks);
    }

    @Override
    public void prepareSelection(View view, int position) {
        if(((CheckBox)view).isChecked()) {
            selectedTasks.add(tasks.get(position));
            deleteCount +=1;
        } else {
            selectedTasks.remove(tasks.get(position));
            deleteCount -= 1;
        }
        updateCounter(deleteCount);
    }


    @Override
    public void expandView(boolean isExpanded, int position) {
        mExpandedPosition = isExpanded ? -1:position;
        adapter.notifyItemChanged(previousExpandPosition);
        adapter.notifyItemChanged(position);
        adapter.updateItems(tasks);
    }

    // Changes menu and starts multi delete students mode.
    public void startActionMode() {
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.menu_action_mode);
        is_in_action_mode = true;
        toolbarText.setVisibility(View.VISIBLE);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        adapter.notifyDataSetChanged();
    }

    // Ends the multi delete students mode.
    public void finishActionMode() {
        is_in_action_mode = false;
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.menu_task);
        toolbarText.setVisibility(View.GONE);
        deleteCount = 0;
        toolbarText.setText("0 SELECTED");
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(true);
        selectedTasks = new ArrayList<>();
        adapter.updateItems(tasks);

    }

    // Deletes all selected students from the database.
    public void deleteAllSelected(List<Task> selectedTasks){
        for (int i = 0; i < selectedTasks.size();i++) {
            db.TaskDao().deleteTask(selectedTasks.get(i).getTask_ID());
        }
    }

    public void updateCounter(int counter) {
        if (counter == 0) {
            toolbarText.setText("0 SELECTED");
        } else {
            toolbarText.setText(counter + " SELECTED");
        }
    }
}
