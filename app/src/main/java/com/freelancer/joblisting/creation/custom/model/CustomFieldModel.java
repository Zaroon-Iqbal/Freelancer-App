package com.freelancer.joblisting.creation.custom.model;

import androidx.lifecycle.MutableLiveData;

import com.freelancer.joblisting.creation.custom.CustomFieldType;

public abstract class CustomFieldModel {
    private final CustomFieldType customFieldType;
    private final MutableLiveData<String> customFieldName;
    private final MutableLiveData<Double> additionalCost;

    public CustomFieldModel(CustomFieldType customFieldType) {
        this.customFieldType = customFieldType;
        this.customFieldName = new MutableLiveData<>();
        this.additionalCost = new MutableLiveData<>();
    }

    public CustomFieldModel(CustomFieldType customFieldType, String customFieldName, Double additionalCost) {
        this.customFieldType = customFieldType;
        this.customFieldName = new MutableLiveData<>(customFieldName);
        this.additionalCost = new MutableLiveData<>(additionalCost);
    }

    @Override
    public String toString() {
        return "CustomFieldModel{" +
                "customFieldType=" + customFieldType +
                ", customFieldName='" + customFieldName.getValue() + '\'' +
                ", additionalCost='" + additionalCost.getValue() + '\'' +
                '}';
    }
}
