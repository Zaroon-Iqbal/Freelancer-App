package com.freelancer.joblisting.creation.custom.model;

import com.freelancer.joblisting.creation.custom.CustomFieldType;

public class SelectionCustomFieldModel extends CustomFieldModel {
    private SelectionType selectionType;

    public SelectionCustomFieldModel(CustomFieldType customFieldType, SelectionType selectionType, String customFieldName) {
        super(customFieldType, customFieldName);
        this.selectionType = selectionType;
    }
}