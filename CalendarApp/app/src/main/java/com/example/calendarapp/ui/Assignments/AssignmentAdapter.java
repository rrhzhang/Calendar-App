package com.example.calendarapp.ui.Assignments;

import android.content.Context;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.calendarapp.R;

import java.util.List;

public class AssignmentAdapter extends ArrayAdapter<Assignments> {

    private final AssignmentItemListener listener;

    public AssignmentAdapter(Context context, List<Assignments> itemList, AssignmentItemListener listener) {
        super(context, android.R.layout.simple_list_item_1, itemList);
        this.listener = listener;
    }

    public void updateDataSet(List<Assignments> sortedAssignments) {
        clear();
        addAll(sortedAssignments);
        notifyDataSetChanged();
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
            SpannableStringBuilder builder = new SpannableStringBuilder();

            String title = currentAssignment.getTitle();
            SpannableString titleSpannable = new SpannableString(title);
            titleSpannable.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, title.length(), 0);
            titleSpannable.setSpan(new RelativeSizeSpan(1.7f), 0, title.length(), 0);

            String due = "\nDue: " + currentAssignment.getDue();
            SpannableString dueSpannable = new SpannableString(due);

            String course = "\nCourse: " + currentAssignment.getCourse();
            SpannableString courseSpannable = new SpannableString(course);

            builder.append(titleSpannable);
            builder.append(dueSpannable);
            builder.append(courseSpannable);

            assignmentInfoTextView.setText(builder, TextView.BufferType.SPANNABLE);

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
