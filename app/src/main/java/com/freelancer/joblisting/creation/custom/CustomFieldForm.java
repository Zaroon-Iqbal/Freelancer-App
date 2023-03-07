package com.freelancer.joblisting.creation.custom;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

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

    private HashMap<String, CustomFieldType> getCustomFields() {
        HashMap<String, CustomFieldType> customFieldTypeHashMap = new HashMap<>();
        for (int a = 0; a < CustomFieldType.values().length; a++) {
            customFieldTypeHashMap.put(CustomFieldType.values()[a].customFieldName, CustomFieldType.values()[a]);
        }

        return customFieldTypeHashMap;
    }

    private Fragment addChildFragmentTransaction(String selection) {
        HashMap<String, CustomFieldType> fields = getCustomFields();

        Log.i("TAG", fields.keySet().toString());
        Log.i("TAG", selection);
        CustomFieldType fieldType = fields.getOrDefault(selection, CustomFieldType.BOOLEAN);
        CustomFieldTemplate fragment;
        Toast.makeText(getApplicationContext(), "Field type: " + fieldType.customFieldName, Toast.LENGTH_SHORT).show();
        switch (fieldType) {
            case BOOLEAN:
                fragment = CustomFieldTemplate.newInstance(CustomFieldType.BOOLEAN);
                break;

            case FREE_FORM:
            case MULTI_SELECT:
            case SINGLE_SELECT:
                fragment = CustomFieldTemplate.newInstance(CustomFieldType.MULTI_SELECT);
                break;

            default:
                return null;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_linear_layout, fragment);
        transaction.commit();
        return fragment;
    }

    private void setDropdownArrayAdapter(AutoCompleteTextView dropdown) {
        HashMap<String, CustomFieldType> fields = getCustomFields();
        String[] dropdownOptions = fields.keySet().toArray(new String[0]);

        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (getApplicationContext(), R.layout.dropdown_menu_popup_item, dropdownOptions);

        dropdown.setAdapter(adapter);
    }

    public CustomFieldFormViewModel getViewModel() {
        return viewModel;
    }
}