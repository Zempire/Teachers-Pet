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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    int deleteCount = 0;
    FloatingActionButton examFab;
    TextView toolbarText;
    RecyclerView completedRecycler, upcomingRecycler;
    ExamAdapter completedAdapter, upcomingAdapter;
    AppDatabase db;
    List<Exam> exams, upcomingExams, completedExams;
    List<Exam> selectedExams = new ArrayList<>();
    androidx.appcompat.widget.Toolbar toolbar;
    AppCompatActivity activity;

    Date currentTime = Calendar.getInstance().getTime();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_exam_tab, container, false);
        upcomingRecycler = rootView.findViewById(R.id.upcomingRecycler);
        completedRecycler = rootView.findViewById(R.id.completedRecylcer);
        examFab = rootView.findViewById(R.id.examFab);

        //Setting up Fragment access to the toolbar and menu.
        toolbar = getActivity().findViewById(R.id.mainToolbar);
        activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbarText = toolbar.findViewById(R.id.toolbar_text);
        toolbarText.setText("0 SELECTED");
        toolbarText.setVisibility(View.GONE);

        db = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class,
                "production").allowMainThreadQueries().build();

        updateAdapters();

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
        switch(item.getItemId()) {
            case R.id.massDelete:                   // Deletes all chosen tasks.
                deleteAllSelected(selectedExams);
                exams = db.ExamDao().getAllExams();
                finishActionMode();
                break;
            case R.id.action_delete_exam:           // Starts mode for deleting multiple tasks.
                startActionMode();
                break;
            case android.R.id.home:                // Closes mode for deleting multiple tasks.
                exams = db.ExamDao().getAllExams();
                finishActionMode();
                break;
        }
        return true;
    }


    @Override
    public boolean onLongClick(View view) {
        startActionMode();
        return true;
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
                        updateAdapters();
                    }
                }).setNegativeButton("CANCEL", null);
        AlertDialog alert = builder.create();
        alert.show();
    }
    @Override
    public void prepareSelection(View view, int position) {
        if(((CheckBox)view).isChecked()) {
            selectedExams.add(exams.get(position));
            deleteCount +=1;
        } else {
            selectedExams.remove(exams.get(position));
            deleteCount -= 1;
        }
        updateCounter(deleteCount);
    }


    //Sorry this is messy but only way I could think of targeting the recyclerViews separately.
    @Override
    public void expandView(boolean isExpanded, int position, Exam item) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Date date = new Date();
        try {
            String dateTime = item.getDateTime();
            date = format.parse(dateTime);
            if (currentTime.after(date)) {
                completedAdapter.mExpandedPosition = isExpanded ? -1:position;
                completedAdapter.notifyItemChanged(completedAdapter.previousExpandPosition);
                completedAdapter.notifyItemChanged(position);
                completedAdapter.updateItems(completedExams);
            } else {
                upcomingAdapter.mExpandedPosition = isExpanded ? -1:position;
                upcomingAdapter.notifyItemChanged(upcomingAdapter.previousExpandPosition);
                upcomingAdapter.notifyItemChanged(position);
                upcomingAdapter.updateItems(upcomingExams);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // Changes menu and starts multi delete exam mode.
    public void startActionMode() {
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.menu_action_mode);
        is_in_action_mode = true;
        toolbarText.setVisibility(View.VISIBLE);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        updateAdapters();
//        adapter.notifyDataSetChanged();
    }

    // Ends the multi delete exam mode.
    public void finishActionMode() {
        is_in_action_mode = false;
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.menu_exam);
        toolbarText.setVisibility(View.GONE);
        deleteCount = 0;
        toolbarText.setText("0 SELECTED");
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(true);
        selectedExams = new ArrayList<>();
        updateAdapters();

    }

    // Deletes all selected students from the database.
    public void deleteAllSelected(List<Exam> selectedExams){
        for (int i = 0; i < selectedExams.size();i++) {
            db.StudentExamDao().deleteExam(selectedExams.get(i).getExam_ID());
            db.ExamDao().deleteExam(selectedExams.get(i).getExam_ID());
        }
    }

    public void updateCounter(int counter) {
        if (counter == 0) {
            toolbarText.setText("0 SELECTED");
        } else {
            toolbarText.setText(counter + " SELECTED");
        }
    }

    public void updateAdapters() {
        upcomingExams = new ArrayList<>();
        completedExams = new ArrayList<>();
        exams = db.ExamDao().getAllExams();
        sortExams(exams);
        upcomingRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        upcomingAdapter = new ExamAdapter(getLayoutInflater(), this, this);
        upcomingRecycler.setAdapter(upcomingAdapter);
        upcomingAdapter.updateItems(upcomingExams);

        completedRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        completedAdapter = new ExamAdapter(getLayoutInflater(), this, this);
        completedRecycler.setAdapter(completedAdapter);
        exams = db.ExamDao().getAllExams();
        completedAdapter.updateItems(completedExams);
    }

    public void sortExams(List<Exam> exams) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");

        for (Exam exam : exams) {
            Date date = new Date();
            try {
                String dateTime = exam.getDateTime();
                date = format.parse(dateTime);
                if (currentTime.before(date)) {
                    upcomingExams.add(exam);
                } else {
                    completedExams.add(exam);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }




}
