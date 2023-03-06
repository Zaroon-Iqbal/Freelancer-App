package com.freelancer.joblisting.creation.custom.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.freelancer.joblisting.creation.custom.CustomFieldType;
import com.freelancer.joblisting.creation.custom.viewmodel.CustomSelectionFieldViewModel;

public class CustomSelectionFieldFactory implements ViewModelProvider.Factory {
    private CustomFieldType customFieldType;

    public CustomSelectionFieldFactory(CustomFieldType customFieldType) {
        this.customFieldType = customFieldType;
    }

    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CustomSelectionFieldViewModel(customFieldType);
    }
}

