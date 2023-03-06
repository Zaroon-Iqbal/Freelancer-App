package com.freelancer.joblisting.creation.custom.model;

import com.freelancer.joblisting.creation.custom.CustomFieldType;

public abstract class CustomFieldModel {
    private CustomFieldType customFieldType;
    private String customFieldName;

    public CustomFieldModel(CustomFieldType customFieldType) {
        this.customFieldType = customFieldType;
        this.customFieldName = "";
    }
}
