package com.example.calendarapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.calendarapp.databinding.FragmentClassesBinding;
import com.example.calendarapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentClassesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentClassesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textView;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;

        //textView.findViewById(R.id.class_button)
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}