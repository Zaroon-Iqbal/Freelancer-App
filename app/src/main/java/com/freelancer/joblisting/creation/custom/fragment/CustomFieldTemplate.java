package com.freelancer.joblisting.creation.custom.fragment;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.freelancer.R;
import com.freelancer.joblisting.creation.custom.FieldType;
import com.freelancer.joblisting.creation.custom.model.CustomFieldModel;
import com.freelancer.joblisting.creation.custom.viewmodel.CustomCheckboxViewModel;
import com.freelancer.joblisting.creation.custom.viewmodel.CustomFieldFormViewModel;
import com.freelancer.joblisting.creation.custom.viewmodel.FreeformFieldViewModel;
import com.freelancer.joblisting.creation.custom.viewmodel.SelectionFieldViewModel;
import com.freelancer.joblisting.creation.custom.viewmodel.factory.CustomSelectionFieldFactory;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomFieldTemplate#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomFieldTemplate extends Fragment {
    private FieldType fieldType;
    private Fragment customFieldFragment;

    public CustomFieldTemplate() {
    }

    public CustomFieldTemplate(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CustomCheckboxField.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomFieldTemplate newInstance(FieldType fieldType) {
        CustomFieldTemplate fragment = new CustomFieldTemplate(fieldType);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        switch (fieldType) {
            case BOOLEAN:
                fragment.customFieldFragment = CustomCheckboxField.newInstance();
                break;

            case SINGLE_SELECT:
            case MULTI_SELECT:
                fragment.customFieldFragment = CustomSelectionField.newInstance(fieldType);
                break;

            case FREE_FORM:
                fragment.customFieldFragment = FreeformField.newInstance();
                break;

            default:
                break;
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager manager = this.getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.add(R.id.linear_layout_insertion, customFieldFragment);
        transaction.commit();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewModel viewModel;
        CustomFieldModel fieldModel;
        switch (fieldType) {
            case MULTI_SELECT:
                viewModel = new ViewModelProvider(this,
                        new CustomSelectionFieldFactory(FieldType.MULTI_SELECT))
                        .get(SelectionFieldViewModel.class);
                fieldModel = ((SelectionFieldViewModel) viewModel).getCustomFieldModel();
                break;

            case SINGLE_SELECT:
                viewModel = new ViewModelProvider(this,
                        new CustomSelectionFieldFactory(FieldType.SINGLE_SELECT))
                        .get(SelectionFieldViewModel.class);
                fieldModel = ((SelectionFieldViewModel) viewModel).getCustomFieldModel();
                break;


            case BOOLEAN:
                viewModel = new ViewModelProvider(this).get(CustomCheckboxViewModel.class);
                fieldModel = ((CustomCheckboxViewModel) viewModel).getModel();
                break;

            case FREE_FORM:
                viewModel
                        = new ViewModelProvider(this).get(FreeformFieldViewModel.class);
                fieldModel = ((FreeformFieldViewModel) viewModel).getModel();
                break;

            default:
                return;
        }

        TextView customFieldHeader = view.findViewById(R.id.custom_field_header);
        TextInputEditText customFieldTitle = view.findViewById(R.id.custom_field_title);
        ImageView deleteImage = view.findViewById(R.id.delete_image_icon);

        ConstraintLayout constraintLayout = view.findViewById(R.id.coordinatorLayout);
        MaterialCardView cardView = view.findViewById(R.id.checkbox_card_view);
        CustomFieldFormViewModel formViewModel = new ViewModelProvider(requireActivity()).get(CustomFieldFormViewModel.class);


        deleteImage.setOnClickListener(onClick -> {
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
            formViewModel.removeCustomField(fieldModel);
        });

        customFieldTitle.setOnKeyListener((v, keyCode, event) -> {
            customFieldHeader.setText(customFieldTitle.getText().toString());
            fieldModel.getCustomFieldName().setValue(customFieldTitle.getText().toString());
            return false;
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_field_template, container, false);

        if (this.getActivity() == null) {
            Toast.makeText(getContext(), "The parent activity is null.", Toast.LENGTH_SHORT).show();
            return view;
        }

        ImageView expandedStateArrow = view.findViewById(R.id.expanded_state_arrow);
        LinearLayout layoutToHide = view.findViewById(R.id.custom_field_details);
        LinearLayout titleLayout = view.findViewById(R.id.title_layout);

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

        return view;
    }
}