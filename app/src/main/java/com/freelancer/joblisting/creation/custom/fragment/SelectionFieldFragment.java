package com.freelancer.joblisting.creation.custom.fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.freelancer.R;
import com.freelancer.joblisting.creation.custom.model.FieldType;
import com.freelancer.joblisting.creation.custom.viewmodel.FieldFormViewModel;
import com.freelancer.joblisting.creation.custom.viewmodel.SelectionFieldViewModel;
import com.freelancer.joblisting.creation.custom.viewmodel.factory.SelectionFieldViewModelFactory;
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

    private FieldType fieldType;

    public SelectionFieldFragment(FieldType fieldType) {
        this.fieldType = fieldType;
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CustomCheckboxField.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectionFieldFragment newInstance(FieldType fieldType) {
        SelectionFieldFragment fragment = new SelectionFieldFragment(fieldType);
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

        viewModel = new ViewModelProvider(requireParentFragment(),
                new SelectionFieldViewModelFactory(fieldType))
                .get(SelectionFieldViewModel.class);

        FieldFormViewModel parentViewModel = new ViewModelProvider(requireActivity()).get(FieldFormViewModel.class);
        parentViewModel.addCustomField(viewModel.getCustomFieldModel());

        ChipGroup chipGroup = view.findViewById(R.id.chip_group);
        TextInputEditText chipTextView = view.findViewById(R.id.custom_mutliselect_text);

        chipTextView.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (chipTextView.getText() == null || chipTextView.getText().toString().equals("")) {
                    return false;
                }
                String currentText = chipTextView.getText().toString();
                chipTextView.setText(currentText);
                viewModel.getCustomFieldModel().addOption(currentText);
                Chip newChip = (Chip) inflater.inflate(R.layout.custom_chip, chipGroup, false);
                newChip.setCloseIconVisible(true);
                newChip.setText(currentText);
                newChip.setCheckable(false);
                newChip.setOnCloseIconClickListener(v1 -> {
                    newChip.setVisibility(View.GONE);
                    chipGroup.removeView(newChip);
                    viewModel.getCustomFieldModel().removeOption(currentText);
                    Toast.makeText(getContext(), "Size: " + chipGroup.getChildCount(), Toast.LENGTH_SHORT).show();
                });
                chipGroup.addView(newChip);
                chipTextView.setText("");
                return true;
            }
            return false;
        });
        return view;
    }
}