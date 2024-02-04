package com.example.calendarapp.ui.Classes;

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
import androidx.core.content.ContextCompat;

import com.example.calendarapp.R;

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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.classes_list_item, parent, false);
        }

        Classes currentCourse = getItem(position);

        TextView courseInfoTextView = convertView.findViewById(R.id.courseInfoTextView);
        Button editButton = convertView.findViewById(R.id.editButton);
        Button deleteButton = convertView.findViewById(R.id.deleteButton);

        if (currentCourse != null) {
            SpannableStringBuilder builder = new SpannableStringBuilder();

            String course = currentCourse.getCourse();
            SpannableString courseSpannable = new SpannableString(course);
            courseSpannable.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, course.length(), 0);
            courseSpannable.setSpan(new RelativeSizeSpan(1.7f), 0, course.length(), 0);

            String time = "\nTime: " + currentCourse.getTime();
            SpannableString timeSpannable = new SpannableString(time);

            String instructor = "\nInstructor: " + currentCourse.getInstructor();
            SpannableString instructorSpannable = new SpannableString(instructor);

            builder.append(courseSpannable);
            builder.append(timeSpannable);
            builder.append(instructorSpannable);

            courseInfoTextView.setText(builder, TextView.BufferType.SPANNABLE);

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
