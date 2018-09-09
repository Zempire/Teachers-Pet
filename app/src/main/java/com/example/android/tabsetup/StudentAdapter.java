package com.example.android.tabsetup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder>{
    private List<Student> students;
    private List<Student> selectedStudents = new ArrayList<>();

    //For controlling expansion of just 1 ViewHolder.

    //Constructor for students array.
    public StudentAdapter(List<Student> students) {
        this.students = students;
    }

    // Code from http://blog.teamtreehouse.com/contextual-action-bars-removing-items-recyclerview
    public boolean multiSelect = false;
    private ActionMode.Callback actionModeCallbacks = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            multiSelect = true;
            mode.getMenuInflater().inflate(R.menu.menu_student, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            for (Student intItems : selectedStudents) {
                students.remove(intItems);
            }
            mode.finish();
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            multiSelect = false;
            selectedStudents.clear();
            notifyDataSetChanged();
        }
    };


    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final StudentAdapter.ViewHolder holder, final int position) {
        holder.studentName.setText(students.get(position).getFirstName() + " " + students.get(position).getLastName());
        holder.student_ID.setText(Integer.toString(students.get(position).getStudent_ID()));
        holder.student_Address.setText(students.get(position).getAddress());

        // Add a profile image to the student's view.
        String imageFileName = "/storage/emulated/0/Android/data/com.example.android.tabsetup/files/"
                + "Pictures/" + "PROFILE_" + students.get(position).getStudent_IDString() +".jpg";
        File image = new File(imageFileName);
        if (image.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(image.getAbsolutePath());
            holder.profilePic.setImageBitmap(myBitmap);
        }

        holder.multiDelete(students.get(position));

    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView profilePic;
        public TextView studentName;
        public TextView student_ID;
        public TextView student_Address;
        public ConstraintLayout studentContainer, optionsContainer;
        public Button deleteStudentBtn, viewStudentBtn;
        public ToggleButton toggleStudentInfo;

        AppDatabase db = Room.databaseBuilder(itemView.getContext(), AppDatabase.class,
                "production").allowMainThreadQueries().build();

        public ViewHolder(final View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.studentName);
            student_ID = itemView.findViewById(R.id.student_id);
            student_Address = itemView.findViewById(R.id.student_address);
            studentContainer = itemView.findViewById(R.id.studentContainer);
            optionsContainer = itemView.findViewById(R.id.optionsContainer);
            deleteStudentBtn = itemView.findViewById(R.id.deleteStudentBtn);
            viewStudentBtn = itemView.findViewById(R.id.viewStudentBtn);
            profilePic = itemView.findViewById(R.id.profilePic);
            toggleStudentInfo = itemView.findViewById(R.id.toggleStudentInfo);

            deleteStudentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delete(getLayoutPosition());
                }
            });

            viewStudentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    update(getAdapterPosition(), view);
                }
            });

            toggleStudentInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (optionsContainer.getVisibility() == View.GONE) {
                        optionsContainer.setVisibility(View.VISIBLE);
                    } else {
                        optionsContainer.setVisibility(View.GONE);
                    }
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
                            db.StudentExamDao().deleteStudent(ID);
                            db.StudentDao().deleteStudent(ID);
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

        void selectItem(Student item) {
            if (multiSelect) {
                if (selectedStudents.contains(item)) {
                    selectedStudents.remove(item);
                    studentContainer.setBackgroundColor(Color.WHITE);
                } else {
                    selectedStudents.add(item);
                    studentContainer.setBackgroundColor(Color.DKGRAY);
                }
            }
        }

        void multiDelete(final Student value) {
            if (selectedStudents.contains(value)) {
                studentContainer.setBackgroundColor(Color.DKGRAY);
            } else {
                studentContainer.setBackgroundColor(Color.WHITE);
            }
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    ((AppCompatActivity)view.getContext()).startActionMode(actionModeCallbacks);
                    selectItem(value);
                    return true;
                }
            });
                studentContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectItem(value);
                    }
                });
        }
    }
}
