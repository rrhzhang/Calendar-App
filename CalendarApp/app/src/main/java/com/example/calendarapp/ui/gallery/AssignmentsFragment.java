package com.example.calendarapp.ui.gallery;

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
import com.example.calendarapp.ui.home.ClassAdapter;
import com.example.calendarapp.ui.home.Classes;
import com.example.calendarapp.ui.home.CourseItemListener;

import java.util.ArrayList;
import java.util.List;

public class AssignmentsFragment extends Fragment implements AssignmentItemListener {

    private List<Assignments> assignmentsList = new ArrayList<>();
    private AssignmentAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assignments, container, false);

        adapter = new AssignmentAdapter(requireContext(), assignmentsList, this);
        ListView assignmentsView = view.findViewById(R.id.assignmentsView);
        assignmentsView.setAdapter(adapter);

        final EditText editAssignmentTitle = view.findViewById(R.id.editAssignmentTitle);
        final EditText editDue = view.findViewById(R.id.editDue);
        final EditText editCourseAssignments = view.findViewById(R.id.editCourseAssignments);

        Button addAssignmentsButton = view.findViewById(R.id.addAssignmentsButton);
        addAssignmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String assignmentTitle = editAssignmentTitle.getText().toString().trim();
                String due = editDue.getText().toString().trim();
                String course = editCourseAssignments.getText().toString().trim();

                Assignments assignment = new Assignments();
                assignment.setTitle(assignmentTitle);
                assignment.setDue(due);
                assignment.setCourse(course);

                assignmentsList.add(assignment);

                adapter.notifyDataSetChanged();

                editAssignmentTitle.getText().clear();
                editDue.getText().clear();
                editCourseAssignments.getText().clear();
            }
        });

        return view;
    }

    public void onEditAssignment(final int position) {
        final Assignments selectedAssignment = assignmentsList.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Edit Assignment");

        View editView = getLayoutInflater().inflate(R.layout.edit_assignment_dialog, null);

        final EditText editAssignmentTitle = editView.findViewById(R.id.editAssignmentTitle);
        final EditText editDue = editView.findViewById(R.id.editDue);
        final EditText editCourseAssignments = editView.findViewById(R.id.editCourseAssignments);

        editAssignmentTitle.setText(selectedAssignment.getTitle());
        editDue.setText(selectedAssignment.getDue());
        editCourseAssignments.setText(selectedAssignment.getCourse());

        builder.setView(editView);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedAssignment.setTitle(editAssignmentTitle.getText().toString().trim());
                selectedAssignment.setDue(editDue.getText().toString().trim());
                selectedAssignment.setCourse(editCourseAssignments.getText().toString().trim());

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

    public void onDeleteAssignment(final int position) {
        final Assignments selectedAssignment = assignmentsList.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Confirm Deletion");
        builder.setMessage("Are you sure you want to delete this assignment?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                assignmentsList.remove(position);
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
