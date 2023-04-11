package com.freelancer.joblisting.creation.custom.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.freelancer.R;
import com.freelancer.joblisting.creation.custom.viewmodel.CheckboxFieldViewModel;
import com.freelancer.joblisting.creation.custom.viewmodel.FieldFormViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CheckboxFieldFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckboxFieldFragment extends Fragment {
    private CheckboxFieldViewModel viewModel;

    public CheckboxFieldFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CustomCheckboxField.
     */
    // TODO: Rename and change types and number of parameters
    public static CheckboxFieldFragment newInstance() {
        CheckboxFieldFragment fragment = new CheckboxFieldFragment();
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
        viewModel = new ViewModelProvider(requireParentFragment()).get(CheckboxFieldViewModel.class);

        FieldFormViewModel parentViewModel = new ViewModelProvider(requireActivity()).get(FieldFormViewModel.class);
        parentViewModel.addCustomField(viewModel.getModel());

        return view;
    }
}