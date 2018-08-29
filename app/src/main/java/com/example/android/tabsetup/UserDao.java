package com.example.android.tabsetup;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {

    //STUDENT TABLE FUNCTIONALITY

    @Query("SELECT * FROM Student")
    List<Student> getAllStudents();

    @Insert
    void insertAllStudent(Student... students);

    @Query("DELETE FROM Student WHERE student_ID = :studentID")
    abstract void deleteStudent(int studentID);

    @Query("SELECT * FROM Student WHERE student_ID = :studentID")
    abstract Student getStudent(int studentID);

    @Update
    public void updateStudent(Student student);

    //TASK TABLE FUNCTIONALITY

    @Query("SELECT * FROM Task")
    List<Task> getAllTasks();

    @Insert
    void insertAllTask(Task... tasks);

    @Query("DELETE FROM Task WHERE task_ID = :taskID")
    abstract void deleteTask(int taskID);

    @Query("SELECT * FROM Task WHERE task_ID = :taskID")
    abstract Task getTask(int taskID);

    @Update
    public void updateTask(Task task);
}
