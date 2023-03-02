package com.freelancer.joblisting.creation.custom;

public enum CustomFieldType {
    MULTI_SELECT("Multiple selection"),
    SINGLE_SELECT("Single selection"),
    BOOLEAN("Checkbox"),
    FREE_FORM("Free-form");

    public final String customFieldName;

    CustomFieldType(String customFieldName) {
        this.customFieldName = customFieldName;
    }
    public String getCustomFieldName() {
        return customFieldName;
    }

    public static CustomFieldType getFieldTypeFromString(String value) {
        try {
            return CustomFieldType.valueOf(value);
        } catch (IllegalArgumentException iae) {
            return BOOLEAN;
        }
    }
}