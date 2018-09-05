package com.example.android.tabsetup;

public class StudentResult {

    private String first_name;
    private String last_name;
    private int studentID;
    private int score;

    public StudentResult(String first_name, String last_name, int studentID, int score) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.studentID = studentID;
        this.score = score;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}