package com.example.calendarapp.ui.slideshow;


import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.calendarapp.MainActivity;
import com.example.calendarapp.R;
import com.example.calendarapp.databinding.FragmentTodoBinding;
import com.example.calendarapp.ui.gallery.AssignmentAdapter;
import com.example.calendarapp.ui.gallery.Assignments;
import com.example.calendarapp.ui.gallery.AssignmentsFragment;
import com.example.calendarapp.ui.gallery.Tasks;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import android.widget.CalendarView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.Month;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.calendarapp.R;
import com.example.calendarapp.ui.gallery.AssignmentsFragment;
import com.example.calendarapp.ui.gallery.Tasks;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;





public class TodoFragment extends Fragment {

    CalendarView calendarView;
    Calendar calendar;
    TextView dateText;
    ArrayList<String> tasks = new ArrayList<>();
    //HashMap<String, ArrayList<String>> dateAssignmentPair= new HashMap<>();
    ArrayList<Tasks> tasksList = AssignmentsFragment.tasks;

    ConstraintLayout parent;
    EditText inputTaskText;
    EditText inputDueText;
    View popupView;
    ListView listView;
    ArrayAdapter<Tasks> taskAdapter;
    int year;
    int dayOfMonth;
    int month;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_todo, container, false);

        calendarView = rootView.findViewById(R.id.calendarView);
        dateText = rootView.findViewById(R.id.dateText);
        parent = rootView.findViewById(R.id._parent_layout);


        // Initialize calendar with the current date
        calendar = Calendar.getInstance();

        // Set the initial date text
        updateDateText();

        listView = rootView.findViewById(R.id.tasks);
        FloatingActionButton addTask = rootView.findViewById(R.id.addItem);

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {

                handleDateChange(year, month, dayOfMonth, rootView);
            }
        });
        return rootView;
    }
    private ArrayList<Tasks> filterTasksByDate (String selectedDate) {
        ArrayList<Tasks> filteredTasks = new ArrayList<>();
        for (Tasks t: tasksList) {
            if (t.getDue().equals(selectedDate)) {
                filteredTasks.add(t);
            }
        }
        return filteredTasks;
    }
    private void updateDateText() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        dateText.setText("Date: " + (month + 1) + "/" + dayOfMonth + "/" + year);
    }

    private void handleDateChange(int year, int month, int dayOfMonth, View rootView) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        updateDateText();

        String newDate = (month + 1) + "/" + dayOfMonth + "/" + year;

        ArrayList<Tasks> uploadTasks = filterTasksByDate(newDate);
        taskAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, uploadTasks);

        listView = rootView.findViewById(R.id.tasks);
        listView.setAdapter(taskAdapter);

        // Show a Snackbar or perform other actions as needed
        Snackbar.make(rootView, "Tasks loaded for selected date", Snackbar.LENGTH_LONG).show();
    }

    private void showPopupWindow() {
        popupView = View.inflate(requireContext(), R.layout.popout_layout, null);
        Button close = popupView.findViewById(R.id.close);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        PopupWindow window = new PopupWindow(popupView, width, height, true);
        window.showAtLocation(parent, Gravity.CENTER, 0,0);
        close.setOnClickListener(v -> {
            window.dismiss();
        });

        Button saveChanges = popupView.findViewById(R.id.saveChanges);
        inputTaskText = popupView.findViewById(R.id.editName);
        inputDueText = popupView.findViewById(R.id.editDue);
        saveChanges.setOnClickListener(v -> {
            addToList();
            inputTaskText.getText().clear();
            inputDueText.getText().clear();
            updateDateFromCalendar();
            window.dismiss();
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tasks item = (Tasks) parent.getItemAtPosition(position);
                popupView = View.inflate(requireContext(), R.layout.editdelete_layout, null);
                Button close = popupView.findViewById(R.id.close);
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                PopupWindow window = new PopupWindow(popupView, width, height, true);
                window.showAtLocation(parent, Gravity.CENTER, 0,0);
                inputTaskText = (EditText) popupView.findViewById(R.id.editName);
                inputDueText =  (EditText) popupView.findViewById(R.id.editDue);
                inputTaskText.setText(item.getTitle());
                inputDueText.setText(item.getDue());
                close.setOnClickListener(v -> {
                    window.dismiss();
                });
                Button delete = (Button) popupView.findViewById(R.id.deleteTask);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tasksList.remove(item);
                        listView.setAdapter(taskAdapter);
                        updateDateFromCalendar();
                        window.dismiss();
                    }
                });
                Button update = popupView.findViewById(R.id.saveChanges);
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        item.setDue(inputDueText.getText().toString().trim());
                        item.setTitle(inputTaskText.getText().toString().trim());
                        inputTaskText.getText().clear();
                        inputDueText.getText().clear();
                        updateDateFromCalendar();
                        window.dismiss();
                    }
                });

            }
        });


    }

    private void addToList() {
        inputTaskText = (EditText) popupView.findViewById(R.id.editName);
        inputDueText = (EditText) popupView.findViewById(R.id.editDue);
        Tasks newTask = new Tasks(inputTaskText.getText().toString().trim(), inputDueText.getText().toString().trim());
        tasksList.add(newTask);
    }
    private void updateDateFromCalendar() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        String updatedDate = (month+1) +"/"+dayOfMonth +"/"+year;
        taskAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, filterTasksByDate(updatedDate));
        Log.d("check", updatedDate);
        listView.setAdapter(taskAdapter);
    }
}