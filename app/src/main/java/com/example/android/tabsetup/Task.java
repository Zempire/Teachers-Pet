package com.example.android.tabsetup;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task {

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

    public Task(String task_name, String task_desc, String task_location, String task_date) {
        this.taskName = task_name;
        this.taskDesc = task_desc;
        this.taskLocation = task_location;
        this.taskDate = task_date;

    }

    public int getTaskID() {
        return task_ID;
    }

    public void setTaskID(int task_ID) {
        this.task_ID = task_ID;
    }

    public String getTaskname() {
        return taskName;
    }

    public void setTaskname(String task_name) {
        this.taskName = task_name;
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
