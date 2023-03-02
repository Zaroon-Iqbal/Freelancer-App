package com.freelancer.joblisting.creation.custom;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.freelancer.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomMultiSelectField#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomMultiSelectField extends Fragment {
    public CustomMultiSelectField() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CustomCheckboxField.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomMultiSelectField newInstance() {
        CustomMultiSelectField fragment = new CustomMultiSelectField();
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
        View view = inflater.inflate(R.layout.fragment_custom_multiselect_field, container, false);

        ChipGroup chipGroup = view.findViewById(R.id.chip_group);
        Chip chip = view.findViewById(R.id.chip_1);

        chip.setOnClickListener(onClick -> {
            if(chip.getAnimation() != null && !chip.getAnimation().hasEnded()) {
                Toast.makeText(getContext(), "Returning", Toast.LENGTH_SHORT).show();
                return;
            }
            TranslateAnimation animation =
                    new TranslateAnimation(0, view.getWidth() - 200, 0, 0);

            animation.setRepeatMode(Animation.REVERSE);
            animation.setRepeatCount(Animation.INFINITE);
            animation.setDuration(900);

            chip.startAnimation(animation);
        });
        return view;
    }
}