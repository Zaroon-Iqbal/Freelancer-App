package com.freelancer.joblisting.creation.custom.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.freelancer.R;
import com.freelancer.joblisting.creation.custom.viewmodel.CustomCheckboxViewModel;
import com.freelancer.joblisting.creation.custom.viewmodel.CustomFieldFormViewModel;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomCheckboxField#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomCheckboxField extends Fragment {
    private CustomCheckboxViewModel viewModel;

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
        TextInputEditText textInput = view.findViewById(R.id.custom_checkbox_text_input);
        CustomFieldFormViewModel parentViewModel = new ViewModelProvider(requireActivity()).get(CustomFieldFormViewModel.class);
        viewModel = new ViewModelProvider(requireActivity()).get(CustomCheckboxViewModel.class);

        //parentViewModel.addCustomField(viewModel.getCustomFieldModel());
        textInput.setOnKeyListener((view1, i, keyEvent) -> {
            if (textInput.getText() != null) {
                viewModel.getCustomCheckboxTitle().setValue(textInput.getText().toString());
                return false;
            }
            return false;
        });

        return view;
    }

    public CustomCheckboxViewModel getViewModel() {
        return viewModel;
    }
}