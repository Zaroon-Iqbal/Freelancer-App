package com.freelancer.joblisting.creation.custom.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.freelancer.joblisting.creation.custom.CustomFieldType;

public class CustomFieldTemplateViewModel extends AndroidViewModel {
    private CustomFieldType customFieldType;

    private String hello;

    public CustomFieldTemplateViewModel(@NonNull Application application, CustomFieldType customFieldType) {
        super(application);
        hello = "Hi there";
        this.customFieldType = customFieldType;
    }

    public String getHello() {
        return hello;
    }
}
