package com.example.android.tabsetup;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Query("SELECT * FROM student")
    List<Student> getAllStudents();

    @Insert
    void insertAll(Student... students);
}
