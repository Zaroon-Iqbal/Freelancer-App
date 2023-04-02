package com.freelancer.joblisting.creation.custom.model;

import androidx.lifecycle.MutableLiveData;

public class BooleanFieldModel extends TemplateFieldModel {
    private final MutableLiveData<String> booleanFieldTitle;

    public BooleanFieldModel() {
        super(FieldType.BOOLEAN);
        booleanFieldTitle = new MutableLiveData<>();
    }

    public MutableLiveData<String> getBooleanFieldTitle() {
        return booleanFieldTitle;
    }

    @Override
    public String toString() {
        return super.toString() + ", BooleanCustomFieldModel{" +
                "booleanFieldTitle=" + booleanFieldTitle.getValue() +
                '}';
    }
}