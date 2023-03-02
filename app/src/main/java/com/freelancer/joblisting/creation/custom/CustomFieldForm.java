package com.freelancer.joblisting.creation.custom;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.freelancer.R;
import com.freelancer.databinding.ActivityCustomFieldFormBinding;

import java.util.HashMap;
import java.util.List;

public class CustomFieldForm extends AppCompatActivity {
    private ActivityCustomFieldFormBinding binding;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_field_form);
        binding = ActivityCustomFieldFormBinding.inflate(getLayoutInflater());
        fragmentManager = getSupportFragmentManager();

        AutoCompleteTextView customFieldTypeDropdown = findViewById(R.id.custom_field_dropdown);
        setDropdownArrayAdapter(customFieldTypeDropdown);

        String selectedDropdownEntry = customFieldTypeDropdown.getText().toString();
        Button addCustomField = findViewById(R.id.add_custom_field_button);

        customFieldTypeDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addCustomField.setEnabled(true);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                addCustomField.setEnabled(false);
            }
        });
        addCustomField.setOnClickListener(onClick -> {
            addChildFragmentTransaction(customFieldTypeDropdown.getText().toString());
        });
    }

    private HashMap<String, CustomFieldType> getCustomFields() {
        HashMap<String, CustomFieldType> customFieldTypeHashMap = new HashMap<>();
        for (int a = 0; a < CustomFieldType.values().length; a++) {
            customFieldTypeHashMap.put(CustomFieldType.values()[a].customFieldName, CustomFieldType.values()[a]);
        }

        return customFieldTypeHashMap;
    }

    private List<Fragment> getAddedFragments() {
        return fragmentManager.getFragments();
    }

    private void addChildFragmentTransaction(String selection) {

        HashMap<String, CustomFieldType> fields = getCustomFields();


        Log.i("TAG", fields.keySet().toString());
        Log.i("TAG", selection);
        CustomFieldType fieldType = fields.getOrDefault(selection, CustomFieldType.BOOLEAN);
        Fragment fragment;
        Toast.makeText(getApplicationContext(), "Field type: " + fieldType.customFieldName, Toast.LENGTH_SHORT).show();
        switch (fieldType) {
            case BOOLEAN:
                fragment = new CustomField(CustomFieldType.BOOLEAN);
                break;

            case FREE_FORM:
                fragment = new CustomField(CustomFieldType.MULTI_SELECT);
                break;

            case MULTI_SELECT:
                fragment = new CustomField(CustomFieldType.MULTI_SELECT);
                break;

            case SINGLE_SELECT:
                fragment = new CustomMultiSelectField();
                break;

            default:
                fragment = new CustomCheckboxField();
                return;
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.parent_linear_layout, fragment);
        transaction.commit();
    }

    private void setDropdownArrayAdapter(AutoCompleteTextView dropdown) {
        HashMap<String, CustomFieldType> fields = getCustomFields();
        String[] dropdownOptions = fields.keySet().toArray(new String[0]);

        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (getApplicationContext(), R.layout.dropdown_menu_popup_item, dropdownOptions);

        dropdown.setAdapter(adapter);
    }
}