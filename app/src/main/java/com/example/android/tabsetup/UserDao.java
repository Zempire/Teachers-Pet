package com.example.android.tabsetup;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Query("SELECT * FROM Student")
    List<Student> getAllStudents();

    @Insert
    void insertAll(Student... students);

    @Query("DELETE FROM Student WHERE student_ID = :studentID")
    abstract void deleteStudent(int studentID);
}
