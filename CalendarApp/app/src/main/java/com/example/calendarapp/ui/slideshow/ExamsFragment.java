package com.example.calendarapp.ui.slideshow;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.calendarapp.R;
import com.example.calendarapp.ui.gallery.AssignmentAdapter;
import com.example.calendarapp.ui.gallery.AssignmentItemListener;
import com.example.calendarapp.ui.gallery.Assignments;
import com.example.calendarapp.ui.gallery.AssignmentsViewModel;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExamsFragment extends Fragment implements ExamsItemListener {

    private ExamAdapter adapter;
    private ExamsViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ExamsViewModel.class);
        adapter = new ExamAdapter(requireContext(), new ArrayList<>(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exams, container, false);
        ListView examsView = view.findViewById(R.id.examsList);
        examsView.setAdapter(adapter);

        final EditText editExamCourse = view.findViewById(R.id.editExamCourse);
        final EditText editExamDate = view.findViewById(R.id.editExamDate);
        final EditText editExamTime = view.findViewById(R.id.editExamTime);
        final EditText editExamLocation = view.findViewById(R.id.editExamLocation);
        Button addExamsButton = view.findViewById(R.id.addExamsButton);

        addExamsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String examCourse = editExamCourse.getText().toString().trim();
                String examDate = editExamDate.getText().toString().trim();
                String examTime = editExamTime.getText().toString().trim();
                String examLocation = editExamLocation.getText().toString().trim();


                Exams exam = new Exams();
                exam.setCourse(examCourse);
                exam.setDate(examDate);
                exam.setTime(examTime);
                exam.setLocation(examLocation);

                viewModel.addExam(exam);

                editExamCourse.getText().clear();
                editExamDate.getText().clear();
                editExamTime.getText().clear();
                editExamLocation.getText().clear();
            }
        });

        viewModel.getExamsList().observe(getViewLifecycleOwner(), new Observer<List<Exams>>() {
            @Override
            public void onChanged(List<Exams> exams) {
                adapter.updateDataSet(exams);
            }
        });

        examsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Exams clickedExam = (Exams) parent.getItemAtPosition(position);

                String course = clickedExam.getCourse();
                String date = clickedExam.getDate();
                String time = clickedExam.getTime();
                String location = clickedExam.getLocation();

                showDetailsDialog(course, date, time, location);
            }
        });


        return view;
    }


    @Override
    public void onDeleteExam(final int position) {
        viewModel.deleteExam(position);
    }

    private void showDetailsDialog(String course, String date, String time, String location) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(course + " Exam Details");
        builder.setMessage("Date: " + date + "\nTime: " + time + "\nLocation: " + location);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
