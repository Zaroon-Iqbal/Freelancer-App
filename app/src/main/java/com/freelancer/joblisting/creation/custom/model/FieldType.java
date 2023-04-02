package com.freelancer.joblisting.creation.custom.model;

public enum FieldType {
    MULTI_SELECT("Multiple selection"),
    SINGLE_SELECT("Single selection"),
    BOOLEAN("Checkbox"),
    FREE_FORM("Free-form");

    public final String customFieldName;

    FieldType(String customFieldName) {
        this.customFieldName = customFieldName;
    }
}