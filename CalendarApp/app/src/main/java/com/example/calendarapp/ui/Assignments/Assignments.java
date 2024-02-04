package com.example.calendarapp.ui.Assignments;

public class Assignments extends Tasks {
    private String title;
    private String due;
    private String course;

    public Assignments() {
        super();
    }
    public Assignments(String title, String due, String instructor) {
        this.title = title;
        this.due = due;
        this.course = instructor;
    }

    public String getCourse() {
        return course;
    }
    public String getTitle() {
        return title;
    }
    public String getDue() {
        return due;
    }
    public void setCourse(String course) {
        this.course = course;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDue(String due) {
        this.due = due;
    }
}
