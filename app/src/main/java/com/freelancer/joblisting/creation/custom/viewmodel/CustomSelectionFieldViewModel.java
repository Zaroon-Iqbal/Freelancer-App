package com.freelancer.joblisting.creation.custom.viewmodel;

import androidx.lifecycle.ViewModel;

import com.freelancer.joblisting.creation.custom.CustomFieldType;
import com.freelancer.joblisting.creation.custom.model.SelectionCustomFieldModel;

public class CustomSelectionFieldViewModel extends ViewModel {
    private final SelectionCustomFieldModel customFieldModel;
    private CustomFieldType customFieldType;

    public CustomSelectionFieldViewModel(CustomFieldType customFieldType) {
        this.customFieldType = customFieldType;
        customFieldModel = new SelectionCustomFieldModel(customFieldType);
    }

    
}
