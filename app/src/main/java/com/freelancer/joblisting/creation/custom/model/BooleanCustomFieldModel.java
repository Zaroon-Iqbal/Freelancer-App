package com.freelancer.joblisting.creation.custom.model;

import androidx.lifecycle.MutableLiveData;

import com.freelancer.joblisting.creation.custom.FieldType;

public class BooleanCustomFieldModel extends CustomFieldModel {
    private final MutableLiveData<String> booleanFieldTitle;

    public BooleanCustomFieldModel() {
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