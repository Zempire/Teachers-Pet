package com.example.android.tabsetup;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TaskDao {
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
