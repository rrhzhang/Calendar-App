package com.example.calendarapp.ui.home;

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

import org.checkerframework.checker.units.qual.C;

import java.util.List;

public class ClassAdapter extends ArrayAdapter<Classes> {

    private final CourseItemListener listener;

    public ClassAdapter(Context context, List<Classes> itemList, CourseItemListener listener) {
        super(context, android.R.layout.simple_list_item_1, itemList);
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.course_list_item, parent, false);
        }

        Classes currentCourse = getItem(position);

        TextView courseInfoTextView = convertView.findViewById(R.id.courseInfoTextView);
        Button editButton = convertView.findViewById(R.id.editButton);
        Button deleteButton = convertView.findViewById(R.id.deleteButton);

        if (currentCourse != null) {
            String displayText = currentCourse.getCourse() + "\nTime: " + currentCourse.getTime() +
                    "\nInstructor: " + currentCourse.getInstructor();
            courseInfoTextView.setText(displayText);

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onEditCourse(position);
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeleteCourse(position);
                }
            });
        }

        return convertView;
    }
}
