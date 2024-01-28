package com.example.calendarapp.ui.home;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
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

import com.example.calendarapp.R;

import java.util.ArrayList;
import java.util.List;

public class ClassesFragment extends Fragment implements CourseItemListener {

    private List<Classes> courseList = new ArrayList<>();
    private ClassAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classes, container, false);

        adapter = new ClassAdapter(requireContext(), courseList, this);
        ListView listView = view.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        final EditText editCourseName = view.findViewById(R.id.editCourseName);
        final EditText editTime = view.findViewById(R.id.editTime);
        final EditText editInstructor = view.findViewById(R.id.editInstructor);

        Button addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseName = editCourseName.getText().toString().trim();
                String time = editTime.getText().toString().trim();
                String instructor = editInstructor.getText().toString().trim();

                Classes newCourse = new Classes();
                newCourse.setCourse(courseName);
                newCourse.setTime(time);
                newCourse.setInstructor(instructor);

                courseList.add(newCourse);

                adapter.notifyDataSetChanged();

                editCourseName.getText().clear();
                editTime.getText().clear();
                editInstructor.getText().clear();
            }
        });

        return view;
    }

    public void onEditCourse(final int position) {
        final Classes selectedCourse = courseList.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Edit Course");

        View editView = getLayoutInflater().inflate(R.layout.edit_course_dialog, null);

        final EditText editCourseName = editView.findViewById(R.id.editCourseName);
        final EditText editTime = editView.findViewById(R.id.editTime);
        final EditText editInstructor = editView.findViewById(R.id.editInstructor);

        editCourseName.setText(selectedCourse.getCourse());
        editTime.setText(selectedCourse.getTime());
        editInstructor.setText(selectedCourse.getInstructor());

        builder.setView(editView);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedCourse.setCourse(editCourseName.getText().toString().trim());
                selectedCourse.setTime(editTime.getText().toString().trim());
                selectedCourse.setInstructor(editInstructor.getText().toString().trim());

                adapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing or handle cancellation
            }
        });

        builder.show();
    }

    public void onDeleteCourse(final int position) {
        final Classes selectedCourse = courseList.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Confirm Deletion");
        builder.setMessage("Are you sure you want to delete this course?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                courseList.remove(position);
                adapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing or handle cancellation
            }
        });

        builder.show();
    }
}
