package com.freelancer.joblisting.creation.custom.viewmodel;

import androidx.lifecycle.ViewModel;

import com.freelancer.joblisting.creation.custom.FieldType;
import com.freelancer.joblisting.creation.custom.model.BooleanCustomFieldModel;

public class CustomCheckboxViewModel extends ViewModel {
    private final BooleanCustomFieldModel model;
    private FieldType fieldType;

    public CustomCheckboxViewModel() {
        fieldType = FieldType.BOOLEAN;
        model = new BooleanCustomFieldModel();
    }

    public BooleanCustomFieldModel getModel() {
        return model;
    }
}
