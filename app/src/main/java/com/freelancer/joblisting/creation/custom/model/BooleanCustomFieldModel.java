package com.freelancer.joblisting.creation.custom.model;

import androidx.lifecycle.MutableLiveData;

import com.freelancer.joblisting.creation.custom.CustomFieldType;

public class BooleanCustomFieldModel extends CustomFieldModel {
    private final MutableLiveData<String> booleanFieldTitle;

    public BooleanCustomFieldModel() {
        super(CustomFieldType.BOOLEAN);
        booleanFieldTitle = new MutableLiveData<>();
    }

    public BooleanCustomFieldModel(String booleanFieldTitle) {
        super(CustomFieldType.BOOLEAN);
        this.booleanFieldTitle = new MutableLiveData<>(booleanFieldTitle);
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