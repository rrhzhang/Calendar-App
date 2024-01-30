package com.example.calendarapp.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.calendarapp.ui.gallery.Assignments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AssignmentsViewModel extends ViewModel {
    private final MutableLiveData<List<Assignments>> assignmentsListLiveData;

    public AssignmentsViewModel() {
        assignmentsListLiveData = new MutableLiveData<>(new ArrayList<>());
    }

    LiveData<List<Assignments>> getAssignmentsList() {
        return assignmentsListLiveData;
    }

    void addAssignment(Assignments assignment) {
        List<Assignments> currentList = assignmentsListLiveData.getValue();
        if (currentList != null) {
            currentList.add(assignment);
            assignmentsListLiveData.setValue(new ArrayList<>(currentList));
        }
    }

    void deleteAssignment(int position) {
        List<Assignments> currentList = assignmentsListLiveData.getValue();
        if (currentList != null) {
            currentList.remove(position);
            assignmentsListLiveData.setValue(new ArrayList<>(currentList));
        }
    }

    void sortAssignmentsByClass() {
        List<Assignments> currentList = assignmentsListLiveData.getValue();
        if (currentList != null) {
            Collections.sort(currentList, new Comparator<Assignments>() {
                @Override
                public int compare(Assignments assignment1, Assignments assignment2) {
                    return assignment1.getCourse().compareTo(assignment2.getCourse());
                }
            });
            assignmentsListLiveData.setValue(new ArrayList<>(currentList));
        }
    }

    void sortAssignmentsByDueDate() {
        List<Assignments> currentList = assignmentsListLiveData.getValue();
        if (currentList != null) {
            Collections.sort(currentList, new Comparator<Assignments>() {
                @Override
                public int compare(Assignments assignment1, Assignments assignment2) {
                    // Add your date parsing and comparison logic here
                    return assignment1.getDue().compareTo(assignment2.getDue());
                }
            });
            assignmentsListLiveData.setValue(new ArrayList<>(currentList));
        }
    }

    public void updateAssignment(int position, Assignments updatedAssignment) {
        List<Assignments> currentList = assignmentsListLiveData.getValue();
        if (currentList != null && position >= 0 && position < currentList.size()) {
            currentList.set(position, updatedAssignment);
            assignmentsListLiveData.setValue(new ArrayList<>(currentList));
        }
    }


}
