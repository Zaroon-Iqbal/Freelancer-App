package com.freelancer;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_custom_checkbox_field, container, false);
        TextView titleTextView = view.findViewById(R.id.custom_checkbox_field_header);
        ImageView expandedStateArrow = view.findViewById(R.id.expanded_state_arrow);
        LinearLayout layoutToHide = view.findViewById(R.id.custom_field_details);
        LinearLayout titleLayout = view.findViewById(R.id.title_layout);
        titleLayout.setOnClickListener(onClick -> {
            TypedValue typedValue = new TypedValue();
            if (layoutToHide.getVisibility() == View.VISIBLE) {
                layoutToHide.setVisibility(View.GONE);
                view.getContext().getTheme().resolveAttribute(com.google.android.material.R.attr.colorSurfaceVariant, typedValue, true);
                int color = ContextCompat.getColor(view.getContext(), typedValue.resourceId);
                expandedStateArrow.setImageResource(R.drawable.drop_down);
                titleTextView.setBackgroundColor(color);
            } else {
                view.getContext().getTheme().resolveAttribute(com.google.android.material.R.attr.colorOnSecondary, typedValue, true);
                int color = ContextCompat.getColor(view.getContext(), typedValue.resourceId);
                titleTextView.setBackgroundColor(color);
                expandedStateArrow.setImageResource(R.drawable.drop_up);
                layoutToHide.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }
}