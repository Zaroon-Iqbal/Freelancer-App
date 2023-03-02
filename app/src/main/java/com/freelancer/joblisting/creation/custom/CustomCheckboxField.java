package com.freelancer.joblisting.creation.custom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.freelancer.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomCheckboxField#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomCheckboxField extends Fragment {
    public CustomCheckboxField() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CustomCheckboxField.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomCheckboxField newInstance() {
        CustomCheckboxField fragment = new CustomCheckboxField();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_checkbox_field, container, false);
        TextView textView = view.findViewById(R.id.custom_checkbox_text_view);
        textView.setOnClickListener(onClick -> {
            Toast.makeText(view.getContext(), "Tapped on the checkbox thing!", Toast.LENGTH_SHORT).show();
        });
        return view;
    }
}