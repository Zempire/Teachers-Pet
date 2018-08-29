package com.example.android.tabsetup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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
    public TaskAdapter(List<Task> students) {
        this.tasks = tasks;
    }

    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TaskAdapter.ViewHolder holder, final int position) {
        holder.taskDate.setText(tasks.get(position).getTaskDate());
        holder.taskName.setText(tasks.get(position).getTaskname());
        holder.taskDesc.setText(tasks.get(position).getTaskDesc());
        holder.taskLocation.setText(tasks.get(position).getTaskLocation());

        final boolean isExpanded = position==mExpandedPosition;
        holder.optionsContainer.setVisibility(isExpanded?View.VISIBLE:View.GONE);
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
        public ConstraintLayout taskContainer, optionsContainer;
        public Button deleteTaskBtn, completeTaskBtn;

        AppDatabase db = Room.databaseBuilder(itemView.getContext(), AppDatabase.class,
                "production").allowMainThreadQueries().build();

        public ViewHolder(View itemView) {
            super(itemView);
            taskID = itemView.findViewById(R.id.task_ID)
            taskName = itemView.findViewById(R.id.task_name);
            taskDesc = itemView.findViewById(R.id.task_desc);
            taskDate = itemView.findViewById(R.id.task_date);
            taskLocation = itemView.findViewById(R.id.student_address);
            taskContainer = itemView.findViewById(R.id.studentContainer);
            optionsContainer = itemView.findViewById(R.id.optionsContainer);
            deleteTaskBtn = itemView.findViewById(R.id.deleteStudentBtn);
            completeTaskBtn = itemView.findViewById(R.id.viewStudentBtn);

            deleteTaskBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delete(getLayoutPosition());
                }
            });

            completeTaskBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    update(getAdapterPosition(), view);
                }
            });
        }



        public void delete(final int position) {
            final int ID = Integer.parseInt(student_ID.getText().toString());
            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
            builder.setMessage("Delete Student?")
                    .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            students.remove(position);
                            db.UserDao().deleteStudent(ID);
                            notifyItemRemoved(position);
                        }
                    }).setNegativeButton("CANCEL", null);

            AlertDialog alert = builder.create();
            alert.show();

        }

        public void update(int position, View view) {
            Context context = view.getContext();
            Intent intent = new Intent(context, StudentUpdater.class);
            intent.putExtra("STUDENT_ID", student_ID.getText().toString());
            context.startActivity(intent);
            ((Activity)context).finish();
        }

    }
}
