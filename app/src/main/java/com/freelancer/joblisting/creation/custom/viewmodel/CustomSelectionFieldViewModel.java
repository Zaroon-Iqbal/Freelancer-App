package com.freelancer.joblisting.creation.custom.viewmodel;

import androidx.lifecycle.ViewModel;

import com.freelancer.joblisting.creation.custom.CustomFieldType;

public class CustomSelectionFieldViewModel extends ViewModel {
    private CustomFieldType customFieldType;

    public CustomSelectionFieldViewModel(CustomFieldType customFieldType) {
        this.customFieldType = customFieldType;
    }
}
