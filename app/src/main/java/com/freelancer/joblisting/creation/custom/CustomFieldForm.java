package com.freelancer.joblisting.creation.custom;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.freelancer.CustomCheckboxField;
import com.freelancer.R;
import com.freelancer.databinding.ActivityCustomFieldFormBinding;

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

        Button addCustomField = findViewById(R.id.add_custom_field_button);
        addCustomField.setOnClickListener(onClick -> addChildFragmentTransaction());

        Button checkFields = findViewById(R.id.check_fields);

        checkFields.setOnClickListener(onClick -> {
            List<Fragment> addedFragments = getAddedFragments();
            Toast.makeText(getApplicationContext(), "Fragments: " + addedFragments.size(), Toast.LENGTH_SHORT).show();
        });
    }

    private List<Fragment> getAddedFragments() {
        return fragmentManager.getFragments();
    }

    private void addChildFragmentTransaction() {

        CustomCheckboxField customCheckboxField = new CustomCheckboxField();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.parent_linear_layout, customCheckboxField);
        transaction.commit();
    }

    private void setDropdownArrayAdapter(AutoCompleteTextView dropdown) {
        String[] customFieldTitles = new String[CustomFieldType.values().length];
        for (int a = 0; a < CustomFieldType.values().length; a++) {
            customFieldTitles[a] = CustomFieldType.values()[a].customFieldName;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (getApplicationContext(), R.layout.dropdown_menu_popup_item, customFieldTitles);

        dropdown.setAdapter(adapter);
    }
}