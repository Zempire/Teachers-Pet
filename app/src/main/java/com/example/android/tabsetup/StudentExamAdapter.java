package com.example.android.tabsetup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

public class StudentExamAdapter extends RecyclerView.Adapter {

    private List<Exam> items;
    private LayoutInflater inflater;
    private StudentExamViewHolder.StudentExamListener studentExamListener;
    StudentExamViewHolder seh;


    public StudentExamAdapter(LayoutInflater inflater, StudentExamViewHolder.StudentExamListener studentExamListener) {
        this.inflater = inflater;
        this.studentExamListener = studentExamListener;
        items = new ArrayList<>();
    }

    public void updateItems(final List<Exam> newItems) {
        final List<Exam> oldItems = new ArrayList<>(this.items);
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
        View v = inflater.inflate(R.layout.exam_chooser_row, parent, false);
        return new StudentExamViewHolder(v, studentExamListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        seh = (StudentExamViewHolder) holder;
        seh.setItem(items.get(position));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}