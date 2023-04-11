package com.freelancer.joblisting.creation.custom.model;

import androidx.lifecycle.MutableLiveData;

import java.io.Serializable;

public abstract class TemplateFieldModel implements Serializable {
    public FieldType fieldType;
    public MutableLiveData<String> fieldName;

    public TemplateFieldModel(FieldType fieldType) {
        this.fieldType = fieldType;
        this.fieldName = new MutableLiveData<>();
    }

    public void setTitle(String title) {
        fieldName.setValue(title);
    }

    @Override
    public String toString() {
        return "CustomFieldModel{" +
                "fieldType=" + fieldType +
                ", fieldName='" + fieldName.getValue() + '\'' +
                '}';
    }
}