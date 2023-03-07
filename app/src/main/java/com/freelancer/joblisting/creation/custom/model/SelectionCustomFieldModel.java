package com.freelancer.joblisting.creation.custom.model;

import com.freelancer.joblisting.creation.custom.CustomFieldType;

import java.util.ArrayList;

public class SelectionCustomFieldModel extends CustomFieldModel {
    private final ArrayList<CustomFieldOption> options;

    public SelectionCustomFieldModel(CustomFieldType customFieldType) {
        super(customFieldType);
        options = new ArrayList<>();
    }

    public void addOption(CustomFieldOption option) {
        options.add(option);
    }

    @Override
    public String toString() {
        return super.toString() + ", SelectionCustomFieldModel{" +
                "options=" + options +
                '}';
    }
}