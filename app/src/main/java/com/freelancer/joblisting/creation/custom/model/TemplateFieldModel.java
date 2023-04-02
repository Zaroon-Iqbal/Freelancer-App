package com.freelancer.joblisting.creation.custom.model;

import androidx.lifecycle.MutableLiveData;

public abstract class TemplateFieldModel {
    private final FieldType fieldType;
    private final MutableLiveData<String> fieldName;

    public TemplateFieldModel(FieldType fieldType) {
        this.fieldType = fieldType;
        this.fieldName = new MutableLiveData<>();
    }

    public MutableLiveData<String> getFieldName() {
        return fieldName;
    }

    @Override
    public String toString() {
        return "CustomFieldModel{" +
                "fieldType=" + fieldType +
                ", fieldName='" + fieldName.getValue() + '\'' +
                '}';
    }
}