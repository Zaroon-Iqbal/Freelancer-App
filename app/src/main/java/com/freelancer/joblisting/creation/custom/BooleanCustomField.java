package com.freelancer.joblisting.creation.custom;

public class BooleanCustomField extends CustomField {
    private String booleanFieldTitle;
    private boolean customFieldState;

    public BooleanCustomField(CustomFieldType customFieldType, String customFieldName, String booleanFieldTitle, boolean customFieldState) {
        super(customFieldType, customFieldName);
        this.booleanFieldTitle = booleanFieldTitle;
        this.customFieldState = customFieldState;
    }
}
