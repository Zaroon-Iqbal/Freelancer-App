package com.freelancer.joblisting.creation.custom.viewmodel;

import androidx.lifecycle.ViewModel;

import com.freelancer.joblisting.creation.custom.model.SelectionFieldModel;
import com.freelancer.joblisting.creation.custom.model.SelectionType;

public class SelectionFieldViewModel extends ViewModel {
    private final SelectionFieldModel customFieldModel;
    private SelectionType selectionType;

    public SelectionFieldViewModel(SelectionType selectionType) {
        this.selectionType = selectionType;
        customFieldModel = new SelectionFieldModel(selectionType);
    }

    public SelectionFieldModel getCustomFieldModel() {
        return customFieldModel;
    }
}
