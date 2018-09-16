package com.example.android.tabsetup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

public class  StudentList extends Fragment implements StudentViewHolder.StudentListener, View.OnLongClickListener {

    boolean is_in_action_mode = false;  //Flag for mass delete mode.
    int mExpandedPosition = -1;
    int previousExpandPosition = -1;
    int deleteCount = 0;                //For use in displaying selected count.
    FloatingActionButton studentFab;
    TextView toolbarText;
    RecyclerView recyclerView;
    StudentAdapter adapter;
    AppDatabase db;
    List<Student> students;
    List<Student> selectedStudents = new ArrayList<>();
    androidx.appcompat.widget.Toolbar toolbar;
    AppCompatActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_student_tab, container, false);
        recyclerView = rootView.findViewById(R.id.studentRecycler);
        studentFab = rootView.findViewById(R.id.studentFab);

        // Setting up Fragment access to the toolbar and menu.
        toolbar = getActivity().findViewById(R.id.mainToolbar);
        activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbarText = toolbar.findViewById(R.id.toolbar_text);
        toolbarText.setText("0 SELECTED");
        toolbarText.setVisibility(View.GONE);


        db = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class,
                "production").allowMainThreadQueries().build();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new StudentAdapter(getLayoutInflater(), this, this);
        recyclerView.setAdapter(adapter);
        students = db.StudentDao().getAllStudents();
        adapter.updateItems(students);

        studentFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), StudentCreator.class);
                startActivity(intent);

            }
        });

        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_student, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.massDelete:                  // Deletes all chosen students.
                deleteAllSelected(selectedStudents);
                students = db.StudentDao().getAllStudents();
                finishActionMode();
                break;
            case R.id.action_delete_student:     // Starts mode for deleting multiple students.
                startActionMode();
                break;
            case android.R.id.home:              // Closes mode for deleting multiple students.
                students = db.StudentDao().getAllStudents();
                finishActionMode();
                break;
            case R.id.action_order_name:        // Reorders the students by name.
                students = db.StudentDao().getByName();
                adapter.updateItems(students);
                break;
            case R.id.action_order_id:          // Orders the students by ID.
                students = db.StudentDao().getAllStudents();
                adapter.updateItems(students);
                break;
            default:
                break;
        }
        return true;
    }

    //Implemented Methods to be passed to the ViewHolder Interface.
    @Override
    public void deleteStudent(final Student item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        String message = "Delete " + item.getFirstName() + " " + item.getLastName() + "?";
        builder.setMessage(message)
                .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.StudentExamDao().deleteStudent(item.getStudent_ID());
                        db.StudentDao().deleteStudent(item.getStudent_ID());
                        students = db.StudentDao().getAllStudents();
                        adapter.updateItems(students);
                    }
                }).setNegativeButton("CANCEL", null);

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void updateStudent(Student item) {
        Context context = getContext();
        Intent intent = new Intent(context, StudentUpdater.class);
        intent.putExtra("STUDENT_ID", Integer.toString(item.getStudent_ID()));
        context.startActivity(intent);
//        ((Activity)context).finish();
    }

    @Override
    public void openMaps(Student item) {
        Uri gmmIntentUri = Uri.parse("geo:0,0,?q=" + item.getAddress());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    @Override
    public void prepareSelection(View view, int position) {
        if(((CheckBox)view).isChecked()) {
            selectedStudents.add(students.get(position));
            deleteCount +=1;
        } else {
            selectedStudents.remove(students.get(position));
            deleteCount -= 1;
        }
        updateCounter(deleteCount);
    }

    @Override
    public void expandView(boolean isExpanded, int position) {
        mExpandedPosition = isExpanded ? -1:position;
        adapter.notifyItemChanged(previousExpandPosition);
        adapter.notifyItemChanged(position);
        adapter.updateItems(students);
    }
    // End of Interface methods.

    // Longclick to be used by the ViewHolder.
    @Override
    public boolean onLongClick(View view) {
        startActionMode();
        return true;
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
        adapter.updateItems(students);
    }

    // Ends the multi delete students mode.
    public void finishActionMode() {
        is_in_action_mode = false;
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.menu_student);
        toolbarText.setVisibility(View.GONE);
        deleteCount = 0;
        toolbarText.setText("0 SELECTED");
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(true);
        selectedStudents = new ArrayList<>();
        adapter.updateItems(students);

    }

    // Deletes all selected students from the database.
    public void deleteAllSelected(List<Student> selectedStudents){
        for (int i = 0; i < selectedStudents.size();i++) {
            db.StudentExamDao().deleteStudent(selectedStudents.get(i).getStudent_ID());
            db.StudentDao().deleteStudent(selectedStudents.get(i).getStudent_ID());
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
