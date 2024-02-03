package com.example.calendarapp.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.calendarapp.ui.gallery.Assignments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ExamsViewModel extends ViewModel {
    private final MutableLiveData<List<Exams>> examsListLive;

    public ExamsViewModel() {
        examsListLive = new MutableLiveData<>(new ArrayList<>());
    }

    LiveData<List<Exams>> getExamsList() {
        return examsListLive;
    }

    void addExam(Exams exam) {
        List<Exams> currentList = examsListLive.getValue();
        if (currentList != null) {
            currentList.add(exam);
            examsListLive.setValue(new ArrayList<>(currentList));
        }
    }

    void deleteExam(int position) {
        List<Exams> currentList = examsListLive.getValue();
        if (currentList != null) {
            currentList.remove(position);
            examsListLive.setValue(new ArrayList<>(currentList));
        }
    }


    public void updateExam(int position, Exams updatedExam) {
        List<Exams> currentList = examsListLive.getValue();
        if (currentList != null && position >= 0 && position < currentList.size()) {
            currentList.set(position, updatedExam);
            examsListLive.setValue(new ArrayList<>(currentList));
        }
    }


}
