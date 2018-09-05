package com.example.android.tabsetup;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ExamDao {
    @Insert
    long insertAll(Exam exam);

    @Query("SELECT * FROM Exam")
    List<Exam> getAllExams();

    @Query("DELETE FROM Exam WHERE exam_ID = :examID")
    abstract void deleteExam(int examID);

    @Query("SELECT * FROM Exam WHERE exam_ID = :examID")
    abstract Exam getExam(int examID);
}
