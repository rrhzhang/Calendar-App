package com.example.calendarapp.ui.gallery;

public class Tasks {
    private String title;
    private String due;
    public Tasks () {
    }
    public Tasks (String title, String due) {
        this.title = title;
        this.due = due;
    }
    public String getDue() {
        return this.due;
    }
    public String getTitle() {
        return this.title;
    }
    @Override
    public String toString() {
        return new String(this.getTitle() + ", "+this.getDue());
    }
    public void setDue(String due) {
        this.due = due;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
