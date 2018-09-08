package com.example.android.tabsetup;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.File;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class SmartViewHolder extends RecyclerView.ViewHolder {
    TextView studentName;
    TextView student_ID;
    TextView student_address;
    TextView gender;
    TextView dateOfBirth;
    ConstraintLayout studentContainer, optionsContainer;
    Button deleteStudentBtn, viewStudentBtn;
    Student item;
    StudentListener listener;
    ToggleButton toggleStudentInfo;
    ImageView profilePic;

    public interface StudentListener{
        void  deleteStudent(Student item);
        void updateStudent(Student item);
        void openMaps(Student item);
    }

    public SmartViewHolder(View itemView, final StudentListener listener) {
        super(itemView);
        this.listener = listener;
        studentName = itemView.findViewById(R.id.studentName);
        student_ID = itemView.findViewById(R.id.student_id);
        student_address = itemView.findViewById(R.id.student_address);
        gender = itemView.findViewById(R.id.student_gender);
        dateOfBirth = itemView.findViewById(R.id.student_dob);
        studentContainer = itemView.findViewById(R.id.studentContainer);
        optionsContainer = itemView.findViewById(R.id.optionsContainer);
        deleteStudentBtn = itemView.findViewById(R.id.deleteStudentBtn);
        viewStudentBtn = itemView.findViewById(R.id.viewStudentBtn);
        profilePic = itemView.findViewById(R.id.profilePic);
        toggleStudentInfo = itemView.findViewById(R.id.toggleStudentInfo);


        deleteStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.deleteStudent(item);
            }
        });
        viewStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.updateStudent(item);
            }
        });

        student_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.openMaps(item);
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

    public void setItem(Student item) {
        String name = item.getFirstName() + " " + item.getLastName();
        this.item = item;
        studentName.setText(name);
        student_ID.setText(Integer.toString(item.getStudent_ID()));
        student_address.setText(item.getAddress());
        gender.setText(item.getGender());
        dateOfBirth.setText(item.getDob());

        // Add a profile image to the student's view.
        String imageFileName = "/storage/emulated/0/Android/data/com.example.android.tabsetup" +
                "/files/" + "Pictures/" + "PROFILE_" + item.getStudent_ID() +".jpg";
        File image = new File(imageFileName);
        if (image.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(image.getAbsolutePath());
            profilePic.setImageBitmap(myBitmap);
        }
    }
//    private String getFormattedDate(Student item) {
//        String date = item.getYear() + " " + item.getEra();
//        return date;
//    }

}