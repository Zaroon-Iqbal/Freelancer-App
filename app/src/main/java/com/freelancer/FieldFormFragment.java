package com.freelancer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.freelancer.joblisting.creation.custom.fragment.FieldTemplateFragment;
import com.freelancer.joblisting.creation.custom.model.FieldType;
import com.freelancer.joblisting.creation.custom.viewmodel.FieldFormViewModel;

import java.util.Arrays;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FieldFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FieldFormFragment extends Fragment {

    private FieldFormViewModel viewModel;
    private FragmentManager fragmentManager;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FieldFormFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FieldFormFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FieldFormFragment newInstance() {
        FieldFormFragment fragment = new FieldFormFragment();
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
        View view = inflater.inflate(R.layout.fragment_field_form, container, false);

        viewModel = new ViewModelProvider(this).get(FieldFormViewModel.class);
        fragmentManager = getParentFragmentManager();

        AutoCompleteTextView customFieldTypeDropdown = view.findViewById(R.id.custom_field_dropdown);

        setDropdownArrayAdapter(customFieldTypeDropdown);

        Button addCustomField = view.findViewById(R.id.add_custom_field_button);
        Button done = view.findViewById(R.id.done_button);

        done.setOnClickListener(v -> viewModel.printModels());
        addCustomField.setOnClickListener(onClick -> addChildFragmentTransaction(customFieldTypeDropdown.getText().toString()));

        return view;
    }

    private HashMap<String, FieldType> getCustomFields() {
        HashMap<String, FieldType> customFieldTypeHashMap = new HashMap<>();
        for (int a = 0; a < FieldType.values().length; a++) {
            customFieldTypeHashMap.put(FieldType.values()[a].customFieldName, FieldType.values()[a]);
        }

        return customFieldTypeHashMap;
    }

    private void addChildFragmentTransaction(String selection) {
        HashMap<String, FieldType> fields = getCustomFields();
        FieldType fieldType = fields.getOrDefault(selection, FieldType.BOOLEAN);
        FieldTemplateFragment fragment = FieldTemplateFragment.newInstance(fieldType);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_linear_layout, fragment);
        transaction.commit();
    }

    private void setDropdownArrayAdapter(AutoCompleteTextView dropdown) {
        HashMap<String, FieldType> fields = getCustomFields();
        String[] dropdownOptions = fields.keySet().toArray(new String[0]);

        Log.i("Options", "dropdown: " + Arrays.toString(dropdownOptions));
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (getContext(), R.layout.dropdown_menu_popup_item, dropdownOptions);

        dropdown.setAdapter(adapter);
    }
}










