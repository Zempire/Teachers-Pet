package com.example.android.tabsetup;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Task")
public class Task {

    public Task(String taskName, String taskDesc, String taskLocation, String taskDate) {
        this.taskName = taskName;
        this.taskDesc = taskDesc;
        this.taskLocation = taskLocation;
        this.taskDate = taskDate;

    }

    @PrimaryKey(autoGenerate = true)
    private int task_ID;

    @ColumnInfo(name = "task_name")
    private String taskName;

    @ColumnInfo(name = "task_desc")
    private String taskDesc;

    @ColumnInfo(name = "task_location")
    private String taskLocation;

    @ColumnInfo(name = "task_status")
    private int taskStatus = 0;

    @ColumnInfo(name = "task_date")
    private String taskDate;

    public int getTask_ID() {
        return task_ID;
    }

    public String getTask_IDString() {
        return String.valueOf(task_ID);
    }

    public void setTask_ID(int task_ID) {
        this.task_ID = task_ID;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public String getTaskLocation() {
        return taskLocation;
    }

    public void setTaskLocation(String taskLocation) {
        this.taskLocation = taskLocation;
    }

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

}
