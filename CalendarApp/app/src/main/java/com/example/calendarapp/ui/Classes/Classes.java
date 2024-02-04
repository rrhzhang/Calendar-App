package com.example.calendarapp.ui.Classes;

public class Classes {
    private String course;
    private String time;
    private String instructor;

    public Classes() {
    }
    public Classes(String course, String time, String instructor) {
        this.course = course.trim();
        this.time = time.trim();
        this.instructor = instructor.trim();
    }

    public String getCourse() {
        return course;
    }
    public String getTime() {
        return time;
    }
    public String getInstructor() {
        return instructor;
    }

    public void setCourse(String course) {
        this.course = course.trim();
    }
    public void setTime(String time) {
        this.time = time.trim();
    }
    public void setInstructor(String instructor) {
        this.instructor = instructor.trim();
    }
}
