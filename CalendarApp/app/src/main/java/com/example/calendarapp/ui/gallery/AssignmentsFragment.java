package com.example.calendarapp.ui.gallery;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AssignmentsFragment extends Fragment implements AssignmentItemListener {

    private AssignmentAdapter adapter;
    private AssignmentsViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        viewModel = new ViewModelProvider(this).get(AssignmentsViewModel.class);
        adapter = new AssignmentAdapter(requireContext(), new ArrayList<>(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assignments, container, false);
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

                viewModel.addAssignment(assignment);

                editAssignmentTitle.getText().clear();
                editDue.getText().clear();
                editCourseAssignments.getText().clear();
            }
        });

        viewModel.getAssignmentsList().observe(getViewLifecycleOwner(), new Observer<List<Assignments>>() {
            @Override
            public void onChanged(List<Assignments> assignments) {
                adapter.updateDataSet(assignments);
            }
        });

        return view;
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();
        requireActivity().getMenuInflater().inflate(R.menu.sort_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.sort_course) {
            viewModel.sortAssignmentsByClass();
            return true;
        } else if (itemId == R.id.sort_due) {
            viewModel.sortAssignmentsByDueDate();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onEditAssignment(final int position) {
        final Assignments selectedAssignment = viewModel.getAssignmentsList().getValue().get(position);

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
                String newTitle = editAssignmentTitle.getText().toString().trim();
                String newDue = editDue.getText().toString().trim();
                String newCourse = editCourseAssignments.getText().toString().trim();

                selectedAssignment.setTitle(newTitle);
                selectedAssignment.setDue(newDue);
                selectedAssignment.setCourse(newCourse);

                viewModel.updateAssignment(position, selectedAssignment);
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


    @Override
    public void onDeleteAssignment(final int position) {
        viewModel.deleteAssignment(position);
    }

    private Date parseDueDate(String dueDateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dueDateString);
        } catch (ParseException e) {
            Log.e("AssignmentsFragment", "Error parsing due date: " + dueDateString, e);
            return null;
        }
    }
}
