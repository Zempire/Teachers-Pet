package com.example.android.tabsetup;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class StudentViewHolder extends RecyclerView.ViewHolder {

    boolean isExpanded = false;
    TextView studentName;
    TextView student_ID;
    TextView student_address;
    TextView gender;
    TextView dateOfBirth;
    ConstraintLayout studentContainer, optionsContainer;
    Button deleteStudentBtn, viewStudentBtn;
    ImageView toggleStudentInfo;
    ImageView profilePicStudent;
    CheckBox multiSelectBox;
    File image = null;
    StudentList studentList; //Allows use of StudentList's onLongClickListener
    Student item;
    StudentListener listener;

    // Allows the passing of methods from the base activity for better communication between
    // the classes and also lightens the load on the ViewHolder too.
    public interface StudentListener{
        void deleteStudent(Student item);
        void updateStudent(Student item);
        void openMaps(Student item);
        void prepareSelection(View view, int position);
        void expandView(boolean isExpanded, int position);
    }

    public StudentViewHolder(View itemView, final StudentListener listener, final StudentList studentList) {
        super(itemView);
        this.listener = listener;
        this.studentList = studentList;
        studentName = itemView.findViewById(R.id.studentName);
        student_ID = itemView.findViewById(R.id.student_id);
        student_address = itemView.findViewById(R.id.student_address);
        gender = itemView.findViewById(R.id.student_gender);
        dateOfBirth = itemView.findViewById(R.id.student_dob);
        studentContainer = itemView.findViewById(R.id.studentContainer);
        optionsContainer = itemView.findViewById(R.id.optionsContainer);
        deleteStudentBtn = itemView.findViewById(R.id.deleteImgBtn);
        viewStudentBtn = itemView.findViewById(R.id.updateImgBtn);
        profilePicStudent = itemView.findViewById(R.id.profilePicList);
        toggleStudentInfo = itemView.findViewById(R.id.toggleStudentInfo);
        multiSelectBox = itemView.findViewById(R.id.multiSelectBox);
        toggleStudentInfo.setEnabled(false);
        studentContainer.setOnLongClickListener(studentList);
        studentContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (studentList.is_in_action_mode) {
                    if (multiSelectBox.isChecked()) {
                        multiSelectBox.setChecked(false);
                    } else {
                        multiSelectBox.setChecked(true);
                    }
                    studentContainer.setBackgroundResource(multiSelectBox.isChecked() ?
                            R.color.deleteObject : R.color.cardBackground);
                    listener.prepareSelection(multiSelectBox, getAdapterPosition());
                } else {
                    System.out.println("MY ID IS: " + item.getStudent_ID());
                    listener.expandView(isExpanded, getAdapterPosition());

                }
            }
        });

        deleteStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { listener.deleteStudent(item);
            }
        });
        viewStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { listener.updateStudent(item);
            }
        });
        student_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.openMaps(item);
            }
        });

    }

    public void setItem(Student item) {
        this.item = item;
        String name = item.getFirstName() + " " + item.getLastName();
        studentName.setText(name);
        student_ID.setText(Integer.toString(item.getStudent_ID()));
        student_address.setText(item.getAddress());
        gender.setText(item.getGender());
        dateOfBirth.setText(item.getDob());

        // Add a profile image to the student's view.
        File image = new File(item.getProfilePicture());
        if (image.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(image.getAbsolutePath());
            Bitmap resized = Bitmap.createScaledBitmap(myBitmap, 75, 75, true);
            profilePicStudent.setImageBitmap(resized);
        } else {
            profilePicStudent.setImageResource(R.drawable.newimage);
        }
    }
}