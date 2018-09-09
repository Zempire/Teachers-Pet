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
import androidx.recyclerview.widget.RecyclerView;

public class SuperChillAdapter extends RecyclerView.Adapter {
    private List<Student> items;
    private LayoutInflater inflater;
    private SmartViewHolder.StudentListener studentListener;

    //For controlling expansion of just 1 ViewHolder.
    private int mExpandedPosition = -1;
    private int previousExpandPosition = -1;


    public SuperChillAdapter(LayoutInflater inflater, SmartViewHolder.StudentListener studentListener) {
        this.inflater = inflater;
        this.studentListener = studentListener;
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
        return new SmartViewHolder(v, studentListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        SmartViewHolder vh = (SmartViewHolder) holder;
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
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}