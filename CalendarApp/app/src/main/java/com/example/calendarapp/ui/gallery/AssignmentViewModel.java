package com.example.calendarapp.ui.gallery;

import androidx.lifecycle.ViewModel;

import java.util.List;

public class AssignmentViewModel extends ViewModel {
    private List<Assignments> assignmentsList;

    public List<Assignments> getAssignmentsList() {
        return assignmentsList;
    }

    public void setAssignmentsList(List<Assignments> list) {
        assignmentsList = list;
    }
}

