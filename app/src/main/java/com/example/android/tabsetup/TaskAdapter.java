package com.example.android.tabsetup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{
    List<Task> tasks;

    //For controlling expansion of just 1 ViewHolder.
    private int mExpandedPosition = -1;
    private int previousExpandPosition = -1;

    //Constructor for students array.
    public TaskAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TaskAdapter.ViewHolder holder, final int position) {
        holder.taskID.setText(tasks.get(position).getTask_IDString());
        holder.taskDate.setText(tasks.get(position).getTaskDate());
        holder.taskName.setText(tasks.get(position).getTaskName());
        holder.taskDesc.setText(tasks.get(position).getTaskDesc());
        holder.taskLocation.setText(tasks.get(position).getTaskLocation());
        holder.taskTime.setText(tasks.get(position).getTaskTime());

        final boolean isExpanded = position==mExpandedPosition;
        holder.optionsContainer.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.taskContainer.setBackgroundResource(isExpanded?R.color.taskExpanded:R.color.taskSmall);
        holder.itemView.setActivated(isExpanded);

        if (isExpanded)
            previousExpandPosition = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
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
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView taskID;
        public TextView taskName;
        public TextView taskDesc;
        public TextView taskDate;
        public TextView taskLocation;
        public TextView taskTime;
        public ConstraintLayout taskContainer, optionsContainer;
        public ImageView deleteTaskBtn, completeTaskBtn;

        AppDatabase db = Room.databaseBuilder(itemView.getContext(), AppDatabase.class,
                "production").allowMainThreadQueries().build();

        public ViewHolder(View itemView) {
            super(itemView);
            taskID = itemView.findViewById(R.id.task_ID);
            taskName = itemView.findViewById(R.id.task_name);
            taskDesc = itemView.findViewById(R.id.task_desc);
            taskDate = itemView.findViewById(R.id.task_date);
            taskTime = itemView.findViewById(R.id.task_time);
            taskLocation = itemView.findViewById(R.id.task_location);
            taskContainer = itemView.findViewById(R.id.taskContainer);
            optionsContainer = itemView.findViewById(R.id.optionsContainer);
            deleteTaskBtn = itemView.findViewById(R.id.deleteTaskBtn);
            completeTaskBtn = itemView.findViewById(R.id.completeTaskBtn);

            deleteTaskBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delete(getLayoutPosition());
                }
            });

            completeTaskBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    update(getAdapterPosition(), view);
                }
            });
        }



        public void delete(final int position) {
            final int ID = Integer.parseInt(taskID.getText().toString());
            Toast.makeText(itemView.getContext(), "This tasks ID is " + ID, Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
            builder.setMessage("Delete Task?")
                    .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            tasks.remove(position);
                            db.UserDao().deleteTask(ID);
                            notifyItemRemoved(position);
                        }
                    }).setNegativeButton("CANCEL", null);

            AlertDialog alert = builder.create();
            alert.show();

        }

//        public void update(int position, View view) {
//            Context context = view.getContext();
//            Intent intent = new Intent(context, StudentUpdater.class);
//            intent.putExtra("STUDENT_ID", student_ID.getText().toString());
//            context.startActivity(intent);
//            ((Activity)context).finish();
//        }

    }
}
