package com.example.android.tabsetup;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Student {
    public Student(int student_ID, String firstName, String lastName, String address,
                   String dob, String gender, String course) {
        this.student_ID = student_ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.dob = dob;
        this.gender = gender;
        this.course = course;
    }

    @PrimaryKey
    private int student_ID;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "dob")
    private String dob;

    @ColumnInfo(name = "gender")
    private String gender;

    @ColumnInfo(name = "course")
    private String course;

    public int getStudent_ID() {
        return student_ID;
    }

    public String getStudent_IDString() {
        return String.valueOf(student_ID);      //RETURNING AS STRING FOR USE WITH STUDENT ADAPTER,
    }

    public void setStudent_ID(int student_ID) {
        this.student_ID = student_ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
