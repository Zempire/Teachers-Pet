package com.example.android.tabsetup;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "Exam")
public class Exam {

    @PrimaryKey (autoGenerate = true)
    private int exam_ID;

    @ColumnInfo (name = "exam_name")
    private String examName;

    @ColumnInfo (name = "exam_location")
    private String examLocation;

    @ColumnInfo (name = "exam_datetime")
    private String dateTime;

    public int getExam_ID() { return exam_ID; }

    public void setExam_ID(int examID) {
        this.exam_ID = examID;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getExamLocation() {
        return examLocation;
    }

    public void setExamLocation(String examLocation) {
        this.examLocation = examLocation;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Exam(String examName, String examLocation, String dateTime) {
        this.examName = examName;
        this.examLocation = examLocation;
        this.dateTime = dateTime;
    }
}
