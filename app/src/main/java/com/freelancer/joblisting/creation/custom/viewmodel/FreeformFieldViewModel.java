package com.freelancer.joblisting.creation.custom.viewmodel;

import androidx.lifecycle.ViewModel;

import com.freelancer.joblisting.creation.custom.model.FieldType;
import com.freelancer.joblisting.creation.custom.model.FreeformFieldModel;

public class FreeformFieldViewModel extends ViewModel {
    private FreeformFieldModel model;
    private FieldType fieldType;

    public FreeformFieldViewModel() {
        fieldType = FieldType.FREE_FORM;
        model = new FreeformFieldModel();
    }

    public FreeformFieldModel getModel() {
        return model;
    }
}
