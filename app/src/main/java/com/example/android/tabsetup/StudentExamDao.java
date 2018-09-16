package com.example.android.tabsetup;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface StudentExamDao {

    @Insert
    void insert(StudentExam StudentExam);

    @Query("SELECT first_name, last_name, studentID, score FROM StudentExam se " +
            "INNER JOIN Student s on se.studentID = s.student_ID " +
            "WHERE examID = :exam_ID ORDER by last_name")
    List<StudentResult> getResults(int exam_ID);

    @Query("DELETE FROM StudentExam WHERE studentID = :studentID")
    abstract void deleteStudent(int studentID);

    @Query("DELETE FROM StudentExam WHERE examID = :examID")
    abstract void deleteExam(int examID);

}

