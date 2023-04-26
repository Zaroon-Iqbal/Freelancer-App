package com.freelancer.joblisting.creation.custom.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.freelancer.R;
import com.freelancer.joblisting.creation.custom.model.SelectionType;
import com.freelancer.joblisting.creation.custom.viewmodel.FieldFormViewModel;
import com.freelancer.joblisting.creation.custom.viewmodel.SelectionFieldViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectionFieldFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectionFieldFragment extends Fragment {
    private SelectionFieldViewModel viewModel;

    public SelectionFieldFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CustomCheckboxField.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectionFieldFragment newInstance() {
        SelectionFieldFragment fragment = new SelectionFieldFragment();
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
        if (getParentFragment() == null || getParentFragment().getView() == null) {
            return view;
        }

        viewModel = new ViewModelProvider(requireParentFragment()).get(SelectionFieldViewModel.class);

//        viewModel = new ViewModelProvider(requireParentFragment(),
//                new SelectionFieldViewModelFactory(fieldType))
//                .get(SelectionFieldViewModel.class);

        FieldFormViewModel parentViewModel = new ViewModelProvider(requireActivity()).get(FieldFormViewModel.class);
        parentViewModel.addCustomField(viewModel.getCustomFieldModel());

        ChipGroup chipGroup = view.findViewById(R.id.chip_group);
        TextInputEditText chipTextView = view.findViewById(R.id.custom_mutliselect_text);
        RadioGroup selectionGroup = view.findViewById(R.id.selection_radio_group);
        Button addSelection = view.findViewById(R.id.add_selection_button);


        selectionGroup.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            switch (checkedId) {
                case R.id.single_selection_radio:
                    viewModel.setSelectionType(SelectionType.SINGLE);
                    break;

                case R.id.multi_selection_radio:
                    viewModel.setSelectionType(SelectionType.MULTIPLE);
                    break;
            }
        });

        addSelection.setOnClickListener(view1 -> {
            if (chipTextView.getText() == null || chipTextView.getText().toString().equals("")) {
                return;
            }
            String currentText = chipTextView.getText().toString();
            chipTextView.setText(currentText);
            viewModel.getCustomFieldModel().addOption(currentText);
            Chip newChip = createCustomizedChip(inflater, chipGroup, currentText);
            chipGroup.addView(newChip);
            chipTextView.setText("");
        });
        return view;
    }

    private Chip createCustomizedChip(LayoutInflater inflater, ChipGroup chipGroup, String title) {
        Chip newChip = (Chip) inflater.inflate(R.layout.custom_chip, chipGroup, false);
        newChip.setCloseIconVisible(true);
        newChip.setText(title);
        newChip.setCheckable(false);
        newChip.setOnCloseIconClickListener(v1 -> {
            newChip.setVisibility(View.GONE);
            chipGroup.removeView(newChip);
            viewModel.getCustomFieldModel().removeOption(title);
        });
        return newChip;
    }
}