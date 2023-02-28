package com.freelancer.joblisting.creation.custom;

public abstract class CustomField {
    private CustomFieldType customFieldType;
    private String customFieldName;

    public CustomField(CustomFieldType customFieldType, String customFieldName) {
        this.customFieldType = customFieldType;
        this.customFieldName = customFieldName;
    }
}
