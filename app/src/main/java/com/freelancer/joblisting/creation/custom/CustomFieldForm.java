package com.freelancer.joblisting.creation.custom;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.freelancer.R;
import com.freelancer.joblisting.creation.custom.fragment.CustomFieldTemplate;
import com.freelancer.joblisting.creation.custom.viewmodel.CustomFieldFormViewModel;

import java.util.HashMap;

public class CustomFieldForm extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private CustomFieldFormViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_field_form);

        viewModel = new ViewModelProvider(this).get(CustomFieldFormViewModel.class);
        fragmentManager = getSupportFragmentManager();

        AutoCompleteTextView customFieldTypeDropdown = findViewById(R.id.custom_field_dropdown);
        setDropdownArrayAdapter(customFieldTypeDropdown);

        Button addCustomField = findViewById(R.id.add_custom_field_button);
        Button done = findViewById(R.id.done_button);

        done.setOnClickListener(v -> viewModel.printModels());
        addCustomField.setOnClickListener(onClick -> {
            Fragment fragment = addChildFragmentTransaction(customFieldTypeDropdown.getText().toString());
            if (fragment == null) {
                return;
            }
        });
    }

    private HashMap<String, FieldType> getCustomFields() {
        HashMap<String, FieldType> customFieldTypeHashMap = new HashMap<>();
        for (int a = 0; a < FieldType.values().length; a++) {
            customFieldTypeHashMap.put(FieldType.values()[a].customFieldName, FieldType.values()[a]);
        }

        return customFieldTypeHashMap;
    }

    private Fragment addChildFragmentTransaction(String selection) {
        HashMap<String, FieldType> fields = getCustomFields();
        FieldType fieldType = fields.getOrDefault(selection, FieldType.BOOLEAN);
        CustomFieldTemplate fragment = CustomFieldTemplate.newInstance(fieldType);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_linear_layout, fragment);
        transaction.commit();
        return fragment;
    }

    private void setDropdownArrayAdapter(AutoCompleteTextView dropdown) {
        HashMap<String, FieldType> fields = getCustomFields();
        String[] dropdownOptions = fields.keySet().toArray(new String[0]);

        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (getApplicationContext(), R.layout.dropdown_menu_popup_item, dropdownOptions);

        dropdown.setAdapter(adapter);
    }
}