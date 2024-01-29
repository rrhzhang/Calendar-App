package com.example.calendarapp.ui.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.calendarapp.R;
import com.example.calendarapp.ui.gallery.Assignments;
import com.example.calendarapp.ui.home.Classes;
import com.example.calendarapp.ui.home.CourseItemListener;

import org.checkerframework.checker.units.qual.C;

import java.util.List;

public class AssignmentAdapter extends ArrayAdapter<Assignments> {

    private final AssignmentItemListener listener;

    public AssignmentAdapter(Context context, List<Assignments> itemList, AssignmentItemListener listener) {
        super(context, android.R.layout.simple_list_item_1, itemList);
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.assignment_list_item, parent, false);
        }

        Assignments currentAssignment = getItem(position);

        TextView assignmentInfoTextView = convertView.findViewById(R.id.assignmentInfoTextView);
        Button editAssignmentButton = convertView.findViewById(R.id.editAssignmentButton);
        Button deleteAssignmentButton = convertView.findViewById(R.id.deleteAssignmentButton);

        if (currentAssignment != null) {
            String displayText = currentAssignment.getTitle() + "\nDue: " + currentAssignment.getDue() +
                    "\nCourse: " + currentAssignment.getCourse();
            assignmentInfoTextView.setText(displayText);

            editAssignmentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onEditAssignment(position);
                }
            });

            deleteAssignmentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeleteAssignment(position);
                }
            });
        }

        return convertView;
    }
}
