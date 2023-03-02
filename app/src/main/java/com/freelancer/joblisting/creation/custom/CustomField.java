package com.freelancer.joblisting.creation.custom;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.freelancer.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomField#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomField extends Fragment {
    private final CustomFieldType customFieldType;
    public CustomField(CustomFieldType customFieldType) {
        this.customFieldType = customFieldType;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CustomCheckboxField.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomField newInstance(CustomFieldType customFieldType) {
        CustomField fragment = new CustomField(customFieldType);
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

        deleteImage.setOnClickListener(onClick -> {
            if(constraintLayout.getAnimation() != null) {
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
        Fragment fragment;
        switch (customFieldType) {
            case BOOLEAN:
                fragment = CustomCheckboxField.newInstance();
                break;

            case MULTI_SELECT:
                fragment = CustomMultiSelectField.newInstance();
                break;

            default:
                fragment = CustomMultiSelectField.newInstance();
                break;
        }

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.custom_field_details, fragment);
        transaction.commit();

        return view;
    }
}