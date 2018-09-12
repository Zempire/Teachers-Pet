package com.example.android.tabsetup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class StudentAdapter extends RecyclerView.Adapter {
    private List<Student> items;
    private LayoutInflater inflater;
    private StudentViewHolder.StudentListener studentListener;
    StudentList studentList;
    StudentViewHolder vh;

    //For controlling expansion of just 1 ViewHolder.
    private int mExpandedPosition = -1;
    private int previousExpandPosition = -1;


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
        final boolean isExpanded = position==mExpandedPosition;
        vh.optionsContainer.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        vh.toggleStudentInfo.setChecked(isExpanded?true:false);

        if (isExpanded)
            previousExpandPosition = position;
        vh.toggleStudentInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1:position;
                notifyItemChanged(previousExpandPosition);
                notifyItemChanged(position);
            }
        });

        vh.multiSelectBox.setChecked(false);
        vh.studentContainer.setBackgroundResource(R.color.taskSmall);
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