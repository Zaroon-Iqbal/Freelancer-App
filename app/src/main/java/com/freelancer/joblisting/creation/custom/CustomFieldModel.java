package com.freelancer.joblisting.creation.custom;

public abstract class CustomFieldModel {
    private CustomFieldType customFieldType;
    private String customFieldName;

    public CustomFieldModel(CustomFieldType customFieldType, String customFieldName) {
        this.customFieldType = customFieldType;
        this.customFieldName = customFieldName;
    }
}
