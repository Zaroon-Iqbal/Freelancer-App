package com.freelancer.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.freelancer.R;
import com.freelancer.databinding.CalendarViewFragmentBinding;

public class CalendarViewFragment extends Fragment {

    private CalendarViewFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = CalendarViewFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.textView23.setOnClickListener(view1 -> NavHostFragment.findNavController(CalendarViewFragment.this)
                .navigate(R.id.action_FirstFragment_to_SecondFragment));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}