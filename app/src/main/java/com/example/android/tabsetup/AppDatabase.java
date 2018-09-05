package com.example.android.tabsetup;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Student.class, Task.class, Exam.class, StudentExam.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract StudentDao StudentDao();
    public abstract ExamDao ExamDao();
    public abstract TaskDao TaskDao();
    public abstract StudentExamDao StudentExamDao();
}
