package com.freelancer.joblisting.creation.custom.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.freelancer.joblisting.creation.custom.FieldType;
import com.freelancer.joblisting.creation.custom.model.SelectionType;
import com.freelancer.joblisting.creation.custom.viewmodel.SelectionFieldViewModel;

public class CustomSelectionFieldFactory implements ViewModelProvider.Factory {
    private FieldType fieldType;

    public CustomSelectionFieldFactory(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        SelectionType selectionType;
        if (fieldType.equals(FieldType.MULTI_SELECT)) {
            selectionType = SelectionType.MULTIPLE;
        } else {
            selectionType = SelectionType.SINGLE;
        }
        return (T) new SelectionFieldViewModel(selectionType);
    }
}

