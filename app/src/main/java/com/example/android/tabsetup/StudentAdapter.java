package com.example.android.tabsetup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder>{
    List<Student> students;

    //For controlling expansion of just 1 ViewHolder.
    private int mExpandedPosition = -1;
    private int previousExpandPosition = -1;

    //Constructor for students array.
    public StudentAdapter(List<Student> students) {
        this.students = students;
    }

    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final StudentAdapter.ViewHolder holder, final int position) {
        holder.firstName.setText(students.get(position).getFirstName());
        holder.lastName.setText(students.get(position).getLastName());
        holder.student_ID.setText(students.get(position).getStudent_IDString()); //How to convert to string?
        holder.student_Address.setText(students.get(position).getAddress());

        String imageFileName = "/storage/emulated/0/Android/data/com.example.android.tabsetup/files/Pictures/" + "PROFILE_" + students.get(position).getStudent_IDString() +".jpg";
        File image = new File(imageFileName);
        if (image.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(image.getAbsolutePath());
            holder.profilePic.setImageBitmap(myBitmap);
        }


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
        return students.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView profilePic;
        public TextView firstName;
        public TextView lastName;
        public TextView student_ID;
        public TextView student_Address;
        public ConstraintLayout studentContainer, optionsContainer;
        public Button deleteStudentBtn, viewStudentBtn;

        AppDatabase db = Room.databaseBuilder(itemView.getContext(), AppDatabase.class,
                "production").allowMainThreadQueries().build();

        public ViewHolder(View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.first_name);
            lastName = itemView.findViewById(R.id.last_name);
            student_ID = itemView.findViewById(R.id.student_id);
            student_Address = itemView.findViewById(R.id.student_address);
            studentContainer = itemView.findViewById(R.id.studentContainer);
            optionsContainer = itemView.findViewById(R.id.optionsContainer);
            deleteStudentBtn = itemView.findViewById(R.id.deleteStudentBtn);
            viewStudentBtn = itemView.findViewById(R.id.viewStudentBtn);
            profilePic = itemView.findViewById(R.id.profilePic);

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
        }



        public void delete(final int position) {
            final int ID = Integer.parseInt(student_ID.getText().toString());
            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
            builder.setMessage("Delete Student?")
                    .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            students.remove(position);
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

    }
}
