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
            "WHERE examID = :exam_ID")
    List<StudentResult> getResults(int exam_ID);

}

