package com.freelancer.joblisting.creation.custom.viewmodel;

import androidx.lifecycle.ViewModel;

import com.freelancer.joblisting.creation.custom.model.BooleanFieldModel;
import com.freelancer.joblisting.creation.custom.model.FieldType;

public class CheckboxFieldViewModel extends ViewModel {
    private final BooleanFieldModel model;
    private FieldType fieldType;

    public CheckboxFieldViewModel() {
        fieldType = FieldType.BOOLEAN;
        model = new BooleanFieldModel();
    }

    public BooleanFieldModel getModel() {
        return model;
    }
}
