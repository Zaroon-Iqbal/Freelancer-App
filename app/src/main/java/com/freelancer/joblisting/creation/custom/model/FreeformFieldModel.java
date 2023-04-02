package com.freelancer.joblisting.creation.custom.model;

import androidx.lifecycle.MutableLiveData;

public class FreeformFieldModel extends TemplateFieldModel {
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