package com.example.calendarapp.ui.Exams;

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

import java.util.List;

public class ExamAdapter extends ArrayAdapter<Exams> {

    private final ExamsItemListener listener;

    public ExamAdapter(Context context, List<Exams> itemList, ExamsItemListener listener) {
        super(context, android.R.layout.simple_list_item_1, itemList);
        this.listener = listener;
    }

    public void updateDataSet(List<Exams> sortedExams) {
        clear();
        addAll(sortedExams);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.exam_list_item, parent, false);
        }

        Exams currentExam = getItem(position);

        TextView examInfoText = convertView.findViewById(R.id.examInfoTextView);
        Button deleteExamButton = convertView.findViewById(R.id.deleteExamButton);

        if (currentExam != null) {
            String displayText = currentExam.getCourse();
            examInfoText.setText(displayText);

            deleteExamButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeleteExam(position);
                }
            });
        }

        return convertView;
    }
}
