package com.freelancer.joblisting.creation.custom.model;

import com.freelancer.joblisting.creation.custom.CustomFieldType;

public class BooleanCustomFieldModel extends CustomFieldModel {
    private String booleanFieldTitle;
    private boolean customFieldState;

    public BooleanCustomFieldModel(String customFieldName, String booleanFieldTitle, boolean customFieldState) {
        super(CustomFieldType.BOOLEAN);
        this.booleanFieldTitle = booleanFieldTitle;
        this.customFieldState = customFieldState;
    }
}
