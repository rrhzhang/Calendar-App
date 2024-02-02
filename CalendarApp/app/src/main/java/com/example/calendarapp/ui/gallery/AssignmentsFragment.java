package com.example.calendarapp.ui.gallery;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.calendarapp.R;
//import com.google.android.gms.tasks.Tasks;

import java.util.ArrayList;
import java.util.List;

public class AssignmentsFragment extends Fragment implements AssignmentItemListener {

    public static List<Assignments> assignmentsList = new ArrayList<>();
    //public static HashMap<String, ArrayList<Tasks>> todoList = new HashMap<>();
    private AssignmentAdapter adapter;
    public static ArrayList<Tasks> tasks = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assignments, container, false);

        adapter = new AssignmentAdapter(requireContext(), assignmentsList, this);
        ListView assignmentsView = view.findViewById(R.id.tasks);
        assignmentsView.setAdapter(adapter);

        final EditText editAssignmentTitle = view.findViewById(R.id.editName);
        final EditText editDue = view.findViewById(R.id.editDue);
        final EditText editCourseAssignments = view.findViewById(R.id.editDueTime);

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

                tasks.add(assignment);
                /*if (todoList.containsKey(due)) {
                    tasks = todoList.get(due);
                    tasks.add(assignment);
                    todoList.put(due, tasks);
                }
                else {
                    tasks = new ArrayList<>();
                    tasks.add(assignment);
                    todoList.put(due, tasks);
                }*/


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

        final EditText editAssignmentTitle = editView.findViewById(R.id.editName);
        final EditText editDue = editView.findViewById(R.id.editDue);
        final EditText editCourseAssignments = editView.findViewById(R.id.editDueTime);

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
    public List<Assignments> getAssignments () {
        List<Assignments> arrList = new ArrayList<>();
        for (Assignments a: assignmentsList) {
            arrList.add(a);
        }
        return arrList;
    }
}
