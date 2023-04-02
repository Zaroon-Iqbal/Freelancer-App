package com.freelancer.joblisting.creation.custom.model;

import androidx.lifecycle.MutableLiveData;

import com.freelancer.joblisting.creation.custom.FieldType;

public abstract class CustomFieldModel {
    private final FieldType fieldType;
    private final MutableLiveData<String> customFieldName;

    public CustomFieldModel(FieldType fieldType) {
        this.fieldType = fieldType;
        this.customFieldName = new MutableLiveData<>();
    }

    public MutableLiveData<String> getCustomFieldName() {
        return customFieldName;
    }

    @Override
    public String toString() {
        return "CustomFieldModel{" +
                "customFieldType=" + fieldType +
                ", customFieldName='" + customFieldName.getValue() + '\'' +
                '}';
    }
}
