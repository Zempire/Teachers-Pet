package com.example.android.tabsetup;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {
    @Query("SELECT * FROM Student")
    List<Student> getAllStudents();

    @Insert
    void insertAll(Student... students);

    @Query("DELETE FROM Student WHERE student_ID = :studentID")
    abstract void deleteStudent(int studentID);

    @Query("SELECT * FROM Student WHERE student_ID = :studentID")
    abstract Student getStudent(int studentID);

    @Update
    public void updateStudent(Student student);
}
