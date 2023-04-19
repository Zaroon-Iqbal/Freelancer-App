package com.freelancer.joblisting.creation.custom.model;

public enum FieldType {
    SELECTION("Selection"),
    //SINGLE_SELECT("Single-select"),
    //MULTI_SELECT("Multi-select"),
    BOOLEAN("Checkbox"),
    FREE_FORM("Free-form");

    public final String customFieldName;

    FieldType(String customFieldName) {
        this.customFieldName = customFieldName;
    }
}