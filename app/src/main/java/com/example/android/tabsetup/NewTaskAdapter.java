package com.example.android.tabsetup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class NewTaskAdapter extends RecyclerView.Adapter {
    private List<Task> items;
    private LayoutInflater inflater;
    private TaskViewHolder.TaskListener taskListener;
    TaskList taskList;
    TaskViewHolder th;

    //For controlling expansion of just 1 ViewHolder.
    private int mExpandedPosition = -1;
    private int previousExpandPosition = -1;


    public NewTaskAdapter(LayoutInflater inflater, TaskViewHolder.TaskListener taskListener, TaskList taskList) {
        this.inflater = inflater;
        this.taskListener = taskListener;
        this.taskList = taskList;
        items = new ArrayList<>();
    }

    public void updateItems(final List<Task> newItems) {
        final List<Task> oldItems = new ArrayList<>(this.items);
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
        View v = inflater.inflate(R.layout.task_row, parent, false);
        return new TaskViewHolder(v, taskListener, taskList);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        th = (TaskViewHolder) holder;
        th.setItem(items.get(position));
        final boolean isExpanded = position==mExpandedPosition;
        th.optionsContainer.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        th.toggleTaskInfo.setChecked(isExpanded?true:false);

        if (isExpanded)
            previousExpandPosition = position;
        th.toggleTaskInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1:position;
                notifyItemChanged(previousExpandPosition);
                notifyItemChanged(position);
            }
        });

        th.multiSelectBox.setChecked(false);
        th.taskContainer.setBackgroundResource(R.color.taskSmall);
        if (!taskList.is_in_action_mode) {
            th.toggleTaskInfo.setVisibility(View.VISIBLE);
        } else {
            th.toggleTaskInfo.setVisibility(View.GONE);
        }

        // Changes the look of Task depending on it's complete Status.
        th.taskContainer.setBackgroundResource(items.get(position).getTaskStatus() == 1?
                R.color.completedTask:R.color.taskSmall);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}