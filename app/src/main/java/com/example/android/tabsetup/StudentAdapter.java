package com.example.android.tabsetup;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

public class StudentAdapter extends RecyclerView.Adapter {
    private List<Student> items;
    private LayoutInflater inflater;
    private StudentViewHolder.StudentListener studentListener;
    StudentList studentList;
    StudentViewHolder vh;

    AppDatabase db;
    List<StudentExamResult> results;

    public StudentAdapter(LayoutInflater inflater, StudentViewHolder.StudentListener studentListener, StudentList studentList) {
        this.inflater = inflater;
        this.studentListener = studentListener;
        this.studentList = studentList;
        items = new ArrayList<>();
    }

    public void updateItems(final List<Student> newItems) {
        final List<Student> oldItems = new ArrayList<>(this.items);
        this.items.clear();
        if (newItems != null) {
            this.items.addAll(newItems);
        }
        DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return oldItems.size();
            }

            @Override
            public int getNewListSize() {
                return items.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return oldItems.get(oldItemPosition).equals(newItems.get(newItemPosition));
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return oldItems.get(oldItemPosition).equals(newItems.get(newItemPosition));
            }
        }).dispatchUpdatesTo(this);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.student_row, parent, false);
        return new StudentViewHolder(v, studentListener, studentList);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        vh = (StudentViewHolder) holder;
        vh.setItem(items.get(position));
        vh.isExpanded = position==studentList.mExpandedPosition;
        vh.optionsContainer.setVisibility(vh.isExpanded?View.VISIBLE:View.GONE);
        vh.toggleStudentInfo.setRotation(vh.isExpanded?180:0);

        db = Room.databaseBuilder(vh.optionsContainer.getContext(), AppDatabase.class,
                "production").allowMainThreadQueries().build();
        results = db.StudentExamDao().getResults(vh.item.getStudent_ID());
        StudentResultAdapter adapter = new StudentResultAdapter(results);
        vh.recyclerView.setLayoutManager(new LinearLayoutManager(vh.optionsContainer.getContext()));
        vh.recyclerView.setAdapter(adapter);


        if (vh.isExpanded)
            studentList.previousExpandPosition = position;

        vh.multiSelectBox.setChecked(false);
        vh.studentContainer.setBackgroundResource(R.color.cardBackground);
        if (!studentList.is_in_action_mode) {
           vh.toggleStudentInfo.setVisibility(View.VISIBLE);
        } else {
            vh.toggleStudentInfo.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}