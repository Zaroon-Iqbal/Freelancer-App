package com.freelancer.joblisting.creation.custom;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.freelancer.R;
import com.freelancer.joblisting.creation.custom.viewmodel.CustomFieldFormViewModel;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomFieldTemplate#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomFieldTemplate extends Fragment {
    private Fragment customFieldFragment;

    public CustomFieldTemplate() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CustomCheckboxField.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomFieldTemplate newInstance(CustomFieldType customFieldType) {
        CustomFieldTemplate fragment = new CustomFieldTemplate();
        switch (customFieldType) {
            case BOOLEAN:
                fragment.customFieldFragment = CustomCheckboxField.newInstance();
                break;

            case MULTI_SELECT:
                fragment.customFieldFragment = CustomMultiSelectField.newInstance();
                break;

            default:
                fragment.customFieldFragment = CustomMultiSelectField.newInstance();
                break;
        }
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
        View view = inflater.inflate(R.layout.fragment_custom_field_template, container, false);

        CustomFieldFormViewModel formViewModel = ((CustomFieldForm) requireActivity()).getViewModel();
        if (this.getActivity() == null) {
            Toast.makeText(getContext(), "The parent activity is null.", Toast.LENGTH_SHORT).show();
            return view;
        }

        ImageView expandedStateArrow = view.findViewById(R.id.expanded_state_arrow);
        LinearLayout layoutToHide = view.findViewById(R.id.custom_field_details);
        LinearLayout titleLayout = view.findViewById(R.id.title_layout);
        ImageView deleteImage = view.findViewById(R.id.delete_image_icon);

        CheckBox additionalCostCheckbox = view.findViewById(R.id.additional_cost_checkbox);
        ConstraintLayout constraintLayout = view.findViewById(R.id.coordinatorLayout);
        MaterialCardView cardView = view.findViewById(R.id.checkbox_card_view);
        TextInputLayout additionalCostTextView = view.findViewById(R.id.custom_checkbox_additional_cost_layout);

        additionalCostCheckbox.setOnClickListener(onClick -> {
            int visibility = (additionalCostCheckbox.isChecked() ? View.VISIBLE : View.GONE);
            additionalCostTextView.setVisibility(visibility);
        });

        TextView customFieldHeader = view.findViewById(R.id.custom_field_header);
        TextInputEditText customFieldTitle = view.findViewById(R.id.custom_field_title);
        customFieldTitle.setOnKeyListener((v, keyCode, event) -> {
            customFieldHeader.setText(customFieldTitle.getText().toString());
            return false;
        });

        deleteImage.setOnClickListener(onClick -> {
            Log.i("ACTIVITY B4", formViewModel.getFragmentsSize() + "");
            if (constraintLayout.getAnimation() != null) {
                return;
            }
            TranslateAnimation animate = new TranslateAnimation(
                    0,
                    view.getWidth(),
                    0,
                    0);
            animate.setDuration(350);
            animate.setFillAfter(true);
            animate.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    cardView.setVisibility(View.GONE);
                    constraintLayout.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    
                }
            });
            constraintLayout.startAnimation(animate);

            formViewModel.removeFragment(this);

            Log.i("ACTIVITY", formViewModel.getFragmentsSize() + "");
        });

        titleLayout.setOnClickListener(onClick -> {
            TypedValue typedValue = new TypedValue();
            if (layoutToHide.getVisibility() == View.VISIBLE) {
                view.getContext().getTheme().resolveAttribute(com.google.android.material.R.attr.colorOnSecondary, typedValue, true);
                int color = ContextCompat.getColor(view.getContext(), typedValue.resourceId);
                expandedStateArrow.setImageResource(R.drawable.drop_down);
                titleLayout.setBackgroundColor(color);
                layoutToHide.setVisibility(View.GONE);
            } else {
                view.getContext().getTheme().resolveAttribute(com.google.android.material.R.attr.colorSurfaceVariant, typedValue, true);
                int color = ContextCompat.getColor(view.getContext(), typedValue.resourceId);
                expandedStateArrow.setImageResource(R.drawable.drop_up);
                titleLayout.setBackgroundColor(color);
                layoutToHide.setVisibility(View.VISIBLE);
            }
        });

        FragmentManager manager = this.getChildFragmentManager();

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.linear_layout_insertion, customFieldFragment);
        transaction.commit();

        return view;
    }
}