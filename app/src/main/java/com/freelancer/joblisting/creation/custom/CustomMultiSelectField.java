package com.freelancer.joblisting.creation.custom;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.freelancer.R;
import com.freelancer.joblisting.creation.custom.viewmodel.CustomSelectionFieldViewModel;
import com.freelancer.joblisting.creation.custom.viewmodel.factory.CustomSelectionFieldFactory;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomMultiSelectField#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomMultiSelectField extends Fragment {
    private CustomSelectionFieldViewModel viewModel;

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
        viewModel = new ViewModelProvider(this, new CustomSelectionFieldFactory(CustomFieldType.MULTI_SELECT)).get(CustomSelectionFieldViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_multiselect_field, container, false);
//        ViewModelProvider vmp = new ViewModelProvider(getParentFragment());
//        CustomFieldTemplateViewModel parentViewModel = vmp.get(CustomFieldTemplateViewModel.class);
//        Toast.makeText(getContext(), parentViewModel.getHello(), Toast.LENGTH_SHORT).show();
        if (getParentFragment() == null || getParentFragment().getView() == null) {
            return view;
        }
        ChipGroup chipGroup = view.findViewById(R.id.chip_group);
        TextInputEditText chipTextView = view.findViewById(R.id.chip_text_edit);

        CheckBox additionalCost = getActivity().findViewById(R.id.additional_cost_checkbox);
        TextInputEditText additionalCostText = getParentFragment().getView().findViewById(R.id.additional_cost_text);

        ArrayList<String> entries = new ArrayList<>();

        chipTextView.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (chipTextView.getText() == null || chipTextView.getText().toString().equals("")) {
                    return false;
                }
                String currentText = chipTextView.getText().toString() + '\0';
                chipTextView.setText(currentText);
                Chip newChip = (Chip) inflater.inflate(R.layout.custom_chip, chipGroup, false);
                newChip.setCloseIconVisible(true);
                newChip.setText(currentText);
                newChip.setCheckable(false);
                newChip.setOnCloseIconClickListener(v1 -> {
                    newChip.setVisibility(View.GONE);
                    chipGroup.removeView(newChip);
                    Toast.makeText(getContext(), "Size: " + chipGroup.getChildCount(), Toast.LENGTH_SHORT).show();
                });
                if (additionalCost.isChecked() && additionalCostText.getText().equals("")) {
                    Toast.makeText(getContext(), "No, bad", Toast.LENGTH_SHORT).show();
                }
                chipGroup.addView(newChip);
                chipTextView.setText("");
                return true;
            }
            return false;
        });
        return view;
    }
}