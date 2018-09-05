package com.example.android.tabsetup;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "StudentExam",
        primaryKeys = {"examID", "studentID"},
        foreignKeys = {
            @ForeignKey(entity = Student.class,
                        parentColumns = "student_ID",
                        childColumns = "studentID"),
            @ForeignKey(entity = Exam.class,
                        parentColumns = "exam_ID",
                        childColumns = "examID")
        })
public class StudentExam {

    private int examID;
    private int studentID;

    @ColumnInfo(name = "score")
    private int score;

    public StudentExam(int examID, int studentID, int score) {

        this.examID = examID;
        this.studentID = studentID;
        this.score = score;
    }

    public int getExamID() {
        return examID;
    }

    public void setExamID(int examID) {
        this.examID = examID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
