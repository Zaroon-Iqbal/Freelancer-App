package com.freelancer.joblisting.creation.custom.viewmodel;

import androidx.lifecycle.ViewModel;

import com.freelancer.joblisting.creation.custom.model.SelectionFieldModel;
import com.freelancer.joblisting.creation.custom.model.SelectionType;

public class SelectionFieldViewModel extends ViewModel {
    private final SelectionFieldModel customFieldModel;

    public SelectionFieldViewModel() {
        customFieldModel = new SelectionFieldModel();
    }

    public void setSelectionType(SelectionType selectionType) {
        customFieldModel.setSelectionType(selectionType);
    }

    public SelectionFieldModel getCustomFieldModel() {
        return customFieldModel;
    }
}
