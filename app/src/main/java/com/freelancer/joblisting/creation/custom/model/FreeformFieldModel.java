package com.freelancer.joblisting.creation.custom.model;

import androidx.lifecycle.MutableLiveData;

import com.freelancer.joblisting.creation.custom.FieldType;

public class FreeformFieldModel extends CustomFieldModel {
    private final MutableLiveData<String> freeformFieldTitle;

    public FreeformFieldModel() {
        super(FieldType.FREE_FORM);
        freeformFieldTitle = new MutableLiveData<>();
    }

    public MutableLiveData<String> getFreeformFieldTitle() {
        return freeformFieldTitle;
    }

    @Override
    public String toString() {
        return super.toString() + ", FreeformFieldModel{" +
                "freeFormFieldTitle=" + freeformFieldTitle.getValue() +
                '}';
    }
}